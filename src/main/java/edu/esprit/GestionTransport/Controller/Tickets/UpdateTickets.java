package edu.esprit.GestionTransport.Controller.Tickets;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import edu.esprit.GestionTransport.Entity.Tickets;
import edu.esprit.GestionTransport.Service.TicketService;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateTickets implements Initializable {

    @FXML
    private TextField departField;

    @FXML
    private TextField arriveField;

    @FXML
    private TextField prixField;

    @FXML
    private TextField typeTransportField;

    @FXML
    private Button updateButton;

    private TicketService ticketService;
    private Tickets selectedTicket;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketService = new TicketService(); // Initialisation du service TicketService
    }

    public void initData(Tickets ticket) {
        selectedTicket = ticket;
        departField.setText(ticket.getDepart());
        arriveField.setText(ticket.getArrive());
        prixField.setText(String.valueOf(ticket.getPrix()));
        typeTransportField.setText(ticket.getTypeTransport());
    }

    @FXML
    private void handleUpdateButtonAction() {
        // Récupérer les nouvelles valeurs des champs
        String newDepart = departField.getText().trim();
        String newArrive = arriveField.getText().trim();
        double newPrix;
        try {
            newPrix = Double.parseDouble(prixField.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez saisir un prix valide.");
            return;
        }
        String newTypeTransport = typeTransportField.getText().trim();

        // Vérifier que les champs obligatoires ne sont pas vides
        if (newDepart.isEmpty() || newArrive.isEmpty() || newTypeTransport.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Mettre à jour l'objet Ticket sélectionné avec les nouvelles valeurs
        selectedTicket.setDepart(newDepart);
        selectedTicket.setArrive(newArrive);
        selectedTicket.setPrix(newPrix);
        selectedTicket.setTypeTransport(newTypeTransport);

        // Appeler le service pour mettre à jour le ticket
        boolean updated = ticketService.updateTicket(selectedTicket);

        if (updated) {
            showAlert("Succès", "Ticket mis à jour avec succès!");
        } else {
            showAlert("Erreur", "Erreur lors de la mise à jour du ticket. Veuillez réessayer.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
