package client.chat;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;

public class NetInThread extends Thread{

    DataInputStream in;
    UsernameService usernameService;

    public NetInThread(DataInputStream in, UsernameService usernameService){
        this.in = in;
        this.usernameService = usernameService;
    }

    @Override
    public void run() {
        String content = "";
        while(true){
            try {
                content = in.readUTF();
                if(!usernameService.isValid()){
                    if(content == ("server:username_ok").toUpperCase()){
                        usernameService.setValid(true);
                        System.out.println("INFO: Username is valid");
                    }else{
                        System.out.println("INFO: Username is invalid");
                    }
                }else{
                    System.out.println("INFO: Username is set");
                    System.out.println(content);
                }
                System.out.println(content);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}