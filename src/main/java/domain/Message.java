package domain;

import java.time.LocalDateTime;

public class Message {
    private String text;
    private LocalDateTime date;
    private int userID1;
    private int userID2;

    public Message(String text, LocalDateTime date, int userID1, int userID2) {
        this.text = text;
        this.date = date;
        this.userID1 = userID1;
        this.userID2 = userID2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getUserID1() {
        return userID1;
    }

    public void setUserID1(int userID1) {
        this.userID1 = userID1;
    }

    public int getUserID2() {
        return userID2;
    }

    public void setUserID2(int userID2) {
        this.userID2 = userID2;
    }
}
