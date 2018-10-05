package repository;

import domein.Sessie;


//Code Written by Fréderic Terryn. All rights reserved. 


public class SessieDaoJpa extends GenericDaoJpa<Sessie> implements SessieDao{

    public SessieDaoJpa() {
        super(Sessie.class);
    }

}
