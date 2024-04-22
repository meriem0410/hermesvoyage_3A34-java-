package edu.esprit.gestionvoyage.controllers;

import edu.esprit.gestionvoyage.entities.Programme;
import edu.esprit.gestionvoyage.entities.Voyage;
import edu.esprit.gestionvoyage.services.ProgrammeService;
import edu.esprit.gestionvoyage.services.VoyageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class ModifPart
{
    @javafx.fxml.FXML
    private ComboBox VoyageInput;
    @javafx.fxml.FXML
    private Label btnListeVoy;
    @javafx.fxml.FXML
    private Label btnListeRes;
    @javafx.fxml.FXML
    private String Simple;
    @javafx.fxml.FXML
    private DatePicker retourInput;
    @javafx.fxml.FXML
    private Label btnAjoutProg;
    @javafx.fxml.FXML
    private Button btnSave;
    @javafx.fxml.FXML
    private Label btnListeProg;
    @javafx.fxml.FXML
    private DatePicker depInput;
    @javafx.fxml.FXML
    private Label btnAjoutVoy;
    @javafx.fxml.FXML
    private TextField actInput;
    @javafx.fxml.FXML
    private String Organisé;
    @javafx.fxml.FXML
    private TextField descInput;

    Programme v = new Programme();
    ProgrammeService programmeService = new ProgrammeService();
    VoyageService voyageService = new VoyageService();
    public void setObjectToSend(Programme obj) throws SQLException {
        this.v = obj;
        actInput.setText(v.getActivite());
        descInput.setText(v.getDescription());
        depInput.setValue(v.getDateDepart().toLocalDate());
        retourInput.setValue(v.getDateRetour().toLocalDate());
        VoyageInput.setValue(voyageService.getById(v.getVoyageId()));
    }

    @javafx.fxml.FXML
    public void initialize() {
        ObservableList<Voyage> voyages = FXCollections.observableArrayList(voyageService.getAll());
        VoyageInput.setItems(voyages);
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
        v.setActivite(actInput.getText());
        v.setDescription(descInput.getText());
        v.setDateDepart(Date.valueOf(depInput.getValue()));
        v.setDateRetour(Date.valueOf(retourInput.getValue()));
        v.setVoyageId(((Voyage) VoyageInput.getSelectionModel().getSelectedItem()).getId());
        programmeService.modif(v);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Opération terminée");
        alert.setHeaderText("Programme ajouté avec succès.");
        alert.showAndWait();
    }

    public Map<String, String> verifyInputs() {
        Map<String, String> validationErrors = new HashMap<>();
        if (actInput.getText() == null || Objects.equals(actInput.getText(), "")) {
            validationErrors.put("destination", "Veuillez indiquer une activité.");
        }
        if (descInput.getText() == null || Objects.equals(descInput.getText(), "")) {
            validationErrors.put("description", "Veuillez indiquer une description de l'activité.");
        }
        if (depInput.getValue() == null) {
            validationErrors.put("date depart", "Veuillez sélectionner une date de départ.");
        }
        if (retourInput.getValue() == null) {
            validationErrors.put("date retour", "Veuillez sélectionner une date de retour.");
        }
        if (VoyageInput.getSelectionModel().getSelectedItem() == null) {
            validationErrors.put("type", "Veuillez sélectionner un voyage.");
        }
        return validationErrors;
    }
}