import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class TheoryTaskPanel extends JPanel {

    private JPanel contentPanel;
    private JPanel topPanel;
    private DataBaseManager db;

    private JTextArea questionArea;
    private JTextField answerField;
    private JLabel checkLabel;
    private JLabel statusLabel;
    private List<TheoryTask> tasks;
    private int currentIndex = 0;
    private TheoryTask currentTask;
    private boolean isAnswered = false;

    public TheoryTaskPanel(List<TheoryTask> tasks, JPanel contentPanel, JPanel topPanel, DataBaseManager db) {
        this.tasks = tasks;
        this.contentPanel = contentPanel;
        this.topPanel = topPanel;
        this.db = db;
        this.currentTask = tasks.get(0);


        // Берем первую задачу из списка
        if (tasks != null && !tasks.isEmpty()) {
            this.currentTask = tasks.get(0);
        } else {
            System.out.println("Список задач пуст!");
            return; // Если задач нет, дальше не рисуем
        }

        setLayout(new GridBagLayout());
        setBackground(new Color(25, 20, 35));

        // ... дальше твой код отрисовки карточки ...

        // 1. Карточка стала шире, высота фиксированная (без лишней пустоты)
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(new Color(35, 30, 45));
        cardPanel.setPreferredSize(new Dimension(420, 230)); // <-- Ширина 600, высота 300

        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // 2. Вопрос
        questionArea = new JTextArea(currentTask.getQuestion());
        questionArea.setFont(new Font("Arial", Font.BOLD, 16));
        questionArea.setForeground(new Color(210, 200, 230));
        questionArea.setLineWrap(true);
        questionArea.setWrapStyleWord(true);
        questionArea.setEditable(false);
        questionArea.setOpaque(false);
        questionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Ограничиваем максимальную высоту вопроса, чтобы он не съедал всё место
        questionArea.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        cardPanel.add(questionArea);

        // Отступ от вопроса до поля
        cardPanel.add(Box.createVerticalStrut(15));

        // 3. Поле ввода
        answerField = new JTextField();
        answerField.setFont(new Font("Arial", Font.PLAIN, 15));
        answerField.setForeground(new Color(210, 200, 230));
        answerField.setBackground(new Color(45, 40, 60));
        answerField.setCaretColor(new Color(150, 130, 200));
        answerField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 80, 150), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        answerField.setAlignmentX(Component.LEFT_ALIGNMENT);
        answerField.setMaximumSize(new Dimension(300, 40));
        cardPanel.add(answerField);

        // Отступ от поля до кнопки
        cardPanel.add(Box.createVerticalStrut(20));

        // 4. Кнопка "Проверить" — приглушенный цвет, размер 150x35
        checkLabel = new JLabel("Проверить", SwingConstants.CENTER);
        checkLabel.setFont(new Font("Arial", Font.BOLD, 14));
        checkLabel.setOpaque(true);
        checkLabel.setBackground(new Color(100, 80, 150));
        checkLabel.setForeground(new Color(210, 200, 230));
        checkLabel.setPreferredSize(new Dimension(150, 35));
        checkLabel.setMaximumSize(new Dimension(150, 35)); // Фиксируем размер, чтобы не растягивалась
        

        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
        statusLabel.setForeground(new Color(210, 200, 230));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setVisible(false);
        cardPanel.add(statusLabel);


        // Панель для кнопки, чтобы прижать её влево
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setOpaque(false);
        // Важно: говорим панели растянуться на всю ширину карточки
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(checkLabel);

        cardPanel.add(buttonPanel);

        // Добавляем карточку на панель по центру
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(cardPanel, gbc);

        // Слушатель для кнопки (пока просто выводит в консоль)
        checkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isAnswered) {
                    // === РЕЖИМ 1: ПРОВЕРКА ОТВЕТА ===
                    String userAnswer = answerField.getText().trim().toLowerCase();
                    String[] currentKeywords = currentTask.getKeywords();

                    // Проверка (твоя любимая)
                    if (userAnswer.contains(currentKeywords[0]) && userAnswer.contains(currentKeywords[1])) {
                        statusLabel.setText("Верно! Молодец");
                        statusLabel.setForeground(new Color(100, 200, 100));
                    } else {
                        statusLabel.setText("Неверно! Правильный ответ: " + currentTask.getCorrectAnswer());
                        statusLabel.setForeground(new Color(200, 100, 100));
                    }
                    statusLabel.setVisible(true);

                    // Меняем режим и текст кнопки
                    isAnswered = true;
                    checkLabel.setText("Дальше");

                } else {
                    // === РЕЖИМ 2: ПЕРЕХОД К СЛЕДУЮЩЕМУ ВОПРОСУ ===
                    currentIndex++;

                    // Проверяем, не закончились ли вопросы
                    if (currentIndex < tasks.size()) {
                        currentTask = tasks.get(currentIndex);

                        // Обновляем интерфейс на новый вопрос
                        questionArea.setText(currentTask.getQuestion());
                        answerField.setText(""); // Очищаем поле
                        statusLabel.setVisible(false); // Прячем старый статус

                        // Возвращаем кнопку в исходное состояние
                        checkLabel.setText("Проверить");
                        isAnswered = false;
                    } else {
                        // Вопросы закончились!
                        statusLabel.setText("Ты прошел все задания!");
                        statusLabel.setForeground(new Color(150, 130, 200)); // Фиолетовый
                        checkLabel.setText("Конец");
                        checkLabel.setEnabled(false); // Выключаем кнопку
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                checkLabel.setBackground(new Color(130, 110, 180));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                checkLabel.setBackground(new Color(100, 80, 150));
            }
        });
    }
}




