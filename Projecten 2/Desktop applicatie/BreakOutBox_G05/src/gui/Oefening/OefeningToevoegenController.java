/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.Oefening;

import com.sun.javafx.tk.Toolkit;
import domein.BoxController;
import domein.FacadeControllers;
import domein.GroepsBewerkingController;
import domein.OefeningController;
import gui.HoofdMenuController;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Johan
 */
public class OefeningToevoegenController extends AnchorPane
{

    @FXML
    private Button btnPdf;
    @FXML
    private Label lblPdf;

    @FXML
    private TextField txfNaam;
    @FXML
    private TextField txfOpgave;
    @FXML
    private TextField txfFeedback;
    @FXML
    private TextField txfAntwoord;
    @FXML
    private TextField txfVak;
    @FXML
    private ListView<String> listDoelstellingen;
    @FXML
    private Button btnOpslaan;
    private ListView<?> lstvGroepsbewerkingen;
    @FXML
    private ListView<String> listGroepsbewerkingen;
    @FXML
    private ListView<String> listBestaandeOefeningen;
    private List<String> namenGroepsBewerkingen;
    String PdfName;
    private FileChooser fileChooser;
    private File selectedFile;

    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;
    @FXML
    private Button btnannuleren;
    @FXML
    private TextField txfTimeLeft;
    private boolean isAangepast = false;
    private Button btnVoegToe;
    @FXML
    private Label lblNaam;
    @FXML
    private Label lblOpgave;
    @FXML
    private Label lblFeedback;
    @FXML
    private Label lblAntwoord;
    @FXML
    private Label lblVak;
    @FXML
    private Label lblTijd;

    /**
     *
     * @param dcOef
     */
    public OefeningToevoegenController(FacadeControllers facade)
    {

        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("oefeningToevoegen.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
            fillListView();
            PdfName = "";
            opslaanAanUit();
        } catch (IOException ex)
        {
            System.out.print("kan oefeningenToevoegenController niet openen");
        }
        txfVak.setText("Wiskunde");

    }

