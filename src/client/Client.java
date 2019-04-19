package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private String ipAddress;
    private int serverPort;
    private String endOfMessage = "\r\n\r\n";

    public Client(String address, int port) {
        ipAddress = address;
        serverPort = port;
    }
    public void createSocket(String message) {

        Socket socket;
        try {
            socket = new Socket(ipAddress, serverPort);
            OutputStream out = socket.getOutputStream();

            out.write((message + endOfMessage).getBytes());
            System.out.println(readResponse(socket));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readResponse(Socket socket)
    {
        byte[] messageByte = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            InputStream inputStream = socket.getInputStream();
            DataInputStream in = new DataInputStream(inputStream);

            while(check(stringBuilder.toString()))
            {
                int bytesRead = in.read(messageByte);
                if (bytesRead == -1)
                    continue;
                stringBuilder.append(new String(messageByte, 0, bytesRead));
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.substring(0, stringBuilder.length() - 4);
    }

    private boolean check(String s)
    {
        if (s.length() < 4)
            return true;
        return !s.substring(s.length() - 4).equals(endOfMessage);
    }
}