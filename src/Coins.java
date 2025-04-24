import javax.swing.*;
import java.awt.*;

public class Coins extends JLabel {
    private int size;
    private JPanel gridPanel;
    private int x;
    private int y;
    public boolean eaten = false;
    public String file = "Assets/coin.png";
    private PacmanGame game;

    public Coins(int size_width, int size_height, int size, int x, int y, PacmanGame game) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.game = game;
        setBackground(new Color(41, 40, 41));
        ImageIcon pacmanIcon = new ImageIcon(file);
        Image scaledImage = pacmanIcon.getImage().getScaledInstance(size_width, size_height, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));
        setOpaque(false);
    }

    public void collect(JPanel gridPanel, int newX, int newY) {
        SwingUtilities.invokeLater(() -> {
            synchronized (game) {
                EmptyField emptyField = new EmptyField(size, newX, newY);
                gridPanel.remove(this);
                gridPanel.add(emptyField, newY * game.mapka[0].length + newX);
                game.mapa[newY][newX] = emptyField;
                this.eaten = true;
            }
        });
    }
}
