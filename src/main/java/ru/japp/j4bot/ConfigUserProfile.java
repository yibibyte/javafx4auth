package ru.japp.j4bot;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ConfigUserProfile {
    @FXML
    private Label nameLabel;

    private StringProperty nameProperty = new SimpleStringProperty();

    public void initialize() {
        nameLabel.textProperty().bind(nameProperty);
    }

    public void setName(String name) {
        nameProperty.set(name);
    }
}
