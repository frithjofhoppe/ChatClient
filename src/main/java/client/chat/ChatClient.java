package client.chat;

public class ChatClient {
    private UsernameService usernameService;
    private ConnectionObservable connectionObservable;
    private String host;
    private int port;
    private boolean hasUserName;
    private NetConnection connection;

    public ChatClient(String host, int port, ConnectionObservable connectionObservable){
        this.host = host;
        this.port = port;
        this.connectionObservable = connectionObservable;
        usernameService = new UsernameService();
    }

    public void startConnection(){
        System.out.println("startConnection");
        connection = new NetConnection(port,host, usernameService, connectionObservable);
        connection.start();
    }
}
