import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Queue Implementation Using Custom Linked List
 * 
 * A custom queue implementation using a singly linked list.
 * This provides O(1) time complexity for enqueue/dequeue operations
 * and demonstrates low-level linked list manipulation.
 * 
 * Advantages over Java's built-in Queue:
 * 1. Complete control over implementation
 * 2. No dependencies on Java Collections Framework
 * 3. Educational value for understanding data structures
 * 4. Memory efficiency for specific use cases
 * 
 * Applications:
 * - Embedded systems with memory constraints
 * - Custom data structure libraries
 * - Interview preparation
 * - Educational demonstrations
 */
public class QueueUsingLinkedList {

    /**
     * Node class representing each element in the queue.
     * Each node contains:
     * - Value: The actual data
     * - Next: Reference to the next node in the queue
     */
    private static class Node {
        int val;
        Node next;
        
        Node(int v) { 
            val = v; 
            next = null; // Explicitly set to null for clarity
        }
    }

    // Front of the queue (where elements are dequeued)
    private Node head;
    
    // Rear of the queue (where elements are enqueued)
    private Node tail;
    
    // Track queue size for O(1) size operation
    private int size;

    /**
     * Constructor: Initialize empty queue
     */
    public QueueUsingLinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Enqueue: Add element to the rear of the queue
     * 
     * Algorithm:
     * 1. Create new node with given value
     * 2. If queue is empty, set both head and tail to new node
     * 3. Otherwise, add new node after tail and update tail
     * 4. Increment size
     * 
     * Time Complexity: O(1)
     * Space Complexity: O(1) for the operation, O(n) for storage
     * 
     * @param x Element to add to queue
     */
    public void enqueue(int x) {
        Node newNode = new Node(x);
        
        if (tail == null) {
            // Queue is empty, new node is both head and tail
            head = newNode;
            tail = newNode;
        } else {
            // Add new node at the end and update tail
            tail.next = newNode;
            tail = newNode;
        }
        
        size++;
    }

    /**
     * Dequeue: Remove and return element from front of queue
     * 
     * Algorithm:
     * 1. Check if queue is empty (throw exception if so)
     * 2. Store value from head node
     * 3. Move head to next node
     * 4. If queue becomes empty, set tail to null
     * 5. Decrement size
     * 6. Return stored value
     * 
     * Time Complexity: O(1)
     * 
     * @return Front element of queue
     * @throws NoSuchElementException if queue is empty
     */
    public int dequeue() {
        if (head == null) {
            throw new NoSuchElementException("Queue underflow: Cannot dequeue from empty queue");
        }
        
        int value = head.val;
        head = head.next;
        
        // If queue becomes empty, also set tail to null
        if (head == null) {
            tail = null;
        }
        
        size--;
        return value;
    }

    /**
     * Front/Peek: View front element without removing it
     * 
     * @return Front element of queue
     * @throws NoSuchElementException if queue is empty
     */
    public int front() {
        if (head == null) {
            throw new NoSuchElementException("Queue empty: Cannot peek empty queue");
        }
        return head.val;
    }

    /**
     * Rear: View last element without removing it
     * 
     * @return Last element of queue
     * @throws NoSuchElementException if queue is empty
     */
    public int rear() {
        if (tail == null) {
            throw new NoSuchElementException("Queue empty: Cannot view rear of empty queue");
        }
        return tail.val;
    }

    /**
     * Check if queue is empty
     * 
     * @return true if queue is empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Get current size of queue
     * O(1) operation due to size tracking
     * 
     * @return Number of elements in queue
     */
    public int size() {
        return size;
    }

    /**
     * Clear all elements from queue
     * O(1) operation (Java GC will handle memory cleanup)
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * 1. GENERIC QUEUE IMPLEMENTATION
     * Supports any data type using generics
     */
    static class GenericQueue<T> {
        private static class Node<T> {
            T val;
            Node<T> next;
            
            Node(T v) {
                val = v;
                next = null;
            }
        }
        
        private Node<T> head;
        private Node<T> tail;
        private int size;
        
        public GenericQueue() {
            head = null;
            tail = null;
            size = 0;
        }
        
        public void enqueue(T x) {
            Node<T> newNode = new Node<>(x);
            
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            
            size++;
        }
        
        public T dequeue() {
            if (head == null) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            T value = head.val;
            head = head.next;
            
            if (head == null) {
                tail = null;
            }
            
            size--;
            return value;
        }
        
        public T front() {
            if (head == null) {
                throw new NoSuchElementException("Queue empty");
            }
            return head.val;
        }
        
