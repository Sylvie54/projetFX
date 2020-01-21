/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import AFPA.CDA03.demo.App;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import model.Person;

/**
 *
 * @author Sylvie
 */
public class BaseMySQL {
    public static void AccesBase() {
    ResultSet Resultat=null;
        Connection conn = null;
        String mysqlUser = "root";
        String mysqlPassword = "";
        String connexionString = "jdbc:mysql://localhost/filrouge_active?"; 
        try {
        Class.forName("com.mysql.jdbc.Driver" );
       
        conn = DriverManager.getConnection(connexionString, mysqlUser, mysqlPassword);  
     //   conn = DriverManager.getConnection(connexionString);
        Statement stm = conn.createStatement(); // crÃ©ation d'un objet requÃªte directe 

           Resultat = stm.executeQuery("SELECT *  FROM client"); 
           while (Resultat.next())
            {
      //        System.out.println(Resultat.getString("RSCLIENT"));
                Person person = new Person(Resultat.getString("RSCLIENT"), Resultat.getString("ADRCLIENT"));
              //  personData.add(person);
                 App.ajouterPersonne(person);
            }
//           for (Person person : getPersonData()) {
//               System.out.println("lect : " + person.getFirstName());
//        }
           
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
}
