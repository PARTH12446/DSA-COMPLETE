// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Majority Element II (Elements appearing more than n/3 times)
 * 
 * Given an array 'v' of size 'n', find all elements that appear more than ?n/3? times.
 * 
 * CONSTRAINT: At most 2 such elements can exist.
 * 
 * WHY AT MOST 2?
 * - If an element appears > n/3 times, it occupies > 1/3 of array
 * - 3*(n/3 + 1) > n, so only 2 such elements can fit in array
 * - Example: n=8, n/3=2, >2 means =3
 *   If 3 elements each appear 3 times: 3*3=9 > 8 (impossible)
 * 
 * CONSTRAINTS:
 * - 1 = n = 10^5
 * - -10^9 = v[i] = 10^9
 * - Return elements in any order (sorted in our implementation)
 * 
 * APPROACH 1: Hash Map Counting (Implemented)
 * - Count frequency of each element
 * - Check if frequency > n/3
 * 
 * TIME COMPLEXITY: O(n)
 *   - One pass to count frequencies
 *   - Another pass (or during counting) to check condition
 * 
 * SPACE COMPLEXITY: O(n)
 *   - HashMap stores up to n distinct elements
 * 
 * APPROACH 2: Boyer-Moore Majority Vote Algorithm (Optimal)
 * - Modified version for n/3 threshold
 * - O(1) space complexity
 * 
 * KEY INSIGHT:
 * - Can have at most 2 majority elements for n/3 threshold
 * - Use extended Boyer-Moore with 2 candidates
 */

import java.util.*;

public class MajorityElementHard {

