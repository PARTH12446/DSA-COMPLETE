// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Number of Inversions (Coding Ninjas / Count Inversions)
 * 
 * Given an array 'a' of size 'n', count the total number of inversions.
 * 
 * DEFINITION: An inversion is a pair of indices (i, j) such that:
 *   i < j  AND  a[i] > a[j]
 * 
 * In other words: When a larger element appears before a smaller element.
 * 
 * EXAMPLE:
 * Array: [2, 4, 1, 3, 5]
 * Inversions:
 *   (2,1) ? 2 > 1
 *   (4,1) ? 4 > 1
 *   (4,3) ? 4 > 3
 * Total: 3 inversions
 * 
 * IMPORTANCE:
 * - Inversions measure how "unsorted" an array is
 * - Used in analyzing sorting algorithm performance
 * - Applications: Collaborative filtering, ranking systems, database analysis
 * 
 * CONSTRAINTS:
 * - 1 = n = 10^5
 * - 1 = a[i] = 10^9
 * - Result can be large, use long data type
 * 
 * APPROACH: Modified Merge Sort (Divide and Conquer)
 * 
 * INTUITION:
 * - During merge step of merge sort, when we take element from right half
 *   before left half, all remaining elements in left half are greater
 * - Each such case represents multiple inversions
 * 
 * ALGORITHM:
 * 1. Divide: Split array into halves recursively
 * 2. Conquer: Count inversions in left and right halves
 * 3. Combine: Count cross inversions during merge + merge sorted halves
 * 
 * TIME COMPLEXITY: O(n log n)
 *   - Same as merge sort
 *   - Each level processes n elements
 *   - log n levels of recursion
 * 
 * SPACE COMPLEXITY: O(n)
 *   - Temporary array for merging
 *   - Recursion stack depth: O(log n)
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Brute Force: O(n²) - Check all pairs
 * 2. Binary Indexed Tree (Fenwick Tree): O(n log n)
 * 3. Self-balancing BST: O(n log n)
 */

import java.util.*;

public class CountInversionsHard {

    /**
     * Main function to count total inversions in array.
     * 
     * @param a Input array
     * @param n Size of array
     * @return Total number of inversions (as long to handle large values)
     */
    public static long numberOfInversions(int[] a, int n) {
        // Create a copy to avoid modifying original array if needed
        int[] arr = a.clone();
        return mergeSort(arr, 0, n - 1);
    }

    /**
     * Recursive merge sort function that counts inversions.
     * 
     * @param arr Array to sort and count inversions
     * @param low Start index of current subarray
     * @param high End index of current subarray
     * @return Number of inversions in this subarray
     */
    private static long mergeSort(int[] arr, int low, int high) {
        long cnt = 0;
        
        // Base case: single element or empty subarray
        if (low >= high) return cnt;
        
        // Divide: find middle point
        int mid = low + (high - low) / 2;  // Avoid overflow
        
        // Conquer: count inversions in left and right halves
        cnt += mergeSort(arr, low, mid);      // Left half
        cnt += mergeSort(arr, mid + 1, high); // Right half
        
        // Combine: count cross inversions and merge
        cnt += merge(arr, low, mid, high);
        
        return cnt;
    }

