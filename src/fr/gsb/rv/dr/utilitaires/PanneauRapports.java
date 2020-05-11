/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.utilitaires;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Mois;
import java.time.LocalDate;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


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
    private TableView<Visiteur> table = new TableView<Visiteur>();
    
    
    public PanneauRapports(){
        
        vueRapports = new GridPane();
        vueRapports.setHgap(10);
        vueRapports.setVgap(10);
        vueRapports.setPadding(new Insets(20, 150, 10, 10));
        
        vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10); 
        
        Text title = new Text("Accueil");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        vbox.setStyle("-fx-background-color: white;");
        vueRapports.add(vbox, 1, 0);
        
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
