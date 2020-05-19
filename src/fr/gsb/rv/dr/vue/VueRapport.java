/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vue;

import fr.gsb.rv.dr.entites.RapportVisite;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 *
 * @author developpeur
 */
public class VueRapport {
    private Dialog<String> dialog = new Dialog<>();

    public VueRapport() {
        RapportVisite rpV = new RapportVisite();
        
        this.dialog.setTitle("Rapport de visite");
        this.dialog.setHeaderText("Information relative au rapport de visite");
        
        //ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        this.dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);


        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        root.add(new Label(""+rpV.getNumero()), 0, 0);
        root.add(new Label(""+ rpV.getLeVisiteur()), 1, 0);
        root.add(new Label("" + rpV.getLePraticien()), 0, 1);
        root.add(new Label("" + rpV.getDateVisite()), 1, 1);
        root.add(new Label("" + rpV.getDateRedaction()), 0, 2);
        root.add(new Label("" + rpV.getBilan()), 1, 2);
        root.add(new Label("" + rpV.getMotif()), 0, 3);
        


        //this.dialog.getDialogPane().lookupButton(loginButtonType);

       

        this.dialog.getDialogPane().setContent(root);
    }
        //Platform.runLater();


        /*this.dialog.setResultConverter(
                new Callback<ButtonType>(){
                    @Override
                    public Pair<String,String> call( ButtonType typeBouton){
                        if (typeBouton == loginButtonType) {
                            return new String;
                        }
                        return null;
                    }
                }
        );

    }*/

    public Dialog<String> getDialog() {
        return dialog;
    }

  
}