        public T rear() {
            if (tail == null) {
                throw new NoSuchElementException("Queue empty");
            }
            return tail.val;
        }
        
        public boolean isEmpty() {
            return head == null;
        }
        
        public int size() {
            return size;
        }
        
        public void clear() {
            head = null;
            tail = null;
            size = 0;
        }
    }

    /**
     * 2. CIRCULAR LINKED LIST QUEUE
     * More efficient for certain operations
     */
    static class CircularQueueLinkedList {
        private static class Node {
            int val;
            Node next;
            
            Node(int v) {
                val = v;
                next = null;
            }
        }
        
        private Node tail; // Only need tail in circular list
        private int size;
        
        public CircularQueueLinkedList() {
            tail = null;
            size = 0;
        }
        
        public void enqueue(int x) {
            Node newNode = new Node(x);
            
            if (tail == null) {
                // First element - point to itself
                newNode.next = newNode;
                tail = newNode;
            } else {
                // Insert after tail and update tail
                newNode.next = tail.next;
                tail.next = newNode;
                tail = newNode;
            }
            
            size++;
        }
        
        public int dequeue() {
            if (tail == null) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            Node head = tail.next;
            int value = head.val;
            
            if (head == tail) {
                // Only one element
                tail = null;
            } else {
                // Remove head and update tail's next
                tail.next = head.next;
            }
            
            size--;
            return value;
        }
        
        public int front() {
            if (tail == null) {
                throw new NoSuchElementException("Queue empty");
            }
            return tail.next.val; // Head is tail.next
        }
        
        public int rear() {
            if (tail == null) {
                throw new NoSuchElementException("Queue empty");
            }
            return tail.val;
        }
        
        public boolean isEmpty() {
            return tail == null;
        }
        
        public int size() {
            return size;
        }
        
        public void rotate() {
            if (tail != null) {
                tail = tail.next; // Move head to rear (rotate)
            }
        }
    }

    /**
     * 3. QUEUE WITH ITERATOR SUPPORT
     * Implements Iterable for enhanced usability
     */
    static class IterableQueue implements Iterable<Integer> {
        private static class Node {
            int val;
            Node next;
            
            Node(int v) {
                val = v;
                next = null;
            }
        }
        
        private Node head;
        private Node tail;
        private int size;
        
        public IterableQueue() {
            head = null;
            tail = null;
            size = 0;
        }
        
        public void enqueue(int x) {
            Node newNode = new Node(x);
            
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            
            size++;
        }
        
        public int dequeue() {
            if (head == null) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            int value = head.val;
            head = head.next;
            
            if (head == null) {
                tail = null;
            }
            
            size--;
            return value;
        }
        
        public int front() {
            if (head == null) {
                throw new NoSuchElementException("Queue empty");
            }
            return head.val;
        }
        
        public boolean isEmpty() {
            return head == null;
        }
        
        public int size() {
            return size;
        }
        
        @Override
        public Iterator<Integer> iterator() {
            return new QueueIterator();
        }
        
        private class QueueIterator implements Iterator<Integer> {
            private Node current = head;
            
