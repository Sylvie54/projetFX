/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitaires;

import javafx.scene.control.Alert;
import controller.PersonEditDialogController;
import javafx.stage.Stage;

/**
 *
 * @author Acer
 */
public class Alertes {
    public static void alerte(Stage stage, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner( stage);
            alert.setTitle("problème");
            alert.setHeaderText("");
            alert.setContentText(message);

            alert.showAndWait();
    }
}
