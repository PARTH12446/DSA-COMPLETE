// Problem: LeetCode <ID>. <Title>
// Problem: Left Rotate Array by One
// Goal: Move all elements left by one, first element goes to end

public class LeftRotate {

    /**
     * OPTIMAL SOLUTION: In-place rotation
     * Time Complexity: O(n) - Single pass through array
     * Space Complexity: O(1) - Only one temporary variable
     * 
     * Steps:
     * 1. Store first element in temp
     * 2. Shift all elements one position left
     * 3. Place temp at last position
     */
    public static int[] rotateArray(int[] arr, int n) {
        if (n <= 1) return arr; // Edge case: empty or single element
        
        // Step 1: Save first element
        int first = arr[0];
        
        // Step 2: Shift all elements left by one
        for (int i = 1; i < n; i++) {
            arr[i - 1] = arr[i];
        }
        
        // Step 3: Put first element at end
        arr[n - 1] = first;
        
        return arr;
    }

    /**
     * Example Walkthrough:
     * Input: [1, 2, 3, 4, 5]
     * 
     * Step 1: first = 1
     * Step 2: Shift left:
     *   i=1: arr[0] = arr[1] → [2,2,3,4,5]
     *   i=2: arr[1] = arr[2] → [2,3,3,4,5]
     *   i=3: arr[2] = arr[3] → [2,3,4,4,5]
     *   i=4: arr[3] = arr[4] → [2,3,4,5,5]
     * Step 3: arr[4] = first → [2,3,4,5,1]
     */

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        rotateArray(arr, arr.length);
        
        System.out.print("After left rotate by 1: ");
        for (int v : arr) System.out.print(v + " ");
        // Output: 2 3 4 5 1
        
        // Test edge cases
        int[] single = {7};
        rotateArray(single, 1);
        System.out.print("\nSingle element: ");
        for (int v : single) System.out.print(v + " "); // 7
        
        int[] empty = {};
        rotateArray(empty, 0);
        System.out.print("\nEmpty array: ");
        for (int v : empty) System.out.print(v + " "); // (nothing)
        
        int[] two = {1, 2};
        rotateArray(two, 2);
        System.out.print("\nTwo elements: ");
        for (int v : two) System.out.print(v + " "); // 2 1
    }
}
