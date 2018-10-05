/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Box;

import com.sun.javafx.tk.Toolkit;
import domein.BoxController;
import domein.FacadeControllers;
import domein.GroepsBewerkingController;
import domein.OefeningController;
import gui.Acties.ActiesOverzichtController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Johan
 */
public class BoxToevoegenSchermController extends AnchorPane {

    @FXML
    private TextField txfNaam;
    @FXML
    private TextField txfOmschrijving;
    @FXML
    private Button btnannuleren;
    @FXML
    private Button btnOpslaan;
    @FXML
    private Button btnActieToevoegen;
    @FXML
    private TextField actieText;
    @FXML
    private ListView listAlleActies;
    @FXML
    private ListView listAlleOefeningen;
    @FXML
    private ListView<String> listBox;
    @FXML
    private Button btnNieuweActie;
    @FXML
    private Label naamTitel;

    private boolean isAangepast = false;

    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button btnOefeningenToevoegen;

    public BoxToevoegenSchermController(FacadeControllers facade) {
        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("BoxToevoegenScherm.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
            fillListViews();
            opslaanAanUit();
        } catch (IOException ex) {
            System.out.print("kan BoxToevoegenController niet openen");
        }
        //opslaanAanUit();
    }

    public void fillListViews() {
        EventHandler<MouseEvent> eventHandler = (event)
                -> {
            if (!event.isShortcutDown()) {
                Event.fireEvent(event.getTarget(), cloneMouseEvent(event));
                event.consume();
            }
        };
        listAlleActies.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listAlleActies.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        listAlleActies.addEventFilter(MouseEvent.MOUSE_RELEASED, eventHandler);
        listAlleOefeningen.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listAlleOefeningen.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        listAlleOefeningen.addEventFilter(MouseEvent.MOUSE_RELEASED, eventHandler);
        listAlleActies.setItems(dcBox.geefAlleOpgeslagenActies());
        listAlleOefeningen.setItems(dcOef.geefAlleOefeningenNamenObservable());
        listBox.setItems(dcBox.geefAlleBoxesNamenObservable());
        listBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                -> {
            if (newValue != null) {
                selectieInit(newValue);
                controleren();
            }
        });

    }

    private void selectieInit(String geselecteerd) {
        String[] info = dcBox.geefBoxOpNaam(geselecteerd).split(";");
        txfNaam.setText(info[0]);
        txfOmschrijving.setText(info[1]);
    }

    @FXML
    private void aanpassen(KeyEvent event) {
        isAangepast = true;
        opslaanAanUit();
    }

    @FXML
    private void annuleer(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("!");
        alert.setHeaderText("Wijzigingen opgeslagen?");
        alert.setContentText("Zorg ervoor dat al uw wijzigingen zijn opgeslaan voor u terug gaat. Niet opgeslagen wijzigingen gaan verloren.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Scene s = this.getScene();
            Stage stage = (Stage) s.getWindow();
            s.setRoot(new BoxOverzichtSchermController(facade));
        } else {
            alert.close();
        }

    }

    @FXML
    private void opslaan(ActionEvent event) {
        try {

            int oefLengte = listAlleOefeningen.getSelectionModel().getSelectedItems().size();
            int actiesLengte = listAlleActies.getSelectionModel().getSelectedItems().size();;
            String naam;
            String omschrijving;
            List<String> acties;
            naam = txfNaam.getText();
            omschrijving = txfOmschrijving.getText();
            acties = listAlleActies.getSelectionModel().getSelectedItems();
            List<String> oefeningNamen = new ArrayList<>();

            oefeningNamen = listAlleOefeningen.getSelectionModel().getSelectedItems();

            if (actiesLengte == (oefLengte - 1)) {
                dcBox.addBox(naam, oefeningNamen, acties, omschrijving);
                isAangepast = true;
                opslaanAanUit();
                Scene s = this.getScene();
                Stage stage = (Stage) s.getWindow();
                s.setRoot(new BoxOverzichtSchermController(facade));
            } else {
                int aantalOef = listAlleOefeningen.getSelectionModel().getSelectedItems().size();
                int aantalActies = listAlleActies.getSelectionModel().getSelectedItems().size();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("!");
                alert.setHeaderText("Correct aantal acties?");
                if (aantalOef - aantalActies < 1) {
                    alert.setContentText("Het aantal geslecteerde acties moet gelijk zijn aan het aantal geslecteerde oefeningen -1.%n"
                            + "selecteer nog " + (aantalOef - aantalActies - 1) + " actie(s)");
                } else {
                    alert.setContentText("Het aantal geslecteerde acties moet gelijk zijn aan het aantal geslecteerde oefeningen -1.");
                }

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    alert.close();
                }
            }
        } catch (Exception e) {
            controleren();
        }
    }

    private void controleren() {
        if (dcBox.geefAlleBoxesNamenObservable().contains(txfNaam.getText().toLowerCase())) {
            txfNaam.setStyle("-fx-text-box-border: red ;");
            naamTitel.setText("Deze naam bestaat al");
            naamTitel.setTextFill(Color.web("#ff0000"));
        } else if (txfNaam.getText().isEmpty() || txfNaam.getText() == null) {
            txfNaam.setStyle("-fx-text-box-border: red ;");
            naamTitel.setText("Naam mag niet leeg zijn");
            naamTitel.setTextFill(Color.web("#ff0000"));
        } else {
            txfNaam.setStyle("-fx-text-box-border: lightgrey ;");
            naamTitel.setText("NAAM");
            naamTitel.setTextFill(Color.web("#8b9dc3"));

        }

    }

    private MouseEvent cloneMouseEvent(MouseEvent event) {
        switch (Toolkit.getToolkit().getPlatformShortcutKey()) {
            case SHIFT:
                return new MouseEvent(
                        event.getSource(),
                        event.getTarget(),
                        event.getEventType(),
                        event.getX(),
                        event.getY(),
                        event.getScreenX(),
                        event.getScreenY(),
                        event.getButton(),
                        event.getClickCount(),
                        true,
                        event.isControlDown(),
                        event.isAltDown(),
                        event.isMetaDown(),
                        event.isPrimaryButtonDown(),
                        event.isMiddleButtonDown(),
                        event.isSecondaryButtonDown(),
                        event.isSynthesized(),
                        event.isPopupTrigger(),
                        event.isStillSincePress(),
                        event.getPickResult()
                );

            case CONTROL:
                return new MouseEvent(
                        event.getSource(),
                        event.getTarget(),
                        event.getEventType(),
                        event.getX(),
                        event.getY(),
                        event.getScreenX(),
                        event.getScreenY(),
                        event.getButton(),
                        event.getClickCount(),
                        event.isShiftDown(),
                        true,
                        event.isAltDown(),
                        event.isMetaDown(),
                        event.isPrimaryButtonDown(),
                        event.isMiddleButtonDown(),
                        event.isSecondaryButtonDown(),
                        event.isSynthesized(),
                        event.isPopupTrigger(),
                        event.isStillSincePress(),
                        event.getPickResult()
                );

            case ALT:
                return new MouseEvent(
                        event.getSource(),
                        event.getTarget(),
                        event.getEventType(),
                        event.getX(),
                        event.getY(),
                        event.getScreenX(),
                        event.getScreenY(),
                        event.getButton(),
                        event.getClickCount(),
                        event.isShiftDown(),
                        event.isControlDown(),
                        true,
                        event.isMetaDown(),
                        event.isPrimaryButtonDown(),
                        event.isMiddleButtonDown(),
                        event.isSecondaryButtonDown(),
                        event.isSynthesized(),
                        event.isPopupTrigger(),
                        event.isStillSincePress(),
                        event.getPickResult()
                );

            case META:
                return new MouseEvent(
                        event.getSource(),
                        event.getTarget(),
                        event.getEventType(),
                        event.getX(),
                        event.getY(),
                        event.getScreenX(),
                        event.getScreenY(),
                        event.getButton(),
                        event.getClickCount(),
                        event.isShiftDown(),
                        event.isControlDown(),
                        event.isAltDown(),
                        true,
                        event.isPrimaryButtonDown(),
                        event.isMiddleButtonDown(),
                        event.isSecondaryButtonDown(),
                        event.isSynthesized(),
                        event.isPopupTrigger(),
                        event.isStillSincePress(),
                        event.getPickResult()
                );

            default: // well return itself then
                return event;

        }
    }

    private void opslaanAanUit() {
        if (isAangepast) {
            btnOpslaan.setDisable(false);
        } else {
            btnOpslaan.setDisable(true);
        }
    }

}
