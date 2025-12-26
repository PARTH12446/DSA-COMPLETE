// Problem: LeetCode <ID>. <Title>
import java.util.Arrays;
import java.util.Random;

/*
 * PROBLEM: Merge Sorted Array (LeetCode 88)
 * 
 * You are given two integer arrays nums1 and nums2, sorted in non-decreasing order,
 * and two integers m and n, representing the number of elements in nums1 and nums2 respectively.
 * 
 * Merge nums1 and nums2 into a single array sorted in non-decreasing order.
 * 
 * IMPORTANT CONSTRAINTS:
 * 1. nums1 has a length of m + n, where:
 *    - The first m elements denote the elements that should be merged
 *    - The last n elements are set to 0 and should be ignored
 * 2. nums2 has a length of n
 * 3. Merge in-place into nums1 without returning anything
 * 
 * CONSTRAINTS:
 * - nums1.length == m + n
 * - nums2.length == n
 * - 0 <= m, n <= 200
 * - 1 <= m + n <= 200
 * - -10^9 <= nums1[i], nums2[j] <= 10^9
 * 
 * APPROACH: Three-pointer from the end
 * 
 * INTUITION:
 * 1. Since nums1 has extra space at the end, merge from the end to avoid overwriting
 * 2. Use three pointers:
 *    - i: points to last actual element in nums1 (index m-1)
 *    - j: points to last element in nums2 (index n-1)
 *    - k: points to last position in nums1 (index m+n-1)
 * 3. Compare largest elements from both arrays, place larger at end
 * 4. Move pointers accordingly
 * 
 * TIME COMPLEXITY: O(m + n)
 *   - Each element processed exactly once
 *   - Single pass from end to beginning
 * 
 * SPACE COMPLEXITY: O(1)
 *   - Only constant extra space used
 *   - In-place modification of nums1
 */

public class MergeSortedArraysLeetHard {

    /**
     * Merge two sorted arrays in-place into nums1
     * 
     * @param nums1 First array with extra space at the end
     * @param m     Number of actual elements in nums1
     * @param nums2 Second array
     * @param n     Number of elements in nums2
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // Pointers starting from the end
        int i = m - 1;      // Last element in nums1's actual data
        int j = n - 1;      // Last element in nums2
        int k = m + n - 1;  // Last position in nums1
        
        // Merge from the end
        while (j >= 0) {
            // If nums1 has elements left AND nums1[i] > nums2[j]
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
            k--;
        }
        
        // Note: No need to handle remaining nums1 elements
        // They're already in correct position
    }
    
    /**
     * Alternative: Simplified while loop (as in original code)
     */
    public void mergeSimplified(int[] nums1, int m, int[] nums2, int n) {
        while (n > 0) {
            // If nums1 has elements AND nums1's last > nums2's last
            if (m > 0 && nums1[m - 1] > nums2[n - 1]) {
                nums1[m + n - 1] = nums1[m - 1];
                m--;
            } else {
                nums1[m + n - 1] = nums2[n - 1];
                n--;
            }
        }
    }
    
    /**
     * Using for loop (reverse iteration)
     */
    public void mergeWithForLoop(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1, j = n - 1;
        
        for (int k = m + n - 1; k >= 0; k--) {
            if (j < 0) {
                // All nums2 elements placed, nums1 already sorted
                break;
            }
            if (i >= 0 && nums1[i] > nums2[j]) {
                nums1[k] = nums1[i];
                i--;
            } else {
                nums1[k] = nums2[j];
                j--;
            }
        }
    }
    
    /**
     * Brute force approach (not in-place, for verification)
     * Time: O((m+n) log(m+n)), Space: O(m+n)
     */
    public void mergeBruteForce(int[] nums1, int m, int[] nums2, int n) {
        // Copy nums2 into the end of nums1
        for (int i = 0; i < n; i++) {
            nums1[m + i] = nums2[i];
        }
        // Sort the entire array
        Arrays.sort(nums1);
    }
    
    /**
     * Using System.arraycopy and sort (simple but not optimal)
     */
    public void mergeWithSystemCopy(int[] nums1, int m, int[] nums2, int n) {
        // Copy nums2 to the end of nums1
        System.arraycopy(nums2, 0, nums1, m, n);
        // Sort
        Arrays.sort(nums1);
    }
    
