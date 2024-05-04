package edu.esprit.Controllers;

import edu.esprit.entities.User;
import edu.esprit.services.LoginService;
import edu.esprit.services.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
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

    @FXML
    private PasswordField password;

    @FXML
    private Button loginbtn;

    @FXML
    private Hyperlink signinbtn;

    @FXML
    private WebView captchaWebView = new WebView();

    @FXML
    private Label warning;

    @FXML
    private Hyperlink forgetpass;

    private final UserService userService = new UserService();
    private final LoginService loginService = new LoginService();

    private static final String OTP_CHARS = "123456789";
    private static final int OTP_LENGTH = 6;
    private static String username;
    private static String mail;
    private static String storedOTP;
    private static boolean verified;
    private static boolean banusr;

    public void initialize() {
        WebEngine engine = captchaWebView.getEngine();
        engine.load("http://localhost/captcha.html");
    }

    @FXML
    void handleLogin(ActionEvent event) {
        warning.setText("");
        String userName = name.getText();
        String pass = password.getText();

        // Retrieve user from login service
        User user = loginService.getUserByUsername(userName);

        if (user == null) {
            warning.setText("Invalid username!");
            return; // Exit the method if user is null
        }

        if (captchaWebView != null) {
            try {
                WebEngine engine = captchaWebView.getEngine();
                String result = (String) engine.executeScript(
                        "function isRecaptchaVerified() {" +
                                " var isVerified = grecaptcha.getResponse().length > 0;" +
                                " return String(isVerified);" +
                                "} " +
                                "isRecaptchaVerified();"
                );
                if (result.equals("false")) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("captcha");
                    alert.setContentText("Please check the captcha.");
                    alert.showAndWait();
                    System.out.println("erreur");
                    return;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            // Handle the case when captchaWebView is null
            System.out.println("captchaWebView is null.");
            return;
        }

        verified = userService.getVerified(user.getEmail());
        banusr = userService.getban(user.getEmail());

        if (BCrypt.checkpw(pass, user.getPassword().replace("$2y$", "$2a$"))) {
            UserSession.setUser(user);
            System.out.println(user.toString());
            username = userName;
            String fxmlFile;
            String role = user.getRole();
            boolean ban = !(user.isBanned());

            mail = (user.getEmail());
            if (!verified){
                switchScene("/verif.fxml", event);
                verifyemail(mail);
            } else {
                System.out.println("Banned status: " + ban);
                if (banusr) {
                    switchScene("/Banned.fxml", event);
                } else {
                    if (role.equals("admin")) {
                        switchScene("/profileuser.fxml", event);
                    } else if (role.equals("voyageur")) {
                        switchScene("/Uiuser.fxml", event);
                    }
                }
            }
        } else {
            warning.setText("Incorrect password!!!");
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
            User user = userService.getUserByEmail(mail);
            if (storedOTP == null || !(storedOTP.equals(vcode))) {
                warning2.setText("The verification codes do not match!!");
                return;
            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Verification Successful!");
                alert.setContentText("Your account is verified! Welcome aboard");
                alert.show();
                String role = user.getRole();
                user.setVerified(true);
                boolean updatedSuccessfully = userService.updateverified(true, user.getEmail());
                System.out.println("verif2 " + user.isVerified());

                System.out.println(role);
                // Check the role and switch scene accordingly
                if (role.equals("Admin")) {
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

        private void showAlert(Alert.AlertType alertType, String title, String message) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        }
    }
