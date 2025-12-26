// Problem: LeetCode <ID>. <Title>
// Problem: Find Single Element (appears once, all others appear twice)
// Optimal Approach: XOR Bitwise Operation

public class FrequencyOne {

    /**
     * OPTIMAL SOLUTION using XOR
     * Time Complexity: O(n) - Single pass through array
     * Space Complexity: O(1) - Only one variable used
     * 
     * Steps:
     * 1. Initialize result = 0
     * 2. For each num in array:
     *    - XOR it with result
     * 3. Return result
     */
    public static int getSingleElement(int[] arr) {
        int result = 0;
        for (int num : arr) {
            result ^= num;  // XOR operation
        }
        return result;
    }
    
    /**
     * How XOR works:
     * a XOR a = 0
     * 0 XOR a = a
     * XOR is commutative: a XOR b = b XOR a
     * 
     * Example: [2, 2, 3, 4, 4]
     * Step-by-step:
     * result = 0
     * result = 0 ^ 2 = 2
     * result = 2 ^ 2 = 0
     * result = 0 ^ 3 = 3
     * result = 3 ^ 4 = 7
     * result = 7 ^ 4 = 3 ← ANSWER
     */

    public static void main(String[] args) {
        int[] arr = {1,2,2,3,4,5,5,6,7};
        int ans = getSingleElement(arr);
        System.out.println("Single element = " + ans); // Output: 3
    }
}
