import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ScoresWindow extends JFrame {
    public ScoresWindow(){
        setTitle("Scores");
        setSize(700,600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);

        JPanel scorePanel = new JPanel();
        scorePanel.setPreferredSize(new Dimension(400,400));
        scorePanel.setBackground(Color.WHITE);
        scorePanel.setLayout(new BorderLayout());

        ListOfScores listOfScores = new ListOfScores();
        List<ScoreBox> scores = listOfScores.getScores();

        DefaultListModel<String> defaultListModel = new DefaultListModel<>();
        for (ScoreBox score : scores) {
            defaultListModel.addElement(score.toString());
        }

        JList<String> scoreList = new JList<>(defaultListModel);
        JScrollPane scrollPane = new JScrollPane(scoreList);
        scorePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setOpaque(false);
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.add(scorePanel);
        add(mainPanel, BorderLayout.CENTER);

        JButton backToMenu = new JButton("Menu");
        backToMenu.setPreferredSize(new Dimension(200,50));
        backToMenu.setBackground(new Color(0, 210, 255));
        backToMenu.setFont(new Font("Arial", Font.BOLD,18));
        backToMenu.setForeground(Color.BLACK);
        backToMenu.setBorderPainted(false);
        backToMenu.addActionListener(action ->{
            dispose();
            new Menu();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(backToMenu);
        add(buttonPanel,BorderLayout.SOUTH);
        setVisible(true);
    }
}
