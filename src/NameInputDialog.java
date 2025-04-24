import javax.swing.*;

public class NameInputDialog {

    public static String getPlayerName() {
        String playerName = null;
        while (playerName == null || playerName.trim().isEmpty()) {
            playerName = JOptionPane.showInputDialog(null, "Podaj nick:", "Koniec gry", JOptionPane.PLAIN_MESSAGE);
            if (playerName == null) {
                JOptionPane.showMessageDialog(null, "Podaj prosze nick");
            }
        }
        return playerName;
    }
}
