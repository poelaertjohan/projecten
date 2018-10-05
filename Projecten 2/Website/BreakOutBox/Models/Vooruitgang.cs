using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models
{
    public partial class Vooruitgang
    {
        [Key]
        public string Id { get; set; }
        public string Sessienaam { get; set; }
        public string GroepID { get; set; }
        public int OefNummer { get; set; }
        public int Score { get; set; }
        public int IsFrozen { get; set; }
        public int AantalFoutePogingen { get; set; }

        public Vooruitgang()
        {
        }

        public Vooruitgang(string Sessienaam, string GroepID, int OefNummer, int Score, int Frozen, int AantalFoutePogingen)
        {
            this.Sessienaam = Sessienaam;
            this.GroepID = GroepID;
            this.OefNummer = OefNummer;
            this.Score = Score;
            this.IsFrozen = Frozen;
            this.AantalFoutePogingen = AantalFoutePogingen;
        }



}
}
