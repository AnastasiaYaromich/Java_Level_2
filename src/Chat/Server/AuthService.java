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

        public static void addBlackList(String usernick, String blackusernick) {
            try {
               String a = usernick;
                String b = blackusernick;
                System.out.println(a);
                System.out.println(b);
                ResultSet rs1 = stmt.executeQuery("SELECT id FROM users WHERE nickname = '" + a + "'");
                int userId = rs1.getInt(1);
                System.out.println(userId);

                ResultSet rs2 = stmt.executeQuery("SELECT id FROM users WHERE nickname = '" + b + "'");
                int blackuserId = rs2.getInt(1);
                System.out.println(blackuserId);

               String query1 = "INSERT INTO blacklist (user_id, blackuser_id) VALUES (?, ?);";
               PreparedStatement ps1 = connection.prepareStatement(query1);
                ps1.setInt(1, userId);
                ps1.setInt(2, blackuserId);
                ps1.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    public static void addUser(String login, String pass, String nick) {
        try {
            String query = "INSERT INTO users (login, password, nickname) VALUES (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setInt(2, pass.hashCode());
            ps.setString(3, nick);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkBlackList(String usernick, String blackusernick) {
        try {
            System.out.println();
            String c = usernick;
            String d = blackusernick;
            System.out.println(c);
            System.out.println(d);
            ResultSet rs3 = stmt.executeQuery("SELECT id FROM users WHERE nickname = '" + usernick + "'");
            ResultSet rs4 = stmt.executeQuery("SELECT id FROM users WHERE nickname = '" + blackusernick + "'");
            int user_id = rs3.getInt(1);
            int blackuser_id = rs4.getInt(1);
            ResultSet rs5 = stmt.executeQuery("SELECT blackuser_id FROM blacklist WHERE user_id = '" + user_id + "'");
            if (rs5.next()) {
                int blackuser1_id = rs5.getInt(1);
                if (blackuser_id == blackuser1_id) {
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
      return false;
    }


    public static String getNickByLoginAndPass(String login, String pass) {
        try {
            ResultSet rs = stmt.executeQuery("SELECT nickname, password FROM users WHERE login = '" + login + "'");
            int myHash = pass.hashCode();
            // 106438208
            if (rs.next()) {
                String nick = rs.getString(1);
                int dbHash = rs.getInt(2);
                if (myHash == dbHash) {
                    return nick;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
