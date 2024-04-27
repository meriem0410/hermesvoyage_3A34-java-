package edu.esprit.GestionTransport.Controller.Tickets;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import edu.esprit.GestionTransport.Entity.Tickets;

import java.net.URL;
import java.util.ResourceBundle;

public class GridTicketsController implements Initializable {

    @FXML
    private GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addTicketToGrid(Tickets ticket, int rowIndex, int columnIndex) {
        // Création des labels pour afficher les informations du ticket
        Label departLabel = new Label("Départ : " + ticket.getDepart());
        Label arriveLabel = new Label("Arrivée : " + ticket.getArrive());
        Label prixLabel = new Label("Prix : " + ticket.getPrix());
        Label typeTransportLabel = new Label("Type de transport : " + ticket.getTypeTransport());

        // Ajout des labels au GridPane
        gridPane.add(departLabel, columnIndex, rowIndex);
        gridPane.add(arriveLabel, columnIndex, rowIndex + 1);
        gridPane.add(prixLabel, columnIndex, rowIndex + 2);
        gridPane.add(typeTransportLabel, columnIndex, rowIndex + 3);

        // Configuration de styles CSS pour les labels
        departLabel.getStyleClass().add("ticket-label");
        arriveLabel.getStyleClass().add("ticket-label");
        prixLabel.getStyleClass().add("ticket-label");
        typeTransportLabel.getStyleClass().add("ticket-label");
    }
}
