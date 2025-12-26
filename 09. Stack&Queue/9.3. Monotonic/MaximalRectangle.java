import java.util.*;

/**
 * Maximal Rectangle in Binary Matrix
 * 
 * Problem: Given a 2D binary matrix filled with 0's and 1's, 
 * find the largest rectangle containing only 1's and return its area.
 * 
 * Example:
 * [1 0 1 0 0]
 * [1 0 1 1 1]
 * [1 1 1 1 1]
 * [1 0 0 1 0]
 * 
 * Largest rectangle area = 6 (2×3 rectangle at rows 1-2, columns 2-4)
 * 
 * Key Insight: Convert each row to a histogram problem where:
 * - Each column height = number of consecutive 1's from current row upwards
 * - Then apply Largest Rectangle in Histogram algorithm
 * 
 * Time Complexity: O(m×n) where m=rows, n=columns
 * Space Complexity: O(n) for heights array
 */
public class MaximalRectangle {

    /**
     * Standard solution using histogram conversion
     * 
     * Algorithm:
     * 1. For each row, build heights array:
     *    - If cell is '1', increment height from previous row
     *    - If cell is '0', reset height to 0
     * 2. For each row's heights, find largest rectangle area using histogram algorithm
     * 3. Track maximum area across all rows
     * 
     * @param matrix 2D binary matrix with '1's and '0's
     * @return Area of largest rectangle containing only 1's
     */
    public static int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int m = matrix.length;      // Number of rows
        int n = matrix[0].length;   // Number of columns
        int[] heights = new int[n]; // Histogram heights for current row
        int maxArea = 0;
        
