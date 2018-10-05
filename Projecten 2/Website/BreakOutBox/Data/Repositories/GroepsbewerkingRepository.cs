using BreakOutBox.Models;
using BreakOutBox.Models.RepositoryInterfaces;
using dotnet_g05.Data;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Data.Repositories
{
    public class GroepsbewerkingRepository : IGroepsBewerkingRepository
    {
        private readonly ApplicationDbContext _dbContext;
        private readonly DbSet<Groepsbewerking> _groepsbewerkingen;

        public GroepsbewerkingRepository(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
            _groepsbewerkingen = dbContext.Groepsbewerking;
        }

        public Groepsbewerking GetBy(string opgave)
        {
            return _groepsbewerkingen.SingleOrDefault(x => x.Opgave == opgave);
        }
    }
}
