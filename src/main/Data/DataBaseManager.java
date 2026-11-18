import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;

public class DataBaseManager {
    public void connect() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:learning.db");
            Statement stmt = conn.createStatement();

            System.out.println("База данных готова");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    public void createProgressTable() {
        try {
            // 1. Открываем соединение с базой данных
            Connection conn = DriverManager.getConnection("jdbc:sqlite:learning.db");

            // 2. Создаём Statement — объект, через который отправляем SQL-команды
            Statement stmt = conn.createStatement();

            // 3. Пишем SQL-запрос на создание таблицы
            String sql = "CREATE TABLE IF NOT EXISTS progress (" +
                    "nickname TEXT, " +
                    "topic TEXT, " +
                    "completed INTEGER, " +
                    "UNIQUE (nickname, topic))";

            // 4. Выполняем запрос
            stmt.execute(sql);

            // 5. Сообщаем об успехе
            System.out.println("Таблица progress готова");

        } catch (Exception e) {
            // Если что-то пошло не так — показываем ошибку
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    public void saveProgress(String nickname, String topic, int completed) {
        if (nickname == null || nickname.trim().isEmpty() || topic == null || topic.trim().isEmpty()) {
            System.out.println("Попытка сохранить пустые данные. Отменено.");
            return;
        }

        String sql = "INSERT OR IGNORE INTO progress (nickname, topic, completed) " +
                "VALUES (?, ?, ?) ";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:learning.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nickname);
            pstmt.setString(2, topic);
            pstmt.setInt(3, completed);

           // String sql = "INSERT OR IGNORE INTO progress (nickname, topic, completed) " +
                  //  "VALUES ('" + nickname + "', '" + topic + "', " + completed + ")";

            pstmt.execute();
            System.out.println("Прогресс сохранен: " + topic);

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public List<String> getCompletedTopics(String nickname) {
        List<String> completedTopics = new ArrayList<>();

        // Внимание: скобки КРУГЛЫЕ после try!
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:learning.db");
             Statement stmt = conn.createStatement()) {

            // Используем переданный nickname вместо захардкоженного 'wpqimm'
            String sql = "SELECT topic FROM progress WHERE nickname = '" + nickname + "' AND completed = 1";

            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String topic = rs.getString("topic");
                completedTopics.add(topic);
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        return completedTopics;
    }

    public void createUsersTable() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:learning.db");
            Statement stmt = conn.createStatement();

            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nickname TEXT UNIQUE, " +
                    "password TEXT)";

            stmt.execute(sql);
            System.out.println("Таблица юзеров готова");

        }   catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public boolean registerUser(String nickname, String password) {

        String sql = "INSERT OR IGNORE INTO users (nickname, password) " +
                "VALUES (?, ?) ";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:learning.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nickname);
            pstmt.setString(2, password);

            // String sql = "INSERT OR IGNORE INTO users (nickname, password) " +
                  //  "VALUES ('" + nickname + "', '" + password + "')";

            int result = pstmt.executeUpdate();

            if (result > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return false;
    }

    public boolean loginUser(String nickname, String password) {

        String sql = "SELECT * FROM users WHERE nickname = ? AND password = ? ";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:learning.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nickname);
            pstmt.setString(2, password);

            // String sql = "SELECT * FROM users WHERE nickname = '" + nickname + "' AND password = '" + password + "'";

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return false;
    }

    public List<String> getUserProgressReport() {
        List<String> report = new ArrayList<>();

        String sql = "SELECT users.nickname, progress.topic FROM users " +
                "JOIN progress ON users.nickname = progress.nickname ";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:learning.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nickname = rs.getString("nickname");
                String topic = rs.getString("topic");

                report.add("Ник " + nickname + " | Тема: " + topic);
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return report;
    }

    public List<String> getUserStats() {
        List<String> stats = new ArrayList<>();

        String sql = "SELECT nickname, COUNT(*) as total FROM progress WHERE completed = 1 " +
                "GROUP BY nickname ";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:learning.db");
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String nickname = rs.getString("nickname");
                int total = rs.getInt("total");

                stats.add("Пользователь: " + nickname + " | Пройдено тем: " + total);
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return stats;
    }

}