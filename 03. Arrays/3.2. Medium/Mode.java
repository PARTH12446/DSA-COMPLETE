// Problem: LeetCode <ID>. <Title>
// Problem: Majority Element (Coding Ninjas)
// Converted from Python to Java (Boyer-Moore)
// Source: https://www.codingninjas.com/studio/problems/majority-element_6783241

// Problem: Find element that appears more than n/2 times in array
// Assumption: Majority element always exists (per problem statement)

public class Mode {

    /**
     * Boyer-Moore Majority Vote Algorithm
     * Time: O(n), Space: O(1)
     * 
     * @param v Input array with majority element guaranteed
     * @return Majority element (appears > n/2 times)
     */
    public static int majorityElement(int[] v) {
        int candidate = 0;  // Current potential majority element
        int count = 0;      // Net "votes" for current candidate
        
        for (int num : v) {
            if (count == 0) {
                // No current candidate, pick this element
                candidate = num;
                count = 1;
            } else if (num == candidate) {
                // Same as candidate, increase count
                count++;
            } else {
                // Different from candidate, decrease count
                count--;
            }
        }
        
        // Since majority is guaranteed, candidate is the answer
        return candidate;
        
        // If majority not guaranteed, need verification pass:
        // count = 0;
        // for (int num : v) if (num == candidate) count++;
        // return count > v.length/2 ? candidate : -1;
    }
    
    /**
     * Alternative: Using HashMap (O(n) time, O(n) space)
     */
    public static int majorityElementHashMap(int[] v) {
        java.util.Map<Integer, Integer> countMap = new java.util.HashMap<>();
        
        for (int num : v) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
            if (countMap.get(num) > v.length / 2) {
                return num;
            }
        }
        return -1; // Shouldn't reach here if majority exists
    }
    
    /**
     * Alternative: Sorting approach (O(n log n) time, O(1) space if in-place)
     */
    public static int majorityElementSort(int[] v) {
        java.util.Arrays.sort(v);
        return v[v.length / 2];  // Middle element must be majority
    }
    
    /**
     * Alternative: Bit manipulation (for integers only)
     * Count bits position by position
     */
    public static int majorityElementBits(int[] v) {
        int majority = 0;
        int n = v.length;
        
        // For each bit position (assuming 32-bit integers)
        for (int i = 0; i < 32; i++) {
            int countOnes = 0;
            int mask = 1 << i;
            
            for (int num : v) {
                if ((num & mask) != 0) {
                    countOnes++;
                }
            }
            
            // If more than half have this bit set, set it in result
            if (countOnes > n / 2) {
                majority |= mask;
            }
        }
        
        return majority;
    }
    
    /**
     * Boyer-Moore with verification (for when majority not guaranteed)
     */
    public static int majorityElementWithVerification(int[] v) {
        if (v.length == 0) return -1;
        
        // Boyer-Moore first pass
        int candidate = 0;
        int count = 0;
        
        for (int num : v) {
            if (count == 0) {
                candidate = num;
                count = 1;
            } else if (num == candidate) {
                count++;
            } else {
                count--;
            }
        }
        
        // Verification second pass
        count = 0;
        for (int num : v) {
            if (num == candidate) {
                count++;
            }
        }
        
        return count > v.length / 2 ? candidate : -1;
    }
    
    /**
     * Variation: Find all elements appearing more than n/3 times
     * Extended Boyer-Moore for k-1 candidates
     */
    public static java.util.List<Integer> majorityElementII(int[] v) {
        java.util.List<Integer> result = new java.util.ArrayList<>();
        if (v.length == 0) return result;
        
        // For n/3 threshold, at most 2 such elements can exist
        int candidate1 = 0, candidate2 = 0;
        int count1 = 0, count2 = 0;
        
        // First pass: find two potential candidates
        for (int num : v) {
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
                count1--;
                count2--;
            }
        }
        
        // Second pass: verify candidates
        count1 = 0;
        count2 = 0;
        for (int num : v) {
            if (num == candidate1) count1++;
            else if (num == candidate2) count2++;
        }
        
        if (count1 > v.length / 3) result.add(candidate1);
        if (count2 > v.length / 3) result.add(candidate2);
        
        return result;
    }
    
    /**
     * Main method with test cases
     */
    public static void main(String[] args) {
        // Test Case 1: Simple case
        int[] test1 = {2, 2, 1, 1, 1, 2, 2};
        System.out.println("Test 1: " + java.util.Arrays.toString(test1));
        System.out.println("Boyer-Moore: " + majorityElement(test1));
        System.out.println("Expected: 2");
        System.out.println("HashMap: " + majorityElementHashMap(test1));
        System.out.println("Sort: " + majorityElementSort(test1));
        System.out.println();
        
        // Test Case 2: All same element
        int[] test2 = {5, 5, 5, 5};
        System.out.println("Test 2: " + java.util.Arrays.toString(test2));
        System.out.println("Result: " + majorityElement(test2));
        System.out.println("Expected: 5");
        System.out.println();
        
        // Test Case 3: Alternating pattern
        int[] test3 = {1, 2, 1, 2, 1};
        System.out.println("Test 3: " + java.util.Arrays.toString(test3));
        System.out.println("Result: " + majorityElement(test3));
        System.out.println("Expected: 1");
        System.out.println();
        
        // Test Case 4: Single element
        int[] test4 = {7};
        System.out.println("Test 4: " + java.util.Arrays.toString(test4));
        System.out.println("Result: " + majorityElement(test4));
        System.out.println("Expected: 7");
        System.out.println();
        
        // Test Case 5: Large numbers
        int[] test5 = {100, 100, 200, 100, 100};
        System.out.println("Test 5: " + java.util.Arrays.toString(test5));
        System.out.println("Result: " + majorityElement(test5));
        System.out.println("Expected: 100");
        System.out.println();
        
        // Test Boyer-Moore with verification
        int[] test6 = {1, 2, 3, 4, 5};  // No majority
        System.out.println("Test 6 (no majority): " + java.util.Arrays.toString(test6));
        System.out.println("With verification: " + majorityElementWithVerification(test6));
        System.out.println("Expected: -1 (no majority)");
        System.out.println();
        
        // Test n/3 majority elements
        int[] test7 = {1, 1, 1, 3, 3, 2, 2, 2};
        System.out.println("Test 7 (n/3 majority): " + java.util.Arrays.toString(test7));
        System.out.println("Elements > n/3: " + majorityElementII(test7));
        System.out.println("Expected: [1, 2]");
        System.out.println();
        
        // Test bit manipulation approach
        int[] test8 = {3, 3, 4, 2, 4, 4, 2, 4, 4};
        System.out.println("Test 8: " + java.util.Arrays.toString(test8));
        System.out.println("Bit manipulation: " + majorityElementBits(test8));
        System.out.println("Expected: 4");
    }
}

