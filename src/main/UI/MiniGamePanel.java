import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MiniGamePanel extends JPanel {
    public MiniGamePanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(25, 20, 35));

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(new Color(35, 30, 45));
        cardPanel.setPreferredSize(new Dimension(420, 230)); // <-- Ширина 600, высота 300

        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));



        JLabel example = new JLabel("x + 5 = 7");
        example.setFont(new Font("Arial", Font.BOLD, 19));
        example.setForeground(new Color(210, 200, 230));
        example.setAlignmentX(Component.LEFT_ALIGNMENT);
        example.setBorder(BorderFactory.createEmptyBorder(10, 20, 10 ,20));
        cardPanel.add(example, BorderLayout.WEST);

        JTextField field = new JTextField();
        field.setFont(new Font("Arial", Font.PLAIN, 15));
        field.setForeground(new Color(210, 200, 230));
        field.setBackground(new Color(45, 40, 60));
        field.setCaretColor(new Color(150, 130, 200));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 80, 150), 1),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setMaximumSize(new Dimension(300, 40));
        cardPanel.add(field, BorderLayout.CENTER);
        cardPanel.add(Box.createVerticalStrut(20));

        JLabel checkLabel = new JLabel("Проверить");
        checkLabel.setFont(new Font("Arial", Font.BOLD, 16));
        checkLabel.setForeground(new Color(210, 200, 230));
        checkLabel.setOpaque(true);
        checkLabel.setBackground(new Color(60, 50, 80));
        checkLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(110, 90, 160),1 ),
                BorderFactory.createEmptyBorder(5, 10, 10, 10)
        ));
        checkLabel.setPreferredSize(new Dimension(150, 35));
        checkLabel.setMaximumSize(new Dimension(150, 35));
        cardPanel.add(checkLabel, BorderLayout.SOUTH);


        checkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e ) {
                String answer = field.getText().trim();

                if (answer.equals("2")) {
                    JOptionPane.showMessageDialog(null, "Молодец, пять!");
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(null, "Неверно, ДВА!");
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                checkLabel.setForeground(new Color(150, 130, 200));
                checkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                checkLabel.setForeground(new Color(210, 200, 230));
                checkLabel.setCursor(Cursor.getDefaultCursor());
            }
        });



        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(cardPanel, gbc);
    }

}
