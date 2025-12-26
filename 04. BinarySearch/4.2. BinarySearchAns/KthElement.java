/**
 * Problem: K-th Element of Two Sorted Arrays
 * 
 * Problem Description:
 * Given two sorted arrays of sizes n1 and n2, find the k-th smallest element
 * in the combined sorted array formed by merging both arrays.
 * 
 * Example:
 * arr1 = [2, 3, 6, 7, 9]
 * arr2 = [1, 4, 8, 10]
 * k = 5
 * Combined sorted array: [1, 2, 3, 4, 6, 7, 8, 9, 10]
 * 5th element = 6
 * 
 * Approach: Binary Search on Partition Point
 * 
 * Key Insight:
 * Instead of actually merging the arrays (O(n1+n2) time and space),
 * we use binary search to find the correct partition point in both arrays
 * such that exactly k elements are on the left side of the partition.
 * 
 * Time Complexity: O(log(min(n1, n2)))
 * Space Complexity: O(1)
 */

import java.util.Arrays;

public class KthElement {

    /**
     * Finds the k-th smallest element in two sorted arrays
     * 
     * @param a - First sorted array
     * @param n1 - Size of first array (a.length)
     * @param b - Second sorted array
     * @param n2 - Size of second array (b.length)
     * @param k - k-th element to find (1-indexed)
     * @return k-th smallest element
     * 
     * Algorithm Steps:
     * 1. Ensure a is the smaller array (swap if necessary)
     * 2. Binary search on number of elements to take from array a (mid1)
     * 3. Calculate corresponding elements from array b (mid2 = k - mid1)
     * 4. Check partition validity using boundary elements
     * 5. Adjust search range based on comparison
     */
    public static int kthElement(int[] a, int n1, int[] b, int n2, int k) {
        // Edge cases
        if (a == null || b == null || n1 < 0 || n2 < 0 || k < 1 || k > n1 + n2) {
            throw new IllegalArgumentException("Invalid input parameters");
        }
        
        // Ensure array a is smaller for binary search efficiency
        if (n1 > n2) {
            return kthElement(b, n2, a, n1, k);
        }
        
        // Binary search boundaries
        // low: minimum elements we must take from array a
        // high: maximum elements we can take from array a
        int low = Math.max(0, k - n2);  // Need at least (k - n2) from a if k > n2
        int high = Math.min(n1, k);     // Cannot take more than min(n1, k) from a
        
        System.out.println("Searching for k = " + k + "-th element");
        System.out.println("Array sizes: a[" + n1 + "], b[" + n2 + "]");
        System.out.println("Binary search range for elements from a: [" + low + ", " + high + "]");
        
        while (low <= high) {
            // mid1 = number of elements to take from array a
            int mid1 = low + (high - low) / 2;
            // mid2 = number of elements to take from array b
            int mid2 = k - mid1;
            
            System.out.println("\nIteration:");
            System.out.println("  Taking " + mid1 + " elements from a, " + mid2 + " elements from b");
            
            // Boundary elements for validation
            // l1: last element in left partition of a (or -∞ if no elements)
            // l2: last element in left partition of b (or -∞ if no elements)
            // r1: first element in right partition of a (or +∞ if no elements)
            // r2: first element in right partition of b (or +∞ if no elements)
            
            int l1 = (mid1 > 0) ? a[mid1 - 1] : Integer.MIN_VALUE;
            int l2 = (mid2 > 0) ? b[mid2 - 1] : Integer.MIN_VALUE;
            int r1 = (mid1 < n1) ? a[mid1] : Integer.MAX_VALUE;
            int r2 = (mid2 < n2) ? b[mid2] : Integer.MAX_VALUE;
            
            System.out.println("  Boundary values:");
            System.out.println("    l1 (last left in a) = " + formatValue(l1));
            System.out.println("    r1 (first right in a) = " + formatValue(r1));
            System.out.println("    l2 (last left in b) = " + formatValue(l2));
            System.out.println("    r2 (first right in b) = " + formatValue(r2));
            
            // Check if partition is valid
            // Valid partition means all elements in left partition ≤ all elements in right partition
            if (l1 <= r2 && l2 <= r1) {
                System.out.println("  ✓ Valid partition found!");
                // k-th element is the maximum of last elements in left partitions
                int result = Math.max(l1, l2);
                System.out.println("  Result: max(" + formatValue(l1) + ", " + formatValue(l2) + ") = " + result);
                return result;
            } 
            // If l1 > r2, we took too many elements from a, reduce mid1
            else if (l1 > r2) {
                System.out.println("  ✗ l1 > r2, reducing elements from a");
                high = mid1 - 1;
            }
            // If l2 > r1, we took too few elements from a, increase mid1
            else {
                System.out.println("  ✗ l2 > r1, increasing elements from a");
                low = mid1 + 1;
            }
        }
        
        // Should never reach here for valid inputs
        return -1;
    }
    
