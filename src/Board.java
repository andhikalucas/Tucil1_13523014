import java.util.Arrays;
import java.util.List;

public class Board {
    private int rows, cols;
    private char[][] board;

    
    public int getRowLength() {
        return this.rows;
    }

    public int getColLength() {
        return this.cols;
    }

    public char[][] getBoard() {
        return this.board;
    }
    
    public Board(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new char[rows][cols];

        // Set empty space on board with '.'
        for (int i = 0; i < this.rows; i++) {
            Arrays.fill(this.board[i], '.');
        }
    }
    
    public void printBoard() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                System.out.print(getColor(board[i][j]) + board[i][j] + "\u001B[0m ");
            }
            System.out.println();
        }
    }

    /*** Helper function for brute force algorithm ***/
    public static boolean canPlaceBlock(char[][] board, List<int[]> orientation, int row, int col) {
        for (int[] coordinate : orientation) {
            int x = coordinate[0] + col;
            int y = coordinate[1] + row;

            // Check if out of bounds or overlap with existing blocks
            if (x < 0 || y < 0 || x >= board[0].length || y >= board.length || board[y][x] != '.') {
                return false;
            }
        }
        return true;
        }

    public static void placeBlock(char[][] board, List<int[]> orientation, int row, int col, char blockID) {
        for (int[] coordinate : orientation) {
            int x = coordinate[0] + col;
            int y = coordinate[1] + row;
            board[y][x] = blockID;  // Place the block on the board
        }
    }

    public static void removeBlock(char[][] board, List<int[]> orientation, int row, int col) {
        for (int[] coordinate : orientation) {
            int x = coordinate[0] + col;
            int y = coordinate[1] + row;
            board[y][x] = '.';  // Reset the cell to . (empty)
        }
    }

    /*** OTHER UTILS ***/
    public static String getColor(char blockID) {
        int ascii = blockID;  // Convert A-Z to ASCII (65-90)
        int r = (ascii * 47) % 256;  // Prime numbers help spread colors
        int g = (ascii * 83) % 256;
        int b = (ascii * 191) % 256;
        
        return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);  // Return ANSI RGB code
    }

    public static boolean isBoardFull(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == '.') {
                    return false;
                }
            }
        }
        return true;
    }
    
}
