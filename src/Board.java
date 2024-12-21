/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2024/2025
 * Group Capstone Project
 * Group #10
 * 1 - 5026231108 - M Raihan Hassan
 * 2 - 5026231014 - Missy Tiffaini Novlensia Sinaga
 * 3 - 5026231026 - Azzahra Amalia Arfin
 */
public class Board {
    public static final int ROWS = 3;
    public static final int COLS = 3;

    private Cell[][] cells;

    public Board() {
        cells = new Cell[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cells[i][j] = new Cell(i, j);  // Setiap sel mendapatkan posisi (i, j)
            }
        }
    }

    // Metode untuk mereset papan permainan
    public void newGame() {
        clearBoard();  // Mengosongkan papan untuk permainan baru
    }

    // Mengosongkan papan permainan
    public void clearBoard() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                cells[i][j].setContent(Seed.NO_SEED);  // Reset semua sel ke keadaan awal
            }
        }
    }

    // Mendapatkan sel tertentu berdasarkan baris dan kolom
    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    // Langkah permainan, setelah pemain membuat gerakan
    public State stepGame(Seed currentPlayer, int row, int col) {
        cells[row][col].setContent(currentPlayer);  // Memperbarui sel dengan simbol pemain

        // Cek apakah ada pemenang atau permainan berakhir seri
        if (checkWin(currentPlayer)) {
            return currentPlayer == Seed.CROSS ? State.CROSS_WON : State.NOUGHT_WON;
        } else if (isBoardFull()) {
            return State.DRAW;
        }

        return State.PLAYING;
    }

    // Cek apakah pemain saat ini menang
    private boolean checkWin(Seed currentPlayer) {
        // Cek baris, kolom, dan diagonal untuk kondisi menang
        for (int i = 0; i < ROWS; i++) {
            if (cells[i][0].getContent() == currentPlayer &&
                    cells[i][1].getContent() == currentPlayer &&
                    cells[i][2].getContent() == currentPlayer) {
                return true;
            }
            if (cells[0][i].getContent() == currentPlayer &&
                    cells[1][i].getContent() == currentPlayer &&
                    cells[2][i].getContent() == currentPlayer) {
                return true;
            }
        }
        // Cek diagonal
        if (cells[0][0].getContent() == currentPlayer &&
                cells[1][1].getContent() == currentPlayer &&
                cells[2][2].getContent() == currentPlayer) {
            return true;
        }
        if (cells[0][2].getContent() == currentPlayer &&
                cells[1][1].getContent() == currentPlayer &&
                cells[2][0].getContent() == currentPlayer) {
            return true;
        }
        return false;
    }

    // Cek apakah papan sudah penuh (untuk kondisi draw)
    private boolean isBoardFull() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (cells[i][j].getContent() == Seed.NO_SEED) {
                    return false;
                }
            }
        }
        return true;
    }
}
