package client.chat;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

public class MessageSender  extends Thread{
    private Deque<String> messageQueue = new ConcurrentLinkedDeque<>();
    private boolean isRunning = true;
    private DataOutputStream outputStream;
    private boolean hasMessage = false;

    public MessageSender(DataOutputStream outputStream){
        this.outputStream = outputStream;
    }

    public void queueMessage(String message){
        System.out.println("Add message -> " + message);
        messageQueue.add(message);
        System.out.println(messageQueue.size() + " size");
        System.out.println(messageQueue.isEmpty() + " isEmpty");
        System.out.println(!messageQueue.isEmpty() + " Check condition");
        hasMessage = true;
    }

    @Override
    public void run() {
        System.out.println("Thread is running");
        while(isRunning){
            if(hasMessage){
                System.out.println("Message received");
                hasMessage = false;
            }
            if(!messageQueue.isEmpty()){
                System.out.println("Quee is not empty");
                String message = messageQueue.pop();
                try {
                    outputStream.writeUTF(message);
                    outputStream.flush();
                }catch (IOException e){
                    System.out.println("Send exception");
                }
            }
        }
        System.out.println("Thread has stopped");
    }
}

