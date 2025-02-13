module ru.japp.j4bot {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires jbcrypt;
    requires annotations;

    opens ru.japp.j4bot to javafx.fxml;
    exports ru.japp.j4bot;
    exports ru.japp.j4bot.model;
    opens ru.japp.j4bot.model to javafx.fxml;
}