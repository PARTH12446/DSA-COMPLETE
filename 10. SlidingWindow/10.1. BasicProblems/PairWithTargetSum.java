import java.util.*;

/**
 * Pair with Target Sum (Two Sum Variant)
 * 
 * Problem: Given an array of integers and a target sum,
 * determine if there exists a pair of elements that sum to the target.
 * 
 * Example: nums = [2, 7, 11, 15], target = 9
 * Pair exists: 2 + 7 = 9
 * 
 * Approaches:
 * 1. Brute force: O(n²) - Check all pairs
 * 2. HashSet: O(n) - Store complements, O(n) space
 * 3. Sorting + Two-pointer: O(n log n) time, O(1) space if sorted in-place
 * 4. Two-pointer on sorted array: O(n) after sorting (implemented)
 * 
 * Note: If indices are needed (Two Sum problem), use HashMap approach.
 * If just existence check and array can be sorted, two-pointer is efficient.
 * 
 * Time Complexity: O(n log n) due to sorting, O(n) for two-pointer pass
 * Space Complexity: O(1) if sorted in-place, O(n) if new array created
 */
public class PairWithTargetSum {
    
    /**
     * Checks if array contains a pair that sums to target.
     * Uses sorting + two-pointer technique.
     * 
     * Algorithm:
     * 1. Sort the array (ascending order)
     * 2. Initialize left=0, right=n-1
     * 3. While left < right:
     *    a. Calculate sum = nums[left] + nums[right]
     *    b. If sum == target: return true
     *    c. If sum < target: increment left (need larger sum)
     *    d. If sum > target: decrement right (need smaller sum)
     * 4. Return false if no pair found
     * 
     * Why it works:
     * - Sorting ensures we can systematically explore pairs
     * - If sum is too small, moving left pointer increases sum
     * - If sum is too large, moving right pointer decreases sum
     * - Exploits monotonic property of sorted array
     * 
     * @param nums Array of integers
     * @param target Target sum to find
     * @return true if pair exists, false otherwise
     */
    public boolean hasPairWithSum(int[] nums, int target) {
        // Edge cases
        if (nums == null || nums.length < 2) {
            return false;
        }
        
        // Sort the array (modifies input array)
        Arrays.sort(nums);
        
        int left = 0;
        int right = nums.length - 1;
        
        while (left < right) {
            int currentSum = nums[left] + nums[right];
            
            if (currentSum == target) {
                return true; // Pair found
            } else if (currentSum < target) {
                // Sum is too small, need larger number
                left++;
            } else {
                // Sum is too large, need smaller number
                right--;
            }
        }
        
        return false; // No pair found
    }
    
    /**
     * HashMap approach for Two Sum (returns indices).
     * Most efficient for unsorted arrays when indices are needed.
     * 
     * Algorithm:
     * 1. Create HashMap to store number → index mapping
     * 2. For each number nums[i]:
     *    a. Calculate complement = target - nums[i]
     *    b. If complement exists in map, return indices
     *    c. Otherwise, store nums[i] in map
     * 
     * @param nums Array of integers
     * @param target Target sum
     * @return Array of two indices or empty array if not found
     */
    public int[] twoSumWithIndices(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return new int[0];
        }
        
        Map<Integer, Integer> numToIndex = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            if (numToIndex.containsKey(complement)) {
                return new int[]{numToIndex.get(complement), i};
            }
            
