package com.gmail.pavelchupin.net_storage.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

public class Main extends Application {
    private static final String SERVER_PORT = "server.port";
    private static final String SERVER_URL = "server.url";

    private static Properties prop = new Properties();
    private static DataOutputStream out;
    private static DataInputStream in;


    static {
        try {
            prop.load(new FileInputStream("D:/GitRepository/LearnGeekBrains/Курс четверть 2 - Разработка сетевого хранилища на Java/HomeWork/net_storage_client/src/main/resources/conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("/gui/main.fxml"));
        primaryStage.setTitle("Net_Storage");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        //launch(args);
        try {
            Socket socket = new Socket(prop.getProperty(SERVER_URL),Integer.parseInt(prop.getProperty(SERVER_PORT)));
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(in.readUTF());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            readThread.setDaemon(true);
            readThread.start();

            while (true){
                String text = new BufferedReader(new InputStreamReader(System.in)).readLine();
                out.writeUTF(text);
                //System.out.println(in.readUTF());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
