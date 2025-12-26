// Problem: LeetCode <ID>. <Title>
// Problem: Sort an Array of 0s, 1s and 2s (Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/sort-an-array-of-0s-1s-and-2s_892977

// Dutch National Flag Algorithm (Three-way partitioning)
// Sort array containing only 0s, 1s, and 2s in O(n) time, O(1) space

public class Sort_0_1_2 {

    /**
     * Dutch National Flag Algorithm (three-way partitioning)
     * Time: O(n) - single pass, Space: O(1) - in-place
     * 
     * @param arr Array containing only 0s, 1s, and 2s
     * @param n Length of array
     */
    public static void sortArray(int[] arr, int n) {
        // Three pointers:
        // zeroPos: boundary of 0s (next position for 0)
        // onePos: current element being processed
        // twoPos: boundary of 2s (next position for 2 from right)
        int zeroPos = 0, onePos = 0, twoPos = n - 1;
        
        // Process until onePos crosses twoPos
        while (onePos <= twoPos) {
            if (arr[onePos] == 1) {
                // Current element is 1, leave it in middle section
                onePos++;
            } 
            else if (arr[onePos] == 0) {
                // Current element is 0, swap to beginning
                swap(arr, zeroPos, onePos);
                zeroPos++;  // Expand 0s section
                onePos++;   // Move to next element
            } 
            else { // arr[onePos] == 2
                // Current element is 2, swap to end
                swap(arr, onePos, twoPos);
                twoPos--;   // Expand 2s section
                // Don't increment onePos - need to check swapped element
            }
        }
    }
    
    /**
     * Helper: Swap two elements
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    /**
     * Alternative: Counting sort approach
     * Count frequencies then reconstruct
     */
    public static void sortArrayCounting(int[] arr, int n) {
        int count0 = 0, count1 = 0, count2 = 0;
        
        // Count occurrences
        for (int num : arr) {
            if (num == 0) count0++;
            else if (num == 1) count1++;
            else count2++;
        }
        
        // Reconstruct array
        int index = 0;
        for (int i = 0; i < count0; i++) arr[index++] = 0;
        for (int i = 0; i < count1; i++) arr[index++] = 1;
        for (int i = 0; i < count2; i++) arr[index++] = 2;
    }
    
    /**
     * Variation: Partition into three sections using two passes
     */
    public static void sortArrayTwoPass(int[] arr, int n) {
        // First pass: move all 0s to beginning
        int zeroIndex = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == 0) {
                swap(arr, i, zeroIndex);
                zeroIndex++;
            }
        }
        
        // Second pass: move all 1s after 0s
        int oneIndex = zeroIndex;
        for (int i = zeroIndex; i < n; i++) {
            if (arr[i] == 1) {
                swap(arr, i, oneIndex);
                oneIndex++;
            }
        }
        // Remaining are 2s automatically
    }
    
    /**
     * Variation: Sort array of three distinct values (not necessarily 0,1,2)
     */
    public static void sortThreeDistinct(int[] arr, int lowVal, int midVal, int highVal) {
        int low = 0, mid = 0, high = arr.length - 1;
        
        while (mid <= high) {
            if (arr[mid] == lowVal) {
                swap(arr, low, mid);
                low++;
                mid++;
            } else if (arr[mid] == midVal) {
                mid++;
            } else { // arr[mid] == highVal
                swap(arr, mid, high);
                high--;
            }
        }
    }
    
    /**
     * Variation: Move all zeros to end (keeping order of non-zeros)
     * Similar to LeetCode 283: Move Zeroes
     */
    public static void moveZerosToEnd(int[] arr) {
        int nonZeroPos = 0;
        
        // Move all non-zeros to front
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                swap(arr, i, nonZeroPos);
                nonZeroPos++;
            }
        }
    }
    
    /**
     * Variation: Sort colors (LeetCode version)
     */
    public static void sortColors(int[] nums) {
        int low = 0, mid = 0, high = nums.length - 1;
        
        while (mid <= high) {
            switch (nums[mid]) {
                case 0: // Red
                    swap(nums, low, mid);
                    low++;
                    mid++;
                    break;
                case 1: // White
                    mid++;
                    break;
                case 2: // Blue
                    swap(nums, mid, high);
                    high--;
                    break;
            }
        }
    }
    
    /**
     * Helper to print array
     */
    public static void printArray(int[] arr, int n) {
        System.out.print("[");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i]);
            if (i < n - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
    
    /**
     * Main method with test cases
     */
    public static void main(String[] args) {
        // Test Case 1: Standard example
        int[] test1 = {0, 1, 2, 1, 2, 1, 0};
        int n1 = test1.length;
        System.out.println("Test 1 - Original:");
        printArray(test1, n1);
        sortArray(test1, n1);
        System.out.println("After Dutch National Flag:");
        printArray(test1, n1);
        System.out.println("Expected: [0, 0, 1, 1, 1, 2, 2]");
        System.out.println();
        
        // Test Case 2: Already sorted
        int[] test2 = {0, 0, 1, 1, 2, 2};
        int n2 = test2.length;
        System.out.println("Test 2 - Already sorted:");
        printArray(test2, n2);
        sortArray(test2, n2);
        System.out.println("After sorting:");
        printArray(test2, n2);
        System.out.println();
        
        // Test Case 3: All zeros
        int[] test3 = {0, 0, 0, 0};
        int n3 = test3.length;
        System.out.println("Test 3 - All zeros:");
        printArray(test3, n3);
        sortArray(test3, n3);
        printArray(test3, n3);
        System.out.println();
        
        // Test Case 4: All ones
        int[] test4 = {1, 1, 1, 1};
        int n4 = test4.length;
        System.out.println("Test 4 - All ones:");
        printArray(test4, n4);
        sortArray(test4, n4);
        printArray(test4, n4);
        System.out.println();
        
        // Test Case 5: All twos
        int[] test5 = {2, 2, 2, 2};
        int n5 = test5.length;
        System.out.println("Test 5 - All twos:");
        printArray(test5, n5);
        sortArray(test5, n5);
        printArray(test5, n5);
        System.out.println();
        
        // Test Case 6: Single element
        int[] test6 = {1};
        int n6 = test6.length;
        System.out.println("Test 6 - Single element:");
        printArray(test6, n6);
        sortArray(test6, n6);
        printArray(test6, n6);
        System.out.println();
        
        // Test Case 7: Complex mix
        int[] test7 = {2, 0, 1, 2, 0, 1, 2, 0, 0};
        int n7 = test7.length;
        System.out.println("Test 7 - Complex mix:");
        printArray(test7, n7);
        sortArray(test7, n7);
        System.out.println("After sorting:");
        printArray(test7, n7);
        System.out.println("Expected: [0, 0, 0, 0, 1, 1, 2, 2, 2]");
        System.out.println();
        
        // Test counting sort approach
        int[] test8 = {2, 0, 2, 1, 1, 0};
        System.out.println("Test 8 - Counting sort:");
        printArray(test8, test8.length);
        sortArrayCounting(test8, test8.length);
        printArray(test8, test8.length);
        
        // Test three distinct values
        int[] test9 = {3, 1, 2, 3, 2, 1, 3};
        System.out.println("\nTest 9 - Three distinct values (1,2,3):");
        printArray(test9, test9.length);
        sortThreeDistinct(test9, 1, 2, 3);
        printArray(test9, test9.length);
        System.out.println("Expected: [1, 1, 2, 2, 3, 3, 3]");
    }
}

