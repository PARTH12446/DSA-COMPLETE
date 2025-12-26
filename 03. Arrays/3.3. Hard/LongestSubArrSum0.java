// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Longest Subarray with Zero Sum (Coding Ninjas)
 * 
 * Given an array 'arr' containing both positive and negative numbers,
 * find the length of the longest subarray with sum equal to 0.
 * 
 * DEFINITION:
 * A subarray is a contiguous part of an array.
 * We need to find the longest subarray where sum of elements = 0.
 * 
 * CONSTRAINTS:
 * - 1 = arr.length = 10^5
 * - -10^9 = arr[i] = 10^9
 * - Array may contain both positive and negative numbers
 * 
 * APPROACH: Prefix Sum with Hash Map
 * 
 * INTUITION:
 * If sum of arr[0...i] = sum of arr[0...j], then sum of arr[i+1...j] = 0.
 * 
 * Example: arr = [1, 2, -2, -1, 3]
 * Prefix sums: [1, 3, 1, 0, 3]
 * At index 0: sum=1
 * At index 2: sum=1 ? same as index 0!
 * This means arr[1...2] = [2, -2] sums to 0
 * 
 * ALGORITHM:
 * 1. Maintain running sum (prefix sum)
 * 2. Use hash map to store first occurrence of each prefix sum
 * 3. If current sum = 0 ? subarray from start to current has sum 0
 * 4. If current sum seen before ? subarray from previous index+1 to current has sum 0
 * 
 * TIME COMPLEXITY: O(n)
 *   - Single pass through array
 *   - HashMap operations are O(1) average case
 * 
 * SPACE COMPLEXITY: O(n)
 *   - HashMap stores up to n prefix sums
 * 
 * EDGE CASES:
 * - Entire array sums to 0
 * - No zero-sum subarray exists
 * - Multiple zero-sum subarrays of same length
 * - Array contains only zeros
 */

import java.util.*;

public class LongestSubArrSum0 {

    /**
     * Main function to find length of longest zero-sum subarray.
     * 
     * @param arr Input array of integers
     * @return Length of longest subarray with sum equal to 0
     */
    public static int getLongestZeroSumSubarrayLength(int[] arr) {
        // HashMap to store prefix sum and its first occurrence index
        Map<Integer, Integer> prefixSumMap = new HashMap<>();
        int currentSum = 0;
        int maxLength = 0;
        
        for (int i = 0; i < arr.length; i++) {
            // Calculate running sum
            currentSum += arr[i];
            
            // Case 1: Subarray from start to i has sum 0
            if (currentSum == 0) {
                maxLength = i + 1;  // Entire array up to i
            }
            
            // Case 2: We've seen this sum before
            // The subarray from (previous index + 1) to i has sum 0
            if (prefixSumMap.containsKey(currentSum)) {
                int prevIndex = prefixSumMap.get(currentSum);
                maxLength = Math.max(maxLength, i - prevIndex);
            } 
            // Case 3: First time seeing this sum, store its index
            else {
                prefixSumMap.put(currentSum, i);
            }
        }
        
        return maxLength;
    }
    
    /**
     * Alternative implementation with clearer logic
     */
    public static int getLongestZeroSumSubarrayLengthAlt(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int maxLen = 0;
        int sum = 0;
        
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            
            // If sum becomes 0, update max length
            if (sum == 0) {
                maxLen = i + 1;
            }
            
            // If this sum was seen before
            if (map.containsKey(sum)) {
                // Calculate length of subarray with sum 0
                int len = i - map.get(sum);
                maxLen = Math.max(maxLen, len);
            } else {
                // Store first occurrence of this sum
                map.put(sum, i);
            }
        }
        
