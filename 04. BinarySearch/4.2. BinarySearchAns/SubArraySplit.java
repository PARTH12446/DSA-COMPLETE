/*
 * PROBLEM: Largest Subarray Sum Minimized
 * 
 * Given an array 'a' of size 'n' and an integer 'k',
 * split the array into 'k' contiguous subarrays such that
 * the largest sum among these subarrays is minimized.
 * 
 * APPROACH: Binary Search on Answer
 * 
 * Why Binary Search works:
 * 1. If we fix a maximum subarray sum (cap), we can count how many
 *    subarrays are needed so that each subarray sum ≤ cap.
 * 2. If required subarrays > k → cap is too small (increase it).
 * 3. If required subarrays ≤ k → cap might be too big or optimal
 *    (try smaller cap).
 * 
 * BINARY SEARCH BOUNDS:
 * - low = max_element(arr)  → Min possible largest sum
 * - high = sum(arr)         → Max possible (entire array as one subarray)
 * 
 * HELPER FUNCTION: subarrCheck(arr, cap)
 * - Given a maximum allowed sum 'cap', count how many subarrays
 *   are needed so that no subarray sum exceeds 'cap'.
 * - Greedy approach: Add elements to current subarray until adding
 *   the next element would exceed 'cap', then start a new subarray.
 * 
 * TIME COMPLEXITY: O(n * log(sum(arr) - max(arr)))
 * - Each subarrCheck is O(n)
 * - Binary search runs O(log(range)) times
 * 
 * SPACE COMPLEXITY: O(1)
 * 
 * EXAMPLE:
 * arr = [1, 2, 3, 4, 5], k = 3
 * low = 5, high = 15
 * 
 * Binary search:
 * mid=10 → subarrays needed=2 (≤3) → try smaller
 * mid=7 → subarrays needed=3 (≤3) → try smaller  
 * mid=6 → subarrays needed=3 (≤3) → try smaller
 * mid=5 → subarrays needed=5 (>3) → too small
 * Result: 6 is the minimal maximum sum
 * Split: [1,2,3], [4], [5] → sums=6,4,5 → largest=6
 */

public class SubArraySplit {

    /**
     * Helper function to count how many subarrays are needed
     * when no subarray sum can exceed 'cap'
     * 
     * @param arr Input array
     * @param cap Maximum allowed sum per subarray
     * @return Number of subarrays needed
     * 
     * Example: arr=[1,2,3,4,5], cap=6
     * Process: [1+2+3] (sum=6), [4] (can't add 5 → new), [5]
     * Returns: 3 subarrays needed
     */
    private static int subarrCheck(int[] arr, int cap) {
        int subarraysCount = 1;  // Start with first subarray
        int currentSum = 0;      // Sum of current subarray
        
        for (int value : arr) {
            // If adding this value exceeds cap, start new subarray
            if (currentSum + value > cap) {
                subarraysCount++;    // Increment subarray count
                currentSum = value;  // Start new subarray with current value
            } else {
                // Add to current subarray
                currentSum += value;
            }
        }
        return subarraysCount;
    }

    /**
     * Main function to find minimized largest subarray sum
     * 
     * @param a Input array
     * @param k Number of required subarrays
     * @return Minimized maximum subarray sum
     * 
     * Special case: If k > array length, impossible → return -1
     * 
     * Binary Search logic:
     * - We're looking for smallest 'cap' where subarrCheck(cap) ≤ k
     * - Standard binary search on answer pattern
     */
    public static int largestSubarraySumMinimized(int[] a, int k) {
        // Edge case: More subarrays than elements
        if (k > a.length) return -1;
        
        // Initialize binary search bounds
        int low = 0;    // Will be set to max element
        int high = 0;   // Will be set to total sum
        
        for (int value : a) {
            low = Math.max(low, value);  // Max element
            high += value;               // Total sum
        }
        
        // Binary search for minimal maximum sum
        while (low <= high) {
            int mid = low + (high - low) / 2;  // Current cap to test
            int subarraysNeeded = subarrCheck(a, mid);
            
            if (subarraysNeeded > k) {
                // Need more than k subarrays → cap too small
                low = mid + 1;
            } else {
                // Need ≤ k subarrays → cap might be too large or optimal
                high = mid - 1;
            }
        }
        
        // 'low' contains the minimal maximum sum
        return low;
    }
    
    // Test examples
    public static void main(String[] args) {
        // Example 1
        int[] arr1 = {1, 2, 3, 4, 5};
        int k1 = 3;
        System.out.println("Test 1: " + largestSubarraySumMinimized(arr1, k1)); // Expected: 6
        
        // Example 2  
        int[] arr2 = {10, 20, 30, 40};
        int k2 = 2;
        System.out.println("Test 2: " + largestSubarraySumMinimized(arr2, k2)); // Expected: 60
        
        // Edge case
        int[] arr3 = {1, 1, 1, 1};
        int k3 = 5;
        System.out.println("Test 3: " + largestSubarraySumMinimized(arr3, k3)); // Expected: -1
    }
}