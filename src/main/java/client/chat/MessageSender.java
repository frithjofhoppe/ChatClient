package client.chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class MessageSender  extends Thread{
    private Deque<String> messageQueue = new ArrayDeque<>();
    private boolean isRunning = true;
    private DataOutputStream outputStream;

    public MessageSender(DataOutputStream outputStream){
        this.outputStream = outputStream;
    }

    public void queueMessage(String message){
        messageQueue.add(message);
    }

    @Override
    public void run() {
        while(isRunning){
            if(!messageQueue.isEmpty()){
                String message = messageQueue.pop();
                try {
                    outputStream.writeUTF(message);
                    outputStream.flush();
                }catch (IOException e){

                }
            }
        }
    }
}
