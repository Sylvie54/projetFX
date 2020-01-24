/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javax.swing.JOptionPane;

/**
 *
 * @author Acer
 */
public class Connexion  {
   // private static String saisie = JOptionPane.showInputDialog(null, "mysql ou sqlserver");
  //  private static int choix = Integer.parseInt(saisie);
    private static Connection conn = null;
    private static ResultSet Resultat = null;
   
    public static void AccesBase() throws Exception {
        try {
            final Properties prop = new Properties();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Choix de la connexion");
            alert.setHeaderText("");
            alert.setContentText("Choose your option.");

            ButtonType buttonTypeOne = new ButtonType("Mysql");
            ButtonType buttonTypeTwo = new ButtonType("SQLServer");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne){
                prop.load(Connexion.class.getClassLoader().getResourceAsStream("dataBase.properties"));
            } else if (result.get() == buttonTypeTwo) {
                prop.load(Connexion.class.getClassLoader().getResourceAsStream("dataBaseSQLServer.properties"));
            } else {
                System.exit(0);
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
        catch (Exception e) {
            System.out.println("pb connexion");
        }
        
    } 

    public static Connection getConn() {
        return conn;
    }
 
}
