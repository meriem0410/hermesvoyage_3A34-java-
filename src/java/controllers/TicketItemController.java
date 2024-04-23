package java.controllers;


import java.entity.tickets;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class TicketItemController {

    @FXML
    private Label departLabel;

    @FXML
    private Label arriveLabel;

    @FXML
    private Label prixLabel;

    @FXML
    private Label dateLabel;

    public void setTicket(tickets ticket) {
        departLabel.setText(ticket.getDepart());
        arriveLabel.setText(ticket.getArrive());
        prixLabel.setText(String.valueOf(ticket.getPrix()));
        dateLabel.setText(ticket.getDate().toString());
    }
}
