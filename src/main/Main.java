package main;

import client.Client;
import fileWorker.FileWorker;
import serializator.testClass.MyClass1;
import server.WebServer;
import serializator.Serializator;

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Serializator serializator = new Serializator();
        MyClass1 testClass = new MyClass1();

        byte[] ser = serializator.serialaize(testClass);
        FileWorker.write(ser);
        byte[] get = FileWorker.getBytesFromFile();
        MyClass1 deserializeMyClass1 = serializator.deserialize(get);

        System.out.println("doubleField         " + deserializeMyClass1.getDoubleField());
        System.out.println("strField            " + deserializeMyClass1.strField);
        System.out.println("subMyClass.intField " + deserializeMyClass1.subMyClass.intField);
    }

    private static void test1()
    {
        Thread server = new Thread(() -> {
            WebServer s = new WebServer(8080);
            s.createSocket();
            s.startServer();
        });
        server.start();

        try { Thread.sleep(100); } catch (InterruptedException e) {}

        Thread client1 = new Thread(() -> {
            Client client = new Client("127.0.0.1", 6666, false);
            client.createSocket();
            String result = "client1: " + client.readResponse();
            System.out.println(result);
            client.sendMessage("blabla");
            result = "client1: " + client.readResponse();
            System.out.println(result);
            client.closeConnection();
        });

        Thread client2 = new Thread(() -> {
            Client client = new Client("127.0.0.1", 6666, false);
            client.createSocket();
            String result = "client2: " + client.readResponse();
            System.out.println(result);
            client.sendMessage("list");
            result = "client2: " + client.readResponse();
            System.out.println(result);
            client.closeConnection();
        });

        Thread client3 = new Thread(() -> {
            Client client = new Client("127.0.0.1", 6666, false);
            client.createSocket();
            try { Thread.sleep(5000); } catch (InterruptedException e) {}
            String result = "client3: " + client.readResponse();
            System.out.println(result);
            try { Thread.sleep(5000); } catch (InterruptedException e) {}
            client.sendMessage("hash ./tests/example.txt");
            result = "client3: " + client.readResponse();
            System.out.println(result);
            client.closeConnection();
        });

        try { Thread.sleep(100); } catch (Exception e) {}
        client1.setName("client-1");
        client2.setName("client-2");
        client3.setName("client-3");

        client1.start();
        client2.start();
        client3.start();
    }
}