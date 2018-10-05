///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package gui.Oefening;
//
//import domein.BoxController;
//import domein.FacadeControllers;
//import domein.GroepsBewerkingController;
//import domein.OefeningController;
//import gui.Box.BoxToevoegenSchermController;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.ListView;
//import javafx.scene.layout.AnchorPane;
//import javafx.stage.Stage;
//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.read.biff.BiffException;
//
///**
// * FXML Controller class
// *
// * @author Johan
// */
//public class OefeningenToevoegenAanBoxController extends AnchorPane
//{
//
//    private FacadeControllers facade;
//    private GroepsBewerkingController dcGroepsBew;
//    private OefeningController dcOef;
//    private BoxController dcBox;
//    @FXML
//    private AnchorPane AnchorPane;
//    @FXML
//    private Button btnToevoegen;
//    @FXML
//    private ListView<String> listAlleOefeningen;
//    @FXML
//    private ListView<String> listGekozenOefeningen;
//    @FXML
//    private static final String EXCEL_FILE_LOCATION = "c:\\project\\Excel\\BOB.xls";
//    private boolean isAangepast = false;
//
//    private List<String> gekozenOefeningen;
//    private List<String> alleOefeningen;
//    private ObservableList<String> gekozenOefeningenObservable;
//    private ObservableList<String> alleOefeningenObservable;
//    @FXML
//    private Button btnAnnuleren;
//    @FXML
//    private Button btnOpslaan;
//    @FXML
//    private Button btnExcel;
//
//    public OefeningenToevoegenAanBoxController(FacadeControllers facade)
//    {
//        this.facade = facade;
//        dcGroepsBew = facade.getGroepsBewerkingController();
//        dcOef = facade.getOefeningController();
//        dcBox = facade.getBoxController();
//
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("OefeningenToevoegenAanBox.fxml"));
//        loader.setRoot(this);
//        loader.setController(this);
//        try
//        {
//            loader.load();
//            vulList();
//            gekozenOefeningen = new ArrayList<>();
//            alleOefeningen = listAlleOefeningen.getItems().stream().map(Object::toString).collect(Collectors.toList());
//        } catch (IOException ex)
//        {
//            throw new RuntimeException(ex);
//        }
//
//    }
//
//    private void vulList()
//    {
//        listAlleOefeningen.getItems().clear();
//        listGekozenOefeningen.getItems().clear();
//
//        listAlleOefeningen.setItems(dcOef.geefAlleOefeningenNamenObservable());
//    }
//
//    @FXML
//    private void voegToe(ActionEvent event)
//    {
//        gekozenOefeningen.add(listAlleOefeningen.getSelectionModel().getSelectedItem());
//        gekozenOefeningenObservable = FXCollections.observableArrayList(gekozenOefeningen);
//        listGekozenOefeningen.setItems(gekozenOefeningenObservable);
//
//        alleOefeningen.remove(listAlleOefeningen.getSelectionModel().getSelectedIndex());
//        alleOefeningenObservable = FXCollections.observableArrayList(alleOefeningen);
//        listAlleOefeningen.setItems(alleOefeningenObservable);
//
//    }
//
//    @FXML
//    private void Annuleren(ActionEvent event)
//    {
//        Scene s = this.getScene();
//        Stage stage = (Stage) s.getWindow();
//        s.setRoot(new BoxToevoegenSchermController(facade));
//    }
//
//    @FXML
//    private void Opslaan(ActionEvent event)
//    {
//        gekozenOefeningen = listGekozenOefeningen.getItems().stream().map(Object::toString).collect(Collectors.toList());
////        dcBox.setOefeningenVoorEenBox(gekozenOefeningen);
//        dcBox.setOefeningen(gekozenOefeningen);
//        Scene s = this.getScene();
//        Stage stage = (Stage) s.getWindow();
//        s.setRoot(new BoxToevoegenSchermController(facade));
//    }
//    
//    @FXML
//    public void leesExcel() throws Exception{
//        Workbook workbook = null;
//        try{
//        workbook = Workbook.getWorkbook(new File(EXCEL_FILE_LOCATION));
//        Sheet sheet = workbook.getSheet(0);
//        int i=2; 
//        int j=3;
//        while(j < 30){
//            System.out.println(sheet.getCell(i,j).getContents());
//            j++;
//        }
//        } catch (ArrayIndexOutOfBoundsException e){
//            System.err.println("dit klopt nog niet volledig");
//        } catch (IOException | BiffException e){
//        } finally {
//
//            if (workbook != null) {
//                workbook.close();
//            }
//
//        }
//        
//    }
//
// 
//}
