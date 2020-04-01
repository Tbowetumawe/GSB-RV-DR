package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

public class ModeleGsbRv {
    
    public static Visiteur seConnecter( String matricule , String mdp ) throws ConnexionException{
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        String requete = "select vis_nom, vis_prenom "
                + "from Visiteur "
                + "where vis_matricule = ?" ;
          
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
            ResultSet resultat = requetePreparee.executeQuery() ;
            
            if( resultat.next() ){
                
                Visiteur visiteur = new Visiteur() ;
                visiteur.setMatricule( matricule );
                visiteur.setNom( resultat.getString( "vis_nom" ) ) ;
                visiteur.setPrenom(resultat.getString( "vis_prenom" ) ) ;
               
                
                
                requetePreparee.close() ;
                return visiteur ;
            }
            else {
                
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        } 
    }
    
    public static List<Praticien> getPraticiensHesitants() throws ConnexionException{
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        List<Praticien> praticiens = new ArrayList<Praticien>();
        
        String requete = "select Praticien.pra_num, Praticien.pra_nom, Praticien.pra_ville, Praticien.pra_coefnotoriete, max(RapportVisite.rap_date_visite) as date,RapportVisite.rap_coefConfiance " 
                + "from Praticien"
                + "inner join RapportVisite"
                + "on Praticien.pra_num = RapportVisite.pra_num "
                + "group by Praticien.pra_num";
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            ResultSet resultat = requetePreparee.executeQuery() ;
            
            if( resultat.next() ){
               
                Praticien praticien = new Praticien() ;
                praticien.setNumero(resultat.getString("pra_num") );
                praticien.setNom(resultat.getString("pra_nom") );
                praticien.setVille(resultat.getString("pra_ville") );
                praticien.setCoefNotoriete(Float.valueOf(resultat.getString("pra_coefnotoriete")) );                
                praticien.setDateDernierVisite(Date.valueOf(resultat.getString("date")).toLocalDate());
                praticien.setDernierCoefConfiance(Integer.valueOf(resultat.getString("rap_coefConfiance")));
                
                praticiens.add(praticien);
                //requetePreparee.close() ;
                
            }   
            else {
                return null ;
            }
           return praticiens; 
        }
        catch( Exception e ){
            return null;
        } 
    }
}
