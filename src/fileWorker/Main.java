package fileWorker;

public class Main {

    public static void main(String[] args)  {
        FileWorker fileWorker = new FileWorker(".");
        fileWorker.setIsRecursive(true);
        Md5Executor ex = new Md5Executor();
        fileWorker.execute(ex);

        System.out.println(ex.dirHash);
    }
}