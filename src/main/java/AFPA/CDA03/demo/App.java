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
//import metier.classes.*;
import model.Person;
import controller.PersonOverviewController;
import javafx.stage.Modality;
import DAO.*;

//import metier.exceptions.*;

/**
 * Hello world!
 *
 */
public class App extends Application
{
    private static Stage primaryStage;
    private BorderPane rootLayout;
    private static ObservableList<Person> personData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        BaseSQLServer.AccesBase();
        BaseSQLServer.selectAll();
        
        initRootLayout();

        showPersonOverview();
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
     */
    public boolean showPersonEditDialog(Person person) {
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
    }
    public static void main( String[] args )
    {
         launch(args);
    }
}
