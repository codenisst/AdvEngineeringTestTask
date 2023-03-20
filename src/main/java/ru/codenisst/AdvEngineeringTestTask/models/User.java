package ru.codenisst.AdvEngineeringTestTask.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    private String password;
    private String role;
    private boolean banned;

    public User(String login, String password, String role, boolean banned) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.banned = banned;
    }

    public User() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean getBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && banned == user.banned && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, role, banned);
    }

    @Override
    public String toString() {
        return "User{" + "\n" +
                "id=" + id + "\n" +
                ", login='" + login + '\'' + "\n" +
                ", password='" + password + '\'' + "\n" +
                ", role='" + role + '\'' + "\n" +
                ", banned=" + banned + "\n" +
                '}';
    }
}
