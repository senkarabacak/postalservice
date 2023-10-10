module com.disys.gui {
    requires javafx.controls;
    requires javafx.fxml;
        requires javafx.web;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;

            requires com.almasb.fxgl.all;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    opens com.disys.gui to javafx.fxml;
    exports com.disys.gui;
}