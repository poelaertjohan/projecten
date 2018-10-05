/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import javax.persistence.Transient;

/**
 *
 * @author Vital Verleyen
 */
@NamedQueries(
        {
            @NamedQuery(name = "domein.Box.findAll", query = "SELECT b FROM Box b")

        }
)

@Entity
public class Box implements Serializable {

    @Id
    private String naam;
    private String omschrijving;

    @ElementCollection
    @JoinColumn(name = "toegangsCodes")
    private List<String> toegangsCodes;

    @JoinTable(name = "Box_oef", joinColumns = @JoinColumn(name = "box_naam"), inverseJoinColumns = @JoinColumn(name = "Oefening_naam"))
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Oefening> oefeningen;

//    @ElementCollection
//    @JoinColumn(name = "acties")
    /*(mappedBy = "oefeningen*/
    @JoinTable(name = "Box_Acties", joinColumns = @JoinColumn(name = "box_naam"), inverseJoinColumns = @JoinColumn(name = "actie_actie"))
    @OneToMany(cascade = CascadeType.MERGE)
    private List<Actie> acties;

    protected Box() {
    }

    public Box(String naam, String omschrijving, List<String> toegangsCodes, List<Oefening> oefeningen, List<Actie> acties) {
        setNaam(naam);
        setOmschrijving(omschrijving);
        setToegangsCodes(toegangsCodes);
        setOefeningen(oefeningen);
        setActies(acties);
    }

    public List<String> getToegangsCodes() {
        return toegangsCodes;
    }

    private void setToegangsCodes(List<String> toegangsCodes) {
        this.toegangsCodes = toegangsCodes;
    }

    public List<Oefening> getOefeningen() {
        return oefeningen;
    }

    private void setOefeningen(List<Oefening> oefeningen) {
        this.oefeningen = oefeningen;
    }

    public List<Actie> getActies() {
        return acties;
    }

    public void setActies(List<Actie> acties) {
        this.acties = acties;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        if (naam.isEmpty() || naam.equals(null)) {
            throw new IllegalArgumentException("Naam mag niet leeg zijn");
        }
        this.naam = naam;
    }

    @Override
    public String toString() {
        StringBuilder weergave = new StringBuilder();
        weergave.append(this.naam);
        weergave.append(";");
        weergave.append(this.omschrijving);
        return weergave.toString();
    }
}
