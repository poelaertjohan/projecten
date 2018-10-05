/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Acties;

import domein.BoxController;
import domein.FacadeControllers;
import domein.GroepsBewerkingController;
import domein.OefeningController;
import gui.HoofdMenuController;
import java.io.IOException;
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
public class ActiesOverzichtController extends AnchorPane {

    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;
    @FXML
    private ListView listActies;
    @FXML
    private TextField opgave;
    @FXML
    private Button btnOpslaan;
    @FXML
    private Label opgaveTitel;
    private boolean isAangepast = false;

    /**
     * Initializes the controller class.
     */
    public ActiesOverzichtController(FacadeControllers facade) {
        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ActiesOverzicht.fxml"));
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
        listActies.setItems(dcBox.geefAlleOpgeslagenActies());
    }

    @FXML
    public void verwijderen() {
        if (listActies.getSelectionModel().getSelectedIndex() >= 0) {
            String teVerwijderenActie = listActies.getSelectionModel().getSelectedItem().toString();
            dcBox.verwijderActie(teVerwijderenActie);
        }
    }

    @FXML
    public void annuleren() {
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
    public void opslaan() {
        if (opgave.getText().isEmpty()) {
            Scene s = this.getScene();
            Stage stage = (Stage) s.getWindow();
            s.setRoot(new HoofdMenuController(facade));
        }

    }

    @FXML
    public void toevoegen() {
        String nieuweActie = opgave.getText();
        if ((!nieuweActie.equals(null)) && (!nieuweActie.isEmpty())) {
            dcBox.slaActieOp(nieuweActie);
            opgave.clear();
            isAangepast = false;
            opgaveTitel.setText("NIEUWE ACTIE:");
            opgaveTitel.setTextFill(Color.web("#8b9dc3"));
            opgave.setStyle("-fx-text-box-border: lightgrey ;");

        } else {
            opgaveTitel.setText("voeg hier een naam in voor je actie.");
            opgaveTitel.setTextFill(Color.web("#ff0000"));
            opgave.setStyle("-fx-text-box-border: red ;");

        }

    }

    @FXML
    public void aanpassen(KeyEvent event
    ) {
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
