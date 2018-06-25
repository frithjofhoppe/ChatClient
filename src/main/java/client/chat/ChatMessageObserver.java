package client.chat;

import client.viewModel.ChatViewModel;

import java.util.Observable;
import java.util.Observer;

public class ChatMessageObserver implements Observer{

    private ChatViewModel model;

    public ChatMessageObserver(ChatViewModel chatViewModel){
        this.model = chatViewModel;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("INFORMED");
        model.receiveMessage((String)arg);
    }
}
