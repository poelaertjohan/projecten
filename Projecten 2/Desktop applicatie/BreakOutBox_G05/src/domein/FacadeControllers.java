/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

/**
 *
 * @author JorisLaptop
 */
public class FacadeControllers {

    private BoxController boxController;
    private OefeningController oefeningController;
    private GroepsBewerkingController groepsBewerkingController;
    private SessieController sessieController;

    public FacadeControllers() {
        this.boxController = new BoxController();
        this.oefeningController = new OefeningController();
        this.groepsBewerkingController = new GroepsBewerkingController();
        this.sessieController = new SessieController();
    }

    public BoxController getBoxController() {
        return boxController;
    }

    public OefeningController getOefeningController() {
        return oefeningController;
    }

    public GroepsBewerkingController getGroepsBewerkingController() {
        return groepsBewerkingController;
    }
    
    public SessieController getSessieController(){
        return sessieController;
    }


}

