/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.utilitaires.*;
import fr.gsb.rv.dr.vue.VueConnexion;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 *
 * @author developpeur
 */
public class Appli extends Application {
    
       
    
    //méthod d'implementation boite de dialogue
    private void confirmation(){

            Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
            alertQuitter.setTitle("Quitter");
            alertQuitter.setHeaderText("demande de confirmation");
            alertQuitter.setContentText("Voulez-vous quitter l'application?");
                  
            ButtonType btnOui = new ButtonType("Oui");
            ButtonType btnNon = new ButtonType("Non");
           
            alertQuitter.getButtonTypes().setAll( btnOui, btnNon);
            Optional<ButtonType>reponse=alertQuitter.showAndWait();
            reponse.get();
            
        } 
    
        Visiteur visiteur;
    
        PanneauPraticiens praticien = new PanneauPraticiens();
        PanneauRapports rapport = new PanneauRapports();
        PanneauAccueil accueil = new PanneauAccueil();

        GridPane vuepraticien = praticien.getVuePraticien();
        GridPane vueRapport = rapport.getVueRapports();
        GridPane vueAccueil = accueil.getVueAccueil();
        
        StackPane stackPane = new StackPane();
            
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
        stackPane.getChildren().addAll(vuepraticien,vueAccueil);
        stackPane.setPrefSize(300, 150);
    
  //creation du barre de menu
        MenuBar barreMenu = new MenuBar();
        
        //creation du menu
        Menu menuFichier = new Menu("Fichier");
        Menu menuRapports = new Menu("Rapports");
        Menu menuPraticiens = new Menu("Praticiens");

        //creation de l'item du menu
        MenuItem itemSeConnecter = new MenuItem("Se connecter");
        MenuItem itemSeDeconnecter = new MenuItem("Se deconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter");
        MenuItem itemConsulter = new MenuItem("Consulter");
        MenuItem itemHesitant = new MenuItem("Hésitants");
       
        
        //ajout de l'item dans le menu
        menuFichier.getItems().addAll(itemSeConnecter, itemSeDeconnecter, itemQuitter );
        menuRapports.getItems().addAll(itemConsulter);
        menuPraticiens.getItems().addAll(itemHesitant);
       
        
        //ajout menu à la barre de menu
        barreMenu.getMenus().addAll(menuFichier,menuRapports, menuPraticiens);
  
        BorderPane root = new BorderPane();
        root.setTop(barreMenu);
        //root.setCenter(stackPane);
        
        Scene scene = new Scene(root, 550, 400);
        
        primaryStage.setTitle("GSB-RV-DR");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        // function de l'item exit ou Quitter
        itemQuitter.setOnAction((ActionEvent event) -> {
            confirmation();
            Platform.exit();
        });
        
        
        
