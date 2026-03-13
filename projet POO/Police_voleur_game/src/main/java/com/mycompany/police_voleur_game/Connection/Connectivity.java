/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.police_voleur_game.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mr-kajemba
 */
public class Connectivity {
   private static Connection con = null;

    public static Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Database databaseInfo = new Database(); 
            
            con = DriverManager.getConnection(
                databaseInfo.getConnectionUrl(), 
                databaseInfo.getUserName(), 
                databaseInfo.getPassWord()
            );
            
            System.out.println("Database connected = " + con);
            
        }catch (ClassNotFoundException e) {
            System.out.println("Driver PostgreSQL introuvable ! Ajoutez la dépendance Maven.");
        }catch (SQLException e) {
            System.out.println("Erreur de connexion SQL:" + e.getMessage());
        }
        
        return con;
    }
}
