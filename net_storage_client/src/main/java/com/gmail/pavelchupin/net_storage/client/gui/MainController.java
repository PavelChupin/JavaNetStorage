package com.gmail.pavelchupin.net_storage.client.gui;

import com.gmail.pavelchupin.net_storage.client.io.INetworkService;
import com.gmail.pavelchupin.net_storage.client.io.NetworkService;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private INetworkService networkService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.networkService = new NetworkService(this);
        this.networkService.start();
    }
}