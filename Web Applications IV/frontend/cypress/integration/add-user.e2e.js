describe('add movie', function() {

  
    it('should be possible to add a user', function() {
  
      cy.visit('http://localhost:4200/add-movie');
  
      cy.get('[data-test=register]').click();
      cy.get('[data-test=username]').type('testuser');
      cy.get('[data-test=password]').type('testusertestuser');
      cy.get('[data-test=confirmPassword]').type('testusertestuser');
      
      cy.get('[data-test=register]').click();
  
      cy.visit('http://localhost:4200/add-movie');

      cy.contains('Title');
    });
  }); 