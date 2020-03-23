package com.gmail.pavelchupin.net_storage.client.io;

import com.gmail.pavelchupin.net_storage.client.netty.Client;

public class Main /*extends Application*/ {


   /* @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/main.fxml"));
        primaryStage.setTitle("Net_Storage");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }*/


    public static void main(String[] args) {
        new Client().start();
        //launch(args);
    }
}
