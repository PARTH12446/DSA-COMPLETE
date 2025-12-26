import java.util.*;

/**
 * Maximum Sum Subarray of Size K
 * 
 * Problem: Given an array of integers and a number k,
 * find the maximum sum of any contiguous subarray of size k.
 * 
 * Example: nums = [1, 2, 3, 4, 5, 6], k = 3
 * Subarrays of size 3: [1,2,3]=6, [2,3,4]=9, [3,4,5]=12, [4,5,6]=15
 * Maximum sum = 15
 * 
 * Approaches:
 * 1. Brute force: O(n*k) - Check all subarrays
 * 2. Sliding window: O(n) - Maintain running sum and slide window
 * 
 * Key Insight: When sliding window by 1 position:
 * New sum = Old sum - element leaving window + element entering window
 * This avoids recalculating the entire sum for each window.
 * 
 * Time Complexity: O(n) - Single pass after initial window
 * Space Complexity: O(1) - Only a few variables
 */
public class MaxSumSubarrayOfSizeK {
    
    /**
     * Finds maximum sum of any contiguous subarray of size k.
     * Uses sliding window technique for O(n) time.
     * 
     * Algorithm:
     * 1. Calculate sum of first k elements (initial window)
     * 2. Set this as initial maximum
     * 3. Slide window one position at a time:
     *    - Add next element (entering window)
     *    - Subtract first element of previous window (leaving window)
     *    - Update maximum if current sum is larger
     * 
     * Mathematical Form:
     * Let window[i] = [i, i+k-1]
     * sum(window[i+1]) = sum(window[i]) - nums[i] + nums[i+k]
     * 
     * @param nums Array of integers
     * @param k Size of subarray (must be positive)
     * @return Maximum sum of any contiguous subarray of size k
     */
    public int maxSumOfSizeK(int[] nums, int k) {
        // Edge cases
        if (nums == null || nums.length == 0 || k <= 0) {
            return 0;
        }
        
        // If k is larger than array, we can't have subarray of size k
        if (k > nums.length) {
            // Option 1: Return sum of entire array
            int total = 0;
            for (int num : nums) total += num;
            return total;
            // Option 2: Return 0 or throw exception based on requirements
        }
        
        // Calculate sum of first window
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        
        // Initialize maximum with first window sum
        int maxSum = windowSum;
        
        // Slide window across the array
        for (int right = k; right < nums.length; right++) {
            // right is the index of new element entering window
            // right - k is the index of element leaving window
            windowSum += nums[right] - nums[right - k];
            
            // Update maximum sum
            if (windowSum > maxSum) {
                maxSum = windowSum;
            }
        }
        
        return maxSum;
    }
    
    /**
     * Alternative: Return the actual subarray with maximum sum.
     */
    public int[] maxSumSubarrayOfSizeK(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return new int[0];
        }
        
        // Calculate initial window
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        
        int maxSum = windowSum;
        int startIndex = 0; // Start index of maximum sum subarray
        
        // Slide window
        for (int right = k; right < nums.length; right++) {
            windowSum += nums[right] - nums[right - k];
            
            if (windowSum > maxSum) {
                maxSum = windowSum;
                startIndex = right - k + 1;
            }
        }
        
