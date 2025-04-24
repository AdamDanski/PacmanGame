import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOver extends JFrame {

    public GameOver(int score, int timeInSeconds) {

        JLabel background = new JLabel();
        background.setLayout(new BorderLayout());
        background.setPreferredSize(new Dimension(700, 400));
        background.setBackground(Color.BLACK);

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);
        ImageIcon gameOverIcon = new ImageIcon("Assets/game.png");
        Image scaledGameOverImage = gameOverIcon.getImage().getScaledInstance(320, 200, Image.SCALE_SMOOTH);
        JLabel gameOverLabel = new JLabel(new ImageIcon(scaledGameOverImage));
        topPanel.add(gameOverLabel);
        background.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new GridLayout(3, 1));

        JLabel scoreLabel = new JLabel("Wynik: " + score);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(scoreLabel);

        JLabel timeLabel = new JLabel("Czas gry: " + timeInSeconds + " sekund");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(timeLabel);

        JTextField nick = new JTextField();
        nick.setFont(new Font("Arial", Font.PLAIN, 20));
        centerPanel.add(nick);

        background.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton submitButton = new JButton("Zapisz wynik");
        submitButton.setPreferredSize(new Dimension(200, 50));
        submitButton.setFont(new Font("Arial", Font.PLAIN, 20));
        submitButton.setBackground(new Color(0, 210, 255));
        submitButton.setForeground(Color.BLACK);
        submitButton.setBorderPainted(false);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = nick.getText();
                if(nickname!=null){
                    ScoreBox scoreBox = new ScoreBox(nickname, score, timeInSeconds);
                    ListOfScores listOfScores = new ListOfScores();
                    listOfScores.addScore(scoreBox);
                    dispose();
                    new Menu();
                }else{
                    JOptionPane.showMessageDialog(null, "Prosze wpisac swoj nick");
                }
            }
        });
        bottomPanel.add(submitButton);


        JButton tryAgainButton = new JButton("Zagraj jeszcze raz");
        tryAgainButton.setPreferredSize(new Dimension(200, 50));
        tryAgainButton.setFont(new Font("Arial", Font.PLAIN, 18));
        tryAgainButton.setBackground(new Color(255, 234, 0));
        tryAgainButton.setForeground(Color.BLACK);
        tryAgainButton.setBorderPainted(false);
        tryAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MapWindow();
            }
        });
        bottomPanel.add(tryAgainButton);

        background.add(bottomPanel, BorderLayout.SOUTH);
        background.setOpaque(true);
        background.setBackground(Color.BLACK);

        setContentPane(background);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
