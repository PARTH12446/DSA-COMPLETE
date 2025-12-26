import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Sudoku Solver
 * 
 * Problem: Fill a 9×9 Sudoku grid so that each row, each column, 
 * and each of the nine 3×3 subgrids contain all digits from 1 to 9.
 * 
 * This is a classic constraint satisfaction problem that can be solved
 * using backtracking with constraint propagation.
 * 
 * Applications:
 * - Puzzle solving
 * - Constraint programming
 * - AI planning
 * - Resource allocation problems
 */
public class Sudoku {

    /**
     * Main method to solve Sudoku puzzle.
     * 
     * @param board 9×9 Sudoku board with '.' representing empty cells
     * @return true if puzzle is solvable, false otherwise
     * 
     * Time Complexity: O(9^(n)) where n is number of empty cells (worst case)
     *                 but much better in practice due to pruning
     * Space Complexity: O(1) extra space, O(81) recursion stack
     */
    public static boolean solveSudoku(char[][] board) {
        validateBoard(board);
        return solve(board);
    }

    /**
     * Recursive backtracking solver.
     * Uses Most Constrained Variable (MCV) heuristic for optimization.
     * 
     * Strategy:
     * 1. Find empty cell with fewest possible values (MCV heuristic)
     * 2. Try each possible value
     * 3. Recurse
     * 4. Backtrack if no value works
     * 
     * @param board Current board state
     * @return true if solution found
     */
    private static boolean solve(char[][] board) {
        int[] cell = findEmptyCell(board);
        
        // No empty cells - puzzle solved
        if (cell[0] == -1) {
            return true;
        }
        
        int row = cell[0];
        int col = cell[1];
        
        // Get possible values for this cell
        Set<Character> possibleValues = getPossibleValues(board, row, col);
        
        // Try each possible value
        for (char num : possibleValues) {
            // Place number
            board[row][col] = num;
            
            // Recurse
            if (solve(board)) {
                return true;
            }
            
            // Backtrack
            board[row][col] = '.';
        }
        
        return false;
    }

