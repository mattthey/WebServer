package server.commands;

import fileWorker.FileWorker;
import fileWorker.Md5Executor;

public class CommandHash implements ICommand
{
    @Override
    public String getResult(String[] param) {
        FileWorker fileWorker = new FileWorker("./src/fileWorker/" + param[0]);
        Md5Executor md = new Md5Executor();
        fileWorker.execute(md);
        return md.dirHash;
    }
}
