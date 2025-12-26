// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: 4Sum (LeetCode 18)
 * 
 * Given an array 'nums' of n integers, return an array of all the 
 * unique quadruplets [nums[a], nums[b], nums[c], nums[d]] such that:
 * 
 * 1. 0 <= a, b, c, d < n
 * 2. a, b, c, and d are distinct
 * 3. nums[a] + nums[b] + nums[c] + nums[d] == target
 * 4. You may return the answer in any order.
 * 
 * NOTE: The solution set must not contain duplicate quadruplets.
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 200
 * - -10^9 <= nums[i] <= 10^9
 * - -10^9 <= target <= 10^9
 * 
 * APPROACH: Generalized Two Pointers with Fixed First Two Elements
 * 
 * INTUITION:
 * 1. Sort the array to enable two-pointer search and duplicate removal
 * 2. Fix first two indices (i, j) with nested loops
 * 3. Use two-pointer technique for remaining two indices (k, l)
 * 4. Skip duplicates at each level to ensure unique quadruplets
 * 
 * TIME COMPLEXITY: O(n³)
 *   - Outer loop (i): O(n)
 *   - Inner loop (j): O(n) 
 *   - Two-pointer (k, l): O(n)
 *   - Total: O(n³) worst case
 * 
 * SPACE COMPLEXITY: O(1) [excluding output storage]
 *   - Sorting uses O(log n) space (Java's TimSort)
 *   - Output list may contain O(n³) quadruplets in worst case
 * 
 * KEY OPTIMIZATIONS:
 * 1. Early pruning: Skip impossible i/j values
 * 2. Duplicate skipping: At i, j, k, l levels
 * 3. Long arithmetic: Prevent integer overflow
 * 
 * DIFFERENCES FROM CODING NINJAS VERSION:
 * - Same algorithm, different class structure
 * - LeetCode expects List<List<Integer>> return type
 * - May have different edge cases
 */

import java.util.*;

public class FourSumLeetHard {

    /**
     * Main solution method for LeetCode 4Sum problem.
     * 
     * @param nums   Input array of integers
     * @param target Target sum for quadruplets
     * @return List of unique quadruplets that sum to target
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        
        // Edge case: not enough elements
        if (n < 4) {
            return ans;
        }
        
        // Step 1: Sort the array (enables two-pointer and duplicate skipping)
        Arrays.sort(nums);
        
        // Step 2: Fix first two elements
        for (int i = 0; i < n - 3; i++) {
            // Skip duplicate values for i
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            // Early optimization 1: Smallest possible sum with nums[i] > target
            long minSumI = (long) nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3];
            if (minSumI > target) {
                break; // No possible quadruplets with this or larger i
            }
            
            // Early optimization 2: Largest possible sum with nums[i] < target
            long maxSumI = (long) nums[i] + nums[n - 3] + nums[n - 2] + nums[n - 1];
            if (maxSumI < target) {
                continue; // Skip this i, try next
            }
            
            for (int j = i + 1; j < n - 2; j++) {
                // Skip duplicate values for j
                // j != i+1 ensures we don't skip first element after i
                if (j != i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                
                // Early optimization for j
                long minSumJ = (long) nums[i] + nums[j] + nums[j + 1] + nums[j + 2];
                if (minSumJ > target) {
                    break; // No possible quadruplets with this or larger j
                }
                
                long maxSumJ = (long) nums[i] + nums[j] + nums[n - 2] + nums[n - 1];
                if (maxSumJ < target) {
                    continue; // Skip this j, try next
                }
                
                // Step 3: Two-pointer search for remaining two elements
                int k = j + 1;
                int l = n - 1;
                
                while (k < l) {
                    // Use long to prevent integer overflow
                    long sum = (long) nums[i] + nums[j] + nums[k] + nums[l];
                    
                    if (sum == target) {
                        // Found valid quadruplet
                        ans.add(Arrays.asList(nums[i], nums[j], nums[k], nums[l]));
                        
                        // Move both pointers inward
                        k++;
                        l--;
                        
                        // Skip duplicate values for k
                        while (k < l && nums[k] == nums[k - 1]) {
                            k++;
                        }
                        
                        // Skip duplicate values for l
                        while (k < l && nums[l] == nums[l + 1]) {
                            l--;
                        }
                    } 
                    else if (sum < target) {
                        // Sum too small, increase k to increase sum
                        k++;
                        
                        // Optional: skip duplicates while sum < target
                        while (k < l && nums[k] == nums[k - 1]) {
                            k++;
                        }
                    } 
                    else {
                        // Sum too large, decrease l to decrease sum
                        l--;
                        
                        // Optional: skip duplicates while sum > target
                        while (k < l && nums[l] == nums[l + 1]) {
                            l--;
                        }
                    }
                }
            }
        }
        
        return ans;
    }
    
    /**
     * Alternative: k-Sum recursive solution (more general)
     * Reduces 4-Sum to 2-Sum recursively
     */
    public List<List<Integer>> fourSumRecursive(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, target, 0, 4);
    }
    
    private List<List<Integer>> kSum(int[] nums, long target, int start, int k) {
        List<List<Integer>> res = new ArrayList<>();
        
        // If we have run out of numbers to add, return res
        if (start == nums.length) {
            return res;
        }
        
        // Check if we can find target with k numbers
        // There are two checks:
        // 1. The smallest possible sum (k * smallest number) is greater than target
        // 2. The largest possible sum (k * largest number) is less than target
        long average_value = target / k;
        
        // We need to use long to avoid overflow
        if (nums[start] > average_value || average_value > nums[nums.length - 1]) {
            return res;
        }
        
        // Base case: 2-Sum
        if (k == 2) {
            return twoSum(nums, target, start);
        }
        
        // Recursive case
        for (int i = start; i < nums.length; ++i) {
            // Skip duplicates
            if (i == start || nums[i] != nums[i - 1]) {
                // Recursively find (k-1)-Sum
                for (List<Integer> subset : kSum(nums, target - nums[i], i + 1, k - 1)) {
                    List<Integer> current = new ArrayList<>();
                    current.add(nums[i]);
                    current.addAll(subset);
                    res.add(current);
                }
            }
        }
        
        return res;
    }
    
    private List<List<Integer>> twoSum(int[] nums, long target, int start) {
        List<List<Integer>> res = new ArrayList<>();
        int lo = start, hi = nums.length - 1;
        
        while (lo < hi) {
            long currSum = (long) nums[lo] + nums[hi];
            if (currSum < target || (lo > start && nums[lo] == nums[lo - 1])) {
                ++lo;
            } else if (currSum > target || (hi < nums.length - 1 && nums[hi] == nums[hi + 1])) {
                --hi;
            } else {
                res.add(Arrays.asList(nums[lo++], nums[hi--]));
            }
        }
        
        return res;
    }
    
    /**
     * HashMap-based solution (alternative approach)
     * Time: O(n²) average, O(n³) worst case (due to duplicates)
     * Space: O(n²) for storing pair sums
     */
    public List<List<Integer>> fourSumHashMap(int[] nums, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;
        if (n < 4) return ans;
        
        Arrays.sort(nums);
        Map<Integer, List<int[]>> map = new HashMap<>();
        
        // Store all pair sums and their indices
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                int sum = nums[i] + nums[j];
                map.putIfAbsent(sum, new ArrayList<>());
                map.get(sum).add(new int[]{i, j});
            }
        }
        
        Set<String> seen = new HashSet<>();
        
        for (int i = 0; i < n - 3; i++) {
            // Skip duplicates
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            for (int j = i + 1; j < n - 2; j++) {
                // Skip duplicates
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                
                long needed = (long) target - nums[i] - nums[j];
                
                // Check if needed sum exists in our map
                if (map.containsKey((int) needed)) {
                    for (int[] pair : map.get((int) needed)) {
                        int k = pair[0];
                        int l = pair[1];
                        
                        // Ensure indices are in increasing order and distinct
                        if (k > j) {
                            List<Integer> quad = Arrays.asList(nums[i], nums[j], nums[k], nums[l]);
                            // Convert to string for duplicate checking
                            String key = quad.toString();
                            
                            if (!seen.contains(key)) {
                                ans.add(quad);
                                seen.add(key);
                            }
                        }
                    }
                }
            }
        }
        
        return ans;
    }
    
    /**
     * Test method with LeetCode examples
     */
    public static void main(String[] args) {
        FourSumLeetHard solver = new FourSumLeetHard();
        
        // Test case 1: Example from LeetCode
        int[] nums1 = {1, 0, -1, 0, -2, 2};
        int target1 = 0;
        System.out.println("Test 1 - LeetCode Example:");
        System.out.println("Input: nums = [1,0,-1,0,-2,2], target = 0");
        List<List<Integer>> result1 = solver.fourSum(nums1, target1);
        System.out.println("Output: " + result1);
        // Expected: [[-2,-1,1,2], [-2,0,0,2], [-1,0,0,1]]
        
        // Test case 2: All zeros
        int[] nums2 = {0, 0, 0, 0};
        int target2 = 0;
        System.out.println("\nTest 2 - All zeros:");
        System.out.println("Input: nums = [0,0,0,0], target = 0");
        List<List<Integer>> result2 = solver.fourSum(nums2, target2);
        System.out.println("Output: " + result2);
        // Expected: [[0,0,0,0]]
        
        // Test case 3: Large values with potential overflow
        int[] nums3 = {1000000000, 1000000000, 1000000000, 1000000000};
        int target3 = -294967296;
        System.out.println("\nTest 3 - Large values (overflow test):");
        System.out.println("Input: nums = [10^9,10^9,10^9,10^9], target = -294967296");
        List<List<Integer>> result3 = solver.fourSum(nums3, target3);
        System.out.println("Output: " + result3);
        // Expected: [] (no solution due to overflow handling)
        
        // Test case 4: Mixed case
        int[] nums4 = {-3, -2, -1, 0, 0, 1, 2, 3};
        int target4 = 0;
        System.out.println("\nTest 4 - Mixed positive/negative:");
        System.out.println("Input: nums = [-3,-2,-1,0,0,1,2,3], target = 0");
        List<List<Integer>> result4 = solver.fourSum(nums4, target4);
        System.out.println("Output: " + result4);
        // Expected: Multiple quadruplets
        
        // Test case 5: Edge case - not enough elements
        int[] nums5 = {1, 2, 3};
        int target5 = 6;
        System.out.println("\nTest 5 - Not enough elements:");
        System.out.println("Input: nums = [1,2,3], target = 6");
        List<List<Integer>> result5 = solver.fourSum(nums5, target5);
        System.out.println("Output: " + result5);
        // Expected: [] (need at least 4 elements)
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        Random rand = new Random();
        int[] largeNums = new int[200];
        for (int i = 0; i < largeNums.length; i++) {
            largeNums[i] = rand.nextInt(200) - 100; // Range: -100 to 100
        }
        int largeTarget = rand.nextInt(200) - 100;
        
        long startTime = System.currentTimeMillis();
        List<List<Integer>> perfResult = solver.fourSum(largeNums, largeTarget);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Array size: 200");
        System.out.println("Time taken: " + (endTime - startTime) + "ms");
        System.out.println("Number of quadruplets found: " + perfResult.size());
        
        // Compare with recursive approach
        startTime = System.currentTimeMillis();
        List<List<Integer>> recursiveResult = solver.fourSumRecursive(largeNums, largeTarget);
        endTime = System.currentTimeMillis();
        System.out.println("Recursive approach time: " + (endTime - startTime) + "ms");
        System.out.println("Results match: " + perfResult.equals(recursiveResult));
    }
    
    /**
     * Why the two-pointer approach works:
     * 
     * After sorting and fixing i and j:
     * 1. We have reduced problem to 2-Sum on subarray [j+1, n-1]
     * 2. For 2-Sum on sorted array, two-pointer is optimal
     * 3. Pointer k starts from j+1 (smallest remaining)
     * 4. Pointer l starts from n-1 (largest remaining)
     * 5. Based on sum comparison:
     *    - sum < target ? need larger sum ? move k right
     *    - sum > target ? need smaller sum ? move l left
     *    - sum == target ? found solution, move both
     */
    
    /**
     * Duplicate handling explanation:
     * 
     * At i level: Skip if nums[i] == nums[i-1]
     *   Why? Because any quadruplet starting with nums[i] would be
     *   identical to one starting with nums[i-1]
     * 
     * At j level: Skip if j != i+1 AND nums[j] == nums[j-1]
     *   Why j != i+1? Because when j = i+1, it's the first element
     *   after i, and we shouldn't skip it even if it equals nums[i]
     * 
     * At k level: After finding a match, skip while nums[k] == nums[k-1]
     *   Why? Avoid duplicate k values in the same (i,j) pair
     * 
     * At l level: After finding a match, skip while nums[l] == nums[l+1]
     *   Why? Avoid duplicate l values in the same (i,j) pair
     */
    
    /**
     * Early termination optimizations:
     * 
     * 1. For fixed i:
     *    minSum = nums[i] + nums[i+1] + nums[i+2] + nums[i+3]
     *    if minSum > target ? break (all future i will be larger)
     *    
     *    maxSum = nums[i] + nums[n-3] + nums[n-2] + nums[n-1]
     *    if maxSum < target ? continue (try next i)
     * 
     * 2. For fixed (i, j):
     *    minSum = nums[i] + nums[j] + nums[j+1] + nums[j+2]
     *    if minSum > target ? break (all future j will be larger)
     *    
     *    maxSum = nums[i] + nums[j] + nums[n-2] + nums[n-1]
     *    if maxSum < target ? continue (try next j)
     */
    
    /**
     * Important implementation details:
     * 
     * 1. Use long for sum calculations:
     *    - nums[i] can be up to 10^9
     *    - Sum of 4 such numbers can exceed 32-bit integer range
     *    - Example: 10^9 * 4 = 4×10^9 > 2^31-1
     * 
     * 2. Loop bounds:
     *    - i: 0 to n-4 (need 3 more elements)
     *    - j: i+1 to n-3 (need 2 more elements)
     *    - k: j+1 to l-1 (two-pointer range)
     * 
     * 3. Result ordering:
     *    - Quadruplets will be in sorted order due to array sorting
     *    - Individual quadruplets will have elements in non-decreasing order
     */
    
    /**
     * Common pitfalls to avoid:
     * 
     * 1. Forgetting to sort the array
     * 2. Incorrect duplicate skipping conditions
     * 3. Integer overflow in sum calculations
     * 4. Not checking array bounds (n < 4 case)
     * 5. Using int instead of long for target comparisons
     */
    
    /**
     * Related LeetCode problems:
     * 
     * 1. Two Sum (1) - O(n) with hash map
     * 2. 3Sum (15) - O(n²) with sorting and two-pointer
     * 3. 3Sum Closest (16) - Similar to 3Sum but find closest sum
     * 4. 4Sum II (454) - Count tuples from 4 arrays that sum to 0
     * 5. k-Sum (General problem) - Recursive reduction to 2-Sum
     */
    
    /**
     * Real-world applications:
     * 
     * 1. Combinatorial optimization problems
     * 2. Financial portfolio selection (4-asset combinations)
     * 3. Bioinformatics (finding molecular combinations)
     * 4. Game theory (resource allocation strategies)
     */
}
