package com.gmail.pavelchupin.net_storage.client.io;

import com.gmail.pavelchupin.net_storage.common.files.FileSerializable;
import com.gmail.pavelchupin.net_storage.common.oper.Operations;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class Main /*extends Application*/ {
    private static final String SERVER_PORT = "server.port";
    private static final String SERVER_URL = "server.url";
    private static final int READ_COUNT_BYTE = 10;

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
            //Сдесь будет выбор файла пока через консоль, а в дольнейшем через интерфейс
            String text = new BufferedReader(new InputStreamReader(System.in)).readLine();
            //out.writeUTF(text);


            //Операция закачки файла на сервер
            Path path = Paths.get("1", "2", "1.txt");
            InputStream in = new FileInputStream(path.toFile());
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutput objOut = new ObjectOutputStream(byteOut);
            byte[] arr = new byte[READ_COUNT_BYTE];
            FileSerializable file = null;
            int part = 0;
            int partCount = (int) Math.ceil(Files.size(path) / (double) READ_COUNT_BYTE);

            while (in.available() > 0) {
                part++;
                if (in.available() < READ_COUNT_BYTE) {
                    byte[] b = new byte[in.available()];
                    in.read(b);
                    arr = b;
                }else {
                    in.read(arr, 0, READ_COUNT_BYTE);
                }

                file = new FileSerializable(path.toString(), Files.size(path), part,partCount, arr,Operations.UPLOAD);

                //Сереализуем
                objOut.writeObject(file);
                System.out.println(byteOut.toString());
                //Оправляем на сервер
                out.writeUTF(/*Operations.UPLOAD + */byteOut.toString());

                objOut.flush();
                byteOut.flush();
            }

            objOut.close();
            byteOut.close();
            in.close();
            //}
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
