package ru.japp.j4bot;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label ageUser;

    @FXML
    private Label emailUser;

    @FXML
    private Label firstNameUser;

    @FXML
    private Label lastNameUser;

    @FXML
    private Label middleNameUser;

    @FXML
    private Button saveFields;

    @FXML
    void initialize() {

    }
}