        return maxLen;
    }
    
    /**
     * Returns the actual subarray with maximum length and sum 0
     * (Not just the length)
     */
    public static int[] getLongestZeroSumSubarray(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int maxLen = 0;
        int sum = 0;
        int start = -1, end = -1;
        
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            
            if (sum == 0) {
                maxLen = i + 1;
                start = 0;
                end = i;
            }
            
            if (map.containsKey(sum)) {
                int prevIndex = map.get(sum);
                int currentLen = i - prevIndex;
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                    start = prevIndex + 1;
                    end = i;
                }
            } else {
                map.put(sum, i);
            }
        }
        
        if (start == -1) {
            return new int[0];  // No zero-sum subarray found
        }
        
        return Arrays.copyOfRange(arr, start, end + 1);
    }
    
    /**
     * Brute force solution for verification (O(n²) time)
     */
    public static int getLongestZeroSumSubarrayLengthBruteForce(int[] arr) {
        int maxLen = 0;
        
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = i; j < arr.length; j++) {
                sum += arr[j];
                if (sum == 0) {
                    maxLen = Math.max(maxLen, j - i + 1);
                }
            }
        }
        
        return maxLen;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Simple case
        int[] arr1 = {1, 2, -2, -1, 3};
        System.out.println("Test 1: [1, 2, -2, -1, 3]");
        System.out.println("Expected: 2 (subarray [2, -2])");
        System.out.println("Result: " + getLongestZeroSumSubarrayLength(arr1));
        System.out.println("Subarray: " + Arrays.toString(getLongestZeroSumSubarray(arr1)));
        
        // Test case 2: Entire array sums to 0
        int[] arr2 = {1, -1, 2, -2};
        System.out.println("\nTest 2: [1, -1, 2, -2]");
        System.out.println("Expected: 4 (entire array)");
        System.out.println("Result: " + getLongestZeroSumSubarrayLength(arr2));
        System.out.println("Subarray: " + Arrays.toString(getLongestZeroSumSubarray(arr2)));
        
        // Test case 3: No zero-sum subarray
        int[] arr3 = {1, 2, 3, 4};
        System.out.println("\nTest 3: [1, 2, 3, 4]");
        System.out.println("Expected: 0");
        System.out.println("Result: " + getLongestZeroSumSubarrayLength(arr3));
        System.out.println("Subarray: " + Arrays.toString(getLongestZeroSumSubarray(arr3)));
        
        // Test case 4: All zeros
        int[] arr4 = {0, 0, 0, 0};
        System.out.println("\nTest 4: [0, 0, 0, 0]");
        System.out.println("Expected: 4 (entire array)");
        System.out.println("Result: " + getLongestZeroSumSubarrayLength(arr4));
        System.out.println("Subarray: " + Arrays.toString(getLongestZeroSumSubarray(arr4)));
        
        // Test case 5: Multiple zero-sum subarrays
        int[] arr5 = {15, -2, 2, -8, 1, 7, 10, 23};
        System.out.println("\nTest 5: [15, -2, 2, -8, 1, 7, 10, 23]");
        System.out.println("Expected: 5 (subarray [-2, 2, -8, 1, 7])");
        System.out.println("Result: " + getLongestZeroSumSubarrayLength(arr5));
        System.out.println("Subarray: " + Arrays.toString(getLongestZeroSumSubarray(arr5)));
        
        // Test case 6: Complex case
        int[] arr6 = {6, 3, -1, -3, 4, -2, 2, 4, 6, -12, -7};
        System.out.println("\nTest 6: Complex array");
        System.out.println("Result: " + getLongestZeroSumSubarrayLength(arr6));
        
        // Performance test: Large array
        System.out.println("\n=== Performance Test ===");
        int[] largeArr = new int[100000];
        Random rand = new Random();
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = rand.nextInt(21) - 10; // Range -10 to 10
        }
        
        long startTime = System.currentTimeMillis();
        int result = getLongestZeroSumSubarrayLength(largeArr);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Array size: 100,000");
        System.out.println("Result: " + result);
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
        
        // Verify with brute force for small array
        System.out.println("\n=== Verification with Brute Force ===");
        int[] testArr = {1, 2, -3, 3, -1, 2};
        int hashResult = getLongestZeroSumSubarrayLength(testArr);
        int bruteResult = getLongestZeroSumSubarrayLengthBruteForce(testArr);
        System.out.println("Hash Map Result: " + hashResult);
        System.out.println("Brute Force Result: " + bruteResult);
        System.out.println("Match: " + (hashResult == bruteResult));
    }
    
    /**
     * Why the algorithm works: Mathematical Proof
     * 
     * Let prefix sum P[i] = sum of arr[0...i]
     * If P[i] = P[j] for i < j, then:
     *   P[j] - P[i] = 0
     *   But P[j] - P[i] = sum of arr[i+1...j]
     *   Therefore, arr[i+1...j] has sum 0
     * 
     * Example:
     * arr = [a0, a1, a2, a3, a4]
     * P[0] = a0
     * P[1] = a0 + a1
     * P[2] = a0 + a1 + a2
     * 
     * If P[1] = P[2], then:
     * (a0 + a1) = (a0 + a1 + a2)
     * => a2 = 0
     * But actually: P[2] - P[1] = a2 = 0
     * Wait, that's wrong. Let's fix:
     * 
     * If P[1] = P[2], then:
     * P[2] = P[1]
     * => (a0 + a1 + a2) = (a0 + a1)
     * => a2 = 0 ?
     * 
     * So the subarray arr[2...2] has sum 0.
     * 
     * Actually, for i < j:
     * P[j] = P[i] means:
     * sum(arr[0...j]) = sum(arr[0...i])
     * => sum(arr[i+1...j]) = 0 ?
     */
    
    /**
     * Visual Example:
     * 
     * arr = [1, 2, -2, -1, 3]
     * 
     * i=0: sum=1, map={1:0}
     * i=1: sum=3, map={1:0, 3:1}
     * i=2: sum=1, seen before at index 0!
     *      Subarray from index 1 to 2: [2, -2] sum=0
     *      Length = 2-0 = 2
     * i=3: sum=0, sum==0!
     *      Subarray from start to 3: [1,2,-2,-1] sum=0
     *      Length = 3+1 = 4 (but our max is still 2)
     * i=4: sum=3, seen before at index 1
     *      Subarray from index 2 to 4: [-2,-1,3] sum=0
     *      Length = 4-1 = 3
     * 
     * Final max length = 3
     */
    
    /**
     * Edge Cases and Special Handling:
     * 
     * 1. Multiple occurrences of same sum:
     *    - Store only first occurrence to get longest subarray
     *    - If we stored last occurrence, we'd get shorter subarrays
     * 
     * 2. Sum = 0 at index i:
     *    - Means subarray from start to i has sum 0
     *    - Length = i+1
     * 
     * 3. Empty subarray:
     *    - By definition, empty subarray has sum 0
     *    - But problem asks for longest non-empty? Check constraints
     *    - Usually we consider non-empty subarrays
     * 
     * 4. All elements zero:
     *    - Every subarray has sum 0
     *    - Longest is entire array
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Storing all occurrences of sum:
     *    Wrong: map.put(sum, i) always
     *    Right: only if sum not already in map
     * 
     * 2. Not checking sum == 0 separately:
     *    Need special handling because map won't contain key 0 initially
     * 
     * 3. Using wrong index calculation:
     *    Length = current_index - previous_index (not +1)
     *    Because subarray starts at previous_index+1
     * 
     * 4. Forgetting to initialize maxLen:
     *    Default should be 0 (no zero-sum subarray found)
     */
    
    /**
     * Related Problems:
     * 
     * 1. Longest Subarray with Sum K (generalization)
     * 2. Count Subarrays with Sum K
     * 3. Maximum Size Subarray Sum Equals K (LeetCode 325)
     * 4. Subarray Sum Equals K (LeetCode 560)
     * 5. Continuous Subarray Sum (LeetCode 523) - sum multiple of k
     */
    
    /**
     * Time Complexity Analysis:
     * 
     * - n iterations
     * - Each iteration: O(1) HashMap operations
     * - Total: O(n)
     * 
     * Space Complexity Analysis:
     * 
     * - HashMap stores at most n entries
     * - Each entry: key (int), value (int)
     * - Worst case: O(n) when all prefix sums are distinct
     */
    
    /**
     * Optimization for large arrays:
     * 
     * 1. Use primitive int array with linear probing for better cache locality
     * 2. Estimate initial HashMap capacity to avoid rehashing
     * 3. For very large n, consider using array instead of HashMap
     *    if value range is limited
     */
    
    /**
     * Applications:
     * 
     * 1. Financial analysis: Find periods with zero net change
     * 2. Signal processing: Find intervals with zero average
     * 3. Game theory: Find sequences of moves with zero net gain
     * 4. Data compression: Find redundant data segments
     */
}
