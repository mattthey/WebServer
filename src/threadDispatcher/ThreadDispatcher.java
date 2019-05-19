package threadDispatcher;


import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadDispatcher
{

    private static volatile ThreadDispatcher instance;
    public static LinkedList<Threaded> currentThread;

    protected static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 100,
            20,TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(300));

    private ThreadDispatcher()
    {
        currentThread = new LinkedList<>();
        Threaded t = new ThreadMonitor();
        Add(t);
    }

    public static ThreadDispatcher getInstance(){
        if(instance == null)
            synchronized (ThreadDispatcher.class) {
                if(instance == null)
                    instance = new ThreadDispatcher();
            }
        return instance;
    }

    public void Add(Threaded task) {
        threadPoolExecutor.submit(task);
    }

}