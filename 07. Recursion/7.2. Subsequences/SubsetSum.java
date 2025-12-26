import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class to generate all possible subset sums from a given array.
 * A subset sum is the sum of elements in any possible subset (including empty subset).
 * This problem is fundamental in combinatorial optimization and has applications in:
 * - Partition problems
 * - Knapsack problems
 * - Resource allocation
 */
public class SubsetSum {

    /**
     * Main method to generate all possible subset sums.
     * 
     * @param nums Input array of integers (can include negative, zero, positive)
     * @return Sorted list of all possible subset sums
     * 
     * Time Complexity: O(2^n) for generating sums + O(2^n log 2^n) for sorting
     *                 = O(n * 2^n) in total (since log(2^n) = n)
     * Space Complexity: O(2^n) for storing results + O(n) recursion stack
     * 
     * Example: nums = [1, 2, 3]
     * Subsets: [], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]
     * Sums: 0, 1, 2, 3, 3, 4, 5, 6 → Sorted: [0, 1, 2, 3, 3, 4, 5, 6]
     */
    public static List<Integer> subsetSums(int[] nums) {
        List<Integer> res = new ArrayList<>();
        // Start backtracking from index 0 with initial sum 0 (empty subset)
        backtrack(0, nums, 0, res);
        // Sort results for better readability and further processing
        Collections.sort(res);
        return res;
    }

    /**
     * Recursive backtracking function to generate all subset sums.
     * Uses DFS to explore the complete binary decision tree.
     * 
     * Decision Tree Visualization for nums = [a, b, c]:
     * 
     *                Start (sum=0)
     *               /          \
     *         Include a      Exclude a
     *         (sum=a)        (sum=0)
     *         /    \         /    \
     *    Include Exclude Include Exclude
     *      b       b        b       b
     *    ... and so on ...
     * 
     * Each leaf node represents the sum of one particular subset.
     * 
     * @param idx  Current index in the array (which element we're deciding on)
     * @param nums Original input array
     * @param sum  Current accumulated sum (sum of chosen elements so far)
     * @param res  Result list to store all subset sums
     */
    private static void backtrack(int idx, int[] nums, int sum, List<Integer> res) {
        // Base case: processed all elements
        if (idx == nums.length) {
            res.add(sum);  // Add the sum of current subset
            return;
        }

        // ========== OPTION 1: Include nums[idx] ==========
        // Add current element to the subset sum
        backtrack(idx + 1, nums, sum + nums[idx], res);

        // ========== OPTION 2: Exclude nums[idx] ==========
        // Skip current element, sum remains unchanged
        backtrack(idx + 1, nums, sum, res);
        
        // Note: No explicit backtracking needed here since `sum` is passed by value
        // In Java, primitive types (int) are passed by value, so changes aren't reflected back
    }

