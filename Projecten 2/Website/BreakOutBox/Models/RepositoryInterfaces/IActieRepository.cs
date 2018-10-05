using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models
{
    public interface IActieRepository
    {
        IEnumerable<Actie> GetAll();
        IEnumerable<String> GetActiesByBoxNaam(string naam);
        Actie GetBy(string Actie);
        void SaveChanges();
    }
}
