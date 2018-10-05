using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class Mergeall
    {
        [Key]
        public string Id { get; set; }
        public int? Actiecode { get; set; }
        public string Sessienaam { get; set; }
        public string GroepId { get; set; }
        public string GroepsbewerkingOpgave { get; set; }
        public string OefNaam { get; set; }

        public Groep Groep { get; set; }
        public Groepsbewerking GroepsbewerkingOpgaveNavigation { get; set; }
        public Oefening OefNaamNavigation { get; set; }
    }
}
