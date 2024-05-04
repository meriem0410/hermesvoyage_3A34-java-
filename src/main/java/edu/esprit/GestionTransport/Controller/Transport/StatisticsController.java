package edu.esprit.GestionTransport.Controller.Transport;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private Button RetourButton ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Exemple de valeurs de statistiques
        int ticketsEnBus = 20;
        int ticketsEnTrain = 15;

        // Créez les séries de données pour les statistiques
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Bus", ticketsEnBus));
        series.getData().add(new XYChart.Data<>("Train", ticketsEnTrain));
        series.getData().add(new XYChart.Data<>("avoin", ticketsEnTrain));
        // Ajoutez les séries de données au BarChart
        barChart.getData().add(series);

        // Définissez les libellés des axes
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Type de Transport");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Nombre de Tickets");

        // Définir l'axe des catégories et l'axe des valeurs pour le BarChart
        barChart.setBarGap(3);
        barChart.setCategoryGap(20);
        barChart.setVerticalGridLinesVisible(true);
        barChart.setHorizontalGridLinesVisible(true);
        barChart.getXAxis().setLabel("Type de Transport");
        barChart.getYAxis().setLabel("Nombre de Tickets");
    }
    @FXML
    private void handleRetourButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/esprit/Transport/FrontTransport.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
