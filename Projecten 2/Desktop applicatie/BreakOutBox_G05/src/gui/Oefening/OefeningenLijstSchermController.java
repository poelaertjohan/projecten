package gui.Oefening;

import domein.BoxController;
import domein.FacadeControllers;
import domein.GroepsBewerkingController;
import domein.OefeningController;
import gui.HoofdMenuController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author frede
 */
public class OefeningenLijstSchermController extends AnchorPane {

    @FXML
    private TextField naam;
    @FXML
    private TextField opgave;
    @FXML
    private TextField oplossing;
    @FXML
    private TextField feedback;
    @FXML
    private TextField vak;
    @FXML
    private TextField txtFilter;
    @FXML
    private TextField doelstelling;
    @FXML
    private TitledPane selectFilter;
    @FXML
    private Label naamTitel;
    @FXML
    private Label opgaveTitel;
    @FXML
    private Label vakTitel;
    @FXML
    private Label oplossingTitel;
    @FXML
    private Label feedbackTitel;
    @FXML
    private Label tijdTitel;
    @FXML
    private Button btnAddOef;
    @FXML
    private Button btnDelOef;
    @FXML
    Button btnAnnuleren;
    @FXML
    private ListView<String> groepsBewerkingenList;
    @FXML
    private ListView<String> listOefeningen; //moet opgevuld wordden door oefeningenLijst
    @FXML
    private TextField timeLeft;
    @FXML
    private Button btnOpslaan;

    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;

    boolean isAangepast = false;

