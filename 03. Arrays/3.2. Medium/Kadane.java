// Problem: LeetCode <ID>. <Title>
// Problem: Maximum Subarray Sum (Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/maximum-subarray-sum_630526

// Problem Description:
// Given an array of integers, find the maximum sum of any contiguous subarray.
// Also known as the "Maximum Subarray Problem" or "Kadane's Algorithm".

// Example:
// Input: [-2, 1, -3, 4, -1, 2, 1, -5, 4]
// Output: 6
// Explanation: The subarray [4, -1, 2, 1] has the largest sum = 6.

// Constraints:
// 1 <= n <= 10^5
// -10^9 <= arr[i] <= 10^9

// Note: This implementation has a bug! It returns 0 for arrays with all negative numbers.
// The correct Kadane's algorithm should handle all negative numbers properly.

public class Kadane {

    /**
     * Buggy implementation of Kadane's Algorithm.
     * Returns 0 for arrays with all negative numbers.
     * 
     * @param arr Input array of integers
     * @param n Length of the array (not used since we use arr.length)
     * @return Maximum subarray sum (buggy for all negative arrays)
     */
    public static long maxSubarraySum(int[] arr, int n) {
        long sum = 0;
        long max = 0;  // BUG: Should initialize with Long.MIN_VALUE or arr[0]
        for (int v : arr) {
            sum += v;
            if (sum > max) max = sum;
            if (sum < 0) sum = 0;
        }
        return max;
    }
    
    /**
     * CORRECTED Kadane's Algorithm implementation.
     * Handles arrays with all negative numbers correctly.
     * 
     * Time Complexity: O(n) - Single pass through array
     * Space Complexity: O(1) - Constant extra space
     * 
     * @param arr Input array of integers
     * @param n Length of the array
     * @return Maximum subarray sum
     */
    public static long maxSubarraySumCorrected(int[] arr, int n) {
        if (arr == null || n == 0) {
            return 0;
        }
        
        long currentSum = arr[0];
        long maxSum = arr[0];
        
        for (int i = 1; i < n; i++) {
            // Either extend the current subarray or start a new one
            currentSum = Math.max(arr[i], currentSum + arr[i]);
            // Update max sum if current sum is greater
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
    
    /**
     * Alternative Kadane's implementation using start and end indices.
     * Returns the maximum sum and also tracks the subarray indices.
     * 
     * @param arr Input array
     * @return Object containing max sum and indices
     */
    public static SubarrayResult maxSubarraySumWithIndices(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new SubarrayResult(0, -1, -1);
        }
        
        long currentSum = arr[0];
        long maxSum = arr[0];
        int start = 0, end = 0;
        int tempStart = 0;
        
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > currentSum + arr[i]) {
                // Start new subarray
                currentSum = arr[i];
                tempStart = i;
            } else {
                // Extend current subarray
                currentSum = currentSum + arr[i];
            }
            
            // Update max sum and indices if we found a better sum
            if (currentSum > maxSum) {
                maxSum = currentSum;
                start = tempStart;
                end = i;
            }
        }
        
