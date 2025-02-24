import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Block {
    private char id;  // Nama blok puzzle (A-Z)
    private List<int[]> shape;  // Array (row, col), offset dari origin 0,0

    public char getID() {
        return id;
    }

    public List<int[]> getShape() {
        return shape;
    }

    public Block(char id, List<int[]> shape) {
        this.id = id;
        this.shape = new ArrayList<>(shape);
    }

    /*** Transformative Functions ***/
    // Rotate 90 degrees clockwise
    public Block rotate() {
        List<int[]> rotateShape = new ArrayList<>();
        // Formula: (r,c)→(c,−r)
        for (int[] pos : shape) {
            int newRow = pos[1];
            int newCol = -pos[0];
            rotateShape.add(new int[]{newRow, newCol});
        }
        return new Block(id, rotateShape);
    }

    // Mirror block horizontally
    public Block mirrorHorizontal() {
        List<int[]> mirrorShape = new ArrayList<>();
        // Formula: (r,c)→(r,−c)
        for (int[] pos : shape) {
            int newRow = pos[0];
            int newCol = -pos[1];
            mirrorShape.add(new int[]{newRow, newCol});
        }
        return new Block(id, mirrorShape);
    }

    // Mirror block vertically
    public Block mirrorVertical() {
        List<int[]> mirrorShape = new ArrayList<>();
        // Formula: (r,c)→(-r,c)
        for (int[] pos : shape) {
            int newRow = -pos[0];
            int newCol = pos[1];
            mirrorShape.add(new int[]{newRow, newCol});
        }
        return new Block(id, mirrorShape);
    }

    // Normalize coordinates to (0,0)
    public static List<int[]> normalizeShape(List<int[]> shape) {
        int minRow = Integer.MAX_VALUE;
        int minCol = Integer.MAX_VALUE;

        // Find min row/col
        for (int[] pos : shape) {
            minRow = Math.min(minRow, pos[0]);
            minCol = Math.min(minCol, pos[1]);
        }

        // Shift coordinate
        List<int[]> normalizedShape = new ArrayList<>();
        for (int[] pos : shape) {
            normalizedShape.add(new int[]{pos[0] - minRow, pos[1] - minCol});
        }

        return normalizedShape;
    }
    
    // Find if shapes are equal
    private static boolean isShapeEqual(List<int[]> shape1, List<int[]> shape2) {
        if (shape1.size() != shape2.size()) return false;

        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        for (int[] pos : shape1) {
            set1.add(pos[0] + "," + pos[1]);  // Store coordinates in set as "row,col"
        }
        for (int[] pos : shape2) {
            set2.add(pos[0] + "," + pos[1]);
        }

        return set1.equals(set2);  // Fast O(1) comparison
    }

    // Find if a shape is already in a block to avoid duplicates
    private static boolean isShapeInBlock(List<Block> source, Block target) {
        for (Block b : source) {
            if (isShapeEqual(b.getShape(), target.getShape())) {
                return true;
            }
        }
        return false;
    }

    // Generate all possible orientation of blocks
    public static List<Block> generateAllOrientation(Block block) {
        List<Block> uniqueOrientation = new ArrayList<>();
        Block currentShape = block;

        // Generate All Rotation
        for (int i = 0; i < 4; i++) {
            if (!isShapeInBlock(uniqueOrientation, currentShape)){
                uniqueOrientation.add(currentShape);
            }
            currentShape = currentShape.rotate();
            currentShape = new Block(block.id, normalizeShape(currentShape.getShape()));
        }

        // Generate Horizontal Mirror
        currentShape = block.mirrorHorizontal();
        currentShape = new Block(block.id, normalizeShape(currentShape.getShape()));
        for (int i = 0; i < 4; i++) {
            if (!isShapeInBlock(uniqueOrientation, currentShape)){
                uniqueOrientation.add(currentShape);
            }
            currentShape = currentShape.rotate();
            currentShape = new Block(block.id, normalizeShape(currentShape.getShape()));
        }

        // Generate Vertical Mirror
        currentShape = block.mirrorVertical();
        currentShape = new Block(block.id, normalizeShape(currentShape.getShape()));
        for (int i = 0; i < 4; i++) {
            if (!isShapeInBlock(uniqueOrientation, currentShape)){
                uniqueOrientation.add(currentShape);
            }
            currentShape = currentShape.rotate();
            currentShape = new Block(block.id, normalizeShape(currentShape.getShape()));
        }

        return uniqueOrientation;
    }

    // Print block
    public void printShape() {
        System.out.println("Block " + id + ":");
        for (int[] pos : shape) {
            System.out.println("(" + pos[0] + ", " + pos[1] + ")");
        }
    }
}