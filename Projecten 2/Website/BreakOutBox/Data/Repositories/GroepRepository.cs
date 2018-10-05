using BreakOutBox.Models;
using dotnet_g05.Data;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Data.Repositories
{
    public class GroepRepository : IGroepRepository
    {
        private readonly ApplicationDbContext _dbContext;
        private readonly DbSet<Groep> _groepen;

        public GroepRepository(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
            _groepen = dbContext.Groep;
        }

        public IEnumerable<Groep> GetAll()
        {
            
            return _groepen;

        }

        public Groep GetBy(string id)
        {
            return _groepen.SingleOrDefault(x => x.Id == id);
        }
    }
}
