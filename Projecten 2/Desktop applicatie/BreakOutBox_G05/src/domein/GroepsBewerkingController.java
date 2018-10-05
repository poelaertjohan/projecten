/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;
import javafx.collections.ObservableList;
import repository.PersistentieController;

/**
 *
 * @author JorisLaptop
 */
public class GroepsBewerkingController {
    
    private GroepsBewerkingBeheerder groepsBewerkingBeheerder;
    
    public GroepsBewerkingController() {
        
        this.groepsBewerkingBeheerder = new GroepsBewerkingBeheerder();
    }
    
    public List<GroepsBewerking> getGroepsBewerkingen() {
        return groepsBewerkingBeheerder.getGroepsBewerkingen();
    }
    
    public ObservableList<String> getObservableNamenGroepsBewerking()
    {
        return groepsBewerkingBeheerder.getObservableNamenGroepsBewerking();
    }
    
    public void addGroepsBewerking(String opgave, String operator, String getal) {
        groepsBewerkingBeheerder.addGroepsBewerking(opgave, operator, getal);
    }
    
    public void delGroepsBewerking(String opgave) {
        groepsBewerkingBeheerder.delGroepsBewerking(opgave);
    }
    
    public String geefGroepsBewerkingStringOpNaam(String naam){
        return groepsBewerkingBeheerder.geefGroepsBewerkingStringOpNaam(naam);
    }
    
    public void wijzigGroepsBewerking(String opgave, String operator, String getal)
    {
        groepsBewerkingBeheerder.wijzigGroepsBewerking(opgave, operator, getal);
    }
     public void setPersistentieController(PersistentieController persistentieController){
        groepsBewerkingBeheerder.setPersistentieController(persistentieController);
    }
}
