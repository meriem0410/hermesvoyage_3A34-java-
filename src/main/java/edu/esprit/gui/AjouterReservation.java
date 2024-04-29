package edu.esprit.gui;

import edu.esprit.services.ServiceReservation;
import edu.esprit.services.ServiceUser;
import edu.esprit.services.Servicehebergement;
import edu.esprit.entities.reservation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class AjouterReservation {

    @FXML
    private DatePicker Checkout;

    @FXML
    private Button ReservationBtn;

    @FXML
    private ChoiceBox<String> hebergementId; // Change to String to hold event names

   @FXML
   private TextField nbguest;

    @FXML
    private DatePicker Checkin;

    @FXML
    private ChoiceBox<String> userId;

    private  ServiceReservation service = new ServiceReservation();
    private  Servicehebergement SE = new Servicehebergement();

    private final ServiceUser SU = new ServiceUser(); // Ajoutez l'instance de ServiceUser


    @FXML
    void OnClickedAjouter(ActionEvent event) {
        try {
            Date checkinDate = java.sql.Date.valueOf(Checkin.getValue());
            Date checkoutDate = java.sql.Date.valueOf(Checkout.getValue());
            int nombrePlaces = Integer.parseInt(nbguest.getText());
            String hebergementIdValue = hebergementId.getValue();
            String userIdValue = userId.getValue();
            boolean condition1 = false;
            boolean condition2 = false;
            boolean condition3 = false;


            // Vérification de la validité de l'adresse
            /*if (checkinDate > checkoutDate) {
                throw new IllegalArgumentException("condition 1 ");
                condition1 = true;
                System.out.print("condition 1" + condition1);
            } else if ((checkinDate == null) || (checkoutDate == null)) {
                throw new IllegalArgumentException("condition 1 ");
                condition1 = true;
                System.out.print("condition 1" + condition1);
            }


            // Validation supplémentaire des champs numériques
            if ((nombrePlaces == null) || (nombrePlaces < 1)) {
                throw new IllegalArgumentException("condition 2 ");
                condition2 = true;
                System.out.print("condition 2" + condition2);

            }
            if (hebergementIdValue == null) {
                throw new IllegalArgumentException("condition 3");
                condition3 = true;
                System.out.print("condition 3" + condition3);


            }*/

            if (condition1 == false && condition2 == false && condition3 == false) {
                reservation newReservation = new reservation();
                newReservation.setCheckin(checkinDate);
                newReservation.setCheckout(checkoutDate);
                newReservation.setNbguest(nombrePlaces);
                newReservation.setHebergement_id(hebergementIdValue);
                newReservation.setUser_id(userIdValue);
                service.ajouter(newReservation);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }


    @FXML
    public void initialize() throws SQLException {
        // Populate event names ChoiceBox
        List<String> eventNames = SE.getAllEventNames(); // Assuming you have a method in Servicehebergement to retrieve all event names
        hebergementId.setItems(FXCollections.observableArrayList(eventNames));



        // You may also need to populate userIds ChoiceBox here if needed
        List<String> roles = SU.getRoleByUserId();
        userId.setItems(FXCollections.observableArrayList(roles));
        assert Checkout != null : "fx:id=\"Checkout\" was not injected: check your FXML file 'Ajouterreservation.fxml'.";
        //assert txtequip != null : "fx:id=\"amenities\" was not injected: check your FXML file 'Ajouterhebergement.fxml'.";
        assert Checkin != null : "fx:id=\"Checkin\" was not injected: check your FXML file 'Ajouterhebergement.fxml'.";
        assert nbguest != null : "fx:id=\"nbguest\" was not injected: check your FXML file 'Ajouterhebergement.fxml'.";

       // List<Integer> userIds = SU.getAllUserIds(); // Assuming you have a method in ServiceUser to retrieve all user IDs
        //userId.setItems(FXCollections.observableArrayList(userIds.stream().map(String::valueOf).collect(Collectors.toList())));
       /* List<String> roles = getUserRoles(); // Call the method to get user roles
        userId.setItems(FXCollections.observableArrayList(roles));*/



    }

    private void afficherNotification(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}