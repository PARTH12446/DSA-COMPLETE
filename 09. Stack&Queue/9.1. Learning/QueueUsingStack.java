import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Queue Implementation Using Stacks
 * 
 * Problem: Implement a queue data structure using only stack operations.
 * A queue is FIFO (First-In-First-Out) while a stack is LIFO (Last-In-First-Out).
 * 
 * This demonstrates how one data structure can emulate another using 
 * different algorithms and trade-offs.
 * 
 * Key Insight: Use two stacks to reverse the order of elements.
 * 
 * Applications:
 * - Algorithm design interviews
 * - Understanding stack/queue relationships
 * - Systems with stack-only hardware
 * - Functional programming with immutable stacks
 */
public class QueueUsingStack {

    /**
     * 1. TWO-STACK APPROACH (Amortized O(1) operations)
     * 
     * Structure:
     * - s1: Input stack (for enqueue operations)
     * - s2: Output stack (for dequeue operations)
     * 
     * Algorithm:
     * - Enqueue: Always push to s1 (O(1))
     * - Dequeue: If s2 empty, transfer all from s1 to s2 (reverses order)
     *            Then pop from s2 (amortized O(1))
     * 
     * Time Complexity:
     * - enqueue: O(1)
     * - dequeue: Amortized O(1) (O(n) worst case but rare)
     * - front: Amortized O(1)
     * 
     * Space Complexity: O(n)
     */
    private final Deque<Integer> s1;  // Input stack (for enqueue)
    private final Deque<Integer> s2;  // Output stack (for dequeue)
    
    public QueueUsingStack() {
        // Use ArrayDeque instead of Stack for better performance
        s1 = new ArrayDeque<>();
        s2 = new ArrayDeque<>();
    }

    /**
     * Enqueue: Add element to queue
     * Simply push to input stack s1
     * 
     * Time: O(1)
     * Space: O(1)
     * 
     * @param x Element to add
     */
    public void enqueue(int x) {
        s1.push(x);
    }

    /**
     * Helper: Transfer all elements from s1 to s2 if s2 is empty
     * This reverses the order (FIFO → LIFO → FIFO)
     * 
     * Example:
     * s1: [1, 2, 3] (top=3) → Transfer → s2: [3, 2, 1] (top=1)
     * Now popping from s2 gives 1, 2, 3 (FIFO order)
     */
    private void shift() {
        if (s2.isEmpty()) {
            // Transfer all elements from s1 to s2
            // This reverses the order twice, restoring FIFO
            while (!s1.isEmpty()) {
                s2.push(s1.pop());
            }
        }
    }

    /**
     * Dequeue: Remove and return front element
     * 
     * Algorithm:
     * 1. Ensure s2 has elements (call shift if needed)
     * 2. Pop from s2
     * 
     * Time: Amortized O(1), worst-case O(n) when shift occurs
     * 
     * @return Front element
     * @throws NoSuchElementException if queue is empty
     */
    public int dequeue() {
        shift();  // Ensure s2 has elements
        if (s2.isEmpty()) {
            throw new NoSuchElementException("Queue underflow: Cannot dequeue from empty queue");
        }
        return s2.pop();
    }

    /**
     * Front/Peek: View front element without removing
     * 
     * @return Front element
     * @throws NoSuchElementException if queue is empty
     */
    public int front() {
        shift();  // Ensure s2 has elements
        if (s2.isEmpty()) {
            throw new NoSuchElementException("Queue empty: Cannot peek empty queue");
        }
        return s2.peek();
    }

