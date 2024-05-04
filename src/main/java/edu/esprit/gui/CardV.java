package edu.esprit.gui;

import edu.esprit.entities.hebergement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.web.WebView;

import java.io.IOException;
import java.io.InputStream;

public class CardV {

    public Button reserveButton;
    @FXML
    private AnchorPane cardPane;

    @FXML
    private Label descriptionPub;

    @FXML
    private ImageView imagePub;

    @FXML
    private Label tarifs;

    @FXML
    private Label titrePub;

    @FXML
    private Label Maxguest;

    @FXML
    private Label hebergementIdLabel;

    private hebergement logement;

    @FXML
    private Button mapButton;

    // Add other fields and methods as needed

    @FXML
    void mapButtonClicked(ActionEvent event) {
        // Get the address of the accommodation
        String address = titrePub.getText(); // Assuming Adresse is a Label displaying the address

        // Construct the URL for displaying the map
        String mapURL = "https://www.google.com/maps?q=" + address;

        // Open the map in a new window or dialog
        openMap(mapURL);
    }

    private void openMap(String url) {
        // Display the map in a WebView
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);

        // Create a new dialog to display the map
        Alert mapDialog = new Alert(AlertType.INFORMATION);
        mapDialog.setTitle("Map");
        mapDialog.setHeaderText(null);
        mapDialog.getDialogPane().setContent(webView);
        mapDialog.showAndWait();
    }

    public void setLogement(hebergement logement) {
        this.logement = logement;

        // Initialiser le bouton
       //reserveButton = new Button("Réserver");

        // Définir l'événement de clic et les données utilisateur
        reserveButton.setOnAction(this::reserveButtonClicked);
        reserveButton.setUserData(logement.getId());
        System.out.println(reserveButton.getUserData());
        // Ajouter le bouton à votre mise en page
       // cardPane.getChildren().add(reserveButton);

        // Récupérer l'ID et le prix de l'hébergement et les afficher, ou les utiliser comme nécessaire
        //String idHebergement = String.valueOf(logement.getId());
        //Integer prixHebergement = logement.getPrix();
        titrePub.setText(logement.getAdresse());
        descriptionPub.setText(logement.getDescription());
        tarifs.setText(String.valueOf(logement.getPrix()));

        String imagePath = "/img/" + logement.getImage();
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream != null) {
            Image image = new Image(imageStream);
            imagePub.setImage(image);
        } else {
            System.out.println("L'image n'a pas pu être chargée : " + imagePath);
        }
    }

    @FXML
    private void reserveButtonClicked(ActionEvent event) {

        Button clickedButton = (Button) event.getSource();
        Integer idHebergement = (Integer) clickedButton.getUserData();
        System.out.println(idHebergement);

        if (idHebergement != null) {
            // Rediriger vers le formulaire de réservation en passant l'ID de l'hébergement
            ouvrirFormulaireReservation(idHebergement);
        } else {
            System.out.println("User data is null");
        }
    }

    private void ouvrirFormulaireReservation(Integer idHebergement) {
        try {
            // Charger le FXML pour le formulaire de réservation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent root = loader.load();

            // Passer l'ID de l'hébergement au contrôleur du formulaire de réservation
            AjouterReservation controller = loader.getController();
            controller.setHebergement_id(idHebergement);

            // Afficher le formulaire de réservation dans une nouvelle fenêtre
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void gotoAjouterReservation(MouseEvent actionEvent) {
        try {
            // Charger le FXML pour le formulaire de réservation
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent root = loader.load();

            // Afficher le formulaire de réservation dans une nouvelle fenêtre
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAfficherLogFController(AfficherLogF afficherLogF) {
    }
}