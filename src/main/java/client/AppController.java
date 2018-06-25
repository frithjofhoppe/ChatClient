package client;

import client.chat.ChatClient;
import client.view.ChatView;
import client.view.StartupView;
import client.viewModel.ChatViewModel;
import client.viewModel.StartupViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class AppController {

    Map<String, ViewTuple> views = new HashMap<>();
    Stage stage;
    int width = 412;
    int height = 491;
    ChatClient chatClient;
    private boolean hasStarted = false;
    private boolean initalized = false;
    private Scene scene;

    public AppController(int width, int height, Stage stage) {
        this.width = width;
        this.height = height;
        this.stage = stage;
        initializeViews();
    }

    public void start() {
        if (!hasStarted) {
            loadView("startup");
        }
        hasStarted = true;
    }

    public void initializeViews() {
        ViewTuple<StartupView, StartupViewModel> startupView = FluentViewLoader.fxmlView(StartupView.class).load();
        views.put("startup", startupView);

        ViewTuple<ChatView, ChatViewModel> chatView = FluentViewLoader.fxmlView(ChatView.class).load();
        views.put("chat", chatView);
    }

    public void loadView(String name) {
        ViewTuple tuple = views.get(name);
        if (tuple != null) {
            if (scene == null) {
                scene = new Scene(tuple.getView(), width, height);
                stage.setScene(scene);
                stage.show();
            } else {
                Platform.runLater(() -> {
                    scene.setRoot(tuple.getView());
                        stage.show();
                });
            }

        }
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
}
