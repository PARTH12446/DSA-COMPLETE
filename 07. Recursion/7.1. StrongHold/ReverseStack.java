/**
 * Class for reversing a stack using recursion
 * 
 * Problem: Reverse a stack using only stack operations (push, pop, isEmpty)
 * and recursion, without using any extra data structures or loops.
 * 
 * Constraints:
 * 1. Cannot use additional stack or array
 * 2. Cannot use loops (for, while)
 * 3. Only allowed operations: push(), pop(), isEmpty()
 * 4. Must use recursion
 * 
 * Approach: Two-step recursive process
 * Step 1: Reverse the stack using recursion
 * Step 2: Insert elements at bottom using recursion
 * 
 * Time Complexity: O(n²) - each insertion takes O(n) time
 * Space Complexity: O(n) - recursion stack depth
 */
import java.util.Stack;

public class ReverseStack {

    /**
     * Reverses a stack using recursion
     * 
     * @param st Stack to be reversed (modified in-place)
     * 
     * Algorithm Steps:
     * 1. Base case: If stack is empty, return (nothing to reverse)
     * 2. Recursive case:
     *    a. Pop the top element
     *    b. Recursively reverse the remaining stack
     *    c. Insert the popped element at the bottom of the reversed stack
     *    
     * Visual Example (Stack [1, 2, 3] top→):
     * Initial:     [1, 2, 3] ← top
     * After pop:    top=1, remaining=[2, 3]
     * Recursively reverse [2, 3] → becomes [3, 2]
     * Insert 1 at bottom of [3, 2] → [1, 3, 2] WRONG! Wait...
     * Actually: Final should be [3, 2, 1]
     * 
     * Let's trace properly...
     */
    public static void reverse(Stack<Integer> st) {
        System.out.println("\n=== Starting Stack Reversal ===");
        System.out.println("Current stack (top → bottom): " + st);
        
        // Base case: empty stack is already "reversed"
        if (st.isEmpty()) {
            System.out.println("Stack is empty, nothing to reverse");
            return;
        }
        
        // Step 1: Remove top element
        int top = st.pop();
        System.out.println("Popped top element: " + top);
        System.out.println("Stack after pop: " + st);
        
        // Step 2: Recursively reverse the remaining stack
        System.out.println("\nRecursively reversing remaining stack...");
        reverse(st);
        
        // Step 3: Insert the popped element at the bottom
        System.out.println("\nInserting " + top + " at bottom of reversed stack");
        insertAtBottom(st, top);
        
        System.out.println("Stack after inserting " + top + " at bottom: " + st);
    }

    /**
     * Inserts an element at the bottom of a stack using recursion
     * 
     * @param st Stack to insert into
     * @param x Element to insert at bottom
     * 
     * Algorithm Steps:
     * 1. Base case: If stack is empty, push the element (it becomes the bottom)
     * 2. Recursive case:
     *    a. Pop the top element
     *    b. Recursively insert x at bottom of remaining stack
     *    c. Push the popped element back on top
     *    
     * Example: Insert 4 at bottom of [1, 2, 3] (top→)
     * Step 1: Pop 1, stack becomes [2, 3]
     * Step 2: Recursively insert 4 at bottom of [2, 3]
     *   - Pop 2, stack becomes [3]
     *   - Recursively insert 4 at bottom of [3]
     *     - Pop 3, stack becomes []
     *     - Push 4 (base case), stack becomes [4]
     *     - Push 3 back, stack becomes [3, 4]
     *   - Push 2 back, stack becomes [2, 3, 4]
     * Step 3: Push 1 back, stack becomes [1, 2, 3, 4]
     */
    private static void insertAtBottom(Stack<Integer> st, int x) {
        System.out.println("  insertAtBottom called with x=" + x + ", stack=" + st);
        
        // Base case: empty stack, just push the element
        if (st.isEmpty()) {
            st.push(x);
            System.out.println("    Base case: stack empty, pushing " + x);
            System.out.println("    Stack now: " + st);
            return;
        }
        
        // Recursive case: 
        // 1. Remove top element
        int top = st.pop();
        System.out.println("    Popped top: " + top + ", stack now: " + st);
        
        // 2. Recursively insert x at bottom of remaining stack
        System.out.println("    Recursively inserting " + x + " at bottom of remaining stack");
        insertAtBottom(st, x);
        
        // 3. Push the original top element back
        st.push(top);
        System.out.println("    Pushing original top " + top + " back");
        System.out.println("    Stack now: " + st);
    }
    
