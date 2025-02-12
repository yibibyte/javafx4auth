package ru.japp.j4bot;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;

// MainController.java
public class MainController {
    @FXML private ImageView userAvatar;
    @FXML private Label userName;
    @FXML private Label userEmail;
    @FXML private Button chatButton;
    String avatarPath = "/images/no_avatar.jpg";

    @FXML
    private void initialize() {
        User user = MainApp.getCurrentUser();
        userName.setText(user.getFirstName() + " " + user.getLastName());
        userEmail.setText(user.getEmail());

        if(user.getAvatarPath() != null) {
            loadAvatar(user.getAvatarPath());
        }
    }

    @FXML
    private void handleChangeAvatar() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(userAvatar.getScene().getWindow());
        if (file != null) {
            String imagePath = file.toURI().toString();
            loadAvatar(imagePath);
            // Сохраняем путь к аватару в профиле пользователя
            MainApp.getCurrentUser().setAvatarPath(imagePath);
        }
    }

    @FXML
    private void handleOpenChat() {
        try {
            MainApp.showChatView();
        } catch (Exception e) {
            showAlert("Ошибка", "Не удалось открыть чат");
        }
    }

    private void loadAvatar(String path) {
        Image image = new Image(path);
        userAvatar.setImage(image);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}