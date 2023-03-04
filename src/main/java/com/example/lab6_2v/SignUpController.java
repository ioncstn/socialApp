package com.example.lab6_2v;

import exceptions.RepositoryException;
import exceptions.ValidatorException;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.Service;

import java.io.IOException;

public class SignUpController {
    private Service service;

    @FXML
    private TextField name_field;
    @FXML
    private TextField email_field;
    @FXML
    private PasswordField password_field;
    @FXML
    private Label error_label;


    public void setService(Service service){
        this.service = service;
    }

    public void createAccount(ActionEvent event){
        try{
            service.addUser(name_field.getText(), email_field.getText(), password_field.getText());
            error_label.setTextFill(Paint.valueOf("GREEN"));
            error_label.setText("account created successfully");
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(o -> switchToLogInScene(event));
            pause.play();
        }
        catch(RepositoryException | ValidatorException ex){
            error_label.setText(ex.getMessage());
        }
    }

    public void switchToLogInScene(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("login-view.fxml"));
            Parent root = fxmlLoader.load();

            LogInController controller = fxmlLoader.getController();
            controller.setService(this.service);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("log in");
            stage.show();
        }
        catch(IOException e){
            System.exit(2);
        }
    }
}
