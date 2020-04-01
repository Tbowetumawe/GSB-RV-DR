/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.utilitaires;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author developpeur
 */
public class PanneauPraticiens extends Pane  {
    
    public static int CRITERE_COEF_CONFIANCE = 1;
    public static int CRITERE_COEF_NOTORIETE = 2;
    public static int CRITERE_DATE_VISITE = 3;

    private int critereTri = CRITERE_COEF_CONFIANCE;
    
    private RadioButton rbCoefConfiance;
    private RadioButton rbCoefNotoriete;
    private RadioButton rbDateVisite;
    
    private GridPane vuePraticien;
    private VBox vbox;
    
    private TableView<Praticien> tabPraticiens = new TableView<Praticien>();

    public PanneauPraticiens() {
        
        vuePraticien = new GridPane();
        vuePraticien.setHgap(10);
        vuePraticien.setVgap(10);
        vuePraticien.setPadding(new Insets(20, 150, 10, 10));

        vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10);
        
        
        final ToggleGroup group = new ToggleGroup();
        rbCoefConfiance = new RadioButton("Confiance");
        rbCoefNotoriete = new RadioButton("Notoriété");
        rbDateVisite = new RadioButton("Date Visite");
        
        rbCoefConfiance.setToggleGroup(group);
        rbCoefNotoriete.setToggleGroup(group);
        rbDateVisite.setToggleGroup(group);
        
        rbCoefConfiance.setUserData(CRITERE_COEF_CONFIANCE);
        rbCoefConfiance.setSelected(true);
        rbCoefNotoriete.setUserData(CRITERE_COEF_NOTORIETE);
        rbDateVisite.setUserData(CRITERE_DATE_VISITE);
        
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        Text title = new Text("Sélectionner un critère de tri :");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        
        hbox.getChildren().addAll(rbCoefConfiance,rbCoefNotoriete,rbDateVisite);
        vbox.getChildren().add(hbox);
        
        TableColumn<Praticien,String> colNumero = new TableColumn<Praticien,String>("Numéro");
        TableColumn<Praticien,String> colNom = new TableColumn<Praticien,String>("Nom");
        TableColumn<Praticien,String> colVille = new TableColumn<Praticien,String>("Ville");
        
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colVille.setCellValueFactory(new PropertyValueFactory<>("ville"));
        
        tabPraticiens.getColumns().add(colNumero);
        tabPraticiens.getColumns().add(colNom);
        tabPraticiens.getColumns().add(colVille);
        
        vbox.getChildren().add(tabPraticiens);
        
        //rafraichir();
        rbCoefConfiance.setOnAction((ActionEvent event) -> {
            critereTri = CRITERE_COEF_CONFIANCE;
            rafraichir();
            }
        );
        
        rbCoefNotoriete.setOnAction((ActionEvent event) -> {
            critereTri = CRITERE_COEF_NOTORIETE;
            rafraichir();
            }
        );
        
        rbDateVisite.setOnAction((ActionEvent event) -> {
            critereTri = CRITERE_DATE_VISITE;
            rafraichir();
            }
        );
        
        vuePraticien.add(vbox, 1, 0);
        
    }  
    
    

    public GridPane getVuePraticien() {
        return vuePraticien;
    }

    public void setVuePraticien(GridPane vuePraticien) {
        this.vuePraticien = vuePraticien;
    }
    
    

    
    public void rafraichir(){
        List<Praticien> praticiens;
        try{
            praticiens = ModeleGsbRv.getPraticiensHesitants();
            ObservableList<Praticien> list;
            if(this.critereTri == CRITERE_COEF_CONFIANCE){
               
                Collections.sort(praticiens, new ComparateurCoefConfiance());
                
            }
            else if(this.critereTri == CRITERE_COEF_NOTORIETE){
               
                Collections.sort(praticiens, new ComparateurCoefNotoriete());
                Collections.reverse(praticiens);
                
            }
            else{
                Collections.sort(praticiens, new ComparateurDateVisite());
                Collections.reverse(praticiens);
               
            }
            
            list = FXCollections.observableArrayList(praticiens);
            tabPraticiens.setItems(list);
            
        }
        catch(Exception e){
                System.out.println(e);

         }
        
    }
    
    
    public int getCritereTri() {
        return critereTri;
    }

    public void setCritereTri(int critereTri) {
        this.critereTri = critereTri;
    }
    
    
    
    
    
}
