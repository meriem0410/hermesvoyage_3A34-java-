package edu.esprit.gui;


import edu.esprit.services.ServiceReservation;
import edu.esprit.services.Servicehebergement;
import edu.esprit.entities.hebergement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class Modifierhebergement {
    @FXML
    private TextField idTextField111;
    @FXML
    private TextField AdrTextField;

    @FXML
    private TextField EquipTextField1;

    @FXML
    private ImageView brandingImageView;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmerButton;

    @FXML
    private TextField AmnInput;
    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField maxguestTextField11;

    @FXML
    private TextField imageTextField11;

    @FXML
    private ImageView imageView;


    private String imagePathInDatabase;

    @FXML
    private TextField prixTextField;
    private hebergement selectedhebergement;
    private ServiceReservation servicehebergement;

    //hebergement v = new hebergement(Adresse, description, prix, maxguest, amenities, image);
    @FXML
    void cancelButtonOnAction(ActionEvent event) {

    }





    public void initData (hebergement selectedhebergement) {
        this.selectedhebergement=selectedhebergement;

        // Populate the fields in the UI with the data from selected

        AdrTextField.setText(selectedhebergement.getAdresse());
        AmnInput.setText(selectedhebergement.getAmenities());
        descriptionTextField.setText(selectedhebergement.getDescription());
        prixTextField.setText(String.valueOf(selectedhebergement.getPrix()));
        maxguestTextField11.setText(String.valueOf(selectedhebergement.getMaxguest()));
        //imageTextField11.setText(selectedhebergement.getImage());
        imagePathInDatabase = selectedhebergement.getImage();
        if (imagePathInDatabase != null && !imagePathInDatabase.isEmpty()) {
            Image image = new Image(new File(imagePathInDatabase).toURI().toString());
            imageView.setImage(image);
        }

    }
    /*@FXML
    void updateOne(ActionEvent event, hebergement hebergement) throws SQLException {

        String selectedAdresse = AdrTextField.getText();
        String selectedDescription = descriptionTextField.getText();
        String selectedamenities = EquipTextField1.getText();
        String selectedImage = imageTextField11.getText();
        float selectedprix = Float.parseFloat(prixTextField.getText());
            if (InputValidation.isTextFieldEmpty(selectedAdresse)) {
                InputValidation.showAlert("Input Error", null, "Please enter a valid Address");
            } else if (InputValidation.isTextFieldEmpty(selectedDescription)) {
                InputValidation.showAlert("Input Error", null, "Please enter a valid Description");
            } else {
                // Update the hebergement
                servicehebergement.modifier( selectedhebergement);
                System.out.println("hebergement updated successfully.");


             /*   if (Afficherhebergement!= null) {
                    Afficherhebergement.refreshList();
                }
                Stage stage = (Stage) cancelButton.getScene().getWindow();
                stage.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to update hebergement: " + e.getMessage());
        }
    }
*/




    public void gotoAjouterReservation(MouseEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterReservation.fxml"));
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

    public void GotoAjouterHebergement(MouseEvent actionEvent) {
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


    public void gotoAfficherHebergement(MouseEvent actionEvent) {
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

    public void rowClicked(MouseEvent mouseEvent) {
        TableView<?> tableView = (TableView<?>) mouseEvent.getSource();

        // Get the selected item from the TableView
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Perform actions based on the selected item
            System.out.println("Selected item: " + selectedItem.toString());
        } else {
            // No item was selected, do nothing or show an error message
            System.out.println("No item selected.");
        }
    }

    public void gotoModifierheb(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the ModifierVoyage screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierLogement.fxml"));
            Parent root = loader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Get the stage from the action event
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            // Set the scene on the stage
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle any potential IOException
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

    public void saveHebergement(ActionEvent actionEvent) throws SQLException {
        // Récupération des données modifiées
        selectedhebergement.setAdresse(AdrTextField.getText());
        selectedhebergement.setPrix(Integer.parseInt(prixTextField.getText()));
        selectedhebergement.setAmenities(AmnInput.getText());
        selectedhebergement.setDescription(descriptionTextField.getText());
        selectedhebergement.setMaxguest(Integer.parseInt(maxguestTextField11.getText()));
        //String adresse = AdrTextField.getText();
        //int prix = Integer.parseInt(prixTextField.getText());
        //String description = descriptionTextField.getText();
        //String amenities = AmnInput.getText();
        //int maxGuest = Integer.parseInt(maxguestTextField11.getText());
        Servicehebergement serviceReservation = new Servicehebergement();

        try {
            serviceReservation.modifier(selectedhebergement);


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Opération terminée");
            alert.setHeaderText("Voyage modifié avec succès.");
            alert.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Opération échouée");
            alert.setHeaderText("Erreur lors de la modification du voyage.");
            alert.setContentText(e.getMessage()); // Afficher le message d'erreur SQL
            alert.showAndWait();
        }
    }
    public void gotoSupprimerheb(ActionEvent actionEvent) {
        // Obtenez la ligne sélectionnée dans la TableView
        TableView<hebergement> tableView = (TableView<hebergement>) ((javafx.scene.Node) actionEvent.getSource()).getScene().lookup("#devisTableView");
        Object selectedItem = tableView.getSelectionModel().getSelectedItem();

        // Vérifiez si un élément est sélectionné
        if (selectedItem != null) {
            // Afficher une boîte de dialogue de confirmation
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText("Supprimer l'hébergement sélectionné ?");
            confirmationDialog.setContentText("Êtes-vous sûr de vouloir supprimer cet hébergement ? Cette action est irréversible.");

            // Affichez la boîte de dialogue de confirmation et attendez la réponse de l'utilisateur
            confirmationDialog.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    // Si l'utilisateur clique sur OK, supprimez l'élément sélectionné
                    // Ici, vous devez écrire du code pour supprimer l'hébergement de la base de données ou de votre liste
                    System.out.println("Suppression de l'hébergement sélectionné...");
                }
            });
        } else {
            // Affichez un message d'erreur si aucun élément n'est sélectionné
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Erreur de sélection");
            errorDialog.setHeaderText(null);
            errorDialog.setContentText("Veuillez sélectionner un hébergement à supprimer.");
            errorDialog.showAndWait();
        }
    }


}

