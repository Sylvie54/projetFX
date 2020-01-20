package AFPA.CDA03.demo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import metier.classes.*;
import model.Person;
import controller.PersonOverviewController;
//import metier.exceptions.*;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    private Stage primaryStage;
    private BorderPane rootLayout;
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
         //           System.out.println( client.getIdSociete() + " nom societe :  "+ client.getRsSociete()) ;
//        String driver ="com.microsoft.sqlserver.jdbc.SQLSERVERDRIVER"; 
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
                personData.add(person);
            }
            System.out.println("***********   avant lecture ************");
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
        initRootLayout();

        showPersonOverview();
        
        

        
    }
    public ObservableList<Person> getPersonData() {
            return personData;
    }
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(getClass().getClassLoader().getResource("RootLayout.fxml"));
             rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
          
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(getClass().getClassLoader().getResource("PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
          
        //    AnchorPane personOverview = loader.load(getClass().getClassLoader().getResource("PersonOverview.fxml"));
            
            // Set person overview into the center of root layout.
            rootLayout.setCenter(personOverview);
            
             // Give the controller access to the main app.
             
   
        PersonOverviewController controller = loader.getController();
        controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public static void main( String[] args )
    {
         launch(args);
    }
}
