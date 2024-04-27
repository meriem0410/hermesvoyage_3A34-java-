package edu.esprit.GestionTransport.Controller.Tickets;

import edu.esprit.GestionTransport.Entity.Tickets;
import edu.esprit.GestionTransport.Service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javafx.scene.control.Button;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class FrontTickets {
    @FXML
    private Button reserveTicketButton;
    @FXML
    private Button RetourButton ;

    @FXML
    private TableView<Tickets> ticketsTableView;

    @FXML
    private TableColumn<Tickets, Integer> tickets_cell_id;

    @FXML
    private TableColumn<Tickets, String> tickets_cell_depart;

    @FXML
    private TableColumn<Tickets, String> tickets_cell_arrive;

    @FXML
    private TableColumn<Tickets, String> tickets_cell_type;

    @FXML
    private TableColumn<Tickets, Double> tickets_cell_prix;

    private final TicketService ticketService = new TicketService(); // Inject your TicketService here

    // Initialize method to load data into TableView
    @FXML
    public void initialize() {
        // Set cell value factories for each column
        tickets_cell_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tickets_cell_depart.setCellValueFactory(new PropertyValueFactory<>("depart"));
        tickets_cell_arrive.setCellValueFactory(new PropertyValueFactory<>("arrive"));
        tickets_cell_type.setCellValueFactory(new PropertyValueFactory<>("typeTransport"));
        tickets_cell_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));

        // Load tickets data into TableView
        loadTicketsData();
    }

    // Method to load data into TableView
    private void loadTicketsData() {
        List<Tickets> ticketsList = ticketService.getAllTickets();
        ticketsTableView.getItems().addAll(ticketsList);
    }

    // Method to handle reservation button action

    @FXML
    private void handleReserveTicketButtonAction(ActionEvent event) {
        Tickets selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();

        if (selectedTicket == null) {
            showAlert("Error", "Please select a ticket to reserve.");
            return;
        }

        // Envoyer un email
        String destinataire = "louay2.khelifi@gmail.com";
        String sujet = "Réservation de ticket";
        String contenu = "Vous avez réservé le ticket avec l'ID : " + selectedTicket.getId();

        // Remplacez "VotreAdresseEmail" par votre adresse e-mail Gmail
        String adresseExpeditrice = "slimenachref01@gmail.com";
        String motDePasse = "jkhi eeyp wpfz egyp";

        // Configuration de la session JavaMail
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(adresseExpeditrice, motDePasse);
                    }
                });

        try {
            // Création du message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(adresseExpeditrice));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setText(contenu);

            // Envoi du message
            Transport.send(message);

            showAlert("Success", "L'e-mail de confirmation a été envoyé avec succès.");
        } catch (MessagingException e) {
            showAlert("Error", "Une erreur s'est produite lors de l'envoi de l'e-mail : " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Method to show alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleRetourButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/Transport/FrontTransport.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    // Add more methods for other functionalities as needed
}
