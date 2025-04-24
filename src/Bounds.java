import javax.swing.*;
import java.awt.*;

public class Bounds extends JLabel {
    public boolean canRun = false;
    int size;

    private int x;
    private int y;
    public Bounds(int x, int y, int size){
        this.x=x;
        this.y=y;
        this.size=size;
        setBounds(x, y, size, size);
        setOpaque(true);
        ImageIcon pacmanIcon = new ImageIcon("Assets/brick1.png");
        Image scaledImage = pacmanIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));
        setOpaque(false);
    }
}
