import java.util.HashMap;
import java.util.Map;

/**
 * LRU (Least Recently Used) Cache Implementation
 * 
 * Problem: Design and implement a data structure for Least Recently Used cache.
 * 
 * Requirements:
 * 1. `get(key)` - Return value if key exists, otherwise return -1 (O(1) time)
 * 2. `put(key, value)` - Update value if key exists, otherwise insert
 *    - If cache is full, evict least recently used item
 * 
 * Data Structure Design:
 * 1. HashMap<Integer, Node>: For O(1) key lookup
 * 2. Doubly Linked List: For O(1) removal/insertion and maintaining order
 *    - Head (dummy): Most recently used side
 *    - Tail (dummy): Least recently used side
 * 
 * Time Complexity: O(1) for both get and put operations
 * Space Complexity: O(capacity) - Stores nodes for all entries
 * 
 * Design Trade-offs:
 * - Doubly linked list enables O(1) reordering for LRU maintenance
 * - Dummy head/tail nodes simplify edge case handling
 */
public class LRUCache {
    
    /**
     * Node class for doubly linked list.
     * Each node stores key-value pair and pointers to neighbors.
     */
    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;
        
        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public String toString() {
            return "(" + key + "," + value + ")";
        }
    }
    
    private final int capacity;            // Maximum cache capacity
    private final Map<Integer, Node> map;  // Key to node mapping for O(1) lookup
    private final Node head;               // Dummy head (most recently used side)
    private final Node tail;               // Dummy tail (least recently used side)
    private int size;                      // Current cache size
    
    /**
     * Initialize LRU cache with given capacity.
     * 
     * @param capacity Maximum number of items cache can hold
     * @throws IllegalArgumentException if capacity is negative
     */
    public LRUCache(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("Capacity cannot be negative");
        }
        
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.size = 0;
        
        // Initialize dummy head and tail nodes
        this.head = new Node(-1, -1);
        this.tail = new Node(-1, -1);
        
        // Connect head and tail
        head.next = tail;
        tail.prev = head;
    }
    
    /**
     * Get value for key if it exists in cache.
     * 
     * Steps:
     * 1. Look up node in hashmap
     * 2. If not found, return -1
     * 3. If found:
     *    a. Move node to front (most recently used)
     *    b. Return value
     * 
     * @param key Key to look up
     * @return Value if key exists, -1 otherwise
     */
    public int get(int key) {
        Node node = map.get(key);
        
        // Key not in cache
        if (node == null) {
            return -1;
        }
        
        // Move accessed node to front (most recently used)
        moveToFront(node);
        
        return node.value;
    }
    
    /**
     * Insert or update key-value pair in cache.
     * 
     * Steps:
     * 1. If key exists: update value and move to front
     * 2. If key doesn't exist:
     *    a. If cache is full: evict LRU item (tail.prev)
     *    b. Create new node, insert at front, add to map
     * 
     * @param key Key to insert/update
     * @param value Value to associate with key
     */
    public void put(int key, int value) {
        Node node = map.get(key);
        
        if (node != null) {
            // Case 1: Key exists - update value and move to front
            node.value = value;
            moveToFront(node);
        } else {
            // Case 2: New key - need to insert
            
            if (size == capacity) {
                // Cache is full - evict least recently used item
                evictLRU();
            }
            
            // Create and insert new node
            Node newNode = new Node(key, value);
            insertAtFront(newNode);
            map.put(key, newNode);
            size++;
        }
    }
    
    /**
     * Remove a node from the linked list.
     * This removes node from its current position without deleting it.
     * 
     * @param node Node to remove from list
     */
    private void removeNode(Node node) {
        // Update neighbors to skip this node
        node.prev.next = node.next;
        node.next.prev = node.prev;
        
        // Clear node's pointers (optional, helps GC)
        node.prev = null;
        node.next = null;
    }
    
    /**
     * Insert a node at the front of the list (most recently used).
     * 
     * @param node Node to insert at front
     */
    private void insertAtFront(Node node) {
        // Insert between head and head.next
        node.next = head.next;
        node.prev = head;
        
        // Update neighbors
        head.next.prev = node;
        head.next = node;
    }
    
    /**
     * Move existing node to front (most recently used).
     * 
     * @param node Node to move to front
     */
    private void moveToFront(Node node) {
        // Remove from current position
        removeNode(node);
        
        // Insert at front
        insertAtFront(node);
    }
    
    /**
     * Evict least recently used item from cache.
     * Removes node before tail (least recently used).
     */
    private void evictLRU() {
        // The node before tail is the LRU item
        Node lruNode = tail.prev;
        
        // Remove from list
        removeNode(lruNode);
        
        // Remove from map
        map.remove(lruNode.key);
        size--;
    }
    
    // ========== UTILITY METHODS ==========
    
    /**
     * Get current cache size.
     * 
     * @return Number of items currently in cache
     */
    public int size() {
        return size;
    }
    
    /**
     * Check if cache is empty.
     * 
     * @return true if cache has no items, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Check if cache contains key.
     * 
     * @param key Key to check
     * @return true if key exists in cache, false otherwise
     */
    public boolean containsKey(int key) {
        return map.containsKey(key);
    }
    
    /**
     * Clear all entries from cache.
     */
    public void clear() {
        // Clear map
        map.clear();
        
        // Reset list (head points to tail, tail points to head)
        head.next = tail;
        tail.prev = head;
        
        // Reset size
        size = 0;
    }
    
    /**
     * Get all keys in cache in LRU order (most recent first).
     * 
     * @return Array of keys in order from most to least recently used
     */
    public int[] getKeysInOrder() {
        int[] keys = new int[size];
        Node current = head.next;
        int index = 0;
        
        while (current != tail) {
            keys[index++] = current.key;
            current = current.next;
        }
        
        return keys;
    }
    
    /**
     * Get all values in cache in LRU order (most recent first).
     * 
     * @return Array of values in order from most to least recently used
     */
    public int[] getValuesInOrder() {
        int[] values = new int[size];
        Node current = head.next;
        int index = 0;
        
        while (current != tail) {
            values[index++] = current.value;
            current = current.next;
        }
        
        return values;
    }
    
    /**
     * Visualize current cache state for debugging.
     */
    public void printCacheState() {
        System.out.println("\n=== LRU Cache State ===");
        System.out.println("Capacity: " + capacity);
        System.out.println("Current size: " + size);
        System.out.println("Items (most recent → least recent):");
        
        Node current = head.next;
        int position = 1;
        
        while (current != tail) {
            System.out.printf("  %d. Key: %d, Value: %d%n", 
                            position++, current.key, current.value);
            current = current.next;
        }
        
        if (size == 0) {
            System.out.println("  [Cache is empty]");
        }
        
        System.out.println("HashMap size: " + map.size());
    }
    
    // ========== TESTING AND DEMONSTRATION ==========
    
    /**
     * Test scenario demonstrating LRU behavior.
     */
    public static void runLRUDemo() {
        System.out.println("=== LRU Cache Demonstration ===\n");
        
        LRUCache cache = new LRUCache(3);
        
        System.out.println("Initializing cache with capacity 3");
        cache.printCacheState();
        
        System.out.println("\n1. Putting (1, 100), (2, 200), (3, 300)");
        cache.put(1, 100);
        cache.put(2, 200);
        cache.put(3, 300);
        cache.printCacheState();
        
        System.out.println("\n2. Getting key 1 (becomes most recent)");
        int val = cache.get(1);
        System.out.println("Value: " + val);
        cache.printCacheState();
        
        System.out.println("\n3. Putting key 4 (cache full, should evict key 2 - LRU)");
        cache.put(4, 400);
        cache.printCacheState();
        
        System.out.println("\n4. Updating key 3 (should move to front)");
        cache.put(3, 333);
        cache.printCacheState();
        
        System.out.println("\n5. Getting non-existent key 2 (should return -1)");
        val = cache.get(2);
        System.out.println("Value: " + val);
        cache.printCacheState();
    }
    
    /**
     * Run comprehensive test cases.
     */
    public static void runTestCases() {
        System.out.println("=== LRU Cache Test Cases ===\n");
        
        boolean allTestsPassed = true;
        
        // Test 1: Basic operations
        try {
            System.out.println("Test 1: Basic put and get");
            LRUCache cache = new LRUCache(2);
            cache.put(1, 1);
            cache.put(2, 2);
            assert cache.get(1) == 1;
            cache.put(3, 3); // Should evict key 2
            assert cache.get(2) == -1;
            assert cache.get(3) == 3;
            System.out.println("✓ Test 1 passed");
        } catch (AssertionError e) {
            System.out.println("✗ Test 1 failed");
            allTestsPassed = false;
        }
        
        // Test 2: Zero capacity
        try {
            System.out.println("\nTest 2: Zero capacity cache");
            LRUCache cache = new LRUCache(0);
            cache.put(1, 1);
            assert cache.get(1) == -1;
            System.out.println("✓ Test 2 passed");
        } catch (AssertionError e) {
            System.out.println("✗ Test 2 failed");
            allTestsPassed = false;
        }
        
        // Test 3: Update existing key
        try {
            System.out.println("\nTest 3: Update existing key");
            LRUCache cache = new LRUCache(2);
            cache.put(1, 100);
            cache.put(1, 101); // Update
            assert cache.get(1) == 101;
            cache.put(2, 200);
            cache.put(3, 300); // Should evict key 2, not key 1
            assert cache.get(1) == 101;
            assert cache.get(2) == -1;
            System.out.println("✓ Test 3 passed");
        } catch (AssertionError e) {
            System.out.println("✗ Test 3 failed");
            allTestsPassed = false;
        }
        
        // Test 4: LRU ordering
        try {
            System.out.println("\nTest 4: LRU ordering");
            LRUCache cache = new LRUCache(3);
            cache.put(1, 100);
            cache.put(2, 200);
            cache.put(3, 300);
            
            // Access pattern: 2, 1, 3, 2
            cache.get(2);
            cache.get(1);
            cache.get(3);
            cache.get(2);
            
            // Add 4, should evict 1 (least recently accessed)
            cache.put(4, 400);
            assert cache.get(1) == -1;
            assert cache.get(4) == 400;
            System.out.println("✓ Test 4 passed");
        } catch (AssertionError e) {
            System.out.println("✗ Test 4 failed");
            allTestsPassed = false;
        }
        
        // Test 5: Large capacity
        try {
            System.out.println("\nTest 5: Large capacity");
            LRUCache cache = new LRUCache(1000);
            for (int i = 0; i < 1000; i++) {
                cache.put(i, i * 10);
            }
            assert cache.size() == 1000;
            assert cache.get(500) == 5000;
            cache.put(1000, 10000); // Should evict key 0
            assert cache.get(0) == -1;
            System.out.println("✓ Test 5 passed");
        } catch (AssertionError e) {
            System.out.println("✗ Test 5 failed");
            allTestsPassed = false;
        }
        
        System.out.println("\n" + (allTestsPassed ? "All tests passed!" : "Some tests failed"));
    }
    
    /**
     * Performance test with large dataset.
     */
    public static void performanceTest() {
        System.out.println("\n=== Performance Test ===");
        
        int capacity = 10000;
        int operations = 100000;
        LRUCache cache = new LRUCache(capacity);
        java.util.Random rand = new java.util.Random(42);
        
        System.out.println("Capacity: " + capacity);
        System.out.println("Operations: " + operations);
        
        long startTime = System.currentTimeMillis();
        
        // Mix of puts and gets
        for (int i = 0; i < operations; i++) {
            int key = rand.nextInt(capacity * 2); // Some keys will miss
            if (rand.nextBoolean()) {
                cache.put(key, rand.nextInt(1000));
            } else {
                cache.get(key);
            }
        }
        
        long endTime = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (endTime - startTime) + " ms");
        System.out.println("Final cache size: " + cache.size());
        System.out.println("Cache hit rate: " + 
                          (double)cache.size() / operations * 100 + "%");
    }
    
    /**
     * Compare with Java's LinkedHashMap LRU implementation.
     */
    public static void compareWithLinkedHashMap() {
        System.out.println("\n=== Comparison with LinkedHashMap ===");
        
        // Java's LinkedHashMap can be used for LRU with accessOrder=true
        java.util.LinkedHashMap<Integer, Integer> linkedMap = 
            new java.util.LinkedHashMap<>(4, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
                    return size() > 3;
                }
            };
        
        System.out.println("LinkedHashMap LRU (accessOrder=true):");
        linkedMap.put(1, 100);
        linkedMap.put(2, 200);
        linkedMap.put(3, 300);
        linkedMap.get(1); // Move to end (most recent)
        linkedMap.put(4, 400); // Should evict 2
        System.out.println("LinkedHashMap: " + linkedMap);
        System.out.println("Our LRUCache would produce same ordering");
    }
    
    public static void main(String[] args) {
        // Run demonstration
        runLRUDemo();
        
        // Run test cases
        runTestCases();
        
        // Performance test
        performanceTest();
        
        // Comparison
        compareWithLinkedHashMap();
    }
}