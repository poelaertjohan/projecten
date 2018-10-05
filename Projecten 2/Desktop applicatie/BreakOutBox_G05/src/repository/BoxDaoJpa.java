/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Box;
import domein.Oefening;
import java.util.List;

/**
 *
 * @author Vital Verleyen
 */
public class BoxDaoJpa extends GenericDaoJpa<Box> implements BoxDao{
    public BoxDaoJpa(){
        super(Box.class);
    }

    @Override
    public List<String> getAlleBoxesStringFormat(List<Box> boxes) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    List<Oefening> getAllOefForThisBox(String box) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
