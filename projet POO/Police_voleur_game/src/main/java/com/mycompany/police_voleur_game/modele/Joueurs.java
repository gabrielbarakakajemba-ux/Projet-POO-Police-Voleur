/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.police_voleur_game.modele;

/**
 *
 * @author mr-kajemba
 */
public class Joueurs {
    private int id_joueurs;
    private String pseudo;

    public Joueurs(int id_joueurs, String pseudo) {
        this.id_joueurs = id_joueurs;
        this.pseudo = pseudo;
    }
    
    public int getIdJoueur() { return id_joueurs; }
    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }
}
