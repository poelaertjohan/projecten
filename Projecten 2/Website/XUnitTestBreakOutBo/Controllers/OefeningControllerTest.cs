using BreakOutBox.Controllers;
using BreakOutBox.Models;
using BreakOutBox.Models.RepositoryInterfaces;
using BreakOutBox.Models.ViewModels;
using Microsoft.AspNetCore.Mvc;
using Moq;
using System;
using System.Collections.Generic;
using System.Text;
using Xunit;
using XUnitTestBreakOutBox.Data;

namespace XUnitTestBreakOutBox.Controllers
{
    public class OefeningControllerTest
    {
        private OefeningController _controller;
        private DummyApplicationDbContext _dummyContext;
        private Mock<IBoxRepository> _boxRepository;
        private Mock<IVooruitgangRepository> _vooruitgangRepository;
        private Mock<ISessieRepository> _sessieRepository;
        private Mock<IGroepRepository> _groepRepository;
        private Mock<IOefeningRepository> _oefeningRepository;
        private Mock<IMergeallRepository> _mergeAllRepository;
        private Mock<IGroepsBewerkingRepository> _groepsbewerkingRepository;
        private Mock<IActieRepository> _actieRepository;
        private Mock<IToegangscodeRepository> _toegangscodeRepository;



        public OefeningControllerTest()
        {
            _dummyContext = new DummyApplicationDbContext();
            _sessieRepository = new Mock<ISessieRepository>();
            _groepRepository = new Mock<IGroepRepository>();
            _vooruitgangRepository = new Mock<IVooruitgangRepository>();
            _boxRepository = new Mock<IBoxRepository>();
            _oefeningRepository = new Mock<IOefeningRepository>();
            _mergeAllRepository = new Mock<IMergeallRepository>();
            _groepsbewerkingRepository = new Mock<IGroepsBewerkingRepository>();
            _actieRepository = new Mock<IActieRepository>();
            _toegangscodeRepository = new Mock<IToegangscodeRepository>();
            _controller = new OefeningController(_oefeningRepository.Object, _boxRepository.Object, _vooruitgangRepository.Object, _sessieRepository.Object, _mergeAllRepository.Object, _groepsbewerkingRepository.Object, _actieRepository.Object, _toegangscodeRepository.Object);

        }

        #region -- Index GET --

        [Fact]
        public void Index_PassesNewOefeningIndexViewModel()
        {
            _mergeAllRepository.Setup(m => m.GetBy(_dummyContext.Groep1.Id)).Returns(_dummyContext.Mergeall);
            _oefeningRepository.Setup(m => m.GetBy(_dummyContext.Oefening1.Naam)).Returns(_dummyContext.Oefening1);
            _boxRepository.Setup(m => m.getOefeningenByBox(_dummyContext.Sessie1.BoxNaam)).Returns(_dummyContext.BoxOefNamen);
            _vooruitgangRepository.Setup(m => m.GetBy(_dummyContext.Groep1.Id)).Returns(_dummyContext.Vooruitgang1);
            _groepsbewerkingRepository.Setup(m => m.GetBy(_dummyContext.Mergeall1.GroepsbewerkingOpgave)).Returns(_dummyContext.Groepsbewerking1);
            var actionResult = _controller.Index(_dummyContext.Sessie1, _dummyContext.Groep1) as ViewResult;
            var oefeningEvm = actionResult?.Model as OefeningIndexViewModel;
            Assert.Equal(null, oefeningEvm?.Antwoord);
            Assert.Equal(_dummyContext.Oefening1.Opgave, oefeningEvm?.Opgave);
            Assert.Equal(_dummyContext.Oefening1.Naam, oefeningEvm?.Naam);
        }

        [Fact]
        public void Index_Frozen_RedirectsFrozenView()
        {
            _mergeAllRepository.Setup(m => m.GetBy(_dummyContext.Groep2.Id)).Returns(_dummyContext.Mergeall);
            _oefeningRepository.Setup(m => m.GetBy(_dummyContext.Oefening1.Naam)).Returns(_dummyContext.Oefening1);
            _boxRepository.Setup(m => m.getOefeningenByBox(_dummyContext.Sessie2.BoxNaam)).Returns(_dummyContext.BoxOefNamen);
            _vooruitgangRepository.Setup(m => m.GetBy(_dummyContext.Groep2.Id)).Returns(_dummyContext.Vooruitgang2);
            _groepsbewerkingRepository.Setup(m => m.GetBy(_dummyContext.Mergeall1.GroepsbewerkingOpgave)).Returns(_dummyContext.Groepsbewerking1);
            var actionResult = _controller.Index(_dummyContext.Sessie2, _dummyContext.Groep2) as RedirectToActionResult;
            Assert.Equal("FrozenView", actionResult?.ActionName);
        }
        #endregion

