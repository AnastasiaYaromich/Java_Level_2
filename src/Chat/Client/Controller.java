package Chat.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller {

    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    HBox bottomPanel;

    @FXML
    HBox upperPanel;

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button authButton;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final  int PORT = 8189;

    private boolean isAuthorized;


    // Метод определющий видимость окон в зависимости
    // от состояния авторизации.
    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if (!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
        }
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // На текущий момент мы не авторизованы.
            // Вызываем метод setAuthorized для установки видимости панелей.
            setAuthorized(false);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            // Читаем входящий поток с сервера
                            String str = in.readUTF();
                            // Если полученное с сервера сообщение начинается с authok
                            // делаем следующее -->
                            if (str.startsWith("/authok")) {
                                // На текущий момент мы авторизованы.
                                // Вызываем метод setAuthorized для установки видимости панелей.
                               setAuthorized(true);
                                break;
                            } else {
                                // Если полученное с сервера сообщение не начинается с authok
                                // добавляем в textArea сообщение неверный логин и пароль
                                textArea.appendText(str + "\n");
                            }
                        }
                        while (true) {
                            // Читаем входящий поток с сервера
                            // если сообщение сервера содержит строку
                            // serverclosed завершаем работу
                            String str = in.readUTF();
                            if (str.equals("/serverclosed")) {
                           //     out.writeUTF("/end");
                                break;
                            }
                            // Добавляем в textArea сообщение /serverclosed
                            textArea.appendText(str + "\n");
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
                        // На текущий момент мы не авторизованы.
                        setAuthorized(false);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            // Клиент написал сообщение в textField
            // и нажал конпку Send.
            // отправляем сообщение на сервер
//            String clMsg = textField.getText();
//            if (clMsg == )
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // При нажатии кнопки tryToAuth вызывается данный метод
    public void tryToAuth() {
        // Если соединения с сервером нет - создаем сокет, вызвав метод connect()
        if (socket == null || socket.isClosed()) {
            connect();
        }
        try {
            // Отправляем серверу сообщение для с логином и паролем клиента
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



