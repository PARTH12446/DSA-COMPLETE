import java.util.*;

/**
 * Sliding Window Maximum (LeetCode 239)
 * 
 * Problem: Given an array nums and a sliding window of size k moving from left to right,
 * find the maximum value in each window position.
 * 
 * Approach: Use a monotonic decreasing deque (double-ended queue) to store indices.
 * The deque maintains indices of elements in decreasing order of their values.
 * 
 * Key Insights:
 * 1. Deque stores indices, not values, to handle both value and position
 * 2. Deque maintains decreasing order (front has largest, back has smallest)
 * 3. Remove elements that are out of current window from front
 * 4. Remove elements smaller than current from back (they can't be maximum)
 * 
 * Time Complexity: O(n) - Each element added/removed at most once
 * Space Complexity: O(k) - Deque stores at most k elements
 * 
 * Example: nums = [1,3,-1,-3,5,3,6,7], k = 3
 * Windows: [1,3,-1], [3,-1,-3], [-1,-3,5], [-3,5,3], [5,3,6], [3,6,7]
 * Output: [3,3,5,5,6,7]
 */
public class SlidingWindowMax {
    
    /**
     * Finds maximum values in all sliding windows of size k.
     * 
     * Algorithm:
     * 1. Use deque to store indices of array elements
     * 2. Deque maintains decreasing order of values (front is max for current window)
     * 3. For each element at index i:
     *    a. Remove indices from front that are out of current window (<= i-k)
     *    b. Remove indices from back whose values <= nums[i] (they can't be max)
     *    c. Add current index i to back of deque
     *    d. If window is complete (i >= k-1), front of deque is max for this window
     * 
     * @param nums Input array
     * @param k Window size
     * @return Array containing maximum of each sliding window
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        
        // Handle edge cases
        if (n == 0 || k == 0) {
            return new int[0];
        }
        if (k == 1) {
            return Arrays.copyOf(nums, n); // Each element is its own max
        }
        if (k >= n) {
            // Only one window - find global max
            int max = Integer.MIN_VALUE;
            for (int num : nums) max = Math.max(max, num);
            return new int[]{max};
        }
        
        // Result array: n - k + 1 windows
        int[] result = new int[n - k + 1];
        int resultIdx = 0;
        
        // Deque to store indices (maintains decreasing order of values)
        Deque<Integer> deque = new ArrayDeque<>();
        
        for (int i = 0; i < n; i++) {
            // Step 1: Remove indices that are out of current window from front
            // Window: [i-k+1, i], so indices <= i-k are out of window
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            
            // Step 2: Remove indices from back whose values are <= nums[i]
            // These elements can never be maximum in any future window
            // because nums[i] is larger and will stay in window longer
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            
            // Step 3: Add current index to back of deque
            deque.offerLast(i);
            
            // Step 4: If window is complete, front of deque is maximum
            if (i >= k - 1) {
                result[resultIdx++] = nums[deque.peekFirst()];
            }
        }
        
        return result;
    }
    
    /**
     * Alternative: Brute force solution for verification (O(n*k)).
     * Only for small arrays or testing.
     */
    public int[] maxSlidingWindowBruteForce(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || k == 0) return new int[0];
        
        int[] result = new int[n - k + 1];
        
