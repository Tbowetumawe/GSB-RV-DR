/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.vue;

import fr.gsb.rv.dr.entites.RapportVisite;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

/**
 *
 * @author developpeur
 */
public class VueRapport {
    private Dialog<void> dialog = new Dialog<>();

    public VueRapport() {
    
        this.dialog.setTitle("Authentification");
        this.dialog.setHeaderText("Saisir vos données de connexion");
        
        //ButtonType loginButtonType = new ButtonType("Se connecter", ButtonBar.ButtonData.OK_DONE);
        this.dialog.getDialogPane().getButtonTypes().addAll(/*loginButtonType*/ ButtonType.CANCEL);


        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(20));

        /*TextField matricule = new TextField();
        matricule.setPromptText("matricule");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");*/

        root.add(new Label("matricule:"), 0, 0);
        root.add(matricule, 1, 0);
        root.add(new Label("Password:"), 0, 1);
        root.add(password, 1, 1);


        this.dialog.getDialogPane().lookupButton(loginButtonType);

        //matricule.textProperty().addListener();

        this.dialog.getDialogPane().setContent(root);

        Platform.runLater(() -> matricule.requestFocus());


        this.dialog.setResultConverter(
                new Callback<ButtonType, Pair<String,String>>(){
                    @Override
                    public Pair<String,String> call( ButtonType typeBouton){
                        if (typeBouton == loginButtonType) {
                            return new Pair<String,String>(matricule.getText(), password.getText());
                        }
                        return null;
                    }
                }
        );

    }

    public Dialog<Pair<String, String>> getDialog() {
        return dialog;
    }
}
