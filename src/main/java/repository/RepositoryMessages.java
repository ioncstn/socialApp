package repository;

import domain.Message;

import java.util.ArrayList;

public class RepositoryMessages {
    protected ArrayList<Message> messages;

    public RepositoryMessages() {
        messages = new ArrayList<>();
    }

    public void add(Message message){
        messages.add(message);
    }

    public ArrayList<Message> getAll(){
        return messages;
    }
}
