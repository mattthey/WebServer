package server;

import threadDispatcher.ThreadDispatcher;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    private int port;
    private ServerSocket serverSocket;

    public WebServer(int serverPort) {
        port = serverPort;
    }

    public void createSocket () {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        ThreadDispatcher td = ThreadDispatcher.getInstance();
        Socket client;
        try {
            while (true) {
                client = serverSocket.accept();
                td.Add(new WorkerWithClient(client));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}