using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Models.RepositoryInterfaces
{
    public interface IMergeallRepository
    {

        IEnumerable<Mergeall> GetBy(string groepId);
        void SaveChanges();
    }
}
