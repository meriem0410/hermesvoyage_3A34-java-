package edu.esprit.Controllers;

import edu.esprit.entities.User;
import edu.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

public class ProfileController {

    @FXML
    private ImageView profileImageView;
    @FXML
    Label emailLabel;
    @FXML
    Label usernameLabel;
    @FXML
    Label verificationStatusLabel;
    @FXML
    private Button logoutbtn;

    @FXML
    private ImageView rollback;
    private final UserService userService = new UserService();

    static byte[] pic;
    @FXML
    private Label info;
    static boolean verif;

    User loggedinuser = UserSession.getUser();
    String role = loggedinuser.getRole();


    @FXML
    public void initialize() {
        User loggedinuser = UserSession.getUser();
        System.out.println(Arrays.toString(loggedinuser.getPictureData()));
        info.setText(loggedinuser.getUsername()+"'s Profile");
        emailLabel.setText(loggedinuser.getEmail());
        usernameLabel.setText(loggedinuser.getUsername());
        verif = userService.getVerified(loggedinuser.getEmail());
        pic = userService.getProfilePictureData(loggedinuser.getEmail());
        verificationStatusLabel.setText(verif ? "Verified" : "Not Verified");
        if (pic != null) {
            Image profileImage = new Image(new ByteArrayInputStream(pic));
            profileImageView.setImage(profileImage);}
        else {
            profileImageView.setImage(new Image("/default_picture.png"));
        }

    }

    @FXML
    private void logout(ActionEvent event) {
        // Clear user session upon logout
        UserSession.setUser(null);
        switchScene("/login.fxml", event);
    }

    @FXML
    private void goback(MouseEvent event) {
        if (role.equals("admin")){
        switchScene("/profileuser.fxml", event);
    }
        else {switchScene("/Uiuser.fxml", event);}}





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

    private void switchScene(String fxmlFile, MouseEvent event) {

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
