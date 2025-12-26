import java.util.*;

/**
 * Minimum Size Subarray Sum (LeetCode 209)
 * 
 * Problem: Given an array of positive integers nums and a positive integer target,
 * return the minimal length of a contiguous subarray whose sum is greater than or
 * equal to target. If there is no such subarray, return 0.
 * 
 * Example: nums = [2, 3, 1, 2, 4, 3], target = 7
 * Subarray [4, 3] has sum 7 and length 2
 * Subarray [2, 3, 1, 2] has sum 8 and length 4
 * Minimum length = 2
 * 
 * Approaches:
 * 1. Brute force: O(n²) - Check all subarrays
 * 2. Sliding window: O(n) - Expand right, shrink left when sum ≥ target
 * 3. Prefix sum + binary search: O(n log n) - For each start, binary search end
 * 
 * Key Insight: Since all numbers are positive, sliding window sum increases
 * when expanding right and decreases when shrinking left.
 * 
 * Time Complexity: O(n) - Each element processed at most twice
 * Space Complexity: O(1) - Only a few variables
 */
public class SmallestSubarrayWithSumAtLeastK {
    
    /**
     * Finds minimum length of contiguous subarray with sum ≥ target.
     * Uses sliding window technique for O(n) time.
     * 
     * Algorithm:
     * 1. Maintain window [left, right] with sum < target
     * 2. Expand right pointer until sum ≥ target
     * 3. When sum ≥ target:
     *    a. Record window length (right - left + 1)
     *    b. Shrink from left while sum ≥ target, updating minimum length
     * 4. Continue until all elements processed
     * 
     * Why it works (positivity of numbers is crucial):
     * - When we find a valid window, shrinking can only reduce sum
     * - Expanding can only increase sum (since numbers are positive)
     * - This monotonic property enables sliding window
     * 
     * @param target Minimum sum required
     * @param nums Array of positive integers
     * @return Minimum subarray length, or 0 if no such subarray exists
     */
    public int minSubArrayLen(int target, int[] nums) {
        // Edge cases
        if (nums == null || nums.length == 0 || target <= 0) {
            return 0;
        }
        
        // Check if any single element meets target
        for (int num : nums) {
            if (num >= target) {
                return 1; // Single element suffices
            }
        }
        
        int left = 0;           // Left pointer of sliding window
        int currentSum = 0;     // Sum of current window
        int minLength = Integer.MAX_VALUE;
        
        for (int right = 0; right < nums.length; right++) {
            // Add current element to window
            currentSum += nums[right];
            
            // While window sum is at least target, try to shrink
            while (currentSum >= target && left <= right) {
                // Update minimum length
                int currentLength = right - left + 1;
                minLength = Math.min(minLength, currentLength);
                
                // Shrink window from left
                currentSum -= nums[left];
                left++;
            }
        }
        
        // Return result (0 if no valid subarray found)
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    /**
     * Alternative: Prefix sum + binary search approach.
     * Useful when need O(n log n) solution or when numbers can be negative.
     * 
     * Algorithm:
     * 1. Compute prefix sums
     * 2. For each starting position i, binary search for smallest j where
     *    prefix[j] - prefix[i] >= target
     * 3. Track minimum (j - i)
     * 
     * Time: O(n log n), Space: O(n)
     */
    public int minSubArrayLenBinarySearch(int target, int[] nums) {
        if (nums == null || nums.length == 0 || target <= 0) {
            return 0;
        }
        
        int n = nums.length;
        int minLength = Integer.MAX_VALUE;
        
        // Compute prefix sums
        int[] prefixSum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        // For each starting position, binary search for ending position
        for (int start = 0; start < n; start++) {
            // Find smallest end where sum[start..end] >= target
            int left = start, right = n;
            
            while (left < right) {
                int mid = left + (right - left) / 2;
                int currentSum = prefixSum[mid + 1] - prefixSum[start];
                
                if (currentSum >= target) {
                    right = mid; // Try smaller end
                } else {
                    left = mid + 1; // Need larger end
                }
            }
            
            // Check if we found a valid end position
            if (left < n) {
                int currentSum = prefixSum[left + 1] - prefixSum[start];
                if (currentSum >= target) {
                    minLength = Math.min(minLength, left - start + 1);
                }
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    /**
     * Variation: Return the actual smallest subarray.
     */
    public int[] minSubArray(int target, int[] nums) {
        if (nums == null || nums.length == 0 || target <= 0) {
            return new int[0];
        }
        
        int left = 0, sum = 0;
        int minLength = Integer.MAX_VALUE;
        int bestLeft = 0, bestRight = -1;
        
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            
            while (sum >= target) {
                int currentLength = right - left + 1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                    bestLeft = left;
                    bestRight = right;
                }
                sum -= nums[left++];
            }
        }
        
        if (minLength == Integer.MAX_VALUE) {
            return new int[0];
        }
        
        return Arrays.copyOfRange(nums, bestLeft, bestRight + 1);
    }
    
    /**
     * Variation: Handle negative numbers (more complex).
     * Uses monotonic deque to maintain minimum prefix sums.
     */
    public int minSubArrayLenWithNegatives(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int n = nums.length;
        int minLength = Integer.MAX_VALUE;
        int[] prefixSum = new int[n + 1];
        
        // Compute prefix sums
        for (int i = 0; i < n; i++) {
            prefixSum[i + 1] = prefixSum[i] + nums[i];
        }
        
        // Monotonic deque to store indices of increasing prefix sums
        Deque<Integer> deque = new ArrayDeque<>();
        
        for (int i = 0; i <= n; i++) {
            // Remove from back while current prefix sum is smaller or equal
            while (!deque.isEmpty() && prefixSum[i] <= prefixSum[deque.peekLast()]) {
                deque.pollLast();
            }
            
            // Remove from front while we have a valid subarray
            while (!deque.isEmpty() && prefixSum[i] - prefixSum[deque.peekFirst()] >= target) {
                minLength = Math.min(minLength, i - deque.pollFirst());
            }
            
            deque.offerLast(i);
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    /**
     * Brute force solution for verification (O(n²)).
     */
    public int minSubArrayLenBruteForce(int target, int[] nums) {
        if (nums == null || nums.length == 0 || target <= 0) return 0;
        
        int minLength = Integer.MAX_VALUE;
        int n = nums.length;
        
        for (int start = 0; start < n; start++) {
            int sum = 0;
            for (int end = start; end < n; end++) {
                sum += nums[end];
                if (sum >= target) {
                    minLength = Math.min(minLength, end - start + 1);
                    break; // Further extensions will only make subarray longer
                }
            }
        }
        
        return minLength == Integer.MAX_VALUE ? 0 : minLength;
    }
    
    /**
     * Variation: Maximum size subarray with sum ≤ target.
     * Find longest subarray with sum at most target.
     */
    public int maxSubArrayLenAtMost(int target, int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int left = 0, sum = 0, maxLength = 0;
        
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            
            // Shrink while sum > target
            while (sum > target) {
                sum -= nums[left++];
            }
            
            // Update maximum length (current window sum ≤ target)
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeMinSubarray(int target, int[] nums) {
        System.out.println("\n=== Minimum Size Subarray Sum Visualization ===");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("Target sum: " + target);
        System.out.println();
        
        System.out.println("Window | Window Content | Sum | Action | Min Length So Far");
        System.out.println("-------|----------------|-----|--------|-------------------");
        
        int left = 0, sum = 0, minLength = Integer.MAX_VALUE;
        
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            String action = "Add " + nums[right];
            
            // Check if we have a valid window
            if (sum >= target) {
                int currentLength = right - left + 1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                    action += " → Found new min length: " + currentLength;
                } else {
                    action += " → Found length: " + currentLength;
                }
            }
            
            // Print current state
            String windowContent = Arrays.toString(
                Arrays.copyOfRange(nums, left, right + 1)
            );
            String minStr = minLength == Integer.MAX_VALUE ? "∞" : String.valueOf(minLength);
            
            System.out.printf("[%d,%d] | %14s | %3d | %6s | %17s%n",
                left, right, windowContent, sum, action, minStr);
            
            // Shrink window if possible
            while (sum >= target) {
                // Shrink and print
                sum -= nums[left];
                left++;
                action = "Remove " + nums[left-1];
                
                if (sum >= target) {
                    int currentLength = right - left + 1;
                    if (currentLength < minLength) {
                        minLength = currentLength;
                        action += " → New min length: " + currentLength;
                    } else {
                        action += " → Length: " + currentLength;
                    }
                }
                
                windowContent = Arrays.toString(
                    Arrays.copyOfRange(nums, left, right + 1)
                );
                minStr = minLength == Integer.MAX_VALUE ? "∞" : String.valueOf(minLength);
                
                System.out.printf("[%d,%d] | %14s | %3d | %6s | %17s%n",
                    left, right, windowContent, sum, action, minStr);
            }
        }
        
        System.out.println("\nMinimum subarray length with sum ≥ " + target + ": " + 
                          (minLength == Integer.MAX_VALUE ? 0 : minLength));
    }
    
    /**
     * Test cases for minimum size subarray sum.
     */
    public static void runTestCases() {
        SmallestSubarrayWithSumAtLeastK solver = new SmallestSubarrayWithSumAtLeastK();
        
        System.out.println("=== Minimum Size Subarray Sum Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {2, 3, 1, 2, 4, 3};
        int target1 = 7;
        System.out.println("Test 1:");
        System.out.println("nums = " + Arrays.toString(test1) + ", target = " + target1);
        int result1 = solver.minSubArrayLen(target1, test1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 2 (subarray [4,3])");
        
        // Verify with brute force
        int brute1 = solver.minSubArrayLenBruteForce(target1, test1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        
        // Show actual subarray
        int[] subarray1 = solver.minSubArray(target1, test1);
        System.out.println("Minimum subarray: " + Arrays.toString(subarray1));
        System.out.println();
        
        // Test 2: Single element solution
        int[] test2 = {1, 4, 4};
        int target2 = 4;
        System.out.println("Test 2 (single element solution):");
        System.out.println("nums = " + Arrays.toString(test2) + ", target = " + target2);
        int result2 = solver.minSubArrayLen(target2, test2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 1 (single element 4)");
        System.out.println();
        
        // Test 3: Whole array needed
        int[] test3 = {1, 1, 1, 1, 1, 1};
        int target3 = 6;
        System.out.println("Test 3 (whole array needed):");
        System.out.println("nums = " + Arrays.toString(test3) + ", target = " + target3);
        int result3 = solver.minSubArrayLen(target3, test3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 6 (all elements)");
        System.out.println();
        
        // Test 4: No solution
        int[] test4 = {1, 2, 3, 4};
        int target4 = 11;
        System.out.println("Test 4 (no solution):");
        System.out.println("nums = " + Arrays.toString(test4) + ", target = " + target4);
        int result4 = solver.minSubArrayLen(target4, test4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test 5: Multiple valid subarrays
        int[] test5 = {1, 2, 3, 4, 5, 6, 7, 8};
        int target5 = 20;
        System.out.println("Test 5 (multiple solutions):");
        System.out.println("nums = " + Arrays.toString(test5) + ", target = " + target5);
        int result5 = solver.minSubArrayLen(target5, test5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 3 (6+7+8 = 21) or (5+6+7 = 18, 5+6+7+8 = 26)");
        System.out.println();
        
        // Test 6: Large numbers
        int[] test6 = {10, 20, 30, 40, 50};
        int target6 = 70;
        System.out.println("Test 6 (large numbers):");
        System.out.println("nums = " + Arrays.toString(test6) + ", target = " + target6);
        int result6 = solver.minSubArrayLen(target6, test6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 2 (30+40)");
        System.out.println();
        
        // Test 7: Edge cases
        int[] test7 = {};
        int target7 = 1;
        System.out.println("Test 7 (empty array):");
        System.out.println("nums = [], target = " + target7);
        int result7 = solver.minSubArrayLen(target7, test7);
        System.out.println("Result: " + result7);
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test binary search approach
        System.out.println("Testing binary search approach:");
        int result8 = solver.minSubArrayLenBinarySearch(target1, test1);
        System.out.println("Binary search result: " + result8 + " (matches: " + (result1 == result8) + ")");
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        SmallestSubarrayWithSumAtLeastK solver = new SmallestSubarrayWithSumAtLeastK();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test array
        int n = 1000000;
        int[] nums = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            nums[i] = rand.nextInt(100) + 1; // Positive numbers 1-100
        }
        int target = 10000;
        
        System.out.println("Array size: " + n + ", target = " + target);
        
        // Sliding window approach
        long start = System.currentTimeMillis();
        int result1 = solver.minSubArrayLen(target, nums);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Sliding window: " + time1 + " ms, result: " + result1);
        
        // Binary search approach
        start = System.currentTimeMillis();
        int result2 = solver.minSubArrayLenBinarySearch(target, nums);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Binary search: " + time2 + " ms, result: " + result2);
        
        System.out.println("Results match: " + (result1 == result2));
        System.out.println("Sliding window is faster for this problem (O(n) vs O(n log n))");
    }
    
    /**
     * Explain why sliding window works (importance of positive numbers).
     */
    public static void explainSlidingWindow() {
        System.out.println("\n=== Why Sliding Window Works ===");
        System.out.println();
        System.out.println("Crucial Assumption: All numbers are positive");
        System.out.println();
        System.out.println("Key Properties:");
        System.out.println("1. Monotonic Increase: When we add an element (expand right),");
        System.out.println("   the window sum can only increase");
        System.out.println("2. Monotonic Decrease: When we remove an element (shrink left),");
        System.out.println("   the window sum can only decrease");
        System.out.println();
        System.out.println("Why these properties matter:");
        System.out.println("- If current window sum < target, we must expand right");
        System.out.println("  (shrinking would only make sum smaller)");
        System.out.println("- If current window sum ≥ target, we can try shrinking left");
        System.out.println("  to find potentially smaller valid windows");
        System.out.println();
        System.out.println("Counterexample with negative numbers:");
        System.out.println("  nums = [10, -9, 10], target = 11");
        System.out.println("  Window [10, -9] sum = 1 < 11 → expand");
        System.out.println("  Window [10, -9, 10] sum = 11 ≥ target");
        System.out.println("  But we missed [10, 10] which has sum 20");
        System.out.println("  With negatives, shrinking can INCREASE sum!");
        System.out.println();
        System.out.println("For arrays with negative numbers:");
        System.out.println("  Use prefix sum + monotonic deque (O(n))");
        System.out.println("  Or prefix sum + binary search (O(n log n))");
    }
    
    /**
     * Show related problems and variations.
     */
    public static void showRelatedProblems() {
        System.out.println("\n=== Related Problems and Variations ===");
        
        SmallestSubarrayWithSumAtLeastK solver = new SmallestSubarrayWithSumAtLeastK();
        
        // Variation 1: Subarray with sum exactly equal to target
        System.out.println("Variation 1: Subarray with sum exactly equal to target");
        System.out.println("  Can use HashMap to store prefix sums");
        System.out.println("  Time: O(n), Space: O(n)");
        System.out.println();
        
        // Variation 2: Longest subarray with sum at most target
        System.out.println("Variation 2: Longest subarray with sum ≤ target");
        int[] nums1 = {1, 2, 3, 4, 5};
        int target1 = 10;
        int maxLen = solver.maxSubArrayLenAtMost(target1, nums1);
        System.out.println("  nums = " + Arrays.toString(nums1) + ", target = " + target1);
        System.out.println("  Maximum length with sum ≤ target: " + maxLen);
        System.out.println("  Expected: 4 (1+2+3+4 = 10)");
        System.out.println();
        
        // Variation 3: Minimum subarray with sum ≥ target (circular array)
        System.out.println("Variation 3: Circular array version");
        System.out.println("  Array wraps around (LeetCode 918)");
        System.out.println("  Can duplicate array or use prefix sums");
        System.out.println();
        
        // Variation 4: Two constraints (sum ≥ target and length ≤ maxLen)
        System.out.println("Variation 4: Subarray with sum ≥ target and length ≤ maxLen");
        System.out.println("  Need to track both constraints");
        System.out.println("  Can use sliding window with additional checks");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        SmallestSubarrayWithSumAtLeastK solver = new SmallestSubarrayWithSumAtLeastK();
        int[] nums = {2, 3, 1, 2, 4, 3};
        int target = 7;
        solver.visualizeMinSubarray(target, nums);
        
        // Explain why sliding window works
        explainSlidingWindow();
        
        // Show related problems
        showRelatedProblems();
        
        // Run benchmark (optional)
        // benchmark();
    }
}