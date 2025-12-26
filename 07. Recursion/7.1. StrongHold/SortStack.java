/**
 * Class for sorting a stack using recursion
 * 
 * Problem: Sort a stack in ascending order (smallest at bottom, largest at top)
 * using only stack operations (push, pop, peek, isEmpty) and recursion,
 * without using any extra data structures or loops.
 * 
 * Constraints:
 * 1. Cannot use additional stack or array
 * 2. Cannot use loops (for, while)
 * 3. Only allowed operations: push(), pop(), peek(), isEmpty()
 * 4. Must use recursion
 * 
 * Approach: Two-step recursive process
 * Step 1: Recursively sort the stack (similar to stack reversal)
 * Step 2: Insert elements in sorted order using recursion
 * 
 * Time Complexity: O(n²) - each insertion may take O(n) time
 * Space Complexity: O(n) - recursion stack depth
 * 
 * This is similar to insertion sort algorithm adapted for stacks.
 */
import java.util.Stack;

public class SortStack {

    /**
     * Sorts a stack in ascending order (smallest at bottom, largest at top)
     * using recursion
     * 
     * @param st Stack to be sorted (modified in-place)
     * 
     * Algorithm Steps:
     * 1. Base case: If stack is empty, return (already sorted)
     * 2. Recursive case:
     *    a. Pop the top element
     *    b. Recursively sort the remaining stack
     *    c. Insert the popped element in sorted order into the sorted stack
     * 
     * Example: Sort [3, 1, 4, 2] (top→)
     * - Pop 3, sort [1, 4, 2]
     *   - Pop 1, sort [4, 2]
     *     - Pop 4, sort [2]
     *       - Pop 2, sort [] → return
     *       - Insert 2 into [] → [2]
     *     - Insert 4 into [2] → [2, 4] (4 > 2, goes on top)
     *   - Insert 1 into [2, 4] → [1, 2, 4] (1 < 2, goes to bottom)
     * - Insert 3 into [1, 2, 4] → [1, 2, 3, 4] (inserted in middle)
     */
    public static void sortStack(Stack<Integer> st) {
        System.out.println("\n=== Starting Stack Sorting ===");
        System.out.println("Current stack (top → bottom): " + st);
        
        // Base case: empty stack or single element is already sorted
        if (st.isEmpty()) {
            System.out.println("Stack is empty, nothing to sort");
            return;
        }
        
        if (st.size() == 1) {
            System.out.println("Single element stack is already sorted");
            return;
        }
        
        // Step 1: Remove top element
        int top = st.pop();
        System.out.println("Popped top element: " + top);
        System.out.println("Stack after pop: " + st);
        
        // Step 2: Recursively sort the remaining stack
        System.out.println("\nRecursively sorting remaining stack...");
        sortStack(st);
        
        // Step 3: Insert the popped element in sorted order
        System.out.println("\nInserting " + top + " in sorted order into: " + st);
        insertSorted(st, top);
        
        System.out.println("Stack after inserting " + top + ": " + st);
    }

    /**
     * Inserts an element into a sorted stack while maintaining sorted order
     * 
     * @param st Sorted stack (ascending: smallest at bottom, largest at top)
     * @param x Element to insert in sorted order
     * 
     * Algorithm Steps:
     * 1. Base case: If stack is empty OR top element ≤ x, push x
     * 2. Recursive case:
     *    a. Pop the top element (which is > x since stack is sorted ascending)
     *    b. Recursively insert x into the remaining sorted stack
     *    c. Push the popped element back on top
     * 
     * Example: Insert 3 into sorted stack [1, 2, 4] (top→)
     * - Top is 4, 4 > 3, so pop 4, stack becomes [1, 2]
     * - Recursively insert 3 into [1, 2]
     *   - Top is 2, 2 < 3, so push 3, stack becomes [1, 2, 3]
     *   - Push 4 back, stack becomes [1, 2, 3, 4]
     */
    private static void insertSorted(Stack<Integer> st, int x) {
        System.out.println("  insertSorted called with x=" + x + ", stack=" + st);
        
        // Base case 1: Stack is empty, just push the element
        // Base case 2: Top element is ≤ x (x belongs on top or in correct position)
        if (st.isEmpty() || st.peek() <= x) {
            st.push(x);
            System.out.println("    Base case: " + 
                (st.isEmpty() ? "stack empty" : "top=" + st.peek() + " ≤ " + x) + 
                ", pushing " + x);
            System.out.println("    Stack now: " + st);
            return;
        }
        
        // Recursive case: Top element > x, need to insert deeper
        int top = st.pop();
        System.out.println("    Top " + top + " > " + x + ", popping " + top);
        System.out.println("    Stack after pop: " + st);
        
        // Recursively insert x into remaining sorted stack
        System.out.println("    Recursively inserting " + x + " into remaining stack");
        insertSorted(st, x);
        
        // Push the original top element back
        st.push(top);
        System.out.println("    Pushing original top " + top + " back");
        System.out.println("    Stack now: " + st);
    }
    
