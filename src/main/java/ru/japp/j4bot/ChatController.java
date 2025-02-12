package ru.japp.j4bot;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

// ChatController.java
public class ChatController {
    @FXML
    private TextArea chatArea;
    @FXML private TextField messageField;

    @FXML
    private void initialize() {
        // Инициализация чата
    }

    @FXML
    private void handleSendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            chatArea.appendText("Вы: " + message + "\n");
            messageField.clear();
        }
    }
}