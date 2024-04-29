
package edu.esprit.gui;

import edu.esprit.entities.hebergement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import edu.esprit.services.Servicehebergement;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Ajouterhebergement {

    @FXML
    private TextField AdrInput;

    @FXML
    private TextField descInput;

    @FXML
    private TextField prixInput;

    @FXML
    private TextField AmnInput;

   // @FXML
    //private TextField txtimage;

    @FXML
    private ImageView imageView;

    @FXML
    private TextField maxgInput;

    //   @FXML
    //  private TextField txttype;
    private String imagePathInDatabase;
    @FXML
    void addhebergement(ActionEvent event) {
        try {
            String adresse = AdrInput.getText();
            String description = descInput.getText();
            String amenities = AmnInput.getText();
            // String image = txtimage.getText();
            int prix = Integer.parseInt(prixInput.getText());
            int maxguest = Integer.parseInt(maxgInput.getText());

            // Vérification de la validité de l'adresse
            if (adresse.isEmpty()) {
                throw new IllegalArgumentException("L'adresse ne peut pas être vide.");
            }
            if (adresse.length() < 5) {
                throw new IllegalArgumentException("L'adresse doit contenir au moins 5 caractères.");
            }

            // Validation supplémentaire des champs numériques
            if (prix < 0 || maxguest < 0) {
                throw new IllegalArgumentException("Les champs 'Tarif' et 'maxguestnibilité' doivent être des nombres positifs.");
            }
            if (imagePathInDatabase == null || imagePathInDatabase.isEmpty()) {
                throw new IllegalArgumentException("Veuillez sélectionner une image.");
            }

            // Obtenir le nom du fichier à partir du chemin complet de l'image
            File imageFile = new File(imagePathInDatabase);
            String imageName = imageFile.getName();

            Servicehebergement sl = new Servicehebergement();
            hebergement l = new hebergement(prix , maxguest, adresse,  description, amenities , imageName);
            sl.ajouter(l);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Afficherlogement.fxml"));
            Parent root = loader.load();

            // al.setRlist(sl.readAll().toString());
            descInput.getScene().setRoot(root);
        } catch (NumberFormatException e) {
            showAlert("Erreur de saisie", "Veuillez vous assurer que les champs 'Tarif' et 'maxguest' sont des nombres valides.");
        } catch (IllegalArgumentException e) {
            showAlert("Erreur de saisie", e.getMessage());
        } catch (SQLException e) {
            showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout du hebergement dans la base de données!");
        } catch (IOException e) {
            showAlert("Erreur d'E/S", "Une erreur s'est produite lors du chargement de la page Afficherhebergement.");
        }
    }

    @FXML
    void browseImageAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePathInDatabase = selectedFile.getAbsolutePath();
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    // Méthode utilitaire pour afficher une alerte
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }


    @FXML
    void initialize() {
        assert descInput != null : "fx:id=\"description\" was not injected: check your FXML file 'Ajouterhebergement.fxml'.";
        //assert txtequip != null : "fx:id=\"amenities\" was not injected: check your FXML file 'Ajouterhebergement.fxml'.";
        assert AdrInput != null : "fx:id=\"addresse\" was not injected: check your FXML file 'Ajouterhebergement.fxml'.";
        assert AmnInput != null : "fx:id=\"image\" was not injected: check your FXML file 'Ajouterhebergement.fxml'.";
        assert prixInput != null : "fx:id=\"prix\" was not injected: check your FXML file 'Ajouterhebergement.fxml'.";
        assert maxgInput != null : "fx:id=\"maxguest\" was not injected: check your FXML file 'Ajouterhebergement.fxml'.";
        //assert txttype != null : "fx:id=\"id_type\" was not injected: check your FXML file 'Ajouterhebergement.fxml'.";

    }
    public void gotoAjouterReservation(ActionEvent actionEvent) {
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

    public void gotoAjouterHeb (MouseEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLogement.fxml"));
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


    public void gotoListeHeb(MouseEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLogement.fxml"));
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

    public void gotoListReservation(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AfficherLogement.fxml"));
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


    public void saveHeb(ActionEvent actionEvent) {

        String Adresse = AdrInput.getText();
        String description = descInput.getText();
        String amenities = AmnInput.getText();
        Integer prix = Integer.valueOf((prixInput.getText()));
        Integer maxguest = Integer.valueOf((maxgInput.getText()));
        if (imagePathInDatabase == null || imagePathInDatabase.isEmpty()) {
            throw new IllegalArgumentException("Veuillez sélectionner une image.");
        }

        // Obtenir le nom du fichier à partir du chemin complet de l'image
        File imageFile = new File(imagePathInDatabase);
        String imageName = imageFile.getName();
        //String image = imageView.getText();
        //LocalDate datee = dateInput.getValue();
        //String type = typeInput.getSelectionModel().getSelectedItem().toString();
        // Create a hebergement object with the provided data
        hebergement hebergement = new hebergement(Adresse,description ,prix, maxguest, amenities ,imageName);

        // Create an instance of Servicehebergement
        Servicehebergement service = new Servicehebergement();

        try {
            // Call the ajouter() method with the hebergement object as an argument
            service.ajouter(hebergement);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération terminée");
        alert.setHeaderText("Voyage ajouté avec succès.");
        alert.showAndWait();
        } catch (SQLException e) {
            // Handle SQL exceptions
            showAlert("Erreur SQL", "Une erreur s'est produite lors de l'ajout du hebergement dans la base de données!");
        }
    }

}




