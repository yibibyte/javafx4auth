package ru.japp.j4bot.model;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String login;
    private String avatarPath;

    public User(String firstName, String lastName, int age, String email, String login, String avatarPath) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = age;
        this.login = login;
        this.avatarPath = avatarPath;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAvatarPath(String path) {
        this.avatarPath = path;
    }

    public String getAvatarPath() {
        return avatarPath;
    }
}