    @FXML
    private void annuleer(ActionEvent event)
    {
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
        } else
        {
            Scene s = this.getScene();
            Stage stage = (Stage) s.getWindow();
            s.setRoot(new HoofdMenuController(facade));
        }

    }

    @FXML
    private void opslaan(ActionEvent event)
    {
        String naam;
        String opgave;
        String feedback;
        String antwoord;
        String vak;
        String padNaarPdf;
        String doelstelling;
        int timeleft;

        boolean check = true;
        while (check)
        {
            try
            {
                if (txfTimeLeft.getText().isEmpty())
                {//tijd op 0 zetten indien leeggelaten is
                    txfTimeLeft.setText("0");
                }

                naam = txfNaam.getText();
                opgave = txfOpgave.getText();
                feedback = txfFeedback.getText();
                antwoord = txfAntwoord.getText();
                vak = txfVak.getText();
                doelstelling = listDoelstellingen.getSelectionModel().getSelectedItem();
                timeleft = Integer.parseInt(txfTimeLeft.getText());

                namenGroepsBewerkingen = new ArrayList<>();
                if (selectedFile == null)
                {
                    padNaarPdf = " ";
                } else
                {
                    padNaarPdf = selectedFile.getAbsolutePath();
                }
                namenGroepsBewerkingen = listGroepsbewerkingen.getSelectionModel().getSelectedItems();

                if (!namenGroepsBewerkingen.isEmpty())
                {
                    dcOef.addOefening(naam, opgave, feedback, antwoord, namenGroepsBewerkingen, padNaarPdf, vak, doelstelling, timeleft);
                    isAangepast = false;
                    opslaanAanUit();
                    Scene s = this.getScene();
                    Stage stage = (Stage) s.getWindow();
                    s.setRoot(new OefeningenLijstSchermController(facade));
                }else
                {
                    controleInvoer();
                }

            } catch (IllegalArgumentException e)
            {
                controleInvoer();
            } catch (Exception ex)
            {
                controleInvoer();
            }
            check = false;
        }
    }

    private void controleInvoer()
    {

        lblPdf.setText("");

        if (txfNaam.getText().isEmpty())
        {
            txfNaam.setStyle("-fx-text-box-border: red ;");
            lblNaam.setText("Geef een naam in voor de oefening.");
            lblNaam.setTextFill(Color.web("red"));
        } else if (dcOef.geefAlleOefeningenNamenObservable().contains(txfNaam.getText().toLowerCase()))
        {
            lblNaam.setText("Er bestaat al een oef met deze naam.");
            lblNaam.setTextFill(Color.web("#ff0000"));
            txfNaam.setStyle("-fx-text-box-border: red ;");

        } else
        {
            txfNaam.setStyle("-fx-text-box-border: lightgrey ;");
            lblNaam.setText("NAAM");
            lblNaam.setTextFill(Color.web("#8b9dc3"));
        }

        if (txfOpgave.getText().isEmpty() && PdfName.isEmpty())
        {
            txfOpgave.setStyle("-fx-text-box-border: red ;");
            lblOpgave.setText("Vul een opgave in voor de oefening, of selecteer een pdfbestand.");
            lblOpgave.setTextFill(Color.web("#ff0000"));
            lblPdf.setText("Gelieve een bestand te selecteren.");
            lblPdf.setTextFill(Color.web("#ff0000"));
        } else
        {
            lblOpgave.setText("OPGAVE: (zelf ingeven of .PDF-bestand selecteren hieronder)");
            txfOpgave.setStyle("-fx-text-box-border: lightgrey ;");
            lblOpgave.setTextFill(Color.web("#8b9dc3"));
        }

        if (listGroepsbewerkingen.getSelectionModel().getSelectedItems().isEmpty())
        {
            listGroepsbewerkingen.setStyle("-fx-background-color: red;");
        } else
        {
            listGroepsbewerkingen.setStyle("-fx-background-color: lightgrey;");
        }

        if (txfFeedback.getText().isEmpty())
        {
            txfFeedback.setStyle("-fx-text-box-border: red ;");
            lblFeedback.setText("Vul feedback in voor de leerlingen bij deze oefening");
            lblFeedback.setTextFill(Color.web("red"));
        } else
        {
            lblFeedback.setText("FEEDBACK");
            txfFeedback.setStyle("-fx-text-box-border: lightgrey ;");
            lblFeedback.setTextFill(Color.web("#8b9dc3"));
        }
        if (txfAntwoord.getText().isEmpty() || !txfAntwoord.getText().matches("[0-9]*") && !txfAntwoord.getText().matches("-[0-9]*"))
        {
            txfAntwoord.setStyle("-fx-text-box-border: red ;");
            lblAntwoord.setText("Antwoord moet numeriek zijn.");
            lblAntwoord.setTextFill(Color.web("#ff0000"));
        } else
        {
            lblAntwoord.setText("Antwoord");
            txfAntwoord.setStyle("-fx-text-box-border: lightgrey ;");
            lblAntwoord.setTextFill(Color.web("#8b9dc3"));
        }
        if (txfVak.getText().isEmpty())
        {
            txfVak.setStyle("-fx-text-box-border: red ;");
            lblVak.setText("Vul hier de naam van het vak in.");
            lblVak.setTextFill(Color.web("red"));
        } else
        {
            lblVak.setText("VAK");
            txfVak.setStyle("-fx-text-box-border: lightgrey ;");
            lblVak.setTextFill(Color.web("#8b9dc3"));
        }

        if (txfTimeLeft.getText().matches("\\D+"))
        {//indien niet leeg en geen digit ==> rood
            txfTimeLeft.setStyle("-fx-text-box-border: red ;");
            lblTijd.setTextFill(Color.web("#8b9dc3"));
        } else
        {
            lblTijd.setText("Tijd in minuten");
            txfTimeLeft.setStyle("-fx-text-box-border: lightgrey ;");
            lblTijd.setTextFill(Color.web("#8b9dc3"));
        }
        if (listDoelstellingen.getSelectionModel().getSelectedItems().size() <= 0)
        {
            listDoelstellingen.setStyle("-fx-background-color: red;");
        } else
        {
            listDoelstellingen.setStyle("-fx-background-color: lightgrey;");
        }

        if (selectedFile != null)
        {
            if (lblPdf.getText().equals(""))
            {
                lblPdf.setText(selectedFile.toString());
            }
        }

//        dcOef.geefAlleOefeningenNamenObservable().stream().forEach(s -> {
//            if (s.equals(txfNaam.toString())) {
//                lblPdf.setText("Er bestaat al een oef met deze naam.");
//                txfTimeLeft.setStyle("-fx-text-box-border: red ;");
//            }
//        });
    }

    private void fillListView()
    {

        EventHandler<MouseEvent> eventHandler = (event)
                ->
        {
            if (!event.isShortcutDown())
            {
                Event.fireEvent(event.getTarget(), cloneMouseEvent(event));
                event.consume();
            }
        };
        listGroepsbewerkingen.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listGroepsbewerkingen.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
        listGroepsbewerkingen.addEventFilter(MouseEvent.MOUSE_RELEASED, eventHandler);
        listGroepsbewerkingen.getItems().clear();
        listGroepsbewerkingen.setItems(dcGroepsBew.getObservableNamenGroepsBewerking());
        listBestaandeOefeningen.setItems(dcOef.geefAlleOefeningenNamenObservable());
        listDoelstellingen.setItems(dcOef.geefAlleOpgeslagenDoelstellingen());
        listBestaandeOefeningen.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue)
                ->
        {
            if (newValue != null)
            {
                selectieInit(newValue);
                controleInvoer();
            }
        });
    }

    public void selectieInit(String infoNaam)
    {
        /*  if (isAangepast)
        {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Nog niet alle wijzigingen zijn opgeslagen, en zullen verloren gaan. Toch verder gaan? ", ButtonType.YES, ButtonType.NO);
            alert.setTitle("Opgepast");
            alert.setHeaderText("Aanpassingen zijn nog niet opgeslaan");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES)
            {
                btnVoegToe.setDisable(false);
                txfNaam.setEditable(false);
                txfOpgave.setEditable(false);
                txfAntwoord.setEditable(false);
                txfVak.setEditable(false);
            }
        }*/

        String[] info = dcOef.giveOefNameReturnOefObjectInString(infoNaam).split(";");
        txfNaam.setText(info[0]);
        txfOpgave.setText(info[1]);
        txfFeedback.setText(info[2]);
        txfAntwoord.setText(info[3]);
        txfVak.setText(info[4]);

        txfTimeLeft.setText(info[6]);

    }

    @FXML
    private void pdfToevoegen(ActionEvent event)
    {

        fileChooser = new FileChooser();
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null)
        {
            PdfName = selectedFile.getName();
            PdfName = PdfName.substring(0, PdfName.lastIndexOf('.'));
            lblPdf.setText("Bestand geselecteerd: " + selectedFile.getName());
            lblPdf.setTextFill(Color.web("#000000"));
            txfOpgave.setStyle("-fx-text-box-border: lightgrey ;");
        } else
        {
            lblPdf.setText("Gelieve een bestand te selecteren.");
        }

        if (txfOpgave.getText().isEmpty())
        {
            txfOpgave.setText(PdfName);
        }

    }

    private void naamAanpassen(KeyEvent event)
    {
        txfNaam.setStyle("-fx-text-box-border: lightgrey ;");
    }

    private void opgaveAanpassen(KeyEvent event)
    {
        txfOpgave.setStyle("-fx-text-box-border: lightgrey ;");
    }

    private void feedbackAanpassen(KeyEvent event)
    {
        txfFeedback.setStyle("-fx-text-box-border: lightgrey ;");
    }

    private void antwoordAanpassen(KeyEvent event)
    {
        txfAntwoord.setStyle("-fx-text-box-border: lightgrey ;");
    }

    private void vakAanpassen(KeyEvent event)
    {
        txfAntwoord.setStyle("-fx-text-box-border: lightgrey ;");
    }

    private MouseEvent cloneMouseEvent(MouseEvent event)
    {
        switch (Toolkit.getToolkit().getPlatformShortcutKey())
        {
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

    @FXML
    private void Aangepast(KeyEvent event)
    {
        isAangepast = true;
        opslaanAanUit();
    }

    private void opslaanAanUit()
    {
        if (isAangepast)
        {
            btnOpslaan.setDisable(false);
        } else
        {
            btnOpslaan.setDisable(true);
        }
    }

}
