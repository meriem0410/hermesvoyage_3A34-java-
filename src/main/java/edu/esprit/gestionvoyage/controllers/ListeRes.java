package edu.esprit.gestionvoyage.controllers;
import edu.esprit.gestionvoyage.entities.Programme;
import edu.esprit.gestionvoyage.entities.Reservation;
import edu.esprit.gestionvoyage.entities.Voyage;
import edu.esprit.gestionvoyage.services.ProgrammeService;
import edu.esprit.gestionvoyage.services.ReservationService;
import edu.esprit.gestionvoyage.services.VoyageService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class ListeRes
{
    @javafx.fxml.FXML
    private TableColumn<Reservation, String> colOrigine;
    @javafx.fxml.FXML
    private TableColumn<Reservation, Integer> colAge;
    @javafx.fxml.FXML
    private TableColumn<Reservation, Integer> colUtilisateur;
    @javafx.fxml.FXML
    private Label btnListeVoy;
    @javafx.fxml.FXML
    private Label btnListeRes;
    @javafx.fxml.FXML
    private TableColumn<Reservation, Integer> colNombre;
    @javafx.fxml.FXML
    private Button btnConfirm;
    @javafx.fxml.FXML
    private TableColumn<Reservation, String> colVoyage;
    @javafx.fxml.FXML
    private Label btnAjoutProg;
    @javafx.fxml.FXML
    private TableView tableVoy;
    @javafx.fxml.FXML
    private Label btnListeProg;
    @javafx.fxml.FXML
    private Label btnAjoutVoy;
    @javafx.fxml.FXML
    private TableColumn<Reservation, Boolean> colConfirm;
    @javafx.fxml.FXML
    private TableColumn<Reservation, String> colMail;

    ReservationService reservationService = new ReservationService();
    VoyageService voyageService = new VoyageService();
    @javafx.fxml.FXML
    private TextField searchText;

    @javafx.fxml.FXML
    public void initialize() {
        colNombre.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("number"));
        colUtilisateur.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("userId"));
        colAge.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("age"));
        colMail.setCellValueFactory(new PropertyValueFactory<Reservation, String>("mail"));
        colOrigine.setCellValueFactory(new PropertyValueFactory<Reservation, String>("origine"));
        colConfirm.setCellValueFactory(new PropertyValueFactory<Reservation, Boolean>("confirmed"));
        colVoyage.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Reservation, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Reservation, String> param) {
                Reservation reservation = param.getValue();
                try {
                    Voyage voyage = voyageService.getById(reservation.getPaysId());
                    return new SimpleStringProperty(voyage.getDestination()+" "+" le "+voyage.getDate());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ObservableList<Reservation> programmes = FXCollections.observableArrayList(reservationService.getAll());
        tableVoy.setItems(programmes);
    }

    @javafx.fxml.FXML
    public void gotoListeRes(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/gestionvoyage/backOffice/reservation/liste.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @javafx.fxml.FXML
    public void gotoListeVoy(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/gestionvoyage/backOffice/voyage/liste.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @javafx.fxml.FXML
    public void rowClicked(Event event) {
    }

    @javafx.fxml.FXML
    public void gotoAjoutVoy(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/gestionvoyage/backOffice/voyage/ajout.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @javafx.fxml.FXML
    public void gotoListeProg(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/gestionvoyage/backOffice/programme/liste.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @javafx.fxml.FXML
    public void gotoAjoutProg(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/gestionvoyage/backOffice/programme/ajout.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @javafx.fxml.FXML
    public void gotoConfirmRes(ActionEvent actionEvent) {
        Reservation r = (Reservation) tableVoy.getSelectionModel().getSelectedItem();
        if (r.isConfirmed()){
            Alert alertDone = new Alert(Alert.AlertType.INFORMATION);
            alertDone.setTitle("Opération terminée");
            alertDone.setHeaderText("Réservation déja confirmée.");
            alertDone.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirmation");
            alert.setContentText("Voulez cous confirmer la reservation de "+r.getUserId()+" pour le voyage "+r.getPaysId());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                r.setConfirmed(true);
                reservationService.modif(r);
                Alert alertDone = new Alert(Alert.AlertType.INFORMATION);
                alertDone.setTitle("Opération terminée");
                alertDone.setHeaderText("Réservation confirmée avec succès.");
                alertDone.showAndWait();
            }
        }
    }

    @javafx.fxml.FXML
    public void search(Event event) {
        String searchTerm = searchText.getText().trim().toLowerCase();
        List<Reservation> reservations = reservationService.getAll();
        List<Reservation> searchRes = new ArrayList<>();
        for (Reservation reservation:reservations){
            if (reservation.getMail().toLowerCase().contains(searchTerm))
                searchRes.add(reservation);
        }
        tableVoy.getItems().clear();
        tableVoy.getItems().addAll(searchRes);
    }
}