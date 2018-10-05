/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Sessie;

import domein.BoxController;
import domein.FacadeControllers;
import domein.GroepsBewerkingController;
import domein.OefeningController;
import domein.SessieController;
import gui.HoofdMenuController;
import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdfwriter.ContentStreamWriter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 * FXML Controller class
 *
 * @author Vital Verleyen
 */
public class SessieOverzichtController extends AnchorPane {

    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;
    private SessieController dcSessie;
    @FXML
    private ListView<String> sessieList;
    @FXML
    private ListView listOefeningen;
    @FXML
    private ListView actiesLijst;
    @FXML
    private ListView listGroepen;
    @FXML
    private TextField sessieNaam;
    @FXML
    private TextField sessieOmschrijving;
    @FXML
    private TextField sessieBox;
    @FXML
    private TextField sessieDatum;
    @FXML
    private TextField sessieCode;
    @FXML
    private CheckBox isAfstandsOnderwijs;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button btnAnnulerenAlgemeen;
    @FXML
    private Button btnDelSessie;
    @FXML
    private Button btnOpslaan;
    @FXML
    private ImageView foto;
    @FXML
    private Button btnGenereerPdf;
    @FXML
    private TextField txtFilter;
    private boolean isAangepast;

    public SessieOverzichtController(FacadeControllers facade) {
        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();
        dcSessie = facade.getSessieController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieOverzicht.fxml"));

        loader.setController(this);
        try {
            loader.setRoot(this);
            loader.load();
            fillListView();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        sessieList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                -> {
            if (newValue != null) {
                selectieInit(newValue);
            }
        });
        
        opslaanAanUit();
    }

    private void fillListView() {
        resetFilter();
        sessieList.setItems(dcSessie.geefSessieObservable());
    }

    private void selectieInit(String newValue) {
        listOefeningen.setDisable(false);
        actiesLijst.setDisable(false);
        listGroepen.setDisable(false);
//        sessieNaam.setDisable(false);
        sessieOmschrijving.setDisable(false);
        sessieBox.setDisable(false);
        sessieDatum.setDisable(false);
        sessieCode.setDisable(false);
        isAfstandsOnderwijs.setDisable(false);
        String[] sessieInfo = dcSessie.geefSessieOpNaam(newValue).split(";");
        String boxNaam = sessieInfo[2];
        String naamGekozenSessie = sessieList.getSelectionModel().getSelectedItem();
        sessieNaam.setText(sessieInfo[0]);
        sessieOmschrijving.setText(sessieInfo[1]);
        sessieBox.setText(boxNaam);
        sessieCode.setText(sessieInfo[3]);
        isAfstandsOnderwijs.setSelected(sessieInfo[4].equals("true") ? true : false);
        sessieDatum.setText(sessieInfo[5]);
        listOefeningen.setItems(dcBox.geefOefeningenObservableOpBoxNaam(boxNaam));
        actiesLijst.setItems(dcBox.geefActiesObservableOpBoxNaam(boxNaam));
        listGroepen.setItems(dcSessie.geefGroepenOpservableOpSessieNaam(naamGekozenSessie));
         btnGenereerPdf.setStyle("-fx-background-color: white;");
    }

