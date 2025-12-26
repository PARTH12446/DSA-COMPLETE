/**
 * Class solving the Combination Sum problem (unlimited use of candidates)
 * 
 * Problem: Given an array of distinct integers candidates and a target integer,
 * find all unique combinations of candidates where the chosen numbers sum to target.
 * The same number may be chosen from candidates an unlimited number of times.
 * 
 * Example:
 * Input: candidates = [2,3,6,7], target = 7
 * Output: [[2,2,3], [7]]
 * Explanation:
 * 2+2+3 = 7
 * 7 = 7
 * 
 * Constraints:
 * - All numbers are positive integers
 * - Combinations must be unique (different orders are considered the same)
 * - The same number can be used multiple times
 * 
 * Approach: Backtracking with DFS (Depth-First Search)
 * Time Complexity: O(N^(T/M)) where N = candidates, T = target, M = min(candidate)
 * Space Complexity: O(T/M) for recursion depth + space for results
 */
import java.util.ArrayList;
import java.util.List;

public class ComboSum1 {

    /**
     * Finds all unique combinations that sum to target
     * 
     * @param candidates Array of distinct positive integers
     * @param target Target sum to achieve
     * @return List of all unique combinations that sum to target
     * 
     * Algorithm Steps:
     * 1. Start with empty combination and index 0
     * 2. At each step, we have two choices:
     *    a. Take current candidate (if it doesn't exceed target)
     *    b. Skip current candidate and move to next
     * 3. When target becomes 0, we found a valid combination
     * 4. When we exhaust all candidates, we stop
     * 
     * Key Insight: Since we can use same number multiple times,
     * when we choose to take it, we stay at same index.
     * When we skip it, we move to next index.
     */
    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        System.out.println("\n=== Solving Combination Sum ===");
        System.out.println("Candidates: " + java.util.Arrays.toString(candidates));
        System.out.println("Target: " + target);
        System.out.println("Same candidate can be used multiple times");
        
        List<List<Integer>> res = new ArrayList<>();
        
        // Edge cases
        if (candidates == null || candidates.length == 0) {
            System.out.println("No candidates provided");
            return res;
        }
        
        if (target <= 0) {
            System.out.println("Target must be positive");
            return res;
        }
        
        // Sort candidates to help with pruning and duplicate prevention
        int[] sortedCandidates = candidates.clone();
        java.util.Arrays.sort(sortedCandidates);
        System.out.println("Sorted candidates: " + java.util.Arrays.toString(sortedCandidates));
        
        System.out.println("\nStarting backtracking...");
        backtrack(0, sortedCandidates, target, new ArrayList<>(), res, 0);
        
