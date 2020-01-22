/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import AFPA.CDA03.demo.App;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import model.Person;

/**
 *
 * @author Sylvie
 */
public class BaseSQLServer {
   private static Connection conn = null;
   private static ResultSet Resultat = null;
   
   /**
    * méthode d'accès à la base
    */
   public static void AccesBase() {
        
        try {
            
            final Properties prop = new Properties();
            prop.load(BaseMySQL.class.getClassLoader().getResourceAsStream("dataBase.properties"));
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
   /**
    * méthode de sélection de toutes les personnes
    */
    public static void selectAll() {
        try {
                Statement stm = conn.createStatement(); // crÃ©ation d'un objet requÃªte directe 

                Resultat = stm.executeQuery("SELECT *  FROM client"); 
                while (Resultat.next())
                {
                    Person person = new Person(Resultat.getString("nom"), Resultat.getString("prenom"));
                    App.ajouterPersonne(person);
                }
    
        }
        catch ( Exception e )
        {
            System.out.println("pb connexion");
            e.printStackTrace();
        }
    }
    /**
     * méthode d'insertion d'une personne
     * @param person Person
     */
    public static void insert(Person person) {
        String query = "INSERT INTO client ("
                + " nom,"
                + " prenom) VALUES ("
                + "?, ?)";
        try(PreparedStatement pstatement = conn.prepareStatement(query)){
            
            //  Recuperation des parametres pour le PreparedStatement
            pstatement.setString(1, person.getFirstName());
            pstatement.setString(2, person.getLastName());
            //  Execution du PreparedStatement pour insertion
            pstatement.executeUpdate();
        }catch(SQLException SQLex){
            System.out.println(SQLex.getMessage());
        }
    }
    /**
     * 
     * @param person Person
     * @param ancNom String ancien nom de la personne
     */
    public static void update(Person person, String ancNom) {
        String query = "UPDATE client "
                + "SET nom = ? "
                + ", prenom = ? "
                + "WHERE nom = ? ";
        try(PreparedStatement pstatement = conn.prepareStatement(query)){
            System.out.println("********     " + person.getFirstName()+ " " +ancNom+  "  *****************");
            //  Recuperation des parametres pour le PreparedStatement
            pstatement.setString(1, person.getFirstName());
            pstatement.setString(2, person.getLastName());
            pstatement.setString(3, ancNom);
            //  Execution du PreparedStatement pour modif
            pstatement.executeUpdate();
        }catch(SQLException SQLex){
            System.out.println(SQLex.getMessage());
        }
    }
    /**
     * méthode de supression d' une personne
     * @param person Person
     */
    public static void delete(Person person) {
        String query = "DELETE FROM client WHERE nom = ? ";
        try(PreparedStatement pstatement = conn.prepareStatement(query)){
           
            //  Recuperation des parametres pour le PreparedStatement
            pstatement.setString(1, person.getFirstName());
            //  Execution du PreparedStatement pour modif
            pstatement.executeUpdate();
        }catch(SQLException SQLex){
            System.out.println(SQLex.getMessage());
        }
    }
 
}