    @FXML
    public void addSessie() {
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new SessieBeheerderController(facade));
    }

    @FXML
    public void delSessie() {
        try{
        String geselecteerdeSessie = sessieList.getSelectionModel().getSelectedItem();
        dcSessie.delSessie(geselecteerdeSessie);

        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new SessieOverzichtController(facade));
        } catch (Exception e){
        }
    }
    
    @FXML
    private void filter(KeyEvent event) {
        String newValue = txtFilter.getText();
        dcSessie.changeFilter(newValue);
    }

    private void resetFilter(){
        String newValue = txtFilter.getText();
        dcSessie.changeFilter(newValue);
    }
    

    @FXML
    public void annuleren() {
              if (isAangepast) 
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Er zijn aanpassingen gemaakt die nog niet zijn opgeslaan. Bent u zeker dat u wilt annuleren?", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Opgepast");
            alert.setHeaderText("Aanpassingen zijn nog niet opgeslaan");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) 
            {
                Scene s = this.getScene();
                Stage stage = (Stage) s.getWindow();
                s.setRoot(new HoofdMenuController(facade));
            }
        } else {
            Scene s = this.getScene();
            Stage stage = (Stage) s.getWindow();
            s.setRoot(new HoofdMenuController(facade));
        }
    }

    @FXML
    public void opslaan() throws Exception {
        String naam = sessieNaam.getText();
        String nieuweOmschrijving = " ";
        if (!sessieOmschrijving.getText().isEmpty()) {
            nieuweOmschrijving = sessieOmschrijving.getText();
        }
        boolean isNieuweAfstandsonderwijs = isAfstandsOnderwijs.isSelected();

        dcSessie.wijzigSessie(naam, nieuweOmschrijving, isNieuweAfstandsonderwijs);
        isAangepast = false;
        opslaanAanUit();
    }

    @FXML
    private void genereerPdf(ActionEvent event) throws IOException {
        btnGenereerPdf.setText("SELECTEER HET PAD WAAR DE PDF WORDT OPGESLAAN");
        try{
            pdfAanmaken();
        }catch(Exception e){
               Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Foutmelding");
            alert.setHeaderText("PDF genereren");
            alert.setContentText("Sluit het programma dat je gebruikt om de pdf te bekijken en probeer daarna opnieuw");
            alert.showAndWait();
        }
        btnGenereerPdf.setText("MAAK PDF");
    }

    private void pdfAanmaken() throws IOException {
/*om de pdf te fixen moeten we er voor zorgen dat het aantal groepsbewerkingen van elke oefening >= het aantal groepen is*/

        if (sessieList.getSelectionModel().getSelectedIndex() >= 0) {

            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(null);

            if (selectedDirectory != null) {
                String sessieNaam = sessieList.getSelectionModel().getSelectedItem();
                String pdfNaam = sessieNaam + "-BreakOutBox.pdf";
                PDDocument doc = new PDDocument();
                PDPage page = new PDPage();
                doc.addPage(page);
                PDPageContentStream contentStream = new PDPageContentStream(doc, page);

                contentStream.beginText();

                contentStream.setFont(PDType1Font.TIMES_ROMAN, 32);
                contentStream.newLineAtOffset(75, 725);
                contentStream.showText("BREAKOUTBOX - " + sessieNaam.toUpperCase());
                contentStream.moveTextPositionByAmount(0, -40);
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 22);
                contentStream.showText(dcSessie.getCodeDoorNaam(sessieNaam));

                for (String item : dcSessie.geefDetailsOpdrachtenOpSessieNaam(sessieNaam)) {
                    if (item.equalsIgnoreCase("eindeGroep")) {
                        contentStream.close();
                        PDPage newPage = new PDPage();
                        doc.addPage(newPage);
                        contentStream = new PDPageContentStream(doc, newPage);
                        contentStream.beginText();
                        contentStream.newLineAtOffset(75, 725);
                    } else if (item.equalsIgnoreCase("Groep")) {
                        contentStream.setFont(PDType1Font.TIMES_ROMAN, 24);
                        contentStream.showText(item);
                        contentStream.moveTextPositionByAmount(0, -20);
                    } else if (item.startsWith("Leden:")) {
                        contentStream.setFont(PDType1Font.TIMES_ROMAN, 20);
                        contentStream.showText(item);
                        contentStream.moveTextPositionByAmount(0, -20);
                    } else {
                        contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
                        contentStream.showText(item);
                        contentStream.moveTextPositionByAmount(0, -20);
                    }
                }

                contentStream.close();
                doc.save(selectedDirectory.getPath() + "\\" + pdfNaam);
                doc.close();
            }
        }
    }
    
    
      @FXML
    private void Aangepast(KeyEvent event) {
        isAangepast = true;
        opslaanAanUit();
    }
    
     private void opslaanAanUit(){
        if(isAangepast){
            btnOpslaan.setDisable(false);
        }
        else{
            btnOpslaan.setDisable(true);
        }
    }
}
