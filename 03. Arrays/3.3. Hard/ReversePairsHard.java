// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Reverse Pairs (LeetCode 493 / Striver DSA variation)
 * 
 * Given an integer array nums, return the number of reverse pairs.
 * A reverse pair is a pair (i, j) such that:
 *   - 0 <= i < j < n
 *   - nums[i] > 2 * nums[j]
 * 
 * EXAMPLE:
 *   nums = [1, 3, 2, 3, 1]
 *   Reverse pairs: (3, 1) at indices (1, 4) and (2, 1) at (3, 4)
 *   Answer = 2
 * 
 * NAIVE APPROACH:
 *   - Double loop over all pairs (i, j)
 *   - Check if nums[i] > 2 * nums[j]
 *   - Time: O(n^2), Space: O(1)
 * 
 * OPTIMAL APPROACH (MERGE SORT BASED):
 *   - Use modified merge sort (divide & conquer)
 *   - While merging two sorted halves [low..mid] and [mid+1..high],
 *     count j for each i such that nums[i] > 2 * nums[j].
 *   - Because halves are sorted, we can use two pointers to count pairs
 *     in O(n) per merge level.
 *   - Total Time: O(n log n), Space: O(n) for temp array (typical version).
 * 
 * THIS CLASS:
 *   - Demonstrates an in-place merge sort style helper that *could* be used
 *     to explore reverse-pair counting with less extra memory.
 *   - The actual reverse-pair counting logic lives in ReversePairsLeetHard.
 *   - Here we keep an in-place merge + structure for educational purposes.
 * 
 * TIME COMPLEXITY (current helpers):
 *   - mergeSortInPlace: O(n log n) comparisons and rotations.
 *   - mergeInPlace: O(n^2) worst case if rotation is used naively.
 * 
 * SPACE COMPLEXITY:
 *   - O(1) extra space (in-place rotation only uses a few variables).
 */

public class ReversePairsHard {

    /**
     * In-place merge sort variant (less memory but more complex)
     *
     * NOTE:
     * - Currently, countPairsInPlace(...) is a placeholder that returns 0.
     * - The main correct reverse-pairs solution is implemented in
     *   ReversePairsLeetHard (using a more standard merge-sort with temp array).
     */
    private static int mergeSortInPlace(int[] arr, int low, int high) {
        if (low >= high) return 0;
        
        int mid = low + (high - low) / 2;
        int count = mergeSortInPlace(arr, low, mid) +
                    mergeSortInPlace(arr, mid + 1, high) +
                    countPairsInPlace(arr, low, mid, high);
        
        // In-place merge using rotation
        mergeInPlace(arr, low, mid, high);
        return count;
    }

    /**
     * Placeholder for counting reverse pairs in-place within [low, high].
     * Currently returns 0 so the helper compiles; main solutions live in ReversePairsLeetHard.
     */
    private static int countPairsInPlace(int[] arr, int low, int mid, int high) {
        return 0;
    }

    /**
     * In-place merge using rotation (block swap)
     */
    private static void mergeInPlace(int[] arr, int low, int mid, int high) {
        int i = low, j = mid + 1;
        
        while (i <= mid && j <= high) {
            if (arr[i] <= arr[j]) {
                i++;
            } else {
                // Rotate arr[i..j-1] to the right by 1
                int temp = arr[j];
                for (int k = j; k > i; k--) {
                    arr[k] = arr[k - 1];
                }
                arr[i] = temp;
                
                i++;
                mid++;
                j++;
            }
        }
    }

    /**
     * Demo main to show how mergeSortInPlace behaves on a small array.
     * This does NOT compute the correct reverse-pair count yet (count=0),
     * but illustrates how the in-place merge transforms the array.
     */
    public static void main(String[] args) {
        int[] nums = {1, 3, 2, 3, 1};
        System.out.println("Original array: " + java.util.Arrays.toString(nums));

        int count = mergeSortInPlace(nums, 0, nums.length - 1);
        System.out.println("Array after in-place merge sort helper: " + java.util.Arrays.toString(nums));
        System.out.println("Reverse pairs counted by this helper (placeholder): " + count);
        System.out.println("Note: For correct reverse-pair count, see ReversePairsLeetHard.");
    }
}
