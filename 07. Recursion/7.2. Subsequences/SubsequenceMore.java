 /**
 * Class for generating all subsequences of an array
 * 
 * Problem: Given an array of integers, generate all possible subsequences.
 * A subsequence is a sequence that can be derived from another sequence
 * by deleting some or no elements without changing the order of the 
 * remaining elements.
 * 
 * Key Differences from subsets:
 * - Subsequence maintains the original order
 * - All subsequences are subsets, but not all subsets are subsequences
 *   (since subsequences maintain order)
 * 
 * Example:
 * Input: [1, 2, 3]
 * Output: [[], [1], [2], [3], [1,2], [1,3], [2,3], [1,2,3]]
 * Note: [2,1] is NOT a subsequence (order changed)
 * 
 * Total subsequences: 2^n where n = array length
 * 
 * Approach: Backtracking / DFS with include/exclude decisions
 * Time Complexity: O(2^n) - exponential
 * Space Complexity: O(n) recursion depth + O(2^n) for results
 */
import java.util.ArrayList;
import java.util.List;

public class SubsequenceMore {

    /**
     * Generates all subsequences of an array
     * 
     * @param nums Input array
     * @return List of all subsequences
     * 
     * Algorithm Steps:
     * 1. At each position, we have two choices:
     *    a. Include the current element
     *    b. Exclude the current element
     * 2. Recurse to next position
     * 3. When we reach end of array, add current subsequence to results
     * 
     * This generates a binary recursion tree of height n
     * Each leaf node represents one subsequence
     */
    public static List<List<Integer>> allSubsequences(int[] nums) {
        System.out.println("\n=== Generating All Subsequences ===");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("Total subsequences expected: 2^" + nums.length + " = " + 
                         (1 << nums.length));
        System.out.println("Subsequences maintain original order");
        
        List<List<Integer>> res = new ArrayList<>();
        
        // Edge case: empty array
        if (nums == null || nums.length == 0) {
            System.out.println("Empty array, returning [[]]");
            res.add(new ArrayList<>());
            return res;
        }
        
        System.out.println("\nStarting backtracking...");
        backtrack(0, nums, new ArrayList<>(), res, 0);
        
        System.out.println("\nTotal subsequences generated: " + res.size());
        System.out.println("Expected: " + (1 << nums.length) + 
                         " (match: " + (res.size() == (1 << nums.length) ? "✓" : "✗") + ")");
        return res;
    }

    /**
     * Recursive backtracking function
     * 
     * @param idx Current index in array
     * @param nums Input array
     * @param curr Current subsequence being built
     * @param res Result list to store complete subsequences
     * @param depth Recursion depth (for visualization)
     */
    private static void backtrack(int idx, int[] nums, List<Integer> curr, 
                                 List<List<Integer>> res, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "backtrack(idx=" + idx + ", curr=" + curr + 
                         ", depth=" + depth + ")");
        
        // Base case: reached end of array
        if (idx == nums.length) {
            System.out.println(indent + "  ✓ Complete subsequence: " + curr);
            res.add(new ArrayList<>(curr));  // Add copy to results
            return;
        }
        
        System.out.println(indent + "  Processing element nums[" + idx + "]=" + nums[idx]);
        
        // Choice 1: INCLUDE current element
        System.out.println(indent + "  INCLUDE " + nums[idx] + ":");
        curr.add(nums[idx]);
        System.out.println(indent + "    Added " + nums[idx] + ", curr: " + curr);
        backtrack(idx + 1, nums, curr, res, depth + 1);
        
        // Backtrack: remove the element we just added
        int removed = curr.remove(curr.size() - 1);
        System.out.println(indent + "    Backtracked, removed " + removed + ", curr: " + curr);
        
