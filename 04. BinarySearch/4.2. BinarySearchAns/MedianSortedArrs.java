/**
 * Problem: Median of Two Sorted Arrays (LeetCode Hard / Coding Ninjas)
 * 
 * Problem Description:
 * Given two sorted arrays nums1 and nums2 of size m and n respectively,
 * return the median of the two sorted arrays.
 * 
 * The overall run time complexity should be O(log(min(m, n))).
 * 
 * Example 1:
 * Input: nums1 = [1,3], nums2 = [2]
 * Output: 2.00000
 * Explanation: merged array = [1,2,3] and median is 2.
 * 
 * Example 2:
 * Input: nums1 = [1,2], nums2 = [3,4]
 * Output: 2.50000
 * Explanation: merged array = [1,2,3,4] and median = (2+3)/2 = 2.5.
 * 
 * Approach: Binary Search on Partition Point
 * 
 * Key Insight:
 * Instead of merging arrays (O(m+n) time), we can use binary search
 * to find the correct partition point in the smaller array such that
 * elements on left side of partition = elements on right side (or ±1).
 * 
 * Time Complexity: O(log(min(m, n)))
 * Space Complexity: O(1)
 */

import java.util.Arrays;

public class MedianSortedArrs {

    /**
     * Finds median of two sorted arrays
     * 
     * @param a - First sorted array
     * @param b - Second sorted array
     * @return Median of combined arrays
     * 
     * Algorithm Steps:
     * 1. Ensure a is the smaller array (swap if necessary)
     * 2. Binary search on number of elements to take from array a
     * 3. Partition both arrays such that:
     *    - Left partition has (m+n+1)/2 elements
     *    - All elements in left ≤ all elements in right
     * 4. Calculate median based on partition boundaries
     */
    public static double median(int[] a, int[] b) {
        int n1 = a.length;
        int n2 = b.length;
        
        // Ensure a is the smaller array for binary search efficiency
        if (n1 > n2) {
            return median(b, a);
        }
        
        int total = n1 + n2;
        int leftPartitionSize = (total + 1) / 2; // Ceiling of (m+n)/2
        
        int low = 0;
        int high = n1; // We can take 0 to n1 elements from a
        
        System.out.println("Finding median of two sorted arrays");
        System.out.println("Array A (" + n1 + "): " + Arrays.toString(a));
        System.out.println("Array B (" + n2 + "): " + Arrays.toString(b));
        System.out.println("Total elements: " + total);
        System.out.println("Left partition should have: " + leftPartitionSize + " elements");
        System.out.println("Binary search range for elements from A: [0, " + n1 + "]");
        System.out.println();
        
        while (low <= high) {
            // mid1 = number of elements to take from array a
            int mid1 = low + (high - low) / 2;
            // mid2 = number of elements to take from array b
            int mid2 = leftPartitionSize - mid1;
            
            System.out.println("Iteration:");
            System.out.println("  Taking " + mid1 + " elements from A, " + mid2 + " elements from B");
            
            // Boundary elements
            // l1: last element in left partition of A (or -∞ if none)
            // l2: last element in left partition of B (or -∞ if none)
            // r1: first element in right partition of A (or +∞ if none)
            // r2: first element in right partition of B (or +∞ if none)
            
            double l1 = (mid1 > 0) ? a[mid1 - 1] : Double.NEGATIVE_INFINITY;
            double l2 = (mid2 > 0) ? b[mid2 - 1] : Double.NEGATIVE_INFINITY;
            double r1 = (mid1 < n1) ? a[mid1] : Double.POSITIVE_INFINITY;
            double r2 = (mid2 < n2) ? b[mid2] : Double.POSITIVE_INFINITY;
            
            System.out.println("  Boundaries:");
            System.out.println("    l1 (last left in A) = " + formatValue(l1));
            System.out.println("    r1 (first right in A) = " + formatValue(r1));
            System.out.println("    l2 (last left in B) = " + formatValue(l2));
            System.out.println("    r2 (first right in B) = " + formatValue(r2));
            
            // Check if partition is valid
            // Valid partition means: all left elements ≤ all right elements
            if (l1 <= r2 && l2 <= r1) {
                System.out.println("  ✓ Valid partition found!");
                
                // Calculate median based on total number of elements
                if (total % 2 == 1) {
                    // Odd total: median is the middle element = max(l1, l2)
                    double result = Math.max(l1, l2);
                    System.out.println("  Total is odd, median = max(" + formatValue(l1) + 
                                      ", " + formatValue(l2) + ") = " + result);
                    return result;
                } else {
                    // Even total: median is average of two middle elements
                    double leftMax = Math.max(l1, l2);
                    double rightMin = Math.min(r1, r2);
                    double result = (leftMax + rightMin) / 2.0;
                    System.out.println("  Total is even, median = (max(" + formatValue(l1) + 
                                      ", " + formatValue(l2) + ") + min(" + formatValue(r1) + 
                                      ", " + formatValue(r2) + ")) / 2 = " + result);
                    return result;
                }
            } 
            // If l1 > r2, we took too many elements from A
            else if (l1 > r2) {
                System.out.println("  ✗ l1 > r2: Reduce elements from A");
                high = mid1 - 1;
            }
            // If l2 > r1, we took too few elements from A
            else {
                System.out.println("  ✗ l2 > r1: Increase elements from A");
                low = mid1 + 1;
            }
            
            System.out.println("  New search range for A elements: [" + low + ", " + high + "]");
            System.out.println();
        }
        
        // Should never reach here for valid inputs
        throw new IllegalArgumentException("Input arrays are not sorted properly");
    }
    
