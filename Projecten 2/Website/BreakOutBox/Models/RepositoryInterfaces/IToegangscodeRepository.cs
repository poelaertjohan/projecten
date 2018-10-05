using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models.RepositoryInterfaces
{
    public interface IToegangscodeRepository
    {
        IEnumerable<BoxToegangscode> GetCodesByBoxNaam(string naam);
    }
}
