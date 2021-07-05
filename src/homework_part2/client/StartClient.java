package homework_part2.client;

import homework_part2.client.Client;

import java.io.IOException;

public class StartClient {
    public static void main(String[] args) throws IOException {
        System.out.println("Клиент подключился к локальному серверу: 5541.");
        Client client = new Client();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                     client.readMsg();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                        }
                    }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    client.sendMessage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
