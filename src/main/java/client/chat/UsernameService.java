package client.chat;

import java.util.Observable;

public class UsernameService {
    private String username;
    private boolean isValid;

    public UsernameService(){
        username = "";
        isValid = false;
    }

    public synchronized void setUsername(String username){
        this.username = username;
    }

    public synchronized String getUsername(){
        return username;
    }

    public synchronized boolean isValid(){
        return isValid;
    }

    public synchronized void setValid(boolean status){
        isValid = status;
    }
}
