package server.commands;

public interface ICommand
{
    public String getResult(String[] param);

    public String getExampleCall();
}
