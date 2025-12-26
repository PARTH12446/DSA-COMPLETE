// Problem: LeetCode <ID>. <Title>
/**
 * Problem: Linear Search
 * 
 * Objective: Find the first occurrence of a target value in an array.
 * Return the index if found, -1 if not found.
 * 
 * Key Points:
 * - Works on both sorted and unsorted arrays
 * - Returns FIRST occurrence (not necessarily only occurrence)
 * - Simple but O(n) time complexity
 * - No preprocessing or special data structures needed
 * 
 * Time Complexity: O(n) - May need to check all elements in worst case
 * Space Complexity: O(1) - Only using constant extra space
 * 
 * Use Cases:
 * - Small arrays (n ≤ 100)
 * - Unsorted arrays where binary search cannot be used
 * - When you need to find first occurrence, not just existence
 */

public class LinearSearch {
    
    /**
     * PRIMARY SOLUTION: Basic Linear Search
     * 
     * Algorithm:
     * 1. Iterate through array from start to end (index 0 to n-1)
     * 2. Compare each element with target value
     * 3. If match found, return current index immediately
     * 4. If loop completes without finding, return -1
     * 
     * @param n Size of the array
     * @param num Target value to search for
     * @param arr The array to search in
     * @return Index of first occurrence, or -1 if not found
     * 
     * Characteristics:
     * - Early return on first match
     * - Handles empty arrays gracefully
     * - Works with any data type (with appropriate comparison)
     */
    public static int linearSearch(int n, int num, int[] arr) {
        for (int i = 0; i < n; i++) {
            if (arr[i] == num) {
                return i; // Found at index i
            }
        }
        return -1; // Not found
    }
    
    /**
     * ALTERNATIVE: Enhanced for loop with index tracking
     * 
     * Advantages:
     * - Cleaner syntax
     * - Less error-prone (no manual index management)
     * 
     * Disadvantages:
     * - Need to track index separately
     * - Slightly less efficient for primitive arrays
     */
    public static int linearSearchEnhanced(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * ALTERNATIVE: Using while loop
     * 
     * Useful when you need more control over loop conditions
     */
    public static int linearSearchWhile(int[] arr, int target) {
        int i = 0;
        while (i < arr.length) {
            if (arr[i] == target) {
                return i;
            }
            i++;
        }
        return -1;
    }
    
    /**
     * ALTERNATIVE: Recursive approach (for educational purposes)
     * 
     * Not recommended for production due to stack overflow risk
     * Demonstrates recursive thinking
     */
    public static int linearSearchRecursive(int[] arr, int target, int index) {
        // Base cases
        if (index >= arr.length) {
            return -1; // Not found
        }
        if (arr[index] == target) {
            return index; // Found
        }
        // Recursive case
        return linearSearchRecursive(arr, target, index + 1);
    }
    
    /**
     * EXTENSION: Find all occurrences
     * 
     * Returns an array of indices where target is found
     * Useful when you need all positions, not just first
     */
    public static int[] linearSearchAll(int[] arr, int target) {
        // First pass: count occurrences
        int count = 0;
        for (int num : arr) {
            if (num == target) {
                count++;
            }
        }
        
        // Second pass: collect indices
        int[] indices = new int[count];
        int pos = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                indices[pos] = i;
                pos++;
            }
        }
        