    /**
     * Helper method to format boundary values for display
     */
    private static String formatValue(int val) {
        if (val == Integer.MIN_VALUE) return "-∞";
        if (val == Integer.MAX_VALUE) return "+∞";
        return String.valueOf(val);
    }
    
    /**
     * Alternative implementation with cleaner code
     */
    public static int findKthElement(int[] nums1, int[] nums2, int k) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            return findKthElement(nums2, nums1, k);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        
        int left = 0;
        int right = Math.min(m, k);
        
        while (left <= right) {
            // Partition point in nums1
            int partition1 = left + (right - left) / 2;
            // Partition point in nums2
            int partition2 = k - partition1;
            
            // Handle edge cases for boundaries
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];
            
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];
            
            // Check if we found the correct partition
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // The k-th element is the maximum of the left partition
                return Math.max(maxLeft1, maxLeft2);
            } 
            // If we're too far right in nums1, move left
            else if (maxLeft1 > minRight2) {
                right = partition1 - 1;
            } 
            // If we're too far left in nums1, move right
            else {
                left = partition1 + 1;
            }
        }
        
        throw new IllegalArgumentException("Invalid input");
    }
    
    /**
     * Naive approach for comparison (merge and find)
     * Time: O(n+m), Space: O(n+m)
     */
    public static int kthElementNaive(int[] a, int[] b, int k) {
        int[] merged = new int[a.length + b.length];
        int i = 0, j = 0, idx = 0;
        
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                merged[idx++] = a[i++];
            } else {
                merged[idx++] = b[j++];
            }
        }
        
        while (i < a.length) merged[idx++] = a[i++];
        while (j < b.length) merged[idx++] = b[j++];
        
        return merged[k - 1]; // k is 1-indexed
    }
    
    public static void main(String[] args) {
        System.out.println("=== K-th Element of Two Sorted Arrays ===\n");
        
        // Test Case 1: Standard case
        System.out.println("Test Case 1:");
        int[] a1 = {2, 3, 6, 7, 9};
        int[] b1 = {1, 4, 8, 10};
        int k1 = 5;
        
        System.out.println("Array A: " + Arrays.toString(a1));
        System.out.println("Array B: " + Arrays.toString(b1));
        System.out.println("Finding " + ordinal(k1) + " element");
        
        int result1 = kthElement(a1, a1.length, b1, b1.length, k1);
        int naive1 = kthElementNaive(a1, b1, k1);
        
        System.out.println("\nBinary Search Result: " + result1);
        System.out.println("Naive Merge Result: " + naive1);
        System.out.println("Match: " + (result1 == naive1 ? "✓" : "✗"));
        
        // Show combined array for verification
        System.out.print("Combined sorted array: ");
        int[] merged1 = mergeArrays(a1, b1);
        System.out.println(Arrays.toString(merged1));
        System.out.println(ordinal(k1) + " element = " + merged1[k1-1] + "\n");
        
        // Test Case 2: k in first array
        System.out.println("Test Case 2: k in first array");
        int[] a2 = {1, 3, 5};
        int[] b2 = {2, 4, 6, 8};
        int k2 = 2;
        
        System.out.println("Array A: " + Arrays.toString(a2));
        System.out.println("Array B: " + Arrays.toString(b2));
        System.out.println("Finding " + ordinal(k2) + " element");
        
        int result2 = kthElement(a2, a2.length, b2, b2.length, k2);
        int naive2 = kthElementNaive(a2, b2, k2);
        
        System.out.println("\nBinary Search Result: " + result2);
        System.out.println("Naive Merge Result: " + naive2);
        System.out.println("Match: " + (result2 == naive2 ? "✓" : "✗") + "\n");
        
        // Test Case 3: k in second array
        System.out.println("Test Case 3: k in second array");
        int[] a3 = {1, 3, 5};
        int[] b3 = {2, 4, 6, 8, 10};
        int k3 = 7;
        
        System.out.println("Array A: " + Arrays.toString(a3));
        System.out.println("Array B: " + Arrays.toString(b3));
        System.out.println("Finding " + ordinal(k3) + " element");
        
        int result3 = kthElement(a3, a3.length, b3, b3.length, k3);
        int naive3 = kthElementNaive(a3, b3, k3);
        
        System.out.println("\nBinary Search Result: " + result3);
        System.out.println("Naive Merge Result: " + naive3);
        System.out.println("Match: " + (result3 == naive3 ? "✓" : "✗") + "\n");
        
        // Test Case 4: Edge case - all elements from first array
        System.out.println("Test Case 4: k smaller than size of first array");
        int[] a4 = {1, 2, 3, 4, 5};
        int[] b4 = {6, 7, 8, 9, 10};
        int k4 = 3;
        
        System.out.println("Array A: " + Arrays.toString(a4));
        System.out.println("Array B: " + Arrays.toString(b4));
        System.out.println("Finding " + ordinal(k4) + " element");
        
        int result4 = kthElement(a4, a4.length, b4, b4.length, k4);
        int naive4 = kthElementNaive(a4, b4, k4);
        
        System.out.println("\nBinary Search Result: " + result4);
        System.out.println("Naive Merge Result: " + naive4);
        System.out.println("Match: " + (result4 == naive4 ? "✓" : "✗") + "\n");
        
        // Test Case 5: Edge case - k equals total size
        System.out.println("Test Case 5: k equals total size");
        int[] a5 = {1, 3, 5, 7};
        int[] b5 = {2, 4, 6, 8};
        int k5 = 8;
        
        System.out.println("Array A: " + Arrays.toString(a5));
        System.out.println("Array B: " + Arrays.toString(b5));
        System.out.println("Finding " + ordinal(k5) + " element (largest)");
        
        int result5 = kthElement(a5, a5.length, b5, b5.length, k5);
        int naive5 = kthElementNaive(a5, b5, k5);
        
        System.out.println("\nBinary Search Result: " + result5);
        System.out.println("Naive Merge Result: " + naive5);
        System.out.println("Match: " + (result5 == naive5 ? "✓" : "✗") + "\n");
        
        // Test Case 6: Empty array
        System.out.println("Test Case 6: One empty array");
        int[] a6 = {};
        int[] b6 = {1, 2, 3, 4, 5};
        int k6 = 3;
        
        System.out.println("Array A: " + Arrays.toString(a6));
        System.out.println("Array B: " + Arrays.toString(b6));
        System.out.println("Finding " + ordinal(k6) + " element");
        
        int result6 = kthElement(a6, a6.length, b6, b6.length, k6);
        int naive6 = kthElementNaive(a6, b6, k6);
        
        System.out.println("\nBinary Search Result: " + result6);
        System.out.println("Naive Merge Result: " + naive6);
        System.out.println("Match: " + (result6 == naive6 ? "✓" : "✗") + "\n");
        
        // Performance comparison
        System.out.println("=== Performance Comparison ===");
        int[] large1 = generateSortedArray(10000);
        int[] large2 = generateSortedArray(15000);
        int kLarge = 12500;
        
        long startTime = System.currentTimeMillis();
        int resultLarge = kthElement(large1, large1.length, large2, large2.length, kLarge);
        long time1 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        int naiveLarge = kthElementNaive(large1, large2, kLarge);
        long time2 = System.currentTimeMillis() - startTime;
        
        System.out.println("Array sizes: " + large1.length + " and " + large2.length);
        System.out.println("Finding " + ordinal(kLarge) + " element");
        System.out.println("Binary Search: result = " + resultLarge + ", time = " + time1 + "ms");
        System.out.println("Naive Merge: result = " + naiveLarge + ", time = " + time2 + "ms");
        System.out.println("Speedup: " + (time2 - time1) + "ms faster");
        System.out.println("Results match: " + (resultLarge == naiveLarge ? "✓" : "✗"));
    }
    
    /**
     * Helper method to merge two arrays (for verification)
     */
    private static int[] mergeArrays(int[] a, int[] b) {
        int[] merged = new int[a.length + b.length];
        int i = 0, j = 0, idx = 0;
        
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                merged[idx++] = a[i++];
            } else {
                merged[idx++] = b[j++];
            }
        }
        
        while (i < a.length) merged[idx++] = a[i++];
        while (j < b.length) merged[idx++] = b[j++];
        
        return merged;
    }
    
    /**
     * Helper method to generate ordinal string (1st, 2nd, 3rd, etc.)
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
     * Helper method to generate sorted array for testing
     */
    private static int[] generateSortedArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = (i * 2) + (int)(Math.random() * 2); // Mostly sorted with some randomness
        }
        Arrays.sort(arr);
        return arr;
    }
}

