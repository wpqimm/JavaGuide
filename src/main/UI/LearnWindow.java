import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LearnWindow extends JFrame {
    private JPanel contentPanel;
    private JPanel topPanel;
    private JPanel mainPanel;
    private DataBaseManager db;
    private String topic;
    private String currentUser;


    public LearnWindow(DataBaseManager db) {
        setTitle("Мой путь в Java");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // центрируем окно на экране
        this.db = db;


        // ========= ОСНОВНЫЕ ПАНЕЛИ ===========

        mainPanel = new JPanel();
        mainPanel.setBackground(new Color(25, 20, 35));
        mainPanel.setLayout(new BorderLayout());

        contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // или другой layout на твой вкус

       // contentPanel.add(Box.createVerticalGlue());
       // contentPanel.add(createMainMenuItem("Практические задания"));
      //  contentPanel.add(createMainMenuItem("Теоретические задания"));
       // contentPanel.add(createMainMenuItem("Работа над ошибками"));
       // contentPanel.add(createMainMenuItem("Статистика"));
       // contentPanel.add(Box.createVerticalGlue());


        mainPanel.add(contentPanel, BorderLayout.CENTER);

        topPanel = new JPanel();
        topPanel.setBackground(new Color(35, 30, 45));
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 8));
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        topPanel.add(createTopMenuItem("Главное меню"));
        topPanel.add(createTopMenuItem("Теория"));
        topPanel.add(createTopMenuItem("Заметки"));
        topPanel.add(createTopMenuItem("Карта пути"));
        topPanel.add(createTopMenuItem("Настройки"));

        mainPanel.add(topPanel, BorderLayout.NORTH);
        add(mainPanel);
        showAuth();
        setVisible(true);



    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
    }


        // =========  ФАБРИКИ (создание надписей) =========


        // ======= верхняя панель (главное меню, заметки и тд) ========

    private  JLabel createTopMenuItem(String text) {
        JLabel settingLabel = new JLabel(text);
        settingLabel.setFont(new Font("Arial", Font.BOLD, 14));
        settingLabel.setForeground(new Color(210, 200, 230));
        settingLabel.setBorder(BorderFactory.createEmptyBorder(5, 10,5, 10));

        settingLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (text.equals("Теория")) {
                    showTheorySections();
                } else {
                    System.out.println("Нажато: " + text);
                }
                if (text.equals("Главное меню")) {
                    showMainMenu();
                } else {
                    System.out.println("Кнопка нажата: " + text);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                settingLabel.setForeground(new Color(150, 130, 200));
                settingLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                settingLabel.setForeground(new Color(210, 200, 230));
                settingLabel.setCursor(Cursor.getDefaultCursor());
            }
        });
            return settingLabel;

    }

        // ======== панель для главного меню (практика теория и тд)

    private JLabel createMainMenuItem(String text) {
        JLabel mainLabel = new JLabel(text);
        mainLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainLabel.setForeground(new Color(210, 200, 230));
        mainLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (text.equals("Теоретические задания")) {
                    showTheoryTasks();
                } else {
                    System.out.println("Кнопка нажата: " + text);
                }

                if (text.equals("Статистика")) {
                    showStatistics();
                } else {
                    System.out.println("Кнопка нажата: " + text);
                }

                if (text.equals("Практические задания")) {
                    showPracticeTasks();
                } else {
                    System.out.println("Кнопка нажата: " + text);
                }

            }
            @Override
            public void mouseEntered(MouseEvent e) {
                mainLabel.setForeground(new Color(150,130,200));
                mainLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                mainLabel.setForeground(new Color(210, 200, 230));
                mainLabel.setCursor(Cursor.getDefaultCursor());
            }

        });
        return mainLabel;
    }

         private void showStatistics() {
             contentPanel.removeAll();
             contentPanel.add(new StatisticsPanel(db, currentUser));
             contentPanel.revalidate();
             contentPanel.repaint();
        }

        // для разделов теории (Основы Java, ООП и тд)

        private JLabel createSectionItem(String text) {
            JLabel label = new JLabel(text);
            label.setForeground(new Color(210, 200, 230));
            label.setFont(new Font("Arial", Font.BOLD, 16));
            label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            label.setAlignmentX(Component.LEFT_ALIGNMENT);

            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    showSubSections(text);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setForeground(new Color(150,130,200));
                    label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setForeground(new Color(210, 200, 230));
                    label.setCursor(Cursor.getDefaultCursor());
                }

            });
            return label;
        }

        // для подразделов теории (Переменные, операторы и тд)
        private JLabel createSubSectionItem(String text) {
        JLabel label1 = new JLabel(text);
        label1.setFont(new Font("Arial", Font.BOLD, 16));
        label1.setForeground(new Color(210, 200,230));
        label1.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        label1.setAlignmentX(Component.LEFT_ALIGNMENT);

        label1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showTheoryText(text);
                topPanel.setVisible(true);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                label1.setForeground(new Color(150, 130, 200));
                label1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                label1.setForeground(new Color(210, 200, 230));
                label1.setCursor(Cursor.getDefaultCursor());
            }
        });
            return label1;
        }


    // ======== ИЗМЕНЕНИЯ CONTENTPANEL (ПОКАЗЫВАТЕЛИ) =======

    // главное меню

      public void showMainMenu() {
         contentPanel.removeAll();
         topPanel.setVisible(true);
         mainPanel.repaint();
         mainPanel.revalidate();


         contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(createMainMenuItem("Практические задания"));
        contentPanel.add(createMainMenuItem("Теоретические задания"));
        contentPanel.add(createMainMenuItem("Работа над ошибками"));
        contentPanel.add(createMainMenuItem("Статистика"));
        contentPanel.add(Box.createVerticalGlue());
        contentPanel.add(Box.createVerticalStrut(20));

          JLabel secretLabel = new JLabel("Секрет <3");
          secretLabel.setFont(new Font("Arial", Font.ITALIC, 14)); // Курсив, чтобы отличалась
          secretLabel.setForeground(new Color(100, 80, 150)); // Приглушенный фиолетовый
          secretLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // По центру
          secretLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


          secretLabel.addMouseListener(new MouseAdapter() {
              @Override
              public void mouseClicked(MouseEvent e) {
                  showMiniGame(); // Вызываем твой метод
              }

              // Эффект при наведении (чтобы было понятно, что это кнопка)
              @Override
              public void mouseEntered(MouseEvent e) {
                  secretLabel.setForeground(new Color(210, 200, 230)); // Загорается ярче
              }
              @Override
              public void mouseExited(MouseEvent e) {
                  secretLabel.setForeground(new Color(100, 80, 150)); // Тускнеет обратно
              }
          });


          contentPanel.add(secretLabel);

        contentPanel.revalidate();
        contentPanel.repaint();
        topPanel.setVisible(true);
    }

    private void showMiniGame() {
        contentPanel.removeAll();
        topPanel.setVisible(false);
        mainPanel.revalidate();
        mainPanel.repaint();

        contentPanel.add(new MiniGamePanel());

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // список разделов
    private void showTheorySections() {
        contentPanel.removeAll();


        contentPanel.add(createSectionItem("Основы Java"));
        contentPanel.add(createSectionItem("ООП"));
        contentPanel.add(createSectionItem("Исключения и ошибки"));
        contentPanel.add(createSectionItem("Работа с данными"));
        contentPanel.add(createSectionItem("Инструменты"));
        contentPanel.add(Box.createVerticalGlue());

        contentPanel.revalidate();
        contentPanel.repaint();

    }

    // список подразделов для выбранного раздела
    private void showSubSections(String sectionName) {
        contentPanel.removeAll();

        if (sectionName.equals("Основы Java")) {
            contentPanel.add(createSubSectionItem("Переменные и типы"));
            contentPanel.add(createSubSectionItem("Операторы"));
            contentPanel.add(createSubSectionItem("Условия"));
            contentPanel.add(createSubSectionItem("Циклы"));

        } else if (sectionName.equals("Работа с данными")) {

        }

        contentPanel.repaint();
        contentPanel.revalidate();

    }

    // текст теории для выбранного раздела
    private void showTheoryText(String subSectionName) {
        String fileName = "";
        if (subSectionName.equals("Переменные и типы")) {
            fileName = "Theory_varibales.txt";
        } else if (subSectionName.equals("Операторы")) {
            fileName = "Theory_operators.txt";
        }

        topPanel.setVisible(false);
        contentPanel.removeAll();

        JTextArea textArea = new JTextArea(readTheoryFile(fileName));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        textArea.setForeground(new Color(210, 200, 230));
        textArea.setBackground(new Color(25, 20, 35));
        textArea.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        JLabel finishLabel = new JLabel("Завершить");
        finishLabel.setFont(new Font("Arial", Font.BOLD, 14));
        finishLabel.setForeground(new Color(210, 200, 230));
        finishLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        finishLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        finishLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                db.saveProgress(currentUser, subSectionName, 1);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                finishLabel.setForeground(new Color(150, 130, 200));
                finishLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                finishLabel.setForeground(new Color(210, 200, 230));
                finishLabel.setCursor(Cursor.getDefaultCursor());
            }
        });

            JPanel textPanel = new JPanel(new BorderLayout());
            textPanel.setOpaque(false);
            textPanel.add(scrollPane, BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            bottomPanel.setOpaque(false);
            bottomPanel.add(finishLabel);
            textPanel.add(bottomPanel, BorderLayout.SOUTH);

        contentPanel.add(textPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showTheoryTasks() {
        contentPanel.removeAll();
        topPanel.setVisible(false);
        mainPanel.revalidate();
        mainPanel.repaint();

        List<TheoryTask> tasks = loadTasksFromFile(); // Загружаем
        contentPanel.add(new TheoryTaskPanel(tasks, contentPanel, topPanel, db)); // Передаем

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private void showPracticeTasks() {
        contentPanel.removeAll();
        topPanel.setVisible(false);
        mainPanel.revalidate();
        mainPanel.repaint();

        List<PracticeTask> tasks = loadPracticeTasksFromFile();

        // ЗАЩИТА: если список пустой, показываем ошибку
        if (tasks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Файл practice_tasks.txt пуст или не найден. Проверь ресурсы!");
            showMainMenu(); // Возвращаем в меню
            return;
        }

        contentPanel.add(new PracticeTaskPanel(tasks, contentPanel, topPanel, db));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // ========== УТИЛИТЫ (ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ И КЛАССЫ)

    private String readTheoryFile(String fileName) {
        try (var is = getClass().getResourceAsStream("/" + fileName)) {
            if (is == null) {
                return "Файл теории не найден";
            }

            return new String(is.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            return "Ошибка чтения файла: " + e.getMessage();
        }
    }

    private void showAuth() {
        topPanel.setVisible(false);
        contentPanel.removeAll();
        contentPanel.add(new AuthPanel(db, this));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private List<TheoryTask> loadTasksFromFile() {
        List<TheoryTask> tasks = new ArrayList<>();
        try (var is = getClass().getResourceAsStream("/tasks.txt")) {
            if (is == null) {
                System.out.println("Файл не найден");
                return tasks;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";"); // ["Вопрос", "ключ1,ключ2", "ответ", "объяснение"]
                if (parts.length == 4) {
                    String question = parts[0];
                    String[] keywords = parts[1].split(",");
                    String correctAnswer = parts[2];
                    String explanation = parts[3];
                    tasks.add(new TheoryTask(question, correctAnswer, keywords, explanation));
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return tasks;
    }

    private List<PracticeTask> loadPracticeTasksFromFile() {
        List<PracticeTask> tasks = new ArrayList<>();
        try (var is = getClass().getResourceAsStream("/practice_tasks.txt")) {
            if (is == null) {
                System.out.println("Файл practice_tasks.txt не найден");
                return tasks;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, java.nio.charset.StandardCharsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";", 3);
                if (parts.length == 3) {
                    String description = parts[0];
                    String correctCode = parts[1];
                    String hint = parts[2];
                    tasks.add(new PracticeTask(description, correctCode, hint));
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка чтения практических задач: " + e.getMessage());
        }
        return tasks;
    }




}


