package com.example.lab6_2v;

import domain.FriendshipDTO;
import domain.User;
import exceptions.RepositoryException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;
import java.time.LocalDateTime;

public class FriendshipsController {
    private Service service;

    private final ObservableList<FriendshipDTO> modelFriends = FXCollections.observableArrayList();

    private final ObservableList<FriendshipDTO> modelPending = FXCollections.observableArrayList();

    private User current_user;
    @FXML
    private TableColumn<FriendshipDTO, String> username_column_pending;
    @FXML
    private TableView<FriendshipDTO> pending_table;
    @FXML
    private TableColumn<FriendshipDTO, String> username_column_friends;
    @FXML
    private TableColumn<FriendshipDTO, LocalDateTime> date_column_friends;
    @FXML
    private TableView<FriendshipDTO> friends_table;

    public void setService(Service service){
        this.service = service;
    }

    public void setCurrentUser(User u){
        current_user = u;
        this.init();
    }

    public void openFriendRequestWindow(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("friend-request.fxml"));
        Parent root = fxmlLoader.load();
        FriendRequestController controller = fxmlLoader.getController();
        controller.setService(this.service);
        controller.setCurrentUser(current_user);
        Stage stage = new Stage();
        stage.setTitle("send friend requests u noob");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void init(){
        username_column_pending.setCellValueFactory(new PropertyValueFactory<>("username"));
        username_column_friends.setCellValueFactory(new PropertyValueFactory<>("username"));
        date_column_friends.setCellValueFactory(new PropertyValueFactory<>("date"));

        this.update();

        friends_table.setItems(modelFriends);

        pending_table.setItems(modelPending);

        /*

        friends_table.getSelectionModel().selectedItemProperty().addListener(o -> {
            button_delete_friend.setDisable(false);
        });

        pending_table.getSelectionModel().selectedItemProperty().addListener(o -> {
            button_accept_fr.setDisable(false);
            button_decline_fr.setDisable(false);
        });

        button_delete_friend.setDisable(true);
        button_accept_fr.setDisable(true);
        button_decline_fr.setDisable(true);
        */
    }

    public void acceptRequest(ActionEvent event) throws RepositoryException {
        var selected = pending_table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.handleRequest(current_user.getID(), selected.getID(), true);
            this.update();
        }
    }

    public void declineRequest(ActionEvent event) throws RepositoryException {
        var selected = pending_table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.handleRequest(current_user.getID(), selected.getID(), false);
            this.update();
        }
    }

    public void deleteFriend(ActionEvent event) throws RepositoryException {
        var selected = friends_table.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.handleRequest(current_user.getID(), selected.getID(), false);
            this.update();
        }
    }

    private void update(){
        modelPending.setAll(service.getPendingOf(current_user));
        modelFriends.setAll(service.getFriendsOf(current_user));
    }
}