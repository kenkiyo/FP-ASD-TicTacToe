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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class TicTacToeGUI extends JFrame {
    private static final int SIZE = 3;  // 3x3 grid
    private JButton[][] buttons;
    private Board board;
    private Seed currentPlayer;
    private State currentState;

    // Variables for keeping track of the score
    private int crossWins = 0;
    private int noughtWins = 0;
    private int draws = 0;
    private JLabel scoreLabel;  // Label to display the score

    // Player names
    private String playerXName = "Player X";
    private String playerOName = "Player O";

    private boolean isVsComputer = false;  // Flag to track if it's vs computer mode
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

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setSize(400, 400);  // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());  // Use BorderLayout to position the score at the top

        buttons = new JButton[SIZE][SIZE];  // Create a 3x3 grid of buttons
        board = new Board();  // Initialize the board
        currentPlayer = Seed.CROSS;  // CROSS plays first
        currentState = State.PLAYING;  // Game is ongoing

        playBackgroundMusic();

        // Prompt for game mode before player names
        promptForGameMode();

        // Initialize the score label and add it to the top
        scoreLabel = new JLabel(playerXName + " (X): " + crossWins + " | " + playerOName + " (O): " + noughtWins + " | Draws: " + draws);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(scoreLabel, BorderLayout.NORTH);

        // Initialize buttons and add them to the grid
        JPanel panel = new JPanel(new GridLayout(SIZE, SIZE));  // Create a GridLayout for buttons
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));  // Big font for X and O
                buttons[row][col].setFocusPainted(false);
                buttons[row][col].setEnabled(true);  // Make buttons clickable
                buttons[row][col].addActionListener(new ButtonClickListener(row, col)); // Use ButtonClickListener with row and col
                panel.add(buttons[row][col]);
            }
        }
        add(panel, BorderLayout.CENTER);  // Add buttons to the center of the window
    }

    // Method to prompt for game mode
    private void promptForGameMode() {
        String[] options = {"Player vs Player", "Player vs Computer"};
        int choice = JOptionPane.showOptionDialog(this, "Choose Game Mode", "Game Mode",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            // Player vs Player
            isVsComputer = false;  // Set flag to false for player vs player mode
        } else if (choice == 1) {
            // Player vs Computer
            isVsComputer = true;  // Set flag to true for computer mode
        }

        // Prompt for player names after selecting game mode
        promptForPlayerNames();
    }

    // ActionListener for handling button clicks
    public class ButtonClickListener implements ActionListener {
        private int row, col;  // Store the row and column of the button clicked

        // Constructor accepting row and col
        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Only make a move if the button is not already clicked
            if (buttons[row][col].getText().equals("") && currentState == State.PLAYING) {
                // Play click sound effect
                clickSound();

                // Set the button's text to the current player's symbol
                buttons[row][col].setText(currentPlayer == Seed.CROSS ? "X" : "O");

                // After making a move, update the board
                currentState = board.stepGame(currentPlayer, row, col);

                // Check for game state
                if (currentState == State.CROSS_WON) {
                    highlightWinningCells(Seed.CROSS);
                    JOptionPane.showMessageDialog(TicTacToeGUI.this, playerXName + " ('X') won!");
                    updateScore();
                    resetBoard();
                } else if (currentState == State.NOUGHT_WON) {
                    highlightWinningCells(Seed.NOUGHT);
                    JOptionPane.showMessageDialog(TicTacToeGUI.this, playerOName + " ('O') won!");
                    updateScore();
                    resetBoard();
                } else if (currentState == State.DRAW) {
                    JOptionPane.showMessageDialog(TicTacToeGUI.this, "It's a draw!");
                    updateScore();
                    resetBoard();
                } else {
                    // Switch current player
                    currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;

                    // If it's VS Computer mode and the current player is O (Computer), make a move for the computer
                    if (isVsComputer && currentPlayer == Seed.NOUGHT && currentState == State.PLAYING) {
                        SwingUtilities.invokeLater(() -> makeComputerMove());  // Use invokeLater for better UI response
                    }
                }
            }
        }
    }

    // Method to make a computer move
    private void makeComputerMove() {
        // Add small delay to make computer move more natural
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Simple logic for computer to pick the first available spot
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (buttons[row][col].getText().equals("")) {
                    // Play sound for computer move too
                    clickSound();

                    // Update button and board
                    buttons[row][col].setText("O");
                    currentState = board.stepGame(Seed.NOUGHT, row, col);

                    // Check for game state after computer move
                    if (currentState == State.NOUGHT_WON) {
                        highlightWinningCells(Seed.NOUGHT);
                        JOptionPane.showMessageDialog(TicTacToeGUI.this, playerOName + " ('O') won!");
                        updateScore();
                        resetBoard();
                    } else if (currentState == State.DRAW) {
                        JOptionPane.showMessageDialog(TicTacToeGUI.this, "It's a draw!");
                        updateScore();
                        resetBoard();
                    } else {
                        currentPlayer = Seed.CROSS;  // Switch back to Player X
                    }
                    return;
                }
            }
        }
    }

    // Highlight the winning cells
    private void highlightWinningCells(Seed winner) {
        // Iterate through the board and highlight the winning row/column
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board.getCell(i, j).getContent() == winner) {
                    buttons[i][j].setBackground(Color.GREEN);  // Highlight winning cells
                }
            }
        }
    }

    // Method to update the score after each game
    private void updateScore() {
        if (currentState == State.CROSS_WON) {
            crossWins++;
        } else if (currentState == State.NOUGHT_WON) {
            noughtWins++;
        } else if (currentState == State.DRAW) {
            draws++;
        }

        // Update the score label
        scoreLabel.setText(playerXName + " (X): " + crossWins + " | " + playerOName + " (O): " + noughtWins + " | Draws: " + draws);
    }

    // Method to reset the board and game state
    private void resetBoard() {
        // Clear all button texts and re-enable them
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                buttons[i][j].setText("");  // Clear the text (X or O)
                buttons[i][j].setEnabled(true);  // Re-enable the button
                buttons[i][j].setBackground(null);  // Reset background color
            }
        }
        // Reset the board game state
        board.newGame();  // Reset the game state on the board
        currentPlayer = Seed.CROSS;  // CROSS plays first
        currentState = State.PLAYING;  // Game is ongoing
    }

    // Method to play the click sound effect
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

    // Method to prompt for player names
    private void promptForPlayerNames() {
        playerXName = JOptionPane.showInputDialog("Enter name for Player X: ");
        if (playerXName == null || playerXName.trim().isEmpty()) {
            playerXName = "Player X";  // Default name if nothing is entered
        }

        if (!isVsComputer) {
            playerOName = JOptionPane.showInputDialog("Enter name for Player O: ");
            if (playerOName == null || playerOName.trim().isEmpty()) {
                playerOName = "Player O";  // Default name if nothing is entered
            }
        } else {
            playerOName = "Computer";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TicTacToeGUI game = new TicTacToeGUI();
            game.setVisible(true);  // Show the GUI
        });
    }
}