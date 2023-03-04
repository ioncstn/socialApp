package com.example.lab6_2v;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import service.Service;

import java.io.IOException;

public class SettingsController {

    private Service service;

    public void setService(Service service){
        this.service = service;
    }

    public void switchToLoginScene(ActionEvent event){
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
