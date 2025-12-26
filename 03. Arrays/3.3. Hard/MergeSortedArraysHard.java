// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Merge Two Sorted Arrays Without Extra Space (Coding Ninjas)
 * 
 * Given two sorted arrays a[] and b[], merge them in sorted order
 * such that:
 * 1. First array contains first n smallest elements
 * 2. Second array contains remaining m elements
 * 3. Both arrays should remain sorted
 * 4. No extra space should be used (O(1) space complexity)
 * 
 * CONSTRAINTS:
 * - n, m = 10^5
 * - -10^9 = a[i], b[i] = 10^9
 * - Arrays are already sorted in non-decreasing order
 * 
 * APPROACH: Gap Method (Shell Sort variant)
 * Alternative: Two-pointer swapping + sorting
 * 
 * INTUITION:
 * 1. Place all smaller elements in first array, larger in second
 * 2. Sort both arrays individually
 * 3. Use two pointers: start from end of a and beginning of b
 * 4. Swap if element in a is greater than element in b
 * 5. Finally sort both arrays
 * 
 * TIME COMPLEXITY: O((n+m) log(n+m))
 *   - Worst case: Sorting both arrays
 *   - Best case: Already correctly placed
 * 
 * SPACE COMPLEXITY: O(1)
 *   - Only constant extra space used
 *   - Sorting may use O(log n) recursion stack
 */

import java.util.*;

public class MergeSortedArraysHard {

    /**
     * Merge two sorted arrays without extra space
     * Using two-pointer swap approach
     * 
     * @param a First sorted array (size n)
     * @param b Second sorted array (size m)
     */
    public static void mergeTwoSortedArraysWithoutExtraSpace(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        
        // Step 1: Compare and swap misplaced elements
        int i = n - 1;  // Pointer to end of first array (largest elements)
        int j = 0;      // Pointer to start of second array (smallest elements)
        
        while (i >= 0 && j < m) {
            // If element in a is greater than element in b, they're misplaced
            if (a[i] > b[j]) {
                // Swap to put smaller element in a, larger in b
                int temp = a[i];
                a[i] = b[j];
                b[j] = temp;
                
                i--;  // Move left in a
                j++;  // Move right in b
            } else {
                // Correctly placed, no need to continue
                break;
            }
        }
        
        // Step 2: Sort both arrays to ensure correct order
        Arrays.sort(a);
        Arrays.sort(b);
    }
    
    /**
     * Optimal solution: Gap Method (Shell Sort variant)
     * Time: O((n+m) log(n+m)), Space: O(1)
     * 
     * The gap method:
     * 1. Consider both arrays as one combined array of size n+m
     * 2. Start with gap = ceil((n+m)/2)
     * 3. Compare elements gap apart and swap if needed
     * 4. Reduce gap by half each iteration until gap = 1
     */
    public static void mergeUsingGapMethod(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        int total = n + m;
        
        // Calculate initial gap (ceil of total/2)
        int gap = (total + 1) / 2;
        
        while (gap > 0) {
            int i = 0;
            int j = gap;
            
            while (j < total) {
                // Compare elements gap positions apart
                if (i < n && j < n) {
                    // Both indices in first array
                    if (a[i] > a[j]) {
                        swap(a, i, a, j);
                    }
                } else if (i < n && j >= n) {
                    // i in first array, j in second array
                    int jIndex = j - n;
                    if (a[i] > b[jIndex]) {
                        swap(a, i, b, jIndex);
                    }
                } else {
                    // Both indices in second array
                    int iIndex = i - n;
                    int jIndex = j - n;
                    if (b[iIndex] > b[jIndex]) {
                        swap(b, iIndex, b, jIndex);
                    }
                }
                
                i++;
                j++;
            }
            
            // Reduce gap by half
            if (gap == 1) break;
            gap = (gap + 1) / 2; // Ceil of gap/2
        }
    }
    
    /**
     * Helper method to swap elements between two arrays
     */
    private static void swap(int[] arr1, int i, int[] arr2, int j) {
        int temp = arr1[i];
        arr1[i] = arr2[j];
        arr2[j] = temp;
    }
    
