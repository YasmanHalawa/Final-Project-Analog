/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
//import java.sql.JOptionPane;

/**
 *
 * @author Yasman
 */
public class db_Koneksi {
    private static Connection conn;
    public static Connection getKoneksi(){
        String host = "jdbc:mysql://localhost/database.java_gui",
               user = "root",
               pass = "";
               
        try {
            conn = (Connection) DriverManager.getConnection(host, user, pass);
            
        }catch (SQLException err){
    //      System.out.println("Koneksi ke Database Gagal" + err.getMessage());        
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
       return conn;
        
    }
}