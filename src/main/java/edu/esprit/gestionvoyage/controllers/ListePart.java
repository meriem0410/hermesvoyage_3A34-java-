package edu.esprit.gestionvoyage.controllers;

import edu.esprit.gestionvoyage.entities.Programme;
import edu.esprit.gestionvoyage.entities.Voyage;
import edu.esprit.gestionvoyage.services.ProgrammeService;
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
import java.util.Optional;

public class ListePart {
    @javafx.fxml.FXML
    private TableColumn<Programme, Date> colRetour;
    @javafx.fxml.FXML
    private Label btnListeVoy;
    @javafx.fxml.FXML
    private Label btnListeRes;
    @javafx.fxml.FXML
    private TableColumn<Programme, String> colAct;
    @javafx.fxml.FXML
    private TableColumn<Programme, Date> colDep;
    @javafx.fxml.FXML
    private Label btnAjoutProg;
    @javafx.fxml.FXML
    private TableView tableVoy;
    @javafx.fxml.FXML
    private Label btnListeProg;
    @javafx.fxml.FXML
    private TableColumn<Programme, String> colDesc;
    @javafx.fxml.FXML
    private Button btnModifier;
    @javafx.fxml.FXML
    private Label btnAjoutVoy;
    @javafx.fxml.FXML
    private TableColumn<Programme, String> colVoy;
    @javafx.fxml.FXML
    private Button btnSupprimer;

    ProgrammeService programmeService = new ProgrammeService();
    VoyageService voyageService = new VoyageService();

    @javafx.fxml.FXML
    public void initialize() {
        colDep.setCellValueFactory(new PropertyValueFactory<Programme, Date>("dateDepart"));
        colRetour.setCellValueFactory(new PropertyValueFactory<Programme, Date>("dateRetour"));
        colAct.setCellValueFactory(new PropertyValueFactory<Programme, String>("activite"));
        colDesc.setCellValueFactory(new PropertyValueFactory<Programme, String>("description"));
        colVoy.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Programme, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Programme, String> param) {
                Programme programme = param.getValue();
                try {
                    Voyage voyage = voyageService.getById(programme.getVoyageId());
                    return new SimpleStringProperty(voyage.getDestination()+" "+" le "+voyage.getDate());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        ObservableList<Programme> programmes = FXCollections.observableArrayList(programmeService.getAll());
        tableVoy.setItems(programmes);
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
    public void rowClicked(Event event) {
    }

    @javafx.fxml.FXML
    public void gotoSupprimerVoy(ActionEvent actionEvent) {
        Programme p = (Programme) tableVoy.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmer la suppression");
        alert.setContentText("Voulez cous supprimer le programme à "+p.getActivite()+" entre "+p.getDateDepart()+" et "+p.getDateRetour());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            programmeService.supp(p.getId());
            Alert alertDone = new Alert(Alert.AlertType.INFORMATION);
            alertDone.setTitle("Opération terminée");
            alertDone.setHeaderText("Programme supprimé avec succès.");
            alertDone.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void gotoModifierVoy(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/gestionvoyage/backOffice/programme/modifier.fxml"));
        Parent root = loader.load();

        ModifPart newController = loader.getController();
        newController.setObjectToSend((Programme) tableVoy.getSelectionModel().getSelectedItem());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
}