using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using BreakOutBox.Models;
using Microsoft.AspNetCore.Mvc;
using BreakOutBox.Filters;
using BreakOutBox.Models.ViewModels;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Authorization;

namespace BreakOutBox.Controllers
{
    [Authorize]
    [ServiceFilter(typeof(SessieFilter))]
    public class BeheerController : Controller
    {
        public string sessieCode { get; set; }
        private readonly ISessieRepository sessieRepository;
        private readonly IVooruitgangRepository vooruitgangRepository;
        private readonly IBoxRepository boxRepository;


        public BeheerController(ISessieRepository sessieRepository, IVooruitgangRepository vooruitgangRepository, IBoxRepository boxRepository)
        {
            this.sessieRepository = sessieRepository;
            this.vooruitgangRepository = vooruitgangRepository;
            this.boxRepository = boxRepository;
        }

        public ActionResult Index()
        {
            IEnumerable<Sessie> _sessies = this.sessieRepository.GetAll();
            return View(_sessies);
        }

        [HttpPost]
        public ActionResult Index(Sessie sessie, string sessiecode)
        {
            Sessie sessieObj = this.sessieRepository.getByCode(sessiecode);
            if (sessieObj != null)
            {
                sessie.Omschrijving = sessieObj.Omschrijving;
                sessie.Afstandsonderwijs = sessieObj.Afstandsonderwijs;
                sessie.Beschikbaarvanaf = sessieObj.Beschikbaarvanaf;
                sessie.BoxNaam = sessieObj.BoxNaam;
                sessie.BoxNaamNavigation = sessieObj.BoxNaamNavigation;
                sessie.Code = sessieObj.Code;
                sessie.Naam = sessieObj.Naam;
                sessie.SessieGroep = sessieObj.SessieGroep;
                
                return RedirectToAction(nameof(Groepsoverzicht));
            }
            return RedirectToAction(nameof(Index));

        }

        public ActionResult Groepsoverzicht(Sessie sessie)
        {
            var aantalOefeningen = boxRepository.getOefeningenByBox(sessie.BoxNaam).Count();
            TempData["aantalOefeningen"] = aantalOefeningen;
            IEnumerable<Vooruitgang> vooruitgangen = this.vooruitgangRepository.GetVooruitgangenBySessieNaam(sessie.Naam);
            return View(vooruitgangen);
        }

        [HttpPost]
        public ActionResult Groepsoverzicht(string id)
        {
            var vooruit = vooruitgangRepository.GetBy(id);
            var sessienaam = vooruit.Sessienaam;
            var oefnummer = vooruit.OefNummer;
            var score = vooruit.Score;
            var frozen = vooruit.IsFrozen;
            var aantalfoutepog = 0;

            vooruitgangRepository.remove(vooruit);
            vooruitgangRepository.add(new Vooruitgang(sessienaam, id, oefnummer, score, (frozen+3)%2, aantalfoutepog));

            return RedirectToAction(nameof(Groepsoverzicht));
        }

        public ActionResult Refresh()
        {
            return RedirectToAction(nameof(Groepsoverzicht));
        }

        public ActionResult ChangeAllFreezeStatus(string sessiecode, Boolean freeze)
        {
            String sessienaam = this.sessieRepository.GetSessieNaamByCode(sessiecode);
            this.vooruitgangRepository.ChangeAllFrozenBySessieCode(sessienaam, freeze);
            return RedirectToAction(nameof(Index));
        }
    }
}