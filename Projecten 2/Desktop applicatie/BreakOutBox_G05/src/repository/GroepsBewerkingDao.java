/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.GroepsBewerking;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author JorisLaptop
 */
public interface GroepsBewerkingDao extends GenericDao<GroepsBewerking> {
    
    public GroepsBewerking getBewerkingByName(String name) throws EntityNotFoundException;
    //public List<String> getAlleOefeningenStringFormat(List<Oefening> oefeningen);
}
