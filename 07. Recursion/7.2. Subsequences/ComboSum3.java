/**
 * Class solving the Combination Sum II problem (each candidate used at most once)
 * 
 * Problem: Given a collection of candidate numbers (candidates) and a target number,
 * find all unique combinations in candidates where the candidate numbers sum to target.
 * 
 * Key Differences from Combination Sum I:
 * 1. Each number in candidates may only be used ONCE in the combination
 * 2. The input may contain DUPLICATE numbers
 * 3. The solution set must not contain duplicate combinations
 * 
 * Example:
 * Input: candidates = [10,1,2,7,6,1,5], target = 8
 * Output: [
 *   [1,1,6],
 *   [1,2,5],
 *   [1,7],
 *   [2,6]
 * ]
 * 
 * Approach: Backtracking with careful duplicate handling
 * Time Complexity: O(2^N) where N = number of candidates
 * Space Complexity: O(N) for recursion stack + space for results
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComboSum2 {

    /**
     * Finds all unique combinations where each candidate is used at most once
     * 
     * @param nums Array of candidate numbers (may contain duplicates)
     * @param target Target sum to achieve
     * @return List of all unique combinations that sum to target
     * 
     * Algorithm Steps:
     * 1. Sort the array (crucial for duplicate handling and pruning)
     * 2. Use backtracking to explore combinations
     * 3. At each step, for loop from start index to end
     * 4. Skip duplicates: if nums[i] == nums[i-1] and i > start
     * 5. Prune when candidate > remaining target (since array is sorted)
     * 6. When taking a candidate, move to next index (i+1) since can't reuse
     * 
     * Why sorting helps:
     * - Enables pruning: if nums[i] > target, all later nums also > target
     * - Groups duplicates together, making them easy to skip
     */
    public static List<List<Integer>> combinationSum2(int[] nums, int target) {
        System.out.println("\n=== Solving Combination Sum II ===");
        System.out.println("Candidates: " + Arrays.toString(nums));
        System.out.println("Target: " + target);
        System.out.println("Each candidate can be used AT MOST ONCE");
        System.out.println("Input may contain DUPLICATES");
        System.out.println("Output must not contain duplicate combinations");
        
        List<List<Integer>> res = new ArrayList<>();
        
        // Edge cases
        if (nums == null || nums.length == 0) {
            System.out.println("No candidates provided");
            return res;
        }
        
        if (target <= 0) {
            System.out.println("Target must be positive");
            return res;
        }
        
        // STEP 1: Sort the array (CRITICAL for duplicate handling)
        int[] sortedNums = nums.clone();
        Arrays.sort(sortedNums);
        System.out.println("Sorted candidates: " + Arrays.toString(sortedNums));
        
        System.out.println("\nStarting backtracking...");
        backtrack(0, sortedNums, target, new ArrayList<>(), res, 0);
        
        System.out.println("\nTotal unique combinations found: " + res.size());
        return res;
    }

    /**
     * Recursive backtracking function with duplicate handling
     * 
     * @param start Starting index in the candidates array
     * @param nums Sorted array of candidates
     * @param target Remaining target sum to achieve
     * @param curr Current combination being built
     * @param res Result list to store valid combinations
     * @param depth Recursion depth (for visualization)
     */
    private static void backtrack(int start, int[] nums, int target, 
                                 List<Integer> curr, List<List<Integer>> res, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "backtrack(start=" + start + ", target=" + target + 
                         ", curr=" + curr + ")");
        
        // Base case 1: Found valid combination
        if (target == 0) {
            System.out.println(indent + "  ✓ Valid combination found: " + curr);
            res.add(new ArrayList<>(curr));  // Add copy to result
            return;
        }
        
        // Base case 2: Target negative (invalid path)
        if (target < 0) {
            System.out.println(indent + "  ✗ Target negative: " + target + ", backtracking");
            return;
        }
        
        // Explore candidates from start index to end
        for (int i = start; i < nums.length; i++) {
            System.out.println(indent + "  Considering nums[" + i + "]=" + nums[i]);
            
            // CRITICAL: Skip duplicates to avoid duplicate combinations
            // Only skip if it's NOT the first element at this recursion level
            if (i > start && nums[i] == nums[i - 1]) {
                System.out.println(indent + "    Skipping duplicate: nums[" + i + "]=" + nums[i] + 
                                 " == nums[" + (i-1) + "]=" + nums[i-1]);
                System.out.println(indent + "    i=" + i + " > start=" + start + 
                                 ", so this is a duplicate at this level");
                continue;
            }
            
            // Pruning: if current candidate exceeds target, break (array is sorted)
            if (nums[i] > target) {
                System.out.println(indent + "    Pruning: nums[" + i + "]=" + nums[i] + 
                                 " > target=" + target + ", breaking loop");
                break;  // Since array is sorted, all later elements are also > target
            }
            
            // Make choice: take nums[i]
            System.out.println(indent + "    Taking nums[" + i + "]=" + nums[i]);
            curr.add(nums[i]);
            System.out.println(indent + "    Added to curr: " + curr);
            System.out.println(indent + "    New target: " + target + " - " + nums[i] + " = " + (target - nums[i]));
            
            // Recurse with NEXT index (i+1) since we can't reuse same element
            backtrack(i + 1, nums, target - nums[i], curr, res, depth + 1);
            
            // Backtrack: undo choice
            int removed = curr.remove(curr.size() - 1);
            System.out.println(indent + "    Backtracked, removed " + removed);
            System.out.println(indent + "    Curr after backtrack: " + curr);
        }
    }
    
    /**
     * Alternative approach with counting frequencies (for better duplicate handling)
     * Useful when there are many duplicates
     */
    public static List<List<Integer>> combinationSum2WithFrequency(int[] candidates, int target) {
        System.out.println("\n=== Alternative: Using Frequency Count ===");
        
        List<List<Integer>> res = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return res;
        
        // Sort and count frequencies
        Arrays.sort(candidates);
        List<int[]> freqList = new ArrayList<>();
        
        for (int num : candidates) {
            if (freqList.isEmpty() || freqList.get(freqList.size() - 1)[0] != num) {
                freqList.add(new int[]{num, 1});  // New number
            } else {
                freqList.get(freqList.size() - 1)[1]++;  // Increment count
            }
        }
        
        System.out.println("Frequency list:");
        for (int[] pair : freqList) {
            System.out.println("  " + pair[0] + ": " + pair[1] + " times");
        }
        
        backtrackWithFrequency(freqList, target, 0, new ArrayList<>(), res, 0);
        return res;
    }
    
    private static void backtrackWithFrequency(List<int[]> freqList, int target, int idx,
                                              List<Integer> curr, List<List<Integer>> res, int depth) {
        String indent = "  ".repeat(depth);
        
        if (target == 0) {
            System.out.println(indent + "✓ Found: " + curr);
            res.add(new ArrayList<>(curr));
            return;
        }
        
        if (idx == freqList.size() || target < 0) {
            return;
        }
        
        int num = freqList.get(idx)[0];
        int maxCount = freqList.get(idx)[1];
        
        System.out.println(indent + "Processing " + num + " (available: " + maxCount + " times)");
        
        // Try using 0 to maxCount of current number
        for (int count = 0; count <= maxCount; count++) {
            if (count * num > target) {
                System.out.println(indent + "  Can't use " + count + " of " + num + 
                                 " (exceeds target " + target + ")");
                break;
            }
            
            if (count > 0) {
                System.out.println(indent + "  Using " + count + " of " + num);
                for (int c = 0; c < count; c++) {
                    curr.add(num);
                }
                System.out.println(indent + "  Curr: " + curr);
            }
            
            backtrackWithFrequency(freqList, target - count * num, idx + 1, curr, res, depth + 1);
            
            // Backtrack
            if (count > 0) {
                for (int c = 0; c < count; c++) {
                    curr.remove(curr.size() - 1);
                }
                System.out.println(indent + "  Backtracked " + count + " of " + num);
            }
        }
    }
    
    /**
     * Visualizes the decision tree for a given input
     */
    public static void visualizeDecisionTree(int[] nums, int target) {
        System.out.println("\n=== Visualizing Decision Tree ===");
        System.out.println("Candidates: " + Arrays.toString(nums));
        System.out.println("Target: " + target);
        
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        System.out.println("Sorted: " + Arrays.toString(sorted));
        
        System.out.println("\nTree Structure (DFS with pruning):");
        visualizeTreeHelper(sorted, target, 0, new ArrayList<>(), 0, new boolean[sorted.length]);
    }
    
    private static void visualizeTreeHelper(int[] nums, int target, int start,
                                           List<Integer> curr, int depth, boolean[] used) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "Node: start=" + start + ", target=" + target + 
                         ", path=" + curr);
        
        if (target == 0) {
            System.out.println(indent + "  ✓ Leaf: valid combination " + curr);
            return;
        }
        
        if (target < 0 || start >= nums.length) {
            System.out.println(indent + "  ✗ Leaf: invalid path");
            return;
        }
        
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i-1]) {
                System.out.println(indent + "  ├─ Skip duplicate " + nums[i]);
                continue;
            }
            
            if (nums[i] > target) {
                System.out.println(indent + "  ├─ Prune (nums[" + i + "]=" + nums[i] + " > target)");
                break;
            }
            
            System.out.println(indent + "  ├─ Take " + nums[i] + ":");
            curr.add(nums[i]);
            visualizeTreeHelper(nums, target - nums[i], i + 1, curr, depth + 1, used);
            curr.remove(curr.size() - 1);
        }
    }
    
    /**
     * Demonstrates duplicate handling with examples
     */
    public static void demonstrateDuplicateHandling() {
        System.out.println("\n=== Demonstrating Duplicate Handling ===");
        
        // Example 1: Simple duplicates
        System.out.println("\n1. Simple case: [1, 1, 2], target=3");
        System.out.println("Without duplicate handling would generate:");
        System.out.println("  [1, 2] (using first 1)");
        System.out.println("  [1, 2] (using second 1) <- DUPLICATE!");
        System.out.println("With duplicate handling:");
        System.out.println("  Only [1, 2] generated once");
        
        int[] nums1 = {1, 1, 2};
        List<List<Integer>> res1 = combinationSum2(nums1, 3);
        System.out.println("Result: " + res1);
        
        // Example 2: Why i > start is important
        System.out.println("\n2. Why we need 'i > start' not 'i > 0':");
        System.out.println("For [1, 1, 2], at start=0:");
        System.out.println("  i=0: take first 1 (allowed)");
        System.out.println("  i=1: skip second 1 (i=1 > start=0, and nums[1]==nums[0])");
        System.out.println("At deeper level when we skip first 1:");
        System.out.println("  start=1, i=1: take second 1 (i=1 == start=1, so allowed)");
        System.out.println("This allows [1, 2] using either first or second 1, but not both");
    }
    
    /**
     * Performance comparison
     */
    public static void performanceComparison() {
        System.out.println("\n=== Performance Comparison ===");
        
        int[] candidates = {10, 1, 2, 7, 6, 1, 5, 8, 3, 4, 9};
        int target = 15;
        
        // Method 1: Standard backtracking
        System.out.println("\n1. Standard backtracking with duplicate skipping:");
        long start = System.nanoTime();
        List<List<Integer>> res1 = combinationSum2(candidates, target);
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        System.out.println("Combinations found: " + res1.size());
        
        // Method 2: Frequency count approach
        System.out.println("\n2. Frequency count approach:");
        start = System.nanoTime();
        List<List<Integer>> res2 = combinationSum2WithFrequency(candidates, target);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        System.out.println("Combinations found: " + res2.size());
        
        // Verify results match
        boolean match = res1.size() == res2.size();
        if (match) {
            // Convert to sets of strings for comparison
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
        
        // Test 1: Basic example with duplicates
        System.out.println("\n1. Basic example with duplicates:");
        int[] nums1 = {10, 1, 2, 7, 6, 1, 5};
        int target1 = 8;
        List<List<Integer>> res1 = combinationSum2(nums1, target1);
        System.out.println("Result: " + res1);
        System.out.println("Expected: [[1,1,6], [1,2,5], [1,7], [2,6]]");
        
        // Test 2: No duplicates in input
        System.out.println("\n2. No duplicates in input:");
        int[] nums2 = {2, 3, 5};
        int target2 = 8;
        List<List<Integer>> res2 = combinationSum2(nums2, target2);
        System.out.println("Result: " + res2);
        System.out.println("Expected: [[3,5]]");
        
        // Test 3: All duplicates
        System.out.println("\n3. All duplicates:");
        int[] nums3 = {2, 2, 2, 2};
        int target3 = 6;
        List<List<Integer>> res3 = combinationSum2(nums3, target3);
        System.out.println("Result: " + res3);
        System.out.println("Expected: [[2,2,2]]");
        
        // Test 4: No solution
        System.out.println("\n4. No solution:");
        int[] nums4 = {2, 4, 6};
        int target4 = 7;
        List<List<Integer>> res4 = combinationSum2(nums4, target4);
        System.out.println("Result: " + res4);
        System.out.println("Expected: []");
        
        // Test 5: Empty combination
        System.out.println("\n5. Target = 0 (empty combination):");
        int[] nums5 = {1, 2, 3};
        int target5 = 0;
        List<List<Integer>> res5 = combinationSum2(nums5, target5);
        System.out.println("Result: " + res5);
        System.out.println("Expected: [[]] (empty combination)");
        
        // Test 6: Large numbers
        System.out.println("\n6. Large numbers with duplicates:");
        int[] nums6 = {5, 5, 10, 10, 15, 15};
        int target6 = 20;
        List<List<Integer>> res6 = combinationSum2(nums6, target6);
        System.out.println("Result: " + res6);
        System.out.println("Expected: [[5,5,10], [5,15], [10,10]]");
    }
    
    /**
     * Compare with Combination Sum I
     */
    public static void compareWithComboSum1() {
        System.out.println("\n=== Comparison with Combination Sum I ===");
        
        int[] candidates = {2, 3, 5};
        int target = 8;
        
        System.out.println("Same candidates: " + Arrays.toString(candidates));
        System.out.println("Same target: " + target);
        
        System.out.println("\nCombination Sum I (unlimited use):");
        System.out.println("Can use same number multiple times");
        System.out.println("Result would be: [[2,2,2,2], [2,3,3], [3,5]]");
        
        System.out.println("\nCombination Sum II (use at most once):");
        System.out.println("Each number can be used at most once");
        System.out.println("Result is: [[3,5]]");
        
        System.out.println("\nKey difference: In backtracking call:");
        System.out.println("ComboSum1: backtrack(i, ...)  // Stay at same index");
        System.out.println("ComboSum2: backtrack(i+1, ...) // Move to next index");
    }
    
    public static void main(String[] args) {
        System.out.println("=== Combination Sum II (Each Candidate Used Once) ===\n");
        
        // Basic example with detailed explanation
        System.out.println("Basic Example: candidates=[10,1,2,7,6,1,5], target=8");
        int[] candidates = {10, 1, 2, 7, 6, 1, 5};
        int target = 8;
        
        List<List<Integer>> result = combinationSum2(candidates, target);
        System.out.println("\nFinal Result: " + result);
        
        // Visualize decision tree
        visualizeDecisionTree(candidates, target);
        
        // Demonstrate duplicate handling
        demonstrateDuplicateHandling();
        
        // Test different scenarios
        testScenarios();
        
        // Performance comparison
        performanceComparison();
        
        // Compare with Combination Sum I
        compareWithComboSum1();
        
        // Mathematical analysis
        System.out.println("\n=== Mathematical Analysis ===");
        System.out.println("\nWhy sorting is crucial:");
        System.out.println("1. Groups duplicates together for easy skipping");
        System.out.println("2. Enables pruning: if nums[i] > target, break");
        System.out.println("3. Makes duplicate detection simple: nums[i] == nums[i-1]");
        
        System.out.println("\nDuplicate handling logic explained:");
        System.out.println("Condition: if (i > start && nums[i] == nums[i-1]) continue");
        System.out.println("Why i > start (not i > 0):");
        System.out.println("- At start position, we want to include the element");
        System.out.println("- At same level (same start), skip duplicates");
        System.out.println("- At different levels (different start), allow same value");
        
        System.out.println("\nExample: nums = [1, 1, 2], target = 3");
        System.out.println("Tree traversal:");
        System.out.println("Level 1 (start=0):");
        System.out.println("  Take nums[0]=1");
        System.out.println("  Skip nums[1]=1 (duplicate at same level)");
        System.out.println("Level 2 (after taking first 1, start=1):");
        System.out.println("  Can take nums[1]=1 (now i=1 == start=1, not duplicate at this level)");
        
        System.out.println("\nTime Complexity:");
        System.out.println("Worst case: O(2^N) where N = number of candidates");
        System.out.println("We explore a binary decision tree of depth N");
        System.out.println("Each node: take or skip current candidate");
        
        System.out.println("\nSpace Complexity:");
        System.out.println("O(N) for recursion stack depth");
        System.out.println("O(K * L) for result storage where:");
        System.out.println("  K = number of valid combinations");
        System.out.println("  L = average length of combination");
        
        System.out.println("\n=== Optimization Techniques ===");
        System.out.println("1. Sorting: Critical for pruning and duplicate handling");
        System.out.println("2. Pruning: Break when nums[i] > target (sorted array)");
        System.out.println("3. Duplicate skipping: if(i > start && nums[i] == nums[i-1])");
        System.out.println("4. Frequency counting: When many duplicates, precompute counts");
        
        System.out.println("\n=== Common Interview Questions ===");
        System.out.println("Q: Why do we need to sort the array?");
        System.out.println("A: To group duplicates and enable pruning");
        
        System.out.println("\nQ: Explain the condition 'i > start && nums[i] == nums[i-1]'");
        System.out.println("A: Skips duplicates at the same recursion level");
        System.out.println("   but allows same value at different levels");
        
        System.out.println("\nQ: What happens if we don't sort?");
        System.out.println("A: We can't prune efficiently and duplicate handling becomes complex");
        
        System.out.println("\nQ: How is this different from subset sum?");
        System.out.println("A: Subset sum finds ANY subset that sums to target");
        System.out.println("   This finds ALL UNIQUE subsets that sum to target");
        
        System.out.println("\n=== Real-World Applications ===");
        System.out.println("1. Resource allocation with limited quantities");
        System.out.println("2. Investment portfolio selection (limited shares)");
        System.out.println("3. Course selection with credit limits");
        System.out.println("4. Shopping cart with budget and item limits");
        
        System.out.println("\n=== Key Insights ===");
        System.out.println("1. Backtracking with for-loop from start index");
        System.out.println("2. Sort first for pruning and duplicate handling");
        System.out.println("3. Skip duplicates at same level: i > start && nums[i] == nums[i-1]");
        System.out.println("4. Prune when candidate > remaining target (sorted array)");
        System.out.println("5. Move to next index (i+1) when taking element (no reuse)");
    }
}