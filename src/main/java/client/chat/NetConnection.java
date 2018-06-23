package client.chat;

import javafx.beans.property.StringProperty;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetConnection extends Thread {
    Socket socket;
    int port;
    String host;
    UsernameService username;
    ConnectionObservable connectionObservable;

    public NetConnection(int port, String host, UsernameService username, ConnectionObservable connectionObservable) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.connectionObservable = connectionObservable;
    }

    @Override
    public void run() {
        NetInThread inputStream;
        NetOutThread outputStream;
        try {
            System.out.println("BEFORE");
            socket = new Socket(host, port);
            connectionObservable.connected(true);
            System.out.println("CONNECTED");
            inputStream = new NetInThread(new DataInputStream(socket.getInputStream()), username);
            outputStream = new NetOutThread(new DataOutputStream(socket.getOutputStream()), username);
            inputStream.start();
            outputStream.start();
        } catch (IOException e) {
            connectionObservable.connected(false);
            System.out.println("Client IOECEPTION");
        }
    }
}