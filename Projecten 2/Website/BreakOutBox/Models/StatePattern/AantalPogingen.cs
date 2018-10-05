using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models.StatePattern
{
    public abstract class AantalPogingen
    {
        private readonly IVooruitgangRepository _vooruitgangRepository;
        private int aantalpogingen { get; set; }
        public AantalPogingen(IVooruitgangRepository vooruitgangRepository)
        {
            _vooruitgangRepository = vooruitgangRepository;
        }
      
        public AantalPogingen(int aantal)
        {
            this.aantalpogingen = aantal;
        }

        public void VerhoogAantalPogingen(string groepId)
        {
            _vooruitgangRepository.UpdateAantalPogingen(groepId);
        }

    }
}
