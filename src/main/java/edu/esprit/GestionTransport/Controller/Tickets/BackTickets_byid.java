package edu.esprit.GestionTransport.Controller.Tickets;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import edu.esprit.GestionTransport.Entity.Tickets;
import edu.esprit.GestionTransport.Service.TicketService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BackTickets_byid implements Initializable {

    @FXML
    private JFXButton backButton;

    @FXML
    private Label idLabel;

    @FXML
    private Label departLabel;

    @FXML
    private Label arriveLabel;

    @FXML
    private Label prixLabel;

    @FXML
    private Label typeTransportLabel;

    @FXML
    private ImageView ticketImageView;

    private Tickets selectedTicket;
    private TicketService ticketService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketService = new TicketService(); // Création d'une instance de TicketService
    }

    public void initData(Tickets ticket) {
        selectedTicket = ticket;
        displayTicketDetails(selectedTicket);
    }

    private void displayTicketDetails(Tickets ticket) {
        idLabel.setText("ID: " + ticket.getId());
        departLabel.setText("Départ: " + ticket.getDepart());
        arriveLabel.setText("Arrivée: " + ticket.getArrive());
        prixLabel.setText("Prix: " + ticket.getPrix());
        typeTransportLabel.setText("Type de Transport: " + ticket.getTypeTransport());



    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        // Fermer la fenêtre actuelle
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
