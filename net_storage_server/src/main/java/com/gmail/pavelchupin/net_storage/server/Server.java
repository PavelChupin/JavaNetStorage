package com.gmail.pavelchupin.net_storage.server;

import com.gmail.pavelchupin.net_storage.server.clienthandler.ClientHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final String SERVER_PORT = "server.port";

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(new FileInputStream("D:/GitRepository/LearnGeekBrains/Курс четверть 2 - Разработка сетевого хранилища на Java/HomeWork/net_storage_server/src/main/resources/conf.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ExecutorService executorService = Executors.newCachedThreadPool();
    private List<ClientHandler> clientHandlers = new ArrayList<>();

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(Integer.parseInt(prop.getProperty(SERVER_PORT)))) {
            while (true) {
                //Ожидаем подключение пользователей
                System.out.println("Wait client connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Client is connect...");

                //Создаем объект клиентского подключения
                ClientHandler clientHandler = new ClientHandler(socket, this);
                //Добавляем и запускаем клиентский поток
                executorService.execute(clientHandler);
                //Записываем в список нового подключившегося клиента
                clientHandlers.add(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
            //Если на сервере произошла ошибка гасим все клиентские потоки
            executorService.shutdownNow();
        }
    }
}