        return indices;
    }
    
    /**
     * Detailed Example Walkthrough:
     * Input: arr = [2, 5, 1, 3, 0], target = 3
     * 
     * Step-by-step:
     * 
     * i=0: arr[0] = 2, 2 == 3? No → continue
     * i=1: arr[1] = 5, 5 == 3? No → continue
     * i=2: arr[2] = 1, 1 == 3? No → continue
     * i=3: arr[3] = 3, 3 == 3? Yes → return 3
     * 
     * Result: 3 (found at index 3)
     */
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Testing Linear Search ===");
        
        // Test Case 1: Target present in middle
        System.out.println("\nTest 1: [2, 5, 1, 3, 0], target = 3");
        int[] test1 = {2, 5, 1, 3, 0};
        int result1 = linearSearch(test1.length, 3, test1);
        System.out.println("Result: Index " + result1);
        System.out.println("Expected: 3");
        
        // Test Case 2: Target at beginning
        System.out.println("\nTest 2: [2, 5, 1, 3, 0], target = 2");
        int result2 = linearSearch(test1.length, 2, test1);
        System.out.println("Result: Index " + result2);
        System.out.println("Expected: 0");
        
        // Test Case 3: Target at end
        System.out.println("\nTest 3: [2, 5, 1, 3, 0], target = 0");
        int result3 = linearSearch(test1.length, 0, test1);
        System.out.println("Result: Index " + result3);
        System.out.println("Expected: 4");
        
        // Test Case 4: Target not present
        System.out.println("\nTest 4: [2, 5, 1, 3, 0], target = 7");
        int result4 = linearSearch(test1.length, 7, test1);
        System.out.println("Result: Index " + result4);
        System.out.println("Expected: -1");
        
        // Test Case 5: Empty array
        System.out.println("\nTest 5: [], target = 5");
        int[] test5 = {};
        int result5 = linearSearch(test5.length, 5, test5);
        System.out.println("Result: Index " + result5);
        System.out.println("Expected: -1");
        
        // Test Case 6: Single element, found
        System.out.println("\nTest 6: [10], target = 10");
        int[] test6 = {10};
        int result6 = linearSearch(test6.length, 10, test6);
        System.out.println("Result: Index " + result6);
        System.out.println("Expected: 0");
        
        // Test Case 7: Single element, not found
        System.out.println("\nTest 7: [10], target = 5");
        int result7 = linearSearch(test6.length, 5, test6);
        System.out.println("Result: Index " + result7);
        System.out.println("Expected: -1");
        
        // Test Case 8: Multiple occurrences
        System.out.println("\nTest 8: [1, 2, 3, 2, 4, 2, 5], target = 2");
        int[] test8 = {1, 2, 3, 2, 4, 2, 5};
        int result8 = linearSearch(test8.length, 2, test8);
        System.out.println("Result: Index " + result8 + " (first occurrence)");
        System.out.println("Expected: 1");
        
        // Test all occurrences
        int[] allOccurrences = linearSearchAll(test8, 2);
        System.out.print("All occurrences: ");
        for (int idx : allOccurrences) {
            System.out.print(idx + " ");
        }
        System.out.println("\nExpected: 1, 3, 5");
        
        // Test Case 9: Large array performance
        System.out.println("\n=== Performance Test ===");
        int size = 1000000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size; i++) {
            largeArray[i] = i; // Sorted array 0..999999
        }
        
        // Best case: element at beginning
        long start1 = System.currentTimeMillis();
        linearSearch(size, 0, largeArray);
        long end1 = System.currentTimeMillis();
        System.out.println("Best case (first element): " + (end1 - start1) + " ms");
        
        // Average case: element in middle
        long start2 = System.currentTimeMillis();
        linearSearch(size, size/2, largeArray);
        long end2 = System.currentTimeMillis();
        System.out.println("Average case (middle): " + (end2 - start2) + " ms");
        
        // Worst case: element at end (or not present)
        long start3 = System.currentTimeMillis();
        linearSearch(size, size-1, largeArray);
        long end3 = System.currentTimeMillis();
        System.out.println("Worst case (last element): " + (end3 - start3) + " ms");
        
        // Test Case 10: Comparison of all methods
        System.out.println("\n=== Method Comparison ===");
        int[] test10 = {1, 2, 3, 4, 5};
        System.out.println("Testing all methods on [1, 2, 3, 4, 5], target = 3");
        System.out.println("Basic for-loop: " + linearSearch(test10.length, 3, test10));
        System.out.println("Enhanced for-loop: " + linearSearchEnhanced(test10, 3));
        System.out.println("While loop: " + linearSearchWhile(test10, 3));
        System.out.println("Recursive: " + linearSearchRecursive(test10, 3, 0));
    }
}
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. RETURNING WRONG TYPE:
     *    ❌ Returning boolean instead of index
     *    ❌ Returning 0 for not found (0 is a valid index!)
     *    
     * 2. OFF-BY-ONE ERRORS:
     *    ❌ for (int i = 1; i <= n; i++) // Skips first element or causes index out of bounds
     *    
     * 3. NOT HANDLING EMPTY ARRAYS:
     *    ❌ Throws ArrayIndexOutOfBoundsException
     *    
     * 4. CONTINUING AFTER FINDING:
     *    ❌ Not using early return, continuing to search
     *    This finds last occurrence instead of first
     *    
     * 5. USING == FOR OBJECT COMPARISON:
     *    For non-primitive arrays, use .equals() instead of ==
     */
    
    /**
     * When to Use Linear Search vs Binary Search:
     * 
     * USE LINEAR SEARCH WHEN:
     * - Array is unsorted
     * - Array is small (n ≤ 100)
     * - You need to find first occurrence
     * - You're searching multiple different values in unsorted array
     * 
     * USE BINARY SEARCH WHEN:
     * - Array is sorted
     * - Array is large (n > 100)
     * - You need to perform multiple searches on same sorted array
     * 
     * Performance Comparison:
     * - Linear Search: O(n) average and worst case
     * - Binary Search: O(log n) average and worst case (but requires sorted array)
     */
    
    
