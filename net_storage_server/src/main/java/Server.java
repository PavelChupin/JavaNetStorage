import client.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Server {
    private static final String SERVER_PORT = "server.port";
    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new FileInputStream("/conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<ClientHandler> clientHandlers = new ArrayList<>();

    public Server()  {
        try(ServerSocket serverSocket = new ServerSocket(Integer.parseInt(prop.getProperty(SERVER_PORT)))){

            //Ожидаем подключение пользователей
            Socket socket = serverSocket.accept();

            clientHandlers.add(new ClientHandler(socket,this));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
