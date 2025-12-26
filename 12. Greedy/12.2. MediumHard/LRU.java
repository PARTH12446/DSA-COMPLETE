import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Deque;
import java.util.LinkedList;
import java.util.HashSet;

/**
 * Page Faults in LRU (Least Recently Used) Cache (GFG)
 * 
 * Problem Statement:
 * Given the total number of pages and memory capacity (cache size),
 * and a sequence of page references, implement LRU (Least Recently Used)
 * page replacement algorithm and count total page faults.
 * 
 * Page Fault: When a requested page is not present in memory (cache).
 * 
 * Example:
 * Input: pages = [5, 0, 1, 3, 2, 4, 1, 0, 5], capacity = 4
 * Output: 8
 * Explanation: 
 *   Page 5: fault (cache: [5])                    faults=1
 *   Page 0: fault (cache: [5,0])                  faults=2
 *   Page 1: fault (cache: [5,0,1])                faults=3
 *   Page 3: fault (cache: [5,0,1,3])              faults=4
 *   Page 2: fault → replace LRU (5) (cache: [0,1,3,2]) faults=5
 *   Page 4: fault → replace LRU (0) (cache: [1,3,2,4]) faults=6
 *   Page 1: hit (cache: [3,2,4,1])                faults=6
 *   Page 0: fault → replace LRU (3) (cache: [2,4,1,0]) faults=7
 *   Page 5: fault → replace LRU (2) (cache: [4,1,0,5]) faults=8
 * 
 * LRU Implementation Approaches:
 * 1. LinkedHashMap with access order (easiest)
 * 2. HashMap + Doubly Linked List (custom implementation)
 * 3. Deque + HashSet (simpler but O(n) for removal)
 * 
 * Time Complexity: O(n) for n page references
 * Space Complexity: O(c) where c = cache capacity
 */
public class LRU {

    /**
     * Method 1: Using LinkedHashMap with access ordering
     * LinkedHashMap maintains insertion order by default.
     * With accessOrder=true, it maintains access order (LRU).
     * When size exceeds capacity, removeEldestEntry removes LRU.
     */
    private static class LRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;
        