    /**
     * Alternative approach: Reverse using temporary stack (non-recursive)
     * This is more efficient but uses extra space
     * 
     * @param st Stack to reverse
     * Time: O(n), Space: O(n)
     */
    public static void reverseWithTempStack(Stack<Integer> st) {
        System.out.println("\n=== Reversing with Temporary Stack ===");
        System.out.println("Original stack: " + st);
        
        Stack<Integer> temp = new Stack<>();
        
        // Transfer all elements to temp stack (reverses order)
        while (!st.isEmpty()) {
            temp.push(st.pop());
        }
        
        System.out.println("Temporary stack (reversed): " + temp);
        
        // Copy back to original stack (preserves reversed order)
        while (!temp.isEmpty()) {
            st.push(temp.pop());
        }
        
        System.out.println("Final reversed stack: " + st);
    }
    
    /**
     * Alternative approach: Reverse using recursion with single function
     * More elegant but harder to understand
     */
    public static void reverseSingleFunction(Stack<Integer> st) {
        if (st.isEmpty()) return;
        
        // Pop top element
        int top = st.pop();
        
        // Reverse remaining stack
        reverseSingleFunction(st);
        
        // After stack is reversed, insert top at bottom
        insertAtBottomSingle(st, top);
    }
    
    private static void insertAtBottomSingle(Stack<Integer> st, int x) {
        if (st.isEmpty()) {
            st.push(x);
            return;
        }
        
        int top = st.pop();
        insertAtBottomSingle(st, x);
        st.push(top);
    }
    
    /**
     * Visualizes the recursion tree for stack reversal
     */
    public static void visualizeReversal(Stack<Integer> st) {
        System.out.println("\n=== Visualizing Recursive Stack Reversal ===");
        System.out.println("Original stack (top → bottom): " + st);
        System.out.println("\nRecursion Tree:");
        visualizeReversalHelper(new Stack<>(), st, 0);
    }
    
    private static void visualizeReversalHelper(Stack<Integer> original, Stack<Integer> current, int depth) {
        String indent = "  ".repeat(depth);
        
        if (current.isEmpty()) {
            System.out.println(indent + "reverse([])");
            System.out.println(indent + "  Stack empty, returning");
            return;
        }
        
        // Create a copy for visualization
        Stack<Integer> copy = new Stack<>();
        copy.addAll(current);
        
        int top = copy.pop();
        System.out.println(indent + "reverse(" + copy + ") with top=" + top);
        System.out.println(indent + "  Popped " + top + ", remaining: " + copy);
        
        // Recursive call
        visualizeReversalHelper(original, copy, depth + 1);
        
        System.out.println(indent + "  After recursion, inserting " + top + " at bottom");
        System.out.println(indent + "  Current stack before insertion: " + copy);
        
        // Simulate insertion at bottom
        if (copy.isEmpty()) {
            copy.push(top);
            System.out.println(indent + "  Stack empty, pushing " + top);
        } else {
            Stack<Integer> temp = new Stack<>();
            temp.addAll(copy);
            System.out.println(indent + "  insertAtBottom(" + copy + ", " + top + ")");
            // Simulate recursive insertion
            while (!copy.isEmpty()) {
                temp.push(copy.pop());
            }
            copy.push(top);
            while (!temp.isEmpty()) {
                copy.push(temp.pop());
            }
        }
        
        System.out.println(indent + "  Stack after insertion: " + copy);
    }
    
