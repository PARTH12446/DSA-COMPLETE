/**
 * Stack Implementation using Singly Linked List
 * Memory-efficient, dynamic sizing, no array resizing overhead
 */
public class StackUsingLinkedList {

    // Node represents each stack element
    private static class Node {
        int val;
        Node next;
        Node(int v) { 
            val = v; 
            next = null;  // Explicit null for clarity
        }
    }

    private Node head;  // Top of stack
    private int size;   // Optional: track size for O(1) size()

    public StackUsingLinkedList() {
        head = null;
        size = 0;
    }

    /**
     * Push: Add to top (insert at head)
     * Time: O(1), Space: O(1) per element
     */
    public void push(int x) {
        Node node = new Node(x);
        node.next = head;   // New node points to old top
        head = node;        // Update top to new node
        size++;
    }

    /**
     * Pop: Remove and return top element
     * Time: O(1)
     */
    public int pop() {
        if (head == null) throw new RuntimeException("Stack underflow");
        int v = head.val;
        head = head.next;   // Move top to next node
        size--;
        return v;
    }

    /**
     * Top/Peek: View top without removal
     * Time: O(1)
     */
    public int top() {
        if (head == null) throw new RuntimeException("Stack empty");
        return head.val;
    }

    /**
     * Check if stack is empty
     * Time: O(1)
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Get current stack size
     * Time: O(1) with size tracking
     */
    public int size() {
        return size;
    }

    /**
     * Clear all elements
     * Time: O(1) - GC handles cleanup
     */
    public void clear() {
        head = null;
        size = 0;
    }

    public static void main(String[] args) {
        StackUsingLinkedList stack = new StackUsingLinkedList();
        stack.push(10);
        stack.push(20);
        System.out.println(stack.top());   // 20
        System.out.println(stack.pop());    // 20
        System.out.println(stack.size());   // 1
    }
}