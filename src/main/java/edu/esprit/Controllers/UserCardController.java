package edu.esprit.Controllers;

import edu.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import edu.esprit.entities.User;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

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

    @FXML
    private Button ban;
    private final UserService userService = new UserService();

    static boolean newBannedStatus;



    private boolean selected = false;

    // Add more @FXML fields for other user properties as needed

    public void initData(User user) {
        emailLabel.setText(user.getEmail());
        usernameLabel.setText(user.getUsername());
        roleLabel.setText(user.getRole());
        if (user.isVerified()) {
            verifLabel.setText("Yes");
            ban.setText("Unban");
            ban.setStyle("-fx-background-color: WHITE; -fx-background-radius: 30;");
            ban.setTextFill(Paint.valueOf("Red"));
        } else {
            verifLabel.setText("No");
            ban.setText("Ban");
            ban.setStyle("-fx-background-color: RED; -fx-background-radius: 30;");
            ban.setTextFill(Paint.valueOf("White"));
        }
        if (user.isBanned()) {
            isBannedLabel.setText("Yes");

        } else {
            isBannedLabel.setText("No");
        }

        ban.setOnAction(event -> banuser(user));






    }


    @FXML
    void banuser(User user) {
        newBannedStatus = !user.isBanned(); // Toggle the banned status
        boolean updatedSuccessfully = userService.updateban(newBannedStatus, user.getEmail());

        if (updatedSuccessfully) {
            user.setBanned(newBannedStatus);

            if (user.isBanned()) {
                ban.setText("Unban");
                isBannedLabel.setText("Yes");
                ban.setStyle("-fx-background-color: WHITE; -fx-background-radius: 30;");
                ban.setTextFill(Paint.valueOf("Red"));
            } else {
                ban.setText("Ban");
                isBannedLabel.setText("No");
                ban.setStyle("-fx-background-color: RED; -fx-background-radius: 30;");
                ban.setTextFill(Paint.valueOf("White"));
            }
        } else {
            // Handle error, maybe show an alert to the user
            System.out.println("Failed to update banned status in the database.");
        }
    }



    public Label getemailLabel() {
        return emailLabel;
    }


}
