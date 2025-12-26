import java.util.*;

/**
 * Binary Subarrays With Sum (LeetCode 930)
 * 
 * Problem: Given a binary array nums (0s and 1s) and an integer goal,
 * return the number of non-empty subarrays with sum equal to goal.
 * 
 * Example: nums = [1,0,1,0,1], goal = 2
 * Subarrays with sum 2: [1,0,1], [1,0,1,0], [0,1,0,1], [1,0,1]
 * Result: 4
 * 
 * Approach 1: Prefix Sum + HashMap (O(n) time, O(n) space)
 * Approach 2: Sliding Window "atMost" technique (O(n) time, O(1) space) - used here
 * 
 * Key Insight: 
 * Number of subarrays with sum exactly K 
 * = Number of subarrays with sum ≤ K - Number of subarrays with sum ≤ (K-1)
 * 
 * Time Complexity: O(n) - Two passes with sliding window
 * Space Complexity: O(1) - Only a few variables
 */
public class BinarySubarrayWithSum {
    
    /**
     * Counts number of subarrays with sum exactly equal to goal.
     * Uses the "atMost" sliding window technique.
     * 
     * Mathematical Insight:
     * count(exactly K) = count(atMost K) - count(atMost K-1)
     * 
     * Why this works:
     * - atMost(K) counts all subarrays with sum ≤ K
     * - atMost(K-1) counts all subarrays with sum ≤ K-1
     * - Their difference gives subarrays with sum exactly K
     * 
     * @param nums Binary array containing only 0s and 1s
     * @param goal Target sum to achieve
     * @return Number of non-empty subarrays with sum exactly equal to goal
     */
    public int numSubarraysWithSum(int[] nums, int goal) {
        // Edge case: empty array
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // Apply the formula: exactly(K) = atMost(K) - atMost(K-1)
        return atMost(nums, goal) - atMost(nums, goal - 1);
    }
    
    /**
     * Counts number of subarrays with sum at most (≤) goal.
     * Uses sliding window technique.
     * 
     * Algorithm:
     * 1. Maintain window [left, right] where sum ≤ goal
     * 2. Expand right pointer, add nums[right] to sum
     * 3. While sum > goal, shrink from left
     * 4. For each valid window, all subarrays ending at right are valid:
     *    Number of such subarrays = right - left + 1
     * 
     * Example: nums = [1,0,1,0,1], goal = 2
     * Window [0,2] sum=2 → 3 subarrays: [1], [1,0], [1,0,1]
     * 
     * @param nums Binary array
     * @param goal Maximum allowed sum
     * @return Number of subarrays with sum ≤ goal
     */
    private int atMost(int[] nums, int goal) {
        // If goal is negative, no subarray can have sum ≤ goal
        if (goal < 0) {
            return 0;
        }
        
        int left = 0;       // Left pointer of sliding window
        int sum = 0;        // Current sum of window
        int count = 0;      // Count of valid subarrays
        
        for (int right = 0; right < nums.length; right++) {
            // Expand window to include nums[right]
            sum += nums[right];
            
            // Shrink window while sum exceeds goal
            while (sum > goal) {
                sum -= nums[left];
                left++;
            }
            
            // All subarrays ending at right with start in [left, right] are valid
            // Number of such subarrays = (right - left + 1)
            count += right - left + 1;
        }
        
        return count;
    }
    
    /**
     * Alternative: Prefix Sum + HashMap solution.
     * More general (works for non-binary arrays too).
     * 
     * Algorithm:
     * 1. Calculate prefix sum array
     * 2. For each position, if prefixSum[i] - goal exists in map,
     *    it means there's a subarray ending at i with sum goal
     * 3. Store prefix sums in hashmap with frequencies
     * 
     * Time: O(n), Space: O(n)
     */
    public int numSubarraysWithSumPrefixSum(int[] nums, int goal) {
        // Map stores frequency of prefix sums encountered so far
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1); // Empty subarray has sum 0
        
        int prefixSum = 0;
        int count = 0;
        
