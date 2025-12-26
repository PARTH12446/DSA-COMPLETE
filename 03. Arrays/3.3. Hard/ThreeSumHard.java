// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: 3-Sum / Triplet Sum (Coding Ninjas variant)
 * 
 * Given an array 'nums' of size 'n', find all unique triplets [nums[i], nums[j], nums[k]]
 * such that:
 *   1. i ? j ? k (all three indices are distinct)
 *   2. nums[i] + nums[j] + nums[k] = 0
 *   3. The solution set must not contain duplicate triplets
 * 
 * CONSTRAINTS:
 * - 3 = n = 3000
 * - -10^5 = nums[i] = 10^5
 * - Array may contain duplicates
 * - Must return unique triplets only
 * 
 * APPROACH: Categorization + Two-pointer / Hash Set
 * 
 * INTUITION:
 * 1. Separate numbers into negative, positive, and zero lists
 * 2. Three cases for sum=0:
 *    a) 0 + positive + negative (where positive = -negative)
 *    b) 0 + 0 + 0 (if at least 3 zeros)
 *    c) Two negatives + one positive (where positive = -(sum of two negatives))
 *    d) Two positives + one negative (where negative = -(sum of two positives))
 * 
 * TIME COMPLEXITY: O(n²) in worst case
 *   - Sorting: O(n log n) [if we sort the lists]
 *   - Pairwise checking: O(n²) for negative and positive lists
 * 
 * SPACE COMPLEXITY: O(n) for storing separate lists
 *   - Plus O(k) for output where k = number of triplets
 */

import java.util.*;

public class ThreeSumHard {

    /**
     * Find all unique triplets that sum to 0
     * 
     * @param n    Size of array (not strictly needed but included in signature)
     * @param nums Input array
     * @return List of unique triplets that sum to 0
     */
    public static List<List<Integer>> triplet(int n, int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        
        // Step 1: Categorize numbers
        List<Integer> negatives = new ArrayList<>();
        List<Integer> positives = new ArrayList<>();
        List<Integer> zeros = new ArrayList<>();
        
        for (int num : nums) {
            if (num > 0) {
                positives.add(num);
            } else if (num < 0) {
                negatives.add(num);
            } else {
                zeros.add(num);
            }
        }
        
        // Convert to sets for O(1) lookup
        Set<Integer> negativeSet = new HashSet<>(negatives);
        Set<Integer> positiveSet = new HashSet<>(positives);
        
        // Step 2: Case 1: 0 + positive + negative (where positive = -negative)
        if (!zeros.isEmpty()) {
            for (int pos : positiveSet) {
                int requiredNeg = -pos;
                if (negativeSet.contains(requiredNeg)) {
                    List<Integer> triplet = Arrays.asList(requiredNeg, 0, pos);
                    Collections.sort(triplet);
                    result.add(triplet);
                }
            }
        }
        
        // Step 3: Case 2: 0 + 0 + 0 (if we have at least 3 zeros)
        if (zeros.size() >= 3) {
            result.add(Arrays.asList(0, 0, 0));
        }
        
        // Step 4: Case 3: Two negatives + one positive
        // For each pair of negatives, check if their sum's complement exists in positives
        for (int i = 0; i < negatives.size(); i++) {
            for (int j = i + 1; j < negatives.size(); j++) {
                int neg1 = negatives.get(i);
                int neg2 = negatives.get(j);
                int requiredPos = -(neg1 + neg2);  // We need positive = -(neg1 + neg2)
                
                if (positiveSet.contains(requiredPos)) {
                    List<Integer> triplet = Arrays.asList(neg1, neg2, requiredPos);
                    Collections.sort(triplet);
                    result.add(triplet);
                }
            }
        }
        
        // Step 5: Case 4: Two positives + one negative
        // For each pair of positives, check if their sum's complement exists in negatives
        for (int i = 0; i < positives.size(); i++) {
            for (int j = i + 1; j < positives.size(); j++) {
                int pos1 = positives.get(i);
                int pos2 = positives.get(j);
                int requiredNeg = -(pos1 + pos2);  // We need negative = -(pos1 + pos2)
                
                if (negativeSet.contains(requiredNeg)) {
                    List<Integer> triplet = Arrays.asList(pos1, pos2, requiredNeg);
                    Collections.sort(triplet);
                    result.add(triplet);
                }
            }
        }
        
        return new ArrayList<>(result);
    }
    
