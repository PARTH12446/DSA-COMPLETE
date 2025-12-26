import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;

/**
 * Min Stack Implementation
 * 
 * Problem: Design a stack that supports push, pop, top, and retrieving
 * the minimum element in constant time.
 * 
 * Requirements:
 * - push(x): Push element x onto stack
 * - pop(): Remove element on top of stack
 * - top(): Get top element
 * - getMin(): Retrieve minimum element in stack
 * - All operations must be O(1) time complexity
 * 
 * Applications:
 * - Undo operations in editors
 * - Function call stack tracking
 * - Algorithm implementations (DFS, parsing)
 * - Financial systems tracking minimum values
 */
public class MinStack {

    /**
     * 1. TWO-STACK APPROACH (Standard solution)
     * 
     * Structure:
     * - Main stack: Stores all elements
     * - Min stack: Stores minimum values at each point
     * 
     * Time Complexity: O(1) for all operations
     * Space Complexity: O(n) worst case
     */
    private final Deque<Integer> mainStack;
    private final Deque<Integer> minStack;
    
    public MinStack() {
        // Use ArrayDeque instead of Stack for better performance
        mainStack = new ArrayDeque<>();
        minStack = new ArrayDeque<>();
    }

    /**
     * Push element onto stack.
     * 
     * Algorithm:
     * 1. Push to main stack
     * 2. If minStack is empty OR x ≤ current min, push to minStack
     * 
     * Why ≤ instead of < ?
     * To handle duplicate minimum values correctly.
     * Example: Push 2, 1, 1 → minStack should have [2, 1, 1]
     */
    public void push(int x) {
        // Push to main stack
        mainStack.push(x);
        
        // Push to min stack if it's a new minimum or equal to current minimum
        if (minStack.isEmpty() || x <= minStack.peek()) {
            minStack.push(x);
        }
    }

    /**
     * Remove the element on top of the stack.
     * 
     * Algorithm:
     * 1. Pop from main stack
     * 2. If popped value equals current min, pop from minStack
     */
    public void pop() {
        if (mainStack.isEmpty()) {
            throw new EmptyStackException();
        }
        
        int poppedValue = mainStack.pop();
        
        // If we're popping the current minimum, remove from minStack too
        if (!minStack.isEmpty() && poppedValue == minStack.peek()) {
            minStack.pop();
        }
    }

    /**
     * Get the top element.
     */
    public int top() {
        if (mainStack.isEmpty()) {
            throw new EmptyStackException();
        }
        return mainStack.peek();
    }

    /**
     * Retrieve the minimum element in the stack.
     */
    public int getMin() {
        if (minStack.isEmpty()) {
            throw new EmptyStackException();
        }
        return minStack.peek();
    }

    /**
     * 2. SINGLE-STACK APPROACH WITH PAIRS
     * More space efficient when many duplicate values.
     * 
     * Idea: Store pairs of (value, currentMin) in a single stack.
     */
    static class MinStackPairApproach {
        private final Deque<Pair> stack;
        
        private static class Pair {
            int value;
            int min;
            
            Pair(int value, int min) {
                this.value = value;
                this.min = min;
            }
        }
        
        public MinStackPairApproach() {
            stack = new ArrayDeque<>();
        }
        
        public void push(int x) {
            int currentMin;
            if (stack.isEmpty()) {
                currentMin = x;
            } else {
                currentMin = Math.min(stack.peek().min, x);
            }
            stack.push(new Pair(x, currentMin));
        }
        
        public void pop() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            stack.pop();
        }
        
