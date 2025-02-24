import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Solve {
    public boolean solvePuzzle(char[][] board, List<Block> blocks, int blockIndex, int[] stepsTaken) {
        // Base case: All blocks placed, return true
        if (blockIndex == blocks.size()) {
            return Board.isBoardFull(board);
        }
    
        Block currentBlock = blocks.get(blockIndex);
        List<Block> orientations = Block.generateAllOrientation(currentBlock);
    
        // Try placing all block positions on the board
        for (int i = 0; i < board.length; i++) { 
            for (int j = 0; j < board[0].length; j++) {
                for (Block orientation : orientations) {

                    stepsTaken[0]++;

                    if (Board.canPlaceBlock(board, orientation.getShape(), i, j)) {
                        Board.placeBlock(board, orientation.getShape(), i, j, orientation.getID());  // place the block with the ID of char A-Z
                        
                        // Recursion call to place next block
                        if (solvePuzzle(board, blocks, blockIndex + 1, stepsTaken)) {
                            return true;
                        } else {

                        // Backtrack if placing next block fail
                        Board.removeBlock(board, orientation.getShape(), i, j);
                        }
                    }
                }
            }
        }
    
        // No valid placement found for current block
        return false;
    }

    public static void saveSolution(char[][] board) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Apakah anda ingin menyimpan solusi? (Y/N): ");
        String choice = scanner.nextLine().trim().toLowerCase();

        if (!choice.equals("y")) {
            return;
        }
        

        // Format output file name
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmmss_ddMMyyyy");
        String timestamp = time.format(timeFormat);
        String filePath = "../test/output/solusi_" + timestamp + ".txt";
        
        try (FileWriter writer = new FileWriter(filePath)) {
            for (char[] row : board) {
                for (char cell : row) {
                    writer.write(cell);
                }
                writer.write("\n");
            }
            System.out.println("Solusi tersimpan dengan nama: solusi_" + timestamp + ".txt");
        } catch (IOException e) {
            System.out.println("Gagal menyimpan solusi.");
        }
    }

}
