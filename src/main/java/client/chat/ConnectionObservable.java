package client.chat;

import java.util.Observable;

public class ConnectionObservable extends Observable {

    public synchronized void connected(boolean state){
        setChanged();
        notifyObservers(state);
    }
}
