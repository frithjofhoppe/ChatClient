package client.chat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetOutThread extends Thread {

    DataOutputStream out;
    BufferedReader reader;
    UsernameService usernameService;

    public NetOutThread(DataOutputStream out, UsernameService usernameService){
        this.out = out;
        this.usernameService = usernameService;
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run() {
        String content;
        while (true) {
            try {
                content = reader.readLine();
                if(!usernameService.isValid()){
                    usernameService.setUsername(content);
                }
                out.writeUTF(content);
                out.flush();
            } catch (IOException e) {
                System.out.println("Server IOEXCEPTION");
                break;
            }
        }
    }
}
