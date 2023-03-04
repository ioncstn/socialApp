package domain;

import repository.Formatter;

import java.time.LocalDateTime;
import java.util.Objects;

public class Friendship {
    private int ID;
    private int userID1;
    private int userID2;

    private LocalDateTime date;

    private boolean pending;

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Friendship(int ID, int userID1, int userID2, LocalDateTime date) {
        this.ID = ID;
        this.userID1 = userID1;
        this.userID2 = userID2;
        this.date = date;
        this.pending = false;
    }

    public Friendship(int ID, int userID1, int userID2, LocalDateTime date, boolean pending) {
        this.ID = ID;
        this.userID1 = userID1;
        this.userID2 = userID2;
        this.date = date;
        this.pending = pending;
    }

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }

    public int getID() {
        return ID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friendship that = (Friendship) o;
        return (this.ID == that.getID()) ||
                (userID1 == that.userID1 && userID2 == that.userID2) ||
                (userID1 == that.userID2 && userID2 == that.userID1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID1, userID2);
    }

    @Override
    public String toString() {
        return ID + ". user1: " + userID1 + "; user2: " + userID2 + "; date: " + date.format(Formatter.formatter).split(" ")[0];
    }
}
