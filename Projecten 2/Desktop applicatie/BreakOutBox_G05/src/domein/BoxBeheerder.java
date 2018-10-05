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
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Alert;
import repository.PersistentieController;

/**
 *
 * @author Vital Verleyen
 */
public class BoxBeheerder
{

    private PersistentieController persistentie;
    private List<Box> boxes;
    private ObservableList<String> boxNamen;
    private ObservableList<String> OefeningNamen;
    private ObservableList<String> acties;
    private ObservableList<String> toegansCodes;
    private ObservableList<String> opgeslagenActies;
    private FilteredList<String> filteredBoxList;
    private Map<String, Box> mapBox;
    private List<Box> boxenList;

    public BoxBeheerder()
    {
        persistentie = new PersistentieController();
        boxes = new ArrayList<>();
        mapBox = new HashMap<>();
        List<Box> tijdelijkeLijstmetBox = new ArrayList<>(persistentie.findAllBoxes());
        tijdelijkeLijstmetBox.stream().forEach(o -> mapBox.put(o.getNaam(), o));
        setBoxes(persistentie.findAllBoxes());

        boxNamen = FXCollections.observableArrayList(getBoxNamen());
        filteredBoxList = new FilteredList<>(boxNamen, s -> true);
        opgeslagenActies = FXCollections.observableArrayList(persistentie.findAllOpgeslagenActies().stream().map(Actie::toString).collect(Collectors.toList()));
        acties = FXCollections.observableArrayList(persistentie.findAllOpgeslagenActies().stream().map(Actie::toString).collect(Collectors.toList()));
    }

    public String geefBoxInfoOpNaam(String naam)
    {
        return mapBox.get(naam).toString();

    }

    private List<String> getBoxNamen()
    {
        return new ArrayList<String>(mapBox.keySet());
        // for(String naam : mapBox.keySet()){
        //   boxNamen.add(naam);
        //}
        //return boxNamen;

    }

    public void setActiesOpBox(String box)
    {
        acties = FXCollections.observableArrayList(getActiesOpBox(box));
    }

    public ObservableList<String> geefActiesObservable()
    {
        return FXCollections.unmodifiableObservableList(acties);
    }

    private List<String> getActiesOpBox(String box)
    {
        List<String> namen = new ArrayList<>();
        for (Actie actie : mapBox.get(box).getActies())
        {
            namen.add(actie.toString());
        }
        return namen;
    }

    public void setToegansCodesOpBox(String box)
    {
        toegansCodes = FXCollections.observableArrayList(getToegansCodesOpBox(box));
    }

    public ObservableList<String> geefToegansCodesObservable()
    {
        return FXCollections.unmodifiableObservableList(toegansCodes);
    }

    private List<String> getToegansCodesOpBox(String box)
    {
        List<String> namen = new ArrayList<>();
        for (String naam : mapBox.get(box).getToegangsCodes())
        {
            namen.add(naam);
        }
        return namen;
    }

    public void setOefeningenBox(String box)
    {
        OefeningNamen = FXCollections.observableArrayList(getOefeningenOpBox(box));
    }

    public ObservableList<String> geefOefeningenObservable()
    {
        return FXCollections.unmodifiableObservableList(OefeningNamen);
    }

    private List<String> getOefeningenOpBox(String box)
    {
        List<String> namen = new ArrayList<>();
        for (Oefening oefening : mapBox.get(box).getOefeningen())
        {
            namen.add(oefening.getNaam());
        }
        return namen;
    }

    public List<Box> geefBoxes()
    {
        Set<String> boxNamen = mapBox.keySet();
        return boxNamen.stream().map(o -> mapBox.get(o)).collect(Collectors.toList());
    }

    public ObservableList<String> geefAlleBoxNamenObservable()
    {
        // return FXCollections.unmodifiableObservableList(boxNamen);
        return filteredBoxList;
    }

    public void addBox(String naam, List<String> oefeningenNamen, List<String> acties, String omschrijving)
    {

        List<Oefening> temporarelyOefList = new ArrayList<>();
        persistentie.findAllOefeningen().stream().forEach(o ->
        {

            //tijdelijkeGroepsbewList.addAll(o.getGroepsBewerkingen());
            if (oefeningenNamen.stream().anyMatch(oefn -> oefn.equals(o.getNaam())))
            {
                temporarelyOefList.add(o);
            }
        });
        Random random = new Random();
        ArrayList<String> toegangsCodes = new ArrayList<String>();
        for (int i = 0; i < acties.size(); i++)
        {
            int rand = random.nextInt(8999) + 1000;
            toegangsCodes.add(Integer.toString(rand));
        }

        List<Actie> actiesObj = acties.stream().map(o -> new Actie(o)).collect(Collectors.toList());

        Box box = new Box(naam, omschrijving, toegangsCodes, temporarelyOefList, actiesObj);
        mapBox.put(naam, box);
        persistentie.voegBoxToe(box);
        boxNamen.add(naam);
    }

