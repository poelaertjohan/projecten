using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class Doelstelling
    {
        public Doelstelling()
        {
            Oefening = new HashSet<Oefening>();
        }

        [Key]
        public string Doelstelling1 { get; set; }

        public ICollection<Oefening> Oefening { get; set; }
    }
}
