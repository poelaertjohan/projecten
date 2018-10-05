/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
            @NamedQuery(name = "domein.Oefening.findAll", query = "SELECT o FROM Oefening o")
        })
public class Oefening implements Serializable
{

    /*   @Entity
@NamedQueries({
    @NamedQuery(name = "Bier.findByName",
                         query = "select b from Bier b where b.naam = :bierNaam")            
})
     */
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
    @Id
    private String naam;
    private String opgave;
    private String feedback;
    private String antwoord; //indien antwoordt int/double is ==> parsen
    private String vak;
    private int timeLeft;
    @ManyToMany(cascade = CascadeType.PERSIST)/*(mappedBy = "oefeningen*/
    @JoinTable(name = "Oef_Gbw", joinColumns = @JoinColumn(name = "Oefening_naam"), inverseJoinColumns = @JoinColumn(name = "GroepsBewerking_id"))
    private List<GroepsBewerking> groepsBewerkingen;
    private String padNaarPdf;

    @ManyToOne(cascade = CascadeType.DETACH)
    private Doelstelling doelstelling;

    protected Oefening()
    {
    }

//    public Oefening(int groepsBewerkingen, String naam, String opgave, String feedback, String antwoord)
//    {
//        setGroepsBewerkingen(groepsBewerkingen);
//        setNaam(naam);
//        setOpgave(opgave);
//        setFeedback(feedback);
//        setAntwoord(antwoord);
//    }
////    public Oefening(String naam, String opgave, String feedback, String antwoord)
////    {
////        setNaam(naam);
////        setOpgave(opgave);
////        setFeedback(feedback);
////        setAntwoord(antwoord);
////    }
//
//    public Oefening(int id, String naam, String opgave, String feedback, String antwoord)
//    {
//        this.id = id;
//        setNaam(naam);
//        setOpgave(opgave);
//        setFeedback(feedback);
//        setAntwoord(antwoord);
//    }
    public Oefening(String naam, String opgave, String feedback, String antwoord, List<GroepsBewerking> groepsBewerkingen, String padNaarPdf, String vak, Doelstelling doelstelling, int timeLeft)
    {
        setNaam(naam);
        setOpgave(opgave);
        setFeedback(feedback);
        setAntwoord(antwoord);
        setGroepsBewerkingen(groepsBewerkingen);
        setPadNaarPdf(padNaarPdf);
        setVak(vak);
        setDoelstelling(doelstelling);
        setTimeLeft(timeLeft);
    }

    public Oefening(String naam, String opgave, String feedback, String antwoord, String vak, Doelstelling doelstelling, int timeLeft)
    { //constructor zonder groepsbewerking
        setNaam(naam);
        setOpgave(opgave);
        setFeedback(feedback);
        setAntwoord(antwoord);
        setPadNaarPdf(padNaarPdf);
        setVak(vak);
        setDoelstelling(doelstelling);
        setTimeLeft(timeLeft);
        //setGroepsBewerkingen(groepsBewerkingen);

    }

    private void setGroepsBewerkingen(List<GroepsBewerking> groepsBewerkingen)
    {
        this.groepsBewerkingen = groepsBewerkingen;
        //this.groepsBewerkingen.add(new GroepsBewerking("opgave1", "+", "5");
    }

    public List<GroepsBewerking> getGroepsBewerkingen()
    {
        return groepsBewerkingen;
    }

    /*   public String getNaam()
    {
        return naam;
    }

    public void setNaam(String naam)
    {
        this.naam = naam;
    }*/
    public void setVak(String vak)
    {
        if (vak.isEmpty() || vak.equals(null))
        {
            throw new IllegalArgumentException("vak mag niet leeg zijn");
        }
        this.vak = vak;
    }

    public String getVak()
    {
        return this.vak;
    }

    public void setDoelstelling(Doelstelling doelstelling)
    {
        if (doelstelling.toString().isEmpty() || doelstelling.toString().equals(null))
        {
            throw new IllegalArgumentException("doelstelling mag niet leeg zijn");
        }
        this.doelstelling = doelstelling;

    }

    public Doelstelling getDoelstelling()
    {
        return this.doelstelling;
    }

    public void setPadNaarPdf(String padNaarPdf)
    {
        if (padNaarPdf.isEmpty() || padNaarPdf.equals(null))
        {
            throw new IllegalArgumentException("Pad mag niet leeg zijn.");
        }
        this.padNaarPdf = padNaarPdf;
    }

    public String getPadNaarPdf()
    {
        return padNaarPdf;
    }

    public String getOpgave()
    {
        return opgave;
    }

    private void setOpgave(String opgave)
    {
        if (opgave.equals(null) || opgave.isEmpty())
        {
            throw new IllegalArgumentException("Opgave mag niet leeg zijn.");
        }
        this.opgave = opgave;
    }

    public String getFeedback()
    {
        return feedback;
    }

    private void setFeedback(String feedback)
    {
        if (feedback.equals(null) || feedback.isEmpty())
        {
            throw new IllegalArgumentException("Feedback mag niet leeg zijn.");
        }
        this.feedback = feedback;
    }

    public String getAntwoord()
    {
        return antwoord;
    }

    private void setAntwoord(String antwoord)
    {
        if (antwoord.equals(null) || antwoord.isEmpty())
        {
            throw new IllegalArgumentException("het antwoord mag niet leeg zijn");
        }
        if (!antwoord.matches("[0-9]*") || antwoord.matches("-[0-9]*"))
        {
            throw new IllegalArgumentException("het antwoord moet numeriek zijn.");
        }

        this.antwoord = antwoord;
    }

    @Override
    public String toString()
    {
        StringBuilder weergave = new StringBuilder();
        weergave.append(naam);
        weergave.append(";");
        weergave.append(opgave);
        weergave.append(";");
        weergave.append(feedback);
        weergave.append(";");
        weergave.append(antwoord);
        weergave.append(";");
        weergave.append(vak);
        weergave.append(";");
        weergave.append(doelstelling);
        weergave.append(";");
        weergave.append(timeLeft);
        return weergave.toString();
    }

    public String getNaam()
    {
        return naam;
    }

    public void setNaam(String naam)
    {
        if (naam.equals(null) || naam.isEmpty())
        {
            throw new IllegalArgumentException("Naam mag niet leeg zijn.");
        }
        this.naam = naam;
    }

    public String getDaadwerkelijkAntwoord(GroepsBewerking groepsBewerking)
    {
        return groepsBewerking.voerUitInt(antwoord);
    }

    public int getTimeLeft()
    {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft)
    {
        if (timeLeft >= 0)
        {
            this.timeLeft = timeLeft;
        } else
        {
            throw new IllegalArgumentException("Tijd mag niet negatief zijn.");
        }
    }
}
