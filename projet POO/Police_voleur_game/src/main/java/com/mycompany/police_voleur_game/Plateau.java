/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.police_voleur_game;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author mr-kajemba
 */
public class Plateau extends JFrame {
    public Plateau() {
        setTitle("Jeu Police vs Voleur");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Ajout du plateau
        add(new BoardPanel());
    }
    
    class BoardPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();
            int centerX = width / 2;
            int centerY = height / 2;
            int radius = 200;
            
            // 1. Dessiner le cercle extérieur
            g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

            // 2. Dessiner les lignes (la croix)
            g2d.drawLine(centerX - radius, centerY, centerX + radius, centerY); // Horizontale
            g2d.drawLine(centerX, centerY - radius, centerX, centerY + radius); // Verticale

            // 3. Dessiner les Noeuds (les emplacements des pions)
            int nodeRadius = 15;
            // Centre (P)
            drawNode(g2d, centerX, centerY, nodeRadius);
            // Haut (V)
            drawNode(g2d, centerX, centerY - radius, nodeRadius);
            // Bas (V)
            drawNode(g2d, centerX, centerY + radius, nodeRadius);
            // Gauche (P)
            drawNode(g2d, centerX - radius, centerY, nodeRadius);
            // Droite (P)
            drawNode(g2d, centerX + radius, centerY, nodeRadius);
        }
        
        private void drawNode(Graphics2D g, int x, int y, int r) {
            g.setColor(Color.WHITE);
            g.fillOval(x - r, y - r, r * 2, r * 2);
            g.setColor(Color.BLACK);
            g.drawOval(x - r, y - r, r * 2, r * 2);
        }
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Plateau().setVisible(true);
        });
    }
}


