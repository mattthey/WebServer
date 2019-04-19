package main;
import client.Client;
import server.WebServer;

public class Main {
    public static void main(String[] args) {
        Thread server = new Thread(() -> {
            WebServer s = new WebServer(6666);
            s.createSocket();
            s.startServer();
        });


        Thread client1 = new Thread(() -> {
            Client client = new Client("127.0.0.1", 6666);
            client.createSocket("blablablablablablabla");
        });

        Thread client2 = new Thread(() -> {
            Client client = new Client("127.0.0.1", 6666);
            client.createSocket("list");
        });

        Thread client3 = new Thread(() -> {
            Client client = new Client("127.0.0.1", 6666);
            client.createSocket("hash----example.txt");
        });

        server.start();
        try { Thread.sleep(100); } catch (Exception e) {}
        client1.start();
        client2.start();
        client3.start();

    }
}