// Problem: LeetCode <ID>. <Title>
// Problem: Spiral Matrix (Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/spiral-matrix_6922069

import java.util.ArrayList;
import java.util.List;

public class MatrixSpiral {

    /**
     * Returns spiral order traversal of matrix
     * Time: O(n*m), Space: O(1) excluding output
     * 
     * @param matrix 2D array to traverse
     * @return List of elements in spiral order
     */
    public static List<Integer> spiralMatrix(int[][] matrix) {
        int n = matrix.length;        // rows
        int m = matrix[0].length;     // columns
        
        // Boundaries for spiral traversal
        int top = 0, bottom = n - 1;
        int left = 0, right = m - 1;
        
        List<Integer> result = new ArrayList<>();
        
        // Continue while boundaries are valid
        while (top <= bottom && left <= right) {
            // 1. Traverse top row (left ? right)
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++;  // Move top boundary down
            
            // 2. Traverse right column (top ? bottom)
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;  // Move right boundary left
            
            // 3. Traverse bottom row (right ? left) - if rows remain
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    result.add(matrix[bottom][i]);
                }
                bottom--;  // Move bottom boundary up
            }
            
            // 4. Traverse left column (bottom ? top) - if columns remain
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;  // Move left boundary right
            }
        }
        
        return result;
    }
    
    /**
     * Alternative: Directional approach (cleaner loop)
     */
    public static List<Integer> spiralMatrixDirectional(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix.length == 0) return result;
        
        int n = matrix.length, m = matrix[0].length;
        int top = 0, bottom = n - 1, left = 0, right = m - 1;
        
        while (result.size() < n * m) {
            // Right
            for (int i = left; i <= right && result.size() < n * m; i++) {
                result.add(matrix[top][i]);
            }
            top++;
            
            // Down
            for (int i = top; i <= bottom && result.size() < n * m; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            
            // Left
            for (int i = right; i >= left && result.size() < n * m; i--) {
                result.add(matrix[bottom][i]);
            }
            bottom--;
            
            // Up
            for (int i = bottom; i >= top && result.size() < n * m; i--) {
                result.add(matrix[i][left]);
            }
            left++;
        }
        
        return result;
    }
    
    /**
     * Variation: Generate matrix in spiral order
     * Fills n x n matrix with 1 to n² in spiral order
     */
    public static int[][] generateSpiralMatrix(int n) {
        int[][] matrix = new int[n][n];
        int num = 1;
        int top = 0, bottom = n - 1, left = 0, right = n - 1;
        
        while (num <= n * n) {
            // Fill top row
            for (int i = left; i <= right; i++) {
                matrix[top][i] = num++;
            }
            top++;
            
            // Fill right column
            for (int i = top; i <= bottom; i++) {
                matrix[i][right] = num++;
            }
            right--;
            
            // Fill bottom row
            for (int i = right; i >= left; i--) {
                matrix[bottom][i] = num++;
            }
            bottom--;
            
            // Fill left column
            for (int i = bottom; i >= top; i--) {
                matrix[i][left] = num++;
            }
            left++;
        }
        
        return matrix;
    }
    
    /**
     * Recursive approach for spiral traversal
     */
    public static List<Integer> spiralMatrixRecursive(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix.length == 0) return result;
        spiralHelper(matrix, 0, matrix.length - 1, 0, matrix[0].length - 1, result);
        return result;
    }
    
    private static void spiralHelper(int[][] matrix, int top, int bottom, int left, int right, List<Integer> result) {
        // Base case: boundaries cross
        if (top > bottom || left > right) return;
        
        // Top row
        for (int i = left; i <= right; i++) {
            result.add(matrix[top][i]);
        }
        top++;
        
        // Right column
        for (int i = top; i <= bottom; i++) {
            result.add(matrix[i][right]);
        }
        right--;
        
        // Bottom row (if exists)
        if (top <= bottom) {
            for (int i = right; i >= left; i--) {
                result.add(matrix[bottom][i]);
            }
            bottom--;
        }
        
        // Left column (if exists)
        if (left <= right) {
            for (int i = bottom; i >= top; i--) {
                result.add(matrix[i][left]);
            }
            left++;
        }
        
        // Recurse on inner matrix
        spiralHelper(matrix, top, bottom, left, right, result);
    }
    
    /**
     * Helper to print matrix
     */
    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
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
        // Test Case 1: 3x3 matrix
        int[][] test1 = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Test 1 - 3x3 Matrix:");
        printMatrix(test1);
        System.out.println("Spiral order: " + spiralMatrix(test1));
        System.out.println("Expected: [1, 2, 3, 6, 9, 8, 7, 4, 5]");
        System.out.println();
        
        // Test Case 2: 4x4 matrix
        int[][] test2 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };
        System.out.println("Test 2 - 4x4 Matrix:");
        printMatrix(test2);
        System.out.println("Spiral order: " + spiralMatrix(test2));
        System.out.println("Expected: [1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10]");
        System.out.println();
        
        // Test Case 3: Rectangular 3x4 matrix
        int[][] test3 = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };
        System.out.println("Test 3 - 3x4 Matrix:");
        printMatrix(test3);
        System.out.println("Spiral order: " + spiralMatrix(test3));
        System.out.println("Expected: [1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7]");
        System.out.println();
        
        // Test Case 4: Single row
        int[][] test4 = {{1, 2, 3, 4, 5}};
        System.out.println("Test 4 - 1x5 Matrix:");
        printMatrix(test4);
        System.out.println("Spiral order: " + spiralMatrix(test4));
        System.out.println("Expected: [1, 2, 3, 4, 5]");
        System.out.println();
        
        // Test Case 5: Single column
        int[][] test5 = {{1}, {2}, {3}, {4}};
        System.out.println("Test 5 - 4x1 Matrix:");
        printMatrix(test5);
        System.out.println("Spiral order: " + spiralMatrix(test5));
        System.out.println("Expected: [1, 2, 3, 4]");
        System.out.println();
        
        // Test Case 6: Empty matrix
        int[][] test6 = {};
        System.out.println("Test 6 - Empty matrix: " + spiralMatrix(test6));
        System.out.println("Expected: []");
        System.out.println();
        
        // Test generate spiral matrix
        System.out.println("Generate 4x4 spiral matrix:");
        int[][] generated = generateSpiralMatrix(4);
        printMatrix(generated);
        System.out.println("Generated spiral order: " + spiralMatrix(generated));
        
        // Test recursive approach
        System.out.println("\nTest recursive on 3x3:");
        System.out.println(spiralMatrixRecursive(test1));
    }
}

