package homework_part2.server;

// Написать консольный вариант клиент серверного приложения, в котором пользователь
// может писать сообщения, как на клиентской стороне, так и на серверной.
// Т.е. если на клиентской стороне написать "Привет", нажать Enter то сообщение должно
// передаться на сервер и там отпечататься в консоли. Если сделать то же самое на серверной стороне,
// сообщение соответственно передается клиенту и печатается у него в консоли. Есть одна
// особенность, которую нужно учитывать: клиент или сервер может написать несколько сообщений подряд,
// такую ситуацию необходимо корректно обработать.

import homework_part2.server.Server;

import java.io.*;

public class StartServer {
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
        server.catchClient();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String txt = null;

                    try {
                       txt = server.in.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (txt != null) {
                        try {
                            server.sendMessage(txt);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server.writeToConsole();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
