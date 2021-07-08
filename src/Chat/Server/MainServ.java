package Chat.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class MainServ {
    private Vector<ClientHandler> clients;

    public MainServ() {
        // Создаем специальный массив для хранения списка клиентов
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            // Создаем подлючение сервера к базе данных
            AuthService.connect();

            // Создаем сервер
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");
            while (true) {
                // Ожидаем подключения клиента
                socket = server.accept();
                System.out.println("Клиент подключился!");
                // Добавляем текущего клиента в список клиентов
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Закрываем сокет
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                // Закрываем сервер
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Отключаем соединение с базой данных
            AuthService.disconnect();
        }
    }

    public void subscribe(ClientHandler client) {
        // Добавляем нового клиента в список
        clients.add(client);
    }

    public void unsubscribe(ClientHandler client) {
        // Удаляем клиента из списка
        clients.remove(client);
    }

    // Метод для проверки не занят ли ник
    public boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    // Метод для рассылки сообщений всем клиентам
    public void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    public void personalMsg(ClientHandler clientHandler, String to, String msg) {
        for (ClientHandler client: clients) {
            if(client.getNick().equals(to)) {
                client.sendMsg("[W from: " + clientHandler.getNick() + "] " + msg);
                break;
            }
        }
        clientHandler.sendMsg("[W to: " + to + "] " + msg);
    }
    }