            @Override
            public boolean hasNext() {
                return current != null;
            }
            
            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                int value = current.val;
                current = current.next;
                return value;
            }
        }
    }

    /**
     * 4. THREAD-SAFE QUEUE IMPLEMENTATION
     * Synchronized methods for concurrent access
     */
    static class ThreadSafeQueue {
        private static class Node {
            int val;
            Node next;
            
            Node(int v) {
                val = v;
                next = null;
            }
        }
        
        private Node head;
        private Node tail;
        private int size;
        
        public ThreadSafeQueue() {
            head = null;
            tail = null;
            size = 0;
        }
        
        public synchronized void enqueue(int x) {
            Node newNode = new Node(x);
            
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            
            size++;
            notifyAll(); // Notify waiting consumers
        }
        
        public synchronized int dequeue() throws InterruptedException {
            while (head == null) {
                wait(); // Wait for elements to be added
            }
            
            int value = head.val;
            head = head.next;
            
            if (head == null) {
                tail = null;
            }
            
            size--;
            return value;
        }
        
        public synchronized int front() {
            if (head == null) {
                throw new NoSuchElementException("Queue empty");
            }
            return head.val;
        }
        
        public synchronized boolean isEmpty() {
            return head == null;
        }
        
        public synchronized int size() {
            return size;
        }
        
        public synchronized void clear() {
            head = null;
            tail = null;
            size = 0;
            notifyAll();
        }
    }

    /**
     * 5. QUEUE WITH BULK OPERATIONS
     * Supports adding/removing multiple elements
     */
    static class BulkQueue {
        private static class Node {
            int val;
            Node next;
            
            Node(int v) {
                val = v;
                next = null;
            }
        }
        
        private Node head;
        private Node tail;
        private int size;
        
        public BulkQueue() {
            head = null;
            tail = null;
            size = 0;
        }
        
        public void enqueue(int x) {
            Node newNode = new Node(x);
            
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            
            size++;
        }
        
        public void enqueueAll(int... values) {
            for (int value : values) {
                enqueue(value);
            }
        }
        
        public int dequeue() {
            if (head == null) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            int value = head.val;
            head = head.next;
            
            if (head == null) {
                tail = null;
            }
            
            size--;
            return value;
        }
        
        public int[] dequeueAll() {
            int[] result = new int[size];
            for (int i = 0; i < result.length; i++) {
                result[i] = dequeue();
            }
            return result;
        }
        
        public int front() {
            if (head == null) {
                throw new NoSuchElementException("Queue empty");
            }
            return head.val;
        }
        
        public boolean isEmpty() {
            return head == null;
        }
        
        public int size() {
            return size;
        }
        
        public void clear() {
            head = null;
            tail = null;
            size = 0;
        }
    }

    /**
     * 6. QUEUE WITH REVERSE OPERATION
     * Can reverse the queue in-place
     */
    static class ReversibleQueue {
        private static class Node {
            int val;
            Node next;
            
            Node(int v) {
                val = v;
                next = null;
            }
        }
        
        private Node head;
        private Node tail;
        private int size;
        
        public ReversibleQueue() {
            head = null;
            tail = null;
            size = 0;
        }
        
        public void enqueue(int x) {
            Node newNode = new Node(x);
            
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            
            size++;
        }
        
        public int dequeue() {
            if (head == null) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            int value = head.val;
            head = head.next;
            
            if (head == null) {
                tail = null;
            }
            
            size--;
            return value;
        }
        
        public int front() {
            if (head == null) {
                throw new NoSuchElementException("Queue empty");
            }
            return head.val;
        }
        
        public boolean isEmpty() {
            return head == null;
        }
        
        public int size() {
            return size;
        }
        
        /**
         * Reverse the queue in-place using iterative method
         * Time Complexity: O(n)
         * Space Complexity: O(1)
         */
        public void reverse() {
            if (head == null || head.next == null) {
                return; // Nothing to reverse
            }
            
            Node prev = null;
            Node current = head;
            Node next = null;
            
            // Swap head and tail
            Node temp = head;
            head = tail;
            tail = temp;
            
            // Reverse the linked list
            while (current != null) {
                next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            }
        }
        
        /**
         * Reverse the queue using recursion
         */
        public void reverseRecursive() {
            tail = head;
            head = reverseRecursiveHelper(head);
        }
        
        private Node reverseRecursiveHelper(Node node) {
            if (node == null || node.next == null) {
                return node;
            }
            
            Node newHead = reverseRecursiveHelper(node.next);
            node.next.next = node;
            node.next = null;
            return newHead;
        }
    }

    /**
     * 7. COMPREHENSIVE TEST DRIVER
     */
    public static void main(String[] args) {
        System.out.println("=== QUEUE USING LINKED LIST DEMO ===\n");
        
        testBasicOperations();
        testEdgeCases();
        testGenericQueue();
        testCircularQueue();
        testIterableQueue();
        testReversibleQueue();
        testPerformance();
    }
    
    private static void testBasicOperations() {
        System.out.println("1. BASIC QUEUE OPERATIONS");
        
        QueueUsingLinkedList queue = new QueueUsingLinkedList();
        
        System.out.println("Enqueuing elements: 10, 20, 30");
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        
        System.out.println("Size: " + queue.size() + " (expected: 3)");
        System.out.println("Front: " + queue.front() + " (expected: 10)");
        System.out.println("Rear: " + queue.rear() + " (expected: 30)");
        System.out.println("Is empty? " + queue.isEmpty() + " (expected: false)");
        
        System.out.println("\nDequeuing...");
        System.out.println("Dequeue: " + queue.dequeue() + " (expected: 10)");
        System.out.println("Size: " + queue.size() + " (expected: 2)");
        System.out.println("Front: " + queue.front() + " (expected: 20)");
        
        System.out.println("\nDequeuing remaining elements...");
        while (!queue.isEmpty()) {
            System.out.println("Dequeue: " + queue.dequeue());
        }
        
        System.out.println("Size: " + queue.size() + " (expected: 0)");
        System.out.println("Is empty? " + queue.isEmpty() + " (expected: true)");
        
        System.out.println();
    }
    
    private static void testEdgeCases() {
        System.out.println("2. EDGE CASES");
        
        QueueUsingLinkedList queue = new QueueUsingLinkedList();
        
        // Test empty queue operations
        try {
            queue.dequeue();
            System.out.println("FAIL: Should have thrown exception on empty dequeue");
        } catch (NoSuchElementException e) {
            System.out.println("PASS: Correctly threw exception on empty dequeue");
        }
        
        try {
            queue.front();
            System.out.println("FAIL: Should have thrown exception on empty front");
        } catch (NoSuchElementException e) {
            System.out.println("PASS: Correctly threw exception on empty front");
        }
        
        try {
            queue.rear();
            System.out.println("FAIL: Should have thrown exception on empty rear");
        } catch (NoSuchElementException e) {
            System.out.println("PASS: Correctly threw exception on empty rear");
        }
        
        // Test single element queue
        System.out.println("\nTesting single element queue...");
        queue.enqueue(99);
        System.out.println("Size: " + queue.size() + " (expected: 1)");
        System.out.println("Front: " + queue.front() + " (expected: 99)");
        System.out.println("Rear: " + queue.rear() + " (expected: 99)");
        
        // Test clear operation
        System.out.println("\nTesting clear operation...");
        queue.clear();
        System.out.println("Size after clear: " + queue.size() + " (expected: 0)");
        System.out.println("Is empty? " + queue.isEmpty() + " (expected: true)");
        
        System.out.println();
    }
    
    private static void testGenericQueue() {
        System.out.println("3. GENERIC QUEUE");
        
        GenericQueue<String> stringQueue = new GenericQueue<>();
        
        System.out.println("Enqueuing strings...");
        stringQueue.enqueue("Hello");
        stringQueue.enqueue("World");
        stringQueue.enqueue("!");
        
        System.out.println("Size: " + stringQueue.size() + " (expected: 3)");
        System.out.println("Front: " + stringQueue.front() + " (expected: Hello)");
        System.out.println("Rear: " + stringQueue.rear() + " (expected: !)");
        
        System.out.println("\nDequeuing...");
        while (!stringQueue.isEmpty()) {
            System.out.println("Dequeue: " + stringQueue.dequeue());
        }
        
        // Test with other types
        GenericQueue<Double> doubleQueue = new GenericQueue<>();
        doubleQueue.enqueue(3.14);
        doubleQueue.enqueue(2.71);
        System.out.println("\nDouble queue front: " + doubleQueue.front() + " (expected: 3.14)");
        
        System.out.println();
    }
    
    private static void testCircularQueue() {
        System.out.println("4. CIRCULAR LINKED LIST QUEUE");
        
        CircularQueueLinkedList circularQueue = new CircularQueueLinkedList();
        
        System.out.println("Enqueuing: 1, 2, 3");
        circularQueue.enqueue(1);
        circularQueue.enqueue(2);
        circularQueue.enqueue(3);
        
        System.out.println("Front: " + circularQueue.front() + " (expected: 1)");
        System.out.println("Rear: " + circularQueue.rear() + " (expected: 3)");
        System.out.println("Size: " + circularQueue.size() + " (expected: 3)");
        
        System.out.println("\nRotating queue...");
        circularQueue.rotate();
        System.out.println("After rotate, Front: " + circularQueue.front() + " (expected: 2)");
        System.out.println("After rotate, Rear: " + circularQueue.rear() + " (expected: 1)");
        
        System.out.println("\nDequeuing...");
        System.out.println("Dequeue: " + circularQueue.dequeue() + " (expected: 2)");
        System.out.println("Size: " + circularQueue.size() + " (expected: 2)");
        
        System.out.println();
    }
    
    private static void testIterableQueue() {
        System.out.println("5. ITERABLE QUEUE");
        
        IterableQueue iterableQueue = new IterableQueue();
        
        System.out.println("Enqueuing: 100, 200, 300, 400");
        iterableQueue.enqueue(100);
        iterableQueue.enqueue(200);
        iterableQueue.enqueue(300);
        iterableQueue.enqueue(400);
        
        System.out.println("Iterating through queue (front to rear):");
        for (int value : iterableQueue) {
            System.out.println("  " + value);
        }
        
        System.out.println("\nDequeuing first element: " + iterableQueue.dequeue());
        System.out.println("Updated queue contents:");
        for (int value : iterableQueue) {
            System.out.println("  " + value);
        }
        
        System.out.println();
    }
    
    private static void testReversibleQueue() {
        System.out.println("6. REVERSIBLE QUEUE");
        
        ReversibleQueue reversibleQueue = new ReversibleQueue();
        
        System.out.println("Enqueuing: 1, 2, 3, 4, 5");
        reversibleQueue.enqueue(1);
        reversibleQueue.enqueue(2);
        reversibleQueue.enqueue(3);
        reversibleQueue.enqueue(4);
        reversibleQueue.enqueue(5);
        
        System.out.println("Original queue (front to rear):");
        ReversibleQueue temp = new ReversibleQueue();
        while (!reversibleQueue.isEmpty()) {
            int value = reversibleQueue.dequeue();
            System.out.println("  " + value);
            temp.enqueue(value);
        }
        
        // Restore queue
        while (!temp.isEmpty()) {
            reversibleQueue.enqueue(temp.dequeue());
        }
        
        System.out.println("\nReversing queue...");
        reversibleQueue.reverse();
        
        System.out.println("Reversed queue (front to rear):");
        while (!reversibleQueue.isEmpty()) {
            System.out.println("  " + reversibleQueue.dequeue());
        }
        
        System.out.println();
    }
    
    private static void testPerformance() {
        System.out.println("7. PERFORMANCE TEST");
        
        int testSize = 1000000;
        System.out.println("Testing with " + testSize + " operations\n");
        
        // Test basic linked list queue
        long start = System.nanoTime();
        QueueUsingLinkedList queue1 = new QueueUsingLinkedList();
        for (int i = 0; i < testSize; i++) {
            queue1.enqueue(i);
        }
        for (int i = 0; i < testSize; i++) {
            queue1.dequeue();
        }
        long time1 = System.nanoTime() - start;
        
        // Test circular linked list queue
        start = System.nanoTime();
        CircularQueueLinkedList queue2 = new CircularQueueLinkedList();
        for (int i = 0; i < testSize; i++) {
            queue2.enqueue(i);
        }
        for (int i = 0; i < testSize; i++) {
            queue2.dequeue();
        }
        long time2 = System.nanoTime() - start;
        
        System.out.printf("Basic Linked List Queue:  %8.2f ms\n", time1 / 1_000_000.0);
        System.out.printf("Circular Linked List Queue: %8.2f ms\n", time2 / 1_000_000.0);
        
        System.out.println("\nMemory comparison:");
        System.out.println("- Basic Linked List: Each node stores value + next pointer (8-16 bytes overhead)");
        System.out.println("- Circular Linked List: Similar memory, slightly faster rotations");
        System.out.println("- Array-based queue: Fixed size, no per-element overhead");
    }
    
    /**
     * 8. QUEUE WITH FIND OPERATION
     * Can search for elements
     */
    static class SearchableQueue {
        private static class Node {
            int val;
            Node next;
            
            Node(int v) {
                val = v;
                next = null;
            }
        }
        
        private Node head;
        private Node tail;
        private int size;
        
        public SearchableQueue() {
            head = null;
            tail = null;
            size = 0;
        }
        
        public void enqueue(int x) {
            Node newNode = new Node(x);
            
            if (tail == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                tail = newNode;
            }
            
            size++;
        }
        
        public int dequeue() {
            if (head == null) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            int value = head.val;
            head = head.next;
            
            if (head == null) {
                tail = null;
            }
            
            size--;
            return value;
        }
        
        public int front() {
            if (head == null) {
                throw new NoSuchElementException("Queue empty");
            }
            return head.val;
        }
        
        public boolean isEmpty() {
            return head == null;
        }
        
        public int size() {
            return size;
        }
        
        /**
         * Check if queue contains a value
         * Time Complexity: O(n)
         */
        public boolean contains(int value) {
            Node current = head;
            while (current != null) {
                if (current.val == value) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
        
        /**
         * Find first occurrence of value
         * Returns position (0-based) or -1 if not found
         */
        public int find(int value) {
            Node current = head;
            int position = 0;
            
            while (current != null) {
                if (current.val == value) {
                    return position;
                }
                current = current.next;
                position++;
            }
            
            return -1;
        }
        
        /**
         * Remove first occurrence of value
         * Returns true if removed, false if not found
         */
        public boolean remove(int value) {
            if (head == null) {
                return false;
            }
            
            // Special case: remove from head
            if (head.val == value) {
                head = head.next;
                if (head == null) {
                    tail = null;
                }
                size--;
                return true;
            }
            
            // General case: find and remove
            Node current = head;
            while (current.next != null) {
                if (current.next.val == value) {
                    current.next = current.next.next;
                    
                    // If we removed the tail, update tail
                    if (current.next == null) {
                        tail = current;
                    }
                    
                    size--;
                    return true;
                }
                current = current.next;
            }
            
            return false;
        }
    }
}