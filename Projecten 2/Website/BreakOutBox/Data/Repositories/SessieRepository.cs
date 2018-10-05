using BreakOutBox.Models;
using dotnet_g05.Data;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;

namespace BreakOutBox.Data.Repositories
{
    public class SessieRepository : ISessieRepository
    {
        private readonly ApplicationDbContext _dbContext;
        private readonly DbSet<Sessie> _sessies;
        private readonly DbSet<SessieGroep> _sessieGroepen;
        private readonly DbSet<Box> _boxen;


        public SessieRepository(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
            _sessies = dbContext.Sessie;
            _sessieGroepen = dbContext.SessieGroep;
            _boxen = dbContext.Box;
        }

        public IEnumerable<Sessie> GetAll()
        {
            return _sessies; //.Include(o => o.Naam).Include(o => o.Opgave).ToList();
        }

        public Sessie getByCode(String code)
        {
            return _sessies.SingleOrDefault(o => o.Code == code);
        }

        public Sessie GetBy(string naam)
        {
            return _sessies.SingleOrDefault(o => o.Code == naam);
        }

        public String GetSessieNaamByCode(String code)
        {
            return _sessies.SingleOrDefault(s => s.Code == code).Naam;
        }
        public IEnumerable<Groep> GetGroepenBySessie(string naam)
        {
             var sessiegroepen = _sessieGroepen.Where(x => x.SessieNaam == naam);
             return sessiegroepen.Select(e => e.Groep).ToList();
           /* var groepen = new[] { (new Groep() { Id = "1", Groepnummer = "1", Leerlingen = "Jan, Jos, Jaap" }), (new Groep() { Id = "2", Groepnummer = "2", Leerlingen = "Tom, Tim, Timon" }), (new Groep() { Id = "3", Groepnummer = "3", Leerlingen = "Vital, Joris, Johan" }) };
            return groepen;*/
        }

        public IEnumerable<Box> GetBoxBySessie(string naam)
        {
            return _sessies.Where(x => x.Naam == naam).Select(e => e.BoxNaamNavigation).ToList();
        }


        public void saveChanges()
        {
            _dbContext.SaveChanges();
        }
    }
}
