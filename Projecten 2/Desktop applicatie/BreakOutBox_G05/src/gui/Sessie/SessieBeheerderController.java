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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * FXML Controller class
 *
 * @author frede
 */
public class SessieBeheerderController extends AnchorPane {

    @FXML
    private TextField txfNaam;
    @FXML
    private TextField txfOmschrijving;
    @FXML
    private Button btnannuleren;
    @FXML
    private Button btnOpslaan;

    private List<String> leerlingen;
    private List<String> groepsnummers;
    private List<String> groepEnLeerling;
    //  private static final String EXCEL_FILE_LOCATION = "c:\\project\\Excel\\BOB.xls";
    @FXML
    private Button btnExcel;
    String ExcelName = "";
    private FileChooser fileChooser;
    private File selectedFile;
    @FXML
    private ListView<String> listBox;
    /**
     * Initializes the controller class.
     */
    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;
    private SessieController dcSessie;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private CheckBox chkAfstandsonderwijs;
    @FXML
    private Label lblLeerlingen;
    @FXML
    private DatePicker beschikbaarVan;
    @FXML
    private ListView<String> bestaandeSessie;
    @FXML
    private Label lblNaam;
    @FXML
    private Label lblDate;
    private boolean isAangepast;
    LocalDate date;
    private boolean excelInOrde = false;