            numToIndex.put(nums[i], i);
        }
        
        return new int[0]; // No pair found
    }
    
    /**
     * HashSet approach for existence check only.
     * More space-efficient than HashMap when indices not needed.
     */
    public boolean hasPairWithSumHashSet(int[] nums, int target) {
        if (nums == null || nums.length < 2) return false;
        
        Set<Integer> seen = new HashSet<>();
        
        for (int num : nums) {
            int complement = target - num;
            
            if (seen.contains(complement)) {
                return true;
            }
            
            seen.add(num);
        }
        
        return false;
    }
    
    /**
     * Variation: Count all pairs that sum to target.
     * Handles duplicate elements correctly.
     */
    public int countPairsWithSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) return 0;
        
        Arrays.sort(nums);
        int count = 0;
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[left] + nums[right];
            
            if (sum == target) {
                // Handle duplicates
                if (nums[left] == nums[right]) {
                    // All elements between left and right are the same
                    int n = right - left + 1;
                    count += n * (n - 1) / 2; // Combination C(n,2)
                    break;
                } else {
                    // Count duplicates on both sides
                    int leftCount = 1, rightCount = 1;
                    
                    while (left + 1 < right && nums[left] == nums[left + 1]) {
                        leftCount++;
                        left++;
                    }
                    
                    while (right - 1 > left && nums[right] == nums[right - 1]) {
                        rightCount++;
                        right--;
                    }
                    
                    count += leftCount * rightCount;
                    left++;
                    right--;
                }
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return count;
    }
    
    /**
     * Variation: Find all unique pairs (values, not indices).
     */
    public List<int[]> findAllUniquePairs(int[] nums, int target) {
        List<int[]> result = new ArrayList<>();
        if (nums == null || nums.length < 2) return result;
        
        Arrays.sort(nums);
        int left = 0, right = nums.length - 1;
        
        while (left < right) {
            int sum = nums[left] + nums[right];
            
            if (sum == target) {
                result.add(new int[]{nums[left], nums[right]});
                
                // Skip duplicates
                while (left < right && nums[left] == nums[left + 1]) left++;
                while (left < right && nums[right] == nums[right - 1]) right--;
                
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        
        return result;
    }
    
    /**
     * Brute force solution for verification (O(n²)).
     */
    public boolean hasPairWithSumBruteForce(int[] nums, int target) {
        if (nums == null || nums.length < 2) return false;
        
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Visualization helper to show the two-pointer process.
     */
    public void visualizeTwoPointer(int[] nums, int target) {
        System.out.println("\n=== Two-Pointer Technique Visualization ===");
        System.out.println("Array (sorted): " + Arrays.toString(nums));
        System.out.println("Target sum: " + target);
        System.out.println();
        
        System.out.println("Step | Left | Right | nums[left] | nums[right] | Sum | Action");
        System.out.println("-----|------|-------|------------|-------------|-----|--------");
        
        // Create copy to avoid modifying original
        int[] sorted = nums.clone();
        Arrays.sort(sorted);
        
        int left = 0, right = sorted.length - 1;
        int step = 1;
        
        while (left < right) {
            int sum = sorted[left] + sorted[right];
            String action;
            
            if (sum == target) {
                action = "FOUND! " + sorted[left] + " + " + sorted[right] + " = " + target;
                System.out.printf("%4d | %4d | %5d | %10d | %11d | %3d | %s%n",
                    step, left, right, sorted[left], sorted[right], sum, action);
                break;
            } else if (sum < target) {
                action = "Sum too small, move left pointer right";
                System.out.printf("%4d | %4d | %5d | %10d | %11d | %3d | %s%n",
                    step, left, right, sorted[left], sorted[right], sum, action);
                left++;
            } else {
                action = "Sum too large, move right pointer left";
                System.out.printf("%4d | %4d | %5d | %10d | %11d | %3d | %s%n",
                    step, left, right, sorted[left], sorted[right], sum, action);
                right--;
            }
            step++;
        }
        
        if (left >= right) {
            System.out.println("\nNo pair found with sum " + target);
        }
    }
    
    /**
     * Test cases for pair with target sum problem.
     */
    public static void runTestCases() {
        PairWithTargetSum solver = new PairWithTargetSum();
        
        System.out.println("=== Pair with Target Sum Test Cases ===\n");
        
        // Test 1: Standard case
        int[] test1 = {2, 7, 11, 15};
        int target1 = 9;
        System.out.println("Test 1:");
        System.out.println("nums = " + Arrays.toString(test1) + ", target = " + target1);
        boolean result1 = solver.hasPairWithSum(test1, target1);
        System.out.println("Result: " + result1);
        System.out.println("Expected: true (2 + 7 = 9)");
        
        // Verify with brute force
        boolean brute1 = solver.hasPairWithSumBruteForce(test1, target1);
        System.out.println("Brute force: " + brute1 + " (matches: " + (result1 == brute1) + ")");
        System.out.println();
        
        // Test 2: No pair exists
        int[] test2 = {1, 2, 3, 4};
        int target2 = 8;
        System.out.println("Test 2:");
        System.out.println("nums = " + Arrays.toString(test2) + ", target = " + target2);
        boolean result2 = solver.hasPairWithSum(test2, target2);
        System.out.println("Result: " + result2);
        System.out.println("Expected: false");
        System.out.println();
        
        // Test 3: Duplicate elements
        int[] test3 = {3, 3, 4, 5};
        int target3 = 6;
        System.out.println("Test 3 (with duplicates):");
        System.out.println("nums = " + Arrays.toString(test3) + ", target = " + target3);
        boolean result3 = solver.hasPairWithSum(test3, target3);
        System.out.println("Result: " + result3);
        System.out.println("Expected: true (3 + 3 = 6)");
        System.out.println();
        
        // Test 4: Negative numbers
        int[] test4 = {-1, 2, 3, 4, 7};
        int target4 = 6;
        System.out.println("Test 4 (with negatives):");
        System.out.println("nums = " + Arrays.toString(test4) + ", target = " + target4);
        boolean result4 = solver.hasPairWithSum(test4, target4);
        System.out.println("Result: " + result4);
        System.out.println("Expected: true (-1 + 7 = 6)");
        System.out.println();
        
        // Test 5: Empty array
        int[] test5 = {};
        int target5 = 10;
        System.out.println("Test 5 (empty array):");
        System.out.println("nums = [], target = " + target5);
        boolean result5 = solver.hasPairWithSum(test5, target5);
        System.out.println("Result: " + result5);
        System.out.println("Expected: false");
        System.out.println();
        
        // Test 6: Single element
        int[] test6 = {5};
        int target6 = 5;
        System.out.println("Test 6 (single element):");
        System.out.println("nums = " + Arrays.toString(test6) + ", target = " + target6);
        boolean result6 = solver.hasPairWithSum(test6, target6);
        System.out.println("Result: " + result6);
        System.out.println("Expected: false (need at least 2 elements)");
        System.out.println();
        
        // Test counting pairs
        System.out.println("Test 7: Count all pairs (with duplicates)");
        int[] test7 = {1, 1, 1, 1};
        int target7 = 2;
        int count = solver.countPairsWithSum(test7, target7);
        System.out.println("nums = " + Arrays.toString(test7) + ", target = " + target7);
        System.out.println("Number of pairs: " + count);
        System.out.println("Expected: 6 (C(4,2) = 6)");
        System.out.println();
        
        // Test finding all unique pairs
        System.out.println("Test 8: Find all unique pairs");
        int[] test8 = {1, 1, 2, 2, 3, 4};
        int target8 = 5;
        List<int[]> pairs = solver.findAllUniquePairs(test8, target8);
        System.out.println("nums = " + Arrays.toString(test8) + ", target = " + target8);
        System.out.println("Unique pairs: ");
        for (int[] pair : pairs) {
            System.out.println("  " + pair[0] + " + " + pair[1] + " = " + target8);
        }
        System.out.println("Expected: (1,4) and (2,3)");
    }
    
    /**
     * Performance comparison between different approaches.
     */
    public static void benchmark() {
        PairWithTargetSum solver = new PairWithTargetSum();
        
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test array
        int n = 1000000;
        int[] nums = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            nums[i] = rand.nextInt(1000000) - 500000; // Range: -500k to 499k
        }
        int target = rand.nextInt(2000000) - 1000000; // Random target
        
        System.out.println("Array size: " + n);
        
        // Two-pointer (with sorting)
        long start = System.currentTimeMillis();
        boolean result1 = solver.hasPairWithSum(nums, target);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Two-pointer (with sort): " + time1 + " ms, result: " + result1);
        
        // HashSet approach
        start = System.currentTimeMillis();
        boolean result2 = solver.hasPairWithSumHashSet(nums, target);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("HashSet: " + time2 + " ms, result: " + result2);
        
        System.out.println("Results match: " + (result1 == result2));
        System.out.println("\nNote: Two-pointer sorts array (O(n log n)), HashSet is O(n)");
        System.out.println("HashSet uses O(n) space, two-pointer uses O(1) if sorted in-place");
    }
    
    /**
     * Explain the two-pointer technique.
     */
    public static void explainTwoPointer() {
        System.out.println("\n=== Two-Pointer Technique Explained ===");
        System.out.println();
        System.out.println("Why two-pointer works on sorted arrays:");
        System.out.println("  In sorted array [a₁, a₂, ..., aₙ] where a₁ ≤ a₂ ≤ ... ≤ aₙ:");
        System.out.println("  1. Smallest sum = a₁ + a₂");
        System.out.println("  2. Largest sum = aₙ₋₁ + aₙ");
        System.out.println("  3. If we fix one pointer, moving the other changes sum monotonically");
        System.out.println();
        System.out.println("Algorithm intuition:");
        System.out.println("  1. Start with left=0 (smallest), right=n-1 (largest)");
        System.out.println("  2. Calculate sum = nums[left] + nums[right]");
        System.out.println("  3. If sum == target: Found!");
        System.out.println("  4. If sum < target: Need larger sum → move left pointer right");
        System.out.println("  5. If sum > target: Need smaller sum → move right pointer left");
        System.out.println();
        System.out.println("Example: nums = [1, 2, 3, 4, 5], target = 8");
        System.out.println("  Step 1: left=0, right=4 → 1+5=6 < 8 → left=1");
        System.out.println("  Step 2: left=1, right=4 → 2+5=7 < 8 → left=2");
        System.out.println("  Step 3: left=2, right=4 → 3+5=8 == 8 → Found!");
        System.out.println();
        System.out.println("Time Complexity: O(n) after sorting");
        System.out.println("Space Complexity: O(1) if sorted in-place");
    }
    
    /**
     * Show different use cases and variations.
     */
    public static void showVariations() {
        System.out.println("\n=== Variations and Use Cases ===");
        
        PairWithTargetSum solver = new PairWithTargetSum();
        
        // Variation 1: Three Sum
        System.out.println("Variation 1: Three Sum Problem");
        System.out.println("  Find three numbers that sum to target");
        System.out.println("  Can use two-pointer inside nested loop");
        System.out.println("  Time: O(n²) after sorting");
        System.out.println();
        
        // Variation 2: Closest sum
        System.out.println("Variation 2: Closest Sum to Target");
        System.out.println("  Find pair with sum closest to target");
        System.out.println("  Similar two-pointer, track minimum difference");
        System.out.println();
        
        // Variation 3: Multiple pairs
        System.out.println("Variation 3: Find all pairs (not just existence)");
        System.out.println("  Need to handle duplicates carefully");
        System.out.println("  Example with counting:");
        int[] nums = {1, 2, 2, 3, 4};
        int target = 5;
        List<int[]> pairs = solver.findAllUniquePairs(nums, target);
        System.out.println("  nums = " + Arrays.toString(nums) + ", target = " + target);
        System.out.println("  Unique pairs: " + pairs.size());
        for (int[] pair : pairs) {
            System.out.println("    " + pair[0] + " + " + pair[1]);
        }
        System.out.println();
        
        // Use case comparison
        System.out.println("When to use which approach:");
        System.out.println("1. Need indices → HashMap (Two Sum problem)");
        System.out.println("2. Just existence check, array can be sorted → Two-pointer");
        System.out.println("3. Array already sorted → Definitely two-pointer");
        System.out.println("4. Memory constrained → Two-pointer (O(1) space)");
        System.out.println("5. Need all pairs → Two-pointer with duplicate handling");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize the algorithm
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Detailed Visualization of Two-Pointer Technique:");
        System.out.println("=".repeat(60));
        
        PairWithTargetSum solver = new PairWithTargetSum();
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        solver.visualizeTwoPointer(nums, target);
        
        // Explain the technique
        explainTwoPointer();
        
        // Show variations
        showVariations();
        
        // Run benchmark (optional)
        // benchmark();
    }
}