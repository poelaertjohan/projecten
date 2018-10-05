/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author Vital Verleyen
 */
@NamedQueries(
        {
            @NamedQuery(name = "domein.Actie.findAll", query = "SELECT a FROM Actie a")
        })

@Entity
public class Actie implements Serializable {
    
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
    private String actie;
    
    protected Actie() {
        
    }
    
    public Actie(String actie) {
        setActie(actie);
    }
    
    public String getActie() {
        return actie;
    }
    
    public void setActie(String actie) {
        if(actie.equals(null) || actie.isEmpty()){
            throw new IllegalArgumentException("Naam mag niet leeg zijn");
        }
        this.actie = actie;
    }
    
    @Override
    public String toString() {
        return this.actie;
    }
    
}
