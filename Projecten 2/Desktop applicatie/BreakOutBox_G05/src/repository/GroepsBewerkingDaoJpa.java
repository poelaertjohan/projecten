/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.GroepsBewerking;
import domein.Oefening;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import static repository.GenericDaoJpa.em;

/**
 *
 * @author JorisLaptop
 */
public class GroepsBewerkingDaoJpa extends GenericDaoJpa<GroepsBewerking> implements GroepsBewerkingDao
{

    public GroepsBewerkingDaoJpa()
    {
        super(GroepsBewerking.class);
    }

    @Override
    public GroepsBewerking getBewerkingByName(String name) throws EntityNotFoundException
    {
        try
        {
            return em.createNamedQuery("GroepsBewerking.findByName", GroepsBewerking.class)
                    .setParameter("oefeningNaam", name)
                    .getSingleResult();
        } catch (NoResultException ex)
        {
            throw new EntityNotFoundException();
        }
    }

    /*@Override
    public GroepsBewerkingDao getBewerkingByName(String name) throws EntityNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

}