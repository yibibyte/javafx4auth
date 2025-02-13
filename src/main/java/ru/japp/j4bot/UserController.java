package ru.japp.j4bot;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label ageUser;

    @FXML
    private TextField ageUserField;

    @FXML
    private Button backButton;

    @FXML
    private Label emailUser;

    @FXML
    private TextField emailUserField;

    @FXML
    private Label firstNameUser;

    @FXML
    private TextField firstNameUserField;

    @FXML
    private Label lastNameUser;

    @FXML
    private TextField lastNameUserField;

    @FXML
    private ImageView photoUser;

    @FXML
    private Button saveFields;

    // Метод для установки данных пользователя
    public void setUserData(String name, String email, int age, String imageUrl) {
        firstNameUserField.setText(name);
        emailUserField.setText(email);
        ageUserField.setText("Возраст: " + age);

        // Загрузка изображения
        Image image = new Image(imageUrl);
        photoUser.setImage(image);
    }

    @FXML
    void initialize() {

    }

}


