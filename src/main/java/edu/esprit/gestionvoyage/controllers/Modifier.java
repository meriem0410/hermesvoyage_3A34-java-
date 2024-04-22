package edu.esprit.gestionvoyage.controllers;

import edu.esprit.gestionvoyage.entities.Voyage;
import edu.esprit.gestionvoyage.services.VoyageService;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Modifier {
    @javafx.fxml.FXML
    private Label btnAjoutVoy;
    @javafx.fxml.FXML
    private Label btnAjoutProg;
    @javafx.fxml.FXML
    private Label btnListeProg;
    @javafx.fxml.FXML
    private Label btnListeVoy;
    @javafx.fxml.FXML
    private Label btnListeRes;
    @javafx.fxml.FXML
    private TextField destInput;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private TextField prixInput;
    @javafx.fxml.FXML
    private ComboBox typeInput;
    @javafx.fxml.FXML
    private DatePicker dateInput;

    Voyage v = new Voyage();

    public void setObjectToSend(Voyage obj) {
        this.v = obj;
        destInput.setText(v.getDestination());
        prixInput.setText(String.valueOf(v.getPrix()));
        dateInput.setValue(v.getDate().toLocalDate());
        typeInput.setValue(v.getType());
    }

    VoyageService voyageService = new VoyageService();
    @javafx.fxml.FXML
    private String Organisé;
    @javafx.fxml.FXML
    private String Simple;

    @javafx.fxml.FXML
    public void gotoAjoutVoy(Event event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/gestionvoyage/backOffice/voyage/ajout.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
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
    public void saveVoyage(ActionEvent actionEvent) {
        Map<String, String> validationErrors = verifyInputs();

        if (!validationErrors.isEmpty()) {
            StringBuilder errorMessage = new StringBuilder();
            for (String error : validationErrors.values()) {
                errorMessage.append(error).append("\n");
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Please correct the following errors:");
            alert.setContentText(errorMessage.toString());
            alert.showAndWait();
            return;
        }
        v.setDestination(destInput.getText());
        v.setPrix(Float.parseFloat(prixInput.getText()));
        v.setDate(Date.valueOf(dateInput.getValue()));
        v.setType(typeInput.getSelectionModel().getSelectedItem().toString());
        voyageService.modif(v);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération terminée");
        alert.setHeaderText("Voyage modifié avec succès.");
        alert.showAndWait();
    }

    public Map<String, String> verifyInputs() {
        Map<String, String> validationErrors = new HashMap<>();
        if (destInput.getText() == null || Objects.equals(destInput.getText(), "")) {
            validationErrors.put("destination", "Veuillez indiquer une destination.");
        }
        if (prixInput.getText() == null || Objects.equals(prixInput.getText(), "")) {
            validationErrors.put("prix", "Veuillez indiquer un prix.");
        } else {
            try {
                Float.parseFloat(prixInput.getText());
            } catch (NumberFormatException e) {
                validationErrors.put("price", "Le prix n'est pas un nombre valide.");
            }
        }
        if (dateInput.getValue() == null) {
            validationErrors.put("date", "Veuillez sélectionner une date.");
        }
        if (typeInput.getSelectionModel().getSelectedItem() == null) {
            validationErrors.put("type", "Veuillez sélectionner un type.");
        }
        return validationErrors;
    }
}
