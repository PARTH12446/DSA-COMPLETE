/**
 * Problem: Smallest Divisor with Given Limit
 * 
 * Problem Description:
 * Given an array of integers 'arr' and an integer 'limit', 
 * we need to find the smallest positive integer divisor such that 
 * when we divide each element of the array by this divisor (taking ceiling),
 * the sum of these results is less than or equal to limit.
 * 
 * Formula: sum(ceil(arr[i] / divisor)) ≤ limit
 * 
 * Example:
 * Input: arr = [1, 2, 5, 9], limit = 6
 * Output: 5
 * Explanation:
 * divisor = 5 → ceil(1/5)=1, ceil(2/5)=1, ceil(5/5)=1, ceil(9/5)=2
 * sum = 1+1+1+2 = 5 ≤ 6 ✓
 * 
 * divisor = 4 → ceil(1/4)=1, ceil(2/4)=1, ceil(5/4)=2, ceil(9/4)=3
 * sum = 1+1+2+3 = 7 > 6 ✗
 * 
 * Approach: Binary Search on Divisor
 * 
 * Key Insight:
 * - Small divisor → large sum (since ceil(x/d) is large when d is small)
 * - Large divisor → small sum (since ceil(x/d) is small when d is large)
 * - The function f(d) = sum(ceil(arr[i]/d)) is monotonic decreasing
 * 
 * Time Complexity: O(n log(max(arr)))
 * Space Complexity: O(1)
 */

import java.util.Arrays;

public class SmallestDivisor {

    /**
     * Finds the smallest divisor such that sum of ceilings ≤ limit
     * 
     * @param arr - Array of positive integers
     * @param limit - Maximum allowed sum
     * @return Smallest divisor satisfying the condition
     * 
     * Binary Search Bounds:
     * - low = 1 (smallest possible divisor)
     * - high = max(arr) (largest needed divisor)
     * 
     * Monotonic Property:
     * Let f(d) = sum(ceil(arr[i]/d))
     * - If d increases → f(d) decreases or stays same
     * - If d decreases → f(d) increases or stays same
     * 
     * We want smallest d such that f(d) ≤ limit
     */
    public static int smallestDivisor(int[] arr, int limit) {
        // Edge cases
        if (arr == null || arr.length == 0) {
            return 1; // Empty array, any divisor works
        }
        
        if (limit < arr.length) {
            // Minimum possible sum is length of array (when divisor → ∞)
            // If limit is less than that, impossible
            return -1; // Or throw exception
        }
        
        // Find maximum element for upper bound
        int maxVal = 0;
        for (int num : arr) {
            maxVal = Math.max(maxVal, num);
        }
        
        System.out.println("Finding smallest divisor for:");
        System.out.println("  Array: " + Arrays.toString(arr));
        System.out.println("  Limit: " + limit);
        System.out.println("  Max value: " + maxVal);
        System.out.println("  Search range: [1, " + maxVal + "]");
        
        int low = 1;
        int high = maxVal;
        int answer = -1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Calculate sum of ceilings
            long sum = calculateSum(arr, mid);
            System.out.printf("\n  Divisor %d → sum = %d", mid, sum);
            
            if (sum <= limit) {
                System.out.println(" ✓ (≤ " + limit + ")");
                // Sum is within limit, try smaller divisor
                answer = mid;
                high = mid - 1;
            } else {
                System.out.println(" ✗ (> " + limit + ")");
                // Sum exceeds limit, need larger divisor
                low = mid + 1;
            }
        }
        
