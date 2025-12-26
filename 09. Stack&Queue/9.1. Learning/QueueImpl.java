import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Queue Implementation with Enhanced Features
 * 
 * A Queue is a FIFO (First-In-First-Out) data structure.
 * Operations:
 * - enqueue/add: Add element to rear
 * - dequeue/remove: Remove element from front
 * - peek/front: View front element without removing
 * 
 * Applications:
 * - BFS graph traversal
 * - Task scheduling
 * - Message queues
 * - Buffers in streaming
 * - Print job management
 */
public class QueueImpl {

    /**
     * 1. BASIC QUEUE IMPLEMENTATION USING LinkedList
     * 
     * Why LinkedList?
     * - Implements both List and Deque interfaces
     * - O(1) for add/remove at both ends
     * - Dynamic sizing
     * 
     * Time Complexity: All operations O(1)
     * Space Complexity: O(n)
     */
    private final Queue<Integer> queue;
    
    public QueueImpl() {
        // Using LinkedList for basic queue operations
        queue = new LinkedList<>();
    }

    /**
     * Add element to the end of queue (enqueue)
     * 
     * @param x Element to add
     * @return true if successful (always true for LinkedList)
     */
    public boolean enqueue(int x) {
        return queue.offer(x); // Returns true if successful
    }

    /**
     * Remove and return element from front of queue (dequeue)
     * 
     * @return Front element
     * @throws NoSuchElementException if queue is empty
     */
    public int dequeue() {
        if (queue.isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        return queue.poll();
    }

    /**
     * View front element without removing it
     * 
     * @return Front element
     * @throws NoSuchElementException if queue is empty
     */
    public int front() {
        if (queue.isEmpty()) {
            throw new NoSuchElementException("Queue empty");
        }
        return queue.peek();
    }

    /**
     * Check if queue is empty
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * Get current size of queue
     */
    public int size() {
        return queue.size();
    }

    /**
     * Clear all elements from queue
     */
    public void clear() {
        queue.clear();
    }

    /**
     * 2. CIRCULAR QUEUE IMPLEMENTATION USING ARRAY
     * Fixed size, efficient memory usage
     */
    static class CircularQueue {
        private final int[] data;
        private int front;
        private int rear;
        private int count;
        private final int capacity;
        
        public CircularQueue(int capacity) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be positive");
            }
            this.capacity = capacity;
            this.data = new int[capacity];
            this.front = 0;
            this.rear = -1;
            this.count = 0;
        }
        
        /**
         * Add element to queue
         * 
         * @param x Element to add
         * @return true if successful, false if queue is full
         */
        public boolean enqueue(int x) {
            if (isFull()) {
                return false;
            }
            
            rear = (rear + 1) % capacity;
            data[rear] = x;
            count++;
            return true;
        }
        
