using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using BreakOutBox.Controllers;
using BreakOutBox.Models;
using BreakOutBox.Models.RepositoryInterfaces;
using BreakOutBox.Models.ViewModels;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.AspNetCore.Mvc.ViewFeatures;
using Moq;
using Xunit;
using XUnitTestBreakOutBox.Data;

namespace XUnitTestBreakOutBox.Controllers
{
    public class SessieControllerTest
    {
        private SessieController _controller;
        private DummyApplicationDbContext _dummyContext;
        private Mock<IBoxRepository> _boxRepository;
        private Mock<IVooruitgangRepository> _vooruitgangRepository;
        private Mock<ISessieRepository> _sessieRepository;
        private Mock<IGroepRepository> _groepRepository;


        public SessieControllerTest()
        {
            _dummyContext = new DummyApplicationDbContext();
            _sessieRepository = new Mock<ISessieRepository>();
            _groepRepository = new Mock<IGroepRepository>();
            _vooruitgangRepository = new Mock<IVooruitgangRepository>();
            _boxRepository = new Mock<IBoxRepository>();
            _controller = new SessieController(_sessieRepository.Object, _groepRepository.Object, _vooruitgangRepository.Object, _boxRepository.Object);

        }
        #region -- SelectGroup --
        [Fact]
        public void SelectGroup_ReturnGroepenVanSessie()
        {
            var ses = _dummyContext.Sessie1;
            _sessieRepository.Setup(m => m.GetGroepenBySessie(ses.Naam)).Returns(_dummyContext.Groep);
            ViewResult result = _controller.SelectGroup(ses) as ViewResult;
            IEnumerable<Groep> Groepen = result?.Model as IEnumerable<Groep>;
            Assert.Equal(2, Groepen.Count());
        }
        #endregion

        #region -- Index GET --

        [Fact]
        public void Index_PassesNewSessieIndexViewModel()
        {

            var actionResult = _controller.Index() as ViewResult;
            var sessieEvm = actionResult?.Model as SessieIndexViewModel;
            Assert.Equal(null, sessieEvm?.Naam);
            Assert.Equal(null, sessieEvm?.Code);
        }
        #endregion

        #region -- Index POST --
        [Fact]
        public void Index_RedirectsToActionIndex()
        {
            _sessieRepository.Setup(m => m.GetBy("123")).Returns(_dummyContext.Sessie1);
            var sessieEvm = new SessieIndexViewModel()
            {
                Code = "123"
            };
            var actionResult = _controller.Index(_dummyContext.Sessie1, sessieEvm) as RedirectToActionResult;
            Assert.Equal("SelectGroup", actionResult?.ActionName);
        }

        [Fact]
        public void Index_NietBeschikbaar_RedirectsToNietBeschikbaar()
        {
            _sessieRepository.Setup(m => m.GetBy("321")).Returns(_dummyContext.Sessie2);
            var sessieEvm = new SessieIndexViewModel()
            {
                Code = "321"
            };
            var actionResult = _controller.Index(_dummyContext.Sessie2, sessieEvm) as RedirectToActionResult;
            Assert.Equal("NietBeschikbaar", actionResult?.ActionName);
        }
        #endregion

        #region-- StartOefeningen --
        [Fact]
        public void StartOefeningen_Frozen_RedirectsToFrozenView()
        {
            _groepRepository.Setup(m => m.GetBy("15")).Returns(_dummyContext.Groep2);
            _vooruitgangRepository.Setup(m => m.GetBy("13")).Returns(_dummyContext.Vooruitgang2);
            var actionResult = _controller.StartOefeningen(_dummyContext.Sessie1, _dummyContext.Groep2, "15") as RedirectToActionResult;
            Assert.Equal("FrozenView", actionResult?.ActionName);
        }
        [Fact]
        public void StartOefeningen_RedirectsToIndex()
        {
            _groepRepository.Setup(m => m.GetBy("15")).Returns(_dummyContext.Groep1);
            _vooruitgangRepository.Setup(m => m.GetBy("13")).Returns(_dummyContext.Vooruitgang1);
            var actionResult = _controller.StartOefeningen(_dummyContext.Sessie1, _dummyContext.Groep2, "15") as RedirectToActionResult;
            Assert.Equal("FrozenView", actionResult?.ActionName);
        }
        #endregion
    }
}
