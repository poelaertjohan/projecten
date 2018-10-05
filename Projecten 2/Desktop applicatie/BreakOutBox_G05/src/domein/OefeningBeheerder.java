/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import repository.PersistentieController;

/**
 *
 * @author Vital Verleyen
 */
public class OefeningBeheerder
{

    private PersistentieController persistentie;
    private List<Oefening> oefeningen;
    //  private List<String> infoOefeningen;
    private ObservableList<String> oefeningNamen;
    private ObservableList<String> groepsBewerkingen;
    private FilteredList<String> filteredOefeningen;
    private ObservableList<String> opgeslagenDoelstellingen;
    private Map<String, Oefening> mapOef;
    private List<Oefening> oefeningenList;

    public OefeningBeheerder()
    {

        oefeningen = new ArrayList<>();
        persistentie = new PersistentieController();

//map maken en alle oef er in steken keyset is naam
        mapOef = new HashMap<>();//persistentie alle oef 
        List<Oefening> tijdelijkelijstmetOef = new ArrayList<>(persistentie.findAllOefeningen());
        tijdelijkelijstmetOef.stream().forEach(o -> mapOef.put(o.getNaam(), o));
        oefeningNamen = FXCollections.observableArrayList(getOefeningNamen());
        filteredOefeningen = new FilteredList<>(oefeningNamen, s -> true);
//        oefeningenList = new ArrayList<>();
        setOefeningen(persistentie.findAllOefeningen());
        opgeslagenDoelstellingen = FXCollections.observableArrayList(persistentie.findAllOpgeslagenDoelstellingen().stream().map(Doelstelling::toString).collect(Collectors.toList()));

    }

    private List<String> getOefeningNamen()
    {
        //return infoOefeningen.stream().map(e -> e.split(";")[0]).collect(Collectors.toList());
        return new ArrayList<String>(mapOef.keySet());
        //for (String naam : mapOef.keySet()) {
        //   oefeningNamen.add(naam);
        //}
        // return oefeningNamen;
    }

    public void setGroepsBewerkingOpOefening(String oefening)
    {
        groepsBewerkingen = FXCollections.observableArrayList(getGroepsBewerkingenOpOefening(oefening));
    }

    public ObservableList<String> geefGroepsBewerkingenObservable()
    {
        return FXCollections.unmodifiableObservableList(groepsBewerkingen);
    }

    private List<String> getGroepsBewerkingenOpOefening(String oefening)
    {

        List<String> namen = new ArrayList<>();

//        namen = mapOef.get(oefening).getGroepsBewerkingen().stream().map(GroepsBewerking::toString).collect(Collectors.toList());
        for (GroepsBewerking naam : mapOef.get(oefening).getGroepsBewerkingen())
        {
            namen.add(naam.getOpgave());
        }
        return namen;
    }

    public List<Oefening> geefOefeningen()
    {
        Set<String> oefeningNamen = mapOef.keySet();
        return oefeningNamen.stream().map(o -> mapOef.get(o)).collect(Collectors.toList());
    }

    public String giveOefNameReturnOefObjectInString(String naam)
    {
        String test;
        test = mapOef.get(naam).toString();
        return test;
    }

    public void wijzigOefening(String origineleNaam, String nieuweNaam, String nieuweOpdracht, String nieuweFeedback, String nieuwAntwoord, String vak, String doelstelling, int timeleft)
    {
        Oefening teWijzigenOefening = mapOef.get(origineleNaam);

        Doelstelling doelstellingObj = new Doelstelling(doelstelling);

        Oefening nieuweOefening = new Oefening(origineleNaam, nieuweOpdracht, nieuweFeedback, nieuwAntwoord, teWijzigenOefening.getGroepsBewerkingen(), teWijzigenOefening.getPadNaarPdf(), vak, doelstellingObj, timeleft);
        persistentie.updateOefening(nieuweOefening);
    }

    public ObservableList<String> geefAlleOefeningenNamenObservable()
    {
        //return FXCollections.unmodifiableObservableList(oefeningen);
        return filteredOefeningen;
    }

    public Oefening geefOefeningOpOefeningNaam(String naam)
    {
        return mapOef.get(naam);
    }

    public void addListener(ListChangeListener<String> listener)
    {
        oefeningNamen.addListener(listener);
    }

    public void addOef(String naam, String opgave, String feedback, String antwoord, List<String> groepsBewerkingen, String padNaarPdf, String vak, String doelstelling, int timeLeft)
    {
        Doelstelling doelstellingObj = new Doelstelling(doelstelling);
        Oefening oef = new Oefening(naam, opgave, feedback, antwoord, persistentie.getGroepsbewerkingenDoorOpgave(groepsBewerkingen), padNaarPdf, vak, doelstellingObj, timeLeft);
        mapOef.put(naam, oef);
        persistentie.voegOefeningToe(oef);
        oefeningenList.add(oef);
        oefeningNamen.add(naam);
    }

