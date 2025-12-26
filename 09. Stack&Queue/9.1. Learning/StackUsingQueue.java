import java.util.LinkedList;
import java.util.Queue;

/**
 * Stack Implementation using Two Queues
 * Simulates LIFO behavior using FIFO queues
 */
public class StackUsingQueue {

    // q1: Main queue holding elements in stack order
    // q2: Temporary queue for reordering during push
    private final Queue<Integer> q1 = new LinkedList<>();
    private final Queue<Integer> q2 = new LinkedList<>();

    /**
     * Push: O(n) time, O(1) space
     * Algorithm:
     * 1. Add new element to empty q2
     * 2. Move all elements from q1 to q2 (maintains LIFO order)
     * 3. Swap q1 and q2 (q1 now has new element at front)
     */
    public void push(int x) {
        // Step 1: Add new element to empty q2
        q2.offer(x);
        
        // Step 2: Move all from q1 to q2 (reverses order)
        while (!q1.isEmpty()) {
            q2.offer(q1.poll());
        }
        
        // Step 3: Swap references (no data copying)
        Queue<Integer> temp = q1;
        q1 = q2;
        q2 = temp;
    }

    /**
     * Pop: O(1) time - simply remove from front of q1
     */
    public int pop() {
        if (q1.isEmpty()) throw new RuntimeException("Stack underflow");
        return q1.poll();
    }

    /**
     * Top: O(1) time - peek at front of q1
     */
    public int top() {
        if (q1.isEmpty()) throw new RuntimeException("Stack empty");
        return q1.peek();
    }

    public boolean isEmpty() {
        return q1.isEmpty();
    }

    public int size() {
        return q1.size();
    }

    public static void main(String[] args) {
        StackUsingQueue stack = new StackUsingQueue();
        stack.push(10);  // q1: [10]
        stack.push(20);  // q1: [20, 10]
        System.out.println(stack.top());   // 20
        System.out.println(stack.pop());    // 20
        System.out.println(stack.top());    // 10
    }
}