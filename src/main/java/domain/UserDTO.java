package domain;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserDTO {
    private final int ID;
    private final StringProperty username;

    public UserDTO(int ID, String username) {
        this.ID = ID;
        this.username = new SimpleStringProperty(username);
    }

    public void setUsername(String name){
        username.set(name);
    }
    public String getUsername(){
        return username.get();
    }
    public int getID(){
        return ID;
    }
}