    /**
     * Alternative approach: Sort in descending order (largest at bottom)
     * 
     * @param st Stack to sort in descending order
     */
    public static void sortStackDescending(Stack<Integer> st) {
        if (st.isEmpty()) return;
        int top = st.pop();
        sortStackDescending(st);
        insertSortedDescending(st, top);
    }
    
    private static void insertSortedDescending(Stack<Integer> st, int x) {
        if (st.isEmpty() || st.peek() >= x) {
            st.push(x);
            return;
        }
        int top = st.pop();
        insertSortedDescending(st, x);
        st.push(top);
    }
    
    /**
     * Iterative sorting using temporary stack (like insertion sort for stacks)
     * More efficient: O(n²) time, O(n) space, but uses loops
     */
    public static void sortStackIterative(Stack<Integer> st) {
        System.out.println("\n=== Sorting Stack Iteratively ===");
        System.out.println("Original stack: " + st);
        
        Stack<Integer> temp = new Stack<>();
        
        while (!st.isEmpty()) {
            // Pop an element from original stack
            int current = st.pop();
            System.out.println("\nProcessing element: " + current);
            
            // While temporary stack is not empty and top of temp stack is > current
            while (!temp.isEmpty() && temp.peek() > current) {
                // Move from temp back to original
                st.push(temp.pop());
                System.out.println("  Moved " + st.peek() + " back to original stack");
            }
            
            // Push current to temp stack
            temp.push(current);
            System.out.println("  Pushed " + current + " to temp stack");
            System.out.println("  Temp stack: " + temp);
            System.out.println("  Original stack: " + st);
        }
        
        // Transfer all elements from temp back to original (now sorted)
        while (!temp.isEmpty()) {
            st.push(temp.pop());
        }
        
        System.out.println("\nFinal sorted stack: " + st);
    }
    
    /**
     * Visualizes the recursion tree for stack sorting
     */
    public static void visualizeSorting(Stack<Integer> st) {
        System.out.println("\n=== Visualizing Recursive Stack Sorting ===");
        System.out.println("Original stack (top → bottom): " + st);
        System.out.println("\nRecursion Tree:");
        visualizeSortingHelper(st, 0);
    }
    
    private static void visualizeSortingHelper(Stack<Integer> st, int depth) {
        String indent = "  ".repeat(depth);
        
        if (st.isEmpty()) {
            System.out.println(indent + "sortStack([])");
            System.out.println(indent + "  Stack empty, returning");
            return;
        }
        
        // Create a copy for visualization
        Stack<Integer> copy = new Stack<>();
        copy.addAll(st);
        
        int top = copy.pop();
        System.out.println(indent + "sortStack(" + copy + ") with top=" + top);
        System.out.println(indent + "  Popped " + top + ", remaining: " + copy);
        
        // Recursive call
        visualizeSortingHelper(copy, depth + 1);
        
        System.out.println(indent + "  After recursion, inserting " + top + " in sorted order");
        System.out.println(indent + "  Current stack before insertion: " + copy);
        
        // Simulate sorted insertion
        visualizeInsertSorted(copy, top, depth + 1);
        
        System.out.println(indent + "  Stack after insertion: " + copy);
    }
    
