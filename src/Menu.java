import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame {
    public Menu() {
        JButton newGame = new JButton("New Game");
        JButton highScores = new JButton("High Scores");
        JButton exit = new JButton("Exit");

        JButton[] buttons = new JButton[]{newGame, highScores, exit};

        JPanel background = new JPanel(new GridBagLayout());
        background.setBackground(Color.BLACK);

        background.setPreferredSize(new Dimension(700, 30));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        ImageIcon logo_graphic = new ImageIcon("Assets/pacman_logo.png");
        JLabel logo = new JLabel(logo_graphic);

        gbc.gridy = 0;
        background.add(logo, gbc);

        Dimension buttonsSize = new Dimension(300, 80);
        Font buttonsFont = new Font("Arial", Font.PLAIN, 30);
        Color fontColor = Color.BLACK;

        for (JButton j : buttons) {
            j.setOpaque(true);
            j.setBorderPainted(false);
            j.setPreferredSize(buttonsSize);
            j.setFont(buttonsFont);
            j.setForeground(fontColor);
        }

        newGame.setBackground(new Color(255, 234, 0));
        gbc.gridx = 0;
        gbc.gridy = 1;
        background.add(newGame, gbc);

        highScores.setBackground(new Color(0, 210, 255));
        gbc.gridx = 0;
        gbc.gridy = 2;
        background.add(highScores, gbc);

        exit.setBackground(new Color(255, 0, 43));
        gbc.gridx = 0;
        gbc.gridy = 3;
        background.add(exit, gbc);

        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                background.removeAll();
                dispose();
                new MapWindow();
            }
        });

        highScores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                background.removeAll();
                dispose();
                new ScoresWindow();
            }
        });

        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        setContentPane(background);
        setSize(new Dimension(700, 600));
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new Menu();
    }
}