        // Process each row
        for (int i = 0; i < m; i++) {
            // Update heights array for current row
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    // Extend height from previous row
                    heights[j] += 1;
                } else {
                    // Reset height (broken column of 1's)
                    heights[j] = 0;
                }
            }
            
            // Find largest rectangle in current histogram
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }
        
        return maxArea;
    }

    /**
     * Largest Rectangle in Histogram helper function
     * Uses monotonic stack algorithm
     */
    private static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        Stack<Integer> st = new Stack<>();
        
        for (int i = 0; i <= n; i++) {
            // Use 0 as sentinel value for last iteration
            int currentHeight = (i == n) ? 0 : heights[i];
            
            // Pop bars taller than current
            while (!st.isEmpty() && heights[st.peek()] > currentHeight) {
                int height = heights[st.pop()];
                int width = st.isEmpty() ? i : i - st.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            
            st.push(i);
        }
        
        return maxArea;
    }

    /**
     * Alternative approach using dynamic programming
     * Computes left boundary, right boundary, and height for each cell
     * 
     * Time: O(m×n), Space: O(n)
     */
    public static int maximalRectangleDP(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return 0;
        
        int m = matrix.length;
        int n = matrix[0].length;
        int maxArea = 0;
        
        // DP arrays
        int[] height = new int[n];     // Height of '1's in current column
        int[] left = new int[n];       // Left boundary of rectangle
        int[] right = new int[n];      // Right boundary of rectangle
        Arrays.fill(right, n - 1);     // Initialize right to last column
        
        for (int i = 0; i < m; i++) {
            int curLeft = 0;      // Current left boundary
            int curRight = n - 1; // Current right boundary
            
            // Update height array
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    height[j]++;
                } else {
                    height[j] = 0;
                }
            }
            
            // Update left boundaries
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    left[j] = Math.max(left[j], curLeft);
                } else {
                    left[j] = 0;
                    curLeft = j + 1;
                }
            }
            
            // Update right boundaries
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == '1') {
                    right[j] = Math.min(right[j], curRight);
                } else {
                    right[j] = n - 1;
                    curRight = j - 1;
                }
            }
            
            // Calculate area for each column
            for (int j = 0; j < n; j++) {
                int width = right[j] - left[j] + 1;
                maxArea = Math.max(maxArea, height[j] * width);
            }
        }
        
        return maxArea;
    }

    /**
     * Brute force solution (for comparison)
     * Check all possible rectangles
     * Time: O(m³×n³), Space: O(1)
     */
    public static int maximalRectangleBruteForce(char[][] matrix) {
        if (matrix.length == 0) return 0;
        
        int m = matrix.length;
        int n = matrix[0].length;
        int maxArea = 0;
        
        // Try all possible top-left corners
        for (int top = 0; top < m; top++) {
            for (int left = 0; left < n; left++) {
                // Try all possible bottom-right corners
                for (int bottom = top; bottom < m; bottom++) {
                    for (int right = left; right < n; right++) {
                        // Check if rectangle contains only 1's
                        if (isValidRectangle(matrix, top, left, bottom, right)) {
                            int area = (bottom - top + 1) * (right - left + 1);
                            maxArea = Math.max(maxArea, area);
                        }
                    }
                }
            }
        }
        
        return maxArea;
    }
    
    private static boolean isValidRectangle(char[][] matrix, 
                                           int top, int left, 
                                           int bottom, int right) {
        for (int i = top; i <= bottom; i++) {
            for (int j = left; j <= right; j++) {
                if (matrix[i][j] == '0') {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Visualization helper - shows matrix and identified rectangle
     */
    public static void visualizeMatrix(char[][] matrix, int[] rectangle) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        System.out.println("\nMatrix Visualization:");
        System.out.println("Rows: " + m + ", Columns: " + n);
        
        // Print column indices
        System.out.print("   ");
        for (int j = 0; j < n; j++) {
            System.out.printf("%2d ", j);
        }
        System.out.println();
        
        // Print separator
        System.out.print("   ");
        for (int j = 0; j < n; j++) {
            System.out.print("---");
        }
        System.out.println();
        
        // Print matrix with row indices
        for (int i = 0; i < m; i++) {
            System.out.printf("%2d|", i);
            for (int j = 0; j < n; j++) {
                char cell = matrix[i][j];
                boolean inRect = rectangle != null && 
                                i >= rectangle[0] && i <= rectangle[2] &&
                                j >= rectangle[1] && j <= rectangle[3];
                
                if (inRect && cell == '1') {
                    System.out.print(" █ "); // Highlight rectangle
                } else {
                    System.out.print(" " + cell + " ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Find and return the coordinates of the maximal rectangle
     * Returns [top, left, bottom, right] or null if no rectangle
     */
    public static int[] findMaximalRectangleCoordinates(char[][] matrix) {
        if (matrix == null || matrix.length == 0) return null;
        
        int m = matrix.length;
        int n = matrix[0].length;
        int[] heights = new int[n];
        int maxArea = 0;
        int[] bestRect = null;
        
        // Store rectangle boundaries for each row's histogram
        List<int[]>[] rectBoundaries = new ArrayList[m];
        for (int i = 0; i < m; i++) rectBoundaries[i] = new ArrayList<>();
        
        for (int i = 0; i < m; i++) {
            // Update heights
            for (int j = 0; j < n; j++) {
                heights[j] = matrix[i][j] == '1' ? heights[j] + 1 : 0;
            }
            
            // Find all rectangles in current histogram
            List<int[]> rectangles = findRectanglesInHistogram(heights, i);
            rectBoundaries[i] = rectangles;
            
            // Track best rectangle
            for (int[] rect : rectangles) {
                int area = rect[4]; // area stored at index 4
                if (area > maxArea) {
                    maxArea = area;
                    bestRect = rect;
                }
            }
        }
        
        return bestRect;
    }
    
    private static List<int[]> findRectanglesInHistogram(int[] heights, int row) {
        List<int[]> rectangles = new ArrayList<>();
        Stack<Integer> st = new Stack<>();
        
        for (int i = 0; i <= heights.length; i++) {
            int h = (i == heights.length) ? 0 : heights[i];
            
            while (!st.isEmpty() && heights[st.peek()] > h) {
                int heightIdx = st.pop();
                int height = heights[heightIdx];
                int left = st.isEmpty() ? 0 : st.peek() + 1;
                int right = i - 1;
                int area = height * (right - left + 1);
                
                // Calculate top row (row - height + 1)
                int top = row - height + 1;
                rectangles.add(new int[]{top, left, row, right, area});
            }
            
            st.push(i);
        }
        
        return rectangles;
    }

    /**
     * Step-by-step explanation
     */
    public static int maximalRectangleWithSteps(char[][] matrix) {
        System.out.println("\n=== STEP-BY-STEP SOLUTION ===");
        
        int m = matrix.length;
        int n = matrix[0].length;
        int[] heights = new int[n];
        int maxArea = 0;
        
        visualizeMatrix(matrix, null);
        
        System.out.println("\nProcessing row by row:");
        System.out.println("Row | Heights Array | Max Area in Row | Global Max");
        System.out.println("----|---------------|-----------------|-----------");
        
        for (int i = 0; i < m; i++) {
            // Update heights
            for (int j = 0; j < n; j++) {
                heights[j] = matrix[i][j] == '1' ? heights[j] + 1 : 0;
            }
            
            // Calculate max area for current row
            int rowMaxArea = largestRectangleArea(heights);
            maxArea = Math.max(maxArea, rowMaxArea);
            
            System.out.printf("%3d | %s | %15d | %10d\n", 
                i, Arrays.toString(heights), rowMaxArea, maxArea);
        }
        
        System.out.println("\nMaximum rectangle area: " + maxArea);
        return maxArea;
    }

    /**
     * Test cases
     */
    public static void main(String[] args) {
        System.out.println("=== MAXIMAL RECTANGLE IN BINARY MATRIX ===\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: Standard 4×5 matrix");
        char[][] matrix1 = {
            {'1','0','1','0','0'},
            {'1','0','1','1','1'},
            {'1','1','1','1','1'},
            {'1','0','0','1','0'}
        };
        
        int result1 = maximalRectangle(matrix1);
        System.out.println("Result: " + result1 + " (Expected: 6)");
        
        // Show step-by-step solution
        maximalRectangleWithSteps(matrix1);
        
        // Find and visualize the rectangle
        int[] rect1 = findMaximalRectangleCoordinates(matrix1);
        if (rect1 != null) {
            System.out.println("\nMaximal rectangle coordinates:");
            System.out.println("Top-left: (" + rect1[0] + ", " + rect1[1] + ")");
            System.out.println("Bottom-right: (" + rect1[2] + ", " + rect1[3] + ")");
            System.out.println("Area: " + rect1[4]);
            visualizeMatrix(matrix1, rect1);
        }
        
        // Test case 2: Single row
        System.out.println("\n\nTest 2: Single row matrix");
        char[][] matrix2 = {{'1','0','1','1','0','1'}};
        int result2 = maximalRectangle(matrix2);
        System.out.println("Result: " + result2 + " (Expected: 2)");
        
        // Test case 3: Single column
        System.out.println("\n\nTest 3: Single column matrix");
        char[][] matrix3 = {{'1'},{'1'},{'0'},{'1'}};
        int result3 = maximalRectangle(matrix3);
        System.out.println("Result: " + result3 + " (Expected: 2)");
        
        // Test case 4: All ones
        System.out.println("\n\nTest 4: All ones matrix (3×4)");
        char[][] matrix4 = {
            {'1','1','1','1'},
            {'1','1','1','1'},
            {'1','1','1','1'}
        };
        int result4 = maximalRectangle(matrix4);
        System.out.println("Result: " + result4 + " (Expected: 12)");
        
        // Test case 5: All zeros
        System.out.println("\n\nTest 5: All zeros matrix (2×3)");
        char[][] matrix5 = {
            {'0','0','0'},
            {'0','0','0'}
        };
        int result5 = maximalRectangle(matrix5);
        System.out.println("Result: " + result5 + " (Expected: 0)");
        
        // Test case 6: Checkerboard pattern
        System.out.println("\n\nTest 6: Checkerboard pattern (4×4)");
        char[][] matrix6 = {
            {'1','0','1','0'},
            {'0','1','0','1'},
            {'1','0','1','0'},
            {'0','1','0','1'}
        };
        int result6 = maximalRectangle(matrix6);
        System.out.println("Result: " + result6 + " (Expected: 1)");
        
        // Compare different methods
        System.out.println("\n=== METHOD COMPARISON ===");
        compareMethods(matrix1);
        
        // Performance test
        performanceTest();
    }
    
    /**
     * Compare different solution methods
     */
    private static void compareMethods(char[][] matrix) {
        System.out.println("\nComparing methods for " + matrix.length + "×" + matrix[0].length + " matrix:");
        
        long start, end;
        
        // Method 1: Histogram conversion (optimal)
        start = System.nanoTime();
        int result1 = maximalRectangle(matrix);
        end = System.nanoTime();
        System.out.printf("Histogram method: %d (%.3f ms)\n", result1, (end - start) / 1_000_000.0);
        
        // Method 2: Dynamic Programming
        start = System.nanoTime();
        int result2 = maximalRectangleDP(matrix);
        end = System.nanoTime();
        System.out.printf("DP method: %d (%.3f ms)\n", result2, (end - start) / 1_000_000.0);
        
        // Method 3: Brute force (only for small matrices)
        if (matrix.length * matrix[0].length <= 100) {
            start = System.nanoTime();
            int result3 = maximalRectangleBruteForce(matrix);
            end = System.nanoTime();
            System.out.printf("Brute force: %d (%.3f ms)\n", result3, (end - start) / 1_000_000.0);
        }
    }
    
    /**
     * Performance test with large matrix
     */
    public static void performanceTest() {
        System.out.println("\n=== PERFORMANCE TEST ===");
        
        // Generate large random binary matrix
        int rows = 1000;
        int cols = 1000;
        char[][] largeMatrix = new char[rows][cols];
        Random rand = new Random();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                largeMatrix[i][j] = rand.nextDouble() > 0.5 ? '1' : '0';
            }
        }
        
        System.out.println("Testing " + rows + "×" + cols + " matrix (1 million cells)");
        
        long start = System.nanoTime();
        int result = maximalRectangle(largeMatrix);
        long end = System.nanoTime();
        
        System.out.printf("Processed in %.2f ms\n", (end - start) / 1_000_000.0);
        System.out.println("Maximum rectangle area: " + result);
    }
    
    /**
     * Helper: Convert matrix to string for debugging
     */
    public static String matrixToString(char[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (char[] row : matrix) {
            for (char cell : row) {
                sb.append(cell).append(' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
    
    /**
     * Generate test cases with visual patterns
     */
    public static char[][] generatePatternMatrix(String pattern, int rows, int cols) {
        char[][] matrix = new char[rows][cols];
        switch (pattern) {
            case "diagonal":
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        matrix[i][j] = (i == j) ? '1' : '0';
                    }
                }
                break;
            case "border":
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        matrix[i][j] = (i == 0 || i == rows-1 || j == 0 || j == cols-1) ? '1' : '0';
                    }
                }
                break;
            case "cross":
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        matrix[i][j] = (i == j || i + j == rows - 1) ? '1' : '0';
                    }
                }
                break;
        }
        return matrix;
    }
}