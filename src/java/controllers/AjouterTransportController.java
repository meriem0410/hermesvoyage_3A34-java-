package java.controllers;

import java.entity.Transport;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AjouterTransportController {

    @FXML
    private TextField txtTransport;

    @FXML
    private TextField txtDescription;

    @FXML
    void ajouterTransport(ActionEvent event) {
        String transport = txtTransport.getText();
        String description = txtDescription.getText();

        if (transport.isEmpty() || description.isEmpty()) {
            showAlert("Champs requis", "Veuillez remplir tous les champs.");
            return;
        }

        Transport newTransport = new Transport(transport, description);

        // Ici, vous pouvez appeler votre service ou gestionnaire pour ajouter le nouveau transport à votre système
        // Par exemple : transportService.ajouterTransport(newTransport);

        showAlert("Succès", "Le transport a été ajouté avec succès : " + newTransport);

        // Effacer les champs après l'ajout
        txtTransport.clear();
        txtDescription.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
