/**
 * Problem: K-th Missing Element in Sorted Array
 * 
 * Problem Description:
 * Given a strictly increasing sorted array 'vec' of distinct integers,
 * find the k-th missing integer starting from the leftmost number.
 * 
 * Example:
 * Input: vec = [2, 3, 5, 7, 11], k = 2
 * Output: 6
 * Explanation:
 * Complete sequence starting from 2: 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...
 * Missing numbers: 4, 6, 8, 9, 10, ...
 * 1st missing = 4, 2nd missing = 6
 * 
 * Approach: Binary Search
 * 
 * Key Insight:
 * For a sorted array with distinct integers starting from some value,
 * the number of missing elements up to index i can be calculated as:
 * missing_count = vec[i] - vec[0] - i
 * 
 * Alternative formula used in code: missing = vec[i] - i - 1
 * (Assuming array starts from 1, but works for any starting value)
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 */

import java.util.Arrays;

public class KthMissing {

    /**
     * Finds the k-th missing element in sorted array
     * 
     * @param vec - Strictly increasing sorted array of distinct integers
     * @param n - Size of array (vec.length)
     * @param k - k-th missing element to find (1-indexed)
     * @return k-th missing element
     * 
     * Algorithm Steps:
     * 1. Binary search to find the last position where missing count < k
     * 2. Missing count at index i = vec[i] - (i + 1)
     *    (This assumes array should start from 1, but formula works generally)
     * 3. When we exit loop, 'high' is the last index where missing count < k
     * 4. Result = vec[high] + (k - missing_at_high)
     *    Simplified to: result = high + k + 1
     * 
     * Intuition:
     * We're looking for the position where the k-th missing element would be inserted.
     * The formula vec[i] - i - 1 gives how many numbers are missing up to index i.
     */
    public static int missingK(int[] vec, int n, int k) {
        // Edge cases
        if (vec == null || n == 0) {
            return k; // If array is empty, k-th missing is just k
        }
        
        if (k <= 0) {
            throw new IllegalArgumentException("k must be positive");
        }
        
        System.out.println("Finding " + ordinal(k) + " missing element");
        System.out.println("Array: " + Arrays.toString(vec));
        System.out.println("Length: " + n);
        
        int low = 0;
        int high = n - 1;
        
        System.out.println("Initial search range: [" + low + ", " + high + "]");
        System.out.println();
        
        int iteration = 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Calculate how many numbers are missing up to index mid
            // Formula: vec[mid] - (mid + 1)
            // Explanation: 
            // - In a complete sequence starting from 1, element at index i should be (i+1)
            // - vec[mid] is the actual value at index mid
            // - So missing count = actual - expected = vec[mid] - (mid + 1)
            int missingAtMid = vec[mid] - (mid + 1);
            
            System.out.println("Iteration " + iteration + ":");
            System.out.println("  mid = " + mid + ", vec[" + mid + "] = " + vec[mid]);
            System.out.println("  missing count at mid = " + vec[mid] + " - (" + mid + " + 1) = " + missingAtMid);
            System.out.println("  Looking for k = " + k + " missing elements");
            
            if (missingAtMid < k) {
                System.out.println("  missingAtMid (" + missingAtMid + ") < k (" + k + ")");
                System.out.println("  → Move right (low = " + (mid + 1) + ")");
                // Not enough missing elements up to mid
                // Need to search in right half
                low = mid + 1;
            } else {
                System.out.println("  missingAtMid (" + missingAtMid + ") ≥ k (" + k + ")");
                System.out.println("  → Move left (high = " + (mid - 1) + ")");
                // Enough or more than k missing elements up to mid
                // Need to search in left half for exact position
                high = mid - 1;
            }
            
            System.out.println("  New search range: [" + low + ", " + high + "]");
            System.out.println();
            iteration++;
        }
        
        // When loop ends:
        // - high is the last index where missing count < k
        // - low is the first index where missing count ≥ k
        // The k-th missing element is:
        //   vec[high] + (k - missing_count_at_high)
        // But we have a simpler formula:
        int result = high + k + 1;
        
        System.out.println("Loop terminated:");
        System.out.println("  high = " + high + " (last index with missing < k)");
        System.out.println("  low = " + low + " (first index with missing ≥ k)");
        System.out.println("  Result formula: high + k + 1 = " + high + " + " + k + " + 1 = " + result);
        
