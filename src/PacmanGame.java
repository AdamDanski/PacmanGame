import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PacmanGame extends JFrame {
    public static int scoreCounter;
    public int[][] mapka;
    public JLabel[][] mapa;
    public JPanel gridPanel = new JPanel();
    public int dimension;
    public int size;
    public int map_width;
    public int map_length;
    public JPanel topPanel = new JPanel();
    private Pacman pacman;
    private Spirit greenGhost;
    private Spirit orangeGhost;
    private Spirit redGhost;
    private Spirit blueGhost;

    JLabel scoreText;
    JLabel timeLabel;
    private GameTimer timer;
    private JPanel life;
    public boolean load = true;

    public PacmanGame(int[][] mapka) {
        this.mapka = mapka;
        this.mapa = new JLabel[mapka.length][mapka[0].length];
        this.dimension = 800;
        this.size = dimension / mapka.length;
        this.map_width = mapka[0].length;
        this.map_length = mapka.length;

        int ghostStartX = 1;
        int ghostStartY = 1;

        this.pacman = new Pacman(size, map_width, map_length, gridPanel, this, topPanel);
        this.greenGhost = new Spirit(size, map_width, map_length, ghostStartX, ghostStartY, this, "greenGhost1.png");
        this.orangeGhost = new Spirit(size, map_width, map_length, ghostStartX + 1, ghostStartY, this, "orangeGhost1.png");
        this.redGhost = new Spirit(size, map_width, map_length, ghostStartX, ghostStartY + 1, this, "redGhost1.png");
        this.blueGhost = new Spirit(size, map_width, map_length, ghostStartX + 1, ghostStartY + 1, this, "blueGhost1.png");

        setTitle("Pacman");
        topPanel.setPreferredSize(new Dimension(800, 30));
        topPanel.setLayout(new GridLayout(1, 3));
        topPanel.setBackground(Color.BLACK);
        scoreText = new JLabel("Score: " + scoreCounter);
        scoreText.setForeground(Color.WHITE);
        scoreText.setFont(new Font("Arial", Font.BOLD, 20));

        timeLabel = new JLabel("Time: 0s");
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 20));

        life = new JPanel();
        life.setBackground(Color.BLACK);
        life.setLayout(new FlowLayout(FlowLayout.RIGHT));
        for (int i = 0; i < pacman.life; i++) {
            ImageIcon heartImage = new ImageIcon("Assets/heart1.png");
            Image scaledImage = heartImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            JLabel lifeLabel = new JLabel(new ImageIcon(scaledImage));
            life.add(lifeLabel);
        }

        topPanel.add(scoreText);
        topPanel.add(timeLabel);
        topPanel.add(life);

        add(topPanel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);

        setSize(new Dimension(dimension, dimension));
        gridPanel.setSize(new Dimension(dimension, dimension + 100));
        gridPanel.setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gridPanel.setLayout(new GridLayout(mapka.length, mapka[0].length));
        gridPanel.setBackground(Color.BLACK);
        if (load) {
            loadMap();
        }
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int key = e.getKeyCode();
                switch (key) {
                    case KeyEvent.VK_UP -> {
                        pacman.directory(0, -1);
                        pacman.setDirectoryName(DirectoryName.UP);
                    }
                    case KeyEvent.VK_DOWN -> {
                        pacman.directory(0, 1);
                        pacman.setDirectoryName(DirectoryName.DOWN);
                    }
                    case KeyEvent.VK_RIGHT -> {
                        pacman.directory(1, 0);
                        pacman.setDirectoryName(DirectoryName.RIGHT);
                    }
                    case KeyEvent.VK_LEFT -> {
                        pacman.directory(-1, 0);
                        pacman.setDirectoryName(DirectoryName.LEFT);
                    }
                    case KeyEvent.VK_ESCAPE -> {
                        gridPanel.removeAll();
                        topPanel.removeAll();
                        dispose();
                        new Menu();
                    }
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();

        new Thread(pacman).start();
        new Thread(blueGhost).start();
        new Thread(redGhost).start();
        new Thread(greenGhost).start();
        new Thread(orangeGhost).start();
        timer = new GameTimer(timeLabel, this);
        new Thread(timer).start();
        pacman.startAnimation();
    }

    public synchronized void updateScoreLabel() {
        scoreText.setText("Score: " + scoreCounter);
    }

    public Pacman getPacman() {
        return pacman;
    }

    public Spirit getSpirit1() {
        return greenGhost;
    }

    public Spirit getOrangeGhost() {
        return orangeGhost;
    }

    public Spirit getRedGhost() {
        return redGhost;
    }

    public Spirit getBlueGhost() {
        return blueGhost;
    }

    public synchronized void loadMap() {
        gridPanel.removeAll();
        gridPanel.setLayout(new GridLayout(mapka.length, mapka[0].length));
        for (int i = 0; i < mapka.length; i++) {
            for (int j = 0; j < mapka[i].length; j++) {
                if (mapka[i][j] == 1) {
                    Bounds bound = new Bounds(j * size, i * size, size);
                    mapa[i][j] = bound;
                    gridPanel.add(bound);
                } else if (i == pacman.y && j == pacman.x) {
                    mapa[i][j] = pacman;
                    gridPanel.add(pacman);
                } else if (i == greenGhost.y && j == greenGhost.x) {
                    mapa[i][j] = greenGhost;
                    gridPanel.add(greenGhost);
                } else if (i == orangeGhost.y && j == orangeGhost.x) {
                    mapa[i][j] = orangeGhost;
                    gridPanel.add(orangeGhost);
                } else if (i == redGhost.y && j == redGhost.x) {
                    mapa[i][j] = redGhost;
                    gridPanel.add(redGhost);
                } else if (i == blueGhost.y && j == blueGhost.x) {
                    mapa[i][j] = blueGhost;
                    gridPanel.add(blueGhost);
                } else {
                    Coins coin = new Coins((gridPanel.getWidth() / map_width), (gridPanel.getHeight() / map_length), size, j, i, this);
                    mapa[i][j] = coin;
                    gridPanel.add(coin);
                }
            }
        }
        load = false;
    }


    public synchronized void updateLifeDisplay() {
        SwingUtilities.invokeLater(() -> {
            life.removeAll();
            for (int i = 0; i < pacman.life; i++) {
                ImageIcon heartImage = new ImageIcon("Assets/heart1.png");
                Image scaledImage = heartImage.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                JLabel lifeLabel = new JLabel(new ImageIcon(scaledImage));
                life.add(lifeLabel);
            }
            life.revalidate();
            life.repaint();
        });
    }

}
