using BreakOutBox.Models;
using dotnet_g05.Data;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Data.Repositories
{
    public class BoxRepository : IBoxRepository
    {
        private readonly ApplicationDbContext _dbContext;
        private readonly DbSet<Box> _boxes;
        private readonly DbSet<BoxOef> _boxOefeningen;
        private readonly DbSet<BoxActies> _boxActies;
        private readonly DbSet<BoxToegangscode> _boxToegangscodes;

        public BoxRepository(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
            _boxes = _dbContext.Box;
            _boxOefeningen = dbContext.BoxOef;
            _boxActies = dbContext.BoxActies;
            _boxToegangscodes = dbContext.BoxToegangscodes;
        }
        public IEnumerable<Box> GetAll()
        {
            return _boxes;
        }

        public Box GetBy(string naam)
        {
            return _boxes.SingleOrDefault(o => o.Naam == naam);
        }

        public IEnumerable<String> getOefeningenByBox(string naam)
        {
            var boxOefeningen = _boxOefeningen.Where(x => x.BoxNaam == naam);
            return boxOefeningen.Select(e => e.OefeningNaam).ToList();
           
        }


        public IEnumerable<String> GetActiesByBoxNaam(string naam)
        {
            IEnumerable<BoxActies> boxActies = _boxActies.Where(x => x.BoxNaam == naam);
            return boxActies.Select(e => e.Actie).ToList();


        }

        public IEnumerable<String> GetToegangscodesByBoxNaam(string naam)
        {
            IEnumerable<BoxToegangscode> boxCodes = _boxToegangscodes.Where(x => x.BoxNaam == naam);
            return boxCodes.Select(e => e.TOEGANGSCODE).ToList();


        }
    }
}
