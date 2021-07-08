package Chat.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private MainServ serv;
    private Socket socket;
    DataInputStream in;
    DataOutputStream out;
    private String nick;

    public  ClientHandler(MainServ serv, Socket socket) {
        try {
            this.serv = serv;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            // Считываем входящий поток от клиента
                            String str = in.readUTF();
                            // Если строка переданная клиентом начинается
                            // c /auth выполняем следующее -->
                            if (str.startsWith("/auth")) {
                                // Создаем массив, содержащий логин и пароль,
                                // переданные клиентом
                                String[] tokens = str.split(" ");
                                // Вызываем метод getNickByLoginAndPass передавая в качестве
                                // аргуметов логин и пароль клиента
                                String currentNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                // Если верувшаяся из базы данных строка не null делаем следующее -->
                                if (currentNick != null) {
                                    if (!serv.isNickBusy(currentNick)) {
                                        // Вызываем метод sendMsg c аргуметом /authok
                                        sendMsg("/authok " + currentNick);
                                        // Присваиваем переменной nick значение строки currentNick
                                        nick = currentNick;
                                        // Делаем рассылку что такой-то клиент зашел в чат
                                        serv.broadcastMsg(nick + " зашел в чат");
                                        // Вызываем метод subscribe передавая в качестве
                                        // аргумента текущего клиента
                                        serv.subscribe(ClientHandler.this);
                                        break;
                                    } else {
                                        // Отправляем сообщение что учетная запись уже используется
                                        sendMsg("Учетная запись уже используется");
                                    }
                                } else {
                                        // Если верувшаяся из базы данных строка null делаем следующее -->
                                        // Вызываем метод sendMsg c аргуметом "Неверный логин/пароль"
                                        sendMsg("Неверный логин/пароль");
                                    }
                                }
                            }


                        while (true) {
                            // Читаем входящий поток от клиента
                            String str = in.readUTF();
                            // Выводим в консоль сообщение клиента
                            System.out.println("Client " + str);
                            // Проверяем, если сообщение клиента заканчивается /end
                            // оправляем ему сообщение /serverclosed
                            if (str.equals("/end")) {
                                out.writeUTF("/serverclosed");
                                break;
                            }
                            // Иначе рассылаем сообщение данного клиента
                            // всем клиентам на сервере либо одному клиенту
                            // если это личное сообщение
                            if (str.startsWith("/w")) {
                                // реализация личных сообщений
                                String to = str.split(" ")[1];
                                String msg = str.split(" ")[2];
                                serv.personalMsg(ClientHandler.this, to, msg);
                            } else
                                serv.broadcastMsg("[" + getNick() + "] " + str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            // Закрываем исходящий поток
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            // Закрываем входящий поток
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            // Закрываем сокет
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        // Удаляем клиента из списка клиентов
                        serv.unsubscribe(ClientHandler.this);
                    }

                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            // Отправляем клиенту сообщение
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }
}




