/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Groep;

/**
 *
 * @author JorisLaptop
 */
public class GroepDaoJpa extends GenericDaoJpa<Groep> implements GroepDao {

    public GroepDaoJpa() {
        super(Groep.class);
    }

}
