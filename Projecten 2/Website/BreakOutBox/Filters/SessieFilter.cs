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


    public class SessieFilter : ActionFilterAttribute
    {
        private Sessie _sessie;
        private readonly ISessieRepository _sessieRepository;

        public SessieFilter(ISessieRepository sessieRepository)
        {
            _sessieRepository = sessieRepository;
        }

        public override void OnActionExecuting(ActionExecutingContext context)
        {
            _sessie = ReadSessieFromSession(context.HttpContext);
            context.ActionArguments["sessie"] = _sessie;
            base.OnActionExecuting(context);
        }

        public override void OnActionExecuted(ActionExecutedContext context)
        {
            WriteSessieToSession(_sessie, context.HttpContext);
            base.OnActionExecuted(context);
        }

        private Sessie ReadSessieFromSession(HttpContext context)
        {
            Sessie sessie = context.Session.GetString("sessie") == null ?
                new Sessie() : JsonConvert.DeserializeObject<Sessie>(context.Session.GetString("sessie"));
            return sessie;
        }

        private void WriteSessieToSession(Sessie Sessie, HttpContext context)
        {
            context.Session.SetString("sessie", JsonConvert.SerializeObject(Sessie));
        }
    }
}
