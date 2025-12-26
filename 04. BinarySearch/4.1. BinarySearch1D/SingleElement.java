/**
 * Problem: Find the single non-duplicate element in a sorted array
 * where every element appears exactly twice except for one element.
 * 
 * Array properties:
 * - Sorted in non-decreasing order
 * - Each element appears exactly TWICE except ONE element
 * - Total elements: 2N + 1 (odd number)
 * 
 * Example 1:
 * Input: [1,1,2,3,3,4,4,8,8]
 * Output: 2
 * 
 * Example 2:
 * Input: [3,3,7,7,10,11,11]
 * Output: 10
 * 
 * Time Complexity: O(log n) using binary search
 * Space Complexity: O(1)
 * 
 * Approach: Binary search on indices based on pair parity
 * Key Insight: In a perfectly paired array (without single element),
 * the first occurrence of each pair is at EVEN index, second at ODD index.
 * The single element disrupts this pattern.
 */

public class SingleElement {

    /**
     * Finds the single non-duplicate element in sorted array
     * 
     * @param arr - Sorted array where every element appears twice except one
     * @return The single element, or -1 if not found (should not happen for valid input)
     * 
     * Algorithm Explanation:
     * 
     * Visual representation of normal pattern:
     * Index:   0   1   2   3   4   5   6   7   8
     * Array:  [1,  1,  2,  2,  3,  3,  4,  4,  5]
     *           ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑   ↑
     *           E   O   E   O   E   O   E   O   E   (E=Even, O=Odd)
     *           ↑ first  ↑ second
     *           ↑ pair   ↑ pair
     * 
     * In normal case (without single element):
     * - Each pair starts at EVEN index
     * - Each pair ends at ODD index
     * 
     * When single element is present (example):
     * Index:   0   1   2   3   4   5   6   7   8
     * Array:  [1,  1,  2,  3,  3,  4,  4,  8,  8]
     * Pattern: E   O   E   O   E   O   E   O   E
     *                 ↑ Single element disrupts pattern here!
     * 
     * Strategy:
     * 1. Check boundaries first (first and last elements)
     * 2. Binary search on remaining elements
     * 3. At each mid, check if it's the single element
     * 4. If not, check which side contains the single element based on parity
     */
    public static int singleNonDuplicate(int[] arr) {
        int n = arr.length;
        
        // Edge cases
        if (n == 1) {
            return arr[0];
        }
        
        // Check boundaries first (optimization)
        // Single element could be at first or last position
        if (arr[0] != arr[1]) {
            return arr[0];
        }
        if (arr[n - 1] != arr[n - 2]) {
            return arr[n - 1];
        }
        
        // Binary search on the internal elements (excluding boundaries)
        // We exclude first and last because we already checked them
        int low = 1;
        int high = n - 2;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;  // Prevent overflow
            
            // Check if mid is the single element
            // A single element will be different from both neighbors
            if (arr[mid] != arr[mid - 1] && arr[mid] != arr[mid + 1]) {
                return arr[mid];
            }
            
            // ===========================================
            // CASE 1: mid is the FIRST occurrence of a pair
            // ===========================================
            // If arr[mid] == arr[mid-1], then mid is the SECOND element of pair
            // Example: at index 3 in [1,1,2,2,3], arr[3]=2 equals arr[2]=2
            else if (arr[mid] == arr[mid - 1]) {
                // Check parity to determine which side has the single element
                
                // If mid is ODD index: pattern is correct
                // Example: Index 3 (odd), arr[3]=2 equals arr[2]=2 (even)
                // This means the left half [0..mid] is correctly paired
                // Single element must be on RIGHT side
                if (mid % 2 == 1) {
                    low = mid + 1;  // Search right
                } 
                // If mid is EVEN index: pattern is broken
                // Example: Index 2 (even), arr[2]=2 equals arr[1]=2 (odd) - BROKEN!
                // This means single element is on LEFT side
                else {
                    high = mid - 1;  // Search left
                }
            }
            
            // ===========================================
            // CASE 2: mid is the SECOND occurrence of a pair
            // ===========================================
            // If arr[mid] == arr[mid+1], then mid is the FIRST element of pair
            // Example: at index 2 in [1,1,2,2,3], arr[2]=2 equals arr[3]=2
            else if (arr[mid] == arr[mid + 1]) {
                // Check parity
                
                // If mid is EVEN index: pattern is correct
                // Example: Index 2 (even), arr[2]=2 equals arr[3]=2 (odd)
                // This means the left half [0..mid+1] is correctly paired
                // Single element must be on RIGHT side
                if (mid % 2 == 0) {
                    low = mid + 1;  // Search right
                } 
                // If mid is ODD index: pattern is broken
                // Example: Index 1 (odd), arr[1]=1 equals arr[2]=2 - Wait this wouldn't happen
                // Actually if mid is odd and equals mid+1, pattern is broken
                // Single element is on LEFT side
                else {
                    high = mid - 1;  // Search left
                }
            }
        }
        
