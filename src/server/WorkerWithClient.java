package server;

import server.commands.CommandHash;
import server.commands.CommandList;
import server.commands.ICommand;
import threadDispatcher.Threaded;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class WorkerWithClient extends Threaded
{
    private Socket myClientSocket;
    private String endOfMessage = "\r\n\r\n";
    private HashMap<String, ICommand> commands = new HashMap<String, ICommand>()
    {
        {
            put("list", new CommandList());
            put("hash", new CommandHash());
        }
    };

    public WorkerWithClient(Socket s)
    {
        myClientSocket = s;
    }

    @Override
    public void doRun()
    {
        sendHello();
        while (true)
        {
            String[] command = readCommand();
            String result;
            if (commands.containsKey(command[0]))
            {
                result = commands.get(command[0]).getResult(command);
            } else {
                result = "Sorry, we havn't this command " + command[0];
            }
            sendResult(result);
        }
    }

    private void sendResult(String result)
    {
        result += endOfMessage;
        try
        {
            OutputStream outputStream = myClientSocket.getOutputStream();
            outputStream.write(result.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] readCommand()
    {
        byte[] messageByte = new byte[1024];
        StringBuilder stringBuilder = new StringBuilder();

        try
        {
            InputStream inputStream = myClientSocket.getInputStream();
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
        return stringBuilder.substring(0, stringBuilder.length() - 4).split("----");
    }

    private boolean check(String s)
    {
        if (s.length() < 4)
            return true;
        return !s.substring(s.length() - 4).equals(endOfMessage);
    }

    private void sendHello()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("we have next command: \n");
        for (String com : commands.keySet())
            stringBuilder.append(com).append("\n");
        sendResult(stringBuilder.toString());
    }

}