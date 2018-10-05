using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class SessieGroep
    {
        [Key]
        public string SessieNaam { get; set; }

        
        public string GroepId { get; set; }

        public Groep Groep { get; set; }
        public Sessie SessieNaamNavigation { get; set; }
    }
}
