// Problem: LeetCode <ID>. <Title>
// Problem: Longest Subarray with Sum K (non-negative elements)
// Optimal Approach: Sliding Window / Two Pointers

public class LongestSubArr {

    /**
     * OPTIMAL SOLUTION for non-negative arrays
     * Time Complexity: O(n) - Each element processed once
     * Space Complexity: O(1) - Only pointers and variables
     * 
     * Steps (Sliding Window):
     * 1. Initialize left=0, right=0, sum=0, maxLen=0
     * 2. Expand right pointer: add a[right] to sum
     * 3. While sum > K: shrink from left (subtract a[left])
     * 4. If sum == K: update maxLen with window size
     * 5. Repeat until right reaches end
     */
    public static int longestSubarrayWithSumK(int[] a, int k) {
        int left = 0, right = 0;
        int sum = 0;
        int maxLen = 0;
        int n = a.length;
        
        while (right < n) {
            // Expand window
            sum += a[right];
            
            // Shrink window if sum exceeds K
            while (left <= right && sum > k) {
                sum -= a[left];
                left++;
            }
            
            // Check if sum equals K
            if (sum == k) {
                maxLen = Math.max(maxLen, right - left + 1);
            }
            
            // Move right pointer
            right++;
        }
        
        return maxLen;
    }

    /**
     * Example Walkthrough:
     * Input: a = [1, 2, 3, 1, 1, 1, 1], K = 6
     * 
     * right=0: sum=1, sum<6
     * right=1: sum=3, sum<6  
     * right=2: sum=6, sum==6 → maxLen=3
     * right=3: sum=7, sum>6 → shrink: left=1, sum=5
     * right=4: sum=6, sum==6 → maxLen=4
     * right=5: sum=7, sum>6 → shrink: left=2, sum=6 → maxLen=4
     * right=6: sum=7, sum>6 → shrink: left=3, sum=6 → maxLen=4
     * 
     * Result: 4 (subarray [2,3,1,1] or [3,1,1,1])
     */

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 1, 1, 1, 1};
        int k = 6;
        int ans = longestSubarrayWithSumK(a, k);
        System.out.println("Longest subarray with sum " + k + " = " + ans); // 4
        
        // Test cases
        int[] test1 = {10, 5, 2, 7, 1, 9};
        System.out.println("Test 1, K=15: " + longestSubarrayWithSumK(test1, 15)); // 4
        
        int[] test2 = {1, 1, 1};
        System.out.println("Test 2, K=2: " + longestSubarrayWithSumK(test2, 2)); // 2
        
        int[] test3 = {2, 3, 5};
        System.out.println("Test 3, K=5: " + longestSubarrayWithSumK(test3, 5)); // 1
        
        int[] test4 = {};
        System.out.println("Empty array: " + longestSubarrayWithSumK(test4, 5)); // 0
    }
}
