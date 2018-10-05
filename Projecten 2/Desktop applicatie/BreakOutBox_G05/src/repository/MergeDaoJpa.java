/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.GroepsBewerking;
import domein.MergeAll;
import domein.Oefening;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import static repository.GenericDaoJpa.em;

/**
 *
 * @author JorisLaptop
 */
public class MergeDaoJpa extends GenericDaoJpa<MergeAll> implements MergeDao
{

    public MergeDaoJpa()
    {
        super(MergeAll.class);
    }

    @Override
    public List<String> getAlleMergAll(List<MergeAll> MergeAlls)
    {
        return MergeAlls.stream().map(e -> e.toString()).collect(Collectors.toList());
    }

    @Override
    public List<MergeAll> getMergeBySessieNaam(String name) throws EntityNotFoundException
    {
        try
        {
            return em.createNamedQuery("domein.MergeAll.findBySessie", MergeAll.class)
                    .setParameter("sessieNaam", name).getResultList();
        } catch (NoResultException ex)
        {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public List<MergeAll> removeMergesOpSessieNaam(String naam) throws EntityNotFoundException
    {
        try
        {
            return em.createNamedQuery("domein.MergeAll.deleteBySessie", MergeAll.class).setParameter("sessieNaam", naam).getResultList();
        } catch (NoResultException ex)
        {
            throw new EntityNotFoundException();
        }
    }
}
