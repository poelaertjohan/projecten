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
            @NamedQuery(name = "domein.Doelstelling.findAll", query = "SELECT d FROM Doelstelling d")
        })
@Entity
public class Doelstelling implements Serializable
{

    @Id
    private String doelstelling;

    protected Doelstelling()
    {
    }

    public Doelstelling(String doelstelling)
    {
        setDoelstelling(doelstelling);
    }

    @Override
    public String toString()
    {
        return this.doelstelling;
    }

    public String getDoelstelling()
    {
        return doelstelling;
    }

    public void setDoelstelling(String doelstelling)
    {
        if (doelstelling.equals(null) || doelstelling.isEmpty())
        {
            throw new IllegalArgumentException("Naam mag niet leeg zijn");
        }
        this.doelstelling = doelstelling;
    }
}