        return answer;
    }
    
    /**
     * Calculates sum of ceil(arr[i]/divisor) for all elements
     * 
     * @param arr - Array of numbers
     * @param divisor - Divisor to use
     * @return Sum of ceilings
     * 
     * Ceiling division trick: ceil(a/b) = (a + b - 1) / b
     * This avoids floating point operations
     */
    private static long calculateSum(int[] arr, int divisor) {
        long sum = 0;
        
        for (int num : arr) {
            // Ceiling division: (num + divisor - 1) / divisor
            sum += (num + (long)divisor - 1) / divisor;
        }
        
        return sum;
    }
    
    /**
     * Alternative implementation with detailed calculation logging
     */
    private static long calculateSumVerbose(int[] arr, int divisor, long limit) {
        System.out.printf("\n    Calculating for divisor %d:\n", divisor);
        long sum = 0;
        
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            // Ceiling division using integer arithmetic
            long ceilValue = (num + (long)divisor - 1) / divisor;
            sum += ceilValue;
            
            System.out.printf("      ceil(%d/%d) = %d", num, divisor, ceilValue);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
            
            // Early exit if sum already exceeds limit
            if (sum > limit) {
                System.out.printf("... (sum = %d > limit = %d)\n", sum, limit);
                return sum;
            }
        }
        
        System.out.printf("\n    Total sum = %d\n", sum);
        return sum;
    }
    
    /**
     * Alternative: Using Math.ceil (slower but clearer)
     */
    private static long calculateSumWithCeil(int[] arr, int divisor) {
        long sum = 0;
        
        for (int num : arr) {
            sum += (int) Math.ceil((double) num / divisor);
        }
        
        return sum;
    }
    
    /**
     * Optimized version with early exit and cleaner code
     */
    public static int smallestDivisorOptimized(int[] arr, int limit) {
        if (arr == null || arr.length == 0) {
            return 1;
        }
        
        // Find maximum element for binary search upper bound
        int maxVal = 0;
        for (int num : arr) {
            maxVal = Math.max(maxVal, num);
        }
        
        // If limit is less than number of elements, impossible
        // (minimum sum is arr.length when divisor = maxVal)
        if (limit < arr.length) {
            return -1;
        }
        
        int left = 1;
        int right = maxVal;
        int result = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (isValidDivisor(arr, limit, mid)) {
                // This divisor works, try smaller
                result = mid;
                right = mid - 1;
            } else {
                // This divisor doesn't work, need larger
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    /**
     * Checks if a divisor is valid (sum ≤ limit)
     */
    private static boolean isValidDivisor(int[] arr, int limit, int divisor) {
        long sum = 0;
        
        for (int num : arr) {
            // Ceiling division without floating point
            sum += (num + (long)divisor - 1) / divisor;
            
            // Early exit if already exceeded limit
            if (sum > limit) {
                return false;
            }
        }
        
        return sum <= limit;
    }
    
    /**
     * Linear search for comparison (slow)
     */
    public static int smallestDivisorLinear(int[] arr, int limit) {
        if (arr == null || arr.length == 0) {
            return 1;
        }
        
        int maxVal = 0;
        for (int num : arr) {
            maxVal = Math.max(maxVal, num);
        }
        
        // Try each divisor from 1 to maxVal
        for (int divisor = 1; divisor <= maxVal; divisor++) {
            long sum = 0;
            
            for (int num : arr) {
                sum += (num + divisor - 1) / divisor;
                
                if (sum > limit) {
                    break; // Early exit for this divisor
                }
            }
            
            if (sum <= limit) {
                return divisor;
            }
        }
        
        return maxVal; // Should always work
    }
    
    public static void main(String[] args) {
        System.out.println("=== Smallest Divisor with Given Limit ===\n");
        
        // Test Case 1: Example from problem
        System.out.println("Test Case 1:");
        int[] arr1 = {1, 2, 5, 9};
        int limit1 = 6;
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Limit: " + limit1);
        
        int result1 = smallestDivisor(arr1, limit1);
        int linear1 = smallestDivisorLinear(arr1, limit1);
        
        System.out.println("\nBinary Search Result: " + result1);
        System.out.println("Linear Search Result: " + linear1);
        System.out.println("Expected: 5");
        System.out.println("Match: " + (result1 == 5 ? "✓" : "✗"));
        System.out.println("Results match: " + (result1 == linear1 ? "✓" : "✗"));
        
        // Show detailed calculation for divisor 5
        System.out.println("\nVerification for divisor 5:");
        calculateSumVerbose(arr1, 5, limit1);
        
        // Test Case 2: All numbers divisible
        System.out.println("\n\nTest Case 2:");
        int[] arr2 = {8, 4, 2, 6};
        int limit2 = 8;
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("Limit: " + limit2);
        
        int result2 = smallestDivisor(arr2, limit2);
        System.out.println("\nResult: " + result2);
        System.out.println("Expected: 2 (ceil(8/2)=4, ceil(4/2)=2, ceil(2/2)=1, ceil(6/2)=3 → sum=10)");
        System.out.println("Expected: 3 (ceil(8/3)=3, ceil(4/3)=2, ceil(2/3)=1, ceil(6/3)=2 → sum=8)");
        System.out.println("Match: " + (result2 == 3 ? "✓" : "✗"));
        
        // Test Case 3: Limit equals array length (minimum possible sum)
        System.out.println("\n\nTest Case 3:");
        int[] arr3 = {10, 20, 30, 40};
        int limit3 = 4; // Same as array length
        System.out.println("Array: " + Arrays.toString(arr3));
        System.out.println("Limit: " + limit3);
        
        int result3 = smallestDivisor(arr3, limit3);
        System.out.println("\nResult: " + result3);
        System.out.println("Expected: 40 (largest element, gives sum = 4)");
        System.out.println("Match: " + (result3 == 40 ? "✓" : "✗"));
        
        // Test Case 4: Large numbers
        System.out.println("\n\nTest Case 4:");
        int[] arr4 = {100, 200, 300, 400};
        int limit4 = 10;
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("Limit: " + limit4);
        
        int result4 = smallestDivisor(arr4, limit4);
        System.out.println("\nResult: " + result4);
        // Let's calculate expected
        System.out.println("Verifying...");
        for (int d = 1; d <= 400; d++) {
            long sum = 0;
            for (int num : arr4) {
                sum += (num + d - 1) / d;
            }
            if (sum <= limit4) {
                System.out.println("First valid divisor: " + d);
                System.out.println("Expected: " + d);
                System.out.println("Match: " + (result4 == d ? "✓" : "✗"));
                break;
            }
        }
        
        // Test Case 5: Single element array
        System.out.println("\n\nTest Case 5:");
        int[] arr5 = {25};
        int limit5 = 5;
        System.out.println("Array: " + Arrays.toString(arr5));
        System.out.println("Limit: " + limit5);
        
        int result5 = smallestDivisor(arr5, limit5);
        System.out.println("\nResult: " + result5);
        System.out.println("Expected: 5 (ceil(25/5)=5)");
        System.out.println("Match: " + (result5 == 5 ? "✓" : "✗"));
        
        // Test Case 6: All elements same
        System.out.println("\n\nTest Case 6:");
        int[] arr6 = {7, 7, 7, 7, 7};
        int limit6 = 10;
        System.out.println("Array: " + Arrays.toString(arr6));
        System.out.println("Limit: " + limit6);
        
        int result6 = smallestDivisor(arr6, limit6);
        System.out.println("\nResult: " + result6);
        // ceil(7/2)=4, 4*5=20 > 10, ceil(7/3)=3, 3*5=15 > 10, ceil(7/4)=2, 2*5=10 ✓
        System.out.println("Expected: 4");
        System.out.println("Match: " + (result6 == 4 ? "✓" : "✗"));
        
        // Test Case 7: Impossible case (limit too small)
        System.out.println("\n\nTest Case 7:");
        int[] arr7 = {1, 2, 3};
        int limit7 = 2; // Minimum possible sum is 3 (when divisor → ∞)
        System.out.println("Array: " + Arrays.toString(arr7));
        System.out.println("Limit: " + limit7);
        
        int result7 = smallestDivisor(arr7, limit7);
        System.out.println("\nResult: " + result7);
        System.out.println("Expected: -1 (impossible)");
        System.out.println("Match: " + (result7 == -1 ? "✓" : "✗"));
        
        // Test Case 8: Large divisor needed
        System.out.println("\n\nTest Case 8:");
        int[] arr8 = {1, 1000};
        int limit8 = 2;
        System.out.println("Array: " + Arrays.toString(arr8));
        System.out.println("Limit: " + limit8);
        
        int result8 = smallestDivisor(arr8, limit8);
        System.out.println("\nResult: " + result8);
        System.out.println("Expected: 1000 (ceil(1/1000)=1, ceil(1000/1000)=1 → sum=2)");
        System.out.println("Match: " + (result8 == 1000 ? "✓" : "✗"));
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeArr = new int[100000];
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = (int)(Math.random() * 10000) + 1;
        }
        int largeLimit = 1000000;
        
        long startTime = System.currentTimeMillis();
        int binaryResult = smallestDivisor(largeArr, largeLimit);
        long binaryTime = System.currentTimeMillis() - startTime;
        
        // Linear search is too slow for large arrays
        if (largeArr.length <= 10000) {
            startTime = System.currentTimeMillis();
            int linearResult = smallestDivisorLinear(largeArr, largeLimit);
            long linearTime = System.currentTimeMillis() - startTime;
            
            System.out.println("Array size: " + largeArr.length);
            System.out.println("Binary Search: " + binaryResult + " (" + binaryTime + "ms)");
            System.out.println("Linear Search: " + linearResult + " (" + linearTime + "ms)");
            System.out.println("Speedup: " + (linearTime/binaryTime) + "x faster");
        } else {
            System.out.println("Array size: " + largeArr.length + " (too large for linear search)");
            System.out.println("Binary Search: " + binaryResult + " (" + binaryTime + "ms)");
        }
        
        // Additional verification with random tests
        System.out.println("\n=== Random Test Verification ===");
        int numTests = 1000;
        int passed = 0;
        
        for (int test = 0; test < numTests; test++) {
            int n = (int)(Math.random() * 100) + 1;
            int[] randomArr = new int[n];
            
            for (int i = 0; i < n; i++) {
                randomArr[i] = (int)(Math.random() * 1000) + 1;
            }
            
            // Choose a reasonable limit
            int randomLimit = n + (int)(Math.random() * 1000);
            
            int binaryAns = smallestDivisor(randomArr, randomLimit);
            int linearAns = smallestDivisorLinear(randomArr, randomLimit);
            
            if (binaryAns == linearAns) {
                passed++;
            } else {
                System.out.println("Mismatch: arr=" + Arrays.toString(randomArr) +
                                 ", limit=" + randomLimit +
                                 ", binary=" + binaryAns + ", linear=" + linearAns);
            }
        }
        
        System.out.println("Passed " + passed + "/" + numTests + " random tests");
    }
}