        System.out.println("\nTotal combinations found: " + res.size());
        return res;
    }

    /**
     * Recursive backtracking function
     * 
     * @param idx Current index in candidates array
     * @param nums Sorted candidates array
     * @param target Remaining target sum to achieve
     * @param curr Current combination being built
     * @param res Result list to store valid combinations
     * @param depth Recursion depth (for visualization)
     */
    private static void backtrack(int idx, int[] nums, int target, 
                                 List<Integer> curr, List<List<Integer>> res, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "backtrack(idx=" + idx + ", target=" + target + 
                         ", curr=" + curr + ")");
        
        // Base case 1: Reached end of candidates array
        if (idx == nums.length) {
            System.out.println(indent + "  Reached end of candidates");
            if (target == 0) {
                System.out.println(indent + "  ✓ Valid combination found: " + curr);
                res.add(new ArrayList<>(curr));  // Add copy to result
            } else {
                System.out.println(indent + "  ✗ Target not zero: " + target);
            }
            return;
        }
        
        // Base case 2: Target reached (can also check here for early termination)
        if (target == 0) {
            System.out.println(indent + "  ✓ Target reached: " + curr);
            res.add(new ArrayList<>(curr));
            return;
        }
        
        // Base case 3: Target negative (prune this path)
        if (target < 0) {
            System.out.println(indent + "  ✗ Target negative: " + target + ", backtracking");
            return;
        }
        
        // Choice 1: Take current candidate (if it doesn't exceed target)
        if (nums[idx] <= target) {
            System.out.println(indent + "  Taking " + nums[idx] + " at index " + idx);
            curr.add(nums[idx]);  // Make choice
            System.out.println(indent + "  Added " + nums[idx] + ", curr: " + curr + 
                             ", new target: " + (target - nums[idx]));
            
            // Recurse with SAME index (since we can reuse same candidate)
            backtrack(idx, nums, target - nums[idx], curr, res, depth + 1);
            
            // Backtrack: undo choice
            int removed = curr.remove(curr.size() - 1);
            System.out.println(indent + "  Backtracked, removed " + removed + 
                             ", curr: " + curr);
        } else {
            System.out.println(indent + "  Skipping take of " + nums[idx] + 
                             " (exceeds target " + target + ")");
        }
        
        // Choice 2: Skip current candidate (move to next index)
        System.out.println(indent + "  Skipping " + nums[idx] + ", moving to index " + (idx + 1));
        backtrack(idx + 1, nums, target, curr, res, depth + 1);
    }
    
    /**
     * Alternative implementation with for-loop (more common pattern)
     * This approach naturally prevents duplicate combinations
     */
    public static List<List<Integer>> combinationSumWithLoop(int[] candidates, int target) {
        System.out.println("\n=== Alternative: Combination Sum with For-Loop ===");
        
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return res;
        
        // Sort to help with pruning
        int[] sorted = candidates.clone();
        java.util.Arrays.sort(sorted);
        
        backtrackWithLoop(sorted, target, 0, new ArrayList<>(), res, 0);
        return res;
    }
    
    private static void backtrackWithLoop(int[] nums, int target, int start, 
                                         List<Integer> curr, List<List<Integer>> res, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "backtrackWithLoop(start=" + start + 
                         ", target=" + target + ", curr=" + curr + ")");
        
        // Found valid combination
        if (target == 0) {
            System.out.println(indent + "  ✓ Found combination: " + curr);
            res.add(new ArrayList<>(curr));
            return;
        }
        
        // Try each candidate starting from 'start' index
        for (int i = start; i < nums.length; i++) {
            // Prune if candidate exceeds remaining target
            if (nums[i] > target) {
                System.out.println(indent + "  Pruning at i=" + i + 
                                 " (nums[" + i + "]=" + nums[i] + " > target=" + target + ")");
                break;  // Since array is sorted, all later nums will also be > target
            }
            
            System.out.println(indent + "  Trying nums[" + i + "]=" + nums[i]);
            curr.add(nums[i]);  // Make choice
            System.out.println(indent + "  Added " + nums[i] + ", curr: " + curr + 
                             ", new target: " + (target - nums[i]));
            
            // Recurse with same i (can reuse same candidate)
            backtrackWithLoop(nums, target - nums[i], i, curr, res, depth + 1);
            
            // Backtrack
            int removed = curr.remove(curr.size() - 1);
            System.out.println(indent + "  Backtracked, removed " + removed + 
                             ", curr: " + curr);
        }
    }
    
    /**
     * Combination Sum II: Each candidate can be used only once
     * Candidates may contain duplicates
     */
    public static List<List<Integer>> combinationSum2(int[] candidates, int target) {
        System.out.println("\n=== Combination Sum II (Use Each Candidate Once) ===");
        
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return res;
        
        // Sort to handle duplicates
        int[] sorted = candidates.clone();
        java.util.Arrays.sort(sorted);
        
        backtrackSum2(sorted, target, 0, new ArrayList<>(), res, 0);
        return res;
    }
    
    private static void backtrackSum2(int[] nums, int target, int start, 
                                     List<Integer> curr, List<List<Integer>> res, int depth) {
        String indent = "  ".repeat(depth);
        
        if (target == 0) {
            System.out.println(indent + "✓ Found combination: " + curr);
            res.add(new ArrayList<>(curr));
            return;
        }
        
        if (target < 0) {
            System.out.println(indent + "✗ Target negative: " + target);
            return;
        }
        
        for (int i = start; i < nums.length; i++) {
            // Skip duplicates to avoid duplicate combinations
            if (i > start && nums[i] == nums[i-1]) {
                System.out.println(indent + "Skipping duplicate: nums[" + i + "]=" + nums[i]);
                continue;
            }
            
            if (nums[i] > target) {
                System.out.println(indent + "Pruning at i=" + i);
                break;
            }
            
            System.out.println(indent + "Trying nums[" + i + "]=" + nums[i]);
            curr.add(nums[i]);
            // Note: i+1 because we can't reuse same element
            backtrackSum2(nums, target - nums[i], i + 1, curr, res, depth + 1);
            curr.remove(curr.size() - 1);
        }
    }
    
    /**
     * Combination Sum III: Use exactly k numbers from 1..9 that sum to n
     */
    public static List<List<Integer>> combinationSum3(int k, int n) {
        System.out.println("\n=== Combination Sum III (k numbers from 1..9) ===");
        System.out.println("k=" + k + ", n=" + n);
        
        List<List<Integer>> res = new ArrayList<>();
        backtrackSum3(k, n, 1, new ArrayList<>(), res, 0);
        return res;
    }
    
    private static void backtrackSum3(int k, int target, int start, 
                                     List<Integer> curr, List<List<Integer>> res, int depth) {
        String indent = "  ".repeat(depth);
        
        // Found valid combination
        if (curr.size() == k && target == 0) {
            System.out.println(indent + "✓ Found: " + curr);
            res.add(new ArrayList<>(curr));
            return;
        }
        
        // Prune if we have too many numbers or target negative
        if (curr.size() > k || target < 0) {
            System.out.println(indent + "✗ Pruning: size=" + curr.size() + ", target=" + target);
            return;
        }
        
        // Try numbers from start to 9
        for (int i = start; i <= 9; i++) {
            if (i > target) {
                System.out.println(indent + "Pruning: " + i + " > " + target);
                break;
            }
            
            System.out.println(indent + "Trying " + i);
            curr.add(i);
            backtrackSum3(k, target - i, i + 1, curr, res, depth + 1);
            curr.remove(curr.size() - 1);
        }
    }
    
    /**
     * Visualizes the recursion tree for a given input
     */
    public static void visualizeRecursionTree(int[] candidates, int target) {
        System.out.println("\n=== Visualizing Recursion Tree ===");
        System.out.println("Candidates: " + java.util.Arrays.toString(candidates));
        System.out.println("Target: " + target);
        System.out.println("\nTree Structure (DFS):");
        
        int[] sorted = candidates.clone();
        java.util.Arrays.sort(sorted);
        visualizeTreeHelper(sorted, target, 0, new ArrayList<>(), 0);
    }
    
    private static void visualizeTreeHelper(int[] nums, int target, int idx, 
                                           List<Integer> curr, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "Node: idx=" + idx + ", target=" + target + 
                         ", path=" + curr);
        
        // Termination conditions
        if (target == 0) {
            System.out.println(indent + "  ✓ Leaf: valid combination " + curr);
            return;
        }
        
        if (idx >= nums.length || target < 0) {
            System.out.println(indent + "  ✗ Leaf: invalid path");
            return;
        }
        
        // Left branch: take current number
        if (nums[idx] <= target) {
            System.out.println(indent + "  ├─ Take " + nums[idx] + ":");
            curr.add(nums[idx]);
            visualizeTreeHelper(nums, target - nums[idx], idx, curr, depth + 1);
            curr.remove(curr.size() - 1);
        } else {
            System.out.println(indent + "  ├─ Can't take " + nums[idx] + " (too large)");
        }
        
        // Right branch: skip current number
        System.out.println(indent + "  └─ Skip " + nums[idx] + ":");
        visualizeTreeHelper(nums, target, idx + 1, curr, depth + 1);
    }
    
    /**
     * Performance comparison of different approaches
     */
    public static void performanceComparison() {
        System.out.println("\n=== Performance Comparison ===");
        
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        
        // Method 1: Original recursive approach
        System.out.println("\n1. Original recursive approach:");
        long start = System.nanoTime();
        List<List<Integer>> res1 = combinationSum(candidates, target);
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000.0 + " microseconds");
        
        // Method 2: For-loop approach
        System.out.println("\n2. For-loop approach:");
        start = System.nanoTime();
        List<List<Integer>> res2 = combinationSumWithLoop(candidates, target);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000.0 + " microseconds");
        
        // Verify results match
        boolean match = res1.size() == res2.size();
        if (match) {
            // Convert to sets of strings for comparison (ignore order)
            java.util.Set<String> set1 = new java.util.HashSet<>();
            java.util.Set<String> set2 = new java.util.HashSet<>();
            
            for (List<Integer> list : res1) {
                java.util.Collections.sort(list);
                set1.add(list.toString());
            }
            for (List<Integer> list : res2) {
                java.util.Collections.sort(list);
                set2.add(list.toString());
            }
            
            match = set1.equals(set2);
        }
        System.out.println("Results match: " + (match ? "✓" : "✗"));
    }
    
    /**
     * Test different scenarios
     */
    public static void testScenarios() {
        System.out.println("\n=== Testing Different Scenarios ===");
        
        // Test 1: Basic example
        System.out.println("\n1. Basic example:");
        int[] candidates1 = {2, 3, 6, 7};
        int target1 = 7;
        List<List<Integer>> res1 = combinationSum(candidates1, target1);
        System.out.println("Result: " + res1);
        
        // Test 2: Multiple solutions
        System.out.println("\n2. Multiple solutions:");
        int[] candidates2 = {2, 3, 5};
        int target2 = 8;
        List<List<Integer>> res2 = combinationSum(candidates2, target2);
        System.out.println("Result: " + res2);
        
        // Test 3: No solution
        System.out.println("\n3. No solution:");
        int[] candidates3 = {2, 4, 6};
        int target3 = 7;
        List<List<Integer>> res3 = combinationSum(candidates3, target3);
        System.out.println("Result: " + res3);
        
        // Test 4: Single candidate repeated
        System.out.println("\n4. Single candidate that divides target:");
        int[] candidates4 = {3};
        int target4 = 9;
        List<List<Integer>> res4 = combinationSum(candidates4, target4);
        System.out.println("Result: " + res4);
        
        // Test 5: Combination Sum II (unique combinations)
        System.out.println("\n5. Combination Sum II (use each once, with duplicates):");
        int[] candidates5 = {10, 1, 2, 7, 6, 1, 5};
        int target5 = 8;
        List<List<Integer>> res5 = combinationSum2(candidates5, target5);
        System.out.println("Result: " + res5);
        
        // Test 6: Combination Sum III
        System.out.println("\n6. Combination Sum III (k=3, n=7):");
        List<List<Integer>> res6 = combinationSum3(3, 7);
        System.out.println("Result: " + res6);
    }
    
    /**
     * Demonstrates pruning with sorted array
     */
    public static void demonstratePruning() {
        System.out.println("\n=== Demonstrating Pruning with Sorted Array ===");
        
        int[] candidates = {8, 7, 4, 3};
        int target = 11;
        
        // Unsorted
        System.out.println("Unsorted array: " + java.util.Arrays.toString(candidates));
        System.out.println("Will explore many invalid paths");
        
        // Sorted
        int[] sorted = candidates.clone();
        java.util.Arrays.sort(sorted);
        System.out.println("\nSorted array: " + java.util.Arrays.toString(sorted));
        System.out.println("Can prune when candidate > remaining target");
        
        // Visualize
        visualizeRecursionTree(sorted, target);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Combination Sum Problem (Backtracking) ===\n");
        
        // Basic example with visualization
        System.out.println("Basic Example: candidates=[2,3,6,7], target=7");
        int[] candidates = {2, 3, 6, 7};
        int target = 7;
        
        List<List<Integer>> result = combinationSum(candidates, target);
        System.out.println("\nFinal Result: " + result);
        
        // Visualize recursion tree
        visualizeRecursionTree(candidates, target);
        
        // Test different scenarios
        testScenarios();
        
        // Performance comparison
        performanceComparison();
        
        // Demonstrate pruning
        demonstratePruning();
        
        // Mathematical analysis
        System.out.println("\n=== Mathematical Analysis ===");
        System.out.println("\nTime Complexity:");
        System.out.println("Worst case: O(N^(T/M)) where:");
        System.out.println("  N = number of candidates");
        System.out.println("  T = target value");
        System.out.println("  M = minimum candidate value");
        System.out.println("This is because in worst case, we explore a tree");
        System.out.println("where each node has N children, depth up to T/M");
        
        System.out.println("\nSpace Complexity:");
        System.out.println("O(T/M) for recursion depth");
        System.out.println("Plus space for storing results: O(K * L) where:");
        System.out.println("  K = number of valid combinations");
        System.out.println("  L = average length of combination");
        
        System.out.println("\n=== Backtracking Template ===");
        System.out.println("This problem follows the classic backtracking pattern:");
        System.out.println("1. Make a choice (take current candidate)");
        System.out.println("2. Recurse with updated state");
        System.out.println("3. Undo the choice (backtrack)");
        System.out.println("4. Make another choice (skip current candidate)");
        System.out.println("5. Recurse with original state");
        
        System.out.println("\n=== Optimization Techniques ===");
        System.out.println("1. Sorting candidates: Enables pruning when candidate > target");
        System.out.println("2. Starting index: Prevents duplicate combinations");
        System.out.println("3. Early termination: Return when target reached or negative");
        System.out.println("4. Memoization: Not applicable here because of unlimited reuse");
        
        System.out.println("\n=== Common Variations ===");
        System.out.println("1. Combination Sum I: Unlimited use of candidates");
        System.out.println("2. Combination Sum II: Each candidate used at most once");
        System.out.println("3. Combination Sum III: Use exactly k numbers from 1..9");
        System.out.println("4. Combination Sum IV: Count number of combinations (DP)");
        
        System.out.println("\n=== Real-World Applications ===");
        System.out.println("1. Coin change problem (making exact change)");
        System.out.println("2. Resource allocation (assigning resources to meet target)");
        System.out.println("3. Knapsack problem variants");
        System.out.println("4. Generating all possible scores in games");
        
        System.out.println("\n=== Common Interview Questions ===");
        System.out.println("Q: How do you avoid duplicate combinations?");
        System.out.println("A: Use a starting index and don't go backwards");
        
        System.out.println("\nQ: What if candidates contain negative numbers?");
        System.out.println("A: Can't prune when target negative, need to explore all paths");
        System.out.println("   Might need to limit depth to avoid infinite recursion");
        
        System.out.println("\nQ: How would you count combinations without listing them?");
        System.out.println("A: Use dynamic programming (coin change problem)");
        
        System.out.println("\nQ: What if we need to find the combination with minimum size?");
        System.out.println("A: Track minimum during backtracking or use BFS approach");
        
        System.out.println("\n=== Key Insights ===");
        System.out.println("1. Backtracking is natural for combination generation");
        System.out.println("2. Sorting enables pruning optimization");
        System.out.println("3. Starting index prevents duplicate combinations");
        System.out.println("4. The decision tree has two branches at each node:");
        System.out.println("   - Take current element (stay at same index)");
        System.out.println("   - Skip current element (move to next index)");
    }
}