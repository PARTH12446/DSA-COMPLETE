// Problem: LeetCode <ID>. <Title>
// Problem: Zero Matrix (Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/zero-matrix_1171153

import java.util.HashSet;
import java.util.Set;

public class Matrix {

    /**
     * Zero Matrix using HashSet to track rows and columns to zero out
     * Time: O(n*m), Space: O(n + m)
     * 
     * @param matrix Input matrix
     * @param n Number of rows
     * @param m Number of columns
     * @return Modified matrix with rows/columns zeroed
     */
    public static int[][] zeroMatrix(int[][] matrix, int n, int m) {
        // Step 1: Track which rows and columns contain zeros
        Set<Integer> rowSet = new HashSet<>();  // Rows to zero
        Set<Integer> colSet = new HashSet<>();  // Columns to zero
        
        // First pass: find all zeros and mark their rows/columns
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 0) {
                    rowSet.add(i);
                    colSet.add(j);
                }
            }
        }
        
        // Second pass: zero out marked rows and columns
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (rowSet.contains(i) || colSet.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        return matrix;
    }
    
    /**
     * Optimized solution using first row and column as markers
     * Time: O(n*m), Space: O(1)
     */
    public static int[][] zeroMatrixOptimized(int[][] matrix, int n, int m) {
        boolean firstRowHasZero = false;
        boolean firstColHasZero = false;
        
        // Check if first row has zero
        for (int j = 0; j < m; j++) {
            if (matrix[0][j] == 0) {
                firstRowHasZero = true;
                break;
            }
        }
        
        // Check if first column has zero
        for (int i = 0; i < n; i++) {
            if (matrix[i][0] == 0) {
                firstColHasZero = true;
                break;
            }
        }
        
        // Use first row and column as markers
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;  // Mark row
                    matrix[0][j] = 0;  // Mark column
                }
            }
        }
        
        // Zero out cells based on markers
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // Zero out first row if needed
        if (firstRowHasZero) {
            for (int j = 0; j < m; j++) {
                matrix[0][j] = 0;
            }
        }
        
        // Zero out first column if needed
        if (firstColHasZero) {
            for (int i = 0; i < n; i++) {
                matrix[i][0] = 0;
            }
        }
        
        return matrix;
    }
    
    /**
     * Alternative using boolean arrays (clearer but O(n+m) space)
     */
    public static int[][] zeroMatrixBoolean(int[][] matrix, int n, int m) {
        boolean[] zeroRows = new boolean[n];
        boolean[] zeroCols = new boolean[m];
        
        // Mark rows and columns that need to be zeroed
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 0) {
                    zeroRows[i] = true;
                    zeroCols[j] = true;
                }
            }
        }
        
        // Zero out rows
        for (int i = 0; i < n; i++) {
            if (zeroRows[i]) {
                for (int j = 0; j < m; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // Zero out columns
        for (int j = 0; j < m; j++) {
            if (zeroCols[j]) {
                for (int i = 0; i < n; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        return matrix;
    }
    
    /**
     * Variation: Set zeros in-place with two passes (no extra space for non-square matrices)
     */
    public static int[][] zeroMatrixInPlace(int[][] matrix, int n, int m) {
        // This approach uses -1 as marker for cells to zero
        // Only works if matrix doesn't contain -1, or we can use other sentinel
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == 0) {
                    // Mark entire row (except zeros)
                    for (int k = 0; k < m; k++) {
                        if (matrix[i][k] != 0) {
                            matrix[i][k] = -1;  // Sentinel value
                        }
                    }
                    // Mark entire column (except zeros)
                    for (int k = 0; k < n; k++) {
                        if (matrix[k][j] != 0) {
                            matrix[k][j] = -1;
                        }
                    }
                }
            }
        }
        
        // Replace -1 with 0
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == -1) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        return matrix;
    }
    
    /**
     * Helper method to print matrix
     */
    public static void printMatrix(int[][] matrix, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Main method with test cases
     */
    public static void main(String[] args) {
        // Test Case 1: 3x3 matrix
        int[][] test1 = {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        };
        System.out.println("Test 1 - Original:");
        printMatrix(test1, 3, 3);
        
        zeroMatrix(test1, 3, 3);
        System.out.println("After zeroMatrix:");
        printMatrix(test1, 3, 3);
        System.out.println("Expected:\n1 0 1\n0 0 0\n1 0 1");
        System.out.println();
        
        // Test Case 2: 4x3 matrix
        int[][] test2 = {
            {0, 1, 2},
            {3, 4, 5},
            {6, 7, 8},
            {9, 10, 0}
        };
        System.out.println("Test 2 - Original:");
        printMatrix(test2, 4, 3);
        
        zeroMatrix(test2, 4, 3);
        System.out.println("After zeroMatrix:");
        printMatrix(test2, 4, 3);
        System.out.println("Expected:\n0 0 0\n0 4 0\n0 7 0\n0 0 0");
        System.out.println();
        
        // Test Case 3: All zeros
        int[][] test3 = {
            {0, 0},
            {0, 0}
        };
        System.out.println("Test 3 - All zeros (unchanged):");
        printMatrix(test3, 2, 2);
        System.out.println();
        
        // Test Case 4: Single element
        int[][] test4 = {{5}};
        System.out.println("Test 4 - Single element [5]:");
        printMatrix(test4, 1, 1);
        
        zeroMatrix(test4, 1, 1);
        System.out.println("After: " + test4[0][0]);
        System.out.println();
        
        // Test optimized solution
        int[][] test5 = {
            {1, 2, 3, 4},
            {5, 0, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 0}
        };
        System.out.println("Test 5 - Optimized solution:");
        printMatrix(test5, 4, 4);
        
        zeroMatrixOptimized(test5, 4, 4);
        System.out.println("After optimization:");
        printMatrix(test5, 4, 4);
    }
}

// Algorithm Analysis:
// Original Solution (HashSet):
// - Time: O(n*m) - Two passes through matrix
// - Space: O(n + m) - For storing row and column indices

// Optimized Solution (first row/col as markers):
// - Time: O(n*m) - Still two passes
// - Space: O(1) - Only two boolean variables

// Key Insight:
// The challenge is to avoid O(n*m) space while still achieving O(n*m) time
// Using first row/column as markers allows O(1) space solution

// Step-by-step (Optimized):
// 1. Check if first row/column need to be zeroed (store flags)
// 2. Use matrix[0][j] and matrix[i][0] as markers for other rows/columns
// 3. Process inner matrix (i=1..n-1, j=1..m-1)
// 4. Apply zeros based on markers
// 5. Zero first row/column if needed based on flags

// Edge Cases:
// 1. 1x1 matrix with 0 ? entire matrix becomes 0
// 2. 1x1 matrix without 0 ? unchanged
// 3. All zeros matrix ? unchanged (already all zeros)
// 4. No zeros in matrix ? unchanged
// 5. Zero in first row/column ? handled by flags

// Common Mistakes:
// 1. Modifying matrix while iterating (cascading zeros)
// 2. Not handling first row/column separately in optimized solution
// 3. Using O(n*m) space when not necessary
// 4. Forgetting to zero both rows AND columns for each zero found

// Example Walkthrough (Optimized):
// Input:
// [1, 2, 3]
// [4, 0, 6]
// [7, 8, 9]
//
// Steps:
// 1. firstRowHasZero = false, firstColHasZero = false
// 2. matrix[1][1] = 0 ? mark: matrix[1][0] = 0, matrix[0][1] = 0
// 3. Process inner cells ? cell[2][1] sees row marker ? becomes 0
// 4. Zero first row? No. Zero first col? No.
// Result:
// [1, 0, 3]
// [0, 0, 0]
// [7, 0, 9]

// Related Problems:
// 1. LeetCode 73: Set Matrix Zeroes (same problem)
// 2. Rotate Matrix (in-place 90 degree rotation)
// 3. Spiral Matrix traversal
// 4. Search in 2D Matrix

// Performance Notes:
// - For n,m up to 200, all solutions work fine
// - For very large matrices, O(1) space is crucial
// - HashSet solution is simpler but uses more memory

// Interview Tips:
// 1. Always start with brute force (O(n+m) space)
// 2. Then optimize to O(1) space using markers
// 3. Handle edge cases explicitly
// 4. Draw example to explain algorithm clearly
// 5. Discuss trade-offs between different approaches
