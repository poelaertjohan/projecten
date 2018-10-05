using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models
{
    public interface IBoxRepository
    {

        Box GetBy(string naam);
        IEnumerable<String> GetActiesByBoxNaam(String naam);
        IEnumerable<String> GetToegangscodesByBoxNaam(String naam);
        IEnumerable<Box> GetAll();
        IEnumerable<String> getOefeningenByBox(String naam);
    }
}
