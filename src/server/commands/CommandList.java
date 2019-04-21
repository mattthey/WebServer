package server.commands;

import fileWorker.FileWorker;

public class CommandList implements ICommand
{
    @Override
    public String getResult(String[] param)
    {
        FileWorker fileWorker = new FileWorker("./tests");
        fileWorker.setIsRecursive(true);
        return fileWorker.getFiles();
    }

    @Override
    public String getExampleCall()
    {
        return "This method displays a list of files where the fileWorker points\nCommand: list";
    }
}
