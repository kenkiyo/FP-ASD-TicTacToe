/**
 * This enum represents the possible states of a cell or a player in the game.
 * It defines three constants:
 * 1. CROSS: Represents a player marked with an "X".
 * 2. NOUGHT: Represents a player marked with an "O".
 * 3. NO_SEED: Represents an empty cell in the grid.
 *
 * Each constant also has a display icon associated with it, which is either "X", "O", or a space (" ") for no seed.
 */
public enum Seed {   // Save as "Seed.java"

    /** Represents a player's move as "X" */
    CROSS("X"),

    /** Represents a player's move as "O" */
    NOUGHT("O"),

    /** Represents an empty cell, marked as " " */
    NO_SEED(" ");

    // Private variable to hold the icon for each enum constant
    private String icon;

    /**
     * Constructor to initialize the enum constant with its corresponding icon.
     * This constructor is private to prevent instantiation outside of the enum.
     *
     * @param icon The display icon for the enum constant.
     */
    private Seed(String icon) {
        this.icon = icon;
    }

    /**
     * Getter method to retrieve the icon associated with this enum constant.
     *
     * @return The icon representing the enum constant.
     */
    public String getIcon() {
        return icon;
    }
}
