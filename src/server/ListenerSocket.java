package server;


import java.util.ArrayDeque;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;

public class ListenerSocket implements Runnable
{
    private String endOfMessage = "\r\n\r\n";
    private Socket myClientSocket;
    private InputStream myClientInputStream;
    private volatile ArrayDeque<String[]> tasks;

    protected ListenerSocket(Socket socket, ArrayDeque<String[]> tasks)
    {
        myClientSocket = socket;
        try {
            myClientInputStream = socket.getInputStream();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.tasks = tasks;
    }

    @Override
    public void run()
    {
        String[] command = null;
        while (true)
        {
            command = readCommand();
            tasks.add(command);
        }
    }

    private String[] readCommand() {
        byte[] messageByte = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            DataInputStream in = new DataInputStream(myClientInputStream);
            while(check(stringBuilder.toString()))
            {
                int bytesRead = in.read(messageByte);
                if (bytesRead == -1)
                {
                    try {
                        myClientSocket.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    continue;
                }
                stringBuilder.append(new String(messageByte, 0, bytesRead));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.substring(0, stringBuilder.length() - 4).split(" ");
    }

    private boolean check(String s)
    {
        if (s.length() < 4)
            return true;
        return !s.substring(s.length() - 4).equals(endOfMessage);
    }
}