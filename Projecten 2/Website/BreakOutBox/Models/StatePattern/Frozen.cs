using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models.StatePattern
{
    public class Frozen : StateVooruitgang
    {
        private readonly IVooruitgangRepository _vooruitgangRepository;
        private int frozen { get; set; }

        public Frozen(IVooruitgangRepository vooruitgangRepository)
        {
            _vooruitgangRepository = vooruitgangRepository;

        }

        public Boolean FrozenStatus(string groepid)
        {
            var status = _vooruitgangRepository.GetBy(groepid).IsFrozen;
            if (status == 0)
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        public void ChangeFrozenStatus(string groepid, Boolean tf)
        {
            _vooruitgangRepository.UpdateFrozen(groepid, tf);
        }


    }
}
