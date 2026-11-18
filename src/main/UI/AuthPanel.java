import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;


public class AuthPanel extends JPanel {
    private JLabel registerLabel;
    private JLabel loginLabel;
    private JTextField nameField;
    private JPasswordField passwordField;
    private JLabel actionLabel;
    private boolean isLoginMode = true;
    private JLabel statusLabel;
    private DataBaseManager db;
    private LearnWindow learnWindow;


    public AuthPanel(DataBaseManager db, LearnWindow learnWindow) {
        setBackground(new Color(25, 20, 35));
        setLayout(new GridBagLayout());
        this.db = db;
        this.learnWindow = learnWindow;
        setFocusable(true);
        SwingUtilities.invokeLater(() -> this.requestFocusInWindow());

        // --- ПАНЕЛЬ ДЛЯ РЕГИСТРАЦИИ И ВХОДА -----


        // --- ФОРМА ДЛЯ РЕГИСТРАЦИИ

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BorderLayout());
        cardPanel.setBackground(new Color(35, 30,50));
        cardPanel.setPreferredSize(new Dimension(400, 260));
        cardPanel.setBorder(BorderFactory.createLineBorder(new Color(65, 65, 65),1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        add(cardPanel, gbc);

        // ----

        registerLabel = new JLabel("Регистрация");
        registerLabel.setFont(new Font("Arial", Font.BOLD, 15));
        registerLabel.setForeground(new Color(210, 200, 230));
        registerLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5 ,10));

        registerLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                isLoginMode = false;
                actionLabel.setText("Зарегистрироваться");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                registerLabel.setForeground(new Color(150, 130 ,200));
                registerLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                registerLabel.setForeground(new Color(210, 200, 230));
                registerLabel.setCursor(Cursor.getDefaultCursor());
            }
        });

        loginLabel = new JLabel("Вход");
        loginLabel.setForeground(new Color(210, 200, 230));
        loginLabel.setFont(new Font("Arial", Font.BOLD, 15));
        loginLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                isLoginMode = true;
                actionLabel.setText("Войти");
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                loginLabel.setForeground(new Color(150, 130 ,200));
                loginLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginLabel.setForeground(new Color(210, 200, 230));
                loginLabel.setCursor(Cursor.getDefaultCursor());
            }
        });

        JPanel tabPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        tabPanel.setOpaque(false);

        tabPanel.add(loginLabel);
        tabPanel.add(registerLabel);

        cardPanel.add(tabPanel, BorderLayout.NORTH);


        // ----- ПАНЕЛЬ ДЛЯ ФОРМЫ (ПАРОЛЬ И ИМЯ) ------

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);
        nameField.setForeground(new Color(210, 200, 230));
        nameField.setBackground(new Color(40, 35, 55));
        nameField.setCaretColor(new Color(180, 160, 230));
        String nammePlaceholder = "Введите имя";
        nameField.setText(nammePlaceholder);


        // ----- ПОДСКАЗКА PLACEHOLDER ДЛЯ ПОЛЯ ИМЕНИ

        nameField.setText("Введите имя");
        nameField.setForeground(Color.GRAY);
        nameField.setMargin(new Insets(5, 10, 5, 10));

        nameField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (nameField.getText().equals(nammePlaceholder)) {
                    nameField.setText("");
                    nameField.setForeground(new Color(210, 200, 230));

                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setText(nammePlaceholder);
                    nameField.setForeground(Color.GRAY);

                }
            }
        });

        passwordField = new JPasswordField();
        passwordField.setAlignmentX(Component.LEFT_ALIGNMENT);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        passwordField.setForeground(new Color(210, 200 ,230));
        passwordField.setBackground(new Color(40, 35, 55));
        passwordField.setCaretColor(new Color(180, 160, 230));
        String passPlaceholder = "Введите пароль";
        passwordField.setText(passPlaceholder);

        // ---- ПОДСКАЗКА PLACEHOLDER ДЛЯ ПАРОЛЯ

        passwordField.setText("Введите пароль");
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0);
        passwordField.setMargin(new Insets(5, 10, 5, 10));

        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals(passPlaceholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(new Color(210, 200, 230));
                    passwordField.setEchoChar('*');

                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (passwordField.getPassword().length == 0) {
                    passwordField.setText(passPlaceholder);
                    passwordField.setForeground(Color.GRAY);
                    passwordField.setEchoChar((char) 0);
                }
            }
        });

        JLabel nameLabel = new JLabel("Имя");
        nameLabel.setForeground(new Color(210, 200, 230));
        nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        //centerPanel.add(nameLabel);

        centerPanel.add(nameField);

        centerPanel.add(Box.createVerticalStrut(15));

        JLabel passLabel = new JLabel("Пароль");
        passLabel.setFont(new Font("Arial", Font.BOLD, 15));
        passLabel.setForeground(new Color(210, 200, 230));
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
       //centerPanel.add(passLabel);

        centerPanel.add(passwordField);
        centerPanel.add(Box.createVerticalStrut(15));
        statusLabel = new JLabel("Статус");
        statusLabel.setFont(new Font("Arial", Font.BOLD, 15));
        statusLabel.setForeground(new Color(210, 200, 230));
        statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        statusLabel.setVisible(false);
        statusLabel.setPreferredSize(new Dimension(300, 20));
        centerPanel.add(statusLabel);

        cardPanel.add(centerPanel, BorderLayout.CENTER);

        // ----- ПАНЕЛЬ ДЛЯ ВХОДА/РЕГИСТРАЦИИ ------


        // 1. Создаем statusLabel


        // 2. Создаем actionLabel (кнопку)
        actionLabel = new JLabel("Войти");
        actionLabel.setForeground(new Color(180, 160, 230)); // Темный текст
        actionLabel.setFont(new Font("Arial", Font.BOLD, 15));
        actionLabel.setOpaque(true);
        actionLabel.setBackground(new Color(45, 40, 55)); // Фиолетовый фон
        actionLabel.setBorder(BorderFactory.createLineBorder(new Color(150, 130, 200), 1));
        actionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        actionLabel.setPreferredSize(new Dimension(300, 40));
        actionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        actionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        actionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String name = nameField.getText();
                String pass = new String(passwordField.getPassword());

                statusLabel.setVisible(true);

                if (isLoginMode) {
                    boolean success = db.loginUser(name, pass);
                    if (success) {
                        statusLabel.setText("Успешный вход!");
                        statusLabel.setForeground(new Color(100, 200, 100));
                        learnWindow.setCurrentUser(name);
                        learnWindow.showMainMenu();
                    } else {
                        statusLabel.setText("Неверный логин или пароль");
                        statusLabel.setForeground(new Color(200, 100, 100));
                    }
                } else {
                    boolean success = db.registerUser(name, pass);
                    if (success) {
                        statusLabel.setText("Успешно! Войдите.");
                        statusLabel.setForeground(new Color(100, 200, 100));
                        isLoginMode = true;
                        actionLabel.setText("Войти");
                    } else {
                        statusLabel.setText("Этот ник уже занят");
                        statusLabel.setForeground(new Color(150, 130, 200));
                    }
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                actionLabel.setBackground(new Color(100, 80, 150)); // Фон чуть светлее
                actionLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                actionLabel.setBackground(new Color(45, 40, 55)); // Возвращаем фиолетовый
                actionLabel.setCursor(Cursor.getDefaultCursor());
            }
        });

        // 3. Собираем нижнюю панель карточки (только кнопка)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setOpaque(false);
        bottomPanel.add(actionLabel);
        cardPanel.add(bottomPanel, BorderLayout.SOUTH);







    }

}
