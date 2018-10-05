/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package repository;

import domein.Actie;
import domein.Box;
import domein.Doelstelling;
import domein.Groep;
import domein.GroepsBewerking;
import domein.MergeAll;
import domein.Oefening;
import domein.Sessie;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javax.persistence.TypedQuery;

/**
 *
 * @author Vital Verleyen
 */
public class PersistentieController {

    private OefeningDaoJpa oefeningRepo;
    private GroepsBewerkingDaoJpa groepsBewerkingRepo;
    private BoxDaoJpa boxRepo;
    private ActieDaoJpa actieRepo;
    private DoelstellingDaoJpa doelstellingRepo;
    private SessieDaoJpa sessieRepo;
    private GroepDaoJpa groepRepo;
    private MergeDaoJpa mergeRepo;

    public PersistentieController() {
        oefeningRepo = new OefeningDaoJpa();
        groepsBewerkingRepo = new GroepsBewerkingDaoJpa();
        boxRepo = new BoxDaoJpa();
        actieRepo = new ActieDaoJpa();
        doelstellingRepo = new DoelstellingDaoJpa();
        sessieRepo = new SessieDaoJpa();
        groepRepo = new GroepDaoJpa();
        mergeRepo = new MergeDaoJpa();
    }

    public void voegOefeningToe(Oefening oef /*, GroepsBewerking groepsbewerking */) {
        oefeningRepo.startTransaction();
        oefeningRepo.insert(oef);
        oefeningRepo.commitTransaction();
    }

    public void removeOef(Oefening oef) {
        oefeningRepo.startTransaction();
        oefeningRepo.delete(oef);
        oefeningRepo.commitTransaction();
    }

    public void voegBoxToe(Box box) {
        boxRepo.startTransaction();
        boxRepo.insert(box);
        boxRepo.commitTransaction();
    }

    public void removeBox(Box box) {
        boxRepo.startTransaction();
        boxRepo.delete(box);
        boxRepo.commitTransaction();
    }

    public void voegGroepsBewerkingToe(GroepsBewerking groepsbewerking) {
        groepsBewerkingRepo.startTransaction();
        groepsBewerkingRepo.insert(groepsbewerking);
        groepsBewerkingRepo.commitTransaction();
    }

    public List<Oefening> findAllOefeningen() {
        return oefeningRepo.findAll();
    }

    public List<MergeAll> findAllMergeAlls() {
        return mergeRepo.findAll();
    }

    public List<GroepsBewerking> findAllGroepsBewerkingen() {
        return groepsBewerkingRepo.findAll();
    }

    public List<Box> findAllBoxes() {
        return boxRepo.findAll();
    }

    public void updateOefening(Oefening oefening) {
        oefeningRepo.startTransaction();
        oefeningRepo.update(oefening);
        oefeningRepo.commitTransaction();
    }

    public void updateGroepsBewerking(GroepsBewerking groepsbewerking) {
        groepsBewerkingRepo.startTransaction();
        groepsBewerkingRepo.update(groepsbewerking);
        groepsBewerkingRepo.commitTransaction();
    }

    public List<GroepsBewerking> getGroepsbewerkingenDoorOpgave(List<String> namenGroepsBewerkingen) {
        List<GroepsBewerking> groepsbewerking = new ArrayList<>();

        for (String naam : namenGroepsBewerkingen) {
            for (GroepsBewerking gbw : groepsBewerkingRepo.findAll()) {
                if (naam.equalsIgnoreCase(gbw.getOpgave())) {
                    groepsbewerking.add(gbw);
                }
            }
        }

        return groepsbewerking;
    }

    public void removeGroepsBewerking(GroepsBewerking gr) {
        oefeningRepo.startTransaction();
        groepsBewerkingRepo.delete(gr);
        oefeningRepo.commitTransaction();
    }

    public List<Oefening> getAllOefForThisBox(String box) {
        return boxRepo.getAllOefForThisBox(box);
    }

    public List<Actie> findAllOpgeslagenActies() {
        return actieRepo.findAll();
    }

    public void voegActieToe(Actie actie) {
        actieRepo.startTransaction();
        actieRepo.insert(actie);
        actieRepo.commitTransaction();
    }

    public void removeActie(Actie actie) {
        actieRepo.startTransaction();
        actieRepo.delete(actie);
        actieRepo.commitTransaction();
    }

    public List<Doelstelling> findAllOpgeslagenDoelstellingen() {
        return doelstellingRepo.findAll();
    }

    public void voegDoelstellingToe(Doelstelling doelstelling) {
        doelstellingRepo.startTransaction();
        doelstellingRepo.insert(doelstelling);
        doelstellingRepo.commitTransaction();
    }

    public void removeDoelstelling(Doelstelling doelstelling) {
        doelstellingRepo.startTransaction();
        doelstellingRepo.delete(doelstelling);
        doelstellingRepo.commitTransaction();
    }

    public List<Sessie> findAllSessies() {
        return sessieRepo.findAll();
    }

    public void voegSessieToe(Sessie sessie) {
        sessieRepo.startTransaction();
        sessieRepo.insert(sessie);
        sessieRepo.commitTransaction();

    }

    public void voegGroepToe(Groep groep) {
        groepRepo.startTransaction();
        groepRepo.insert(groep);
        groepRepo.commitTransaction();
    }

    /*  public void voegGroepToe(int groepsnr, String leerling) {
        groepRepo.startTransaction();
        groepRepo.insert(leerling);
        groepRepo.commitTransaction();
    }*/
    public void removeSessie(Sessie sessie) {
        sessieRepo.startTransaction();
        sessieRepo.delete(sessie);
        sessieRepo.commitTransaction();
    }

    public Box findBox(String box) {
        for (Box b : boxRepo.findAll()) {
            if (b.getNaam().equalsIgnoreCase(box)) {
                return b;
            }
        }
        return null;    //er wordt altijd iets gereturned dus die return null
        //zal nooit worden gedaan
    }

    public void updateBox(Box nieuweBox) {
        boxRepo.startTransaction();
        boxRepo.update(nieuweBox);
        boxRepo.commitTransaction();
    }

    public void updateSessie(Sessie s) {
        sessieRepo.startTransaction();
        sessieRepo.update(s);
        sessieRepo.commitTransaction();
    }

    public void insertMerge(MergeAll merge) {
        mergeRepo.startTransaction();
        mergeRepo.insert(merge);
        mergeRepo.commitTransaction();
    }

    public void removeMerge(MergeAll m) {
        mergeRepo.startTransaction();
        mergeRepo.delete(m);
        mergeRepo.commitTransaction();
    }

    public void updateMerge(MergeAll m) {
        mergeRepo.startTransaction();
        mergeRepo.update(m);
        mergeRepo.commitTransaction();
    }

    public List<MergeAll> getMergeBySessieNaam(String sessieNaam) {
        return mergeRepo.getMergeBySessieNaam(sessieNaam);
    }

    public List<MergeAll> removeMergesOpSessieNaam(String naam) {
        return mergeRepo.removeMergesOpSessieNaam(naam);
    }

    public void removeGroepen() {
        System.out.println("oops");
        List<Groep> g = new ArrayList<>(groepRepo.findAll());
        g.stream().forEach(gr -> {
            groepRepo.startTransaction();
            groepRepo.delete(gr);
            groepRepo.commitTransaction();
        });
    }

}
