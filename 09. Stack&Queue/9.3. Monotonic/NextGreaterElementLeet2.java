import java.util.Stack;

public class NextGreaterElementLeet2 {
    
    /**
     * Finds the next greater element for each element in a circular array.
     * For each element in nums, finds the first element to its right (circularly)
     * that is strictly greater than it. If no such element exists, returns -1.
     *
     * Time Complexity: O(n) - Each element is pushed and popped from stack at most once
     * Space Complexity: O(n) - For the stack and result array
     *
     * @param nums Input array of integers
     * @return Array where res[i] contains the next greater element for nums[i]
     */
    public int[] nextGreaterElements(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> st = new Stack<>();
        
        // Traverse from right to left twice the length (for circular array)
        // Using 2n-1 to 0 ensures we properly handle circular wrap-around
        for (int i = 2 * n - 1; i >= 0; i--) {
            int idx = i % n;  // Map to actual array index (0 to n-1)
            
            // Maintain monotonic decreasing stack (top has smallest value)
            // Remove elements from stack while current element is >= stack top
            // This ensures stack always contains potential next greater elements
            while (!st.isEmpty() && nums[st.peek()] <= nums[idx]) {
                st.pop();
            }
            
            // After removing all smaller/equal elements from stack:
            // - If stack is empty: no greater element found (circularly)
            // - Otherwise: stack top is the next greater element
            res[idx] = st.isEmpty() ? -1 : nums[st.peek()];
            
            // Push current index to stack for future comparisons
            st.push(idx);
        }
        return res;
    }
    
    /**
     * Alternative implementation with explicit circular logic.
     * Some find this version more readable for understanding the circular nature.
     */
    public int[] nextGreaterElementsAlternative(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        Stack<Integer> st = new Stack<>();
        
        // First pass: handle normal next greater elements
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && nums[st.peek()] <= nums[i]) {
                st.pop();
            }
            st.push(i);
        }
        
        // Second pass: handle circular wrap-around
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && nums[st.peek()] <= nums[i]) {
                st.pop();
            }
            res[i] = st.isEmpty() ? -1 : nums[st.peek()];
            st.push(i);
        }
        
        return res;
    }
    
    public static void main(String[] args) {
        NextGreaterElementLeet2 solver = new NextGreaterElementLeet2();
        
        // Test cases
        int[] nums1 = {1, 2, 1};
        int[] result1 = solver.nextGreaterElements(nums1);
        // Expected: [2, -1, 2]
        // Explanation: 
        // - For 1 at index 0: next greater is 2 (index 1)
        // - For 2 at index 1: no greater element, circular wrap gives 1 which is smaller
        // - For 1 at index 2: circular wrap to index 0 gives 2
        
        int[] nums2 = {5, 4, 3, 2, 1};
        int[] result2 = solver.nextGreaterElements(nums2);
        // Expected: [-1, 5, 5, 5, 5]
        // Explanation: Circular array, so last element 1 wraps to find 5
        
        int[] nums3 = {1, 2, 3, 4, 5};
        int[] result3 = solver.nextGreaterElements(nums3);
        // Expected: [2, 3, 4, 5, -1]
    }
}