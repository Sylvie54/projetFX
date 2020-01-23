/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class Connexion  {
    private static String saisie = JOptionPane.showInputDialog(null, "mysql ou sqlserver");
    private static int choix = Integer.parseInt(saisie);
    private static Connection conn = null;
    private static ResultSet Resultat = null;
   
    public static void AccesBase() throws Exception {
        try {
            final Properties prop = new Properties();
            if (choix == 1) {
                prop.load(Connexion.class.getClassLoader().getResourceAsStream("dataBase.properties"));
            }
            else {
                prop.load(Connexion.class.getClassLoader().getResourceAsStream("dataBaseSQLServer.properties"));
            }
            String driver =(prop.getProperty("sgbd.driver"));        
            String mysqlUser = prop.getProperty("sgbd.login");
            String mysqlPassword = prop.getProperty("sgbd.password");
            String connexionString = prop.getProperty("sgbd.connexionString"); 
            System.out.println("driver : " + prop.getProperty("sgbd.driver"));
            Class.forName(driver);
            conn = DriverManager.getConnection(connexionString, mysqlUser, mysqlPassword);  
        }    
        catch ( ClassNotFoundException e )
        {
            System.out.println("pb class forname");
            e.printStackTrace();
            
        }
        catch ( Exception e )
        {
            System.out.println("pb connexion");
            e.printStackTrace();
        }
        
    } 

    public static Connection getConn() {
        return conn;
    }
 
}
