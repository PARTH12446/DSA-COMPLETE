import java.util.*;

/**
 * Max Consecutive Ones III (LeetCode 1004)
 * 
 * Problem: Given a binary array nums and an integer k, return the maximum
 * number of consecutive 1's in the array if you can flip at most k 0's.
 * 
 * Example: nums = [1,1,1,0,0,0,1,1,1,1,0], k = 2
 * We can flip two 0's to 1's to get longer sequence of consecutive 1's.
 * 
 * Problem Restatement: Find the longest contiguous subarray containing
 * at most k zeros (which we can flip to ones).
 * 
 * Approaches:
 * 1. Brute force: O(n²) - Check all subarrays
 * 2. Sliding window: O(n) - Maintain window with ≤ k zeros
 * 
 * Key Insight: This is equivalent to finding longest subarray with ≤ k zeros.
 * We can flip zeros to ones, so zeros in the window don't break continuity
 * as long as we don't exceed k.
 * 
 * Time Complexity: O(n) - Each element processed at most twice
 * Space Complexity: O(1) - Only a few variables
 */
public class MaxConsecutiveOnesIII {
    
    /**
     * Finds maximum consecutive ones after flipping at most k zeros.
     * 
     * Algorithm (Sliding Window):
     * 1. Maintain window [left, right] with at most k zeros
     * 2. Expand right pointer, count zeros encountered
     * 3. While zeros > k, shrink from left
     * 4. Track maximum window length
     * 
     * Why it works:
     * - Each zero in window can be flipped to 1
     * - Window with ≤ k zeros can be converted to all 1's
     * - Maximum window length = maximum consecutive 1's after flipping
     * 
     * @param nums Binary array (0's and 1's)
     * @param k Maximum zeros that can be flipped
     * @return Maximum consecutive ones possible after flipping at most k zeros
     */
    public int longestOnes(int[] nums, int k) {
        // Edge cases
        if (nums == null || nums.length == 0) {
            return 0;
        }
        if (k >= nums.length) {
            return nums.length; // Can flip all zeros if needed
        }
        
        int left = 0;           // Left pointer of sliding window
        int zeroCount = 0;      // Count of zeros in current window
        int maxLength = 0;      // Maximum valid window length
        
        for (int right = 0; right < nums.length; right++) {
            // Add element at 'right' to window
            if (nums[right] == 0) {
                zeroCount++;
            }
            
            // Shrink window if we have more than k zeros
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
            }
            
            // Update maximum window length
            int currentLength = right - left + 1;
            maxLength = Math.max(maxLength, currentLength);
        }
        