        /**
         * Remove element from queue
         * 
         * @return Front element
         * @throws NoSuchElementException if queue is empty
         */
        public int dequeue() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            int value = data[front];
            front = (front + 1) % capacity;
            count--;
            return value;
        }
        
        /**
         * View front element
         */
        public int front() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            return data[front];
        }
        
        /**
         * View rear element
         */
        public int rear() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            return data[rear];
        }
        
        public boolean isEmpty() {
            return count == 0;
        }
        
        public boolean isFull() {
            return count == capacity;
        }
        
        public int size() {
            return count;
        }
        
        public int capacity() {
            return capacity;
        }
    }

    /**
     * 3. QUEUE USING TWO STACKS
     * Implements queue using two stacks for educational purposes
     */
    static class QueueWithTwoStacks {
        private final Deque<Integer> inputStack;
        private final Deque<Integer> outputStack;
        
        public QueueWithTwoStacks() {
            inputStack = new ArrayDeque<>();
            outputStack = new ArrayDeque<>();
        }
        
        /**
         * Enqueue - always push to input stack
         */
        public void enqueue(int x) {
            inputStack.push(x);
        }
        
        /**
         * Dequeue - if output stack empty, transfer all from input
         */
        public int dequeue() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            // If output stack is empty, transfer all elements from input
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
            }
            
            return outputStack.pop();
        }
        
        public int front() {
            if (isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            
            // If output stack is empty, transfer all elements from input
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
            }
            
            return outputStack.peek();
        }
        
        public boolean isEmpty() {
            return inputStack.isEmpty() && outputStack.isEmpty();
        }
        
        public int size() {
            return inputStack.size() + outputStack.size();
        }
    }

    /**
     * 4. PRIORITY QUEUE IMPLEMENTATION
     * Elements with higher priority served first
     */
    static class PriorityQueueImpl {
        private final java.util.PriorityQueue<PriorityItem> pq;
        
        private static class PriorityItem implements Comparable<PriorityItem> {
            int value;
            int priority;
            
            PriorityItem(int value, int priority) {
                this.value = value;
                this.priority = priority;
            }
            
            @Override
            public int compareTo(PriorityItem other) {
                // Higher priority first (lower number = higher priority)
                return Integer.compare(this.priority, other.priority);
            }
        }
        
        public PriorityQueueImpl() {
            pq = new java.util.PriorityQueue<>();
        }
        
        public void enqueue(int value, int priority) {
            pq.offer(new PriorityItem(value, priority));
        }
        
        public int dequeue() {
            if (pq.isEmpty()) {
                throw new NoSuchElementException("Priority queue empty");
            }
            return pq.poll().value;
        }
        
        public int peek() {
            if (pq.isEmpty()) {
                throw new NoSuchElementException("Priority queue empty");
            }
            return pq.peek().value;
        }
        
        public boolean isEmpty() {
            return pq.isEmpty();
        }
        
        public int size() {
            return pq.size();
        }
    }

    /**
     * 5. DEQUE (DOUBLE-ENDED QUEUE) IMPLEMENTATION
     * Supports insertion/removal at both ends
     */
    static class DequeImpl {
        private final Deque<Integer> deque;
        
        public DequeImpl() {
            deque = new ArrayDeque<>();
        }
        
        // Front operations
        public void addFirst(int x) {
            deque.addFirst(x);
        }
        
        public int removeFirst() {
            if (isEmpty()) {
                throw new NoSuchElementException("Deque empty");
            }
            return deque.removeFirst();
        }
        
        public int peekFirst() {
            if (isEmpty()) {
                throw new NoSuchElementException("Deque empty");
            }
            return deque.peekFirst();
        }
        
        // Rear operations
        public void addLast(int x) {
            deque.addLast(x);
        }
        
        public int removeLast() {
            if (isEmpty()) {
                throw new NoSuchElementException("Deque empty");
            }
            return deque.removeLast();
        }
        
        public int peekLast() {
            if (isEmpty()) {
                throw new NoSuchElementException("Deque empty");
            }
            return deque.peekLast();
        }
        
        public boolean isEmpty() {
            return deque.isEmpty();
        }
        
        public int size() {
            return deque.size();
        }
        
        public void clear() {
            deque.clear();
        }
    }

    /**
     * 6. BLOCKING QUEUE (SIMULATED)
     * Thread-safe queue with blocking operations
     */
    static class BlockingQueue {
        private final Queue<Integer> queue;
        private final int capacity;
        
        public BlockingQueue(int capacity) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be positive");
            }
            this.capacity = capacity;
            this.queue = new LinkedList<>();
        }
        
        /**
         * Add element, blocks if queue is full
         */
        public synchronized void put(int x) throws InterruptedException {
            while (queue.size() == capacity) {
                wait(); // Wait for space
            }
            queue.offer(x);
            notifyAll(); // Notify waiting consumers
        }
        
        /**
         * Remove element, blocks if queue is empty
         */
        public synchronized int take() throws InterruptedException {
            while (queue.isEmpty()) {
                wait(); // Wait for elements
            }
            int value = queue.poll();
            notifyAll(); // Notify waiting producers
            return value;
        }
        
        public synchronized int size() {
            return queue.size();
        }
        
        public synchronized boolean isEmpty() {
            return queue.isEmpty();
        }
        
        public synchronized boolean isFull() {
            return queue.size() == capacity;
        }
    }

    /**
     * 7. ITERABLE QUEUE WITH CUSTOM ITERATOR
     */
    static class IterableQueue implements Iterable<Integer> {
        private final Queue<Integer> queue;
        
        public IterableQueue() {
            queue = new LinkedList<>();
        }
        
        public void enqueue(int x) {
            queue.offer(x);
        }
        
        public int dequeue() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            return queue.poll();
        }
        
        public int front() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            return queue.peek();
        }
        
        public boolean isEmpty() {
            return queue.isEmpty();
        }
        
        public int size() {
            return queue.size();
        }
        
        @Override
        public Iterator<Integer> iterator() {
            return queue.iterator();
        }
        
        /**
         * Get iterator that iterates from front to rear
         */
        public Iterator<Integer> frontToRearIterator() {
            return new Iterator<Integer>() {
                private final Iterator<Integer> it = queue.iterator();
                
                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }
                
                @Override
                public Integer next() {
                    return it.next();
                }
            };
        }
    }

    /**
     * 8. QUEUE WITH STATISTICS
     * Tracks min, max, sum, average
     */
    static class QueueWithStats {
        private final Queue<Integer> queue;
        private int sum;
        private Integer min;
        private Integer max;
        private int count;
        
        public QueueWithStats() {
            queue = new LinkedList<>();
            sum = 0;
            min = null;
            max = null;
            count = 0;
        }
        
        public void enqueue(int x) {
            queue.offer(x);
            sum += x;
            count++;
            
            if (min == null || x < min) {
                min = x;
            }
            if (max == null || x > max) {
                max = x;
            }
        }
        
        public int dequeue() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            int value = queue.poll();
            sum -= value;
            count--;
            
            // Recalculate min/max if needed
            if (value == min || value == max) {
                recalculateStats();
            }
            
            return value;
        }
        
        public int front() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            return queue.peek();
        }
        
        public boolean isEmpty() {
            return queue.isEmpty();
        }
        
        public int size() {
            return queue.size();
        }
        
        public int getSum() {
            return sum;
        }
        
        public double getAverage() {
            if (count == 0) {
                return 0.0;
            }
            return (double) sum / count;
        }
        
        public Integer getMin() {
            return min;
        }
        
        public Integer getMax() {
            return max;
        }
        
        private void recalculateStats() {
            if (queue.isEmpty()) {
                min = null;
                max = null;
                return;
            }
            
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
            for (int num : queue) {
                if (num < min) min = num;
                if (num > max) max = num;
            }
        }
    }

    /**
     * 9. BOUNDED QUEUE WITH SLIDING WINDOW
     * Maintains only last N elements
     */
    static class BoundedQueue {
        private final Queue<Integer> queue;
        private final int capacity;
        
        public BoundedQueue(int capacity) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be positive");
            }
            this.capacity = capacity;
            this.queue = new LinkedList<>();
        }
        
        public void enqueue(int x) {
            if (queue.size() == capacity) {
                queue.poll(); // Remove oldest element
            }
            queue.offer(x);
        }
        
        public int dequeue() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            return queue.poll();
        }
        
        public int front() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            return queue.peek();
        }
        
        public boolean isEmpty() {
            return queue.isEmpty();
        }
        
        public boolean isFull() {
            return queue.size() == capacity;
        }
        
        public int size() {
            return queue.size();
        }
        
        /**
         * Get all elements as array (oldest to newest)
         */
        public int[] toArray() {
            int[] result = new int[queue.size()];
            int i = 0;
            for (int num : queue) {
                result[i++] = num;
            }
            return result;
        }
    }

    /**
     * 10. COMPREHENSIVE TEST DRIVER
     */
    public static void main(String[] args) {
        System.out.println("=== QUEUE IMPLEMENTATION DEMO ===\n");
        
        testBasicQueue();
        testCircularQueue();
        testQueueWithTwoStacks();
        testPriorityQueue();
        testDeque();
        testQueueWithStats();
        testBoundedQueue();
        testPerformance();
    }
    
    private static void testBasicQueue() {
        System.out.println("1. BASIC QUEUE OPERATIONS");
        
        QueueImpl queue = new QueueImpl();
        
        // Test enqueue
        System.out.println("Enqueuing 1, 2, 3...");
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        System.out.println("Front: " + queue.front() + " (expected: 1)");
        System.out.println("Size: " + queue.size() + " (expected: 3)");
        
        // Test dequeue
        System.out.println("\nDequeuing...");
        System.out.println("Dequeue: " + queue.dequeue() + " (expected: 1)");
        System.out.println("Front: " + queue.front() + " (expected: 2)");
        System.out.println("Size: " + queue.size() + " (expected: 2)");
        
        // Test isEmpty
        System.out.println("\nIs empty? " + queue.isEmpty() + " (expected: false)");
        
        // Clear and test
        queue.clear();
        System.out.println("After clear, is empty? " + queue.isEmpty() + " (expected: true)");
        
        // Test exceptions
        try {
            queue.dequeue();
            System.out.println("FAIL: Should have thrown exception");
        } catch (NoSuchElementException e) {
            System.out.println("PASS: Correctly threw exception on empty dequeue");
        }
        
        System.out.println();
    }
    
    private static void testCircularQueue() {
        System.out.println("2. CIRCULAR QUEUE");
        
        CircularQueue cq = new CircularQueue(3);
        
        System.out.println("Capacity: " + cq.capacity() + " (expected: 3)");
        
        // Test enqueue
        System.out.println("\nEnqueuing 1, 2, 3...");
        System.out.println("Enqueue 1: " + cq.enqueue(1) + " (expected: true)");
        System.out.println("Enqueue 2: " + cq.enqueue(2) + " (expected: true)");
        System.out.println("Enqueue 3: " + cq.enqueue(3) + " (expected: true)");
        System.out.println("Enqueue 4: " + cq.enqueue(4) + " (expected: false - full)");
        
        System.out.println("Is full? " + cq.isFull() + " (expected: true)");
        System.out.println("Front: " + cq.front() + " (expected: 1)");
        System.out.println("Rear: " + cq.rear() + " (expected: 3)");
        
        // Test dequeue with wrap-around
        System.out.println("\nDequeuing 1...");
        System.out.println("Dequeue: " + cq.dequeue() + " (expected: 1)");
        
        System.out.println("Enqueuing 4 (should wrap around)...");
        System.out.println("Enqueue 4: " + cq.enqueue(4) + " (expected: true)");
        System.out.println("Front: " + cq.front() + " (expected: 2)");
        System.out.println("Rear: " + cq.rear() + " (expected: 4)");
        
        System.out.println();
    }
    
    private static void testQueueWithTwoStacks() {
        System.out.println("3. QUEUE USING TWO STACKS");
        
        QueueWithTwoStacks queue = new QueueWithTwoStacks();
        
        System.out.println("Enqueuing 1, 2, 3...");
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        
        System.out.println("Front: " + queue.front() + " (expected: 1)");
        System.out.println("Size: " + queue.size() + " (expected: 3)");
        
        System.out.println("\nDequeuing...");
        System.out.println("Dequeue: " + queue.dequeue() + " (expected: 1)");
        System.out.println("Dequeue: " + queue.dequeue() + " (expected: 2)");
        
        System.out.println("Enqueuing 4, 5...");
        queue.enqueue(4);
        queue.enqueue(5);
        
        System.out.println("Front: " + queue.front() + " (expected: 3)");
        
        System.out.println("\nDequeuing remaining...");
        while (!queue.isEmpty()) {
            System.out.println("Dequeue: " + queue.dequeue());
        }
        
        System.out.println();
    }
    
    private static void testPriorityQueue() {
        System.out.println("4. PRIORITY QUEUE");
        
        PriorityQueueImpl pq = new PriorityQueueImpl();
        
        System.out.println("Enqueuing with priorities:");
        System.out.println("  Value: 10, Priority: 3");
        System.out.println("  Value: 20, Priority: 1");
        System.out.println("  Value: 30, Priority: 2");
        
        pq.enqueue(10, 3);  // Lowest priority
        pq.enqueue(20, 1);  // Highest priority
        pq.enqueue(30, 2);  // Medium priority
        
        System.out.println("\nDequeuing (should be in priority order):");
        System.out.println("Dequeue: " + pq.dequeue() + " (expected: 20 - highest priority)");
        System.out.println("Dequeue: " + pq.dequeue() + " (expected: 30 - medium priority)");
        System.out.println("Dequeue: " + pq.dequeue() + " (expected: 10 - lowest priority)");
        
        System.out.println();
    }
    
    private static void testDeque() {
        System.out.println("5. DOUBLE-ENDED QUEUE (DEQUE)");
        
        DequeImpl deque = new DequeImpl();
        
        System.out.println("Adding to front: 3, 2, 1");
        deque.addFirst(3);
        deque.addFirst(2);
        deque.addFirst(1);
        
        System.out.println("Adding to rear: 4, 5, 6");
        deque.addLast(4);
        deque.addLast(5);
        deque.addLast(6);
        
        System.out.println("\nPeek first: " + deque.peekFirst() + " (expected: 1)");
        System.out.println("Peek last: " + deque.peekLast() + " (expected: 6)");
        System.out.println("Size: " + deque.size() + " (expected: 6)");
        
        System.out.println("\nRemoving from front: " + deque.removeFirst() + " (expected: 1)");
        System.out.println("Removing from rear: " + deque.removeLast() + " (expected: 6)");
        
        System.out.println("Peek first: " + deque.peekFirst() + " (expected: 2)");
        System.out.println("Peek last: " + deque.peekLast() + " (expected: 5)");
        
        System.out.println();
    }
    
    private static void testQueueWithStats() {
        System.out.println("6. QUEUE WITH STATISTICS");
        
        QueueWithStats queue = new QueueWithStats();
        
        System.out.println("Enqueuing: 10, 20, 30, 40");
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(40);
        
        System.out.println("\nStatistics:");
        System.out.println("  Size: " + queue.size() + " (expected: 4)");
        System.out.println("  Sum: " + queue.getSum() + " (expected: 100)");
        System.out.println("  Average: " + queue.getAverage() + " (expected: 25.0)");
        System.out.println("  Min: " + queue.getMin() + " (expected: 10)");
        System.out.println("  Max: " + queue.getMax() + " (expected: 40)");
        
        System.out.println("\nDequeuing: " + queue.dequeue() + " (expected: 10)");
        System.out.println("Updated statistics:");
        System.out.println("  Size: " + queue.size() + " (expected: 3)");
        System.out.println("  Sum: " + queue.getSum() + " (expected: 90)");
        System.out.println("  Average: " + queue.getAverage() + " (expected: 30.0)");
        System.out.println("  Min: " + queue.getMin() + " (expected: 20)");
        System.out.println("  Max: " + queue.getMax() + " (expected: 40)");
        
        System.out.println();
    }
    
    private static void testBoundedQueue() {
        System.out.println("7. BOUNDED QUEUE (Sliding Window)");
        
        BoundedQueue queue = new BoundedQueue(3);
        
        System.out.println("Enqueuing: 1, 2, 3, 4, 5");
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4); // Should remove 1
        queue.enqueue(5); // Should remove 2
        
        System.out.println("\nCurrent queue contents (oldest to newest):");
        int[] contents = queue.toArray();
        for (int i = 0; i < contents.length; i++) {
            System.out.println("  Position " + i + ": " + contents[i] + 
                             " (expected: " + (i + 3) + ")");
        }
        
        System.out.println("\nIs full? " + queue.isFull() + " (expected: true)");
        System.out.println("Size: " + queue.size() + " (expected: 3)");
        System.out.println("Front: " + queue.front() + " (expected: 3)");
        
        System.out.println();
    }
    
    private static void testPerformance() {
        System.out.println("8. PERFORMANCE COMPARISON");
        
        int testSize = 1000000;
        System.out.println("Testing with " + testSize + " operations\n");
        
        // Test LinkedList-based queue
        long start = System.nanoTime();
        QueueImpl queue1 = new QueueImpl();
        for (int i = 0; i < testSize; i++) {
            queue1.enqueue(i);
        }
        for (int i = 0; i < testSize; i++) {
            queue1.dequeue();
        }
        long time1 = System.nanoTime() - start;
        
        // Test Circular Queue
        start = System.nanoTime();
        CircularQueue queue2 = new CircularQueue(testSize);
        for (int i = 0; i < testSize; i++) {
            queue2.enqueue(i);
        }
        for (int i = 0; i < testSize; i++) {
            queue2.dequeue();
        }
        long time2 = System.nanoTime() - start;
        
        // Test ArrayDeque
        start = System.nanoTime();
        Deque<Integer> queue3 = new ArrayDeque<>();
        for (int i = 0; i < testSize; i++) {
            queue3.offer(i);
        }
        for (int i = 0; i < testSize; i++) {
            queue3.poll();
        }
        long time3 = System.nanoTime() - start;
        
        System.out.printf("LinkedList Queue:   %8.2f ms\n", time1 / 1_000_000.0);
        System.out.printf("Circular Queue:     %8.2f ms\n", time2 / 1_000_000.0);
        System.out.printf("ArrayDeque:         %8.2f ms\n", time3 / 1_000_000.0);
        
        System.out.println("\nMemory comparison:");
        System.out.println("- LinkedList: O(n) with node overhead (8-16 bytes per node)");
        System.out.println("- Circular Queue: O(n) fixed, no overhead per element");
        System.out.println("- ArrayDeque: O(n) with resizing, minimal overhead");
    }
    
    /**
     * 11. QUEUE WITH TIME-BASED EXPIRATION
     * Elements expire after certain time
     */
    static class TimeBasedQueue {
        private static class TimedItem {
            int value;
            long timestamp;
            
            TimedItem(int value, long timestamp) {
                this.value = value;
                this.timestamp = timestamp;
            }
        }
        
        private final Queue<TimedItem> queue;
        private final long expirationTimeMillis;
        
        public TimeBasedQueue(long expirationTimeMillis) {
            this.queue = new LinkedList<>();
            this.expirationTimeMillis = expirationTimeMillis;
        }
        
        public void enqueue(int x) {
            cleanExpired(); // Clean before adding
            queue.offer(new TimedItem(x, System.currentTimeMillis()));
        }
        
        public Integer dequeue() {
            cleanExpired(); // Clean before removing
            
            if (queue.isEmpty()) {
                return null;
            }
            
            return queue.poll().value;
        }
        
        public Integer peek() {
            cleanExpired(); // Clean before peeking
            
            if (queue.isEmpty()) {
                return null;
            }
            
            return queue.peek().value;
        }
        
        public int size() {
            cleanExpired(); // Clean before counting
            return queue.size();
        }
        
        public boolean isEmpty() {
            cleanExpired(); // Clean before checking
            return queue.isEmpty();
        }
        
        private void cleanExpired() {
            long currentTime = System.currentTimeMillis();
            
            while (!queue.isEmpty()) {
                TimedItem item = queue.peek();
                if (currentTime - item.timestamp > expirationTimeMillis) {
                    queue.poll(); // Remove expired
                } else {
                    break; // First non-expired item found
                }
            }
        }
    }
    
    /**
     * 12. QUEUE WITH FREQUENCY COUNTER
     * Tracks frequency of each element
     */
    static class FrequencyQueue {
        private final Queue<Integer> queue;
        private final java.util.Map<Integer, Integer> frequencyMap;
        
        public FrequencyQueue() {
            queue = new LinkedList<>();
            frequencyMap = new java.util.HashMap<>();
        }
        
        public void enqueue(int x) {
            queue.offer(x);
            frequencyMap.put(x, frequencyMap.getOrDefault(x, 0) + 1);
        }
        
        public int dequeue() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            int value = queue.poll();
            int count = frequencyMap.get(value);
            
            if (count == 1) {
                frequencyMap.remove(value);
            } else {
                frequencyMap.put(value, count - 1);
            }
            
            return value;
        }
        
        public int front() {
            if (queue.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            return queue.peek();
        }
        
        public boolean isEmpty() {
            return queue.isEmpty();
        }
        
        public int size() {
            return queue.size();
        }
        
        public int getFrequency(int x) {
            return frequencyMap.getOrDefault(x, 0);
        }
        
        public int getMostFrequent() {
            if (frequencyMap.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            
            int maxFreq = 0;
            int mostFrequent = 0;
            
            for (java.util.Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
                if (entry.getValue() > maxFreq) {
                    maxFreq = entry.getValue();
                    mostFrequent = entry.getKey();
                }
            }
            
            return mostFrequent;
        }
    }
}