    private static void visualizeInsertSorted(Stack<Integer> st, int x, int depth) {
        String indent = "  ".repeat(depth);
        
        if (st.isEmpty() || st.peek() <= x) {
            st.push(x);
            System.out.println(indent + "insertSorted: " + 
                (st.size() == 1 ? "stack empty" : "top=" + (st.size() > 1 ? st.get(st.size()-2) : "?") + " ≤ " + x) + 
                ", pushing " + x);
            return;
        }
        
        int top = st.pop();
        System.out.println(indent + "insertSorted: top " + top + " > " + x + ", popping " + top);
        
        visualizeInsertSorted(st, x, depth + 1);
        
        st.push(top);
        System.out.println(indent + "insertSorted: pushing " + top + " back");
    }
    
    /**
     * Test different sorting scenarios
     */
    public static void testSortingScenarios() {
        System.out.println("\n=== Testing Different Sorting Scenarios ===");
        
        // Test case 1: Empty stack
        System.out.println("\n1. Empty stack:");
        Stack<Integer> st1 = new Stack<>();
        System.out.println("Original: " + st1);
        sortStack(st1);
        System.out.println("Sorted: " + st1);
        
        // Test case 2: Single element
        System.out.println("\n2. Single element:");
        Stack<Integer> st2 = new Stack<>();
        st2.push(5);
        System.out.println("Original: " + st2);
        sortStack(st2);
        System.out.println("Sorted: " + st2);
        
        // Test case 3: Already sorted ascending
        System.out.println("\n3. Already sorted ascending [1, 2, 3, 4] (top→):");
        Stack<Integer> st3 = new Stack<>();
        st3.push(4);
        st3.push(3);
        st3.push(2);
        st3.push(1);
        System.out.println("Original: " + st3);
        sortStack(st3);
        System.out.println("Sorted: " + st3);
        
        // Test case 4: Reverse sorted (descending)
        System.out.println("\n4. Reverse sorted [4, 3, 2, 1] (top→):");
        Stack<Integer> st4 = new Stack<>();
        st4.push(1);
        st4.push(2);
        st4.push(3);
        st4.push(4);
        System.out.println("Original: " + st4);
        sortStack(st4);
        System.out.println("Sorted: " + st4);
        
        // Test case 5: Random order
        System.out.println("\n5. Random order [3, 1, 4, 2] (top→):");
        Stack<Integer> st5 = new Stack<>();
        st5.push(2);
        st5.push(4);
        st5.push(1);
        st5.push(3);
        System.out.println("Original: " + st5);
        visualizeSorting(new Stack<>(st5));
        sortStack(st5);
        System.out.println("Sorted: " + st5);
        
        // Test case 6: Duplicate elements
        System.out.println("\n6. With duplicates [2, 3, 1, 2, 4] (top→):");
        Stack<Integer> st6 = new Stack<>();
        st6.push(4);
        st6.push(2);
        st6.push(1);
        st6.push(3);
        st6.push(2);
        System.out.println("Original: " + st6);
        sortStack(st6);
        System.out.println("Sorted: " + st6);
        
        // Test case 7: Iterative sorting
        System.out.println("\n7. Iterative sorting [5, 2, 8, 1, 3]:");
        Stack<Integer> st7 = new Stack<>();
        st7.push(3);
        st7.push(1);
        st7.push(8);
        st7.push(2);
        st7.push(5);
        System.out.println("Original: " + st7);
        sortStackIterative(st7);
        System.out.println("Sorted: " + st7);
    }
    
    /**
     * Performance comparison of different sorting methods
     */
    public static void performanceComparison() {
        System.out.println("\n=== Performance Comparison ===");
        
        // Create large stack with random elements
        Stack<Integer> st = new Stack<>();
        int size = 100;
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < size; i++) {
            st.push(rand.nextInt(1000));
        }
        
