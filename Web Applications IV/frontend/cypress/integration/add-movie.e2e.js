describe('add movie', function() {


  it('should be possible to add a movie', function() {

    cy.visit('http://localhost:4200/add-movie');

    cy.get('[data-test=loginUsername]').type('johanjohan');
    cy.get('[data-test=loginPassword]').type('johanjohanjohan');

    cy.get('[data-test=loginSubmit]').click();

    cy.get('[data-test=movieTitle]').type('FilmJohan');
    cy.get('[data-test=movieRated]').type('R');
    cy.get('[data-test=movieReleased]').type('2018-01-06');
    cy.get('[data-test=movieRuntime]').type('22');
    cy.get('[data-test=movieGenres]').type('Drama');
    cy.get('[data-test=movieDirector]').type('Johan Poelaert');
    cy.get('[data-test=movieActors]').type('Johan Poelaert, Kevin Desmet');
    cy.get('[data-test=movieReview]').type('Het is een goede film. Het is een goede film. Het is een goede film. Het is een goede film.');
    cy.get('[data-test=moviePlot]').type('Johan maakt een siteJohan maakt een siteJohan maakt een site');
    cy.get('[data-test=movieLanguages]').type('Nederlands');
    cy.get('[data-test=movieCountry]').type('Belgie');
    cy.get('[data-test=movieAwards]').type('Oscar');
    cy.get('[data-test=moviePoster]').type('linkNaarPoster');
    cy.get('[data-test=movieRating]').type('10');
    cy.get('[data-test=movieImdbId]').type('tt1234');
    cy.get('[data-test=movieType]').type('movie');
    cy.get('[data-test=movieSubmit]').click();

    cy.visit('http://localhost:4200/movie-list');

    cy.contains('FilmJohan');
  });
}); 