import java.util.ArrayList;
import java.util.List;

/**
 * Custom Stack Implementation using ArrayList
 * LIFO (Last-In-First-Out) data structure
 */
public class Stack {

    // ArrayList for dynamic resizing, O(1) amortized push/pop
    private final List<Integer> data = new ArrayList<>();

    /**
     * Push: Add element to top of stack
     * Time: O(1) amortized (ArrayList resize occasionally O(n))
     */
    public void push(int x) {
        data.add(x);
    }

    /**
     * Pop: Remove and return top element
     * Time: O(1) - removes last element
     */
    public int pop() {
        if (data.isEmpty()) throw new RuntimeException("Stack underflow");
        return data.remove(data.size() - 1);
    }

    /**
     * Top/Peek: View top element without removing
     * Time: O(1) - access last element
     */
    public int top() {
        if (data.isEmpty()) throw new RuntimeException("Stack empty");
        return data.get(data.size() - 1);
    }

    /**
     * Check if stack is empty
     * Time: O(1)
     */
    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * Get stack size
     * Time: O(1)
     */
    public int size() {
        return data.size();
    }

    /**
     * Clear all elements
     * Time: O(1) for reference clearing
     */
    public void clear() {
        data.clear();
    }

    public static void main(String[] args) {
        Stack stack = new Stack();
        stack.push(10);
        stack.push(20);
        System.out.println(stack.top());  // 20
        System.out.println(stack.pop());   // 20
        System.out.println(stack.isEmpty()); // false
    }
}