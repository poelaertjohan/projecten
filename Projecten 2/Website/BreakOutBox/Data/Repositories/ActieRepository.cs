using BreakOutBox.Models;
using dotnet_g05.Data;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Data.Repositories
{
    public class ActieRepository : IActieRepository
    {
        private readonly DbSet<Actie> _actie;
        private readonly DbSet<BoxActies> _boxActies;
        private readonly ApplicationDbContext _dbContext;

        public ActieRepository(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
            _actie = _dbContext.Actie;
            _boxActies = dbContext.BoxActies;
        }

        public IEnumerable<Actie> GetAll()
        {
            return _actie;

        }

        public Actie GetBy(string Actie)
        {
            return _actie.SingleOrDefault(g => g.actie == Actie);
        }

        public IEnumerable<String> GetActiesByBoxNaam(string naam)
        {
            IEnumerable<BoxActies> boxActies = _boxActies.Where(x => x.BoxNaam == naam);
            return boxActies.Select(e => e.Actie).ToList();
            

        }

        public void SaveChanges()
        {
            _dbContext.SaveChanges();
        }

       
    }
}
