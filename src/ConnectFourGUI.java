/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #10
 * 1 - 5026231108 - M Raihan Hassan
 * 2 - 5026231014 - Missy Tiffaini Novlensia Sinaga
 * 3 - 5026231026 - Azzahra Amalia Arfin
 */

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ConnectFourGUI extends JFrame {
    private static final int ROWS = 6;  // Jumlah baris
    private static final int COLS = 7;  // Jumlah kolom
    private JButton[][] buttons;  // Matriks untuk tombol
    private int[][] board;  // Matriks untuk papan permainan
    private int currentPlayer;  // Pemain saat ini (1 untuk merah, 2 untuk kuning)
    private JLabel statusLabel;  // Label status permainan

    private void playBackgroundMusic() {
        try {
            File bgmFile = new File("src/pop-beat-62044.wav"); // Path to your BGM file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bgmFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Loop the music continuously
            clip.start(); // Start playing
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    private void clickSound() {
        try {
            File clickSoundFile = new File("src/pop-94319.wav"); // Path to your click sound file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(clickSoundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();  // Play the click sound
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
    public ConnectFourGUI() {
        setTitle("Connect Four");
        setSize(700, 600);  // Ukuran jendela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        playBackgroundMusic();
        buttons = new JButton[ROWS][COLS];
        board = new int[ROWS][COLS];
        currentPlayer = 1;  // Pemain 1 (Merah) dimulai

        // Inisialisasi papan dan tombol
        JPanel panel = new JPanel(new GridLayout(ROWS, COLS));
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 24));
                buttons[row][col].setBackground(Color.WHITE);
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                panel.add(buttons[row][col]);
            }
        }
        add(panel, BorderLayout.CENTER);

        // Status label untuk menampilkan giliran pemain
        statusLabel = new JLabel("Player 1's Turn (Red)", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        add(statusLabel, BorderLayout.NORTH);

        // Reset papan saat game pertama dimulai
        resetBoard();
    }

    // ActionListener untuk menangani klik tombol
    private class ButtonClickListener implements ActionListener {
        private int row, col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Cari baris terbuka di kolom yang dipilih
            for (int i = ROWS - 1; i >= 0; i--) {
                if (board[i][col] == 0) {
                    clickSound();
                    board[i][col] = currentPlayer;
                    buttons[i][col].setBackground(currentPlayer == 1 ? Color.RED : Color.BLUE);
                    break;

                }
            }

            // Periksa kondisi kemenangan
            if (checkWin()) {
                JOptionPane.showMessageDialog(ConnectFourGUI.this, "Player " + currentPlayer + " Wins!");
                resetBoard();
            } else {
                // Ganti pemain
                currentPlayer = (currentPlayer == 1) ? 2 : 1;
                statusLabel.setText("Player " + currentPlayer + "'s Turn (" + (currentPlayer == 1 ? "Red" : "Yellow") + ")");
            }
        }
    }

    // Method untuk memeriksa kondisi kemenangan
    private boolean checkWin() {
        // Periksa horizontal, vertikal, dan diagonal
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] != 0) {
                    if (checkDirection(row, col, 1, 0) ||  // Horizontal
                            checkDirection(row, col, 0, 1) ||  // Vertical
                            checkDirection(row, col, 1, 1) ||  // Diagonal /
                            checkDirection(row, col, 1, -1)) { // Diagonal \
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // Method untuk memeriksa arah tertentu (horizontal, vertikal, diagonal)
    private boolean checkDirection(int row, int col, int dRow, int dCol) {
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r >= 0 && r < ROWS && c >= 0 && c < COLS && board[r][c] == currentPlayer) {
                count++;
            } else {
                break;
            }
        }
        return count == 4;
    }

    // Method untuk mereset papan dan memulai permainan baru
    private void resetBoard() {
        // Reset papan
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = 0;
                buttons[i][j].setBackground(Color.WHITE);
            }
        }
        currentPlayer = 1;
        statusLabel.setText("Player 1's Turn (Red)");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ConnectFourGUI game = new ConnectFourGUI();
            game.setVisible(true);
        });
    }
}
