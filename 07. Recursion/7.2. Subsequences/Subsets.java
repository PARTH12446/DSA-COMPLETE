import java.util.ArrayList;
import java.util.List;

/**
 * Class to generate all possible subsets (power set) of a given array.
 * A subset is any combination of elements from the array, including the empty set and the full set.
 * This implementation uses backtracking/DFS to explore all possible combinations.
 */
public class Subsets {

    /**
     * Main method to generate all subsets of the input array.
     * 
     * @param nums Input array of integers
     * @return List of all subsets (each subset is a List<Integer>)
     * 
     * Time Complexity: O(n * 2^n) where n is the length of nums
     * - 2^n subsets total
     * - O(n) time to copy each subset to result
     * Space Complexity: O(n) for recursion stack + O(n * 2^n) for output storage
     */
    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // Start backtracking from index 0 with empty current subset
        backtrack(0, nums, new ArrayList<>(), res);
        return res;
    }

    /**
     * Recursive backtracking function to generate all subsets.
     * Uses depth-first search to explore the decision tree.
     * 
     * Decision Tree Visualization for nums = [1, 2, 3]:
     * 
     *              Start
     *              /    \
     *            Not 1  Take 1
     *            /  \    /  \
     *         Not 2  T2 Not2 T2
     *         / \   / \ / \  / \
     *       N3 T3 N3 T3 ... etc.
     * 
     * Each path from root to leaf represents one subset.
     * 
     * @param idx Current index in nums array to make decision for
     * @param nums Original input array
     * @param curr Current subset being built
     * @param res Result list containing all subsets
     */
    private static void backtrack(int idx, int[] nums, List<Integer> curr, List<List<Integer>> res) {
        // Base case: reached end of array
        if (idx == nums.length) {
            // Add a copy of current subset to results
            res.add(new ArrayList<>(curr));  // Must create new ArrayList to avoid reference issues
            return;
        }

        // ========== OPTION 1: Do NOT include nums[idx] ==========
        // Decision: Skip current element
        // Move to next index without modifying current subset
        backtrack(idx + 1, nums, curr, res);

        // ========== OPTION 2: Include nums[idx] ==========
        // Decision: Take current element
        curr.add(nums[idx]);                    // 1. CHOOSE - add element to current subset
        backtrack(idx + 1, nums, curr, res);    // 2. EXPLORE - recurse with element included
        curr.remove(curr.size() - 1);           // 3. UNCHOOSE - backtrack (remove last element)
        
        // Note: The order of operations (take/not take) can be swapped
        // This implementation processes "not take" first, then "take"
    }

    /**
     * Alternative implementation with different traversal order.
     * This version adds subsets as we go, not just at leaf nodes.
     * 
     * @param nums Input array
     * @return All subsets
     */
    public static List<List<Integer>> subsetsAlt(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        backtrackAlt(0, nums, new ArrayList<>(), res);
        return res;
    }

    private static void backtrackAlt(int start, int[] nums, List<Integer> curr, List<List<Integer>> res) {
        // Add the current subset at every step (not just at leaves)
        res.add(new ArrayList<>(curr));
        
        // Explore further elements to add to the subset
        for (int i = start; i < nums.length; i++) {
            curr.add(nums[i]);                    // Include nums[i]
            backtrackAlt(i + 1, nums, curr, res); // Recurse with next starting index
            curr.remove(curr.size() - 1);         // Backtrack
        }
    }

    /**
     * Test driver with multiple examples.
     */
    public static void main(String[] args) {
        System.out.println("=== SUBSETS GENERATION DEMO ===\n");
        
        // Test Case 1: Basic example
        int[] nums1 = {1, 2, 3};
        List<List<Integer>> subsets1 = subsets(nums1);
        System.out.println("Input: [1, 2, 3]");
        System.out.println("Number of subsets: " + subsets1.size());
        System.out.println("Subsets: " + subsets1);
        System.out.println("Expected count: 2^3 = 8\n");
        
        // Test Case 2: Single element
        int[] nums2 = {5};
        List<List<Integer>> subsets2 = subsets(nums2);
        System.out.println("Input: [5]");
        System.out.println("Number of subsets: " + subsets2.size());
        System.out.println("Subsets: " + subsets2);
        System.out.println("Expected: [[], [5]]\n");
        
        // Test Case 3: Empty array
        int[] nums3 = {};
        List<List<Integer>> subsets3 = subsets(nums3);
        System.out.println("Input: []");
        System.out.println("Number of subsets: " + subsets3.size());
        System.out.println("Subsets: " + subsets3);
        System.out.println("Expected: [[]] (only the empty set)\n");
        
        // Test Case 4: Compare with alternative implementation
        int[] nums4 = {1, 2};
        List<List<Integer>> subsets4 = subsets(nums4);
        List<List<Integer>> subsets4Alt = subsetsAlt(nums4);
        System.out.println("Input: [1, 2]");
        System.out.println("Original method: " + subsets4);
        System.out.println("Alternative method: " + subsets4Alt);
        System.out.println("Both should produce: [[], [2], [1], [1, 2]]");
        System.out.println("Order may differ between implementations.\n");
        
        // Visualization of decision tree for [1, 2]
        System.out.println("Decision Tree for [1, 2]:");
        System.out.println("        Start");
        System.out.println("       /    \\");
        System.out.println("   Not 1   Take 1");
        System.out.println("   /  \\      /  \\");
        System.out.println("Not2 Take2 Not2 Take2");
        System.out.println(" |    |     |    |");
        System.out.println(" []  [2]   [1]  [1,2]");
    }
}