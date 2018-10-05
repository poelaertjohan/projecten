using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class Groepsbewerking
    {
        public Groepsbewerking()
        {
            Mergeall = new HashSet<Mergeall>();
            OefGbw = new HashSet<OefGbw>();
        }

        [Key]
        public string Opgave { get; set; }
        public string Getal { get; set; }
        public string Operator { get; set; }

        public ICollection<Mergeall> Mergeall { get; set; }
        public ICollection<OefGbw> OefGbw { get; set; }
    }
}
