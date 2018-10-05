using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class Oefening
    {
        public Oefening()
        {
            BoxOef = new HashSet<BoxOef>();
            GroepOef = new HashSet<GroepOef>();
            Mergeall = new HashSet<Mergeall>();
            OefGbw = new HashSet<OefGbw>();
        }

        [Key]
        public string Naam { get; set; }
        public string Antwoord { get; set; }
        public string Feedback { get; set; }
        public string Opgave { get; set; }
        public string Padnaarpdf { get; set; }
        public int? Timeleft { get; set; }
        public string Vak { get; set; }
        public string Doelstelling { get; set; }

        public Doelstelling DoelstellingDoelstellingNavigation { get; set; }
        public ICollection<BoxOef> BoxOef { get; set; }
        public ICollection<GroepOef> GroepOef { get; set; }
        public ICollection<Mergeall> Mergeall { get; set; }
        public ICollection<OefGbw> OefGbw { get; set; }
    }
}
