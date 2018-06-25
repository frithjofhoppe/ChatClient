package client.chat;

import java.util.Observable;
import java.util.Observer;

public class MessageSenderObserver implements Observer {

    private MessageSender messageSender;

    public MessageSenderObserver(MessageSender sender){
        this.messageSender = sender;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Message Observer");
        messageSender.letNotifying((String)arg);
    }
}
