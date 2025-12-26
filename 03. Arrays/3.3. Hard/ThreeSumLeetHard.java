// Problem: LeetCode <ID>. <Title>
import java.util.*;

// LeetCode 3Sum
// Problem Statement: Given an integer array nums, return all the triplets 
// [nums[i], nums[j], nums[k]] such that i != j != k, and nums[i] + nums[j] + nums[k] == 0.
// The solution set must not contain duplicate triplets.

public class ThreeSumLeetHard {

    /**
     * Finds all unique triplets in the array that sum to zero.
     * Time Complexity: O(n²) - Due to nested loops over positive/negative lists
     * Space Complexity: O(n) - For storing separate lists and hash sets
     * 
     * @param nums Input array of integers
     * @return List of unique triplets that sum to zero
     */
    public List<List<Integer>> threeSum(int[] nums) {
        // Using HashSet to automatically handle duplicate triplets
        // Each triplet is sorted before adding, so same triplets will be filtered out
        Set<List<Integer>> res = new HashSet<>();
        
        // Separate numbers into three categories for more efficient processing
        List<Integer> n = new ArrayList<>();  // Negative numbers (less than 0)
        List<Integer> p = new ArrayList<>();  // Positive numbers (greater than 0)
        List<Integer> z = new ArrayList<>();  // Zeros (equal to 0)
        
        // Categorize each number - O(n)
        for (int v : nums) {
            if (v > 0) p.add(v);
            else if (v < 0) n.add(v);
            else z.add(v);
        }
        
        // Convert to HashSet for O(1) lookups
        Set<Integer> N = new HashSet<>(n);
        Set<Integer> P = new HashSet<>(p);

        // Case 1: Handle triplets containing zero
        // For a valid triplet with zero, we need -a and +a where a is positive
        if (!z.isEmpty()) {
            for (int num : P) {
                if (N.contains(-num)) {
                    // Found pair (-num, 0, num) that sums to zero
                    List<Integer> t = Arrays.asList(-num, 0, num);
                    Collections.sort(t);  // Sort to ensure consistent representation
                    res.add(t);
                }
            }
        }
        
        // Case 2: Three zeros (if we have at least 3 zeros)
        if (z.size() >= 3) {
            res.add(Arrays.asList(0, 0, 0));
        }
        
        // Case 3: Two negatives and one positive
        // We look for two negatives whose sum can be cancelled by a positive
        for (int i = 0; i < n.size(); i++) {
            for (int j = i + 1; j < n.size(); j++) {
                int tVal = -1 * (n.get(i) + n.get(j));  // Required positive value
                if (P.contains(tVal)) {
                    List<Integer> t = Arrays.asList(n.get(i), n.get(j), tVal);
                    Collections.sort(t);
                    res.add(t);
                }
            }
        }
        
        // Case 4: Two positives and one negative
        // We look for two positives whose sum can be cancelled by a negative
        for (int i = 0; i < p.size(); i++) {
            for (int j = i + 1; j < p.size(); j++) {
                int tVal = -1 * (p.get(i) + p.get(j));  // Required negative value
                if (N.contains(tVal)) {
                    List<Integer> t = Arrays.asList(p.get(i), p.get(j), tVal);
                    Collections.sort(t);
                    res.add(t);
                }
            }
        }
        
        // Convert HashSet to ArrayList for return
        return new ArrayList<>(res);
    }
    
    /**
     * Alternative optimized solution using sorting and two-pointer approach
     * More efficient and commonly used in interviews
     * Time Complexity: O(n²), Space Complexity: O(1) excluding output
     */
    public List<List<Integer>> threeSumOptimized(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);  // Sort array first
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate elements for i
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates for left pointer
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    // Skip duplicates for right pointer
                    while (left < right && nums[right] == nums[right - 1]) right--;
                    
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;  // Need larger sum
                } else {
                    right--; // Need smaller sum
                }
            }
        }
        return result;
    }
    
    public static void main(String[] args) {
        ThreeSumLeetHard solver = new ThreeSumLeetHard();
        
        // Test cases
        int[] test1 = {-1, 0, 1, 2, -1, -4};
        System.out.println("Test 1: " + solver.threeSum(test1));
        // Expected: [[-1,-1,2], [-1,0,1]]
        
        int[] test2 = {0, 0, 0};
        System.out.println("Test 2: " + solver.threeSum(test2));
        // Expected: [[0,0,0]]
        
        int[] test3 = {};
        System.out.println("Test 3: " + solver.threeSum(test3));
        // Expected: []
    }
}

// Key Insights:
// 1. The solution separates numbers into negative, positive, and zero for more targeted processing
// 2. Using HashSet ensures automatic deduplication of triplets
// 3. Four main cases to consider:
//    a) Zero + positive + negative pairs
//    b) Three zeros
//    c) Two negatives + one positive
//    d) Two positives + one negative
// 4. Time complexity is O(n²) in worst case when all numbers are positive or all negative
// 5. The optimized two-pointer solution is generally preferred for:
//    - Better constant factors
//    - No need for extra space (except output)
//    - More elegant and interview-friendly

// Edge Cases to Consider:
// 1. Empty array or array with less than 3 elements
// 2. All zeros
// 3. All positive or all negative numbers
// 4. Large arrays with many duplicates
// 5. Arrays with Integer.MIN_VALUE and Integer.MAX_VALUE values

// Common Follow-up Questions:
// 1. How would you modify for 4Sum or kSum?
// 2. Can you solve with O(1) extra space?
// 3. How to handle very large arrays that don't fit in memory?