// Algorithm Analysis (Dutch National Flag):
// Time Complexity: O(n) - Single pass through array
// Space Complexity: O(1) - Only three pointers used

// Three Pointer Invariants:
// 1. [0, zeroPos): All elements are 0s
// 2. [zeroPos, onePos): All elements are 1s
// 3. [onePos, twoPos]: Unknown/unprocessed elements
// 4. (twoPos, n-1]: All elements are 2s

// How it works:
// - onePos scans through array
// - When we see 0: swap with zeroPos, expand 0s section
// - When we see 1: leave it, expand 1s section by moving onePos
// - When we see 2: swap with twoPos, expand 2s section
// - Stop when onePos > twoPos (all elements processed)

// Example Walkthrough: [0, 1, 2, 1, 2, 1, 0]
// zeroPos=0, onePos=0, twoPos=6
// 1. arr[0]=0: swap with zeroPos(0), zeroPos=1, onePos=1 ? [0,1,2,1,2,1,0]
// 2. arr[1]=1: onePos=2 ? [0,1,2,1,2,1,0]
// 3. arr[2]=2: swap with twoPos(6), twoPos=5 ? [0,1,0,1,2,1,2]
// 4. arr[2]=0: swap with zeroPos(1), zeroPos=2, onePos=3 ? [0,0,1,1,2,1,2]
// 5. arr[3]=1: onePos=4 ? [0,0,1,1,2,1,2]
// 6. arr[4]=2: swap with twoPos(5), twoPos=4 ? [0,0,1,1,1,2,2]
// 7. onePos=4 > twoPos=4 ? STOP

// Edge Cases:
// 1. Empty array ? nothing to do
// 2. Single element ? unchanged
// 3. Already sorted ? pointers move but no swaps needed
// 4. All same value ? algorithm still works
// 5. Large arrays ? O(n) time efficient

// Why not increment onePos when swapping with twoPos?
// Because element swapped from twoPos could be 0, 1, or 2
// Need to process it in next iteration

// Comparison of Approaches:
// 1. Dutch National Flag: O(n) time, O(1) space, single pass
// 2. Counting sort: O(n) time, O(1) space if counts stored in variables
// 3. Two-pass: O(2n) time, O(1) space, simpler logic
// 4. Library sort: O(n log n) time, not optimal for this problem

// Related Problems:
// 1. LeetCode 75: Sort Colors (identical)
// 2. Move all zeros to end (LeetCode 283)
// 3. Partition array (LeetCode 86)
// 4. Sort by parity (move evens before odds)

// Applications:
// 1. Sorting colors in image processing
// 2. Three-way partitioning in quicksort
// 3. Flag sorting problems
// 4. Data with three categories

// Interview Tips:
// 1. Explain the three pointer invariants
// 2. Walk through example step by step
// 3. Discuss why onePos doesn't always increment
// 4. Handle edge cases
// 5. Compare with alternative approaches

// Common Mistakes:
// 1. Incrementing onePos when swapping with twoPos
// 2. Wrong loop condition (should be onePos <= twoPos)
// 3. Not handling empty/single element arrays
// 4. Confusing which pointer to swap with
// 5. Off-by-one errors in pointer updates

// Performance:
// For n up to 10^5, O(n) algorithm works efficiently
// Counting sort is also O(n) but requires two passes through data

// Key Insight:
// This is essentially a three-way partition of the array
// Similar to what happens in one pass of quicksort with pivot value 1
