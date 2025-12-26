// Problem: LeetCode <ID>. <Title>
// Problem: Rotate Array by k positions
// Goal: Rotate array to right by k steps (if k negative, rotate left)

public class RotateArray {

    /**
     * OPTIMAL SOLUTION: Reversal Algorithm
     * Time Complexity: O(n) - Three passes through array
     * Space Complexity: O(1) - In-place rotation
     * 
     * Steps:
     * 1. Normalize k: k = k % n (handle k > n)
     * 2. Reverse first k elements
     * 3. Reverse remaining n-k elements  
     * 4. Reverse entire array
     */
    public static int[] rotateArray(int[] arr, int k) {
        int n = arr.length;
        if (n == 0 || k == 0) return arr;
        
        // Normalize k (handle negative and large k)
        k = ((k % n) + n) % n; // Handles negative k for left rotation
        
        // Step 1: Reverse first k elements
        reverse(arr, 0, k - 1);
        
        // Step 2: Reverse remaining elements
        reverse(arr, k, n - 1);
        
        // Step 3: Reverse entire array
        reverse(arr, 0, n - 1);
        
        return arr;
    }
    
    /**
     * Helper: Reverse array between indices l and r (inclusive)
     */
    private static void reverse(int[] arr, int l, int r) {
        while (l < r) {
            int temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;
            l++;
            r--;
        }
    }
    
    /**
     * Alternative: Using extra array (O(n) space)
     * Simpler but uses extra memory
     */
    public static int[] rotateArrayExtraSpace(int[] arr, int k) {
        int n = arr.length;
        if (n == 0) return arr;
        
        k = k % n;
        int[] result = new int[n];
        
        // Copy last k elements to beginning of result
        for (int i = 0; i < k; i++) {
            result[i] = arr[n - k + i];
        }
        
        // Copy first n-k elements to end of result
        for (int i = 0; i < n - k; i++) {
            result[k + i] = arr[i];
        }
        
        return result;
    }

    /**
     * Example Walkthrough (k=2, n=5):
     * Input: [1, 2, 3, 4, 5]
     * 
     * Step 1: Reverse first k=2 elements
     *   [1,2,3,4,5] → [2,1,3,4,5]
     *   
     * Step 2: Reverse remaining n-k=3 elements
     *   [2,1,3,4,5] → [2,1,5,4,3]
     *   
     * Step 3: Reverse entire array
     *   [2,1,5,4,3] → [3,4,5,1,2] ✓
     *   
     * Result: [3,4,5,1,2] (right rotation by 2)
     */

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5};
        int k = 2;
        int[] res = rotateArray(arr, k);
        
        System.out.print("Right rotate by " + k + ": ");
        for (int v : res) System.out.print(v + " "); // 3 4 5 1 2
        System.out.println();
        
        // Test cases
        int[] test1 = {1, 2, 3, 4, 5};
        System.out.print("Left rotate by 2 (k=-2): ");
        rotateArray(test1, -2);
        for (int v : test1) System.out.print(v + " "); // 3 4 5 1 2
        
        int[] test2 = {1};
        rotateArray(test2, 5);
        System.out.print("\nSingle element, k=5: ");
        for (int v : test2) System.out.print(v + " "); // 1
        
        int[] test3 = {};
        rotateArray(test3, 3);
        System.out.print("\nEmpty array: ");
        for (int v : test3) System.out.print(v + " "); // (nothing)
        
        int[] test4 = {1, 2, 3, 4, 5, 6};
        rotateArray(test4, 8); // k=8%6=2
        System.out.print("\nk=8 (k>n): ");
        for (int v : test4) System.out.print(v + " "); // 5 6 1 2 3 4
    }
}
