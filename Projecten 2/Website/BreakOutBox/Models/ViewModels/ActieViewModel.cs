using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models.ViewModels
{
    public class ActieViewModel
    {
        public String Naam { get; set; }
        public String ToegangsCode { get; set; }


        public ActieViewModel() { }

        public ActieViewModel(Actie actie)
        {
            Naam = actie.actie;
        }

    }
}
