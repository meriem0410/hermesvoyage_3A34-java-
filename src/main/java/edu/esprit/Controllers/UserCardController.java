package edu.esprit.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import edu.esprit.entities.User;
import javafx.scene.layout.VBox;

import java.awt.event.MouseEvent;

public class UserCardController {
    @FXML
    private Label emailLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label roleLabel;
    @FXML
    private Label verifLabel;
    @FXML
    private Label isBannedLabel;

    @FXML
    private VBox userCard;

    private boolean selected = false;

    // Add more @FXML fields for other user properties as needed

    public void initData(User user) {
        emailLabel.setText(user.getEmail());
        usernameLabel.setText(user.getUsername());
        roleLabel.setText(user.getRole());
        if (user.isVerified()) {
            verifLabel.setText("Yes");
        } else {
            verifLabel.setText("No");
        }
        if (user.isBanned()) {
            isBannedLabel.setText("Yes");
        } else {
            isBannedLabel.setText("No");
        }

        userCard.setOnMouseClicked(event -> toggleSelection());



    }
    private void toggleSelection() {
        selected = !selected;
        // Change the style of the card based on selection state
        if (selected) {
            userCard.setStyle("-fx-border-color: blue; -fx-border-width: 2px;");
        } else {
            userCard.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        }
    }
    public Label getemailLabel() {
        return emailLabel;
    }


}
