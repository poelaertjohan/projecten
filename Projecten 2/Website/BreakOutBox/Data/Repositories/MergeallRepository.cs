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
    public class MergeallRepository : IMergeallRepository
    {
        private readonly ApplicationDbContext _dbContext;
        private readonly DbSet<Mergeall> _mergealls;


        public MergeallRepository(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
            _mergealls = _dbContext.Mergeall;

        }
        public IEnumerable<Mergeall> GetBy(string groepId)
        {
            return _mergealls.ToList().Where(m => m.GroepId == groepId);
        }

        public void SaveChanges()
        {
            throw new NotImplementedException();
        }
    }
}
