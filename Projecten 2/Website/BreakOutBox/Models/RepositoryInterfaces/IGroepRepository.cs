using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models
{
    public interface IGroepRepository
    {
        Groep GetBy(string id);
        IEnumerable<Groep> GetAll();
    }
}
