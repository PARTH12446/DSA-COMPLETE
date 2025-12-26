// Problem: LeetCode <ID>. <Title>
/**
 * Problem: Ninja and the Sorted Check (Coding Ninjas)
 * Link: https://www.codingninjas.com/studio/problems/ninja-and-the-sorted-check_6581957
 * 
 * GOAL: Check if array is sorted in non-decreasing order (each element ≤ next element)
 * 
 * KEY POINTS TO REMEMBER:
 * 1. Non-decreasing means: a[i] ≤ a[i+1] for all valid i
 * 2. Early return on first violation → O(1) best case
 * 3. Only need to check adjacent elements (not all pairs)
 * 4. Loop runs from 0 to n-2 (to avoid ArrayIndexOutOfBounds)
 */

public class CheckSortedArray {
    
    /**
     * Check if array is sorted in non-decreasing order
     * 
     * @param n - size of array
     * @param a - input array
     * @return 1 if sorted, 0 if not sorted
     * 
     * TIME COMPLEXITY: O(n) - single pass through array
     * SPACE COMPLEXITY: O(1) - no extra space used
     * 
     * EDGE CASES HANDLED:
     * - Empty array → returns 1 (considered sorted)
     * - Single element → returns 1 (trivially sorted)
     * - All equal elements → returns 1 (non-decreasing satisfied)
     */
    public static int isSorted(int n, int[] a) {
        // If array has 0 or 1 element, it's trivially sorted
        // This check is optional but good for clarity
        if (n <= 1) {
            return 1;
        }
        
        // Check each adjacent pair
        // Loop only till n-2 because we compare i and i+1
        for (int i = 0; i < n - 1; i++) {
            // If current element > next element → violation found
            // Note: Using > not >= because equal is allowed (non-decreasing)
            if (a[i] > a[i + 1]) {
                return 0;  // Early exit - array is NOT sorted
            }
        }
        
        // If loop completes without finding violations → array IS sorted
        return 1;
    }
    
    /**
     * Alternative implementation without early size check
     * (As required by some platforms)
     * 
     * COMMON MISTAKES TO AVOID:
     * 1. DON'T use a[i] < a[i+1] → checks for strictly increasing
     * 2. DON'T loop to i < n → causes index out of bounds
     * 3. DON'T forget to handle empty/single element arrays
     */
    public static int isSortedAlternative(int n, int[] a) {
        // Single loop approach - most common solution
        for (int i = 0; i < n - 1; i++) {
            if (a[i] > a[i + 1]) {
                return 0;
            }
        }
        return 1;
    }
    
    /**
     * Test cases to verify implementation
     * 
     * TEST CASES COVER:
     * 1. Sorted array with duplicates
     * 2. Unsorted array
     * 3. Single element
     * 4. Empty array
     * 5. Strictly increasing
     * 6. Descending order (should fail)
     */
    public static void main(String[] args) {
        // Test Case 1: Sorted with duplicates
        int[] test1 = {1, 2, 2, 4, 5};
        System.out.println("Test 1 [1,2,2,4,5]: " + isSorted(test1.length, test1));
        // Expected: 1 ✓
        
        // Test Case 2: Not sorted
        int[] test2 = {1, 3, 2, 4};
        System.out.println("Test 2 [1,3,2,4]: " + isSorted(test2.length, test2));
        // Expected: 0 ✓
        
        // Test Case 3: Single element
        int[] test3 = {5};
        System.out.println("Test 3 [5]: " + isSorted(test3.length, test3));
        // Expected: 1 ✓
        
        // Test Case 4: Empty array
        int[] test4 = {};
        System.out.println("Test 4 []: " + isSorted(test4.length, test4));
        // Expected: 1 ✓
        
        // Test Case 5: Strictly increasing
        int[] test5 = {1, 2, 3, 4, 5};
        System.out.println("Test 5 [1,2,3,4,5]: " + isSorted(test5.length, test5));
        // Expected: 1 ✓
        
        // Test Case 6: Descending (should fail)
        int[] test6 = {5, 4, 3, 2, 1};
        System.out.println("Test 6 [5,4,3,2,1]: " + isSorted(test6.length, test6));
        // Expected: 0 ✓
        
        // Test Case 7: All equal elements
        int[] test7 = {2, 2, 2, 2};
        System.out.println("Test 7 [2,2,2,2]: " + isSorted(test7.length, test7));
        // Expected: 1 ✓
    }
}

/**
 * ALTERNATIVE APPROACHES (for reference):
 * 
 * 1. Using Java Streams (less efficient but concise):
 *    IntStream.range(0, n-1).allMatch(i -> a[i] <= a[i+1]) ? 1 : 0;
 * 
 * 2. Recursive approach:
 *    private static boolean isSortedRecursive(int[] a, int index) {
 *        if (index == a.length - 1) return true;
 *        return a[index] <= a[index + 1] && isSortedRecursive(a, index + 1);
 *    }
 * 
 * 3. Sorting and comparing (inefficient):
 *    int[] sorted = a.clone();
 *    Arrays.sort(sorted);
 *    return Arrays.equals(a, sorted) ? 1 : 0;
 */

/**
 * TIME & SPACE ANALYSIS:
 * 
 * Best Case: O(1) - Array not sorted at first position
 * Worst Case: O(n) - Array is sorted or violation at last position
 * Average Case: O(n) - Linear scan
 * 
 * Space: O(1) - Only loop variable, no extra data structures
 */

/**
 * WHY THIS SOLUTION IS OPTIMAL:
 * 1. Minimum comparisons needed (n-1 comparisons for n elements)
 * 2. Early termination on first violation
 * 3. No extra memory allocation
 * 4. Simple and easy to understand
 */
