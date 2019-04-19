package server.commands;

import fileWorker.FileWorker;
import fileWorker.Md5Executor;

public class CommandHash implements ICommand
{
    @Override
    public String getResult(String[] param) {
        FileWorker fileWorker = new FileWorker("./src/tests");
        Md5Executor md = new Md5Executor();
        fileWorker.execute(md);
        return md.dirHash;
    }
}
