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
import DAO.BaseSQLServer;
import model.Person;
import java.util.Optional;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import util.DateUtil;
import utilitaires.Alertes;

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
    
    private final IntegerProperty pId  ;
    private final StringProperty pFirstName;
    private final StringProperty pLastName;
//    private final StringProperty pstreet;
//    private final IntegerProperty pPostalCode;
//    private final StringProperty pCity;
//    private final ObjectProperty<LocalDate> pBirthday;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public PersonOverviewController() {
        this(0,null, null);
    }
    
    public PersonOverviewController(int id,String firstName, String lastName) {
        this.pId = new SimpleIntegerProperty(id);
        this.pFirstName = new SimpleStringProperty(firstName);
        this.pLastName = new SimpleStringProperty(lastName);
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        
//       this.pid = new SimpleIntegerProperty(id);
//        this.firstName = new SimpleStringProperty(firstName);
//        this.lastName = new SimpleStringProperty(lastName);
//        
//        // Some initial dummy data, just for convenient testing.
//        this.street = new SimpleStringProperty("some street");
//        this.postalCode = new SimpleIntegerProperty(1234);
//        this.city = new SimpleStringProperty("some city");
//        this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1999, 2, 21));
        
        // Initialize the person table with the two columns.
   //    firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
   //    lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
       
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
                BaseSQLServer.delete(selectedPerson);
            }
        }
        else {
           // Nothing selected.
            Alertes.alerte(Alert.AlertType.WARNING,App.getPrimaryStage(), "pas de sélection", "No Person Selected","Please select a person in the table." );
        }
    }
    /**
 * Called when the user clicks the new button. Opens a dialog to edit
 * details for a new person.
 */
@FXML
private void handleNewPerson() throws Exception  {
    
    Person tempPerson = new Person();
     
    App app = new App();
    boolean okClicked = app.showPersonEditDialog(tempPerson);
    if (okClicked) {
        App.getPersonData().add(tempPerson);
        BaseSQLServer.insert(tempPerson);
    }
    
}

/**
 * Called when the user clicks the edit button. Opens a dialog to edit
 * details for the selected person.
 */
@FXML
private void handleEditPerson() throws Exception {
    Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
    if (selectedPerson != null) {
        
        int ancId = selectedPerson.getId();
        App app = new App();
        boolean okClicked = app.showPersonEditDialog(selectedPerson);
        if (okClicked) {
            showPersonDetails(selectedPerson);
            BaseSQLServer.update(selectedPerson, ancId);
        }
    }   else {
        // Nothing selected.
        Alertes.alerte(Alert.AlertType.WARNING,App.getPrimaryStage(), "pas de sélection", "No Person Selected","Please select a person in the table." );
        }
    }
public StringProperty firstNameProperty() {
    return pFirstName;
}
public StringProperty lastNameProperty() {
    return pLastName;
}
}