        // Should never reach here for valid input
        return -1;
    }
    
    /**
     * Alternative simpler implementation using XOR property
     * This version is more intuitive and handles all cases uniformly
     * 
     * Key Insight: In a sorted array with pairs, XOR of index with 1 gives:
     * - For first element of pair at even index i: i^1 = i+1 (next element)
     * - For second element of pair at odd index i: i^1 = i-1 (previous element)
     * 
     * Example:
     * Index 0 (even): 0^1 = 1 (partner is at index 1)
     * Index 1 (odd): 1^1 = 0 (partner is at index 0)
     * Index 2 (even): 2^1 = 3 (partner is at index 3)
     * Index 3 (odd): 3^1 = 2 (partner is at index 2)
     */
    public static int singleNonDuplicateXOR(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            // Check if mid and its partner are equal
            // XOR with 1 gives partner index
            if (arr[mid] == arr[mid ^ 1]) {
                // Pattern is normal, single element is on RIGHT side
                low = mid + 1;
            } else {
                // Pattern broken, single element is on LEFT side (including mid)
                high = mid;
            }
        }
        
        return arr[low];
    }
    
    public static void main(String[] args) {
        System.out.println("=== Testing Single Non-Duplicate Element ===\n");
        
        // Test Case 1: Single element in middle
        int[] test1 = {1, 1, 2, 3, 3, 4, 4, 8, 8};
        System.out.println("Test 1: [1,1,2,3,3,4,4,8,8]");
        System.out.println("Original method: " + singleNonDuplicate(test1) + " (expected: 2)");
        System.out.println("XOR method: " + singleNonDuplicateXOR(test1) + " (expected: 2)\n");
        
        // Test Case 2: Single element at first position
        int[] test2 = {2, 3, 3, 4, 4, 8, 8};
        System.out.println("Test 2: [2,3,3,4,4,8,8]");
        System.out.println("Original method: " + singleNonDuplicate(test2) + " (expected: 2)");
        System.out.println("XOR method: " + singleNonDuplicateXOR(test2) + " (expected: 2)\n");
        
        // Test Case 3: Single element at last position
        int[] test3 = {1, 1, 2, 2, 3, 3, 4, 4, 8};
        System.out.println("Test 3: [1,1,2,2,3,3,4,4,8]");
        System.out.println("Original method: " + singleNonDuplicate(test3) + " (expected: 8)");
        System.out.println("XOR method: " + singleNonDuplicateXOR(test3) + " (expected: 8)\n");
        
        // Test Case 4: Single element between pairs
        int[] test4 = {3, 3, 7, 7, 10, 11, 11};
        System.out.println("Test 4: [3,3,7,7,10,11,11]");
        System.out.println("Original method: " + singleNonDuplicate(test4) + " (expected: 10)");
        System.out.println("XOR method: " + singleNonDuplicateXOR(test4) + " (expected: 10)\n");
        
        // Test Case 5: Minimum size array
        int[] test5 = {5};
        System.out.println("Test 5: [5] (single element)");
        System.out.println("Original method: " + singleNonDuplicate(test5) + " (expected: 5)");
        System.out.println("XOR method: " + singleNonDuplicateXOR(test5) + " (expected: 5)\n");
        
        // Test Case 6: All pairs except one in middle
        int[] test6 = {1, 1, 2, 2, 3, 3, 4, 5, 5};
        System.out.println("Test 6: [1,1,2,2,3,3,4,5,5]");
        System.out.println("Original method: " + singleNonDuplicate(test6) + " (expected: 4)");
        System.out.println("XOR method: " + singleNonDuplicateXOR(test6) + " (expected: 4)\n");
        
        // Test Case 7: Large array
        int[] test7 = {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 8, 8};
        System.out.println("Test 7: Large array with single element 7");
        System.out.println("Original method: " + singleNonDuplicate(test7) + " (expected: 7)");
        System.out.println("XOR method: " + singleNonDuplicateXOR(test7) + " (expected: 7)\n");
    }
}