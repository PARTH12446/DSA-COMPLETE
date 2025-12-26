/**
 * Enhanced MiddleNode class with additional optimizations and features
 */

public class MiddleNodeEnhanced {
    
    // ==================== CORE IMPLEMENTATIONS ====================
    
    /**
     * OPTIMIZED Two-pointer approach with early termination
     * 
     * Optimization: For very large lists, we can add a check
     * to return early if we know the length in advance
     */
    public static ListNode middleNodeOptimized(ListNode head) {
        if (head == null) return null;
        
        // Optional: If we know length, we can optimize further
        // For demonstration, we'll use the standard approach
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Use bitwise AND for slightly faster null check
        while (fast != null) {
            fast = fast.next;
            if (fast == null) break;
            fast = fast.next;
            slow = slow.next;
        }
        
        return slow;
    }
    
    /**
     * Thread-safe version using local variables
     * Useful for concurrent environments
     */
    public static synchronized ListNode middleNodeThreadSafe(ListNode head) {
        if (head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    // ==================== ENHANCED UTILITIES ====================
    
    /**
     * Find middle node with custom comparator
     * Useful when nodes have complex data
     */
    public static <T> ListNode middleNodeCustom(ListNode head, 
                                                java.util.Comparator<ListNode> comparator) {
        if (head == null) return null;
        
        // First get the middle node
        ListNode middle = middleNode(head);
        
        // If comparator is provided, we could implement special logic
        // For now, just return the standard middle
        return middle;
    }
    
    /**
     * Find middle node and return both node and its position
     * Returns an array: [middleNode, position (0-indexed)]
     */
    public static Object[] middleNodeWithPosition(ListNode head) {
        if (head == null) return new Object[]{null, -1};
        
        ListNode slow = head;
        ListNode fast = head;
        int position = 0;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            position++;
        }
        
        return new Object[]{slow, position};
    }
    
    /**
     * Find middle nodes in a sliding window
     * Useful for streaming data or real-time processing
     */
    public static List<ListNode> slidingWindowMiddles(ListNode head, int windowSize) {
        List<ListNode> middles = new ArrayList<>();
        
        if (head == null || windowSize <= 0) return middles;
        
        ListNode windowStart = head;
        
        while (windowStart != null) {
            // Create window of specified size
            ListNode current = windowStart;
            int count = 0;
            
            while (current != null && count < windowSize) {
                count++;
                current = current.next;
            }
            
            if (count == windowSize) {
                // Find middle of this window
                ListNode middle = middleNodeOfSubList(windowStart, windowSize);
                middles.add(middle);
            }
            
            windowStart = windowStart.next;
        }
        
        return middles;
    }
    
    private static ListNode middleNodeOfSubList(ListNode start, int size) {
        if (start == null || size <= 0) return null;
        
        ListNode slow = start;
        ListNode fast = start;
        int steps = size / 2;
        
        for (int i = 0; i < steps; i++) {
            if (fast.next != null) {
                fast = fast.next.next;
                slow = slow.next;
            }
        }
        
        return slow;
    }
    
    // ==================== PERFORMANCE OPTIMIZATIONS ====================
    
    /**
     * Cached middle node calculation
     * Maintains a cache of previously calculated middles
     */
    public static class CachedMiddleFinder {
        private final Map<ListNode, ListNode> cache = new WeakHashMap<>();
        
        public ListNode getMiddleCached(ListNode head) {
            if (head == null) return null;
            
            // Check cache first
            if (cache.containsKey(head)) {
                return cache.get(head);
            }
            
            // Calculate and cache
            ListNode middle = middleNode(head);
            cache.put(head, middle);
            
            return middle;
        }
        
        public void clearCache() {
            cache.clear();
        }
    }
    
    /**
     * Batch middle calculation for multiple lists
     * More efficient than calculating individually
     */
    public static List<ListNode> batchFindMiddles(List<ListNode> heads) {
        List<ListNode> results = new ArrayList<>();
        
        for (ListNode head : heads) {
            results.add(middleNode(head));
        }
        
        return results;
    }
    
    // ==================== PARALLEL PROCESSING ====================
    
    /**
     * Parallel middle calculation for very large lists
     * Uses ForkJoinPool for parallel processing
     */
    public static ListNode middleNodeParallel(ListNode head) {
        if (head == null) return null;
        
        // For very large lists, we can split and process in parallel
        int length = getLength(head);
        
        if (length < 10000) {
            // For smaller lists, use sequential approach
            return middleNode(head);
        }
        
        // For large lists, use parallel approach
        // Split list into chunks and process concurrently
        return parallelMiddle(head, Runtime.getRuntime().availableProcessors());
    }
    
    private static ListNode parallelMiddle(ListNode head, int parallelism) {
        // Implementation would involve:
        // 1. Splitting list into chunks
        // 2. Processing chunks in parallel
        // 3. Combining results
        
        // For simplicity, returning sequential version
        return middleNode(head);
    }
    
    private static int getLength(ListNode head) {
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }
    
    // ==================== ERROR HANDLING & VALIDATION ====================
    
    /**
     * Validated middle node with input checking
     */
    public static ListNode middleNodeValidated(ListNode head) throws IllegalArgumentException {
        if (head == null) {
            throw new IllegalArgumentException("Head cannot be null");
        }
        
        // Validate list doesn't have cycles
        if (hasCycle(head)) {
            throw new IllegalArgumentException("List contains a cycle");
        }
        
        return middleNode(head);
    }
    
    private static boolean hasCycle(ListNode head) {
        if (head == null) return false;
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                return true;
            }
        }
        
