/*
 * PROBLEM: Row with Maximum 1s (Coding Ninjas / LeetCode 1198 variant)
 * 
 * Given a 2D binary matrix (contains only 0s and 1s) of size n x m,
 * where each row is sorted in non-decreasing order (0s followed by 1s),
 * find the row index (0-based) that contains the maximum number of 1s.
 * 
 * If multiple rows have the same maximum number of 1s, return the
 * smallest row index. If no row contains any 1s, return -1.
 * 
 * CONSTRAINTS:
 * - 1 ≤ n, m ≤ 10^3
 * - Each row is sorted in non-decreasing order
 * - matrix[i][j] ∈ {0, 1}
 * 
 * APPROACH: Binary Search per Row
 * 
 * INTUITION:
 * - Since each row is sorted, all 1s are consecutive at the end.
 * - Number of 1s in a row = m - (index of first 1)
 * - We can find the first 1 in each row using binary search (O(log m))
 * - Track the row with maximum count of 1s.
 * 
 * KEY OBSERVATIONS:
 * 1. If a row has no 1s, firstOneIndex returns -1 → count = 0
 * 2. If row[mid] == 1, we store it as potential answer and search left
 *    to find an earlier 1 (if exists).
 * 3. We can optimize further by starting next row's search from the
 *    previous row's first 1 index (not implemented here).
 * 
 * TIME COMPLEXITY: O(n log m)
 *   - For each of n rows, binary search takes O(log m)
 *   - Optimal for constraints up to 10^3 x 10^3
 * 
 * SPACE COMPLEXITY: O(1)
 *   - Only a few integer variables used
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Linear Scan from Top-Right (Optimized O(n + m)):
 *    - Start from top-right corner
 *    - Move left while 1s found, move down when 0 found
 *    - Most efficient for this problem
 * 
 * 2. Linear Scan per Row (O(n × m)):
 *    - Count 1s in each row by scanning
 *    - Works but not optimal for large matrices
 * 
 * EXAMPLE 1:
 * Matrix:
 * [0, 1, 1, 1]
 * [0, 0, 1, 1]
 * [1, 1, 1, 1]
 * [0, 0, 0, 0]
 * 
 * Row 0: First 1 at index 1 → 4-1 = 3 ones
 * Row 1: First 1 at index 2 → 4-2 = 2 ones  
 * Row 2: First 1 at index 0 → 4-0 = 4 ones
 * Row 3: No 1s → 0 ones
 * Answer: Row 2 (index 2)
 * 
 * EXAMPLE 2:
 * Matrix:
 * [0, 0, 0, 0]
 * [0, 0, 0, 0]
 * Answer: -1 (no 1s in any row)
 */

public class Max1s {

    /**
     * Main function to find the row with maximum number of 1s.
     *
     * @param matrix 2D binary matrix (0s and 1s), each row sorted
     * @param n      Number of rows
     * @param m      Number of columns
     * @return Index of row with maximum 1s, or -1 if none
     */
    public static int rowWithMax1s(int[][] matrix, int n, int m) {
        int maxCount = 0;     // Maximum number of 1s found so far
        int ans = -1;         // Row index with maximum 1s
        
        for (int i = 0; i < n; i++) {
            int[] row = matrix[i];
            
            // Find index of first 1 in this row
            int firstOneIdx = firstOneIndex(row, m);
            
            // Calculate number of 1s in this row
            int currentCount = 0;
            if (firstOneIdx != -1) {
                // All elements from firstOneIdx to end are 1s
                currentCount = m - firstOneIdx;
            }
            
            // Update answer if this row has more 1s than current max
            // Note: For equal counts, we keep the earlier row (smaller index)
            if (currentCount > maxCount) {
                ans = i;
                maxCount = currentCount;
            }
        }
        
        return ans;
    }

    /**
     * Helper function to find the first occurrence of 1 in a sorted binary row.
     * Uses binary search for O(log m) time.
     *
     * @param row The binary array (sorted with 0s then 1s)
     * @param m   Length of the row
     * @return Index of first 1, or -1 if no 1s found
     * 
     * Binary Search Logic:
     * - We're looking for the leftmost occurrence of 1
     * - If mid == 1, it's a candidate, but check left for earlier 1
     * - If mid == 0, all left elements are also 0, search right
     */
    private static int firstOneIndex(int[] row, int m) {
        int low = 0;
        int high = m - 1;
        int firstIdx = -1;  // Will store index of first 1
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (row[mid] == 1) {
                // Found a 1, but there might be earlier ones
                firstIdx = mid;      // Store as candidate
                high = mid - 1;      // Search left half for earlier 1
            } else {
                // row[mid] == 0, so first 1 must be to the right
                low = mid + 1;       // Search right half
            }
        }
        
        return firstIdx;
    }
    
    /**
     * Alternative optimized approach: Linear scan from top-right
     * More efficient: O(n + m) time
     * 
     * @param matrix Binary matrix
     * @param n Number of rows
     * @param m Number of columns
     * @return Row index with maximum 1s
     */
    public static int rowWithMax1sOptimized(int[][] matrix, int n, int m) {
        int ans = -1;
        int col = m - 1;  // Start from top-right corner
        
        for (int row = 0; row < n; row++) {
            // Move left while we find 1s
            while (col >= 0 && matrix[row][col] == 1) {
                col--;          // Move left
                ans = row;      // Update answer (this row has at least as many 1s)
            }
        }
        
        return ans;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Standard case
        int[][] matrix1 = {
            {0, 1, 1, 1},
            {0, 0, 1, 1},
            {1, 1, 1, 1},
            {0, 0, 0, 0}
        };
        System.out.println("Test 1 (Binary Search): " + rowWithMax1s(matrix1, 4, 4)); // Expected: 2
        System.out.println("Test 1 (Optimized): " + rowWithMax1sOptimized(matrix1, 4, 4)); // Expected: 2
        
        // Test case 2: No 1s
        int[][] matrix2 = {
            {0, 0, 0},
            {0, 0, 0}
        };
        System.out.println("\nTest 2: " + rowWithMax1s(matrix2, 2, 3)); // Expected: -1
        
        // Test case 3: All 1s
        int[][] matrix3 = {
            {1, 1},
            {1, 1}
        };
        System.out.println("\nTest 3: " + rowWithMax1s(matrix3, 2, 2)); // Expected: 0 (first row)
        
        // Test case 4: Multiple rows with same max 1s
        int[][] matrix4 = {
            {0, 0, 1, 1},
            {0, 1, 1, 1},
            {0, 0, 0, 1},
            {0, 0, 1, 1}
        };
        System.out.println("\nTest 4: " + rowWithMax1s(matrix4, 4, 4)); // Expected: 1 (2 ones vs others have 2 or less)
    }
}