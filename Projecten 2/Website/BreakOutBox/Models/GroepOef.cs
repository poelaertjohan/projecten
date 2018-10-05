using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class GroepOef
    {
        [Key]
        public string Groep { get; set; }
        public string Oefening { get; set; }

        public Groep GroepNavigation { get; set; }
        public Oefening OefeningNavigation { get; set; }
    }
}