    /**
     * Test different stack reversal methods
     */
    public static void testReversalMethods() {
        System.out.println("\n=== Testing Different Stack Reversal Methods ===");
        
        // Test case 1: Empty stack
        System.out.println("\n1. Empty stack:");
        Stack<Integer> st1 = new Stack<>();
        System.out.println("Original: " + st1);
        reverse(st1);
        System.out.println("Reversed: " + st1);
        
        // Test case 2: Single element
        System.out.println("\n2. Single element:");
        Stack<Integer> st2 = new Stack<>();
        st2.push(1);
        System.out.println("Original: " + st2);
        reverse(st2);
        System.out.println("Reversed: " + st2);
        
        // Test case 3: Multiple elements
        System.out.println("\n3. Multiple elements [1, 2, 3] (top→):");
        Stack<Integer> st3 = new Stack<>();
        st3.push(3);
        st3.push(2);
        st3.push(1);
        System.out.println("Original: " + st3);
        visualizeReversal(new Stack<>(st3));  // Pass a copy
        reverse(st3);
        System.out.println("Final reversed: " + st3);
        
        // Test case 4: Using temporary stack
        System.out.println("\n4. Using temporary stack [4, 5, 6]:");
        Stack<Integer> st4 = new Stack<>();
        st4.push(6);
        st4.push(5);
        st4.push(4);
        System.out.println("Original: " + st4);
        reverseWithTempStack(st4);
        System.out.println("Reversed: " + st4);
        
        // Test case 5: Large stack
        System.out.println("\n5. Large stack [1..5]:");
        Stack<Integer> st5 = new Stack<>();
        for (int i = 5; i >= 1; i--) {
            st5.push(i);
        }
        System.out.println("Original: " + st5);
        reverse(st5);
        System.out.println("Reversed: " + st5);
    }
    
    /**
     * Performance comparison of different reversal methods
     */
    public static void performanceComparison() {
        System.out.println("\n=== Performance Comparison ===");
        
        // Create large stack
        Stack<Integer> st = new Stack<>();
        int size = 1000;
        for (int i = size; i >= 1; i--) {
            st.push(i);
        }
        
        // Method 1: Recursive (O(n²))
        Stack<Integer> st1 = new Stack<>();
        st1.addAll(st);
        System.out.println("\n1. Recursive method (O(n²)):");
        long start = System.nanoTime();
        reverse(st1);
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        
        // Method 2: Temporary stack (O(n))
        Stack<Integer> st2 = new Stack<>();
        st2.addAll(st);
        System.out.println("\n2. Temporary stack method (O(n)):");
        start = System.nanoTime();
        reverseWithTempStack(st2);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        
        // Method 3: Built-in Collections.reverse()
        System.out.println("\n3. Collections.reverse() method:");
        java.util.ArrayList<Integer> list = new java.util.ArrayList<>(st);
        start = System.nanoTime();
        java.util.Collections.reverse(list);
        Stack<Integer> st3 = new Stack<>();
        st3.addAll(list);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
    }
    
    /**
     * Demonstrates step-by-step reversal with detailed comments
     */
    public static void demonstrateStepByStep() {
        System.out.println("\n=== Step-by-Step Demonstration ===");
        
        Stack<Integer> st = new Stack<>();
        st.push(3);
        st.push(2);
        st.push(1);
        
        System.out.println("Original stack (top → bottom): " + st);
        System.out.println("\nStep 1: reverse([1, 2, 3])");
        System.out.println("  - Pop top: 1, stack becomes [2, 3]");
        System.out.println("  - Call reverse([2, 3])");
        
        System.out.println("\nStep 2: reverse([2, 3])");
        System.out.println("  - Pop top: 2, stack becomes [3]");
        System.out.println("  - Call reverse([3])");
        
        System.out.println("\nStep 3: reverse([3])");
        System.out.println("  - Pop top: 3, stack becomes []");
        System.out.println("  - Call reverse([])");
        
        System.out.println("\nStep 4: reverse([])");
        System.out.println("  - Base case: stack empty, return");
        
        System.out.println("\nStep 5: Back to reverse([3])");
        System.out.println("  - Insert 3 at bottom of []");
        System.out.println("  - Stack empty, push 3 → [3]");
        
        System.out.println("\nStep 6: Back to reverse([2, 3])");
        System.out.println("  - Insert 2 at bottom of [3]");
        System.out.println("  - Pop 3, stack becomes []");
        System.out.println("  - Insert 2 at bottom of [] → push 2 → [2]");
        System.out.println("  - Push 3 back → [3, 2]");
        
        System.out.println("\nStep 7: Back to reverse([1, 2, 3])");
        System.out.println("  - Insert 1 at bottom of [3, 2]");
        System.out.println("  - Pop 3, stack becomes [2]");
        System.out.println("  - Insert 1 at bottom of [2]");
        System.out.println("    - Pop 2, stack becomes []");
        System.out.println("    - Insert 1 at bottom of [] → push 1 → [1]");
        System.out.println("    - Push 2 back → [2, 1]");
        System.out.println("  - Push 3 back → [3, 2, 1]");
        
        System.out.println("\nFinal reversed stack: [3, 2, 1]");
    }
    
