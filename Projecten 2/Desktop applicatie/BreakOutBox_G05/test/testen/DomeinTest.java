package testen;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import domein.Actie;
import domein.Box;
import domein.BoxController;
import domein.Doelstelling;
import domein.Groep;
import domein.GroepsBewerking;
import domein.GroepsBewerkingController;
import domein.Oefening;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.mockito.Mockito;
import repository.PersistentieController;
import domein.OefeningBeheerder;
import domein.OefeningController;
import domein.Sessie;
import domein.SessieController;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import junit.framework.AssertionFailedError;
import org.junit.Assert;

/**
 *
 * @author Vital Verleyen
 */
public class DomeinTest {

    private PersistentieController persistentieController;
    private OefeningController oefCon;
    private GroepsBewerkingController groepsBewCon;
    private BoxController boxCon;
    private SessieController sessieCon;

    @Before
    public void before() {
        oefCon = new OefeningController();
        groepsBewCon = new GroepsBewerkingController();
        boxCon = new BoxController();
        sessieCon = new SessieController();

        persistentieController = Mockito.mock(PersistentieController.class);
        oefCon.setPersistentieController(persistentieController);
        groepsBewCon.setPersistentieController(persistentieController);
        boxCon.setPersistentieController(persistentieController);
        sessieCon.setPersistentieController(persistentieController);
    }

    @Test
    public void voegOefeningToe() {
        final String OEFENINGNAAM = "ZoekX";
        final String OPGAVE = "x + 2 = 5";
        final String FEEDBACK = "probeer beide kanten af te trekken met 2";
        final String ANTWOORD = "3";
        final ArrayList<GroepsBewerking> GROEPSBEWERKINGEN = new ArrayList<>();
        final String PAD = "pad";
        final String VAK = "Wiskunde";
        final Doelstelling DOELSTELLING = new Doelstelling("aanleren van sommen");
        final int TIJD = 5;
        GROEPSBEWERKINGEN.add(new GroepsBewerking("tel 2 op", "+", "2"));
        List<String> groepsBewerkingenString = GROEPSBEWERKINGEN.stream().map(GroepsBewerking::toString).collect(Collectors.toList());

        Oefening oefening = new Oefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD, GROEPSBEWERKINGEN, PAD, VAK, DOELSTELLING, TIJD);

        Mockito.when(persistentieController.findAllOefeningen()).thenReturn(Arrays.asList(oefening));