    public List<Box> getBoxes()
    {
        boxenList = persistentie.findAllBoxes();
        return boxenList;
    }

    private void setBoxes(List<Box> box)
    {
        boxenList = box;
    }

    public void delBox(String naamBox)
    {
//        persistentie.removeBox(mapBox.get(naamBox));
//        mapBox.remove(naamBox);
//        boxNamen.remove(naamBox);

        Optional<Box> thisBox = boxenList.stream().filter(g -> g.getNaam().equals(naamBox)).findFirst();
        try
        {
            persistentie.removeBox(mapBox.get(naamBox));
            mapBox.remove(naamBox);
            boxNamen.remove(naamBox);
        } catch (Exception e)
        {

            List<Sessie> tijdelijkeSessieLijst = new ArrayList<>();
            List<Box> tijdelijkeBoxLijst = new ArrayList<>();
            persistentie.findAllSessies().stream().forEach(s ->
            {
                tijdelijkeBoxLijst.add(s.getBox());
                if (tijdelijkeBoxLijst.stream().anyMatch(o -> o.getNaam().equals(naamBox)))
                {
                    tijdelijkeSessieLijst.add(s);
                }
            });

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Opgepast");
            alert.setHeaderText("Deze oefening hangt vast aan een sessie!");
            alert.setContentText("Verwijder eerst de sessie met naam: " + tijdelijkeSessieLijst.get(0).getNaam()
                    + "\n en daarna deze box");

            alert.showAndWait();
        }
    }

    public void setPersistentieController(PersistentieController persistentieController)
    {
        this.persistentie = persistentieController;
    }

    public ObservableList<String> geefAlleOpgeslagenActies()
    {
        return FXCollections.unmodifiableObservableList(opgeslagenActies);
    }

    public void slaActieOp(String actie)
    {
        try
        {
            Actie actieObj = new Actie(actie);
            persistentie.voegActieToe(actieObj);
            opgeslagenActies.add(actie);
        } catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Opgepast");
            alert.setHeaderText("Deze actie bestaat al!");
            alert.setContentText("Er bestaat al een actie met deze naam");

            alert.showAndWait();
        }

    }

    public void verwijderActie(String actie)
    {
        try
        {
            Actie actieObj = new Actie(actie);
            persistentie.removeActie(actieObj);
            opgeslagenActies.remove(actie);
            acties.remove(actie);
        } catch (Exception e)
        {
            List<Box> tijdelijkeBoxen = new ArrayList<>();
            List<Actie> tijdelijkeActies = new ArrayList<>();
            persistentie.findAllBoxes().stream().forEach(o ->
            {
                tijdelijkeActies.addAll(o.getActies());
                if (tijdelijkeActies.stream().anyMatch(g -> g.getActie().equals(actie)))
                {
                    tijdelijkeBoxen.add(o);
                }
            });
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Opgepast");
            alert.setHeaderText("Deze actie hangt vast aan een box!");
            alert.setContentText("Verwijder eerst de Box met naam:" + tijdelijkeBoxen.get(0).getNaam()
                    + "\n en daarna deze actie");

            alert.showAndWait();
        }

    }

    public void changeFilter(String filterValue)
    {
        filteredBoxList.setPredicate(box
                ->
        {
            if (filterValue == null || filterValue.isEmpty())
            {
                return true;
            }

            String lowerCaseValue = filterValue.toLowerCase();
            Box boxobj = mapBox.get(box);
            return boxobj.getNaam().toLowerCase().contains(lowerCaseValue)
                    || boxobj.getNaam().toLowerCase().contains(lowerCaseValue);

        });

    }

    void wijzigBox(String naam, String nieuweOmschrijving)
    {
        Box nieuweBox = mapBox.get(naam);
        nieuweBox.setOmschrijving(nieuweOmschrijving);

        persistentie.updateBox(nieuweBox);
    }
}
