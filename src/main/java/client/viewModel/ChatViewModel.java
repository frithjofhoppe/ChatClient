package client.viewModel;

import client.Dialog;
import client.Main;
import client.chat.*;
import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ChatViewModel implements ClientFxmlViewModel {
    public StringProperty txt_messageContent = new SimpleStringProperty();
    private ArrayList<Label> messages = new ArrayList<>();
    public ObservableList<Node> list_messages;
    public VBox list;

    private MessageSenderObservable messageSenderObservable;
    private MessageReceiverObservable messageReceiverObservable;
    private ChatConnectionObserver chatConnectionObserver;
    private ChatMessageObserver chatMessageObserver;
    private UsernameService usernameService;
    private String username = "";
    private boolean isCurrentView = false;
    private boolean notHandling = false;

    public ChatViewModel() {
        initializeViews();
    }

    public void initalizeConnection() {
//        if (chatMessageObserver == null) {
//            setConnection();
//            System.out.println("Entered");
//        }
    }

    private void setConnection() {
        messageSenderObservable = Main.appController.getChatClient().getMessageSenderObservable();
        messageReceiverObservable = Main.appController.getChatClient().getMessageReceiverObservable();
        chatMessageObserver = new ChatMessageObserver(this);
        chatConnectionObserver = new ChatConnectionObserver(this);
        Main.appController.getChatClient().getConnectionObservable().addObserver(chatConnectionObserver);
        messageReceiverObservable.addObserver(chatMessageObserver);
        usernameService = Main.appController.getChatClient().getUsernameService();
    }

    private void initializeViews() {
        isCurrentView = true;
        txt_messageContent.set("");
        if (list_messages != null) list_messages.clear();
        messageSenderObservable = null;
        messageReceiverObservable = null;
        chatConnectionObserver = null;
        chatMessageObserver = null;
        usernameService = null;
        username = null;
    }

    public void receiveMessage(String message) {
        updateMessages(message);
    }

    private HBox generateMessage(String message, String user, boolean orientation) {
        HBox h1 = new HBox();
        h1.setMinWidth(Region.USE_COMPUTED_SIZE);
        h1.setMinHeight(Region.USE_COMPUTED_SIZE);
        h1.prefWidth(415);
        h1.prefHeight(Region.USE_COMPUTED_SIZE);
        h1.setMaxHeight(Region.USE_COMPUTED_SIZE);
        h1.setMaxWidth(Region.USE_PREF_SIZE);
        h1.setAlignment(Pos.TOP_LEFT);
        h1.setNodeOrientation(orientation ? NodeOrientation.RIGHT_TO_LEFT : NodeOrientation.LEFT_TO_RIGHT);

        VBox v1 = new VBox();
        v1.setMinWidth(Region.USE_COMPUTED_SIZE);
        v1.setMinHeight(Region.USE_COMPUTED_SIZE);
        v1.prefWidth(313);
        v1.prefHeight(Region.USE_COMPUTED_SIZE);
        v1.setMaxHeight(Region.USE_COMPUTED_SIZE);
        v1.setMaxWidth(Region.USE_COMPUTED_SIZE);
        v1.setAlignment(Pos.TOP_LEFT);
        v1.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        HBox h2 = new HBox();
        h2.setMinWidth(Region.USE_COMPUTED_SIZE);
        h2.setMinHeight(Region.USE_COMPUTED_SIZE);
        h2.prefWidth(Region.USE_COMPUTED_SIZE);
        h2.prefHeight(Region.USE_COMPUTED_SIZE);
        h2.setMaxHeight(Region.USE_COMPUTED_SIZE);
        h2.setMaxWidth(Region.USE_COMPUTED_SIZE);
        h2.setAlignment(Pos.TOP_LEFT);
        h2.setNodeOrientation(NodeOrientation.INHERIT);

        Label username = new Label();
        username.setText(user);
        username.setStyle("-fx-font-weight: bold;");
        username.setWrapText(true);
        username.setMinWidth(Region.USE_COMPUTED_SIZE);
        username.setMinHeight(Region.USE_COMPUTED_SIZE);
        username.prefWidth(Region.USE_COMPUTED_SIZE);
        username.prefHeight(Region.USE_COMPUTED_SIZE);
        username.setMaxHeight(Region.USE_COMPUTED_SIZE);
        username.setMaxWidth(Region.USE_COMPUTED_SIZE);

        Date date = new Date();   // given date
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(date);   // assigns calendar to given date
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)); // gets hour in 24h format
        String minutes = String.valueOf(calendar.get(Calendar.MINUTE));

        if (minutes.length() < 2) minutes = "0" + minutes;

        Label time = new Label();
        time.setText(hour + ":" + minutes);
        time.setStyle("-fx-font-weight: bold;");
        time.setWrapText(true);
        time.setMinWidth(Region.USE_COMPUTED_SIZE);
        time.setMinHeight(Region.USE_COMPUTED_SIZE);
        time.prefWidth(Region.USE_COMPUTED_SIZE);
        time.prefHeight(Region.USE_COMPUTED_SIZE);
        time.setMaxHeight(Region.USE_COMPUTED_SIZE);
        time.setMaxWidth(Region.USE_COMPUTED_SIZE);

        Label content = new Label();
        content.setText(message);
        content.setWrapText(true);
        content.setMinWidth(Region.USE_COMPUTED_SIZE);
        content.setMinHeight(Region.USE_COMPUTED_SIZE);
        content.prefWidth(Region.USE_COMPUTED_SIZE);
        content.prefHeight(Region.USE_COMPUTED_SIZE);
        content.setMaxHeight(Region.USE_COMPUTED_SIZE);
        content.setMaxWidth(Region.USE_COMPUTED_SIZE);
        content.setContentDisplay(ContentDisplay.LEFT);
        content.setAlignment(Pos.TOP_LEFT);
        content.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        h2.getChildren().addAll(username, time);
        h2.setSpacing(3);
        v1.getChildren().addAll(h2, content);
        h1.getChildren().add(v1);

        return h1;
    }

    private void updateMessages(String message) {

        boolean personal;
        String user;
        String[] splitted = message.split(":");

        if (usernameService.getUsername().equals(splitted[0])) {
            personal = false;
            user = usernameService.getUsername();
        } else {
            personal = true;
            user = splitted[0];
        }

        Platform.runLater(() -> {
            HBox toSet = generateMessage(splitted[1], user, personal);
            list_messages.add(toSet);
        });
    }

    public void connectionClosedByObservable() {
        if (!notHandling) {
            closeConnection();
            Platform.runLater(() -> {
                Dialog.Error("Connection closed", "Server connection has sbruptely been closed", "Try to connect again");
            });
            Main.appController.loadView("startup");
        } else {
            notHandling = false;
        }
    }

    public void sendMessage(String message) {
        if (message.matches("^((?!:).)*$")) {
            System.out.println("Send");
            messageSenderObservable.sendMessage(message);
            Platform.runLater(() -> {
                txt_messageContent.set("");
            });
        } else {
            Platform.runLater(() -> {
                Dialog.InvalidCharachter();
            });
        }
    }

    public void connectionClosedByView() {
        notHandling = true;
        closeConnection();
        Main.appController.loadView("startup");
    }

    public void closeConnection() {
        Main.appController.getChatClient().stopServer();
        isCurrentView = false;
    }

    @Override
    public void resetView() {
        initializeViews();
        if (messageSenderObservable == null) setConnection();
    }
}
