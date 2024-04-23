package java.controllers;

import java.entity.Transport;
import javafx.scene.control.ListCell;

public class TransportListCell extends ListCell<Transport> {

    @Override
    protected void updateItem(Transport transport, boolean empty) {
        super.updateItem(transport, empty);

        if (empty || transport == null) {
            setText(null);
        } else {
            setText(transport.getTransport() + " - " + transport.getDescription());
        }
    }
}
