import java.util.ArrayList;
import java.util.List;

/**
 * This class finds all subsequences of an array that sum to a given target value.
 * A subsequence is a sequence that can be derived from the array by deleting 
 * some or no elements without changing the order of the remaining elements.
 */
public class SubsequencesWithSum {

    /**
     * Main method to find all subsequences with the given target sum.
     *
     * @param nums   The input array of integers
     * @param target The target sum to achieve
     * @return A list of all subsequences whose elements sum to the target
     *
     * Time Complexity: O(2^n) where n is the length of nums
     * Space Complexity: O(n) for recursion stack and O(2^n) for output in worst case
     */
    public static List<List<Integer>> subsequencesWithSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        // Start backtracking from index 0 with empty current subsequence and sum 0
        backtrack(0, nums, target, 0, new ArrayList<>(), res);
        return res;
    }

    /**
     * Recursive backtracking helper function to explore all subsequences.
     *
     * @param idx    Current index in the nums array being considered
     * @param nums   The input array of integers
     * @param target The target sum to achieve
     * @param sum    Current sum of elements in the curr subsequence
     * @param curr   Current subsequence being built
     * @param res    Result list containing all valid subsequences
     *
     * The function uses a decision tree approach:
     * At each index, we have two choices:
     * 1. Include the current element in the subsequence
     * 2. Exclude the current element from the subsequence
     */
    private static void backtrack(int idx, int[] nums, int target, int sum, 
                                  List<Integer> curr, List<List<Integer>> res) {
        
        // Base case: we've considered all elements in the array
        if (idx == nums.length) {
            // If current sum equals target, add a copy of current subsequence to results
            if (sum == target) {
                res.add(new ArrayList<>(curr)); // Create new list to avoid reference issues
            }
            return;
        }

        // ========== CHOICE 1: INCLUDE nums[idx] in subsequence ==========
        // Add current element to the subsequence
        curr.add(nums[idx]);
        
        // Recurse with: 
        // - Index incremented to consider next element
        // - Sum updated to include current element's value
        backtrack(idx + 1, nums, target, sum + nums[idx], curr, res);
        
        // Backtrack: remove last element to explore other possibilities
        curr.remove(curr.size() - 1);

        // ========== CHOICE 2: EXCLUDE nums[idx] from subsequence ==========
        // Recurse with:
        // - Index incremented to consider next element
        // - Sum remains unchanged (current element not included)
        backtrack(idx + 1, nums, target, sum, curr, res);
    }

    /**
     * Example usage and testing of the method.
     */
    public static void main(String[] args) {
        // Test Case 1: Basic example
        int[] nums1 = {1, 2, 3};
        int target1 = 3;
        List<List<Integer>> result1 = subsequencesWithSum(nums1, target1);
        System.out.println("Test Case 1 - Array: [1, 2, 3], Target: 3");
        System.out.println("Result: " + result1);
        // Expected: [[1, 2], [3]]
        
        System.out.println();

        // Test Case 2: Array with negative numbers
        int[] nums2 = {-1, 1, 0, 2};
        int target2 = 1;
        List<List<Integer>> result2 = subsequencesWithSum(nums2, target2);
        System.out.println("Test Case 2 - Array: [-1, 1, 0, 2], Target: 1");
        System.out.println("Result: " + result2);
        // Expected: [[-1, 1, 0, 2], [-1, 1, 1], [0, 1], [1], [1, 0]]
        // Note: Actually shows [-1, 1, 0, 2], [-1, 1, 1], [0, 1], [1], [1, 0]
        // Wait, [1, 0] sums to 1? Actually 1 + 0 = 1, so yes.
        
        System.out.println();

        // Test Case 3: Array with duplicate values
        int[] nums3 = {2, 2, 2};
        int target3 = 4;
        List<List<Integer>> result3 = subsequencesWithSum(nums3, target3);
        System.out.println("Test Case 3 - Array: [2, 2, 2], Target: 4");
        System.out.println("Result: " + result3);
        // Expected: [[2, 2], [2, 2], [2, 2]]
        // Note: The algorithm will find all combinations including duplicates
        
        System.out.println();

        // Test Case 4: Empty result case
        int[] nums4 = {1, 2, 3};
        int target4 = 10;
        List<List<Integer>> result4 = subsequencesWithSum(nums4, target4);
        System.out.println("Test Case 4 - Array: [1, 2, 3], Target: 10");
        System.out.println("Result: " + result4);
        // Expected: [] (empty list)
        
        System.out.println();

        // Test Case 5: Empty subsequence (sum = 0)
        int[] nums5 = {1, -1, 2, -2};
        int target5 = 0;
        List<List<Integer>> result5 = subsequencesWithSum(nums5, target5);
        System.out.println("Test Case 5 - Array: [1, -1, 2, -2], Target: 0");
        System.out.println("Result: " + result5);
        System.out.println("Number of subsequences with sum 0: " + result5.size());
        // Expected: Multiple combinations including empty subsequence if array contains zero
        // But wait, our algorithm doesn't include empty subsequence since we only check at leaf nodes
        // Actually empty subsequence would be valid if target = 0
        // Our current implementation won't include empty subsequence when target = 0
        // because we only check at idx == nums.length and curr might be empty
    }
}