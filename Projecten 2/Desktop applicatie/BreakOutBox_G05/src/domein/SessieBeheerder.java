package domein;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javax.persistence.TypedQuery;
import repository.PersistentieController;

//Code Written by Fréderic Terryn. All rights reserved.
public class SessieBeheerder {

    private ObservableList<String> sessieObservable;
    private Box box;
    private String omschrijving;
    private ObservableList<String> groepObservable;
    private PersistentieController persistentie;
    private FilteredList<String> filteredSessieList;

    private Map<String, Sessie> mapSessie;

    public SessieBeheerder() {
        persistentie = new PersistentieController();
        mapSessie = new HashMap<>();
        //sessieObservable = FXCollections.observableArrayList(items);
        List<Sessie> tijdelijkeLijstMetSessie = new ArrayList<>(persistentie.findAllSessies());
        tijdelijkeLijstMetSessie.stream().forEach(o -> mapSessie.put(o.getNaam(), o));

        sessieObservable = FXCollections.observableArrayList(getSessieNamen());
        filteredSessieList = new FilteredList<>(sessieObservable, s -> true);

    }

    private List<String> getSessieNamen() {
        return new ArrayList<String>(mapSessie.keySet());
    }

    public ObservableList<String> geefSessieObservable() {
        //return FXCollections.unmodifiableObservableList(sessieObservable);
        return filteredSessieList;
    }

    public String geefSessieOpSessieNaam(String naam) {
        return mapSessie.get(naam).toString();
    }

    public void addSessie(boolean afstandsOnderwijs, String naam, String box, String omschrijving, LocalDate beschikbaarVanaf, List<String> groepen) {
        Box gevondenBox = persistentie.findBox(box);
        List<Groep> groepenLijst = maakGroepen(groepen);
        List<Oefening> oefeningenVanBox = gevondenBox.getOefeningen();
        geefGroepenOefeningen(oefeningenVanBox, groepenLijst, naam);

        //sessie omzetten naar String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd LLLL yyyy");
        String datumInString = beschikbaarVanaf.format(formatter);

        Sessie sessie = new Sessie(afstandsOnderwijs, naam, gevondenBox, omschrijving, datumInString, groepenLijst);
        mapSessie.put(naam, sessie);

        persistentie.voegSessieToe(sessie);
        groepenLijst.stream().forEach(g -> persistentie.voegGroepToe(g));
        sessieObservable.add(naam);
    }

    private void geefGroepenOefeningen(List<Oefening> oefeningen, List<Groep> groepen, String naam) {
        
        java.util.Random ran = new java.util.Random();
        for (Groep g : groepen) {
            //dit moet omdat anders elke shuffle hetzelfde is
            List<Oefening> oefeningShuffled = new ArrayList<>(oefeningen);
            Collections.shuffle(oefeningShuffled);
            g.setOefeningen(oefeningShuffled);

            oefeningShuffled.stream().forEach(o
                    -> {
                List<GroepsBewerking> groepsbewerkingListPerOef = new ArrayList<>(o.getGroepsBewerkingen());
                Collections.shuffle(groepsbewerkingListPerOef);

                int actieCode = 1000 + ran.nextInt(8999);
                
                //doorgeven naar merge klasse
                MergeAll merge = new MergeAll(groepsbewerkingListPerOef.get(0), o, g, naam, actieCode);
                persistentie.insertMerge(merge);
            });
        }
    }

    private List<Groep> maakGroepen(List<String> groepen) {
        List<Groep> lijstGroepen = new ArrayList<>();
        String groep;
        boolean isNieuweGroep;

        for (String s : groepen) {
            isNieuweGroep = true;
            groep = s.substring(0, 1);
            for (Groep g : lijstGroepen) {
                if (g.getGroepNummer().equalsIgnoreCase(groep) && !(s.substring(s.indexOf(";") + 1).equalsIgnoreCase("null"))) {
                    g.addLeerling(s.substring(s.indexOf(";") + 1));
                    isNieuweGroep = false;
                    break;
                }
            }

            if (isNieuweGroep) {
                Groep gr = new Groep(s.substring(0, 1));
                gr.addLeerling(s.substring(s.indexOf(";") + 1));
                lijstGroepen.add(gr);
            }
        }

        return lijstGroepen;
    }

