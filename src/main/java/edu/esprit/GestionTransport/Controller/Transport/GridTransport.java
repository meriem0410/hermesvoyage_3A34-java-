package edu.esprit.GestionTransport.Controller.Transport;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import edu.esprit.GestionTransport.Entity.Transport;

import java.net.URL;
import java.util.ResourceBundle;

public class GridTransport implements Initializable {

    @FXML
    private Label gridType;

    @FXML
    private Label gridDescription;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setData(Transport transport) {
        gridType.setText(transport.getTypeTransport());
        gridDescription.setText(transport.getDescription());
    }
}
