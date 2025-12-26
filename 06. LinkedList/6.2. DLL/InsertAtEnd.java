/**
 * Insert at End in Doubly Linked List
 * 
 * This class demonstrates insertion at the end of a doubly linked list.
 * Inserting at the end is a common operation that requires special attention
 * to maintain proper bidirectional links.
 * 
 * Key Concepts:
 * - Handling empty list (becomes new head)
 * - Maintaining both next and prev pointers
 * - Efficiency considerations with/without tail pointer
 * - Edge cases and error handling
 */

public class InsertAtEnd {
    
    /**
     * INSERT AT END (Basic version)
     * 
     * Algorithm:
     * 1. Create new node with given value
     * 2. If list is empty (head is null), new node becomes head
     * 3. Otherwise, traverse to last node
     * 4. Link last node to new node (last.next = newNode)
     * 5. Link new node to last node (newNode.prev = last)
     * 6. Return head (unchanged unless list was empty)
     * 
     * @param head Head node of the doubly linked list
     * @param val Value to insert at end
     * @return Head of the list (may be new head if list was empty)
     * 
     * Time Complexity: O(n) - Need to traverse entire list
     * Space Complexity: O(1) - Only creates one new node
     * 
     * Example:
     * Before: null ← [10] ⇄ [20] → null, insert 30 at end
     * After: null ← [10] ⇄ [20] ⇄ [30] → null
     */
    public static DNode insertAtEnd(DNode head, int val) {
        DNode newNode = new DNode(val);
        
        // Special case: empty list
        if (head == null) {
            return newNode;
        }
        
        // Traverse to last node
        DNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        
        // Link new node at end
        current.next = newNode;
        newNode.prev = current;
        
        return head;
    }
    
    /**
     * INSERT AT END with tail pointer (More efficient)
     * 
     * If we maintain a tail pointer, insertion at end becomes O(1)
     * 
     * @param head Head node of list (may be null)
     * @param tail Tail node of list (may be null)
     * @param val Value to insert
     * @return New head and tail in an array [head, tail]
     */
    public static DNode[] insertAtEndWithTail(DNode head, DNode tail, int val) {
        DNode newNode = new DNode(val);
        
        // Special case: empty list
        if (head == null) {
            head = newNode;
            tail = newNode;
        } else {
            // Link new node after current tail
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode; // Update tail pointer
        }
        
        return new DNode[]{head, tail};
    }
    
    /**
     * INSERT AT END using getTail helper
     * 
     * @param head Head node of list
     * @param val Value to insert
     * @return Head of modified list
     */
    public static DNode insertAtEndUsingGetTail(DNode head, int val) {
        DNode newNode = new DNode(val);
        
        if (head == null) {
            return newNode;
        }
        
        DNode tail = getTail(head);
        tail.next = newNode;
        newNode.prev = tail;
        
        return head;
    }
    