    public List<Oefening> getOefeningen()
    {
        oefeningenList = persistentie.findAllOefeningen();
        return oefeningenList;
    }

    private void setOefeningen(List<Oefening> oefeningen)
    {
        oefeningenList = oefeningen;
    }

    public void delOef(String naamOef)
    {
//    Oefening removeSelectedOef = mapOef.get(naamOef);

        Optional<Oefening> thisOefening = oefeningenList.stream().filter(g -> g.getNaam().equals(naamOef)).findFirst();
        try
        {
            persistentie.removeOef(thisOefening.get());
            oefeningenList.remove(thisOefening.get());
            mapOef.remove(naamOef);
            oefeningNamen.remove(naamOef);
        } catch (Exception e)
        {

            List<Box> tijdelijkeBoxList = new ArrayList<>();
            List<Oefening> tijdelijkeOefLijst = new ArrayList<>();
            persistentie.findAllBoxes().stream().forEach(b ->
            {
                tijdelijkeOefLijst.addAll(b.getOefeningen());
                if (tijdelijkeOefLijst.stream().anyMatch(o -> o.getNaam().equals(naamOef)))
                {
                    tijdelijkeBoxList.add(b);
                }
            });

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Opgepast");
            alert.setHeaderText("Deze oefening hangt vast aan een box!");
            alert.setContentText("Verwijder eerst de box met naam: " + tijdelijkeBoxList.get(0).getNaam()
                    + "\n en daarna deze oefening");

            alert.showAndWait();
        }
    }

    public boolean geenOefeningen()
    {
        return mapOef.isEmpty();
    }

    public void changeFilter(String filterValue, String keuze)
    {
        filteredOefeningen.setPredicate(oef
                ->
        {
            if (filterValue == null || filterValue.isEmpty())
            {
                return true;
            }

            String lowerCaseValue = filterValue.toLowerCase();
            Oefening oefening = mapOef.get(oef);
            if (keuze == "Doelstelling")
            {
                return oefening.getDoelstelling().toString().toLowerCase().contains(lowerCaseValue)
                        || oefening.getDoelstelling().toString().toLowerCase().contains(lowerCaseValue);
            } else if (keuze == "Vak")
            {
                return oefening.getVak().toLowerCase().contains(lowerCaseValue)
                        || oefening.getVak().toLowerCase().contains(lowerCaseValue);
            }
            return oefening.getNaam().toLowerCase().contains(lowerCaseValue)
                    || oefening.getNaam().toLowerCase().contains(lowerCaseValue);

        });
    }

    public void setPersistentieController(PersistentieController persistentieController)
    {
        this.persistentie = persistentieController;
    }

    public void verwijderDoelstelling(String doelstelling)
    {

//        Doelstelling doelstellingObj = new Doelstelling(doelstelling);
//        persistentie.removeDoelstelling(doelstellingObj);
//        opgeslagenDoelstellingen.remove(doelstelling);
        List<Oefening> tijdelijkeOefList = new ArrayList<>();
        List<Doelstelling> tijdelijkedoelstellinglijst = new ArrayList<>();
        persistentie.findAllOefeningen().stream().forEach(o ->
        {
            tijdelijkedoelstellinglijst.add(o.getDoelstelling());
            if (tijdelijkedoelstellinglijst.stream().anyMatch(g -> g.getDoelstelling().equals(doelstelling)))
            {
                tijdelijkeOefList.add(o);
            }
        });

        if (tijdelijkeOefList.isEmpty())
        {
            Doelstelling doelstellingObj = new Doelstelling(doelstelling);
            persistentie.removeDoelstelling(doelstellingObj);
            opgeslagenDoelstellingen.remove(doelstelling);
        } else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Opgepast");
            alert.setHeaderText("Deze doelstelling hangt vast aan een oefening!");
            alert.setContentText("Verwijder eerst de oefening met naam: " + tijdelijkeOefList.get(0).getNaam());

            alert.showAndWait();
        }

    }

    public ObservableList<String> geefAlleOpgeslagenDoelstellingen()
    {
        return FXCollections.unmodifiableObservableList(opgeslagenDoelstellingen);
    }

    public void slaDoelstellingOp(String doelstelling)
    {
        Doelstelling doelstellingObj = new Doelstelling(doelstelling);
        persistentie.voegDoelstellingToe(doelstellingObj);
        opgeslagenDoelstellingen.add(doelstelling);
    }
}
