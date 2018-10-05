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
    public class ToegangscodeRepository : IToegangscodeRepository
    {

        private readonly ApplicationDbContext _dbContext;
        private readonly DbSet<BoxToegangscode> _BoxToegangsCodes;

        public ToegangscodeRepository(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
            _BoxToegangsCodes = dbContext.BoxToegangscodes;
        }
        public IEnumerable<BoxToegangscode> GetCodesByBoxNaam(String naam)
        {
            return _BoxToegangsCodes.Where(x => x.BoxNaam == naam).ToList();
            
        }
    }
}
