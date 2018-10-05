/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author JorisLaptop
 */
@Entity
@NamedQueries(
        {
            @NamedQuery(name = "domein.MergeAll.findAll", query = "SELECT m FROM MergeAll m"),
            @NamedQuery(name = "domein.MergeAll.findBySessie", query = "select m FROM MergeAll m WHERE m.sessieNaam = :sessieNaam"),
            @NamedQuery(name = "domein.MergeAll.deleteBySessie", query = "DELETE FROM MergeAll m WHERE m.sessieNaam = :sessieNaam")
        })
public class MergeAll {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private String id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private GroepsBewerking groepsBewerking;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Oefening oef;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Groep groep;
    private String sessieNaam;
    private int actiecode;

    protected MergeAll() {

    }

    public MergeAll(GroepsBewerking groepsBewerking, Oefening oef, Groep groep, String sessieNaam, int actiecode) {
        setGroepsBewerking(groepsBewerking);
        setOef(oef);
        setGroep(groep);
        setSessieNaam(sessieNaam);
        setActiecode(actiecode);
    }

    public GroepsBewerking getGroepsBewerking() {
        return groepsBewerking;
    }

    public void setGroepsBewerking(GroepsBewerking groepsBewerking) {
        this.groepsBewerking = groepsBewerking;
    }

    public Oefening getOef() {
        return oef;
    }

    public void setOef(Oefening oef) {
        this.oef = oef;
    }

    public Groep getGroep() {
        return groep;
    }

    public void setGroep(Groep groep) {
        this.groep = groep;
    }

    public String getSessieNaam()
    {
        return sessieNaam;
    }

    public void setSessieNaam(String sessieNaam)
    {
        this.sessieNaam = sessieNaam;
    }

    public int getActiecode()
    {
        return actiecode;
    }

    public void setActiecode(int actiecode)
    {
        this.actiecode = actiecode;
    }
    
    
}
