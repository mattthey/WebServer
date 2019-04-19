package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private String ipAddress;
    private int serverPort;

    public Client(String address, int port) {
        ipAddress = address;
        serverPort = port;
    }
    public void createSocket() {

        Socket socket;
        try {
            socket = new Socket(ipAddress, serverPort);
            OutputStream out = socket.getOutputStream();
            out.write("hello".getBytes());
//            InputStream in = socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}