/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.utilitaires;

import fr.gsb.rv.dr.entites.Praticien;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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
        vbox.setStyle("-fx-background-color: white;");
        
        final ToggleGroup group = new ToggleGroup();
        rbCoefConfiance = new RadioButton("Confiance");
        rbCoefConfiance.setToggleGroup(group);
        
        rbCoefNotoriete = new RadioButton("Notoriété");
        rbCoefNotoriete.setToggleGroup(group);
        
        rbDateVisite = new RadioButton("Date Visite");
        rbDateVisite.setToggleGroup(group);
        
        rbCoefConfiance.setUserData(CRITERE_COEF_CONFIANCE);
        rbCoefConfiance.setSelected(true);
        rbCoefNotoriete.setUserData(CRITERE_COEF_NOTORIETE);
        rbDateVisite.setUserData(CRITERE_DATE_VISITE);
        
    }   

    
    public void rafraichir(){
        
    }
    
    
    public int getCritereTri() {
        return critereTri;
    }
    
    
    
}