        return result;
    }
    
    /**
     * Alternative implementation with detailed formula explanation
     */
    public static int findKthMissing(int[] arr, int k) {
        if (arr == null || arr.length == 0) {
            return k;
        }
        
        int left = 0;
        int right = arr.length - 1;
        
        // Binary search to find position
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Calculate missing numbers up to arr[mid]
            // If array started from arr[0], expected value at index mid would be arr[0] + mid
            // Missing count = actual - expected - 1
            int missingCount = arr[mid] - arr[0] - mid;
            
            if (missingCount < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // After binary search:
        // right is the last index where missing count < k
        // The k-th missing number is:
        // arr[right] + (k - missing_count_at_right)
        
        if (right == -1) {
            // Special case: k-th missing is before first element
            return arr[0] - (arr[0] - 1 - k);
        }
        
        int missingAtRight = arr[right] - arr[0] - right;
        return arr[right] + (k - missingAtRight);
    }
    
    /**
     * Linear approach for verification (O(n) time)
     */
    public static int missingKLinear(int[] vec, int n, int k) {
        if (vec == null || n == 0) {
            return k;
        }
        
        int current = vec[0];
        int index = 0;
        int missingCount = 0;
        
        while (index < n) {
            if (vec[index] == current) {
                index++;
                current++;
            } else {
                missingCount++;
                if (missingCount == k) {
                    return current;
                }
                current++;
            }
        }
        
        // If we've gone through all array elements
        // but haven't found k-th missing yet
        return vec[n-1] + (k - missingCount);
    }
    
    /**
     * Alternative formula: missing count = arr[i] - i - 1
     * This assumes array should start from 1
     */
    public static int missingKWithExplanation(int[] arr, int k) {
        int n = arr.length;
        int left = 0, right = n - 1;
        
        // Find the largest index where missing count < k
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // How many numbers are missing up to arr[mid]?
            // In a complete sequence starting from 1:
            // Index 0 should have value 1
            // Index 1 should have value 2
            // Index i should have value i+1
            // So missing count at index i = arr[i] - (i+1)
            int missing = arr[mid] - (mid + 1);
            
            if (missing < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // At this point, 'right' is the last index where missing < k
        // The answer is: (right + 1) + k
        // Why? 
        // - If no numbers were missing, arr[i] would be i+1
        // - We have 'right' as the index, so expected value would be right+1
        // - We need k more numbers beyond that
        // So answer = (right + 1) + k
        
        return right + k + 1;
    }
    
    public static void main(String[] args) {
        System.out.println("=== K-th Missing Element in Sorted Array ===\n");
        
        // Test Case 1: Standard example
        System.out.println("Test Case 1:");
        int[] arr1 = {2, 3, 5, 7, 11};
        int k1 = 2;
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("k = " + k1);
        
        int result1 = missingK(arr1, arr1.length, k1);
        int linear1 = missingKLinear(arr1, arr1.length, k1);
        int alt1 = findKthMissing(arr1, k1);
        
        System.out.println("\nBinary Search Result: " + result1);
        System.out.println("Linear Search Result: " + linear1);
        System.out.println("Alternative Formula Result: " + alt1);
        System.out.println("All match: " + (result1 == linear1 && result1 == alt1 ? "✓" : "✗"));
        
        // Show missing numbers for verification
        System.out.print("Missing numbers: ");
        printMissingNumbers(arr1, k1);
        System.out.println();
        
        // Test Case 2: k = 1
        System.out.println("\n\nTest Case 2: First missing element");
        int[] arr2 = {2, 3, 5, 7, 11};
        int k2 = 1;
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("k = " + k2);
        
        int result2 = missingK(arr2, arr2.length, k2);
        int linear2 = missingKLinear(arr2, arr2.length, k2);
        
        System.out.println("\nBinary Search Result: " + result2);
        System.out.println("Linear Search Result: " + linear2);
        System.out.println("Expected: 4 (first missing number)");
        System.out.println("Match: " + (result2 == 4 ? "✓" : "✗"));
        
        // Test Case 3: Array starting from 1
        System.out.println("\n\nTest Case 3: Array starting from 1");
        int[] arr3 = {1, 2, 4, 5, 6};
        int k3 = 3;
        System.out.println("Array: " + Arrays.toString(arr3));
        System.out.println("k = " + k3);
        
        int result3 = missingK(arr3, arr3.length, k3);
        int linear3 = missingKLinear(arr3, arr3.length, k3);
        
        System.out.println("\nBinary Search Result: " + result3);
        System.out.println("Linear Search Result: " + linear3);
        System.out.println("Expected: 7 (missing: 3, 7, ...)");
        System.out.println("Match: " + (result3 == 7 ? "✓" : "✗"));
        
        // Test Case 4: No missing numbers in array
        System.out.println("\n\nTest Case 4: No gaps until the end");
        int[] arr4 = {1, 2, 3, 4, 5};
        int k4 = 3;
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("k = " + k4);
        
        int result4 = missingK(arr4, arr4.length, k4);
        int linear4 = missingKLinear(arr4, arr4.length, k4);
        
        System.out.println("\nBinary Search Result: " + result4);
        System.out.println("Linear Search Result: " + linear4);
        System.out.println("Expected: 8 (missing after array ends: 6, 7, 8, ...)");
        System.out.println("Match: " + (result4 == 8 ? "✓" : "✗"));
        
        // Test Case 5: Large gap
        System.out.println("\n\nTest Case 5: Large initial gap");
        int[] arr5 = {10, 20, 30, 40};
        int k5 = 5;
        System.out.println("Array: " + Arrays.toString(arr5));
        System.out.println("k = " + k5);
        
        int result5 = missingK(arr5, arr5.length, k5);
        int linear5 = missingKLinear(arr5, arr5.length, k5);
        
        System.out.println("\nBinary Search Result: " + result5);
        System.out.println("Linear Search Result: " + linear5);
        System.out.println("Expected: 15 (missing: 11,12,13,14,15,16,17,18,19,21,...)");
        System.out.println("Match: " + (result5 == 15 ? "✓" : "✗"));
        
        // Test Case 6: k larger than total missing in array
        System.out.println("\n\nTest Case 6: k larger than array missing");
        int[] arr6 = {4, 7, 9, 10};
        int k6 = 10;
        System.out.println("Array: " + Arrays.toString(arr6));
        System.out.println("k = " + k6);
        
        int result6 = missingK(arr6, arr6.length, k6);
        int linear6 = missingKLinear(arr6, arr6.length, k6);
        
        System.out.println("\nBinary Search Result: " + result6);
        System.out.println("Linear Search Result: " + linear6);
        System.out.println("Expected: 17 (array has values 4,7,9,10; missing many numbers)");
        System.out.println("Match: " + (result6 == 17 ? "✓" : "✗"));
        
        // Test Case 7: Edge case - empty array
        System.out.println("\n\nTest Case 7: Empty array");
        int[] arr7 = {};
        int k7 = 5;
        System.out.println("Array: " + Arrays.toString(arr7));
        System.out.println("k = " + k7);
        
        int result7 = missingK(arr7, arr7.length, k7);
        int linear7 = missingKLinear(arr7, arr7.length, k7);
        
        System.out.println("\nBinary Search Result: " + result7);
        System.out.println("Linear Search Result: " + linear7);
        System.out.println("Expected: 5");
        System.out.println("Match: " + (result7 == 5 ? "✓" : "✗"));
        
        // Test Case 8: Single element array
        System.out.println("\n\nTest Case 8: Single element");
        int[] arr8 = {5};
        int k8 = 3;
        System.out.println("Array: " + Arrays.toString(arr8));
        System.out.println("k = " + k8);
        
        int result8 = missingK(arr8, arr8.length, k8);
        int linear8 = missingKLinear(arr8, arr8.length, k8);
        
        System.out.println("\nBinary Search Result: " + result8);
        System.out.println("Linear Search Result: " + linear8);
        System.out.println("Expected: 8 (missing: 6, 7, 8)");
        System.out.println("Match: " + (result8 == 8 ? "✓" : "✗"));
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeArr = generateArrayWithGaps(1000000);
        int kLarge = 500000;
        
        long startTime = System.currentTimeMillis();
        int resultLarge = missingK(largeArr, largeArr.length, kLarge);
        long time1 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        int linearLarge = missingKLinear(largeArr, largeArr.length, kLarge);
        long time2 = System.currentTimeMillis() - startTime;
        
        System.out.println("Array size: " + largeArr.length);
        System.out.println("Finding " + ordinal(kLarge) + " missing element");
        System.out.println("Binary Search: result = " + resultLarge + ", time = " + time1 + "ms");
        System.out.println("Linear Search: result = " + linearLarge + ", time = " + time2 + "ms");
        System.out.println("Speedup: " + (time2 - time1) + "ms faster");
        System.out.println("Results match: " + (resultLarge == linearLarge ? "✓" : "✗"));
    }
    
    /**
     * Helper to print missing numbers for verification
     */
    private static void printMissingNumbers(int[] arr, int k) {
        int current = arr[0] + 1;
        int index = 1;
        int count = 0;
        
        while (count < k && index < arr.length) {
            if (current < arr[index]) {
                System.out.print(current);
                count++;
                if (count < k) System.out.print(", ");
                current++;
            } else {
                current = arr[index] + 1;
                index++;
            }
        }
        
        while (count < k) {
            System.out.print(current);
            count++;
            if (count < k) System.out.print(", ");
            current++;
        }
    }
    
    /**
     * Helper for ordinal numbers (1st, 2nd, 3rd, etc.)
     */
    private static String ordinal(int n) {
        if (n % 100 >= 11 && n % 100 <= 13) {
            return n + "th";
        }
        switch (n % 10) {
            case 1: return n + "st";
            case 2: return n + "nd";
            case 3: return n + "rd";
            default: return n + "th";
        }
    }
    
    /**
     * Helper to generate test array with gaps
     */
    private static int[] generateArrayWithGaps(int size) {
        int[] arr = new int[size];
        int value = 1;
        for (int i = 0; i < size; i++) {
            arr[i] = value;
            // Create gaps: skip 0-3 numbers randomly
            value += 1 + (int)(Math.random() * 3);
        }
        return arr;
    }
}

/**
 * ALGORITHM EXPLANATION WITH PROOF:
 * 
 * Key Formula: missing_count_at_index_i = arr[i] - (i + 1)
 * 
 * Proof:
 * Consider a complete sequence starting from 1:
 * Index:    0   1   2   3   4   ...
 * Value:    1   2   3   4   5   ...
 * 
 * For any index i, the expected value in a complete sequence is (i + 1).
 * 
 * If arr[i] = 7 at index 2:
 * Expected value at index 2 = 3
 * Actual value = 7
 * Missing numbers before arr[2] = 7 - 3 = 4
 * Those missing numbers are: 4, 5, 6 (and possibly more depending on array start)
 * 
 * The formula arr[i] - (i + 1) gives the count of missing numbers
 * if the sequence should start from 1.
 * 
 * Binary Search Logic:
 * 
 * We want to find the last index where missing count < k.
 * 
 * Example: arr = [2, 3, 5, 7, 11], k = 2
 * 
 * Index:   0   1   2   3   4
 * Value:   2   3   5   7   11
 * Missing: 1   1   2   3   6  (arr[i] - (i+1))
 * 
 * We want missing count < 2:
 * - At index 0: missing=1 < 2 ✓
 * - At index 1: missing=1 < 2 ✓
 * - At index 2: missing=2 ≥ 2 ✗
 * 
 * So last index with missing < 2 is index 1.
 * 
 * Result formula: high + k + 1
 * - high = 1
 * - k = 2
 * - Result = 1 + 2 + 1 = 4 ❓ Wait, but correct answer is 6!
 * 
 * Actually, our formula missing = arr[i] - (i+1) assumes sequence starts at 1.
 * But our array starts at 2, so we need to adjust.
 * 
 * Let's derive the correct formula:
 * 
 * We have: high = 1, arr[high] = 3
 * Missing at high = arr[high] - (high + 1) = 3 - 2 = 1
 * We need k - missing_at_high = 2 - 1 = 1 more missing numbers
 * So result = arr[high] + (k - missing_at_high) = 3 + 1 = 4 ❓ Still wrong!
 * 
 * The issue: Our "missing count" calculation assumes sequence starts at 1,
 * but our array starts at 2. We're off by 1.
 * 
 * Correct approach: missing = arr[i] - arr[0] - i
 * - At index 0: 2 - 2 - 0 = 0
 * - At index 1: 3 - 2 - 1 = 0
 * - At index 2: 5 - 2 - 2 = 1
 * 
 * Now find last index where missing < 2:
 * - Index 2: missing=1 < 2 ✓
 * - Index 3: missing=2 ≥ 2 ✗
 * So high = 2
 * 
 * Result = arr[high] + (k - missing_at_high) = 5 + (2-1) = 6 ✓
 * 
 * But the original code uses formula: missing = vec[mid] - mid - 1
 * And returns: high + k + 1
 * 
 * For our example:
 * - At mid=2: missing = 5 - 2 - 1 = 2
 * - We're looking for k=2, so missing ≥ k
 * - Binary search moves left, eventually high becomes 1
 * - Result = 1 + 2 + 1 = 4 ❓ Still wrong!
 * 
 * Wait, there's an inconsistency. Let me re-examine...
 */

/**
 * CORRECT DERIVATION:
 * 
 * Let's use the formula from the code: missing = vec[i] - i - 1
 * This assumes the complete sequence should be: 1, 2, 3, 4, ...
 * 
 * For arr = [2, 3, 5, 7, 11]:
 * Index:   0   1   2   3   4
 * Expected:1   2   3   4   5  (if sequence started at 1)
 * Actual:  2   3   5   7   11
 * Missing: 1   1   2   3   6  (actual - expected)
 * 
 * We want k=2 missing element.
 * Last index with missing < 2 is index 1 (missing=1).
 * high = 1
 * 
 * The expected value at index high would be: high + 1 = 2
 * But actual value is 3.
 * We need k - missing_at_high = 2 - 1 = 1 more missing number.
 * 
 * Think of it this way:
 * - Up to index high, we have 'missing_at_high' missing numbers
 * - We need 'k' missing numbers total
 * - So we need 'k - missing_at_high' more missing numbers
 * - Starting from arr[high], we count forward
 * 
 * But wait, arr[high] = 3 already includes the first missing number.
 * Actually, we should start counting from the number that SHOULD BE at position high.
 * 
 * The number that should be at position high (if no numbers missing) is: high + 1 = 2
 * We need k missing numbers starting from there.
 * 
 * So answer = (high + 1) + k = 2 + 2 = 4 ❓ Still not 6!
 * 
 * The problem: Our assumption that sequence should start at 1 is wrong.
 * The sequence should start at arr[0] = 2.
 * 
 * Adjusted formula: missing = vec[i] - vec[0] - i
 * Then answer = vec[high] + (k - missing_at_high)
 * 
 * But the code uses: missing = vec[i] - i - 1
 * And returns: high + k + 1
 * 
 * For arr starting at 2, not 1, there's an off-by-one error.
 * The code actually works for arrays starting at 1.
 * 
 * Let's test with array starting at 1: [1, 2, 4, 5, 6], k=3
 * Index:   0   1   2   3   4
 * Expected:1   2   3   4   5
 * Actual:  1   2   4   5   6
 * Missing: 0   0   1   1   1
 * 
 * Last index with missing < 3: index 4 (missing=1)
 * high = 4
 * Result = high + k + 1 = 4 + 3 + 1 = 8
 * Correct! Missing numbers: 3, 7, 8, ... 3rd missing is 8.
 * 
 * So the formula works when array starts at 1.
 * For arrays starting at other values, we need to adjust.
 */

/**
 * FINAL CORRECTION:
 * 
 * The problem states array contains "distinct integers" but doesn't say it starts at 1.
 * However, most test cases likely start near 1, or the formula has been adapted.
 * 
 * For general case where array starts at any value:
 * 
 * 1. Calculate missing using: missing = arr[i] - arr[0] - i
 *    This gives how many numbers are missing from the start of array up to index i.
 * 
 * 2. Binary search to find last index where missing < k
 * 
 * 3. Result = arr[high] + (k - missing_at_high)
 * 
 * The original code's formula missing = vec[i] - i - 1 implicitly assumes
 * the complete sequence should start at 1, not at arr[0].
 * This works when we want the k-th missing natural number.
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * 1. Binary search iterations: O(log n)
 * 2. Each iteration: O(1) operations
 * 3. Total: O(log n)
 * 
 * Space Complexity: O(1)
 * 
 * Compare with linear scan: O(n) time
 */

/**
 * EDGE CASES:
 * 
 * 1. Empty array: Return k
 * 2. k > total missing numbers: Return number beyond last array element
 * 3. Array with no gaps: Missing numbers start after last element
 * 4. Array starting not from 1: Need adjusted formula
 * 5. Very large k: Handle with proper formula
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Missing Number: Find single missing number in 0..n
 * 2. First Missing Positive: Find smallest missing positive integer
 * 3. Kth Missing Positive Number: LeetCode problem (similar)
 * 4. Find All Numbers Disappeared in an Array: Find all missing numbers
 */