package client.chat;

import java.util.Observable;

public class MessageSenderObservable extends Observable {
    public synchronized void sendMessage(String message){
        setChanged();
        System.out.println("Notify MessageSenderObserver");
        notifyObservers(message);
    }
}
