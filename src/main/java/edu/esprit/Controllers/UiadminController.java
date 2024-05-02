package edu.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import edu.esprit.entities.User;
import edu.esprit.services.UserService;

import java.io.IOException;

public class UiadminController {

    @FXML
    private Pagination paginator;

    private final int itemsPerPage = 4; // Number of items per page

    private ObservableList<User> userList = FXCollections.observableArrayList();
    private ObservableList<VBox> userCards = FXCollections.observableArrayList();

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        userList.addAll(userService.getAllData());

        // Calculate the number of pages needed
        int pageCount = (int) Math.ceil((double) userList.size() / itemsPerPage);
        paginator.setPageCount(pageCount);
        paginator.setPageFactory(this::createPage);
    }

    private VBox createPage(int pageIndex) {
        int startIndex = pageIndex * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, userList.size());

        userCards.clear();

        for (int i = startIndex; i < endIndex; i++) {
            loadUserCard(userList.get(i));
        }

        FlowPane pageContent = new FlowPane();
        pageContent.setHgap(10);
        pageContent.setVgap(10);
        pageContent.getChildren().addAll(userCards);

        return new VBox(pageContent);
    }

    private void loadUserCard(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserCard.fxml"));
            VBox card = loader.load();
            UserCardController cardController = loader.getController();
            cardController.initData(user);
            userCards.add(card);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddUser(ActionEvent event) {
        switchScene("/adduser.fxml", event);
    }

    @FXML
    private void handleDeleteUser(ActionEvent event) {
        switchScene("/deleteuser.fxml", event);
    }

    @FXML
    private void logout(ActionEvent event) {
        switchScene("/login.fxml", event);
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
}