        //  Assert.assertFalse(persistentieController.findAllOefeningen().contains(oefening));
        oefCon.addOefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD, groepsBewerkingenString, PAD, VAK, DOELSTELLING.toString(), TIJD);
        Assert.assertTrue(persistentieController.findAllOefeningen().contains(oefening));
        Mockito.verify(persistentieController).findAllOefeningen();
    }

    @Test
    public void wijzigOefening() {
        final String OEFENINGNAAM = "ZoekX";
        final String OPGAVE = "x + 2 = 5";
        final String FEEDBACK = "probeer beide kanten af te trekken met 2";
        final String ANTWOORD1 = "3";
        final String ANTWOORD2 = "5";
        final ArrayList<GroepsBewerking> GROEPSBEWERKINGEN = new ArrayList<>();
        final String PAD = "pad";
        final String VAK = "Wiskunde";
        final Doelstelling DOELSTELLING = new Doelstelling("aanleren van sommen");
        final int TIJD = 5;
        GROEPSBEWERKINGEN.add(new GroepsBewerking("tel 2 op", "+", "2"));
        List<String> groepsBewerkingenString = GROEPSBEWERKINGEN.stream().map(GroepsBewerking::toString).collect(Collectors.toList());

        Oefening oefening = new Oefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD2, GROEPSBEWERKINGEN, PAD, VAK, DOELSTELLING, TIJD);

        Mockito.when(persistentieController.findAllOefeningen()).thenReturn(Arrays.asList(oefening));

        oefCon.addOefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD1, groepsBewerkingenString, PAD, VAK, DOELSTELLING.toString(), TIJD);
        oefCon.wijzigOefening(OEFENINGNAAM, OEFENINGNAAM, FEEDBACK, FEEDBACK, ANTWOORD2, VAK, FEEDBACK, TIJD);
        Assert.assertTrue(persistentieController.findAllOefeningen().contains(oefening));
        Mockito.verify(persistentieController).findAllOefeningen();
    }

    @Test
    public void voegGroepsBewerkingToe() {
        final String OPGAVE = "voeg10Toe";
        final String OPERATOR = "+";
        final String GETAL = "10";

        GroepsBewerking groepsBewerking = new GroepsBewerking(OPGAVE, OPERATOR, GETAL);

        Mockito.when(persistentieController.findAllGroepsBewerkingen()).thenReturn(Arrays.asList(groepsBewerking));

        groepsBewCon.addGroepsBewerking(OPGAVE, OPERATOR, GETAL);
        Assert.assertTrue(persistentieController.findAllGroepsBewerkingen().contains(groepsBewerking));
        Mockito.verify(persistentieController).findAllGroepsBewerkingen();

    }

    @Test
    public void wijzigGroepsBewerking() {
        final String OPGAVE = "voeg10Toe";
        final String OPERATOR = "+";
        final String GETAL1 = "10";
        final String GETAL2 = "20";

        GroepsBewerking groepsBewerking = new GroepsBewerking(OPGAVE, OPERATOR, GETAL2);

        Mockito.when(persistentieController.findAllGroepsBewerkingen()).thenReturn(Arrays.asList(groepsBewerking));

        groepsBewCon.addGroepsBewerking(OPGAVE, OPERATOR, GETAL1);
        groepsBewCon.wijzigGroepsBewerking(OPGAVE, OPERATOR, GETAL2);
        Assert.assertTrue(persistentieController.findAllGroepsBewerkingen().contains(groepsBewerking));
        Mockito.verify(persistentieController).findAllGroepsBewerkingen();

    }

    @Test
    public void voegBoxToe() {
        final String NAAM = "box";
        final String OMSCHRIJVING = "boxken";
        final ArrayList<String> TOEGANGSCODES = new ArrayList<>();
        final ArrayList<Oefening> OEFENINGEN = new ArrayList<>();
        final ArrayList<Actie> ACTIES = new ArrayList<>();
        final String OEFENINGNAAM = "ZoekX";
        final String OPGAVE = "x + 2 = 5";
        final String FEEDBACK = "probeer beide kanten af te trekken met 2";
        final String ANTWOORD = "3";
        final ArrayList<GroepsBewerking> GROEPSBEWERKINGEN = new ArrayList<>();
        final String PAD = "pad";
        final String VAK = "Wiskunde";
        final Doelstelling DOELSTELLING = new Doelstelling("aanleren van sommen");
        final int TIJD = 5;
        GROEPSBEWERKINGEN.add(new GroepsBewerking("tel 2 op", "+", "2"));
        TOEGANGSCODES.add("123");
        TOEGANGSCODES.add("45");
        ACTIES.add(new Actie("doe dit"));
        ACTIES.add(new Actie("doe dat"));

        OEFENINGEN.add(new Oefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD, GROEPSBEWERKINGEN, PAD, VAK, DOELSTELLING, TIJD));

        Box box = new Box(NAAM, OMSCHRIJVING, TOEGANGSCODES, OEFENINGEN, ACTIES);

        Mockito.when(persistentieController.findAllBoxes()).thenReturn(Arrays.asList(box));
        Mockito.when(persistentieController.findAllOefeningen()).thenReturn(OEFENINGEN);

        
        oefCon.addOefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD, GROEPSBEWERKINGEN.stream().map(GroepsBewerking::toString).collect(Collectors.toList()), PAD, VAK, DOELSTELLING.toString(), TIJD);
        boxCon.addBox(NAAM, OEFENINGEN.stream().map(Oefening::toString).collect(Collectors.toList()), ACTIES.stream().map(Actie::toString).collect(Collectors.toList()), OMSCHRIJVING);
        Assert.assertTrue(persistentieController.findAllBoxes().contains(box));
        Mockito.verify(persistentieController).findAllBoxes();
        Mockito.verify(persistentieController).findAllOefeningen();
    }

    @Test
    public void wijzigBox() {
        final String NAAM = "box";
        final String OMSCHRIJVING1 = "boxken";
        final String OMSCHRIJVING2 = "nog een boxken";
        final ArrayList<String> TOEGANGSCODES = new ArrayList<>();
        final ArrayList<Oefening> OEFENINGEN = new ArrayList<>();
        final ArrayList<Actie> ACTIES = new ArrayList<>();
        final String OEFENINGNAAM = "ZoekX";
        final String OPGAVE = "x + 2 = 5";
        final String FEEDBACK = "probeer beide kanten af te trekken met 2";
        final String ANTWOORD = "3";
        final ArrayList<GroepsBewerking> GROEPSBEWERKINGEN = new ArrayList<>();
        final String PAD = "pad";
        final String VAK = "Wiskunde";
        final Doelstelling DOELSTELLING = new Doelstelling("aanleren van sommen");
        final int TIJD = 5;
        GROEPSBEWERKINGEN.add(new GroepsBewerking("tel 2 op", "+", "2"));
        TOEGANGSCODES.add("123");
        TOEGANGSCODES.add("45");
        ACTIES.add(new Actie("doe dit"));
        ACTIES.add(new Actie("doe dat"));

        OEFENINGEN.add(new Oefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD, GROEPSBEWERKINGEN, PAD, VAK, DOELSTELLING, TIJD));

        Box box = new Box(NAAM, OMSCHRIJVING2, TOEGANGSCODES, OEFENINGEN, ACTIES);

        Mockito.when(persistentieController.findAllBoxes()).thenReturn(Arrays.asList(box));

        oefCon.addOefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD, GROEPSBEWERKINGEN.stream().map(GroepsBewerking::toString).collect(Collectors.toList()), PAD, VAK, DOELSTELLING.toString(), TIJD);
        boxCon.addBox(NAAM, OEFENINGEN.stream().map(Oefening::toString).collect(Collectors.toList()), ACTIES.stream().map(Actie::toString).collect(Collectors.toList()), OMSCHRIJVING1);
        Assert.assertTrue(persistentieController.findAllBoxes().contains(box));
        Mockito.verify(persistentieController).findAllBoxes();
    }

    @Test
    public void voegSessieToe() {
        final String BOXNAAM = "box";
        final String BOXOMSCHRIJVING = "boxken";
        final ArrayList<String> TOEGANGSCODES = new ArrayList<>();
        final ArrayList<Oefening> OEFENINGEN = new ArrayList<>();
        final ArrayList<Actie> ACTIES = new ArrayList<>();
        final String OEFENINGNAAM = "ZoekX";
        final String OPGAVE = "x + 2 = 5";
        final String FEEDBACK = "probeer beide kanten af te trekken met 2";
        final String ANTWOORD = "3";
        final ArrayList<GroepsBewerking> GROEPSBEWERKINGEN = new ArrayList<>();
        final String PAD = "pad";
        final String VAK = "Wiskunde";
        final Doelstelling DOELSTELLING = new Doelstelling("aanleren van sommen");
        final int TIJD = 5;
        GROEPSBEWERKINGEN.add(new GroepsBewerking("tel 2 op", "+", "2"));
        TOEGANGSCODES.add("123");
        TOEGANGSCODES.add("45");
        ACTIES.add(new Actie("doe dit"));
        ACTIES.add(new Actie("doe dat"));
        final boolean AFSTANDSONDERWIJS = false;
        final String SESSIENAAM = "sessie";
        final String SESSIEOMSCHRIJVING = "een sessie";
        final LocalDate BESCHIKBAARVANAF = LocalDate.now();
        final List<Groep> GROEPEN = new ArrayList<>();
        GROEPEN.add(new Groep("1"));

        OEFENINGEN.add(new Oefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD, GROEPSBEWERKINGEN, PAD, VAK, DOELSTELLING, TIJD));

        Box box = new Box(BOXNAAM, BOXOMSCHRIJVING, TOEGANGSCODES, OEFENINGEN, ACTIES);

        Sessie sessie = new Sessie(AFSTANDSONDERWIJS, SESSIENAAM, box, SESSIEOMSCHRIJVING, BESCHIKBAARVANAF.toString(), GROEPEN);

        Mockito.when(persistentieController.findAllSessies()).thenReturn(Arrays.asList(sessie));
        Mockito.when(persistentieController.findBox(BOXNAAM)).thenReturn(box);
        

        oefCon.addOefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD, GROEPSBEWERKINGEN.stream().map(GroepsBewerking::toString).collect(Collectors.toList()), PAD, VAK, DOELSTELLING.toString(), TIJD);
        boxCon.addBox(BOXNAAM, OEFENINGEN.stream().map(Oefening::toString).collect(Collectors.toList()), ACTIES.stream().map(Actie::toString).collect(Collectors.toList()), BOXOMSCHRIJVING);
        sessieCon.addSessie(AFSTANDSONDERWIJS, SESSIENAAM, BOXNAAM, SESSIEOMSCHRIJVING, BESCHIKBAARVANAF, GROEPEN.stream().map(Groep::toString).collect(Collectors.toList()));
        Assert.assertTrue(persistentieController.findAllSessies().contains(sessie));
        Mockito.verify(persistentieController).findAllSessies();
    }

    @Test
    public void wijzigSessie() {
        final String BOXNAAM = "box";
        final String BOXOMSCHRIJVING = "boxken";
        final ArrayList<String> TOEGANGSCODES = new ArrayList<>();
        final ArrayList<Oefening> OEFENINGEN = new ArrayList<>();
        final ArrayList<Actie> ACTIES = new ArrayList<>();
        final String OEFENINGNAAM = "ZoekX";
        final String OPGAVE = "x + 2 = 5";
        final String FEEDBACK = "probeer beide kanten af te trekken met 2";
        final String ANTWOORD = "3";
        final ArrayList<GroepsBewerking> GROEPSBEWERKINGEN = new ArrayList<>();
        final String PAD = "pad";
        final String VAK = "Wiskunde";
        final Doelstelling DOELSTELLING = new Doelstelling("aanleren van sommen");
        final int TIJD = 5;
        GROEPSBEWERKINGEN.add(new GroepsBewerking("tel 2 op", "+", "2"));
        TOEGANGSCODES.add("123");
        TOEGANGSCODES.add("45");
        ACTIES.add(new Actie("doe dit"));
        ACTIES.add(new Actie("doe dat"));
        final boolean AFSTANDSONDERWIJS = false;
        final String SESSIENAAM = "sessie";
        final String SESSIEOMSCHRIJVING1 = "een sessie";
        final String SESSIEOMSCHRIJVING2 = "een andere sessie";
        final LocalDate BESCHIKBAARVANAF = LocalDate.now();
        final List<Groep> GROEPEN = new ArrayList<>();
        GROEPEN.add(new Groep("1"));

        OEFENINGEN.add(new Oefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD, GROEPSBEWERKINGEN, PAD, VAK, DOELSTELLING, TIJD));

        Box box = new Box(BOXNAAM, BOXOMSCHRIJVING, TOEGANGSCODES, OEFENINGEN, ACTIES);

        Sessie sessie = new Sessie(AFSTANDSONDERWIJS, SESSIENAAM, box, SESSIEOMSCHRIJVING2, BESCHIKBAARVANAF.toString(), GROEPEN);

        Mockito.when(persistentieController.findAllSessies()).thenReturn(Arrays.asList(sessie));
        Mockito.when(persistentieController.findBox(BOXNAAM)).thenReturn(box);

        oefCon.addOefening(OEFENINGNAAM, OPGAVE, FEEDBACK, ANTWOORD, GROEPSBEWERKINGEN.stream().map(GroepsBewerking::toString).collect(Collectors.toList()), PAD, VAK, DOELSTELLING.toString(), TIJD);
        boxCon.addBox(BOXNAAM, OEFENINGEN.stream().map(Oefening::toString).collect(Collectors.toList()), ACTIES.stream().map(Actie::toString).collect(Collectors.toList()), BOXOMSCHRIJVING);
        sessieCon.addSessie(AFSTANDSONDERWIJS, SESSIENAAM, BOXNAAM, SESSIEOMSCHRIJVING1, BESCHIKBAARVANAF, GROEPEN.stream().map(Groep::toString).collect(Collectors.toList()));
        sessieCon.wijzigSessie(SESSIENAAM, SESSIEOMSCHRIJVING2, AFSTANDSONDERWIJS);
        Assert.assertTrue(persistentieController.findAllSessies().contains(sessie));
        Mockito.verify(persistentieController).findAllSessies();
    }

    @Test
    public void toevoegenVanBoxGenereertToegangsCodes() {

        ArrayList<String> toeganscodes = new ArrayList<>();
        ArrayList<Oefening> oefeningen = new ArrayList<>();
        ArrayList<Actie> acties = new ArrayList<>();

        ArrayList<GroepsBewerking> groepsBewerkingen = new ArrayList<>();

        groepsBewerkingen.add(new GroepsBewerking("tel 2 op", "+", "2"));
        toeganscodes.add("123");
        toeganscodes.add("45");
        acties.add(new Actie("doe dit"));
        acties.add(new Actie("doe dat"));

        oefeningen.add(new Oefening("ZoekX", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "3", groepsBewerkingen, "pad", "Wiskunde", new Doelstelling("sommen aanleren"), 3));
        oefeningen.add(new Oefening("ZoekX2", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "3", groepsBewerkingen, "pad", "Wiskunde", new Doelstelling("sommen aanleren"), 3));

        Box box = new Box("box", "boxken", toeganscodes, oefeningen, acties);

        boxCon.addBox("box", oefeningen.stream().map(Oefening::toString).collect(Collectors.toList()), acties.stream().map(Actie::toString).collect(Collectors.toList()), "boxken");
        assertTrue(boxCon.geefToegangsCodesObservableOpBoxNaam("box").size() == acties.size());

    }

    @Test
    public void toevoegenActie() {
        final String NAAM = "Actie";
        Actie actie = new Actie(NAAM);
        Mockito.when(persistentieController.findAllOpgeslagenActies()).thenReturn(Arrays.asList(actie));
        boxCon.slaActieOp(NAAM);
        assertTrue(persistentieController.findAllOpgeslagenActies().contains(actie));

    }

    @Test
    public void toevoegenDoelstelling() {
        final String NAAM = "sommen aanleren";
        Doelstelling doelstelling = new Doelstelling(NAAM);
        Mockito.when(persistentieController.findAllOpgeslagenDoelstellingen()).thenReturn(Arrays.asList(doelstelling));
        oefCon.slaDoelstellingOp(NAAM);
        assertTrue(persistentieController.findAllOpgeslagenDoelstellingen().contains(doelstelling));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void OefeningToevoegenMetLegeNaam() {
        List<GroepsBewerking> groepsBewerkingen = new ArrayList<GroepsBewerking>();
        groepsBewerkingen.add(new GroepsBewerking("tel 2 op", "+", "2"));
        Oefening oefening = new Oefening("", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "3", groepsBewerkingen, "pad", "Wiskunde", new Doelstelling("aanleren van sommen"), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void OefeningToevoegenMetNegatieveTijd() {
        List<GroepsBewerking> groepsBewerkingen = new ArrayList<GroepsBewerking>();
        groepsBewerkingen.add(new GroepsBewerking("tel 2 op", "+", "2"));
        Oefening oefening = new Oefening("zoekX", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "3", groepsBewerkingen, "pad", "Wiskunde", new Doelstelling("aanleren van sommen"), -3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void OefeningToevoegenMetAntwoordNietNumeriek() {
        List<GroepsBewerking> groepsBewerkingen = new ArrayList<GroepsBewerking>();
        groepsBewerkingen.add(new GroepsBewerking("tel 2 op", "+", "2"));
        Oefening oefening = new Oefening("ZoekX", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "test", groepsBewerkingen, "pad", "Wiskunde", new Doelstelling("aanleren van sommen"), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void OefeningToevoegenMetLeegAntwoord() {
        List<GroepsBewerking> groepsBewerkingen = new ArrayList<GroepsBewerking>();
        groepsBewerkingen.add(new GroepsBewerking("tel 2 op", "+", "2"));
        Oefening oefening = new Oefening("zoekX", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "", groepsBewerkingen, "pad", "Wiskunde", new Doelstelling("aanleren van sommen"), 3);

    }

    @Test(expected = IllegalArgumentException.class)
    public void OefeningToevoegenMetLegeFeedback() {
        List<GroepsBewerking> groepsBewerkingen = new ArrayList<GroepsBewerking>();
        groepsBewerkingen.add(new GroepsBewerking("tel 2 op", "+", "2"));
        Oefening oefening = new Oefening("ZoekX", "x + 2 = 3", "", "3", groepsBewerkingen, "pad", "Wiskunde", new Doelstelling("aanleren van sommen"), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void OefeningToevoegenMetLeefPad() {
        List<GroepsBewerking> groepsBewerkingen = new ArrayList<GroepsBewerking>();
        groepsBewerkingen.add(new GroepsBewerking("tel 2 op", "+", "2"));
        Oefening oefening = new Oefening("ZoekX", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "3", groepsBewerkingen, "", "Wiskunde", new Doelstelling("aanleren van sommen"), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void OefeningToevoegenMetLegeDoelstelling() {
        List<GroepsBewerking> groepsBewerkingen = new ArrayList<GroepsBewerking>();
        groepsBewerkingen.add(new GroepsBewerking("tel 2 op", "+", "2"));
        Oefening oefening = new Oefening("ZoekX", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "3", groepsBewerkingen, "pad", "Wiskunde", new Doelstelling(""), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void OefeningToevoegenMetLeegVak() {
        List<GroepsBewerking> groepsBewerkingen = new ArrayList<GroepsBewerking>();
        groepsBewerkingen.add(new GroepsBewerking("tel 2 op", "+", "2"));
        Oefening oefening = new Oefening("ZoekX", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "3", groepsBewerkingen, "pad", "", new Doelstelling("aanleren van sommen"), 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void BoxToevoegenMetLegeNaam() {
        List<Actie> acties = new ArrayList<Actie>();
        List<String> toegangsCodes = new ArrayList<>();
        List<Oefening> oefeningen = new ArrayList<>();
        List<GroepsBewerking> groepsBewerkingen = new ArrayList<GroepsBewerking>();
        groepsBewerkingen.add(new GroepsBewerking("tel 2 op", "+", "2"));
        acties.add(new Actie("actie"));
        toegangsCodes.add("1234");
        oefeningen.add(new Oefening("ZoekX", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "3", groepsBewerkingen, "pad", "Wiskunde", new Doelstelling("sommen aanleren"), 3));
        oefeningen.add(new Oefening("ZoekX2", "x + 2 = 3", "probeer beide kanten af te trekken met 2", "3", groepsBewerkingen, "pad", "Wiskunde", new Doelstelling("sommen aanleren"), 3));

        Box box = new Box("", "een box", toegangsCodes, oefeningen, acties);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ActieToevoegenMetLegeNaam() {
        Actie actie = new Actie("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void DoelstellingToevoegenMetLegeNaam() {
        Doelstelling doelstelling = new Doelstelling("");
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void GroepToevoegenMetLeegGroepNummer(){
        Groep groep = new Groep("");
    }
}