    /**
     * Two-step approach: Copy then merge (extra space)
     */
    public void mergeWithExtraSpace(int[] nums1, int m, int[] nums2, int n) {
        int[] temp = new int[m];
        // Copy first m elements of nums1 to temp
        System.arraycopy(nums1, 0, temp, 0, m);
        
        int i = 0, j = 0, k = 0;
        
        // Merge temp and nums2 into nums1
        while (i < m && j < n) {
            if (temp[i] <= nums2[j]) {
                nums1[k++] = temp[i++];
            } else {
                nums1[k++] = nums2[j++];
            }
        }
        
        // Copy remaining elements from temp
        while (i < m) {
            nums1[k++] = temp[i++];
        }
        
        // Copy remaining elements from nums2
        while (j < n) {
            nums1[k++] = nums2[j++];
        }
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        MergeSortedArraysLeetHard solver = new MergeSortedArraysLeetHard();
        
        // Test case 1: Standard case
        System.out.println("Test 1: Standard merge");
        int[] nums1 = {1, 2, 3, 0, 0, 0};
        int[] nums2 = {2, 5, 6};
        solver.merge(nums1, 3, nums2, 3);
        System.out.println("Result: " + Arrays.toString(nums1));
        System.out.println("Expected: [1, 2, 2, 3, 5, 6]");
        
        // Test case 2: nums1 empty
        System.out.println("\nTest 2: nums1 empty");
        nums1 = new int[]{0};
        nums2 = new int[]{1};
        solver.merge(nums1, 0, nums2, 1);
        System.out.println("Result: " + Arrays.toString(nums1));
        System.out.println("Expected: [1]");
        
        // Test case 3: nums2 empty
        System.out.println("\nTest 3: nums2 empty");
        nums1 = new int[]{1};
        nums2 = new int[]{};
        solver.merge(nums1, 1, nums2, 0);
        System.out.println("Result: " + Arrays.toString(nums1));
        System.out.println("Expected: [1]");
        
        // Test case 4: Both arrays have elements
        System.out.println("\nTest 4: Mixed values");
        nums1 = new int[]{4, 5, 6, 0, 0, 0};
        nums2 = new int[]{1, 2, 3};
        solver.merge(nums1, 3, nums2, 3);
        System.out.println("Result: " + Arrays.toString(nums1));
        System.out.println("Expected: [1, 2, 3, 4, 5, 6]");
        
        // Test case 5: nums1 elements all larger
        System.out.println("\nTest 5: nums1 elements all larger");
        nums1 = new int[]{7, 8, 9, 0, 0};
        nums2 = new int[]{1, 2};
        solver.merge(nums1, 3, nums2, 2);
        System.out.println("Result: " + Arrays.toString(nums1));
        System.out.println("Expected: [1, 2, 7, 8, 9]");
        
        // Test case 6: nums2 elements all larger
        System.out.println("\nTest 6: nums2 elements all larger");
        nums1 = new int[]{1, 2, 3, 0, 0};
        nums2 = new int[]{4, 5};
        solver.merge(nums1, 3, nums2, 2);
        System.out.println("Result: " + Arrays.toString(nums1));
        System.out.println("Expected: [1, 2, 3, 4, 5]");
        
        // Test case 7: With negative numbers
        System.out.println("\nTest 7: With negative numbers");
        nums1 = new int[]{-5, 0, 3, 0, 0, 0};
        nums2 = new int[]{-2, -1, 4};
        solver.merge(nums1, 3, nums2, 3);
        System.out.println("Result: " + Arrays.toString(nums1));
        System.out.println("Expected: [-5, -2, -1, 0, 3, 4]");
        
        // Test case 8: Single element arrays
        System.out.println("\nTest 8: Single elements");
        nums1 = new int[]{2, 0};
        nums2 = new int[]{1};
        solver.merge(nums1, 1, nums2, 1);
        System.out.println("Result: " + Arrays.toString(nums1));
        System.out.println("Expected: [1, 2]");
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int m = 10000, n = 10000;
        nums1 = new int[m + n];
        nums2 = new int[n];
        
        // Fill with sorted values
        for (int i = 0; i < m; i++) {
            nums1[i] = i * 2;  // Even numbers
        }
        for (int i = 0; i < n; i++) {
            nums2[i] = i * 2 + 1;  // Odd numbers
        }
        
        long startTime = System.currentTimeMillis();
        solver.merge(nums1, m, nums2, n);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Time for merging 20,000 elements: " + (endTime - startTime) + "ms");
        
        // Verify result is sorted
        boolean sorted = true;
        for (int i = 1; i < nums1.length; i++) {
            if (nums1[i] < nums1[i - 1]) {
                sorted = false;
                break;
            }
        }
        System.out.println("Result is sorted: " + sorted);
        
        // Compare with brute force on small array
        System.out.println("\n=== Verification with Brute Force ===");
        int[] testNums1 = {1, 3, 5, 0, 0, 0};
        int[] testNums2 = {2, 4, 6};
        int[] testCopy = testNums1.clone();
        
        solver.merge(testNums1, 3, testNums2, 3);
        solver.mergeBruteForce(testCopy, 3, testNums2.clone(), 3);
        
        System.out.println("Our algorithm: " + Arrays.toString(testNums1));
        System.out.println("Brute force:   " + Arrays.toString(testCopy));
        System.out.println("Match: " + Arrays.equals(testNums1, testCopy));
    }
    
