// Problem: LeetCode <ID>. <Title>
// Problem: Next Greater Permutation (Coding Ninjas)
// Converted from Python to Java
// Source: https://www.codingninjas.com/studio/problems/next-greater-permutation_6929564

// Problem: Find the next lexicographically greater permutation of array
// If it's the last permutation, return the first (sorted ascending)

public class NextPermutation {

    /**
     * Next Permutation Algorithm (Narayana Pandita's algorithm)
     * Time: O(n), Space: O(1) - in-place modification
     * 
     * @param arr Input array to find next permutation of
     * @return Next greater permutation (modified in-place)
     */
    public static int[] nextGreaterPermutation(int[] arr) {
        int n = arr.length;
        
        // Step 1: Find first decreasing element from right
        // This is the "pivot" that needs to be increased
        int pivot = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] < arr[i + 1]) {
                pivot = i;
                break;
            }
        }
        
        // If no pivot found, array is in descending order
        // Next permutation is the first (sorted ascending)
        if (pivot == -1) {
            reverse(arr, 0, n - 1);
            return arr;
        }
        
        // Step 2: Find element just larger than pivot from right
        // This will be swapped with pivot
        for (int i = n - 1; i > pivot; i--) {
            if (arr[i] > arr[pivot]) {
                // Swap pivot with next larger element
                swap(arr, pivot, i);
                break;
            }
        }
        
        // Step 3: Reverse the suffix (after pivot)
        // This makes it the smallest possible suffix
        reverse(arr, pivot + 1, n - 1);
        
        return arr;
    }
    
    /**
     * Helper: Reverse array segment in-place
     */
    private static void reverse(int[] arr, int left, int right) {
        while (left < right) {
            swap(arr, left, right);
            left++;
            right--;
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
     * Alternative: Library approach (for comparison)
     * Uses Java's built-in next_permutation equivalent
     */
    public static int[] nextPermutationLibrary(int[] arr) {
        // Convert to list, use Collections, convert back
        java.util.List<Integer> list = new java.util.ArrayList<>();
        for (int num : arr) list.add(num);
        
        // Find next permutation using algorithm
        int i = arr.length - 2;
        while (i >= 0 && list.get(i) >= list.get(i + 1)) i--;
        
        if (i >= 0) {
            int j = arr.length - 1;
            while (list.get(j) <= list.get(i)) j--;
            java.util.Collections.swap(list, i, j);
        }
        java.util.Collections.reverse(list.subList(i + 1, arr.length));
        
        // Convert back to array
        for (int k = 0; k < arr.length; k++) arr[k] = list.get(k);
        return arr;
    }
    
    /**
     * Variation: Previous permutation (next smaller)
     * Inverse of next greater permutation
     */
    public static int[] previousPermutation(int[] arr) {
        int n = arr.length;
        
        // Find first increasing element from right
        int pivot = -1;
        for (int i = n - 2; i >= 0; i--) {
            if (arr[i] > arr[i + 1]) {
                pivot = i;
                break;
            }
        }
        
        // If no pivot, array is in ascending order
        // Previous permutation is the last (sorted descending)
        if (pivot == -1) {
            reverse(arr, 0, n - 1);
            return arr;
        }
        
        // Find element just smaller than pivot from right
        for (int i = n - 1; i > pivot; i--) {
            if (arr[i] < arr[pivot]) {
                swap(arr, pivot, i);
                break;
            }
        }
        
        // Reverse the suffix
        reverse(arr, pivot + 1, n - 1);
        return arr;
    }
    
    /**
     * Find k-th next permutation
     * Repeatedly apply next permutation k times
     */
    public static int[] kthNextPermutation(int[] arr, int k) {
        for (int i = 0; i < k; i++) {
            nextGreaterPermutation(arr);
        }
        return arr;
    }
    
    /**
     * Check if next permutation exists
     */
    public static boolean hasNextPermutation(int[] arr) {
        for (int i = arr.length - 2; i >= 0; i--) {
            if (arr[i] < arr[i + 1]) return true;
        }
        return false;
    }
    
    /**
     * Generate all permutations (for small n)
     */
    public static java.util.List<int[]> generateAllPermutations(int[] arr) {
        java.util.List<int[]> result = new java.util.ArrayList<>();
        // Sort to start from first permutation
        java.util.Arrays.sort(arr);
        
        do {
            result.add(arr.clone());  // Clone to store copy
        } while (hasNextPermutation(arr));
        
        return result;
    }
    
    /**
     * Helper to print array
     */
    public static void printArray(int[] arr) {
        System.out.println(java.util.Arrays.toString(arr));
    }
    
    /**
     * Main method with test cases
     */
    public static void main(String[] args) {
        // Test Case 1: Normal case
        int[] test1 = {1, 2, 3};
        System.out.println("Test 1 - Original: " + java.util.Arrays.toString(test1));
        nextGreaterPermutation(test1);
        System.out.println("Next permutation: " + java.util.Arrays.toString(test1));
        System.out.println("Expected: [1, 3, 2]");
        System.out.println();
        
        // Test Case 2: Last permutation (wrap around)
        int[] test2 = {3, 2, 1};
        System.out.println("Test 2 - Original: " + java.util.Arrays.toString(test2));
        nextGreaterPermutation(test2);
        System.out.println("Next permutation: " + java.util.Arrays.toString(test2));
        System.out.println("Expected: [1, 2, 3] (wrap around)");
        System.out.println();
        
        // Test Case 3: With duplicates
        int[] test3 = {1, 1, 5};
        System.out.println("Test 3 - Original: " + java.util.Arrays.toString(test3));
        nextGreaterPermutation(test3);
        System.out.println("Next permutation: " + java.util.Arrays.toString(test3));
        System.out.println("Expected: [1, 5, 1]");
        System.out.println();
        
        // Test Case 4: Complex case
        int[] test4 = {1, 3, 5, 4, 2};
        System.out.println("Test 4 - Original: " + java.util.Arrays.toString(test4));
        nextGreaterPermutation(test4);
        System.out.println("Next permutation: " + java.util.Arrays.toString(test4));
        System.out.println("Expected: [1, 4, 2, 3, 5]");
        System.out.println();
        
        // Test Case 5: Single element
        int[] test5 = {5};
        System.out.println("Test 5 - Original: " + java.util.Arrays.toString(test5));
        nextGreaterPermutation(test5);
        System.out.println("Next permutation: " + java.util.Arrays.toString(test5));
        System.out.println("Expected: [5] (unchanged)");
        System.out.println();
        
        // Test Case 6: Already largest (descending)
        int[] test6 = {5, 4, 3, 2, 1};
        System.out.println("Test 6 - Largest permutation:");
        System.out.println("Has next? " + hasNextPermutation(test6));
        nextGreaterPermutation(test6);
        System.out.println("After next: " + java.util.Arrays.toString(test6));
        System.out.println();
        
        // Test previous permutation
        int[] test7 = {1, 3, 2};
        System.out.println("Test 7 - Previous permutation:");
        System.out.println("Original: " + java.util.Arrays.toString(test7));
        previousPermutation(test7);
        System.out.println("Previous: " + java.util.Arrays.toString(test7));
        System.out.println("Expected: [1, 2, 3]");
        System.out.println();
        
        // Test k-th permutation
        int[] test8 = {1, 2, 3, 4};
        System.out.println("Test 8 - 5th next permutation of [1,2,3,4]:");
        printArray(test8);
        kthNextPermutation(test8, 5);
        printArray(test8);
        
        // Generate all permutations
        System.out.println("\nAll permutations of [1,2,3]:");
        int[] test9 = {1, 2, 3};
        java.util.List<int[]> allPerms = generateAllPermutations(test9);
        for (int[] perm : allPerms) {
            printArray(perm);
        }
    }
}

// Algorithm Analysis:
// Time Complexity: O(n) - Two passes (find pivot, find swap, reverse)
// Space Complexity: O(1) - In-place modification

// Step-by-step Algorithm:
// 1. Find first decreasing element from right (pivot)
//    - Traverse from right, find first arr[i] < arr[i+1]
//    - This is the element that can be increased
// 2. If no pivot found ? array is in descending order
//    - Reverse entire array to get first permutation
// 3. Find element just larger than pivot from right
//    - Traverse from right, find first arr[j] > arr[pivot]
//    - This is smallest element larger than pivot
// 4. Swap pivot with this element
// 5. Reverse the suffix (after pivot position)
//    - This makes suffix in ascending order (smallest possible)

// Why It Works:
// - Lexicographic order means we want smallest increase
// - Pivot is the rightmost position we can increase
// - Swapping with next larger maintains minimal increase
// - Reversing suffix makes it smallest possible after increase

// Example Walkthrough: [1, 3, 5, 4, 2]
// 1. Find pivot: from right, 3 < 5 at index 1 (arr[1]=3)
// 2. Find swap: from right, 4 > 3 at index 3 (arr[3]=4)
// 3. Swap: [1, 4, 5, 3, 2]
// 4. Reverse suffix (indices 2-4): [1, 4, 2, 3, 5]
// Result: [1, 4, 2, 3, 5] ? Next lexicographic permutation

// Edge Cases:
// 1. Single element array ? unchanged
// 2. Already largest permutation (descending) ? wrap to smallest
// 3. Duplicate elements ? algorithm handles correctly
// 4. Empty array ? returns empty array
// 5. All elements equal ? unchanged (already only permutation)

// Key Insight:
// For next permutation, we want to:
// 1. Increase as rightmost position as possible (pivot)
// 2. Increase by as small amount as possible (next larger element)
// 3. Make suffix as small as possible (reverse to ascending)

// Applications:
// 1. Generating permutations in lexicographic order
// 2. Combinatorial algorithms
// 3. Password/sequence generation
// 4. Game theory (move ordering)

// Related Problems:
// 1. LeetCode 31: Next Permutation (identical)
// 2. Previous permutation
// 3. K-th permutation
// 4. Permutation sequence

// Common Mistakes:
// 1. Not handling descending order case (pivot = -1)
// 2. Forgetting to reverse suffix after swap
// 3. Swapping with wrong element (not next larger)
// 4. Off-by-one errors in indices
// 5. Modifying array while iterating (should clone if needed)

// Performance:
// O(n) is optimal for next permutation
// Generating all permutations would be O(n! * n)

// Interview Tips:
// 1. Walk through example step-by-step
// 2. Explain why algorithm finds minimal increase
// 3. Handle edge cases explicitly
// 4. Discuss time/space complexity
// 5. Consider variations (previous, k-th)

// Remember:
// - Works with duplicates
// - Modifies array in-place
// - Wrap-around for last permutation
// - Pivot is first decreasing element from RIGHT
