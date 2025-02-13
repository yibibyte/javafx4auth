package ru.japp.j4bot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.japp.j4bot.model.User;

// MainApp.java
public class MainApp extends Application {
    private static Stage primaryStage;
    private static User currentUser;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        primaryStage.setResizable(false);
        showAuthView();
    }

    // Методы перехода между окнами
    public static void showAuthView() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/auth-view.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Авторизация");
        primaryStage.show();
    }

    public static void showRegistrationView() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/registration-view.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Регистрация");
        primaryStage.show();
    }

    public static void showMainView(User user) throws Exception {
        currentUser = user;
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/main-view.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Главная страница");
        primaryStage.show();
    }

    public static void showChatView() throws Exception {
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/chat-view.fxml"));
        Parent root = loader.load();
        Stage chatStage = new Stage();
        chatStage.setScene(new Scene(root));
        chatStage.setTitle("Чат");
        chatStage.initModality(Modality.WINDOW_MODAL);
        chatStage.initOwner(primaryStage);
        chatStage.show();
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void main(String[] args) {
        launch(args);
    }
}