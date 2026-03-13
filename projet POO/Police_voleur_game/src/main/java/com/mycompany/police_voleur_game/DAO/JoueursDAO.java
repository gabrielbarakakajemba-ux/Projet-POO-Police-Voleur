/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.police_voleur_game.DAO;

import com.mycompany.police_voleur_game.modele.Joueurs;
import com.mycompany.police_voleur_game.Connection.Connectivity;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mr-kajemba
 */
public class JoueursDAO {
    public void create(Joueurs joueur) throws SQLException {
        String sql = "INSERT INTO Joueurs (pseudo) VALUES (?)";
        try (Connection conn = Connectivity.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, joueur.getPseudo());
            stmt.executeUpdate();
        }
    }

    public List<Joueurs> findAll() throws SQLException {
        List<Joueurs> joueurs = new ArrayList<>();
        String sql = "SELECT * FROM Joueurs";
        try (Connection conn = Connectivity.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                joueurs.add(new Joueurs(rs.getInt("id_joueur"), rs.getString("pseudo")));
            }
        }
        return joueurs;
    }

    public void update(Joueurs joueur) throws SQLException {
        String sql = "UPDATE Joueurs SET pseudo = ? WHERE id_joueur = ?";
        try (Connection conn = Connectivity.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, joueur.getPseudo());
            stmt.setInt(2, joueur.getIdJoueur());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Joueurs WHERE id_joueur = ?";
        try (Connection conn = Connectivity.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
