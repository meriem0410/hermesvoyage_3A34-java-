package edu.esprit.gestionvoyage.controllers;
import com.gluonhq.charm.glisten.control.CardPane;
import edu.esprit.gestionvoyage.entities.Programme;
import edu.esprit.gestionvoyage.entities.Voyage;
import edu.esprit.gestionvoyage.services.ProgrammeService;
import edu.esprit.gestionvoyage.services.VoyageService;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ListeFront
{
    @javafx.fxml.FXML
    private CardPane listeVoyage;
    VoyageService voyageService = new VoyageService();
    ProgrammeService programmeService = new ProgrammeService();
    @javafx.fxml.FXML
    public void initialize() {
        List<Voyage> voyages = voyageService.getAll();
        for (Voyage voyage : voyages) {
            HBox hBox = createVoyageCard(voyage);
            listeVoyage.getItems().add(hBox);
        }
    }
    private HBox createVoyageCard(Voyage voyage) {
        ImageView imageView = new ImageView();
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/esprit/gestionvoyage/assets/logo.png")));
        imageView.setImage(image);

        Label placeLabel = new Label("Destination: " + voyage.getDestination());
        Label dateLabel = new Label("Date: " + voyage.getDate());
        Label typeLabel = new Label("Type: " + voyage.getType());

        String listProg = "||";
        List<Programme> programmes = programmeService.getProgrammesVoyage(voyage.getId());
        System.out.println(programmes);
        for (Programme programme: programmes){
            listProg += programme.getActivite()+": "+programme.getDescription()+"\n";
        }

        Button reservationButton = new Button("Book");
        reservationButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reservation Confirmation");
            alert.setHeaderText("Enter your email to confirm the reservation:");
            TextField emailField = new TextField();
            emailField.setPromptText("Email");
            alert.getDialogPane().setContent(emailField);
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String email = emailField.getText();
                try {
                    sendEmail(email,voyage);
                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        HBox hBox = new HBox(imageView, new VBox(placeLabel, dateLabel, typeLabel), new Label(listProg), reservationButton);
        hBox.setSpacing(10);

        return hBox;
    }

    private void sendEmail(String email,Voyage voyage) throws MessagingException {
        System.out.println("Sending email to " + email);
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("ploutos.connecte@gmail.com", "eprfxkbgketkgezf");
            }
        };
        Session session = Session.getDefaultInstance(properties, authenticator);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("ploutos.connecte@address"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Booking confirmed");
        message.setText("Your booking for the trip to "+voyage.getDestination()+" on "+voyage.getDate()+" has been confirmed.");
        Transport.send(message);
    }
}