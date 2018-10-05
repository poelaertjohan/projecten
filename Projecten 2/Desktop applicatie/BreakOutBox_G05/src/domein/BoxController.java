/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import repository.PersistentieController;
//import javafx.collectionObservableList;

/**
 *
 * @author Vital Verleyen
 */
public class BoxController {

    private List<String> selectedOef;
    private BoxBeheerder boxBeheerder;
    // private OefeningBeheerder oefeningBeheerder;

    public BoxController() {
        this.boxBeheerder = new BoxBeheerder();
        selectedOef = new ArrayList<>();
//        this.oefeningBeheerder = new OefeningBeheerder();
    }

    public List<Box> geefBoxes() {
        return boxBeheerder.geefBoxes();
    }

    public ObservableList<String> geefAlleBoxesNamenObservable() {
        return boxBeheerder.geefAlleBoxNamenObservable();
    }

    public ObservableList<String> geefActiesObservableOpBoxNaam(String box) {
        boxBeheerder.setActiesOpBox(box);
        return boxBeheerder.geefActiesObservable();
    }

    public ObservableList<String> geefToegangsCodesObservableOpBoxNaam(String box) {
        boxBeheerder.setToegansCodesOpBox(box);
        return boxBeheerder.geefToegansCodesObservable();
    }

    public ObservableList<String> geefOefeningenObservableOpBoxNaam(String box) { //deze methode niet gebruiken, gebruik getAllOefForThisBox
        boxBeheerder.setOefeningenBox(box);
        return boxBeheerder.geefOefeningenObservable();
    }

    public void addBox(String naam, List<String> oefeningenNamen, List<String> acties, String omschrijving) {
//        List<Oefening> oefeningen = oefeningenNamen.stream().map(oefeningBeheerder::geefOefeningOpOefeningNaam).collect(Collectors.toList());
        boxBeheerder.addBox(naam, oefeningenNamen, acties, omschrijving);
    }

    public void delBox(String teVerwijderenBox) {
        boxBeheerder.delBox(teVerwijderenBox);
    }

    public String geefBoxOpNaam(String naam) {
        return boxBeheerder.geefBoxInfoOpNaam(naam);
    }

    public void setOefeningen(List<String> gekozenOefeningen) {
        gekozenOefeningen.stream().forEach(g -> selectedOef.add(g));
    }

    public List<String> getSelectedOef() {
        return selectedOef;
    }

    public void setPersistentieController(PersistentieController persistentieController) {
        boxBeheerder.setPersistentieController(persistentieController);
    }

    public ObservableList<String> geefAlleOpgeslagenActies() {
        return boxBeheerder.geefAlleOpgeslagenActies();
    }

    public void slaActieOp(String actie) {
        boxBeheerder.slaActieOp(actie);
    }

    public void verwijderActie(String actie) {
        boxBeheerder.verwijderActie(actie);
    }

    public void changeFilter(String filterValue) {
        boxBeheerder.changeFilter(filterValue);
    }

    public void wijzigBox(String naam, String nieuweOmschrijving)
    {
        boxBeheerder.wijzigBox(naam, nieuweOmschrijving);
    }
}
