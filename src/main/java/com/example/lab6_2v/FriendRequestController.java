package com.example.lab6_2v;

import domain.FriendshipDTO;
import domain.User;
import domain.UserDTO;
import exceptions.RepositoryException;
import exceptions.ServiceException;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import service.Service;

import java.util.Timer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FriendRequestController {

    private Service service;

    private User current_user;

    private final ObservableList<UserDTO> modelUsers = FXCollections.observableArrayList();

    private final ObservableList<UserDTO> modelSentFriend = FXCollections.observableArrayList();

    @FXML
    private TableView<UserDTO> table_sent;

    @FXML
    private TableColumn<UserDTO, String> column_name_sent;

    @FXML
    private TableView<UserDTO> table_users;

    @FXML
    private TableColumn<UserDTO, String> column_name;

    @FXML
    private TextField field_name;

    @FXML
    private Button send_button;

    @FXML
    private Label success_label;

    public void setService(Service service){
        this.service = service;
    }

    public void setCurrentUser(User u){
        current_user = u;
        this.initialize();
    }

    private void initialize(){
        column_name.setCellValueFactory(new PropertyValueFactory<>("username"));
        column_name_sent.setCellValueFactory(new PropertyValueFactory<>("username"));

        this.update();

        table_users.setItems(modelUsers);
        table_sent.setItems(modelSentFriend);

        field_name.textProperty().addListener(o -> handleFilter());
    }

    private void update(){
        modelUsers.setAll(service.getNotFriends(current_user.getID()));
        modelSentFriend.setAll(service.getFriendRequestsSent(current_user.getID()));
    }

    private void handleFilter(){
        int prevLength = field_name.getText().length();
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(o -> {
            int postLength = field_name.getText().length();
            if (postLength == prevLength){
                var name = field_name.getText();
                Predicate<UserDTO> predicate = user -> user.getUsername().toLowerCase().contains(name.toLowerCase());
                modelUsers.setAll(service.getNotFriends(current_user.getID())
                        .stream()
                        .filter(predicate)
                        .collect(Collectors.toList())
                );

            }
            else{
                handleFilter();
            }
        });
        pause.play();
    }

    public void sendFriendRequest(ActionEvent event) throws ServiceException, RepositoryException {
        var user = table_users.getSelectionModel().getSelectedItem();
        if (user != null) {
            service.addFriendship(current_user.getID(), user.getID());
            update();
            success_label.setText("friend request sent successfully");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(o -> success_label.setText(""));
            pause.play();
        }
    }

    public void cancelFriendRequest(ActionEvent event) throws RepositoryException {
        var selected = table_sent.getSelectionModel().getSelectedItem();
        if (selected != null) {
            service.handleRequest(current_user.getID(), selected.getID(), false);
            this.update();
        }
    }
}
