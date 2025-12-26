/**
 * Problem: Find the Upper Bound of a value in a sorted array
 * 
 * Definition: 
 * Upper Bound is the index of the FIRST element in the array that is STRICTLY GREATER than x
 * 
 * Formal Definition:
 * For sorted array arr of size n and target x:
 * Upper Bound = smallest index i such that arr[i] > x
 * If no such element exists, return n (beyond the last index)
 * 
 * Example 1:
 * Input: arr = [1, 2, 3, 5, 7, 9], x = 5
 * Output: 4 (arr[4] = 7 > 5)
 * 
 * Example 2:
 * Input: arr = [1, 2, 2, 2, 3, 4], x = 2
 * Output: 4 (arr[4] = 3 > 2, skipping all 2's)
 * 
 * Example 3:
 * Input: arr = [1, 2, 3, 4, 5], x = 10
 * Output: 5 (no element > 10, return array length)
 * 
 * Visual Representation:
 * Index:   0  1  2  3  4  5
 * Array:  [1, 2, 3, 5, 7, 9]
 * Target: x = 5
 *           ↓ ↓ ↓ ↓ ↓ ↓ ↓
 *          ≤5 ≤5 ≤5 ≤5 >5 >5
 * Upper Bound: index 4 (first >5)
 */

public class UpperBound {

    /**
     * Finds the upper bound of x in sorted array arr
     * 
     * @param arr - Sorted array in non-decreasing order
     * @param n - Size of the array (arr.length)
     * @param x - Target value
     * @return Index of first element > x, or n if no such element
     * 
     * Time Complexity: O(log n)
     * Space Complexity: O(1)
     * 
     * Algorithm:
     * 1. Initialize answer as n (default if no element > x)
     * 2. Binary search to find the smallest index where arr[mid] > x
     * 3. When arr[mid] > x found, it's a candidate for upper bound
     * 4. Try to find a smaller index with same property (move left)
     * 5. When arr[mid] ≤ x, upper bound must be to the right
     */
    public static int upperBound(int[] arr, int n, int x) {
        int low = 0;
        int high = n - 1;
        // Initialize answer as n (beyond last index)
        // This handles case when no element > x
        int answer = n;
        
        while (low <= high) {
            // Use this to prevent integer overflow
            int mid = low + (high - low) / 2;
            
            if (arr[mid] > x) {
                // Found a candidate for upper bound
                // arr[mid] is > x, so answer could be mid
                // But we need the FIRST such element, so search left
                answer = mid;      // Update answer
                high = mid - 1;    // Try to find smaller index
            } else {
                // arr[mid] <= x
                // Upper bound must be to the right
                low = mid + 1;
            }
        }
        
        return answer;
    }
    
    /**
     * Alternative implementation with clearer variable names
     * This version uses result variable instead of reusing n
     */
    public static int upperBoundAlt(int[] arr, int n, int x) {
        int left = 0;
        int right = n - 1;
        int result = n; // Default: no element > x
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // Key condition for upper bound
            if (arr[mid] > x) {
                // We found an element > x
                // Record this position and search left for earlier occurrence
                result = mid;
                right = mid - 1;
            } else {
                // arr[mid] <= x
                // Upper bound must be in right half
                left = mid + 1;
            }
        }
        