        public int top() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            return stack.peek().value;
        }
        
        public int getMin() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            return stack.peek().min;
        }
    }

    /**
     * 3. SINGLE-STACK WITH CUSTOM NODE CLASS
     * More object-oriented approach.
     */
    static class MinStackNodeApproach {
        private Node top;
        
        private static class Node {
            int value;
            int min;
            Node next;
            
            Node(int value, int min, Node next) {
                this.value = value;
                this.min = min;
                this.next = next;
            }
        }
        
        public void push(int x) {
            if (top == null) {
                top = new Node(x, x, null);
            } else {
                int currentMin = Math.min(top.min, x);
                top = new Node(x, currentMin, top);
            }
        }
        
        public void pop() {
            if (top == null) {
                throw new EmptyStackException();
            }
            top = top.next;
        }
        
        public int top() {
            if (top == null) {
                throw new EmptyStackException();
            }
            return top.value;
        }
        
        public int getMin() {
            if (top == null) {
                throw new EmptyStackException();
            }
            return top.min;
        }
    }

    /**
     * 4. OPTIMIZED TWO-STACK APPROACH
     * Stores minimum value with count to handle duplicates efficiently.
     */
    static class MinStackOptimized {
        private final Deque<Integer> mainStack;
        private final Deque<MinEntry> minStack;
        
        private static class MinEntry {
            int value;
            int count;
            
            MinEntry(int value) {
                this.value = value;
                this.count = 1;
            }
        }
        
        public MinStackOptimized() {
            mainStack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
        }
        
        public void push(int x) {
            mainStack.push(x);
            
            if (minStack.isEmpty()) {
                minStack.push(new MinEntry(x));
            } else if (x < minStack.peek().value) {
                minStack.push(new MinEntry(x));
            } else if (x == minStack.peek().value) {
                minStack.peek().count++;
            }
        }
        
        public void pop() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            
            int poppedValue = mainStack.pop();
            
            if (poppedValue == minStack.peek().value) {
                if (minStack.peek().count == 1) {
                    minStack.pop();
                } else {
                    minStack.peek().count--;
                }
            }
        }
        
        public int top() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return mainStack.peek();
        }
        
        public int getMin() {
            if (minStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return minStack.peek().value;
        }
    }

    /**
     * 5. MIN STACK WITH MAX OPERATION
     * Supports both getMin() and getMax() in O(1).
     */
    static class MinMaxStack {
        private final Deque<Integer> mainStack;
        private final Deque<Integer> minStack;
        private final Deque<Integer> maxStack;
        
        public MinMaxStack() {
            mainStack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
            maxStack = new ArrayDeque<>();
        }
        
        public void push(int x) {
            mainStack.push(x);
            
            // Update min stack
            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
            }
            
            // Update max stack
            if (maxStack.isEmpty() || x >= maxStack.peek()) {
                maxStack.push(x);
            }
        }
        
        public void pop() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            
            int poppedValue = mainStack.pop();
            
            if (poppedValue == minStack.peek()) {
                minStack.pop();
            }
            
            if (poppedValue == maxStack.peek()) {
                maxStack.pop();
            }
        }
        
        public int top() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return mainStack.peek();
        }
        
        public int getMin() {
            if (minStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return minStack.peek();
        }
        
        public int getMax() {
            if (maxStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return maxStack.peek();
        }
    }

    /**
     * 6. MIN STACK WITH O(1) OPERATIONS AND O(1) EXTRA SPACE
     * Mathematical trick using difference storage.
     * 
     * Key insight: Store (value - min) instead of value.
     * When popping, reconstruct original value.
     */
    static class MinStackConstantSpace {
        private final Deque<Long> stack;
        private long min;
        
        public MinStackConstantSpace() {
            stack = new ArrayDeque<>();
        }
        
        public void push(int x) {
            if (stack.isEmpty()) {
                stack.push(0L);
                min = x;
            } else {
                stack.push((long) x - min);
                if (x < min) {
                    min = x;
                }
            }
        }
        
        public void pop() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            
            long diff = stack.pop();
            
            // If diff is negative, it means we popped the minimum
            if (diff < 0) {
                min = min - diff; // Restore previous min
            }
        }
        
        public int top() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            
            long diff = stack.peek();
            
            if (diff > 0) {
                return (int) (min + diff);
            } else {
                return (int) min;
            }
        }
        
        public int getMin() {
            if (stack.isEmpty()) {
                throw new EmptyStackException();
            }
            return (int) min;
        }
    }

    /**
     * 7. MIN STACK WITH SIZE LIMIT
     * Throws exception if stack exceeds capacity.
     */
    static class BoundedMinStack {
        private final Deque<Integer> mainStack;
        private final Deque<Integer> minStack;
        private final int capacity;
        private int size;
        
        public BoundedMinStack(int capacity) {
            if (capacity <= 0) {
                throw new IllegalArgumentException("Capacity must be positive");
            }
            this.capacity = capacity;
            this.size = 0;
            mainStack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
        }
        
        public void push(int x) {
            if (size == capacity) {
                throw new IllegalStateException("Stack overflow");
            }
            
            mainStack.push(x);
            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
            }
            size++;
        }
        
        public void pop() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            
            int poppedValue = mainStack.pop();
            if (poppedValue == minStack.peek()) {
                minStack.pop();
            }
            size--;
        }
        
        public int top() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return mainStack.peek();
        }
        
        public int getMin() {
            if (minStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return minStack.peek();
        }
        
        public boolean isFull() {
            return size == capacity;
        }
        
        public boolean isEmpty() {
            return size == 0;
        }
        
        public int size() {
            return size;
        }
    }

    /**
     * 8. MIN STACK WITH ROLLBACK SUPPORT
     * Supports save/restore operations.
     */
    static class MinStackWithRollback {
        private final Deque<Integer> mainStack;
        private final Deque<Integer> minStack;
        private final Deque<Integer> checkpointStack;
        
        public MinStackWithRollback() {
            mainStack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
            checkpointStack = new ArrayDeque<>();
        }
        
        public void push(int x) {
            mainStack.push(x);
            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
            }
        }
        
        public void pop() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            
            int poppedValue = mainStack.pop();
            if (poppedValue == minStack.peek()) {
                minStack.pop();
            }
        }
        
        public int top() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return mainStack.peek();
        }
        
        public int getMin() {
            if (minStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return minStack.peek();
        }
        
        // Save current state
        public void save() {
            checkpointStack.push(mainStack.size());
        }
        
        // Restore to last saved state
        public void restore() {
            if (checkpointStack.isEmpty()) {
                throw new IllegalStateException("No checkpoint to restore");
            }
            
            int targetSize = checkpointStack.pop();
            while (mainStack.size() > targetSize) {
                pop();
            }
        }
    }

    /**
     * 9. MIN STACK WITH ITERATOR SUPPORT
     * Allows iteration through stack elements.
     */
    static class IterableMinStack {
        private final Deque<Integer> mainStack;
        private final Deque<Integer> minStack;
        
        public IterableMinStack() {
            mainStack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
        }
        
        public void push(int x) {
            mainStack.push(x);
            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
            }
        }
        
        public void pop() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            
            int poppedValue = mainStack.pop();
            if (poppedValue == minStack.peek()) {
                minStack.pop();
            }
        }
        
        public int top() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return mainStack.peek();
        }
        
        public int getMin() {
            if (minStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return minStack.peek();
        }
        
        // Iterate from top to bottom
        public Iterable<Integer> elements() {
            return () -> mainStack.iterator();
        }
        
        // Iterate from bottom to top
        public Iterable<Integer> elementsBottomUp() {
            return () -> {
                Deque<Integer> temp = new ArrayDeque<>(mainStack);
                Deque<Integer> reversed = new ArrayDeque<>();
                while (!temp.isEmpty()) {
                    reversed.push(temp.pop());
                }
                return reversed.iterator();
            };
        }
    }

    /**
     * 10. COMPREHENSIVE TEST DRIVER
     */
    public static void main(String[] args) {
        System.out.println("=== MIN STACK DEMO ===\n");
        
        testBasicOperations();
        testEdgeCases();
        testPerformance();
        testDifferentImplementations();
        testAdvancedFeatures();
    }
    
    private static void testBasicOperations() {
        System.out.println("1. BASIC OPERATIONS");
        
        MinStack stack = new MinStack();
        
        System.out.println("Testing push, pop, top, getMin...");
        
        // Test 1: Basic sequence
        stack.push(3);
        stack.push(5);
        System.out.println("After push(3), push(5):");
        System.out.println("  top() = " + stack.top() + " (expected: 5)");
        System.out.println("  getMin() = " + stack.getMin() + " (expected: 3)");
        
        stack.push(2);
        stack.push(1);
        System.out.println("\nAfter push(2), push(1):");
        System.out.println("  top() = " + stack.top() + " (expected: 1)");
        System.out.println("  getMin() = " + stack.getMin() + " (expected: 1)");
        
        stack.pop();
        System.out.println("\nAfter pop():");
        System.out.println("  top() = " + stack.top() + " (expected: 2)");
        System.out.println("  getMin() = " + stack.getMin() + " (expected: 2)");
        
        stack.pop();
        System.out.println("\nAfter pop():");
        System.out.println("  top() = " + stack.top() + " (expected: 5)");
        System.out.println("  getMin() = " + stack.getMin() + " (expected: 3)");
        
        System.out.println();
    }
    
    private static void testEdgeCases() {
        System.out.println("2. EDGE CASES");
        
        // Test empty stack operations
        MinStack stack = new MinStack();
        
        try {
            stack.pop();
            System.out.println("FAIL: Should have thrown exception on empty pop");
        } catch (EmptyStackException e) {
            System.out.println("PASS: Correctly threw exception on empty pop");
        }
        
        try {
            stack.top();
            System.out.println("FAIL: Should have thrown exception on empty top");
        } catch (EmptyStackException e) {
            System.out.println("PASS: Correctly threw exception on empty top");
        }
        
        try {
            stack.getMin();
            System.out.println("FAIL: Should have thrown exception on empty getMin");
        } catch (EmptyStackException e) {
            System.out.println("PASS: Correctly threw exception on empty getMin");
        }
        
        // Test duplicate minimum values
        stack.push(2);
        stack.push(2);
        stack.push(1);
        stack.push(1);
        
        System.out.println("\nTesting duplicate minimum values:");
        System.out.println("getMin() = " + stack.getMin() + " (expected: 1)");
        
        stack.pop();
        System.out.println("After pop(), getMin() = " + stack.getMin() + " (expected: 1)");
        
        stack.pop();
        System.out.println("After pop(), getMin() = " + stack.getMin() + " (expected: 2)");
        
        // Test negative numbers
        MinStack negativeStack = new MinStack();
        negativeStack.push(-3);
        negativeStack.push(0);
        negativeStack.push(-5);
        
        System.out.println("\nTesting negative numbers:");
        System.out.println("getMin() = " + negativeStack.getMin() + " (expected: -5)");
        
        negativeStack.pop();
        System.out.println("After pop(), getMin() = " + negativeStack.getMin() + " (expected: -3)");
        
        System.out.println();
    }
    
    private static void testPerformance() {
        System.out.println("3. PERFORMANCE TEST");
        
        int testSize = 1000000;
        System.out.println("Testing with " + testSize + " operations");
        
        // Test Two-Stack approach
        long start = System.nanoTime();
        MinStack stack1 = new MinStack();
        for (int i = 0; i < testSize; i++) {
            stack1.push(i);
        }
        for (int i = 0; i < testSize; i++) {
            stack1.getMin();
            stack1.pop();
        }
        long time1 = System.nanoTime() - start;
        
        // Test Pair approach
        start = System.nanoTime();
        MinStackPairApproach stack2 = new MinStackPairApproach();
        for (int i = 0; i < testSize; i++) {
            stack2.push(i);
        }
        for (int i = 0; i < testSize; i++) {
            stack2.getMin();
            stack2.pop();
        }
        long time2 = System.nanoTime() - start;
        
        // Test Constant Space approach
        start = System.nanoTime();
        MinStackConstantSpace stack3 = new MinStackConstantSpace();
        for (int i = 0; i < testSize; i++) {
            stack3.push(i);
        }
        for (int i = 0; i < testSize; i++) {
            stack3.getMin();
            stack3.pop();
        }
        long time3 = System.nanoTime() - start;
        
        System.out.printf("Two-Stack approach: %8.2f ms\n", time1 / 1_000_000.0);
        System.out.printf("Pair approach:      %8.2f ms\n", time2 / 1_000_000.0);
        System.out.printf("Constant Space:     %8.2f ms\n", time3 / 1_000_000.0);
        System.out.println();
    }
    
    private static void testDifferentImplementations() {
        System.out.println("4. DIFFERENT IMPLEMENTATIONS");
        
        System.out.println("Testing MinMaxStack (supports both min and max):");
        MinMaxStack minMaxStack = new MinMaxStack();
        minMaxStack.push(3);
        minMaxStack.push(5);
        minMaxStack.push(2);
        minMaxStack.push(1);
        
        System.out.println("  getMin() = " + minMaxStack.getMin() + " (expected: 1)");
        System.out.println("  getMax() = " + minMaxStack.getMax() + " (expected: 5)");
        
        minMaxStack.pop();
        System.out.println("After pop():");
        System.out.println("  getMin() = " + minMaxStack.getMin() + " (expected: 2)");
        System.out.println("  getMax() = " + minMaxStack.getMax() + " (expected: 5)");
        
        System.out.println("\nTesting BoundedMinStack (capacity = 3):");
        BoundedMinStack boundedStack = new BoundedMinStack(3);
        boundedStack.push(1);
        boundedStack.push(2);
        boundedStack.push(3);
        
        try {
            boundedStack.push(4);
            System.out.println("FAIL: Should have thrown exception on overflow");
        } catch (IllegalStateException e) {
            System.out.println("PASS: Correctly threw exception on overflow");
        }
        
        System.out.println("\nTesting IterableMinStack:");
        IterableMinStack iterableStack = new IterableMinStack();
        iterableStack.push(1);
        iterableStack.push(2);
        iterableStack.push(3);
        
        System.out.print("  Elements (top to bottom): ");
        for (int num : iterableStack.elements()) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        System.out.print("  Elements (bottom to top): ");
        for (int num : iterableStack.elementsBottomUp()) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        System.out.println();
    }
    
    private static void testAdvancedFeatures() {
        System.out.println("5. ADVANCED FEATURES");
        
        System.out.println("Testing MinStackWithRollback:");
        MinStackWithRollback rollbackStack = new MinStackWithRollback();
        
        rollbackStack.push(10);
        rollbackStack.push(20);
        rollbackStack.push(5);
        
        System.out.println("Current min: " + rollbackStack.getMin() + " (expected: 5)");
        
        // Save state
        rollbackStack.save();
        
        // Make changes
        rollbackStack.push(3);
        rollbackStack.push(15);
        System.out.println("After more pushes, min: " + rollbackStack.getMin() + " (expected: 3)");
        
        // Restore to saved state
        rollbackStack.restore();
        System.out.println("After restore, min: " + rollbackStack.getMin() + " (expected: 5)");
        
        System.out.println("\nTesting OptimizedTwoStack (handles duplicates efficiently):");
        MinStackOptimized optimizedStack = new MinStackOptimized();
        
        optimizedStack.push(2);
        optimizedStack.push(2);
        optimizedStack.push(1);
        optimizedStack.push(1);
        
        System.out.println("Min: " + optimizedStack.getMin() + " (expected: 1)");
        optimizedStack.pop();
        System.out.println("After pop, Min: " + optimizedStack.getMin() + " (expected: 1)");
        optimizedStack.pop();
        System.out.println("After pop, Min: " + optimizedStack.getMin() + " (expected: 2)");
    }
    
    /**
     * 11. MIN STACK WITH BULK OPERATIONS
     * Supports pushing multiple values at once.
     */
    static class BulkMinStack {
        private final Deque<Integer> mainStack;
        private final Deque<Integer> minStack;
        
        public BulkMinStack() {
            mainStack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
        }
        
        public void pushAll(int... values) {
            for (int value : values) {
                push(value);
            }
        }
        
        public void push(int x) {
            mainStack.push(x);
            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
            }
        }
        
        public void pop(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("Count must be positive");
            }
            if (mainStack.size() < count) {
                throw new IllegalStateException("Not enough elements to pop");
            }
            
            for (int i = 0; i < count; i++) {
                pop();
            }
        }
        
        public void pop() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            
            int poppedValue = mainStack.pop();
            if (poppedValue == minStack.peek()) {
                minStack.pop();
            }
        }
        
        public int top() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return mainStack.peek();
        }
        
        public int getMin() {
            if (minStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return minStack.peek();
        }
        
        public void clear() {
            mainStack.clear();
            minStack.clear();
        }
    }
    
    /**
     * 12. MIN STACK WITH CUSTOM COMPARATOR
     * Allows custom ordering for minimum calculation.
     */
    static class CustomComparatorMinStack<T extends Comparable<T>> {
        private final Deque<T> mainStack;
        private final Deque<T> minStack;
        
        public CustomComparatorMinStack() {
            mainStack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
        }
        
        public void push(T x) {
            mainStack.push(x);
            if (minStack.isEmpty() || x.compareTo(minStack.peek()) <= 0) {
                minStack.push(x);
            }
        }
        
        public void pop() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            
            T poppedValue = mainStack.pop();
            if (poppedValue.equals(minStack.peek())) {
                minStack.pop();
            }
        }
        
        public T top() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return mainStack.peek();
        }
        
        public T getMin() {
            if (minStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return minStack.peek();
        }
    }
    
    /**
     * 13. MIN STACK WITH HISTORY
     * Tracks all minimum values over time.
     */
    static class MinStackWithHistory {
        private final Deque<Integer> mainStack;
        private final Deque<Integer> minStack;
        private final Deque<Integer> minHistory;
        
        public MinStackWithHistory() {
            mainStack = new ArrayDeque<>();
            minStack = new ArrayDeque<>();
            minHistory = new ArrayDeque<>();
        }
        
        public void push(int x) {
            mainStack.push(x);
            if (minStack.isEmpty() || x <= minStack.peek()) {
                minStack.push(x);
                minHistory.push(x); // Record when min changes
            }
        }
        
        public void pop() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            
            int poppedValue = mainStack.pop();
            if (poppedValue == minStack.peek()) {
                minStack.pop();
                minHistory.pop(); // Remove from history
            }
        }
        
        public int top() {
            if (mainStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return mainStack.peek();
        }
        
        public int getMin() {
            if (minStack.isEmpty()) {
                throw new EmptyStackException();
            }
            return minStack.peek();
        }
        
        public List<Integer> getMinHistory() {
            List<Integer> history = new ArrayList<>();
            for (int min : minHistory) {
                history.add(min);
            }
            return history;
        }
    }
}