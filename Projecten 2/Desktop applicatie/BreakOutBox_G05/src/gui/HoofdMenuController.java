/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import gui.Doelstelling.DoelstellingOverzichtController;
import gui.Acties.ActiesOverzichtController;
import gui.Sessie.SessieOverzichtController;
import gui.Box.BoxOverzichtSchermController;
import gui.Oefening.OefeningenLijstSchermController;
import gui.Groepsbewerking.GroepsBewerkingLijstSchermControllerController;
import domein.BoxController;
import domein.FacadeControllers;
import domein.GroepsBewerkingController;
import domein.OefeningController;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author frede
 */
public class HoofdMenuController extends AnchorPane
{

    @FXML
    Button btnOefeningenBeheren;

    private FacadeControllers facade;
    private GroepsBewerkingController dcGroepsBew;
    private OefeningController dcOef;
    private BoxController dcBox;
    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button btnGroepsbewerkingen;
    @FXML
    private ImageView foto;
    @FXML
    private Button btnBoxBeheren;
    @FXML
    private Button btnSessieBeheren;

    public HoofdMenuController(FacadeControllers facade)
    {

        this.facade = facade;
        dcGroepsBew = facade.getGroepsBewerkingController();
        dcOef = facade.getOefeningController();
        dcBox = facade.getBoxController();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hoofdMenu.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try
        {
            loader.load();
        } catch (IOException ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @FXML
    private void OefeningenBeheren(ActionEvent event)
    {
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new OefeningenLijstSchermController(facade));
    }

    private void Annuleren(ActionEvent event)
    {
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new OefeningenLijstSchermController(facade));
    }

    @FXML
    private void groepsbewerking(ActionEvent event)
    {
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new GroepsBewerkingLijstSchermControllerController(facade));
    }

    @FXML
    private void boxBeheren(ActionEvent event)
    {
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new BoxOverzichtSchermController(facade));
    }
    
    @FXML
    private void acties(ActionEvent event){
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new ActiesOverzichtController(facade));
    }
    
    @FXML
    private void doelstelling(ActionEvent event){
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new DoelstellingOverzichtController(facade));
    }
    
    @FXML
    private void sessieBeheren(ActionEvent event){
        Scene s = this.getScene();
        Stage stage = (Stage) s.getWindow();
        s.setRoot(new SessieOverzichtController(facade));
    }
    
      
}
