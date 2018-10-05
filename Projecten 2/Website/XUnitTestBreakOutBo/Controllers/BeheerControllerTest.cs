using BreakOutBox.Controllers;
using BreakOutBox.Models;
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
    public class BeheerControllerTest
    {
        private BeheerController _controller;
        private DummyApplicationDbContext _dummyContext;
        private Mock<IBoxRepository> _boxRepository;
        private Mock<IVooruitgangRepository> _vooruitgangRepository;
        private Mock<ISessieRepository> _sessieRepository;


        public BeheerControllerTest()
        {
            _dummyContext = new DummyApplicationDbContext();
            _sessieRepository = new Mock<ISessieRepository>();
            _vooruitgangRepository = new Mock<IVooruitgangRepository>();
            _boxRepository = new Mock<IBoxRepository>();
            _controller = new BeheerController(_sessieRepository.Object, _vooruitgangRepository.Object, _boxRepository.Object);

        }

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

        #region -- Index GET --
        [Fact]
        public void Index_RedirectsToGroepsoverzicht()
        {
            _sessieRepository.Setup(m => m.getByCode("123")).Returns(_dummyContext.Sessie1);
            var sessieEvm = new SessieIndexViewModel()
            {
                Code = "123"
            };
            var actionResult = _controller.Index(_dummyContext.Sessie1, "123") as RedirectToActionResult;
            Assert.Equal("Groepsoverzicht", actionResult?.ActionName);
        }

        #endregion

        #region -- Groepsoverzicht POST --

        [Fact]
        public void Groepsoverzicht_RedirectsToGroepsoverzicht()
        {
            _vooruitgangRepository.Setup(m => m.GetBy("1")).Returns(_dummyContext.Vooruitgang1);
            var actionResult = _controller.Groepsoverzicht("1") as RedirectToActionResult;
            Assert.Equal("Groepsoverzicht", actionResult?.ActionName);
        }

        #endregion

    }
}
