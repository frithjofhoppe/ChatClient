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

public class StartupViewModel implements ViewModel {
    public BooleanProperty grid_user = new SimpleBooleanProperty();
    public BooleanProperty grid_server = new SimpleBooleanProperty();
    public BooleanProperty btn_server_connect = new SimpleBooleanProperty();
    public BooleanProperty btn_user_twaddle = new SimpleBooleanProperty();
    public StringProperty txt_server_address = new SimpleStringProperty();
    public StringProperty txt_server_port = new SimpleStringProperty();
    public StringProperty lbl_server_status = new SimpleStringProperty();
    public StringProperty lbl_user_status = new SimpleStringProperty();

    public ConnectionObserver connectionObserver;
    public StartupUsernameObserver startupUsernameObserver;
    public MessageSenderObservable messageSenderObservable;

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
        grid_user.set(true);
        grid_server.set(false);
        btn_server_connect.set(false);
        btn_user_twaddle.set(false);
        lbl_server_status.set("");
        lbl_user_status.set("");
    }

    public void setConnectionState(boolean state) {
        if (state) {
            Platform.runLater(() -> {
                grid_server.set(true);
                grid_user.set(false);
                btn_server_connect.set(false);
                lbl_server_status.set("Establishing suceeded");
            });
        } else {
            Platform.runLater(() -> {
                btn_server_connect.set(false);
                lbl_server_status.set("Establishing failed");
                Dialog.Error("Connection failed", "No connection could be established", "Please check the credentials and try again");
            });
        }
    }

    public void setUsernameState(boolean state){
        if(state){
            System.out.println("Username is valid");
            Platform.runLater(() ->{
                lbl_user_status.set("Valid");
                Main.appController.loadView("chat");
            });
        }else {
            System.out.println("Username is already taken");
            Platform.runLater(() ->{
                lbl_user_status.set("Invalid");
            });
        }
        Platform.runLater(() -> {
            btn_user_twaddle.set(false);
        });
    }

    public void registerWithUsername(String username){
        Platform.runLater(() -> {
            btn_user_twaddle.set(true);
            lbl_user_status.set("Checking");
        });
        Main.appController.getChatClient().getUsernameService().setUsername(username);
        messageSenderObservable.sendMessage(username);
    }

    public void startConnection() {
        int port = Integer.parseInt(txt_server_port.get());
        String host = txt_server_address.get();
        Main.appController.setChatClient(new ChatClient(host, port));
        setConnection();
        Main.appController.getChatClient().start();
        Platform.runLater(() -> {
            btn_server_connect.set(true);
            lbl_server_status.set("Trying to connect...");
        });
    }
}