        LRUCache(int capacity) {
            // Parameters: initial capacity, load factor, accessOrder
            // accessOrder=true: maintain in access order (LRU at head, MRU at tail)
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }
        
        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            // Remove LRU (eldest) when size exceeds capacity
            return size() > capacity;
        }
    }
    
    public int pageFaults(int n, int c, int[] pages) {
        LRUCache<Integer, Integer> cache = new LRUCache<>(c);
        int faults = 0;
        
        for (int i = 0; i < n; i++) {
            int page = pages[i];
            
            // Page fault occurs if page not in cache
            if (!cache.containsKey(page)) {
                faults++;
            }
            
            // Put (or update) page in cache
            // LinkedHashMap will move to most recently used position
            cache.put(page, 1);
        }
        
        return faults;
    }
    
    /**
     * Method 2: Custom LRU using HashMap + Doubly Linked List
     * More efficient O(1) operations, shows internal working
     */
    public int pageFaultsCustom(int n, int c, int[] pages) {
        if (c <= 0) return n; // No cache capacity, all are faults
        
        int faults = 0;
        Map<Integer, Node> map = new HashMap<>();
        DoublyLinkedList dll = new DoublyLinkedList();
        
        for (int i = 0; i < n; i++) {
            int page = pages[i];
            
            if (map.containsKey(page)) {
                // Page hit - move to most recent
                Node node = map.get(page);
                dll.remove(node);
                dll.addToFront(node);
            } else {
                // Page fault
                faults++;
                
                if (map.size() == c) {
                    // Cache full - remove LRU (tail)
                    Node lru = dll.removeTail();
                    map.remove(lru.page);
                }
                
                // Add new page
                Node newNode = new Node(page);
                dll.addToFront(newNode);
                map.put(page, newNode);
            }
        }
        
        return faults;
    }
    
    // Doubly Linked List Node for custom LRU
    private static class Node {
        int page;
        Node prev;
        Node next;
        
        Node(int page) {
            this.page = page;
        }
    }
    
    // Doubly Linked List for LRU ordering
    private static class DoublyLinkedList {
        private Node head;
        private Node tail;
        
        void addToFront(Node node) {
            if (head == null) {
                head = tail = node;
            } else {
                node.next = head;
                head.prev = node;
                head = node;
            }
        }
        
        void remove(Node node) {
            if (node == head && node == tail) {
                head = tail = null;
            } else if (node == head) {
                head = head.next;
                head.prev = null;
            } else if (node == tail) {
                tail = tail.prev;
                tail.next = null;
            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
            node.prev = null;
            node.next = null;
        }
        
        Node removeTail() {
            if (tail == null) return null;
            Node removed = tail;
            remove(tail);
            return removed;
        }
    }
    
    /**
     * Method 3: Using Deque and HashSet (simpler but O(n) removal)
     * Not optimal for large c, but easier to understand
     */
    public int pageFaultsDeque(int n, int c, int[] pages) {
        if (c <= 0) return n;
        
        Deque<Integer> deque = new LinkedList<>();  // Store pages in access order
        HashSet<Integer> set = new HashSet<>();     // Quick lookup
        int faults = 0;
        
        for (int i = 0; i < n; i++) {
            int page = pages[i];
            
            if (!set.contains(page)) {
                // Page fault
                faults++;
                
                if (deque.size() == c) {
                    // Remove LRU (front of deque)
                    int lru = deque.removeFirst();
                    set.remove(lru);
                }
                
                // Add new page
                deque.addLast(page);
                set.add(page);
            } else {
                // Page hit - move to most recent (remove and add to end)
                deque.remove(page);  // O(n) operation
                deque.addLast(page);
            }
        }
        
        return faults;
    }
    
    /**
     * Visualization helper: Show step-by-step LRU operations
     */
    public void visualizeLRU(int n, int c, int[] pages) {
        System.out.println("\n=== Visualizing LRU Page Replacement ===");
        System.out.println("Cache capacity: " + c);
        System.out.print("Page reference sequence: [");
        for (int i = 0; i < n; i++) {
            System.out.print(pages[i]);
            if (i < n - 1) System.out.print(", ");
        }
        System.out.println("]");
        
        System.out.println("\nStep-by-step simulation:");
        System.out.printf("%-15s %-30s %-10s %-20s\n", 
            "Page", "Cache Contents (LRU → MRU)", "Hit/Fault", "Action");
        System.out.println("-".repeat(75));
        
        // Using custom implementation for detailed visualization
        Map<Integer, Node> map = new HashMap<>();
        DoublyLinkedList dll = new DoublyLinkedList();
        int faults = 0;
        
        for (int i = 0; i < n; i++) {
            int page = pages[i];
            String hitFault;
            String action = "";
            
            if (map.containsKey(page)) {
                // Page hit
                hitFault = "HIT";
                Node node = map.get(page);
                dll.remove(node);
                dll.addToFront(node);
                action = "Move to front (MRU)";
            } else {
                // Page fault
                hitFault = "FAULT";
                faults++;
                
                if (map.size() == c) {
                    // Cache full
                    Node lru = dll.removeTail();
                    map.remove(lru.page);
                    action = "Replace LRU (" + lru.page + ")";
                } else {
                    action = "Insert into cache";
                }
                
                // Add new page
                Node newNode = new Node(page);
                dll.addToFront(newNode);
                map.put(page, newNode);
            }
            
            // Display current cache state
            StringBuilder cacheState = new StringBuilder("[");
            Node current = dll.head;
            while (current != null) {
                cacheState.append(current.page);
                if (current.next != null) cacheState.append(", ");
                current = current.next;
            }
            cacheState.append("]");  // Head is MRU, tail is LRU
            
            System.out.printf("%-15d %-30s %-10s %-20s\n", 
                page, cacheState.toString(), hitFault, action);
        }
        
        System.out.println("\n=== Summary ===");
        System.out.println("Total page references: " + n);
        System.out.println("Cache hits: " + (n - faults));
        System.out.println("Page faults: " + faults);
        System.out.println("Hit ratio: " + String.format("%.2f%%", (n - faults) * 100.0 / n));
        System.out.println("Fault ratio: " + String.format("%.2f%%", faults * 100.0 / n));
        
        // Compare with other algorithms
        System.out.println("\n=== Comparison with Other Algorithms ===");
        System.out.println("LRU faults: " + faults);
        System.out.println("FIFO faults: " + simulateFIFO(n, c, pages));
        System.out.println("Optimal faults: " + simulateOptimal(n, c, pages));
    }
    
    /**
     * Simulate FIFO (First-In-First-Out) for comparison
     */
    private int simulateFIFO(int n, int c, int[] pages) {
        if (c <= 0) return n;
        
        Deque<Integer> queue = new LinkedList<>();
        HashSet<Integer> set = new HashSet<>();
        int faults = 0;
        
        for (int i = 0; i < n; i++) {
            int page = pages[i];
            
            if (!set.contains(page)) {
                faults++;
                
                if (queue.size() == c) {
                    int oldest = queue.removeFirst();
                    set.remove(oldest);
                }
                
                queue.addLast(page);
                set.add(page);
            }
        }
        
        return faults;
    }
    
    /**
     * Simulate Optimal (clairvoyant) for comparison
     * Replaces page that won't be used for longest time
     */
    private int simulateOptimal(int n, int c, int[] pages) {
        if (c <= 0) return n;
        
        HashSet<Integer> cache = new HashSet<>();
        int faults = 0;
        
        for (int i = 0; i < n; i++) {
            int page = pages[i];
            
            if (!cache.contains(page)) {
                faults++;
                
                if (cache.size() == c) {
                    // Find page that won't be used for longest time
                    int farthest = -1;
                    int pageToRemove = -1;
                    
                    for (int p : cache) {
                        int nextUse = Integer.MAX_VALUE;
                        
                        // Find next use of this page
                        for (int j = i + 1; j < n; j++) {
                            if (pages[j] == p) {
                                nextUse = j;
                                break;
                            }
                        }
                        
                        if (nextUse > farthest) {
                            farthest = nextUse;
                            pageToRemove = p;
                        }
                    }
                    
                    cache.remove(pageToRemove);
                }
                
                cache.add(page);
            }
        }
        
        return faults;
    }
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        LRU solver = new LRU();
        
        Object[][] testCases = {
            // {pages, capacity, expected faults}
            {new int[]{5, 0, 1, 3, 2, 4, 1, 0, 5}, 4, 8},
            {new int[]{7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2}, 4, 6},
            {new int[]{1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5}, 3, 10},
            {new int[]{1, 2, 3, 4, 5, 3, 4, 1, 6, 7, 8, 7, 8, 9, 7, 8, 9, 5, 4, 5, 3, 4, 7}, 5, 14},
            {new int[]{1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5}, 4, 8},
            {new int[]{1, 2, 3, 4, 5, 6, 1, 2, 3, 4, 5, 6}, 2, 12}, // All faults
            {new int[]{1, 1, 1, 1, 1}, 3, 1}, // All hits after first
            {new int[]{}, 3, 0}, // Empty sequence
            {new int[]{1, 2, 3}, 0, 3}, // No cache
            {new int[]{1, 2, 3, 4, 5}, 5, 5}, // Exactly fits
        };
        
        System.out.println("=== Test Cases for LRU Page Replacement ===");
        System.out.printf("%-30s %-10s %-15s %-15s %s\n", 
            "Pages", "Capacity", "Expected", "Got", "Status");
        System.out.println("-".repeat(90));
        
        for (Object[] test : testCases) {
            int[] pages = (int[]) test[0];
            int capacity = (int) test[1];
            int expected = (int) test[2];
            
            int result = solver.pageFaults(pages.length, capacity, pages);
            boolean passed = (result == expected);
            
            String pagesStr = arrayToString(pages);
            System.out.printf("%-30s %-10d %-15d %-15d %s\n", 
                pagesStr, capacity, expected, result, passed ? "✓" : "✗");
        }
    }
    
    private static String arrayToString(int[] arr) {
        if (arr == null) return "null";
        if (arr.length == 0) return "[]";
        if (arr.length > 8) {
            return "[" + arr[0] + ",...," + arr[arr.length-1] + "](" + arr.length + ")";
        }
        
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int c = sc.nextInt();
            int[] pages = new int[n];
            
            for (int i = 0; i < n; i++) {
                pages[i] = sc.nextInt();
            }
            
            LRU solver = new LRU();
            int result = solver.pageFaults(n, c, pages);
            System.out.println(result);
            
            // Uncomment for visualization
            // solver.visualizeLRU(n, c, pages);
            
            // Alternative implementations
            // int result2 = solver.pageFaultsCustom(n, c, pages);
            // System.out.println("Custom LRU: " + result2);
            
            // int result3 = solver.pageFaultsDeque(n, c, pages);
            // System.out.println("Deque LRU: " + result3);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}