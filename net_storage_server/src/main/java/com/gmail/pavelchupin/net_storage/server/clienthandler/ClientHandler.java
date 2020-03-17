package com.gmail.pavelchupin.net_storage.server.clienthandler;

import com.gmail.pavelchupin.net_storage.common.files.FileSerializable;
import com.gmail.pavelchupin.net_storage.common.oper.Operations;
import com.gmail.pavelchupin.net_storage.server.Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String login = "user";

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            //Запускаем авторизацию.
            Thread authorizationClient = new Thread(new Runnable() {
                @Override
                public void run() {
                    //TO DO
                }
            });
            authorizationClient.start();
            authorizationClient.join();

            //Запускаем поток по чтению данных из потока от клиента
            Thread readThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        readMess();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } finally {
                        closeConnection();
                    }
                }
            });
            readThread.setDaemon(true);
            readThread.start();
            readThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void writeMess(String mess) throws IOException {
        out.writeUTF(mess);
    }

    private void readMess() throws IOException, ClassNotFoundException {
        String mess = null;
        while (true) {
            mess = in.readUTF();

            System.out.println(mess);
            //System.out.println(mess.replaceFirst(Operations.UPLOAD.toString(), ""));

            //Если прилетел запрос на закачку файла на сервер
            if (mess.startsWith(Operations.UPLOAD.toString())) {
                ByteArrayInputStream byteIn = new ByteArrayInputStream(mess.replaceFirst(Operations.UPLOAD.toString(), "").getBytes());
                ObjectInputStream objIn = new ObjectInputStream(byteIn);
                FileSerializable file = (FileSerializable) objIn.readObject();

                //Сохраняем файл на сервере
                server.saveFileToServer(file, this);

                objIn.close();
                byteIn.close();
            } //Если прилетел запрос на скачку файла
            else if (mess.startsWith(Operations.DOWNLOAD.toString())) {

            } //Если прилетел запрос на дерево каталогов.
            else if (mess.startsWith(Operations.DIR.toString())) {

            }
        }

    }

    private void closeConnection() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogin() {
        return login;
    }
}
