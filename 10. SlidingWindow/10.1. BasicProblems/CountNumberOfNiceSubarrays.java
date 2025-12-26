import java.util.*;

/**
 * Count Number of Nice Subarrays (LeetCode 1248)
 * 
 * Problem: Given an array of integers nums and an integer k.
 * A subarray is called "nice" if it contains exactly k odd numbers.
 * Return the number of nice subarrays.
 * 
 * Example: nums = [1,1,2,1,1], k = 3
 * Nice subarrays: [1,1,2,1], [1,2,1,1], [1,1,2,1,1]
 * Result: 3
 * 
 * Key Insight: Transform "exactly k" to "at most k" using:
 * numberOfSubarraysWithExactlyK = atMost(K) - atMost(K-1)
 * 
 * This works because:
 * - atMost(K) counts all subarrays with ≤ K odd numbers
 * - atMost(K-1) counts all subarrays with ≤ K-1 odd numbers
 * - Their difference gives subarrays with exactly K odd numbers
 * 
 * Time Complexity: O(n) - Two passes with sliding window
 * Space Complexity: O(1) - Only a few variables
 */
public class CountNumberOfNiceSubarrays {
    
    /**
     * Counts number of subarrays with exactly k odd numbers.
     * 
     * @param nums Array of integers
     * @param k Exact number of odd numbers required
     * @return Number of nice subarrays
     */
    public int numberOfSubarrays(int[] nums, int k) {
        // Edge cases
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        // Transform: exactly(k) = atMost(k) - atMost(k-1)
        return atMost(nums, k) - atMost(nums, k - 1);
    }
    
    /**
     * Counts number of subarrays with at most k odd numbers.
     * Uses sliding window technique.
     * 
     * Algorithm:
     * 1. Maintain window [left, right] with ≤ k odd numbers
     * 2. Expand right pointer, increment odd count if nums[right] is odd
     * 3. While odd count > k, shrink from left
     * 4. For each valid window, count all subarrays ending at right:
     *    count += right - left + 1
     * 
     * Why right - left + 1 works?
     * For window [left, right], all subarrays ending at right are:
     * [left, right], [left+1, right], ..., [right, right]
     * Total = right - left + 1 subarrays
     * 
     * @param nums Array of integers
     * @param k Maximum allowed odd numbers
     * @return Number of subarrays with ≤ k odd numbers
     */
    private int atMost(int[] nums, int k) {
        // Base case: if k < 0, no subarray can have ≤ k odd numbers
        if (k < 0) {
            return 0;
        }
        
        int left = 0;           // Left pointer of sliding window
        int oddCount = 0;       // Count of odd numbers in current window
        int totalCount = 0;     // Total count of valid subarrays
        
        for (int right = 0; right < nums.length; right++) {
            // Check if current number is odd (using bitwise AND for efficiency)
            if ((nums[right] & 1) == 1) {
                oddCount++;
            }
            
            // Shrink window while we have too many odd numbers
            while (oddCount > k) {
                if ((nums[left] & 1) == 1) {
                    oddCount--;
                }
                left++;
            }
            
            // All subarrays ending at 'right' with start in [left, right] are valid
            // Number of such subarrays = right - left + 1
            totalCount += right - left + 1;
        }
        
        return totalCount;
    }
    
    /**
     * Alternative solution: Using prefix sum transformation.
     * Convert odd numbers to 1, even numbers to 0, then problem becomes:
     * "Count number of subarrays with sum exactly k" (same as previous problem).
     * 
     * Then use prefix sum + hashmap approach.
     */
    public int numberOfSubarraysPrefixSum(int[] nums, int k) {
        // Transform: odd → 1, even → 0
        int[] transformed = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            transformed[i] = nums[i] % 2; // 1 for odd, 0 for even
        }
        
