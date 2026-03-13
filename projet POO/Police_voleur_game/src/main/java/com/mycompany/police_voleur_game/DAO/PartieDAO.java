/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.police_voleur_game.DAO;

import com.mycompany.police_voleur_game.modele.Partie;
import com.mycompany.police_voleur_game.Connection.Connectivity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mr-kajemba
 */
public class PartieDAO {
    public void insert(Partie p) throws SQLException {
        String sql = "INSERT INTO partie (date_partie, \"Nb_voleur\", nb_police, resultat, duree) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = Connectivity.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setDate(1, p.getDate_partie());
            stmt.setInt(2, p.getNb_voleur());
            stmt.setInt(3, p.getNb_police());
            stmt.setInt(4, p.getResultat());
            stmt.setTimestamp(5, p.getDuree());
            
            stmt.executeUpdate();
        }
    }

   
    public List<Partie> findAll() throws SQLException {
        List<Partie> liste = new ArrayList<>();
        String sql = "SELECT * FROM partie";
        
        try (Connection conn = Connectivity.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Partie p = new Partie(
                    rs.getInt("id_partie"),
                    rs.getDate("date_partie"),
                    rs.getInt("Nb_voleur"),
                    rs.getInt("nb_police"),
                    rs.getInt("resultat"),
                    rs.getTimestamp("duree")
                );
                liste.add(p);
            }
        }
        return liste;
    }

    
    public void update(Partie p) throws SQLException {
        String sql = "UPDATE partie SET date_partie = ?, \"Nb_voleur\" = ?, nb_police = ?, resultat = ?, duree = ? WHERE id_partie = ?";
    
        try (Connection conn = Connectivity.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, p.getDate_partie());
            stmt.setInt(2, p.getNb_voleur());
            stmt.setInt(3, p.getNb_police());
            stmt.setInt(4, p.getResultat());
            stmt.setTimestamp(5, p.getDuree());
            stmt.setInt(6, p.getId_partie()); // Le WHERE pour cibler la bonne partie

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("La mise à jour de la partie " + p.getId_partie() + " a réussi !");
            }
        }
    }
    
    
    
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM partie WHERE id_partie = ?";
        try (Connection conn = Connectivity.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