    /**
     * Helper method to get tail node
     */
    private static DNode getTail(DNode head) {
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
     * INSERT MULTIPLE VALUES at end
     * 
     * @param head Head node of list
     * @param values Array of values to insert
     * @return Head of modified list
     */
    public static DNode insertMultipleAtEnd(DNode head, int[] values) {
        if (values == null || values.length == 0) {
            return head;
        }
        
        // Get tail once to avoid repeated traversal
        DNode tail = getTail(head);
        
        // If list was empty, first value becomes head
        if (head == null) {
            head = new DNode(values[0]);
            tail = head;
            
            // Insert remaining values
            for (int i = 1; i < values.length; i++) {
                DNode newNode = new DNode(values[i]);
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
        } else {
            // List was not empty, insert all values after tail
            for (int val : values) {
                DNode newNode = new DNode(val);
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
        }
        
        return head;
    }
    
    /**
     * INSERT AT END in circular doubly linked list
     * 
     * @param head Head node of circular list
     * @param val Value to insert
     * @return Head of modified circular list
     */
    public static DNode insertAtEndCircular(DNode head, int val) {
        DNode newNode = new DNode(val);
        
        // Special case: empty list
        if (head == null) {
            newNode.next = newNode;
            newNode.prev = newNode;
            return newNode;
        }
        
        // Get tail (head.prev in circular list)
        DNode tail = head.prev;
        
        // Insert new node between tail and head
        tail.next = newNode;
        newNode.prev = tail;
        newNode.next = head;
        head.prev = newNode;
        
        return head;
    }
    
    /**
     * INSERT AT END and return both head and tail
     * (Useful when caller needs both references)
     * 
     * @param head Head node
     * @param val Value to insert
     * @return Array containing [newHead, newTail]
     */
    public static DNode[] insertAtEndReturnBoth(DNode head, int val) {
        DNode newNode = new DNode(val);
        
        if (head == null) {
            return new DNode[]{newNode, newNode};
        }
        
        DNode tail = getTail(head);
        tail.next = newNode;
        newNode.prev = tail;
        
        return new DNode[]{head, newNode};
    }
    
    /**
     * INSERT AT END with validation
     * 
     * @param head Head node
     * @param val Value to insert
     * @return Head of modified list
     * @throws IllegalArgumentException if validation fails
     */
    public static DNode insertAtEndValidated(DNode head, int val) {
        // Validate input list before modification
        if (!validateList(head)) {
            throw new IllegalArgumentException("Input list is malformed");
        }
        
        DNode newNode = new DNode(val);
        
        if (head == null) {
            return newNode;
        }
        
        DNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        
        current.next = newNode;
        newNode.prev = current;
        
        // Validate after modification
        if (!validateList(head)) {
            throw new IllegalStateException("List became malformed after insertion");
        }
        
        return head;
    }
    
    /**
     * Validate doubly linked list structure
     */
    private static boolean validateList(DNode head) {
        if (head == null) {
            return true;
        }
        
        DNode current = head;
        DNode prev = null;
        
        while (current != null) {
            if (current.prev != prev) {
                return false;
            }
            prev = current;
            current = current.next;
        }
        
        return true;
    }
    
    /**
     * Main method with comprehensive testing
     */
    public static void main(String[] args) {
        System.out.println("=== Insert at End in Doubly Linked List ===\n");
        
        // Test 1: Basic insertion at end
        System.out.println("1. Basic Insertion at End:");
        DNode list1 = null;
        System.out.print("Start: ");
        Intro.printForward(list1);
        
        list1 = insertAtEnd(list1, 10);
        System.out.print("Insert 10 at end: ");
        Intro.printForward(list1);
        
        list1 = insertAtEnd(list1, 20);
        System.out.print("Insert 20 at end: ");
        Intro.printForward(list1);
        
        list1 = insertAtEnd(list1, 30);
        System.out.print("Insert 30 at end: ");
        Intro.printForward(list1);
        
        // Verify backward links
        DNode tail1 = Intro.getTail(list1);
        System.out.print("Backward verification: ");
        Intro.printBackward(tail1);
        
        // Test 2: Insert at end with tail pointer (more efficient)
        System.out.println("\n2. Insert with Tail Pointer (O(1)):");
        DNode head2 = null;
        DNode tail2 = null;
        
        DNode[] result = insertAtEndWithTail(head2, tail2, 100);
        head2 = result[0];
        tail2 = result[1];
        System.out.print("Insert 100: ");
        Intro.printForward(head2);
        System.out.println("Head: " + head2.val + ", Tail: " + tail2.val);
        
        result = insertAtEndWithTail(head2, tail2, 200);
        head2 = result[0];
        tail2 = result[1];
        System.out.print("Insert 200: ");
        Intro.printForward(head2);
        System.out.println("Head: " + head2.val + ", Tail: " + tail2.val);
        
        result = insertAtEndWithTail(head2, tail2, 300);
        head2 = result[0];
        tail2 = result[1];
        System.out.print("Insert 300: ");
        Intro.printForward(head2);
        System.out.println("Head: " + head2.val + ", Tail: " + tail2.val);
        
        // Test 3: Insert multiple values
        System.out.println("\n3. Insert Multiple Values:");
        DNode list3 = Intro.buildFromArray(new int[]{1, 2});
        System.out.print("Original: ");
        Intro.printForward(list3);
        
        list3 = insertMultipleAtEnd(list3, new int[]{3, 4, 5});
        System.out.print("After inserting [3,4,5]: ");
        Intro.printForward(list3);
        
        // Test 4: Edge cases
        System.out.println("\n4. Edge Cases:");
        
        // Empty list
        DNode empty = null;
        empty = insertAtEnd(empty, 999);
        System.out.print("Insert into empty list: ");
        Intro.printForward(empty);
        
        // Single element
        DNode single = new DNode(5);
        single = insertAtEnd(single, 6);
        System.out.print("Insert into single element list: ");
        Intro.printForward(single);
        
        // Test 5: Circular doubly linked list insertion
        System.out.println("\n5. Circular Doubly Linked List:");
        DNode circular = null;
        circular = insertAtEndCircular(circular, 1);
        System.out.println("Insert 1 into empty circular list");
        System.out.println("Circular properties: head=" + circular.val + 
                         ", head.prev=" + circular.prev.val + 
                         ", head.next=" + circular.next.val);
        
        circular = insertAtEndCircular(circular, 2);
        circular = insertAtEndCircular(circular, 3);
        System.out.println("After inserting 2 and 3");
        System.out.println("Circular traversal (3 nodes):");
        DNode current = circular;
        for (int i = 0; i < 6; i++) { // Go around twice
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println("... (infinite circular)");
        
        // Test 6: Performance comparison
        System.out.println("\n6. Performance Comparison:");
        int size = 10000;
        
        // Method 1: Basic O(n) each insertion
        DNode perfList1 = null;
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            perfList1 = insertAtEnd(perfList1, i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Basic method (" + size + " insertions): " + 
                          (end - start) + " ms");
        
        // Method 2: With tail pointer O(1) each insertion
        DNode perfHead2 = null;
        DNode perfTail2 = null;
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            DNode[] res = insertAtEndWithTail(perfHead2, perfTail2, i);
            perfHead2 = res[0];
            perfTail2 = res[1];
        }
        end = System.currentTimeMillis();
        System.out.println("With tail pointer (" + size + " insertions): " + 
                          (end - start) + " ms");
        
        // Test 7: Validation
        System.out.println("\n7. Validation:");
        DNode validList = Intro.buildFromArray(new int[]{1, 2, 3});
        try {
            validList = insertAtEndValidated(validList, 4);
            System.out.println("Valid insertion successful");
            System.out.print("List after insertion: ");
            Intro.printForward(validList);
        } catch (Exception e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
        
        // Test malformed list
        DNode malformed = new DNode(1);
        malformed.next = new DNode(2);
        // Forgot to set backward link
        try {
            malformed = insertAtEndValidated(malformed, 3);
        } catch (IllegalArgumentException e) {
            System.out.println("Correctly caught malformed list: " + e.getMessage());
        }
        
        // Test 8: Real-world scenario - Building a playlist
        System.out.println("\n8. Real-World Scenario - Music Playlist:");
        System.out.println("Building a playlist by adding songs to end:");
        
        DNode playlist = null;
        playlist = insertAtEnd(playlist, 101); // Song ID 101
        playlist = insertAtEnd(playlist, 102); // Song ID 102
        playlist = insertAtEnd(playlist, 103); // Song ID 103
        playlist = insertAtEnd(playlist, 104); // Song ID 104
        
        System.out.print("Playlist order: ");
        Intro.printForward(playlist);
        
        // Navigate playlist
        DNode currentSong = playlist;
        System.out.println("\nNavigation simulation:");
        System.out.println("Current song: " + currentSong.val);
        System.out.println("Next song: " + (currentSong.next != null ? currentSong.next.val : "End"));
        System.out.println("Previous song: " + (currentSong.prev != null ? currentSong.prev.val : "Beginning"));
        
        // Add more songs
        playlist = insertAtEnd(playlist, 105);
        playlist = insertAtEnd(playlist, 106);
        System.out.print("\nAfter adding more songs: ");
        Intro.printForward(playlist);
        
        // Test 9: Memory and pointer verification
        System.out.println("\n9. Memory and Pointer Verification:");
        DNode testList = Intro.buildFromArray(new int[]{10, 20});
        System.out.print("Original: ");
        Intro.printForward(testList);
        
        testList = insertAtEnd(testList, 30);
        System.out.print("After inserting 30 at end: ");
        Intro.printForward(testList);
        
        // Verify all pointers
        DNode node1 = testList;
        DNode node2 = testList.next;
        DNode node3 = testList.next.next;
        
        System.out.println("\nPointer verification:");
        System.out.println("Node1 (" + node1.val + "): prev=" + 
                          (node1.prev == null ? "null" : node1.prev.val) + 
                          ", next=" + (node1.next == null ? "null" : node1.next.val));
        System.out.println("Node2 (" + node2.val + "): prev=" + 
                          (node2.prev == null ? "null" : node2.prev.val) + 
                          ", next=" + (node2.next == null ? "null" : node2.next.val));
        System.out.println("Node3 (" + node3.val + "): prev=" + 
                          (node3.prev == null ? "null" : node3.prev.val) + 
                          ", next=" + (node3.next == null ? "null" : node3.next.val));
    }
    
    /**
     * Complexity Analysis:
     * 
     * Method                    Time Complexity   Space Complexity   Notes
     * Basic insertAtEnd         O(n) per insert   O(1)               Need to traverse each time
     * With tail pointer         O(1) per insert   O(1)               Maintain tail reference
     * Multiple insert           O(n + m)          O(m)               n = initial length, m = values to insert
     * Circular insert           O(n) or O(1)      O(1)               Depends on implementation
     * 
     * Optimization Strategies:
     * 1. Maintain tail pointer for O(1) end insertion
     * 2. Batch insert multiple values to reduce traversal
     * 3. Consider circular lists if end insertion is frequent
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. FORGETTING BOTH POINTERS:
     *    ❌ current.next = newNode; // Missing newNode.prev = current
     *    
     * 2. NOT UPDATING TAIL:
     *    If maintaining tail pointer, must update it
     *    
     * 3. NULL POINTERS:
     *    Not checking if head is null
     *    
     * 4. MEMORY LEAKS:
     *    In C/C++, need to manage memory properly
     *    
     * 5. CIRCULAR REFERENCES:
     *    Accidentally creating cycles in non-circular lists
     */
    
    /**
     * Best Practices:
     * 
     * 1. ALWAYS UPDATE BOTH DIRECTIONS: next AND prev
     * 2. USE HELPER METHODS: getTail, validateList
     * 3. CONSIDER TAIL POINTER: If frequent end insertions
     * 4. DEFENSIVE PROGRAMMING: Validate input and state
     * 5. DOCUMENT ASSUMPTIONS: Circular vs linear, null handling
     */
}