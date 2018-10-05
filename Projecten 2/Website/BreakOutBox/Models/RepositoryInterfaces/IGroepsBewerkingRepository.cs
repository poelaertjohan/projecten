using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models.RepositoryInterfaces
{
    public interface IGroepsBewerkingRepository
    {
        Groepsbewerking GetBy(string opgave);
    }
}
