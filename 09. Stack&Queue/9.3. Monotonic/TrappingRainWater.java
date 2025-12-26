import java.util.*;

/**
 * Trapping Rain Water (LeetCode 42)
 * 
 * Problem: Given n non-negative integers representing an elevation map
 * where each bar has width 1, compute how much water it can trap after raining.
 * 
 * Stack-based Approach:
 * Use monotonic decreasing stack to track bars. When we encounter a bar taller
 * than stack top, it forms a basin with previous bar in stack.
 * 
 * Alternative Approaches:
 * 1. Two-pointer approach: O(n) time, O(1) space
 * 2. Dynamic programming: Precompute leftMax and rightMax arrays
 * 
 * Key Insight: Water trapped at position i = 
 * min(leftMax[i], rightMax[i]) - height[i] (if positive)
 * 
 * Time Complexity: O(n) - Each bar pushed/popped at most once
 * Space Complexity: O(n) - Stack storage
 * 
 * Example: height = [0,1,0,2,1,0,1,3,2,1,2,1]
 * Visual: 
 *               X
 *       X       X X   X
 *   X   X X   X X X X X X
 * 0 1 0 2 1 0 1 3 2 1 2 1
 * Water units: 6
 */
public class TrappingRainwater {
    
    /**
     * Stack-based solution for trapping rainwater.
     * 
     * Algorithm:
     * 1. Use monotonic decreasing stack (taller bars to shorter from bottom to top)
     * 2. When current bar > stack top, it forms a basin with previous bar in stack
     * 3. Calculate water trapped between left boundary (stack.peek()) and current
     * 
     * Visual: 
     * Stack: [3(bar at index 3 with height 2)] ← decreasing
     * Current: i=7, height=3
     * Since 3 > 2, pop 2, calculate water between index 6(height=1) and 7(height=3)
     * 
     * @param height Array of bar heights
     * @return Total trapped water units
     */
    public static int trap(int[] height) {
        int totalWater = 0;
        Stack<Integer> stack = new Stack<>(); // Stores indices of bars in decreasing height
        
        for (int i = 0; i < height.length; i++) {
            // While current bar is taller than stack top (forms a basin)
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int bottomIndex = stack.pop(); // The bottom of the basin
                
                // If no left boundary exists, break (can't trap water)
                if (stack.isEmpty()) break;
                
                int leftIndex = stack.peek(); // Left boundary of the basin
                
                // Calculate water trapped in this basin
                int width = i - leftIndex - 1; // Distance between boundaries
                int depth = Math.min(height[leftIndex], height[i]) - height[bottomIndex];
                
                totalWater += width * depth;
            }
            
            // Push current index to stack
            stack.push(i);
        }
        
