package edu.esprit.Controllers;

import edu.esprit.entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileAdminController {


    @FXML
    private Button logout;

    @FXML
    private Label greetingText;

    @FXML
    private Button profilebtn;

    @FXML
    private Button dashboard;

    @FXML
    public void initialize() {
        // Retrieve user information from UserSession
        User loggedInUser = UserSession.getUser();

        if (loggedInUser != null) {
            // Display greeting with username
            greetingText.setText("Welcome " + loggedInUser.getUsername());
        } else {
            // Handle case where user is not logged in
            greetingText.setText("Welcome Guest");
        }
    }

    @FXML
    private void gotodashboard(ActionEvent event){

        switchScene("/Uiadmin.fxml", event);
    }

    @FXML
    private void logout(ActionEvent event) {
        // Clear user session upon logout
        UserSession.setUser(null);
        switchScene("/login.fxml", event);
    }

    @FXML
    private void gotoprofile(ActionEvent event){

        switchScene("/profile.fxml", event);
    }

    private void switchScene(String fxmlFile, ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the controller associated with the FXML file
            Object controller = loader.getController();

            // Create a new scene
            Scene scene = new Scene(root);

            // Load the CSS file
            String css = getClass().getResource("/pagination.css").toExternalForm();
            scene.getStylesheets().add(css);

            // Get the stage from the event source and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
