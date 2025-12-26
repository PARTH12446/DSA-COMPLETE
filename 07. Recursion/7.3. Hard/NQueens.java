import java.util.ArrayList;
import java.util.List;

/**
 * N-Queens Problem
 * 
 * Problem: Place N queens on an N×N chessboard such that no two queens 
 * threaten each other. A queen can attack horizontally, vertically, and diagonally.
 * 
 * This is a classic backtracking problem with applications in:
 * - Constraint satisfaction problems
 * - Resource allocation
 * - Parallel processing scheduling
 * - VLSI chip design
 * - DNA sequencing
 */
public class NQueens {

    /**
     * Main method to solve N-Queens problem.
     * 
     * @param n Size of chessboard (n×n)
     * @return List of all distinct solutions where each solution is a list of strings
     *         representing board configurations with 'Q' for queen and '.' for empty
     * 
     * Time Complexity: O(n!) in practice (much better than naive O(n^n))
     * Space Complexity: O(n²) for board + O(n) for tracking arrays + recursion stack
     */
    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        
        // Validate input
        if (n <= 0) {
            System.out.println("Board size must be positive");
            return res;
        }
        if (n == 2 || n == 3) {
            System.out.println("No solution exists for n = " + n);
            // Still return empty list (no solutions possible)
        }
        
        // Initialize empty board
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        
        // Tracking arrays for efficient constraint checking
        boolean[] col = new boolean[n];          // Track occupied columns
        boolean[] diag1 = new boolean[2 * n];    // Track main diagonals (r-c)
        boolean[] diag2 = new boolean[2 * n];    // Track anti-diagonals (r+c)
        
