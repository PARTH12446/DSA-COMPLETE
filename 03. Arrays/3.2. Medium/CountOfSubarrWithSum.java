// Problem: LeetCode <ID>. <Title>
// Problem: Subarray Sums I (Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/subarray-sums-i_1467103

import java.util.HashMap;
import java.util.Map;

// Problem Description:
// Given an array of integers 'arr' and an integer 'k',
// return the total number of subarrays with a sum equal to k.
// A subarray is a contiguous non-empty sequence of elements within an array.

// Example 1:
// Input: arr = [3, 1, 2, 4], k = 6
// Output: 2
// Explanation: Subarrays with sum 6 are [3, 1, 2] and [2, 4]

// Example 2:
// Input: arr = [1, 2, 3, -3, 1, 1, 1, 4, 2, -3], k = 3
// Output: 8

// Constraints:
// 1 <= arr.length <= 10^5
// -1000 <= arr[i] <= 1000
// -10^9 <= k <= 10^9

public class CountOfSubarrWithSum {

    /**
     * Counts all subarrays with sum equal to k using prefix sum and hashmap.
     * 
     * Time Complexity: O(n) - Single pass through the array
     * Space Complexity: O(n) - For the hashmap storing prefix sums
     * 
     * @param arr Input array of integers
     * @param k Target sum to find
     * @return Number of subarrays with sum equal to k
     */
    public static int findAllSubarraysWithGivenSum(int[] arr, int k) {
        // HashMap to store frequency of prefix sums encountered so far
        // Key: Prefix sum, Value: Frequency of that prefix sum
        Map<Integer, Integer> hashmap = new HashMap<>();
        
        // Initialize with prefix sum 0 having frequency 1
        // This handles cases where a subarray starts from index 0
        hashmap.put(0, 1);
        
        int prefixSum = 0;  // Current prefix sum
        int count = 0;      // Count of subarrays with sum k
        
        for (int v : arr) {
            // Update current prefix sum
            prefixSum += v;
            
            // Calculate what prefix sum we need to remove to get sum k
            // If prefixSum - target = requiredPrefix, then target = prefixSum - requiredPrefix
            int requiredPrefix = prefixSum - k;
            
            // Check if we've seen the required prefix sum before
            // If yes, each occurrence gives us a subarray with sum k
            if (hashmap.containsKey(requiredPrefix)) {
                count += hashmap.get(requiredPrefix);
            }
            
            // Update frequency of current prefix sum in hashmap
            hashmap.put(prefixSum, hashmap.getOrDefault(prefixSum, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * Alternative brute-force approach (for understanding, not optimal).
     * Time Complexity: O(n²), Space Complexity: O(1)
     */
    public static int findAllSubarraysBruteForce(int[] arr, int k) {
        int count = 0;
        int n = arr.length;
        
        for (int start = 0; start < n; start++) {
            int currentSum = 0;
            for (int end = start; end < n; end++) {
                currentSum += arr[end];
                if (currentSum == k) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Variation: Count subarrays with sum k and also return their indices.
     * This method prints all subarrays with sum k along with their indices.
     */
    public static int findAllSubarraysWithIndices(int[] arr, int k) {
        Map<Integer, Integer> prefixSumMap = new HashMap<>();
        prefixSumMap.put(0, 1);
        
        int prefixSum = 0;
        int count = 0;
        
        System.out.println("Subarrays with sum " + k + ":");
        
        for (int i = 0; i < arr.length; i++) {
            prefixSum += arr[i];
            int requiredPrefix = prefixSum - k;
            
            if (prefixSumMap.containsKey(requiredPrefix)) {
                // This indicates there are subarrays ending at i with sum k
                // The actual start indices would need additional tracking
                count += prefixSumMap.get(requiredPrefix);
                
                // For demonstration, we can't print exact indices without additional data structure
                // A more complete solution would store list of indices for each prefix sum
                System.out.println("Found " + prefixSumMap.get(requiredPrefix) + 
                                 " subarray(s) ending at index " + i);
            }
            
            prefixSumMap.put(prefixSum, prefixSumMap.getOrDefault(prefixSum, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * Optimized version with edge case handling and input validation.
     */
    public static int findAllSubarraysOptimized(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        Map<Integer, Integer> prefixSumFreq = new HashMap<>();
        prefixSumFreq.put(0, 1);  // Base case: empty subarray has sum 0
        
        int currentSum = 0;
        int result = 0;
        
        for (int num : arr) {
            currentSum += num;
            
            // Check if (currentSum - k) exists in map
            // If yes, add its frequency to result
            int neededSum = currentSum - k;
            result += prefixSumFreq.getOrDefault(neededSum, 0);
            
            // Update frequency of current prefix sum
            prefixSumFreq.put(currentSum, prefixSumFreq.getOrDefault(currentSum, 0) + 1);
        }
        
        return result;
    }
    
    /**
     * Variation: Handle negative numbers and large k values.
     * This version uses long to prevent integer overflow.
     */
    public static int findAllSubarraysLargeNumbers(int[] arr, long k) {
        Map<Long, Integer> prefixSumMap = new HashMap<>();
        prefixSumMap.put(0L, 1);
        
        long currentSum = 0;
        int count = 0;
        
        for (int num : arr) {
            currentSum += num;
            long requiredPrefix = currentSum - k;
            
            count += prefixSumMap.getOrDefault(requiredPrefix, 0);
            
            prefixSumMap.put(currentSum, prefixSumMap.getOrDefault(currentSum, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        // Test Case 1: Standard example
        int[] test1 = {3, 1, 2, 4};
        int k1 = 6;
        System.out.println("Test 1:");
        System.out.println("Array: " + java.util.Arrays.toString(test1));
        System.out.println("Target sum k = " + k1);
        System.out.println("Number of subarrays: " + findAllSubarraysWithGivenSum(test1, k1));
        System.out.println("Expected: 2 (subarrays: [3,1,2] and [2,4])");
        System.out.println();
        
        // Test Case 2: Multiple subarrays
        int[] test2 = {1, 2, 3, -3, 1, 1, 1, 4, 2, -3};
        int k2 = 3;
        System.out.println("Test 2:");
        System.out.println("Array: " + java.util.Arrays.toString(test2));
        System.out.println("Target sum k = " + k2);
        System.out.println("Number of subarrays: " + findAllSubarraysWithGivenSum(test2, k2));
        System.out.println("Expected: 8");
        System.out.println();
        
        // Test Case 3: All zeros
        int[] test3 = {0, 0, 0, 0};
        int k3 = 0;
        System.out.println("Test 3:");
        System.out.println("Array: " + java.util.Arrays.toString(test3));
        System.out.println("Target sum k = " + k3);
        System.out.println("Number of subarrays: " + findAllSubarraysWithGivenSum(test3, k3));
        System.out.println("Expected: 10 (n*(n+1)/2 where n=4)");
        System.out.println();
        
        // Test Case 4: Negative numbers
        int[] test4 = {-1, -1, 1};
        int k4 = 0;
        System.out.println("Test 4:");
        System.out.println("Array: " + java.util.Arrays.toString(test4));
        System.out.println("Target sum k = " + k4);
        System.out.println("Number of subarrays: " + findAllSubarraysWithGivenSum(test4, k4));
        System.out.println("Expected: 2 (subarrays: [-1,1] and [1,-1]?)");
        System.out.println("Actual calculation: " + findAllSubarraysWithGivenSum(test4, k4));
        System.out.println();
        
        // Test Case 5: Single element
        int[] test5 = {5};
        int k5 = 5;
        System.out.println("Test 5:");
        System.out.println("Array: " + java.util.Arrays.toString(test5));
        System.out.println("Target sum k = " + k5);
        System.out.println("Number of subarrays: " + findAllSubarraysWithGivenSum(test5, k5));
        System.out.println("Expected: 1");
        System.out.println();
        
        // Test Case 6: No subarray with sum k
        int[] test6 = {1, 2, 3};
        int k6 = 7;
        System.out.println("Test 6:");
        System.out.println("Array: " + java.util.Arrays.toString(test6));
        System.out.println("Target sum k = " + k6);
        System.out.println("Number of subarrays: " + findAllSubarraysWithGivenSum(test6, k6));
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test brute force for comparison
        System.out.println("=== Brute Force Comparison ===");
        System.out.println("Test 1 (Brute Force): " + findAllSubarraysBruteForce(test1, k1));
        System.out.println("Test 2 (Brute Force): " + findAllSubarraysBruteForce(test2, k2));
        
        // Test with indices
        System.out.println("\n=== Testing with Index Information ===");
        findAllSubarraysWithIndices(test1, k1);
    }
}

// Key Insights:
// 1. The optimal solution uses the concept of prefix sums and hashmap
// 2. Core idea: If prefixSum[j] - prefixSum[i] = k, then subarray (i+1 to j) has sum k
// 3. We store frequencies of prefix sums because multiple subarrays can have same prefix sum
// 4. The hashmap initialization with (0, 1) handles subarrays starting from index 0

// Mathematical Formulation:
// Let prefixSum[i] = sum of arr[0] to arr[i]
// For subarray from i+1 to j: sum = prefixSum[j] - prefixSum[i] = k
// ? prefixSum[i] = prefixSum[j] - k
// So we need to count how many times (prefixSum[j] - k) has appeared before

// Algorithm Steps:
// 1. Initialize hashmap with (0, 1) to handle base case
// 2. Initialize prefixSum = 0, count = 0
// 3. For each element in array:
//    a. Add element to prefixSum
//    b. Calculate requiredPrefix = prefixSum - k
//    c. If requiredPrefix exists in hashmap, add its frequency to count
//    d. Update frequency of current prefixSum in hashmap
// 4. Return count

// Why This Works:
// 1. Time Complexity O(n): Single pass through array with O(1) hashmap operations
// 2. Handles negative numbers correctly
// 3. Counts all possible subarrays including overlapping ones
// 4. Works for any integer values (positive, negative, zero)

// Edge Cases:
// 1. Empty array ? returns 0
// 2. All zeros with k=0 ? returns n*(n+1)/2
// 3. Negative k values ? works correctly
// 4. Large array size (10^5) ? efficient O(n) solution needed
// 5. Integer overflow for large sums ? use long if needed

// Time Complexity Analysis:
// - Single iteration: O(n)
// - HashMap operations (get, put, containsKey): O(1) average case
// - Total: O(n)

// Space Complexity Analysis:
// - HashMap stores up to n unique prefix sums: O(n)
// - Additional variables: O(1)
// - Total: O(n)

// Common Follow-up Questions:
// 1. Return the actual subarrays, not just count ? Need to store indices
// 2. Find longest subarray with sum k ? Use hashmap storing first occurrence
// 3. Handle when array contains only positive numbers ? Can use sliding window
// 4. What if we want subarrays with sum divisible by k? ? Different hashmap key
// 5. Minimum length subarray with sum >= k (with positive numbers) ? Sliding window

// Sliding Window Alternative (Only for positive numbers):
// For arrays with only positive numbers, we can use sliding window:
// - Time Complexity: O(n)
// - Space Complexity: O(1)
// - Two pointers: expand right when sum < k, shrink left when sum > k

// Related Problems:
// 1. Subarray Sum Equals K (LeetCode 560) - Exact same problem
// 2. Continuous Subarray Sum (LeetCode 523) - Sum divisible by k
// 3. Minimum Size Subarray Sum (LeetCode 209) - Sum >= target
// 4. Binary Subarrays With Sum (LeetCode 930) - Sum equals k in binary array

// Important Note:
// The brute force O(n²) solution will time out for large constraints (n up to 10^5)
// Always use the prefix sum + hashmap approach for this problem