        // Now problem becomes: count subarrays with sum exactly k
        return countSubarraysWithSum(transformed, k);
    }
    
    /**
     * Helper: Count subarrays with sum exactly k (using prefix sum + hashmap).
     */
    private int countSubarraysWithSum(int[] nums, int k) {
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1); // Empty subarray has sum 0
        
        int prefixSum = 0;
        int count = 0;
        
        for (int num : nums) {
            prefixSum += num;
            
            // If (prefixSum - k) exists, we found subarrays with sum k
            count += prefixSumCount.getOrDefault(prefixSum - k, 0);
            
            // Update frequency of current prefix sum
            prefixSumCount.put(prefixSum, prefixSumCount.getOrDefault(prefixSum, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * Alternative: Two-pointer approach without atMost subtraction.
     * Directly count subarrays with exactly k odd numbers.
     * More complex but single-pass.
     */
    public int numberOfSubarraysDirect(int[] nums, int k) {
        int left = 0;           // Left boundary of current window
        int oddCount = 0;       // Count of odd numbers in window
        int count = 0;          // Result count
        int prefixZeros = 0;    // Count of leading even numbers before first odd
        
        for (int right = 0; right < nums.length; right++) {
            // Update odd count
            if (nums[right] % 2 == 1) {
                oddCount++;
            }
            
            // Reset prefix zeros when we encounter an odd
            if (oddCount > 0) {
                prefixZeros = 0;
            }
            
            // Shrink window if we have more than k odd numbers
            while (oddCount > k) {
                if (nums[left] % 2 == 1) {
                    oddCount--;
                }
                left++;
            }
            
            // Count leading even numbers at the beginning
            while (left < right && nums[left] % 2 == 0) {
                prefixZeros++;
                left++;
            }
            
            // If we have exactly k odd numbers, count valid subarrays
            if (oddCount == k) {
                count += (prefixZeros + 1);
            }
        }
        
        return count;
    }
    
    /**
     * Brute force solution for verification (O(n²)).
     */
    public int numberOfSubarraysBruteForce(int[] nums, int k) {
        int count = 0;
        int n = nums.length;
        
        for (int start = 0; start < n; start++) {
            int oddCount = 0;
            for (int end = start; end < n; end++) {
                if (nums[end] % 2 == 1) {
                    oddCount++;
                }
                if (oddCount == k) {
                    count++;
                } else if (oddCount > k) {
                    break; // Early termination since adding more numbers won't reduce odd count
                }
            }
        }
        
        return count;
    }
    
    /**
     * Visualization helper to show the sliding window process.
     */
    public void visualizeAtMost(int[] nums, int k) {
        System.out.println("\n=== Visualizing atMost(" + k + ") ===");
        System.out.println("Array: " + Arrays.toString(nums));
        System.out.println("(O = odd, E = even)");
        
        // Convert to O/E representation for clarity
        String[] oe = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            oe[i] = (nums[i] % 2 == 1) ? "O" : "E";
        }
        System.out.println("OE:    " + Arrays.toString(oe));
        System.out.println();
        
        System.out.println("Window | Odds | Action | Valid Subarrays Ending at Right | Count");
        System.out.println("-------|------|--------|---------------------------------|------");
        
        int left = 0;
        int oddCount = 0;
        int totalCount = 0;
        
        for (int right = 0; right < nums.length; right++) {
            // Update odd count
            if (nums[right] % 2 == 1) {
                oddCount++;
            }
            
            // Shrink if needed
            String action = "Expand to " + right;
            while (oddCount > k) {
                if (nums[left] % 2 == 1) {
                    oddCount--;
                }
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
            
            System.out.printf("%6s | %4d | %6s | %31s | %5d%n",
                window, oddCount, action, subarraysDesc, validSubarrays);
        }
        
        System.out.println("\nTotal count for atMost(" + k + "): " + totalCount);
    }
    
    /**
     * Test cases for counting nice subarrays.
     */
    public static void runTestCases() {
        CountNumberOfNiceSubarrays solver = new CountNumberOfNiceSubarrays();
        
        System.out.println("=== Count Number of Nice Subarrays Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {1, 1, 2, 1, 1};
        int k1 = 3;
        System.out.println("Test 1:");
        System.out.println("nums = " + Arrays.toString(test1) + ", k = " + k1);
        int result1 = solver.numberOfSubarrays(test1, k1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: 3");
        
        // Verify with brute force
        int brute1 = solver.numberOfSubarraysBruteForce(test1, k1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        System.out.println();
        
        // Test 2: All even numbers
        int[] test2 = {2, 4, 6, 8};
        int k2 = 1;
        System.out.println("Test 2 (all even):");
        System.out.println("nums = " + Arrays.toString(test2) + ", k = " + k2);
        int result2 = solver.numberOfSubarrays(test2, k2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: 0 (no odd numbers)");
        System.out.println();
        
        // Test 3: All odd numbers
        int[] test3 = {1, 3, 5, 7};
        int k3 = 2;
        System.out.println("Test 3 (all odd):");
        System.out.println("nums = " + Arrays.toString(test3) + ", k = " + k3);
        int result3 = solver.numberOfSubarrays(test3, k3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: 3 ([0-1], [1-2], [2-3])");
        System.out.println();
        
        // Test 4: Mixed with k = 0
        int[] test4 = {2, 1, 4, 6, 3};
        int k4 = 0;
        System.out.println("Test 4 (k=0):");
        System.out.println("nums = " + Arrays.toString(test4) + ", k = " + k4);
        int result4 = solver.numberOfSubarrays(test4, k4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: 2 (subarrays with only even numbers: [0-0], [2-3])");
        System.out.println();
        
        // Test 5: Single element
        int[] test5 = {1};
        int k5 = 1;
        System.out.println("Test 5 (single odd):");
        System.out.println("nums = " + Arrays.toString(test5) + ", k = " + k5);
        int result5 = solver.numberOfSubarrays(test5, k5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: 1");
        System.out.println();
        
        // Test 6: Complex pattern
        int[] test6 = {2, 2, 2, 1, 2, 2, 1, 2, 2, 2};
        int k6 = 2;
        System.out.println("Test 6 (from LeetCode):");
        System.out.println("nums = " + Arrays.toString(test6) + ", k = " + k6);
        int result6 = solver.numberOfSubarrays(test6, k6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: 16");
        
        // Verify with prefix sum approach
        int prefixResult = solver.numberOfSubarraysPrefixSum(test6, k6);
        System.out.println("Prefix sum result: " + prefixResult + " (matches: " + (result6 == prefixResult) + ")");
        System.out.println();
        
        // Test 7: Large k
        int[] test7 = {1, 2, 3, 4, 5, 6, 7};
        int k7 = 4;
        System.out.println("Test 7 (large k):");
        System.out.println("nums = " + Arrays.toString(test7) + ", k = " + k7);
        int result7 = solver.numberOfSubarrays(test7, k7);
        System.out.println("Result: " + result7);
        System.out.println("Brute force: " + solver.numberOfSubarraysBruteForce(test7, k7));
    }
    
    /**
     * Compare different approaches.
     */
    public static void compareApproaches() {
        System.out.println("\n=== Approach Comparison ===");
        
        CountNumberOfNiceSubarrays solver = new CountNumberOfNiceSubarrays();
        int[] nums = {1, 2, 1, 2, 1, 2, 1, 2, 1};
        int k = 3;
        
        System.out.println("nums = " + Arrays.toString(nums));
        System.out.println("k = " + k);
        System.out.println();
        
        System.out.println("1. Sliding Window (atMost technique):");
        long start = System.nanoTime();
        int result1 = solver.numberOfSubarrays(nums, k);
        long time1 = System.nanoTime() - start;
        System.out.println("   Result: " + result1 + ", Time: " + time1 + " ns");
        
        System.out.println("2. Prefix Sum + HashMap:");
        start = System.nanoTime();
        int result2 = solver.numberOfSubarraysPrefixSum(nums, k);
        long time2 = System.nanoTime() - start;
        System.out.println("   Result: " + result2 + ", Time: " + time2 + " ns");
        
        System.out.println("3. Direct Two-pointer:");
        start = System.nanoTime();
        int result3 = solver.numberOfSubarraysDirect(nums, k);
        long time3 = System.nanoTime() - start;
        System.out.println("   Result: " + result3 + ", Time: " + time3 + " ns");
        
        System.out.println("4. Brute Force (for verification):");
        start = System.nanoTime();
        int result4 = solver.numberOfSubarraysBruteForce(nums, k);
        long time4 = System.nanoTime() - start;
        System.out.println("   Result: " + result4 + ", Time: " + time4 + " ns");
        
        System.out.println("\nAll results match: " + 
                          (result1 == result2 && result2 == result3 && result3 == result4));
        System.out.println("Speedup vs brute force: " + (time4 * 1.0 / time1) + "x");
    }
    
    /**
     * Mathematical explanation of the transformation.
     */
    public static void explainTransformation() {
        System.out.println("\n=== Mathematical Transformation ===");
        System.out.println();
        System.out.println("Let f(k) = number of subarrays with ≤ k odd numbers");
        System.out.println("Let g(k) = number of subarrays with exactly k odd numbers");
        System.out.println();
        System.out.println("Observation 1: f(k) includes:");
        System.out.println("  - All subarrays with 0 odd numbers");
        System.out.println("  - All subarrays with 1 odd number");
        System.out.println("  - ...");
        System.out.println("  - All subarrays with k odd numbers");
        System.out.println();
        System.out.println("Observation 2: f(k-1) includes:");
        System.out.println("  - All subarrays with 0 odd numbers");
        System.out.println("  - All subarrays with 1 odd number");
        System.out.println("  - ...");
        System.out.println("  - All subarrays with k-1 odd numbers");
        System.out.println();
        System.out.println("Therefore:");
        System.out.println("  f(k) - f(k-1) = subarrays with exactly k odd numbers");
        System.out.println("  g(k) = f(k) - f(k-1)");
        System.out.println();
        System.out.println("Why is f(k) easier to compute?");
        System.out.println("  - For 'at most k' constraint, we can use sliding window");
        System.out.println("  - Window maintains ≤ k odd numbers");
        System.out.println("  - When window is valid, all subarrays ending at 'right' are counted");
        System.out.println("  - Count = right - left + 1 (all possible start positions)");
    }
    
    /**
     * Performance test with large dataset.
     */
    public static void performanceTest() {
        System.out.println("\n=== Performance Test ===");
        
        CountNumberOfNiceSubarrays solver = new CountNumberOfNiceSubarrays();
        
        // Generate large test array
        int n = 1000000;
        int[] nums = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            nums[i] = rand.nextInt(100); // Random numbers, about half will be odd
        }
        
        int k = 10;
        
        System.out.println("Array size: " + n + ", k = " + k);
        
        // Sliding window approach
        long start = System.currentTimeMillis();
        int result1 = solver.numberOfSubarrays(nums, k);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Sliding window: " + time1 + " ms, result: " + result1);
        
        // Prefix sum approach
        start = System.currentTimeMillis();
        int result2 = solver.numberOfSubarraysPrefixSum(nums, k);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Prefix sum: " + time2 + " ms, result: " + result2);
        
        System.out.println("Results match: " + (result1 == result2));
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the sliding window technique
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Visualization of atMost Technique:");
        System.out.println("=".repeat(60));
        
        CountNumberOfNiceSubarrays solver = new CountNumberOfNiceSubarrays();
        int[] nums = {1, 1, 2, 1, 1};
        int k = 3;
        solver.visualizeAtMost(nums, k);
        
        // Show the complete calculation
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Complete Calculation: exactly(3) = atMost(3) - atMost(2)");
        System.out.println("=".repeat(60));
        
        int atMost3 = solver.atMost(nums, 3);
        int atMost2 = solver.atMost(nums, 2);
        int exactly3 = atMost3 - atMost2;
        
        System.out.println("atMost(3) = " + atMost3);
        System.out.println("atMost(2) = " + atMost2);
        System.out.println("exactly(3) = " + atMost3 + " - " + atMost2 + " = " + exactly3);
        
        // Compare approaches
        compareApproaches();
        
        // Explain the mathematics
        explainTransformation();
        
        // Run performance test (optional)
        // performanceTest();
    }
}