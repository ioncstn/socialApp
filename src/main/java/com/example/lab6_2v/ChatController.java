package com.example.lab6_2v;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.Service;

public class ChatController {

    private Service service;
    private User currentUser;
    private int friendSelected = -1;
    private ObservableList<FriendshipDTO> modelFriends = FXCollections.observableArrayList();
    private ObservableList<MessageDTO> modelMessages = FXCollections.observableArrayList();
    @FXML
    private TableView<FriendshipDTO> friendsTable;
    @FXML
    private TableColumn<FriendshipDTO, String> column_name;
    @FXML
    private TextArea message;
    @FXML
    private ListView<MessageDTO> messagesList;

    public void setService(Service service){
        this.service = service;
    }
    public void setCurrentUser(User user){
        this.currentUser = user;
        this.init();
    }
    private void init(){
        column_name.setCellValueFactory(new PropertyValueFactory<>("username"));

        this.updateFriends(currentUser);

        friendsTable.setItems(modelFriends);
        friendsTable.getSelectionModel().selectedItemProperty().addListener(o -> update());
        messagesList.setItems(modelMessages);
        messagesList.setCellFactory(new Callback<ListView<MessageDTO>, ListCell<MessageDTO>>() {
            @Override
            public ListCell<MessageDTO> call(ListView<MessageDTO> param) {
                return new CustomListViewCellMessage();
            }
        });
    }

    public void sendMessage(ActionEvent event){
        var text = message.getText();
        if (!text.isEmpty() && friendSelected != -1){
            service.addMessage(currentUser.getID(), friendSelected, text);
            message.clear();
            update();
        }
    }

    private void update(){
        if(friendsTable.getSelectionModel().getSelectedItem() != null) {
            friendSelected = friendsTable.getSelectionModel().getSelectedItem().getID();
            modelMessages.setAll(service.getMessagesBetween(currentUser.getID(), friendSelected, currentUser.getUsername(), friendsTable.getSelectionModel().getSelectedItem().getUsername()));
        }
        else{
            friendSelected = -1;
            modelMessages.clear();
            message.clear();
        }
    }

    public void updateFriends(User user){
        modelFriends.setAll(service.getFriendsOf(user));
    }
}
