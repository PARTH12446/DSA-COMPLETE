// Problem: Subarrays with K Different Integers (LeetCode 992)
// Technique: Count subarrays with at most K distinct integers, then use 
//            atMost(K) - atMost(K-1) to get exactly K distinct integers
// Time Complexity: O(n) - each element visited at most twice by left and right pointers
// Space Complexity: O(n) - frequency array of size n+1

public class SubArrWithKDifferent {
    
    // Main function: returns number of subarrays with EXACTLY K distinct integers
    public int subarraysWithKDistinct(int[] nums, int k) {
        // KEY INSIGHT:
        // Number of subarrays with exactly K distinct =
        // Number of subarrays with at most K distinct -
        // Number of subarrays with at most (K-1) distinct
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    // Helper function: returns number of subarrays with AT MOST K distinct integers
    private int atMost(int[] nums, int k) {
        // Edge case: if k < 0, no subarrays possible
        if (k < 0) return 0;
        
        // Frequency array: index = number value, value = count in current window
        // Size is nums.length + 1 because number values are in range [1, nums.length]
        int[] freq = new int[nums.length + 1];
        
        int distinct = 0;   // Count of distinct integers in current window
        int left = 0;       // Left pointer of sliding window
        int res = 0;        // Result: count of valid subarrays
        
        // Expand window by moving right pointer
        for (int right = 0; right < nums.length; right++) {
            // Add nums[right] to window
            // If it's the first occurrence in current window, increment distinct count
            if (freq[nums[right]] == 0) distinct++;
            freq[nums[right]]++;
            
            // If window has more than K distinct integers, shrink from left
            while (distinct > k) {
                freq[nums[left]]--;
                // If count becomes 0, we've removed all occurrences of this integer from window
                if (freq[nums[left]] == 0) distinct--;
                left++;
            }
            
            // IMPORTANT: Count all subarrays ending at 'right' that have at most K distinct
            // For a fixed right endpoint, subarrays [left, right], [left+1, right], ..., [right, right]
            // all have at most K distinct integers
            // Number of such subarrays = right - left + 1
            res += right - left + 1;
        }
        
        return res;
    }

    public static void main(String[] args) {
        SubArrWithKDifferent solver = new SubArrWithKDifferent();
        
        // Example from LeetCode
        int[] nums = {1, 2, 1, 2, 3};
        int k = 2;
        int ans = solver.subarraysWithKDistinct(nums, k);
        System.out.println("Number of subarrays with exactly " + k + " distinct integers = " + ans);
        // Expected: 7
        
        /*
        EXPLANATION for nums = [1,2,1,2,3], k = 2:
        
        Subarrays with exactly 2 distinct integers:
        1. [1,2]         -> distinct: {1,2}
        2. [2,1]         -> distinct: {1,2}
        3. [1,2]         -> distinct: {1,2} (starting at index 2)
        4. [2,3]         -> distinct: {2,3}
        5. [1,2,1]       -> distinct: {1,2}
        6. [2,1,2]       -> distinct: {1,2}
        7. [1,2,1,2]     -> distinct: {1,2}
        Total = 7
        
        Using atMost transformation:
        atMost(2) = 12   (all subarrays except those containing all 3 distinct numbers)
        atMost(1) = 5    (subarrays with only 1 distinct number)
        Exactly 2 = 12 - 5 = 7
        */
        
        // Additional test cases:
        // Test case 1: [1,2,1,3,4], k = 3
        // Test case 2: [1,1,1,1,1], k = 1
        // Test case 3: [1,2,3,4,5], k = 1
    }
}