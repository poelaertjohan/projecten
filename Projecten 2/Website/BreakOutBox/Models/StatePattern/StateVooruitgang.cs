using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models.StatePattern
{
    public abstract class StateVooruitgang
    {
        private AantalPogingen aantalpogingen;
        private Frozen frozen;


        public Boolean Frozenstatus(string groepId)
        {
            return frozen.FrozenStatus(groepId);
        }
        public void ChangeFrozenStatus(string groepId, Boolean tf)
        {
            frozen.ChangeFrozenStatus(groepId, tf);
        }


        }

    }