    /**
     * Standard 3-Sum solution using sorting and two-pointer technique
     * Time: O(n²), Space: O(1) excluding output
     */
    public static List<List<Integer>> threeSumTwoPointer(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);  // Sort the array first
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate elements for i
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // Skip duplicates for left
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // Skip duplicates for right
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
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
    
    /**
     * 3-Sum using hash set (similar to categorization but different implementation)
     */
    public static List<List<Integer>> threeSumHashSet(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            Set<Integer> seen = new HashSet<>();
            for (int j = i + 1; j < nums.length; j++) {
                int complement = -nums[i] - nums[j];
                
                if (seen.contains(complement)) {
                    List<Integer> triplet = Arrays.asList(nums[i], complement, nums[j]);
                    Collections.sort(triplet);
                    result.add(triplet);
                }
                seen.add(nums[j]);
            }
        }
        
        return new ArrayList<>(result);
    }
    
    /**
     * Brute force solution for verification (O(n³) time)
     */
    public static List<List<Integer>> threeSumBruteForce(int[] nums) {
        Set<List<Integer>> result = new HashSet<>();
        int n = nums.length;
        
        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                        Collections.sort(triplet);
                        result.add(triplet);
                    }
                }
            }
        }
        
        return new ArrayList<>(result);
    }
    
    /**
     * Count number of triplets (not return them)
     */
    public static int countTriplets(int[] nums) {
        int count = 0;
        Arrays.sort(nums);
        
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicates
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                
                if (sum == 0) {
                    count++;
                    
                    // Handle duplicates
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                        count++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                        count++;
                    }
                    
                    left++;
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Standard example
        int[] nums1 = {-1, 0, 1, 2, -1, -4};
        System.out.println("Test 1: [-1, 0, 1, 2, -1, -4]");
        System.out.println("Categorization method: " + triplet(nums1.length, nums1));
        System.out.println("Two-pointer method: " + threeSumTwoPointer(nums1.clone()));
        System.out.println("Expected: [[-1, -1, 2], [-1, 0, 1]]");
        
        // Test case 2: All zeros
        int[] nums2 = {0, 0, 0, 0};
        System.out.println("\nTest 2: [0, 0, 0, 0]");
        System.out.println("Categorization method: " + triplet(nums2.length, nums2));
        System.out.println("Two-pointer method: " + threeSumTwoPointer(nums2.clone()));
        System.out.println("Expected: [[0, 0, 0]]");
        
        // Test case 3: No triplets
        int[] nums3 = {1, 2, 3, 4};
        System.out.println("\nTest 3: [1, 2, 3, 4]");
        System.out.println("Categorization method: " + triplet(nums3.length, nums3));
        System.out.println("Two-pointer method: " + threeSumTwoPointer(nums3.clone()));
        System.out.println("Expected: []");
        
        // Test case 4: With duplicates
        int[] nums4 = {-2, 0, 1, 1, 2};
        System.out.println("\nTest 4: [-2, 0, 1, 1, 2]");
        System.out.println("Categorization method: " + triplet(nums4.length, nums4));
        System.out.println("Two-pointer method: " + threeSumTwoPointer(nums4.clone()));
        System.out.println("Expected: [[-2, 0, 2], [-2, 1, 1]]");
        
        // Test case 5: Large array with many combinations
        int[] nums5 = {-4, -2, -2, -1, -1, 0, 1, 2, 2, 3, 3, 4, 4};
        System.out.println("\nTest 5: Larger array");
        System.out.println("Categorization method count: " + triplet(nums5.length, nums5).size());
        System.out.println("Two-pointer method count: " + threeSumTwoPointer(nums5.clone()).size());
        
        // Test case 6: Edge case - only positives
        int[] nums6 = {1, 2, 3};
        System.out.println("\nTest 6: [1, 2, 3] (all positive)");
        System.out.println("Categorization method: " + triplet(nums6.length, nums6));
        System.out.println("Expected: []");
        
        // Test case 7: Edge case - only negatives
        int[] nums7 = {-1, -2, -3};
        System.out.println("\nTest 7: [-1, -2, -3] (all negative)");
        System.out.println("Categorization method: " + triplet(nums7.length, nums7));
        System.out.println("Expected: []");
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeArray = new int[1000];
        Random rand = new Random();
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = rand.nextInt(2001) - 1000; // Range -1000 to 1000
        }
        
        long startTime = System.currentTimeMillis();
        List<List<Integer>> result1 = triplet(largeArray.length, largeArray);
        long endTime = System.currentTimeMillis();
        System.out.println("Categorization method: " + (endTime - startTime) + "ms");
        System.out.println("Number of triplets: " + result1.size());
        
        startTime = System.currentTimeMillis();
        List<List<Integer>> result2 = threeSumTwoPointer(largeArray.clone());
        endTime = System.currentTimeMillis();
        System.out.println("Two-pointer method: " + (endTime - startTime) + "ms");
        System.out.println("Number of triplets: " + result2.size());
        
        // Verify results match
        Set<List<Integer>> set1 = new HashSet<>(result1);
        Set<List<Integer>> set2 = new HashSet<>(result2);
        System.out.println("Results match: " + set1.equals(set2));
        
        // Verify with brute force on small array
        System.out.println("\n=== Verification with Brute Force ===");
        int[] testArray = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> algoResult = triplet(testArray.length, testArray.clone());
        List<List<Integer>> bruteResult = threeSumBruteForce(testArray);
        
        // Sort both results for comparison
        Set<List<Integer>> algoSet = new HashSet<>(algoResult);
        Set<List<Integer>> bruteSet = new HashSet<>(bruteResult);
        
        System.out.println("Algorithm result: " + algoSet);
        System.out.println("Brute force result: " + bruteSet);
        System.out.println("Match: " + algoSet.equals(bruteSet));
    }
    
    /**
     * Why the categorization approach works:
     * 
     * For three numbers a, b, c to sum to 0, we have four cases:
     * 
     * 1. One zero, one positive, one negative:
     *    a + b + c = 0, with one of them = 0
     *    Then the positive and negative must be opposites
     *    Example: (-2, 0, 2)
     * 
     * 2. Three zeros:
     *    Only possible if array has at least 3 zeros
     * 
     * 3. Two negatives and one positive:
     *    Let a, b < 0, c > 0
     *    Then c = -(a + b) must be positive
     *    Example: (-4, -1, 5)
     * 
     * 4. Two positives and one negative:
     *    Let a, b > 0, c < 0
     *    Then c = -(a + b) must be negative
     *    Example: (1, 2, -3)
     * 
     * Cases 3 and 4 are symmetric. Cases 1 and 2 are special.
     * 
     * The algorithm handles all four cases separately.
     */
    
    /**
     * Step-by-step example:
     * 
     * Input: [-1, 0, 1, 2, -1, -4]
     * 
     * Step 1: Categorize
     * negatives: [-1, -1, -4]
     * positives: [1, 2]
     * zeros: [0]
     * 
     * Step 2: Case 1 (0 + positive + negative)
     * For each positive in {1, 2}:
     *   pos=1 ? need neg=-1 (exists) ? triplet [-1, 0, 1]
     *   pos=2 ? need neg=-2 (doesn't exist) ? skip
     * 
     * Step 3: Case 2 (0 + 0 + 0)
     * Only 1 zero, skip
     * 
     * Step 4: Case 3 (two negatives + one positive)
     * Check pairs of negatives:
     *   (-1, -1) ? need pos=2 (exists) ? triplet [-1, -1, 2]
     *   (-1, -4) ? need pos=5 (doesn't exist)
     *   (-1, -4) [second -1] ? same as above
     * 
     * Step 5: Case 4 (two positives + one negative)
     * Only one pair (1, 2) ? need neg=-3 (doesn't exist)
     * 
     * Result: [[-1, 0, 1], [-1, -1, 2]]
     */
    
    /**
     * Handling Duplicates:
     * 
     * The algorithm uses HashSet to store triplets, and sorts each triplet
     * before adding. This automatically handles duplicates because:
     * 
     * 1. Sorting ensures same elements in different order become same list
     *    Example: [-1, 0, 1] and [0, -1, 1] both become [-1, 0, 1]
     * 2. HashSet eliminates duplicates
     * 
     * However, we still get duplicates in our iteration. For better performance,
     * we could sort the negative and positive lists and skip duplicates while iterating.
     */
    
    /**
     * Time Complexity Analysis:
     * 
     * Let n = total elements
     * Let n1 = number of negatives, n2 = number of positives, n3 = number of zeros
     * 
     * Worst case analysis:
     * 1. Categorization: O(n)
     * 2. Case 1: O(n1 * n2) but with sets: O(min(n1, n2))
     * 3. Case 3: O(n1² * log n2) for checking each negative pair
     * 4. Case 4: O(n2² * log n1) for checking each positive pair
     * 
     * In worst case (all numbers negative or all positive):
     * - n1 ˜ n or n2 ˜ n
     * - Complexity: O(n²) for pairwise checking
     * 
     * Overall: O(n²) worst case
     */
    
    /**
     * Space Complexity Analysis:
     * 
     * 1. Lists for negatives, positives, zeros: O(n)
     * 2. Sets for negatives and positives: O(n)
     * 3. Result set: O(k) where k = n² (but typically much smaller)
     * 
     * Total: O(n) + O(k)
     */
    
    /**
     * Advantages of this approach:
     * 
     * 1. No need to sort entire array (can be expensive for large n)
     * 2. Naturally handles separate cases
     * 3. Easy to understand logic
     * 4. Can be optimized further (sort lists to skip duplicates)
     */
    
    /**
     * Disadvantages:
     * 
     * 1. Uses more memory (separate lists and sets)
     * 2. May have duplicate work (pairs checked multiple times)
     * 3. Not as elegant as two-pointer solution
     * 4. Need to handle four separate cases
     */
    
    /**
     * Comparison with Two-pointer Approach:
     * 
     * Two-pointer (sorting + two-pointer):
     * - Pros: O(n²) worst case, O(1) extra space (excluding output)
     * - Cons: Requires sorting O(n log n), modifies input
     * 
     * Categorization approach:
     * - Pros: No sorting needed, logical separation of cases
     * - Cons: O(n) extra space, may be slower in practice
     * 
     * Hash set approach:
     * - Pros: O(n²) average, easy implementation
     * - Cons: O(n) extra space, may have collision issues
     */
    
    /**
     * Optimization Suggestions:
     * 
     * 1. Sort negative and positive lists to skip duplicates:
     *    for (int i = 0; i < negatives.size(); i++) {
     *        if (i > 0 && negatives.get(i) == negatives.get(i-1)) continue;
     *        ...
     *    }
     * 
     * 2. Use binary search instead of set lookup for faster complement search
     * 
     * 3. Early termination: If all numbers same sign (all positive or all negative),
     *    no triplets possible
     * 
     * 4. Use array instead of ArrayList for better cache locality
     */
    
    /**
     * Edge Cases:
     * 
     * 1. Array with less than 3 elements: return empty list
     * 2. All zeros: return [0,0,0]
     * 3. All same sign (all positive or all negative): no solution
     * 4. Large numbers causing overflow in sum (use long if needed)
     * 5. Many duplicates: ensure unique triplets only
     */
    
    /**
     * Related Problems:
     * 
     * 1. 3Sum Closest (LeetCode 16) - Find triplet with sum closest to target
     * 2. 3Sum Smaller (LeetCode 259) - Count triplets with sum less than target
     * 3. 4Sum (LeetCode 18) - Find quadruplets summing to target
     * 4. 3Sum With Multiplicity (LeetCode 923) - Count triplets with duplicates
     */
    
    /**
     * Applications:
     * 
     * 1. Data analysis: Finding correlated data points
     * 2. Financial analysis: Portfolio optimization
     * 3. Computational geometry: Point sets with certain properties
     * 4. Chemistry: Molecular combinations
     */
}
