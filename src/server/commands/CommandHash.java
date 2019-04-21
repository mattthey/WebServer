package server.commands;

import fileWorker.Md5Executor;

import java.io.File;
import java.io.FileNotFoundException;

public class CommandHash implements ICommand
{
    @Override
    public String getResult(String[] param)
    {
        Md5Executor md = new Md5Executor();
        return md.process(new File(param[1]));
    }

    @Override
    public String getExampleCall()
    {
        return "This method considers the file hash \nCommand: hash<space><path to file>\nExample: hash ./tests/example.txt";
    }
}
