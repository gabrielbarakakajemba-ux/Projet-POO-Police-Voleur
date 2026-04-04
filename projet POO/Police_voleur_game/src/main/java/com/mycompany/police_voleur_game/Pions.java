/*
 * Pions.java — version complète
 * Représente un pion (Police ou Voleur) sur le plateau.
 */
package com.mycompany.police_voleur_game;

import java.awt.*;

public class Pions {
    private Positions positionActuelle;
    private Color couleur;
    private String type;
    private static final int DIAMETRE = 30;

    public Pions(Positions pos, String type) {
        this.positionActuelle = pos;
        this.type = type;
        this.couleur = type.equals("P") ? Color.BLUE : Color.RED;
    }

    public void dessiner(Graphics2D g2d) {
        g2d.setColor(couleur);
        g2d.fillOval(positionActuelle.x - DIAMETRE / 2,
                     positionActuelle.y - DIAMETRE / 2,
                     DIAMETRE, DIAMETRE);
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString(type, positionActuelle.x - 5, positionActuelle.y + 5);
    }

    public Positions getPosition()           { return positionActuelle; }
    public void      setPosition(Positions pos) { this.positionActuelle = pos; }
    public String    getType()               { return type; }
}