/**
 * MATHEMATICAL INSIGHTS:
 * 
 * 1. Ceiling Division Formula:
 *    ceil(a/b) = (a + b - 1) / b  for positive integers
 *    Example: ceil(7/3) = (7 + 3 - 1)/3 = 9/3 = 3
 * 
 * 2. Monotonic Property Proof:
 *    Let f(d) = Σ ceil(arr[i]/d)
 *    For d₁ < d₂:
 *    ceil(arr[i]/d₁) ≥ ceil(arr[i]/d₂)  (larger denominator → smaller quotient)
 *    Therefore f(d₁) ≥ f(d₂)
 *    So f(d) is monotonic decreasing
 * 
 * 3. Search Space Bounds:
 *    - Lower bound: 1 (smallest possible divisor)
 *    - Upper bound: max(arr) (when d = max, each ceil is at least 1)
 * 
 * 4. Impossibility Condition:
 *    Minimum possible sum = n (when d → ∞, each ceil → 1)
 *    So if limit < n, impossible
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * Let n = arr.length, m = max(arr)
 * 
 * Binary Search Approach:
 * - Binary search iterations: O(log m)
 * - Each validation: O(n) to calculate sum
 * - Total: O(n log m)
 * 
 * Linear Search:
 * - O(n * m) worst case
 * - Impractical for large m
 */

