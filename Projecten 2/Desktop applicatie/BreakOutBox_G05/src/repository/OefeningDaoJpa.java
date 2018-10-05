/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Oefening;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

/**
 *
 * @author Johan
 */
public class OefeningDaoJpa extends GenericDaoJpa<Oefening> implements OefeningDao
{

    public OefeningDaoJpa()
    {
        super(Oefening.class);
    }

    @Override
    public Oefening getOefeningByName(String name) throws EntityNotFoundException
    {
        try
        {
            return em.createNamedQuery("Oefening.findByName", Oefening.class)
                    .setParameter("oefeningNaam", name)
                    .getSingleResult();
        } catch (NoResultException ex)
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<String> getAlleOefeningenStringFormat(List<Oefening> oefeningen)
    {
        return oefeningen.stream().map(e -> e.toString()).collect(Collectors.toList());
    }
}
