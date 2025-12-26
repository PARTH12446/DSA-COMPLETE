/*
 * PROBLEM: Search in a Sorted 2D Matrix (Coding Ninjas)
 * 
 * Given a 2D integer matrix 'matrix' of size n x m and an integer 'target',
 * determine if 'target' exists in the matrix.
 * 
 * KEY PROPERTY:
 * - Each row is sorted in non-decreasing order from left to right
 * - Each column is sorted in non-decreasing order from top to bottom
 * - This is known as a "Young Tableau" property
 * 
 * CONSTRAINTS:
 * - 1 ≤ n, m ≤ 1000
 * - -10^9 ≤ matrix[i][j], target ≤ 10^9
 * - Matrix satisfies Young Tableau property
 * 
 * APPROACH: Staircase Search (Start from Top-Right)
 * 
 * INTUITION:
 * 1. Start from top-right corner (row=0, col=m-1)
 * 2. Compare current element with target:
 *    - If equal → found!
 *    - If current < target → target must be below (increment row)
 *    - If current > target → target must be left (decrement col)
 * 3. Continue until we find target or go out of bounds
 * 
 * WHY START FROM TOP-RIGHT?
 * - From top-right, we have two choices: down (larger) or left (smaller)
 * - This gives us a clear decision at each step
 * - Alternative: Start from bottom-left works similarly
 * 
 * TIME COMPLEXITY: O(n + m)
 *   - In worst case, we traverse one full row and one full column
 *   - Maximum steps = n + m - 1
 * 
 * SPACE COMPLEXITY: O(1)
 *   - Only two pointers (row, col) needed
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Binary Search on each row: O(n log m)
 * 2. Binary Search on flattened matrix: O(log(n×m)) - but requires conversion
 * 3. Divide & Conquer: O(n^log3) - not practical
 * 
 * EXAMPLE 1 (Target Found):
 * matrix = [
 *   [1, 4, 7, 11, 15],
 *   [2, 5, 8, 12, 19],
 *   [3, 6, 9, 16, 22],
 *   [10, 13, 14, 17, 24],
 *   [18, 21, 23, 26, 30]
 * ]
 * target = 5
 * 
 * Path: Start at (0,4)=15 > 5 → left to (0,3)=11 > 5 → left to (0,2)=7 > 5 → 
 * left to (0,1)=4 < 5 → down to (1,1)=5 == 5 → Found!
 * 
 * EXAMPLE 2 (Target Not Found):
 * target = 20
 * 
 * Path: (0,4)=15 < 20 → down to (1,4)=19 < 20 → down to (2,4)=22 > 20 → 
 * left to (2,3)=16 < 20 → down to (3,3)=17 < 20 → down to (4,3)=26 > 20 → 
 * left to (4,2)=23 > 20 → left to (4,1)=21 > 20 → left to (4,0)=18 < 20 → 
 * down to (5,0) → out of bounds → Not found
 */

public class TargetSearchII {

    /**
     * Main function to search for target in a sorted 2D matrix.
     * Uses staircase search starting from top-right corner.
     *
     * @param matrix  2D integer matrix (rows and columns sorted)
     * @param target  Element to search for
     * @return 1 if target exists, 0 otherwise
     */
    public static int searchElement(int[][] matrix, int target) {
        // Handle edge case: empty matrix
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int n = matrix.length;     // Number of rows
        int m = matrix[0].length;  // Number of columns
        
        // Start from top-right corner
        int row = 0;
        int col = m - 1;
        
        // Traverse matrix in staircase pattern
        while (row < n && col >= 0) {
            if (matrix[row][col] == target) {
                return 1;  // Target found
            } else if (matrix[row][col] < target) {
                // Current element is smaller than target
                // Target must be below (in next rows)
                row++;
            } else {
                // Current element is larger than target
                // Target must be to the left (in previous columns)
                col--;
            }
        }
        
        // Target not found
        return 0;
    }
    
    /**
     * Alternative: Start from bottom-left corner
     * Same time complexity, different movement pattern
     */
    public static int searchElementBottomLeft(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int n = matrix.length;
        int m = matrix[0].length;
        
        // Start from bottom-left corner
        int row = n - 1;
        int col = 0;
        
        while (row >= 0 && col < m) {
            if (matrix[row][col] == target) {
                return 1;
            } else if (matrix[row][col] < target) {
                // Current element smaller → move right
                col++;
            } else {
                // Current element larger → move up
                row--;
            }
        }
        
        return 0;
    }
    
