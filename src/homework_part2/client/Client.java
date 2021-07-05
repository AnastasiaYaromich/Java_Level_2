package homework_part2.client;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.*;
import java.net.*;

public class Client {
    // Создаем сокет для общения
    Socket clientSocket;

    // Создаем ридер читающий с консоли
    BufferedReader console;

    // Создаем поток для чтения из сокета
    // (получения сообщений с сервера)
    DataInputStream in;

    // Создаем поток для записи в сокет
    // (отправки сообщений на сервер)
    DataOutputStream out;

    String clientMsg, serverMsg;

    public Client() throws IOException {
        // Создаем сокет, указывая адрес нахождения сервера и порт,
        // запрашивая у сервера доступ на подключение
        clientSocket = new Socket("localhost", 5541);

        // Создаем потоки ввода и выводы для возможности
        // отправлять и принимать сообщения
        in = new DataInputStream(clientSocket.getInputStream());
        out = new DataOutputStream(clientSocket.getOutputStream());
        console = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Вы хотите что-то отправить на сервер? Введите это здесь:");
        }

        void sendMessage() throws IOException {
        while (true) {
            if((clientMsg = console.readLine()) != null) {
                out.writeUTF(clientMsg + "\n");
                if (clientMsg.equalsIgnoreCase("close") || clientMsg.equalsIgnoreCase("exit")) break;
            }
        }
        }

        void readMsg() throws IOException {
        while (true) {
            if ((serverMsg = in.readUTF()) != null) {
                System.out.println(serverMsg);
            }
        }
        }

        void close() throws IOException {
        out.close();
        in.close();
        console.close();
        clientSocket.close();
        }
    }