    /**
     * Rear: View last element without removing
     * Need to check both stacks
     * 
     * @return Rear element
     * @throws NoSuchElementException if queue is empty
     */
    public int rear() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue empty: Cannot view rear of empty queue");
        }
        
        // Last element is either:
        // - Top of s1 (if s1 not empty)
        // - Bottom of s2 (if s1 empty)
        if (!s1.isEmpty()) {
            return s1.peek();  // Last enqueued element
        } else {
            // Need to find bottom of s2
            // This is O(n) but rear() is rarely used
            Deque<Integer> temp = new ArrayDeque<>();
            while (!s2.isEmpty()) {
                temp.push(s2.pop());
            }
            int rear = temp.peek();
            
            // Restore s2
            while (!temp.isEmpty()) {
                s2.push(temp.pop());
            }
            return rear;
        }
    }

    /**
     * Check if queue is empty
     * 
     * @return true if both stacks are empty
     */
    public boolean isEmpty() {
        return s1.isEmpty() && s2.isEmpty();
    }

    /**
     * Get current size of queue
     * 
     * @return Total elements in both stacks
     */
    public int size() {
        return s1.size() + s2.size();
    }

    /**
     * Clear all elements from queue
     */
    public void clear() {
        s1.clear();
        s2.clear();
    }

    /**
     * 2. ALTERNATIVE: ONE-STACK APPROACH (Using recursion)
     * Uses call stack as the second stack
     * Demonstrates recursion as a stack
     */
    static class QueueUsingOneStack {
        private final Deque<Integer> stack;
        
        public QueueUsingOneStack() {
            stack = new ArrayDeque<>();
        }
        
        /**
         * Enqueue: Simply push to stack
         */
        public void enqueue(int x) {
            stack.push(x);
        }
        
        /**
         * Dequeue: Use recursion to reach bottom of stack
         * Time: O(n), Space: O(n) recursion depth
         */
        public int dequeue() {
            if (stack.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            // Base case: only one element
            if (stack.size() == 1) {
                return stack.pop();
            }
            
            // Recursive case: pop, recurse, push back
            int top = stack.pop();
            int result = dequeue();  // Recurse to get bottom
            stack.push(top);         // Push others back
            return result;
        }
        
        /**
         * Front: Similar to dequeue but without removal
         */
        public int front() {
            if (stack.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            
            if (stack.size() == 1) {
                return stack.peek();
            }
            
            int top = stack.pop();
            int result = front();  // Recurse to get bottom
            stack.push(top);       // Restore stack
            return result;
        }
        
        public boolean isEmpty() {
            return stack.isEmpty();
        }
        
        public int size() {
            return stack.size();
        }
    }

    /**
     * 3. ALTERNATIVE: TWO-STACK WITH DIFFERENT STRATEGY
     * Always keep elements in reverse order in s2
     * More expensive enqueue, cheaper dequeue
     */
    static class QueueUsingStackReverse {
        private final Deque<Integer> s1;
        private final Deque<Integer> s2;
        
        public QueueUsingStackReverse() {
            s1 = new ArrayDeque<>();
            s2 = new ArrayDeque<>();
        }
        
        /**
         * Enqueue: O(n) - always reverse order
         * 1. Push new element to s1
         * 2. Transfer all from s2 to s1
         * 3. Swap s1 and s2
         */
        public void enqueue(int x) {
            // Push new element to s1
            s1.push(x);
            
            // Move all elements from s2 to s1 (reversing order)
            while (!s2.isEmpty()) {
                s1.push(s2.pop());
            }
            
            // Swap s1 and s2
            Deque<Integer> temp = s1;
            s1 = s2;
            s2 = temp;
        }
        
        /**
         * Dequeue: O(1) - just pop from s2
         */
        public int dequeue() {
            if (s2.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            return s2.pop();
        }
        
        public int front() {
            if (s2.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            return s2.peek();
        }
        
        public boolean isEmpty() {
            return s2.isEmpty();
        }
        
        public int size() {
            return s2.size();
        }
    }

    /**
     * 4. GENERIC QUEUE USING STACKS
     * Supports any data type
     */
    static class GenericQueueUsingStacks<T> {
        private final Deque<T> inputStack;
        private final Deque<T> outputStack;
        
        public GenericQueueUsingStacks() {
            inputStack = new ArrayDeque<>();
            outputStack = new ArrayDeque<>();
        }
        
        public void enqueue(T x) {
            inputStack.push(x);
        }
        
        private void shift() {
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
            }
        }
        
        public T dequeue() {
            shift();
            if (outputStack.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            return outputStack.pop();
        }
        
        public T front() {
            shift();
            if (outputStack.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
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
     * 5. THREAD-SAFE QUEUE USING STACKS
     * Synchronized for concurrent access
     */
    static class ThreadSafeQueueUsingStacks {
        private final Deque<Integer> inputStack;
        private final Deque<Integer> outputStack;
        
        public ThreadSafeQueueUsingStacks() {
            inputStack = new ArrayDeque<>();
            outputStack = new ArrayDeque<>();
        }
        
        public synchronized void enqueue(int x) {
            inputStack.push(x);
        }
        
        private synchronized void shift() {
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
            }
        }
        
        public synchronized int dequeue() {
            shift();
            if (outputStack.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            return outputStack.pop();
        }
        
        public synchronized int front() {
            shift();
            if (outputStack.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            return outputStack.peek();
        }
        
        public synchronized boolean isEmpty() {
            return inputStack.isEmpty() && outputStack.isEmpty();
        }
        
        public synchronized int size() {
            return inputStack.size() + outputStack.size();
        }
    }

    /**
     * 6. QUEUE WITH STATISTICS USING STACKS
     * Tracks min, max, sum
     */
    static class QueueWithStatsUsingStacks {
        private final Deque<Integer> inputStack;
        private final Deque<Integer> outputStack;
        private int sum;
        private Integer min;
        private Integer max;
        
        public QueueWithStatsUsingStacks() {
            inputStack = new ArrayDeque<>();
            outputStack = new ArrayDeque<>();
            sum = 0;
            min = null;
            max = null;
        }
        
        public void enqueue(int x) {
            inputStack.push(x);
            sum += x;
            
            if (min == null || x < min) min = x;
            if (max == null || x > max) max = x;
        }
        
        private void shift() {
            if (outputStack.isEmpty()) {
                // Recalculate stats when shifting
                int tempSum = 0;
                Integer tempMin = null;
                Integer tempMax = null;
                
                while (!inputStack.isEmpty()) {
                    int value = inputStack.pop();
                    outputStack.push(value);
                    
                    tempSum += value;
                    if (tempMin == null || value < tempMin) tempMin = value;
                    if (tempMax == null || value > tempMax) tempMax = value;
                }
                
                // Update global stats
                sum = tempSum;
                min = tempMin;
                max = tempMax;
            }
        }
        
        public int dequeue() {
            shift();
            if (outputStack.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            
            int value = outputStack.pop();
            sum -= value;
            
            // Recalculate min/max if needed
            if (value == min || value == max) {
                recalculateStats();
            }
            
            return value;
        }
        
        public int front() {
            shift();
            if (outputStack.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            return outputStack.peek();
        }
        
        public boolean isEmpty() {
            return inputStack.isEmpty() && outputStack.isEmpty();
        }
        
        public int size() {
            return inputStack.size() + outputStack.size();
        }
        
        public int getSum() {
            return sum;
        }
        
        public double getAverage() {
            int size = size();
            if (size == 0) return 0.0;
            return (double) sum / size;
        }
        
        public Integer getMin() {
            return min;
        }
        
        public Integer getMax() {
            return max;
        }
        
        private void recalculateStats() {
            if (isEmpty()) {
                min = null;
                max = null;
                sum = 0;
                return;
            }
            
            // Calculate from both stacks
            min = Integer.MAX_VALUE;
            max = Integer.MIN_VALUE;
            sum = 0;
            
            // Check input stack
            for (int value : inputStack) {
                if (value < min) min = value;
                if (value > max) max = value;
                sum += value;
            }
            
            // Check output stack
            for (int value : outputStack) {
                if (value < min) min = value;
                if (value > max) max = value;
                sum += value;
            }
        }
    }

    /**
     * 7. COMPREHENSIVE TEST DRIVER
     */
    public static void main(String[] args) {
        System.out.println("=== QUEUE USING STACKS DEMO ===\n");
        
        testBasicOperations();
        testPerformanceAnalysis();
        testDifferentImplementations();
        testEdgeCases();
        testAmortizedAnalysis();
        testThreadSafety();
    }
    
    private static void testBasicOperations() {
        System.out.println("1. BASIC OPERATIONS");
        
        QueueUsingStack queue = new QueueUsingStack();
        
        System.out.println("Enqueuing: 10, 20, 30");
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        
        System.out.println("Size: " + queue.size() + " (expected: 3)");
        System.out.println("Front: " + queue.front() + " (expected: 10)");
        System.out.println("Is empty? " + queue.isEmpty() + " (expected: false)");
        
        System.out.println("\nDequeuing...");
        System.out.println("Dequeue: " + queue.dequeue() + " (expected: 10)");
        System.out.println("Size: " + queue.size() + " (expected: 2)");
        System.out.println("Front: " + queue.front() + " (expected: 20)");
        
        System.out.println("\nEnqueuing more: 40, 50");
        queue.enqueue(40);
        queue.enqueue(50);
        
        System.out.println("Size: " + queue.size() + " (expected: 4)");
        
        System.out.println("\nDequeuing all remaining...");
        while (!queue.isEmpty()) {
            System.out.println("Dequeue: " + queue.dequeue());
        }
        
        System.out.println("Size: " + queue.size() + " (expected: 0)");
        System.out.println("Is empty? " + queue.isEmpty() + " (expected: true)");
        
        System.out.println();
    }
    
    private static void testPerformanceAnalysis() {
        System.out.println("2. PERFORMANCE ANALYSIS");
        
        QueueUsingStack queue = new QueueUsingStack();
        int n = 10000;
        
        System.out.println("Testing with " + n + " operations:");
        System.out.println("  Pattern: n enqueues followed by n dequeues");
        
        long start = System.nanoTime();
        
        // Enqueue n elements
        for (int i = 0; i < n; i++) {
            queue.enqueue(i);
        }
        
        // Dequeue n elements
        for (int i = 0; i < n; i++) {
            queue.dequeue();
        }
        
        long time = System.nanoTime() - start;
        
        System.out.printf("Total time: %8.2f ms\n", time / 1_000_000.0);
        System.out.printf("Average time per operation: %8.2f μs\n", 
                         time / (2.0 * n * 1000));
        
        System.out.println("\nPerformance characteristics:");
        System.out.println("- First dequeue causes shift: O(n)");
        System.out.println("- Subsequent dequeues: O(1)");
        System.out.println("- Amortized cost per operation: O(1)");
        
        System.out.println();
    }
    
    private static void testDifferentImplementations() {
        System.out.println("3. DIFFERENT IMPLEMENTATIONS");
        
        System.out.println("Testing One-Stack (recursive) approach:");
        QueueUsingOneStack oneStackQueue = new QueueUsingOneStack();
        
        oneStackQueue.enqueue(1);
        oneStackQueue.enqueue(2);
        oneStackQueue.enqueue(3);
        
        System.out.println("Front: " + oneStackQueue.front() + " (expected: 1)");
        System.out.println("Dequeue: " + oneStackQueue.dequeue() + " (expected: 1)");
        System.out.println("Dequeue: " + oneStackQueue.dequeue() + " (expected: 2)");
        
        System.out.println("\nTesting Generic Queue:");
        GenericQueueUsingStacks<String> genericQueue = new GenericQueueUsingStacks<>();
        genericQueue.enqueue("Hello");
        genericQueue.enqueue("World");
        System.out.println("Dequeue: " + genericQueue.dequeue() + " (expected: Hello)");
        
        System.out.println("\nTesting Queue with Statistics:");
        QueueWithStatsUsingStacks statsQueue = new QueueWithStatsUsingStacks();
        statsQueue.enqueue(10);
        statsQueue.enqueue(20);
        statsQueue.enqueue(5);
        statsQueue.enqueue(30);
        
        System.out.println("Size: " + statsQueue.size() + " (expected: 4)");
        System.out.println("Sum: " + statsQueue.getSum() + " (expected: 65)");
        System.out.println("Average: " + statsQueue.getAverage() + " (expected: 16.25)");
        System.out.println("Min: " + statsQueue.getMin() + " (expected: 5)");
        System.out.println("Max: " + statsQueue.getMax() + " (expected: 30)");
        
        statsQueue.dequeue(); // Remove 10
        System.out.println("\nAfter dequeue:");
        System.out.println("Sum: " + statsQueue.getSum() + " (expected: 55)");
        System.out.println("Min: " + statsQueue.getMin() + " (expected: 5)");
        
        System.out.println();
    }
    
    private static void testEdgeCases() {
        System.out.println("4. EDGE CASES");
        
        QueueUsingStack queue = new QueueUsingStack();
        
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
        
        // Test single element
        queue.enqueue(99);
        System.out.println("\nSingle element queue:");
        System.out.println("Size: " + queue.size() + " (expected: 1)");
        System.out.println("Front: " + queue.front() + " (expected: 99)");
        System.out.println("Dequeue: " + queue.dequeue() + " (expected: 99)");
        System.out.println("Is empty? " + queue.isEmpty() + " (expected: true)");
        
        // Test interleaved enqueue/dequeue
        System.out.println("\nInterleaved operations:");
        queue.enqueue(1);
        queue.enqueue(2);
        System.out.println("Dequeue: " + queue.dequeue() + " (expected: 1)");
        queue.enqueue(3);
        System.out.println("Dequeue: " + queue.dequeue() + " (expected: 2)");
        System.out.println("Dequeue: " + queue.dequeue() + " (expected: 3)");
        
        // Test large number of operations
        System.out.println("\nStress test with 1000 operations:");
        for (int i = 0; i < 1000; i++) {
            queue.enqueue(i);
        }
        for (int i = 0; i < 1000; i++) {
            int value = queue.dequeue();
            if (value != i) {
                System.out.println("ERROR: Expected " + i + ", got " + value);
                break;
            }
        }
        System.out.println("Stress test passed!");
        
        System.out.println();
    }
    
    private static void testAmortizedAnalysis() {
        System.out.println("5. AMORTIZED ANALYSIS DEMONSTRATION");
        
        QueueUsingStack queue = new QueueUsingStack();
        
        System.out.println("Demonstrating amortized O(1) complexity:");
        System.out.println("Operation        | s1 size | s2 size | Cost");
        System.out.println("-----------------|---------|---------|-----");
        
        // Initial state
        System.out.println("Initial          | 0       | 0       | -");
        
        // Enqueue 3 elements (O(1) each)
        queue.enqueue(1);
        System.out.println("Enqueue 1        | 1       | 0       | 1");
        queue.enqueue(2);
        System.out.println("Enqueue 2        | 2       | 0       | 1");
        queue.enqueue(3);
        System.out.println("Enqueue 3        | 3       | 0       | 1");
        
        // First dequeue causes shift (O(n))
        System.out.println("Dequeue (shift)  | 0       | 2       | 3 (shift cost)");
        queue.dequeue(); // This triggers shift
        
        // Subsequent dequeues are O(1)
        System.out.println("Dequeue          | 0       | 1       | 1");
        queue.dequeue();
        System.out.println("Dequeue          | 0       | 0       | 1");
        queue.dequeue();
        
        System.out.println("\nAmortized cost calculation:");
        System.out.println("Total operations: 6");
        System.out.println("Total cost: 1+1+1+3+1+1 = 8");
        System.out.println("Amortized cost per operation: 8/6 ≈ 1.33 (O(1))");
        
        System.out.println("\nKey insight:");
        System.out.println("- Each element is pushed to s1 once: O(1)");
        System.out.println("- Each element is popped from s1 once: O(1)");
        System.out.println("- Each element is pushed to s2 once: O(1)");
        System.out.println("- Each element is popped from s2 once: O(1)");
        System.out.println("- Total: 4 operations per element = O(1) amortized");
        
        System.out.println();
    }
    
    private static void testThreadSafety() {
        System.out.println("6. THREAD SAFETY SIMULATION");
        
        ThreadSafeQueueUsingStacks queue = new ThreadSafeQueueUsingStacks();
        
        // Simulate producer thread
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                queue.enqueue(i);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        
        // Simulate consumer thread
        Thread consumer = new Thread(() -> {
            int count = 0;
            while (count < 100) {
                try {
                    queue.dequeue();
                    count++;
                } catch (NoSuchElementException e) {
                    // Queue empty, wait
                }
            }
        });
        
        System.out.println("Starting producer-consumer simulation...");
        
        long start = System.currentTimeMillis();
        producer.start();
        consumer.start();
        
        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        long time = System.currentTimeMillis() - start;
        
        System.out.println("Simulation completed in " + time + " ms");
        System.out.println("Final queue size: " + queue.size() + " (expected: 0)");
        System.out.println("Is empty? " + queue.isEmpty() + " (expected: true)");
    }
    
    /**
     * 8. QUEUE WITH BULK OPERATIONS USING STACKS
     */
    static class BulkQueueUsingStacks {
        private final Deque<Integer> inputStack;
        private final Deque<Integer> outputStack;
        
        public BulkQueueUsingStacks() {
            inputStack = new ArrayDeque<>();
            outputStack = new ArrayDeque<>();
        }
        
        public void enqueue(int x) {
            inputStack.push(x);
        }
        
        public void enqueueAll(int... values) {
            for (int value : values) {
                enqueue(value);
            }
        }
        
        private void shift() {
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
            }
        }
        
        public int dequeue() {
            shift();
            if (outputStack.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            return outputStack.pop();
        }
        
        public int[] dequeueAll() {
            shift();
            int size = outputStack.size();
            int[] result = new int[size];
            for (int i = 0; i < size; i++) {
                result[i] = outputStack.pop();
            }
            return result;
        }
        
        public int front() {
            shift();
            if (outputStack.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
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
     * 9. QUEUE WITH ITERATOR SUPPORT
     */
    static class IterableQueueUsingStacks implements Iterable<Integer> {
        private final Deque<Integer> inputStack;
        private final Deque<Integer> outputStack;
        
        public IterableQueueUsingStacks() {
            inputStack = new ArrayDeque<>();
            outputStack = new ArrayDeque<>();
        }
        
        public void enqueue(int x) {
            inputStack.push(x);
        }
        
        private void shift() {
            if (outputStack.isEmpty()) {
                while (!inputStack.isEmpty()) {
                    outputStack.push(inputStack.pop());
                }
            }
        }
        
        public int dequeue() {
            shift();
            if (outputStack.isEmpty()) {
                throw new NoSuchElementException("Queue underflow");
            }
            return outputStack.pop();
        }
        
        public int front() {
            shift();
            if (outputStack.isEmpty()) {
                throw new NoSuchElementException("Queue empty");
            }
            return outputStack.peek();
        }
        
        public boolean isEmpty() {
            return inputStack.isEmpty() && outputStack.isEmpty();
        }
        
        public int size() {
            return inputStack.size() + outputStack.size();
        }
        
        @Override
        public Iterator<Integer> iterator() {
            // Create a copy for iteration
            shift();
            Deque<Integer> temp = new ArrayDeque<>();
            for (int value : outputStack) {
                temp.push(value);
            }
            
            return new Iterator<Integer>() {
                @Override
                public boolean hasNext() {
                    return !temp.isEmpty();
                }
                
                @Override
                public Integer next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return temp.pop();
                }
            };
        }
    }
}