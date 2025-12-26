// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Count Subarrays with Given XOR (Coding Ninjas)
 * 
 * Given an array 'a' of integers and an integer 'b',
 * find the total number of subarrays having bitwise XOR of all elements equal to 'b'.
 * 
 * FORMAL DEFINITION:
 * Count all pairs (i, j) such that:
 *   0 = i = j < n
 *   a[i] ? a[i+1] ? ... ? a[j] = b
 * 
 * CONSTRAINTS:
 * - 1 = n = 10^5
 * - 0 = a[i] = 10^9
 * - 0 = b = 10^9
 * - Need efficient O(n) solution
 * 
 * APPROACH: Prefix XOR with Hash Map
 * 
 * INTUITION:
 * Let prefix XOR P[i] = a[0] ? a[1] ? ... ? a[i]
 * For subarray (i, j): XOR = P[j] ? P[i-1] (or P[j] if i=0)
 * 
 * We want: P[j] ? P[i-1] = b
 * Rearranging: P[i-1] = P[j] ? b
 * 
 * So for each j, count how many i (i = j) satisfy P[i-1] = P[j] ? b
 * 
 * ALGORITHM:
 * 1. Maintain running XOR and count map of prefix XORs seen
 * 2. For each element:
 *    - Update running XOR
 *    - If running XOR == b, increment count (subarray from start)
 *    - Check if (running XOR ? b) exists in map (middle subarrays)
 *    - Update map with current running XOR
 * 
 * TIME COMPLEXITY: O(n)
 *   - Single pass through array
 *   - HashMap operations O(1) average case
 * 
 * SPACE COMPLEXITY: O(n)
 *   - HashMap stores up to n prefix XORs
 */

import java.util.*;

public class SubArrXOR {

