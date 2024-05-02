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

public class UiuserController {

    @FXML
    private Button logout;

    @FXML
    private Label greetingText;

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
    private void logout(ActionEvent event) {
        // Clear user session upon logout
        UserSession.setUser(null);
        switchScene("/login.fxml", event);
    }

    private void switchScene(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
