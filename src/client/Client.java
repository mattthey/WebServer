package client;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private String ipAddress;
    private int serverPort;
    private String endOfMessage = "\r\n\r\n";
    Socket socket;

    public Client(String address, int port) {
        ipAddress = address;
        serverPort = port;
    }
    public void createSocket() {
        try {
            socket = new Socket(ipAddress, serverPort);
            while (true)
            {
                System.out.println(readResponse());
                sendMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendMessage()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Введите запрос: ");
        String request = sc.nextLine();
        try
        {
            OutputStream out = socket.getOutputStream();
            out.write((request + endOfMessage).getBytes());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    private String readResponse()
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