using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class Box
    {
        public Box()
        {
            BoxActies = new HashSet<BoxActies>();
            BoxOef = new HashSet<BoxOef>();
            Sessie = new HashSet<Sessie>();
        }

        [Key]
        public string Naam { get; set; }
        public string Omschrijving { get; set; }

        public ICollection<BoxActies> BoxActies { get; set; }
        public ICollection<BoxOef> BoxOef { get; set; }
        public ICollection<Sessie> Sessie { get; set; }
    }
}