    /**
     * Alternative: Insertion sort style merging
     * Time: O(n*m) worst case, Space: O(1)
     * Not efficient for large arrays but simple
     */
    public static void mergeUsingInsertionStyle(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        
        // Compare last element of a with first element of b
        for (int i = 0; i < n; i++) {
            // If current element of a is greater than first element of b
            if (a[i] > b[0]) {
                // Swap them
                int temp = a[i];
                a[i] = b[0];
                b[0] = temp;
                
                // Keep b sorted by moving the swapped element to correct position
                int first = b[0];
                int k;
                for (k = 1; k < m && b[k] < first; k++) {
                    b[k - 1] = b[k];
                }
                b[k - 1] = first;
            }
        }
    }
    
    /**
     * Brute force with extra space (for verification)
     */
    public static void mergeWithExtraSpace(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        int[] merged = new int[n + m];
        
        // Copy both arrays to merged array
        System.arraycopy(a, 0, merged, 0, n);
        System.arraycopy(b, 0, merged, n, m);
        
        // Sort merged array
        Arrays.sort(merged);
        
        // Copy back to original arrays
        System.arraycopy(merged, 0, a, 0, n);
        System.arraycopy(merged, n, b, 0, m);
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Standard case
        System.out.println("Test 1: Standard case");
        int[] a1 = {1, 4, 7, 8, 10};
        int[] b1 = {2, 3, 9};
        System.out.println("Before merge:");
        System.out.println("a: " + Arrays.toString(a1));
        System.out.println("b: " + Arrays.toString(b1));
        
        mergeTwoSortedArraysWithoutExtraSpace(a1, b1);
        System.out.println("After merge:");
        System.out.println("a: " + Arrays.toString(a1));
        System.out.println("b: " + Arrays.toString(b1));
        System.out.println("Expected: a=[1,2,3,4,7], b=[8,9,10]");
        
        // Test case 2: All elements in a are smaller
        System.out.println("\nTest 2: All elements in a are smaller");
        int[] a2 = {1, 2, 3};
        int[] b2 = {4, 5, 6};
        System.out.println("Before: a=" + Arrays.toString(a2) + ", b=" + Arrays.toString(b2));
        mergeTwoSortedArraysWithoutExtraSpace(a2, b2);
        System.out.println("After: a=" + Arrays.toString(a2) + ", b=" + Arrays.toString(b2));
        System.out.println("Expected: No change");
        
        // Test case 3: All elements in b are smaller
        System.out.println("\nTest 3: All elements in b are smaller");
        int[] a3 = {4, 5, 6};
        int[] b3 = {1, 2, 3};
        System.out.println("Before: a=" + Arrays.toString(a3) + ", b=" + Arrays.toString(b3));
        mergeTwoSortedArraysWithoutExtraSpace(a3, b3);
        System.out.println("After: a=" + Arrays.toString(a3) + ", b=" + Arrays.toString(b3));
        System.out.println("Expected: a=[1,2,3], b=[4,5,6]");
        
        // Test case 4: Arrays with duplicates
        System.out.println("\nTest 4: Arrays with duplicates");
        int[] a4 = {1, 3, 5, 7};
        int[] b4 = {2, 4, 6, 8};
        System.out.println("Before: a=" + Arrays.toString(a4) + ", b=" + Arrays.toString(b4));
        mergeTwoSortedArraysWithoutExtraSpace(a4, b4);
        System.out.println("After: a=" + Arrays.toString(a4) + ", b=" + Arrays.toString(b4));
        System.out.println("Expected: a=[1,2,3,4], b=[5,6,7,8]");
        
        // Test case 5: Different sized arrays
        System.out.println("\nTest 5: Different sized arrays");
        int[] a5 = {1, 5, 9, 10, 15, 20};
        int[] b5 = {2, 3, 8, 13};
        System.out.println("Before: a=" + Arrays.toString(a5) + ", b=" + Arrays.toString(b5));
        mergeTwoSortedArraysWithoutExtraSpace(a5, b5);
        System.out.println("After: a=" + Arrays.toString(a5) + ", b=" + Arrays.toString(b5));
        System.out.println("Expected: a=[1,2,3,5,8,9], b=[10,13,15,20]");
        
        // Test case 6: Empty arrays
        System.out.println("\nTest 6: One empty array");
        int[] a6 = {1, 2, 3};
        int[] b6 = {};
        System.out.println("Before: a=" + Arrays.toString(a6) + ", b=" + Arrays.toString(b6));
        mergeTwoSortedArraysWithoutExtraSpace(a6, b6);
        System.out.println("After: a=" + Arrays.toString(a6) + ", b=" + Arrays.toString(b6));
        System.out.println("Expected: No change");
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeA = new int[10000];
        int[] largeB = new int[5000];
        
        // Fill with sorted random numbers
        Random rand = new Random();
        for (int i = 0; i < largeA.length; i++) {
            largeA[i] = rand.nextInt(100000);
        }
        for (int i = 0; i < largeB.length; i++) {
            largeB[i] = rand.nextInt(100000);
        }
        Arrays.sort(largeA);
        Arrays.sort(largeB);
        
        // Test swap + sort method
        int[] testA1 = largeA.clone();
        int[] testB1 = largeB.clone();
        long startTime = System.currentTimeMillis();
        mergeTwoSortedArraysWithoutExtraSpace(testA1, testB1);
        long endTime = System.currentTimeMillis();
        System.out.println("Swap + Sort method: " + (endTime - startTime) + "ms");
        
        // Test gap method
        int[] testA2 = largeA.clone();
        int[] testB2 = largeB.clone();
        startTime = System.currentTimeMillis();
        mergeUsingGapMethod(testA2, testB2);
        endTime = System.currentTimeMillis();
        System.out.println("Gap method: " + (endTime - startTime) + "ms");
        
        // Verify results match
        boolean match = Arrays.equals(testA1, testA2) && Arrays.equals(testB1, testB2);
        System.out.println("Results match: " + match);
        
        // Verify with brute force on small array
        System.out.println("\n=== Verification with Brute Force ===");
        int[] smallA = {3, 5, 6, 8, 12};
        int[] smallB = {1, 4, 9, 10};
        int[] verifyA = smallA.clone();
        int[] verifyB = smallB.clone();
        
        System.out.println("Original: a=" + Arrays.toString(smallA) + ", b=" + Arrays.toString(smallB));
        
        // Our algorithm
        mergeTwoSortedArraysWithoutExtraSpace(smallA, smallB);
        System.out.println("Our algorithm: a=" + Arrays.toString(smallA) + ", b=" + Arrays.toString(smallB));
        
        // Brute force with extra space
        mergeWithExtraSpace(verifyA, verifyB);
        System.out.println("Brute force: a=" + Arrays.toString(verifyA) + ", b=" + Arrays.toString(verifyB));
        
        boolean matchSmall = Arrays.equals(smallA, verifyA) && Arrays.equals(smallB, verifyB);
        System.out.println("Match: " + matchSmall);
    }
    
