package server.commands;

import fileWorker.FileWorker;

public class CommandList implements ICommand
{
    @Override
    public String getResult(String[] param)
    {
        FileWorker fileWorker = new FileWorker("./src/fileWorker");
        return fileWorker.getFiles();
    }
}
