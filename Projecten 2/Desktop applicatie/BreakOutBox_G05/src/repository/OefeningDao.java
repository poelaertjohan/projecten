/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Oefening;
import java.util.List;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Johan
 */
public interface OefeningDao extends GenericDao<Oefening>
{
    public Oefening getOefeningByName(String name) throws EntityNotFoundException;
    public List<String> getAlleOefeningenStringFormat(List<Oefening> oefeningen);
}
