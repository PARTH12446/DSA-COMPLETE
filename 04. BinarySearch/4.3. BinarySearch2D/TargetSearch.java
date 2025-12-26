/*
 * PROBLEM: Search in a 2D Matrix (Coding Ninjas / LeetCode 74 variant)
 * 
 * Given a 2D integer matrix 'mat' of size n x m and an integer 'target',
 * determine if 'target' exists in the matrix.
 * 
 * This version assumes:
 * - No guarantee that rows are sorted (only individual rows may be sorted)
 * - No guarantee that columns are sorted
 * - We need to search each row independently
 * 
 * CONSTRAINTS:
 * - 1 ≤ n, m ≤ 1000
 * - -10^9 ≤ mat[i][j], target ≤ 10^9
 * - Each individual row may or may not be sorted
 * 
 * APPROACH 1: Linear Scan + Binary Search per Row
 * - For each row, use binary search to check if target exists
 * - This works if each row is individually sorted
 * 
 * TIME COMPLEXITY: O(n log m)
 *   - n rows, binary search on each row takes O(log m)
 *   - Worst case: target not found, check all rows
 * 
 * SPACE COMPLEXITY: O(1)
 *   - Only constant extra space used
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n × m) - Check every element
 * 2. Binary Search on Flattened Matrix: O(log(n×m)) if fully sorted
 * 3. Step-wise Linear Search: O(n + m) if both rows and columns sorted
 * 
 * NOTE: This implementation is suboptimal if the matrix is fully sorted
 * (both rows and columns sorted). For that case, a more efficient O(log(n×m))
 * or O(n + m) solution exists.
 * 
 * EXAMPLE 1:
 * mat = [
 *   [1, 3, 5, 7],
 *   [10, 11, 16, 20],
 *   [23, 30, 34, 60]
 * ]
 * target = 3
 * 
 * Row 0: Binary search finds 3 → return true
 * 
 * EXAMPLE 2:
 * mat = [
 *   [1, 3, 5, 7],
 *   [10, 11, 16, 20],
 *   [23, 30, 34, 60]
 * ]
 * target = 13
 * 
 * Row 0: Not found
 * Row 1: Not found  
 * Row 2: Not found
 * Return false
 */

public class TargetSearch {

    /**
     * Binary search helper for a single sorted row.
     * Returns 1 if target found, 0 otherwise.
     *
     * @param arr    Sorted array (individual row)
     * @param target Element to search for
     * @return 1 if found, 0 if not found
     */
    private static int searchRow(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == target) {
                return 1;  // Found
            } else if (arr[mid] < target) {
                low = mid + 1;  // Search right half
            } else {
                high = mid - 1;  // Search left half
            }
        }
        
        return 0;  // Not found
    }

    /**
     * Main function to search for target in 2D matrix.
     * Assumes each row is individually sorted.
     *
     * @param mat    2D integer matrix
     * @param target Element to search for
     * @return true if target exists in matrix, false otherwise
     */
    public static boolean searchMatrix(int[][] mat, int target) {
        // Iterate through each row
        for (int[] row : mat) {
            // Use binary search on current row
            if (searchRow(row, target) == 1) {
                return true;  // Found in this row
            }
        }
        return false;  // Not found in any row
    }
    
    /**
     * OPTIMIZED VERSION: For matrix where both rows AND columns are sorted
     * Time: O(log(n×m)) using single binary search on flattened indices
     */
    public static boolean searchMatrixOptimized(int[][] mat, int target) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return false;
        }
        
        int n = mat.length;     // Number of rows
        int m = mat[0].length;  // Number of columns
        
        int low = 0;
        int high = n * m - 1;   // Treat matrix as 1D array of size n*m
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Convert 1D index to 2D indices
            int row = mid / m;
            int col = mid % m;
            
            if (mat[row][col] == target) {
                return true;
            } else if (mat[row][col] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return false;
    }
    
    /**
     * ALTERNATIVE: Staircase Search (if both rows and columns sorted)
     * Start from top-right corner, move left or down
     * Time: O(n + m), Space: O(1)
     */
    public static boolean searchMatrixStaircase(int[][] mat, int target) {
        if (mat == null || mat.length == 0 || mat[0].length == 0) {
            return false;
        }
        
        int n = mat.length;
        int m = mat[0].length;
        
        // Start from top-right corner
        int row = 0;
        int col = m - 1;
        
        while (row < n && col >= 0) {
            if (mat[row][col] == target) {
                return true;
            } else if (mat[row][col] > target) {
                // Current element too large, move left
                col--;
            } else {
                // Current element too small, move down
                row++;
            }
        }
        
        return false;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Target exists
        int[][] matrix1 = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target1 = 3;
        System.out.println("Test 1 - Target " + target1 + " exists? " + 
                          searchMatrix(matrix1, target1));  // Expected: true
        
        // Test case 2: Target doesn't exist
        int target2 = 13;
        System.out.println("Test 2 - Target " + target2 + " exists? " + 
                          searchMatrix(matrix1, target2));  // Expected: false
        
        // Test case 3: Empty matrix
        int[][] matrix2 = {};
        int target3 = 5;
        System.out.println("Test 3 - Target " + target3 + " exists? " + 
                          searchMatrix(matrix2, target3));  // Expected: false
        
        // Test case 4: Single element matrix
        int[][] matrix3 = {{5}};
        int target4 = 5;
        System.out.println("Test 4 - Target " + target4 + " exists? " + 
                          searchMatrix(matrix3, target4));  // Expected: true
        
        // Test case 5: Unsorted rows (binary search won't work!)
        int[][] matrix4 = {
            {5, 1, 3, 2},  // Not sorted!
            {4, 6, 8, 7}   // Not sorted!
        };
        int target5 = 3;
        System.out.println("Test 5 - Target " + target5 + " exists? " + 
                          searchMatrix(matrix4, target5));  // May give incorrect result
        
        // Test optimized version
        System.out.println("\n--- Optimized Version Tests ---");
        System.out.println("Test 1 (Optimized): " + searchMatrixOptimized(matrix1, target1));  // true
        System.out.println("Test 2 (Optimized): " + searchMatrixOptimized(matrix1, target2));  // false
        System.out.println("Test 4 (Optimized): " + searchMatrixOptimized(matrix3, target4));  // true
        
        // Test staircase version
        System.out.println("\n--- Staircase Version Tests ---");
        System.out.println("Test 1 (Staircase): " + searchMatrixStaircase(matrix1, target1));  // true
        System.out.println("Test 2 (Staircase): " + searchMatrixStaircase(matrix1, target2));  // false
    }
    
    /**
     * ANALYSIS: When to use which approach?
     * 
     * 1. If ONLY rows are sorted (columns not sorted):
     *    - Use current approach: O(n log m)
     *    - Binary search each row independently
     * 
     * 2. If BOTH rows and columns are sorted:
     *    - Use optimized approach: O(log(n×m))
     *    - Treat as 1D array and binary search
     * 
     * 3. If BOTH rows and columns are sorted (alternative):
     *    - Use staircase search: O(n + m)
     *    - Better when n ≈ m
     * 
     * 4. If matrix is NOT sorted at all:
     *    - Brute force: O(n × m)
     *    - Must check every element
     * 
     * PERFORMANCE COMPARISON for n=1000, m=1000:
     * - Current approach: 1000 * log2(1000) ≈ 1000 * 10 = 10,000 operations
     * - Optimized approach: log2(1,000,000) ≈ 20 operations
     * - Staircase approach: 1000 + 1000 = 2,000 operations
     * - Brute force: 1,000,000 operations
     */
}