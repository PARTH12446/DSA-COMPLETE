// Problem: LeetCode <ID>. <Title>
/**
 * Problem: Largest Element in Array
 * 
 * Objective: Find the largest element in an unsorted array.
 * 
 * Key Points:
 * - Array may contain positive, negative, or zero values
 * - Array may be empty or contain duplicates
 * - Single pass O(n) solution is optimal (cannot do better)
 * 
 * Optimal Approach: Single pass with max tracking
 * Time Complexity: O(n) - Must check every element at least once
 * Space Complexity: O(1) - Only using constant extra space
 */

public class LargestElement {
    
    /**
     * OPTIMAL SOLUTION: Single Pass Maximum Tracking
     * 
     * Approach:
     * 1. Initialize max with first element (or MIN_VALUE for empty arrays)
     * 2. Iterate through each element in array:
     *    - Compare current element with current max
     *    - Update max if element is larger
     * 3. Return max
     * 
     * @param arr The input array
     * @param n Size of the array
     * @return Largest element or Integer.MIN_VALUE if array is empty
     * 
     * Example: [2, 5, 1, 3, 0] → Returns 5
     * 
     * Edge Cases:
     * - Empty array → Returns Integer.MIN_VALUE
     * - Single element → Returns that element
     * - All negative numbers → Returns largest (closest to zero)
     */
    public static int largestElement(int[] arr, int n) {
        // Handle empty array
        if (n == 0) {
            return Integer.MIN_VALUE; // Sentinel value for empty array
        }
        
        // Start with first element as max
        int max = arr[0];
        
        // Compare with remaining elements
        for (int i = 1; i < n; i++) {
            if (arr[i] > max) {
                max = arr[i]; // Update max if current element is larger
            }
        }
        
        return max;
    }
    
    /**
     * Alternative: Using Integer.MIN_VALUE as initial value
     * 
     * Advantages:
     * - Works correctly even if array is empty (returns MIN_VALUE)
     * - More explicit about handling edge cases
     * 
     * Disadvantages:
     * - If array contains only MIN_VALUE, we can't distinguish from empty case
     * - Less intuitive for beginners
     */
    public static int largestElement2(int[] arr, int n) {
        int max = Integer.MIN_VALUE;
        
        for (int value : arr) {
            if (value > max) {
                max = value;
            }
        }
        
        return max; // Returns MIN_VALUE if array is empty
    }
    
