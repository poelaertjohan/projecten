/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import repository.PersistentieController;

/**
 *
 * @author JorisLaptop
 */
public class OefeningController
{

//    private final OefeningRepository oefeningenRepository;
//    private ObservableList<String> oefeningen;
    private OefeningBeheerder oefeningBeheerder;
    private GroepsBewerkingBeheerder groepsBewerkingBeheerder;

    public OefeningController()
    {

        this.oefeningBeheerder = new OefeningBeheerder();
        groepsBewerkingBeheerder = new GroepsBewerkingBeheerder();
    }

//    public ObservableList<String> geefGroepsBewerkingenOpOefeningNaam(String naam){
//        return oefeningBeheerder.geefGroepsBewerkingenOpOefeningNaam(naam);
//    }
    public List<Oefening> geefOefeningen()
    {
        return oefeningBeheerder.geefOefeningen();
    }

    public ObservableList<String> geefAlleOefeningenNamenObservable()
    {
        //return FXCollections.unmodifiableObservableList(oefeningen);
        return oefeningBeheerder.geefAlleOefeningenNamenObservable();
    }

    public ObservableList<String> geefGroepsBewerkingenObservableOpOefeningNaam(String oefening)
    {
        oefeningBeheerder.setGroepsBewerkingOpOefening(oefening);
        return oefeningBeheerder.geefGroepsBewerkingenObservable();
    }

    public String giveOefNameReturnOefObjectInString(String naam)
    {
        return oefeningBeheerder.giveOefNameReturnOefObjectInString(naam).toString();
    }

    public void addListener(ListChangeListener<String> listener)
    {
        oefeningBeheerder.addListener(listener);
    }

    public void addOefening(String naam, String opgave, String feedback, String antwoord, List<String> namenGroepsBewerkingen, String padNaarPdf, String vak, String doelstelling, int timeLeft)
    {
        oefeningBeheerder.addOef(naam, opgave, feedback, antwoord, namenGroepsBewerkingen, padNaarPdf, vak, doelstelling, timeLeft);

    }

    public void delOef(String teVerwijderenOef)
    {
        oefeningBeheerder.delOef(teVerwijderenOef);
    }

    public boolean geenOefeningen()
    {
        return oefeningBeheerder.geenOefeningen();
    }

    public void changeFilter(String filterValue, String keuze)
    {
        oefeningBeheerder.changeFilter(filterValue, keuze);
    }

    public ObservableList<String> getObservableNamenGroepsBewerking()
    {
        return groepsBewerkingBeheerder.getObservableNamenGroepsBewerking();
    }

    public void wijzigOefening(String origineleNaam, String nieuweNaam, String nieuweOpdracht, String nieuweFeedback, String nieuwAntwoord, String vak, String doelstelling, int timeLeft)
    {
        oefeningBeheerder.wijzigOefening(origineleNaam, nieuweNaam, nieuweOpdracht, nieuweFeedback, nieuwAntwoord, vak, doelstelling, timeLeft);
    }

    public void addGroepsBewerking(String opgave, String operator, String getal)
    {
        groepsBewerkingBeheerder.addGroepsBewerking(opgave, operator, getal);
    }

    public void setPersistentieController(PersistentieController persistentieController)
    {
        oefeningBeheerder.setPersistentieController(persistentieController);
    }

    public ObservableList<String> geefAlleOpgeslagenDoelstellingen()
    {
        return oefeningBeheerder.geefAlleOpgeslagenDoelstellingen();
    }

    public void slaDoelstellingOp(String doelstelling)
    {
        oefeningBeheerder.slaDoelstellingOp(doelstelling);
    }

    public void verwijderDoelstelling(String doelstelling)
    {
        oefeningBeheerder.verwijderDoelstelling(doelstelling);
    }

}
