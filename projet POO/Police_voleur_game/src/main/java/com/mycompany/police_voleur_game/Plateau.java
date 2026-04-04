/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.police_voleur_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 *
 * @author mr-kajemba
 */
public class Plateau extends JFrame{
   public Plateau() {
        setTitle("Police Voleur");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        add(new BoardPanel());
    }
    class BoardPanel extends JPanel {

      
        private final Map<String, Positions> posMap;
        private final GameLogic              logique;
        private String messageStatut = "Cliquez où vous voulez ! Le voleur peut aller n'importe où (sauf sur une police). [R] = rejouer";

        
        private static final int W      = 800;
        private static final int H      = 800;
        private static final int CX     = W / 2;   
        private static final int CY     = H / 2;  
        private static final int RADIUS = 370;
        private static final int NR     = 20;

        BoardPanel() {
            posMap  = construirePositions();
            logique = new GameLogic(posMap);

            setFocusable(true);
            requestFocusInWindow();
            addKeyListener(new KeyAdapter() {
                @Override 
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_R) {
                        logique.nouvellePartie();
                        messageStatut = "Nouvelle partie ! Cliquez où vous voulez déplacer le Voleur.";
                        repaint();
                    }
                }
            });

            addMouseListener(new MouseAdapter() {
                @Override 
                public void mouseClicked(MouseEvent e) {
                    if (logique.isPartieTerminee()) return;

                    String cible = noeudProche(e.getX(), e.getY());
                    if (cible == null) return;

                    boolean ok = logique.deplacerVoleur(cible);

                    if (!ok) {
                        messageStatut = "Impossible ! Une police est déjà sur cette case.";
                        repaint();
                        return;
                    }

                    if (logique.isPartieTerminee()) { 
                        finPartie(); 
                        repaint(); 
                        return; 
                    }

                    messageStatut = "Les polices jouent...";
                    repaint();

                    // Pause courte puis tour des polices
                    javax.swing.Timer t = new javax.swing.Timer(450, evt -> {
                        logique.deplacerPolices();
                        if (logique.isPartieTerminee()) {
                            finPartie();
                        } else {
                            messageStatut = "À vous ! Cliquez où vous voulez (le voleur est libre). [R] = rejouer";
                        }
                        repaint();
                        ((javax.swing.Timer) evt.getSource()).stop();
                    });
                    t.setRepeats(false);
                    t.start();
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width   = getWidth();
            int height  = getHeight();
            int centerX = width  / 2;
            int centerY = height / 2;
            int radius  = 370;

            // Dessin du plateau (code original)
            g2d.drawOval(centerX - radius, centerY - radius, radius * 2, radius * 2);

            g2d.drawLine(centerX - radius, centerY, centerX + radius, centerY);
            g2d.drawLine(centerX, centerY - radius, centerX, centerY + radius);

            int nodeRadius = 20;
            drawNode    (g2d, centerX, centerY,          nodeRadius); // centre
            drawNodeArc (g2d, centerX, centerY - radius, nodeRadius); // arc du Haut
            drawNodeArcD(g2d, centerX, centerY + radius, nodeRadius); // arc du Bas
            drawNodeArcR(g2d, centerX + radius, centerY, nodeRadius); // arc de Droite
            drawNodeArcL(g2d, centerX - radius, centerY, nodeRadius); // arc de Gauche

            dessinerSurbrillance(g2d);   
            dessinerPions(g2d);
            dessinerScore(g2d);
        }

    
        private void drawNode(Graphics2D g, int x, int y, int r) {
            g.setColor(Color.BLACK);
            g.drawOval(x - r, y - r, r * 2, r * 2);
        }

        private void drawNodeArc(Graphics2D g, int x, int y, int r) {
            g.setColor(Color.BLACK);
            g.drawArc(x - r, y - r, r * 2, r * 2, 180, 180);
        }

        private void drawNodeArcD(Graphics2D g, int x, int y, int r) {
            g.setColor(Color.BLACK);
            g.drawArc(x - r, y - r, r * 2, r * 2, 0, 180);
        }

         private void drawNodeArcR(Graphics2D g, int x, int y, int r) {
           g.setColor(Color.BLACK);
           int startAngle = 180;
           int arcAngle = 90;
           
           g.drawArc(x - r,  y - r, r * 2, r * 2,  arcAngle, startAngle);
        } 

        private void drawNodeArcL(Graphics2D g, int x, int y, int r) {
           g.setColor(Color.BLACK);
           int startAngle = 180;
           int arcAngle = - 90;
           
           g.drawArc(x - r,  y - r, r * 2, r * 2,  arcAngle, startAngle);
        }

        private Map<String, Positions> construirePositions() {
            Map<String, Positions> m = new LinkedHashMap<>();

            m.put(GameLogic.CENTRE,      new Positions(CX,            CY,            GameLogic.CENTRE));

            m.put(GameLogic.CERCLE_HAUT, new Positions(CX,            CY - NR,       GameLogic.CERCLE_HAUT));
            m.put(GameLogic.CERCLE_BAS,  new Positions(CX,            CY + NR,       GameLogic.CERCLE_BAS));
            m.put(GameLogic.CERCLE_G,    new Positions(CX - NR,       CY,            GameLogic.CERCLE_G));
            m.put(GameLogic.CERCLE_D,    new Positions(CX + NR,       CY,            GameLogic.CERCLE_D));

            int aHY = CY - RADIUS;
            m.put(GameLogic.HAUT_G,  new Positions(CX - NR, aHY,      GameLogic.HAUT_G));
            m.put(GameLogic.HAUT_D,  new Positions(CX + NR, aHY,      GameLogic.HAUT_D));
            m.put(GameLogic.HAUT_M,  new Positions(CX,      aHY - NR, GameLogic.HAUT_M));

            int aBY = CY + RADIUS;
            m.put(GameLogic.BAS_G,   new Positions(CX - NR, aBY,      GameLogic.BAS_G));
            m.put(GameLogic.BAS_D,   new Positions(CX + NR, aBY,      GameLogic.BAS_D));
            m.put(GameLogic.BAS_M,   new Positions(CX,      aBY + NR, GameLogic.BAS_M));

            int aDX = CX + RADIUS;
            m.put(GameLogic.DROITE_H, new Positions(aDX,      CY - NR, GameLogic.DROITE_H));
            m.put(GameLogic.DROITE_B, new Positions(aDX,      CY + NR, GameLogic.DROITE_B));
            m.put(GameLogic.DROITE_M, new Positions(aDX + NR, CY,      GameLogic.DROITE_M));

            int aGX = CX - RADIUS;
            m.put(GameLogic.GAUCHE_H, new Positions(aGX,      CY - NR, GameLogic.GAUCHE_H));
            m.put(GameLogic.GAUCHE_B, new Positions(aGX,      CY + NR, GameLogic.GAUCHE_B));
            m.put(GameLogic.GAUCHE_M, new Positions(aGX - NR, CY,      GameLogic.GAUCHE_M));

            return m;
        }

    
        private void dessinerSurbrillance(Graphics2D g2d) {
           
        }

        private void dessinerPions(Graphics2D g2d) {
            for (Pions p : logique.getTousPions()) {
                p.dessiner(g2d);
            }
        }

     
        private void dessinerScore(Graphics2D g2d) {

            if (logique.isPartieTerminee()) {
                g2d.setColor(new Color(0, 0, 0, 170));
                g2d.fillRoundRect(150, 340, 500, 110, 18, 18);

                boolean pg = logique.getGagnant().equals("POLICE");
                String ligne1 = pg
                        ? "POLICE GAGNE ! Le voleur est arrêté !"
                        : "VOLEUR GAGNE ! Il s'est échappé !";
                g2d.setFont(new Font("Arial", Font.BOLD, 20));
                g2d.setColor(pg ? Color.CYAN : new Color(255, 120, 0));
                FontMetrics fm2 = g2d.getFontMetrics();
                g2d.drawString(ligne1, (W - fm2.stringWidth(ligne1)) / 2, 390);

                g2d.setFont(new Font("Arial", Font.PLAIN, 14));
                g2d.setColor(Color.WHITE);
                String ligne2 = "Appuyez sur [R] pour rejouer";
                g2d.drawString(ligne2, (W - g2d.getFontMetrics().stringWidth(ligne2)) / 2, 428);
            }
        }

        private String noeudProche(int mx, int my) {
            String res = null;
            double minD = Double.MAX_VALUE;
            for (Map.Entry<String, Positions> e : posMap.entrySet()) {
                Positions p = e.getValue();
                double d = Math.hypot(p.x - mx, p.y - my);
                if (d < 30 && d < minD) {
                    minD = d;
                    res = e.getKey();
                }
            }
            return res;
        }

      
        private void finPartie() {
            boolean pg = logique.getGagnant().equals("POLICE");
            messageStatut = pg
                    ? "Police gagne ! Score -> Police:" + logique.getScorePolice() + "  Voleur:" + logique.getScoreVoleur()
                    : "Voleur gagne ! Score -> Police:" + logique.getScorePolice() + "  Voleur:" + logique.getScoreVoleur();
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Plateau().setVisible(true);
        });
    }
}  