        return totalWater;
    }
    
    /**
     * Two-pointer solution (more efficient, O(1) space).
     * Uses left and right pointers with leftMax and rightMax tracking.
     */
    public static int trapTwoPointer(int[] height) {
        if (height.length == 0) return 0;
        
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int totalWater = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                // Process left side
                if (height[left] >= leftMax) {
                    leftMax = height[left]; // Update left max
                } else {
                    totalWater += leftMax - height[left]; // Trap water
                }
                left++;
            } else {
                // Process right side
                if (height[right] >= rightMax) {
                    rightMax = height[right]; // Update right max
                } else {
                    totalWater += rightMax - height[right]; // Trap water
                }
                right--;
            }
        }
        
        return totalWater;
    }
    
    /**
     * Dynamic Programming solution.
     * Precompute leftMax and rightMax arrays, then calculate trapped water.
     */
    public static int trapDP(int[] height) {
        int n = height.length;
        if (n == 0) return 0;
        
        // Arrays to store maximum height to left and right of each position
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        
        // Fill leftMax array
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }
        
        // Fill rightMax array
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }
        
        // Calculate trapped water
        int totalWater = 0;
        for (int i = 0; i < n; i++) {
            int minBoundary = Math.min(leftMax[i], rightMax[i]);
            totalWater += minBoundary - height[i];
        }
        
        return totalWater;
    }
    
    /**
     * Visualization helper to show water trapping process.
     */
    public static void visualizeTrap(int[] height) {
        System.out.println("\n=== Visualization for height = " + Arrays.toString(height) + " ===");
        System.out.println("Processing with stack-based approach:");
        
        Stack<Integer> stack = new Stack<>();
        int totalWater = 0;
        
        for (int i = 0; i < height.length; i++) {
            System.out.println("\nStep " + i + ": Processing bar at index " + i + " with height " + height[i]);
            System.out.println("Current stack (indices): " + stack);
            
            while (!stack.isEmpty() && height[stack.peek()] < height[i]) {
                int bottom = stack.pop();
                System.out.println("  Popped bottom at index " + bottom + " (height=" + height[bottom] + ")");
                
                if (stack.isEmpty()) {
                    System.out.println("  No left boundary, cannot trap water");
                    break;
                }
                
                int left = stack.peek();
                int width = i - left - 1;
                int depth = Math.min(height[left], height[i]) - height[bottom];
                int waterInBasin = width * depth;
                totalWater += waterInBasin;
                
                System.out.println("  Basin: left=" + left + "(h=" + height[left] + 
                                 "), right=" + i + "(h=" + height[i] + 
                                 "), bottom=" + bottom + "(h=" + height[bottom] + ")");
                System.out.println("  width=" + width + ", depth=" + depth + 
                                 ", water=" + waterInBasin);
                System.out.println("  Total water so far: " + totalWater);
            }
            
            stack.push(i);
            System.out.println("  Pushed index " + i + " to stack");
        }
        
        System.out.println("\nFinal total trapped water: " + totalWater);
    }
    
    /**
     * Draw ASCII visualization of bars and water.
     */
    public static void drawElevationMap(int[] height) {
        int maxHeight = 0;
        for (int h : height) maxHeight = Math.max(maxHeight, h);
        
        System.out.println("\nElevation Map with Water (W = water):");
        System.out.println("Height: " + Arrays.toString(height));
        System.out.println();
        
        for (int level = maxHeight; level >= 0; level--) {
            System.out.print("Level " + level + ": ");
            for (int h : height) {
                if (h > level) {
                    System.out.print("█ "); // Bar
                } else {
                    // Check if this position would hold water
                    int leftMax = 0, rightMax = 0;
                    // Simplified check for visualization
                    boolean hasLeftHigher = false;
                    boolean hasRightHigher = false;
                    
                    // In real calculation, we'd need full DP approach
                    // This is just for visualization
                    if (level < h) {
                        System.out.print("█ ");
                    } else {
                        System.out.print("  ");
                    }
                }
            }
            System.out.println();
        }
        
        // Draw ground
        System.out.print("Ground: ");
        for (int i = 0; i < height.length; i++) {
            System.out.print("--");
        }
        System.out.println();
        
        // Draw indices
        System.out.print("Index:  ");
        for (int i = 0; i < height.length; i++) {
            System.out.print(i + (i < 10 ? " " : ""));
        }
        System.out.println();
    }
    
    /**
     * Test cases for the trapping rainwater problem.
     */
    public static void runTestCases() {
        System.out.println("=== Test Cases for Trapping Rain Water ===\n");
        
        // Test 1: Standard case
        int[] test1 = {0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println("Test 1: " + Arrays.toString(test1));
        System.out.println("Expected: 6");
        System.out.println("Stack: " + trap(test1) + ", Two-pointer: " + trapTwoPointer(test1) + 
                         ", DP: " + trapDP(test1));
        System.out.println();
        
        // Test 2: All increasing
        int[] test2 = {1,2,3,4,5};
        System.out.println("Test 2 (all increasing): " + Arrays.toString(test2));
        System.out.println("Expected: 0 (no basin to trap water)");
        System.out.println("Stack: " + trap(test2) + ", Two-pointer: " + trapTwoPointer(test2));
        System.out.println();
        
        // Test 3: All decreasing
        int[] test3 = {5,4,3,2,1};
        System.out.println("Test 3 (all decreasing): " + Arrays.toString(test3));
        System.out.println("Expected: 0 (no basin to trap water)");
        System.out.println("Stack: " + trap(test3) + ", Two-pointer: " + trapTwoPointer(test3));
        System.out.println();
        
        // Test 4: Flat terrain
        int[] test4 = {3,3,3,3,3};
        System.out.println("Test 4 (flat): " + Arrays.toString(test4));
        System.out.println("Expected: 0 (no depression to hold water)");
        System.out.println("Stack: " + trap(test4) + ", Two-pointer: " + trapTwoPointer(test4));
        System.out.println();
        
        // Test 5: Valley
        int[] test5 = {5,1,1,1,5};
        System.out.println("Test 5 (valley): " + Arrays.toString(test5));
        System.out.println("Expected: 12 (width=3, depth=4, 3*4=12)");
        System.out.println("Stack: " + trap(test5) + ", Two-pointer: " + trapTwoPointer(test5));
        System.out.println();
        
        // Test 6: Single peak
        int[] test6 = {0,0,5,0,0};
        System.out.println("Test 6 (single peak): " + Arrays.toString(test6));
        System.out.println("Expected: 0 (water flows off sides)");
        System.out.println("Stack: " + trap(test6) + ", Two-pointer: " + trapTwoPointer(test6));
        System.out.println();
        
        // Test 7: Complex case
        int[] test7 = {4,2,0,3,2,5};
        System.out.println("Test 7: " + Arrays.toString(test7));
        System.out.println("Expected: 9");
        System.out.println("Stack: " + trap(test7) + ", Two-pointer: " + trapTwoPointer(test7));
        System.out.println();
        
        // Test 8: Edge cases
        int[] test8 = {};
        System.out.println("Test 8 (empty): " + Arrays.toString(test8));
        System.out.println("Expected: 0");
        System.out.println("Stack: " + trap(test8) + ", Two-pointer: " + trapTwoPointer(test8));
        System.out.println();
        
        int[] test9 = {5};
        System.out.println("Test 9 (single bar): " + Arrays.toString(test9));
        System.out.println("Expected: 0");
        System.out.println("Stack: " + trap(test9) + ", Two-pointer: " + trapTwoPointer(test9));
        System.out.println();
    }
    
    /**
     * Performance comparison of different approaches.
     */
    public static void benchmark() {
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test case
        int n = 1000000;
        int[] height = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            height[i] = rand.nextInt(10000);
        }
        
        System.out.println("Array size: " + n);
        
        // Stack approach
        long start = System.currentTimeMillis();
        int result1 = trap(height);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Stack approach: " + time1 + " ms, result: " + result1);
        
        // Two-pointer approach
        start = System.currentTimeMillis();
        int result2 = trapTwoPointer(height);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Two-pointer: " + time2 + " ms, result: " + result2);
        
        // DP approach
        start = System.currentTimeMillis();
        int result3 = trapDP(height);
        long time3 = System.currentTimeMillis() - start;
        System.out.println("DP approach: " + time3 + " ms, result: " + result3);
        
        System.out.println("All results match: " + (result1 == result2 && result2 == result3));
    }
    
    /**
     * Explain the water calculation formula.
     */
    public static void explainWaterCalculation() {
        System.out.println("\n=== Water Calculation Formula ===");
        System.out.println("For a basin between left bar L and right bar R:");
        System.out.println("1. Bottom bar B (the lowest bar between L and R)");
        System.out.println("2. Width = distance(L, R) - 1 = (R.index - L.index - 1)");
        System.out.println("3. Depth = min(L.height, R.height) - B.height");
        System.out.println("4. Water = Width × Depth");
        System.out.println();
        System.out.println("Example: L at index 1 (height=1), R at index 3 (height=2),");
        System.out.println("         Bottom at index 2 (height=0)");
        System.out.println("Width = 3-1-1 = 1, Depth = min(1,2)-0 = 1");
        System.out.println("Water = 1 × 1 = 1 unit");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the main example
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed visualization of main example:");
        System.out.println("=".repeat(60));
        int[] height = {0,1,0,2,1,0,1,3,2,1,2,1};
        visualizeTrap(height);
        
        // Draw ASCII visualization
        drawElevationMap(height);
        
        // Explain the formula
        explainWaterCalculation();
        
        // Run benchmark (optional)
        // benchmark();
    }
}