/**
 * VISUAL EXAMPLE:
 * 
 * arr = [1, 2, 5, 9], limit = 6
 * 
 * Try d = 5:
 * ceil(1/5)=1, ceil(2/5)=1, ceil(5/5)=1, ceil(9/5)=2
 * sum = 1+1+1+2 = 5 ≤ 6 ✓
 * 
 * Try d = 4:
 * ceil(1/4)=1, ceil(2/4)=1, ceil(5/4)=2, ceil(9/4)=3
 * sum = 1+1+2+3 = 7 > 6 ✗
 * 
 * Try d = 5 is smallest valid divisor
 */

/**
 * EDGE CASES:
 * 
 * 1. Empty array: Any divisor works, return 1
 * 2. limit < n: Impossible, return -1
 * 3. Single element: Find smallest d such that ceil(num/d) ≤ limit
 * 4. All elements same: Formula simplifies
 * 5. limit very large: Small divisor (1) works
 * 6. limit = n: Need divisor = max(arr)
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Koko Eating Bananas (LeetCode 875): Very similar
 *    - Instead of ceil(arr[i]/d), it's also about division
 *    - Same binary search pattern
 * 
 * 2. Capacity To Ship Packages: Similar "minimize maximum" pattern
 * 3. Split Array Largest Sum: Same mathematical formulation
 * 4. Painter's Partition: Another variant
 * 
 * All follow "binary search on answer + monotonic validation" pattern.
 */

/**
 * PRACTICAL APPLICATIONS:
 * 
 * 1. Resource Allocation: Divide resources among groups
 * 2. Load Balancing: Distribute work with maximum load constraint
 * 3. Data Partitioning: Split data with size limits
 * 4. Manufacturing: Batch production with capacity limits
 * 5. Finance: Risk allocation with constraints
 */

/**
 * LEETCODE VERSION (1283):
 * This is LeetCode 1283: Find the Smallest Divisor Given a Threshold
 * Constraints:
 * - 1 <= nums.length <= 5 * 10^4
 * - 1 <= nums[i] <= 10^6
 * - nums.length <= threshold <= 10^6
 * 
 * For binary search: O(n log m) = O(5*10^4 * log(10^6)) ≈ O(5*10^4 * 20) ≈ 10^6 operations ✓
 */

/**
 * OPTIMIZATION TIPS:
 * 
 * 1. Use long for sum to prevent overflow
 * 2. Early exit in validation when sum > limit
 * 3. Use integer ceiling division trick (a+b-1)/b
 * 4. Optimize upper bound: max(arr) is sufficient
 * 5. For very large arrays, consider parallel sum calculation
 */

/**
 * ALTERNATIVE APPROACHES:
 * 
 * 1. Mathematical formula: Could derive exact divisor in some cases
 * 2. Precomputation: For repeated queries with same array
 * 3. Segment tree: For dynamic array updates (not needed here)
 * 
 * Binary search is optimal for given constraints.
 */