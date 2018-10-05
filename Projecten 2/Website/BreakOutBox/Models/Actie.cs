using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace BreakOutBox.Models
{
    public partial class Actie
    {
        
        public Actie()
        {
            BoxActies = new HashSet<BoxActies>();
        }

        [Key]
        public string actie{ get; set; }

        public ICollection<BoxActies> BoxActies { get; set; }
    }
}
