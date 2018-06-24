package client.view;

import client.viewModel.ChatViewModel;
import de.saxsys.mvvmfx.FxmlView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

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

    int message = 1;

    @FXML
    public void handler_btn_sendMessage_click(){
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
