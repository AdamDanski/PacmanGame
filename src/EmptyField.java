import javax.swing.*;
import java.awt.*;

public class EmptyField extends JLabel {
    private int size;
    private int x;
    private int y;

    public EmptyField(int size, int x, int y) {
        this.x = x;
        this.y = y;
        this.size = size;
        setBackground(new Color(41, 40, 41));
        setOpaque(false);
    }
}