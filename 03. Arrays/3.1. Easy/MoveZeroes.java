// Problem: LeetCode <ID>. <Title>
/**
 * Problem: Move Zeroes to End (Ninja and the Zero's)
 * 
 * Objective: Move all zeros to the end of the array while maintaining
 * the relative order of non-zero elements.
 * 
 * Key Constraints:
 * - Must be done in-place (modify the original array)
 * - Relative order of non-zero elements must be preserved
 * - Minimize the total number of operations
 * 
 * Example:
 * Input:  [0, 1, 0, 3, 12]
 * Output: [1, 3, 12, 0, 0]
 * 
 * Optimal Approach: Two-pointer in-place rearrangement
 * Time Complexity: O(n) - Single pass through array
 * Space Complexity: O(1) - Only using constant extra space
 */

public class MoveZeroes {
    
    /**
     * PRIMARY SOLUTION: Two-pointer write position tracking
     * 
     * Algorithm:
     * 1. Initialize insertPos = 0 (next position for non-zero element)
     * 2. Traverse array from left to right:
     *    - If current element != 0:
     *        * Copy it to arr[insertPos]
     *        * Increment insertPos
     * 3. Fill remaining positions from insertPos to end with zeros
     * 
     * @param n Size of the array
     * @param a The array to be modified (will be modified in-place)
     * @return The modified array (same reference)
     * 
     * Number of writes: O(n) - Each element written at most once
     * Number of reads: O(n) - Each element read once
     */
    public static int[] moveZeros(int n, int[] a) {
        // Edge case: empty or single element array
        if (n <= 1) {
            return a;
        }
        
        int insertPos = 0; // Pointer for next non-zero position
        
        // Step 1: Move all non-zero elements to front
        for (int i = 0; i < n; i++) {
            if (a[i] != 0) {
                a[insertPos] = a[i];
                insertPos++;
            }
        }
        
        // Step 2: Fill remaining positions with zeros
        while (insertPos < n) {
            a[insertPos] = 0;
            insertPos++;
        }
        
        return a;
    }
    
    /**
     * ALTERNATIVE: Single pass with swapping
     * 
     * Advantages:
     * - Minimizes writes (especially useful if writing is expensive)
     * - Each zero is swapped at most once
     * 
     * Disadvantages:
     * - More operations per iteration (swap vs copy)
     * - Slightly more complex logic
     * 
     * Algorithm:
     * 1. Initialize lastNonZeroPos = 0
     * 2. Traverse array:
     *    - If current element != 0:
     *        * Swap with element at lastNonZeroPos
     *        * Increment lastNonZeroPos
     */
    public static int[] moveZerosSwap(int n, int[] a) {
        int lastNonZeroPos = 0;
        
        for (int i = 0; i < n; i++) {
            if (a[i] != 0) {
                // Swap current non-zero with position at lastNonZeroPos
                int temp = a[lastNonZeroPos];
                a[lastNonZeroPos] = a[i];
                a[i] = temp;
                lastNonZeroPos++;
            }
        }
        
        return a;
    }
    
    /**
     * ALTERNATIVE: Using two-pointer snowball approach
     * 
     * Concept: Think of zeros as snowball that grows as we encounter them
     * When we find non-zero, we swap it with first zero in snowball
     */
    public static int[] moveZerosSnowball(int[] a) {
        int snowballSize = 0;
        
        for (int i = 0; i < a.length; i++) {
            if (a[i] == 0) {
                snowballSize++;
            } else if (snowballSize > 0) {
                // Swap current element with first zero in snowball
                int temp = a[i];
                a[i] = 0;
                a[i - snowballSize] = temp;
            }
        }
        
        return a;
    }
    
    /**
     * Detailed Example Walkthrough - First Method:
     * Input: [0, 1, 0, 3, 12]
     * 
     * Step-by-step:
     * 
     * Initial: [0, 1, 0, 3, 12], insertPos = 0
     * 
     * i=0: a[0]=0 → skip
     *      Array: [0, 1, 0, 3, 12], insertPos = 0
     *      
     * i=1: a[1]=1 != 0 → a[0]=1, insertPos=1
     *      Array: [1, 1, 0, 3, 12], insertPos = 1
     *      
     * i=2: a[2]=0 → skip
     *      Array: [1, 1, 0, 3, 12], insertPos = 1
     *      
     * i=3: a[3]=3 != 0 → a[1]=3, insertPos=2
     *      Array: [1, 3, 0, 3, 12], insertPos = 2
     *      
     * i=4: a[4]=12 != 0 → a[2]=12, insertPos=3
     *      Array: [1, 3, 12, 3, 12], insertPos = 3
     *      
     * After loop: insertPos=3, n=5
     * Fill zeros: a[3]=0, a[4]=0
     * Final: [1, 3, 12, 0, 0]
     */
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Testing Move Zeroes ===");
        
        // Test Case 1: Example from problem
        System.out.println("\nTest 1: [0, 1, 0, 3, 12]");
        int[] test1 = {0, 1, 0, 3, 12};
        System.out.print("Original: ");
        printArray(test1);
        
        int[] result1 = moveZeros(test1.length, test1.clone());
        System.out.print("Method 1: ");
        printArray(result1);
        
        int[] result1Swap = moveZerosSwap(test1.length, test1.clone());
        System.out.print("Method 2: ");
        printArray(result1Swap);
        System.out.println("Expected: [1, 3, 12, 0, 0]");
        
