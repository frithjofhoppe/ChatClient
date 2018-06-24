package client.chat;

public class UsernameValidator {
    public boolean isValid(String message, String username) {
        if(message == ("server:username_ok").toUpperCase()){
            return true;
        }else{
            return false;
        }
    }
}
