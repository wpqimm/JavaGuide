import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Запускаем окно в потоке обработки событий — так правильно для Swing
        DataBaseManager db = new DataBaseManager();
        new DataBaseManager().connect();

         db.createProgressTable();
         db.saveProgress("wpqimm", "Переменные и типы", 1);
         db.createUsersTable();

        List<String> report = db.getUserProgressReport();
        for (String line : report) {
            System.out.println(line);
        }

        List<String> stats = db.getUserStats();
        for (String line : stats) {
            System.out.println(line);
        }

        SwingUtilities.invokeLater(() -> new LearnWindow(db));



    }
}