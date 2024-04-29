package edu.esprit.gui;

import edu.esprit.entities.hebergement;
import javafx.fxml.FXML;
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

import java.io.IOException;
import java.io.InputStream;

public class CardV {
    public Button reserver;
    @FXML
    private Button Detail;

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
    private Label Equip;

    private hebergement logement;

    public hebergement getLogement() {
        return logement;
    }
    private AfficherLogF afficherLogFController;

    public void setAfficherLogFController(AfficherLogF controller) {
        this.afficherLogFController = controller;
    }

    public void setLogement(hebergement logement) {
        this.logement = logement;

        titrePub.setText(logement.getAdresse());
        //Equip.setText(logement.getAmenities());
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

    public void gotoAjouterReservation(MouseEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
            Parent root = loader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Get the stage from the action event
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential IOException
        }
    }

    /*@FXML
    void afficherDetails(ActionEvent event) {
        // Récupérer le logement associé à cette carte
        logement selectedLogement = getLogement();

        try {
            // Chargez le fichier FXML des détails du logement
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../DetailLogement.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));

            // Obtenez le contrôleur des détails du logement
            DetailLogement detailController = loader.getController();

            // Passez les informations du logement sélectionné au contrôleur des détails du logement
            detailController.setLogement(selectedLogement);

            // Afficher la fenêtre modale des détails du logement
            stage.setTitle("Détails du Logement");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}