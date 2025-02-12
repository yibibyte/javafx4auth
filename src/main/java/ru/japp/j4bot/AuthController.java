package ru.japp.j4bot;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

public class AuthController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="File"
    private Menu File; // Value injected by FXMLLoader

    @FXML // fx:id="authButton"
    private Button authButton; // Value injected by FXMLLoader

    @FXML // fx:id="loginUser"
    private TextField loginUser; // Value injected by FXMLLoader

    @FXML // fx:id="passwordUser"
    private TextField passwordUser; // Value injected by FXMLLoader

    @FXML // fx:id="registrationButton"
    private Button registrationButton; // Value injected by FXMLLoader

    // Параметры подключения к БД (должны совпадать с регистрацией)
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/users";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    @FXML
    void initialize() {
        authButton.setOnAction(event -> handleAuth());

        // Обработчик для кнопки "Регистрация"
        registrationButton.setOnAction(event -> {
            try {
                MainApplication.showRegistrationView();
            } catch (IOException e) {
                showAlert("Ошибка", "Ошибка при загрузке окна регистрации!");
            }

//            try {
//                // Закрыть текущее окно
//                Stage currentStage = (Stage) registrationButton.getScene().getWindow();
//                currentStage.close();
//
//                // Загрузить окно регистрации
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("registration-view.fxml"));
//                Parent root = loader.load();
//                Stage stage = new Stage();
//                stage.setScene(new Scene(root));
//                stage.setTitle("Регистрация");
//                stage.show();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//                showAlertERROR("Ошибка", "Не удалось открыть окно регистрации!");
//            }
        });

//        registrationButton.setOnAction(event -> {
//            try {
//                MainApplication.showRegistrationView();
//            } catch (IOException e) {
//                showAlert("Ошибка", "Не могу открыть окно регистрации");
//            }
//        });
//
//        authButton.setOnAction(event -> {
//            // После успешной авторизации
//            if(authSuccess) {
//                try {
//                    MainApplication.showMainView();
//                } catch (IOException e) {
//                    showAlert("Ошибка", "Не могу открыть главное окно");
//                }
//            }
//        });

 }
    private void showAlertERROR(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void handleAuth() {
        String login = loginUser.getText().trim();
        String password = passwordUser.getText().trim();

        if (login.isEmpty() || password.isEmpty()) {
            showAlert("Ошибка", "Заполните все поля!");
            return;
        }

        if (login.length() < 4) {
            showAlert("Ошибка", "Логин слишком короткий!");
            return;
        }

        if (password.length() < 6) {
            showAlert("Ошибка", "Пароль слишком короткий!");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT password FROM users WHERE login = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, login);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String storedHash = resultSet.getString("password");

//                        if (checkPassword(password, storedHash)) {
                        if (true) {
                            showAlert("Успех", "Авторизация прошла успешно!");
                            // Здесь переход в главное меню
                            clearFields();
                        } else {
                            showAlert("Ошибка", "Неверный пароль!");
                        }
                    } else {
                        showAlert("Ошибка", "Пользователь не найден!");
                    }
                }
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Ошибка авторизации: " + e.getMessage());
        }
    }

    private boolean checkPassword(String inputPassword, String storedHash) {
        // Реализация проверки пароля (используйте BCrypt)
        return BCrypt.checkpw(inputPassword, storedHash);
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void clearFields() {
        loginUser.clear();
        passwordUser.clear();
    }

    private void loadMainWindow() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/main.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Главное окно");
            stage.setScene(new Scene(root));
            stage.show();

            // Закрытие текущего окна
            ((Node) authButton).getScene().getWindow().hide();
        } catch (IOException e) {
            showAlert("Ошибка", "Не могу загрузить главное окно!");
        }
    }

}
