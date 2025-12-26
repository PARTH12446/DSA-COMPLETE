/*
 * PROBLEM: Find Peak Element in 2D Matrix (Coding Ninjas)
 * 
 * Given a 2D integer matrix 'arr' of dimensions n x m, find a peak element.
 * A peak element is defined as an element that is strictly greater than
 * all of its adjacent neighbors (up, down, left, right).
 * 
 * CONSTRAINTS:
 * - 1 ≤ n, m ≤ 1000
 * - 1 ≤ arr[i][j] ≤ 10^9
 * - For boundary elements, compare only with existing neighbors
 * - Multiple peaks may exist; return any one valid peak
 * 
 * DEFINITION:
 * Element arr[row][col] is a peak if:
 * 1. arr[row][col] > arr[row-1][col] (if row > 0)
 * 2. arr[row][col] > arr[row+1][col] (if row < n-1)
 * 3. arr[row][col] > arr[row][col-1] (if col > 0)
 * 4. arr[row][col] > arr[row][col+1] (if col < m-1)
 * 
 * APPROACH: Binary Search on Columns + Linear Scan on Rows
 * 
 * INTUITION:
 * 1. Pick middle column (mid)
 * 2. Find maximum element in that column (global max in column)
 * 3. Compare this max with its left and right neighbors
 * 4. If it's greater than both → we found a peak
 * 5. If left neighbor > current → peak must be in left half
 * 6. If right neighbor > current → peak must be in right half
 * 
 * WHY THIS WORKS:
 * - The maximum element in a column is greater than all elements above/below it
 * - We only need to compare with left/right neighbors
 * - If it's not a peak, the direction with larger neighbor must contain a peak
 * 
 * TIME COMPLEXITY: O(n log m)
 *   - Binary search on columns: O(log m)
 *   - For each mid: O(n) to find max in column
 * 
 * SPACE COMPLEXITY: O(1)
 *   - Only constant extra space used
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n × m) - Check all elements
 * 2. Divide & Conquer: O(n log m) or O(m log n) recursive approach
 * 3. Greedy Ascent: Start from random cell, move to larger neighbor
 * 
 * EXAMPLE:
 * Matrix:
 * [10, 20, 15]
 * [21, 30, 14]
 * [ 7, 16, 32]
 * 
 * Step 1: mid = 1 (column index)
 * Column 1: [20, 30, 16] → max = 30 at row=1
 * Compare: left=21, right=14, both < 30 → Peak found at [1,1]
 * 
 * EXAMPLE 2 (Boundary peak):
 * Matrix:
 * [5, 2]
 * [1, 4]
 * 
 * mid = 0 → column 0: max=5 at [0,0]
 * Compare: right=2 >? no, down=1 >? no → Peak at [0,0]
 */

public class Peak2D {

    /**
     * Main function to find a peak element in a 2D matrix.
     * Uses binary search on columns approach.
     *
     * @param arr 2D integer matrix
     * @return int array [row, col] of peak element, or [-1, -1] if not found
     * 
     * Note: The problem guarantees at least one peak exists,
     * so we'll always find one (unless matrix is empty).
     */
    public static int[] findPeakGrid(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        
        // Binary search on columns
        int low = 0;
        int high = m - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;  // Current column to examine
            
            // Step 1: Find maximum element in column 'mid'
            int maxRowIndex = -1;
            int maxValue = Integer.MIN_VALUE;
            
            for (int i = 0; i < n; i++) {
                if (arr[i][mid] > maxValue) {
                    maxValue = arr[i][mid];
                    maxRowIndex = i;
                }
            }
            
            // Step 2: Compare with left and right neighbors
            // Handle boundary cases (first/last column)
            int leftNeighbor = (mid - 1 >= 0) ? arr[maxRowIndex][mid - 1] : -1;
            int rightNeighbor = (mid + 1 < m) ? arr[maxRowIndex][mid + 1] : -1;
            
            // Step 3: Check if current element is a peak
            if (arr[maxRowIndex][mid] > leftNeighbor && 
                arr[maxRowIndex][mid] > rightNeighbor) {
                // Found a peak!
                return new int[]{maxRowIndex, mid};
            }
            
            // Step 4: If not peak, move towards larger neighbor
            else if (arr[maxRowIndex][mid] < leftNeighbor) {
                // Left neighbor is larger → peak must be in left half
                high = mid - 1;
            } else {
                // Right neighbor is larger (or equal) → peak must be in right half
                low = mid + 1;
            }
        }
        
