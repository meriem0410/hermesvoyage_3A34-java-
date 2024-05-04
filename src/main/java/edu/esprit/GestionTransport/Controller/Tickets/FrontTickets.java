package edu.esprit.GestionTransport.Controller.Tickets;
import com.gluonhq.charm.glisten.control.CardPane;
import com.gluonhq.charm.glisten.control.CardCell;
import edu.esprit.GestionTransport.Entity.Tickets;
import edu.esprit.GestionTransport.Service.TicketService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;


public class FrontTickets {

    @FXML
    private Button reserveTicketButton;

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

    @FXML
    private CardPane ListTickets;

    private final TicketService ticketService = new TicketService(); // Inject your TicketService here

    @FXML
    public void initialize() {
        List<Tickets> ticketsList = ticketService.getAllTickets();
        for (Tickets ticket : ticketsList) {
            HBox hBox = createTicketsCard(ticket);
            ListTickets.getItems().add(hBox); // Ajoutez au TableView, pas à la liste de tickets
        }
    }

    private HBox createTicketsCard(Tickets ticket) {
        ImageView imageView = new ImageView();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/esprit/Transport/asset/téléchargement.jpg")));
        imageView.setImage(image);

        Label departLabel = new Label("Depart: " + ticket.getDepart());
        Label arriveLabel = new Label("Arrive: " + ticket.getArrive());
        Label prixLabel = new Label("prix: " + ticket.getPrix());
        Label typeLabel = new Label("Type de transport: " + ticket.getTypeTransport());

        // Je suppose que vous voulez afficher les détails du ticket dans une seule HBox, donc vous pouvez les ajouter à un VBox
        VBox vBox = new VBox(departLabel, arriveLabel, prixLabel, typeLabel);
        HBox hBox = new HBox(imageView, vBox); // Ajoutez l'image et le VBox dans une HBox
        hBox.setSpacing(10); // Définissez l'espacement entre les éléments de la HBox

        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Your action when the HBox is clicked
                System.out.println("HBox clicked!");
                handleReserveTicketButtonAction(ticket);
            }
        });

        return hBox;
    }


    private void loadTicketsData() {
        List<Tickets> ticketsList = ticketService.getAllTickets();
        ticketsTableView.getItems().addAll(ticketsList);
    }


    private void handleReserveTicketButtonAction(Tickets selectedTicket) {
        //Tickets selectedTicket = ticketsTableView.getSelectionModel().getSelectedItem();
        //selectedTicket = ListTickets.focu
        //ListTickets.setOnMouseClicked();

        if (selectedTicket == null) {
            showAlert("Error", "Please select a ticket to reserve.");
            return;
        }

        String destinataire = "jridim64@gmail.com";
        String sujet = "Réservation de ticket";
        String contenu = "<h1>Réservation de ticket</h1>"
                + "<p><strong>Vous avez réservé le ticket avec l'ID :</strong> " + selectedTicket.getId() + "</p>"
                + "<p><strong>et de départ :</strong> " + escapeHtml(selectedTicket.getDepart()) + "</p>"
                + "<p><strong>et de arrive :</strong> " + escapeHtml(selectedTicket.getArrive()) + "</p>"
                + "<p><strong>et de type de Transport :</strong> " + escapeHtml(selectedTicket.getTypeTransport()) + "</p>"
                + "<p><strong>et de prix :</strong> " + selectedTicket.getPrix() + "</p>";

        String adresseExpeditrice = "slimenachref01@gmail.com";
        String motDePasse = "jkhi eeyp wpfz egyp";

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
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(adresseExpeditrice));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinataire));
            message.setSubject(sujet);
            message.setContent(contenu, "text/html; charset=utf-8");

            Transport.send(message);

            showAlert("Success", "L'e-mail de confirmation a été envoyé avec succès.");
        } catch (MessagingException e) {
            showAlert("Error", "Une erreur s'est produite lors de l'envoi de l'e-mail : " + e.getMessage());
            e.printStackTrace();
        }
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/Transport/FrontTransport.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    private String escapeHtml(String input) {
        return input.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&#39;");
    }
}