    /**
     * Helper to format boundary values for display
     */
    private static String formatValue(double val) {
        if (val == Double.NEGATIVE_INFINITY) return "-∞";
        if (val == Double.POSITIVE_INFINITY) return "+∞";
        return String.valueOf((int)val); // Assuming integer arrays
    }
    
    /**
     * Alternative implementation with integer boundaries
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        // Ensure nums1 is the smaller array
        if (nums1.length > nums2.length) {
            return findMedianSortedArrays(nums2, nums1);
        }
        
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;
        int leftPartition = (total + 1) / 2;
        
        int low = 0, high = m;
        
        while (low <= high) {
            int partition1 = low + (high - low) / 2;
            int partition2 = leftPartition - partition1;
            
            // Handle edge cases for boundaries
            int maxLeft1 = (partition1 == 0) ? Integer.MIN_VALUE : nums1[partition1 - 1];
            int minRight1 = (partition1 == m) ? Integer.MAX_VALUE : nums1[partition1];
            
            int maxLeft2 = (partition2 == 0) ? Integer.MIN_VALUE : nums2[partition2 - 1];
            int minRight2 = (partition2 == n) ? Integer.MAX_VALUE : nums2[partition2];
            
            // Check if we found the correct partition
            if (maxLeft1 <= minRight2 && maxLeft2 <= minRight1) {
                // Found the correct partition
                if (total % 2 == 1) {
                    // Odd total length
                    return Math.max(maxLeft1, maxLeft2);
                } else {
                    // Even total length
                    return (Math.max(maxLeft1, maxLeft2) + Math.min(minRight1, minRight2)) / 2.0;
                }
            } 
            // If we're too far right in nums1, move left
            else if (maxLeft1 > minRight2) {
                high = partition1 - 1;
            } 
            // If we're too far left in nums1, move right
            else {
                low = partition1 + 1;
            }
        }
        
        throw new IllegalArgumentException("Input arrays are not sorted");
    }
    
    /**
     * Naive approach for verification (merge and find median)
     * Time: O(m+n), Space: O(m+n)
     */
    public static double medianNaive(int[] a, int[] b) {
        int[] merged = new int[a.length + b.length];
        int i = 0, j = 0, k = 0;
        
        while (i < a.length && j < b.length) {
            if (a[i] <= b[j]) {
                merged[k++] = a[i++];
            } else {
                merged[k++] = b[j++];
            }
        }
        
        while (i < a.length) merged[k++] = a[i++];
        while (j < b.length) merged[k++] = b[j++];
        
        int n = merged.length;
        if (n % 2 == 1) {
            return merged[n / 2];
        } else {
            return (merged[n / 2 - 1] + merged[n / 2]) / 2.0;
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Median of Two Sorted Arrays ===\n");
        
        // Test Case 1: Standard example
        System.out.println("Test Case 1:");
        int[] a1 = {1, 3};
        int[] b1 = {2};
        System.out.println("Array A: " + Arrays.toString(a1));
        System.out.println("Array B: " + Arrays.toString(b1));
        
        double result1 = median(a1, b1);
        double naive1 = medianNaive(a1, b1);
        
        System.out.println("\nBinary Search Median: " + result1);
        System.out.println("Naive Merge Median: " + naive1);
        System.out.println("Match: " + (Math.abs(result1 - naive1) < 0.00001 ? "✓" : "✗"));
        System.out.println("Expected: 2.0\n");
        
        // Test Case 2: Even total length
        System.out.println("Test Case 2:");
        int[] a2 = {1, 2};
        int[] b2 = {3, 4};
        System.out.println("Array A: " + Arrays.toString(a2));
        System.out.println("Array B: " + Arrays.toString(b2));
        
        double result2 = median(a2, b2);
        double naive2 = medianNaive(a2, b2);
        
        System.out.println("\nBinary Search Median: " + result2);
        System.out.println("Naive Merge Median: " + naive2);
        System.out.println("Match: " + (Math.abs(result2 - naive2) < 0.00001 ? "✓" : "✗"));
        System.out.println("Expected: 2.5\n");
        
        // Test Case 3: First array empty
        System.out.println("Test Case 3:");
        int[] a3 = {};
        int[] b3 = {1, 2, 3, 4, 5};
        System.out.println("Array A: " + Arrays.toString(a3));
        System.out.println("Array B: " + Arrays.toString(b3));
        
        double result3 = median(a3, b3);
        double naive3 = medianNaive(a3, b3);
        
        System.out.println("\nBinary Search Median: " + result3);
        System.out.println("Naive Merge Median: " + naive3);
        System.out.println("Match: " + (Math.abs(result3 - naive3) < 0.00001 ? "✓" : "✗"));
        System.out.println("Expected: 3.0\n");
        
        // Test Case 4: Second array empty
        System.out.println("Test Case 4:");
        int[] a4 = {1, 2, 3, 4, 5};
        int[] b4 = {};
        System.out.println("Array A: " + Arrays.toString(a4));
        System.out.println("Array B: " + Arrays.toString(b4));
        
        double result4 = median(a4, b4);
        double naive4 = medianNaive(a4, b4);
        
        System.out.println("\nBinary Search Median: " + result4);
        System.out.println("Naive Merge Median: " + naive4);
        System.out.println("Match: " + (Math.abs(result4 - naive4) < 0.00001 ? "✓" : "✗"));
        System.out.println("Expected: 3.0\n");
        
        // Test Case 5: Interleaved arrays
        System.out.println("Test Case 5:");
        int[] a5 = {1, 3, 5, 7, 9};
        int[] b5 = {2, 4, 6, 8, 10};
        System.out.println("Array A: " + Arrays.toString(a5));
        System.out.println("Array B: " + Arrays.toString(b5));
        
        double result5 = median(a5, b5);
        double naive5 = medianNaive(a5, b5);
        
        System.out.println("\nBinary Search Median: " + result5);
        System.out.println("Naive Merge Median: " + naive5);
        System.out.println("Match: " + (Math.abs(result5 - naive5) < 0.00001 ? "✓" : "✗"));
        System.out.println("Expected: 5.5\n");
        
        // Test Case 6: Arrays with different sizes
        System.out.println("Test Case 6:");
        int[] a6 = {1, 2, 3, 4, 5, 6};
        int[] b6 = {7, 8, 9, 10};
        System.out.println("Array A: " + Arrays.toString(a6));
        System.out.println("Array B: " + Arrays.toString(b6));
        
        double result6 = median(a6, b6);
        double naive6 = medianNaive(a6, b6);
        
        System.out.println("\nBinary Search Median: " + result6);
        System.out.println("Naive Merge Median: " + naive6);
        System.out.println("Match: " + (Math.abs(result6 - naive6) < 0.00001 ? "✓" : "✗"));
        System.out.println("Expected: 5.5\n");
        
        // Test Case 7: Arrays with duplicates
        System.out.println("Test Case 7:");
        int[] a7 = {1, 1, 3, 3};
        int[] b7 = {1, 1, 3, 3};
        System.out.println("Array A: " + Arrays.toString(a7));
        System.out.println("Array B: " + Arrays.toString(b7));
        
        double result7 = median(a7, b7);
        double naive7 = medianNaive(a7, b7);
        
        System.out.println("\nBinary Search Median: " + result7);
        System.out.println("Naive Merge Median: " + naive7);
        System.out.println("Match: " + (Math.abs(result7 - naive7) < 0.00001 ? "✓" : "✗"));
        System.out.println("Expected: 2.0\n");
        
        // Test Case 8: Large arrays for performance
        System.out.println("Test Case 8: Performance test");
        int[] a8 = new int[1000];
        int[] b8 = new int[2000];
        
        for (int i = 0; i < a8.length; i++) {
            a8[i] = i * 2; // Even numbers: 0, 2, 4, ...
        }
        for (int i = 0; i < b8.length; i++) {
            b8[i] = i * 2 + 1; // Odd numbers: 1, 3, 5, ...
        }
        
        long startTime = System.currentTimeMillis();
        double result8 = median(a8, b8);
        long binaryTime = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        double naive8 = medianNaive(a8, b8);
        long naiveTime = System.currentTimeMillis() - startTime;
        
        System.out.println("Array sizes: " + a8.length + " and " + b8.length);
        System.out.println("Binary Search Median: " + result8 + ", Time: " + binaryTime + "ms");
        System.out.println("Naive Merge Median: " + naive8 + ", Time: " + naiveTime + "ms");
        System.out.println("Match: " + (Math.abs(result8 - naive8) < 0.00001 ? "✓" : "✗"));
        System.out.println("Speedup: " + (naiveTime - binaryTime) + "ms faster");
    }
}

/**
 * DETAILED ALGORITHM EXPLANATION:
 * 
 * Goal: Find median without merging arrays.
 * 
 * Key Insight:
 * For two sorted arrays A and B with sizes m and n (m ≤ n):
 * We want to partition both arrays such that:
 * 1. Left partition has (m+n+1)/2 elements (half or half+1)
 * 2. All elements in left partition ≤ all elements in right partition
 * 
 * If we take 'i' elements from A and 'j' elements from B for left partition,
 * then: i + j = (m+n+1)/2
 * 
 * The partition is valid if:
 *   max(A[i-1], B[j-1]) ≤ min(A[i], B[j])
 * 
 * The median is:
 * - If (m+n) is odd: max(A[i-1], B[j-1])
 * - If (m+n) is even: (max(A[i-1], B[j-1]) + min(A[i], B[j])) / 2
 * 
 * Binary Search Setup:
 * - Search on i (elements from A in left partition)
 * - Range: 0 to m (can take 0 to all elements from A)
 * - j = (m+n+1)/2 - i
 * 
 * Check Conditions:
 * 1. If A[i-1] > B[j]: i is too large → decrease i (move left)
 * 2. If B[j-1] > A[i]: i is too small → increase i (move right)
 * 3. Otherwise: valid partition found!
 */

/**
 * PROOF OF CORRECTNESS:
 * 
 * 1. Partition Size:
 *    leftPartition = (m+n+1)/2 ensures left has at least as many elements as right
 *    (for odd total, left has one more element)
 * 
 * 2. Boundary Conditions:
 *    Using -∞ for l1/l2 when no elements taken
 *    Using +∞ for r1/r2 when all elements taken
 *    Handles edge cases (empty partitions) correctly
 * 
 * 3. Median Calculation:
 *    - For odd total: median is middle element = max of left partition
 *    - For even total: median is average of two middle elements
 *      = (max of left + min of right) / 2
 * 
 * 4. Search Space Reduction:
 *    Each iteration eliminates half of search space
 *    Guaranteed to find valid partition because it exists
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * Let m = size of smaller array, n = size of larger array
 * 
 * 1. Binary search on smaller array: O(log m)
 * 2. Each iteration: O(1) operations
 * 3. Total: O(log(min(m, n)))
 * 
 * Space Complexity: O(1) extra space
 * 
 * Compare with naive merge: O(m+n) time, O(m+n) space
 */

/**
 * VISUAL EXAMPLE:
 * 
 * Example: A = [1, 3, 8, 9, 15], B = [7, 11, 18, 19, 21, 25]
 * m = 5, n = 6, total = 11 (odd)
 * leftPartitionSize = (11+1)/2 = 6
 * 
 * We need to partition such that left has 6 elements.
 * 
 * Try i = 3 (take 3 from A), then j = 6-3 = 3 (take 3 from B)
 * 
 * Left partition: [1,3,8] from A, [7,11,18] from B
 * Right partition: [9,15] from A, [19,21,25] from B
 * 
 * Check: max(8,18) = 18 ≤ min(9,19) = 9? NO (18 > 9)
 * So i is too large, reduce i.
 * 
 * Continue binary search until valid partition found.
 */

/**
 * EDGE CASES HANDLED:
 * 
 * 1. Empty arrays: Handled by boundary conditions
 * 2. One array much smaller: Binary search on smaller array
 * 3. All elements in one array ≤ all in other: Partitions at boundaries
 * 4. Duplicate values: Handled by ≤ comparison
 * 5. Single element arrays: Works correctly
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Kth Element of Two Sorted Arrays: Generalization of median problem
 * 2. Merge Two Sorted Arrays: Simpler version (O(m+n) time)
 * 3. Find Median from Data Stream: Dynamic version of this problem
 * 4. Median of Sorted Matrix: Extension to 2D
 */

/**
 * PRACTICAL APPLICATIONS:
 * 
 * 1. Database query optimization: Merge statistics from sorted indexes
 * 2. Data analysis: Combine sorted datasets
 * 3. Machine learning: Merge sorted feature vectors
 * 4. Financial analysis: Combine sorted time series data
 */

/**
 * OPTIMIZATION NOTES:
 * 
 * 1. Use integers instead of doubles for better performance
 * 2. Add early return for obvious cases (arrays don't overlap)
 * 3. Use while (low < high) instead of while (low <= high) for cleaner code
 * 4. Pre-calculate values to avoid repeated calculations
 */