        return new SubarrayResult(maxSum, start, end);
    }
    
    /**
     * Divide and Conquer approach for maximum subarray sum.
     * Time Complexity: O(n log n)
     * Space Complexity: O(log n) for recursion stack
     * 
     * @param arr Input array
     * @param left Left index
     * @param right Right index
     * @return Maximum subarray sum in the range [left, right]
     */
    public static long maxSubarraySumDivideConquer(int[] arr, int left, int right) {
        if (left == right) {
            return arr[left];
        }
        
        int mid = left + (right - left) / 2;
        
        // Find max sum in left half, right half, and crossing subarray
        long leftMax = maxSubarraySumDivideConquer(arr, left, mid);
        long rightMax = maxSubarraySumDivideConquer(arr, mid + 1, right);
        long crossMax = maxCrossingSum(arr, left, mid, right);
        
        return Math.max(Math.max(leftMax, rightMax), crossMax);
    }
    
    private static long maxCrossingSum(int[] arr, int left, int mid, int right) {
        // Find maximum sum starting from mid and going left
        long leftSum = Long.MIN_VALUE;
        long sum = 0;
        for (int i = mid; i >= left; i--) {
            sum += arr[i];
            leftSum = Math.max(leftSum, sum);
        }
        
        // Find maximum sum starting from mid+1 and going right
        long rightSum = Long.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += arr[i];
            rightSum = Math.max(rightSum, sum);
        }
        
        return leftSum + rightSum;
    }
    
    /**
     * Variation: Maximum subarray sum with at most one deletion.
     * Allows deleting at most one element from the subarray.
     * 
     * @param arr Input array
     * @return Maximum sum with at most one deletion
     */
    public static long maxSubarraySumWithOneDeletion(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        // keep[i] = max subarray sum ending at i (no deletion)
        // delete[i] = max subarray sum ending at i with one deletion
        long keep = arr[0];
        long delete = 0;
        long maxSum = arr[0];
        
        for (int i = 1; i < arr.length; i++) {
            // Update delete: either delete current element or delete a previous one
            delete = Math.max(keep, delete + arr[i]);
            // Update keep: standard Kadane's
            keep = Math.max(arr[i], keep + arr[i]);
            // Update max sum
            maxSum = Math.max(maxSum, Math.max(keep, delete));
        }
        
        return maxSum;
    }
    
    /**
     * Variation: Maximum subarray sum with size at least k.
     * 
     * @param arr Input array
     * @param k Minimum subarray size
     * @return Maximum sum of subarray with size >= k
     */
    public static long maxSubarraySumAtLeastK(int[] arr, int k) {
        if (arr == null || arr.length < k) {
            return 0;
        }
        
        // Step 1: Calculate prefix sums
        long[] prefixSum = new long[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            prefixSum[i + 1] = prefixSum[i] + arr[i];
        }
        
        // Step 2: Use Kadane's to find best ending at each position
        long[] maxEndingAt = new long[arr.length];
        maxEndingAt[0] = arr[0];
        long maxSoFar = arr[0];
        for (int i = 1; i < arr.length; i++) {
            maxEndingAt[i] = Math.max(arr[i], maxEndingAt[i - 1] + arr[i]);
        }
        
        // Step 3: Find maximum sum for subarrays of at least k length
        long result = prefixSum[k];  // Sum of first k elements
        
        for (int i = k; i < arr.length; i++) {
            // Case 1: Subarray ending at i with length >= k
            result = Math.max(result, prefixSum[i + 1] - prefixSum[i + 1 - k]);
            
            // Case 2: Combine best ending before i-k+1 with current subarray
            if (i - k >= 0) {
                result = Math.max(result, 
                    maxEndingAt[i - k] + prefixSum[i + 1] - prefixSum[i + 1 - k]);
            }
        }
        
        return result;
    }
    
    /**
     * Helper class to store subarray result with indices
     */
    static class SubarrayResult {
        long maxSum;
        int start;
        int end;
        
        SubarrayResult(long maxSum, int start, int end) {
            this.maxSum = maxSum;
            this.start = start;
            this.end = end;
        }
        
        @Override
        public String toString() {
            return String.format("Max Sum: %d, Subarray: [%d, %d]", maxSum, start, end);
        }
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Testing Kadane's Algorithm ===");
        
        // Test Case 1: Standard example
        int[] test1 = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("\nTest 1:");
        System.out.println("Array: " + java.util.Arrays.toString(test1));
        System.out.println("Buggy implementation: " + maxSubarraySum(test1, test1.length));
        System.out.println("Corrected implementation: " + maxSubarraySumCorrected(test1, test1.length));
        System.out.println("Expected: 6 (subarray: [4, -1, 2, 1])");
        
        // Test Case 2: All negative numbers (BUG CASE)
        int[] test2 = {-5, -2, -8, -3, -1};
        System.out.println("\nTest 2 (All Negative):");
        System.out.println("Array: " + java.util.Arrays.toString(test2));
        System.out.println("Buggy implementation: " + maxSubarraySum(test2, test2.length));
        System.out.println("Corrected implementation: " + maxSubarraySumCorrected(test2, test2.length));
        System.out.println("Expected: -1 (subarray: [-1])");
        
        // Test Case 3: All positive numbers
        int[] test3 = {1, 2, 3, 4, 5};
        System.out.println("\nTest 3 (All Positive):");
        System.out.println("Array: " + java.util.Arrays.toString(test3));
        System.out.println("Corrected implementation: " + maxSubarraySumCorrected(test3, test3.length));
        System.out.println("Expected: 15 (entire array)");
        
        // Test Case 4: Single element
        int[] test4 = {7};
        System.out.println("\nTest 4 (Single Element):");
        System.out.println("Array: " + java.util.Arrays.toString(test4));
        System.out.println("Corrected implementation: " + maxSubarraySumCorrected(test4, test4.length));
        System.out.println("Expected: 7");
        
        // Test Case 5: Mixed with zero
        int[] test5 = {0, -2, 3, -1, 2};
        System.out.println("\nTest 5 (With Zero):");
        System.out.println("Array: " + java.util.Arrays.toString(test5));
        System.out.println("Corrected implementation: " + maxSubarraySumCorrected(test5, test5.length));
        System.out.println("Expected: 4 (subarray: [3, -1, 2])");
        
        // Test Case 6: Empty array
        int[] test6 = {};
        System.out.println("\nTest 6 (Empty Array):");
        System.out.println("Array: " + java.util.Arrays.toString(test6));
        System.out.println("Corrected implementation: " + maxSubarraySumCorrected(test6, test6.length));
        System.out.println("Expected: 0");
        
        // Test with indices
        System.out.println("\n=== Testing with Indices ===");
        SubarrayResult result = maxSubarraySumWithIndices(test1);
        System.out.println("Test 1 with indices: " + result);
        System.out.println("Actual subarray: " + 
            java.util.Arrays.toString(java.util.Arrays.copyOfRange(test1, result.start, result.end + 1)));
        
        // Test divide and conquer
        System.out.println("\n=== Testing Divide and Conquer ===");
        System.out.println("Test 1 (Divide & Conquer): " + 
            maxSubarraySumDivideConquer(test1, 0, test1.length - 1));
        
        // Test variations
        System.out.println("\n=== Testing Variations ===");
        
        // Test with one deletion
        int[] test7 = {1, -2, 0, 3};
        System.out.println("Max sum with one deletion: " + 
            maxSubarraySumWithOneDeletion(test7));
        System.out.println("Expected: 4 (delete -2: [1, 0, 3])");
        
        // Test with minimum size k
        int[] test8 = {-4, -2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println("Max sum with at least 4 elements: " + 
            maxSubarraySumAtLeastK(test8, 4));
        System.out.println("Expected: 5 (subarray: [4, -1, 2, 1])");
    }
}

