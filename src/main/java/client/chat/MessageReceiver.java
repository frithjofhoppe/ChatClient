package client.chat;

import sun.security.krb5.internal.TGSRep;

import java.io.DataInputStream;
import java.io.IOException;

public class MessageReceiver extends Thread{
    private MessageReceiverObservable messageReceiverObservable;
    private DataInputStream inputStream;
    private boolean isRunning = true;

    public MessageReceiver(DataInputStream inputStream, MessageReceiverObservable messageReceiverObservable){
        this.inputStream = inputStream;
        this.messageReceiverObservable = messageReceiverObservable;
    }

    @Override
    public void run() {
        String message;
        while (isRunning){
            try {
                message = inputStream.readUTF();
                messageReceiverObservable.receiveMessage(message);
            }catch (IOException e){
                break;
            }
        }
    }
}
