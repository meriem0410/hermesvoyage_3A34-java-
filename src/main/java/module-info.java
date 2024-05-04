module edu.esprit.gestionvoyage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires eu.hansolo.fx.countries;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires com.gluonhq.charm.glisten;
    requires javax.mail.api;
    requires itextpdf;
    requires twilio;

    opens edu.esprit.gestionvoyage to javafx.fxml;
    opens edu.esprit.gestionvoyage.controllers to javafx.fxml;
    opens edu.esprit.gestionvoyage.entities to javafx.base;
    exports edu.esprit.gestionvoyage;
}