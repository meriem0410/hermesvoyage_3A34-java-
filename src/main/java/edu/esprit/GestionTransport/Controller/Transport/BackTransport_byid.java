package edu.esprit.GestionTransport.Controller.Transport;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import edu.esprit.GestionTransport.Entity.Transport;
import edu.esprit.GestionTransport.Entity.SessionManager;
import edu.esprit.GestionTransport.Service.TransportService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BackTransport_byid implements Initializable {

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton destinationButton;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton volButton;

    @FXML
    private JFXButton update;

    @FXML
    private Label transportId;

    @FXML
    private Label transportType;

    @FXML
    private Label transportDescription;

    private Transport currentTransport;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setTransportData(Transport transport) {
        this.currentTransport = transport;
        transportId.setText(String.valueOf(transport.getId()));
        transportType.setText(transport.getTypeTransport());
        transportDescription.setText(transport.getDescription());
    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu.esprit.GestionTransport/UpdateTransport.fxml"));
        Parent root = loader.load();

        UpdateTransport updateTransportController = loader.getController();
        updateTransportController.populateFieldsWithData(currentTransport);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Êtes-vous sûr que vous voulez supprimer?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                TransportService transportService = new TransportService();
                transportService.removeTransport(currentTransport.getId());

                Stage stage = (Stage) delete.getScene().getWindow();
                stage.close();
            }
        });
    }

    @FXML
    private void logout(ActionEvent event) {
        String currentSessionId = SessionManager.getCurrentSessionId();

        if (currentSessionId != null) {
            SessionManager.terminateSession(currentSessionId);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage currentStage = (Stage) logout.getScene().getWindow();

            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);

            currentStage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToVol(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/ListVol_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) volButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("List of Vols");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToTransport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu.esprit.GestionTransport/ListTransport_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) destinationButton.getScene().getWindow();
            Scene newScene = new Scene(root);

            currentStage.setScene(newScene);

            currentStage.setTitle("List of Transports");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
