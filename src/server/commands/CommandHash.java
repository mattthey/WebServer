package server.commands;

import fileWorker.Md5Executor;

import java.io.File;

public class CommandHash implements ICommand
{
    @Override
    public String getResult(String[] param)
    {
        Md5Executor md = new Md5Executor();
        return md.process(new File(param[1]));
    }
}
