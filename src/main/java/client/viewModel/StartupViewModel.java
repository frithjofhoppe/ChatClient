package client.viewModel;

import client.Dialog;
import client.Main;
import client.chat.*;
import com.sun.media.jfxmedia.events.PlayerStateEvent;
import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StartupViewModel implements ClientFxmlViewModel {
    public BooleanProperty grid_user = new SimpleBooleanProperty();
    public BooleanProperty grid_server = new SimpleBooleanProperty();
    public BooleanProperty btn_server_connect = new SimpleBooleanProperty();
    public BooleanProperty btn_user_twaddle = new SimpleBooleanProperty();
    public BooleanProperty progress_serverConnect = new SimpleBooleanProperty();
    public StringProperty txt_server_address = new SimpleStringProperty();
    public StringProperty txt_server_port = new SimpleStringProperty();
    public StringProperty lbl_server_status = new SimpleStringProperty();
    public StringProperty lbl_user_status = new SimpleStringProperty();
    public StringProperty txt_user_username = new SimpleStringProperty();

    public ConnectionObserver connectionObserver;
    public StartupUsernameObserver startupUsernameObserver;
    public MessageSenderObservable messageSenderObservable;

    private boolean isCurrentView = true;

    public StartupViewModel() {
        initalizeView();
    }

    private void setConnection() {
        connectionObserver = new ConnectionObserver(this);
        startupUsernameObserver = new StartupUsernameObserver(this);
        messageSenderObservable = Main.appController.getChatClient().getMessageSenderObservable();
        Main.appController.getChatClient().getConnectionObservable().addObserver(connectionObserver);
        Main.appController.getChatClient().getUsernameObservable().addObserver(startupUsernameObserver);
    }

    private void initalizeView() {
        isCurrentView = true;
        grid_user.set(true);
        grid_server.set(false);
        btn_server_connect.set(false);
        btn_user_twaddle.set(false);
        lbl_server_status.set("");
        lbl_user_status.set("");
        txt_server_address.set("");
        txt_server_port.set("");
        txt_user_username.set("");
    }

    @Override
    public void resetView() {
        Platform.runLater(() ->{
            initalizeView();
        });
        connectionObserver = null;
        startupUsernameObserver = null;
        messageSenderObservable = null;
    }

    public void setConnectionState(boolean state) {
        if (state) {
            Platform.runLater(() -> {
                grid_server.set(true);
                grid_user.set(false);
                btn_server_connect.set(false);
                progress_serverConnect.set(false);
                lbl_server_status.set("Establishing suceeded");
            });
        } else if(isCurrentView) {
            Platform.runLater(() -> {
                btn_server_connect.set(false);
                lbl_server_status.set("Establishing failed");
                progress_serverConnect.set(false);
                Dialog.Error("Connection failed", "No connection is currently available", "Please try again");
            });
        }
    }

    public void setUsernameState(boolean state) {
        if (state) {
            System.out.println("Username is valid");
            Platform.runLater(() -> {
                lbl_user_status.set("Valid");
                isCurrentView = false;
                Main.appController.loadView("chat");
            });
        } else {
            System.out.println("Username is already taken");
            Platform.runLater(() -> {
                lbl_user_status.set("Invalid");
                Dialog.Error("Username already taken","This name is already in use by another user", "Please try another one");
            });
        }
        Platform.runLater(() -> {
            btn_user_twaddle.set(false);
        });
    }

    public void registerWithUsername(String username) {
        if(username.matches("^((?!:).)*$")) {
            Platform.runLater(() -> {
                btn_user_twaddle.set(true);
                lbl_user_status.set("Checking");
            });
            Main.appController.getChatClient().getUsernameService().setUsername(username);
            messageSenderObservable.sendMessage(username);
        }else{
            Platform.runLater(() -> {
                Dialog.InvalidCharachter();
            });
        }
    }

    public void startConnection() {
        int port = Integer.parseInt(txt_server_port.get());
        String host = txt_server_address.get();
        Main.appController.setChatClient(new ChatClient(host, port));
        setConnection();
        Main.appController.getChatClient().start();
        Platform.runLater(() -> {
            btn_server_connect.set(true);
            progress_serverConnect.set(true);
            lbl_server_status.set("Trying to connect...");
        });
    }
}
