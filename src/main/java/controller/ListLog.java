package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import entities.Evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.EvenementServices;

public class ListLog {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Evenement,Void > action;

    @FXML
    private TableView<Evenement> list_log;

    @FXML
    private TableColumn<Evenement, String> log_date;

    @FXML
    private TableColumn<Evenement,String> log_dure;

    @FXML
    private TableColumn<Evenement, String> log_heure;

    @FXML
    private TableColumn<Evenement, String> log_img;

    @FXML
    private TableColumn<Evenement, String> log_lieu;

    @FXML
    private TableColumn<Evenement, Integer> log_nbreparti;

    @FXML
    private TableColumn<Evenement, String> log_nom;

    @FXML
    private TableColumn<Evenement, String> log_oragnisateur;

    @FXML
    private TableColumn<Evenement, Double> log_prix;

    @FXML
    private TableColumn<Evenement, String> log_type;
    EvenementServices evenementServices = new EvenementServices();

    @FXML
    void initialize() {
        log_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        log_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        log_heure.setCellValueFactory(new PropertyValueFactory<>("heure"));
        log_dure.setCellValueFactory(new PropertyValueFactory<>("dure"));
        log_nbreparti.setCellValueFactory(new PropertyValueFactory<>("nbreparticipants"));
        log_lieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        log_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        log_oragnisateur.setCellValueFactory(new PropertyValueFactory<>("organisateur"));
        log_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        log_img.setCellValueFactory(new PropertyValueFactory<>("image"));
        ObservableList<Evenement> list = FXCollections.observableList(evenementServices.getAllData());
        action.setCellFactory(createActionsCellFactory());
        list_log.setItems(list);
    }
    private Callback<TableColumn<Evenement, Void>, TableCell<Evenement, Void>> createActionsCellFactory() {
        return new Callback<TableColumn<Evenement, Void>, TableCell<Evenement, Void>>() {
            @Override
            public TableCell<Evenement, Void> call(final TableColumn<Evenement, Void> param) {
                return new TableCell<Evenement, Void>() {
                    private final Button btnUpdate = new Button("Update");
                    private final Button btnDelete = new Button("Delete");

                    {
                        // Action for the update button
                        btnUpdate.setOnAction(event -> handleUpdate());
                        btnDelete.setOnAction(event -> handleDelete());
                    }

                    // This method is called whenever the cell needs to be updated
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            // Set the buttons in the cell
                            HBox buttons = new HBox(btnUpdate, btnDelete);
                            setGraphic(buttons);
                        }
                    }
                };
            }
        };
    }
    public void refreshList() {
        ObservableList<Evenement> updatedList = FXCollections.observableList(evenementServices.getAllData());
      list_log.setItems(updatedList);
    }
    @FXML
    void handleUpdate() {
        Evenement selectedProjet = list_log.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            // Load the update scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateLog.fxml"));
            Parent updateScene;
            try {
                updateScene = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Get the controller for the update scene
            UpdateLog updateController = loader.getController();
            updateController.setevenement(selectedProjet);

            // Create a new stage for the update scene
            Stage updateStage = new Stage();
            updateStage.setTitle("Update Evenement");
            updateStage.setScene(new Scene(updateScene));

            // Show the update stage
            updateStage.showAndWait();

            // After the update scene is closed, refresh the table view
            refreshList();
        } else {
            // No item selected, show an information alert
            showAlert(Alert.AlertType.INFORMATION, "Information", null, "Veuillez sélectionner un projet à mettre à jour.");
        }
    }

    @FXML
    void handleDelete() {
        Evenement selectedProjet = list_log.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            // Show confirmation dialog
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirmation de suppression");
            alert.setContentText("Voulez-vous vraiment supprimer ce projet?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // User confirmed, delete the selected item
               evenementServices.DeleteEntityWithConfirmation(selectedProjet);
                refreshList(); // Refresh the TableView
            }
        } else {
            // No item selected, show an information alert
            showAlert(Alert.AlertType.INFORMATION, "Information", null, "Veuillez sélectionner un projet à supprimer.");
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
