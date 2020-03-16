package com.gmail.pavelchupin.net_storage.client;

import com.gmail.pavelchupin.net_storage.common.files.FileSerializable;
import com.gmail.pavelchupin.net_storage.common.oper.Operations;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main /*extends Application*/ {
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

   /* @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/gui/main.fxml"));
        primaryStage.setTitle("Net_Storage");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }*/


    public static void main(String[] args) {
        //launch(args);
        try {
            Socket socket = new Socket(prop.getProperty(SERVER_URL), Integer.parseInt(prop.getProperty(SERVER_PORT)));
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String text = null;
                    try {
                        while (true) {
                            text = in.readUTF();
                            System.out.println(text);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            readThread.setDaemon(true);
            readThread.start();

            //while (true) {
                String text = new BufferedReader(new InputStreamReader(System.in)).readLine();
                //out.writeUTF(text);

                Path path = Paths.get("1", "2", "1.txt");
                byte[] arr = Files.readAllBytes(path);
                FileSerializable file = new FileSerializable(path.toString(), Files.size(path), 1, 1, arr);
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutput objOut = new ObjectOutputStream(byteOut);
                objOut.writeObject(file);
                out.writeUTF(Operations.UPLOAD + byteOut.toString());

                objOut.close();
                byteOut.close();
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
