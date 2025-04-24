import javax.swing.*;

public class GameTimer implements Runnable {
    private volatile boolean isContinue = true;
    public static int seconds = 0;
    private JLabel timer;
    private PacmanGame game;

    public GameTimer(JLabel timer, PacmanGame game) {
        this.timer = timer;
        this.game = game;
    }

    public void stop() {
        isContinue = false;
    }

    public synchronized void run() {
        while (isContinue) {
            try {
                Thread.sleep(1000);
                seconds++;
                SwingUtilities.invokeLater(() -> timer.setText("Time: " + seconds + "s"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized int getSeconds() {
        return seconds;
    }
}