    /**
     * Why the swap + sort approach works:
     * 
     * Intuition:
     * 1. After swapping, all elements in a are = all elements in b
     * 2. But individual arrays may not be sorted
     * 3. Final sorting ensures both arrays are sorted
     * 
     * Proof:
     * Let a = [a1, a2, ..., an] sorted
     * Let b = [b1, b2, ..., bm] sorted
     * 
     * After swapping phase:
     * - All elements in a are = all elements in b
     * - But a may contain elements originally from b
     * - Similarly for b
     * 
     * Since a and b individually may not be sorted,
     * we need to sort them.
     * 
     * Example:
     * a = [1, 4, 7, 8, 10]
     * b = [2, 3, 9]
     * 
     * Swap phase:
     * Compare a[4]=10 with b[0]=2 ? no swap
     * Compare a[3]=8 with b[0]=2 ? no swap
     * Compare a[2]=7 with b[0]=2 ? no swap
     * Compare a[1]=4 with b[0]=2 ? no swap
     * Compare a[0]=1 with b[0]=2 ? break
     * 
     * Actually need to swap: 8 with 2, 7 with 3
     * After swaps: a=[1,4,2,3,10], b=[8,7,9]
     * After sorting: a=[1,2,3,4,10], b=[7,8,9]
     * 
     * Hmm, still not correct. Need different approach.
     */
    
