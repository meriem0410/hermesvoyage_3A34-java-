package edu.esprit.GestionTransport.Controller.Tickets;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import edu.esprit.GestionTransport.Entity.Tickets;
import edu.esprit.GestionTransport.Entity.Transport;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    @FXML
    private ImageView imageViewSample;

    @FXML
    private HBox sampleBox;

    @FXML
    private Label labelDepart;

    @FXML
    private Label labelArrive;

    @FXML
    private Label labelPrix;

    @FXML
    private Label labelTypeTransport;

    private String[] colors = {"B9E5FF", "BDB2FE", "FB9AA8", "FF5056"};
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Tickets ticket) {
        labelDepart.setText(ticket.getDepart());
        labelArrive.setText(ticket.getArrive());
        labelPrix.setText(String.valueOf(ticket.getPrix()));
        labelTypeTransport.setText(ticket.getTypeTransport());

        // Génération aléatoire de la couleur de fond du conteneur
        String randomColor = colors[(int) (Math.random() * colors.length)];
        String style = String.format("-fx-background-color: #%s; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0,10);", randomColor);
        sampleBox.setStyle(style);
    }
}
