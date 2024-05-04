


package edu.esprit.gui;

import edu.esprit.entities.hebergement;
import edu.esprit.services.Servicehebergement;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import edu.esprit.entities.reservation;
import edu.esprit.services.ServiceReservation;
import edu.esprit.services.ServiceUser;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class FrontReservationController implements Initializable {
    @FXML
    public DatePicker CheckoutReservationUpdate;
    @FXML
    private ChoiceBox<String> EventIdUpdateReservation;

    @FXML
    private VBox ReservationVbox;

    @FXML
    private ScrollPane ScrollPaneReservation;

    @FXML
    private Button deleteReservationBtn;



    @FXML
    private TextField nbPlacesUpdate;

    @FXML
    private DatePicker nomReservationUpdate;

    @FXML
    private Button updateReservationBtn;

    @FXML
    private ChoiceBox<String> userIdUpdate;

    private ServiceReservation serviceReservation;
    private reservation selectedReservation;

    private ServiceUser SU;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceReservation = new ServiceReservation();
        SU = new ServiceUser();
        Servicehebergement SE = new Servicehebergement();
        List<String> roles = null;
        try {
            roles = SU.getRoleByUserId();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<String> eventNames = SE.getAllEventNames();
        EventIdUpdateReservation.setItems(FXCollections.observableArrayList(eventNames));
        userIdUpdate.setItems(FXCollections.observableArrayList(roles));
        loadReservations();
    }

    @FXML
    void OnClickedDeleteReservation(ActionEvent event) {
        if (selectedReservation != null) {
            try {
                serviceReservation.supprimer(selectedReservation);
                loadReservations();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void OnClickedReserverUpdate(ActionEvent event) {
        if (selectedReservation != null) {
            try {
                selectedReservation.setCheckin(String.valueOf(nomReservationUpdate.getValue()));
                selectedReservation.setNbguest(Integer.parseInt(nbPlacesUpdate.getText()));
                selectedReservation.setCheckout(String.valueOf(CheckoutReservationUpdate.getValue()));
                selectedReservation.setUser_id(userIdUpdate.getValue());
                selectedReservation.setHebergement_id(EventIdUpdateReservation.getValue());
                serviceReservation.modifier(selectedReservation);
                loadReservations();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    private void loadReservations() {
        ReservationVbox.getChildren().clear();
        //userIdUpdate.getItems().clear();
        EventIdUpdateReservation.getItems().clear();

        try {
            List<reservation> reservations = serviceReservation.afficher();

            //List<hebergement> hebergements = serviceHebergement.afficher();
            for (reservation reservation : reservations) {
                Label reservationLabel = new Label();
                reservationLabel.setText(
                        ", Nom: " + reservation.getCheckin() +
                        ", Nb Places: " + reservation.getNbguest() +
                        ", État: " + reservation.getCheckout() +
                        ", User ID: " + reservation.getUser_id() +
                        ", Événement ID: " + reservation.getHebergement_id());

                reservationLabel.setOnMouseClicked(event -> {
                    selectedReservation = reservation;
                    nomReservationUpdate.setValue(LocalDate.parse(String.valueOf(reservation.getCheckin())));
                    nbPlacesUpdate.setText(String.valueOf(reservation.getNbguest()));
                    CheckoutReservationUpdate.setValue(LocalDate.parse(String.valueOf(reservation.getCheckout())));
                    userIdUpdate.setValue(reservation.getUser_id());
                    EventIdUpdateReservation.setValue(reservation.getHebergement_id());
                });

                ReservationVbox.getChildren().add(reservationLabel);

                userIdUpdate.getItems().add(reservation.getUser_id());
                EventIdUpdateReservation.getItems().add(reservation.getHebergement_id());
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while loading reservations");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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


    public void gotoFront(MouseEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLogF.fxml"));
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

    public void GotoAjouterHebergement(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterLogement.fxml"));
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

    public void gotoAjouterhebergement(MouseEvent actionEvent) {
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

    public void gotoListReservation(MouseEvent actionEvent) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontResrvation.fxml"));
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLogement.fxml"));
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



}
