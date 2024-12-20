import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public TicTacToeGUI() {
        setTitle("Tic Tac Toe");
        setSize(400, 400);  // Window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());  // Use BorderLayout to position the score at the top

        buttons = new JButton[SIZE][SIZE];  // Create a 3x3 grid of buttons
        board = new Board();  // Initialize the board
        currentPlayer = Seed.CROSS;  // CROSS plays first
        currentState = State.PLAYING;  // Game is ongoing

        // Prompt for player names at the start
        promptForPlayerNames();

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

    // Method to prompt for player names
    private void promptForPlayerNames() {
        playerXName = JOptionPane.showInputDialog("Enter name for Player X: ");
        playerOName = JOptionPane.showInputDialog("Enter name for Player O: ");
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
            if (buttons[row][col].getText().equals("")) {
                // Set the button's text to the current player's symbol
                buttons[row][col].setText(currentPlayer == Seed.CROSS ? "X" : "O");

                // After making a move, update the board
                currentState = board.stepGame(currentPlayer, row, col);

                // Check for game state
                if (currentState == State.CROSS_WON) {
                    highlightWinningCells(Seed.CROSS);  // Highlight the winning cells for X
                    JOptionPane.showMessageDialog(TicTacToeGUI.this, playerXName + " ('X') won!");
                } else if (currentState == State.NOUGHT_WON) {
                    highlightWinningCells(Seed.NOUGHT);  // Highlight the winning cells for O
                    JOptionPane.showMessageDialog(TicTacToeGUI.this, playerOName + " ('O') won!");
                } else if (currentState == State.DRAW) {
                    JOptionPane.showMessageDialog(TicTacToeGUI.this, "It's a draw!");
                }

                // If the game is over, update score and reset the board
                if (currentState != State.PLAYING) {
                    updateScore();
                    resetBoard();
                }

                // Switch current player
                currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
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
            }
        }
        // Reset the board game state
        board.newGame();  // Reset the game state on the board
        currentPlayer = Seed.CROSS;  // CROSS plays first
        currentState = State.PLAYING;  // Game is ongoing
    }

    public static void main(String[] args) {
        TicTacToeGUI game = new TicTacToeGUI();
        game.setVisible(true);  // Show the GUI
    }
}