/**
 * ALGORITHM EXPLANATION:
 * 
 * Visual Representation:
 * 
 * We want to partition both arrays such that:
 * - Left partition has exactly k elements
 * - All elements in left partition ≤ all elements in right partition
 * 
 * Example: a = [2, 3, 6, 7, 9], b = [1, 4, 8, 10], k = 5
 * 
 * We want to find partition points (mid1, mid2) such that:
 * - mid1 elements from a + mid2 elements from b = k
 * - max(left_a) ≤ min(right_b) AND max(left_b) ≤ min(right_a)
 * 
 * The k-th element will be max(max(left_a), max(left_b))
 * 
 * Binary Search Process:
 * 
 * 1. Start with mid1 = (low + high) / 2 elements from array a
 * 2. Calculate mid2 = k - mid1 elements from array b
 * 3. Check partition validity:
 *    - l1 = last element in left partition of a (a[mid1-1])
 *    - r1 = first element in right partition of a (a[mid1])
 *    - l2 = last element in left partition of b (b[mid2-1])
 *    - r2 = first element in right partition of b (b[mid2])
 *    
 *    Valid if: l1 ≤ r2 AND l2 ≤ r1
 *    
 * 4. Adjust search range:
 *    - If l1 > r2: too many from a → reduce mid1
 *    - If l2 > r1: too few from a → increase mid1
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * Let m = size of smaller array, n = size of larger array
 * 
 * 1. Binary search on smaller array: O(log m)
 * 2. Each iteration does O(1) operations
 * 3. Total: O(log(min(m, n)))
 * 
 * Space Complexity: O(1) extra space
 * 
 * Compare with naive merge: O(m+n) time, O(m+n) space
 */

/**
 * MATHEMATICAL PROOF OF CORRECTNESS:
 * 
 * 1. Partition Property:
 *    For a valid partition, the k-th element is max(l1, l2)
 *    because there are exactly k elements in the left partition
 *    and they are all ≤ elements in the right partition
 * 
 * 2. Search Space:
 *    - low = max(0, k-n): need at least k-n from a if k > n
 *    - high = min(m, k): cannot take more than min(m, k) from a
 * 
 * 3. Termination:
 *    Binary search reduces search space by half each iteration
 *    Guaranteed to find valid partition because solution exists
 */

/**
 * EDGE CASES HANDLED:
 * 
 * 1. Empty arrays: Use Integer.MIN_VALUE/MAX_VALUE for boundaries
 * 2. k = 1: First element of combined array
 * 3. k = m+n: Last element of combined array
 * 4. All elements from one array: Partition adjusts accordingly
 * 5. Duplicate elements: Handled by ≤ comparison
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Median of Two Sorted Arrays: Special case where k = (m+n)/2
 * 2. Find K Pairs with Smallest Sums: Similar two-pointer approach
 * 3. K-th Smallest Element in a Sorted Matrix: Extension to 2D
 * 4. K-th Smallest Prime Fraction: Similar partitioning concept
 */