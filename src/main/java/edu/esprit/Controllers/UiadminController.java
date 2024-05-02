package edu.esprit.Controllers;

import edu.esprit.entities.User;
import edu.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UiadminController {

    @FXML
    private Pagination paginator;

    @FXML
    private TextField searchField;

    private UserService userService = new UserService();

    private final List<User> userList = new ArrayList<>();
    private final List<VBox> userCards = new ArrayList<>();
    private final int itemsPerPage = 4;

    @FXML
    private void initialize() {
        userList.addAll(userService.getAllData());

        // Calculate the number of pages needed
        int pageCount = (int) Math.ceil((double) userList.size() / itemsPerPage);
        paginator.setPageCount(pageCount);
        paginator.setPageFactory(this::createPage);

        // Add a listener to the search field to update the pagination when the text changes
        searchField.textProperty().addListener((observable, oldValue, newValue) -> updatePagination());
    }

    private void updatePagination() {
        String searchText = searchField.getText().toLowerCase();

        // Filter the userList based on the search text
        List<User> filteredList = userList.stream()
                .filter(user -> user.getEmail().toLowerCase().contains(searchText)) // Assuming name is a property of User class
                .collect(Collectors.toList());

        // Calculate the number of pages needed for the filtered list
        int pageCount = (int) Math.ceil((double) filteredList.size() / itemsPerPage);
        paginator.setPageCount(pageCount);
        paginator.setPageFactory(pageIndex -> createPage(pageIndex, filteredList));
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

    private VBox createPage(int pageIndex, List<User> userList) {
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
        UserSession.setUser(null);
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
