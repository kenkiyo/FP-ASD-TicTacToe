import java.util.Scanner;

/**
 * The main class for the Tic-Tac-Toe (Console-OO, non-graphics version)
 * It acts as the overall controller of the game.
 */
public class TTT {
    // Define properties
    private Board board;          // The game board
    private State currentState;   // The current state of the game
    private Seed currentPlayer;   // The current player

    private static Scanner in = new Scanner(System.in);  // Scanner for user input

    // Constructor to setup the game
    public void play() {
        do {
            // Perform one-time initialization tasks
            initGame();

            // Reset the board, currentStatus, and currentPlayer for a new game
            newGame();

            // Play the game once
            do {
                // The currentPlayer makes a move.
                // Update cells[][] and currentState
                stepGame();
                // Refresh the display
                printBoard();

                // Print message if game over
                if (currentState == State.CROSS_WON) {
                    System.out.println("'X' won!");
                } else if (currentState == State.NOUGHT_WON) {
                    System.out.println("'O' won!");
                } else if (currentState == State.DRAW) {
                    System.out.println("It's Draw!");
                }

                // Switch currentPlayer
                currentPlayer = (currentPlayer == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
            } while (currentState == State.PLAYING);  // Repeat until game over

            // Prompt the user whether to play again
            System.out.print("Play again (y/n)? ");
            char ans = in.next().charAt(0);
            if (ans != 'y' && ans != 'Y') {
                System.out.println("Bye!");
                System.exit(0);  // terminate the program
            }
        } while (true);
    }

    // Perform one-time initialization tasks
    public void initGame() {
        board = new Board();  // Allocate the game board
    }

    // Reset the game-board contents and the current states, ready for a new game
    public void newGame() {
        board.clearBoard();  // Clear the board contents
        currentPlayer = Seed.CROSS;   // CROSS plays first
        currentState = State.PLAYING; // Game is ready to start
    }

    // The currentPlayer makes one move. Update cells[][] and currentState.
    public void stepGame() {
        boolean validInput = false;  // For validating input
        do {
            String icon = currentPlayer.getIcon();
            System.out.print("Player '" + icon + "', enter your move (row[1-3] column[1-3]): ");
            int row = in.nextInt() - 1;   // [0-2] (user input is 1-3, so adjust by -1)
            int col = in.nextInt() - 1;

            // Check if the move is valid
            if (row >= 0 && row < Board.ROWS && col >= 0 && col < Board.COLS
                    && board.getCell(row, col).getContent() == Seed.NO_SEED) {
                // Update cells[][] and return the new game state after the move
                currentState = board.stepGame(currentPlayer, row, col);
                validInput = true; // Input is valid, exit loop
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
        } while (!validInput);   // Repeat until input is valid
    }

    // Print the board to the console (instead of using the non-existent paint() method)
    private void printBoard() {
        for (int i = 0; i < Board.ROWS; i++) {
            for (int j = 0; j < Board.COLS; j++) {
                System.out.print(board.getCell(i, j).getContent().getIcon() + " "); // Display each cell's content
            }
            System.out.println(); // Move to the next line after each row
        }
    }
}
