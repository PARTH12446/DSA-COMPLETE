import java.util.*;

/**
 * Largest Rectangle in Histogram
 * 
 * Problem: Given an array of integers representing histogram bar heights,
 * find the area of the largest rectangle that can be formed within the histogram.
 * 
 * Example: heights = [2,1,5,6,2,3] → Largest rectangle area = 10
 * Visualization: https://assets.leetcode.com/uploads/2021/01/04/histogram.jpg
 * 
 * Key Insight: For each bar, find the left and right boundaries where 
 * bars are shorter than current bar. Width = right - left - 1
 * 
 * Time Complexity: O(n) using monotonic stack
 * Space Complexity: O(n) for stack
 */
public class LargestRectangleHistogram {

    /**
     * Standard solution using monotonic increasing stack
     * 
     * Algorithm:
     * 1. Use stack to store indices of bars in increasing height order
     * 2. For each bar:
     *    - While current bar is shorter than stack top, calculate area for stack top
     *    - Area = height[top] × width, where width = current_index - stack_next_top - 1
     *    - Push current index to stack
     * 3. Process remaining bars in stack with width = n - stack_next_top - 1
     * 
     * @param heights Array of bar heights
     * @return Maximum rectangle area
     */
    public static int largestRectangleArea(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        Stack<Integer> st = new Stack<>();
        
        // Iterate through all bars + one extra for processing remaining stack
        for (int i = 0; i <= n; i++) {
            // Use 0 height for sentinel value at the end
            int currentHeight = (i == n) ? 0 : heights[i];
            
            // Pop bars taller than current bar
            while (!st.isEmpty() && heights[st.peek()] > currentHeight) {
                // Calculate area for the popped bar
                int height = heights[st.pop()];
                // Width extends from previous smaller bar to current bar
                int width = st.isEmpty() ? i : i - st.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            
            // Push current index to stack
            st.push(i);
        }
        
        return maxArea;
    }

    /**
     * Alternative approach using left and right boundary arrays
     * More intuitive but uses more space
     * 
     * Algorithm:
     * 1. For each bar, find first smaller bar to left (left boundary)
     * 2. For each bar, find first smaller bar to right (right boundary)
     * 3. Area for bar i = heights[i] × (right[i] - left[i] - 1)
     * 
     * Time: O(n), Space: O(n)
     */
    public static int largestRectangleAreaBoundaries(int[] heights) {
        int n = heights.length;
        if (n == 0) return 0;
        
        int[] left = new int[n];   // Index of first smaller bar to left
        int[] right = new int[n];  // Index of first smaller bar to right
        Stack<Integer> st = new Stack<>();
        
        // Find left boundaries
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && heights[st.peek()] >= heights[i]) {
                st.pop();
            }
            left[i] = st.isEmpty() ? -1 : st.peek();
            st.push(i);
        }
        
        // Clear stack for right boundaries
        st.clear();
        
