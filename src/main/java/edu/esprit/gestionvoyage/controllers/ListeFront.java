package edu.esprit.gestionvoyage.controllers;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.gluonhq.charm.glisten.control.CardPane;
import edu.esprit.gestionvoyage.entities.Programme;
import edu.esprit.gestionvoyage.entities.Voyage;
import edu.esprit.gestionvoyage.services.ProgrammeService;
import edu.esprit.gestionvoyage.services.VoyageService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.util.*;

import static com.twilio.example.Example.ACCOUNT_SID;
import static com.twilio.example.Example.AUTH_TOKEN;

public class ListeFront
{
    @FXML
    private WebView mapView;
    private static final String FROM_NUMBER = "28768680";
    private static final String GOOGLE_MAPS_URL = "https://www.google.com/maps/embed/v1/place?q=%s&key=YOUR_API_KEY";
    @javafx.fxml.FXML
    private CardPane listeVoyage;
    VoyageService voyageService = new VoyageService();
    ProgrammeService programmeService = new ProgrammeService();
    @javafx.fxml.FXML
    private TextField searchTextField;
    @javafx.fxml.FXML
    private Button triBtn;

    @javafx.fxml.FXML
    public void initialize() {
        List<Voyage> voyages = voyageService.getAll();
        for (Voyage voyage : voyages) {
            HBox hBox = createVoyageCard(voyage);
            listeVoyage.getItems().add(hBox);
        }
    }

    private HBox createVoyageCard(Voyage voyage) {
        WebView mapView = new WebView();
        WebEngine webEngine = mapView.getEngine();
        String mapUrl = String.format(GOOGLE_MAPS_URL, voyage.getDestination());
        webEngine.load(mapUrl);
        mapView.setPrefSize(300, 200);
        ImageView imageView = new ImageView();
        Random random = new Random();
        int randomNumber = random.nextInt(4) + 1;
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/edu/esprit/gestionvoyage/assets/image"+randomNumber+".jpg")));
        imageView.setImage(image);
        imageView.setFitHeight(120);
        imageView.setFitWidth(120);


        Label placeLabel = new Label("Destination: " + voyage.getDestination());
        Label dateLabel = new Label("Date: " + voyage.getDate());
        Label typeLabel = new Label("Type: " + voyage.getType());

        String listProg = "||";
        List<Programme> programmes = programmeService.getProgrammesVoyage(voyage.getId());
        for (Programme programme: programmes){
            listProg += programme.getActivite()+": "+programme.getDescription()+"\n";
        }

        Button reservationButton = new Button("Book");
        reservationButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Reservation Confirmation");
            alert.setHeaderText("Enter your phone number to confirm the reservation:");
            TextField phoneField = new TextField();
            phoneField.setPromptText("Phone number");
            alert.getDialogPane().setContent(phoneField);
            alert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                String phoneNumber = phoneField.getText();
                sendSMS(phoneNumber, voyage);
            }
        });

        HBox hBox = new HBox(imageView, new VBox(placeLabel, dateLabel, typeLabel), new Label(listProg), reservationButton);
        hBox.setSpacing(10);

        return hBox;
    }

    private void sendSMS(String phoneNumber, Voyage voyage) {
        System.out.println("Sending SMS to " + phoneNumber);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String messageBody = "Votre réservation pour le voyage à " + voyage.getDestination() + " le " + voyage.getDate() + "est en cours de traitement ";

        Message message = Message.creator(
                        new PhoneNumber(phoneNumber),
                        new PhoneNumber(FROM_NUMBER),
                        messageBody)
                .create();

        System.out.println("SMS sent successfully. SID: " + message.getSid());
    }

    @javafx.fxml.FXML
    public void search(Event event) {
        String searchTerm = searchTextField.getText().trim().toLowerCase();
        List<Voyage> voyages = voyageService.getAll();
        List<Voyage> searchRes = new ArrayList<>();
        for (Voyage v:voyages){
            if (v.getDestination().toLowerCase().contains(searchTerm))
                searchRes.add(v);
        }
        listeVoyage.getItems().clear();
        for (Voyage v : searchRes) {
            listeVoyage.getItems().add(createVoyageCard(v));
        }
    }

    @javafx.fxml.FXML
    public void sort(ActionEvent actionEvent) {
        List<Voyage> voyages = voyageService.getAll();
        voyages.sort(Comparator.comparing(Voyage::getPrix, Comparator.nullsFirst(Double::compareTo)));
        listeVoyage.getItems().clear();
        for (Voyage voyage : voyages) {
            listeVoyage.getItems().add(createVoyageCard(voyage));
        }
    }
}
