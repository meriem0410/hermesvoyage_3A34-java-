package edu.esprit.gui;

import edu.esprit.entities.hebergement;
import edu.esprit.services.ServiceReservation;
import edu.esprit.services.ServiceUser;
import edu.esprit.services.Servicehebergement;
import edu.esprit.entities.reservation;
import edu.esprit.utiles.DataSource;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;

import java.sql.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;

public class AjouterReservation {
    private Connection cnx;
    private Statement ste;

    public AjouterReservation() {
        cnx = DataSource.getInstance().getCon(); // Initialize the connection
    }

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

   // private  ServiceReservation SR = new ServiceReservation();
    private  Servicehebergement SE = new Servicehebergement();

    private final ServiceUser SU = new ServiceUser(); // Ajoutez l'instance de ServiceUser
    ServiceReservation SR = new ServiceReservation();
    private hebergement hebergement;

    private Integer idHebergement;
    private Integer prixHebergement;

    public void setId(Integer idHebergement) {
        this.idHebergement = idHebergement;
    }

    public void setPrix(Integer prixHebergement) {
        this.prixHebergement = prixHebergement;
    }


    @FXML
    void OnClickedAjouter(ActionEvent event) throws SQLException {
        Button ReservationBtn = (Button) event.getSource(); // Récupérer le bouton à partir de la source de l'événement
        ReservationBtn.setUserData(idHebergement);
        System.out.println(ReservationBtn.getUserData());
        // Vérifier si userData est valide
        if (ReservationBtn != null) {
            Object userData = ReservationBtn.getUserData();
            if (userData != null ) {
                int idHebergement = (int) userData;
                // Obtenir la date système
                LocalDate systemDate = LocalDate.now();

                // Convertir les valeurs des champs de date en LocalDate
                LocalDate checkinLocalDate = Checkin.getValue();
                LocalDate checkoutLocalDate = Checkout.getValue();
                // hebergementId.setDisable(true);
                // Convertir les valeurs des champs
                Date checkinDate = java.sql.Date.valueOf(Checkin.getValue());
                Date checkoutDate = java.sql.Date.valueOf(Checkout.getValue());
                Integer nombrePlaces = Integer.parseInt(nbguest.getText());
                Integer userIdValue = Integer.valueOf(userId.getValue());
// Obtenez l'objet hébergement correspondant à l'identifiant sélectionné
                hebergement hebergement = getHebergementById(String.valueOf(idHebergement));

// Vérifier si l'objet hébergement est null
                if (hebergement == null) {
                    afficherNotification("Erreur de saisie", "L'hébergement sélectionné est invalide.");
                    return;
                }

                try {
                    if (isHebergementAlreadyReserved(String.valueOf(idHebergement), checkinDate, checkoutDate)) {
                        afficherNotification("Erreur de réservation", "L'hébergement sélectionné est déjà réservé pour les dates saisies.");
                        return;
                    }
                } catch (SQLException e) {
                    // Gérer l'exception SQL
                    showAlert("Erreur SQL", e.getMessage());
                    return;
                }

// Vérifier si le nombre de places dépasse la capacité maximale d'hébergement
                if (nombrePlaces > hebergement.getMaxguest()) {
                    afficherNotification("Erreur de saisie", "Le nombre d'invités dépasse la capacité maximale de l'hébergement.");
                    return;
                }
// Vérifier si la date de checkin est avant la date système
                if (checkinLocalDate.isBefore(systemDate)) {
                    afficherNotification("Erreur de saisie", "La date de checkin ne peut pas être avant la date système.");
                    return;
                }

// Vérifier si la date de checkout est avant la date de checkin
                if (checkoutLocalDate.isBefore(checkinLocalDate)) {
                    afficherNotification("Erreur de saisie", "La date de checkout ne peut pas être avant la date de checkin.");
                    return;
                }

                // Vérifier si tous les champs sont remplis
                if (Checkin.getValue() == null || Checkout.getValue() == null || nbguest.getText().isEmpty() || hebergementId.getValue() == null || userId.getValue() == null) {
                    afficherNotification("Erreur de saisie", "Veuillez remplir tous les champs.");
                    return;
                }
                // Créer un nouvel objet reservation avec les valeurs récupérées
                reservation newReservation = new reservation( checkinDate,checkoutDate,nombrePlaces,idHebergement, String.valueOf(userIdValue));
                System.out.println(newReservation);
                int totalReservation = calculerTotal(hebergement, checkinLocalDate, checkoutLocalDate);
                System.out.println(totalReservation);
                try {
                    // Call the ajouter() method with the hebergement object as an argument
                    SR.ajouter(newReservation);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Opération terminée");
                    alert.setHeaderText("Voyage ajouté avec succès.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    //"Une erreur s'est produite lors de l'ajout du hebergement dans la base de données!"
                    // Handle SQL exceptions
                    showAlert("Erreur SQL", e.getMessage() );
                }
            } else {
                afficherNotification("Erreur", "Données utilisateur non valides.");
                return;
            }
        } else {
            afficherNotification("Erreur", "Bouton de réservation non trouvé.");
            return;
        }
    }

    private int calculerTotal(hebergement hebergement, LocalDate checkinDate, LocalDate checkoutDate) {
        // Calculer le nombre de jours de séjour
        long nbJours = ChronoUnit.DAYS.between(checkinDate, checkoutDate);

        // Vérifier si le nombre de jours est valide
        if (nbJours <= 0) {
            throw new IllegalArgumentException("La date de checkin doit être antérieure à la date de checkout.");
        }

        // Calculer le total en multipliant le prix par nuitée par le nombre de jours
        return hebergement.getPrix() * (int) nbJours;
    }
    public void reserveHebergement(int hebergementId) {
        // Effectuer les opérations nécessaires pour réserver l'hébergement avec l'ID donné
        // Vous pouvez utiliser hebergementId ici pour effectuer les opérations de réservation
    }
    private boolean isHebergementAlreadyReserved(String hebergementId, Date checkinDate, Date checkoutDate) throws SQLException {
        // Récupérer toutes les réservations pour l'hébergement sélectionné
        String query = "SELECT * FROM reservation WHERE hebergement_id = ? AND ((checkin BETWEEN ? AND ?) OR (checkout BETWEEN ? AND ?))";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, hebergementId);
            pst.setDate(2, (java.sql.Date) checkinDate);
            pst.setDate(3, (java.sql.Date) checkoutDate);
            pst.setDate(4, (java.sql.Date) checkinDate);
            pst.setDate(5, (java.sql.Date) checkoutDate);
            try (ResultSet rs = pst.executeQuery()) {
                // Si une réservation chevauche les dates saisies, renvoyer vrai
                return rs.next();
            }
        }
    }
    private hebergement getHebergementById(String hebergementId) throws SQLException {
        String query = "SELECT * FROM hebergement WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(query)) {
            pst.setString(1, hebergementId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    hebergement hebergement = new hebergement();
                    hebergement.setId(rs.getInt("id"));
                    hebergement.setAdresse(rs.getString("adresse"));
                    hebergement.setDescription(rs.getString("description"));
                    hebergement.setAmenities(rs.getString("amenities"));
                    hebergement.setImage(rs.getString("image"));
                    hebergement.setMaxguest(rs.getInt("maxguest"));
                    hebergement.setPrix(rs.getInt("prix"));
                    return hebergement;
                } else {
                    return null; // Gérer le cas où aucun hébergement avec cet ID n'est trouvé
                }
            }
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
      /*List<String> eventNames = SE.getAllEventNames(); // Assuming you have a method in Servicehebergement to retrieve all event names
        hebergementId.setItems(FXCollections.observableArrayList(eventNames));*/
        // Définir automatiquement l'ID de l'hébergement dans le formulaire
        hebergementId.setValue(String.valueOf(idHebergement));
        hebergementId.setDisable(true); // Optionnel : désactiver le choix de l'utilisateur



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


    public void setHebergement_id(Integer idHebergement) {
        this.idHebergement = idHebergement;
    }
}