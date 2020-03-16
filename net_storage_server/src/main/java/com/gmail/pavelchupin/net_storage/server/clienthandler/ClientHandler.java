package com.gmail.pavelchupin.net_storage.server.clienthandler;

import com.gmail.pavelchupin.net_storage.server.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientHandler(Socket socket, Server server) throws IOException {
        this.server = server;
        this.socket = socket;
        this.in = new DataInputStream(socket.getInputStream());
        this.out = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        //Запускаем поток по чтению данных из потока от клиента
        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    readMess();
                } catch (IOException e) {
                    closeConnection();
                }
            }
        });
        readThread.setDaemon(true);
        readThread.start();

        //Запускаем поток по записи данных в поток для клиента
        /*Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    writeMess();
                } catch (IOException e) {
                    closeConnection();
                }
            }
        });*/
        //writeThread.setDaemon(true);
        //writeThread.start();


        while (true) {

        }
    }

    private void writeMess(String mess) throws IOException {
        out.writeUTF(mess);
    }

    private void readMess() throws IOException {
        String mess = null;
        while (true) {
            mess = in.readUTF();
            System.out.println(mess);

            if ("W".equalsIgnoreCase(mess.split(" ")[0]))
            {
                writeMess("ok");
            }
        }
    }

    private void closeConnection() {
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
