/**
 * DNode - Doubly Linked List Node
 * 
 * A DNode represents a single node in a doubly linked list.
 * Each node contains:
 * 1. Data (value) - stores the actual data
 * 2. Next pointer - reference to the next node in the list
 * 3. Previous pointer - reference to the previous node in the list
 * 
 * Characteristics of Doubly Linked Lists:
 * - Bidirectional traversal (forward and backward)
 * - Each node has references to both next and previous nodes
 * - More memory overhead (extra pointer per node)
 * - More complex operations but more functionality
 * 
 * Applications:
 * - Browser history (back/forward navigation)
 * - Music playlists (next/previous song)
 * - Undo/redo functionality
 * - LRU Cache implementation
 */

public class DNode {
    // The data stored in this node
    int val;
    
    // Reference to the previous node in the list
    DNode prev;
    
    // Reference to the next node in the list
    DNode next;
    
    /**
     * Constructor to create a new DNode
     * 
     * @param val The value to store in this node
     * 
     * Example:
     * DNode node = new DNode(5);
     * Creates: null ← [5] → null
     */
    DNode(int val) {
        this.val = val;
        this.prev = null;  // Initially points to nothing
        this.next = null;  // Initially points to nothing
    }
    
    /**
     * Alternative constructor that allows setting both pointers
     * 
     * @param val The value to store
     * @param prev Reference to previous node
     * @param next Reference to next node
     * 
     * Example:
     * DNode second = new DNode(2, null, null);
     * DNode first = new DNode(1, null, second);
     * second.prev = first;
     * Creates: null ← [1] ⇄ [2] → null
     */
    DNode(int val, DNode prev, DNode next) {
        this.val = val;
        this.prev = prev;
        this.next = next;
    }
    
    /**
     * Helper method to create a doubly linked list from an array
     * 
     * @param arr Array of integers to convert to doubly linked list
     * @return Head node of the created doubly linked list
     * 
     * Time Complexity: O(n) where n is array length
     * Space Complexity: O(n) for the new nodes
     */
    public static DNode fromArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        DNode head = new DNode(arr[0]);
        DNode current = head;
        
        for (int i = 1; i < arr.length; i++) {
            DNode newNode = new DNode(arr[i]);
            current.next = newNode;
            newNode.prev = current;
            current = newNode;
        }
        
