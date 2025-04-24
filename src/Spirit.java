import javax.swing.*;
import java.awt.*;

public class Spirit extends JLabel implements Runnable {
    private boolean isGameContinuing = true;
    private final int size;
    private final int board_x;
    private final int board_y;
    private final PacmanGame game;
    public int x;
    public int y;
    public int startX;
    public int startY;
    public int directionx = 0;
    public int directiony = 0;
    public String file;

    public Spirit(int size, int board_x, int board_y, int gridPositionx, int gridPositiony, PacmanGame game, String file) {
        this.file = file;
        this.game = game;
        this.x = gridPositionx;
        this.y = gridPositiony;
        this.startX = gridPositionx;
        this.startY = gridPositiony;
        this.setLayout(null);
        this.board_x = board_x;
        this.board_y = board_y;
        this.size = size;
        ImageIcon spiritIcon = new ImageIcon("Assets/" + file);
        Image scaledImage = spiritIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));
        setBounds(x * size, y * size, size, size);
        setOpaque(false);
    }

    public synchronized void setGameContinuing() {
        isGameContinuing = !isGameContinuing;
    }

    public synchronized boolean canSpiritGo(int x, int y) {
        if (x < 0 || y < 0 || x >= game.mapka[0].length || y >= game.mapka.length) {
            return false;
        } else {
            return game.mapka[y][x] != 1;
        }
    }

    public synchronized boolean isBetweenParallelWalls() {
        if (directionx != 0) {
            return !canSpiritGo(x, y - 1) && !canSpiritGo(x, y + 1);
        } else if (directiony != 0) {
            return !canSpiritGo(x - 1, y) && !canSpiritGo(x + 1, y);
        }
        return false;
    }

    public synchronized void chooseDirection() {
        int newDirectionX = 0;
        int newDirectionY = 0;
        newDirectionX = (int) (Math.random() * 3) - 1;
        newDirectionY = (int) (Math.random() * 3) - 1;
        if ((newDirectionX == 0 || newDirectionY == 0) && (newDirectionX != 0 || newDirectionY != 0)) {
            if (canSpiritGo(x + newDirectionX, y + newDirectionY)) {
                directionx = newDirectionX;
                directiony = newDirectionY;
            }
        }
    }

    public synchronized void updatePosition() {
        int size_x = getParent().getWidth() / board_x;
        int size_y = getParent().getHeight() / board_y;
        SwingUtilities.invokeLater(() -> setBounds(x * size_x, y * size_y, size_x, size_y));
    }

    public void run() {
        while (isGameContinuing) {
            synchronized (game) {
                if (!isBetweenParallelWalls()) {
                    chooseDirection();
                }
                int newX = x + directionx;
                int newY = y + directiony;

                if (canSpiritGo(newX, newY)) {
                    x = newX;
                    y = newY;
                    updatePosition();

                } else {
                    directionx = 0;
                    directiony = 0;
                }

                if (x == game.getPacman().x && y == game.getPacman().y) {
                    game.getPacman().life--;
                    game.updateLifeDisplay();
                    game.getPacman().startPosistion();
                    if (game.getPacman().life < 1) {
                        isGameContinuing = false;
                        game.getPacman().stopAnimation();
                        game.gridPanel.removeAll();
                        game.dispose();
                        new GameOver(PacmanGame.scoreCounter, GameTimer.seconds);
                        PacmanGame.scoreCounter = 0;
                    }
                }
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
