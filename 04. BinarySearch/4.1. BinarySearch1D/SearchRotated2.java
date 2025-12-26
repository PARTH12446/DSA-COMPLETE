/**
 * Problem: Search in Rotated Sorted Array II (with duplicates)
 * 
 * Challenge: 
 * - Array is sorted but rotated at some pivot (unknown)
 * - Array MAY contain DUPLICATES
 * - Return true if target exists, false otherwise
 * 
 * Example: 
 * Input: arr = [1,1,1,1,2,1,1], target = 2
 * Output: true
 * 
 * Difficulty: Duplicates prevent us from definitively knowing which half is sorted
 * when arr[low] == arr[mid] == arr[high]
 * 
 * Approach: Modified Binary Search with duplicate handling
 * 
 * Time Complexity:
 * - Best/Average: O(log n) when few duplicates
 * - Worst: O(n) when many duplicates (degenerates to linear search)
 * 
 * Space Complexity: O(1)
 */

public class SearchRotated2 {

    /**
     * Searches for target k in rotated sorted array with duplicates
     * 
     * @param arr - Rotated sorted array (may contain duplicates)
     * @param k - Target value to search for
     * @return true if k exists in arr, false otherwise
     * 
     * Algorithm Steps:
     * 1. Standard binary search while low <= high
     * 2. If mid element equals target → return true
     * 3. Handle duplicates: when arr[low] == arr[mid] == arr[high]
     *    - We cannot determine which half is sorted
     *    - Shrink search space from both ends
     * 4. Determine which half is sorted:
     *    a) If arr[low] <= arr[mid] → left half [low..mid] is sorted
     *        - Check if target lies in sorted left half
     *        - Adjust search range accordingly
     *    b) Else → right half [mid..high] is sorted
     *        - Check if target lies in sorted right half
     *        - Adjust search range accordingly
     * 
     * Why duplicate handling is needed:
     * Example: arr = [1,1,1,1,2,1,1], target = 2
     * Initially: low=0, high=6, mid=3
     * arr[low]=1, arr[mid]=1, arr[high]=1 → all equal!
     * We cannot tell if:
     *   - Left [1,1,1,1] is sorted and target might be there
     *   - Or right [1,2,1,1] contains the target
     * Solution: low++ (to 1), high-- (to 5) and continue
     */
    public static boolean searchInARotatedSortedArrayII(int[] arr, int k) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            // Calculate mid (prevents integer overflow)
            // Using low + (high - low)/2 instead of (low + high)/2
            int mid = low + (high - low) / 2;

            // CASE 1: Found target at mid
            if (arr[mid] == k) {
                return true;
            }

            // ===========================================
            // CASE 2: Handle duplicates (CRITICAL STEP)
            // ===========================================
            // When all three points are equal, we lose binary search property
            // We cannot determine which half is sorted
            if (arr[low] == arr[mid] && arr[mid] == arr[high]) {
                // Example: [2,2,2,2,2] or [1,1,1,1,2,1,1]
                // Shrink search space from both ends
                low++;    // Move left boundary right
                high--;   // Move right boundary left
                continue; // Recalculate mid with new boundaries
            }

            // ===========================================
            // CASE 3: Left half [low..mid] is sorted
            // ===========================================
            // arr[low] <= arr[mid] indicates left half is sorted
            // Even with rotation, if left part is sorted, this holds
            else if (arr[low] <= arr[mid]) {
                // Check if target is within the sorted left half
                // Need to check both boundaries: arr[low] <= k < arr[mid]
                if (arr[low] <= k && k < arr[mid]) {
                    // Target is in sorted left half, search there
                    high = mid - 1;
                } else {
                    // Target is not in sorted left half, 
                    // so it must be in the right half (which may be unsorted)
                    low = mid + 1;
                }
            }
            
            // ===========================================
            // CASE 4: Right half [mid..high] is sorted
            // ===========================================
            // If left half is not sorted, then right half must be sorted
            // (because at least one half must be sorted in rotated array)
            else {
                // Check if target is within the sorted right half
                // Need to check both boundaries: arr[mid] < k <= arr[high]
                if (arr[mid] < k && k <= arr[high]) {
                    // Target is in sorted right half, search there
                    low = mid + 1;
                } else {
                    // Target is not in sorted right half,
                    // so it must be in the left half (which is unsorted)
                    high = mid - 1;
                }
            }
        }

        // Target not found
        return false;
    }

    // ===========================================
    // TEST CASES with detailed comments
    // ===========================================
    public static void main(String[] args) {
        System.out.println("=== Testing Search in Rotated Sorted Array II ===\n");

        // Test Case 1: Standard rotated array without duplicates
        // Expected: O(log n) complexity
        int[] test1 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Test 1: [4,5,6,7,0,1,2]");
        System.out.println("Search 0: " + searchInARotatedSortedArrayII(test1, 0) + " (expected: true)");
        System.out.println("Search 3: " + searchInARotatedSortedArrayII(test1, 3) + " (expected: false)");
        System.out.println("Search 7: " + searchInARotatedSortedArrayII(test1, 7) + " (expected: true)\n");

        // Test Case 2: Rotated array with duplicates
        // Demonstrates the need for duplicate handling
        int[] test2 = {1, 1, 1, 1, 2, 1, 1};
        System.out.println("Test 2: [1,1,1,1,2,1,1] (tricky case)");
        System.out.println("Search 2: " + searchInARotatedSortedArrayII(test2, 2) + " (expected: true)");
        System.out.println("Search 0: " + searchInARotatedSortedArrayII(test2, 0) + " (expected: false)\n");

        // Test Case 3: All elements are the same
        // Worst case: O(n) complexity
        int[] test3 = {2, 2, 2, 2, 2, 2, 2};
        System.out.println("Test 3: [2,2,2,2,2,2,2] (all duplicates)");
        System.out.println("Search 2: " + searchInARotatedSortedArrayII(test3, 2) + " (expected: true)");
        System.out.println("Search 3: " + searchInARotatedSortedArrayII(test3, 3) + " (expected: false)\n");

        // Test Case 4: Rotated at first element
        int[] test4 = {3, 1, 2, 3, 3, 3, 3};
        System.out.println("Test 4: [3,1,2,3,3,3,3] (rotated at start)");
        System.out.println("Search 1: " + searchInARotatedSortedArrayII(test4, 1) + " (expected: true)");
        System.out.println("Search 2: " + searchInARotatedSortedArrayII(test4, 2) + " (expected: true)\n");

        // Test Case 5: Edge cases
        System.out.println("Test 5: Edge cases");
        int[] test5 = {}; // Empty array
        System.out.println("Search in [] for 1: " + searchInARotatedSortedArrayII(test5, 1) + " (expected: false)");
        
        int[] test6 = {1}; // Single element
        System.out.println("Search in [1] for 1: " + searchInARotatedSortedArrayII(test6, 1) + " (expected: true)");
        System.out.println("Search in [1] for 2: " + searchInARotatedSortedArrayII(test6, 2) + " (expected: false)\n");

        // Test Case 6: Not rotated at all (fully sorted)
        int[] test7 = {1, 2, 3, 4, 5};
        System.out.println("Test 6: [1,2,3,4,5] (not rotated)");
        System.out.println("Search 3: " + searchInARotatedSortedArrayII(test7, 3) + " (expected: true)");
        System.out.println("Search 6: " + searchInARotatedSortedArrayII(test7, 6) + " (expected: false)");
    }
}