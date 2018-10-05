using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class BoxOef
    {
        [Key]
        public string BoxNaam { get; set; }
        public string OefeningNaam { get; set; }

        public Box BoxNaamNavigation { get; set; }
        public Oefening OefeningNaamNavigation { get; set; }
    }
}
