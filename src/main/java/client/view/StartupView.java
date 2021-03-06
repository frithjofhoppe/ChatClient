package client.view;

import client.Dialog;
import client.viewModel.StartupViewModel;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StartupView implements FxmlView<StartupViewModel>, Initializable {

    @FXML
    TextField txt_server_address;
    @FXML
    TextField txt_server_port;
    @FXML
    Label lbl_server_status;
    @FXML
    Button btn_server_connect;
    @FXML
    TextField txt_user_username;
    @FXML
    Label lbl_user_status;
    @FXML
    Button btn_user_twaddle;
    @FXML
    GridPane grid_user;
    @FXML
    GridPane grid_server;
    @FXML
    ProgressIndicator progress_serverConnect;

    @InjectViewModel
    private StartupViewModel viewModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grid_user.disableProperty().bind(viewModel.grid_user);
        grid_server.disableProperty().bind(viewModel.grid_server);
        txt_server_address.textProperty().bindBidirectional(viewModel.txt_server_address);
        txt_server_port.textProperty().bindBidirectional(viewModel.txt_server_port);
        btn_server_connect.disableProperty().bindBidirectional(viewModel.btn_server_connect);
        btn_user_twaddle.disableProperty().bindBidirectional(viewModel.btn_user_twaddle);
        lbl_server_status.textProperty().bindBidirectional(viewModel.lbl_server_status);
        lbl_user_status.textProperty().bindBidirectional(viewModel.lbl_user_status);
        txt_user_username.textProperty().bindBidirectional(viewModel.txt_user_username);
        progress_serverConnect.visibleProperty().bindBidirectional(viewModel.progress_serverConnect);
    }

    @FXML
    public void handler_btn_user_twaddle_click(){
        String username = txt_user_username.getText();
        System.out.println(username);
        if(username.length() > 0){
            viewModel.registerWithUsername(username);
        }else{
            Dialog.InvalidContent();
        }
    }

    @FXML
    public void handler_btn_server_connect_click() {
        String portContent = txt_server_port.getText();
        String addressContent = txt_server_address.getText();
        boolean portValid = false;
        boolean addressValid = false;

        System.out.println(portContent);
        System.out.println(addressContent);

        if (portContent.matches("^\\d{5}$")) {
            int port = Integer.parseInt(portContent);
            if (port >= 50000 && port <= 52000) {
                portValid = true;
            }
        }

        if (addressContent.matches("^\\S+$") && addressContent.length() > 2) {
            addressValid = true;
        }

        if (addressValid && portValid) {
            viewModel.startConnection();
        }else{
            Dialog.InvalidContent();
        }
    }
}