        // function de l'item seConnecter
        itemSeConnecter.setOnAction((ActionEvent event) -> {
            
            // la gestion de la session
            try{
                VueConnexion vue = new VueConnexion();
                Optional<Pair<String, String>> result = vue.getDialog().showAndWait();
                // teste de la classe Connexion BD et de la methode seConnecter()
                if(result.isPresent()){
                    Session.ouvrir(ModeleGsbRv.seConnecter((result.get()).getKey(),(result.get()).getValue()));
                    
                    System.out.println("test 1");
                   
                    List<Visiteur>visiteurs = new ArrayList<Visiteur>();
                    visiteurs = ModeleGsbRv.getVisiteurs();
                    for(Visiteur unv:visiteurs){
                        System.out.println(unv);
                    }
                    
                    if(Session.getSession().getLeVisiteur() != null ){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText("Vous êtes Connecter avec Succés");
                        alert.setContentText("Bonjour " + Session.getSession().getLeVisiteur().getNom());
                        alert.showAndWait();
                        itemSeDeconnecter.setDisable(false);
                        itemSeConnecter.setDisable(true);
                        menuRapports.setDisable(false);
                        menuPraticiens.setDisable(false);
                        System.out.println("Session Ouvert:"+Session.getSession().getLeVisiteur().toString()); 
                        praticien.setCritereTri(PanneauPraticiens.CRITERE_COEF_CONFIANCE);
                        root.setCenter(stackPane);
                        changeTop(vueAccueil);
                    }

                    else {
                        
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Saisir Incorrect. ");
                            alert.showAndWait();
                            Optional<Pair<String, String>> result1 = vue.getDialog().showAndWait();
                            System.out.println(result1);
                            changeTop(vueAccueil);
                            Session.ouvrir(ModeleGsbRv.seConnecter((result1.get()).getKey(),(result1.get()).getValue()));       
                        
                    }
                }
            }
            catch(Exception e){
                System.out.println(e);

            }
            //System.out.println(Session.getSession().getLeVisiteur());
        });

      
        itemSeDeconnecter.setOnAction((ActionEvent event) ->{
            
            if(Session.getSession().getLeVisiteur() != null ){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Vous allez être déconnecter");
                alert.setContentText("Déconnecté  vous " + Session.getSession().getLeVisiteur().getNom());
                Optional<ButtonType> resultat = alert.showAndWait();
                
                if(resultat.get() == ButtonType.OK ){
                    Session.fermer();
                    itemSeDeconnecter.setDisable(true);
                    itemSeConnecter.setDisable(false);
                    menuRapports.setDisable(false);
                    menuPraticiens.setDisable(false);
                    //System.out.println("Session Ouvert:"+Session.getSession().getLeVisiteur().toString()); 
                    //praticien.setCritereTri(PanneauPraticiens.CRITERE_COEF_CONFIANCE);
                    root.setTop(barreMenu);
                    root.setCenter(accueil.getVueAccueil());
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    
                }
                    
            }
            
         });
      
        itemConsulter.setOnAction((ActionEvent event) ->{
            try {
                List<Visiteur> visiteurs = ModeleGsbRv.getVisiteurs();
                for(Visiteur unV: visiteurs){
                    System.out.println(unV);
                }
                
            } 
            catch (ConnexionException ex) {
                Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("'[Rapports]'"+Session.getSession().getLeVisiteur().getPrenom()+' '+Session.getSession().getLeVisiteur().getNom() );
        });
        
        
        itemHesitant.setOnAction((ActionEvent event) ->{
            
            
            praticien.rafraichir();
            try{
                List<Praticien> praticiens = ModeleGsbRv.getPraticiensHesitants();
                for(Praticien unP : praticiens){
                    System.out.println(unP+"hello");
                }
                
                System.out.println("test ComparateurCoefConfiance ");
                Collections.sort(praticiens, new ComparateurCoefConfiance());
                for(Praticien unP : praticiens){
                    System.out.println(unP.getDernierCoefConfiance());
                }
                
                System.out.println("test ComparateurCoefNotoriete ");
                Collections.sort(praticiens, new ComparateurCoefNotoriete());
                Collections.reverse(praticiens);
                for(Praticien unP : praticiens){
                    System.out.println(unP);
                }

                System.out.println("test ComparateurDateVisite ");
                Collections.sort(praticiens, new ComparateurDateVisite());
                Collections.reverse(praticiens);
                for(Praticien unP : praticiens){
                    System.out.println(unP);
                }
            }
            
            catch( Exception e){
                System.out.println(e);
            }
           
            //System.out.println("'[Praticiens]'" + Session.getSession().getLeVisiteur().getPrenom()+' '+Session.getSession().getLeVisiteur().getNom() );
        });
       
        
    }
      
   private void changeTop(GridPane i) {
       ObservableList<Node> childs = this.stackPane.getChildren();
 
       if (childs.size() > 1) {
           //
           
           for(Node n : childs){
                n.setVisible(false);
            }
           
           Node topNode = childs.get(childs.size()-1);
          
           
           // This node will be brought to the front
           Node newTopNode = childs.get(childs.size()-2);
                  
           topNode.setVisible(false);
           topNode.toBack();
          
           newTopNode.setVisible(true);
       }
   }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
