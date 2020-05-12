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
    
    private ComboBox<Visiteur> cbVisiteur;
    private ComboBox<Mois> cbMois;
    private ComboBox<Integer> cbAnnee;
    private GridPane vueRapports;
    private VBox vbox;
    private TableView<Visiteur> tabRapports = new TableView<Visiteur>();
    
    
    public PanneauRapports(){
        
        vueRapports = new GridPane();
        vueRapports.setHgap(10);
        vueRapports.setVgap(10);
        vueRapports.setPadding(new Insets(20, 150, 10, 10));
        
        vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10); 
        
        Text title = new Text("Rapports");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        vbox.setStyle("-fx-background-color: white;");
        vueRapports.add(vbox, 1, 0);
        
        TableColumn <RapportVisite, Integer> colNumero = new TableColumn<RapportVisite, Integer>("Numéro");
        TableColumn <RapportVisite, Praticien> colNom = new TableColumn<RapportVisite, Praticien>("Nom");
        TableColumn <RapportVisite, Praticien> colVille = new TableColumn<RapportVisite, Praticien>("Ville");
        TableColumn <RapportVisite, LocalDate> colDateVisite =new TableColumn<RapportVisite, LocalDate>("Date Visite");
        TableColumn <RapportVisite, LocalDate> colDateRedac =new TableColumn<RapportVisite, LocalDate>("Date Redaction");
        
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        
        colNom.setCellValueFactory(
                param ->{        
                    String nom = param.getValue().getLePraticien().getNom();
                    return new SimpleStringProperty(nom);
                    
                }
            );
        
         colVille.setCellValueFactory((CellDataFeatures<RapportVisite, String>)param ->{
            
            String ville = param.getValue().getLePraticien().getVille();
            return new SimpleStringProperty(ville);            
        }
        );
        
            
        colDateVisite.setCellValueFactory(new PropertyValueFactory<>("dateVisite"));
        colDateRedac.setCellValueFactory(new PropertyValueFactory<>("dateRedaction"));
        
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
        tabRapports.setRowFactory(
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
        
        // traitement section d'une ligne 
        
        tabRapports.setOnMouseClicked(
                (MouseEvent event)-> {
                    if( event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
                        tabRapports
                    }
                }
        );
        
        
        try {
            List<Visiteur> visiteurs = ModeleGsbRv.getVisiteurs();
            ObservableList<Visiteur> list;
            list= FXCollections.observableArrayList(visiteurs);
            cbVisiteur.setItems(list);
   
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        for(Mois unMois : Mois.values()){
                ObservableList<Mois> liste;
                liste= FXCollections.observableArrayList(unMois);
                cbMois.setItems(liste);
        }
        
    
    
    LocalDate aujourdhui =LocalDate.now();
        int anneeCourant = aujourdhui.getYear();
        int start = anneeCourant-6;
        //ValueRange range = ValueRange.of(start, anneeCourant);
        for (int i = start; i < anneeCourant; i++) {
            ObservableList<Integer> listAnnee = null;
            listAnnee.add(i);
            listAnnee = FXCollections.observableArrayList(anneeCourant);
            cbAnnee.setItems(listAnnee);
        }
        
        Button btnValider = new Button();
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
        
        
    
    }
    
    

    public GridPane getVueRapports() {
        return vueRapports;
    }
    
    
    
    public void rafraichir(){
        
        System.out.println(cbVisiteur.getValue()+" mois "+ cbMois.getValue() + " année " + cbAnnee.getValue());
        
    }
    

    
}
