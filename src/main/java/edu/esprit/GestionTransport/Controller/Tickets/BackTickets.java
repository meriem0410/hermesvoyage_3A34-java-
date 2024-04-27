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
import javafx.scene.control.Button;

public class BackTickets {
    @FXML
    private Button delete;
    @FXML
    private Button voirtickets;

    @FXML
    private Button addTicket;

    @FXML
    private Button update;

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

    // Method to handle delete button action
    @FXML
    private void handleDeleteButtonAction() {
        Tickets selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();

        if (selectedTicket == null) {
            showAlert("Error", "Please select a ticket to delete.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this ticket?");
        confirmationAlert.setContentText("This action cannot be undone.");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean deleted = ticketService.removeTicket(selectedTicket.getId());
                if (deleted) {
                    ticketsTableView.getItems().remove(selectedTicket);
                    showAlert("Success", "Ticket deleted successfully.");
                } else {
                    showAlert("Error", "Failed to delete ticket.");
                }
            }
        });
    }

    // Method to show alert dialog
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Method to handle navigation to another FXML view
    @FXML
    private void handleNavigateToOtherView(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/path_to_other_fxml_view.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleUpdateButtonAction(ActionEvent event) {
        // Implémentez le code pour gérer l'action du bouton de mise à jour ici
        // Vous pouvez obtenir l'élément sélectionné dans le TableView avec :
        // Tickets selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();
        // Ensuite, vous pouvez ouvrir une boîte de dialogue ou une nouvelle fenêtre pour permettre à l'utilisateur de mettre à jour les informations du billet sélectionné.
    }

    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Tickets selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();

        if (selectedTicket == null) {
            showAlert("Error", "Please select a ticket to delete.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText("Are you sure you want to delete this ticket?");
        confirmationAlert.setContentText("This action cannot be undone.");

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                boolean deleted = ticketService.removeTicket(selectedTicket.getId());
                if (deleted) {
                    ticketsTableView.getItems().remove(selectedTicket);
                    showAlert("Success", "Ticket deleted successfully.");
                } else {
                    showAlert("Error", "Failed to delete ticket.");
                }
            }
        });
    }
    @FXML

    private void handleAjouterTransportButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu/esprit/Transport/AjouterTickets.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handlevoirticketsButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/Transport/BackTransport.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Add more methods for other button actions, such as adding a new ticket, updating a ticket, etc.
}
