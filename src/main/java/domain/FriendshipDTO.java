package domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;

public class FriendshipDTO {
    private final StringProperty username;

    private final StringProperty date;

    private final boolean pending;

    private final int id;

    public FriendshipDTO(int id, String username, LocalDateTime date, boolean pending) {
        this.id = id;
        this.username = new SimpleStringProperty(username);
        this.date = new SimpleStringProperty(date.toLocalDate().toString());
        this.pending = pending;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public void setDate(LocalDateTime date) {
        this.date.set(date.toLocalDate().toString());
    }

    public String getUsername() {
        return username.get();
    }

    public String getDate() {
        return date.get();
    }

    public int getID(){ return id;}

    public boolean isPending() {
        return pending;
    }
}
