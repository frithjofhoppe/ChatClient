package client.view;

import client.Dialog;
import client.viewModel.ChatViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatView implements FxmlView<ChatViewModel>, Initializable {

    @FXML
    ImageView btn_exit;
    @FXML
    ImageView btn_sendMessage;
    @FXML
    TextField txt_messageContent;
    @FXML
    VBox vbox_messages;

    ObservableList<Node> vbox;

    @InjectViewModel
    private ChatViewModel viewModel;

    @FXML
    public void handler_btn_sendMessage_click() {
        String content = txt_messageContent.getText();
        viewModel.sendMessage(content);
    }

    @FXML
    public void handler_txt_messageContent_kex_pressed(javafx.scene.input.KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            String content = txt_messageContent.getText();
            if(content.trim().length() > 0) {
                viewModel.sendMessage(content);
            }else {
                Dialog.InvalidContent();
            }
        }
    }

    @FXML
    public void initMove() {
        viewModel.initalizeConnection();
    }

    @FXML
    public void handler_btn_exit_click() {
        viewModel.connectionClosedByView();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txt_messageContent.textProperty().bindBidirectional(viewModel.txt_messageContent);
        vbox_messages.setSpacing(5);
        viewModel.list_messages = vbox_messages.getChildren();
        viewModel.list = vbox_messages;
    }
}
