package client.chat;

import java.util.Observable;

public class MessageReceiverObservable extends Observable {

    private UsernameObservable usernameObservable;
    private UsernameValidator validator;
    private UsernameService usernameService;

    public MessageReceiverObservable(UsernameObservable usernameObservable, UsernameService usernameService) {
        this.usernameObservable = usernameObservable;
        this.validator = new UsernameValidator();
        this.usernameService = usernameService;
    }

    public synchronized void receiveMessage(String message) {
        System.out.println(message + ": message");
        System.out.println(usernameService.getUsername() + ": username");
        if (!usernameObservable.getState()) {
            if (validator.isValid(message, usernameService.getUsername())) {
                usernameObservable.setState(true);
            } else {
                usernameObservable.setState(false);
            }
        }else{
            //Chat message observabkle
        }
    }
}
