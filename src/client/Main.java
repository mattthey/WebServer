package client;

public class Main {
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 6666);
        client.createSocket();
    }
}
