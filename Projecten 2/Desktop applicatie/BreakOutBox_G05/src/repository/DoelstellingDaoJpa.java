/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Doelstelling;

/**
 *
 * @author Vital Verleyen
 */
public class DoelstellingDaoJpa extends GenericDaoJpa<Doelstelling> implements DoelstellingDao{
    
    public DoelstellingDaoJpa() {
        super(Doelstelling.class);
    }
    
}
