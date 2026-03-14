/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.police_voleur_game.modele;

/**
 *
 * @author mr-kajemba
 */
public class Partie {
    private int id_partie;
    private java.sql.Date date_partie;
    private int Nb_voleur;
    private int nb_police;
    private int resultat;
    private java.sql.Timestamp duree; 

    public Partie(int id_partie, java.sql.Date date_partie, int Nb_voleur, int nb_police, int resultat, java.sql.Timestamp duree) {
        this.id_partie = id_partie;
        this.date_partie = date_partie;
        this.Nb_voleur = Nb_voleur;
        this.nb_police = nb_police;
        this.resultat = resultat;
        this.duree = duree;
    }

    // Getters
    public int getId_partie() {
        return id_partie;
    }

    public java.sql.Date getDate_partie() {
        return date_partie;
    }

    public int getNb_voleur() {
        return Nb_voleur;
    }

    public int getNb_police() {
        return nb_police;
    }

    public int getResultat() {
        return resultat;
    }

    public java.sql.Timestamp getDuree() {
        return duree;
    }

}
