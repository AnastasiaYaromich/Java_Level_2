package Chat.Server;

import java.sql.*;

public class AuthService {
    // Создаем ссылку на объект, который позволяет установить соединение
    // между сервером и базой данных
    private static Connection connection;

    // Создаем ссылку на объект, с помощью которого мы сможем
    // создавать запросы к базе данных и получать результат из базы
    private static Statement stmt;

    public static void connect() {
        try {
            // Обращаемся к драйверу jdbc для того чтобы
            // произошла его инициализация
            Class.forName("org.sqlite.JDBC");

            // Производим инициализацию соединения
            // между сервером и базой данных
            connection = DriverManager.getConnection("jdbc:sqlite:main.db");

            // Подключаемся к базе данных
            // для отравки запросов и получения ответов
            stmt = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        // Создаем sql команду в виде строки
        String sql = String.format("SELECT nickname FROM users WHERE login = '%s' AND password = '%s'", login, pass);
        try {
            // Запрашиваем с помощью объекта ResultSet, есть ли в базе
            // данных клиент с данным логином и паролем
            ResultSet rs = stmt.executeQuery(sql);
            // Если есть возвращаем первую строку
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // Если такого клиента нет, возвращаем null
        return null;
    }


// Отключаем соединение с баззой данных
    public static void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
