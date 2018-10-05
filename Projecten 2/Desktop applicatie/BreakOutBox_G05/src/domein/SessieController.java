package domein;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import javafx.collections.ObservableList;
import repository.PersistentieController;

//Code Written by Fréderic Terryn. All rights reserved. 
public class SessieController {

    private SessieBeheerder sessieBeheerder;

    public SessieController() {
        this.sessieBeheerder = new SessieBeheerder();
    }

    public String geefSessieOpNaam(String sessieNaam) {
        return sessieBeheerder.geefSessieOpSessieNaam(sessieNaam);
    }

    public ObservableList<String> geefSessieObservable() {
        return sessieBeheerder.geefSessieObservable();
    }

    public void addSessie(boolean afstandsOnderwijs, String naam, String box, String omschrijving, LocalDate beschikbaarVanaf, List<String> groepen) {
        if (!naam.isEmpty() && naam!=null) {
            sessieBeheerder.addSessie(afstandsOnderwijs, naam, box, omschrijving, beschikbaarVanaf, groepen);
        } else {
            System.out.println("controller geeft foutmelding");
            throw new IllegalArgumentException("Mag niet leeg zijn");            
        }
    }

    public void delSessie(String naam) {
        sessieBeheerder.delSessie(naam);
    }

    public void setPersistentieController(PersistentieController persistentieController) {
        sessieBeheerder.setPersistentieController(persistentieController);
    }

    public ObservableList<String> geefGroepenOpservableOpSessieNaam(String naamGekozenSessie) {
        return sessieBeheerder.geefGroepenOpservableOpSessieNaam(naamGekozenSessie);
    }

//    public List<String> geefGroepNummerEnNamenLedenOpSessieNaam(String naamGekozenSessie)
//    {
//        return sessieBeheerder.geefGroepNummerEnNamenLedenOpSessieNaam(naamGekozenSessie);
//    }
    public void wijzigSessie(String naam, String nieuweOmschrijving, boolean isNieuweAfstandsonderwijs) {
        sessieBeheerder.wijzigSessie(naam, nieuweOmschrijving, isNieuweAfstandsonderwijs);
    }

    public List<String> geefDetailsOpdrachtenOpSessieNaam(String sessieNaam) {
        return sessieBeheerder.geefDetailsOpdrachtenOpSessieNaam(sessieNaam);
    }

//    public Iterable<String> geefGroepNummerEnNamenLedenOpSessieNaam(String sessieNaam)
//    {
//        return sessieBeheerder.geefGroepNummerEnNamenLedenOpSessieNaam(sessieNaam);
//    }
    public List<String> geefActiesVanBoxVanSessie(String sessieNaam) {
        return sessieBeheerder.geefActiesVanBoxVanSessie(sessieNaam);
    }

    public void changeFilter(String filterValue) {
        sessieBeheerder.changeFilter(filterValue);
    }
    
    public String getCodeDoorNaam(String naam)
    {
        return sessieBeheerder.getCodeDoorNaam(naam);
    }

}
