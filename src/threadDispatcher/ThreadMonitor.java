package threadDispatcher;

import java.util.LinkedList;

public class ThreadMonitor extends Threaded
{
    public static LinkedList<Threaded> activeTasks = new LinkedList<>();

    private synchronized void updateActiveTasks()
    {
        activeTasks = (LinkedList<Threaded>) ThreadDispatcher.currentThread.clone();
    }

    @Override
    public void doRun()
    {
        while (true)
        {
            try { Thread.sleep(200); } catch (Exception e) { System.out.println("Error in ThreadMonitor"); }
            updateActiveTasks();
        }
    }
}