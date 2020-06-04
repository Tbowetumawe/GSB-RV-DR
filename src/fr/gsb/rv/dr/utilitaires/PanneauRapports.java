/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.utilitaires;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Mois;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javax.swing.text.TableView.TableRow;


/**
 *
 * @author developpeur
 */
public class PanneauRapports extends Pane {
    
    private ComboBox<Visiteur> cbVisiteur = new ComboBox<Visiteur>();
    private ComboBox<Mois> cbMois = new ComboBox<Mois>();
    private ComboBox<Integer> cbAnnee = new ComboBox<Integer>();
    private GridPane vueRapports;
    private VBox vbox;
    private TableView<RapportVisite> tabRapports = new TableView<RapportVisite>();
    
    
    public PanneauRapports(){
        
        vueRapports = new GridPane();
        vueRapports.setHgap(10);
        vueRapports.setVgap(10);
        vueRapports.setPadding(new Insets(15, 20, 10, 10));
        
        vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10); 
        
        Text title = new Text("Rapports");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        vbox.setStyle("-fx-background-color: white;");
        
        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        
        Button btnValider = new Button("Valider");
        
        
        //peuplement combobox visiteur
        try {
            List<Visiteur> visiteurs = ModeleGsbRv.getVisiteurs();
            ObservableList<Visiteur> list;
            list= FXCollections.observableArrayList(visiteurs);
            cbVisiteur.setItems(list);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        cbVisiteur.setPromptText(" Sélectionner un Visiteur ");
        
        //peuplement combobox mois 
        Mois[] unMois = Mois.values();
        ObservableList<Mois> lesMois;
        try{
            lesMois = FXCollections.observableArrayList(unMois);        
            cbMois.setItems(lesMois);    
        }
        catch (Exception e) {
            System.out.println(e);
        }
        cbMois.setPromptText(" Sectionner un Mois ");
       

         
    
        //peuplement combobox annee
        LocalDate aujourdhui = LocalDate.now();
        int anneeCourant = aujourdhui.getYear();
        ObservableList<Integer> listAnnee = null;

        //int start = anneeCourant;
        //ValueRange range = ValueRange.of(start, anneeCourant);
        for (int i = anneeCourant; i < anneeCourant; i++) {
            listAnnee.addAll(anneeCourant,anneeCourant-i);
        }
        try{
            listAnnee = FXCollections.observableArrayList(anneeCourant);
            cbAnnee.setItems(listAnnee);
        }
        catch (Exception e) {
            System.out.println(e);
        }
        cbAnnee.setPromptText(" Sectionner une Année ");

        
        hbox.getChildren().add(cbVisiteur);
        hbox.getChildren().add(cbMois);
        hbox.getChildren().add(cbAnnee);
        hbox.getChildren().add(btnValider);

        vbox.getChildren().add(hbox);
        vbox.getChildren().add(tabRapports);
        
        btnValider.setOnAction((ActionEvent e)->{
            if(cbVisiteur.getValue()!= null && cbMois.getValue()!= null && cbAnnee.getValue() != null){
                rafraichir();
            }
            
            else if(cbVisiteur.getValue()== null && cbMois.getValue()!= null && cbAnnee.getValue() != null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Seclectionner toutes les champs");
                alert.setContentText("Le visiteur ou le mois ou l'année n'est pas seclectionné " );
                alert.showAndWait();
            }
            
            else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Seclectionner toutes les champs");
                alert.setContentText("Le visiteur ou le mois ou l'année n'est pas seclectionné");
                alert.showAndWait();   
            }
        });
        
        
        
        TableColumn <RapportVisite, Integer> colNumero = new TableColumn<RapportVisite, Integer>("Numéro");
        TableColumn <RapportVisite, String> colNom = new TableColumn<RapportVisite, String>("Nom");
        TableColumn <RapportVisite, String> colVille = new TableColumn<RapportVisite, String>("Ville");
        TableColumn <RapportVisite, LocalDate> colDateVisite = new TableColumn<RapportVisite, LocalDate>("Date Visite");
        TableColumn <RapportVisite, LocalDate> colDateRedac = new TableColumn<RapportVisite, LocalDate>("Date Redaction");
        
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tabRapports.getColumns().add(colNumero);
        
