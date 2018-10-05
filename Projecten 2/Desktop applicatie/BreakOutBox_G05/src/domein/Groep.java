package domein;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

//Code Written by Fréderic Terryn. All rights reserved. 
@Entity
@NamedQueries(
        {
            @NamedQuery(name = "domein.Groep.findAll", query = "SELECT g FROM Groep g")
        })
public class Groep
{

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private String id;
    private String groepNummer;
    private String leerlingen;
    @JoinTable(name = "Groep_oef", joinColumns = @JoinColumn(name = "Groep"), inverseJoinColumns = @JoinColumn(name = "Oefening"))
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Oefening> oefeningen;

//    private String naam;
    protected Groep()
    {
    }

    public Groep(String groepNummer)
    {
//        setNaam(naam);
        setGroepNummer(groepNummer);
        setLeerlingen("");
    }

//    private void setNaam(String naam)
//    {
//        this.naam = naam;
//    }
    public String getLeerlingen()
    {
        return leerlingen;
    }

    public void setLeerlingen(String leerlingen)
    {
        this.leerlingen = leerlingen;
    }

    public String getGroepNummer()
    {
        return groepNummer;
    }

    public void setGroepNummer(String groepNummer)
    {
        if(groepNummer.equals(null) || groepNummer.isEmpty()){
            throw new IllegalArgumentException("groepsNummer mag niet leeg zijn");
        }
        this.groepNummer = groepNummer;
    }

    public void addLeerling(String naam)
    {
        if (!(leerlingen.trim().isEmpty()))
        {
            leerlingen += ", " + naam;
        } else
        {
            leerlingen = naam;
        }

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public List<Oefening> getOefeningen()
    {
        return oefeningen;
    }

    public void setOefeningen(List<Oefening> oefeningen)
    {
        this.oefeningen = oefeningen;
    }

}
