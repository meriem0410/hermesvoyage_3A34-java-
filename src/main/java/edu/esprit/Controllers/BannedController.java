package edu.esprit.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;

public class BannedController {


    @FXML
    private Button logout;
    @FXML
    private WebView webView;

    @FXML
    private void initialize() {
        // Load HTML content from file into WebView
        WebEngine webEngine = webView.getEngine();
        String url = getClass().getResource("/Banned.html").toExternalForm();
        webEngine.load(url);
    }






    @FXML
    private void logout(ActionEvent event) {
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
