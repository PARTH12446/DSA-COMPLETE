import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Subsets II: Generate all possible subsets (power set) while handling duplicate elements.
 * Given an integer array nums that may contain duplicates, return all possible subsets.
 * The solution must not contain duplicate subsets.
 * 
 * Example: nums = [1,2,2]
 * Output: [[], [1], [1,2], [1,2,2], [2], [2,2]]
 * 
 * Time Complexity: O(n * 2^n) where n is length of nums
 * Space Complexity: O(n) for recursion stack + O(n * 2^n) for output storage
 */
public class SubsetSum2 {

    /**
     * Main method to generate all unique subsets from array with duplicates.
     * 
     * @param nums Array that may contain duplicate elements
     * @return List of all unique subsets
     */
    public static List<List<Integer>> subsetsWithDup(int[] nums) {
        // STEP 1: Sort the array - CRITICAL for duplicate handling
        // Sorting brings duplicates together, making them easy to skip
        Arrays.sort(nums);
        
        List<List<Integer>> res = new ArrayList<>();
        backtrack(0, nums, new ArrayList<>(), res);
        return res;
    }

    /**
     * Recursive backtracking function with duplicate handling strategy.
     * 
     * The key insight: When we have duplicates, we want to include them in a controlled way.
     * For duplicates like [2, 2, 2], we want:
     * - Include 0 of them: []
     * - Include 1 of them: [2]
     * - Include 2 of them: [2, 2]
     * - Include 3 of them: [2, 2, 2]
     * But NOT: [2] (from first 2) and [2] (from second 2) as separate subsets
     * 
     * @param start Starting index for current recursion level
     * @param nums Sorted input array
     * @param curr Current subset being built
     * @param res Result list containing all unique subsets
     */
    private static void backtrack(int start, int[] nums, List<Integer> curr, List<List<Integer>> res) {
        // Add current subset to results
        // This happens at EVERY node of the recursion tree, not just leaves
        res.add(new ArrayList<>(curr));
        
        // Explore further elements to add to the subset
        for (int i = start; i < nums.length; i++) {
            // ========== KEY DUPLICATE HANDLING LOGIC ==========
            // Skip duplicates: if current element is same as previous AND 
            // we're not starting a new level (i > start)
            // 
            // Why i > start (not i > 0)?
            // - At each level, we want to allow the first occurrence of a duplicate
            // - i > start ensures we only skip duplicates within the SAME recursive level
            // - Example: nums = [1, 2, 2]
            //   At start=0, i=0: take 1
            //   At start=0, i=1: take first 2
            //   At start=0, i=2: skip second 2 (because nums[2] == nums[1] AND i > start)
            if (i > start && nums[i] == nums[i - 1]) {
                continue; // Skip this duplicate element
            }
            
            // Choose: Add current element to subset
            curr.add(nums[i]);
            
            // Explore: Recurse with next starting index
            backtrack(i + 1, nums, curr, res);
            
            // Unchoose: Backtrack (remove last element)
            curr.remove(curr.size() - 1);
        }
    }

    /**
     * Alternative approach: Count duplicates and handle them systematically.
     * This approach explicitly counts frequencies and handles duplicates more explicitly.
     * 
     * @param nums Array with duplicates
     * @return All unique subsets
     */
    public static List<List<Integer>> subsetsWithDupAlternative(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> curr = new ArrayList<>();
        backtrackWithFreq(0, nums, curr, res);
        return res;
    }

    private static void backtrackWithFreq(int start, int[] nums, List<Integer> curr, List<List<Integer>> res) {
        res.add(new ArrayList<>(curr));
        
        for (int i = start; i < nums.length; i++) {
            // Alternative duplicate skipping logic
            // This checks if we're at the first occurrence of this number in the current level
            boolean isDuplicate = false;
            for (int j = start; j < i; j++) {
                if (nums[j] == nums[i]) {
                    isDuplicate = true;
                    break;
                }
            }
            if (isDuplicate) {
                continue;
            }
            
            // Count how many duplicates of this number we have
            int count = 1;
            while (i + count < nums.length && nums[i + count] == nums[i]) {
                count++;
            }
            
            // Try including 1, 2, ..., count copies of this number
            for (int c = 1; c <= count; c++) {
                for (int k = 0; k < c; k++) {
                    curr.add(nums[i]);
                }
                backtrackWithFreq(i + count, nums, curr, res);
                for (int k = 0; k < c; k++) {
                    curr.remove(curr.size() - 1);
                }
            }
            
            // Skip to next different number
            i += count - 1;
        }
    }

    /**
     * Iterative approach using bitmask with duplicate checking.
     * Generates all 2^n subsets and filters duplicates using a Set.
     * Less efficient but conceptually simple.
     */
    public static List<List<Integer>> subsetsWithDupIterative(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        
        // Use a Set to track seen subsets (converted to string for easy comparison)
        java.util.HashSet<String> seen = new java.util.HashSet<>();
        
        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            
            // Convert subset to string representation
            String key = subset.toString();
            if (!seen.contains(key)) {
                seen.add(key);
                res.add(subset);
            }
        }
        
