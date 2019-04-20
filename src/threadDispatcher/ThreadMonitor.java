package threadDispatcher;

import java.io.FileWriter;
import java.io.IOException;
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
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n\n");
            for (Threaded t : activeTasks)
                stringBuilder.append(t.toString()).append("\n");
            try(FileWriter out = new FileWriter("logThreaded.txt", true))
            {
                out.write(stringBuilder.toString());
            } catch (IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }
}