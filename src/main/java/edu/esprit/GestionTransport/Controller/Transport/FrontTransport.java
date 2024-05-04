package edu.esprit.GestionTransport.Controller.Transport;



import com.jfoenix.controls.JFXButton;
import edu.esprit.GestionTransport.Entity.Tickets;
import edu.esprit.GestionTransport.Service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import edu.esprit.GestionTransport.Entity.SessionManager;
import edu.esprit.GestionTransport.Entity.Transport;
import edu.esprit.GestionTransport.Service.TransportService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class FrontTransport implements Initializable {

    @FXML
    private Button voirtickets;
    @FXML
    private VBox transportVBox;


    @FXML
    private Button VoirStatistiquesButton ;
    @FXML
    private JFXButton transportButton;

    @FXML
    private JFXButton logout;

    @FXML
    private JFXButton volButton;

    @FXML
    private TableColumn<Transport, Integer> transport_cell_id;

    @FXML
    private TableColumn<Transport, String> transport_cell_type;

    @FXML
    private TableColumn<Transport, String> transport_cell_description;

    @FXML
    private TableView<Transport> transportTableView;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        addTransportShowListData();
    }



    @FXML
    private void handleDeleteButtonAction(ActionEvent event) {
        Transport selectedTransport = transportTableView.getSelectionModel().getSelectedItem();

        if (selectedTransport != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            alert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    TransportService transportService = new TransportService();
                    transportService.removeTransport(selectedTransport.getId());

                    transportTableView.getItems().remove(selectedTransport);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun transport sélectionné");
            alert.setContentText("Veuillez sélectionner un transport à supprimer.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleUpdateButtonAction(ActionEvent event) throws IOException {
        Transport selectedTransport = transportTableView.getSelectionModel().getSelectedItem();

        if (selectedTransport == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun transport sélectionné");
            alert.setContentText("Veuillez sélectionner un transport à modifier.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu.esprit.Transport/UpdateTransport.fxml"));
        Parent root = loader.load();

        UpdateTransport updateTransportController = loader.getController();
        updateTransportController.populateFieldsWithData(selectedTransport);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML

    private void handleAjouterTransportButtonAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/edu.esprit.Transport/AjouterTransport.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }


    @FXML
    private void handleVoirPlusTransportButtonAction(ActionEvent event) throws IOException {
        Transport selectedTransport = transportTableView.getSelectionModel().getSelectedItem();

        if (selectedTransport == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Aucun transport sélectionné");
            alert.setContentText("Veuillez sélectionner un transport à afficher.");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu.esprit.Transport/TransportByID_Back.fxml"));
        Parent root = loader.load();

        BackTransport_byid transportByIDBackController = loader.getController();
        transportByIDBackController.setTransportData(selectedTransport);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void logout(ActionEvent event) {
        String currentSessionId = SessionManager.getCurrentSessionId();

        if (currentSessionId != null) {
            SessionManager.terminateSession(currentSessionId);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/User/Login.fxml"));
            Parent loginRoot = loader.load();

            Stage currentStage = (Stage) logout.getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToVol(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vol/ListVol_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) volButton.getScene().getWindow();
            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.setTitle("Liste des Vols");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToTransport(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu.esprit.Transport/ListTransport_Back.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) transportButton.getScene().getWindow();
            Scene newScene = new Scene(root);

            currentStage.setScene(newScene);
            currentStage.setTitle("Liste des Transports");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleVoirTicketsButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/Transport/FrontTickets.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void handleVoirStatistiquesButtonAction(ActionEvent event)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/Transport/statistique.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    } private void addTransportShowListData() {
        TransportService transportService = new TransportService();
        List<Transport> transportList = transportService.getAllTransports();

        Accordion accordion = new Accordion(); // Créer un nouvel Accordion

        for (Transport transport : transportList) {
            TitledPane titledPane = new TitledPane();
            titledPane.setText(transport.getTypeTransport());

            // Création d'un contenu personnalisé pour le TitledPane
            VBox content = new VBox();
            Label descriptionLabel = new Label("Description: " + transport.getDescription());
            content.getChildren().add(descriptionLabel);

            titledPane.setContent(content);

            accordion.getPanes().add(titledPane); // Ajouter le TitledPane à l'Accordion
        }

        // Ajouter l'Accordion à votre VBox existant
        transportVBox.getChildren().add(accordion);
    }

}


