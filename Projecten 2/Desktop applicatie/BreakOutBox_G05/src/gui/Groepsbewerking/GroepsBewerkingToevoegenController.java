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
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
public class GroepsBewerkingToevoegenController extends AnchorPane {

    @FXML
    private TextField txfopgave;
    @FXML
    private TextField txfoperator;
    @FXML
    private TextField txfgetal;
    @FXML
    private Button btnopslaan;
    @FXML
    private Button btnannuleren;
    @FXML
    private ListView groepsBewerkingenLijst;
    @FXML
    private Label lblnaam;
    @FXML
    private Label lblgetal;
    @FXML
    private Label lblbewerking;
    private boolean isAangepast = false;

    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;
    @FXML
    private AnchorPane AnchorPane;

    public GroepsBewerkingToevoegenController(FacadeControllers facade) {

        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GroepsBewerkingToevoegen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {

            loader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        groepsBewerkingenLijst.setItems(dcGroepsBew.getObservableNamenGroepsBewerking());
        /*   dcOef.addListener(e -> btnDelOef.setDisable(dcOef.geenOefeningen())); */
        groepsBewerkingenLijst.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                -> {
            if (newValue != null) {
                selectieInit(newValue.toString());
            }
        });
    }

    private void selectieInit(String groepsBewerkingNaam) {
        String groepsBewerking = dcGroepsBew.geefGroepsBewerkingStringOpNaam(groepsBewerkingNaam);
        String[] groepsBewerkingString = groepsBewerking.split(";");
        txfopgave.setText(groepsBewerkingString[0]);
        txfoperator.setText(groepsBewerkingString[1]);
        txfgetal.setText(groepsBewerkingString[2]);

    }

    @FXML
    private void opslaan(ActionEvent event) throws SQLException {
        //oef in db steken

        if (txfopgave.getText().isEmpty() || txfoperator.getText().isEmpty()
                || txfgetal.getText().isEmpty() || !(txfgetal.getText().matches("[0-9]+"))) {

            if (txfopgave.getText().isEmpty()) {
                txfopgave.setStyle("-fx-text-box-border: red ;");
                lblnaam.setText("Vul hier een naam in voor de groepsbewerking. Bv: plus 5 ");
                lblnaam.setTextFill(Color.web("#ff0000"));
            } else {
                txfopgave.setStyle("-fx-text-box-border: #8b9dc3 ;");
                lblnaam.setTextFill(Color.web("#8b9dc3"));
            }

            if (txfoperator.getText().isEmpty()) {
                txfoperator.setStyle("-fx-text-box-border: red ;");
                lblbewerking.setText("Geef een bewerking in. Bv: + ");
                lblbewerking.setTextFill(Color.web("#ff0000"));
            } else {
                txfoperator.setStyle("-fx-text-box-border: #8b9dc3;");
                lblbewerking.setTextFill(Color.web("#8b9dc3"));
            }

            if (txfgetal.getText().isEmpty() || !(txfgetal.getText().matches("[0-9]+"))) {
                txfgetal.setStyle("-fx-text-box-border: red ;");
                lblgetal.setText("Geef een getal in. Bv: 5 ");
                lblgetal.setTextFill(Color.web("#ff0000"));
            } else {
                txfgetal.setStyle("-fx-text-box-border: #8b9dc3 ;");
                lblgetal.setTextFill(Color.web("#8b9dc3"));
            }
        } else {
            String opgave;
            String operator;
            String getal;

            opgave = txfopgave.getText();
            operator = txfoperator.getText();
            getal = txfgetal.getText();
            try {
                dcGroepsBew.addGroepsBewerking(opgave, operator, getal);
                isAangepast = false;
                Scene s = this.getScene();
                Stage stage = (Stage) s.getWindow();
                s.setRoot(new GroepsBewerkingLijstSchermControllerController(facade));
            } catch (Exception e) {
                groepsBewerkingenLijst.setItems(dcGroepsBew.getObservableNamenGroepsBewerking());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Dubbele groepsbewerking");
                alert.setContentText("Je kan geen groepsbewerking met dezelfde naam opslaan");
                alert.showAndWait();
            }

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
    private void opgaveAanpassen(KeyEvent event) {
        txfopgave.setStyle("-fx-text-box-border: lightgrey ;");
    }

    @FXML
    private void operatorAanpassen(KeyEvent event) {
        txfoperator.setStyle("-fx-text-box-border: lightgrey ;");
    }

    @FXML
    private void getalAanpassen(KeyEvent event) {
        txfgetal.setStyle("-fx-text-box-border: lightgrey ;");
    }

    @FXML
    private void Aangepast(KeyEvent event) {
        isAangepast = true;
    }

}