    /**
     * Merge two sorted halves and count cross inversions.
     * 
     * KEY INSIGHT: When arr[left] > arr[right], then all elements
     * from left to mid are > arr[right] (because left half is sorted).
     * So we add (mid - left + 1) inversions.
     * 
     * @param arr Array containing both halves
     * @param low Start index of first half
     * @param mid End index of first half
     * @param high End index of second half
     * @return Number of cross inversions between the two halves
     */
    private static long merge(int[] arr, int low, int mid, int high) {
        List<Integer> temp = new ArrayList<>();  // Temporary storage
        int left = low;      // Pointer for left half
        int right = mid + 1; // Pointer for right half
        long cnt = 0;        // Cross inversion count
        
        // Merge both halves
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                // No inversion, take from left
                temp.add(arr[left++]);
            } else {
                // arr[left] > arr[right] ? INVERSION!
                // All remaining elements in left half are > arr[right]
                temp.add(arr[right++]);
                cnt += (mid - left + 1);  // Count all cross inversions
            }
        }
        
        // Copy remaining elements from left half (if any)
        while (left <= mid) {
            temp.add(arr[left++]);
        }
        
        // Copy remaining elements from right half (if any)
        while (right <= high) {
            temp.add(arr[right++]);
        }
        
        // Copy merged result back to original array
        for (int i = low; i <= high; i++) {
            arr[i] = temp.get(i - low);
        }
        
        return cnt;
    }
    
    /**
     * Alternative implementation using array instead of ArrayList
     * for better performance.
     */
    private static long mergeOptimized(int[] arr, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int left = low;
        int right = mid + 1;
        int k = 0;
        long cnt = 0;
        
        while (left <= mid && right <= high) {
            if (arr[left] <= arr[right]) {
                temp[k++] = arr[left++];
            } else {
                temp[k++] = arr[right++];
                cnt += (mid - left + 1);
            }
        }
        
        while (left <= mid) {
            temp[k++] = arr[left++];
        }
        
        while (right <= high) {
            temp[k++] = arr[right++];
        }
        
        // Copy back to original array
        System.arraycopy(temp, 0, arr, low, temp.length);
        
        return cnt;
    }
    
    /**
     * Brute force solution for verification (O(n²) time)
     * Only use for small n (n = 1000)
     */
    public static long numberOfInversionsBruteForce(int[] a, int n) {
        long cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (a[i] > a[j]) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Standard example
        int[] arr1 = {2, 4, 1, 3, 5};
        int n1 = arr1.length;
        System.out.println("Test 1: " + numberOfInversions(arr1, n1)); // Expected: 3
        
        // Test case 2: Sorted array (no inversions)
        int[] arr2 = {1, 2, 3, 4, 5};
        int n2 = arr2.length;
        System.out.println("Test 2: " + numberOfInversions(arr2, n2)); // Expected: 0
        
        // Test case 3: Reverse sorted (maximum inversions)
        int[] arr3 = {5, 4, 3, 2, 1};
        int n3 = arr3.length;
        System.out.println("Test 3: " + numberOfInversions(arr3, n3)); // Expected: 10
        // Formula for reverse sorted: n*(n-1)/2 = 5*4/2 = 10
        
        // Test case 4: All equal elements
        int[] arr4 = {2, 2, 2, 2};
        int n4 = arr4.length;
        System.out.println("Test 4: " + numberOfInversions(arr4, n4)); // Expected: 0
        
        // Test case 5: Single element
        int[] arr5 = {1};
        int n5 = arr5.length;
        System.out.println("Test 5: " + numberOfInversions(arr5, n5)); // Expected: 0
        
        // Test case 6: Large array (performance test)
        int[] arr6 = new int[100000];
        // Create reverse sorted array for worst case
        for (int i = 0; i < arr6.length; i++) {
            arr6[i] = arr6.length - i;
        }
        int n6 = arr6.length;
        
        System.out.println("\nPerformance test on large array (n=100000)...");
        long startTime = System.currentTimeMillis();
        long result = numberOfInversions(arr6, n6);
        long endTime = System.currentTimeMillis();
        
        long expected = (long)n6 * (n6 - 1) / 2;  // n*(n-1)/2
        System.out.println("Result: " + result);
        System.out.println("Expected: " + expected);
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
        
        // Verify with brute force for small arrays
        System.out.println("\n=== Verification with brute force ===");
        int[] testArr = {3, 1, 2, 4, 5};
        long mergeSortResult = numberOfInversions(testArr.clone(), testArr.length);
        long bruteForceResult = numberOfInversionsBruteForce(testArr, testArr.length);
        System.out.println("Merge Sort: " + mergeSortResult);
        System.out.println("Brute Force: " + bruteForceResult);
        System.out.println("Match: " + (mergeSortResult == bruteForceResult));
    }
    
    /**
     * Why this algorithm works:
     * 
     * 1. Divide: Split array into halves until single elements
     * 2. Count local inversions: Inversions within left/right halves
     * 3. Count cross inversions: Inversions between left and right halves
     * 
     * During merge:
     * - Left half: indices [low..mid], sorted
     * - Right half: indices [mid+1..high], sorted
     * - When arr[left] > arr[right], all remaining left elements > arr[right]
     *   because left half is sorted in ascending order
     * 
     * Example:
     * Left: [2, 4, 5], Right: [1, 3]
     * When comparing 2 > 1, we know:
     * - 2 > 1 ?
     * - 4 > 1 ? (because 4 > 2)
     * - 5 > 1 ? (because 5 > 2)
     * So we add 3 inversions: (2,1), (4,1), (5,1)
     */
    
    /**
     * Visual example of counting during merge:
     * 
     * Initial: [2, 4, 1, 3, 5]
     * Recursively split:
     * Left: [2, 4] (sorted: [2, 4])
     * Right: [1, 3, 5] (sorted: [1, 3, 5])
     * 
     * Merge step:
     * Left: [2, 4], Right: [1, 3, 5]
     * Compare 2 > 1 ? inversion! Add (mid-left+1) = (1-0+1) = 2 inversions
     *   These are: (2,1) and (4,1)
     * 
     * Continue merging:
     * Current: [1]
     * Compare 2 = 3 ? no inversion
     * Compare 4 > 3 ? inversion! Add (1-1+1) = 1 inversion
     *   This is: (4,3)
     * 
     * Total: 3 inversions
     */
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. Using int instead of long for count:
     *    - Maximum inversions for n=10^5 is ~5×10^9 > 2^31
     *    - Solution: Use long data type
     * 
     * 2. Not handling equal elements correctly:
     *    - Use = instead of < to avoid counting equal elements as inversions
     *    - Inversion requires STRICT inequality (a[i] > a[j])
     * 
     * 3. Modifying original array:
     *    - Problem may require not modifying input
     *    - Solution: Create copy before sorting
     * 
     * 4. Integer overflow in mid calculation:
     *    - Use: low + (high - low) / 2
     *    - Not: (low + high) / 2
     */
    
    /**
     * Applications of Inversion Count:
     * 
     * 1. Sorting Analysis:
     *    - Measure how far array is from being sorted
     *    - Used in analyzing sorting algorithm performance
     * 
     * 2. Collaborative Filtering:
     *    - Measure similarity between user preferences
     *    - Used in recommendation systems
     * 
     * 3. Ranking Systems:
     *    - Compare different ranking lists
     *    - Used in search engines
     * 
     * 4. Genomics:
     *    - Measure evolutionary distance between species
     *    - Count gene inversions
     */
    
    /**
     * Related Problems:
     * 
     * 1. Count Inversions (LeetCode) - Same problem
     * 2. Reverse Pairs (LeetCode 493) - Similar but with condition nums[i] > 2*nums[j]
     * 3. Global and Local Inversions (LeetCode 775)
     * 4. Count Smaller Numbers After Self (LeetCode 315)
     */
}
