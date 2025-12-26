// Problem: LeetCode <ID>. <Title>
// Problem: Rotate the Matrix (Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/rotate-the-matrix_6825090

// Note: This code assumes n x n matrix (square), but handles m x n
// For clockwise 90° rotation: transpose + reverse each row

public class MatrixRotate {

    /**
     * Rotates matrix 90 degrees clockwise in-place
     * Works for both square and rectangular matrices
     * 
     * @param mat Matrix to rotate (modified in-place)
     */
    public static void rotateMatrix(int[][] mat) {
        int n = mat.length;      // rows
        int m = mat[0].length;   // columns
        
        // Step 1: Transpose the matrix
        // For square matrix: swap mat[i][j] with mat[j][i]
        // For rectangular: would need new matrix
        for (int i = 0; i < n; i++) {
            for (int j = i; j < m; j++) {
                // BUG: This only works for n x n (square) matrices!
                // When n != m, mat[j][i] might be out of bounds
                int tmp = mat[i][j];
                mat[i][j] = mat[j][i];
                mat[j][i] = tmp;
            }
        }
        
        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            int l = 0, r = m - 1;
            while (l < r) {
                int tmp = mat[i][l];
                mat[i][l] = mat[i][r];
                mat[i][r] = tmp;
                l++;
                r--;
            }
        }
    }
    
    /**
     * Correct version for n x n (square) matrices
     * Time: O(n²), Space: O(1)
     */
    public static void rotateMatrixSquare(int[][] mat) {
        int n = mat.length;
        
        // Step 1: Transpose (swap elements across diagonal)
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int temp = mat[i][j];
                mat[i][j] = mat[j][i];
                mat[j][i] = temp;
            }
        }
        
        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            int left = 0, right = n - 1;
            while (left < right) {
                int temp = mat[i][left];
                mat[i][left] = mat[i][right];
                mat[i][right] = temp;
                left++;
                right--;
            }
        }
    }
    
    /**
     * Rotate rectangular matrix (requires new matrix)
     * Time: O(n*m), Space: O(n*m)
     */
    public static int[][] rotateMatrixRectangular(int[][] mat) {
        int n = mat.length;      // original rows
        int m = mat[0].length;   // original columns
        
        // Rotated matrix dimensions swapped
        int[][] rotated = new int[m][n];
        
        // Fill rotated matrix
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // Clockwise 90°: rotated[j][n-1-i] = mat[i][j]
                rotated[j][n - 1 - i] = mat[i][j];
            }
        }
        
        return rotated;
    }
    
    /**
     * Alternative: Layer-by-layer rotation for square matrices
     */
    public static void rotateMatrixLayer(int[][] mat) {
        int n = mat.length;
        
        for (int layer = 0; layer < n / 2; layer++) {
            int first = layer;
            int last = n - 1 - layer;
            
            for (int i = first; i < last; i++) {
                int offset = i - first;
                
                // Save top
                int top = mat[first][i];
                
                // Left ? Top
                mat[first][i] = mat[last - offset][first];
                
                // Bottom ? Left
                mat[last - offset][first] = mat[last][last - offset];
                
                // Right ? Bottom
                mat[last][last - offset] = mat[i][last];
                
                // Top ? Right
                mat[i][last] = top;
            }
        }
    }
    
    /**
     * Helper to print matrix
     */
    public static void printMatrix(int[][] mat) {
        for (int[] row : mat) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Main method with test cases
     */
    public static void main(String[] args) {
        // Test Case 1: 3x3 square matrix
        int[][] test1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Test 1 - 3x3 Original:");
        printMatrix(test1);
        
        rotateMatrixSquare(test1);
        System.out.println("After 90° clockwise rotation:");
        printMatrix(test1);
        System.out.println("Expected:\n7 4 1\n8 5 2\n9 6 3");
        System.out.println();
        
        // Test Case 2: 2x2 square matrix
        int[][] test2 = {
            {1, 2},
            {3, 4}
        };
        System.out.println("Test 2 - 2x2 Original:");
        printMatrix(test2);
        
        rotateMatrixSquare(test2);
        System.out.println("After rotation:");
        printMatrix(test2);
        System.out.println("Expected:\n3 1\n4 2");
        System.out.println();
        
        // Test Case 3: 4x4 square matrix
        int[][] test3 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };
        System.out.println("Test 3 - 4x4 Original:");
        printMatrix(test3);
        
        rotateMatrixSquare(test3);
        System.out.println("After rotation:");
        printMatrix(test3);
        System.out.println("Expected:\n13 9 5 1\n14 10 6 2\n15 11 7 3\n16 12 8 4");
        System.out.println();
        
        // Test Case 4: Rectangular matrix (requires new array)
        int[][] test4 = {
            {1, 2, 3},
            {4, 5, 6}
        };
        System.out.println("Test 4 - 2x3 Rectangular Original:");
        printMatrix(test4);
        
        int[][] rotated4 = rotateMatrixRectangular(test4);
        System.out.println("After rotation (new 3x2 matrix):");
        printMatrix(rotated4);
        System.out.println("Expected:\n4 1\n5 2\n6 3");
        System.out.println();
        
        // Test Case 5: Layer-by-layer rotation
        int[][] test5 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Test 5 - Layer rotation:");
        printMatrix(test5);
        
        rotateMatrixLayer(test5);
        System.out.println("After layer rotation:");
        printMatrix(test5);
        System.out.println("Expected:\n7 4 1\n8 5 2\n9 6 3");
    }
}

