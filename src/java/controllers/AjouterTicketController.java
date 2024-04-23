package java.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import java.entity.tickets;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class AjouterTicketController implements Initializable {

    @FXML
    private AnchorPane addTicketPane;

    @FXML
    private Button btnAddTicket;

    @FXML
    private Button btnClearTicket;

    @FXML
    private TextField txtDepart;

    @FXML
    private TextField txtArrive;

    @FXML
    private TextField txtPrix;

    @FXML
    private TextField txtDate;

    private static final int MAX_DIGITS = 5;

    @FXML
    void numberCodePostalTypedInput(KeyEvent event) {
        // Ici, vous pouvez implémenter une logique de validation pour le champ de prix ou de date si nécessaire
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Code d'initialisation, si nécessaire
    }

    @FXML
    private void ajouterTicket(ActionEvent event) {
        if (event.getSource() == btnAddTicket) {
            if (txtDepart.getText().isEmpty() || txtArrive.getText().isEmpty() || txtPrix.getText().isEmpty() || txtDate.getText().isEmpty()) {
                showAlert(Alert.AlertType.WARNING, "Information manquante", "Veuillez remplir tous les détails du ticket.");
            } else {
                try {
                    String depart = txtDepart.getText();
                    String arrive = txtArrive.getText();
                    float prix = Float.parseFloat(txtPrix.getText());
                    String dateString = txtDate.getText(); // Supposons que la date est saisie sous forme de chaîne

                    // Création d'un objet Date à partir de la chaîne (vous devrez peut-être utiliser LocalDate pour JavaFX)
                    // Exemple : LocalDate date = LocalDate.parse(dateString);

                    // Créer un nouvel objet Ticket
                    tickets tickets = new tickets(depart, arrive, prix, null); // Remplacez null par la date

                    // Ajouter le ticket (implémentation spécifique à votre application)
                    // ticketService.ajouterTicket(ticket);

                    // Afficher un message de confirmation
                    showAlert(Alert.AlertType.CONFIRMATION, "Ajouté avec succès", "Votre ticket a été ajouté avec succès.");

                    // Effacer les champs du formulaire
                    clearFields();

                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Veuillez saisir un prix valide.");
                }
            }
        } else if (event.getSource() == btnClearTicket) {
            clearFields();
        }
    }

    private void clearFields() {
        txtDepart.clear();
        txtArrive.clear();
        txtPrix.clear();
        txtDate.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Méthode pour naviguer vers une autre vue (non utilisée dans cet exemple)
    /*
    @FXML
    void naviguezVersAffichage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/gui/listTicket.fxml"));
            addTicketPane.getChildren().setAll(root);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    */
}
