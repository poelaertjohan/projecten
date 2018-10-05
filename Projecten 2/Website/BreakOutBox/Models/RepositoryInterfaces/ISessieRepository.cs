using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models
{
    public interface ISessieRepository
    {

        Sessie GetBy(string naam);

        IEnumerable<Sessie> GetAll();

        Sessie getByCode(String code);

        String GetSessieNaamByCode(String code);

        IEnumerable<Groep> GetGroepenBySessie(String naam);

        void saveChanges();
    }
}
