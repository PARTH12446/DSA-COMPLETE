// Problem: LeetCode <ID>. <Title>
import java.util.Arrays;

// Problem: Alternate Numbers (Coding Ninjas)
// Converted from Python to Java
// Source: https://codingninjas.com/studio/problems/alternate-numbers_6783445

// Problem Description:
// Given an array of integers 'a' containing equal number of positive and negative integers.
// Rearrange the array such that the rearranged array has the positive and negative numbers
// placed alternatively. The number of positive and negative numbers are equal.
// The relative order of positive and negative numbers should be maintained.

// Constraints:
// 1. The array contains equal number of positive and negative integers
// 2. The first element of the rearranged array must be positive
// 3. Maintain the relative order of positive and negative numbers

// Example:
// Input: [3, 1, -2, -5, 2, -4]
// Output: [3, -2, 1, -5, 2, -4]
// Explanation: Positive numbers: [3, 1, 2], Negative numbers: [-2, -5, -4]
// Arranged alternately starting with positive: 3, -2, 1, -5, 2, -4

public class AlternateNumbers {

    /**
     * Rearranges the array to have positive and negative numbers placed alternately.
     * The first element is always positive, and relative order is maintained.
     * 
     * Time Complexity: O(n) - Single pass through the array
     * Space Complexity: O(n) - For the result array (cannot be done in-place due to order requirement)
     * 
     * @param a Input array with equal number of positive and negative integers
     * @return Rearranged array with positive and negative numbers placed alternately
     */
    public static int[] alternateNumbers(int[] a) {
        int n = a.length;
        int[] ans = new int[n];
        
        // Two pointers for positive and negative positions
        int posIndex = 0;    // Pointer for positive numbers (even indices: 0, 2, 4...)
        int negIndex = 1;    // Pointer for negative numbers (odd indices: 1, 3, 5...)
        
        for (int v : a) {
            if (v > 0) {
                // Place positive number at the next available positive position
                ans[posIndex] = v;
                posIndex += 2;  // Move to next even index
            } else {
                // Place negative number at the next available negative position
                ans[negIndex] = v;
                negIndex += 2;  // Move to next odd index
            }
        }
        return ans;
    }
    
    /**
     * Alternative approach using separate arrays for positives and negatives.
     * This makes the logic clearer but uses slightly more space.
     * 
     * @param a Input array with equal number of positive and negative integers
     * @return Rearranged array with positive and negative numbers placed alternately
     */
    public static int[] alternateNumbersAlternative(int[] a) {
        int n = a.length;
        int[] result = new int[n];
        
        // Separate positive and negative numbers while maintaining order
        int[] positives = new int[n/2];
        int[] negatives = new int[n/2];
        int pIdx = 0, nIdx = 0;
        
        for (int num : a) {
            if (num > 0) {
                positives[pIdx++] = num;
            } else {
                negatives[nIdx++] = num;
            }
        }
        
        // Merge them alternately
        int resultIdx = 0;
        for (int i = 0; i < n/2; i++) {
            result[resultIdx++] = positives[i];  // Positive at even indices
            result[resultIdx++] = negatives[i];  // Negative at odd indices
        }
        
        return result;
    }
    
    /**
     * In-place rearrangement (if allowed, though it's more complex).
     * This approach uses the "cycle leader" algorithm but is not straightforward.
     * Note: This implementation is for demonstration and might not handle all edge cases.
     */
    public static void alternateNumbersInPlace(int[] a) {
        int n = a.length;
        
        // First, separate positives and negatives to the correct sides
        // Positive on left, negative on right (but this breaks relative order)
        int left = 0, right = n - 1;
        while (left < right) {
            while (left < n && a[left] > 0) left++;
            while (right >= 0 && a[right] < 0) right--;
            if (left < right) {
                // Swap
                int temp = a[left];
                a[left] = a[right];
                a[right] = temp;
            }
        }
        
        // Now alternate (simplified version, doesn't maintain exact relative order)
        // This is why in-place is tricky with the "maintain relative order" constraint
        // The original two-pointer solution is preferred for this problem
    }
    
