package client.viewModel;

import client.Dialog;
import client.Main;
import client.chat.ConnectionObservable;
import client.chat.ConnectionObserver;
import client.chat.NetConnection;
import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class StartupViewModel implements ViewModel {
    public BooleanProperty grid_user = new SimpleBooleanProperty();
    public BooleanProperty grid_server = new SimpleBooleanProperty();
    public BooleanProperty btn_server_connect = new SimpleBooleanProperty();
    public StringProperty txt_server_address = new SimpleStringProperty();
    public StringProperty txt_server_port = new SimpleStringProperty();

    public ConnectionObserver connectionObserver;

    public StartupViewModel() {
        initalizeView();
        setConnection();
    }

    private void setConnection() {
        Main.connectionObservable = new ConnectionObservable();
        connectionObserver = new ConnectionObserver(this);
        Main.connectionObservable.addObserver(connectionObserver);
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
                Dialog.Error("Connection failed", "No connection coul be established", "Please check the credentials and try again");
            });
        }
    }

    public void startConnection() {
        int port = Integer.parseInt(txt_server_port.get());
        String host = txt_server_address.get();
        Platform.runLater(() -> {
            btn_server_connect.set(true);
        });
        Main.startChatClientConnection(port, host);
    }
}
