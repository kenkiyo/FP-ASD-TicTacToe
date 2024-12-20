/**
 * The Cell class models each individual cell of the TTT 3x3 grid.
 */
public class Cell {  // save as "Cell.java"
    // Define properties (package-visible)
    /** Content of this cell (CROSS, NOUGHT, NO_SEED) */
    private Seed content;
    /** Row and column of this cell */
    private int row, col;

    /** Constructor to initialize this cell */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.content = Seed.NO_SEED;  // Default content for a new game is NO_SEED
    }

    /** Reset the cell content to NO_SEED, ready for a new game. */
    public void newGame() {
        this.content = Seed.NO_SEED;  // Reset content to no seed
    }

    /** The cell paints itself (prints its content) */
    public void paint() {
        // Retrieve the display icon (text) and print
        String icon = this.content.getIcon();  // Get the symbol of the content (X, O, or empty)
        System.out.print(icon);  // Print the symbol
    }

    /** Getter for the cell content */
    public Seed getContent() {
        return content;
    }

    /** Setter for the cell content */
    public void setContent(Seed content) {
        this.content = content;
    }

    /** Getter for the row of the cell */
    public int getRow() {
        return row;
    }

    /** Getter for the column of the cell */
    public int getCol() {
        return col;
    }

    /** Override toString for easier display of the cell's state */
    @Override
    public String toString() {
        return content.getIcon();  // Return the icon associated with the content of the cell
    }
}
