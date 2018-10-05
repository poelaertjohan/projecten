/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Groepsbewerking;

import domein.BoxController;
import domein.FacadeControllers;
import domein.GroepsBewerkingController;
import domein.OefeningController;
import gui.HoofdMenuController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Johan
 */
public class GroepsBewerkingLijstSchermControllerController extends AnchorPane {

    @FXML
    private ListView<String> listGroepsBewerkingen;
    @FXML
    private Button btnToevoegen;
    @FXML
    private Button btnVerwijderen;

    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;
    @FXML
    private Button annuleren;
    @FXML
    private Button btnopslaan;
    @FXML
    private TextField opgave;
    @FXML
    private TextField bewerking;
    @FXML
    private TextField getal;

    private boolean isAangepast = false;
    private boolean bewerkingGeselecteerd = true;

    public GroepsBewerkingLijstSchermControllerController(FacadeControllers facade) {
        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GroepsBewerkingLijstSchermController.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {

            loader.load();
            opslaanAanUit();
            vulLijstMetGroepsBewerkingen();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        listGroepsBewerkingen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                -> {
            if (newValue != null) {
                selectieInit(newValue);
            }
        });
    }

    public void selectieInit(String infoNaam) {
        opgave.setDisable(false);
        bewerking.setDisable(false);
        getal.setDisable(false);
        String[] info = dcGroepsBew.geefGroepsBewerkingStringOpNaam(infoNaam).split(";");
        opgave.setText(info[0]);
        bewerking.setText(info[1]);
        getal.setText(info[2]);
    }

    public void vulLijstMetGroepsBewerkingen() {
        listGroepsBewerkingen.setItems(dcGroepsBew.getObservableNamenGroepsBewerking());
    }

    @FXML
    private void toevoegen(ActionEvent event) {
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new GroepsBewerkingToevoegenController(facade));
    }

    @FXML
    private void verwijderen(ActionEvent event) {
        opgave.clear();
        bewerking.clear();
        getal.clear();
        try {
            String geselecteerdeGroepsBewerking = listGroepsBewerkingen.getSelectionModel().getSelectedItem();
            dcGroepsBew.delGroepsBewerking(geselecteerdeGroepsBewerking);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @FXML
    private void annuleren(ActionEvent event) {
        if (isAangepast) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Er zijn aanpassingen gemaakt die nog niet zijn opgeslaan. Bent u zeker dat u wilt annuleren?", ButtonType.YES, ButtonType.NO);
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
    private void Opslaan(ActionEvent event) {
        if (!(listGroepsBewerkingen.getSelectionModel().getSelectedItem().isEmpty())
                && !this.opgave.getText().isEmpty() && !this.bewerking.getText().isEmpty()
                && !this.getal.getText().isEmpty()) {
            String opgave = this.opgave.getText();
            String bewerking = this.bewerking.getText();
            String oplossing = this.getal.getText();
            dcGroepsBew.wijzigGroepsBewerking(opgave, bewerking, oplossing);

            isAangepast = false;
            opslaanAanUit();
        }

        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new GroepsBewerkingLijstSchermControllerController(facade));

    }

    @FXML
    private void opgaveAangepast(KeyEvent event) {
        isAangepast = true;
        opslaanAanUit();
    }

    @FXML
    private void bewerkingAangepast(KeyEvent event) {
        isAangepast = true;
        opslaanAanUit();
    }

    @FXML
    private void getalAangepast(KeyEvent event) {
        isAangepast = true;
        opslaanAanUit();
    }

    private void opslaanAanUit() {
        if (isAangepast) {
            btnopslaan.setDisable(false);
        } else {
            btnopslaan.setDisable(true);
        }
    }
}