        for (int i = 0; i <= n - k; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < i + k; j++) {
                max = Math.max(max, nums[j]);
            }
            result[i] = max;
        }
        
        return result;
    }
    
    /**
     * Alternative: Using PriorityQueue (max heap) - O(n log k) solution.
     * Simpler but less efficient for large k.
     */
    public int[] maxSlidingWindowHeap(int[] nums, int k) {
        int n = nums.length;
        if (n == 0 || k == 0) return new int[0];
        
        int[] result = new int[n - k + 1];
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> nums[b] - nums[a]);
        
        for (int i = 0; i < n; i++) {
            // Remove elements that are out of window
            while (!maxHeap.isEmpty() && maxHeap.peek() <= i - k) {
                maxHeap.poll();
            }
            
            maxHeap.offer(i);
            
            if (i >= k - 1) {
                result[i - k + 1] = nums[maxHeap.peek()];
            }
        }
        
        return result;
    }
    
    /**
     * Helper method to visualize the sliding window algorithm.
     */
    public void visualizeSlidingWindow(int[] nums, int k) {
        System.out.println("\n=== Sliding Window Maximum Visualization ===");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Window size k = " + k);
        System.out.println();
        
        Deque<Integer> deque = new ArrayDeque<>();
        int n = nums.length;
        
        System.out.println("Index | Current | Deque (indices) | Window | Max");
        System.out.println("------|---------|-----------------|--------|-----");
        
        for (int i = 0; i < n; i++) {
            // Remove out-of-window indices
            while (!deque.isEmpty() && deque.peekFirst() <= i - k) {
                deque.pollFirst();
            }
            
            // Remove smaller elements from back
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            
            // Add current index
            deque.offerLast(i);
            
            // Determine current window
            String window = "";
            if (i >= k - 1) {
                int start = i - k + 1;
                StringBuilder sb = new StringBuilder("[");
                for (int j = start; j <= i; j++) {
                    sb.append(nums[j]);
                    if (j < i) sb.append(",");
                }
                sb.append("]");
                window = sb.toString();
            }
            
            // Print current state
            System.out.printf("%5d | %7d | %15s | %6s | %s%n",
                i, nums[i],
                deque.toString(),
                window,
                (i >= k - 1) ? String.valueOf(nums[deque.peekFirst()]) : "-"
            );
        }
        
        int[] result = maxSlidingWindow(nums, k);
        System.out.println("\nFinal result: " + Arrays.toString(result));
    }
    
    /**
     * Test cases for sliding window maximum.
     */
    public static void runTestCases() {
        SlidingWindowMax solver = new SlidingWindowMax();
        
        System.out.println("=== Sliding Window Maximum Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {1, 3, -1, -3, 5, 3, 6, 7};
        int k1 = 3;
        System.out.println("Test 1:");
        System.out.println("nums = " + Arrays.toString(test1) + ", k = " + k1);
        int[] result1 = solver.maxSlidingWindow(test1, k1);
        System.out.println("Result: " + Arrays.toString(result1));
        System.out.println("Expected: [3, 3, 5, 5, 6, 7]");
        System.out.println();
        
        // Test 2: Single element windows
        int[] test2 = {4, 2, 1, 8, 5};
        int k2 = 1;
        System.out.println("Test 2 (k=1):");
        System.out.println("nums = " + Arrays.toString(test2) + ", k = " + k2);
        int[] result2 = solver.maxSlidingWindow(test2, k2);
        System.out.println("Result: " + Arrays.toString(result2));
        System.out.println("Expected: [4, 2, 1, 8, 5] (same as input)");
        System.out.println();
        
        // Test 3: Window size equals array length
        int[] test3 = {1, 2, 3, 4, 5};
        int k3 = 5;
        System.out.println("Test 3 (k=n):");
        System.out.println("nums = " + Arrays.toString(test3) + ", k = " + k3);
        int[] result3 = solver.maxSlidingWindow(test3, k3);
        System.out.println("Result: " + Arrays.toString(result3));
        System.out.println("Expected: [5] (global max)");
        System.out.println();
        
        // Test 4: Decreasing sequence
        int[] test4 = {5, 4, 3, 2, 1};
        int k4 = 3;
        System.out.println("Test 4 (decreasing):");
        System.out.println("nums = " + Arrays.toString(test4) + ", k = " + k4);
        int[] result4 = solver.maxSlidingWindow(test4, k4);
        System.out.println("Result: " + Arrays.toString(result4));
        System.out.println("Expected: [5, 4, 3]");
        System.out.println();
        
        // Test 5: Increasing sequence
        int[] test5 = {1, 2, 3, 4, 5};
        int k5 = 3;
        System.out.println("Test 5 (increasing):");
        System.out.println("nums = " + Arrays.toString(test5) + ", k = " + k5);
        int[] result5 = solver.maxSlidingWindow(test5, k5);
        System.out.println("Result: " + Arrays.toString(result5));
        System.out.println("Expected: [3, 4, 5]");
        System.out.println();
        
        // Test 6: Edge cases
        int[] test6 = {};
        int k6 = 3;
        System.out.println("Test 6 (empty array):");
        System.out.println("nums = [], k = " + k6);
        int[] result6 = solver.maxSlidingWindow(test6, k6);
        System.out.println("Result: " + Arrays.toString(result6));
        System.out.println("Expected: []");
        System.out.println();
        
        // Test 7: All equal elements
        int[] test7 = {7, 7, 7, 7, 7};
        int k7 = 2;
        System.out.println("Test 7 (all equal):");
        System.out.println("nums = " + Arrays.toString(test7) + ", k = " + k7);
        int[] result7 = solver.maxSlidingWindow(test7, k7);
        System.out.println("Result: " + Arrays.toString(result7));
        System.out.println("Expected: [7, 7, 7, 7]");
        System.out.println();
        
        // Test 8: Negative numbers
        int[] test8 = {-1, -3, -2, -4, -1, -5};
        int k8 = 2;
        System.out.println("Test 8 (negative numbers):");
        System.out.println("nums = " + Arrays.toString(test8) + ", k = " + k8);
        int[] result8 = solver.maxSlidingWindow(test8, k8);
        System.out.println("Result: " + Arrays.toString(result8));
        System.out.println("Expected: [-1, -2, -2, -1, -1]");
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        SlidingWindowMax solver = new SlidingWindowMax();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test array
        int n = 1000000;
        int k = 1000;
        int[] nums = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            nums[i] = rand.nextInt(10000);
        }
        
        System.out.println("Array size: " + n + ", Window size: " + k);
        
        // Deque solution
        long start = System.currentTimeMillis();
        int[] result1 = solver.maxSlidingWindow(nums, k);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Deque solution (O(n)): " + time1 + " ms");
        
        // Heap solution (only for smaller k)
        if (k <= 10000) {
            start = System.currentTimeMillis();
            int[] result2 = solver.maxSlidingWindowHeap(nums, k);
            long time2 = System.currentTimeMillis() - start;
            System.out.println("Heap solution (O(n log k)): " + time2 + " ms");
            
            // Verify results match
            boolean match = Arrays.equals(result1, result2);
            System.out.println("Results match: " + match);
        }
        
        // Brute force (only for very small)
        if (n <= 10000 && k <= 100) {
            start = System.currentTimeMillis();
            int[] result3 = solver.maxSlidingWindowBruteForce(nums, k);
            long time3 = System.currentTimeMillis() - start;
            System.out.println("Brute force (O(n*k)): " + time3 + " ms");
        }
    }
    
    /**
     * Explain why the deque algorithm works.
     */
    public static void explainAlgorithm() {
        System.out.println("\n=== Why the Deque Algorithm Works ===");
        System.out.println();
        System.out.println("Key Observations:");
        System.out.println("1. We only care about elements that could be maximum in current/future windows");
        System.out.println("2. If we have a new element nums[i] that's larger than previous elements,");
        System.out.println("   those previous elements can never be maximum in any future window");
        System.out.println("   (because nums[i] is larger and will stay in window longer)");
        System.out.println();
        System.out.println("Deque Invariants:");
        System.out.println("1. Indices in deque are in increasing order (from front to back)");
        System.out.println("2. Values at those indices are in decreasing order");
        System.out.println("3. Front of deque is always maximum for current window");
        System.out.println();
        System.out.println("Maintenance Operations:");
        System.out.println("1. Remove from front: Remove indices out of current window");
        System.out.println("2. Remove from back: Remove indices with values <= nums[i]");
        System.out.println("3. Add to back: Add current index");
        System.out.println("4. Get max: Front element is always maximum");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize a specific example
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        SlidingWindowMax solver = new SlidingWindowMax();
        int[] nums = {1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
        solver.visualizeSlidingWindow(nums, k);
        
        // Explain the algorithm
        explainAlgorithm();
        
        // Run benchmark (optional)
        // benchmark();
    }
}