        // Extract the subarray
        int[] result = new int[k];
        System.arraycopy(nums, startIndex, result, 0, k);
        return result;
    }
    
    /**
     * Alternative: Prefix sum approach.
     * Precompute prefix sums, then calculate window sums in O(1) each.
     */
    public int maxSumOfSizeKPrefixSum(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return 0;
        }
        
        // Compute prefix sum array
        int[] prefixSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        int maxSum = Integer.MIN_VALUE;
        
        // Calculate sum for each window using prefix sums
        // sum[i..j] = prefixSum[j+1] - prefixSum[i]
        for (int i = 0; i <= nums.length - k; i++) {
            int windowSum = prefixSum[i + k] - prefixSum[i];
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return maxSum;
    }
    
    /**
     * Brute force solution for verification (O(n*k)).
     */
    public int maxSumOfSizeKBruteForce(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return 0;
        }
        
        int maxSum = Integer.MIN_VALUE;
        
        for (int start = 0; start <= nums.length - k; start++) {
            int windowSum = 0;
            for (int i = start; i < start + k; i++) {
                windowSum += nums[i];
            }
            maxSum = Math.max(maxSum, windowSum);
        }
        
        return maxSum;
    }
    
    /**
     * Variation: Find minimum sum subarray of size k.
     */
    public int minSumOfSizeK(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            return 0;
        }
        
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        
        int minSum = windowSum;
        
        for (int right = k; right < nums.length; right++) {
            windowSum += nums[right] - nums[right - k];
            minSum = Math.min(minSum, windowSum);
        }
        
        return minSum;
    }
    
    /**
     * Variation: Find average of maximum sum subarray of size k.
     */
    public double maxAverageOfSizeK(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return 0.0;
        }
        
        int maxSum = maxSumOfSizeK(nums, k);
        return (double) maxSum / k;
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeMaxSum(int[] nums, int k) {
        System.out.println("\n=== Maximum Sum Subarray of Size K Visualization ===");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("k = " + k);
        System.out.println();
        
        System.out.println("Window | Window Content | Window Sum | Max So Far");
        System.out.println("-------|----------------|------------|-----------");
        
        // Calculate first window
        int windowSum = 0;
        for (int i = 0; i < k; i++) {
            windowSum += nums[i];
        }
        int maxSum = windowSum;
        
        // Print first window
        String windowContent = Arrays.toString(Arrays.copyOfRange(nums, 0, k));
        System.out.printf("[0,%d] | %14s | %10d | %10d%n",
            k-1, windowContent, windowSum, maxSum);
        
        // Slide window and print each step
        for (int right = k; right < nums.length; right++) {
            int left = right - k + 1;
            windowSum += nums[right] - nums[right - k];
            windowContent = Arrays.toString(Arrays.copyOfRange(nums, left, right + 1));
            
            if (windowSum > maxSum) {
                maxSum = windowSum;
                System.out.printf("[%d,%d] | %14s | %10d | %10d (NEW MAX!)%n",
                    left, right, windowContent, windowSum, maxSum);
            } else {
                System.out.printf("[%d,%d] | %14s | %10d | %10d%n",
                    left, right, windowContent, windowSum, maxSum);
            }
        }
        
        System.out.println("\nMaximum sum of any subarray of size " + k + ": " + maxSum);
    }
    
    /**
     * Test cases for maximum sum subarray of size k.
     */
    public static void runTestCases() {
        MaxSumSubarrayOfSizeK solver = new MaxSumSubarrayOfSizeK();
        
        System.out.println("=== Maximum Sum Subarray of Size K Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {1, 2, 3, 4, 5, 6};
        int k1 = 3;
        System.out.println("Test 1:");
        System.out.println("nums = " + Arrays.toString(test1) + ", k = " + k1);
        int result1 = solver.maxSumOfSizeK(test1, k1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 15 (subarray [4,5,6])");
        
        // Verify with brute force
        int brute1 = solver.maxSumOfSizeKBruteForce(test1, k1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        
        // Show actual subarray
        int[] subarray1 = solver.maxSumSubarrayOfSizeK(test1, k1);
        System.out.println("Maximum sum subarray: " + Arrays.toString(subarray1));
        System.out.println();
        
        // Test 2: All negative numbers
        int[] test2 = {-1, -2, -3, -4, -5};
        int k2 = 2;
        System.out.println("Test 2 (all negative):");
        System.out.println("nums = " + Arrays.toString(test2) + ", k = " + k2);
        int result2 = solver.maxSumOfSizeK(test2, k2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: -3 (subarray [-1,-2])");
        System.out.println();
        
        // Test 3: Mixed positive and negative
        int[] test3 = {2, -1, 3, -2, 4, -3, 5};
        int k3 = 3;
        System.out.println("Test 3 (mixed):");
        System.out.println("nums = " + Arrays.toString(test3) + ", k = " + k3);
        int result3 = solver.maxSumOfSizeK(test3, k3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 6 (subarray [3,-2,4] or [4,-3,5])");
        System.out.println();
        
        // Test 4: k equals array length
        int[] test4 = {1, 2, 3, 4};
        int k4 = 4;
        System.out.println("Test 4 (k = n):");
        System.out.println("nums = " + Arrays.toString(test4) + ", k = " + k4);
        int result4 = solver.maxSumOfSizeK(test4, k4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 10 (sum of all elements)");
        System.out.println();
        
        // Test 5: Single element window
        int[] test5 = {5, 10, 15, 20};
        int k5 = 1;
        System.out.println("Test 5 (k = 1):");
        System.out.println("nums = " + Arrays.toString(test5) + ", k = " + k5);
        int result5 = solver.maxSumOfSizeK(test5, k5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 20 (largest single element)");
        System.out.println();
        
        // Test 6: Empty array
        int[] test6 = {};
        int k6 = 3;
        System.out.println("Test 6 (empty array):");
        System.out.println("nums = [], k = " + k6);
        int result6 = solver.maxSumOfSizeK(test6, k6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test 7: k larger than array (edge case)
        int[] test7 = {1, 2, 3};
        int k7 = 5;
        System.out.println("Test 7 (k > n):");
        System.out.println("nums = " + Arrays.toString(test7) + ", k = " + k7);
        int result7 = solver.maxSumOfSizeK(test7, k7);
        System.out.println("Result: " + result7);
        System.out.println("Expected: 6 (sum of all elements when k > n)");
        
        // Test different implementations
        System.out.println("\nTesting different implementations on same input:");
        int[] test8 = {1, 2, 3, 4, 5};
        int k8 = 2;
        System.out.println("Input: " + Arrays.toString(test8) + ", k = " + k8);
        System.out.println("Sliding window: " + solver.maxSumOfSizeK(test8, k8));
        System.out.println("Prefix sum: " + solver.maxSumOfSizeKPrefixSum(test8, k8));
        System.out.println("Brute force: " + solver.maxSumOfSizeKBruteForce(test8, k8));
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        MaxSumSubarrayOfSizeK solver = new MaxSumSubarrayOfSizeK();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test array
        int n = 1000000;
        int k = 1000;
        int[] nums = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            nums[i] = rand.nextInt(1000) - 500; // Range: -500 to 499
        }
        
        System.out.println("Array size: " + n + ", k = " + k);
        
        // Sliding window approach
        long start = System.currentTimeMillis();
        int result1 = solver.maxSumOfSizeK(nums, k);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Sliding window: " + time1 + " ms, result: " + result1);
        
        // Prefix sum approach
        start = System.currentTimeMillis();
        int result2 = solver.maxSumOfSizeKPrefixSum(nums, k);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Prefix sum: " + time2 + " ms, result: " + result2);
        
        // Brute force (only for small sizes)
        if (n <= 10000 && k <= 100) {
            start = System.currentTimeMillis();
            int result3 = solver.maxSumOfSizeKBruteForce(nums, k);
            long time3 = System.currentTimeMillis() - start;
            System.out.println("Brute force: " + time3 + " ms, result: " + result3);
        }
        
        System.out.println("Results match: " + (result1 == result2));
        System.out.println("Sliding window is fastest for this problem");
    }
    
    /**
     * Explain the sliding window technique.
     */
    public static void explainSlidingWindow() {
        System.out.println("\n=== Sliding Window Technique Explained ===");
        System.out.println();
        System.out.println("Problem: Find sum of all subarrays of size k");
        System.out.println();
        System.out.println("Naive approach (O(n*k)):");
        System.out.println("  For each start position i:");
        System.out.println("    sum = 0");
        System.out.println("    for j = i to i+k-1:");
        System.out.println("      sum += nums[j]");
        System.out.println("    Update max");
        System.out.println();
        System.out.println("Sliding window (O(n)):");
        System.out.println("  Key insight: Consecutive windows share (k-1) elements");
        System.out.println("  Window i: [i, i+1, ..., i+k-1]");
        System.out.println("  Window i+1: [i+1, i+2, ..., i+k]");
        System.out.println("  They differ by only 2 elements!");
        System.out.println();
        System.out.println("  So: sum(window[i+1]) = sum(window[i]) - nums[i] + nums[i+k]");
        System.out.println();
        System.out.println("Algorithm:");
        System.out.println("  1. Calculate sum of first window");
        System.out.println("  2. For i = 1 to n-k:");
        System.out.println("       sum = sum - nums[i-1] + nums[i+k-1]");
        System.out.println("       Update max if needed");
        System.out.println();
        System.out.println("Example: nums = [1,2,3,4,5], k = 3");
        System.out.println("  Window 0: [1,2,3] = 6");
        System.out.println("  Window 1: 6 - 1 + 4 = 9");
        System.out.println("  Window 2: 9 - 2 + 5 = 12");
    }
    
    /**
     * Show related problems and variations.
     */
    public static void showRelatedProblems() {
        System.out.println("\n=== Related Problems and Variations ===");
        
        MaxSumSubarrayOfSizeK solver = new MaxSumSubarrayOfSizeK();
        int[] nums = {1, 2, 3, 4, 5, 6};
        
        System.out.println("Base array: " + Arrays.toString(nums));
        System.out.println();
        
        // Variation 1: Minimum sum
        System.out.println("Variation 1: Minimum sum subarray of size k");
        int minSum = solver.minSumOfSizeK(nums, 3);
        System.out.println("Minimum sum (k=3): " + minSum);
        System.out.println();
        
        // Variation 2: Maximum average
        System.out.println("Variation 2: Maximum average subarray of size k");
        double maxAvg = solver.maxAverageOfSizeK(nums, 3);
        System.out.println("Maximum average (k=3): " + maxAvg);
        System.out.println();
        
        // Variation 3: Sliding window with condition
        System.out.println("Variation 3: Longest subarray with sum ≤ target");
        System.out.println("(Different sliding window pattern)");
        System.out.println();
        
        // Variation 4: Multiple k values
        System.out.println("Variation 4: Maximum sum for different window sizes:");
        for (int k = 1; k <= 4; k++) {
            int maxSum = solver.maxSumOfSizeK(nums, k);
            System.out.println("  k=" + k + ": " + maxSum);
        }
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        MaxSumSubarrayOfSizeK solver = new MaxSumSubarrayOfSizeK();
        int[] nums = {1, 2, 3, 4, 5, 6};
        int k = 3;
        solver.visualizeMaxSum(nums, k);
        
        // Explain the technique
        explainSlidingWindow();
        
        // Show related problems
        showRelatedProblems();
        
        // Run benchmark (optional)
        // benchmark();
    }
}