package edu.esprit.Controllers;


import edu.esprit.entities.User;
import edu.esprit.entities.voyageur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import edu.esprit.services.UserService;
import edu.esprit.utiles.MyConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class RegisterUserController {


    voyageur voy = new voyageur();


    @FXML
    private TextField TFemail;

    @FXML
    private TextField TFusername;

    @FXML
    private PasswordField TFpassword;

    @FXML
    private PasswordField TFconfirmpass;

    @FXML
    private CheckBox checkbx;

    @FXML
    private Button loginbtn;

    @FXML
    private Button uploadPictureButton;
    static byte[] picture;
    private static final String DEFAULT_PICTURE_PATH = "resources/default_picture.png";



    private final UserService userService = new UserService();
    @FXML
    private Label wremail;
    @FXML
    private Label wrusr;
    @FXML
    private Label wrps;
    @FXML
    private Label wrpsc;
    @FXML
    private Label wrcheck;
    @FXML
    private ImageView prevpic;




    @FXML
    void registerUserToDatabase(ActionEvent event) {
        try {
            // Check if any field is empty
            if ( TFemail.getText().isEmpty() || TFusername.getText().isEmpty() || TFpassword.getText().isEmpty() || TFconfirmpass.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("Please Fill all fields");
                alert.show();

            }

            // Check if passwords match
            if (!TFpassword.getText().equals(TFconfirmpass.getText())) {
                wrpsc.setText("Passwords do not match");

            }
            else {
                wrpsc.setText("");
            }

            // Validate email format
            String email = TFemail.getText();
            if (!isValidEmail(email)) {
                wremail.setText("Invalid email format");
            }else if (isEmailTaken(email)) {
                wremail.setText("Email is already taken");
            }
            else {
                wremail.setText("");
            }

            // Validate password format
            String password = TFpassword.getText();
            if (!isValidPassword(password)) {
                wrps.setText("Password must be at least 8 characters long \n and contain at least one digit and one capital letter");
            }else {
                wrps.setText("");
            }



            if (!checkbx.isSelected()) {
                wrcheck.setText("Please agree to the Terms of Service and Privacy Policy");
            } else {
                wrcheck.setText("");
            }


            // Check if username is already taken
            String username = TFusername.getText();
            if (isUsernameTaken(username)) {
                wrusr.setText("Username is already taken");
            }else {
                wrusr.setText("");
            }



            if ((wrusr.getText().equals("")) && (wrps.getText().equals("")) && (wremail.getText().equals("")) &&
                    (wrcheck.getText().equals("")) && (wrpsc.getText().equals("")) )
                {
                    String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                    voy.setEmail(email);
                    voy.setUsername(username);
                    voy.setPassword(hashedPassword);
                    voy.setRole("voyageur");
                    voy.setBanned(false);
                    voy.setVerified(false);
                    if (picture!=null)
                    {voy.setPictureData(picture);}
                    else {
                        File defaultPictureFile = new File(DEFAULT_PICTURE_PATH);
                        FileInputStream fis = new FileInputStream(defaultPictureFile);
                        byte[] defaultPictureData = new byte[(int) defaultPictureFile.length()];
                        fis.read(defaultPictureData);
                        fis.close();

                        voy.setPictureData(defaultPictureData);}
                    userService.addEntity(voy);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Signed Up! Please verify your account");
                alert.show();
                switchScene("/login.fxml", event);
            }
            /*
            else {Alert alertf = new Alert(Alert.AlertType.ERROR);
                alertf.setTitle("Error");
                alertf.setContentText("user not added");
                alertf.showAndWait();}
            */
            // Clear input fields
            TFemail.clear();
            TFusername.clear();
            TFpassword.clear();
            TFconfirmpass.clear();
            checkbx.setSelected(false);

            /*
            // Show success message

            */
        } catch (Exception e) {
            // Show error message
            System.out.println(e.getStackTrace());
        }
    }

    @FXML
    void uploadPicture(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File selectedFile = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            try {
                FileInputStream fis = new FileInputStream(selectedFile);
                byte[] data = new byte[(int) selectedFile.length()];
                fis.read(data);
                fis.close();
                picture = data;
                voy.setPictureData(picture);
                Image image = new Image(selectedFile.toURI().toString());
                prevpic.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        // Regular expression pattern for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the pattern into a regex pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Check if the email matches the pattern
        return pattern.matcher(email).matches();
    }



    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one digit and one capital letter
        String passwordRegex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";

        // Compile the pattern into a regex pattern object
        Pattern pattern = Pattern.compile(passwordRegex);

        // Check if the password matches the pattern
        return pattern.matcher(password).matches();
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
    void goToLogin(ActionEvent event) {
        switchScene("/login.fxml", event);
    }

    private boolean isEmailTaken(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count > 0, email is already taken
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count > 0, username is already taken
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }




}
