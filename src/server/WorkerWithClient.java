package server;

import threadDispatcher.Threaded;


import java.io.*;
import java.net.Socket;

public class WorkerWithClient extends Threaded
{
    private Socket myClientSocket;

    public WorkerWithClient(Socket s)
    {
        myClientSocket = s;
    }

    @Override
    public void doRun()
    {
        try
        {
            DataInputStream in = new DataInputStream(myClientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}