        // Test Case 2: All zeros except one
        System.out.println("\nTest 2: [0, 0, 0, 5, 0]");
        int[] test2 = {0, 0, 0, 5, 0};
        int[] result2 = moveZeros(test2.length, test2.clone());
        System.out.print("Result: ");
        printArray(result2);
        System.out.println("Expected: [5, 0, 0, 0, 0]");
        
        // Test Case 3: No zeros
        System.out.println("\nTest 3: [1, 2, 3, 4, 5]");
        int[] test3 = {1, 2, 3, 4, 5};
        int[] result3 = moveZeros(test3.length, test3.clone());
        System.out.print("Result: ");
        printArray(result3);
        System.out.println("Expected: [1, 2, 3, 4, 5] (unchanged)");
        
        // Test Case 4: All zeros
        System.out.println("\nTest 4: [0, 0, 0]");
        int[] test4 = {0, 0, 0};
        int[] result4 = moveZeros(test4.length, test4.clone());
        System.out.print("Result: ");
        printArray(result4);
        System.out.println("Expected: [0, 0, 0] (unchanged)");
        
        // Test Case 5: Multiple zeros interspersed
        System.out.println("\nTest 5: [1, 0, 2, 0, 0, 3, 4]");
        int[] test5 = {1, 0, 2, 0, 0, 3, 4};
        int[] result5 = moveZeros(test5.length, test5.clone());
        System.out.print("Result: ");
        printArray(result5);
        System.out.println("Expected: [1, 2, 3, 4, 0, 0, 0]");
        
        // Test Case 6: Single element - zero
        System.out.println("\nTest 6: [0]");
        int[] test6 = {0};
        int[] result6 = moveZeros(test6.length, test6.clone());
        System.out.print("Result: ");
        printArray(result6);
        System.out.println("Expected: [0]");
        
        // Test Case 7: Single element - non-zero
        System.out.println("\nTest 7: [7]");
        int[] test7 = {7};
        int[] result7 = moveZeros(test7.length, test7.clone());
        System.out.print("Result: ");
        printArray(result7);
        System.out.println("Expected: [7]");
        
        // Test Case 8: Empty array
        System.out.println("\nTest 8: []");
        int[] test8 = {};
        int[] result8 = moveZeros(test8.length, test8.clone());
        System.out.print("Result: ");
        printArray(result8);
        System.out.println("Expected: []");
        
        // Test Case 9: Zeros at the end already
        System.out.println("\nTest 9: [1, 2, 3, 0, 0]");
        int[] test9 = {1, 2, 3, 0, 0};
        int[] result9 = moveZeros(test9.length, test9.clone());
        System.out.print("Result: ");
        printArray(result9);
        System.out.println("Expected: [1, 2, 3, 0, 0] (unchanged)");
        
        // Test Case 10: Zeros at the beginning
        System.out.println("\nTest 10: [0, 0, 1, 2, 3]");
        int[] test10 = {0, 0, 1, 2, 3};
        int[] result10 = moveZeros(test10.length, test10.clone());
        System.out.print("Result: ");
        printArray(result10);
        System.out.println("Expected: [1, 2, 3, 0, 0]");
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeArray = new int[1000000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = Math.random() > 0.3 ? (int)(Math.random() * 100) : 0;
        }
        
        // Method 1
        int[] copy1 = largeArray.clone();
        long start1 = System.currentTimeMillis();
        moveZeros(copy1.length, copy1);
        long end1 = System.currentTimeMillis();
        
        // Method 2
        int[] copy2 = largeArray.clone();
        long start2 = System.currentTimeMillis();
        moveZerosSwap(copy2.length, copy2);
        long end2 = System.currentTimeMillis();
        
        System.out.println("Array size: 1,000,000 elements");
        System.out.println("Method 1 (copy): " + (end1 - start1) + " ms");
        System.out.println("Method 2 (swap): " + (end2 - start2) + " ms");
    }
    
    /**
     * Helper method to print array
     */
    private static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. USING EXTRA ARRAY:
     *    ❌ Creating new array and copying non-zeros, then zeros
     *    This is O(n) space, not in-place
     *    
     * 2. BUBBLE SORT APPROACH:
     *    ❌ Repeatedly swapping zeros to end (O(n²) time)
     *    This is inefficient for large arrays
     *    
     * 3. NOT PRESERVING ORDER:
     *    ❌ Using two-pointer from both ends that swaps without order preservation
     *    
     * 4. OVERCOMPLICATING:
     *    ❌ Counting zeros first, then creating new array
     *    Unnecessary extra pass
     */
    
    /**
     * Algorithm Analysis:
     * 
     * Method 1 (Copy Approach):
     * - Best Case: O(n) when no zeros (all elements copied)
     * - Worst Case: O(n) when all zeros (no copies, only fill)
     * - Average Case: O(n)
     * - Number of writes: n (each element written exactly once)
     * 
     * Method 2 (Swap Approach):
     * - Best Case: O(n) when no zeros (no swaps, just pointer moves)
     * - Worst Case: O(n) when all zeros (no swaps)
     * - Average Case: O(n) with 3 operations per swap
     * - Number of writes: ≤ 2k where k is number of non-zeros
     * 
     * Trade-off: Method 1 does exactly n writes. Method 2 does fewer writes
     * but more operations per write. For most cases, either is fine.
     */
}
