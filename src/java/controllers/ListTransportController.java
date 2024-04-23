package java.controllers;

import java.entity.Transport;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ListTransportController {

    @FXML
    private ListView<Transport> transportListView;

    private ObservableList<Transport> transportList = FXCollections.observableArrayList();

    public void initialize() {
        // Simuler des données de transports (vous pouvez remplacer cela par vos propres données provenant d'une source de données)
        transportList.add(new Transport("Train", "Fast and efficient mode of transportation by rail."));
        transportList.add(new Transport("Bus", "Public road transportation service."));
        transportList.add(new Transport("Plane", "Fast aerial transportation."));

        // Définir la liste des transports dans le ListView
        transportListView.setItems(transportList);

        // Personnaliser l'affichage des éléments dans le ListView (optionnel)
        transportListView.setCellFactory(param -> new TransportListCell());
    }
}
