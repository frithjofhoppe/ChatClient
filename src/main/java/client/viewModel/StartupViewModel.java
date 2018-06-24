package client.viewModel;

import client.Dialog;
import client.Main;
import client.chat.*;
import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StartupViewModel implements ViewModel {
    public BooleanProperty grid_user = new SimpleBooleanProperty();
    public BooleanProperty grid_server = new SimpleBooleanProperty();
    public BooleanProperty btn_server_connect = new SimpleBooleanProperty();
    public StringProperty txt_server_address = new SimpleStringProperty();
    public StringProperty txt_server_port = new SimpleStringProperty();

    public ConnectionObserver connectionObserver;
    public StartupUsernameObserver startupUsernameObserver;

    public StartupViewModel() {
        initalizeView();
    }

    private void setConnection() {
        connectionObserver = new ConnectionObserver(this);
        startupUsernameObserver = new StartupUsernameObserver(this);
        Main.appController.getChatClient().getConnectionObservable().addObserver(connectionObserver);
        Main.appController.getChatClient().getUsernameObservable().addObserver(startupUsernameObserver);
    }

    private void initalizeView() {
        grid_user.set(true);
        grid_server.set(false);
        btn_server_connect.set(false);
    }

    public void setConnectionState(boolean state) {
        if (state) {
            Platform.runLater(() -> {
                grid_server.set(true);
                grid_user.set(false);
                btn_server_connect.set(false);
            });
        } else {
            Platform.runLater(() -> {
                btn_server_connect.set(false);
                Dialog.Error("Connection failed", "No connection could be established", "Please check the credentials and try again");
            });
        }
    }

    public void setUsernameState(boolean state){
        System.out.println("Username is " + state);
    }

    public void startConnection() {
        int port = Integer.parseInt(txt_server_port.get());
        String host = txt_server_address.get();
        Main.appController.setChatClient(new ChatClient(host, port));
        setConnection();
        Main.appController.getChatClient().start();
        Platform.runLater(() -> {
            btn_server_connect.set(true);
        });
    }
}
