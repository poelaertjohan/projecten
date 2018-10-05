/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import repository.PersistentieController;

/**
 *
 * @author Johan
 */
public class GroepsBewerkingBeheerder {

    private PersistentieController persistentie;
    private List<GroepsBewerking> groepsBewerkingenList;
    private ObservableList<String> observableNamenGroepsBewerking;

    public GroepsBewerkingBeheerder() {
        persistentie = new PersistentieController();
        groepsBewerkingenList = new ArrayList<>();
        observableNamenGroepsBewerking = FXCollections.observableArrayList(getGroepsBewerkingen().stream().map(g -> g.getOpgave()).collect(Collectors.toList()));
    }

    public ObservableList<String> getObservableNamenGroepsBewerking() {

        observableNamenGroepsBewerking = FXCollections.observableArrayList(getGroepsBewerkingen().stream().map(g -> g.getOpgave()).collect(Collectors.toList()));
        return FXCollections.unmodifiableObservableList(observableNamenGroepsBewerking);
    }

    public List<GroepsBewerking> getGroepsBewerkingen() {
//        GroepsBewerkingen = persistentie.findAllGroepsBewerkingen().stream().map(e -> e.getOpgave()).collect(Collectors.toList());
        groepsBewerkingenList = persistentie.findAllGroepsBewerkingen();
        return groepsBewerkingenList;
    }

    public void addGroepsBewerking(String opgave, String operator, String getal) {
        GroepsBewerking gr = new GroepsBewerking(opgave, operator, getal);
        groepsBewerkingenList.add(gr);
        observableNamenGroepsBewerking.add(gr.getOpgave());
        persistentie.voegGroepsBewerkingToe(gr);
    }

    public void delGroepsBewerking(String opgave) {
        Optional<GroepsBewerking> thisGroepsBewerking = groepsBewerkingenList.stream().filter(g -> g.getOpgave().equals(opgave)).findFirst();
        try {
            persistentie.removeGroepsBewerking(thisGroepsBewerking.get());
            groepsBewerkingenList.remove(thisGroepsBewerking.get());
            observableNamenGroepsBewerking.remove(opgave);
        } catch (Exception e) {

            List<Oefening> tijdelijkeOefList = new ArrayList<>();
            List<GroepsBewerking> tijdelijkeGroepsbewList = new ArrayList<>();
            persistentie.findAllOefeningen().stream().forEach(o -> {
                tijdelijkeGroepsbewList.addAll(o.getGroepsBewerkingen());
                if (tijdelijkeGroepsbewList.stream().anyMatch(g -> g.getOpgave().equals(opgave))) {
                    tijdelijkeOefList.add(o);
                }
            });

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Opgepast");
            alert.setHeaderText("Deze groepsbewerking hangt vast aan een oefening!");
            alert.setContentText("Verwijder eerst de oefening met naam:" + tijdelijkeOefList.get(0).getNaam()
                    + "\n en met opgave: " + tijdelijkeOefList.get(0).getOpgave()
                    + "\n en daarna deze groepsbewerking");

            alert.showAndWait();
        }

    }

    public String geefGroepsBewerkingStringOpNaam(String naam) {
        return groepsBewerkingenList.stream().filter(o -> o.getOpgave().equals(naam)).findFirst().orElse(null).toString();
    }

    public void wijzigGroepsBewerking(String opgave, String operator, String getal) {
        GroepsBewerking nieuw = new GroepsBewerking(opgave, operator, getal);

        persistentie.updateGroepsBewerking(nieuw);
    }

    public void setPersistentieController(PersistentieController persistentieController) {
        this.persistentie = persistentieController;
    }

}