        // Should never reach here if matrix has at least one peak
        return new int[]{-1, -1};
    }
    
    /**
     * Alternative: Divide and Conquer approach (recursive)
     * Time: O(n log m) or O(m log n) depending on implementation
     */
    public static int[] findPeakGridRecursive(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        return findPeakRecursive(arr, 0, m - 1, n, m);
    }
    
    private static int[] findPeakRecursive(int[][] arr, int low, int high, int n, int m) {
        if (low > high) return new int[]{-1, -1};
        
        int mid = low + (high - low) / 2;
        
        // Find max in column mid
        int maxRow = 0;
        int maxVal = arr[0][mid];
        for (int i = 1; i < n; i++) {
            if (arr[i][mid] > maxVal) {
                maxVal = arr[i][mid];
                maxRow = i;
            }
        }
        
        // Check neighbors
        int left = (mid > 0) ? arr[maxRow][mid - 1] : -1;
        int right = (mid < m - 1) ? arr[maxRow][mid + 1] : -1;
        
        if (maxVal >= left && maxVal >= right) {
            return new int[]{maxRow, mid};
        } else if (maxVal < left) {
            return findPeakRecursive(arr, low, mid - 1, n, m);
        } else {
            return findPeakRecursive(arr, mid + 1, high, n, m);
        }
    }
    
    /**
     * Brute force approach for verification
     * Time: O(n × m), Space: O(1)
     */
    public static int[] findPeakGridBruteForce(int[][] arr) {
        int n = arr.length;
        int m = arr[0].length;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int current = arr[i][j];
                int up = (i > 0) ? arr[i-1][j] : -1;
                int down = (i < n-1) ? arr[i+1][j] : -1;
                int left = (j > 0) ? arr[i][j-1] : -1;
                int right = (j < m-1) ? arr[i][j+1] : -1;
                
                if (current > up && current > down && current > left && current > right) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Peak in middle
        int[][] matrix1 = {
            {10, 20, 15},
            {21, 30, 14},
            {7, 16, 32}
        };
        int[] peak1 = findPeakGrid(matrix1);
        System.out.println("Test 1 - Peak at: [" + peak1[0] + ", " + peak1[1] + "]");
        System.out.println("Value: " + matrix1[peak1[0]][peak1[1]]);
        
        // Test case 2: Boundary peak (top-left)
        int[][] matrix2 = {
            {5, 2},
            {1, 4}
        };
        int[] peak2 = findPeakGrid(matrix2);
        System.out.println("\nTest 2 - Peak at: [" + peak2[0] + ", " + peak2[1] + "]");
        System.out.println("Value: " + matrix2[peak2[0]][peak2[1]]);
        
        // Test case 3: Single element matrix
        int[][] matrix3 = {
            {42}
        };
        int[] peak3 = findPeakGrid(matrix3);
        System.out.println("\nTest 3 - Peak at: [" + peak3[0] + ", " + peak3[1] + "]");
        System.out.println("Value: " + matrix3[peak3[0]][peak3[1]]);
        
        // Test case 4: All same elements (every element is a peak)
        int[][] matrix4 = {
            {5, 5, 5},
            {5, 5, 5},
            {5, 5, 5}
        };
        int[] peak4 = findPeakGrid(matrix4);
        System.out.println("\nTest 4 - Peak at: [" + peak4[0] + ", " + peak4[1] + "]");
        System.out.println("Value: " + matrix4[peak4[0]][peak4[1]]);
        
        // Test case 5: Increasing then decreasing
        int[][] matrix5 = {
            {1, 2, 3, 2, 1},
            {2, 3, 4, 3, 2},
            {1, 2, 3, 2, 1}
        };
        int[] peak5 = findPeakGrid(matrix5);
        System.out.println("\nTest 5 - Peak at: [" + peak5[0] + ", " + peak5[1] + "]");
        System.out.println("Value: " + matrix5[peak5[0]][peak5[1]]);
    }
    
    /**
     * Helper to validate if a position is actually a peak
     */
    private static boolean isValidPeak(int[][] arr, int row, int col) {
        int n = arr.length;
        int m = arr[0].length;
        int current = arr[row][col];
        
        int up = (row > 0) ? arr[row-1][col] : Integer.MIN_VALUE;
        int down = (row < n-1) ? arr[row+1][col] : Integer.MIN_VALUE;
        int left = (col > 0) ? arr[row][col-1] : Integer.MIN_VALUE;
        int right = (col < m-1) ? arr[row][col+1] : Integer.MIN_VALUE;
        
        return current > up && current > down && current > left && current > right;
    }
}