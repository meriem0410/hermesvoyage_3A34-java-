package edu.esprit.GestionTransport.Controller.Tickets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import edu.esprit.GestionTransport.Entity.Tickets;
import edu.esprit.GestionTransport.Service.TicketService;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTicketsController implements Initializable {

    @FXML
    private TextField departField;

    @FXML
    private TextField arriveField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField typeTransportField;

    @FXML
    private Button addButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button RetourButton ;

    private TicketService ticketService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketService = new TicketService(); // Initialisation du service TicketService
    }

    @FXML
    private void handleAddButtonAction(ActionEvent event) {
        // Récupérer les valeurs des champs
        String depart = departField.getText().trim();
        String arrive = arriveField.getText().trim();
        double prix;
        try {
            prix = Double.parseDouble(prixField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez saisir un prix valide.");
            return;
        }
        String typeTransport = typeTransportField.getText().trim();

        // Vérifier que les champs obligatoires ne sont pas vides
        if (depart.isEmpty() || arrive.isEmpty() || typeTransport.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Créer un nouvel objet Ticket
        Tickets newTicket = new Tickets(depart, arrive, prix, typeTransport);

        // Appeler le service pour ajouter le ticket
        boolean added = ticketService.addTicket(newTicket);

        if (added) {
            showAlert("Succès", "Ticket ajouté avec succès!");
            handleClearButtonAction();
        } else {
            showAlert("Erreur", "Erreur lors de l'ajout du ticket. Veuillez réessayer.");
        }
    }

    @FXML
    private void handleClearButtonAction() {
        departField.clear();
        arriveField.clear();
        prixField.clear();
        typeTransportField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleRetourButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/Transport/Ticktes1Back.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
