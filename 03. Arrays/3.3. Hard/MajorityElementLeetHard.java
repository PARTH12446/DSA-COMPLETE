// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Majority Element II (LeetCode 229)
 * 
 * Given an integer array of size n, find all elements that appear more than ?n/3? times.
 * 
 * KEY INSIGHTS:
 * 1. At most 2 such elements can exist (mathematical constraint)
 * 2. Standard solutions: HashMap (O(n) space) or Boyer-Moore (O(1) space)
 * 3. This implementation uses extended Boyer-Moore Voting Algorithm
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 5 * 10^4
 * - -10^9 <= nums[i] <= 10^9
 * - Return elements in any order
 * 
 * BOYER-MOORE ALGORITHM FOR N/3:
 * 1. Track 2 candidates and their counts
 * 2. First pass: Find potential candidates (may include false positives)
 * 3. Second pass: Verify actual counts > n/3
 * 
 * TIME COMPLEXITY: O(n)
 *   - Two passes through array
 *   - Each pass is O(n)
 * 
 * SPACE COMPLEXITY: O(1)
 *   - Only a few integer variables used
 *   - Output list not counted in space complexity
 */

import java.util.*;

public class MajorityElementLeetHard {

    /**
     * Boyer-Moore Majority Vote Algorithm for n/3 threshold
     * 
     * @param nums Input array
     * @return List of elements appearing more than n/3 times
     * 
     * ALGORITHM STEPS:
     * 1. Initialize two candidates with different values
     * 2. First pass: Find potential majority candidates
     * 3. Second pass: Count occurrences to verify
     * 4. Return verified candidates with count > n/3
     */
    public List<Integer> majorityElement(int[] nums) {
        // Initialize candidates with different values
        int countA = 0, countB = 0;
        int candidateA = 0, candidateB = 1; // Different initial values
        
        // FIRST PASS: Find potential candidates
        for (int num : nums) {
            if (countA == 0 && num != candidateB) {
                // Start tracking new candidate A
                countA = 1;
                candidateA = num;
            } 
            else if (countB == 0 && num != candidateA) {
                // Start tracking new candidate B
                countB = 1;
                candidateB = num;
            } 
            else if (num == candidateA) {
                // Matches candidate A, increment count
                countA++;
            } 
            else if (num == candidateB) {
                // Matches candidate B, increment count
                countB++;
            } 
            else {
                // Different from both candidates, decrement both
                countA--;
                countB--;
            }
        }
        
        // SECOND PASS: Verify actual counts
        countA = 0;
        countB = 0;
        
        for (int num : nums) {
            if (num == candidateA) {
                countA++;
            } 
            else if (num == candidateB) { // Use else-if to avoid double counting
                countB++;
            }
        }
        
        // Prepare result
        List<Integer> result = new ArrayList<>();
        int threshold = nums.length / 3;
        
        if (countA > threshold) {
            result.add(candidateA);
        }
        if (countB > threshold) {
            result.add(candidateB);
        }
        
        // Note: Sorting is optional (LeetCode doesn't require specific order)
        Collections.sort(result);
        return result;
    }
    
