using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class BoxActies
    {
        [Key]
        public string BoxNaam { get; set; }
        public string Actie { get; set; }
        public Actie ActieActieNavigation { get; set; }
        public Box BoxNaamNavigation { get; set; }
    }
}
