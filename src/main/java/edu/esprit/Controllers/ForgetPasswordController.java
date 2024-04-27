package edu.esprit.Controllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import edu.esprit.services.UserService;
import edu.esprit.utiles.MyConnection;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

public class ForgetPasswordController {

    @FXML
    private TextField entredemail;
    @FXML
    private Button sendemail;
    static String adremail;
    @FXML
    private Button cancel;

    @FXML
    private Button verify;
    @FXML
    private TextField entredotp;
    private static final String OTP_CHARS = "123456789";
    private static final int OTP_LENGTH = 6;
    @FXML
    private Label warning2;
    private final UserService userService = new UserService();
    private String pass;
    @FXML
    private Label aa;
    static String storedOTP;

    @FXML
    private Label warning ;
    private String vcode;


    @FXML
    void sendemail(ActionEvent event) {
        adremail = entredemail.getText();
    if (!isValidEmail(adremail)) {
        warning.setText("Invalid Email, Please enter a valid email address.");
        return;
    }else if (!userService.doesEmailExist(adremail)){
        warning.setText("Email Not Found, The provided email does not exist");
        return;
    }else {
        pass = generateOTP();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Email sent");
        alert.setContentText("An email with a verification code is sent to your email");
        alert.show();
        userService.updateOTP(adremail, pass);
        storedOTP = userService.getOTP(adremail);
        System.out.println(storedOTP);


        System.out.println(adremail);
        System.out.println("aaaaa");
        System.out.println("aaaaa");System.out.println("aaaaa");System.out.println("aaaaa");System.out.println("aaaaa");

        switchScene("/verification.fxml", event);

        String to = adremail;
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
            message.setSubject("Reset your password!");

            // Now set the actual message
            //message.setText("This is actual message");

            // Send the actual HTML message.
            message.setContent(
                    "<html><body>" +
                            "<h1>Hello there!</h1>" +
                            "<p>You have requested a password update.</p>" +
                            "<p>This is your OTP .</p>" + pass +
                            "<p>If you didn't request a password change, please ignore this e-mail.</p>" +
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




}


    


@FXML
void verifyotp (ActionEvent event) {
    System.out.println(adremail);
    String vcode = entredotp.getText();
    System.out.println(storedOTP);
    if (storedOTP == null || !(storedOTP.equals(vcode))) {
        warning2.setText("The verification codes do not match!!");
        return;
    } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Verification Successful!");
            alert.setContentText("Please proceed to change your password");
            alert.show();
            switchScene("/changepassword.fxml", event);
    }


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



    private String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(OTP_CHARS.charAt(random.nextInt(OTP_CHARS.length())));
        }
        return otp.toString();
    }

    @FXML
    void cancel(ActionEvent event) {
        switchScene("/login.fxml", event);
    }
    private boolean isValidEmail(String email) {
        // Regular expression pattern for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the pattern into a regex pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Check if the email matches the pattern
        return pattern.matcher(email).matches();
    }

}