// Algorithm Analysis (Boyer-Moore):
// Time Complexity: O(n) - Single pass through array
// Space Complexity: O(1) - Only two variables

// How Boyer-Moore Works:
// 1. Initialize candidate = 0, count = 0
// 2. For each element:
//    - If count == 0: set candidate = current, count = 1
//    - Else if current == candidate: count++
//    - Else: count--
// 3. Candidate is majority element (if majority exists)

// Why It Works (Intuition):
// Each pair of different elements cancels each other out
// Majority element appears > n/2 times, so it survives cancellation
// Think of it as "votes": majority has more than half the votes
// Non-majority elements can't outvote the majority

// Example Walkthrough: [2, 2, 1, 1, 1, 2, 2]
// i=0: num=2, count=0 ? candidate=2, count=1
// i=1: num=2, same ? count=2
// i=2: num=1, diff ? count=1
// i=3: num=1, diff ? count=0
// i=4: num=1, count=0 ? candidate=1, count=1
// i=5: num=2, diff ? count=0
// i=6: num=2, count=0 ? candidate=2, count=1
// Result: 2 (correct)

// Edge Cases:
// 1. Single element array ? returns that element
// 2. All elements same ? returns that element
// 3. Majority at beginning/end ? algorithm still works
// 4. Even length with exact n/2+1 majority ? works

// Verification Needed When:
// Majority existence is NOT guaranteed
// Need second pass to count actual occurrences

// Comparison of Approaches:
// 1. Boyer-Moore: O(n) time, O(1) space (optimal)
// 2. HashMap: O(n) time, O(n) space
// 3. Sorting: O(n log n) time, O(1) space if in-place
// 4. Bit counting: O(32n) time, O(1) space (for integers only)

// Extended Boyer-Moore (for n/k):
// For elements appearing > n/k times:
// - Can have at most k-1 such elements
// - Maintain k-1 candidates with counts
// - Cancel k different elements at once
// - Need verification pass

// Related Problems:
// 1. LeetCode 169: Majority Element (same)
// 2. LeetCode 229: Majority Element II (n/3)
// 3. Check if array has majority element
// 4. Find element in sorted array appearing > n/2 times

// Interview Tips:
// 1. Always ask: "Is majority guaranteed to exist?"
// 2. Start with brute force (count each element)
// 3. Optimize to HashMap (O(n) space)
// 4. Finally present Boyer-Moore (O(1) space)
// 5. Discuss proof/explanation of algorithm

// Applications:
// 1. Finding dominant color in image
// 2. Data stream majority element
// 3. Voting systems
// 4. Error detection/correction

// Performance:
// For n up to 10^6, Boyer-Moore is efficient
// Sorting would be O(n log n) ˜ 20M operations vs 1M

// Common Mistakes:
// 1. Forgetting to verify when majority not guaranteed
// 2. Initializing candidate with wrong value
// 3. Not understanding why algorithm works
// 4. Using algorithm for non-integer data without hashing

// Test Cases to Consider:
// 1. [1] ? 1
// 2. [1,2,1] ? 1
// 3. [2,2,1,1,1,2,2] ? 2
// 4. [3,3,4,2,4,4,2,4,4] ? 4
// 5. [1,2,3,4,5] ? -1 (if verification)