    public OefeningenLijstSchermController(FacadeControllers facade) {
        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("OefeningenLijstScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            fillListview();
            opslaanAanUit();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        dcOef.addListener(e -> btnDelOef.setDisable(dcOef.geenOefeningen()));

    }

    public void selectieInit(String infoNaam) {
        opgave.setDisable(false);
        oplossing.setDisable(false);
        feedback.setDisable(false);
        vak.setDisable(false);
        txtFilter.setDisable(false);
        doelstelling.setDisable(false);
        groepsBewerkingenList.setDisable(false);
        listOefeningen.setDisable(false);
        timeLeft.setDisable(false);
        String[] info = geefOefeningOpNaam(infoNaam).split(";");
        naam.setText(info[0]);
        opgave.setText(info[1]);
        feedback.setText(info[2]);
        oplossing.setText(info[3]);
        vak.setText(info[4]);
        doelstelling.setText(info[5]);
        timeLeft.setText(info[6]);
//        groepsBewerkingenList.getItems().clear();
        groepsBewerkingenList.setItems(dcOef.geefGroepsBewerkingenObservableOpOefeningNaam(info[0]));
    }

    public void fillListview() {
        listOefeningen.getItems().clear();
        //ObservableList<String> observ = (ObservableList<String>) dc.geefAlleOefeningenStringFormat();
        resetFilter();
        listOefeningen.setItems(dcOef.geefAlleOefeningenNamenObservable());

        List<String> filterKeuzes = new ArrayList<>();
        ListView filterList = new ListView();
        filterKeuzes.add("Vak");
        filterKeuzes.add("Naam");
        filterKeuzes.add("Doelstelling");
        filterList.setItems(FXCollections.observableArrayList(filterKeuzes));
        selectFilter.setContent(filterList);
        selectFilter.setExpanded(false);
        selectFilter.setText("Naam");
        filterList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                -> {
            if (newValue != null) {
                selectFilter.setText(newValue.toString());
                selectFilter.setExpanded(false);
                resetFilter();
            }
        });
        listOefeningen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                -> {
            if (newValue != null) {
                selectieInit(newValue);
                setLayout();

            }
        });

    }

    @FXML
    private void addOef(ActionEvent event) {
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new OefeningToevoegenController(facade));
    }

    @FXML
    private void delOef(ActionEvent event) {
        try {
            String geselecteerdeOef = listOefeningen.getSelectionModel().getSelectedItem();
            if (geselecteerdeOef != null) {
                listOefeningen.getSelectionModel().clearSelection();
                String[] info = geefOefeningOpNaam(geselecteerdeOef).split(";");
//            dc.delOef(geefOefeningOpNaam(geselecteerdeOef));
                dcOef.delOef(info[0]);
            }

            listOefeningen.getSelectionModel().clearSelection();

            Scene s = this.getScene();
            Stage stage = (Stage) s.getWindow();
            s.setRoot(new OefeningenLijstSchermController(facade));
        } catch (Exception e) {
        }
    }

    private String geefOefeningOpNaam(String naam) {
        return dcOef.giveOefNameReturnOefObjectInString(naam);
    }

    @FXML
    private void filter(KeyEvent event) {
        String newValue = txtFilter.getText();
        dcOef.changeFilter(newValue, selectFilter.getText());
    }

    private void resetFilter() {
        String newValue = txtFilter.getText();
        dcOef.changeFilter(newValue, selectFilter.getText());
    }

    @FXML
    private void oefeningOpslaan(ActionEvent event) {
        String origineleNaam = listOefeningen.getSelectionModel().getSelectedItem();
        String nieuweNaam = naam.getText();
        String nieuweOpdracht = opgave.getText();
        String nieuweFeedback = feedback.getText();
        String nieuwAntwoord = oplossing.getText();
        String nieuwVak = vak.getText();
        String nieuweDoelstelling = doelstelling.getText();
        int timeleft = 0;
        if (timeLeft.getText().matches("\\d+")) {
            timeleft = Integer.parseInt(timeLeft.getText());
        }
        if ((!nieuwAntwoord.matches("[0-9]*") && !nieuwAntwoord.matches("-[0-9]*")) || nieuweNaam.isEmpty() || nieuweOpdracht.isEmpty()
                || nieuweFeedback.isEmpty() || nieuwVak.isEmpty() || nieuwAntwoord.isEmpty()) {

            if (nieuweNaam.isEmpty() || nieuweNaam.equals(null)) {
                naam.setStyle("-fx-text-box-border: red ;");
                naamTitel.setText("Geef hier een naam in voor de oefening");
                naamTitel.setTextFill(Color.web("#ff0000"));
            } else {
                naamTitel.setText("NAAM");
                naamTitel.setTextFill(Color.web("#8b9dc3"));
                naam.setStyle("-fx-text-box-border: lightgrey ;");
            }
            if (nieuweOpdracht.isEmpty() || nieuweOpdracht.equals(null)) {
                opgave.setStyle("-fx-text-box-border: red ;");
                opgaveTitel.setText("Geef hier een opgave in voor de oefening");
                opgaveTitel.setTextFill(Color.web("#ff0000"));
            } else {
                opgaveTitel.setText("OPGAVE");
                opgaveTitel.setTextFill(Color.web("#8b9dc3"));
                opgave.setStyle("-fx-text-box-border: lightgrey ;");
            }
            if (nieuweFeedback.isEmpty() || nieuweFeedback.equals(null)) {
                feedback.setStyle("-fx-text-box-border: red ;");
                feedbackTitel.setText("Geef hier een feedback in voor de oefening");
                feedbackTitel.setTextFill(Color.web("#ff0000"));
            } else {
                feedbackTitel.setText("FEEDBACK");
                feedbackTitel.setTextFill(Color.web("#8b9dc3"));
                feedback.setStyle("-fx-text-box-border: lightgrey ;");
            }
            if (nieuwVak.isEmpty() || nieuwVak.equals(null)) {
                vak.setStyle("-fx-text-box-border: red ;");
                vakTitel.setText("Geef hier een vak in voor de oefening");
                vakTitel.setTextFill(Color.web("#ff0000"));
            } else {
                vakTitel.setText("VAK");
                vakTitel.setTextFill(Color.web("#8b9dc3"));
                vak.setStyle("-fx-text-box-border: lightgrey ;");
            }
            if (nieuwAntwoord.isEmpty() || nieuwAntwoord.equals(null)) {
                oplossing.setStyle("-fx-text-box-border: red ;");
                oplossingTitel.setText("Geef hier een oplossing in voor de oefening");
                oplossingTitel.setTextFill(Color.web("#ff0000"));
            } else if (!nieuwAntwoord.matches("[0-9]*") && !nieuwAntwoord.matches("-[0-9]*")) {
                oplossing.setStyle("-fx-text-box-border: red ;");
                oplossingTitel.setText("Het antwoord moet numeriek zijn");
                oplossingTitel.setTextFill(Color.web("#ff0000"));
            } else {
                oplossingTitel.setText("OPLOSSING");
                oplossingTitel.setTextFill(Color.web("#8b9dc3"));
                oplossing.setStyle("-fx-text-box-border: lightgrey ;");
            }
        } else {
            dcOef.wijzigOefening(origineleNaam, nieuweNaam, nieuweOpdracht, nieuweFeedback, nieuwAntwoord, nieuwVak, nieuweDoelstelling, timeleft);
            isAangepast = false;
            opslaanAanUit();
            setLayout();

        }

        //        Scene s = this.getScene();
        //        Stage stage = (Stage) s.getWindow();
        //        s.setRoot(new HoofdMenuController(dc));
    }

    @FXML
    private void annuleren(ActionEvent event) {
        if (isAangepast) {
            Alert alert = new Alert(AlertType.CONFIRMATION, "Er zijn aanpassingen gemaakt die nog niet zijn opgeslaan. Bent u zeker dat u wilt annuleren?", ButtonType.YES, ButtonType.NO);
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
    private void naamAangepast(KeyEvent event) {
        isAangepast = true;
        opslaanAanUit();
    }

    @FXML
    private void oplossingAangepast(KeyEvent event) {
        isAangepast = true;
        opslaanAanUit();

    }

    @FXML
    private void feedbackAangepast(KeyEvent event) {
        isAangepast = true;
        opslaanAanUit();
    }

    @FXML
    private void opgaveAantepast(KeyEvent event) {
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

    private void setLayout() {
        naamTitel.setText("NAAM");
        naamTitel.setTextFill(Color.web("#8b9dc3"));
        naam.setStyle("-fx-text-box-border: lightgrey ;");
        opgaveTitel.setText("OPGAVE");
        opgaveTitel.setTextFill(Color.web("#8b9dc3"));
        opgave.setStyle("-fx-text-box-border: lightgrey ;");
        feedbackTitel.setText("FEEDBACK");
        feedbackTitel.setTextFill(Color.web("#8b9dc3"));
        feedback.setStyle("-fx-text-box-border: lightgrey ;");
        vakTitel.setText("VAK");
        vakTitel.setTextFill(Color.web("#8b9dc3"));
        vak.setStyle("-fx-text-box-border: lightgrey ;");
        oplossingTitel.setText("OPLOSSING");
        oplossingTitel.setTextFill(Color.web("#8b9dc3"));
        oplossing.setStyle("-fx-text-box-border: lightgrey ;");
    }

}
