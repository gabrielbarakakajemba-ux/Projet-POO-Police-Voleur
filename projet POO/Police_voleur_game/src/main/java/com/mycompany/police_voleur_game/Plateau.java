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
        setTitle("Police Voleur");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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
            int radius = 300;
            
           
            g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

            
            g2d.drawLine(centerX - radius, centerY, centerX + radius, centerY);
            g2d.drawLine(centerX, centerY - radius, centerX, centerY + radius);

            int nodeRadius = 40;
            drawNode(g2d, centerX, centerY, nodeRadius);
            drawNodeArc(g2d, centerX, centerY - radius, nodeRadius);
            
        }
        
        private void drawNode(Graphics2D g, int x, int y, int r) {
            g.setColor(Color.BLACK);
            g.drawOval(x - r, y - r, r * 2, r * 2);
        }
        
        private void drawNodeArc(Graphics2D g, int x, int y, int r) {
           g.setColor(Color.BLACK);
           int startAngle = 180;
           int arcAngle = 180;
           
           g.drawArc(x - r,  y - r, r * 2, r * 2,  arcAngle, startAngle);
        } 

    }   
        
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Plateau().setVisible(true);
        });
    }
}