package ru.japp.j4bot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
//    @Override
//    public void start(Stage stage) throws IOException {
////        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("registration-view.fxml"));
////        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("auth-view.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
////        Scene scene = new Scene(fxmlLoader.load(), 700, 600);
//        stage.setTitle("Привет Пользователь!");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch();
//    }

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        showAuthView();
    }

    public static void showAuthView() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("auth-view.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Авторизация");
        primaryStage.show();
    }

    public static void showRegistrationView() throws IOException {
        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("registration-view.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("Регистрация");
        primaryStage.show();
    }

//    public static void showMainView() throws IOException {
//        FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("main.fxml"));
//        Parent root = loader.load();
//        primaryStage.setScene(new Scene(root));
//        primaryStage.setTitle("Главное окно");
//        primaryStage.show();
//    }

    public static void main(String[] args) {
        launch();
    }
}