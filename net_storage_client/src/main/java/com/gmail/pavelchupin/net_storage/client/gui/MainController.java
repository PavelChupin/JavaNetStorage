package com.gmail.pavelchupin.net_storage.client.gui;

import com.gmail.pavelchupin.net_storage.client.io.INetworkService;
import com.gmail.pavelchupin.net_storage.client.io.NetworkService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public @FXML
    Button buttonDownload;

    public @FXML
    Button buttonUpload;

    public @FXML
    Button buttonDir;

    private INetworkService networkService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //this.networkService = new NetworkService(this);
        //this.networkService.start();
    }

    @FXML
    public void downloadFiles(ActionEvent actionEvent) {

    }

    @FXML
    public void uploadFiles(ActionEvent actionEvent) {

    }

    @FXML
    public void getDir(ActionEvent actionEvent) {

    }


}