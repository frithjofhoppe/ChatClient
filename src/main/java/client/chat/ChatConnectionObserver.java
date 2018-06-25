package client.chat;

import client.viewModel.ChatViewModel;

import java.util.Observable;
import java.util.Observer;

public class ChatConnectionObserver implements Observer{

    private ChatViewModel viewModel;

    public ChatConnectionObserver(ChatViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        viewModel.connectionClosed();
    }
}
