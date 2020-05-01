/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.utilitaires;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author developpeur
 */
public class PanneauAccueil {
    
    private GridPane vueAccueil;
    private VBox vbox;
    
    public PanneauAccueil() {
    
        vueAccueil = new GridPane();
        vueAccueil.setHgap(10);
        vueAccueil.setVgap(10);
        vueAccueil.setPadding(new Insets(20, 150, 10, 10));
        
        vbox = new VBox();
        vbox.setPadding(new Insets(15, 12, 15, 12));
        vbox.setSpacing(10); 
        
        Text title = new Text("Accueil");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);
        vbox.setStyle("-fx-background-color: white;");
        vueAccueil.add(vbox, 1, 0);
        
    }

    public GridPane getVueAccueil() {
        return vueAccueil;
    }
    
    

    
    
}