    public void delSessie(String naam) {
        persistentie.removeSessie(mapSessie.get(naam));
        deleteMerges(naam);
      persistentie.removeGroepen();
        mapSessie.remove(naam);
        sessieObservable.remove(naam);
    }

    public void setPersistentieController(PersistentieController persistentieController) {
        this.persistentie = persistentieController;
    }

    private void deleteMerges(String naam) {
        List<MergeAll> merges = persistentie.getMergeBySessieNaam(naam);

        for (MergeAll m : merges) {
            persistentie.removeMerge(m);
        }
    }

    public ObservableList<String> geefGroepenOpservableOpSessieNaam(String naamGekozenSessie) {
        List<String> groepen = new ArrayList<>();
        Sessie s = mapSessie.get(naamGekozenSessie);
        int groepnr = 1;

        for (Groep g : s.getGroepen()) {
            groepen.add("Groep " + groepnr + ": " + g.getLeerlingen());
            groepnr++;
        }

        return FXCollections.observableArrayList(groepen);
    }

    public void wijzigSessie(String naam, String nieuweOmschrijving, boolean isNieuweAfstandsonderwijs) {
        Sessie s = mapSessie.get(naam);
        s.setOmschrijving(nieuweOmschrijving);
        s.setAfstandsOnderwijs(isNieuweAfstandsonderwijs);

        persistentie.updateSessie(s);
    }

    public List<String> geefDetailsOpdrachtenOpSessieNaam(String sessieNaam) {
        List<String> details = new ArrayList<>();

        //doorgeven naar merge klasse
        Box b = mapSessie.get(sessieNaam).getBox();
        List<MergeAll> merges = new ArrayList<>(persistentie.getMergeBySessieNaam(sessieNaam));
        List<Groep> groepen = new ArrayList<>();

        merges.stream().forEach(m
                -> {
            if (!groepen.contains(m.getGroep())) {
                details.add("eindeGroep");
                groepen.add(m.getGroep());
                details.add("Groep " + m.getGroep().getGroepNummer());
                details.add("Leden: " + m.getGroep().getLeerlingen());
                details.add("Opdrachten: ");

            }
            details.add("Naam oefening: " + m.getOef().getOpgave());
            details.add("Oplossing oefening: " + m.getOef().getAntwoord());
            details.add("Groepsbewerking: " + m.getGroepsBewerking().getOpgave());
            details.add("Oplossing groepsbewerking en oefening: " + m.getGroepsBewerking().voerUitInt(m.getOef().getAntwoord()));
            details.add("Actiecode: " + m.getActiecode());
            details.add("");

            m.setSessieNaam(sessieNaam);
            persistentie.updateMerge(m);
        });

        details.add("Acties: ");

        for (Actie a : b.getActies()) {
            details.add(a.toString());
        }

        return details;
    }

    public List<String> geefActiesVanBoxVanSessie(String sessieNaam) {
//        return mapSessie.get(sessieNaam).getBox().getActies().stream().map(e -> e.toString()).collect(Collectors.toList());

        Sessie s = mapSessie.get(sessieNaam);
        Box b = s.getBox();
        List<String> acties = new ArrayList<>();

        for (Actie a : b.getActies()) {
            acties.add(a.toString());
        }

        return acties;

    }

    public void changeFilter(String filterValue) {
        filteredSessieList.setPredicate(sessie
                -> {
            if (filterValue == null || filterValue.isEmpty()) {
                return true;
            }

            String lowerCaseValue = filterValue.toLowerCase();
            Sessie sessieobj = mapSessie.get(sessie);
            return sessieobj.getNaam().toLowerCase().contains(lowerCaseValue)
                    || sessieobj.getNaam().toLowerCase().contains(lowerCaseValue);

        });

    }

    public String getCodeDoorNaam(String naam)
    {
        return mapSessie.get(naam).getCode();
    }

}