        colNom.setCellValueFactory( param ->{
            String nom = param.getValue().getLePraticien().getNom();
            return new SimpleStringProperty(nom);
        }
        );
        tabRapports.getColumns().add(colNom);
        
         colVille.setCellValueFactory(param ->{
            String ville = param.getValue().getLePraticien().getVille();
            return new SimpleStringProperty(ville);            
        }
        );
        tabRapports.getColumns().add(colVille);
            
        colDateVisite.setCellValueFactory(new PropertyValueFactory<>("dateVisite"));
        tabRapports.getColumns().add(colDateVisite);
        
        colDateRedac.setCellValueFactory(new PropertyValueFactory<>("dateRedaction"));
        tabRapports.getColumns().add(colDateRedac);
        
        
        //modification du format d'affichage de la collone date visite et date redac'
        
        colDateVisite.setCellFactory(
            colonne -> {
                return new TableCell<RapportVisite, LocalDate>(){
                    protected void updateItem(LocalDate item, Boolean empty){
                        super.updateItem( item, empty);
                        if( empty){
                            setText("");
                        }
                        else{
                            DateTimeFormatter formateur = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                            setText( item.format(formateur));
                        }
                    }
                };
            }
        );
        
        
        colDateRedac.setCellFactory(
            colonne -> {
                return new TableCell<RapportVisite, LocalDate>(){
                    protected void updateItem(LocalDate item, Boolean empty){
                        super.updateItem( item, empty);
                        if( empty){
                            setText("");
                        }
                        else{
                            DateTimeFormatter formateur = DateTimeFormatter.ofPattern("dd/MM/uuuu");
                            setText( item.format(formateur));
                        }
                    }
                };
            }
        );
        
        //application du font en fonctionde l'etat de lecture
        
       /* tabRapports.setRowFactory(
            ligne -> {
            return new TableRow<RapportVisite>(){
                    
                protected void updateItem( RapportVisite item, boolean empty ){
                    super.updateItem(item, empty);
                    
                    if( item != null ){
                        if( item.isLu()){
                            setStyle( "-fx-background-color: gold");
                        }
                        else{
                            setStyle( "-fx-baclground-color: cyan");
                        }
                    }
                }
                };
            }
        );
        */
       
        // traitement section d'une ligne 
        
        tabRapports.setOnMouseClicked(
            (MouseEvent event)-> {
                if( event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                    int indiceRapport = tabRapports.getSelectionModel().getSelectedIndex();
                    
                    ObservableList<RapportVisite> rows = tabRapports.getSelectionModel().getSelectedItems();
                    rows.get(indiceRapport).isLu();
                    RapportVisite rapportv = new RapportVisite();
                    
                    try{
                        ModeleGsbRv.setRapportVisiteLu(rapportv.getLeVisiteur().getMatricule(), rapportv.getNumero());
                    }
                    catch (Exception e) {
                        System.out.println (e);
                    }
                    
                    rafraichir();
                    
                
                }
            }
        );
        vueRapports.add(vbox, 1, 0);     
    }
    
    

    public GridPane getVueRapports() {
        return vueRapports;
    }
    
    
    
    public void rafraichir(){
        try {
            RapportVisite rpV = new RapportVisite();
            List<RapportVisite> rapportdeViste = new ArrayList<RapportVisite>();
            rapportdeViste = ModeleGsbRv.getRapportVisite(rpV.getLeVisiteur().getMatricule(), rpV.getDateRedaction().getMonthValue(), rpV.getDateRedaction().getYear());
            
            ObservableList<RapportVisite> obList = FXCollections.observableList(rapportdeViste);
            tabRapports.setItems(obList);
        } 
        catch (Exception e) {
            System.out.println(e);
        }
        
    }
    

    
}