    /**
     * Another alternative: Using enhanced for loop
     * More readable but requires array to be non-null
     */
    public static int largestElement3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return Integer.MIN_VALUE;
        }
        
        int max = arr[0];
        for (int num : arr) {
            max = Math.max(max, num); // Using Math.max for cleaner code
        }
        
        return max;
    }
    
    /**
     * Detailed Example Walkthrough:
     * Input: [2, 5, 1, 3, 0]
     * 
     * Step 1: Initialize max = arr[0] = 2
     * Step 2: Iteration:
     *   - i=1: arr[1]=5 > 2 → max = 5
     *   - i=2: arr[2]=1 > 5? No → max stays 5
     *   - i=3: arr[3]=3 > 5? No → max stays 5
     *   - i=4: arr[4]=0 > 5? No → max stays 5
     * Step 3: Return 5
     * 
     * Visual Representation:
     * Initial: max = 2
     * After arr[1]: max = 5
     * After arr[2]: max = 5 (unchanged)
     * After arr[3]: max = 5 (unchanged)
     * After arr[4]: max = 5 (unchanged)
     */
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Testing Largest Element ===");
        
        // Test Case 1: Normal case with positive numbers
        int[] test1 = {2, 5, 1, 3, 0};
        System.out.println("\nTest 1: [2, 5, 1, 3, 0]");
        System.out.println("Method 1: " + largestElement(test1, test1.length));
        System.out.println("Method 2: " + largestElement2(test1, test1.length));
        System.out.println("Method 3: " + largestElement3(test1));
        System.out.println("Expected: 5");
        
        // Test Case 2: All negative numbers
        int[] test2 = {-5, -2, -10, -1};
        System.out.println("\nTest 2: All negatives [-5, -2, -10, -1]");
        System.out.println("Result: " + largestElement(test2, test2.length));
        System.out.println("Expected: -1 (closest to zero)");
        
        // Test Case 3: Single element
        int[] test3 = {7};
        System.out.println("\nTest 3: Single element [7]");
        System.out.println("Result: " + largestElement(test3, test3.length));
        System.out.println("Expected: 7");
        
        // Test Case 4: Empty array
        int[] test4 = {};
        System.out.println("\nTest 4: Empty array []");
        System.out.println("Result: " + largestElement(test4, 0));
        System.out.println("Expected: " + Integer.MIN_VALUE);
        
        // Test Case 5: All equal elements
        int[] test5 = {3, 3, 3, 3, 3};
        System.out.println("\nTest 5: All equal [3, 3, 3, 3, 3]");
        System.out.println("Result: " + largestElement(test5, test5.length));
        System.out.println("Expected: 3");
        
        // Test Case 6: Mixed positive and negative
        int[] test6 = {-10, 5, 0, -3, 8};
        System.out.println("\nTest 6: Mixed [-10, 5, 0, -3, 8]");
        System.out.println("Result: " + largestElement(test6, test6.length));
        System.out.println("Expected: 8");
        
        // Test Case 7: Large numbers
        int[] test7 = {Integer.MAX_VALUE, 100, 0};
        System.out.println("\nTest 7: With MAX_VALUE");
        System.out.println("Result: " + largestElement(test7, test7.length));
        System.out.println("Expected: " + Integer.MAX_VALUE);
        
        // Test Case 8: Contains MIN_VALUE
        int[] test8 = {Integer.MIN_VALUE, -100, 0};
        System.out.println("\nTest 8: Contains MIN_VALUE");
        System.out.println("Result: " + largestElement(test8, test8.length));
        System.out.println("Expected: 0");
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[] largeArray = new int[1000000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = (int) (Math.random() * 1000000);
        }
        
        long startTime = System.currentTimeMillis();
        int result = largestElement(largeArray, largeArray.length);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Array size: 1,000,000 elements");
        System.out.println("Largest element found: " + result);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. INITIALIZING WITH 0:
     *    ❌ int max = 0; 
     *    This fails for arrays with all negative numbers
     *    
     *    ✅ int max = arr[0];
     *    This works for all cases (except empty array)
     *    
     * 2. OFF-BY-ONE ERRORS:
     *    ❌ for (int i = 0; i < n; i++) // Starting from 0 compares arr[0] with itself
     *    
     *    ✅ for (int i = 1; i < n; i++) // Start from 1 to avoid self-comparison
     *    
     * 3. NOT HANDLING EMPTY ARRAYS:
     *    ❌ Throws ArrayIndexOutOfBoundsException for empty array
     *    
     *    ✅ Check if n == 0 and return appropriate value
     *    
     * 4. USING <= INSTEAD OF <:
     *    This is usually fine but may do one extra unnecessary comparison
     */
    
    /**
     * Alternative Algorithms (for educational purposes):
     * 
     * 1. SORTING APPROACH:
     *    Arrays.sort(arr);
     *    return arr[n-1];
     *    Time: O(n log n), Space: O(1) or O(n) depending on sort
     *    Not optimal but simple
     *    
     * 2. DIVIDE AND CONQUER:
     *    Recursively find max in left and right halves
     *    Time: O(n), Space: O(log n) for recursion stack
     *    More complex, no real advantage
     *    
     * 3. USING STREAMS (Java 8+):
     *    return Arrays.stream(arr).max().orElse(Integer.MIN_VALUE);
     *    Time: O(n), Space: O(1) but creates stream overhead
     *    Clean but less efficient for primitive arrays
     */
}
