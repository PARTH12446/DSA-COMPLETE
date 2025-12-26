// Problem: LeetCode <ID>. <Title>
// Problem: Longest Successive Elements (Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/longest-successive-elements_6811740

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LongestConsecutive {

    /**
     * Solution using sorting - O(n log n) time, O(1) space (or O(n) for sort)
     * Handles duplicates by skipping them, counts only consecutive sequences
     * 
     * @param arr Input array of integers
     * @return Length of longest consecutive sequence
     */
    public static int longestSuccessiveElements(int[] arr) {
        if (arr.length == 0) return 0;
        
        // Sort the array - O(n log n)
        Arrays.sort(arr);
        
        int maxLen = 1;    // Track longest sequence found
        int curLen = 1;    // Track current consecutive sequence
        
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i + 1] == arr[i]) {
                // Skip duplicates - they don't break sequence but don't increase length
                continue;
            } else if (arr[i + 1] - arr[i] == 1) {
                // Consecutive number found - extend current sequence
                curLen++;
            } else {
                // Sequence broken - update max and reset current
                if (curLen > maxLen) maxLen = curLen;
                curLen = 1;
            }
        }
        
        // Final check for last sequence
        return Math.max(curLen, maxLen);
    }
    
    /**
     * Optimal solution using HashSet - O(n) time, O(n) space
     * Finds sequences by looking for sequence starts
     * 
     * @param arr Input array
     * @return Length of longest consecutive sequence
     */
    public static int longestSuccessiveElementsOptimal(int[] arr) {
        if (arr.length == 0) return 0;
        
        Set<Integer> numSet = new HashSet<>();
        for (int num : arr) {
            numSet.add(num);
        }
        
        int longest = 1;
        
        for (int num : arr) {
            // Check if this is the start of a sequence
            // (no num-1 in set means it's a start)
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;
                
                // Count consecutive numbers from this start
                while (numSet.contains(currentNum + 1)) {
                    currentNum++;
                    currentStreak++;
                }
                
                longest = Math.max(longest, currentStreak);
            }
        }
        
        return longest;
    }
    
    /**
     * Alternative using HashSet with early exit optimization
     */
    public static int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;
        
        Set<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);
        
        int maxLength = 0;
        
        for (int num : nums) {
            // Only process if this could be the start of a longer sequence
            if (!set.contains(num - 1)) {
                int currentNum = num;
                int currentLength = 1;
                
                while (set.contains(currentNum + 1)) {
                    currentNum++;
                    currentLength++;
                }
                
                maxLength = Math.max(maxLength, currentLength);
                
                // Early exit if we found maximum possible length
                if (maxLength > nums.length / 2) break;
            }
        }
        
        return maxLength;
    }
    
    /**
     * Main method with test cases
     */
    public static void main(String[] args) {
        // Test Case 1: Normal case with duplicates
        int[] test1 = {100, 4, 200, 1, 3, 2};
        System.out.println("Test 1: " + Arrays.toString(test1));
        System.out.println("Sorting method: " + longestSuccessiveElements(test1));
        System.out.println("Optimal method: " + longestSuccessiveElementsOptimal(test1));
        System.out.println("Expected: 4 (sequence: 1, 2, 3, 4)");
        System.out.println();
        
        // Test Case 2: With negative numbers
        int[] test2 = {0, -1, -2, 1, 2, 3};
        System.out.println("Test 2: " + Arrays.toString(test2));
        System.out.println("Result: " + longestSuccessiveElements(test2));
        System.out.println("Expected: 5 (sequence: -2, -1, 0, 1, 2)");
        System.out.println();
        
        // Test Case 3: All same numbers
        int[] test3 = {5, 5, 5, 5};
        System.out.println("Test 3: " + Arrays.toString(test3));
        System.out.println("Result: " + longestSuccessiveElements(test3));
        System.out.println("Expected: 1 (single element sequence)");
        System.out.println();
        
        // Test Case 4: Empty array
        int[] test4 = {};
        System.out.println("Test 4: " + Arrays.toString(test4));
        System.out.println("Result: " + longestSuccessiveElements(test4));
        System.out.println("Expected: 0");
        System.out.println();
        
        // Test Case 5: Multiple sequences
        int[] test5 = {10, 5, 12, 11, 1, 2, 3};
        System.out.println("Test 5: " + Arrays.toString(test5));
        System.out.println("Result: " + longestSuccessiveElements(test5));
        System.out.println("Expected: 3 (sequence: 1, 2, 3 or 10, 11, 12)");
    }
}

// Algorithm Analysis:
// Sorting Approach (current implementation):
// 1. Sort array ? O(n log n)
// 2. Traverse once to find sequences ? O(n)
// 3. Skip duplicates, count consecutive differences of 1
// 4. Time: O(n log n), Space: O(1) or O(n) depending on sort implementation

// HashSet Approach (optimal):
// 1. Add all elements to HashSet ? O(n)
// 2. For each element, check if it's a sequence start (no num-1 in set)
// 3. If start, count consecutive numbers by incrementing
// 4. Track maximum length found
// 5. Time: O(n), Space: O(n)

// Key Points:
// 1. Problem asks for consecutive integers, not necessarily in sorted order in array
// 2. Duplicates don't count toward sequence length but don't break sequence
// 3. Negative numbers work the same way
// 4. Empty array returns 0
// 5. Single element returns 1

// Edge Cases:
// - Empty array ? return 0
// - All duplicates ? return 1
// - Already sorted array
// - Array with Integer.MIN_VALUE and Integer.MAX_VALUE
// - Large arrays (need efficient solution)

// Common Mistakes:
// 1. Forgetting to handle duplicates
// 2. Counting array indices instead of values
// 3. Not resetting current length when sequence breaks
// 4. Using O(n²) naive approach (checking all pairs)
