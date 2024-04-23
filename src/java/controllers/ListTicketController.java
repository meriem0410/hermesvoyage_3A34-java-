package java.controllers;

import java.entity.tickets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ListTicketController implements Initializable {

    @FXML
    private AnchorPane listTicketPane;

    @FXML
    private TextField searchBar;

    @FXML
    private HBox ticketContainer;

    private ObservableList<tickets> ticketList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Charger les données initiales des tickets (simulées ici)
        ticketList.addAll(
                new tickets("Paris", "Berlin", 120.50f, new Date()),
                new tickets("London", "New York", 300.0f, new Date()),
                new tickets("Rome", "Tokyo", 500.75f, new Date())
        );

        displayTickets(ticketList); // Afficher tous les tickets au démarrage
    }

    @FXML
    void searchForTicket(ActionEvent event) {
        String searchText = searchBar.getText().toLowerCase().trim();

        List<tickets> filteredTickets = ticketList.stream()
                .filter(ticket -> ticket.getDepart().toLowerCase().contains(searchText)
                        || ticket.getArrive().toLowerCase().contains(searchText))
                .collect(Collectors.toList());

        displayTickets(filteredTickets);
    }

    private void displayTickets(List<tickets> tickets) {
        ticketContainer.getChildren().clear(); // Effacer le contenu précédent

        try {
            for (tickets ticket : tickets) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ticketItem.fxml"));
                Parent ticketItem = loader.load();

                TicketItemController itemController = loader.getController();
                itemController.setTicket(ticket);

                ticketContainer.getChildren().add(ticketItem);
            }
        } catch (IOException e) {
            Logger.getLogger(ListTicketController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
