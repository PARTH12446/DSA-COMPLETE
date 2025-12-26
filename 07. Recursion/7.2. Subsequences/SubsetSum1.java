import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Comprehensive implementation of subset sum generation with multiple approaches.
 * Includes optimizations, practical applications, and performance comparisons.
 */
public class SubsetSum1 {

    /**
     * 1. STANDARD RECURSIVE APPROACH
     * Generates all subset sums using DFS/backtracking.
     */
    public static List<Integer> subsetSums(int[] nums) {
        List<Integer> res = new ArrayList<>();
        backtrack(0, nums, 0, res);
        Collections.sort(res);
        return res;
    }

    private static void backtrack(int idx, int[] nums, int sum, List<Integer> res) {
        if (idx == nums.length) {
            res.add(sum);
            return;
        }
        // Include current element
        backtrack(idx + 1, nums, sum + nums[idx], res);
        // Exclude current element
        backtrack(idx + 1, nums, sum, res);
    }

    /**
     * 2. ITERATIVE BIT MANIPULATION APPROACH
     * More intuitive for some, avoids recursion overhead.
     */
    public static List<Integer> subsetSumsIterative(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int n = nums.length;
        int totalSubsets = 1 << n; // 2^n
        
        for (int mask = 0; mask < totalSubsets; mask++) {
            int currentSum = 0;
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    currentSum += nums[i];
                }
            }
            res.add(currentSum);
        }
        Collections.sort(res);
        return res;
    }

    /**
     * 3. UNIQUE SUBSET SUMS ONLY (no duplicates)
     * Useful when only distinct sums matter.
     */
    public static List<Integer> subsetSumsUnique(int[] nums) {
        Set<Integer> uniqueSums = new HashSet<>();
        backtrackUnique(0, nums, 0, uniqueSums);
        List<Integer> res = new ArrayList<>(uniqueSums);
        Collections.sort(res);
        return res;
    }

    private static void backtrackUnique(int idx, int[] nums, int sum, Set<Integer> res) {
        if (idx == nums.length) {
            res.add(sum);
            return;
        }
        backtrackUnique(idx + 1, nums, sum + nums[idx], res);
        backtrackUnique(idx + 1, nums, sum, res);
    }

    /**
     * 4. OPTIMIZED WITH EARLY PRUNING (for non-negative numbers)
     * Can skip branches if we know target range.
     */
    public static List<Integer> subsetSumsWithBounds(int[] nums, int minTarget, int maxTarget) {
        List<Integer> res = new ArrayList<>();
        backtrackWithBounds(0, nums, 0, res, minTarget, maxTarget);
        Collections.sort(res);
        return res;
    }

    private static void backtrackWithBounds(int idx, int[] nums, int sum, 
                                          List<Integer> res, int minTarget, int maxTarget) {
        // Early termination: if sum already exceeds maxTarget (for non-negative nums)
        if (sum > maxTarget && allNonNegative(nums)) {
            return;
        }
        
        if (idx == nums.length) {
            if (sum >= minTarget && sum <= maxTarget) {
                res.add(sum);
            }
            return;
        }
        
        backtrackWithBounds(idx + 1, nums, sum + nums[idx], res, minTarget, maxTarget);
        backtrackWithBounds(idx + 1, nums, sum, res, minTarget, maxTarget);
    }
    
    private static boolean allNonNegative(int[] nums) {
        for (int num : nums) {
            if (num < 0) return false;
        }
        return true;
    }

    /**
     * 5. MEMOIZATION VERSION (Dynamic Programming approach)
     * Efficient for finding IF a particular sum exists.
     * Returns boolean array where dp[s] = true if sum s can be formed.
     */
    public static boolean[] subsetSumDP(int[] nums) {
        int totalSum = 0;
        for (int num : nums) totalSum += Math.abs(num);
        int offset = totalSum; // Handle negative numbers
        int size = 2 * totalSum + 1;
        
        boolean[] dp = new boolean[size];
        dp[offset] = true; // Empty subset sum = 0
        
        for (int num : nums) {
            boolean[] next = new boolean[size];
            for (int s = 0; s < size; s++) {
                if (dp[s]) {
                    next[s] = true; // Don't take current number
                    int newSum = s + num;
                    if (newSum >= 0 && newSum < size) {
                        next[newSum] = true; // Take current number
                    }
                }
            }
            dp = next;
        }
        return dp;
    }

    /**
     * 6. COUNT SUBSET SUMS (number of ways to achieve each sum)
     * Returns count of subsets for each possible sum.
     */
    public static int[] countSubsetSums(int[] nums) {
        int totalSum = 0;
        for (int num : nums) totalSum += Math.abs(num);
        int offset = totalSum;
        int size = 2 * totalSum + 1;
        
        int[] count = new int[size];
        count[offset] = 1; // One way to get sum 0: empty subset
        
        for (int num : nums) {
            int[] next = new int[size];
            for (int s = 0; s < size; s++) {
                if (count[s] > 0) {
                    next[s] += count[s]; // Don't take
                    int newSum = s + num;
                    if (newSum >= 0 && newSum < size) {
                        next[newSum] += count[s]; // Take
                    }
                }
            }
            count = next;
        }
        return count;
    }

    /**
     * COMPREHENSIVE TEST SUITE
     */
    public static void main(String[] args) {
        System.out.println("=== COMPREHENSIVE SUBSET SUM TESTING ===\n");
        
        // Test 1: Basic functionality
        testBasicFunctionality();
        
        // Test 2: Performance comparison
        testPerformance();
        
        // Test 3: Edge cases
        testEdgeCases();
        
        // Test 4: Practical applications
        testPracticalApplications();
    }
    
    private static void testBasicFunctionality() {
        System.out.println("1. BASIC FUNCTIONALITY TESTS");
        
        int[] nums1 = {1, 2, 3};
        System.out.println("\nInput: " + java.util.Arrays.toString(nums1));
        
        List<Integer> sums1 = subsetSums(nums1);
        System.out.println("All subset sums: " + sums1);
        System.out.println("Count: " + sums1.size() + " (should be 2^3 = 8)");
        
        List<Integer> unique1 = subsetSumsUnique(nums1);
        System.out.println("Unique sums: " + unique1);
        System.out.println("Unique count: " + unique1.size());
        
        boolean[] dp1 = subsetSumDP(nums1);
        System.out.println("DP results for sum 3: " + dp1[3 + 6]); // offset = 6
        System.out.println("DP results for sum 10: " + dp1[10 + 6]);
    }
    
    private static void testPerformance() {
        System.out.println("\n\n2. PERFORMANCE COMPARISON");
        
        int[] smallNums = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("\nArray size: 10 (2^10 = 1024 subsets)");
        
        long start = System.nanoTime();
        List<Integer> recursiveResult = subsetSums(smallNums);
        long recursiveTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        List<Integer> iterativeResult = subsetSumsIterative(smallNums);
        long iterativeTime = System.nanoTime() - start;
        
        System.out.println("Recursive time: " + recursiveTime/1000 + " μs");
        System.out.println("Iterative time: " + iterativeTime/1000 + " μs");
        System.out.println("Results match: " + recursiveResult.equals(iterativeResult));
        
        // Show DP efficiency
        start = System.nanoTime();
        boolean[] dp = subsetSumDP(smallNums);
        long dpTime = System.nanoTime() - start;
        System.out.println("DP time (just existence check): " + dpTime/1000 + " μs");
    }
    
    private static void testEdgeCases() {
        System.out.println("\n\n3. EDGE CASES");
        
        // Empty array
        int[] empty = {};
        System.out.println("\nEmpty array: " + subsetSums(empty));
        
        // Single element
        int[] single = {5};
        System.out.println("Single element [5]: " + subsetSums(single));
        
        // All zeros
        int[] zeros = {0, 0, 0};
        System.out.println("All zeros: " + subsetSums(zeros));
        System.out.println("Unique sums for zeros: " + subsetSumsUnique(zeros));
        
        // Negative numbers
        int[] negatives = {-1, 1};
        System.out.println("With negatives [-1, 1]: " + subsetSums(negatives));
    }
    
    private static void testPracticalApplications() {
        System.out.println("\n\n4. PRACTICAL APPLICATIONS");
        
        // Application 1: Partition Problem
        System.out.println("\na) Partition Problem");
        int[] partitionNums = {1, 5, 11, 5};
        List<Integer> sums = subsetSums(partitionNums);
        int total = 0;
        for (int num : partitionNums) total += num;
        System.out.println("Total sum: " + total);
        System.out.println("Can partition equally? " + (total % 2 == 0 && sums.contains(total/2)));
        
        // Application 2: Finding closest sum to target
        System.out.println("\nb) Closest Sum to Target");
        int[] nums = {2, 3, 7, 8, 10};
        int target = 14;
        List<Integer> allSums = subsetSums(nums);
        int closest = allSums.get(0);
        for (int sum : allSums) {
            if (Math.abs(sum - target) < Math.abs(closest - target)) {
                closest = sum;
            }
        }
        System.out.println("Target: " + target);
        System.out.println("Closest achievable sum: " + closest);
        
        // Application 3: Counting subsets with specific sum
        System.out.println("\nc) Counting Subsets with Sum = Target");
        int targetSum = 10;
        int[] countDP = countSubsetSums(nums);
        int offset = 0;
        for (int num : nums) offset += Math.abs(num);
        int ways = countDP[targetSum + offset];
        System.out.println("Number of subsets with sum " + targetSum + ": " + ways);
    }
    
    /**
     * UTILITY: Generate subset sums in real-time with callback
     * Useful for early termination or streaming results.
     */
    public interface SubsetSumCallback {
        void onSumFound(int sum);
    }
    
    public static void generateSumsWithCallback(int[] nums, SubsetSumCallback callback) {
        generateCallback(0, nums, 0, callback);
    }
    
    private static void generateCallback(int idx, int[] nums, int sum, SubsetSumCallback callback) {
        if (idx == nums.length) {
            callback.onSumFound(sum);
            return;
        }
        generateCallback(idx + 1, nums, sum + nums[idx], callback);
        generateCallback(idx + 1, nums, sum, callback);
    }
}