package edu.esprit.GestionTransport.Controller.Transport;



import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import edu.esprit.GestionTransport.Entity.Transport;

import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    @FXML
    private ImageView imageViewSample;

    @FXML
    private HBox sampleBox;

    @FXML
    private Label labelType;

    @FXML
    private Label labelDescription;

    private String[] colors = {"B9E5FF","BDB2FE","FB9AA8","FF5056"};
    int randomIndex = (int) (Math.random() * colors.length);
    String randomColor = colors[randomIndex];

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Transport transport) {
        labelType.setText(transport.getTypeTransport());
        labelDescription.setText(transport.getDescription());

        // Remplacer le chargement de l'image par défaut, si nécessaire
        // Par exemple, setImage(new Image("default-image.png"));
        // Ou utilisez une image par défaut si aucune image n'est spécifiée pour le transport

        String style = String.format("-fx-background-color: #%s; -fx-background-radius: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0,10);", randomColor);
        sampleBox.setStyle(style);
    }
}

