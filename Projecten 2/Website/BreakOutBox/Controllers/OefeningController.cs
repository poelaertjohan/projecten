using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using dotnet_g05.Data;
using BreakOutBox.Models;
using BreakOutBox.Models.ViewModels;
using BreakOutBox.Filters;
using BreakOutBox.Models.RepositoryInterfaces;

namespace BreakOutBox.Controllers
{

    [ServiceFilter(typeof(SessieFilter))]
    [ServiceFilter(typeof(GroepFilter))]
    public class OefeningController : Controller
    {
        private readonly IOefeningRepository _oefeningRepository;
        private readonly IBoxRepository _boxRepository;
        private readonly IVooruitgangRepository _vooruitgangRepository;
        private readonly ISessieRepository _sessieRepository;
        private readonly IMergeallRepository _mergeallRepository;
        private readonly IGroepsBewerkingRepository _groepsbewerkingRepository;
        private readonly IActieRepository _actieRepository;
        private readonly IToegangscodeRepository _toegangscodeRepository;
        private Oefening _oefening;
        private IEnumerable<Mergeall> _mergealls;


        public OefeningController(IOefeningRepository oefeningRepository, IBoxRepository boxRepository, IVooruitgangRepository vooruitgangRepository, ISessieRepository sessieRepository, IMergeallRepository mergeallRepository, IGroepsBewerkingRepository groepsbewerkingRepository, IActieRepository actieRepository, IToegangscodeRepository toegangscodeRepository)
        {
            _boxRepository = boxRepository;
            _oefeningRepository = oefeningRepository;
            _vooruitgangRepository = vooruitgangRepository;
            _sessieRepository = sessieRepository;
            _mergeallRepository = mergeallRepository;
            _groepsbewerkingRepository = groepsbewerkingRepository;
            _actieRepository = actieRepository;
            _toegangscodeRepository = toegangscodeRepository;
        }

        public ActionResult Index(Sessie sessie, Groep groep)
        {

            int welkeOef = ControleerVooruitgang(groep.Id);

            if (welkeOef == 0)
            {
                if (_vooruitgangRepository.GetBy(groep.Id) == null)
                {
                    //maak nieuwe vooruitgang want bestaat nog niet
                    Vooruitgang v = new Vooruitgang(sessie.Naam, groep.Id, 0, 0, 1, 0);
                    _vooruitgangRepository.add(v);
                    _vooruitgangRepository.saveChanges();
                }
            }

            //kijk of frozen indien ja ==> frozenview!!
             if (_vooruitgangRepository.GetBy(groep.Id).IsFrozen == 1)
             {
                 return RedirectToAction(nameof(FrozenView));
             }

            //toon oefeningen

            _mergealls = _mergeallRepository.GetBy(groep.Id); //geef alle mergalls van die groep
            _oefening = _oefeningRepository.GetBy(_mergealls.ElementAt(welkeOef).OefNaam); //neem oef object
            int aantalOefeningen = _boxRepository.getOefeningenByBox(sessie.BoxNaam).Count();
            //_oefening in try is out of range ==> alle oef zijn gedaan! toon uitkomst scherm!

            var vr = _vooruitgangRepository.GetBy(groep.Id);

            float progress = (vr.OefNummer * 1.0f / aantalOefeningen) * 100;
            if (progress == 0)
            {
                progress = 10;
            }
            Groepsbewerking groepsbewerkingObject = _groepsbewerkingRepository.GetBy(_mergealls.ElementAt(welkeOef).GroepsbewerkingOpgave);
            OefeningIndexViewModel oefeningIndexModel = new OefeningIndexViewModel(_oefening, groepsbewerkingObject, progress, groep.Id);

            return View(oefeningIndexModel);

        }

        [HttpPost]
        public ActionResult Index(Sessie sessie, OefeningIndexViewModel oefeningIndexModel, Groep groep)
        {
            int welkeOef = _vooruitgangRepository.GetBy(groep.Id).OefNummer;
            _mergealls = _mergeallRepository.GetBy(groep.Id);
            _oefening = _oefeningRepository.GetBy(_mergealls.ElementAt(welkeOef).OefNaam);
            Vooruitgang vooruitgang = _vooruitgangRepository.GetBy(groep.Id);
            Groepsbewerking groepsbewerkingObject = _groepsbewerkingRepository.GetBy(_mergealls.ElementAt(welkeOef).GroepsbewerkingOpgave);
            Boolean isvalidtimeleft = true;

            //timeleft berekenen
            if (_oefening.Timeleft != 0)
            {
                var dateStart = oefeningIndexModel.DateStart;
                var dateEnd = DateTime.Now;
                var verschilinDatum = dateEnd - dateStart;

                //int timeleft omzetten naar ticks:
                var timeleftTicks = new TimeSpan(0, (int)_oefening.Timeleft, 0);

                if (verschilinDatum.Ticks > timeleftTicks.Ticks)
                {
                    isvalidtimeleft = false;
                }
            }
            if (vooruitgang.IsFrozen == 0)
            {

                String antwoord = oefeningIndexModel.Antwoord;
                String realAntwoord = AlterAntwoord(_oefening.Antwoord, groepsbewerkingObject);
                if (antwoord == realAntwoord && isvalidtimeleft) //Juiste antwoord en binnen de tijd!!
                {
                    Vooruitgang vooruit = _vooruitgangRepository.GetBy(groep.Id);
                    int welkeOefening = _vooruitgangRepository.GetBy(groep.Id).OefNummer;
                    int aantalpogingen = _vooruitgangRepository.GetBy(groep.Id).AantalFoutePogingen;
                    
                    _vooruitgangRepository.remove(_vooruitgangRepository.GetBy(groep.Id));
                    
                    Vooruitgang nieuweVooruit = new Vooruitgang(sessie.Naam, groep.Id, welkeOefening + 1, welkeOefening + 1, 0, aantalpogingen);
                    _vooruitgangRepository.add(nieuweVooruit);

                   
                    return RedirectToAction(nameof(CorrectAntwoord));

                }
                else if (isvalidtimeleft == false) //buiten de tijd
                {
                    _vooruitgangRepository.UpdateAantalPogingen(groep.Id);
                    _vooruitgangRepository.UpdateFrozen(groep.Id, true);


                    return RedirectToAction(nameof(FrozenViewTime));


                }
                else //fout antwoord!
                {
                  
                    TempData["feedback"] = "Oeps, dat is verkeerd. De feedback voor deze oefening is: " +  _oefening.Feedback;
                    _vooruitgangRepository.UpdateAantalPogingen(groep.Id);

                    if(_vooruitgangRepository.GetBy(groep.Id).AantalFoutePogingen >= 3)
                    {
                        _vooruitgangRepository.UpdateFrozen(groep.Id, true);
                        return RedirectToAction(nameof(FrozenView));

                    }
                    return RedirectToAction(nameof(Index));
                }
            }
            else
            {
                return RedirectToAction(nameof(FrozenView));
            }
        }

