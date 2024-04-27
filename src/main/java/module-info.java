module edu.esprit.GestionTransport {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.web;
    requires java.sql;
    requires com.jfoenix;

    requires java.mail;
    // Autres déclarations nécessaires



    opens edu.esprit.GestionTransport to javafx.fxml;
    opens edu.esprit.GestionTransport.Controller.Transport to javafx.fxml;
    opens edu.esprit.GestionTransport.Controller.Tickets to javafx.fxml;
    opens edu.esprit.GestionTransport.Entity to javafx.base;
    exports edu.esprit.GestionTransport;
}