    /**
     * Why merging from the end works:
     * 
     * Visual example:
     * nums1: [1, 2, 3, 0, 0, 0]  m=3
     * nums2: [2, 5, 6]           n=3
     * 
     * Step-by-step:
     * i=2 (nums1[2]=3), j=2 (nums2[2]=6), k=5
     * Compare 3 and 6 ? 6 larger ? nums1[5]=6, j=1, k=4
     * 
     * i=2 (3), j=1 (5), k=4
     * Compare 3 and 5 ? 5 larger ? nums1[4]=5, j=0, k=3
     * 
     * i=2 (3), j=0 (2), k=3
     * Compare 3 and 2 ? 3 larger ? nums1[3]=3, i=1, k=2
     * 
     * i=1 (2), j=0 (2), k=2
     * Compare 2 and 2 ? equal, use nums2 ? nums1[2]=2, j=-1, k=1
     * 
     * j < 0, loop ends
     * Result: [1, 2, 2, 3, 5, 6]
     * 
     * Key insight: By starting at the end, we never overwrite
     * unprocessed elements in nums1 because those positions
     * are either zeros or have already been moved.
     */
    
    /**
     * Edge Cases:
     * 
     * 1. m = 0 (nums1 has no actual elements):
     *    - Simply copy all nums2 into nums1
     *    - Our while (j >= 0) loop handles this
     * 
     * 2. n = 0 (nums2 empty):
     *    - nums1 already sorted, do nothing
     *    - while (j >= 0) loop doesn't execute
     * 
     * 3. All nums1 elements > all nums2 elements:
     *    - nums1 elements get shifted right
     *    - nums2 elements fill beginning
     * 
     * 4. All nums2 elements > all nums1 elements:
     *    - nums2 elements fill the end
     *    - nums1 elements stay in place
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Merging from beginning:
     *    - Overwrites unprocessed elements in nums1
     *    - Need extra space or complex shifting
     * 
     * 2. Forgetting to decrement pointers:
     *    - Leads to infinite loop or incorrect placement
     * 
     * 3. Incorrect loop condition:
     *    - Should continue while nums2 has elements
     *    - nums1 elements already in correct position if nums2 exhausted
     * 
     * 4. Using wrong indices:
     *    - Remember nums1 has length m+n, but only first m are valid initially
     *    - Off-by-one errors common with zero-based indexing
     */
    
    /**
     * Time Complexity Analysis:
     * 
     * Best case: O(n) when all nums2 elements > all nums1 elements
     * Worst case: O(m+n) when elements interleaved
     * Average case: O(m+n)
     * 
     * Each iteration places one element
     * Total iterations = n (from nums2) + potentially some from nums1
     * But actually max(m+n) iterations
     */
    
    /**
     * Space Complexity Analysis:
     * 
     * O(1) extra space:
     * - Only three integer pointers used
     * - No additional arrays created
     * - Modifies nums1 in-place
     */
    
    /**
     * Alternative Approaches:
     * 
     * 1. Brute force (copy then sort):
     *    - Simple but O((m+n) log(m+n)) time
     *    - O(1) space if using in-place sort
     * 
     * 2. Using extra array:
     *    - O(m+n) time and space
     *    - Simple but violates "no extra space" requirement
     * 
     * 3. Insertion sort style:
     *    - Insert nums2 elements into nums1 one by one
     *    - O(m*n) time worst case
     *    - O(1) space
     */
    
    /**
     * Related Problems:
     * 
     * 1. Merge Two Sorted Lists (LeetCode 21)
     * 2. Merge k Sorted Lists (LeetCode 23)
     * 3. Median of Two Sorted Arrays (LeetCode 4)
     * 4. Intersection of Two Arrays (LeetCode 349)
     */
    
    /**
     * Applications:
     * 
     * 1. Database merge operations
     * 2. Memory management (merging free blocks)
     * 3. External sorting algorithms
     * 4. Version control systems (merging changes)
     */
    
    /**
     * Testing Strategy:
     * 
     * 1. Basic cases from problem statement
     * 2. Edge cases (empty arrays, single elements)
     * 3. Different ordering patterns
     * 4. Large inputs for performance
     * 5. Verify with brute force on small inputs
     */
    
    /**
     * Optimization Notes:
     * 
     * 1. The three-pointer from end approach is optimal
     * 2. No way to do better than O(m+n) time
     * 3. O(1) space is optimal for in-place requirement
     * 4. The algorithm is stable (preserves order of equal elements)
     */
}
