// Problem: LeetCode <ID>. <Title>
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

/*
 * PROBLEM: Reverse Pairs (LeetCode 493)
 * 
 * Given an integer array nums, return the number of reverse pairs in the array.
 * A reverse pair is a pair (i, j) where:
 *   0 <= i < j < nums.length and
 *   nums[i] > 2 * nums[j]
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 5 * 10^4
 * - -2^31 <= nums[i] <= 2^31 - 1
 * - Must handle integer overflow (use long for comparisons)
 * 
 * APPROACH: Modified Merge Sort (Divide and Conquer)
 * 
 * INTUITION:
 * 1. Divide array into halves recursively
 * 2. Count reverse pairs where:
 *    - Both in left half (recursive call)
 *    - Both in right half (recursive call)
 *    - One in left, one in right (cross pairs)
 * 3. Merge halves (sort subarray)
 * 
 * KEY OPTIMIZATION:
 * - After counting cross pairs, sort the subarray [l, r]
 * - This ensures each recursive call works with sorted subarrays
 * - Simplifies implementation compared to full merge sort
 * 
 * TIME COMPLEXITY: O(n log² n)
 *   - Divide: O(log n) levels
 *   - Each level: O(n log n) for sorting
 *   - Total: O(n log² n)
 * 
 * SPACE COMPLEXITY: O(log n) for recursion stack
 *   - Uses Arrays.sort() which uses O(n) temporary space internally
 *   - But we don't allocate extra arrays explicitly
 */

public class ReversePairsLeetHard {

    /**
     * Main function to count reverse pairs
     * 
     * @param nums Input array
     * @return Number of reverse pairs (i,j) where nums[i] > 2*nums[j]
     */
    public int reversePairs(int[] nums) {
        return mergeSortCount(nums, 0, nums.length - 1);
    }
    
    /**
     * Modified merge sort that counts reverse pairs
     * Uses Arrays.sort() for merging instead of manual merge
     * 
     * @param nums Array to process
     * @param left Start index of current subarray
     * @param right End index of current subarray
     * @return Number of reverse pairs in subarray nums[left..right]
     */
    private int mergeSortCount(int[] nums, int left, int right) {
        // Base case: single element or empty subarray
        if (left >= right) {
            return 0;
        }
        
        // Divide: find middle point
        int mid = left + (right - left) / 2;
        
        // Conquer: count pairs in left and right halves
        int count = mergeSortCount(nums, left, mid) + 
                    mergeSortCount(nums, mid + 1, right);
        
        // Count cross pairs between left and right halves
        // Since halves are sorted (after recursive calls),
        // we can use two-pointer technique
        int j = mid + 1;
        for (int i = left; i <= mid; i++) {
            // Move j while condition holds: nums[i] > 2 * nums[j]
            // Use long to prevent integer overflow
            while (j <= right && (long) nums[i] > 2L * (long) nums[j]) {
                j++;
            }
            // All elements from mid+1 to j-1 satisfy the condition
            count += (j - (mid + 1));
        }
        
        // Combine: sort the entire subarray
        // This ensures parent calls work with sorted subarrays
        Arrays.sort(nums, left, right + 1);
        
        return count;
    }
    
    /**
     * Alternative: Full merge sort implementation (more efficient)
     * Time: O(n log n), Space: O(n)
     */
    public int reversePairsMergeSort(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        return mergeSort(nums, 0, nums.length - 1, new int[nums.length]);
    }
    
    private int mergeSort(int[] nums, int left, int right, int[] temp) {
        if (left >= right) return 0;
        
        int mid = left + (right - left) / 2;
        int count = mergeSort(nums, left, mid, temp) + 
                    mergeSort(nums, mid + 1, right, temp);
        
        // Count cross pairs
        count += countPairs(nums, left, mid, right);
        
        // Merge sorted halves
        merge(nums, left, mid, right, temp);
        
        return count;
    }
    
    /**
     * Count reverse pairs between two sorted halves
     */
    private int countPairs(int[] nums, int left, int mid, int right) {
        int count = 0;
        int j = mid + 1;
        
        for (int i = left; i <= mid; i++) {
            while (j <= right && (long) nums[i] > 2L * (long) nums[j]) {
                j++;
            }
            count += (j - (mid + 1));
        }
        
        return count;
    }
    
