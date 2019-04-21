package server;

import server.commands.CommandHash;
import server.commands.CommandList;
import server.commands.ICommand;
import sun.misc.Queue;
import threadDispatcher.Threaded;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;


public class WorkerWithClient extends Threaded
{
    private Socket myClientSocket;
    private String endOfMessage = "\r\n\r\n";
    private volatile Queue<String[]> tasks;

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
        tasks = new Queue<String[]>();
    }

    @Override
    public void doRun()
    {
        // поток демон
        Thread t = new Thread(new ListenerSocket(myClientSocket, tasks));
        t.setDaemon(true);
        t.start();

        sendHello();
        String result;
        String[] task = null;
        while (!myClientSocket.isClosed())
        {
            if (!tasks.isEmpty())
            {
                try {
                    task = tasks.dequeue();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (task != null && commands.containsKey(task[0]))
                {
                    result = commands.get(task[0]).getResult(task);
                } else {
                    task = (task == null) ? new String[] {""} : task;
                    result = "Sorry, we haven't this command " + task[0];
                }
                sendResult(result);
            }
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
            return;
        }
    }

    private void sendHello()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("we have next command: \n");
        for (String com : commands.keySet())
        {
            stringBuilder.append("\t\t").append(com).append("\n");
            stringBuilder.append(commands.get(com).getExampleCall()).append("\n");
        }
        sendResult(stringBuilder.toString());
    }

}