        backtrack(0, n, board, col, diag1, diag2, res);
        return res;
    }

    /**
     * Recursive backtracking function to place queens row by row.
     * 
     * Key Insight: Since queens attack each other in same row, we can place
     * exactly one queen per row. This reduces search space from n^n to n!.
     * 
     * @param r    Current row being processed (0-indexed)
     * @param n    Board size
     * @param board Current board state
     * @param col   Column occupancy tracker
     * @param d1    Main diagonal occupancy tracker
     * @param d2    Anti-diagonal occupancy tracker
     * @param res   Result list to collect solutions
     */
    private static void backtrack(int r, int n, char[][] board, 
                                  boolean[] col, boolean[] d1, boolean[] d2, 
                                  List<List<String>> res) {
        // Base case: All queens placed successfully
        if (r == n) {
            res.add(constructSolution(board));
            return;
        }
        
        // Try placing queen in each column of current row
        for (int c = 0; c < n; c++) {
            // Calculate diagonal indices
            // Main diagonal: r - c (shifted by n to avoid negative indices)
            int diag1Idx = r - c + n;
            // Anti-diagonal: r + c
            int diag2Idx = r + c;
            
            // Check if position is safe
            if (col[c] || d1[diag1Idx] || d2[diag2Idx]) {
                continue; // Position attacked, try next column
            }
            
            // Place queen
            board[r][c] = 'Q';
            col[c] = true;
            d1[diag1Idx] = true;
            d2[diag2Idx] = true;
            
            // Recur to next row
            backtrack(r + 1, n, board, col, d1, d2, res);
            
            // Backtrack: remove queen
            board[r][c] = '.';
            col[c] = false;
            d1[diag1Idx] = false;
            d2[diag2Idx] = false;
        }
    }
    
    /**
     * Convert board to solution format.
     */
    private static List<String> constructSolution(char[][] board) {
        List<String> solution = new ArrayList<>();
        for (char[] row : board) {
            solution.add(new String(row));
        }
        return solution;
    }

    /**
     * Alternative implementation: Count solutions without storing boards.
     * More memory efficient when only number of solutions is needed.
     * 
     * @param n Board size
     * @return Number of distinct solutions
     */
    public static int countNQueens(int n) {
        if (n <= 0) return 0;
        
        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n];
        boolean[] diag2 = new boolean[2 * n];
        
        return countBacktrack(0, n, col, diag1, diag2);
    }
    
    private static int countBacktrack(int r, int n, boolean[] col, 
                                     boolean[] d1, boolean[] d2) {
        if (r == n) {
            return 1; // Found a valid solution
        }
        
        int count = 0;
        for (int c = 0; c < n; c++) {
            int id1 = r - c + n;
            int id2 = r + c;
            
            if (col[c] || d1[id1] || d2[id2]) continue;
            
            col[c] = d1[id1] = d2[id2] = true;
            count += countBacktrack(r + 1, n, col, d1, d2);
            col[c] = d1[id1] = d2[id2] = false;
        }
        return count;
    }

    /**
     * Optimized version with bitmasking for faster operations.
     * Uses integers as bitmasks for column and diagonal tracking.
     * 
     * @param n Board size
     * @return Number of solutions
     */
    public static int solveNQueensBitmask(int n) {
        if (n < 0 || n > 32) return 0; // Limited by 32-bit integers
        
        return bitmaskBacktrack(0, 0, 0, 0, n);
    }
    
    private static int bitmaskBacktrack(int row, int cols, int diag1, int diag2, int n) {
        if (row == n) {
            return 1;
        }
        
        int count = 0;
        
        // Create mask of available positions
        // cols, diag1, diag2 have 1 in positions that are attacked
        int available = (~(cols | diag1 | diag2)) & ((1 << n) - 1);
        
        while (available != 0) {
            // Get least significant 1-bit (next available position)
            int pos = available & -available;
            
            // Remove this position from available
            available &= available - 1;
            
            // Recurse with updated masks
            count += bitmaskBacktrack(
                row + 1,
                cols | pos,
                (diag1 | pos) << 1,
                (diag2 | pos) >> 1,
                n
            );
        }
        
        return count;
    }

    /**
     * Find and print first solution only.
     * More efficient when only one solution is needed.
     * 
     * @param n Board size
     * @return First solution found, or empty list if none exists
     */
    public static List<String> findFirstSolution(int n) {
        if (n <= 0) return new ArrayList<>();
        
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        
        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n];
        boolean[] diag2 = new boolean[2 * n];
        
        if (findFirstBacktrack(0, n, board, col, diag1, diag2)) {
            return constructSolution(board);
        }
        return new ArrayList<>();
    }
    
    private static boolean findFirstBacktrack(int r, int n, char[][] board,
                                             boolean[] col, boolean[] d1, boolean[] d2) {
        if (r == n) {
            return true;
        }
        
        for (int c = 0; c < n; c++) {
            int id1 = r - c + n;
            int id2 = r + c;
            
            if (col[c] || d1[id1] || d2[id2]) continue;
            
            board[r][c] = 'Q';
            col[c] = d1[id1] = d2[id2] = true;
            
            if (findFirstBacktrack(r + 1, n, board, col, d1, d2)) {
                return true;
            }
            
            board[r][c] = '.';
            col[c] = d1[id1] = d2[id2] = false;
        }
        return false;
    }

    /**
     * Comprehensive test driver.
     */
    public static void main(String[] args) {
        System.out.println("=== N-QUEENS PROBLEM DEMO ===\n");
        
        testBasicSolutions();
        testCountSolutions();
        testFirstSolution();
        testPerformanceComparison();
        testVisualization();
    }
    
    private static void testBasicSolutions() {
        System.out.println("1. BASIC SOLUTIONS FOR DIFFERENT BOARD SIZES");
        
        for (int n = 1; n <= 6; n++) {
            List<List<String>> solutions = solveNQueens(n);
            System.out.println("\nn = " + n + ": " + solutions.size() + " solutions");
            
            if (n <= 4) {
                // Print all solutions for small boards
                for (int i = 0; i < solutions.size(); i++) {
                    System.out.println("Solution " + (i + 1) + ":");
                    printBoard(solutions.get(i));
                }
            } else if (solutions.size() > 0) {
                // Just print first solution for larger boards
                System.out.println("First solution:");
                printBoard(solutions.get(0));
            }
        }
    }
    
    private static void testCountSolutions() {
        System.out.println("\n\n2. COUNTING SOLUTIONS (KNOWN RESULTS)");
        System.out.println("n  | Standard Count | Bitmask Count");
        System.out.println("---+----------------+--------------");
        
        int[] knownCounts = {1, 0, 0, 2, 10, 4, 40, 92, 352, 724};
        
        for (int n = 1; n <= 8; n++) {
            int count1 = countNQueens(n);
            int count2 = solveNQueensBitmask(n);
            System.out.printf("%-2d | %-14d | %-13d", n, count1, count2);
            if (n <= knownCounts.length) {
                System.out.printf(" (Expected: %d)", knownCounts[n-1]);
            }
            System.out.println();
        }
    }
    
    private static void testFirstSolution() {
        System.out.println("\n\n3. FINDING FIRST SOLUTION ONLY");
        
        for (int n = 4; n <= 8; n++) {
            List<String> solution = findFirstSolution(n);
            System.out.println("\nn = " + n + " (First solution):");
            if (!solution.isEmpty()) {
                printBoard(solution);
            } else {
                System.out.println("No solution found");
            }
        }
    }
    
    private static void testPerformanceComparison() {
        System.out.println("\n\n4. PERFORMANCE COMPARISON");
        
        int[] testSizes = {8, 10, 12};
        
        for (int n : testSizes) {
            System.out.println("\nn = " + n);
            
            // Standard approach
            long start = System.nanoTime();
            int count1 = countNQueens(n);
            long time1 = System.nanoTime() - start;
            
            // Bitmask approach
            start = System.nanoTime();
            int count2 = solveNQueensBitmask(n);
            long time2 = System.nanoTime() - start;
            
            System.out.printf("Standard:  %d solutions, %8.3f ms\n", 
                            count1, time1 / 1_000_000.0);
            System.out.printf("Bitmask:   %d solutions, %8.3f ms\n", 
                            count2, time2 / 1_000_000.0);
            System.out.printf("Speedup:   %.2fx\n", (double)time1 / time2);
        }
    }
    
    private static void testVisualization() {
        System.out.println("\n\n5. VISUALIZING DIAGONAL INDICES");
        System.out.println("For n=4 board, diagonal indices:");
        System.out.println("\nMain diagonals (r - c + n):");
        System.out.println("   c0 c1 c2 c3");
        for (int r = 0; r < 4; r++) {
            System.out.print("r" + r + " ");
            for (int c = 0; c < 4; c++) {
                System.out.printf("%2d ", r - c + 4);
            }
            System.out.println();
        }
        
        System.out.println("\nAnti-diagonals (r + c):");
        System.out.println("   c0 c1 c2 c3");
        for (int r = 0; r < 4; r++) {
            System.out.print("r" + r + " ");
            for (int c = 0; c < 4; c++) {
                System.out.printf("%2d ", r + c);
            }
            System.out.println();
        }
    }
    
    /**
     * Print board in a readable format.
     */
    private static void printBoard(List<String> board) {
        if (board.isEmpty()) return;
        
        int n = board.size();
        System.out.println("+" + "---".repeat(n) + "+");
        
        for (String row : board) {
            System.out.print("|");
            for (int j = 0; j < n; j++) {
                System.out.print(" " + row.charAt(j) + " ");
            }
            System.out.println("|");
        }
        
        System.out.println("+" + "---".repeat(n) + "+");
    }
    
    /**
     * Utility: Check if a given board configuration is valid.
     */
    public static boolean isValidSolution(List<String> board) {
        int n = board.size();
        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n];
        boolean[] diag2 = new boolean[2 * n];
        
        for (int r = 0; r < n; r++) {
            String row = board.get(r);
            for (int c = 0; c < n; c++) {
                if (row.charAt(c) == 'Q') {
                    int id1 = r - c + n;
                    int id2 = r + c;
                    
                    if (col[c] || diag1[id1] || diag2[id2]) {
                        return false;
                    }
                    
                    col[c] = true;
                    diag1[id1] = true;
                    diag2[id2] = true;
                }
            }
        }
        return true;
    }
    
    /**
     * Generate all solutions with symmetry reduction.
     * Reduces search space by eliminating symmetric duplicates.
     */
    public static List<List<String>> solveNQueensSymmetry(int n) {
        List<List<String>> res = new ArrayList<>();
        if (n <= 0) return res;
        
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }
        
        boolean[] col = new boolean[n];
        boolean[] diag1 = new boolean[2 * n];
        boolean[] diag2 = new boolean[2 * n];
        
        // Only try first half of columns for first row (symmetry)
        int limit = (n % 2 == 0) ? n/2 : (n+1)/2;
        for (int c = 0; c < limit; c++) {
            board[0][c] = 'Q';
            col[c] = true;
            diag1[-c + n] = true; // r=0, so r-c = -c
            diag2[c] = true;      // r=0, so r+c = c
            
            symmetryBacktrack(1, n, board, col, diag1, diag2, res, c);
            
            board[0][c] = '.';
            col[c] = false;
            diag1[-c + n] = false;
            diag2[c] = false;
        }
        
        return res;
    }
    
    private static void symmetryBacktrack(int r, int n, char[][] board,
                                         boolean[] col, boolean[] d1, boolean[] d2,
                                         List<List<String>> res, int firstCol) {
        if (r == n) {
            res.add(constructSolution(board));
            // Add mirror solution if not symmetric
            if (firstCol < n/2) {
                // Create mirror board
                char[][] mirror = new char[n][n];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        mirror[i][j] = board[i][n-1-j];
                    }
                }
                res.add(constructSolution(mirror));
            }
            return;
        }
        
        for (int c = 0; c < n; c++) {
            int id1 = r - c + n;
            int id2 = r + c;
            
            if (col[c] || d1[id1] || d2[id2]) continue;
            
            board[r][c] = 'Q';
            col[c] = d1[id1] = d2[id2] = true;
            symmetryBacktrack(r + 1, n, board, col, d1, d2, res, firstCol);
            board[r][c] = '.';
            col[c] = d1[id1] = d2[id2] = false;
        }
    }
}