    /**
     * Alternative iterative implementation using bit manipulation.
     * Each subset corresponds to a binary mask where bit i = 1 means nums[i] is included.
     * 
     * @param nums Input array
     * @return Sorted list of subset sums
     */
    public static List<Integer> subsetSumsIterative(int[] nums) {
        List<Integer> res = new ArrayList<>();
        int n = nums.length;
        int totalSubsets = 1 << n;  // 2^n
        
        // Generate all 2^n subsets
        for (int mask = 0; mask < totalSubsets; mask++) {
            int currentSum = 0;
            
            // Calculate sum for current subset
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    currentSum += nums[i];
                }
            }
            res.add(currentSum);
        }
        
        Collections.sort(res);
        return res;
    }

    /**
     * Optimized version using meet-in-the-middle for large arrays (n ~ 40).
     * Split array into two halves, generate all subset sums for each half,
     * then combine results. Reduces time from O(2^n) to O(2^(n/2)).
     * 
     * @param nums Input array
     * @return Sorted list of subset sums
     */
    public static List<Integer> subsetSumsMeetInMiddle(int[] nums) {
        int n = nums.length;
        int mid = n / 2;
        
        // Generate all subset sums for first half
        List<Integer> firstHalfSums = generateHalfSums(nums, 0, mid);
        
        // Generate all subset sums for second half
        List<Integer> secondHalfSums = generateHalfSums(nums, mid, n);
        
        // Combine results: sum of any subset = sum from first half + sum from second half
        List<Integer> res = new ArrayList<>();
        for (int sum1 : firstHalfSums) {
            for (int sum2 : secondHalfSums) {
                res.add(sum1 + sum2);
            }
        }
        
        Collections.sort(res);
        return res;
    }
    
    private static List<Integer> generateHalfSums(int[] nums, int start, int end) {
        List<Integer> sums = new ArrayList<>();
        int length = end - start;
        int totalSubsets = 1 << length;
        
        for (int mask = 0; mask < totalSubsets; mask++) {
            int sum = 0;
            for (int i = 0; i < length; i++) {
                if ((mask & (1 << i)) != 0) {
                    sum += nums[start + i];
                }
            }
            sums.add(sum);
        }
        return sums;
    }

    /**
     * Test driver with comprehensive examples.
     */
    public static void main(String[] args) {
        System.out.println("=== SUBSET SUMS DEMO ===\n");
        
        // Test Case 1: Basic example
        int[] nums1 = {1, 2, 3};
        List<Integer> sums1 = subsetSums(nums1);
        System.out.println("Input: [1, 2, 3]");
        System.out.println("Number of subset sums: " + sums1.size());
        System.out.println("Expected: 2^3 = 8 sums");
        System.out.println("Subset sums: " + sums1);
        System.out.println("Note: Sum 3 appears twice from subsets [3] and [1,2]\n");
        
        // Test Case 2: With negative numbers
        int[] nums2 = {-1, 1};
        List<Integer> sums2 = subsetSums(nums2);
        System.out.println("Input: [-1, 1]");
        System.out.println("Number of subset sums: " + sums2.size());
        System.out.println("Subset sums: " + sums2);
        System.out.println("Explanation:");
        System.out.println("  [] = 0");
        System.out.println("  [-1] = -1");
        System.out.println("  [1] = 1");
        System.out.println("  [-1, 1] = 0 (duplicate of empty subset sum)\n");
        
        // Test Case 3: All zeros
        int[] nums3 = {0, 0, 0};
        List<Integer> sums3 = subsetSums(nums3);
        System.out.println("Input: [0, 0, 0]");
        System.out.println("Number of subset sums: " + sums3.size());
        System.out.println("Subset sums: " + sums3);
        System.out.println("Note: All sums are 0, but there are 8 entries (all duplicates)\n");
        
        // Test Case 4: Compare recursive vs iterative
        int[] nums4 = {5, -2, 3};
        List<Integer> sums4Recursive = subsetSums(nums4);
        List<Integer> sums4Iterative = subsetSumsIterative(nums4);
        System.out.println("Input: [5, -2, 3]");
        System.out.println("Recursive result: " + sums4Recursive);
        System.out.println("Iterative result: " + sums4Iterative);
        System.out.println("Match: " + sums4Recursive.equals(sums4Iterative) + "\n");
        
        // Test Case 5: Empty array
        int[] nums5 = {};
        List<Integer> sums5 = subsetSums(nums5);
        System.out.println("Input: []");
        System.out.println("Number of subset sums: " + sums5.size());
        System.out.println("Subset sums: " + sums5);
        System.out.println("Expected: [0] (only empty subset)\n");
        
        // Test Case 6: Meet-in-the-middle for larger input
        int[] nums6 = {1, 2, 3, 4};
        List<Integer> sums6Recursive = subsetSums(nums6);
        List<Integer> sums6MeetMiddle = subsetSumsMeetInMiddle(nums6);
        Collections.sort(sums6MeetMiddle);
        System.out.println("Input: [1, 2, 3, 4]");
        System.out.println("Recursive (2^4 = 16 sums): " + sums6Recursive.size() + " sums");
        System.out.println("Meet-in-middle result size: " + sums6MeetMiddle.size());
        System.out.println("Match: " + sums6Recursive.equals(sums6MeetMiddle));
        
        // Complexity comparison
        System.out.println("\n=== COMPLEXITY ANALYSIS ===");
        System.out.println("For n = 20:");
        System.out.println("  Recursive/Iterative: 2^20 = 1,048,576 operations");
        System.out.println("  Meet-in-middle: 2^10 + 2^10 = 2,048 operations");
    }
}