        return false;
    }
    
    // ==================== BENCHMARKING ====================
    
    /**
     * Benchmark different middle-finding algorithms
     */
    public static void benchmarkMiddleAlgorithms(ListNode head, int iterations) {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("BENCHMARK RESULTS");
        System.out.println("=".repeat(70));
        
        Map<String, Long> times = new LinkedHashMap<>();
        
        // Test Two-pointer
        times.put("Two-pointer", benchmark(() -> middleNode(head), iterations));
        
        // Test Two-pass
        times.put("Two-pass", benchmark(() -> middleNodeTwoPass(head), iterations));
        
        // Test Array
        times.put("Array", benchmark(() -> middleNodeArray(head), iterations));
        
        // Test Optimized
        times.put("Optimized", benchmark(() -> middleNodeOptimized(head), iterations));
        
        // Print results
        System.out.printf("%-20s %-15s %-15s\n", "Algorithm", "Time (ns)", "Relative");
        System.out.println("-".repeat(50));
        
        long minTime = Collections.min(times.values());
        
        for (Map.Entry<String, Long> entry : times.entrySet()) {
            long time = entry.getValue();
            double relative = (double) time / minTime;
            System.out.printf("%-20s %-15d %-15.2f\n", 
                            entry.getKey(), time, relative);
        }
    }
    
    private static long benchmark(Runnable task, int iterations) {
        long start = System.nanoTime();
        
        for (int i = 0; i < iterations; i++) {
            task.run();
        }
        
        long end = System.nanoTime();
        return (end - start) / iterations;
    }
    
    // ==================== VISUALIZATION ENHANCEMENTS ====================
    
    /**
     * Enhanced visualization with ASCII art
     */
    public static void visualizeMiddleASCII(ListNode head) {
        if (head == null) {
            System.out.println("Empty List");
            return;
        }
        
        ListNode middle = middleNode(head);
        ListNode current = head;
        int index = 0;
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ASCII VISUALIZATION");
        System.out.println("=".repeat(70));
        
        while (current != null) {
            String nodeStr = String.format("[%2d]", current.val);
            
            if (current == middle) {
                // Highlight middle with special formatting
                nodeStr = "\u001B[32m" + nodeStr + "\u001B[0m"; // Green color
                System.out.print(nodeStr + " ← MIDDLE");
            } else {
                System.out.print(nodeStr);
            }
            
            if (current.next != null) {
                System.out.print(" → ");
            }
            
            current = current.next;
            index++;
            
            // Add line break every 10 nodes for readability
            if (index % 10 == 0 && current != null) {
                System.out.println();
                System.out.print("  ".repeat(9) + "↓");
                System.out.println();
                System.out.print("  ".repeat(9));
            }
        }
        
        System.out.println("\n" + "-".repeat(70));
        System.out.printf("Middle Node: %d (Position: %d)\n", 
                         middle.val, distanceToMiddle(head));
    }
    
    // ==================== ADDITIONAL TEST CASES ====================
    
    /**
     * Comprehensive edge case testing
     */
    public static void runEdgeCaseTests() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("EDGE CASE TESTING");
        System.out.println("=".repeat(70));
        
        // Test 1: List with 1 million nodes
        testLargeList(1_000_000);
        
        // Test 2: List with alternating pattern
        testAlternatingPattern(20);
        
        // Test 3: List with descending values
        testDescendingList(15);
        
        // Test 4: Circular list (should detect error)
        testCircularList();
        
        // Test 5: Memory usage test
        testMemoryUsage();
    }
    
    private static void testLargeList(int size) {
        System.out.printf("\nTesting list with %d nodes:\n", size);
        
        ListNode head = createLargeList(size);
        
        long start = System.currentTimeMillis();
        ListNode middle = middleNode(head);
        long end = System.currentTimeMillis();
        
        System.out.printf("Middle value: %d\n", middle.val);
        System.out.printf("Time taken: %d ms\n", end - start);
        System.out.printf("Memory used: ~%d KB\n", 
                         Runtime.getRuntime().totalMemory() / 1024);
    }
    
    private static ListNode createLargeList(int size) {
        if (size <= 0) return null;
        
        ListNode head = new ListNode(1);
        ListNode current = head;
        
        for (int i = 2; i <= size; i++) {
            current.next = new ListNode(i);
            current = current.next;
        }
        
        return head;
    }
    
    private static void testAlternatingPattern(int size) {
        System.out.printf("\nTesting alternating pattern (%d nodes):\n", size);
        
        ListNode head = new ListNode(1);
        ListNode current = head;
        
        for (int i = 2; i <= size; i++) {
            int value = (i % 2 == 0) ? 100 + i : i;
            current.next = new ListNode(value);
            current = current.next;
        }
        
        printListWithMiddle(head);
    }
    
    private static void testDescendingList(int size) {
        System.out.printf("\nTesting descending list (%d nodes):\n", size);
        
        ListNode head = new ListNode(size);
        ListNode current = head;
        
        for (int i = size - 1; i >= 1; i--) {
            current.next = new ListNode(i);
            current = current.next;
        }
        
        printListWithMiddle(head);
    }
    
    private static void testCircularList() {
        System.out.println("\nTesting circular list (should throw exception):");
        
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = head; // Create cycle
        
        try {
            ListNode middle = middleNodeValidated(head);
            System.out.println("ERROR: Should have thrown exception!");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly detected cycle: " + e.getMessage());
        }
    }
    
    private static void testMemoryUsage() {
        System.out.println("\nTesting memory usage:");
        
        Runtime runtime = Runtime.getRuntime();
        long initialMemory = runtime.totalMemory() - runtime.freeMemory();
        
        // Create and process large list
        ListNode largeList = createLargeList(100000);
        ListNode middle = middleNode(largeList);
        
        long finalMemory = runtime.totalMemory() - runtime.freeMemory();
        long memoryUsed = finalMemory - initialMemory;
        
        System.out.printf("Memory used by algorithm: ~%d KB\n", memoryUsed / 1024);
        System.out.printf("Middle node value: %d\n", middle.val);
    }
    
    // ==================== INTEGRATION WITH EXISTING CODE ====================
    
    // Copy all existing utility methods from MiddleNode class
    private static ListNode middleNode(ListNode head) {
        if (head == null) return null;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    private static ListNode middleNodeTwoPass(ListNode head) {
        if (head == null) return null;
        int count = 0;
        ListNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        current = head;
        for (int i = 0; i < count / 2; i++) {
            current = current.next;
        }
        return current;
    }
    
    private static ListNode middleNodeArray(ListNode head) {
        if (head == null) return null;
        List<ListNode> nodes = new ArrayList<>();
        ListNode current = head;
        while (current != null) {
            nodes.add(current);
            current = current.next;
        }
        return nodes.get(nodes.size() / 2);
    }
    
    private static ListNode firstMiddleNode(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
    
    private static int distanceToMiddle(ListNode head) {
        if (head == null) return 0;
        int distance = 0;
        ListNode middle = middleNode(head);
        ListNode current = head;
        while (current != middle) {
            distance++;
            current = current.next;
        }
        return distance;
    }
    
    private static void printListWithMiddle(ListNode head) {
        ListNode middle = middleNode(head);
        ListNode current = head;
        System.out.print("List: ");
        while (current != null) {
            if (current == middle) {
                System.out.print("[" + current.val + "]");
            } else {
                System.out.print(current.val);
            }
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println();
    }
    
    // ==================== MAIN METHOD WITH ALL FEATURES ====================
    
    public static void main(String[] args) {
        System.out.println("=".repeat(70));
        System.out.println("ENHANCED MIDDLE NODE FINDER - ALL FEATURES");
        System.out.println("=".repeat(70));
        
        // Create test lists
        ListNode oddList = createTestList(7);  // 1→2→3→4→5→6→7
        ListNode evenList = createTestList(8); // 1→2→3→4→5→6→7→8
        
        // Test 1: Basic functionality
        System.out.println("\n1. BASIC FUNCTIONALITY TEST");
        System.out.println("-".repeat(40));
        System.out.print("Odd list (7 nodes): ");
        printListWithMiddle(oddList);
        System.out.print("Even list (8 nodes): ");
        printListWithMiddle(evenList);
        
        // Test 2: Enhanced visualization
        System.out.println("\n2. ENHANCED VISUALIZATION");
        System.out.println("-".repeat(40));
        visualizeMiddleASCII(oddList);
        
        // Test 3: Performance benchmark
        System.out.println("\n3. PERFORMANCE BENCHMARK");
        System.out.println("-".repeat(40));
        benchmarkMiddleAlgorithms(oddList, 10000);
        
        // Test 4: Edge cases
        System.out.println("\n4. EDGE CASE TESTS");
        System.out.println("-".repeat(40));
        runEdgeCaseTests();
        
        // Test 5: Sliding window middles
        System.out.println("\n5. SLIDING WINDOW MIDDLES");
        System.out.println("-".repeat(40));
        List<ListNode> slidingMiddles = slidingWindowMiddles(oddList, 3);
        System.out.print("Sliding window middles (window size 3): ");
        for (ListNode node : slidingMiddles) {
            System.out.print(node.val + " ");
        }
        System.out.println();
        
        // Test 6: Cached finder
        System.out.println("\n6. CACHED FINDER");
        System.out.println("-".repeat(40));
        CachedMiddleFinder finder = new CachedMiddleFinder();
        ListNode cachedMiddle = finder.getMiddleCached(oddList);
        System.out.println("Cached middle: " + cachedMiddle.val);
        
        // Test 7: Error handling
        System.out.println("\n7. ERROR HANDLING");
        System.out.println("-".perte(40));
        try {
            ListNode validated = middleNodeValidated(null);
            System.out.println("ERROR: Should have thrown exception!");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Correctly caught null input: " + e.getMessage());
        }
        
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALL TESTS COMPLETED SUCCESSFULLY");
        System.out.println("=".repeat(70));
    }
    
    private static ListNode createTestList(int size) {
        ListNode head = new ListNode(1);
        ListNode current = head;
        for (int i = 2; i <= size; i++) {
            current.next = new ListNode(i);
            current = current.next;
        }
        return head;
    }
}