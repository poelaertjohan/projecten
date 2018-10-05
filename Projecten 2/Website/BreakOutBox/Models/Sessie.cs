using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class Sessie
    {
        public Sessie()
        {
            SessieGroep = new HashSet<SessieGroep>();
        }

        [Key]
        public string Naam { get; set; }
        public bool? Afstandsonderwijs { get; set; }
        public string Beschikbaarvanaf { get; set; }
        public string Code { get; set; }
        public string Omschrijving { get; set; }
        public string BoxNaam { get; set; }

        public Box BoxNaamNavigation { get; set; }
        public ICollection<SessieGroep> SessieGroep { get; set; }
    }
}