    /**
     * Count subarrays with XOR equal to b
     * 
     * @param a Input array
     * @param b Target XOR value
     * @return Number of subarrays with XOR = b
     */
    public static int subarraysWithSumK(int[] a, int b) {
        int count = 0;
        Map<Integer, Integer> prefixXORCount = new HashMap<>();
        int currentXOR = 0;
        
        for (int num : a) {
            // Update running XOR
            currentXOR ^= num;
            
            // Case 1: Subarray from start to current index
            if (currentXOR == b) {
                count++;
            }
            
            // Case 2: Subarray in middle
            // We want: prefixXOR[i-1] = currentXOR ^ b
            int neededXOR = currentXOR ^ b;
            if (prefixXORCount.containsKey(neededXOR)) {
                count += prefixXORCount.get(neededXOR);
            }
            
            // Update count of current prefix XOR
            prefixXORCount.put(currentXOR, prefixXORCount.getOrDefault(currentXOR, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * Alternative: More explicit implementation
     */
    public static int subarraysWithSumKAlt(int[] a, int b) {
        Map<Integer, Integer> freq = new HashMap<>();
        int count = 0;
        int xor = 0;
        
        for (int num : a) {
            xor ^= num;
            
            // If xor itself equals b, subarray from start
            if (xor == b) {
                count++;
            }
            
            // If (xor ^ b) exists in map, those are starting points
            if (freq.containsKey(xor ^ b)) {
                count += freq.get(xor ^ b);
            }
            
            // Update frequency of current xor
            freq.put(xor, freq.getOrDefault(xor, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * Brute force solution for verification (O(n²) time)
     */
    public static int subarraysWithSumKBruteForce(int[] a, int b) {
        int count = 0;
        int n = a.length;
        
        for (int i = 0; i < n; i++) {
            int xor = 0;
            for (int j = i; j < n; j++) {
                xor ^= a[j];
                if (xor == b) {
                    count++;
                }
            }
        }
        
        return count;
    }
    
    /**
     * Using array instead of HashMap (if values are bounded)
     * Not practical for this problem but good to know
     */
    public static int subarraysWithSumKArray(int[] a, int b) {
        // Find maximum possible XOR value
        int maxElement = 0;
        for (int num : a) {
            maxElement = Math.max(maxElement, num);
        }
        
        // Maximum XOR value is power of 2 greater than max element
        int maxXOR = 1;
        while (maxXOR <= maxElement) {
            maxXOR <<= 1;
        }
        maxXOR--; // All bits set up to highest bit
        
        int[] freq = new int[maxXOR + 1];
        int count = 0;
        int xor = 0;
        
        for (int num : a) {
            xor ^= num;
            
            if (xor == b) {
                count++;
            }
            
            int needed = xor ^ b;
            if (needed <= maxXOR) {
                count += freq[needed];
            }
            
            freq[xor]++;
        }
        
        return count;
    }
    
    /**
     * Find all subarrays with XOR = b (not just count)
     */
    public static List<int[]> findAllSubarraysWithXOR(int[] a, int b) {
        List<int[]> result = new ArrayList<>();
        Map<Integer, List<Integer>> prefixMap = new HashMap<>();
        int xor = 0;
        
        // Initialize with XOR 0 at index -1
        List<Integer> zeroList = new ArrayList<>();
        zeroList.add(-1);
        prefixMap.put(0, zeroList);
        
        for (int i = 0; i < a.length; i++) {
            xor ^= a[i];
            
            // Check for subarrays ending at i
            int needed = xor ^ b;
            if (prefixMap.containsKey(needed)) {
                for (int startIndex : prefixMap.get(needed)) {
                    // Subarray from (startIndex+1) to i
                    result.add(new int[]{startIndex + 1, i});
                }
            }
            
            // Update map with current xor
            if (!prefixMap.containsKey(xor)) {
                prefixMap.put(xor, new ArrayList<>());
            }
            prefixMap.get(xor).add(i);
        }
        
        return result;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Simple example
        int[] a1 = {4, 2, 2, 6, 4};
        int b1 = 6;
        System.out.println("Test 1: a = [4, 2, 2, 6, 4], b = 6");
        System.out.println("Result: " + subarraysWithSumK(a1, b1));
        System.out.println("Brute Force: " + subarraysWithSumKBruteForce(a1, b1));
        System.out.println("Expected: 4");
        
        // Test case 2: All zeros
        int[] a2 = {0, 0, 0, 0, 0};
        int b2 = 0;
        System.out.println("\nTest 2: a = [0, 0, 0, 0, 0], b = 0");
        System.out.println("Result: " + subarraysWithSumK(a2, b2));
        System.out.println("Brute Force: " + subarraysWithSumKBruteForce(a2, b2));
        System.out.println("Expected: 15 (all subarrays of length 5)");
        
        // Test case 3: Single element matching
        int[] a3 = {5};
        int b3 = 5;
        System.out.println("\nTest 3: a = [5], b = 5");
        System.out.println("Result: " + subarraysWithSumK(a3, b3));
        System.out.println("Brute Force: " + subarraysWithSumKBruteForce(a3, b3));
        System.out.println("Expected: 1");
        
        // Test case 4: No subarray with XOR b
        int[] a4 = {1, 2, 3, 4};
        int b4 = 10;
        System.out.println("\nTest 4: a = [1, 2, 3, 4], b = 10");
        System.out.println("Result: " + subarraysWithSumK(a4, b4));
        System.out.println("Brute Force: " + subarraysWithSumKBruteForce(a4, b4));
        System.out.println("Expected: 0");
        
        // Test case 5: Array with duplicates
        int[] a5 = {1, 1, 1, 1};
        int b5 = 0;
        System.out.println("\nTest 5: a = [1, 1, 1, 1], b = 0");
        System.out.println("Result: " + subarraysWithSumK(a5, b5));
        System.out.println("Brute Force: " + subarraysWithSumKBruteForce(a5, b5));
        // XOR of even number of 1s = 0, odd number = 1
        // Subarrays with XOR 0: [1,1], [1,1], [1,1,1,1], [1,1] (different positions)
        
        // Test case 6: Find actual subarrays
        int[] a6 = {4, 2, 2, 6, 4};
        int b6 = 6;
        System.out.println("\nTest 6: Find all subarrays with XOR = 6");
        List<int[]> subarrays = findAllSubarraysWithXOR(a6, b6);
        for (int[] range : subarrays) {
            System.out.println("Subarray [" + range[0] + ".." + range[1] + "]");
            // Print the subarray
            for (int i = range[0]; i <= range[1]; i++) {
                System.out.print(a6[i] + " ");
            }
            System.out.println();
        }
        System.out.println("Total: " + subarrays.size());
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int n = 100000;
        int[] largeArray = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            largeArray[i] = rand.nextInt(1000);
        }
        int target = rand.nextInt(1000);
        
        long startTime = System.currentTimeMillis();
        int result = subarraysWithSumK(largeArray, target);
        long endTime = System.currentTimeMillis();
        System.out.println("HashMap method: " + (endTime - startTime) + "ms");
        System.out.println("Count: " + result);
        
        // Verify with brute force on small array
        System.out.println("\n=== Verification with Brute Force ===");
        int[] testArray = {4, 2, 2, 6, 4};
        int testB = 6;
        int algoResult = subarraysWithSumK(testArray, testB);
        int bruteResult = subarraysWithSumKBruteForce(testArray, testB);
        System.out.println("Algorithm: " + algoResult);
        System.out.println("Brute Force: " + bruteResult);
        System.out.println("Match: " + (algoResult == bruteResult));
        
        // Edge case: Large XOR values
        System.out.println("\n=== Large XOR Values Test ===");
        int[] edgeArray = {Integer.MAX_VALUE, Integer.MAX_VALUE - 1};
        int edgeB = Integer.MAX_VALUE ^ (Integer.MAX_VALUE - 1);
        System.out.println("Array: [MAX, MAX-1], b = MAX ^ (MAX-1)");
        System.out.println("Result: " + subarraysWithSumK(edgeArray, edgeB));
        System.out.println("Brute Force: " + subarraysWithSumKBruteForce(edgeArray, edgeB));
    }
    
    /**
     * Mathematical Proof:
     * 
     * Let P[i] = XOR of first i+1 elements (0-indexed)
     * P[-1] = 0 (empty prefix)
     * 
     * For subarray (i, j):
     * XOR = a[i] ? a[i+1] ? ... ? a[j]
     *     = P[j] ? P[i-1]
     * 
     * We want: P[j] ? P[i-1] = b
     * 
     * Rearranging: P[i-1] = P[j] ? b
     * 
     * So for each j, we need to count how many i = j satisfy:
     * P[i-1] = P[j] ? b
     * 
     * This includes i=0 case where P[-1] = 0.
     * 
     * Example: a = [4, 2, 2, 6, 4], b = 6
     * 
     * Index:  -1   0    1    2    3    4
     * Value:       4    2    2    6    4
     * Prefix: 0    4    6    4    2    6
     * 
     * For j=4, P[4]=6
     * Needed: P[i-1] = 6 ? 6 = 0
     * P[-1]=0, P[2]=4? No, P[?]=0?
     * Actually: P[-1]=0, P[1]=6, P[4]=6
     * P[i-1] = 0 when i=0 ? subarray [0..4]
     * 
     * This matches our formula.
     */
    
    /**
     * Step-by-step example:
     * 
     * a = [4, 2, 2, 6, 4], b = 6
     * 
     * Initialize: count=0, map={}, xor=0
     * 
     * i=0 (num=4):
     *   xor = 0 ? 4 = 4
     *   4 == 6? No
     *   needed = 4 ? 6 = 2, map contains 2? No
     *   map.put(4,1) ? map={4:1}
     * 
     * i=1 (num=2):
     *   xor = 4 ? 2 = 6
     *   6 == 6? Yes ? count=1
     *   needed = 6 ? 6 = 0, map contains 0? No (but prefix[-1]=0)
     *   map.put(6,1) ? map={4:1, 6:1}
     * 
     * i=2 (num=2):
     *   xor = 6 ? 2 = 4
     *   4 == 6? No
     *   needed = 4 ? 6 = 2, map contains 2? No
     *   map.put(4,2) ? map={4:2, 6:1}
     * 
     * i=3 (num=6):
     *   xor = 4 ? 6 = 2
     *   2 == 6? No
     *   needed = 2 ? 6 = 4, map contains 4? Yes (count=2)
     *   count = 1 + 2 = 3
     *   map.put(2,1) ? map={4:2, 6:1, 2:1}
     * 
     * i=4 (num=4):
     *   xor = 2 ? 4 = 6
     *   6 == 6? Yes ? count=4
     *   needed = 6 ? 6 = 0, map contains 0? No
     *   map.put(6,2) ? map={4:2, 6:2, 2:1}
     * 
     * Final count: 4
     * Subarrays: [1..1], [1..2], [0..3], [0..4]
     */
    
    /**
     * Why we need to store frequency, not just existence:
     * 
     * Consider: a = [x, y, z] where:
     * P[0] = x
     * P[1] = x ? y
     * P[2] = x ? y ? z
     * 
     * If P[1] = P[2] ? b, then both i=1 and i=2 give valid subarrays
     * If multiple indices have same prefix XOR, all give valid subarrays
     * 
     * Example: a = [1, 2, 3, 1, 2], b = 3
     * Multiple indices might have same prefix XOR
     */
    
    /**
     * Edge Cases:
     * 
     * 1. Empty array: Should return 0
     * 2. b = 0: Count subarrays with XOR 0
     * 3. Large values: Handle integer overflow in XOR (XOR doesn't overflow)
     * 4. All elements same: Special pattern
     * 5. b equals element: Single element subarrays count
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Forgetting to check xor == b separately
     * 2. Not initializing map with (0,1) for empty prefix
     *    (Our code handles this by checking xor == b)
     * 3. Using wrong formula for needed XOR
     * 4. Not using frequency map (only existence map)
     * 5. Off-by-one in indices
     */
    
    /**
     * Optimization Notes:
     * 
     * 1. Use HashMap for O(1) average operations
     * 2. Can use array if XOR values are bounded (rare)
     * 3. Early exit not possible, must process all elements
     * 4. Memory can be reduced by using array instead of HashMap
     *    if maximum XOR value is known and small
     */
    
    /**
     * Related Problems:
     * 
     * 1. Subarray Sum Equals K (LeetCode 560) - Similar but for sum
     * 2. Count Triplets with XOR 0
     * 3. Longest Subarray with XOR 0
     * 4. Maximum XOR of Two Numbers in Array
     */
    
    /**
     * Applications:
     * 
     * 1. Cryptography (XOR operations)
     * 2. Error detection in data transmission
     * 3. Data compression
     * 4. Game theory (Nim game uses XOR)
     */
    
    /**
     * Time Complexity Analysis:
     * 
     * - n iterations
     * - Each iteration: O(1) HashMap operations
     * - Total: O(n)
     * 
     * Brute force: O(n²) - check all n(n+1)/2 subarrays
     */
    
    /**
     * Space Complexity Analysis:
     * 
     * Worst case: O(n)
     * - All prefix XORs are distinct
     * - HashMap stores n entries
     * 
     * Best case: O(1)
     * - All prefix XORs same (only one entry in map)
     * 
     * Average case: O(n)
     */
    
    /**
     * Alternative Implementation with explicit (0,1) in map:
     */
    public static int subarraysWithSumKExplicit(int[] a, int b) {
        Map<Integer, Integer> freq = new HashMap<>();
        freq.put(0, 1); // Empty prefix has XOR 0
        int count = 0;
        int xor = 0;
        
        for (int num : a) {
            xor ^= num;
            
            // If (xor ^ b) exists in map, add its frequency
            if (freq.containsKey(xor ^ b)) {
                count += freq.get(xor ^ b);
            }
            
            // Update frequency of current xor
            freq.put(xor, freq.getOrDefault(xor, 0) + 1);
        }
        
        return count;
    }
    
    /**
     * This version is cleaner because:
     * 1. Explicitly includes empty prefix
     * 2. No special case for xor == b (handled by xor ^ b = 0)
     * 3. More symmetric
     */
}
