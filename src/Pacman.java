import java.awt.*;
import javax.swing.*;

public class Pacman extends JLabel implements Runnable {
    public boolean isGameContinuing = true;
    private final JPanel gridPanel;
    private final int size;
    private final int board_x;
    private final int board_y;
    private final PacmanGame game;
    private final JPanel topPanel;
    public int x = 9;
    public int y = 9;
    public int startx = x;
    public int starty = y;
    public int directionx = 0;
    public int directiony = 0;
    public int life = 3;
    private GameTimer animationGameTimer;
    private int animationIndex = 0;
    private final String[] pacmanImagesRIGHT = {"Assets/pacman1_right.png", "Assets/pacman2_right.png", "Assets/pacman3_right.png"};
    private final String[] pacmanImagesLEFT = {"Assets/pacman1_left.png", "Assets/pacman2_left.png", "Assets/pacman3_left.png"};
    private final String[] pacmanImagesDOWN = {"Assets/pacman1_down.png", "Assets/pacman2_down.png", "Assets/pacman3_down.png"};
    private final String[] pacmanImagesUP = {"Assets/pacman1_up.png", "Assets/pacman2_up.png", "Assets/pacman3_up.png"};
    private DirectoryName directoryName = DirectoryName.RIGHT;

    public Pacman(int size, int board_x, int board_y, JPanel gridPanel, PacmanGame game, JPanel topPanel) {
        this.topPanel = topPanel;
        this.game = game;
        this.setLayout(null);
        this.board_x = board_x;
        this.board_y = board_y;
        this.gridPanel = gridPanel;
        this.size = size;
        ImageIcon pacmanIcon = new ImageIcon("Assets/pacman2.png");
        Image scaledImage = pacmanIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        setIcon(new ImageIcon(scaledImage));
        setBounds(x * size, y * size, size, size);
        setOpaque(false);
    }

    public void setGameContinuing() {
        isGameContinuing = !isGameContinuing;
    }

    private synchronized void loadImage() {
        switch (directoryName) {
            case UP -> {
                ImageIcon pacmanIcon = new ImageIcon(pacmanImagesUP[animationIndex]);
                Image scaledImage = pacmanIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(scaledImage));
            }
            case DOWN -> {
                ImageIcon pacmanIcon = new ImageIcon(pacmanImagesDOWN[animationIndex]);
                Image scaledImage = pacmanIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(scaledImage));
            }
            case LEFT -> {
                ImageIcon pacmanIcon = new ImageIcon(pacmanImagesLEFT[animationIndex]);
                Image scaledImage = pacmanIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(scaledImage));
            }
            case RIGHT -> {
                ImageIcon pacmanIcon = new ImageIcon(pacmanImagesRIGHT[animationIndex]);
                Image scaledImage = pacmanIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(scaledImage));
            }
        }
    }

    public synchronized void startAnimation() {
        animationGameTimer = new GameTimer(new JLabel(),game) {
            @Override
            public void run() {
                while (isGameContinuing) {
                    try {
                        Thread.sleep(500);
                        animationIndex = (animationIndex + 1) % 3;
                        SwingUtilities.invokeLater(() -> loadImage());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        new Thread(animationGameTimer).start();
    }


    public void stopAnimation() {
        if (animationGameTimer != null) {
            animationGameTimer.stop();
        }
    }

    public void setDirectoryName(DirectoryName directoryName) {
        this.directoryName = directoryName;
    }

    public void directory(int x, int y) {
        this.directionx = x;
        this.directiony = y;
    }

    public boolean canPacmanGo(int x, int y) {
        if (x < 0 || y < 0 || x >= game.mapka[0].length - 1 || y >= game.mapka.length - 1) {
            return false;
        } else {
            return game.mapka[y][x] != 1;
        }
    }

    public void updatePosition() {
        int size_x = getParent().getWidth() / board_x;
        int size_y = getParent().getHeight() / board_y;
        SwingUtilities.invokeLater(() -> setBounds(x * size_x, y * size_y, size_x, size_y));
    }

    public void startPosistion() {
        x = startx;
        y = starty;
        updatePosition();
    }

    public synchronized boolean collison() {
        return x == game.getSpirit1().x && y == game.getSpirit1().y ||
                x == game.getOrangeGhost().x && y == game.getOrangeGhost().y ||
                x == game.getBlueGhost().x && y == game.getBlueGhost().y ||
                x == game.getRedGhost().x && y == game.getRedGhost().y;
    }

    public void run() {
        while (isGameContinuing) {
            synchronized (game) {
                int oldX = x;
                int oldY = y;
                int newX = x + directionx;
                int newY = y + directiony;

                if (directionx != 0 || directiony != 0) {
                    if (canPacmanGo(newX, newY)) {
                        if (game.mapa[newY][newX] instanceof Coins && !((Coins) game.mapa[newY][newX]).eaten) {
                            PacmanGame.scoreCounter += 1;
                            game.updateScoreLabel();
                            ((Coins) game.mapa[newY][newX]).collect(gridPanel, newX, newY);
                        }
                        game.mapa[oldY][oldX] = new EmptyField(getWidth(), oldX, oldY);
                        game.mapa[newY][newX] = this;
                        x = newX;
                        y = newY;
                        updatePosition();
                    } else {
                        directionx = 0;
                        directiony = 0;
                    }
                }

                if (collison()) {
                    life--;
                    game.updateLifeDisplay();
                    startPosistion();
                    if (life < 1) {
                        isGameContinuing = false;
                        stopAnimation();
                        SwingUtilities.invokeLater(() -> {
                            new GameOver(PacmanGame.scoreCounter, GameTimer.seconds);
                            game.gridPanel.removeAll();
                            game.gridPanel.revalidate();
                            game.gridPanel.repaint();
                            game.dispose();
                            PacmanGame.scoreCounter = 0;
                        });
                        break;
                    }
                }
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
