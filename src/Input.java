import java.io.*;
import java.util.*;

public class Input {
    private static Scanner in = new Scanner(System.in);

    public static class ParsedData {
        public int rows, cols;
        public List<Block> blocks;
        public String config;

        public ParsedData(int rows, int cols, List<Block> blocks, String config) {
            this.rows = rows;
            this.cols = cols;
            this.blocks = blocks;
            this.config = config;
        }
    }

    public ParsedData readFile() throws IOException {
        System.out.print("Masukkan nama file: ");
        String fileName = in.next();

        try (Scanner fileScanner = new Scanner(new File("../test/input/" + fileName))) {

            // Read first line N M P
            String[] firstLine = fileScanner.nextLine().split(" ");  
            int rows = Integer.parseInt(firstLine[0]);
            int cols = Integer.parseInt(firstLine[1]);
            int blockCount = Integer.parseInt(firstLine[2]);

            // Read second line "DEFAULT/CUSTOM/PYRAMID"
            String config = fileScanner.nextLine();  

            // Set map (A-Z) to read blocks
            Map<Character, List<int[]>> blockMap = new HashMap<>();  
            int rowIdx = 0;
            Character currentBlockId = null;

            // Read blocks
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                char blockId = line.charAt(0);

                // Reset row count on new block
                if (currentBlockId == null || currentBlockId != blockId) {
                    currentBlockId = blockId;
                    rowIdx = 0;
                }
                
                // Add new coordinates to the block
                for (int colIdx = 0; colIdx < line.length(); colIdx++) {
                    if (line.charAt(colIdx) == blockId) {
                        blockMap.computeIfAbsent(blockId, k -> new ArrayList<>()).add(new int[]{rowIdx, colIdx});
                    }
                }
                rowIdx++;
            }
            
            // Error if initialized block count is not the same as unique number of blocks
            if (blockMap.size() != blockCount) {
                System.out.println("Error: Expected " + blockCount + " blocks, but found " + blockMap.size());
                return null;  
            }
            
            // Convert map to blocks
            List<Block> blocks = new ArrayList<>();
            for (Map.Entry<Character, List<int[]>> entry : blockMap.entrySet()) {
                blocks.add(new Block(entry.getKey(), entry.getValue()));
            }

            return new ParsedData(rows, cols, blocks, config);

        } catch (FileNotFoundException e) {
            System.out.println("File tidak ditemukan: " + e.getMessage());
            return null;
        }
    }
}