    /**
     * Main method with test cases
     */
    public static void main(String[] args) {
        // Test Case 1: Basic example
        int[] test1 = {3, 1, -2, -5, 2, -4};
        System.out.println("Test 1 Input: " + Arrays.toString(test1));
        System.out.println("Test 1 Output: " + Arrays.toString(alternateNumbers(test1)));
        System.out.println("Expected: [3, -2, 1, -5, 2, -4]");
        System.out.println();
        
        // Test Case 2: All positives then all negatives
        int[] test2 = {1, 2, 3, -4, -5, -6};
        System.out.println("Test 2 Input: " + Arrays.toString(test2));
        System.out.println("Test 2 Output: " + Arrays.toString(alternateNumbers(test2)));
        System.out.println("Expected: [1, -4, 2, -5, 3, -6]");
        System.out.println();
        
        // Test Case 3: Already alternated
        int[] test3 = {1, -1, 2, -2, 3, -3};
        System.out.println("Test 3 Input: " + Arrays.toString(test3));
        System.out.println("Test 3 Output: " + Arrays.toString(alternateNumbers(test3)));
        System.out.println("Expected: [1, -1, 2, -2, 3, -3]");
        System.out.println();
        
        // Test Case 4: Single pair
        int[] test4 = {5, -3};
        System.out.println("Test 4 Input: " + Arrays.toString(test4));
        System.out.println("Test 4 Output: " + Arrays.toString(alternateNumbers(test4)));
        System.out.println("Expected: [5, -3]");
        System.out.println();
        
        // Test Case 5: Mixed order
        int[] test5 = {-1, 2, -3, 4, -5, 6};
        System.out.println("Test 5 Input: " + Arrays.toString(test5));
        System.out.println("Test 5 Output: " + Arrays.toString(alternateNumbers(test5)));
        System.out.println("Expected: [2, -1, 4, -3, 6, -5]");
        
        // Testing alternative approach
        System.out.println("\n=== Testing Alternative Approach ===");
        System.out.println("Test 1 (Alternative): " + Arrays.toString(alternateNumbersAlternative(test1)));
    }
}

// Key Insights:
// 1. The problem guarantees equal number of positives and negatives, which simplifies the logic
// 2. The first element must be positive, so we start placing positives at index 0
// 3. We must maintain relative order, which prevents simple in-place swapping solutions
// 4. The two-pointer approach (posIndex, negIndex) is optimal with O(n) time and O(n) space

// Algorithm Steps:
// 1. Initialize result array of same size as input
// 2. Initialize two pointers: posIndex = 0 (for positives), negIndex = 1 (for negatives)
// 3. Iterate through each element in the input array:
//    a. If positive, place at ans[posIndex] and increment posIndex by 2
//    b. If negative, place at ans[negIndex] and increment negIndex by 2
// 4. Return the result array

// Why can't we do it in-place easily?
// 1. The requirement to maintain relative order means we cannot simply swap elements
// 2. If we try to swap in-place, we might overwrite elements that haven't been processed yet
// 3. The "cycle leader" algorithm exists but is complex and not intuitive

// Edge Cases to Consider:
// 1. Array with only 2 elements (one positive, one negative)
// 2. Already alternated array
// 3. All positives first, then all negatives
// 4. All negatives first, then all positives (but output should start with positive)

// Common Follow-up Questions:
// 1. What if the array doesn't have equal number of positives and negatives?
//    ? We would need to handle the extra elements at the end
// 2. Can you do it with O(1) extra space (excluding output array)?
//    ? The current solution already uses O(1) extra space (pointers only)
// 3. What if we want to start with negative instead of positive?
//    ? Simply swap the initial positions: posIndex = 1, negIndex = 0

// Time Complexity Analysis:
// - Single pass through array: O(n)
// - Constant time operations per element: O(1)
// - Total: O(n)

// Space Complexity Analysis:
// - Output array: O(n)
// - Pointers: O(1)
// - Total: O(n) (cannot be reduced due to output requirement)
