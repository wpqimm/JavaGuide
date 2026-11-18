import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class StatisticsPanel extends JPanel {
    private static final Logger log = LoggerFactory.getLogger(StatisticsPanel.class);
    private JPanel contentPanel;
    private DataBaseManager db;
    private List<String> completed;
    private String currentUser;


    public StatisticsPanel(DataBaseManager db, String currentUser) {
        setLayout(new BorderLayout());
        setBackground(new Color(25, 20, 35));
        this.db = db;
        this.currentUser = currentUser;

        JLabel title = new JLabel("Профиль");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(210, 200, 230));
        title.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(title, BorderLayout.NORTH);


        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        add(contentPanel, BorderLayout.CENTER);


        showProfileMain();
        contentPanel.repaint();
        contentPanel.revalidate();
    }

    // ===== ГЛАВНЫЙ ЭКРАН ПРОФИЛЯ =====
    private void showProfileMain() {
        contentPanel.removeAll();

        contentPanel.add(createSection("Мой прогресс"));
        contentPanel.add(Box.createVerticalStrut(20));
        contentPanel.add(createSection("Общая статистика"));


        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ===== ФАБРИКА СЕКЦИЙ ПРОФИЛЯ =====
    private JPanel createSection(String sectionTitle) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(sectionTitle);
        titleLabel.setForeground(new Color(210, 200, 230));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (sectionTitle.equals("Мой прогресс")) {
                    showProgressCategories();
                }
                if (sectionTitle.equals("Общая статистика")) {
                    showGeneralStatistics();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                titleLabel.setForeground(new Color(150, 130, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                titleLabel.setForeground(new Color(210, 200, 230));
            }
        });

        section.add(titleLabel);
        return section;
    }



    private JPanel createExpandableSection(String title, JPanel children) {
        JPanel section = new JPanel();
        section.setLayout(new BoxLayout(section, BoxLayout.Y_AXIS));
        section.setOpaque(false);
        section.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(210, 200, 230));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 5));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JLabel arrow = createArrowLabel("▶");

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
        headerPanel.setOpaque(false);
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(titleLabel);
        headerPanel.add(arrow);


        children.setVisible(false);
        children.setOpaque(false);
        children.setAlignmentX(Component.LEFT_ALIGNMENT);



        titleLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                children.setVisible(!children.isVisible());
                arrow.setText(children.isVisible() ? "▼" : "▶");
                section.revalidate();
                section.repaint();

                if (children.isVisible()) {
                    arrow.setText("▼");
                } else {
                    arrow.setText("▶");
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                titleLabel.setForeground(new Color(150, 130, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                titleLabel.setForeground(new Color(210, 200, 230));
            }
        });



        section.add(headerPanel);
        section.add(children);

        return section;
    }


    private JLabel createArrowLabel(String text) {
        JLabel arrow = new JLabel(text);
        arrow.setFont(new Font("Arial", Font.PLAIN, 14));
        arrow.setForeground(new Color(210, 200, 230));
        arrow.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return arrow;
    }




    private void showProgressCategories() {
        contentPanel.removeAll();

        completed = db.getCompletedTopics(currentUser);

        // Кнопка "Назад" (пока просто выводит в консоль, потом заменишь на showProfileMain)
        JLabel backLabel = new JLabel("← Назад");
        backLabel.setFont(new Font("Arial", Font.BOLD, 14));
        backLabel.setForeground(new Color(210, 200, 230));
        backLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        backLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showProfileMain();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                backLabel.setForeground(new Color(150, 130, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                backLabel.setForeground(new Color(210, 200, 230));
            }
        });
        contentPanel.add(backLabel);
        contentPanel.add(Box.createVerticalStrut(10));

        // ---- Строим ветку "Теория" ----
        // 1. Темы для "Основы Java"
        JPanel javaTopics = new JPanel();
        javaTopics.setLayout(new BoxLayout(javaTopics, BoxLayout.Y_AXIS));
        javaTopics.setOpaque(false);
        javaTopics.setFont(new Font("Arial", Font.BOLD, 14));

        javaTopics.add(createTopicPanel("Переменные и типы"));
        javaTopics.add(Box.createVerticalStrut(10));
        javaTopics.add(createTopicPanel("Операторы"));
        javaTopics.add(Box.createVerticalStrut(10));
        javaTopics.add(createTopicPanel("Условия"));

        // 2. Оборачиваем в подкатегорию "Основы Java"
        JPanel javaSection = createExpandableSection("Основы Java", javaTopics);


        // 3. Темы для "ООП"
        JPanel oopTopics = new JPanel();
        oopTopics.setLayout(new BoxLayout(oopTopics, BoxLayout.Y_AXIS));
        oopTopics.setOpaque(false);
        oopTopics.setFont(new Font("Arial", Font.BOLD, 14));


        oopTopics.add(createTopicPanel("Классы"));
        oopTopics.add(Box.createVerticalStrut(10));
        oopTopics.add(createTopicPanel("Наследования"));
        oopTopics.add(Box.createVerticalStrut(10));
        oopTopics.add(createTopicPanel("Полиморфизм"));

        JPanel oopSection = createExpandableSection("ООП", oopTopics);

        // 4. Собираем подкатегории в панель
        JPanel theoryChildren = new JPanel();
        theoryChildren.setLayout(new BoxLayout(theoryChildren, BoxLayout.Y_AXIS));
        theoryChildren.setOpaque(false);
        theoryChildren.add(javaSection);
        theoryChildren.add(oopSection);

        // 5. Оборачиваем в категорию "Теория"
        JPanel theorySection = createExpandableSection("Теория", theoryChildren);

        // ---- Аналогично можно сделать для "Теоретические задания" и "Практические задания" ----
        // Пока добавим только "Теория" для теста
        contentPanel.add(theorySection);

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showGeneralStatistics() {

        contentPanel.removeAll();


        List<String> stats = db.getUserStats();


        for (String stat : stats) {
            JLabel label = new JLabel(stat);
            label.setForeground(new Color(210, 200, 230));
            label.setFont(new Font("Arial", Font.BOLD, 15));
            label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
            contentPanel.add(label);
        }

        JLabel backLabel = new JLabel("← Назад");
        backLabel.setFont(new Font("Arial", Font.BOLD, 14));
        backLabel.setForeground(new Color(210, 200, 230));
        backLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        backLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        backLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showProfileMain();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                backLabel.setForeground(new Color(150, 130, 200));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                backLabel.setForeground(new Color(210, 200, 230));
            }
        });
        contentPanel.add(backLabel);
        contentPanel.add(Box.createVerticalStrut(30));


        contentPanel.revalidate();
        contentPanel.repaint();

    }

    private JPanel createTopicPanel(String text) {

        JPanel topicPanel = new JPanel();
        topicPanel.setLayout(new BoxLayout(topicPanel, BoxLayout.X_AXIS));
        topicPanel.setOpaque(false);
        topicPanel.setForeground(new Color(210, 200, 230));
        topicPanel.setFont(new Font("Arial", Font.PLAIN, 15));
        topicPanel.setBorder(BorderFactory.createEmptyBorder(5, 30, 5, 20));
        topicPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel(text); // название темы
        nameLabel.setForeground(new Color(150, 130, 200));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));


        JLabel statusLabel = new JLabel(); // стасус
        statusLabel.setForeground(new Color(100, 90, 130));
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));

        if (completed.contains(text)) {
            statusLabel.setText(" — Завершено");
        } else {
            statusLabel.setText(" — Не завершено");
        }

        topicPanel.add(nameLabel);
        topicPanel.add(statusLabel);


        return topicPanel;
    }


}