        // Method 1: Recursive sorting (O(n²))
        Stack<Integer> st1 = new Stack<>();
        st1.addAll(st);
        System.out.println("\n1. Recursive method (O(n²)):");
        long start = System.nanoTime();
        sortStack(st1);
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        
        // Method 2: Iterative sorting with temp stack (O(n²))
        Stack<Integer> st2 = new Stack<>();
        st2.addAll(st);
        System.out.println("\n2. Iterative method with temp stack (O(n²)):");
        start = System.nanoTime();
        sortStackIterative(st2);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        
        // Method 3: Convert to array and sort (O(n log n))
        Stack<Integer> st3 = new Stack<>();
        st3.addAll(st);
        System.out.println("\n3. Convert to array and sort (O(n log n)):");
        start = System.nanoTime();
        Integer[] arr = st3.toArray(new Integer[0]);
        java.util.Arrays.sort(arr);
        st3.clear();
        for (int i = arr.length - 1; i >= 0; i--) {
            st3.push(arr[i]);  // Push in reverse to maintain order
        }
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
    }
    
    /**
     * Demonstrates step-by-step sorting with detailed comments
     */
    public static void demonstrateStepByStep() {
        System.out.println("\n=== Step-by-Step Demonstration ===");
        
        Stack<Integer> st = new Stack<>();
        st.push(3);
        st.push(1);
        st.push(4);
        st.push(2);
        
        System.out.println("Original stack (top → bottom): " + st);
        System.out.println("Goal: Sort in ascending order (smallest at bottom)");
        System.out.println("\nStep 1: sortStack([3, 1, 4, 2])");
        System.out.println("  - Pop top: 3, stack becomes [1, 4, 2]");
        System.out.println("  - Call sortStack([1, 4, 2])");
        
        System.out.println("\nStep 2: sortStack([1, 4, 2])");
        System.out.println("  - Pop top: 1, stack becomes [4, 2]");
        System.out.println("  - Call sortStack([4, 2])");
        
        System.out.println("\nStep 3: sortStack([4, 2])");
        System.out.println("  - Pop top: 4, stack becomes [2]");
        System.out.println("  - Call sortStack([2])");
        
        System.out.println("\nStep 4: sortStack([2])");
        System.out.println("  - Pop top: 2, stack becomes []");
        System.out.println("  - Call sortStack([])");
        
        System.out.println("\nStep 5: sortStack([])");
        System.out.println("  - Base case: empty stack, return");
        
        System.out.println("\nStep 6: Back to sortStack([2])");
        System.out.println("  - Insert 2 in sorted order into []");
        System.out.println("  - Stack empty, push 2 → [2]");
        
        System.out.println("\nStep 7: Back to sortStack([4, 2])");
        System.out.println("  - Insert 4 in sorted order into [2]");
        System.out.println("  - Top is 2, 2 ≤ 4, push 4 → [2, 4]");
        
        System.out.println("\nStep 8: Back to sortStack([1, 4, 2])");
        System.out.println("  - Insert 1 in sorted order into [2, 4]");
        System.out.println("  - Top is 4, 4 > 1, pop 4, stack becomes [2]");
        System.out.println("  - Insert 1 into [2]: top is 2, 2 > 1, pop 2, stack becomes []");
        System.out.println("  - Insert 1 into []: push 1 → [1]");
        System.out.println("  - Push 2 back → [1, 2]");
        System.out.println("  - Push 4 back → [1, 2, 4]");
        
        System.out.println("\nStep 9: Back to sortStack([3, 1, 4, 2])");
        System.out.println("  - Insert 3 in sorted order into [1, 2, 4]");
        System.out.println("  - Top is 4, 4 > 3, pop 4, stack becomes [1, 2]");
        System.out.println("  - Insert 3 into [1, 2]: top is 2, 2 < 3, push 3 → [1, 2, 3]");
        System.out.println("  - Push 4 back → [1, 2, 3, 4]");
        
        System.out.println("\nFinal sorted stack: [1, 2, 3, 4] (smallest 1 at bottom)");
    }
    
    /**
     * Extended version that handles generic comparable stacks
     */
    public static <T extends Comparable<T>> void sortStackGeneric(Stack<T> st) {
        if (st.isEmpty()) return;
        T top = st.pop();
        sortStackGeneric(st);
        insertSortedGeneric(st, top);
    }
    
    private static <T extends Comparable<T>> void insertSortedGeneric(Stack<T> st, T x) {
        if (st.isEmpty() || st.peek().compareTo(x) <= 0) {
            st.push(x);
            return;
        }
        T top = st.pop();
        insertSortedGeneric(st, x);
        st.push(top);
    }
    
    /**
     * Checks if a stack is sorted in ascending order
     */
    public static boolean isSorted(Stack<Integer> st) {
        if (st.isEmpty() || st.size() == 1) return true;
        
        Stack<Integer> temp = new Stack<>();
        boolean sorted = true;
        int prev = st.pop();
        temp.push(prev);
        
        while (!st.isEmpty()) {
            int current = st.pop();
            if (current > prev) {  // Since we're popping from top, we check reverse
                sorted = false;
            }
            temp.push(current);
            prev = current;
        }
        
        // Restore original stack
        while (!temp.isEmpty()) {
            st.push(temp.pop());
        }
        
        return sorted;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Stack Sorting Using Recursion ===\n");
        
        // Basic demonstration
        System.out.println("Basic Example:");
        Stack<Integer> stack = new Stack<>();
        stack.push(3);
        stack.push(1);
        stack.push(4);
        stack.push(2);
        System.out.println("Original: " + stack);
        sortStack(stack);
        System.out.println("Sorted: " + stack);
        System.out.println("Is sorted? " + isSorted(stack));
        
        // Step-by-step demonstration
        demonstrateStepByStep();
        
        // Visualization
        Stack<Integer> stack2 = new Stack<>();
        stack2.push(3);
        stack2.push(1);
        stack2.push(4);
        stack2.push(2);
        visualizeSorting(stack2);
        
        // Test different scenarios
        testSortingScenarios();
        
        // Performance comparison
        performanceComparison();
        
        // Generic stack example
        System.out.println("\n=== Generic Stack Example ===");
        Stack<String> strStack = new Stack<>();
        strStack.push("banana");
        strStack.push("apple");
        strStack.push("date");
        strStack.push("cherry");
        System.out.println("Original string stack: " + strStack);
        sortStackGeneric(strStack);
        System.out.println("Sorted string stack: " + strStack);
        
        // Mathematical analysis
        System.out.println("\n=== Mathematical Analysis ===");
        System.out.println("\nTime Complexity Analysis:");
        System.out.println("Let T(n) be time to sort stack of size n");
        System.out.println("T(n) = T(n-1) + I(n) where I(n) is time to insert in sorted order");
        System.out.println("I(n) = I(n-1) + O(1) = O(n) in worst case");
        System.out.println("So T(n) = T(n-1) + O(n) = O(n²)");
        System.out.println("\nWorst case: Reverse sorted stack (descending to ascending)");
        System.out.println("Best case: Already sorted stack");
        
        System.out.println("\nSpace Complexity Analysis:");
        System.out.println("Recursion depth: O(n)");
        System.out.println("No additional data structures (except recursion stack)");
        
        System.out.println("\n=== Alternative Approaches ===");
        System.out.println("1. Using temporary stack (iterative): O(n²) time, O(n) space");
        System.out.println("2. Convert to array and sort: O(n log n) time, O(n) space");
        System.out.println("3. Merge sort for stacks: O(n log n) possible with O(n) space");
        System.out.println("4. Quick sort for stacks: O(n²) worst case, O(log n) recursion depth");
        
        System.out.println("\n=== Relationship to Other Algorithms ===");
        System.out.println("This algorithm is essentially INSERTION SORT adapted for stacks:");
        System.out.println("1. Remove element from unsorted portion (pop from stack)");
        System.out.println("2. Insert it into correct position in sorted portion");
        System.out.println("3. Repeat until all elements are sorted");
        
        System.out.println("\n=== Common Interview Questions ===");
        System.out.println("Q: Why is the time complexity O(n²)?");
        System.out.println("A: Because in worst case, each insertion requires popping");
        System.out.println("   all elements from the sorted portion to find correct position");
        
        System.out.println("\nQ: Can we do better than O(n²) with stack operations only?");
        System.out.println("A: No, because stack operations only allow access to top element.");
        System.out.println("   To access middle elements, we must pop all elements above them.");
        
        System.out.println("\nQ: What if we're allowed to use one extra stack?");
        System.out.println("A: We can implement a variant of selection sort in O(n²) time");
        System.out.println("   but with better constant factors");
        
        System.out.println("\nQ: How would you sort a stack in descending order?");
        System.out.println("A: Change the comparison in insertSorted from '≤' to '≥'");
        
        System.out.println("\n=== Real-World Applications ===");
        System.out.println("1. Undo stack in editors (recent actions at top)");
        System.out.println("2. Browser history (most recent at top)");
        System.out.println("3. Processing orders by priority");
        System.out.println("4. Managing tasks in priority queues");
    }
}