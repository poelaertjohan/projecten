using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class Groep
    {
        public Groep()
        {
            GroepOef = new HashSet<GroepOef>();
            Mergeall = new HashSet<Mergeall>();
            SessieGroep = new HashSet<SessieGroep>();
        }

        [Key]
        public string Id { get; set; }
        public string Groepnummer { get; set; }
        public string Leerlingen { get; set; }

        public ICollection<GroepOef> GroepOef { get; set; }
        public ICollection<Mergeall> Mergeall { get; set; }
        public ICollection<SessieGroep> SessieGroep { get; set; }
    }
}
