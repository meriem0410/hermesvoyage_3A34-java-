package edu.esprit.GestionTransport.Controller.Transport;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import edu.esprit.GestionTransport.Entity.SessionManager;
import edu.esprit.GestionTransport.Entity.Transport;
import edu.esprit.GestionTransport.Service.TransportService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class UpdateTransport implements Initializable {

    @FXML
    private JFXButton Clear;

    @FXML
    private JFXButton UpdateTransport;

    @FXML
    private JFXButton transportButton;

    @FXML
    private JFXButton Logout;

    @FXML
    private TableView<Transport> transportTableView;

    @FXML
    private TableColumn<Transport, Integer> transport_cell_id;

    @FXML
    private TableColumn<Transport, String> transport_cell_type;

    @FXML
    private TableColumn<Transport, String> transport_cell_description;

    @FXML
    private TextField updateType;

    @FXML
    private TextArea updateDescription;

    @FXML
    private Label errorLabel;

    private TransportService transportService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transportService = new TransportService();
        fillTransportTableView();

        transportTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateFieldsWithData(newSelection);
            }
        });

        errorLabel.setVisible(false);
    }

    private void fillTransportTableView() {
        List<Transport> transportList = transportService.getAllTransports();

        transport_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        transport_cell_type.setCellValueFactory(new PropertyValueFactory<>("typeTransport"));
        transport_cell_description.setCellValueFactory(new PropertyValueFactory<>("description"));

        transportTableView.setItems(FXCollections.observableList(transportList));
    }

    void populateFieldsWithData(Transport transport) {
        updateType.setText(transport.getTypeTransport());
        updateDescription.setText(transport.getDescription());
    }

    @FXML
    private void handleClearButtonAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        updateType.clear();
        updateDescription.clear();
        errorLabel.setVisible(false);
        errorLabel.setText("");
    }

    @FXML
    private void handleUpdateTransportButtonAction(ActionEvent event) {
        Transport selectedTransport = transportTableView.getSelectionModel().getSelectedItem();

        if (selectedTransport == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucun transport sélectionné", "Veuillez sélectionner un transport à mettre à jour.");
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez-vous mettre à jour le transport?");
        alert.setContentText("Cette action ne peut pas être annulée.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            selectedTransport.setTypeTransport(updateType.getText());
            selectedTransport.setDescription(updateDescription.getText());

            transportService.updateTransport(selectedTransport);

            transportTableView.refresh();

            showAlert(Alert.AlertType.INFORMATION, "Succès", null, "Transport mis à jour avec succès.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String header, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void logout(ActionEvent event) {
        String currentSessionId = SessionManager.getCurrentSessionId();

        if (currentSessionId != null) {
            SessionManager.terminateSession(currentSessionId);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage currentStage = (Stage) Logout.getScene().getWindow();

            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);

            currentStage.setTitle("Login");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToTransport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu.esprit.GestionTransport/ListTransport_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) transportButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);

            currentStage.setTitle("Liste des Transports");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
