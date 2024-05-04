package edu.esprit.tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlloader = new FXMLLoader(Main.class.getResource("/AfficherLogement.fxml"));
        Parent root = fxmlloader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();


    }
    public static void  main (String[] args){
        launch(args);
    }

}