    /**
     * Solution using HashMap (straightforward)
     * 
     * @param v Input array
     * @return List of elements appearing more than n/3 times, sorted
     */
    public static List<Integer> majorityElement(int[] v) {
        int n = v.length;
        int threshold = n / 3;  // ?n/3?
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        List<Integer> result = new ArrayList<>();
        
        // Count frequencies
        for (int num : v) {
            frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
        }
        
        // Check which elements exceed threshold
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > threshold) {
                result.add(entry.getKey());
            }
        }
        
        // Sort result (optional, based on problem requirements)
        Collections.sort(result);
        return result;
    }
    
    /**
     * Optimized Boyer-Moore Majority Vote Algorithm for n/3 threshold
     * Time: O(n), Space: O(1)
     * 
     * ALGORITHM:
     * 1. First pass: Find 2 most frequent candidates (may not be > n/3)
     * 2. Second pass: Verify actual counts > n/3
     * 
     * Why it works:
     * - If an element appears > n/3 times, it will survive first pass
     * - But surviving first pass doesn't guarantee > n/3 (false positives possible)
     * - Need verification pass
     */
    public static List<Integer> majorityElementBoyerMoore(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) return result;
        
        int n = nums.length;
        int threshold = n / 3;
        
        // Step 1: Find candidates (potential majority elements)
        int candidate1 = 0, candidate2 = 0;
        int count1 = 0, count2 = 0;
        
        for (int num : nums) {
            if (num == candidate1) {
                count1++;
            } else if (num == candidate2) {
                count2++;
            } else if (count1 == 0) {
                candidate1 = num;
                count1 = 1;
            } else if (count2 == 0) {
                candidate2 = num;
                count2 = 1;
            } else {
                // Decrement both counts
                count1--;
                count2--;
            }
        }
        
        // Step 2: Verify candidates actually appear > n/3 times
        count1 = 0;
        count2 = 0;
        
        for (int num : nums) {
            if (num == candidate1) count1++;
            else if (num == candidate2) count2++;
        }
        
        if (count1 > threshold) result.add(candidate1);
        if (count2 > threshold) result.add(candidate2);
        
        // Additional check: candidates might be equal (e.g., array [0,0,0])
        if (candidate1 == candidate2 && result.size() == 2) {
            result.remove(1); // Remove duplicate
        }
        
        Collections.sort(result);
        return result;
    }
    
    /**
     * Alternative: Using sorting approach
     * Time: O(n log n), Space: O(1) excluding sort
     */
    public static List<Integer> majorityElementSorting(int[] v) {
        List<Integer> result = new ArrayList<>();
        if (v.length == 0) return result;
        
        Arrays.sort(v);
        int n = v.length;
        int threshold = n / 3;
        int count = 1;
        
        for (int i = 1; i < n; i++) {
            if (v[i] == v[i - 1]) {
                count++;
            } else {
                if (count > threshold) {
                    result.add(v[i - 1]);
                }
                count = 1;
            }
        }
        
        // Check last element
        if (count > threshold) {
            result.add(v[n - 1]);
        }
        
        return result;
    }
    
    /**
     * Test method with various examples
     */
    public static void main(String[] args) {
        // Test case 1: Standard example
        int[] arr1 = {3, 2, 3};
        System.out.println("Test 1: [3, 2, 3]");
        System.out.println("HashMap: " + majorityElement(arr1));
        System.out.println("Boyer-Moore: " + majorityElementBoyerMoore(arr1));
        System.out.println("Expected: [3]");
        
        // Test case 2: Two majority elements
        int[] arr2 = {1, 1, 1, 3, 3, 2, 2, 2};
        System.out.println("\nTest 2: [1,1,1,3,3,2,2,2]");
        System.out.println("HashMap: " + majorityElement(arr2));
        System.out.println("Boyer-Moore: " + majorityElementBoyerMoore(arr2));
        System.out.println("Expected: [1, 2] (both appear 3 times > 8/3=2)");
        
        // Test case 3: Single element repeated
        int[] arr3 = {2, 2, 2, 2};
        System.out.println("\nTest 3: [2,2,2,2]");
        System.out.println("HashMap: " + majorityElement(arr3));
        System.out.println("Boyer-Moore: " + majorityElementBoyerMoore(arr3));
        System.out.println("Expected: [2] (4 > 4/3=1)");
        
        // Test case 4: No majority element
        int[] arr4 = {1, 2, 3, 4, 5};
        System.out.println("\nTest 4: [1,2,3,4,5]");
        System.out.println("HashMap: " + majorityElement(arr4));
        System.out.println("Boyer-Moore: " + majorityElementBoyerMoore(arr4));
        System.out.println("Expected: [] (none appear > 5/3=1)");
        
        // Test case 5: All same elements
        int[] arr5 = {7, 7, 7, 7, 7};
        System.out.println("\nTest 5: [7,7,7,7,7]");
        System.out.println("HashMap: " + majorityElement(arr5));
        System.out.println("Boyer-Moore: " + majorityElementBoyerMoore(arr5));
        System.out.println("Expected: [7] (5 > 5/3=1)");
        
        // Test case 6: Edge case with 0
        int[] arr6 = {0, 0, 0};
        System.out.println("\nTest 6: [0,0,0]");
        System.out.println("HashMap: " + majorityElement(arr6));
        System.out.println("Boyer-Moore: " + majorityElementBoyerMoore(arr6));
        System.out.println("Expected: [0] (3 > 3/3=1)");
        
        // Test case 7: Large array for performance
        System.out.println("\n=== Performance Test ===");
        int[] largeArr = new int[100000];
        Random rand = new Random();
        // Fill with 70% 1s, 20% 2s, 10% random
        for (int i = 0; i < largeArr.length; i++) {
            double r = rand.nextDouble();
            if (r < 0.7) largeArr[i] = 1;
            else if (r < 0.9) largeArr[i] = 2;
            else largeArr[i] = rand.nextInt(100);
        }
        
        long startTime = System.currentTimeMillis();
        List<Integer> result1 = majorityElement(largeArr);
        long endTime = System.currentTimeMillis();
        System.out.println("HashMap Time: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        List<Integer> result2 = majorityElementBoyerMoore(largeArr);
        endTime = System.currentTimeMillis();
        System.out.println("Boyer-Moore Time: " + (endTime - startTime) + "ms");
        
        System.out.println("Results match: " + result1.equals(result2));
    }
    
    /**
     * Boyer-Moore Algorithm Detailed Explanation:
     * 
     * For n/2 majority (standard Boyer-Moore):
     * - Keep candidate and count
     * - If same as candidate, increment
     * - If different, decrement
     * - If count=0, pick new candidate
     * 
     * For n/3 majority (extended Boyer-Moore):
     * - Need to track 2 candidates
     * - Maintain count1, count2 for candidate1, candidate2
     * - When seeing new element:
     *   1. If matches candidate1 ? count1++
     *   2. Else if matches candidate2 ? count2++
     *   3. Else if count1=0 ? set as candidate1, count1=1
     *   4. Else if count2=0 ? set as candidate2, count2=1
     *   5. Else ? decrement both counts
     * 
     * Intuition: Each non-candidate element cancels one vote from each candidate
     * If element appears > n/3 times, it survives this cancellation process
     */
    
    /**
     * Proof of Boyer-Moore for n/3:
     * 
     * Let m be number of elements with frequency > n/3.
     * We know m = 2.
     * 
     * Claim: Any element with frequency > n/3 will be among the 2 candidates
     * after first pass.
     * 
     * Why? Suppose element X appears f times, f > n/3.
     * In worst case, all other (n-f) elements work against X.
     * But each cancellation reduces X's count by at most 2 (when both
     * candidates are different from X).
     * So X's final count = f - 2*(n-f)/2 = f - (n-f) = 2f - n > 0
     * Since f > n/3, 2f - n > 2n/3 - n = -n/3 > -n (always positive enough)
     * So X survives first pass.
     */
    
    /**
     * Visual Example of Boyer-Moore:
     * 
     * Array: [1, 2, 3, 1, 2, 1]  n=6, threshold=2
     * 
     * First pass:
     * i=0: c1=1, cnt1=1, c2=0, cnt2=0
     * i=1: c1=1, cnt1=1, c2=2, cnt2=1
     * i=2: both different, decrement both ? c1=1, cnt1=0, c2=2, cnt2=0
     * i=3: cnt1=0 ? c1=1, cnt1=1
     * i=4: cnt2=0 ? c2=2, cnt2=1
     * i=5: matches c1 ? c1=1, cnt1=2
     * 
     * Candidates: 1 and 2
     * 
     * Second pass (verification):
     * Count(1)=3 > 2 ?
     * Count(2)=2 not > 2 ?
     * 
     * Result: [1]
     */
    
    /**
     * Edge Cases:
     * 
     * 1. Array length < 3:
     *    - n=1: threshold=0, any element appears >0 times
     *    - n=2: threshold=0, any element appears >0 times
     *    - Boyer-Moore handles correctly
     * 
     * 2. All elements same:
     *    - Only one candidate needed
     *    - Boyer-Moore might set both candidates to same value
     *    - Need to deduplicate
     * 
     * 3. Exactly at threshold (not >):
     *    - Should not be included
     *    - Example: n=3, [1,2,3] ? no element appears >1 time
     * 
     * 4. Candidates equal after first pass:
     *    - Can happen with arrays like [0,0,0]
     *    - Need to handle in verification
     */
    
    /**
     * Time Complexity Comparison:
     * 
     * 1. HashMap: O(n) time, O(n) space
     *    - Simple, reliable
     *    - Works for any threshold
     *    - Good for general cases
     * 
     * 2. Boyer-Moore: O(n) time, O(1) space
     *    - Optimal space complexity
     *    - Specific to n/3 threshold
     *    - Needs verification pass
     * 
     * 3. Sorting: O(n log n) time, O(1) space
     *    - Simple but slower
     *    - Modifies input (unless copied)
     * 
     * CHOICE:
     * - Interview: Boyer-Moore shows algorithmic knowledge
     * - Production: HashMap is simpler and more maintainable
     * - Memory-constrained: Boyer-Moore
     */
    
    /**
     * Related Problems:
     * 
     * 1. Majority Element (n/2) - LeetCode 169
     *    - Standard Boyer-Moore algorithm
     * 
     * 2. Check if element appears > n/k times
     *    - Generalization: track k-1 candidates
     * 
     * 3. Find all elements appearing n/4 times, n/5 times, etc.
     *    - Same pattern with more candidates
     */
    
    /**
     * Applications:
     * 
     * 1. Data stream processing:
     *    - Find frequent items with limited memory
     *    - Boyer-Moore can process stream in one pass
     * 
     * 2. Voting systems:
     *    - Find candidates with significant support
     * 
     * 3. Anomaly detection:
     *    - Identify frequent patterns in logs
     * 
     * 4. Database query optimization:
     *    - Find frequent values for indexing
     */
    
    /**
     * Implementation Notes:
     * 
     * 1. Initialize candidates carefully:
     *    - Can't use 0 as default if 0 appears in array
     *    - Use null or sentinel values
     * 
     * 2. Integer overflow:
     *    - Counts can be large (up to n=10^5)
     *    - But fits in int range
     * 
     * 3. Input validation:
     *    - Check for null array
     *    - Check for empty array
     */
}
