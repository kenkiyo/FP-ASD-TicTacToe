/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #10
 * 1 - 5026231108 - M Raihan Hassan
 * 2 - 5026231014 - Missy Tiffaini Novlensia Sinaga
 * 3 - 5026231026 - Azzahra Amalia Arfin
 */

import javax.swing.*;
import java.awt.*;

public class GameSelector extends JFrame {

    public GameSelector() {
        // Tampilkan pilihan game menggunakan JOptionPane
        Object[] options = {"Tic Tac Toe", "Connect Four"};
        int choice = JOptionPane.showOptionDialog(this, "Select a game to play", "Game Selector",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        // Tutup GameSelector setelah memilih game
        this.dispose();

        // Periksa pilihan dan jalankan game sesuai pilihan
        if (choice == 0) {
            // Pilih Tic Tac Toe
            new TicTacToeGUI().setVisible(true);
        } else if (choice == 1) {
            // Pilih Connect Four
            new ConnectFourGUI().setVisible(true);
        }
    }

    public static void main(String[] args) {
        // Menjalankan GameSelector
        SwingUtilities.invokeLater(() -> new GameSelector().setVisible(true));
    }
}
