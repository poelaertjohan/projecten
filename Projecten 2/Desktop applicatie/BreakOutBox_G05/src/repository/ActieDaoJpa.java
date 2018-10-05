/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Actie;
import java.util.List;

/**
 *
 * @author Vital Verleyen
 */
public class ActieDaoJpa extends GenericDaoJpa<Actie> implements ActieDao{

    public ActieDaoJpa() {
        super(Actie.class);
    }
    
    @Override
    public List<String> getAlleBoxesStringFormat() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
