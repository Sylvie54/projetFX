/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Acer
 */
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import AFPA.CDA03.demo.App;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Person;
import util.DateUtil;
import DAO.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import javafx.scene.control.ButtonType;
import model.ExceptionsModele;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    // Reference to the main application.
    private App app;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
       firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
       lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
       
       // Clear person details.
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed.
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(App App) {
        this.app = App;

        // Add observable list data to the table
        personTable.setItems(App.getPersonData());
    }
    /**
 * Fills all text fields to show details about the person.
 * If the specified person is null, all text fields are cleared.
 *
 * @param person the person or null
 */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
    //        streetLabel.setText(person.getStreet());
    //        postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
    //        cityLabel.setText(person.getCity());
             birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
            lastNameLabel.setText("");
    //        streetLabel.setText("");
    //        postalCodeLabel.setText("");
    //        cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    @FXML
    private void handleQuitter() {
        System.exit(0);
    }
    @FXML
    private void handleDeletePerson() throws Exception {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.initOwner(App.getPrimaryStage());
            alert.setTitle("Suppression");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Etes vous sûr de vouloir sup cette personne ?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                personTable.getItems().remove(selectedIndex);
                try {
                    BaseSQLServer.delete(selectedPerson);
                }
                catch (Exception e)
                {
                    throw (new Exception ("pb delete personne"));
                }
            }
        }
        else {
           // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(App.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Person Selected");
        alert.setContentText("Please select a person in the table.");

        alert.showAndWait(); 
        }
    }
    /**
 * Called when the user clicks the new button. Opens a dialog to edit
 * details for a new person.
 */
@FXML
private void handleNewPerson() throws Exception,  InvocationTargetException {
    try {
    Person tempPerson = new Person();
    App app = new App();
    boolean okClicked = app.showPersonEditDialog(tempPerson);
    if (okClicked) {
        App.getPersonData().add(tempPerson);
        BaseSQLServer.insert(tempPerson);
    }
    }
    catch (InvocationTargetException ie) {
    Throwable target = null;
    throw (new InvocationTargetException(target, " exception handlenewperson throw setter long <2"));
    }
}

/**
 * Called when the user clicks the edit button. Opens a dialog to edit
 * details for the selected person.
 */
@FXML
private void handleEditPerson() throws Exception,  InvocationTargetException {
    Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
    if (selectedPerson != null) {
        String ancNom = selectedPerson.getFirstName();
        App app = new App();
        boolean okClicked = app.showPersonEditDialog(selectedPerson);
        if (okClicked) {
            showPersonDetails(selectedPerson);
            BaseSQLServer.update(selectedPerson, ancNom);
        }

    } else {
        // Nothing selected.
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(App.getPrimaryStage());
        alert.setTitle("No Selection");
        alert.setHeaderText("No Person Selected");
        alert.setContentText("Please select a person in the table.");

        alert.showAndWait();
    }
}
}
