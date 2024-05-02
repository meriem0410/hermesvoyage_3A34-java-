package edu.esprit.Controllers;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import edu.esprit.entities.User;
import edu.esprit.services.UserService;

import java.io.IOException;

public class DeleteuserController {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> roleColumn;
    @FXML
    private TableColumn<User, Boolean> verifiedColumn;
    @FXML
    private TableColumn<User, Boolean> bannedColumn;


    private final UserService userService = new UserService();
    private ObservableList<User> userList = FXCollections.observableArrayList();


    @FXML
    private Button deleteUserButton;
    @FXML
    private Button cancel;

    @FXML
    public void initialize() {
        // Initialize table columns
        emailColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEmail()));
        usernameColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getUsername()));
        roleColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getRole()));
        verifiedColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().isVerified()));
        bannedColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().isBanned()));

        // Populate table with existing users
        userList.addAll(userService.getAllData());
        userTable.setItems(userList);

        // Listen for selection changes and enable/disable buttons accordingly
        userTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    deleteUserButton.setDisable(newValue == null);
                }
        );
    }

    @FXML
    private void handleDeleteUser() {
        // Code to handle deleting the selected user
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            userService.deleteEntity(selectedUser);
            userList.remove(selectedUser);
        }
    }



    private void switchScene(String fxmlFile, ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Get the controller associated with the FXML file
            Object controller = loader.getController();

            // Create a new scene
            Scene scene = new Scene(root);

            // Load the CSS file
            String css = getClass().getResource("/pagination.css").toExternalForm();
            scene.getStylesheets().add(css);

            // Get the stage from the event source and set the new scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        switchScene("/Uiadmin.fxml", event);
    }
}