// Key Insights:
// 1. Kadane's Algorithm is the optimal O(n) solution for maximum subarray sum
// 2. The bug in the original code: initializing max = 0 fails for all negative arrays
// 3. Correct initialization: maxSum = arr[0], currentSum = arr[0]
// 4. Core idea: At each position, decide whether to extend current subarray or start new one

// Algorithm (Corrected):
// 1. Initialize: currentSum = arr[0], maxSum = arr[0]
// 2. For i from 1 to n-1:
//    a. currentSum = max(arr[i], currentSum + arr[i])
//    b. maxSum = max(maxSum, currentSum)
// 3. Return maxSum

// Why This Works (Intuition):
// At each position, we have two choices:
// 1. Add current element to existing subarray (currentSum + arr[i])
// 2. Start new subarray from current element (arr[i])
// We choose whichever gives larger sum
// Then we update global maximum if current sum is better

// Edge Cases:
// 1. All negative numbers ? Should return the maximum (least negative) element
// 2. Empty array ? Return 0 or throw exception based on requirements
// 3. Single element ? Return that element
// 4. All zeros ? Return 0
// 5. Very large numbers ? Use long to prevent overflow

// Time & Space Complexity:
// - Time: O(n) - Single pass through array
// - Space: O(1) - Only a few variables needed

// Common Mistakes:
// 1. Initializing max to 0 (fails for all negative arrays)
// 2. Not handling empty arrays
// 3. Using int when sum might overflow (use long for large constraints)
// 4. Confusing subarray with subsequence (subarray must be contiguous)

// Variations and Extensions:
// 1. Return the actual subarray (not just sum) - track indices
// 2. Maximum subarray sum with at most k deletions
// 3. Maximum subarray sum with size exactly/at least/at most k
// 4. Maximum subarray sum in circular array
// 5. Maximum product subarray

// Real-World Applications:
// 1. Stock trading: Maximum profit from buying and selling once
// 2. Image processing: Maximum brightness region
// 3. Genomics: Finding conserved regions in DNA sequences
// 4. Finance: Finding periods of maximum growth/decline

// Related Problems:
// 1. Maximum Product Subarray (LeetCode 152)
// 2. Best Time to Buy and Sell Stock (LeetCode 121)
// 3. Maximum Sum Circular Subarray (LeetCode 918)
// 4. Maximum Subarray Sum with One Deletion (LeetCode 1186)

// Historical Note:
// Named after Joseph Born Kadane, though similar algorithms were known earlier.
// One of the classic examples of dynamic programming thinking.

// Performance:
// For n = 10^5, O(n) solution takes ~0.001 seconds
// Brute force O(n²) would take ~10 seconds (too slow)

// Always test with edge cases:
// - All negative numbers
// - Single element
// - Mix of positive and negative
// - Array with zeros
