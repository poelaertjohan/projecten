using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using BreakOutBox.Models;
using BreakOutBox.Models.ViewModels;
using BreakOutBox.Filters;
using System.Collections.Generic;
using BreakOutBox.Data.Repositories;
using System.Linq;
using Microsoft.AspNetCore.Routing;
using Microsoft.AspNetCore.Mvc.Filters;
using System.Threading.Tasks;
using dotnet_g05.Data;
using System;
using System.Text.RegularExpressions;

namespace BreakOutBox.Controllers
{
    [ServiceFilter(typeof(SessieFilter))]
    [ServiceFilter(typeof(GroepFilter))]
    public class SessieController : Controller
    {
        private readonly ISessieRepository _sessieRepository;
        private readonly IGroepRepository _groepRepository;
        private readonly IVooruitgangRepository _vooruitgangRepository;
        private readonly IBoxRepository _boxRepository;
        private IEnumerable<Sessie> sessies;
        private IEnumerable<Groep> groepen;
        private string sessieCode;

        public SessieController(ISessieRepository sessieRepository, IGroepRepository groepRepository, IVooruitgangRepository vooruitgangRepository, IBoxRepository boxRepository)
        {
            this._groepRepository = groepRepository;
            this._sessieRepository = sessieRepository;
            this._vooruitgangRepository = vooruitgangRepository;
            this._boxRepository = boxRepository;
            this.sessies = sessieRepository.GetAll().ToList();
            this.groepen = groepRepository.GetAll().ToList();
            
            vooruitgangRepository.saveChanges();

        }
        
        public ActionResult Index()
        {
            SessieIndexViewModel sessieModel = new SessieIndexViewModel();
            return View(sessieModel);
        }

        [HttpPost]
        public ActionResult Index(Sessie sessie, SessieIndexViewModel sessieIndexViewModel)
        {
            this.sessieCode = sessieIndexViewModel.Code;
            var sessieObj = _sessieRepository.GetBy(sessieCode);
            
            string[] beschikbaarVanaf = Regex.Split(sessieObj.Beschikbaarvanaf, " ");
            
            string beschikbaar;
            beschikbaar = beschikbaarVanaf[0] + "/";
            if(beschikbaarVanaf[1].Length == 1)
            {
                beschikbaar += 0;
            }
            beschikbaar += beschikbaarVanaf[1] + "/" + beschikbaarVanaf[2];

            DateTime datum = DateTime.ParseExact(beschikbaar, "dd/MM/yyyy", null);

            if (sessieObj != null && (datum <= DateTime.Now)) 
            {
                
                sessie.Omschrijving = sessieObj.Omschrijving;
                sessie.Afstandsonderwijs = sessieObj.Afstandsonderwijs;
                sessie.Beschikbaarvanaf = sessieObj.Beschikbaarvanaf;
                sessie.BoxNaam = sessieObj.BoxNaam;
                sessie.BoxNaamNavigation = sessieObj.BoxNaamNavigation;
                sessie.Code = sessieObj.Code;
                sessie.Naam = sessieObj.Naam;
                sessie.SessieGroep = sessieObj.SessieGroep;

                return RedirectToAction(nameof(SelectGroup));
                    
            }
            return RedirectToAction(nameof(NietBeschikbaar));
        }

        public ActionResult NietBeschikbaar()
        {
            return View();
        }

        public ActionResult SelectGroup(Sessie sessie)
        {
            var groepen = _sessieRepository.GetGroepenBySessie(sessie.Naam);
            return View(groepen);
        }

        [ServiceFilter(typeof(GroepFilter))]
        public ActionResult StartOefeningen(Sessie sessie, Groep groep, string id)
        {

            string groepid = id;
            System.Diagnostics.Debug.WriteLine(id);
            var groepObj = _groepRepository.GetBy(id);

            groep.Groepnummer = groepObj.Groepnummer;
            groep.GroepOef = groepObj.GroepOef;
            groep.Id = groepObj.Id;
            groep.Leerlingen = groepObj.Leerlingen;
            groep.Mergeall = groepObj.Mergeall;
            groep.SessieGroep = groepObj.SessieGroep;

            var vooruitgang = _vooruitgangRepository.GetBy(id);
            if (vooruitgang != null)
            {
                if (vooruitgang.IsFrozen == 1)
                {
                    return RedirectToAction("FrozenView", "Oefening");
                }

                int aantaloefeningen = _boxRepository.getOefeningenByBox(sessie.BoxNaam).Count();

                string iddienaaroefcontrollerwordtgestuurd = groep.Id;

                if (vooruitgang.OefNummer == aantaloefeningen)
                {

                    return RedirectToAction(nameof(Errorweergave));
                }
            }
            else
            {
                Vooruitgang v = new Vooruitgang(sessie.Naam, groep.Id, 0, 0, 1, 0);
                _vooruitgangRepository.add(v);
                _vooruitgangRepository.saveChanges();

                if (v.IsFrozen == 1)
                {
                    return RedirectToAction("FrozenView", "Oefening");
                }
            } 
                    return RedirectToAction("Index", "Oefening");
        }

        public ActionResult Errorweergave()
        {
            return View();
        }
    }
}