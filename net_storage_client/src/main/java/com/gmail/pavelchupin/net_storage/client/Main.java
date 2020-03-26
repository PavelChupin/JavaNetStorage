package com.gmail.pavelchupin.net_storage.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/gui/main.fxml"));
        primaryStage.setTitle("Net Storage Client");
        primaryStage.setScene(new Scene(root, 472, 590));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
        //new Client().start();
    }
}





