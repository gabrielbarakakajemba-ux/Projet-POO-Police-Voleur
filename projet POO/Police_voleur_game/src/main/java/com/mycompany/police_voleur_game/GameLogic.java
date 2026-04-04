/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.police_voleur_game;

import java.util.*;
/**
 *
 * @author mr-kajemba
 */




public class GameLogic {

    public static final String CENTRE       = "CENTRE";

    public static final String HAUT_G       = "HAUT_G";
    public static final String HAUT_M       = "HAUT_M";   
    public static final String HAUT_D       = "HAUT_D";

    public static final String BAS_G        = "BAS_G";
    public static final String BAS_M        = "BAS_M";    
    public static final String BAS_D        = "BAS_D";

    public static final String GAUCHE_H     = "GAUCHE_H";
    public static final String GAUCHE_M     = "GAUCHE_M"; 
    public static final String GAUCHE_B     = "GAUCHE_B";

    public static final String DROITE_H     = "DROITE_H";
    public static final String DROITE_M     = "DROITE_M"; 
    public static final String DROITE_B     = "DROITE_B";

    public static final String CERCLE_HAUT  = "CERCLE_HAUT";
    public static final String CERCLE_BAS   = "CERCLE_BAS";
    public static final String CERCLE_G     = "CERCLE_G";
    public static final String CERCLE_D     = "CERCLE_D";

  
    private final Map<String, List<String>> graphe = new LinkedHashMap<>();
    private final Map<String, Positions>    positionsMap;

  
    private Pions voleur;
    private final List<Pions> polices = new ArrayList<>();


    private int scorePolice = 0;
    private int scoreVoleur = 0;

   
    private boolean partieTerminee = false;
    private String gagnant = "";        

    
    private final Random random = new Random();

   
    public GameLogic(Map<String, Positions> positionsMap) {
        this.positionsMap = positionsMap;
        construireGraphe();
        initialiserPions();
    }

   
    private void construireGraphe() {

        
        ajouter(CENTRE,CERCLE_HAUT, CERCLE_BAS, CERCLE_G, CERCLE_D);

      
        ajouter(CERCLE_HAUT, CENTRE, CERCLE_G, CERCLE_D, HAUT_G, HAUT_D);
        ajouter(CERCLE_BAS,  CENTRE, CERCLE_G, CERCLE_D, BAS_G,  BAS_D);
        ajouter(CERCLE_G,    CENTRE, CERCLE_HAUT, CERCLE_BAS, GAUCHE_H, GAUCHE_B);
        ajouter(CERCLE_D,    CENTRE, CERCLE_HAUT, CERCLE_BAS, DROITE_H, DROITE_B);

      
        ajouter(HAUT_G,  CERCLE_HAUT, HAUT_M);
        ajouter(HAUT_M,  HAUT_G, HAUT_D);           
        ajouter(HAUT_D,  CERCLE_HAUT, HAUT_M);

      
        ajouter(BAS_G,   CERCLE_BAS, BAS_M);
        ajouter(BAS_M,   BAS_G, BAS_D);            
        ajouter(BAS_D,   CERCLE_BAS, BAS_M);

       
        ajouter(GAUCHE_H, CERCLE_G, GAUCHE_M);
        ajouter(GAUCHE_M, GAUCHE_H, GAUCHE_B);
        ajouter(GAUCHE_B, CERCLE_G, GAUCHE_M);

        
        ajouter(DROITE_H, CERCLE_D, DROITE_M);
        ajouter(DROITE_M, DROITE_H, DROITE_B);
        ajouter(DROITE_B, CERCLE_D, DROITE_M);
    }

 
    private void ajouter(String src, String... voisins) {
        graphe.computeIfAbsent(src, k -> new ArrayList<>());
        for (String v : voisins) {
            if (!graphe.get(src).contains(v))
                graphe.get(src).add(v);
            graphe.computeIfAbsent(v, k -> new ArrayList<>());
            if (!graphe.get(v).contains(src))
                graphe.get(v).add(src);
        }
    }

    private void initialiserPions() {
        polices.clear();
        partieTerminee = false;
        gagnant = "";

        voleur = new Pions(positionsMap.get(HAUT_M), "V");

        polices.add(new Pions(positionsMap.get(CENTRE),    "P"));
        polices.add(new Pions(positionsMap.get(CERCLE_G),  "P"));
        polices.add(new Pions(positionsMap.get(CERCLE_D),  "P"));
    }

