using BreakOutBox.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;

namespace XUnitTestBreakOutBox.Data
{
    class DummyApplicationDbContext
    {
        public  IEnumerable<Actie> Actie { get; }
        public  IEnumerable<Box> Box { get; }
        public  IEnumerable<BoxActies> BoxActies { get; }
        public IEnumerable<String> BoxActiesNamen { get; }
        public IEnumerable<BoxOef> BoxOef { get;  }
        public IEnumerable<String> BoxOefNamen { get; }
        public  IEnumerable<Doelstelling> Doelstelling { get;  }
        public  IEnumerable<Groep> Groep { get; }
        public  IEnumerable<GroepOef> GroepOef { get;  }
        public  IEnumerable<Groepsbewerking> Groepsbewerking { get;  }
        public  IEnumerable<Mergeall> Mergeall { get;  }
        public  IEnumerable<Oefening> Oefening { get;  }
        public  IEnumerable<OefGbw> OefGbw { get;  }
        public  IEnumerable<Sequence> Sequence { get; }
        public  IEnumerable<Sessie> Sessie { get;  }
        public  IEnumerable<SessieGroep> SessieGroep { get;  }
        public IEnumerable<Vooruitgang> Vooruitgang { get; }
        public IEnumerable<Vooruitgang> Sessie1Vooruitgangen { get; }
        public Oefening Oefening1 { get; }
        public Oefening Oefening2 { get; }
        public Sessie Sessie1 { get; }
        public Sessie Sessie2 { get; }
        public Sessie Sessie3 { get; }
        public Vooruitgang Vooruitgang1 { get; }
        public Vooruitgang Vooruitgang2 { get; }
        public Vooruitgang Vooruitgang3 { get; }
        public Vooruitgang Vooruitgang4 { get; }
        public Groep Groep1 { get; }
        public Groep Groep2 { get; }
        public Groep Groep3 { get; }
        public Mergeall Mergeall1 { get; }
        public Groepsbewerking Groepsbewerking1 { get; }
        public Actie Actie1 { get; }

        public DummyApplicationDbContext()
        {
            Actie1 = new Actie() { actie = "actie1" };
            Actie = new[] { Actie1 };
            BoxActies boxActie = new BoxActies { BoxNaam = "box1", Actie = Actie1.actie };
            BoxActies = new[] { boxActie };
            BoxActiesNamen = new[] { boxActie.Actie };

            Groepsbewerking1 = new Groepsbewerking { Opgave = "voeg 10 toe", Getal = "10", Operator = "+" };
            Groepsbewerking = new[] { Groepsbewerking1 };

            Doelstelling doelstelling = new Doelstelling { Doelstelling1 = "sommen" };
            Doelstelling = new[] { doelstelling };

            Oefening1 = new Oefening { Naam = "Oef1", Antwoord= "12", Feedback= "opnieuw", Opgave = "x + 2 = 14", Padnaarpdf ="pad", Timeleft =0, Vak="Wiskunde", Doelstelling = doelstelling.ToString()};
            Oefening2 = new Oefening { Naam = "Oef2", Antwoord = "10", Feedback = "opnieuw", Opgave = "x + 2 = 12", Padnaarpdf = "pad", Timeleft = 1, Vak = "Wiskunde", Doelstelling = doelstelling.ToString() };
            Oefening oefening3 = new Oefening { Naam = "Oef3", Antwoord = "14", Feedback = "opnieuw", Opgave = "x -2 = 10", Padnaarpdf = "pad", Timeleft = 0, Vak = "Wiskunde", Doelstelling = doelstelling.ToString() };
            Oefening = new[] { Oefening1, Oefening2, oefening3 };
            BoxOef boxOef1 = new BoxOef { BoxNaam = "box1", OefeningNaam = "Oef1" };
            BoxOef boxOef2 = new BoxOef { BoxNaam = "box1", OefeningNaam = "Oef2" };
            BoxOef = new[] { boxOef1, boxOef2 };
            BoxOefNamen = new[] { boxOef1.OefeningNaam, boxOef2.OefeningNaam };

            Groep1 = new Groep { Id = "13", Groepnummer = "2", Leerlingen = "vital, fred, Johan, Joris" };
            Groep2 = new Groep { Id = "15", Groepnummer = "3", Leerlingen = "Jos, Tim, Justin, Wout" };
            Groep3 = new Groep { Id = "14", Groepnummer = "4", Leerlingen = "Jos, Tim, Justin, Wout" };

            Groep = new[] { Groep1, Groep2 };
            

            GroepOef groepOef1 = new GroepOef { Groep = "13", Oefening = "Oef1" };
            GroepOef groepOef2 = new GroepOef { Groep = "13", Oefening = "Oef2" };
            GroepOef = new[] { groepOef1, groepOef2 };

            Mergeall1 = new Mergeall { Id = "46", Actiecode = 10, Sessienaam = "sessie1", GroepId = "13", GroepsbewerkingOpgave = Groepsbewerking1.ToString(), OefNaam = "Oef1" };
            Mergeall = new[] { Mergeall1 };

            OefGbw oefGbw1 = new OefGbw { OefeningNaam = "Oef1", GroepsBewerkingId = Groepsbewerking1.Getal };
            OefGbw = new[] { oefGbw1 };


            Box box = new Box { Naam = "box1", Omschrijving= "een box"};
            Box = new[] { box };

            Sessie1 = new Sessie { Naam = "sessie1", Afstandsonderwijs = false, Beschikbaarvanaf = "18 05 2018", Code = "123", Omschrijving = "een sessie", BoxNaam = "box1" };
            Sessie2 = new Sessie { Naam = "sessie2", Afstandsonderwijs = false, Beschikbaarvanaf = "12 06 2018", Code = "321", Omschrijving = "een sessie", BoxNaam = "box1" };
            Sessie3 = new Sessie { Naam = "sessie3", Afstandsonderwijs = true, Beschikbaarvanaf = "18 05 2018", Code = "032", Omschrijving = "een sessie", BoxNaam = "box1" };
            Sessie = new[] { Sessie1, Sessie2 };

            SessieGroep sessieGroep1 = new SessieGroep { SessieNaam = "sessie1", GroepId = "13" };
            SessieGroep sessieGroep2 = new SessieGroep { SessieNaam = "sessie2", GroepId = "15" };
            SessieGroep sessieGroep3 = new SessieGroep { SessieNaam = "sessie3", GroepId = "14" };

            SessieGroep = new[] { sessieGroep1, sessieGroep2 };

            Vooruitgang1 = new Vooruitgang { AantalFoutePogingen = 0, GroepID = "13", Id = "1", IsFrozen = 0, OefNummer = 0, Score = 0, Sessienaam = "sessie1" };
            Vooruitgang2 = new Vooruitgang { AantalFoutePogingen = 0, GroepID = "15", Id = "2", IsFrozen = 1, OefNummer = 0, Score = 0, Sessienaam = "sessie2" };
            Vooruitgang3 = new Vooruitgang { AantalFoutePogingen = 0, GroepID = "14", Id = "3", IsFrozen = 0, OefNummer = 1, Score = 1, Sessienaam = "sessie1" };
            Vooruitgang4 = new Vooruitgang { AantalFoutePogingen = 0, GroepID = "14", Id = "3", IsFrozen = 0, OefNummer = 2, Score = 2, Sessienaam = "sessie2" };

            Vooruitgang = new[] { Vooruitgang1, Vooruitgang2, Vooruitgang3, Vooruitgang4 };
            Sessie1Vooruitgangen = new[] { Vooruitgang1, Vooruitgang3 };


        }
    }
}
