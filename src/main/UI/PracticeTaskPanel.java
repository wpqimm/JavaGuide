import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PracticeTaskPanel extends JPanel {
    private List<PracticeTask> tasks;
    private int currentIndex = 0;
    private PracticeTask currentTask;
    private boolean isAnswered = false;

    private JTextArea descriptionArea;
    private JTextField codeField;
    private JLabel checkLabel;
    private JLabel statusLabel;

    public PracticeTaskPanel(List<PracticeTask> tasks, JPanel contentPanel, JPanel topPanel, DataBaseManager db) {
        this.tasks = tasks;

        if (tasks != null && !tasks.isEmpty()) {
            this.currentTask = tasks.get(0);
        }

        setLayout(new GridBagLayout());
        setBackground(new Color(25, 20, 35));

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(new Color(35, 30, 45));
        cardPanel.setPreferredSize(new Dimension(550, 280)); // Чуть шире, так как код длинный

        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // 1. Текст задачи
        descriptionArea = new JTextArea(currentTask.getDescription());
        descriptionArea.setFont(new Font("Arial", Font.BOLD, 16));
        descriptionArea.setForeground(new Color(210, 200, 230));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setEditable(false);
        descriptionArea.setOpaque(false);
        descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        cardPanel.add(descriptionArea);
        cardPanel.add(Box.createVerticalStrut(15));

        // 2. Поле для ввода кода
        codeField = new JTextField();
        codeField.setFont(new Font("Consolas", Font.PLAIN, 15)); // Моноширинный шрифт для кода!
        codeField.setForeground(new Color(210, 200, 230));
        codeField.setBackground(new Color(45, 40, 60));
        codeField.setCaretColor(new Color(150, 130, 200));
        codeField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 80, 150), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        codeField.setAlignmentX(Component.LEFT_ALIGNMENT);
        codeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        cardPanel.add(codeField);
        cardPanel.add(Box.createVerticalStrut(20));

        // 3. Кнопка
        checkLabel = new JLabel("Проверить", SwingConstants.CENTER);
        checkLabel.setFont(new Font("Arial", Font.BOLD, 14));
        checkLabel.setOpaque(true);
        checkLabel.setBackground(new Color(100, 80, 150));
        checkLabel.setForeground(new Color(210, 200, 230));
        checkLabel.setPreferredSize(new Dimension(150, 35));
        checkLabel.setMaximumSize(new Dimension(150, 35));
        checkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        buttonPanel.add(checkLabel);
        cardPanel.add(buttonPanel);
        cardPanel.add(Box.createVerticalStrut(10));

        // 4. Статус
        statusLabel = new JLabel("");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 14));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setVisible(false);
        cardPanel.add(statusLabel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(cardPanel, gbc);

        // ===== ЛОГИКА КНОПКИ =====
        checkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isAnswered) {
                    // Режим проверки
                    String userAnswer = codeField.getText();

                    // Удаляем все пробелы для честного сравнения
                    String cleanUserAnswer = userAnswer.replace(" ", "");
                    String cleanCorrectAnswer = currentTask.getCorrectCode().replace(" ", "");

                    if (cleanUserAnswer.equalsIgnoreCase(cleanCorrectAnswer)) {
                        statusLabel.setText("Верно! Отличная работа.");
                        statusLabel.setForeground(new Color(100, 200, 100));
                    } else {
                        statusLabel.setText("Неверно. Подсказка: " + currentTask.getHint());
                        statusLabel.setForeground(new Color(200, 100, 100));
                    }
                    statusLabel.setVisible(true);

                    isAnswered = true;
                    checkLabel.setText("Дальше");

                } else {
                    // Режим переключения
                    currentIndex++;
                    if (currentIndex < tasks.size()) {
                        currentTask = tasks.get(currentIndex);

                        descriptionArea.setText(currentTask.getDescription());
                        codeField.setText("");
                        statusLabel.setVisible(false);

                        checkLabel.setText("Проверить");
                        isAnswered = false;
                    } else {
                        statusLabel.setText("Задачи закончились! Молодец!");
                        statusLabel.setForeground(new Color(150, 130, 200));
                        checkLabel.setText("Конец");
                        checkLabel.setEnabled(false);
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (checkLabel.isEnabled()) checkLabel.setBackground(new Color(130, 110, 180));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (checkLabel.isEnabled()) checkLabel.setBackground(new Color(100, 80, 150));
            }
        });
    }
}