    /**
     * Standard merge operation for merge sort
     */
    private void merge(int[] nums, int left, int mid, int right, int[] temp) {
        int i = left, j = mid + 1, k = left;
        
        while (i <= mid && j <= right) {
            if (nums[i] <= nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }
        
        while (i <= mid) {
            temp[k++] = nums[i++];
        }
        
        while (j <= right) {
            temp[k++] = nums[j++];
        }
        
        // Copy back to original array
        System.arraycopy(temp, left, nums, left, right - left + 1);
    }
    
    /**
     * Using Binary Indexed Tree (Fenwick Tree) approach
     * Time: O(n log n), Space: O(n)
     */
    public int reversePairsBIT(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        // Get sorted unique values of nums and 2*nums
        long[] sorted = new long[nums.length * 2];
        for (int i = 0; i < nums.length; i++) {
            sorted[i * 2] = nums[i];
            sorted[i * 2 + 1] = 2L * nums[i];
        }
        Arrays.sort(sorted);
        
        // Map values to ranks (1-indexed)
        Map<Long, Integer> rankMap = new HashMap<>();
        int rank = 1;
        for (long num : sorted) {
            if (!rankMap.containsKey(num)) {
                rankMap.put(num, rank++);
            }
        }
        
        // Binary Indexed Tree
        int[] bit = new int[rank];
        int count = 0;
        
        // Process from right to left
        for (int i = nums.length - 1; i >= 0; i--) {
            long val = nums[i];
            // Count numbers < val (strictly less than)
            count += query(bit, rankMap.get(val) - 1);
            // Add 2*val to BIT
            update(bit, rankMap.get(2L * val), 1);
        }
        
        return count;
    }
    
    private void update(int[] bit, int index, int delta) {
        while (index < bit.length) {
            bit[index] += delta;
            index += index & -index;
        }
    }
    
    private int query(int[] bit, int index) {
        int sum = 0;
        while (index > 0) {
            sum += bit[index];
            index -= index & -index;
        }
        return sum;
    }
    
    /**
     * Brute force for verification (O(n²) time)
     */
    public int reversePairsBruteForce(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if ((long) nums[i] > 2L * (long) nums[j]) {
                    count++;
                }
            }
        }
        return count;
    }
    
    /**
     * Test method with LeetCode examples
     */
    public static void main(String[] args) {
        ReversePairsLeetHard solver = new ReversePairsLeetHard();
        
        // Test case 1: LeetCode Example 1
        int[] nums1 = {1, 3, 2, 3, 1};
        System.out.println("Test 1: [1, 3, 2, 3, 1]");
        System.out.println("Result: " + solver.reversePairs(nums1));
        System.out.println("Expected: 2");
        System.out.println("Brute Force: " + solver.reversePairsBruteForce(nums1.clone()));
        
        // Test case 2: LeetCode Example 2
        int[] nums2 = {2, 4, 3, 5, 1};
        System.out.println("\nTest 2: [2, 4, 3, 5, 1]");
        System.out.println("Result: " + solver.reversePairs(nums2));
        System.out.println("Expected: 3");
        System.out.println("Brute Force: " + solver.reversePairsBruteForce(nums2.clone()));
        
        // Test case 3: Empty array
        int[] nums3 = {};
        System.out.println("\nTest 3: []");
        System.out.println("Result: " + solver.reversePairs(nums3));
        System.out.println("Expected: 0");
        
        // Test case 4: Single element
        int[] nums4 = {5};
        System.out.println("\nTest 4: [5]");
        System.out.println("Result: " + solver.reversePairs(nums4));
        System.out.println("Expected: 0");
        
        // Test case 5: All ascending
        int[] nums5 = {1, 2, 3, 4, 5};
        System.out.println("\nTest 5: [1, 2, 3, 4, 5]");
        System.out.println("Result: " + solver.reversePairs(nums5));
        System.out.println("Brute Force: " + solver.reversePairsBruteForce(nums5.clone()));
        
        // Test case 6: All descending
        int[] nums6 = {5, 4, 3, 2, 1};
        System.out.println("\nTest 6: [5, 4, 3, 2, 1]");
        System.out.println("Result: " + solver.reversePairs(nums6));
        System.out.println("Brute Force: " + solver.reversePairsBruteForce(nums6.clone()));
        
        // Test case 7: With negative numbers
        int[] nums7 = {-5, -5};
        System.out.println("\nTest 7: [-5, -5]");
        System.out.println("Result: " + solver.reversePairs(nums7));
        System.out.println("Brute Force: " + solver.reversePairsBruteForce(nums7.clone()));
        // Check: -5 > 2 * (-5) = -10? Yes, so 1 pair
        
        // Test case 8: Large values (overflow test)
        int[] nums8 = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0};
        System.out.println("\nTest 8: [MAX, MIN, 0]");
        System.out.println("Result: " + solver.reversePairs(nums8));
        System.out.println("Brute Force: " + solver.reversePairsBruteForce(nums8.clone()));
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeArray = new int[50000];
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = rand.nextInt(20001) - 10000; // Range -10000 to 10000
        }
        
        long startTime = System.currentTimeMillis();
        int result1 = solver.reversePairs(largeArray.clone());
        long endTime = System.currentTimeMillis();
        System.out.println("Arrays.sort() method: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        int result2 = solver.reversePairsMergeSort(largeArray.clone());
        endTime = System.currentTimeMillis();
        System.out.println("Full Merge Sort: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        int result3 = solver.reversePairsBIT(largeArray.clone());
        endTime = System.currentTimeMillis();
        System.out.println("Binary Indexed Tree: " + (endTime - startTime) + "ms");
        
        System.out.println("Results match: " + (result1 == result2 && result2 == result3));
        
        // Verify with brute force on small array
        System.out.println("\n=== Verification with Brute Force ===");
        int[] testArray = {1, 3, 2, 3, 1};
        int algoResult = solver.reversePairs(testArray.clone());
        int bruteResult = solver.reversePairsBruteForce(testArray);
        System.out.println("Algorithm: " + algoResult);
        System.out.println("Brute Force: " + bruteResult);
        System.out.println("Match: " + (algoResult == bruteResult));
    }
    
    /**
     * Why the algorithm works:
     * 
     * Key insight: After processing left and right halves recursively,
     * they become sorted. This allows efficient counting of cross pairs.
     * 
     * Example: nums = [1, 3, 2, 3, 1]
     * 
     * Recursive calls:
     * Level 1: left=[1,3,2], right=[3,1]
     * 
     * Process left half [1,3,2]:
     *   left-left=[1], left-right=[3,2]
     *   Count cross pairs in [1] and [3,2]:
     *     For 1: check 3 (1>2*3? No), check 2 (1>2*2? No) ? 0 pairs
     *   Sort left-right to [2,3], then sort whole left to [1,2,3]
     * 
     * Process right half [3,1]:
     *   Count cross: for 3, check 1 (3>2*1? Yes) ? 1 pair
     *   Sort to [1,3]
     * 
     * Now count cross between [1,2,3] and [1,3]:
     *   For 1: while 1>2*1? No ? j stays at 0 ? count += 0
     *   For 2: while 2>2*1? No ? j stays at 0 ? count += 0
     *   For 3: while 3>2*1? Yes ? j=1
     *          while 3>2*3? No ? j stays at 1 ? count += 1
     *   Total cross pairs: 1
     * 
     * Total: 0 + 1 + 1 = 2 ?
     */
    
    /**
     * Time Complexity Analysis:
     * 
     * Original implementation (using Arrays.sort):
     * Recurrence: T(n) = 2T(n/2) + O(n log n) [for sorting]
     * By Master Theorem: T(n) = O(n log² n)
     * 
     * Full merge sort implementation:
     * Recurrence: T(n) = 2T(n/2) + O(n) [counting + merging]
     * By Master Theorem: T(n) = O(n log n)
     * 
     * Why Arrays.sort adds extra log factor:
     * - Arrays.sort() is O(n log n) for each subarray
     * - At each level, total sorting work is O(n log n)
     * - log n levels × O(n log n) per level = O(n log² n)
     */
    
    /**
     * Space Complexity Analysis:
     * 
     * Original implementation:
     * - Recursion stack: O(log n)
     * - Arrays.sort uses O(n) temporary space internally
     * - Total: O(n)
     * 
     * Full merge sort:
     * - Recursion stack: O(log n)
     * - Temporary array: O(n)
     * - Total: O(n)
     * 
     * BIT implementation:
     * - Rank map: O(n)
     * - BIT array: O(n)
     * - Sorted array: O(n)
     * - Total: O(n)
     */
    
    /**
     * Why use long for comparisons?
     * 
     * Example dangerous case:
     * nums[i] = 1,000,000,000 (1e9)
     * nums[j] = 600,000,000 (6e8)
     * 
     * 2 * nums[j] = 1,200,000,000 (1.2e9)
     * This exceeds 32-bit signed int max (2,147,483,647) but fits in long
     * 
     * Using int: 2 * 600000000 = 1200000000 (fits in int)
     * But consider: nums[i] = 2^30, nums[j] = 2^30
     * 2 * nums[j] = 2^31 which overflows to negative
     * 
     * Always use: (long) nums[i] > 2L * (long) nums[j]
     */
    
    /**
     * Edge Cases:
     * 
     * 1. Empty or single element array: return 0
     * 2. All negative numbers: Condition nums[i] > 2*nums[j] behaves differently
     *    Example: -1 > 2*(-2) = -4? Yes (-1 > -4)
     * 3. Integer overflow cases
     * 4. Duplicate values
     * 5. Already sorted (ascending/descending)
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Not using long for multiplication (overflow)
     * 2. Off-by-one errors in indices
     * 3. Forgetting to sort subarrays
     * 4. Modifying array while counting
     * 5. Incorrectly counting pairs (including or excluding boundary)
     */
    
    /**
     * Optimization Notes:
     * 
     * 1. Full merge sort is faster than Arrays.sort approach
     * 2. Use System.arraycopy instead of manual copying
     * 3. Pre-allocate temporary array once
     * 4. For BIT, compress coordinates to reduce space
     */
    
    /**
     * Related LeetCode Problems:
     * 
     * 1. Count of Smaller Numbers After Self (315)
     * 2. Count of Range Sum (327)
     * 3. Global and Local Inversions (775)
     * 4. Create Sorted Array through Instructions (1649)
     */
    
    /**
     * Applications:
     * 
     * 1. Financial fraud detection (identifying abnormal transactions)
     * 2. Quality control (finding significant deviations)
     * 3. Data analysis (identifying outliers)
     * 4. Version control systems (finding significant changes)
     */
    
    /**
     * Testing Strategy:
     * 
     * 1. Small test cases with known answers
     * 2. Random arrays compared with brute force
     * 3. Edge cases (empty, single, sorted, large values)
     * 4. Performance tests with large n
     * 5. Verify no integer overflow
     */
}