        // Choice 2: EXCLUDE current element
        System.out.println(indent + "  EXCLUDE " + nums[idx] + ":");
        System.out.println(indent + "    Skipping " + nums[idx] + ", curr unchanged: " + curr);
        backtrack(idx + 1, nums, curr, res, depth + 1);
    }
    
    /**
     * Alternative approach: Using bitmask representation
     * Since each subsequence can be represented by n-bit mask
     * where bit i = 1 means include nums[i], 0 means exclude
     * 
     * @param nums Input array
     * @return List of all subsequences
     * 
     * Time: O(2^n * n) - slower but demonstrates concept
     */
    public static List<List<Integer>> allSubsequencesBitmask(int[] nums) {
        System.out.println("\n=== Bitmask Approach ===");
        
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        int total = 1 << n;  // 2^n
        
        System.out.println("Generating " + total + " subsequences using bitmask");
        System.out.println("Each bitmask from 0 to " + (total-1) + " represents a subsequence");
        
        for (int mask = 0; mask < total; mask++) {
            List<Integer> subsequence = new ArrayList<>();
            
            // Check each bit position
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subsequence.add(nums[i]);
                }
            }
            
            res.add(subsequence);
            System.out.println("Mask " + String.format("%" + n + "s", 
                         Integer.toBinaryString(mask)).replace(' ', '0') + 
                         " → " + subsequence);
        }
        
        return res;
    }
    
    /**
     * Alternative approach: Using for-loop backtracking
     * More common pattern for subset generation
     */
    public static List<List<Integer>> allSubsequencesWithLoop(int[] nums) {
        System.out.println("\n=== For-Loop Backtracking Approach ===");
        
        List<List<Integer>> res = new ArrayList<>();
        backtrackWithLoop(nums, 0, new ArrayList<>(), res, 0);
        return res;
    }
    
    private static void backtrackWithLoop(int[] nums, int start, 
                                         List<Integer> curr, List<List<Integer>> res, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "backtrackWithLoop(start=" + start + 
                         ", curr=" + curr + ")");
        
        // Add current subsequence to results
        System.out.println(indent + "  Adding to results: " + curr);
        res.add(new ArrayList<>(curr));
        
        // Generate all subsequences starting from 'start'
        for (int i = start; i < nums.length; i++) {
            System.out.println(indent + "  Including nums[" + i + "]=" + nums[i]);
            curr.add(nums[i]);
            backtrackWithLoop(nums, i + 1, curr, res, depth + 1);
            curr.remove(curr.size() - 1);
            System.out.println(indent + "  Backtracked, curr: " + curr);
        }
    }
    
    /**
     * Visualizes the recursion tree
     */
    public static void visualizeRecursionTree(int[] nums) {
        System.out.println("\n=== Visualizing Recursion Tree ===");
        System.out.println("Array: " + java.util.Arrays.toString(nums));
        System.out.println("\nTree Structure (Binary Decision Tree):");
        System.out.println("Each node: (idx, current subsequence)");
        System.out.println("Left branch: INCLUDE current element");
        System.out.println("Right branch: EXCLUDE current element");
        
        visualizeTreeHelper(0, nums, new ArrayList<>(), 0);
    }
    
    private static void visualizeTreeHelper(int idx, int[] nums, 
                                           List<Integer> curr, int depth) {
        String indent = "  ".repeat(depth);
        
        if (idx == nums.length) {
            System.out.println(indent + "Leaf: " + curr);
            return;
        }
        
        System.out.println(indent + "Node: idx=" + idx + ", elem=" + nums[idx] + 
                         ", curr=" + curr);
        
        // Left branch: include
        System.out.println(indent + "  ├─ Include " + nums[idx] + ":");
        curr.add(nums[idx]);
        visualizeTreeHelper(idx + 1, nums, curr, depth + 1);
        curr.remove(curr.size() - 1);
        
        // Right branch: exclude
        System.out.println(indent + "  └─ Exclude " + nums[idx] + ":");
        visualizeTreeHelper(idx + 1, nums, curr, depth + 1);
    }
    
    /**
     * Demonstrates the power set concept
     */
    public static void demonstratePowerSet() {
        System.out.println("\n=== Power Set Concept ===");
        
        System.out.println("A subsequence is essentially a subset that maintains order");
        System.out.println("For array [1, 2, 3]:");
        System.out.println("Power set (all subsets):");
        System.out.println("  {}, {1}, {2}, {3}, {1,2}, {1,3}, {2,3}, {1,2,3}");
        
        System.out.println("\nNote: {2,1} is NOT a subsequence (order changed)");
        System.out.println("But it IS a subset");
        
        System.out.println("\nMathematical formula:");
        System.out.println("For n elements, total subsequences = 2^n");
        System.out.println("This is the size of the power set");
    }
    
    /**
     * Performance comparison of different approaches
     */
    public static void performanceComparison() {
        System.out.println("\n=== Performance Comparison ===");
        
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("Array size: " + nums.length);
        System.out.println("Expected subsequences: 2^" + nums.length + " = " + 
                         (1 << nums.length));
        
        // Method 1: Recursive backtracking
        System.out.println("\n1. Recursive backtracking (include/exclude):");
        long start = System.nanoTime();
        List<List<Integer>> res1 = allSubsequences(nums);
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        System.out.println("Subsequences: " + res1.size());
        
        // Method 2: Bitmask approach
        System.out.println("\n2. Bitmask approach:");
        start = System.nanoTime();
        List<List<Integer>> res2 = allSubsequencesBitmask(nums);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        System.out.println("Subsequences: " + res2.size());
        
        // Method 3: For-loop backtracking
        System.out.println("\n3. For-loop backtracking:");
        start = System.nanoTime();
        List<List<Integer>> res3 = allSubsequencesWithLoop(nums);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        System.out.println("Subsequences: " + res3.size());
        
        // Verify all methods produce same results
        boolean allMatch = res1.size() == res2.size() && res2.size() == res3.size();
        if (allMatch) {
            java.util.Set<String> set1 = new java.util.HashSet<>();
            java.util.Set<String> set2 = new java.util.HashSet<>();
            java.util.Set<String> set3 = new java.util.HashSet<>();
            
            for (List<Integer> list : res1) {
                set1.add(list.toString());
            }
            for (List<Integer> list : res2) {
                set2.add(list.toString());
            }
            for (List<Integer> list : res3) {
                set3.add(list.toString());
            }
            
            allMatch = set1.equals(set2) && set2.equals(set3);
        }
        System.out.println("All results match: " + (allMatch ? "✓" : "✗"));
    }
    
    /**
     * Test different scenarios
     */
    public static void testScenarios() {
        System.out.println("\n=== Testing Different Scenarios ===");
        
        // Test 1: Empty array
        System.out.println("\n1. Empty array:");
        int[] nums1 = {};
        List<List<Integer>> res1 = allSubsequences(nums1);
        System.out.println("Result: " + res1);
        System.out.println("Expected: [[]] (only empty subsequence)");
        
        // Test 2: Single element
        System.out.println("\n2. Single element [5]:");
        int[] nums2 = {5};
        List<List<Integer>> res2 = allSubsequences(nums2);
        System.out.println("Result: " + res2);
        System.out.println("Expected: [[], [5]]");
        
        // Test 3: Two elements
        System.out.println("\n3. Two elements [1, 2]:");
        int[] nums3 = {1, 2};
        List<List<Integer>> res3 = allSubsequences(nums3);
        System.out.println("Result: " + res3);
        System.out.println("Expected: [[], [1], [2], [1,2]]");
        System.out.println("Note: [2,1] is NOT a subsequence (order matters)");
        
        // Test 4: Three elements
        System.out.println("\n4. Three elements [1, 2, 3]:");
        int[] nums4 = {1, 2, 3};
        List<List<Integer>> res4 = allSubsequences(nums4);
        System.out.println("Result size: " + res4.size());
        System.out.println("Result: " + res4);
        System.out.println("Expected: 2^3 = 8 subsequences");
        
        // Test 5: Duplicate elements (still works)
        System.out.println("\n5. With duplicates [1, 2, 1]:");
        int[] nums5 = {1, 2, 1};
        List<List<Integer>> res5 = allSubsequences(nums5);
        System.out.println("Result size: " + res5.size());
        System.out.println("Note: [1] appears multiple times but from different positions");
        System.out.println("First few: " + res5.subList(0, Math.min(5, res5.size())));
        
        // Test 6: Verify all subsequences are unique
        System.out.println("\n6. Verifying all subsequences are valid:");
        boolean allValid = true;
        for (List<Integer> subseq : res4) {
            // Check if it's a valid subsequence
            int lastIndex = -1;
            for (int num : subseq) {
                // Find this number after lastIndex in original array
                boolean found = false;
                for (int i = lastIndex + 1; i < nums4.length; i++) {
                    if (nums4[i] == num) {
                        lastIndex = i;
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    allValid = false;
                    System.out.println("Invalid: " + subseq + " not a subsequence of " + 
                                     java.util.Arrays.toString(nums4));
                    break;
                }
            }
        }
        System.out.println("All are valid subsequences: " + (allValid ? "✓" : "✗"));
    }
    
    /**
     * Extended version: Generate subsequences with constraints
     */
    public static List<List<Integer>> subsequencesWithSum(int[] nums, int target) {
        System.out.println("\n=== Subsequences with Sum = " + target + " ===");
        
        List<List<Integer>> res = new ArrayList<>();
        backtrackWithSum(nums, 0, 0, target, new ArrayList<>(), res, 0);
        return res;
    }
    
    private static void backtrackWithSum(int[] nums, int idx, int currentSum, int target,
                                        List<Integer> curr, List<List<Integer>> res, int depth) {
        if (idx == nums.length) {
            if (currentSum == target) {
                res.add(new ArrayList<>(curr));
            }
            return;
        }
        
        // Include current element
        curr.add(nums[idx]);
        backtrackWithSum(nums, idx + 1, currentSum + nums[idx], target, curr, res, depth + 1);
        curr.remove(curr.size() - 1);
        
        // Exclude current element
        backtrackWithSum(nums, idx + 1, currentSum, target, curr, res, depth + 1);
    }
    
    /**
     * Generate subsequences of minimum length k
     */
    public static List<List<Integer>> subsequencesMinLength(int[] nums, int k) {
        System.out.println("\n=== Subsequences with minimum length " + k + " ===");
        
        List<List<Integer>> res = new ArrayList<>();
        backtrackMinLength(nums, 0, new ArrayList<>(), res, k, 0);
        return res;
    }
    
    private static void backtrackMinLength(int[] nums, int idx, List<Integer> curr,
                                          List<List<Integer>> res, int k, int depth) {
        if (idx == nums.length) {
            if (curr.size() >= k) {
                res.add(new ArrayList<>(curr));
            }
            return;
        }
        
        // Include
        curr.add(nums[idx]);
        backtrackMinLength(nums, idx + 1, curr, res, k, depth + 1);
        curr.remove(curr.size() - 1);
        
        // Exclude
        backtrackMinLength(nums, idx + 1, curr, res, k, depth + 1);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Generating All Subsequences (Power Set) ===\n");
        
        // Basic example with visualization
        System.out.println("Basic Example: [1, 2, 3]");
        int[] nums = {1, 2, 3};
        List<List<Integer>> result = allSubsequences(nums);
        System.out.println("\nFinal Result: " + result);
        
        // Visualize recursion tree
        visualizeRecursionTree(nums);
        
        // Demonstrate power set concept
        demonstratePowerSet();
        
        // Test different scenarios
        testScenarios();
        
        // Performance comparison
        performanceComparison();
        
        // Extended examples
        System.out.println("\n=== Extended Examples ===");
        
        // Subsequences with sum constraint
        System.out.println("\n1. Subsequences of [1, 2, 3, 4] with sum = 5:");
        int[] nums2 = {1, 2, 3, 4};
        List<List<Integer>> sumResult = subsequencesWithSum(nums2, 5);
        System.out.println("Result: " + sumResult);
        
        // Subsequences with minimum length
        System.out.println("\n2. Subsequences of [1, 2, 3, 4] with minimum length 2:");
        List<List<Integer>> lengthResult = subsequencesMinLength(nums2, 2);
        System.out.println("Result size: " + lengthResult.size());
        System.out.println("First few: " + lengthResult.subList(0, Math.min(5, lengthResult.size())));
        
        // Mathematical analysis
        System.out.println("\n=== Mathematical Analysis ===");
        System.out.println("\nTotal subsequences for n elements: 2^n");
        System.out.println("Proof: Each element has 2 choices: include or exclude");
        System.out.println("So total = 2 × 2 × ... × 2 (n times) = 2^n");
        
        System.out.println("\nRecurrence relation:");
        System.out.println("S(n) = 2 * S(n-1) where S(0) = 1 (empty subsequence)");
        System.out.println("Solution: S(n) = 2^n");
        
        System.out.println("\n=== Time Complexity ===");
        System.out.println("Generating all subsequences: O(2^n)");
        System.out.println("Why? We generate 2^n subsequences");
        System.out.println("Each subsequence takes O(n) to build/copy");
        System.out.println("Total: O(n * 2^n) operations");
        
        System.out.println("\n=== Space Complexity ===");
        System.out.println("Recursion depth: O(n)");
        System.out.println("Current subsequence: O(n)");
        System.out.println("Result storage: O(2^n * n) total characters");
        
        System.out.println("\n=== Alternative Approaches ===");
        System.out.println("\n1. Bitmask (iterative):");
        System.out.println("   - Generate all numbers 0 to 2^n-1");
        System.out.println("   - Each bitmask represents a subsequence");
        System.out.println("   - Time: O(2^n * n), Space: O(1) extra");
        
        System.out.println("\n2. BFS/Queue:");
        System.out.println("   - Start with empty list");
        System.out.println("   - Level by level add elements");
        System.out.println("   - Time: O(2^n * n), Space: O(2^n)");
        
        System.out.println("\n3. Iterative building:");
        System.out.println("   - Start with [[]]");
        System.out.println("   - For each element, append to all existing");
        System.out.println("   - Time: O(2^n * n), Space: O(2^n)");
        
        System.out.println("\n=== Common Interview Questions ===");
        System.out.println("\nQ: What's the difference between subsequence and subset?");
        System.out.println("A: Subsequence maintains order, subset doesn't");
        System.out.println("   All subsequences are subsets but not vice versa");
        
        System.out.println("\nQ: How to generate subsequences in lexicographic order?");
        System.out.println("A: Sort array first, or use bitmask in order");
        
        System.out.println("\nQ: How to generate only non-empty subsequences?");
        System.out.println("A: Skip the empty one, or filter at the end");
        
        System.out.println("\nQ: What about subsequences with duplicates in input?");
        System.out.println("A: Algorithm still works but may produce duplicate subsequences");
        System.out.println("   Can use Set to deduplicate");
        
        System.out.println("\nQ: How to find the longest increasing subsequence?");
        System.out.println("A: Different problem - use dynamic programming (O(n^2))");
        
        System.out.println("\n=== Real-World Applications ===");
        System.out.println("\n1. Data analysis: Explore all feature subsets");
        System.out.println("2. Combinatorial testing: Test all parameter combinations");
        System.out.println("3. Game theory: Analyze all possible move sequences");
        System.out.println("4. Machine learning: Feature selection");
        System.out.println("5. Bioinformatics: DNA subsequence analysis");
        
        System.out.println("\n=== Key Insights ===");
        System.out.println("\n1. Subsequence generation = binary decisions at each position");
        System.out.println("2. Total count = 2^n (exponential growth)");
        System.out.println("3. Multiple equivalent approaches: recursive, iterative, bitmask");
        System.out.println("4. Order preservation is the key constraint");
        System.out.println("5. The decision tree is a perfect binary tree of height n");
        
        System.out.println("\n=== Optimization Ideas ===");
        System.out.println("1. Early pruning for constraints (sum, length)");
        System.out.println("2. Memoization for overlapping subproblems");
        System.out.println("3. Iterative generation for memory efficiency");
        System.out.println("4. Parallel generation (independent branches)");
        System.out.println("5. Lazy evaluation for large n");
    }
}