        for (int num : nums) {
            prefixSum += num;
            
            // If (prefixSum - goal) exists in map, it means there's a subarray
            // ending at current position with sum exactly goal
            count += prefixSumCount.getOrDefault(prefixSum - goal, 0);
            
            // Update frequency of current prefix sum
            prefixSumCount.put(prefixSum, prefixSumCount.getOrDefault(prefixSum, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * Brute force solution for verification (O(n²)).
     */
    public int numSubarraysWithSumBruteForce(int[] nums, int goal) {
        int count = 0;
        int n = nums.length;
        
        for (int start = 0; start < n; start++) {
            int sum = 0;
            for (int end = start; end < n; end++) {
                sum += nums[end];
                if (sum == goal) {
                    count++;
                }
                // Optional early break if sum exceeds goal (since all numbers are non-negative)
                if (sum > goal) {
                    break;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Variation: Count subarrays with sum at least goal.
     * Uses complementary counting.
     */
    public int numSubarraysWithSumAtLeast(int[] nums, int goal) {
        int totalSubarrays = nums.length * (nums.length + 1) / 2;
        int atMostGoalMinus1 = atMost(nums, goal - 1);
        return totalSubarrays - atMostGoalMinus1;
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeAtMost(int[] nums, int goal) {
        System.out.println("\n=== Visualizing atMost(" + goal + ") ===");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println();
        
        System.out.println("Window | Sum | Action | Valid Subarrays Ending at Right | Count");
        System.out.println("-------|-----|--------|---------------------------------|------");
        
        int left = 0;
        int sum = 0;
        int totalCount = 0;
        
        for (int right = 0; right < nums.length; right++) {
            // Expand window
            sum += nums[right];
            
            // Shrink if needed
            String action = "Expand to " + right;
            while (sum > goal) {
                sum -= nums[left];
                left++;
                action = "Shrink from left, left=" + left;
            }
            
            // Calculate valid subarrays
            int validSubarrays = right - left + 1;
            totalCount += validSubarrays;
            
            // Print current state
            String window = "[" + left + "," + right + "]";
            String subarraysDesc = "";
            for (int start = left; start <= right; start++) {
                if (!subarraysDesc.isEmpty()) subarraysDesc += ", ";
                subarraysDesc += "[" + start + ".." + right + "]";
            }
            
            System.out.printf("%6s | %3d | %6s | %31s | %5d%n",
                window, sum, action, subarraysDesc, validSubarrays);
        }
        
        System.out.println("\nTotal count for atMost(" + goal + "): " + totalCount);
    }
    
    /**
     * Test cases for binary subarray sum problem.
     */
    public static void runTestCases() {
        BinarySubarrayWithSum solver = new BinarySubarrayWithSum();
        
        System.out.println("=== Binary Subarray Sum Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {1, 0, 1, 0, 1};
        int goal1 = 2;
        System.out.println("Test 1:");
        System.out.println("nums = " + Arrays.toString(test1) + ", goal = " + goal1);
        int result1 = solver.numSubarraysWithSum(test1, goal1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 4");
        
        // Verify with brute force
        int brute1 = solver.numSubarraysWithSumBruteForce(test1, goal1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        System.out.println();
        
        // Test 2: All zeros
        int[] test2 = {0, 0, 0, 0, 0};
        int goal2 = 0;
        System.out.println("Test 2 (all zeros, goal=0):");
        System.out.println("nums = " + Arrays.toString(test2) + ", goal = " + goal2);
        int result2 = solver.numSubarraysWithSum(test2, goal2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 15 (all subarrays of 5 elements)");
        System.out.println();
        
        // Test 3: All ones
        int[] test3 = {1, 1, 1, 1};
        int goal3 = 2;
        System.out.println("Test 3 (all ones):");
        System.out.println("nums = " + Arrays.toString(test3) + ", goal = " + goal3);
        int result3 = solver.numSubarraysWithSum(test3, goal3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 3 ([0-1], [1-2], [2-3])");
        System.out.println();
        
        // Test 4: Single element
        int[] test4 = {1};
        int goal4 = 1;
        System.out.println("Test 4 (single element):");
        System.out.println("nums = " + Arrays.toString(test4) + ", goal = " + goal4);
        int result4 = solver.numSubarraysWithSum(test4, goal4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 1");
        System.out.println();
        
        // Test 5: No subarray with sum
        int[] test5 = {0, 0, 0, 0};
        int goal5 = 1;
        System.out.println("Test 5 (no matching subarray):");
        System.out.println("nums = " + Arrays.toString(test5) + ", goal = " + goal5);
        int result5 = solver.numSubarraysWithSum(test5, goal5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test 6: Mixed with goal = 0
        int[] test6 = {0, 1, 0, 0, 1, 0};
        int goal6 = 0;
        System.out.println("Test 6 (goal=0):");
        System.out.println("nums = " + Arrays.toString(test6) + ", goal = " + goal6);
        int result6 = solver.numSubarraysWithSum(test6, goal6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 9 (only subarrays containing only 0s)");
        System.out.println();
        
        // Test 7: Large goal
        int[] test7 = {1, 1, 0, 1, 1, 0, 1};
        int goal7 = 3;
        System.out.println("Test 7:");
        System.out.println("nums = " + Arrays.toString(test7) + ", goal = " + goal7);
        int result7 = solver.numSubarraysWithSum(test7, goal7);
        System.out.println("Result: " + result7);
        System.out.println("Brute force: " + solver.numSubarraysWithSumBruteForce(test7, goal7));
        System.out.println();
    }
    
    /**
     * Compare different approaches.
     */
    public static void compareApproaches() {
        System.out.println("=== Approach Comparison ===");
        
        BinarySubarrayWithSum solver = new BinarySubarrayWithSum();
        int[] nums = {1, 0, 1, 0, 1, 0, 0, 1, 1, 0};
        int goal = 3;
        
        System.out.println("nums = " + Arrays.toString(nums));
        System.out.println("goal = " + goal);
        System.out.println();
        
        System.out.println("1. Sliding Window (atMost technique):");
        long start = System.nanoTime();
        int result1 = solver.numSubarraysWithSum(nums, goal);
        long time1 = System.nanoTime() - start;
        System.out.println("   Result: " + result1 + ", Time: " + time1 + " ns");
        
        System.out.println("2. Prefix Sum + HashMap:");
        start = System.nanoTime();
        int result2 = solver.numSubarraysWithSumPrefixSum(nums, goal);
        long time2 = System.nanoTime() - start;
        System.out.println("   Result: " + result2 + ", Time: " + time2 + " ns");
        
        System.out.println("3. Brute Force (for verification):");
        start = System.nanoTime();
        int result3 = solver.numSubarraysWithSumBruteForce(nums, goal);
        long time3 = System.nanoTime() - start;
        System.out.println("   Result: " + result3 + ", Time: " + time3 + " ns");
        
        System.out.println("\nAll results match: " + 
                          (result1 == result2 && result2 == result3));
        System.out.println("Speedup vs brute force: " + (time3 * 1.0 / time1) + "x");
    }
    
    /**
     * Mathematical explanation of the "atMost" technique.
     */
    public static void explainMathematics() {
        System.out.println("\n=== Mathematical Explanation ===");
        System.out.println();
        System.out.println("Let f(K) = number of subarrays with sum ≤ K");
        System.out.println("We want g(K) = number of subarrays with sum exactly K");
        System.out.println();
        System.out.println("Observation: All subarrays can be categorized by their sum:");
        System.out.println("1. Sum < K");
        System.out.println("2. Sum = K");
        System.out.println("3. Sum > K");
        System.out.println();
        System.out.println("Therefore: f(K) = #(sum < K) + #(sum = K)");
        System.out.println("And: f(K-1) = #(sum < K)");
        System.out.println();
        System.out.println("Subtracting: f(K) - f(K-1) = #(sum = K)");
        System.out.println("So: g(K) = f(K) - f(K-1)");
        System.out.println();
        System.out.println("Why f(K) is easier to compute?");
        System.out.println("- For binary arrays, we can use sliding window");
        System.out.println("- Window maintains sum ≤ K");
        System.out.println("- When we add an element at position 'right':");
        System.out.println("  All subarrays ending at 'right' with start ≥ 'left' are valid");
        System.out.println("  Count = right - left + 1");
    }
    
    /**
     * Generalize to arrays with arbitrary integers (not just 0/1).
     * This requires prefix sum + hashmap approach.
     */
    public static void generalizeToArbitraryIntegers() {
        System.out.println("\n=== Generalization to Arbitrary Integers ===");
        System.out.println();
        System.out.println("For arrays with arbitrary integers (positive/negative):");
        System.out.println("1. Sliding window 'atMost' technique doesn't work");
        System.out.println("   - Need monotonic property (non-negative numbers)");
        System.out.println("   - Negative numbers can break the window shrinking logic");
        System.out.println();
        System.out.println("2. Prefix Sum + HashMap approach still works:");
        System.out.println("   - Store frequency of prefix sums");
        System.out.println("   - For current prefixSum, count occurrences of (prefixSum - goal)");
        System.out.println("   - Works for any integers (positive/negative/zero)");
        System.out.println();
        System.out.println("Example with negative numbers:");
        int[] nums = {1, -1, 1, -1, 1};
        int goal = 1;
        BinarySubarrayWithSum solver = new BinarySubarrayWithSum();
        int result = solver.numSubarraysWithSumPrefixSum(nums, goal);
        System.out.println("nums = " + Arrays.toString(nums) + ", goal = " + goal);
        System.out.println("Result: " + result);
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the sliding window technique
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Visualization of atMost Technique:");
        System.out.println("=".repeat(60));
        
        BinarySubarrayWithSum solver = new BinarySubarrayWithSum();
        int[] nums = {1, 0, 1, 0, 1};
        int goal = 2;
        solver.visualizeAtMost(nums, goal);
        
        // Show atMost(1) to demonstrate the subtraction
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Calculating exactly(2) = atMost(2) - atMost(1)");
        System.out.println("=".repeat(60));
        
        int atMost2 = solver.atMost(nums, 2);
        int atMost1 = solver.atMost(nums, 1);
        int exactly2 = atMost2 - atMost1;
        
        System.out.println("atMost(2) = " + atMost2);
        System.out.println("atMost(1) = " + atMost1);
        System.out.println("exactly(2) = " + atMost2 + " - " + atMost1 + " = " + exactly2);
        
        // Compare approaches
        compareApproaches();
        
        // Explain the mathematics
        explainMathematics();
        
        // Show generalization
        generalizeToArbitraryIntegers();
    }
}