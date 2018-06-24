package client.chat;

import java.util.Observable;
import java.util.Observer;

public class UsernameObservable extends Observable {

    private boolean state;

    public synchronized void setState(boolean state) {
        setChanged();
        this.state = state;
        notifyObservers(state);
    }

    public synchronized boolean getState(){
        return state;
    }
}
