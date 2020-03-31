/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.utilitaires.*;
import fr.gsb.rv.dr.vue.VueConnexion;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
    Visiteur visiteur;
    
    StackPane stackPane = new StackPane();
       
    
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
    
    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        
    
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
                        
                        
                        
                        
                        
                    }

                    else {
                        int usernameattempts = 1;
                        int tentative = 2;
                        while (Session.getSession().getLeVisiteur() == null && usernameattempts < 3 ) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Saisir Incorrect. Vous avez "+ tentative +" tentative restante");
                            alert.showAndWait();
                            Optional<Pair<String, String>> result1 = vue.getDialog().showAndWait();
                            System.out.println(result1);
                            Session.ouvrir(ModeleGsbRv.seConnecter((result1.get()).getKey(),(result1.get()).getValue()));
                            usernameattempts++;
                            tentative--;
                        }
                        if(Session.getSession().getLeVisiteur() == null){
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Vous avez dépassé la tentative de connexion !!!");
                            alert.showAndWait();
                            System.exit(0);
                        }
                        else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setHeaderText("Vous êtes Connecter avec Succés");
                            alert.setContentText("Bonjour " + Session.getSession().getLeVisiteur().getNom());
                            alert.showAndWait();
                            itemSeDeconnecter.setDisable(false);
                            itemSeConnecter.setDisable(true);
                            menuRapports.setDisable(false);
                        
                            menuPraticiens.setDisable(false);
                            System.out.println("Session Ouvert:"+Session.getSession().getLeVisiteur().toString());
                            
                            
                        }
                    }
                }
            }
            catch(Exception e){
                System.out.println(e);

            }
            System.out.println(Session.getSession().getLeVisiteur());
        });
            
        
        itemSeDeconnecter.setOnAction((ActionEvent event) -> {
            if (Session.getSession() != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Déconnexion");
                alert.setContentText("Voulez-vous déconnecter "+ Session.getSession().getLeVisiteur().getNom() +" ?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Session.fermer();
                    itemSeDeconnecter.setDisable(false);
                    itemSeConnecter.setDisable(true);
                    menuRapports.setDisable(false);     
                    menuPraticiens.setDisable(false);
                    root.setTop(barreMenu);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                    System.out.println("Session Fermer");
                    
                }
            }
        });
        
        
        //Navigation
      
        itemConsulter.setOnAction((ActionEvent event) ->{
            System.out.println("'[Rapports]'"+Session.getSession().getLeVisiteur().getPrenom()+' '+Session.getSession().getLeVisiteur().getNom() );
        });
        
        itemHesitant.setOnAction((ActionEvent event) ->{
            try{
                List<Praticien> praticiens = ModeleGsbRv.getPraticiensHesitants();
                for(Praticien i : praticiens){
                    System.out.println(i+"hello");
                }

                System.out.println("test ComparateurCoefConfiance ");
                Collections.sort(praticiens, new ComparateurCoefConfiance());
                for(Praticien i : praticiens){
                    System.out.println(i);
                }

                System.out.println("test ComparateurCoefNotoriete ");
                Collections.sort(praticiens, new ComparateurCoefNotoriete());
                Collections.reverse(praticiens);
                for(Praticien i : praticiens){
                    System.out.println(i);
                }

                System.out.println("test ComparateurDateVisite ");
                Collections.sort(praticiens, new ComparateurDateVisite());
                Collections.reverse(praticiens);
                for(Praticien i : praticiens){
                    System.out.println(i);
                    }
            }
            catch( Exception e){
                System.out.println(e);
            }
           
            System.out.println("'[Praticiens]'" + Session.getSession().getLeVisiteur().getPrenom()+' '+Session.getSession().getLeVisiteur().getNom() );
        });
       
        
    }
      
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
