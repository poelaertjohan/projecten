using BreakOutBox.Models;
using dotnet_g05.Data;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Data.Repositories
{
    public class OefeningRepository : IOefeningRepository
    {
        private readonly ApplicationDbContext _dbContext;
        private readonly DbSet<Oefening> _oefeningen;
        private readonly DbSet<OefGbw> _oefGroepsbewerkingen;

        public OefeningRepository(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
            _oefeningen = dbContext.Oefening;
            _oefGroepsbewerkingen = dbContext.OefGbw;
        }

        public IEnumerable<Oefening> GetAll()
        {
            return _oefeningen; //.Include(o => o.Naam).Include(o => o.Opgave).ToList();
        }

        public Oefening GetBy(string naam)
        {
            return _oefeningen //.Include(o => o.Naam).Include(o => o.Opgave)
                .SingleOrDefault(o => o.Naam == naam);
        }

        public IEnumerable<Groepsbewerking> getGroepsBewerkingenByOefNaam(String naam)
        {
            var OefGbw = _oefGroepsbewerkingen.Where(x => x.OefeningNaam == naam);
            return OefGbw.Select(e => e.GroepsBewerking).ToList();
        }



        public void saveChanges()
        {
            _dbContext.SaveChanges();
        }
    }
}
