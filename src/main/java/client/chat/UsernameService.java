package client.chat;

public class UsernameService {
    private String username;

    public synchronized void setUsername(String username){
        this.username = username;
    }

    public synchronized String getUsername(){
        return this.username;
    }
}