        public String AlterAntwoord(String antwoord, Groepsbewerking groepsBewerking)
        {
            var antwoordInt = Int32.Parse(antwoord);
            var Operator = groepsBewerking.Operator;
            var getal = Int32.Parse(groepsBewerking.Getal);
            if (Operator == "+")
            {
                return (antwoordInt + getal).ToString();
            }
            if (Operator == "-")
            {
                return (antwoordInt - getal).ToString();
            }
            if (Operator == "/" || Operator == "%")
            {
                return (antwoordInt / getal).ToString();
            }
            if (Operator == "*" || Operator == "x" || Operator == ".")
            {
                return (antwoordInt * getal).ToString();
            }
            return null;
        }

        public ActionResult FrozenView()
        {
            return View();
        }
        public ActionResult FrozenViewTime()
        {
            return View();
        }

        public ActionResult CorrectAntwoord(Sessie sessie, Groep groep)
        {
            int welkeActie = ControleerVooruitgang(groep.Id);
            _mergealls = _mergeallRepository.GetBy(groep.Id);
            var actieNamen = _boxRepository.GetActiesByBoxNaam(sessie.BoxNaam);

            if (welkeActie > actieNamen.Count())
            {
                return RedirectToAction(nameof(SessieAfgelopen));
            }
            if (sessie.Afstandsonderwijs.Value)
            {
                return RedirectToAction(nameof(Index));
            }
            var actieNaam = actieNamen.ElementAt(welkeActie - 1);
            var actie = _actieRepository.GetBy(actieNaam);
            ActieViewModel actieViewModel = new ActieViewModel(actie);
            return View(actieViewModel);
        }

        public ActionResult SessieAfgelopen(Groep groep)
        {
            var score = _vooruitgangRepository.GetBy(groep.Id).Score;
            var totAantalOef = _vooruitgangRepository.GetBy(groep.Id).OefNummer;
            var huidigeSessieNaam = _vooruitgangRepository.GetBy(groep.Id).Sessienaam;
            
            List<Vooruitgang> alleVooruitgangVanSessie = new List<Vooruitgang>();

            foreach(Vooruitgang v in _vooruitgangRepository.GetAll())
            {
                if(v.Sessienaam == huidigeSessieNaam)
                {
                    alleVooruitgangVanSessie.Add(v);
                }
            }

            int aantal = alleVooruitgangVanSessie.Count(v => v.OefNummer == totAantalOef);

            if(aantal == 1)
            {
                ViewBag.score = "Proficiat! Je hebt de schatkist gevonden!";
            }
            else
            {
                ViewBag.score = "Proficiat, je hebt alle oefeningen opgelost! Iemand is al met de schatkist gaan lopen.";
            }

            return View();
        }

        [HttpPost]
        public ActionResult CorrectAntwoord(Groep groep, ActieViewModel actieViewModel)
        {
            int welkeToegangscode = ControleerVooruitgang(groep.Id);

            var toegangscodeInput = actieViewModel.ToegangsCode;

            var mergeallToegangscode = _mergeallRepository.GetBy(groep.Id);
            Mergeall m = mergeallToegangscode.ElementAt(welkeToegangscode - 1);
            string mergeallActieCode = m.Actiecode.ToString();
            if (toegangscodeInput == mergeallActieCode)
            {
                return RedirectToAction(nameof(Index));

            }
            return RedirectToAction(nameof(CorrectAntwoord));
        }

        private int ControleerVooruitgang(string id)
        {
            int welkeOef;
            var vooruitgang = _vooruitgangRepository.GetBy(id);
            if (vooruitgang != null)
            {
                welkeOef = vooruitgang.OefNummer;
            }
            else
            {
                welkeOef = 0;
            }
            return welkeOef;
        }
    }


}