package com.example.lab6_2v;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import repository.RepositoryFriendshipsDB;
import repository.RepositoryMessages;
import repository.RepositoryMessagesDB;
import repository.RepositoryUsersDB;
import service.Service;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("login-view.fxml"));
        VBox root = fxmlLoader.load();

        RepositoryFriendshipsDB repoFriendships = new RepositoryFriendshipsDB();
        RepositoryUsersDB repoUsers = new RepositoryUsersDB();
        RepositoryMessagesDB repositoryMessages = new RepositoryMessagesDB();
        Service service = Service.getService(repoUsers, repoFriendships, repositoryMessages);

        LogInController controller = fxmlLoader.getController();
        controller.setService(service);


        Scene scene = new Scene(root);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}