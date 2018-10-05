
import domein.FacadeControllers;
import domein.GroepsBewerkingController;
import domein.OefeningController;
import gui.HoofdMenuController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Code Written by Fréderic Terryn. All rights reserved. 
public class StartUp extends Application {

    @Override
    public void start(Stage stage) {

//        OefeningController dcOef = new OefeningController();
//        GroepsBewerkingController dcGroepsBew = new GroepsBewerkingController();
        FacadeControllers facade = new FacadeControllers();
        Scene scene = new Scene(new HoofdMenuController(facade));
        stage.setScene(scene);
        stage.setTitle("Break Out Box");
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(StartUp.class, args);
    }
}
