import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Input input = new Input();
        Input.ParsedData parsedData;

        try {
            parsedData = input.readFile();
            if (parsedData == null) {
                System.out.println("Failed to read input data.");
                return;
            }
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
            return;
        }

        // Initialize data
        int rows = parsedData.rows;
        int cols = parsedData.cols;
        List<Block> blocks = parsedData.blocks;
        Board board = new Board(rows, cols);

        // Track time and steps taken
        long startTime = System.nanoTime();
        int[] stepsTaken = {0};

        Solve solver = new Solve();
        boolean solved = solver.solvePuzzle(board.getBoard(), blocks, 0, stepsTaken);
        long endTime = System.nanoTime();

        // Print results
        if (solved) {
            System.out.println("Solusi:");
            board.printBoard();
            System.out.println();
            System.out.println("Waktu pencarian: " + (endTime - startTime) / 1_000_000.0 + " (ms)");
            System.out.println();
            System.out.println("Banyak kasus yang ditinjau: " + stepsTaken[0]);
            System.out.println();
            Solve.saveSolution(board.getBoard());
        } else {
            System.out.println("Tidak ada solusi.");
            System.out.println();
            System.out.println("Waktu pencarian: " + (endTime - startTime) / 1_000_000.0 + " (ms)");
            System.out.println();
            System.out.println("Banyak kasus yang ditinjau: " + stepsTaken[0]);
        }
    }
}