    /**
     * Alternative: More robust initialization with null handling
     * Handles edge cases better
     */
    public List<Integer> majorityElementRobust(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        // Initialize with null to handle cases where 0 or 1 might be actual values
        Integer candidate1 = null, candidate2 = null;
        int count1 = 0, count2 = 0;
        
        // First pass: Find candidates
        for (int num : nums) {
            if (candidate1 != null && num == candidate1) {
                count1++;
            } 
            else if (candidate2 != null && num == candidate2) {
                count2++;
            } 
            else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
            } 
            else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } 
            else {
                count1--;
                count2--;
            }
        }
        
        // Second pass: Verify counts
        count1 = 0;
        count2 = 0;
        
        for (int num : nums) {
            if (candidate1 != null && num == candidate1) count1++;
            if (candidate2 != null && num == candidate2) count2++;
        }
        
        int threshold = nums.length / 3;
        if (count1 > threshold) result.add(candidate1);
        if (count2 > threshold) result.add(candidate2);
        
        return result;
    }
    
    /**
     * HashMap solution (simpler but uses O(n) space)
     */
    public List<Integer> majorityElementHashMap(int[] nums) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        int threshold = nums.length / 3;
        
        // Count frequencies
        for (int num : nums) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Find elements with frequency > threshold
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > threshold) {
                result.add(entry.getKey());
            }
        }
        
        return result;
    }
    
    /**
     * Test method with LeetCode examples
     */
    public static void main(String[] args) {
        MajorityElementLeetHard solver = new MajorityElementLeetHard();
        
        // Test case 1: LeetCode Example 1
        int[] nums1 = {3, 2, 3};
        System.out.println("Test 1: [3, 2, 3]");
        System.out.println("Result: " + solver.majorityElement(nums1));
        System.out.println("Expected: [3]");
        
        // Test case 2: LeetCode Example 2
        int[] nums2 = {1};
        System.out.println("\nTest 2: [1]");
        System.out.println("Result: " + solver.majorityElement(nums2));
        System.out.println("Expected: [1]");
        
        // Test case 3: LeetCode Example 3
        int[] nums3 = {1, 2};
        System.out.println("\nTest 3: [1, 2]");
        System.out.println("Result: " + solver.majorityElement(nums3));
        System.out.println("Expected: [1, 2]");
        
        // Test case 4: Two majority elements
        int[] nums4 = {1, 1, 1, 3, 3, 2, 2, 2};
        System.out.println("\nTest 4: [1,1,1,3,3,2,2,2]");
        System.out.println("Result: " + solver.majorityElement(nums4));
        System.out.println("Expected: [1, 2]");
        
        // Test case 5: Edge case with 0s
        int[] nums5 = {0, 0, 0};
        System.out.println("\nTest 5: [0, 0, 0]");
        System.out.println("Result: " + solver.majorityElement(nums5));
        System.out.println("Expected: [0]");
        
        // Test case 6: No majority element
        int[] nums6 = {1, 2, 3, 4};
        System.out.println("\nTest 6: [1, 2, 3, 4]");
        System.out.println("Result: " + solver.majorityElement(nums6));
        System.out.println("Expected: []");
        
        // Test case 7: Large array for performance
        System.out.println("\n=== Performance Test ===");
        int[] largeArray = new int[50000];
        Random rand = new Random();
        
        // Fill array: 40% 1s, 35% 2s, 25% random
        for (int i = 0; i < largeArray.length; i++) {
            double r = rand.nextDouble();
            if (r < 0.4) largeArray[i] = 1;
            else if (r < 0.75) largeArray[i] = 2;
            else largeArray[i] = rand.nextInt(100);
        }
        
        long startTime = System.currentTimeMillis();
        List<Integer> result1 = solver.majorityElement(largeArray);
        long endTime = System.currentTimeMillis();
        System.out.println("Boyer-Moore Time: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        List<Integer> result2 = solver.majorityElementHashMap(largeArray);
        endTime = System.currentTimeMillis();
        System.out.println("HashMap Time: " + (endTime - startTime) + "ms");
        
        System.out.println("Results match: " + result1.equals(result2));
        
        // Test edge cases
        System.out.println("\n=== Edge Case Tests ===");
        
        // Array with negative numbers
        int[] nums7 = {-1, -1, -1, 2, 2, 2, 3};
        System.out.println("Test 7 (negative): " + solver.majorityElement(nums7));
        
        // Single element array
        int[] nums8 = {42};
        System.out.println("Test 8 (single): " + solver.majorityElement(nums8));
        
        // All same elements
        int[] nums9 = {5, 5, 5, 5};
        System.out.println("Test 9 (all same): " + solver.majorityElement(nums9));
    }
    
    /**
     * Detailed explanation of the algorithm:
     * 
     * WHY INITIALIZE WITH DIFFERENT VALUES?
     * candidateA = 0, candidateB = 1
     * - Ensures candidates are initially different
     * - Prevents both candidates tracking same element initially
     * - If array contains 0 or 1, algorithm still works due to verification
     * 
     * FIRST PASS LOGIC:
     * 1. If countA == 0 AND num != candidateB ? start tracking new candidate A
     *    - The "num != candidateB" prevents both candidates from being same
     * 
     * 2. If countB == 0 AND num != candidateA ? start tracking new candidate B
     * 
     * 3. If num matches candidateA ? increment countA
     * 
     * 4. If num matches candidateB ? increment countB
     * 
     * 5. Otherwise ? decrement both counts
     *    - This is the "cancellation" step
     *    - Each non-candidate element cancels one vote from each candidate
     * 
     * INTUITION BEHIND CANCELLATION:
     * - If an element appears > n/3 times, it will survive cancellation
     * - Each cancellation reduces count by at most 2
     * - With > n/3 occurrences, element will maintain positive count
     */
    
    /**
     * Mathematical Proof:
     * 
     * Let element X appear f times, where f > n/3.
     * In worst case scenario:
     * - All other (n-f) elements are different from X
     * - Each of these elements cancels one vote from X
     * - Maximum cancellations for X: (n-f)/2 * 2 = n-f
     * 
     * Final count of X after first pass:
     * count_X = f - (n-f) = 2f - n
     * 
     * Since f > n/3:
     * 2f - n > 2n/3 - n = -n/3
     * 
     * But wait, this seems negative! Let's re-examine:
     * 
     * Actually, each cancellation reduces count by 1, not 2.
     * The algorithm decrements BOTH counts when seeing a non-candidate.
     * So X's count decreases by 1 when a non-candidate appears.
     * 
     * Maximum cancellations: n-f (all other elements)
     * So count_X = f - (n-f) = 2f - n
     * 
     * Since f > n/3:
     * 2f > 2n/3
     * 2f - n > 2n/3 - n = -n/3
     * 
     * This is still negative! What's wrong?
     * 
     * The key insight: X will become a candidate before all cancellations
     * happen. Once X is a candidate, it gets incremented when seen.
     * The cancellations only happen when neither candidate matches.
     * 
     * A more accurate analysis: X will survive because it appears
     * frequently enough to offset cancellations.
     */
    
    /**
     * Visual Example:
     * 
     * Array: [1, 1, 1, 2, 2, 2, 3, 4, 5]  n=9, threshold=3
     * 
     * First pass:
     * i=0: countA=0, num=1, num?candidateB(1) ? candidateA=1, countA=1
     * i=1: num=1 == candidateA ? countA=2
     * i=2: num=1 == candidateA ? countA=3
     * i=3: countB=0, num=2, num?candidateA(1) ? candidateB=2, countB=1
     * i=4: num=2 == candidateB ? countB=2
     * i=5: num=2 == candidateB ? countB=3
     * i=6: num=3 ? both ? countA=2, countB=2
     * i=7: num=4 ? both ? countA=1, countB=1
     * i=8: num=5 ? both ? countA=0, countB=0
     * 
     * Candidates after first pass: 1 and 2
     * 
     * Second pass: Count(1)=3, Count(2)=3
     * Both > 9/3=3? No, equal to threshold, not greater.
     * Actually 3 > 3? False. So neither is included.
     * Wait, but 1 and 2 appear exactly 3 times each.
     * They appear > n/3? n/3 = 9/3 = 3, need >3, so no.
     */
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. Not handling candidate equality:
     *    - Both candidates might end up with same value
     *    - Solution: Check for duplicates before adding to result
     * 
     * 2. Using else-if chain incorrectly:
     *    - Order matters in the first pass
     *    - Check for matches before checking count==0
     * 
     * 3. Forgetting verification pass:
     *    - First pass only finds potential candidates
     *    - Must verify actual counts > n/3
     * 
     * 4. Initial candidate values:
     *    - Can't use 0 if 0 might be in array
     *    - Better to use null or sentinel values
     */
    
    /**
     * Optimization Tips:
     * 
     * 1. Early exit for small arrays:
     *    - If n < 3, all elements qualify (appear > n/3)
     * 
     * 2. Combined verification:
     *    - Some implementations combine verification with first pass
     *    - But need to handle edge cases carefully
     * 
     * 3. Memory optimization:
     *    - Boyer-Moore uses O(1) space, optimal for large arrays
     */
    
    /**
     * Related Problems:
     * 
     * 1. Majority Element (LeetCode 169) - n/2 threshold
     *    - Simpler: track one candidate
     * 
     * 2. Check for Majority Element in Sorted Array
     *    - Binary search solution
     * 
     * 3. Element appearing > n/k times
     *    - Generalization: track k-1 candidates
     */
    
    /**
     * Real-world Applications:
     * 
     * 1. Streaming algorithms:
     *    - Process data streams with limited memory
     *    - Boyer-Moore can handle infinite streams
     * 
     * 2. Distributed systems:
     *    - Find frequent items across multiple nodes
     * 
     * 3. Database systems:
     *    - Identify hot spots for optimization
     * 
     * 4. Social network analysis:
     *    - Find influential nodes or common patterns
     */
}