    /**
     * Optimized approach: Binary search on each row
     * Useful when n << m (few rows, many columns)
     * Time: O(n log m)
     */
    public static int searchElementBinaryRows(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int n = matrix.length;
        int m = matrix[0].length;
        
        for (int i = 0; i < n; i++) {
            // Check if target could be in this row
            if (matrix[i][0] <= target && target <= matrix[i][m - 1]) {
                // Perform binary search on this row
                int left = 0, right = m - 1;
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (matrix[i][mid] == target) {
                        return 1;
                    } else if (matrix[i][mid] < target) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
        }
        
        return 0;
    }
    
    /**
     * Most optimized: Binary search on flattened matrix
     * Treats matrix as 1D sorted array
     * Time: O(log(n×m)), Space: O(1)
     * Requires that matrix[i][j] ≤ matrix[i][j+1] AND matrix[i][j] ≤ matrix[i+1][j]
     */
    public static int searchElementBinaryFull(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int n = matrix.length;
        int m = matrix[0].length;
        
        int left = 0;
        int right = n * m - 1;  // Treat as 1D array
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Convert 1D index to 2D indices
            int row = mid / m;
            int col = mid % m;
            
            if (matrix[row][col] == target) {
                return 1;
            } else if (matrix[row][col] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return 0;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Standard matrix (LeetCode example)
        int[][] matrix1 = {
            {1, 4, 7, 11, 15},
            {2, 5, 8, 12, 19},
            {3, 6, 9, 16, 22},
            {10, 13, 14, 17, 24},
            {18, 21, 23, 26, 30}
        };
        
        System.out.println("=== Testing Staircase Search ===");
        System.out.println("Search 5: " + searchElement(matrix1, 5));      // Expected: 1
        System.out.println("Search 20: " + searchElement(matrix1, 20));    // Expected: 0
        System.out.println("Search 30: " + searchElement(matrix1, 30));    // Expected: 1
        System.out.println("Search 0: " + searchElement(matrix1, 0));      // Expected: 0
        System.out.println("Search 35: " + searchElement(matrix1, 35));    // Expected: 0
        
        // Test case 2: Single row matrix
        int[][] matrix2 = {{1, 3, 5, 7, 9}};
        System.out.println("\n=== Single Row Matrix ===");
        System.out.println("Search 5: " + searchElement(matrix2, 5));      // Expected: 1
        System.out.println("Search 6: " + searchElement(matrix2, 6));      // Expected: 0
        
        // Test case 3: Single column matrix
        int[][] matrix3 = {{1}, {3}, {5}, {7}, {9}};
        System.out.println("\n=== Single Column Matrix ===");
        System.out.println("Search 5: " + searchElement(matrix3, 5));      // Expected: 1
        System.out.println("Search 6: " + searchElement(matrix3, 6));      // Expected: 0
        
        // Test case 4: Empty matrix
        int[][] matrix4 = {};
        System.out.println("\n=== Empty Matrix ===");
        System.out.println("Search 5: " + searchElement(matrix4, 5));      // Expected: 0
        
        // Test alternative approaches
        System.out.println("\n=== Comparing All Approaches ===");
        int target = 9;
        System.out.println("Target: " + target);
        System.out.println("Staircase (Top-Right): " + searchElement(matrix1, target));
        System.out.println("Staircase (Bottom-Left): " + searchElementBottomLeft(matrix1, target));
        System.out.println("Binary Rows: " + searchElementBinaryRows(matrix1, target));
        System.out.println("Binary Full: " + searchElementBinaryFull(matrix1, target));
        
        // Performance comparison
        System.out.println("\n=== Performance Analysis ===");
        System.out.println("For n x m matrix:");
        System.out.println("1. Staircase Search: O(n + m)");
        System.out.println("2. Binary Search Rows: O(n log m)");
        System.out.println("3. Binary Search Full: O(log(n×m))");
        System.out.println("\nWhich to use?");
        System.out.println("- If n ≈ m: Staircase is good");
        System.out.println("- If n << m: Binary rows is good");
        System.out.println("- If fully sorted: Binary full is optimal");
    }
    
    /**
     * Visualization of the staircase search path:
     * 
     * For matrix1 searching for 5:
     * 
     * Start: (0,4)=15 > 5 → move left
     *        (0,3)=11 > 5 → move left
     *        (0,2)=7 > 5 → move left
     *        (0,1)=4 < 5 → move down
     *        (1,1)=5 == 5 → FOUND!
     * 
     * Path: → → → ← ← ↑ (metaphorically)
     */
    
    /**
     * Proof of Correctness:
     * 
     * Lemma 1: If matrix[row][col] < target, then target cannot be in
     *          any cell to the left in the same row (all are ≤ current).
     *          Target must be below (row+1).
     * 
     * Lemma 2: If matrix[row][col] > target, then target cannot be in
     *          any cell below in the same column (all are ≥ current).
     *          Target must be to the left (col-1).
     * 
     * By repeatedly applying these lemmas, we either:
     * 1. Find the target, or
     * 2. Eliminate all possibilities (go out of bounds)
     */
    
    /**
     * Common Mistakes to Avoid:
     * 1. Starting from wrong corner (top-left or bottom-right)
     *    - From top-left: both down and right are larger → no clear direction
     *    - From bottom-right: both up and left are smaller → no clear direction
     * 2. Not checking for empty matrix
     * 3. Using wrong comparison operators
     * 4. Not updating both row and col correctly in loop
     */
}