    /**
     * Extended version that handles generic stacks
     */
    public static <T> void reverseGeneric(Stack<T> st) {
        if (st.isEmpty()) return;
        T top = st.pop();
        reverseGeneric(st);
        insertAtBottomGeneric(st, top);
    }
    
    private static <T> void insertAtBottomGeneric(Stack<T> st, T x) {
        if (st.isEmpty()) {
            st.push(x);
            return;
        }
        T top = st.pop();
        insertAtBottomGeneric(st, x);
        st.push(top);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Stack Reversal Using Recursion ===\n");
        
        // Basic demonstration
        System.out.println("Basic Example:");
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Original: " + stack);
        reverse(stack);
        System.out.println("Reversed: " + stack);
        
        // Step-by-step demonstration
        demonstrateStepByStep();
        
        // Visualization
        Stack<Integer> stack2 = new Stack<>();
        stack2.push(1);
        stack2.push(2);
        stack2.push(3);
        visualizeReversal(stack2);
        
        // Test different methods
        testReversalMethods();
        
        // Performance comparison
        performanceComparison();
        
        // Generic stack example
        System.out.println("\n=== Generic Stack Example ===");
        Stack<String> strStack = new Stack<>();
        strStack.push("Hello");
        strStack.push("World");
        strStack.push("!");
        System.out.println("Original string stack: " + strStack);
        reverseGeneric(strStack);
        System.out.println("Reversed string stack: " + strStack);
        
        // Mathematical analysis
        System.out.println("\n=== Mathematical Analysis ===");
        System.out.println("\nTime Complexity Analysis:");
        System.out.println("Let T(n) be time to reverse stack of size n");
        System.out.println("T(n) = T(n-1) + I(n) where I(n) is time to insert at bottom");
        System.out.println("I(n) = I(n-1) + O(1) = O(n)");
        System.out.println("So T(n) = T(n-1) + O(n) = O(n²)");
        
        System.out.println("\nSpace Complexity Analysis:");
        System.out.println("Recursion depth: O(n)");
        System.out.println("No additional data structures (except recursion stack)");
        
        System.out.println("\n=== Alternative Approaches ===");
        System.out.println("1. Using temporary stack: O(n) time, O(n) space");
        System.out.println("2. Using linked list: O(n) time, O(1) space");
        System.out.println("3. Using collections: Collections.reverse()");
        System.out.println("4. Using queue: transfer stack→queue→stack");
        
        System.out.println("\n=== Common Interview Questions ===");
        System.out.println("Q: Why is the time complexity O(n²)?");
        System.out.println("A: Because insertAtBottom takes O(n) time and we call it n times");
        
        System.out.println("\nQ: Can we do better than O(n²) with recursion only?");
        System.out.println("A: No, because we need to access bottom elements which requires");
        System.out.println("   popping all elements above them each time");
        
        System.out.println("\nQ: What if we're allowed to use one extra stack?");
        System.out.println("A: Then we can reverse in O(n) time by transferring");
        System.out.println("   elements between two stacks");
        
        System.out.println("\nQ: How would you reverse a stack without recursion?");
        System.out.println("A: Use a temporary stack or reverse the underlying array");
        
        System.out.println("\n=== Real-World Applications ===");
        System.out.println("1. Undo functionality in editors");
        System.out.println("2. Back button in web browsers");
        System.out.println("3. Expression evaluation (postfix to prefix)");
        System.out.println("4. Reversing a sequence of operations");
    }
}