        return result;
    }
    
    /**
     * Upper bound using Java's built-in Arrays.binarySearch
     * Demonstrates how upper bound relates to binary search return value
     */
    public static int upperBoundBuiltIn(int[] arr, int n, int x) {
        // Java's binarySearch returns:
        // - index of key if found
        // - (-(insertion point) - 1) if not found
        // where insertion point = first index where key would be inserted
        
        // For upper bound, we want first index > x
        // We can use binary search for x, then find next greater
        
        int index = java.util.Arrays.binarySearch(arr, x);
        
        if (index >= 0) {
            // x found at index
            // Need to find first element > x (skip all duplicates of x)
            int i = index;
            while (i < n && arr[i] == x) {
                i++;
            }
            return i;
        } else {
            // x not found, binarySearch returns -(insertion point) - 1
            int insertionPoint = -index - 1;
            // Insertion point is first index where x would be inserted
            // This is actually the LOWER bound (first element >= x)
            // For upper bound, we need first element > x
            // Since x is not in array, lower bound = upper bound
            return insertionPoint;
        }
    }
    
    /**
     * Linear search implementation for comparison
     * Demonstrates why binary search is more efficient
     */
    public static int upperBoundLinear(int[] arr, int n, int x) {
        for (int i = 0; i < n; i++) {
            if (arr[i] > x) {
                return i;
            }
        }
        return n;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Testing Upper Bound Algorithm ===\n");
        
        // Test Case 1: Normal case, upper bound in middle
        int[] test1 = {1, 2, 3, 5, 7, 9};
        int x1 = 5;
        System.out.println("Test 1: arr = " + java.util.Arrays.toString(test1) + ", x = " + x1);
        System.out.println("Binary Search: " + upperBound(test1, test1.length, x1) + " (expected: 4)");
        System.out.println("Alternative: " + upperBoundAlt(test1, test1.length, x1) + " (expected: 4)");
        System.out.println("Built-in based: " + upperBoundBuiltIn(test1, test1.length, x1) + " (expected: 4)");
        System.out.println("Linear: " + upperBoundLinear(test1, test1.length, x1) + " (expected: 4)\n");
        
        // Test Case 2: Duplicate elements
        int[] test2 = {1, 2, 2, 2, 3, 4};
        int x2 = 2;
        System.out.println("Test 2: arr = " + java.util.Arrays.toString(test2) + ", x = " + x2);
        System.out.println("Binary Search: " + upperBound(test2, test2.length, x2) + " (expected: 4)");
        System.out.println("Alternative: " + upperBoundAlt(test2, test2.length, x2) + " (expected: 4)");
        System.out.println("Built-in based: " + upperBoundBuiltIn(test2, test2.length, x2) + " (expected: 4)");
        System.out.println("Linear: " + upperBoundLinear(test2, test2.length, x2) + " (expected: 4)\n");
        
        // Test Case 3: x larger than all elements
        int[] test3 = {1, 2, 3, 4, 5};
        int x3 = 10;
        System.out.println("Test 3: arr = " + java.util.Arrays.toString(test3) + ", x = " + x3);
        System.out.println("Binary Search: " + upperBound(test3, test3.length, x3) + " (expected: 5)");
        System.out.println("Alternative: " + upperBoundAlt(test3, test3.length, x3) + " (expected: 5)");
        System.out.println("Built-in based: " + upperBoundBuiltIn(test3, test3.length, x3) + " (expected: 5)");
        System.out.println("Linear: " + upperBoundLinear(test3, test3.length, x3) + " (expected: 5)\n");
        
        // Test Case 4: x smaller than all elements
        int[] test4 = {5, 6, 7, 8, 9};
        int x4 = 2;
        System.out.println("Test 4: arr = " + java.util.Arrays.toString(test4) + ", x = " + x4);
        System.out.println("Binary Search: " + upperBound(test4, test4.length, x4) + " (expected: 0)");
        System.out.println("Alternative: " + upperBoundAlt(test4, test4.length, x4) + " (expected: 0)");
        System.out.println("Built-in based: " + upperBoundBuiltIn(test4, test4.length, x4) + " (expected: 0)");
        System.out.println("Linear: " + upperBoundLinear(test4, test4.length, x4) + " (expected: 0)\n");
        
        // Test Case 5: x equals some elements
        int[] test5 = {1, 3, 5, 5, 5, 7, 9};
        int x5 = 5;
        System.out.println("Test 5: arr = " + java.util.Arrays.toString(test5) + ", x = " + x5);
        System.out.println("Binary Search: " + upperBound(test5, test5.length, x5) + " (expected: 5)");
        System.out.println("Alternative: " + upperBoundAlt(test5, test5.length, x5) + " (expected: 5)");
        System.out.println("Built-in based: " + upperBoundBuiltIn(test5, test5.length, x5) + " (expected: 5)");
        System.out.println("Linear: " + upperBoundLinear(test5, test5.length, x5) + " (expected: 5)\n");
        
        // Test Case 6: Empty array
        int[] test6 = {};
        int x6 = 5;
        System.out.println("Test 6: arr = []" + ", x = " + x6);
        System.out.println("Binary Search: " + upperBound(test6, test6.length, x6) + " (expected: 0)");
        System.out.println("Alternative: " + upperBoundAlt(test6, test6.length, x6) + " (expected: 0)");
        System.out.println("Built-in based: " + upperBoundBuiltIn(test6, test6.length, x6) + " (expected: 0)");
        System.out.println("Linear: " + upperBoundLinear(test6, test6.length, x6) + " (expected: 0)\n");
        
        // Test Case 7: Single element array
        int[] test7 = {5};
        int x7a = 5;
        int x7b = 3;
        int x7c = 7;
        System.out.println("Test 7: arr = " + java.util.Arrays.toString(test7));
        System.out.println("x = " + x7a + ": " + upperBound(test7, test7.length, x7a) + " (expected: 1)");
        System.out.println("x = " + x7b + ": " + upperBound(test7, test7.length, x7b) + " (expected: 0)");
        System.out.println("x = " + x7c + ": " + upperBound(test7, test7.length, x7c) + " (expected: 1)\n");
        
        // Performance comparison
        System.out.println("=== Performance Analysis ===");
        int[] largeArray = new int[1000000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i * 2; // Even numbers only
        }
        int target = 500000;
        
        long startTime = System.nanoTime();
        int result1 = upperBound(largeArray, largeArray.length, target);
        long binaryTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int result2 = upperBoundLinear(largeArray, largeArray.length, target);
        long linearTime = System.nanoTime() - startTime;
        
        System.out.println("Large array (1,000,000 elements), target = " + target);
        System.out.println("Binary Search Result: " + result1 + ", Time: " + binaryTime/1000 + " microseconds");
        System.out.println("Linear Search Result: " + result2 + ", Time: " + linearTime/1000 + " microseconds");
        System.out.println("Speedup: " + (linearTime/binaryTime) + "x faster");
    }
}