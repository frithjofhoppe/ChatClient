package client;

import client.chat.ChatClient;
import client.chat.ConnectionObservable;
import client.view.StartupView;
import client.viewModel.StartupViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    static Map<String, ViewTuple> views = new HashMap<>();
    static Stage stage;
    static ChatClient chatClient;
    public static ConnectionObservable connectionObservable;
    static final int WIDTH = 412;
    static final int HEIGHT = 491;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeViews();
        stage = primaryStage;
        stage.setResizable(false);
        stage.setTitle("Twaddle");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("view/conversation.png")));
        loadView("startup");
    }

    public static void initializeViews() {
        ViewTuple<StartupView, StartupViewModel> startupView = FluentViewLoader.fxmlView(StartupView.class).load();
        views.put("startup", startupView);
    }

    public static void loadView(String name) {
        ViewTuple tuple = views.get(name);
        if (tuple != null) {
            Parent root = tuple.getView();
            stage.setScene(new Scene(root, WIDTH, HEIGHT));
            stage.show();
        }
    }

    public static void startChatClientConnection(int port, String host){
        chatClient = new ChatClient(host, port, connectionObservable);
        chatClient.startConnection();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
