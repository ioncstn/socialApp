package domain;

import java.util.Objects;

public class User {
    private final int ID;
    private String username;
    private String email;
    private String password;

    public User(int ID, String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return (this.ID == user.getID()) || (Objects.equals(email, user.email) && Objects.equals(password, user.password));
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password);
    }

    @Override
    public String toString() {
        return ID + ". email: " + email + "; password: " + password + "; username: " + username;
    }
}
