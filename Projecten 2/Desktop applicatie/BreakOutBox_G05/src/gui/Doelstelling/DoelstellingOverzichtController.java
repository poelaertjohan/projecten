/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Doelstelling;

import domein.BoxController;
import domein.FacadeControllers;
import domein.GroepsBewerkingController;
import domein.OefeningController;
import gui.HoofdMenuController;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author frede
 */
public class DoelstellingOverzichtController extends AnchorPane {

    @FXML
    private ListView<?> listGroepsBewerkingen;
    @FXML
    private Button annuleren;
    @FXML
    private Button btnopslaan;
    @FXML
    private Button btnToevoegen;
    @FXML
    private Button btnVerwijderen;
    @FXML
    private TextField opgave;
    @FXML
    private Label opgaveTitel;
    @FXML
    private ListView listDoelstellingen;
    private boolean isAangepast = false;

    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;

    public DoelstellingOverzichtController(FacadeControllers facade) {

        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("DoelstellingOverzicht.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            fillListView();
            opslaanAanUit();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void fillListView() {
        listDoelstellingen.setItems(dcOef.geefAlleOpgeslagenDoelstellingen());
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
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new HoofdMenuController(facade));
    }

    @FXML
    private void toevoegen(ActionEvent event) {
        try {
            String doelstelling = opgave.getText();
            if ((!doelstelling.equals(null)) && (!doelstelling.isEmpty())) {
                dcOef.slaDoelstellingOp(doelstelling);
                opgave.clear();
                isAangepast = false;
                opgaveTitel.setText("NIEUWE DOELSTELLING:");
                opgaveTitel.setTextFill(Color.web("#8b9dc3"));
                opgave.setStyle("-fx-text-box-border: lightgrey ;");

            } else {
                opgaveTitel.setText("voeg hier een naam in voor je doelstelling.");
                opgaveTitel.setTextFill(Color.web("#ff0000"));
                opgave.setStyle("-fx-text-box-border: red ;");

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR,
                    "Er bestaat al een doelstelling met deze naam",
                    ButtonType.OK);
            alert.setTitle("Opgepast");
            alert.setHeaderText("Kan doelstelling niet aanmaken");
            alert.showAndWait();
        }

    }

    @FXML
    private void verwijderen(ActionEvent event) {
        if (listDoelstellingen.getSelectionModel().getSelectedIndex() >= 0) {
            String doelstelling = listDoelstellingen.getSelectionModel().getSelectedItem().toString();
            dcOef.verwijderDoelstelling(doelstelling);
        }
    }

    @FXML
    private void opgaveAangepast(KeyEvent event) {
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
