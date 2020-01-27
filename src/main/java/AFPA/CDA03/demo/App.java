package AFPA.CDA03.demo;

import controller.PersonEditDialogController;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Person;
import controller.PersonOverviewController;
import javafx.stage.Modality;
import DAO.*;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import utilitaires.Alertes;
import model.ExceptionsModele;
//import metier.classes.*;
//import metier.exceptions.*;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    private static int choix;
    private static Stage primaryStage;
    private BorderPane rootLayout;
    private static ObservableList<Person> personData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        try {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");
        // sortie del'application par la croix du borderPane
        this.primaryStage.setOnCloseRequest(event ->
        {
            System.exit(0);
        });  
        
        initRootLayout();

        showPersonOverview();
        }
        catch (ExceptionsModele em) {
            Alertes.alerte(primaryStage, em.getMessage());
        }
        catch (IOException e) {
            Alertes.alerte(primaryStage, "un problème de fichier FXML est survenu");
            e.printStackTrace();
            System.exit(0);
        }
        catch (SQLException sqle) {
            Alertes.alerte(primaryStage, "un problème de base de données est survenu");
            sqle.printStackTrace();
            System.exit(0);
        
        }
        catch ( InvocationTargetException ie) {
            Alertes.alerte(primaryStage, "pb de saisie : " + ie.getMessage()); 
        }
        catch (Exception e) {
          Alertes.alerte(primaryStage, "un problème est survenu");
            e.printStackTrace();
            System.exit(0);
        }
    }
    public static ObservableList<Person> getPersonData() {
            return personData;
    }
    public static void ajouterPersonne(Person person) {
        personData.add(person);
    }
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() throws Exception, InvocationTargetException {
       
            // Load root layout from fxml file.
            try {
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(getClass().getClassLoader().getResource("RootLayout.fxml"));
             rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            // accès base de données
            Connexion.AccesBase();
            BaseSQLServer.selectAll();
            }
            catch (InvocationTargetException ie) {
                Throwable target = null;
                throw (new InvocationTargetException(target, " exception initrootlayout"));
            }
        
    }

    /**
     * Shows the person overview inside the root layout.
     * @throws java.lang.Exception
     */
    public void showPersonOverview() throws Exception,  InvocationTargetException {
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
        }
        catch (Exception e) {
            throw new Exception (e.getMessage());
        }
        }
        
    
    
    /**
     * Returns the main stage.
     * @return
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
        /**
     * Opens a dialog to edit details for the specified person. If the user
     * clicks OK, the changes are saved into the provided person object and true
     * is returned.
     *
     * @param person the person object to be edited
     * @return true if the user clicked OK, false otherwise.
     * @throws java.lang.Exception
     */
    public boolean showPersonEditDialog(Person person) throws Exception  {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("PersonEditDialog.fxml"));
           
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Person");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            // sortie del'application par la croix du borderPane
            dialogStage.setOnCloseRequest(event ->
            {
                System.exit(0);
            });    
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            // Set the person into the controller.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);
             
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        catch ( Exception e) {
            e.printStackTrace();
            System.out.println("app.showpersoneditdialog");
            return false;
            
            
        }
    }
    public static void main( String[] args )
    {
         launch(args);
    }
}
