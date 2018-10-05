using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models
{
    public interface IOefeningRepository
    {
        Oefening GetBy(string naam);

        IEnumerable<Oefening> GetAll();

        IEnumerable<Groepsbewerking> getGroepsBewerkingenByOefNaam(String naam);

        void saveChanges();
    }
}
