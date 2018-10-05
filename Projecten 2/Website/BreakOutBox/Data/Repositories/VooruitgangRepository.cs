using BreakOutBox.Models;
using dotnet_g05.Data;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Data.Repositories
{
    public class VooruitgangRepository : IVooruitgangRepository
    {

        private readonly ApplicationDbContext _dbContext;
        private readonly DbSet<Vooruitgang> _vooruitgangen;


        public VooruitgangRepository(ApplicationDbContext dbContext)
        {
            _dbContext = dbContext;
            _vooruitgangen = dbContext.Vooruitgang;
        }

        public IEnumerable<Vooruitgang> GetAll()
        {
            return _vooruitgangen;
        }

        public IEnumerable<Vooruitgang> GetVooruitgangenBySessieNaam(string naam)
        {
            return _vooruitgangen.Where(v => v.Sessienaam == naam); 
        }

        public Vooruitgang GetBy(string groepId)
        {
            return _vooruitgangen.SingleOrDefault(v => v.GroepID == groepId);
        }

        public void saveChanges()
        {
            _dbContext.SaveChanges();
        }

        public void add(Vooruitgang vooruitgang)
        {
            _dbContext.Add(vooruitgang);
            _vooruitgangen.Add(vooruitgang);
            _dbContext.SaveChanges();
        }
        public void remove(Vooruitgang vooruitgang)
        {
            _vooruitgangen.Remove(vooruitgang);
            _dbContext.SaveChanges();
        }
        public void UpdateAantalPogingen(string groepId)
        {
            _vooruitgangen.SingleOrDefault(v => v.GroepID == groepId).AantalFoutePogingen++;
            _dbContext.SaveChanges();
        }
        public void UpdateFrozen(string groepId, Boolean aanuit)
        {
            if (aanuit)
            {
                _vooruitgangen.SingleOrDefault(v => v.GroepID == groepId).IsFrozen = 1;
                _dbContext.SaveChanges();
            } else
            {
                _vooruitgangen.SingleOrDefault(v => v.GroepID == groepId).IsFrozen = 1;
                _dbContext.SaveChanges();
            }


        }

        public void SetAantalPogingenToZero(string groepId)
        {
            var aantalpog = _vooruitgangen.SingleOrDefault(v => v.GroepID == groepId).AantalFoutePogingen;

            if (aantalpog < 3)
            {
                //set score
            }
            else
            {
                //set score (erger want user was gefrozen)
            }

            _vooruitgangen.SingleOrDefault(v => v.GroepID == groepId).AantalFoutePogingen = 0;
            //score verlagen
        }

        public void ChangeAllFrozenBySessieCode(string naam, Boolean freeze)
        {
            var _naam = naam;
            if (freeze)
            {
                _vooruitgangen.Where(s => s.Sessienaam == _naam).ToList().ForEach(s => s.IsFrozen = 1);
                _dbContext.SaveChanges();
            } else
            {
                _vooruitgangen.Where(s => s.Sessienaam == _naam).ToList().ForEach(s => s.IsFrozen = 0);
                _dbContext.SaveChanges();
            }
        }
    }

}