        return res;
    }

    /**
     * Test driver with comprehensive examples and explanations.
     */
    public static void main(String[] args) {
        System.out.println("=== SUBSETS WITH DUPLICATES DEMO ===\n");
        
        // Test Case 1: Basic example with duplicates
        testCase1();
        
        // Test Case 2: All elements are the same
        testCase2();
        
        // Test Case 3: No duplicates (should work like regular subsets)
        testCase3();
        
        // Test Case 4: Mixed positive and negative with duplicates
        testCase4();
        
        // Compare all three approaches
        compareApproaches();
        
        // Visualize the decision tree
        visualizeDecisionTree();
    }
    
    private static void testCase1() {
        System.out.println("1. BASIC EXAMPLE: nums = [1, 2, 2]");
        int[] nums = {1, 2, 2};
        List<List<Integer>> subsets = subsetsWithDup(nums);
        
        System.out.println("Input (sorted): " + Arrays.toString(nums));
        System.out.println("Number of unique subsets: " + subsets.size());
        System.out.println("Expected: 6 subsets (2^3 = 8 total, minus 2 duplicates)");
        System.out.println("Subsets:");
        for (int i = 0; i < subsets.size(); i++) {
            System.out.println("  " + (i + 1) + ": " + subsets.get(i));
        }
        System.out.println();
    }
    
    private static void testCase2() {
        System.out.println("2. ALL SAME ELEMENTS: nums = [2, 2, 2]");
        int[] nums = {2, 2, 2};
        List<List<Integer>> subsets = subsetsWithDup(nums);
        
        System.out.println("Number of unique subsets: " + subsets.size());
        System.out.println("Subsets:");
        for (List<Integer> subset : subsets) {
            System.out.println("  " + subset);
        }
        System.out.println("Note: Only n+1 = 4 subsets (not 8) because duplicates don't create new combinations");
        System.out.println();
    }
    
    private static void testCase3() {
        System.out.println("3. NO DUPLICATES: nums = [1, 2, 3]");
        int[] nums = {1, 2, 3};
        List<List<Integer>> subsets = subsetsWithDup(nums);
        
        System.out.println("Number of subsets: " + subsets.size());
        System.out.println("Should be exactly 2^3 = 8 subsets (no duplicates to skip)");
        System.out.println();
    }
    
    private static void testCase4() {
        System.out.println("4. MIXED NUMBERS: nums = [-1, 0, -1, 2]");
        int[] nums = {-1, 0, -1, 2};
        Arrays.sort(nums); // Will become [-1, -1, 0, 2]
        List<List<Integer>> subsets = subsetsWithDup(nums);
        
        System.out.println("Sorted input: " + Arrays.toString(nums));
        System.out.println("Number of unique subsets: " + subsets.size());
        System.out.println("Total possible without duplicate handling: 2^4 = 16");
        System.out.println("Actual unique: " + subsets.size());
        System.out.println();
    }
    
    private static void compareApproaches() {
        System.out.println("5. COMPARING DIFFERENT APPROACHES");
        int[] nums = {1, 2, 2, 3};
        
        List<List<Integer>> result1 = subsetsWithDup(nums);
        List<List<Integer>> result2 = subsetsWithDupAlternative(nums);
        List<List<Integer>> result3 = subsetsWithDupIterative(nums);
        
        System.out.println("Input: " + Arrays.toString(nums));
        System.out.println("Backtracking result count: " + result1.size());
        System.out.println("Alternative result count: " + result2.size());
        System.out.println("Iterative result count: " + result3.size());
        
        // Sort all results for comparison
        java.util.Comparator<List<Integer>> comparator = (a, b) -> {
            String s1 = a.toString();
            String s2 = b.toString();
            return s1.compareTo(s2);
        };
        
        result1.sort(comparator);
        result2.sort(comparator);
        result3.sort(comparator);
        
        System.out.println("All methods produce same results: " + 
                          result1.equals(result2) && result2.equals(result3));
        System.out.println();
    }
    
    private static void visualizeDecisionTree() {
        System.out.println("6. DECISION TREE VISUALIZATION for [1, 2, 2]");
        System.out.println("(Sorted as [1, 2, 2])");
        System.out.println();
        System.out.println("                     []");
        System.out.println("                    /  \\");
        System.out.println("               Not 1   Take 1");
        System.out.println("                  |       |");
        System.out.println("                  []     [1]");
        System.out.println("                 / \\     / \\");
        System.out.println("           Not 2  Take2  Not2 Take2");
        System.out.println("             |     |       |     |");
        System.out.println("            []    [2]     [1]   [1,2]");
        System.out.println("           / \\    / \\     / \\     / \\");
        System.out.println("      Not2 Take2 Not2 Take2 Not2 Take2");
        System.out.println("        |    |     |    |     |    |");
        System.out.println("       []  [2]   [2] [2,2]  [1] [1,2]");
        System.out.println("                            (skip)");
        System.out.println();
        System.out.println("Note: The duplicate [2] and [1,2] paths are skipped!");
        System.out.println("The key is we only take a duplicate if we're at the FIRST");
        System.out.println("occurrence of that number in the current recursive level.");
    }
    
    /**
     * Additional utility: Generate subsets of a specific size k with duplicates
     */
    public static List<List<Integer>> kSubsetsWithDup(int[] nums, int k) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        backtrackK(0, nums, k, new ArrayList<>(), res);
        return res;
    }
    
    private static void backtrackK(int start, int[] nums, int k, 
                                  List<Integer> curr, List<List<Integer>> res) {
        if (curr.size() == k) {
            res.add(new ArrayList<>(curr));
            return;
        }
        
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i - 1]) continue;
            
            curr.add(nums[i]);
            backtrackK(i + 1, nums, k, curr, res);
            curr.remove(curr.size() - 1);
        }
    }
}