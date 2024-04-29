


package edu.esprit.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import edu.esprit.entities.reservation;
import edu.esprit.services.ServiceReservation;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FrontReservationController implements Initializable {

    @FXML
    private ChoiceBox<String> EventIdUpdateReservation;

    @FXML
    private VBox ReservationVbox;

    @FXML
    private ScrollPane ScrollPaneReservation;

    @FXML
    private Button deleteReservationBtn;

    @FXML
    private TextField checkoutReservationUpdate;

    @FXML
    private TextField nbPlacesUpdate;

    @FXML
    private TextField nomReservationUpdate;

    @FXML
    private Button updateReservationBtn;

    @FXML
    private ChoiceBox<String> userIdUpdate;

    private ServiceReservation serviceReservation;
    private reservation selectedReservation;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serviceReservation = new ServiceReservation();
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
                selectedReservation.setCheckin(nomReservationUpdate.getText());
                selectedReservation.setNbguest(Integer.parseInt(nbPlacesUpdate.getText()));
                selectedReservation.setCheckout(checkoutReservationUpdate.getText());
                //selectedReservation.setUser_id(userIdUpdate.getValue());
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
        userIdUpdate.getItems().clear();
        EventIdUpdateReservation.getItems().clear();

        try {
            List<reservation> reservations = serviceReservation.afficher();
            for (reservation reservation : reservations) {
                Label reservationLabel = new Label();
                reservationLabel.setText("Reservation ID: " + reservation.getId() +
                        ", Nom: " + reservation.getCheckin() +
                        ", Nb Places: " + reservation.getNbguest() +
                        ", État: " + reservation.getCheckout() +
                        ", User ID: " + reservation.getUser_id() +
                        ", Événement ID: " + reservation.getHebergement_id());

                reservationLabel.setOnMouseClicked(event -> {
                    selectedReservation = reservation;
                    nomReservationUpdate.setText(String.valueOf(reservation.getCheckin()));
                    nbPlacesUpdate.setText(String.valueOf(reservation.getNbguest()));
                    checkoutReservationUpdate.setText(String.valueOf(reservation.getCheckout()));
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
}
