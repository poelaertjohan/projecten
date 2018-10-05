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
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

/**
 *
 * @author JorisLaptop
 */
public interface MergeDao extends GenericDao<MergeAll>
{

    public List<String> getAlleMergAll(List<MergeAll> MergeAlls);

    public List<MergeAll> getMergeBySessieNaam(String name) throws EntityNotFoundException;

    public List<MergeAll> removeMergesOpSessieNaam(String naam) throws EntityNotFoundException;
}
