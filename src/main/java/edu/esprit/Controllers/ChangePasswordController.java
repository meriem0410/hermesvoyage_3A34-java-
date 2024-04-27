package edu.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import edu.esprit.services.UserService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class ChangePasswordController implements Initializable {

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmNewPasswordField;

    @FXML
    private Button savebtn;
    @FXML
    private Label intro;

    private final UserService userService = new UserService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        intro.setText(ForgetPasswordController.adremail);
        System.out.println(ForgetPasswordController.adremail);

    }

    @FXML
    void saveChanges(ActionEvent event) {
        String newPassword = newPasswordField.getText();
        String confirmNewPassword = confirmNewPasswordField.getText();

        if (!isValidPassword(newPassword)) {
            showAlert(Alert.AlertType.ERROR,"Password Error","Password must be at least 8 characters long and contain at least one digit and one capital letter");
        }
        else if (!newPassword.equals(confirmNewPassword)) {
            showAlert(Alert.AlertType.ERROR, "Password Mismatch", "The new passwords do not match.");
        }else { userService.updatePassword(newPasswordField.getText(),ForgetPasswordController.adremail);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully.");

            switchScene("/login.fxml", event);


        }
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


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private boolean isValidPassword(String password) {

        String passwordRegex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return pattern.matcher(password).matches();
    }
}





