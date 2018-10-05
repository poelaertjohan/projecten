using BreakOutBox.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc.Filters;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace BreakOutBox.Filters
{
    public class GroepFilter : ActionFilterAttribute
    {
        private Groep _groep;
        private readonly IGroepRepository _groepRepository;

        public GroepFilter(IGroepRepository groepRepository)
        {
            _groepRepository = groepRepository;
        }

        public override void OnActionExecuting(ActionExecutingContext context)
        {
            _groep = ReadGroepFromSession(context.HttpContext);
            context.ActionArguments["groep"] = _groep;
            base.OnActionExecuting(context);
        }

        public override void OnActionExecuted(ActionExecutedContext context)
        {
            WriteGroepToSession(_groep, context.HttpContext);
            base.OnActionExecuted(context);
        }

        private Groep ReadGroepFromSession(HttpContext context)
        {
            Groep groep = context.Session.GetString("groep") == null ?
                new Groep() : JsonConvert.DeserializeObject<Groep>(context.Session.GetString("groep"));
            return groep;
        }

        private void WriteGroepToSession(Groep groep, HttpContext context)
        {
            context.Session.SetString("groep", JsonConvert.SerializeObject(groep));
        }
    }
}
