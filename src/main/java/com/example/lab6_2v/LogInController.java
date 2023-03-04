package com.example.lab6_2v;

import domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class LogInController {
    private Service service;
    @FXML
    private TextField email_field;
    @FXML
    private PasswordField password_field;
    @FXML
    private Label suc;

    public void setService(Service service){
        this.service = service;
    }

    public void checkLogin(ActionEvent event){
        var email = email_field.getText();
        var password = password_field.getText();
        var cur_user = service.checkUserPresent(email, password);
        if(cur_user.isPresent()){
            switchToLoggedInScene(event, cur_user.get());
        }
        else{
            suc.setText("User with given email does not exist or password is not correct! Try again.");
        }
    }


    public void switchToLoggedInScene(ActionEvent event, User u){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("tabs.fxml"));
            Parent root = fxmlLoader.load();

            MainController controller = fxmlLoader.getController();
            controller.setService(this.service);
            controller.setCurrentUser(u);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("logged in");
            stage.show();
        }
        catch(IOException e){
            System.exit(3);
        }
    }
    public void switchToSignUpScene(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sign-up.fxml"));
            Parent root = fxmlLoader.load();

            SignUpController controller = fxmlLoader.getController();
            controller.setService(this.service);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("sign up");
            stage.show();
        }
        catch(IOException e){
            System.exit(2);
        }
    }
}