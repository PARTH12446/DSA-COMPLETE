/**
 * Introduction to Doubly Linked Lists - Basic Operations
 * 
 * This class provides fundamental operations for working with doubly linked lists.
 * Doubly linked lists extend the functionality of singly linked lists by allowing
 * bidirectional traversal and more efficient certain operations.
 * 
 * Key Concepts Covered:
 * 1. Building a doubly linked list from an array
 * 2. Printing in forward and backward directions
 * 3. Understanding bidirectional linking
 * 4. Memory management in doubly linked lists
 * 
 * Why Doubly Linked Lists?
 * - Can traverse in both directions
 * - Delete a node in O(1) given its reference
 * - Insert before a node in O(1) given its reference
 * - Essential for certain data structures (Deque, LRU Cache)
 */

public class Intro {
    
    /**
     * Builds a doubly linked list from an integer array
     * 
     * Algorithm:
     * 1. Handle edge case: null or empty array returns null
     * 2. Create head node from first array element
     * 3. Use current pointer to traverse while appending new nodes
     * 4. For each new node:
     *    - Set new node's prev to current node
     *    - Set current node's next to new node
     *    - Update current pointer
     * 5. Return head of the created list
     * 
     * @param arr Integer array to convert to doubly linked list
     * @return Head node of the created doubly linked list
     * 
     * Time Complexity: O(n) where n is array length
     * Space Complexity: O(n) for the new linked list nodes
     * 
     * Example:
     * Input: [1, 2, 3]
     * Output: null ← [1] ⇄ [2] ⇄ [3] → null
     */
    public static DNode buildFromArray(int[] arr) {
        // Edge case: null or empty array
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        // Create head from first element
        DNode head = new DNode(arr[0]);
        DNode current = head; // Pointer to track current node
        
        // Append remaining elements with bidirectional linking
        for (int i = 1; i < arr.length; i++) {
            DNode newNode = new DNode(arr[i]);
            
            // Link new node to current node
            current.next = newNode;
            newNode.prev = current;
            
            // Move to the new node
            current = newNode;
        }
        
        return head;
    }
    
