package edu.esprit.Controllers;

import edu.esprit.entities.User;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import edu.esprit.services.LoginService;
import java.io.IOException;



public class LoginController {

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginbtn;

    @FXML
    private Hyperlink signinbtn;


    @FXML
    static String username;
    @FXML
    private Label warning;


    private final LoginService loginService = new LoginService();



    @FXML
    void handleLogin(ActionEvent event) {
        warning.setText("");
        String userName = name.getText();
        String pass = password.getText();

        User user = loginService.getUserByUsername(userName);
        if (user != null ) {
            username = userName;
            String fxmlFile;
            String role = user.getRole();
            if (role.equals("admin")) {

                switchScene("/Uiadmin.fxml", event);

            } else if (role.equals("voyageur")) {
                switchScene("/Uiuser.fxml", event);
            }

            //showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + user.getUsername() + "!");

        } else {
            //showAlert(Alert.AlertType.INFORMATION, "Warning","Invalid Username or password!!!");
            warning.setText("Invalid Username or password!!!");




        }
    }

    @FXML
    void handleSignIn(ActionEvent event) {
        switchScene("/RegisterUser.fxml", event);
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
}
