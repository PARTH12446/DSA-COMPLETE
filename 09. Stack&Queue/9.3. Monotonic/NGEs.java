import java.util.Stack;

public class NGEs {

    /**
     * Finds the next greater element for each element in the array.
     * For each element nums[i], finds the first element to its right
     * that is strictly greater than nums[i]. Returns -1 if no such element exists.
     * 
     * Example:
     * Input: [4, 5, 2, 25]
     * Output: [5, 25, 25, -1]
     * Explanation:
     * - Next greater of 4 is 5
     * - Next greater of 5 is 25  
     * - Next greater of 2 is 25
     * - Next greater of 25 is -1 (none)
     *
     * Time Complexity: O(n) - Each element pushed/popped at most once
     * Space Complexity: O(n) - Stack and result array
     *
     * @param nums Input array of integers
     * @return Array where res[i] contains the next greater element for nums[i]
     */
    public static int[] nextGreater(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> st = new Stack<>();
        
        // Traverse from right to left
        // This direction ensures we're always looking for "next" elements
        for (int i = n - 1; i >= 0; i--) {
            // Maintain monotonic decreasing stack (from bottom to top)
            // Remove elements that are <= current element (we need strictly greater)
            while (!st.isEmpty() && st.peek() <= nums[i]) {
                st.pop();
            }
            
            // After popping all elements <= current:
            // - Stack empty means no greater element to the right
            // - Stack top has the next greater element
            res[i] = st.isEmpty() ? -1 : st.peek();
            
            // Push current element to stack for future comparisons
            st.push(nums[i]);
        }
        return res;
    }
    
    /**
     * Alternative implementation storing indices instead of values.
     * Storing indices is often more useful for problems that need
     * positional information or distance calculations.
     */
    public static int[] nextGreaterWithIndices(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> st = new Stack<>(); // Stores indices, not values
        
        for (int i = n - 1; i >= 0; i--) {
            // Compare values using nums[st.peek()] instead of st.peek()
            while (!st.isEmpty() && nums[st.peek()] <= nums[i]) {
                st.pop();
            }
            res[i] = st.isEmpty() ? -1 : nums[st.peek()];
            st.push(i); // Push index instead of value
        }
        return res;
    }
    
    /**
     * Circular version: finds next greater element in circular array.
     * After reaching the end, wraps around to the beginning.
     * This solves LeetCode 503: Next Greater Element II
     */
    public static int[] nextGreaterCircular(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> st = new Stack<>();
        
        // Traverse 2n times to handle circular nature
        for (int i = 2 * n - 1; i >= 0; i--) {
            int idx = i % n; // Wrap around using modulo
            
            // Maintain monotonic decreasing stack
            while (!st.isEmpty() && st.peek() <= nums[idx]) {
                st.pop();
            }
            
            // Only update result for first n iterations (avoid overwriting)
            if (i < n) {
                res[idx] = st.isEmpty() ? -1 : st.peek();
            }
            
            st.push(nums[idx]);
        }
        return res;
    }
    
    /**
     * Finds previous greater element (element to the left that is greater).
     * Traverses left to right instead of right to left.
     */
    public static int[] previousGreater(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> st = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && st.peek() <= nums[i]) {
                st.pop();
            }
            res[i] = st.isEmpty() ? -1 : st.peek();
            st.push(nums[i]);
        }
        return res;
    }
    
    /**
     * Helper method to print array results
     */
    private static void printResult(String testName, int[] input, int[] output) {
        System.out.println("\n" + testName + ":");
        System.out.print("Input:  [");
        for (int i = 0; i < input.length; i++) {
            System.out.print(input[i] + (i < input.length - 1 ? ", " : ""));
        }
        System.out.println("]");
        
        System.out.print("Output: [");
        for (int i = 0; i < output.length; i++) {
            System.out.print(output[i] + (i < output.length - 1 ? ", " : ""));
        }
        System.out.println("]");
    }
    
    public static void main(String[] args) {
        // Test Case 1: Normal case
        int[] test1 = {4, 5, 2, 25};
        int[] result1 = nextGreater(test1);
        printResult("Test 1 - Normal case", test1, result1);
        // Expected: [5, 25, 25, -1]
        
        // Test Case 2: Increasing sequence
        int[] test2 = {1, 2, 3, 4, 5};
        int[] result2 = nextGreater(test2);
        printResult("Test 2 - Increasing sequence", test2, result2);
        // Expected: [2, 3, 4, 5, -1]
        
        // Test Case 3: Decreasing sequence
        int[] test3 = {5, 4, 3, 2, 1};
        int[] result3 = nextGreater(test3);
        printResult("Test 3 - Decreasing sequence", test3, result3);
        // Expected: [-1, -1, -1, -1, -1]
        
        // Test Case 4: Mixed with duplicates
        int[] test4 = {1, 3, 2, 1, 3, 4, 1};
        int[] result4 = nextGreater(test4);
        printResult("Test 4 - Mixed with duplicates", test4, result4);
        // Expected: [3, 4, 3, 3, 4, -1, -1]
        
        // Test Case 5: Single element
        int[] test5 = {7};
        int[] result5 = nextGreater(test5);
        printResult("Test 5 - Single element", test5, result5);
        // Expected: [-1]
        
        // Test Case 6: Empty array
        int[] test6 = {};
        int[] result6 = nextGreater(test6);
        printResult("Test 6 - Empty array", test6, result6);
        // Expected: []
        
        // Test circular version
        int[] test7 = {1, 2, 3, 4, 3};
        int[] result7 = nextGreaterCircular(test7);
        printResult("Test 7 - Circular array", test7, result7);
        // Expected: [2, 3, 4, -1, 4]
        // Explanation: 
        // - For 1: next greater is 2
        // - For 2: next greater is 3
        // - For 3: next greater is 4
        // - For 4: no greater even with wrap-around
        // - For 3: wraps to beginning, finds 4 as greater
    }
}