    /**
     * Find empty cell with fewest possible values (Most Constrained Variable).
     * Returns [-1, -1] if no empty cells.
     */
    private static int[] findEmptyCell(char[][] board) {
        int minPossible = 10; // More than maximum (9)
        int[] bestCell = new int[]{-1, -1};
        
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    Set<Character> possible = getPossibleValues(board, r, c);
                    int count = possible.size();
                    
                    // If cell has no possible values, puzzle is unsolvable
                    if (count == 0) {
                        return new int[]{r, c}; // Will cause backtrack
                    }
                    
                    // Prefer cell with fewest options
                    if (count < minPossible) {
                        minPossible = count;
                        bestCell[0] = r;
                        bestCell[1] = c;
                        
                        // If we found a cell with only 1 possibility, return immediately
                        if (minPossible == 1) {
                            return bestCell;
                        }
                    }
                }
            }
        }
        
        return bestCell;
    }

    /**
     * Get all possible values for a given cell.
     * Uses constraint propagation to eliminate invalid values.
     */
    private static Set<Character> getPossibleValues(char[][] board, int row, int col) {
        Set<Character> possible = new HashSet<>();
        for (char ch = '1'; ch <= '9'; ch++) {
            possible.add(ch);
        }
        
        // Remove values already in row
        for (int c = 0; c < 9; c++) {
            possible.remove(board[row][c]);
        }
        
        // Remove values already in column
        for (int r = 0; r < 9; r++) {
            possible.remove(board[r][col]);
        }
        
        // Remove values already in 3×3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                possible.remove(board[r][c]);
            }
        }
        
        return possible;
    }

    /**
     * Check if placing a digit at given position is valid.
     * Optimized version for quick validation.
     */
    private static boolean isValid(char[][] board, int row, int col, char ch) {
        // Check row
        for (int c = 0; c < 9; c++) {
            if (board[row][c] == ch) {
                return false;
            }
        }
        
        // Check column
        for (int r = 0; r < 9; r++) {
            if (board[r][col] == ch) {
                return false;
            }
        }
        
        // Check 3×3 box
        int boxRow = (row / 3) * 3;
        int boxCol = (col / 3) * 3;
        for (int r = boxRow; r < boxRow + 3; r++) {
            for (int c = boxCol; c < boxCol + 3; c++) {
                if (board[r][c] == ch) {
                    return false;
                }
            }
        }
        
        return true;
    }

    /**
     * Validate Sudoku board format and initial state.
     * 
     * Rules:
     * 1. Board must be 9×9
     * 2. Only digits 1-9 and '.' allowed
     * 3. No duplicates in rows, columns, or boxes
     */
    private static void validateBoard(char[][] board) {
        if (board == null || board.length != 9) {
            throw new IllegalArgumentException("Board must be 9×9");
        }
        
        for (int i = 0; i < 9; i++) {
            if (board[i] == null || board[i].length != 9) {
                throw new IllegalArgumentException("Board must be 9×9");
            }
            
            for (int j = 0; j < 9; j++) {
                char ch = board[i][j];
                if (ch != '.' && (ch < '1' || ch > '9')) {
                    throw new IllegalArgumentException("Invalid character at (" + i + "," + j + "): " + ch);
                }
            }
        }
        
        // Check for initial conflicts
        if (!isBoardValid(board)) {
            throw new IllegalArgumentException("Initial board has conflicts");
        }
    }

    /**
     * Check if current board state is valid (no conflicts).
     * 
     * @param board Current board
     * @return true if board is valid
     */
    public static boolean isBoardValid(char[][] board) {
        // Check rows
        for (int r = 0; r < 9; r++) {
            boolean[] seen = new boolean[9];
            for (int c = 0; c < 9; c++) {
                char ch = board[r][c];
                if (ch != '.') {
                    int num = ch - '1';
                    if (seen[num]) {
                        return false; // Duplicate in row
                    }
                    seen[num] = true;
                }
            }
        }
        
        // Check columns
        for (int c = 0; c < 9; c++) {
            boolean[] seen = new boolean[9];
            for (int r = 0; r < 9; r++) {
                char ch = board[r][c];
                if (ch != '.') {
                    int num = ch - '1';
                    if (seen[num]) {
                        return false; // Duplicate in column
                    }
                    seen[num] = true;
                }
            }
        }
        
        // Check 3×3 boxes
        for (int box = 0; box < 9; box++) {
            boolean[] seen = new boolean[9];
            int boxRow = (box / 3) * 3;
            int boxCol = (box % 3) * 3;
            
            for (int r = boxRow; r < boxRow + 3; r++) {
                for (int c = boxCol; c < boxCol + 3; c++) {
                    char ch = board[r][c];
                    if (ch != '.') {
                        int num = ch - '1';
                        if (seen[num]) {
                            return false; // Duplicate in box
                        }
                        seen[num] = true;
                    }
                }
            }
        }
        
        return true;
    }

    /**
     * Count number of solutions (for puzzles with multiple solutions).
     * 
     * @param board Sudoku board
     * @return Number of valid solutions
     */
    public static int countSolutions(char[][] board) {
        char[][] copy = copyBoard(board);
        return countSolutionsRecursive(copy, 0);
    }
    
    private static int countSolutionsRecursive(char[][] board, int count) {
        int[] cell = findEmptyCell(board);
        
        // No empty cells - found a solution
        if (cell[0] == -1) {
            return count + 1;
        }
        
        int row = cell[0];
        int col = cell[1];
        Set<Character> possible = getPossibleValues(board, row, col);
        
        // If no possible values, this branch is dead
        if (possible.isEmpty()) {
            return count;
        }
        
        for (char num : possible) {
            board[row][col] = num;
            count = countSolutionsRecursive(board, count);
            board[row][col] = '.';
            
            // Limit to prevent infinite recursion for invalid puzzles
            if (count > 1000) {
                return count; // Too many solutions
            }
        }
        
        return count;
    }

    /**
     * Generate a random Sudoku puzzle.
     * 
     * @param difficulty Number of empty cells (17-81)
     * @return Valid Sudoku board
     */
    public static char[][] generatePuzzle(int difficulty) {
        if (difficulty < 17 || difficulty > 81) {
            throw new IllegalArgumentException("Difficulty must be between 17 and 81");
        }
        
        // Start with empty board
        char[][] board = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = '.';
            }
        }
        
        // Fill diagonal boxes first (they are independent)
        fillDiagonalBoxes(board);
        
        // Solve the partially filled board
        solve(board);
        
        // Remove numbers to create puzzle
        removeNumbers(board, difficulty);
        
        return board;
    }
    
    private static void fillDiagonalBoxes(char[][] board) {
        for (int box = 0; box < 3; box++) {
            int startRow = box * 3;
            int startCol = box * 3;
            
            // Fill 3×3 box with random permutation of 1-9
            List<Character> numbers = new ArrayList<>();
            for (char ch = '1'; ch <= '9'; ch++) {
                numbers.add(ch);
            }
            java.util.Collections.shuffle(numbers);
            
            int index = 0;
            for (int r = startRow; r < startRow + 3; r++) {
                for (int c = startCol; c < startCol + 3; c++) {
                    board[r][c] = numbers.get(index++);
                }
            }
        }
    }
    
    private static void removeNumbers(char[][] board, int targetEmpty) {
        int currentEmpty = 0;
        java.util.Random rand = new java.util.Random();
        
        while (currentEmpty < targetEmpty) {
            int r = rand.nextInt(9);
            int c = rand.nextInt(9);
            
            if (board[r][c] != '.') {
                char backup = board[r][c];
                board[r][c] = '.';
                
                // Check if puzzle still has unique solution
                char[][] copy = copyBoard(board);
                int solutions = countSolutions(copy);
                
                if (solutions == 1) {
                    currentEmpty++;
                } else {
                    // Restore number
                    board[r][c] = backup;
                }
            }
        }
    }

    /**
     * Print Sudoku board in readable format.
     */
    public static void printBoard(char[][] board) {
        System.out.println("+-------+-------+-------+");
        for (int r = 0; r < 9; r++) {
            System.out.print("| ");
            for (int c = 0; c < 9; c++) {
                System.out.print(board[r][c] + " ");
                if (c == 2 || c == 5) {
                    System.out.print("| ");
                }
            }
            System.out.println("|");
            if (r == 2 || r == 5 || r == 8) {
                System.out.println("+-------+-------+-------+");
            }
        }
    }

    /**
     * Copy board to prevent mutation.
     */
    private static char[][] copyBoard(char[][] board) {
        char[][] copy = new char[9][9];
        for (int i = 0; i < 9; i++) {
            System.arraycopy(board[i], 0, copy[i], 0, 9);
        }
        return copy;
    }

    /**
     * Check if puzzle is solved correctly.
     */
    public static boolean isSolved(char[][] board) {
        // Check all cells filled
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    return false;
                }
            }
        }
        
        // Check validity
        return isBoardValid(board);
    }

    /**
     * Alternative solver using dancing links algorithm (Algorithm X).
     * More efficient for very hard puzzles.
     */
    public static boolean solveWithDancingLinks(char[][] board) {
        // Implementation of Donald Knuth's Algorithm X
        // This is a placeholder - actual implementation is complex
        return solve(board); // Fallback to backtracking
    }

    /**
     * Comprehensive test driver.
     */
    public static void main(String[] args) {
        System.out.println("=== SUDOKU SOLVER DEMO ===\n");
        
        testEasyPuzzle();
        testHardPuzzle();
        testInvalidPuzzle();
        testSolutionCounting();
        testPuzzleGeneration();
        testPerformance();
    }
    
    private static void testEasyPuzzle() {
        System.out.println("1. EASY PUZZLE");
        char[][] board = {
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        
        System.out.println("Initial board:");
        printBoard(board);
        
        if (solveSudoku(board)) {
            System.out.println("\nSolved board:");
            printBoard(board);
            System.out.println("Is solved correctly: " + isSolved(board));
        } else {
            System.out.println("No solution exists");
        }
        System.out.println();
    }
    
    private static void testHardPuzzle() {
        System.out.println("2. HARD PUZZLE (World's hardest?)");
        char[][] board = {
            {'8', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '3', '6', '.', '.', '.', '.', '.'},
            {'.', '7', '.', '.', '9', '.', '2', '.', '.'},
            {'.', '5', '.', '.', '.', '7', '.', '.', '.'},
            {'.', '.', '.', '.', '4', '5', '7', '.', '.'},
            {'.', '.', '.', '1', '.', '.', '.', '3', '.'},
            {'.', '.', '1', '.', '.', '.', '.', '6', '8'},
            {'.', '.', '8', '5', '.', '.', '.', '1', '.'},
            {'.', '9', '.', '.', '.', '.', '4', '.', '.'}
        };
        
        System.out.println("Initial board (World's hardest Sudoku):");
        printBoard(board);
        
        long start = System.nanoTime();
        boolean solved = solveSudoku(board);
        long time = System.nanoTime() - start;
        
        if (solved) {
            System.out.println("\nSolved in " + (time/1000000.0) + " ms");
            System.out.println("Solved board:");
            printBoard(board);
        } else {
            System.out.println("No solution found");
        }
        System.out.println();
    }
    
    private static void testInvalidPuzzle() {
        System.out.println("3. INVALID PUZZLE (With conflicts)");
        char[][] board = {
            {'5', '5', '.', '.', '7', '.', '.', '.', '.'}, // Duplicate 5 in row 0
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        
        System.out.println("Initial board (with conflict):");
        printBoard(board);
        
        try {
            boolean solved = solveSudoku(board);
            System.out.println("Solved: " + solved);
        } catch (IllegalArgumentException e) {
            System.out.println("Error caught: " + e.getMessage());
        }
        System.out.println();
    }
    
    private static void testSolutionCounting() {
        System.out.println("4. SOLUTION COUNTING");
        
        // Empty board has many solutions
        char[][] emptyBoard = new char[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                emptyBoard[i][j] = '.';
            }
        }
        
        // Count solutions (will be very large number)
        System.out.println("Counting solutions for empty board...");
        int solutions = countSolutions(emptyBoard);
        System.out.println("Number of solutions (limited to 1000): " + solutions);
        
        // Well-formed puzzle should have exactly 1 solution
        char[][] validPuzzle = {
            {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '3', '.', '8', '5'},
            {'.', '.', '1', '.', '2', '.', '.', '.', '.'},
            {'.', '.', '.', '5', '.', '7', '.', '.', '.'},
            {'.', '.', '4', '.', '.', '.', '1', '.', '.'},
            {'.', '9', '.', '.', '.', '.', '.', '.', '.'},
            {'5', '.', '.', '.', '.', '.', '.', '7', '3'},
            {'.', '.', '2', '.', '1', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '4', '.', '.', '.', '9'}
        };
        
        solutions = countSolutions(validPuzzle);
        System.out.println("Number of solutions for valid puzzle: " + solutions);
        System.out.println();
    }
    
    private static void testPuzzleGeneration() {
        System.out.println("5. PUZZLE GENERATION");
        
        System.out.println("Generating puzzle with 30 empty cells...");
        char[][] puzzle = generatePuzzle(30);
        
        System.out.println("Generated puzzle:");
        printBoard(puzzle);
        
        // Verify it's solvable
        char[][] copy = copyBoard(puzzle);
        if (solveSudoku(copy)) {
            System.out.println("\nPuzzle is solvable");
            System.out.println("\nSolution:");
            printBoard(copy);
        }
        System.out.println();
    }
    
    private static void testPerformance() {
        System.out.println("6. PERFORMANCE COMPARISON");
        
        char[][] hardPuzzle = {
            {'.', '.', '.', '.', '.', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '.', '3', '.', '8', '5'},
            {'.', '.', '1', '.', '2', '.', '.', '.', '.'},
            {'.', '.', '.', '5', '.', '7', '.', '.', '.'},
            {'.', '.', '4', '.', '.', '.', '1', '.', '.'},
            {'.', '9', '.', '.', '.', '.', '.', '.', '.'},
            {'5', '.', '.', '.', '.', '.', '.', '7', '3'},
            {'.', '.', '2', '.', '1', '.', '.', '.', '.'},
            {'.', '.', '.', '.', '4', '.', '.', '.', '9'}
        };
        
        // Test with MCV heuristic
        char[][] copy1 = copyBoard(hardPuzzle);
        long start = System.nanoTime();
        boolean solved1 = solve(copy1);
        long time1 = System.nanoTime() - start;
        
        // Test naive backtracking (original approach)
        char[][] copy2 = copyBoard(hardPuzzle);
        start = System.nanoTime();
        boolean solved2 = naiveSolve(copy2);
        long time2 = System.nanoTime() - start;
        
        System.out.println("MCV heuristic: " + (solved1 ? "solved" : "failed") + 
                         " in " + (time1/1000000.0) + " ms");
        System.out.println("Naive approach: " + (solved2 ? "solved" : "failed") + 
                         " in " + (time2/1000000.0) + " ms");
        System.out.println("Speedup: " + (time2/(double)time1) + "x");
    }
    
    /**
     * Naive backtracking solver (original approach for comparison).
     */
    private static boolean naiveSolve(char[][] board) {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                if (board[r][c] == '.') {
                    for (char ch = '1'; ch <= '9'; ch++) {
                        if (isValid(board, r, c, ch)) {
                            board[r][c] = ch;
                            if (naiveSolve(board)) {
                                return true;
                            }
                            board[r][c] = '.';
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}