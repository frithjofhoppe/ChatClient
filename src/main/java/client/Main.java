package client;

import client.chat.ChatClient;
import client.chat.ConnectionObservable;
import client.chat.MessageReceiverObservable;
import client.view.StartupView;
import client.viewModel.StartupViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.omg.CORBA.Environment;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    public static AppController appController;
    public static final int width = 412;
    public static final int height = 491;

    @Override
    public void start(Stage primaryStage) throws Exception {
        appController = new AppController(width, height, primaryStage);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Twaddle");
        primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("view/conversation.png")));
        appController.start();
    }

    @Override
    public void stop() throws Exception {
        if (appController.getChatClient() != null) {
            Platform.runLater(() -> {
                appController.getChatClient().stopServer();
            });
        }
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
