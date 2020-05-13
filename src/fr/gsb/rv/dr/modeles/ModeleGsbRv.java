package fr.gsb.rv.dr.modeles;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        
        String requete = "select p.pra_num,p.pra_nom,p.pra_ville,p.pra_coefnotoriete,max(r.rap_date_visite) as date,r.rap_coefConfiance " 
                + "from Praticien p "
                + "inner join RapportVisite r "
                + "on p.pra_num = r.pra_num "
                + "group by p.pra_num";

        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;

            ResultSet resultat = requetePreparee.executeQuery() ;
            
            if( resultat.next() ){
               do{
                Praticien praticien = new Praticien() ;
                praticien.setNumero(resultat.getString("pra_num") );
                praticien.setNom(resultat.getString("pra_nom") );
                praticien.setVille(resultat.getString("pra_ville") );
                praticien.setCoefNotoriete(Float.valueOf(resultat.getString("pra_coefnotoriete")) );                
                praticien.setDateDernierVisite(Date.valueOf(resultat.getString("date")).toLocalDate());
                praticien.setDernierCoefConfiance(Integer.valueOf(resultat.getString("rap_coefConfiance")));
                
                
                praticiens.add(praticien);
               }while(resultat.next() == true);
               requetePreparee.close() ;
               return praticiens;
                
            }
            else {
                return null ;
            }
            
        }
        catch( Exception e ){
            return null ;
        } 
    }
    
    
    
    
    public static List<Visiteur> getVisiteurs() throws ConnexionException{
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        List<Visiteur> visiteurs = new ArrayList<Visiteur>();
        
        String requete = "select vis_matricule, vis_nom, vis_prenom "
                + "from Visiteur ";
          
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            
            ResultSet resultat = requetePreparee.executeQuery() ;
            
            if( resultat.next() ){
                
                do{
                    Visiteur visiteur = new Visiteur() ;
                    visiteur.setMatricule( resultat.getString("vis_matricule") );
                    visiteur.setNom( resultat.getString( "vis_nom" ) ) ;
                    visiteur.setPrenom(resultat.getString( "vis_prenom" ) ) ;
                    
                    visiteurs.add(visiteur);
                    
                }while(resultat.next() == true);
                
                
                requetePreparee.close() ;
                return visiteurs ;
            }
            else {
                
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        } 
    }
    
    
    public static List<RapportVisite> getRapportVisite(String matricule, int mois, int annee) throws ConnexionException{
        Connection connexion = ConnexionBD.getConnexion();
        
        List<RapportVisite> RVisite = new ArrayList<RapportVisite>();
        
        String req = "select vis_matricule, rap_num, rap_date_visite, rap_bilan, pra_num, rap_coefConfiance, rap_date_redaction, mot_id, rap_lu"
                + "from RapportVisite";
        try{
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( req ) ;
            
            ResultSet result = requetePreparee.executeQuery() ;
            if( result.next() ){
               RapportVisite rpVisite = new RapportVisite();
               rpVisite.setNumero(result.getInt("rap_num"));
               rpVisite.setDateVisite(Date.valueOf(result.getString("rap_date_visite")).toLocalDate());
               rpVisite.setBilan(result.getString("rap_bilan"));
               rpVisite.setCoefConfiance(Integer.valueOf(result.getString("rap_coeConfiance")));
               rpVisite.setDateRedaction(Date.valueOf(result.getString("rap_date_redaction")).toLocalDate());
               rpVisite.setMotif(result.getString("mot_id"));
               rpVisite.setLu(result.getBoolean("rap_lu"));
               RVisite.add(rpVisite);
               requetePreparee.close();
            }
            return RVisite;
        } 
        catch (Exception e) {
            return null;
        }            
    }
  
    
    public static void setRapportVisiteLu(String matricule, int numero)throws ConnexionException{
        Connection connexion = ConnexionBD.getConnexion();
        
        String req = "UPDATE RapportVisite"
                + "SET rap_lu = true"
                +"where vis_matricule=? and rap_num=?";
        
        try{
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( req ) ;
            requetePreparee.setString( 1 , matricule );
            requetePreparee.setString( 2 , matricule );
            ResultSet result = requetePreparee.executeQuery() ;
                        
            requetePreparee.close();

        }
        
        catch (Exception e) {
            System.out.println(e);
        } 
        
    }   
    
}
