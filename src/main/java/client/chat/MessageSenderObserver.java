package client.chat;

import sun.plugin2.message.Message;

import java.io.DataOutputStream;
import java.io.IOException;
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
        messageSender.queueMessage((String)arg);
    }
}
