package ru.japp.j4bot;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import org.jetbrains.annotations.Nullable;

public class RegistrationController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="File"
    private Menu File; // Value injected by FXMLLoader

    @FXML // fx:id="ageUser"
    private TextField ageUser; // Value injected by FXMLLoader

    @FXML // fx:id="authButton"
    private Button authButton; // Value injected by FXMLLoader

    @FXML // fx:id="email"
    private TextField email; // Value injected by FXMLLoader

    @FXML // fx:id="firstName"
    private TextField firstName; // Value injected by FXMLLoader

    @FXML // fx:id="lastName"
    private TextField lastName; // Value injected by FXMLLoader

    @FXML // fx:id="loginUser"
    private TextField loginUser; // Value injected by FXMLLoader

    @FXML // fx:id="passwordUser"
    private TextField passwordUser; // Value injected by FXMLLoader

    @FXML // fx:id="registrationButton"
    private Button registrationButton; // Value injected by FXMLLoader

    @FXML // fx:id="repeatPassUser"
    private TextField repeatPassUser; // Value injected by FXMLLoader

    private List<User> users = new ArrayList<>(); // Временное хранилище пользователей

    User user;
    // Параметры подключения к БД
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/users";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "postgres";

    String avatarPath = "/images/no_avatar.jpg";
    // В RegistrationController и MainController при изменении аватара
    //String avatarPath = user.setAvatarPath(avatarPath); // путь к изображению

    private @Nullable String getString() {
        return user.setAvatarPath(avatarPath);
    } // путь к изображению

// Обновите запись в базе данных

    @FXML
    void initialize() {
        registrationButton.setOnAction(event -> handleRegistration());
        //createUsersTableIfNotExists();
        authButton.setOnAction(event -> {
            try {
                MainApplication.showAuthView();
            } catch (Exception e) {
                showAlert("Ошибка перехода", "Не удалось вернуться к авторизации");
            }
        });
    }

    private void incorrectAge(String title, String message) {
        if (Integer.valueOf(ageUser.getText().trim()) instanceof Integer) {
            showAlert("Ошибка", "Возраст должен быть числом!");
        }
    }

    private void handleRegistration() {
        // Получаем значения из полей
        String firstNameValue = firstName.getText().trim();
        String lastNameValue = lastName.getText().trim();
        String ageValueString = ageUser.getText().trim();
        
        Integer ageValue = Integer.valueOf(ageUser.getText().trim());
        String loginValue = loginUser.getText().trim();
        String passwordValue = passwordUser.getText().trim();
        String repeatPasswordValue = repeatPassUser.getText().trim();
        String emailValue = email.getText().trim();

        // Хеширование пароля
        String hashedPassword = hashPassword(passwordValue);

        // Создание подключения
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Проверка уникальности логина и email
            if (isUserExists(connection, "login", loginValue)) {
                showAlert("Ошибка", "Пользователь с таким логином уже существует!");
                return;
            }

            if (isUserExists(connection, "email", emailValue)) {
                showAlert("Ошибка", "Пользователь с таким email уже существует!");
                return;
            }

            // Вставка нового пользователя
            String sql = "INSERT INTO users (first_name, last_name, age, login, password, email) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, firstNameValue);
                statement.setString(2, lastNameValue);
                statement.setInt(3, ageValue);
                statement.setString(4, loginValue);
                statement.setString(5, hashedPassword);
                statement.setString(6, emailValue);

                int affectedRows = statement.executeUpdate();
                if (affectedRows > 0) {
                    showAlert("Успех", "Регистрация прошла успешно!");
                    clearFields();
                }
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Ошибка при регистрации: " + e.getMessage());
        }


        // Валидация полей
        if (firstNameValue.isEmpty() || lastNameValue.isEmpty() || ageValueString.isEmpty() ||
                loginValue.isEmpty() || passwordValue.isEmpty() || repeatPasswordValue.isEmpty() ||
                emailValue.isEmpty()) {
            showAlert("Ошибка", "Все поля должны быть заполнены!");
            return;
        }

        if (!passwordValue.equals(repeatPasswordValue)) {
            showAlert("Ошибка", "Пароли не совпадают!");
            return;
        }

        int age;
        try {
            age = ageValue;
            if (age < 13) {
                showAlert("Ошибка", "Возраст должен быть не менее 13 лет!");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Ошибка", "Некорректный формат возраста!");
            return;
        }

        if (!isValidEmail(emailValue)) {
            showAlert("Ошибка", "Некорректный формат email!");
            return;
        }

        // Создание нового пользователя
        User newUser = new User(
                firstNameValue,
                lastNameValue,
                age,
                loginValue,
                passwordValue,
                emailValue
        );

        // Проверка уникальности логина
        if (isLoginExists(loginValue)) {
            showAlert("Ошибка", "Пользователь с таким логином уже существует!");
            return;
        }

        // Сохранение пользователя
        users.add(newUser);
        showAlert("Успех", "Регистрация прошла успешно!");
        clearFields();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private boolean isLoginExists(String login) {
        return users.stream().anyMatch(user -> user.getLogin().equalsIgnoreCase(login));
    }

    private void createUsersTableIfNotExists() {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id SERIAL PRIMARY KEY," +
                "first_name VARCHAR(50) NOT NULL," +
                "last_name VARCHAR(50) NOT NULL," +
                "age INTEGER NOT NULL," +
                "login VARCHAR(30) UNIQUE NOT NULL," +
                "password VARCHAR(100) NOT NULL," +
                "email VARCHAR(100) UNIQUE NOT NULL)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to create table: " + e.getMessage());
        }
    }


    private boolean isUserExists(Connection connection, String field, String value) throws SQLException {
        String sql = "SELECT id FROM users WHERE " + field + " = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, value);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }


    private String hashPassword(String password) {
        // Реализация хеширования пароля (используйте BCrypt или аналоги)
        return password; // Замените на реальное хеширование
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        firstName.clear();
        lastName.clear();
        ageUser.clear();
        loginUser.clear();
        passwordUser.clear();
        repeatPassUser.clear();
        email.clear();
    }

    // Внутренний класс пользователя
    private static class User {
        private final String firstName;
        private final String lastName;
        private final int age;
        private final String login;
        private final String password;
        private final String email;

        public User(String firstName, String lastName, int age, String login, String password, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
            this.login = login;
            this.password = password;
            this.email = email;
        }

        public String getLogin() {
            return login;
        }

        public String setAvatarPath(String avatarPath) {
            return null;
        }
        
    }
}
