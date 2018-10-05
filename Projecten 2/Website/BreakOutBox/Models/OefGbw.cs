using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class OefGbw
    {
        [Key]
        public string OefeningNaam { get; set; }
        public string GroepsBewerkingId { get; set; }

        public Groepsbewerking GroepsBewerking { get; set; }
        public Oefening OefeningNaamNavigation { get; set; }
    }
}
