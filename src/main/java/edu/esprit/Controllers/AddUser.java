package edu.esprit.Controllers;

import edu.esprit.entities.User;
import edu.esprit.services.UserService;
import edu.esprit.utiles.MyConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AddUser {

    @FXML
    private TextField emailField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;
    private final UserService userService = new UserService();
    @FXML
    private Label wremail;
    @FXML
    private Label wrusr;
    @FXML
    private Label wrps;
    @FXML
    private Button cncl;
    @FXML
    private Button addusr;

    /*public void initialize() {
        // Initialize role combo box
        roleComboBox.getItems().addAll("Voyageur", "Admin");
    }*/

    @FXML
    private void handleAddUser(ActionEvent event) {
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();

        try {
            // Check if any field is empty
            if (emailField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please Fill all fields");
                alert.show();
                return;
            }

            // Validate email format
            if (!isValidEmail(email)) {
                wremail.setText("Invalid email format");
            } else if (isEmailTaken(email)) {
                wremail.setText("Email is already taken");
            } else {
                wremail.setText("");
            }

            // Validate password
            if (!isValidPassword(password)) {
                wrps.setText("Password must be at least 8 characters long \n and contain at least one digit and one capital letter");
                return;
            } else {
                wrps.setText("");
            }

            if (wrusr.getText().equals("") && wrps.getText().equals("") && wremail.getText().equals("")) {
                User user = new User();
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                user.setEmail(email);
                user.setUsername(username);
                user.setPassword(hashedPassword);
                user.setRole(role);
                userService.addEntity(user);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Account Created! Please verify your account");
                alert.show();
                switchScene("/Uiadmin.fxml", event);
            }
        } catch (Exception e) {
            // Show error message
            System.out.println(e.getStackTrace());
        }



    // Clear fields after adding user
        emailField.clear();
        usernameField.clear();
        passwordField.clear();
        roleComboBox.getSelectionModel().

    clearSelection();

}


    private boolean isValidEmail(String email) {
        // Regular expression pattern for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the pattern into a regex pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Check if the email matches the pattern
        return pattern.matcher(email).matches();
    }
    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one digit and one capital letter
        String passwordRegex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";

        // Compile the pattern into a regex pattern object
        Pattern pattern = Pattern.compile(passwordRegex);

        // Check if the password matches the pattern
        return pattern.matcher(password).matches();
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
    @FXML
    void Cancel(ActionEvent event) {
        switchScene("/Uiadmin.fxml", event);
    }

    private boolean isEmailTaken(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count > 0, email is already taken
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