        #region -- Index POST --
        [Fact]
        public void Index_RedirectsToCorrectAntwoord()
        {
            _mergeAllRepository.Setup(m => m.GetBy(_dummyContext.Groep1.Id)).Returns(_dummyContext.Mergeall);
            _oefeningRepository.Setup(m => m.GetBy(_dummyContext.Oefening1.Naam)).Returns(_dummyContext.Oefening1);
            _boxRepository.Setup(m => m.getOefeningenByBox(_dummyContext.Sessie1.BoxNaam)).Returns(_dummyContext.BoxOefNamen);
            _vooruitgangRepository.Setup(m => m.GetBy(_dummyContext.Groep1.Id)).Returns(_dummyContext.Vooruitgang1);
            _groepsbewerkingRepository.Setup(m => m.GetBy(_dummyContext.Mergeall1.GroepsbewerkingOpgave)).Returns(_dummyContext.Groepsbewerking1);
            var oefeningEvm = new OefeningIndexViewModel()
            {
                Antwoord = "22"
            };
            var actionResult = _controller.Index(_dummyContext.Sessie1, oefeningEvm, _dummyContext.Groep1) as RedirectToActionResult;
            Assert.Equal("CorrectAntwoord", actionResult?.ActionName);
        }
       

        [Fact]
        public void Index_Frozen_RedirectsToFrozenView()
        {
            _mergeAllRepository.Setup(m => m.GetBy(_dummyContext.Groep2.Id)).Returns(_dummyContext.Mergeall);
            _oefeningRepository.Setup(m => m.GetBy(_dummyContext.Oefening1.Naam)).Returns(_dummyContext.Oefening1);
            _boxRepository.Setup(m => m.getOefeningenByBox(_dummyContext.Sessie1.BoxNaam)).Returns(_dummyContext.BoxOefNamen);
            _vooruitgangRepository.Setup(m => m.GetBy(_dummyContext.Groep2.Id)).Returns(_dummyContext.Vooruitgang2);
            _groepsbewerkingRepository.Setup(m => m.GetBy(_dummyContext.Mergeall1.GroepsbewerkingOpgave)).Returns(_dummyContext.Groepsbewerking1);
            var oefeningEvm = new OefeningIndexViewModel()
            {
                Antwoord = "22"
            };
            var actionResult = _controller.Index(_dummyContext.Sessie2, oefeningEvm, _dummyContext.Groep2) as RedirectToActionResult;
            Assert.Equal("FrozenView", actionResult?.ActionName);
        }
        #endregion

        #region --CorrectAntwoord GET --
        [Fact]
        public void CorrectAnwoord_PassesNewActieViewModel()
        {
            _mergeAllRepository.Setup(m => m.GetBy(_dummyContext.Groep1.Id)).Returns(_dummyContext.Mergeall);
            _boxRepository.Setup(m => m.GetActiesByBoxNaam(_dummyContext.Sessie1.BoxNaam)).Returns(_dummyContext.BoxActiesNamen);
            _vooruitgangRepository.Setup(m => m.GetBy(_dummyContext.Groep1.Id)).Returns(_dummyContext.Vooruitgang3);
            _actieRepository.Setup(m => m.GetBy(_dummyContext.Actie1.actie)).Returns(_dummyContext.Actie1);
            var actionResult = _controller.CorrectAntwoord(_dummyContext.Sessie1, _dummyContext.Groep1) as ViewResult;
            var actieEvm = actionResult?.Model as ActieViewModel;
            Assert.Equal(_dummyContext.Actie1.actie, actieEvm?.Naam);
        }

        [Fact]
        public void CorrectAnwoord_Afstandsonderwijs_RedirectsToIndex()
        {
            _mergeAllRepository.Setup(m => m.GetBy(_dummyContext.Groep1.Id)).Returns(_dummyContext.Mergeall);
            _boxRepository.Setup(m => m.GetActiesByBoxNaam(_dummyContext.Sessie3.BoxNaam)).Returns(_dummyContext.BoxActiesNamen);
            _vooruitgangRepository.Setup(m => m.GetBy(_dummyContext.Groep1.Id)).Returns(_dummyContext.Vooruitgang3);
            _actieRepository.Setup(m => m.GetBy(_dummyContext.Actie1.actie)).Returns(_dummyContext.Actie1);
            var actionResult = _controller.CorrectAntwoord(_dummyContext.Sessie3, _dummyContext.Groep1) as RedirectToActionResult;
            Assert.Equal("Index", actionResult?.ActionName);

        }

        [Fact]
        public void CorrectAnwoord_SessieAfgelopen_RedirectsToSessieAfelopen()
        {
            _mergeAllRepository.Setup(m => m.GetBy(_dummyContext.Groep1.Id)).Returns(_dummyContext.Mergeall);
            _boxRepository.Setup(m => m.GetActiesByBoxNaam(_dummyContext.Sessie3.BoxNaam)).Returns(_dummyContext.BoxActiesNamen);
            _vooruitgangRepository.Setup(m => m.GetBy(_dummyContext.Groep1.Id)).Returns(_dummyContext.Vooruitgang4);
            _actieRepository.Setup(m => m.GetBy(_dummyContext.Actie1.actie)).Returns(_dummyContext.Actie1);
            var actionResult = _controller.CorrectAntwoord(_dummyContext.Sessie1, _dummyContext.Groep1) as RedirectToActionResult;
            Assert.Equal("SessieAfgelopen", actionResult?.ActionName);

        }
        #endregion
    }
}
