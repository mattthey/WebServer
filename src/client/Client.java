package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Client {

    private String ipAddress;
    private int serverPort;
    private String endOfMessage = "\r\n\r\n";
    private Socket socket;
    private boolean sessionUser;

    public Client(String address, int port, boolean sessionUser) {
        ipAddress = address;
        serverPort = port;
        this.sessionUser = sessionUser;
    }

    public void createSocket() {
        try {
            socket = new Socket(ipAddress, serverPort);
            if (sessionUser)
            {
                Scanner sc = new Scanner(System.in);
                while (!socket.isClosed())
                {
                    System.out.println(readResponse());
                    System.out.print("Введите запрос: ");
                    String request = sc.nextLine();
                    if (request.equals("close()"))
                    {
                        socket.close();
                        break;
                    }
                    sendMessage(request);
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void closeConnection()
    {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(String message)
    {
        try
        {
            OutputStream out = socket.getOutputStream();
            out.write((message + endOfMessage).getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String readResponse()
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