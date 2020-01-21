/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import AFPA.CDA03.demo.App;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import model.Person;

/**
 *
 * @author Sylvie
 */
public class BaseSQLServer {
   private static Connection conn = null;
   private static ResultSet Resultat=null;
   
   public static void AccesBase() {
        
        try {
            String connexionString = "jdbc:sqlserver://localhost;database=Essai;user=sa;password=root;";     
            Class.forName("com.mysql.jdbc.Driver" );

         //   conn = DriverManager.getConnection(connexionString, mysqlUser, mysqlPassword);  
            conn = DriverManager.getConnection(connexionString);
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
    public static void selectAll() {
        try {
                Statement stm = conn.createStatement(); // crÃ©ation d'un objet requÃªte directe 

                Resultat = stm.executeQuery("SELECT *  FROM tableEssai"); 
                while (Resultat.next())
                {
                    Person person = new Person(Resultat.getString("nom"), Resultat.getString("prenom"));
                   // personData.add(person);
                   App.ajouterPersonne(person);
                }
    //           for (Person person : getPersonData()) {
    //               System.out.println("lect : " + person.getFirstName());
    //        }

        }
        catch ( Exception e )
        {
            System.out.println("pb connexion");
            e.printStackTrace();
        }
    }
    public static void insert(Person person) {
        String query = "INSERT INTO tableEssai ("
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
    public static void update(Person person, String ancNom) {
        String query = "UPDATE tableEssai "
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
 
}
