package threadDispatcher;

public abstract class Threaded implements Runnable
{

    private long timeStart;
    private long id;

    public abstract void doRun();

    @Override
    public void run()
    {
        try
        {
            ThreadDispatcher.currentThread.add(this);
            timeStart = System.currentTimeMillis();
            id = Thread.currentThread().getId();
            doRun();
            ThreadDispatcher.currentThread.remove(this);
            ThreadDispatcher.threadPoolExecutor.remove(this);
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Error in Threaded");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        long tWork = System.currentTimeMillis() - timeStart;
        return "id: " + this.id + "\t timeWork:" + tWork + "\t " + this.getClass();
    }
}