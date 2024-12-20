import javax.swing.*;
import java.awt.*;

public class GameSelector extends JFrame {

    public GameSelector() {
        setTitle("Choose a Game");
        setSize(300, 200);  // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Tampilkan pilihan game menggunakan JOptionPane
        Object[] options = {"Tic Tac Toe", "Connect Four"};
        int choice = JOptionPane.showOptionDialog(this, "Select a game to play", "Game Selector",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Periksa pilihan dan jalankan game sesuai pilihan
        if (choice == 0) {
            // Pilih Tic Tac Toe
            new TicTacToeGUI().setVisible(true);
        } else if (choice == 1) {
            // Pilih Connect Four
            new ConnectFourGUI().setVisible(true);
        }

        // Tutup GameSelector setelah memilih game
        this.dispose();
    }

    public static void main(String[] args) {
        // Menjalankan GameSelector
        SwingUtilities.invokeLater(() -> new GameSelector().setVisible(true));
    }
}
