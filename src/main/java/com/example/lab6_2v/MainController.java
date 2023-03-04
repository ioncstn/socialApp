package com.example.lab6_2v;

import domain.User;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import service.Service;

import java.util.Objects;

public class MainController {
    @FXML
    private TabPane tabPane;
    private Service service;
    private User currentUser;
    @FXML
    private AnchorPane friendshipPage;
    @FXML
    private AnchorPane chatPage;
    @FXML
    private AnchorPane settingsPage;
    @FXML
    private SettingsController settingsPageController;
    @FXML
    private FriendshipsController friendshipPageController;
    @FXML
    private ChatController chatPageController;

    public void setService(Service service){
        this.service = service;
    }
    public void setCurrentUser(User user){
        this.currentUser = user;
        this.init();
    }

    public void init(){
        friendshipPageController.setService(service);
        friendshipPageController.setCurrentUser(currentUser);
        chatPageController.setService(service);
        chatPageController.setCurrentUser(currentUser);
        settingsPageController.setService(service);
        tabPane.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {
                        if(Objects.equals(newTab.getText(), "chat")) {
                            chatPageController.updateFriends(currentUser);
                        }
                    }
                }
        );
    }

    public void changedTab(){
    }
}
