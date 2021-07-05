package homework_part2.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    // Создаем сокет для общения
    Socket clientSocket;
    // Создаем сервер-сокет для ожидания
    // подключения клиента к серверу
    ServerSocket serverSocket;
    // Создаем поток для чтения из сокета
    // (получения сообщений от клиента)
    DataInputStream in;
    // Создаем поток для записи в сокет
    // (отправки сообщений на клиенту)
    DataOutputStream out;

    String clientMSG;
    BufferedReader console;

    void start() {
        try {
            // Создаем объект сервер-сокет для прослушивания
            // подключения к порту 5544
            serverSocket = new ServerSocket(5541);
            System.out.println("Cервер запущен! Ожидаем подключения клиента...");
            // Ожидаем пока какой-нибудь клиент не захочет подключиться
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void catchClient() throws IOException {
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Клиент подключился!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Создаем потоки ввода и выводы для возможности
        // отправлять и принимать сообщения
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
        System.out.println("Ждем пока клиент что-нибудь отправит...");
    }

    void sendMessage(String MSG) throws IOException {
        if (MSG.equalsIgnoreCase("exit")) close();
        out.writeUTF("Привет, это Сервер! Вы написали: " + MSG + "\n");
        System.out.println(MSG);
    }

    void writeToConsole() throws IOException {
        while (true) {
            console = new BufferedReader(new InputStreamReader(System.in));
            String txt = console.readLine();
            sendMessage(txt);
        }
    }

    void close() throws IOException {
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}