        // Find right boundaries
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && heights[st.peek()] >= heights[i]) {
                st.pop();
            }
            right[i] = st.isEmpty() ? n : st.peek();
            st.push(i);
        }
        
        // Calculate maximum area
        int maxArea = 0;
        for (int i = 0; i < n; i++) {
            int width = right[i] - left[i] - 1;
            maxArea = Math.max(maxArea, heights[i] * width);
        }
        
        return maxArea;
    }

    /**
     * Divide and conquer solution using segment tree
     * Time: O(n log n) worst case, O(n) average
     * Good for interview discussions
     */
    public static int largestRectangleAreaDivideConquer(int[] heights) {
        return divideConquer(heights, 0, heights.length - 1);
    }
    
    private static int divideConquer(int[] heights, int left, int right) {
        if (left > right) return 0;
        if (left == right) return heights[left];
        
        // Find minimum height index in current range
        int minIdx = left;
        for (int i = left + 1; i <= right; i++) {
            if (heights[i] < heights[minIdx]) {
                minIdx = i;
            }
        }
        
        // Calculate areas:
        // 1. Area using minimum bar across entire range
        int areaWithMin = heights[minIdx] * (right - left + 1);
        
        // 2. Area in left subarray (excluding min bar)
        int areaLeft = divideConquer(heights, left, minIdx - 1);
        
        // 3. Area in right subarray (excluding min bar)
        int areaRight = divideConquer(heights, minIdx + 1, right);
        
        return Math.max(areaWithMin, Math.max(areaLeft, areaRight));
    }

    /**
     * Brute force solution (for comparison)
     * Time: O(n²), Space: O(1)
     */
    public static int largestRectangleAreaBruteForce(int[] heights) {
        int n = heights.length;
        int maxArea = 0;
        
        for (int i = 0; i < n; i++) {
            int minHeight = Integer.MAX_VALUE;
            for (int j = i; j < n; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                maxArea = Math.max(maxArea, minHeight * (j - i + 1));
            }
        }
        
        return maxArea;
    }

    /**
     * Visualization helper - shows histogram and rectangle
     */
    public static void visualizeHistogram(int[] heights) {
        int maxHeight = Arrays.stream(heights).max().orElse(0);
        
        System.out.println("\nHistogram Visualization:");
        for (int h = maxHeight; h > 0; h--) {
            for (int height : heights) {
                System.out.print(height >= h ? "██ " : "   ");
            }
            System.out.println();
        }
        
        // Print indices
        for (int i = 0; i < heights.length; i++) {
            System.out.printf("%-3d", heights[i]);
        }
        System.out.println();
        
        // Print indices numbers
        for (int i = 0; i < heights.length; i++) {
            System.out.printf("%-3d", i);
        }
        System.out.println();
    }

    /**
     * Step-by-step explanation of the stack algorithm
     */
    public static int largestRectangleAreaWithSteps(int[] heights) {
        System.out.println("\n=== STEP-BY-STEP SOLUTION ===");
        System.out.println("Heights: " + Arrays.toString(heights));
        visualizeHistogram(heights);
        
        int n = heights.length;
        int maxArea = 0;
        Stack<Integer> st = new Stack<>();
        
        System.out.println("\nProcessing with stack:");
        System.out.println("i | Height | Stack Before | Action | Area Calculation | Max Area");
        System.out.println("--|--------|--------------|--------|------------------|---------");
        
        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i];
            String stackBefore = st.toString();
            String action = "";
            String areaCalc = "";
            
            while (!st.isEmpty() && heights[st.peek()] > h) {
                int idx = st.pop();
                int height = heights[idx];
                int width = st.isEmpty() ? i : i - st.peek() - 1;
                int area = height * width;
                
                areaCalc = String.format("%d × %d = %d", height, width, area);
                action = String.format("Pop index %d (height=%d)", idx, height);
                
                maxArea = Math.max(maxArea, area);
                
                System.out.printf("%-2d| %-7d| %-12s| %-30s| %-18s| %-9d\n", 
                    i, h, stackBefore, action, areaCalc, maxArea);
                
                stackBefore = st.toString(); // Update for multiple pops
            }
            
            if (i < n) {
                st.push(i);
                action = String.format("Push index %d", i);
                System.out.printf("%-2d| %-7d| %-12s| %-30s| %-18s| %-9d\n", 
                    i, h, stackBefore, action, "", maxArea);
            }
        }
        
        System.out.println("\nMaximum rectangle area: " + maxArea);
        return maxArea;
    }

    /**
     * Test cases
     */
    public static void main(String[] args) {
        System.out.println("=== LARGEST RECTANGLE IN HISTOGRAM ===\n");
        
        // Test case 1: Standard example
        System.out.println("Test 1: [2,1,5,6,2,3]");
        int[] test1 = {2, 1, 5, 6, 2, 3};
        int result1 = largestRectangleArea(test1);
        System.out.println("Result: " + result1 + " (Expected: 10)");
        
        // Test with step-by-step explanation
        largestRectangleAreaWithSteps(test1);
        
        // Test case 2: Increasing heights
        System.out.println("\n\nTest 2: [1,2,3,4,5]");
        int[] test2 = {1, 2, 3, 4, 5};
        int result2 = largestRectangleArea(test2);
        System.out.println("Result: " + result2 + " (Expected: 9)");
        visualizeHistogram(test2);
        
        // Test case 3: Decreasing heights
        System.out.println("\n\nTest 3: [5,4,3,2,1]");
        int[] test3 = {5, 4, 3, 2, 1};
        int result3 = largestRectangleArea(test3);
        System.out.println("Result: " + result3 + " (Expected: 9)");
        
        // Test case 4: Single bar
        System.out.println("\n\nTest 4: [7]");
        int[] test4 = {7};
        int result4 = largestRectangleArea(test4);
        System.out.println("Result: " + result4 + " (Expected: 7)");
        
        // Test case 5: Empty histogram
        System.out.println("\n\nTest 5: []");
        int[] test5 = {};
        int result5 = largestRectangleArea(test5);
        System.out.println("Result: " + result5 + " (Expected: 0)");
        
        // Test case 6: All equal heights
        System.out.println("\n\nTest 6: [4,4,4,4]");
        int[] test6 = {4, 4, 4, 4};
        int result6 = largestRectangleArea(test6);
        System.out.println("Result: " + result6 + " (Expected: 16)");
        
        // Test case 7: Complex case
        System.out.println("\n\nTest 7: [6,2,5,4,5,1,6]");
        int[] test7 = {6, 2, 5, 4, 5, 1, 6};
        int result7 = largestRectangleArea(test7);
        System.out.println("Result: " + result7 + " (Expected: 12)");
        
        // Compare all methods
        System.out.println("\n=== METHOD COMPARISON ===");
        compareMethods(test1);
    }
    
    /**
     * Compare different solution methods
     */
    private static void compareMethods(int[] heights) {
        System.out.println("\nComparing methods for heights: " + Arrays.toString(heights));
        
        long start, end;
        
        // Method 1: Stack (optimal)
        start = System.nanoTime();
        int result1 = largestRectangleArea(heights);
        end = System.nanoTime();
        System.out.printf("Stack method: %d (%.3f ms)\n", result1, (end - start) / 1_000_000.0);
        
        // Method 2: Boundary arrays
        start = System.nanoTime();
        int result2 = largestRectangleAreaBoundaries(heights);
        end = System.nanoTime();
        System.out.printf("Boundary method: %d (%.3f ms)\n", result2, (end - start) / 1_000_000.0);
        
        // Method 3: Divide and conquer
        start = System.nanoTime();
        int result3 = largestRectangleAreaDivideConquer(heights);
        end = System.nanoTime();
        System.out.printf("Divide & conquer: %d (%.3f ms)\n", result3, (end - start) / 1_000_000.0);
        
        // Method 4: Brute force (only for small arrays)
        if (heights.length <= 1000) {
            start = System.nanoTime();
            int result4 = largestRectangleAreaBruteForce(heights);
            end = System.nanoTime();
            System.out.printf("Brute force: %d (%.3f ms)\n", result4, (end - start) / 1_000_000.0);
        }
    }
    
    /**
     * Performance test for large histogram
     */
    public static void performanceTest() {
        System.out.println("\n=== PERFORMANCE TEST ===");
        
        // Generate large histogram
        int size = 1000000;
        int[] largeHist = new int[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            largeHist[i] = rand.nextInt(10000);
        }
        
        long start = System.nanoTime();
        int result = largestRectangleArea(largeHist);
        long end = System.nanoTime();
        
        System.out.printf("Processed %,d bars in %.2f ms\n", 
            size, (end - start) / 1_000_000.0);
        System.out.println("Maximum area: " + result);
    }
    
    /**
     * Helper class for debugging
     */
    private static class Rectangle {
        int left;
        int right;
        int height;
        int area;
        
        Rectangle(int left, int right, int height) {
            this.left = left;
            this.right = right;
            this.height = height;
            this.area = height * (right - left + 1);
        }
        
        @Override
        public String toString() {
            return String.format("[%d-%d] height=%d, area=%d", 
                left, right, height, area);
        }
    }
    
    /**
     * Find all possible rectangles (for debugging)
     */
    public static List<Rectangle> findAllRectangles(int[] heights) {
        List<Rectangle> rectangles = new ArrayList<>();
        int n = heights.length;
        
        for (int i = 0; i < n; i++) {
            int minHeight = heights[i];
            for (int j = i; j < n; j++) {
                minHeight = Math.min(minHeight, heights[j]);
                rectangles.add(new Rectangle(i, j, minHeight));
            }
        }
        
        // Sort by area descending
        rectangles.sort((a, b) -> b.area - a.area);
        return rectangles;
    }
}