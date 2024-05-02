package edu.esprit.gestionvoyage.controllers;

import edu.esprit.gestionvoyage.entities.Voyage;
import edu.esprit.gestionvoyage.services.VoyageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Liste
{
    @javafx.fxml.FXML
    private Label btnAjoutProg;
    @javafx.fxml.FXML
    private TableView tableVoy;
    @javafx.fxml.FXML
    private Label btnListeProg;
    @javafx.fxml.FXML
    private Button btnModifier;
    @javafx.fxml.FXML
    private Label btnListeVoy;
    @javafx.fxml.FXML
    private Label btnListeRes;
    @javafx.fxml.FXML
    private Label btnAjoutVoy;
    @javafx.fxml.FXML
    private Button btnSupprimer;
    @javafx.fxml.FXML
    private TableColumn<Voyage, String> colType;
    @javafx.fxml.FXML
    private TableColumn<Voyage, String> colDest;
    @javafx.fxml.FXML
    private TableColumn<Voyage, Number> colPrix;
    @javafx.fxml.FXML
    private TableColumn<Voyage, Date> colDate;

    VoyageService voyageService = new VoyageService();
    @javafx.fxml.FXML
    private TableColumn<Voyage, Integer> idCol;
    @javafx.fxml.FXML
    private TextField searchText;

    @javafx.fxml.FXML
    public void initialize() {
        // Initialize the TableView columns
        colDest.setCellValueFactory(new PropertyValueFactory<Voyage,String>("destination"));
        colPrix.setCellValueFactory(new PropertyValueFactory<Voyage,Number>("prix"));
        colDate.setCellValueFactory(new PropertyValueFactory<Voyage,Date>("date"));
        colType.setCellValueFactory(new PropertyValueFactory<Voyage,String>("type"));
        idCol.setCellValueFactory(new PropertyValueFactory<Voyage,Integer>("id"));
        ObservableList<Voyage> voyages = FXCollections.observableArrayList(voyageService.getAll());
        idCol.setVisible(false);
        tableVoy.setItems(voyages);
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
        Voyage v = (Voyage) tableVoy.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmer la suppression");
        alert.setContentText("Voulez cous supprimer le voyage à "+v.getDestination()+" le "+v.getDate());
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            voyageService.supp(v.getId());
            Alert alertDone = new Alert(Alert.AlertType.INFORMATION);
            alertDone.setTitle("Opération terminée");
            alertDone.setHeaderText("Voyage supprimée avec succès.");
            alertDone.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void gotoModifierVoy(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/gestionvoyage/backOffice/voyage/modifier.fxml"));
        Parent root = loader.load();

        Modifier newController = loader.getController();
        newController.setObjectToSend((Voyage) tableVoy.getSelectionModel().getSelectedItem());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @javafx.fxml.FXML
    public void search(Event event) {
        String searchTerm = searchText.getText().trim().toLowerCase();
        List<Voyage> voyages = voyageService.getAll();
        List<Voyage> searchRes = new ArrayList<>();
        for (Voyage voyage:voyages){
            if (voyage.getDestination().toLowerCase().contains(searchTerm))
                searchRes.add(voyage);
        }
        tableVoy.getItems().clear();
        tableVoy.getItems().addAll(searchRes);
    }


    @javafx.fxml.FXML
    private void imprimerPDF(ActionEvent event) {
        ObservableList<Voyage> voyages = tableVoy.getItems();

        // Créer un sélecteur de fichier pour choisir où enregistrer le PDF
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(new Stage());

        if (selectedFile != null) {
            try {
                // Créer un document PDF
                Document document = new Document();
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));

                // Ouvrir le document pour écrire
                document.open();

                // Écrire les données des voyages dans le document
                for (Voyage voyage : voyages) {
                    document.add(new Paragraph("Destination: " + voyage.getDestination()));
                    document.add(new Paragraph("Prix: " + voyage.getPrix()));
                    document.add(new Paragraph("Date: " + voyage.getDate()));
                    document.add(new Paragraph("Type: " + voyage.getType()));
                    document.add(new Paragraph(" "));
                }

                // Fermer le document
                document.close();

                // Afficher un message de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("PDF généré");
                alert.setHeaderText(null);
                alert.setContentText("Le PDF a été généré avec succès.");
                alert.showAndWait();
            } catch (IOException | DocumentException e) {
                // Gérer les erreurs
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de la génération du PDF.");
                alert.showAndWait();
            }
        }
    }
}