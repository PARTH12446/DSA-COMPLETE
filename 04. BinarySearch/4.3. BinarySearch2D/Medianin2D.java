/*
 * PROBLEM: Median of a Row-wise Sorted Matrix (Coding Ninjas / LeetCode variant)
 * 
 * Given a row-wise sorted matrix of size m x n, where each row is sorted
 * in non-decreasing order, find the median of the matrix.
 * 
 * CONSTRAINTS:
 * - m, n are odd? (In this problem, m*n is odd for unique median)
 * - 1 ≤ m, n ≤ 1000
 * - matrix[i][j] are integers
 * 
 * MEDIAN DEFINITION:
 * - For odd number of elements: The middle element when sorted
 * - For even number of elements: Average of two middle elements
 * 
 * APPROACH: Binary Search on Answer Space
 * 
 * INTUITION:
 * - Median is the (m*n + 1)/2-th smallest element in sorted order
 * - We can guess a number 'mid' and count how many elements ≤ mid
 * - If count < required → mid is too small (increase it)
 * - If count ≥ required → mid might be too large or just right (decrease it)
 * 
 * KEY INSIGHTS:
 * 1. Minimum possible answer = minimum of first column (smallest element)
 * 2. Maximum possible answer = maximum of last column (largest element)
 * 3. For each row, we can count elements ≤ mid using binary search
 * 4. Since rows are sorted, we can use upperBound to count efficiently
 * 
 * TIME COMPLEXITY: O(m * log(n) * log(max-min))
 *   - Outer binary search: O(log(max-min))
 *   - For each mid: O(m * log(n)) for counting
 * 
 * SPACE COMPLEXITY: O(1)
 *   - Only constant extra space used
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Flatten and sort: O(m*n log(m*n)) time, O(m*n) space
 * 2. Merge k sorted arrays using heap: O(m*n log m) time
 * 3. Binary search approach (implemented) is most efficient
 * 
 * EXAMPLE:
 * Matrix: 
 * [1, 3, 5]
 * [2, 6, 9]
 * [3, 6, 9]
 * 
 * Total elements = 9, median is 5th smallest when sorted
 * Sorted elements: 1, 2, 3, 3, 5, 6, 6, 9, 9
 * Median (5th element) = 5
 */

public class Medianin2D {

    /**
     * Main function to find the median of a row-wise sorted matrix.
     * Assumes m * n is odd for simplicity (no need to handle even case).
     *
     * @param matrix Row-wise sorted 2D matrix
     * @param m      Number of rows
     * @param n      Number of columns
     * @return Median value of the matrix
     */
    public static int median(int[][] matrix, int m, int n) {
        // Step 1: Find the search space bounds
        int minimum = Integer.MAX_VALUE;
        int maximum = Integer.MIN_VALUE;
        
        // Since each row is sorted:
        // - Minimum element is in first column
        // - Maximum element is in last column
        for (int i = 0; i < m; i++) {
            minimum = Math.min(minimum, matrix[i][0]);
            maximum = Math.max(maximum, matrix[i][n - 1]);
        }
        
        // For odd number of elements, median is the ((m*n + 1)/2)-th smallest
        int requiredPosition = (m * n + 1) / 2;
        
        // Step 2: Binary search for the smallest number with count ≥ requiredPosition
        while (minimum < maximum) {
            int mid = minimum + (maximum - minimum) / 2;
            
            // Count how many elements ≤ mid in the entire matrix
            int count = 0;
            for (int i = 0; i < m; i++) {
                // For each row, count elements ≤ mid
                count += upperBound(matrix[i], n, mid);
            }
            
            // Binary search decision
            if (count < requiredPosition) {
                // Too few elements ≤ mid → median must be larger
                minimum = mid + 1;
            } else {
                // Enough elements ≤ mid → median might be mid or smaller
                maximum = mid;
            }
        }
        
        // At the end, minimum == maximum and it's our median
        return minimum;
    }

    /**
     * Helper function: Returns count of elements ≤ x in a sorted array.
     * Equivalent to index of first element > x (which is the count of ≤ x).
     *
     * @param arr Sorted array
     * @param n   Length of array
     * @param x   Target value
     * @return Number of elements ≤ x
     * 
     * BINARY SEARCH LOGIC:
     * - We're finding the insertion point for x+1
     * - All elements before this index are ≤ x
     * - Implementation finds first element > x, returns its index
     */
    private static int upperBound(int[] arr, int n, int x) {
        int low = 0;
        int high = n - 1;
        int firstGreaterIndex = n;  // If all elements ≤ x, return n
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] <= x) {
                // Current element ≤ x, so first greater is to the right
                low = mid + 1;
            } else {
                // Current element > x, it's a candidate for first greater
                firstGreaterIndex = mid;
                high = mid - 1;  // Search left for earlier greater element
            }
        }
        
        // firstGreaterIndex is the count of elements ≤ x
        return firstGreaterIndex;
    }
    
    /**
     * Alternative: Binary search to find first element > x
     * Returns count of elements ≤ x directly
     */
    private static int countLessEqual(int[] arr, int n, int x) {
        int low = 0, high = n - 1;
        int count = 0;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= x) {
                count = mid + 1;  // All elements up to mid are ≤ x
                low = mid + 1;    // Check right for more
            } else {
                high = mid - 1;   // Check left
            }
        }
        return count;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Standard case (odd number of elements)
        int[][] matrix1 = {
            {1, 3, 5},
            {2, 6, 9},
            {3, 6, 9}
        };
        System.out.println("Test 1 - Median: " + median(matrix1, 3, 3)); // Expected: 5
        
        // Test case 2: Single row
        int[][] matrix2 = {
            {1, 3, 8, 10, 12}
        };
        System.out.println("Test 2 - Median: " + median(matrix2, 1, 5)); // Expected: 8
        
        // Test case 3: Single column
        int[][] matrix3 = {
            {2},
            {4},
            {6},
            {8},
            {10}
        };
        System.out.println("Test 3 - Median: " + median(matrix3, 5, 1)); // Expected: 6
        
        // Test case 4: All same elements
        int[][] matrix4 = {
            {5, 5, 5},
            {5, 5, 5},
            {5, 5, 5}
        };
        System.out.println("Test 4 - Median: " + median(matrix4, 3, 3)); // Expected: 5
        
        // Test case 5: LeetCode example
        int[][] matrix5 = {
            {1, 3, 5},
            {2, 3, 4},
            {1, 6, 9}
        };
        System.out.println("Test 5 - Median: " + median(matrix5, 3, 3)); // Expected: 3
    }
    
    /**
     * BONUS: Function to handle even number of elements case
     * For even m*n, median = average of two middle elements
     */
    public static double medianEvenCase(int[][] matrix, int m, int n) {
        int minimum = Integer.MAX_VALUE;
        int maximum = Integer.MIN_VALUE;
        
        for (int i = 0; i < m; i++) {
            minimum = Math.min(minimum, matrix[i][0]);
            maximum = Math.max(maximum, matrix[i][n - 1]);
        }
        
        int total = m * n;
        
        // Binary search to find (total/2)-th smallest
        while (minimum < maximum) {
            int mid = minimum + (maximum - minimum) / 2;
            int count = 0;
            
            for (int i = 0; i < m; i++) {
                count += upperBound(matrix[i], n, mid);
            }
            
            if (count < total / 2) {
                minimum = mid + 1;
            } else {
                maximum = mid;
            }
        }
        
        // For even case, we need both (total/2)-th and (total/2 + 1)-th
        // This simplified version returns one of them
        // Full implementation would need to find both elements
        return minimum;
    }
}