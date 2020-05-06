/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.gsb.rv.dr.entites;

import java.time.LocalDate;

/**
 *
 * @author developpeur
 */
public class RapportVisite {

    private int numero;
    private LocalDate dataRedaction;
    private LocalDate dateVisite;
    private String bilan;
    private String motif;
    private int coefConfiance;
    private boolean lu;
    private Praticien lePraticien;
    private Visiteur leVisiteur;
    
    public RapportVisite(int numero, LocalDate dataRedaction, LocalDate dateVisite, String bilan, String motif, int coefConfiance, boolean lu) {
        this.numero = numero;
        this.dataRedaction = dataRedaction;
        this.dateVisite = dateVisite;
        this.bilan = bilan;
        this.motif = motif;
        this.coefConfiance = coefConfiance;
        this.lu = lu;
    }

    public RapportVisite() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getDataRedaction() {
        return dataRedaction;
    }

    public void setDataRedaction(LocalDate dataRedaction) {
        this.dataRedaction = dataRedaction;
    }

    public LocalDate getDateVisite() {
        return dateVisite;
    }

    public void setDateVisite(LocalDate dateVisite) {
        this.dateVisite = dateVisite;
    }

    public String getBilan() {
        return bilan;
    }

    public void setBilan(String bilan) {
        this.bilan = bilan;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public int getCoefConfiance() {
        return coefConfiance;
    }

    public void setCoefConfiance(int coefConfiance) {
        this.coefConfiance = coefConfiance;
    }

    public boolean isLu() {
        return lu;
    }

    public void setLu(boolean lu) {
        this.lu = lu;
    }

    public Praticien getLePraticien() {
        return lePraticien;
    }

    public void setLePraticien(Praticien lePraticien) {
        this.lePraticien = lePraticien;
    }

    public Visiteur getLeVisiteur() {
        return leVisiteur;
    }
    
    

    public void setLeVisiteur(Visiteur leVisiteur) {
        this.leVisiteur = leVisiteur;
    }
    
    
    
    
    
}
