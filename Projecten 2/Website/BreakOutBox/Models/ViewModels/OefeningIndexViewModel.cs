using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models.ViewModels
{
    public class OefeningIndexViewModel
    {
        public string Naam { get; set; }
        public string Opgave { get; set; }
        public string Antwoord { get; set; }
        public string Pdfpad { get; set; }
        public string GroepsBewerkingGetal { get; set; }
        public string GroepsBewerkingOperator { get; set; }
        public string GroepsBewerkingOpgave { get; set; }
        public DateTime DateStart { get; set; }
        public float Percentage { get; set; }
        public string GroepId { get; set; }
        public OefeningIndexViewModel() { }

        public OefeningIndexViewModel(Oefening oefening, Groepsbewerking groepsbewerking, float percentage, string groepId)
        {
            Naam = oefening.Naam;
            Opgave = oefening.Opgave;
            var pad = oefening.Padnaarpdf;
            Pdfpad = pad.Split('\\').Last();
            GroepsBewerkingGetal = groepsbewerking.Getal;
            GroepsBewerkingOperator = groepsbewerking.Operator;
            GroepsBewerkingOpgave = groepsbewerking.Opgave;
            DateStart = DateTime.Now;
            Percentage = percentage;
            GroepId = groepId;
        }
    }
}
