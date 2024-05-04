package edu.esprit.GestionTransport;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplicationTransport extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplicationTransport.class.getResource("/edu/esprit/Transport/BackTransport.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hermes Voyage");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}