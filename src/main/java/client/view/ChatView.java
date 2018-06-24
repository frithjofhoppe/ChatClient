package client.view;

import client.viewModel.ChatViewModel;
import de.saxsys.mvvmfx.FxmlView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatView implements FxmlView<ChatViewModel>, Initializable {

    @FXML
    Button btn_exit;
    @FXML
    Button btn_sendMessage;
    @FXML
    TextField txt_messageContent;
    @FXML
    VBox vbox_messages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}