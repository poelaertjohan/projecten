/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Box;

import domein.BoxController;
import domein.FacadeControllers;
import domein.GroepsBewerkingController;
import domein.OefeningController;
import gui.HoofdMenuController;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Johan
 */
public class BoxOverzichtSchermController extends AnchorPane {

    @FXML
    private ListView<String> boxList;
    private ListView toegansCodesList;
    @FXML
    private TextField boxNaam;
    @FXML
    private TextField boxOmschrijving;
    @FXML
    private ListView boxActies;
    @FXML
    private ListView oefeningenLijst;

    @FXML
    private ImageView foto;
    @FXML
    private Button btnAnnulerenAlgemeen;
    @FXML
    private TextField txtFilter;

    private boolean isAangepast;

    /**
     * Initializes the controller class.
     */
    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button btnDelBox;
    @FXML
    private Button btnOpslaan;

    public BoxOverzichtSchermController(FacadeControllers facade) {

        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("BoxOverzichtScherm.fxml"));
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

    public void selectieInit(String geselecteerd) {
        //boxNaam.setDisable(false); naam is PK dus mag niet worden aangepast
        boxActies.setDisable(false);
        boxOmschrijving.setDisable(false);
        oefeningenLijst.setDisable(false);
        String[] info = dcBox.geefBoxOpNaam(geselecteerd).split(";");
        boxActies.setItems(dcBox.geefActiesObservableOpBoxNaam(geselecteerd));
        oefeningenLijst.setItems(dcBox.geefOefeningenObservableOpBoxNaam(geselecteerd));
        boxNaam.setText(info[0]);
        try {
            boxOmschrijving.setText(info[1]);
        } catch (Exception e) {
            boxOmschrijving.setText("");
        }

    }

    public void fillListView() {
        resetFilter();
        boxList.getItems().clear();
        boxList.setItems(dcBox.geefAlleBoxesNamenObservable());
        boxList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                -> {
            if (newValue != null) {
                selectieInit(newValue);
            }
        });
    }

    @FXML
    public void addBox(ActionEvent event) {
        Scene s = this.getScene();
        Stage sttage = (Stage) s.getWindow();
        s.setRoot(new BoxToevoegenSchermController((facade)));
    }

    @FXML
    public void annuleren(ActionEvent event) {
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
    private void delBox(ActionEvent event) {
        if (boxList.getSelectionModel().getSelectedIndex() >= 0) {
            dcBox.delBox(boxList.getSelectionModel().getSelectedItem());
            Scene s = this.getScene();
            Stage sttage = (Stage) s.getWindow();
            s.setRoot(new BoxOverzichtSchermController((facade)));
        }
    }

    @FXML
    private void filter(KeyEvent event) {
        String newValue = txtFilter.getText();
        dcBox.changeFilter(newValue);
    }

    private void resetFilter() {
        String newValue = txtFilter.getText();
        dcBox.changeFilter(newValue);
    }

    @FXML
    private void Opslaan(ActionEvent event) {
        String naam = boxNaam.getText();
        String nieuweOmschrijving = boxOmschrijving.getText();

        dcBox.wijzigBox(naam, nieuweOmschrijving);
        isAangepast = false;
        opslaanAanUit();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informatie");
        alert.setHeaderText("Wijziging");
        alert.setContentText("Box is gewijzigd.");
        alert.showAndWait();
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
