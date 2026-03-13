/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.police_voleur_game.Connection;

/**
 *
 * @author mr-kajemba
 */
public class Database {
     public String getConnectionUrl() {
        return "jdbc:postgresql://localhost:5432/Police_voleur"; 
    }
    public String getUserName() {
        return "postgres"; 
    }
    public String getPassWord() {
        return "baraka@08"; 
    }
}
