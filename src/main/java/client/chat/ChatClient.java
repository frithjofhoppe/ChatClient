package client.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatClient extends Thread {
    private ConnectionObservable connectionObservable;
    private MessageSenderObservable messageSenderObservable;
    private MessageSenderObserver messageSenderObserver;
    private MessageReceiverObservable messageReceiverObservable;
    private UsernameObservable usernameObservable;
    private UsernameService usernameService;
    private String host;
    private int port;
    private Socket socket;
    private MessageReceiver messageReceiver;
    private MessageSender messageSender;

    public ChatClient(String host, int port){
        this.host = host;
        this.port = port;
        this.connectionObservable = new ConnectionObservable();
        this.usernameObservable = new UsernameObservable();
        this.usernameService = new UsernameService();
        this.messageSenderObservable = new MessageSenderObservable();
        this.messageReceiverObservable = new MessageReceiverObservable(getUsernameObservable(), getUsernameService());
    }

    public void stopServer(){
        try {
            this.socket.close();
            connectionObservable.connected(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConnectionObservable getConnectionObservable() {
        return connectionObservable;
    }

    public MessageSenderObservable getMessageSenderObservable() {
        return messageSenderObservable;
    }

    public MessageReceiverObservable getMessageReceiverObservable() {
        return messageReceiverObservable;
    }

    public UsernameObservable getUsernameObservable() {
        return usernameObservable;
    }

    public UsernameService getUsernameService() {
        return usernameService;
    }

    @Override
    public void run() {
        try{
            socket = new Socket(host, port);
            connectionObservable.connected(true);
            messageReceiver = new MessageReceiver(new DataInputStream(socket.getInputStream()), messageReceiverObservable);
            messageSender = new MessageSender(new DataOutputStream(socket.getOutputStream()));
            messageSenderObserver = new MessageSenderObserver(messageSender);
            messageSenderObservable.addObserver(messageSenderObserver);
            messageSender.start();
            messageReceiver.start();
        }catch (IOException e){
            connectionObservable.connected(false);
            System.out.println("IOException");
        }
    }
}
