using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models
{
    public class BoxToegangscode
    {
        [Key]
        public string BoxNaam { get; set; }
        public string TOEGANGSCODE { get; set; }
    }
}