    /**
     * Alternative: Build list using tail pointer for efficiency
     * 
     * @param arr Integer array to convert
     * @return Head node of created list
     */
    public static DNode buildFromArrayWithTail(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        DNode head = new DNode(arr[0]);
        DNode tail = head; // Maintain tail pointer
        
        for (int i = 1; i < arr.length; i++) {
            DNode newNode = new DNode(arr[i]);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        
        return head;
    }
    
    /**
     * Alternative: Build circular doubly linked list
     * 
     * @param arr Integer array to convert
     * @return Head node of circular doubly linked list
     */
    public static DNode buildCircularFromArray(int[] arr) {
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
        
        // Make it circular
        current.next = head;
        head.prev = current;
        
        return head;
    }
    
    /**
     * Prints a doubly linked list in forward direction
     * 
     * Algorithm:
     * 1. Start from head node
     * 2. While current node is not null:
     *    - Print node value
     *    - If next exists, print bidirectional arrow
     *    - Move to next node
     * 
     * @param head Head node of the doubly linked list
     * 
     * Time Complexity: O(n) where n is list length
     * Space Complexity: O(1) extra space
     * 
     * Example output: 1 <-> 2 <-> 3
     */
    public static void printForward(DNode head) {
        DNode current = head;
        
        while (current != null) {
            System.out.print(current.val);
            
            // Print bidirectional arrow if not last node
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            
            current = current.next;
        }
        
        System.out.println();
    }
    
    /**
     * Enhanced forward print with null indicators
     * 
     * @param head Head node of list
     */
    public static void printForwardWithNulls(DNode head) {
        DNode current = head;
        System.out.print("null <- ");
        
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" <-> ");
            }
            current = current.next;
        }
        
        System.out.println(" -> null");
    }
    
    /**
     * Prints a doubly linked list in backward direction
     * 
     * @param tail Tail node of the doubly linked list
     * 
     * Time Complexity: O(n) where n is list length
     * Space Complexity: O(1) extra space
     * 
     * Example output: 3 <-> 2 <-> 1
     */
    public static void printBackward(DNode tail) {
        DNode current = tail;
        
        while (current != null) {
            System.out.print(current.val);
            
            // Print bidirectional arrow if not first node
            if (current.prev != null) {
                System.out.print(" <-> ");
            }
            
            current = current.prev;
        }
        
        System.out.println();
    }
    
    /**
     * Enhanced backward print with null indicators
     * 
     * @param tail Tail node of list
     */
    public static void printBackwardWithNulls(DNode tail) {
        DNode current = tail;
        System.out.print("null <- ");
        
        while (current != null) {
            System.out.print(current.val);
            if (current.prev != null) {
                System.out.print(" <-> ");
            }
            current = current.prev;
        }
        
        System.out.println(" -> null");
    }
    
    /**
     * Gets the tail node of a doubly linked list
     * 
     * @param head Head node of the list
     * @return Tail node, or null if list is empty
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
     * Gets the length of a doubly linked list
     * 
     * @param head Head node of the list
     * @return Number of nodes in list
     */
    public static int getLength(DNode head) {
        int length = 0;
        DNode current = head;
        
        while (current != null) {
            length++;
            current = current.next;
        }
        
        return length;
    }
    
    /**
     * Search for a value in doubly linked list (forward)
     * 
     * @param head Head node of list
     * @param target Value to search for
     * @return Node containing target, or null if not found
     */
    public static DNode searchForward(DNode head, int target) {
        DNode current = head;
        
        while (current != null) {
            if (current.val == target) {
                return current;
            }
            current = current.next;
        }
        
        return null;
    }
    
    /**
     * Search for a value in doubly linked list (backward)
     * 
     * @param tail Tail node of list
     * @param target Value to search for
     * @return Node containing target, or null if not found
     */
    public static DNode searchBackward(DNode tail, int target) {
        DNode current = tail;
        
        while (current != null) {
            if (current.val == target) {
                return current;
            }
            current = current.prev;
        }
        
        return null;
    }
    
    /**
     * Validates that a doubly linked list is properly linked
     * 
     * @param head Head node of list
     * @return true if list is properly linked, false otherwise
     */
    public static boolean validateList(DNode head) {
        if (head == null) {
            return true;
        }
        
        DNode current = head;
        DNode prev = null;
        
        // Check forward links
        while (current != null) {
            if (current.prev != prev) {
                System.out.println("Validation failed: Node " + current.val + 
                                 " has incorrect prev pointer");
                return false;
            }
            prev = current;
            current = current.next;
        }
        
        return true;
    }
    
    /**
     * Main method with comprehensive examples
     */
    public static void main(String[] args) {
        System.out.println("=== Introduction to Doubly Linked Lists ===\n");
        
        // Example 1: Basic list creation and printing
        System.out.println("1. Basic Example:");
        int[] arr1 = {1, 2, 3, 4, 5};
        DNode list1 = buildFromArray(arr1);
        
        System.out.print("Forward: ");
        printForward(list1);
        
        DNode tail1 = getTail(list1);
        System.out.print("Backward from tail: ");
        printBackward(tail1);
        
        System.out.print("Forward with nulls: ");
        printForwardWithNulls(list1);
        
        System.out.print("Backward with nulls: ");
        printBackwardWithNulls(tail1);
        
        // Example 2: Empty and single element lists
        System.out.println("\n2. Edge Cases:");
        System.out.print("Empty array: ");
        printForward(buildFromArray(new int[0]));
        
        System.out.print("Single element: ");
        DNode single = buildFromArray(new int[]{10});
        printForward(single);
        
        DNode singleTail = getTail(single);
        System.out.print("Single element backward: ");
        printBackward(singleTail);
        
        // Example 3: Length calculation
        System.out.println("\n3. List Properties:");
        System.out.println("Length of list1: " + getLength(list1) + " (expected: 5)");
        System.out.println("Length of empty list: " + getLength(null) + " (expected: 0)");
        System.out.println("Length of single element: " + getLength(single) + " (expected: 1)");
        
        // Example 4: Searching
        System.out.println("\n4. Searching:");
        System.out.println("Search forward for 3: " + 
                          (searchForward(list1, 3) != null ? "Found" : "Not found"));
        System.out.println("Search forward for 10: " + 
                          (searchForward(list1, 10) != null ? "Found" : "Not found"));
        
        System.out.println("Search backward for 3: " + 
                          (searchBackward(tail1, 3) != null ? "Found" : "Not found"));
        System.out.println("Search backward for 10: " + 
                          (searchBackward(tail1, 10) != null ? "Found" : "Not found"));
        
        // Example 5: Validation
        System.out.println("\n5. List Validation:");
        System.out.println("Is list1 properly linked? " + validateList(list1));
        
        // Create a malformed list for testing
        DNode nodeA = new DNode(1);
        DNode nodeB = new DNode(2);
        nodeA.next = nodeB;
        // Forgot to set nodeB.prev = nodeA
        System.out.println("Is malformed list properly linked? " + validateList(nodeA));
        
        // Example 6: Circular doubly linked list
        System.out.println("\n6. Circular Doubly Linked List:");
        DNode circularList = buildCircularFromArray(new int[]{1, 2, 3});
        System.out.println("Circular list created (can't print normally without infinite loop)");
        System.out.println("Circular list properties:");
        System.out.println("- head.prev = tail: " + (circularList.prev.val == 3));
        System.out.println("- tail.next = head: " + (circularList.prev.next.val == 1));
        
        // Example 7: Traversal demonstration
        System.out.println("\n7. Traversal Demonstration:");
        DNode traversalList = buildFromArray(new int[]{10, 20, 30, 40, 50});
        System.out.print("Forward traversal: ");
        DNode current = traversalList;
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        
        System.out.print("\nBackward traversal: ");
        current = getTail(traversalList);
        while (current != null) {
            System.out.print(current.val + " ");
            current = current.prev;
        }
        System.out.println();
        
        // Example 8: Memory and pointer demonstration
        System.out.println("\n8. Pointer Relationships:");
        DNode demoList = buildFromArray(new int[]{100, 200, 300});
        DNode first = demoList;
        DNode second = demoList.next;
        DNode third = demoList.next.next;
        
        System.out.println("First node:");
        System.out.println("  Value: " + first.val);
        System.out.println("  Prev: " + (first.prev == null ? "null" : first.prev.val));
        System.out.println("  Next: " + (first.next == null ? "null" : first.next.val));
        
        System.out.println("\nSecond node:");
        System.out.println("  Value: " + second.val);
        System.out.println("  Prev: " + (second.prev == null ? "null" : second.prev.val));
        System.out.println("  Next: " + (second.next == null ? "null" : second.next.val));
        
        System.out.println("\nThird node:");
        System.out.println("  Value: " + third.val);
        System.out.println("  Prev: " + (third.prev == null ? "null" : third.prev.val));
        System.out.println("  Next: " + (third.next == null ? "null" : third.next.val));
        
        // Example 9: Performance demonstration
        System.out.println("\n9. Performance Demonstration:");
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i;
        }
        
        long startTime = System.currentTimeMillis();
        DNode largeList = buildFromArray(largeArray);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Built doubly linked list of 10,000 nodes in " + 
                          (endTime - startTime) + " ms");
        System.out.println("Length of large list: " + getLength(largeList));
        
        // Measure search time
        startTime = System.currentTimeMillis();
        DNode found = searchForward(largeList, 5000);
        endTime = System.currentTimeMillis();
        System.out.println("Search forward for middle element: " + (endTime - startTime) + " ms");
        
        DNode largeTail = getTail(largeList);
        startTime = System.currentTimeMillis();
        found = searchBackward(largeTail, 5000);
        endTime = System.currentTimeMillis();
        System.out.println("Search backward for middle element: " + (endTime - startTime) + " ms");
        
        // Example 10: Real-world analogy
        System.out.println("\n10. Real-World Applications:");
        System.out.println("a) Browser History:");
        System.out.println("   - Each page visit creates a new node");
        System.out.println("   - 'next' is forward navigation");
        System.out.println("   - 'prev' is back navigation");
        System.out.println("   - Allows seamless back/forward browsing");
        
        System.out.println("\nb) Music Playlist:");
        System.out.println("   - Each song is a node");
        System.out.println("   - 'next' plays next song");
        System.out.println("   - 'prev' plays previous song");
        System.out.println("   - Can navigate playlist in both directions");
        
        System.out.println("\nc) Undo/Redo Functionality:");
        System.out.println("   - Each action creates a node");
        System.out.println("   - 'prev' is undo (go back to previous state)");
        System.out.println("   - 'next' is redo (go forward to next state)");
        System.out.println("   - Essential for text editors, graphic software");
    }
    
    /**
     * Common Mistakes with Doubly Linked Lists:
     * 
     * 1. FORGETTING TO UPDATE BOTH POINTERS:
     *    When inserting or deleting, must update both next AND prev pointers
     *    
     * 2. CIRCULAR REFERENCES:
     *    Accidentally creating cycles when not intended
     *    
     * 3. NULL POINTER EXCEPTIONS:
     *    Not checking for null when accessing prev/next
     *    
     * 4. MEMORY LEAKS:
     *    In languages without garbage collection, need to free memory manually
     *    
     * 5. INCONSISTENT STATE:
     *    List becomes inconsistent if operations are interrupted
     */
    
    /**
     * Best Practices for Doubly Linked Lists:
     * 
     * 1. ALWAYS UPDATE BOTH POINTERS: When modifying links
     * 2. USE HELPER METHODS: For common operations (getTail, validate)
     * 3. DEFENSIVE PROGRAMMING: Check for null and validate state
     * 4. ENCAPSULATE OPERATIONS: Don't expose node pointers directly
     * 5. DOCUMENT INVARIANTS: What must always be true for the list
     */
}