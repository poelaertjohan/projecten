using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models
{
    public interface IVooruitgangRepository
    {
        Vooruitgang GetBy(string groepId);

        IEnumerable<Vooruitgang> GetAll();

        IEnumerable<Vooruitgang> GetVooruitgangenBySessieNaam(string naam);

        void saveChanges();

        void add(Vooruitgang vooruitgang);

        void remove(Vooruitgang vooruitgang);

        void UpdateAantalPogingen(string groepId);

        void UpdateFrozen(string groepId, Boolean aanuit);

        void SetAantalPogingenToZero(string groepID);

        void ChangeAllFrozenBySessieCode(string naam, Boolean freeze);

    }
}
