package client.chat;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Stream;

public class MessageSender extends Thread {
    private Deque<String> messageQueue = new ConcurrentLinkedDeque<>();
    private boolean isRunning = true;
    private DataOutputStream outputStream;
    private boolean hasMessage = false;
    private ConnectionObservable connectionObservable;

    public MessageSender(DataOutputStream outputStream, ConnectionObservable connectionObservable) {
        this.outputStream = outputStream;
        this.connectionObservable = connectionObservable;
    }

    public void queueMessage(String message) {
        System.out.println("Add message -> " + message);
        messageQueue.add(message);
        System.out.println(messageQueue.size() + " size");
        System.out.println(messageQueue.isEmpty() + " isEmpty");
        System.out.println(!messageQueue.isEmpty() + " Check condition");
        hasMessage = true;
    }

    public synchronized void letNotifying(String message) {
        System.out.println("Add message -> " + message);
        messageQueue.add(message);
        System.out.println(messageQueue.size() + " size");
        System.out.println(messageQueue.isEmpty() + " isEmpty");
        System.out.println(!messageQueue.isEmpty() + " Check condition");
        hasMessage = true;
        notify();
    }

    public synchronized void letWaiting() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Thread is running");
        while (isRunning) {
            if (hasMessage) {
                System.out.println("Message received");
                hasMessage = false;
            }
            if (!messageQueue.isEmpty()) {
                System.out.println("Quee is not empty");
                String message = messageQueue.pop();
                try {
                    outputStream.writeUTF(message);
                    outputStream.flush();
                } catch (IOException e) {
                    System.out.println("Sender disconnected");
                    connectionObservable.connected(false);
                    break;
                }
            }
            letWaiting();
        }
        System.out.println("Thread has stopped");
    }
}

