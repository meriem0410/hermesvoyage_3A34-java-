 package edu.esprit.gui;

 import javafx.beans.property.SimpleFloatProperty;
 import javafx.beans.property.SimpleStringProperty;
 import javafx.collections.FXCollections;
 import javafx.collections.ObservableList;
 import javafx.collections.transformation.FilteredList;
 import javafx.event.ActionEvent;
 import javafx.scene.Node;
 import javafx.scene.control.*;
 import javafx.scene.control.TextField;
 import javafx.fxml.FXML;
 import javafx.fxml.FXMLLoader;
 import javafx.fxml.Initializable;
 import javafx.scene.Parent;
 import javafx.scene.Scene;
 import javafx.scene.control.TableView;
 import javafx.scene.input.KeyEvent;
 import javafx.scene.input.MouseEvent;
 import javafx.stage.Stage;
 import edu.esprit.entities.hebergement;
 import javafx.stage.StageStyle;
 import edu.esprit.services.Servicehebergement;


 import java.io.IOException;
 import java.net.URL;
 import java.sql.SQLException;
 import java.util.List;
 import java.util.ResourceBundle;
 import java.util.function.Predicate;
 import javafx.collections.transformation.SortedList;

 public class Afficherhebergement implements Initializable {

     @FXML
     private TextField filtrefield;
     private ObservableList<hebergement> logList = FXCollections.observableArrayList();



     @FXML
     private TableColumn<hebergement, String> adresseCol;

     @FXML
     private Button cancelButton;

     @FXML
     private TableColumn<hebergement, String> descriptionCol;

     @FXML
     private TableColumn<hebergement, Integer> idCol;

     @FXML
     private TableColumn<hebergement, String> imageCol;

     @FXML
     private TableColumn<hebergement, String> amenitiesCol;
     @FXML
     private TableColumn<hebergement, String> maxguestCol;

     @FXML
     private TableView<hebergement> devisTableView;

     //@FXML
     //private TableColumn<hebergement, Integer> dispoCol;

     @FXML
     private TableColumn<hebergement, Float> prixCol;

     @FXML
     private Button supprimerButton;
     private FilteredList<hebergement> filteredList;

     public void refreshList() {
         try {
             populateTableView();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     @FXML
     void cancelButtonOnAction(ActionEvent event) {
         Stage stage = (Stage) cancelButton.getScene().getWindow();
         stage.close();
     }

     @Override
     public void initialize(URL url, ResourceBundle resourceBundle) {

         try {
             // Initialize filteredList with all hebergements
             Servicehebergement servicehebergement = new Servicehebergement();
             List<hebergement> hebergementList = servicehebergement.afficher();
             filteredList = new FilteredList<>(FXCollections.observableArrayList(hebergementList));

             // Populate TableView
             populateTableView();
         } catch (SQLException e) {
             e.printStackTrace();
         }
     }

     public void populateTableView() throws SQLException {
         // Retrieve data from the database
         Servicehebergement servicehebergement = new Servicehebergement();
         List<hebergement> hebergementList = servicehebergement.afficher();

         // Clear existing items in the TableView
         devisTableView.getItems().clear();

         // Set cell value factories for each column
       //  idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
         adresseCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresse()));
         descriptionCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
         imageCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getImage()));
         maxguestCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getMaxguest())));
         amenitiesCol.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getAmenities())));
         prixCol.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPrix()).asObject());
         // Add the retrieved data to the TableView
         devisTableView.getItems().addAll(hebergementList);
         //add
         devisTableView.setOnMouseClicked(event -> {
             if (event.getClickCount() == 2) { // Double-click detected
                 hebergement selectedhebergement = devisTableView.getSelectionModel().getSelectedItem();
                 if (selectedhebergement != null) {
                     int hebergementId = selectedhebergement.getId();
                     if (selectedhebergement != null) {
                         // Navigate to UpdateUser.fxml
                         navigateToUpdatehebergement(selectedhebergement);
                     }
                 }
             }
         });
     }



     private void navigateToUpdatehebergement(hebergement hebergement) {
         try {
             // Charger le fichier FXML de la fenêtre de mise à jour
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLogement.fxml"));
             Parent root = loader.load();

             // Accéder au contrôleur et passer le hebergement sélectionné à celui-ci
             Modifierhebergement controller = loader.getController();
             controller.initData(hebergement); // Pass the selected hebergement

             // Afficher la scène contenant le fichier FXML de la mise à jour
             Stage stage = new Stage();
             stage.setScene(new Scene(root));

             // Rafraîchir la TableView lorsque la fenêtre de mise à jour est fermée
             stage.setOnCloseRequest(event -> {
                 try {
                     populateTableView(); // Rafraîchir la TableView
                 } catch (SQLException e) {
                     e.printStackTrace();
                 }
             });

             // Fermer la fenêtre actuelle lorsque la fenêtre de mise à jour est affichée
            // Stage currentStage = (Stage) cancelButton.getScene().getWindow();
            // currentStage.close();

             // Afficher la fenêtre de mise à jour
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     @FXML
     private void AjoutOne(ActionEvent event) {
         try {
             // Load the Ajouterhebergement.fxml file
             Parent root = FXMLLoader.load(getClass().getResource("/AjouterLogement.fxml"));

             // Show the scene containing the Ajouterhebergement.fxml file
             Stage stage = new Stage();
             stage.setScene(new Scene(root));
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
     @FXML
     void getReponseDevis(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/Afficherlogement.fxml"));
         Stage pStage= new Stage();
         pStage.initStyle(StageStyle.UNDECORATED);
         pStage.setScene(new Scene(root, 667,556));
         pStage.show();
         Stage stage = (Stage) cancelButton.getScene().getWindow();
         stage.close();
     }

     public void ouvrirAjouterhebergement(ActionEvent actionEvent) {
         ouvrirFenetre("/AjouterLogement.fxml", "Ajouter");
     }
     private void ouvrirFenetre(String fxmlPath, String title) {
         try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLogement.fxml"));
             Parent root = loader.load();

             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.setTitle(title);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

     public void ouvrirModiferhebergement(ActionEvent actionEvent) {
         ouvrirFenetre("../Modifierhebergement.fxml", "Modifier");

     }
     private void ouvrirModiferhebergement(String fxmlPath, String title) {
         try {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("../Modifierhebergement.fxml"));
             Parent root = loader.load();

             Scene scene = new Scene(root);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.setTitle(title);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }


     public void gotoAjouterReservation(MouseEvent actionEvent) {
         try {
             // Load the FXML file
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReservation.fxml"));
             Parent root = loader.load();

             // Create the scene
             Scene scene = new Scene(root);

             // Get the stage from the action event
             Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

             // Set the scene on the stage
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle any potential IOException
         }
     }


     public void gotoFront(MouseEvent actionEvent) {
         try {
             // Load the FXML file
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLogF.fxml"));
             Parent root = loader.load();

             // Create the scene
             Scene scene = new Scene(root);

             // Get the stage from the action event
             Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

             // Set the scene on the stage
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle any potential IOException
         }
     }

     public void GotoAjouterHebergement(ActionEvent actionEvent) {
         try {
             // Load the FXML file
             FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterLogement.fxml"));
             Parent root = loader.load();

             // Create the scene
             Scene scene = new Scene(root);

             // Get the stage from the action event
             Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

             // Set the scene on the stage
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle any potential IOException
         }
     }

     public void gotoAjouterhebergement(MouseEvent actionEvent) {
         try {
             // Load the FXML file
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLogement.fxml"));
             Parent root = loader.load();

             // Create the scene
             Scene scene = new Scene(root);

             // Get the stage from the action event
             Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

             // Set the scene on the stage
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle any potential IOException
         }
     }
     public void gotoAfficherHebergement(MouseEvent actionEvent) {
         try {
             // Load the FXML file
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherLogement.fxml"));
             Parent root = loader.load();

             // Create the scene
             Scene scene = new Scene(root);

             // Get the stage from the action event
             Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

             // Set the scene on the stage
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle any potential IOException
         }
     }

     public void gotoListReservation(MouseEvent actionEvent) {
         try {
             // Load the FXML file
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontResrvation.fxml"));
             Parent root = loader.load();

             // Create the scene
             Scene scene = new Scene(root);

             // Get the stage from the action event
             Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

             // Set the scene on the stage
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle any potential IOException
         }
     }

     public void rowClicked(MouseEvent mouseEvent) {
         TableView<?> tableView = (TableView<?>) mouseEvent.getSource();

         // Get the selected item from the TableView
         Object selectedItem = tableView.getSelectionModel().getSelectedItem();

         if (selectedItem != null) {
             // Perform actions based on the selected item
             System.out.println("Selected item: " + selectedItem.toString());
         } else {
             // No item was selected, do nothing or show an error message
             System.out.println("No item selected.");
         }
     }

     @FXML
     private void handleFiltering(KeyEvent event) {
         // Obtenez le texte saisi dans le champ texte filtrefield
         String filterText = filtrefield.getText().toLowerCase();

         // Appliquez le filtre en fonction du texte saisi
         filteredList.setPredicate(logement -> {
             // Vérifiez si l'une des propriétés du logement contient le texte filtré
             return logement.getAdresse().toLowerCase().contains(filterText)
                     || logement.getDescription().toLowerCase().contains(filterText)
                     || logement.getAmenities().toLowerCase().contains(filterText)
                     || String.valueOf(logement.getId()).contains(filterText);
         });
         filterTableView();
     }
     @FXML
     private void filterTableView() {
         // Create a predicate to filter the items in the TableView
         Predicate<hebergement> filterPredicate = logement -> {
             // Check if any of the logement properties contain the filter text
             return logement.getDescription().toLowerCase().contains(filtrefield.getText().toLowerCase())
                     || logement.getAdresse().toLowerCase().contains(filtrefield.getText().toLowerCase())
                     || logement.getAmenities().toLowerCase().contains(filtrefield.getText().toLowerCase())
                     || String.valueOf(logement.getId()).contains(filtrefield.getText());
         };

         // Update the filtered list
         filteredList.setPredicate(filterPredicate);

         // Bind the filtered list to the TableView
         SortedList<hebergement> sortedData = new SortedList<>(filteredList);
         sortedData.comparatorProperty().bind(devisTableView.comparatorProperty());
         devisTableView.setItems(sortedData);
     }

     public void gotoModifierheb(ActionEvent actionEvent) {
         try {
             // Load the FXML file for the ModifierVoyage screen
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLogement.fxml"));
             Parent root = loader.load();

             // Create the scene
             Scene scene = new Scene(root);

             // Get the stage from the action event
             Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

             // Set the scene on the stage
             stage.setScene(scene);
             stage.show();
         } catch (IOException e) {
             e.printStackTrace();
             // Handle any potential IOException
         }
     }

     public void gotoSupprimerheb(ActionEvent actionEvent) {
         hebergement selectedLogement = devisTableView.getSelectionModel().getSelectedItem();
         if (selectedLogement != null) {
             Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
             confirmAlert.setTitle("Dialogue de Confirmation");
             confirmAlert.setHeaderText("Supprimer logement");
             confirmAlert.setContentText("Êtes-vous sûr de vouloir supprimer ce logement ?");
             confirmAlert.showAndWait().ifPresent(response -> {
                 if (response == ButtonType.OK) {
                     try {
                         Servicehebergement serviceLogement = new Servicehebergement();
                         serviceLogement.supprimer(selectedLogement);
                         Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                         successAlert.setTitle("Suppression Réussie");
                         successAlert.setHeaderText(null);
                         successAlert.setContentText("Logement supprimé avec succès.");
                         successAlert.showAndWait();
                         populateTableView();
                     } catch (SQLException ex) {
                         Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                         errorAlert.setTitle("Erreur de Suppression");
                         errorAlert.setHeaderText(null);
                         errorAlert.setContentText("Erreur lors de la suppression du logement : " + ex.getMessage());
                         errorAlert.showAndWait();
                     }
                 }
             });
         } else {
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setTitle("Aucune Sélection");
             alert.setHeaderText(null);
             alert.setContentText("Veuillez sélectionner un logement à supprimer.");
             alert.showAndWait();
         }
     }
 }