// Algorithm Analysis:
// Square Matrix (n x n):
// 1. Transpose: swap mat[i][j] with mat[j][i] for i<j (O(n²/2))
// 2. Reverse each row: O(n²/2)
// Total: O(n²) time, O(1) space

// Rectangular Matrix (n x m):
// Cannot rotate in-place if n != m
// Need new m x n matrix, fill with: rotated[j][n-1-i] = mat[i][j]
// O(n*m) time, O(n*m) space

// Mathematical Representation:
// Clockwise 90°: (i, j) ? (j, n-1-i)
// Counter-clockwise 90°: (i, j) ? (m-1-j, i)
// 180°: (i, j) ? (n-1-i, m-1-j)

// Why Transpose + Reverse works for clockwise:
// Original: (i, j)
// Transpose: (j, i) 
// Reverse row: (j, n-1-i) ? Exactly clockwise 90°

// Why the original code has a bug:
// - Uses m (columns) in both loop bounds
// - For non-square: mat[j][i] may be out of bounds
// Example: 2x3 matrix ? tries to access mat[2][0] (out of bounds)

// Correct bounds for square matrix transpose:
// for (int i = 0; i < n; i++) {
//     for (int j = i; j < n; j++) {  // Use n, not m
//         // swap mat[i][j] and mat[j][i]
//     }
// }

// Layer-by-layer approach (for square):
// Rotate elements in concentric layers from outside to inside
// For each element in top edge, rotate 4 elements at once
// More complex but same O(n²) time

// Common Mistakes:
// 1. Assuming matrix is square when it's rectangular
// 2. Using wrong indices in transpose (mat[j][i] vs mat[i][j])
// 3. Forgetting to swap only once (j starts from i)
// 4. Not handling odd-sized matrices correctly
// 5. Modifying while reading (need temp variable)

// Edge Cases:
// 1. 1x1 matrix (no change)
// 2. Empty matrix (n=0 or m=0)
// 3. Single row/column matrix
// 4. Very large matrix (need in-place for memory)

// Related Problems:
// 1. LeetCode 48: Rotate Image (same problem)
// 2. Transpose Matrix
// 3. Spiral Matrix
// 4. Reshape Matrix

// Interview Tips:
// 1. Clarify if matrix is square or rectangular
// 2. Ask about in-place requirement
// 3. Start with simple solution (new matrix)
// 4. Optimize to in-place for square
// 5. Test with small examples (2x2, 3x3)
// 6. Explain the math behind transpose+reverse
