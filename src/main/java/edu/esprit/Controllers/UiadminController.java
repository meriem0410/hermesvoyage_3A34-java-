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
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import edu.esprit.entities.User;
import edu.esprit.services.UserService;

import java.io.IOException;

public class UiadminController {

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
    @FXML
    private Button addUserButton;
    @FXML
    private Button deleteUserButton;
    @FXML
    private Button logout;

    @FXML
    private FlowPane userCardContainer;
    private VBox selectedCard;

    private UserService userService = new UserService();
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        userList.addAll(userService.getAllData());
        userList.forEach(this::loadUserCard);
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        switchScene("/adduser.fxml",event);
    }


    @FXML
    private void handleDeleteUser(ActionEvent event) {
        switchScene("/deleteuser.fxml", event);
    }


    private User getUserFromCard(VBox card) {
        for (Node node : userCardContainer.getChildren()) {
            if (node instanceof VBox && node.equals(card)) {
                UserCardController cardController = (UserCardController) ((FXMLLoader) ((VBox) node).getProperties().get("fxmlLoader")).getController();
                return userService.getUserByEmail(cardController.getemailLabel().getText());
            }
        }
        return null;
    }


    private void switchScene(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void logout(ActionEvent event) {
        switchScene("/login.fxml", event);
    }


    private void loadUserCard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserCard.fxml"));
            VBox card = loader.load();
            UserCardController cardController = loader.getController();
            cardController.initData(user);
            userCardContainer.getChildren().add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
