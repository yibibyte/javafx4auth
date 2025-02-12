package ru.japp.j4bot;

public class User {
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String firstName;
    private String lastName;
    private String email;
    private String avatarPath;

    // Конструктор, геттеры и сеттеры
    public void setAvatarPath(String path) {
        this.avatarPath = path;
    }

    public String getAvatarPath() {
        return avatarPath;
    }
}