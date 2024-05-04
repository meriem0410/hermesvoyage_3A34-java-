package edu.esprit.gui;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import edu.esprit.services.Servicehebergement;
import edu.esprit.entities.hebergement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import edu.esprit.gui.CardV;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class AfficherLogF {

    @FXML
    private ScrollPane scroll;

    @FXML
    private FlowPane logementsFlowPane;

    private final Servicehebergement serviceLogement = new Servicehebergement();



    private List<hebergement> allLogements; // Liste de tous les logements
    private static final int pageSize = 2; // Nombre de logements par page
    private int currentPage = 0; // Page actuelle

    @FXML
    private void initialize() {
        loadLogement(); // Charge tous les logements initialement
    }
    private void loadLogement() {
        try {
            allLogements = serviceLogement.afficher();
            updatePage(currentPage);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur s'est produite lors du chargement des logements.");
        }
    }

  /*@FXML
    void handleDetailButton(ActionEvent event) throws IOException {
        for (Node node : logementsFlowPane.getChildren()) {
            if (node instanceof CardV) {
                CardV card = (CardV) node;
                hebergement selectedLogement = card.getLogement();
                if (selectedLogement != null) {
                    // Passer le logement sélectionné à l'interface DetailLogement
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../DetailLogement.fxml"));
                    Parent root = loader.load();

                    DetailLogement detailController = loader.getController();
                    detailController.initData(selectedLogement);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Détails du Logement");
                    stage.show();
                } else {
                    // Gérer le cas où aucun logement n'est sélectionné
                }
            }
        }

    }
*/public void previousPage() {
      if (currentPage > 0) {
          currentPage--;
          updatePage(currentPage);
      }
  }

    @FXML
    public void nextPage() {
        int totalPages = (int) Math.ceil((double) allLogements.size() / pageSize);
        if (currentPage < totalPages - 1) {
            currentPage++;
            updatePage(currentPage);
        }
    }

    private void sortByPrice(List<hebergement> list) {
        // Utiliser le comparateur pour trier la liste par prix
        list.sort(new PrixComparator());
    }


    // Méthode pour charger les logements triés par prix
    private void loadSortedLogements() {
        try {
            allLogements = serviceLogement.afficher();
            sortByPrice(allLogements); // Tri par prix
            updatePage(currentPage);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erreur", "Une erreur s'est produite lors du chargement et du tri des logements.");
        }
    }
    private void updatePage(int page) {
        logementsFlowPane.getChildren().clear();
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allLogements.size());

        for (int i = startIndex; i < endIndex; i++) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/CardV.fxml"));
                Node card = loader.load();
                CardV controller = loader.getController();
                controller.setLogement(allLogements.get(i));
                controller.setAfficherLogFController(this);
                logementsFlowPane.getChildren().add(card);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur", "Une erreur s'est produite lors de la création de la carte de logement.");
            }
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void handleSortByPrice(javafx.event.ActionEvent actionEvent) {
        sortByPrice(allLogements); // Tri par prix
        updatePage(currentPage); // Mettre à jour l'affichage
    }
}