        return head;
    }
    
    /**
     * Helper method to get tail node of a doubly linked list
     * 
     * @param head Head node of the list
     * @return Tail node of the list
     */
    public static DNode getTail(DNode head) {
        if (head == null) {
            return null;
        }
        
        DNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        
        return current;
    }
    
    /**
     * Helper method to print list forward
     * 
     * @param head Head node of the list
     */
    public static void printForward(DNode head) {
        DNode current = head;
        System.out.print("Forward: null ← ");
        
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" ⇄ ");
            }
            current = current.next;
        }
        
        System.out.println(" → null");
    }
    
    /**
     * Helper method to print list backward
     * 
     * @param tail Tail node of the list
     */
    public static void printBackward(DNode tail) {
        DNode current = tail;
        System.out.print("Backward: null ← ");
        
        while (current != null) {
            System.out.print(current.val);
            if (current.prev != null) {
                System.out.print(" ⇄ ");
            }
            current = current.prev;
        }
        
        System.out.println(" → null");
    }
    
    /**
     * Helper method to visualize the doubly linked list structure
     * 
     * @param head Head node of the list
     */
    public static void visualize(DNode head) {
        if (head == null) {
            System.out.println("List: (empty)");
            return;
        }
        
        DNode current = head;
        System.out.println("\nVisual Representation:");
        System.out.println("Head: " + head.val);
        
        // Print forward chain
        System.out.print("Forward:  ");
        while (current != null) {
            if (current.prev == null) {
                System.out.print("null ← ");
            }
            System.out.print("[" + current.val + "]");
            if (current.next == null) {
                System.out.print(" → null");
            } else {
                System.out.print(" ⇄ ");
            }
            current = current.next;
        }
        
        // Reset to tail and print backward
        DNode tail = getTail(head);
        System.out.print("\nBackward: ");
        current = tail;
        while (current != null) {
            if (current.next == null) {
                System.out.print("null ← ");
            }
            System.out.print("[" + current.val + "]");
            if (current.prev == null) {
                System.out.print(" → null");
            } else {
                System.out.print(" ⇄ ");
            }
            current = current.prev;
        }
        System.out.println("\nTail: " + tail.val);
    }
    
    /**
     * Compare Doubly vs Singly Linked Lists
     */
    public static void comparisonTable() {
        System.out.println("\n=== Doubly vs Singly Linked List Comparison ===");
        System.out.println("+----------------------+------------------------+");
        System.out.println("| Feature              | Doubly   | Singly     |");
        System.out.println("+----------------------+------------------------+");
        System.out.println("| Memory per Node      | 3 fields | 2 fields   |");
        System.out.println("| Traversal Direction  | Both ways| Forward only|");
        System.out.println("| Delete node          | O(1)*    | O(n)       |");
        System.out.println("| Insert before        | O(1)*    | O(n)       |");
        System.out.println("| Implementation       | Complex  | Simpler    |");
        System.out.println("| Common Use Cases     | Browser  | Simple     |");
        System.out.println("|                      | history, | lists,     |");
        System.out.println("|                      | LRU cache| stacks     |");
        System.out.println("+----------------------+------------------------+");
        System.out.println("* Given node reference, otherwise O(n) to find");
    }
    
    /**
     * Example usage and testing
     */
    public static void main(String[] args) {
        System.out.println("=== Testing DNode (Doubly Linked List Node) ===\n");
        
        // Test 1: Basic node creation
        System.out.println("1. Basic Node Creation:");
        DNode node1 = new DNode(10);
        System.out.println("Single node value: " + node1.val);
        System.out.println("Single node prev: " + node1.prev);
        System.out.println("Single node next: " + node1.next);
        
        // Test 2: Creating a small doubly linked list manually
        System.out.println("\n2. Manual Doubly Linked List Creation:");
        DNode first = new DNode(10);
        DNode second = new DNode(20);
        DNode third = new DNode(30);
        
        // Link nodes forward
        first.next = second;
        second.next = third;
        
        // Link nodes backward
        third.prev = second;
        second.prev = first;
        
        System.out.print("Manual list: ");
        printForward(first);
        
        // Test 3: Creating list from array
        System.out.println("\n3. Creating List from Array:");
        int[] arr = {1, 2, 3, 4, 5};
        DNode listFromArray = fromArray(arr);
        System.out.print("From array: ");
        printForward(listFromArray);
        
        // Test 4: Getting and using tail
        System.out.println("\n4. Working with Tail:");
        DNode tail = getTail(listFromArray);
        System.out.println("Tail value: " + tail.val);
        System.out.print("Print backward from tail: ");
        printBackward(tail);
        
        // Test 5: Visualization
        System.out.println("\n5. Visualization:");
        visualize(listFromArray);
        
        // Test 6: Comparison
        comparisonTable();
        
        // Test 7: Memory layout understanding
        System.out.println("\n7. Memory Layout:");
        System.out.println("When we create: DNode node = new DNode(5);");
        System.out.println("\nMemory representation:");
        System.out.println("Stack:             Heap:");
        System.out.println("node ────────→     [val: 5]");
        System.out.println("                  [prev: null]");
        System.out.println("                  [next: null]");
        
        System.out.println("\nAfter linking two nodes:");
        System.out.println("DNode node2 = new DNode(10);");
        System.out.println("node.next = node2;");
        System.out.println("node2.prev = node;");
        System.out.println("\nMemory representation:");
        System.out.println("Stack:             Heap:");
        System.out.println("node ────────→     [val: 5]");
        System.out.println("                  [prev: null]");
        System.out.println("                  [next: ──┐]");
        System.out.println("                           ↓");
        System.out.println("node2 ───────→     [val: 10]");
        System.out.println("                  [prev: ──┐]");
        System.out.println("                  [next: null]");
        System.out.println("                           ↑");
        System.out.println("                  (points back to first node)");
        
        // Test 8: Edge cases
        System.out.println("\n8. Edge Cases:");
        System.out.print("Null list: ");
        printForward(null);
        
        System.out.print("Empty array to list: ");
        printForward(fromArray(new int[0]));
        
        // Test 9: Real-world analogy
        System.out.println("\n9. Real-World Analogy:");
        System.out.println("Doubly linked list is like a web browser:");
        System.out.println("- Each page is a node");
        System.out.println("- 'next' is the forward button");
        System.out.println("- 'prev' is the back button");
        System.out.println("- You can navigate in both directions");
    }
    
    /**
     * Advantages of Doubly Linked Lists:
     * 1. Bidirectional traversal - can traverse both forward and backward
     * 2. Delete node in O(1) if node reference is given (no need to find previous)
     * 3. Insert before a node in O(1) if node reference is given
     * 4. Better for certain algorithms (like LRU cache)
     * 
     * Disadvantages:
     * 1. Extra memory for previous pointer
     * 2. More complex implementation
     * 3. More pointer operations (higher chance of bugs)
     */
}