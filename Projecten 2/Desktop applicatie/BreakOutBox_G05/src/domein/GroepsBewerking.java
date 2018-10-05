/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domein;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author JorisLaptop
 */
@Entity
@NamedQueries(
        {
            @NamedQuery(name = "domein.GroepsBewerking.findAll", query = "SELECT g FROM GroepsBewerking g")
        })
public class GroepsBewerking implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
    private String opgave;
    private String operator;
    private String getal;
//    @ManyToMany(mappedBy = "GroepsBewerking")
//    private List<Oefening> oefeningen;

    public GroepsBewerking(String opgave, String operator, String getal) {
        setOpgave(opgave);
        setOperator(operator);
        setGetal(getal);
//        setOefeningen(oefeningen);

    }
//
//    public void setOefeningen(List<Oefening> oefeningen)
//    {
//        this.oefeningen = oefeningen;
//    }

    public GroepsBewerking() {
    }

    public String getOpgave() {
        return opgave;
    }

    public void setOpgave(String opgave) {
        if (opgave.equals(null) || opgave.isEmpty()) {
            throw new IllegalArgumentException("Opgave mag niet leeg zijn");
        }
        this.opgave = opgave;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        if (operator.equals(null) || operator.isEmpty()) {
            throw new IllegalArgumentException("Operator mag niet leeg zijn");
        }
        this.operator = operator;
    }

    public String getGetal() {
        return getal;
    }

    public void setGetal(String getal) {
        if(getal.equals(null) || getal.isEmpty()){
            throw new IllegalArgumentException("het getal mag niet leeg zijn");
        }
        if (!getal.matches("[0-9]*")) {
            throw new IllegalArgumentException("het getal moet numeriek zijn.");
        }
        this.getal = getal;
    }

    @Override
    public String toString() {
        return opgave + ";" + operator + ";" + getal;
    }

    public String voerUitInt(String antwoord) {
        int daadWerkelijkAntwoord = 0;
        int antwoordInt = Integer.parseInt(antwoord);
        int getalInt = Integer.parseInt(getal);

        switch (operator) {
            case "+":
                daadWerkelijkAntwoord = antwoordInt + getalInt;
                break;
            case "*":
            case "x":
                daadWerkelijkAntwoord = antwoordInt * getalInt;
                break;
            case "/":
                daadWerkelijkAntwoord = antwoordInt / getalInt;
                break;
            case "-":
                daadWerkelijkAntwoord = antwoordInt - getalInt;
                break;
        }
        StringBuilder antwoordString = new StringBuilder();
        antwoordString.append(daadWerkelijkAntwoord);
        return antwoordString.toString();
    }

}
