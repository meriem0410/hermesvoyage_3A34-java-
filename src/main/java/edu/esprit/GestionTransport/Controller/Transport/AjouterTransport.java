package edu.esprit.GestionTransport.Controller.Transport;

import com.jfoenix.controls.JFXButton;
import edu.esprit.GestionTransport.Entity.Transport;
import edu.esprit.GestionTransport.Service.TransportService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjouterTransport implements Initializable {

    @FXML
    private Button AddTransport;

    @FXML
    private Button Clear;
    @FXML
    private Button RetourButton ;

    @FXML
    private TextField addType;

    @FXML
    private TextArea addDescription;

    @FXML
    private Label errorLabel;

    private TransportService transportService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setVisible(false);
        transportService = new TransportService(); // Initialisation du service de transport
    }

    @FXML
    void handleAddTransportButtonAction(ActionEvent event) {
        if (!validateInputs()) {
            return;
        }

        Transport newTransport = new Transport();
        newTransport.setTypeTransport(addType.getText());
        newTransport.setDescription(addDescription.getText());

        transportService.addTransport(newTransport);

        clearFields();
        errorLabel.setVisible(false);
    }

    @FXML
    private void handleClear(ActionEvent event) {
        clearFields();
        errorLabel.setVisible(false);
    }

    private void clearFields() {
        addType.clear();
        addDescription.clear();
    }

    private boolean validateInputs() {
        StringBuilder errors = new StringBuilder();

        if (!validateType()) {
            errors.append("Le type de transport doit comporter entre 4 et 10 caractères.\n");
        }

        if (!validateDescription()) {
            errors.append("La description doit comporter au moins 5 caractères.\n");
        }

        if (errors.length() > 0) {
            errorLabel.setText(errors.toString());
            errorLabel.setVisible(true);
            return false;
        }

        return true;
    }

    private boolean validateType() {
        String type = addType.getText().trim();
        return !type.isEmpty() && type.length() >= 4 && type.length() <= 10;
    }

    private boolean validateDescription() {
        String description = addDescription.getText().trim();
        return description.length() >= 5;
    }
    @FXML
    private void handleRetourButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/Transport/BackTransport.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