        return maxLength;
    }
    
    /**
     * Optimized version without inner while loop.
     * Uses the fact that once we exceed k, we need to move left.
     * More efficient when k is small.
     */
    public int longestOnesOptimized(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;
        
        int left = 0, zeroCount = 0, maxLength = 0;
        
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) {
                zeroCount++;
            }
            
            // If we exceed k zeros, move left until we're back to ≤ k
            if (zeroCount > k) {
                // Move left until we remove a zero
                while (nums[left] != 0) {
                    left++;
                }
                // Now nums[left] is 0, skip it
                left++;
                zeroCount--;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Alternative: Using prefix sums to find zeros.
     * Precompute zero positions for binary search.
     */
    public int longestOnesPrefixSum(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;
        
        // Create prefix sum of zeros
        int n = nums.length;
        int[] prefixZeros = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixZeros[i + 1] = prefixZeros[i] + (nums[i] == 0 ? 1 : 0);
        }
        
        int maxLength = 0;
        
        // For each starting position, find furthest ending position with ≤ k zeros
        for (int left = 0; left < n; left++) {
            // Binary search for right boundary
            int lo = left, hi = n;
            while (lo < hi) {
                int mid = lo + (hi - lo + 1) / 2;
                int zerosInWindow = prefixZeros[mid] - prefixZeros[left];
                if (zerosInWindow <= k) {
                    lo = mid;
                } else {
                    hi = mid - 1;
                }
            }
            
            maxLength = Math.max(maxLength, lo - left);
        }
        
        return maxLength;
    }
    
    /**
     * Variation: Return the actual subarray indices.
     */
    public int[] longestOnesIndices(int[] nums, int k) {
        if (nums == null || nums.length == 0) return new int[]{0, -1};
        
        int left = 0, zeroCount = 0;
        int maxLength = 0, bestLeft = 0, bestRight = -1;
        
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) zeroCount++;
            
            while (zeroCount > k) {
                if (nums[left] == 0) zeroCount--;
                left++;
            }
            
            int currentLength = right - left + 1;
            if (currentLength > maxLength) {
                maxLength = currentLength;
                bestLeft = left;
                bestRight = right;
            }
        }
        
        return new int[]{bestLeft, bestRight};
    }
    
    /**
     * Brute force solution for verification (O(n²)).
     */
    public int longestOnesBruteForce(int[] nums, int k) {
        if (nums == null || nums.length == 0) return 0;
        
        int maxLength = 0;
        int n = nums.length;
        
        for (int start = 0; start < n; start++) {
            int zeros = 0;
            int length = 0;
            
            for (int end = start; end < n; end++) {
                if (nums[end] == 0) {
                    zeros++;
                }
                
                if (zeros > k) {
                    break;
                }
                
                length = end - start + 1;
                maxLength = Math.max(maxLength, length);
            }
        }
        
        return maxLength;
    }
    
    /**
     * Generalized: Maximum consecutive values with at most k changes.
     * Can find longest run of any value with at most k exceptions.
     */
    public int longestConsecutiveValue(int[] nums, int target, int k) {
        if (nums == null || nums.length == 0) return 0;
        
        int left = 0, nonTargetCount = 0, maxLength = 0;
        
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] != target) {
                nonTargetCount++;
            }
            
            while (nonTargetCount > k) {
                if (nums[left] != target) {
                    nonTargetCount--;
                }
                left++;
            }
            
            maxLength = Math.max(maxLength, right - left + 1);
        }
        
        return maxLength;
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeLongestOnes(int[] nums, int k) {
        System.out.println("\n=== Max Consecutive Ones III Visualization ===");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("k = " + k + " (max zeros that can be flipped)");
        System.out.println();
        
        System.out.println("Window | Window Content | Zeros | Action | Max Length");
        System.out.println("-------|----------------|-------|--------|-----------");
        
        int left = 0, zeroCount = 0, maxLength = 0;
        
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0) zeroCount++;
            
            String action = "Add " + nums[right];
            
            // Shrink if needed
            while (zeroCount > k) {
                if (nums[left] == 0) {
                    zeroCount--;
                }
                left++;
                action = "Remove " + nums[left-1] + " (too many zeros)";
            }
            
            // Get window content
            String windowContent = Arrays.toString(
                Arrays.copyOfRange(nums, left, right + 1)
            );
            
            int currentLength = right - left + 1;
            maxLength = Math.max(maxLength, currentLength);
            
            System.out.printf("[%d,%d] | %14s | %5d | %6s | %10d%n",
                left, right, windowContent, zeroCount, action, maxLength);
        }
        
        // Show result explanation
        System.out.println("\nResult Analysis:");
        System.out.println("Maximum consecutive ones after flipping at most " + k + " zeros: " + maxLength);
        
        // Find and show the actual subarray
        int[] indices = longestOnesIndices(nums, k);
        System.out.print("Optimal subarray: ");
        for (int i = indices[0]; i <= indices[1]; i++) {
            System.out.print(nums[i] + " ");
        }
        System.out.println("\n(Can flip zeros to ones to get all 1's)");
    }
    
    /**
     * Test cases for max consecutive ones problem.
     */
    public static void runTestCases() {
        MaxConsecutiveOnesIII solver = new MaxConsecutiveOnesIII();
        
        System.out.println("=== Max Consecutive Ones III Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        int k1 = 2;
        System.out.println("Test 1:");
        System.out.println("nums = " + Arrays.toString(test1) + ", k = " + k1);
        int result1 = solver.longestOnes(test1, k1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 6 (flip two zeros in middle)");
        
        // Verify with brute force
        int brute1 = solver.longestOnesBruteForce(test1, k1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        System.out.println();
        
        // Test 2: k = 0 (no flips allowed)
        int[] test2 = {1, 0, 1, 1, 0, 1};
        int k2 = 0;
        System.out.println("Test 2 (k=0):");
        System.out.println("nums = " + Arrays.toString(test2) + ", k = " + k2);
        int result2 = solver.longestOnes(test2, k2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 2 (longest run of consecutive 1's)");
        System.out.println();
        
        // Test 3: All zeros
        int[] test3 = {0, 0, 0, 0, 0};
        int k3 = 3;
        System.out.println("Test 3 (all zeros):");
        System.out.println("nums = " + Arrays.toString(test3) + ", k = " + k3);
        int result3 = solver.longestOnes(test3, k3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 3 (can flip 3 zeros to 1's)");
        System.out.println();
        
        // Test 4: All ones
        int[] test4 = {1, 1, 1, 1, 1};
        int k4 = 2;
        System.out.println("Test 4 (all ones):");
        System.out.println("nums = " + Arrays.toString(test4) + ", k = " + k4);
        int result4 = solver.longestOnes(test4, k4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 5 (no zeros to flip, all are already 1's)");
        System.out.println();
        
        // Test 5: k larger than array length
        int[] test5 = {0, 1, 0};
        int k5 = 5;
        System.out.println("Test 5 (k > n):");
        System.out.println("nums = " + Arrays.toString(test5) + ", k = " + k5);
        int result5 = solver.longestOnes(test5, k5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 3 (can flip all zeros)");
        System.out.println();
        
        // Test 6: Complex pattern
        int[] test6 = {0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1};
        int k6 = 2;
        System.out.println("Test 6:");
        System.out.println("nums = " + Arrays.toString(test6) + ", k = " + k6);
        int result6 = solver.longestOnes(test6, k6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 8 (flip zeros around the long 1's sequence)");
        
        // Show actual indices
        int[] indices = solver.longestOnesIndices(test6, k6);
        System.out.println("Optimal subarray indices: [" + indices[0] + ", " + indices[1] + "]");
        System.out.println();
        
        // Test 7: Edge cases
        int[] test7 = {};
        int k7 = 2;
        System.out.println("Test 7 (empty array):");
        System.out.println("nums = [], k = " + k7);
        int result7 = solver.longestOnes(test7, k7);
        System.out.println("Result: " + result7);
        System.out.println("Expected: 0");
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        MaxConsecutiveOnesIII solver = new MaxConsecutiveOnesIII();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test array
        int n = 1000000;
        int[] nums = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            // 30% zeros, 70% ones
            nums[i] = rand.nextInt(10) < 3 ? 0 : 1;
        }
        int k = 100;
        
        System.out.println("Array size: " + n + ", k = " + k);
        System.out.println("(30% zeros, 70% ones)");
        
        // Standard sliding window
        long start = System.currentTimeMillis();
        int result1 = solver.longestOnes(nums, k);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Standard sliding window: " + time1 + " ms, result: " + result1);
        
        // Optimized sliding window
        start = System.currentTimeMillis();
        int result2 = solver.longestOnesOptimized(nums, k);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Optimized sliding window: " + time2 + " ms, result: " + result2);
        
        // Prefix sum + binary search
        start = System.currentTimeMillis();
        int result3 = solver.longestOnesPrefixSum(nums, k);
        long time3 = System.currentTimeMillis() - start;
        System.out.println("Prefix sum + binary search: " + time3 + " ms, result: " + result3);
        
        System.out.println("All results match: " + 
                          (result1 == result2 && result2 == result3));
        System.out.println("Sliding window is fastest for this problem");
    }
    
    /**
     * Explain the problem transformation.
     */
    public static void explainTransformation() {
        System.out.println("\n=== Problem Transformation ===");
        System.out.println();
        System.out.println("Original Problem:");
        System.out.println("  - Binary array (0's and 1's)");
        System.out.println("  - Can flip at most k 0's to 1's");
        System.out.println("  - Find longest consecutive sequence of 1's");
        System.out.println();
        System.out.println("Transformed Problem:");
        System.out.println("  Find longest contiguous subarray with at most k zeros");
        System.out.println();
        System.out.println("Why are they equivalent?");
        System.out.println("  1. Each zero in subarray can be flipped to 1");
        System.out.println("  2. If subarray has ≤ k zeros, we can flip them all");
        System.out.println("  3. Result is all 1's with same length as subarray");
        System.out.println("  4. If subarray has > k zeros, can't flip them all");
        System.out.println();
        System.out.println("Mathematical Form:");
        System.out.println("  Let window = [left, right]");
        System.out.println("  Let zeros_in_window = count of 0's in window");
        System.out.println("  We want: zeros_in_window ≤ k");
        System.out.println("  Maximum window size = answer");
    }
    
    /**
     * Show related variations of the problem.
     */
    public static void showRelatedVariations() {
        System.out.println("\n=== Related Variations ===");
        
        MaxConsecutiveOnesIII solver = new MaxConsecutiveOnesIII();
        int[] nums = {1, 0, 1, 1, 0, 0, 1, 1, 1, 0};
        
        System.out.println("Base array: " + Arrays.toString(nums));
        System.out.println();
        
        // Variation 1: At most k ones (instead of zeros)
        System.out.println("Variation 1: Longest consecutive zeros with at most k ones flipped");
        // Just swap 0 and 1 in logic
        int result1 = solver.longestConsecutiveValue(nums, 0, 2);
        System.out.println("Result with k=2: " + result1);
        System.out.println();
        
        // Variation 2: Maximum consecutive trues with at most k falses
        System.out.println("Variation 2: Boolean array version");
        boolean[] bools = {true, false, true, true, false, false, true, true, true, false};
        // Convert to 1/0 array: true→1, false→0
        int[] converted = new int[bools.length];
        for (int i = 0; i < bools.length; i++) {
            converted[i] = bools[i] ? 1 : 0;
        }
        int result2 = solver.longestOnes(converted, 2);
        System.out.println("Result with k=2: " + result2);
        System.out.println();
        
        // Variation 3: 3-state array
        System.out.println("Variation 3: Ternary array (0,1,2), find longest run of 1's");
        System.out.println("Can flip at most k non-1 elements to 1");
        int[] ternary = {1, 0, 2, 1, 1, 0, 2, 1, 1, 1};
        int result3 = solver.longestConsecutiveValue(ternary, 1, 2);
        System.out.println("Array: " + Arrays.toString(ternary));
        System.out.println("Result with k=2: " + result3);
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Example:");
        System.out.println("=".repeat(60));
        
        MaxConsecutiveOnesIII solver = new MaxConsecutiveOnesIII();
        int[] nums = {1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0};
        int k = 2;
        solver.visualizeLongestOnes(nums, k);
        
        // Explain the transformation
        explainTransformation();
        
        // Show related variations
        showRelatedVariations();
        
        // Run benchmark (optional)
        // benchmark();
    }
}