import java.util.Stack;

public class NextSmallerElement {

    /**
     * Finds the next smaller element for each element in the array.
     * For each element nums[i], finds the first element to its right
     * that is strictly smaller than nums[i]. Returns -1 if no such element exists.
     * 
     * Example:
     * Input: [4, 8, 5, 2, 25]
     * Output: [2, 5, 2, -1, -1]
     * Explanation:
     * - Next smaller of 4 is 2
     * - Next smaller of 8 is 5  
     * - Next smaller of 5 is 2
     * - Next smaller of 2 is -1 (none)
     * - Next smaller of 25 is -1 (none)
     *
     * Time Complexity: O(n) - Each element pushed/popped at most once
     * Space Complexity: O(n) - Stack storage
     *
     * @param nums Input array of integers
     * @return Array where res[i] contains the next smaller element for nums[i]
     */
    public static int[] nextSmaller(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> st = new Stack<>();
        
        // Traverse from right to left
        // This ensures we always look for the "next" element to the right
        for (int i = n - 1; i >= 0; i--) {
            // Maintain monotonic increasing stack (bottom to top)
            // Remove elements that are >= current element (we need strictly smaller)
            while (!st.isEmpty() && st.peek() >= nums[i]) {
                st.pop();
            }
            
            // After popping all elements >= current:
            // - Stack empty means no smaller element to the right
            // - Stack top has the next smaller element
            res[i] = st.isEmpty() ? -1 : st.peek();
            
            // Push current element to stack for future comparisons
            st.push(nums[i]);
        }
        return res;
    }
    
    /**
     * Alternative implementation storing indices instead of values.
     * This is often more useful when we need to perform operations
     * based on positions rather than just values.
     */
    public static int[] nextSmallerWithIndices(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> st = new Stack<>(); // Stores indices, not values
        
        for (int i = n - 1; i >= 0; i--) {
            // Compare values using nums[st.peek()] instead of st.peek()
            while (!st.isEmpty() && nums[st.peek()] >= nums[i]) {
                st.pop();
            }
            res[i] = st.isEmpty() ? -1 : nums[st.peek()];
            st.push(i); // Push index instead of value
        }
        return res;
    }
    
    /**
     * Circular version: finds next smaller element in circular array.
     * After reaching end, wraps around to beginning.
     */
    public static int[] nextSmallerCircular(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> st = new Stack<>();
        
        // Traverse 2n times to handle circular nature
        for (int i = 2 * n - 1; i >= 0; i--) {
            int idx = i % n; // Wrap around using modulo
            
            // Maintain monotonic increasing stack
            while (!st.isEmpty() && st.peek() >= nums[idx]) {
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
     * Finds previous smaller element (element to the left).
     * Traverses left to right instead of right to left.
     */
    public static int[] previousSmaller(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> st = new Stack<>();
        
        for (int i = 0; i < n; i++) {
            while (!st.isEmpty() && st.peek() >= nums[i]) {
                st.pop();
            }
            res[i] = st.isEmpty() ? -1 : st.peek();
            st.push(nums[i]);
        }
        return res;
    }
    
    public static void main(String[] args) {
        // Test cases
        int[] test1 = {4, 8, 5, 2, 25};
        int[] result1 = nextSmaller(test1);
        System.out.println("Test 1 - Normal case:");
        System.out.print("Input:  [4, 8, 5, 2, 25]\n");
        System.out.print("Output: [");
        for (int i = 0; i < result1.length; i++) {
            System.out.print(result1[i] + (i < result1.length - 1 ? ", " : ""));
        }
        System.out.println("]");
        // Expected: [2, 5, 2, -1, -1]
        
        int[] test2 = {1, 2, 3, 4, 5};
        int[] result2 = nextSmaller(test2);
        System.out.println("\nTest 2 - Increasing sequence:");
        System.out.println("Input:  [1, 2, 3, 4, 5]");
        System.out.print("Output: [");
        for (int r : result2) System.out.print(r + " ");
        System.out.println("]");
        // Expected: [-1, -1, -1, -1, -1]
        
        int[] test3 = {5, 4, 3, 2, 1};
        int[] result3 = nextSmaller(test3);
        System.out.println("\nTest 3 - Decreasing sequence:");
        System.out.println("Input:  [5, 4, 3, 2, 1]");
        System.out.print("Output: [");
        for (int r : result3) System.out.print(r + " ");
        System.out.println("]");
        // Expected: [4, 3, 2, 1, -1]
        
        int[] test4 = {2, 2, 2, 2};
        int[] result4 = nextSmaller(test4);
        System.out.println("\nTest 4 - All equal elements:");
        System.out.println("Input:  [2, 2, 2, 2]");
        System.out.print("Output: [");
        for (int r : result4) System.out.print(r + " ");
        System.out.println("]");
        // Expected: [-1, -1, -1, -1] (strictly smaller needed)
        
        // Test circular version
        int[] test5 = {3, 8, 4, 1, 2};
        int[] result5 = nextSmallerCircular(test5);
        System.out.println("\nTest 5 - Circular array:");
        System.out.println("Input:  [3, 8, 4, 1, 2]");
        System.out.print("Output: [");
        for (int r : result5) System.out.print(r + " ");
        System.out.println("]");
        // Expected: [1, 4, 1, -1, 1]
        // Explanation: Last element 2 wraps to beginning, finds 1 as smaller
    }
}