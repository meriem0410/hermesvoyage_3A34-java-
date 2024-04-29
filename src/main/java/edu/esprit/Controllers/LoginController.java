package edu.esprit.Controllers;

import edu.esprit.entities.User;

import edu.esprit.services.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import edu.esprit.services.LoginService;
import org.mindrot.jbcrypt.BCrypt;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;


public class LoginController {

    @FXML
    private TextField name;
    static String storedOTP;

    static String mail;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginbtn;

    @FXML
    private Hyperlink signinbtn;


    @FXML
    static String username;
    @FXML
    private Label warning;

    @FXML
    private Hyperlink forgetpass;

    private static final String OTP_CHARS = "123456789";
    private static final int OTP_LENGTH = 6;
    private final UserService userService = new UserService();




    private final LoginService loginService = new LoginService();



    @FXML
    void handleLogin(ActionEvent event) {
        warning.setText("");
        String userName = name.getText();
        String pass = password.getText();

        User user = loginService.getUserByUsername(userName);

        if (user != null && BCrypt.checkpw(pass, user.getPassword().replace("$2y$", "$2a$"))) {
            username = userName;
            String fxmlFile;
            String role = user.getRole();
            String password = user.getPassword();
            boolean ban = !(user.isBanned());
            boolean verif =(user.isVerified());
            mail = (user.getEmail());
            if (!verif){switchScene("/verif.fxml", event);
            verifyemail(mail);}
            else{
            System.out.println("Banned status: " + ban);
            if (ban){switchScene("/Banned.fxml", event);}
            else {
            if (role.equals("admin")) {

                switchScene("/Uiadmin.fxml", event);

            } else if (role.equals("voyageur")) {
                switchScene("/Uiuser.fxml", event);
            }}}

            //showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + user.getUsername() + "!");

        } else {
            //showAlert(Alert.AlertType.INFORMATION, "Warning","Invalid Username or password!!!");
            warning.setText("Invalid Username or password!!!");




        }
    }


    private void verifyemail(String mail) {

        String pass = generateOTP();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Email sent");
        alert.setContentText("An email with a verification code is sent to your email");
        alert.show();
        userService.updateOTP(mail, pass);
        String storedOTP = userService.getOTP(mail);
        System.out.println(storedOTP);


        System.out.println(mail);
        System.out.println("aaaaa");
        System.out.println("aaaaa");System.out.println("aaaaa");System.out.println("aaaaa");System.out.println("aaaaa");

        String to = mail;
        String from = "azizrk19@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("azizrk19@gmail.com", "morypquvjrbupqzp");

            }

        });
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Account verification!");

            // Now set the actual message
            //message.setText("This is actual message");

            // Send the actual HTML message.
            message.setContent(
                    "<html><body>" +
                            "<h1>Hello there!</h1>" +
                            "<p>This is your OTP .</p>" + pass +
                            "<p>Thank you for you using our app!</p>" +
                            "<p>Sincerely yours,\n" +
                            "\n" +
                            "Roamers.</p>" +
                            "</body></html>",
                    "text/html");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }


    }

    @FXML
    private TextField entredotp;
    @FXML
    private Label warning2;

    @FXML
    void verifyotp (ActionEvent event) {
        System.out.println(mail);
        storedOTP= userService.getOTP(mail);
        String vcode = entredotp.getText();
        System.out.println(storedOTP);
        if (storedOTP == null || !(storedOTP.equals(vcode))) {
            warning2.setText("The verification codes do not match!!");
            return;
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Verification Successful!");
            alert.setContentText("Your account is verified! Welcome aboard");
            alert.show();
            User user = loginService.getUserByUsername(LoginController.username);
            String role = user.getRole();

            System.out.println(role);
            // Check the role and switch scene accordingly
            if (role.equals("admin")) {
                switchScene("/Uiadmin.fxml", event);
            } else   {
                switchScene("/Uiuser.fxml", event);

        }}


    }
    private String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(OTP_CHARS.charAt(random.nextInt(OTP_CHARS.length())));
        }
        return otp.toString();
    }


    @FXML
    void forgetpassword(ActionEvent event) {
        switchScene("/forget_password.fxml", event);
    }
    @FXML
    void handleSignIn(ActionEvent event) {
        switchScene("/RegisterUser.fxml", event);
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
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
