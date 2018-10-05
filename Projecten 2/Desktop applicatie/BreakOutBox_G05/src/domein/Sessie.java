package domein;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@NamedQueries(
        {
            @NamedQuery(name = "domein.Sessie.findAll", query = "SELECT s FROM Sessie s")
        })
@Entity
public class Sessie implements Serializable {

    private boolean afstandsOnderwijs;

    @Id
    private String naam;

    private Box box;

    private String code;

    private String omschrijving;

    private String beschikbaarVanaf;

    @JoinTable(name = "Sessie_Groep", joinColumns = @JoinColumn(name = "sessie_naam"), inverseJoinColumns = @JoinColumn(name = "Groep_Id"))
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Groep> groepen;

    protected Sessie() {
    }

    public Sessie(boolean afstandsOnderwijs, String naam, Box box, String omschrijving, String beschikbaarVanaf, List<Groep> groepen) {
        setAfstandsOnderwijs(afstandsOnderwijs);
        setBox(box);
        setGroepen(groepen);
        setNaam(naam);
        setOmschrijving(omschrijving);
        setBeschikbaarVanaf(beschikbaarVanaf);
        this.code = generateCode();

    }

    public boolean isAfstandsOnderwijs() {
        return afstandsOnderwijs;
    }

    public void setAfstandsOnderwijs(boolean afstandsOnderwijs) {
        this.afstandsOnderwijs = afstandsOnderwijs;
    }

    public void setBeschikbaarVanaf(String beschikbaarVanaf) {
        this.beschikbaarVanaf = beschikbaarVanaf;
    }

    public String getBeschikbaarVanaf() {
        return beschikbaarVanaf;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public String getCode() {
        return code;
    }

    private String generateCode() {
//               Random random = new Random();
//            int rand = random.nextInt(8999999) + 1000000;
//            return Integer.toString(rand);
        return UUID.randomUUID().toString();
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public List<Groep> getGroepen() {
        return groepen;
    }

    public void setGroepen(List<Groep> groepen) {
        this.groepen = groepen;
    }

    @Override
    public String toString() {
        StringBuilder weergave = new StringBuilder();
        weergave.append(naam);
        weergave.append(";");
        weergave.append(omschrijving);
        weergave.append(";");
        weergave.append(box.getNaam());
        weergave.append(";");
        weergave.append(code);
        weergave.append(";");
        weergave.append(afstandsOnderwijs);
        weergave.append(";");
        weergave.append(beschikbaarVanaf.toString());

        return weergave.toString();
    }

}