    public Pions getVoleur()          { return voleur; }
    public List<Pions> getPolices()   { return Collections.unmodifiableList(polices); }

    public List<Pions> getTousPions() {
        List<Pions> tous = new ArrayList<>(polices);
        tous.add(voleur);
        return tous;
    }

  public boolean deplacerVoleur(String idCible) {
    if (partieTerminee) return false;

    if (estOccupeeParPolice(idCible)) {
        return false;
    }

    voleur.setPosition(positionsMap.get(idCible));

    if (idCible.equals(BAS_M)) {
        partieTerminee = true;
        gagnant = "VOLEUR";
        scoreVoleur++;
        return true;
    }

    verifierFinPartie();
    return true;
}

   
    public void deplacerPolices() {
        if (partieTerminee) return;

        for (Pions police : polices) {
            String idActuel = police.getPosition().id;

       
            boolean strategieOptimale = random.nextDouble() < 0.80;

            String meilleurCoup;
            if (strategieOptimale) {
                meilleurCoup = trouverMeilleurCoup(idActuel);
            } else {
                meilleurCoup = choisirCoupAleatoire(idActuel);
            }

            if (meilleurCoup != null) {
                police.setPosition(positionsMap.get(meilleurCoup));
            }
        }

        verifierFinPartie();
    }

  
    private String trouverMeilleurCoup(String depart) {
        String cible = voleur.getPosition().id;
        if (depart.equals(cible)) return null;

        // BFS
        Map<String, String> parent = new LinkedHashMap<>();
        Queue<String> file = new LinkedList<>();
        file.add(depart);
        parent.put(depart, null);

        while (!file.isEmpty()) {
            String courant = file.poll();
            if (courant.equals(cible)) break;

            for (String voisin : graphe.getOrDefault(courant, Collections.emptyList())) {
                if (!parent.containsKey(voisin) && !estOccupeeParPolice(voisin)) {
                    parent.put(voisin, courant);
                    file.add(voisin);
                }
            }
        }

       
        if (!parent.containsKey(cible)) return choisirCoupAleatoire(depart);

        String etape = cible;
        while (parent.get(etape) != null && !parent.get(etape).equals(depart)) {
            etape = parent.get(etape);
        }
        if (parent.get(etape) == null) return choisirCoupAleatoire(depart); 

        return etape;
    }

    private String choisirCoupAleatoire(String depart) {
        List<String> voisins = new ArrayList<>(graphe.getOrDefault(depart, Collections.emptyList()));
        voisins.removeIf(this::estOccupeeParPolice);
        if (voisins.isEmpty()) return null;
        return voisins.get(random.nextInt(voisins.size()));
    }

    private boolean estOccupeeParPolice(String id) {
        for (Pions p : polices) {
            if (p.getPosition().id.equals(id)) return true;
        }
        return false;
    }

    private void verifierFinPartie() {
        if (partieTerminee) return;

        String idVoleur = voleur.getPosition().id;    

        boolean voleurDansBas = idVoleur.equals(BAS_M) || idVoleur.equals(BAS_G) || idVoleur.equals(BAS_D);
        boolean basGOccupe = estOccupeeParPolice(BAS_G);
        boolean basDOccupe = estOccupeeParPolice(BAS_D);
        if (voleurDansBas && basGOccupe && basDOccupe) {
            List<String> voisinsVoleur = graphe.getOrDefault(idVoleur, Collections.emptyList());
            boolean tousBloque = voisinsVoleur.stream().allMatch(this::estOccupeeParPolice);
            if (tousBloque) {
                partieTerminee = true;
                gagnant = "POLICE";
                scorePolice++;
                return;
            }
        }
    }

    public boolean isPartieTerminee()   { return partieTerminee; }
    public String  getGagnant()         { return gagnant; }
    public int     getScorePolice()     { return scorePolice; }
    public int     getScoreVoleur()     { return scoreVoleur; }

   
    public List<String> getVoisinsVoleur() {
        String id = voleur.getPosition().id;
        List<String> voisins = new ArrayList<>(graphe.getOrDefault(id, Collections.emptyList()));
        voisins.removeIf(this::estOccupeeParPolice);
        return voisins;
    }

    
    public void nouvellePartie() {
        initialiserPions();
    }

    
    public Map<String, List<String>> getGraphe() {
        return Collections.unmodifiableMap(graphe);
    }
}