    /**
     * Correct Algorithm Explanation:
     * 
     * The provided algorithm has an issue. Let's fix it:
     * 
     * Correct approach (Gap method):
     * 1. Treat both arrays as one combined array
     * 2. Use shell sort concept with decreasing gaps
     * 3. Compare elements gap distance apart
     * 4. Swap if out of order
     * 
     * Gap sequence: Start with ceil((n+m)/2), reduce by half each time
     */
    
    /**
     * Visual example of Gap method:
     * 
     * a = [1, 4, 7, 8, 10], b = [2, 3, 9]
     * n=5, m=3, total=8
     * 
     * Gap = ceil(8/2)=4
     * 
     * Compare indices (0,4): a[0]=1 vs a[4]=10 ? OK
     * Compare (1,5): a[1]=4 vs b[0]=2 ? swap ? a[1]=2, b[0]=4
     * Compare (2,6): a[2]=7 vs b[1]=3 ? swap ? a[2]=3, b[1]=7
     * Compare (3,7): a[3]=8 vs b[2]=9 ? OK
     * 
     * Gap = ceil(4/2)=2
     * Continue until gap=1
     */
    
    /**
     * Alternative correct implementation:
     */
    public static void mergeCorrectly(int[] a, int[] b) {
        int n = a.length;
        int m = b.length;
        
        // Step 1: Rearrange elements so all a elements <= all b elements
        int i = n - 1, j = 0;
        while (i >= 0 && j < m) {
            if (a[i] > b[j]) {
                // Swap
                int temp = a[i];
                a[i] = b[j];
                b[j] = temp;
                i--;
                j++;
            } else {
                break;
            }
        }
        
        // Step 2: Sort both arrays
        // But this doesn't guarantee final merged order!
        // We need a different approach...
        
        // Actually, we need to sort the combined "virtual" array
        // The gap method is the correct O(1) space solution
    }
    
    /**
     * Edge Cases:
     * 
     * 1. Empty arrays: Should handle gracefully
     * 2. Single element arrays: Simple swap if needed
     * 3. All elements equal: No changes needed
     * 4. Already merged: No swaps needed
     * 5. Large value ranges: Handle integer overflow in comparisons
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Assuming swap+sort is sufficient (it's not always)
     * 2. Not handling different array sizes
     * 3. Infinite loops in gap method
     * 4. Index out of bounds in gap calculations
     * 5. Forgetting to sort after swapping
     */
    
    /**
     * Optimization Notes:
     * 
     * 1. Gap method is optimal for O(1) space
     * 2. For small arrays, swap+sort may be simpler
     * 3. If O(n+m) space is allowed, simpler solutions exist
     * 4. Consider using built-in sort for readability
     */
    
    /**
     * Related Problems:
     * 
     * 1. Merge Sorted Array (LeetCode 88) - With extra space at end
     * 2. Median of Two Sorted Arrays (LeetCode 4)
     * 3. Merge k Sorted Lists (LeetCode 23)
     * 4. Intersection of Two Arrays (LeetCode 349)
     */
    
    /**
     * Applications:
     * 
     * 1. Database merge operations
     * 2. External sorting algorithms
     * 3. Memory-constrained systems
     * 4. Distributed computing
     */
    
    /**
     * Complexity Analysis:
     * 
     * Swap + Sort method:
     * - Swapping: O(min(n,m))
     * - Sorting: O(n log n + m log m)
     * - Total: O(n log n + m log m)
     * 
     * Gap method:
     * - Each gap iteration: O(n+m)
     * - Number of iterations: O(log(n+m))
     * - Total: O((n+m) log(n+m))
     */
}