// Algorithm Analysis:
// Time Complexity: O(n*m) - Visit each element exactly once
// Space Complexity: O(1) excluding output list (O(n*m) for output)

// Key Insight:
// Use four boundaries (top, bottom, left, right) that shrink inward
// Traverse in order: right ? down ? left ? up
// After each traversal, adjust the corresponding boundary

// Why the boundary checks are needed (lines 36, 43):
// After traversing right and down, the matrix might be fully traversed
// Without checks, we might:
// 1. Add same elements twice (for bottom row)
// 2. Access out-of-bounds indices

// Example Walkthrough (3x3):
// Initial: top=0, bottom=2, left=0, right=2
// 1. Top row (0,0)?(0,2): 1,2,3 ? top=1
// 2. Right col (1,2)?(2,2): 6,9 ? right=1
// 3. Bottom row (2,1)?(2,0): 8,7 (check: top=1 = bottom=2 ?) ? bottom=1
// 4. Left col (1,0)?(1,0): 4 (check: left=0 = right=1 ?) ? left=1
// 5. Top row (1,1)?(1,1): 5 ? top=2 > bottom=1 ? stop

// Edge Cases:
// 1. Single row matrix ? only traverse right
// 2. Single column matrix ? only traverse down
// 3. Empty matrix ? return empty list
// 4. 1x1 matrix ? return single element
// 5. Rectangular matrices (n ? m)

// Common Mistakes:
// 1. Forgetting to check boundaries after shrinking
// 2. Using while(true) without proper exit condition
// 3. Incorrect indices in traversal loops
// 4. Not handling single row/column cases
// 5. Modifying boundaries in wrong order

// Alternative Approaches:
// 1. Directional (DRUL): Track current direction, change when hitting boundary
// 2. Recursive: Process outer layer, recurse on inner matrix
// 3. Visited set: Mark visited cells, change direction when hitting visited cell

// Related Problems:
// 1. LeetCode 54: Spiral Matrix (same problem)
// 2. LeetCode 59: Spiral Matrix II (generate spiral matrix)
// 3. Diagonal traversal of matrix
// 4. Snake pattern traversal

// Real-world Applications:
// 1. Image processing (spiral sampling)
// 2. Printer path optimization
// 3. Game board traversal
// 4. Data compression (spiral encoding)

// Interview Tips:
// 1. Start with simple 3x3 example to demonstrate logic
// 2. Draw boundaries and show how they shrink
// 3. Handle edge cases explicitly
// 4. Consider both square and rectangular matrices
// 5. Discuss time/space complexity trade-offs

// Performance:
// - Must visit all n*m elements ? O(n*m) is optimal
// - In-place except for output list
// - Boundary approach minimizes conditional checks

// Test with:
// 1. Various dimensions (1x1, 1xn, nx1, nxn)
// 2. Both even and odd dimensions
// 3. Already visited patterns to ensure no duplicates