    public SessieBeheerderController(FacadeControllers facade) {
        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();
        dcSessie = facade.getSessieController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SessieBeheerder.fxml"));

        loader.setController(this);
        try {
            loader.setRoot(this);
            loader.load();
            fillListView();
            opslaanAanUit();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    private void selectieInit(String newValue) {

        String selectedSessie = dcSessie.geefSessieOpNaam(newValue);

        String[] sessieInStukjes = selectedSessie.split(";");
        txfNaam.setText(sessieInStukjes[0]);
        txfOmschrijving.setText(sessieInStukjes[1]);

        /*weergave.append(naam);
        weergave.append(";");
        weergave.append(omschrijving);
        weergave.append(";");
        weergave.append(box.getNaam());
        weergave.append(";");
        weergave.append(code);
        weergave.append(";");
        weergave.append(afstandsOnderwijs);
        weergave.append(";");
        weergave.append(beschikbaarVanaf.toString()); */
    }

    private void fillListView() {
        bestaandeSessie.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                -> {
            if (newValue != null) {
                selectieInit(newValue);
                controleren();
            }
        });
        listBox.setItems(dcBox.geefAlleBoxesNamenObservable());
        bestaandeSessie.setItems(dcSessie.geefSessieObservable());
    }

    @FXML
    private void annuleren(ActionEvent event) {
        if (isAangepast) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Er zijn aanpassingen gemaakt die nog niet zijn opgeslaan. "
                    + "Bent u zeker dat u wilt annuleren?",
                    ButtonType.YES, ButtonType.NO);
            alert.setTitle("Opgepast");
            alert.setHeaderText("Aanpassingen zijn nog niet opgeslaan");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
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
    private void opslaan(ActionEvent event) {
        try {
            boolean isAfstandsonderwijs = chkAfstandsonderwijs.isSelected();
            String naamSessie = txfNaam.getText();
            String naamBox = listBox.getSelectionModel().getSelectedItem();
            String omschrijving = " ";
            if (!txfOmschrijving.getText().isEmpty()) {
                omschrijving = txfOmschrijving.getText();
            }
            date = LocalDate.now();
            if (beschikbaarVan.getValue() != null) {
                date = beschikbaarVan.getValue();
                if (date.isBefore(LocalDate.now())) {
                    beschikbaarVan.setStyle("-fx-background-color: red;");
                    throw new Exception("test");
                } else {
                    beschikbaarVan.setStyle("-fx-background-color: lightgrey;");
                }
            }
            if (excelInOrde) //        List<String> groepen
            {
                btnExcel.setStyle(" -fx-background-color: white;");

                dcSessie.addSessie(isAfstandsonderwijs, naamSessie, naamBox, omschrijving, date, groepEnLeerlingMerge());

                isAangepast = false;
                opslaanAanUit();
                Scene s = this.getScene();
                Stage stage = (Stage) s.getWindow();
                s.setRoot(new SessieOverzichtController(facade));

            } else {
                btnExcel.setStyle(" -fx-background-color: red;");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Excel");
                alert.setContentText("Kan excel bestand niet lezen");
                alert.showAndWait();
            }

        } catch (Exception e) {
            controleren();
        }
    }

    private void controleren() {
        if (txfNaam.getText().isEmpty() || txfNaam.getText() == null) {
            lblNaam.setText("Naam mag niet leeg zijn.");
            lblNaam.setTextFill(Color.web("#ff0000"));
            txfNaam.setStyle("-fx-text-box-border: red ;");
        } else if (dcSessie.geefSessieObservable().contains(txfNaam.getText().toLowerCase())) {
            lblNaam.setText("Er bestaat al een oef met deze naam.");
            lblNaam.setTextFill(Color.web("#ff0000"));
            txfNaam.setStyle("-fx-text-box-border: red ;");

        } else {
            txfNaam.setStyle("-fx-text-box-border: lightgrey ;");
            lblNaam.setText("NAAM");
            lblNaam.setTextFill(Color.web("#8b9dc3"));
        }

        if (ExcelName.equals("")) {
            btnExcel.setStyle(" -fx-background-color: red;");
        } else {
            btnExcel.setStyle(" -fx-background-color: white;");
        }

        if (listBox.getSelectionModel().getSelectedItems().size() <= 0) {
            listBox.setStyle("-fx-background-color: red;");
        } else {
            listBox.setStyle("-fx-background-color: lightgrey;");
        }
        if (beschikbaarVan.getValue() != null) {

            if (date.isBefore(LocalDate.now())) {
                lblDate.setText("De datum moet later zijn dan vandaag.");
                lblDate.setTextFill(Color.web("#ff0000"));
                beschikbaarVan.setStyle("-fx-text-box-border: red ;");

            } else {
                beschikbaarVan.setStyle("-fx-text-box-border: lightgrey ;");
                lblDate.setText("BESCHIKBAAR VANAF:");
                lblDate.setTextFill(Color.web("#8b9dc3"));
            }
        } else {
            lblDate.setText("geef hier de datum in vanaf wanneer de sessie gebruikt kan worden");
            lblDate.setTextFill(Color.web("#ff0000"));
            beschikbaarVan.setStyle("-fx-text-box-border: red ;");
        }
    }

    private List<String> groepEnLeerlingMerge() {
        groepEnLeerling = new ArrayList<>();

        for (int i = 0; i < leerlingen.size(); i++) {
            groepEnLeerling.add(groepsnummers.get(i) + ";" + leerlingen.get(i));
        }
        groepEnLeerling.sort(String::compareToIgnoreCase);

        return groepEnLeerling;
    }

//    private void vulOpties() {
//        List<String> opties = new ArrayList<>();
//
//        //afstandsleren
//        if (checkboxAfstandsleren.isSelected()) {
//            opties.add("Aantal groepen wordt doorgegeven, leerlingen schrijven zichzelf in");
//        } //nieuw excelbestand
//        else if (checkboxExcel.isSelected()) {
//            opties.add("Aantal groepen wordt doorgegeven, verdeel willekeurig");
//            opties.add("Aantal groepen wordt doorgegeven, leerlingen schrijven zichzelf in");
//        } else if (checkboxAfstandsleren.isSelected() && checkboxExcel.isSelected()) {
//            opties.add("Aantal groepen wordt doorgegeven, leerlingen schrijven zichzelf in");
//        } else {
//            opties.add("Bestaande database, leerkracht stelt zelf de groepen samen");//geeft aantal in en zegt wie in welke groep
//            opties.add("Bestaande database, aantal groepen wordt doorgegeven, leerlingen schrijven zichzelf in");
//            opties.add("Bestaande database, verdeel leerlingen willekeurig");//leerkracht geeft aantal in, lln worden er random ingezet
//        }
//        cboOpties.setItems(FXCollections.observableList(opties));
//    }
//    @FXML
//    private void checkboxAfstandslerenGewijzigd(ActionEvent event) {
//        vulOpties();
//    }
//
//    @FXML
//    private void checkboxExcelGewijzigd(ActionEvent event) {
//        vulOpties();
//    }
//    
    @FXML
    public void leesExcel() throws Exception {
        fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            ExcelName = selectedFile.getName();
            ExcelName = ExcelName.substring(0, ExcelName.lastIndexOf('.'));
            lblLeerlingen.setText("Bestand geselecteerd: " + selectedFile.getName());
            lblLeerlingen.setTextFill(Color.web("#000000"));
            /*  txfOpgave.setStyle("-fx-text-box-border: lightgrey ;");
        } else {
            lblPdf.setText("Gelieve een bestand te selecteren.");
        }

        txfOpgave.setText(PdfName);
             */

            Workbook workbook = null;
            leerlingen = new ArrayList<>();
            groepsnummers = new ArrayList<>();
            try {
                workbook = Workbook.getWorkbook(new File(selectedFile.getPath()));
                Sheet sheet = workbook.getSheet(0);
                int i = 2;
                int j = 3;
                while (j < 30) {
                    leerlingen.add(sheet.getCell(i, j).getContents());
                    groepsnummers.add(sheet.getCell((i + 1), j).getContents());
                    j++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.err.println("dit klopt nog niet volledig");
            } catch (IOException | BiffException e) {
            } finally {

                if (workbook != null) {
                    workbook.close();
                }

            }
            if (leerlingen.isEmpty() || groepsnummers.isEmpty()) {
                excelInOrde = false;
            } else {
                excelInOrde = true;
            }
        }

    }

    @FXML
    private void Aangepast(KeyEvent event) {
        isAangepast = true;
        opslaanAanUit();
    }

    private void opslaanAanUit() {
        if (isAangepast) {
            btnOpslaan.setDisable(false);
        } else {
            btnOpslaan.setDisable(true);
        }
    }
}
