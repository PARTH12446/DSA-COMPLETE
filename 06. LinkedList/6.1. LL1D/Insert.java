/**
 * Insert Operations in Singly Linked List
 * 
 * This class demonstrates various insertion operations in a singly linked list.
 * Insertion is a fundamental operation that varies in complexity based on position.
 * 
 * Types of Insertions Covered:
 * 1. Insert at Head (Beginning) - O(1)
 * 2. Insert at Tail (End) - O(n)
 * 3. Insert at Position (Anywhere) - O(n) worst case
 * 
 * Key Concepts:
 * - Node creation and linking
 * - Edge case handling (empty list, invalid positions)
 * - Pointer manipulation
 */

public class Insert {
    
    /**
     * INSERT AT HEAD (Beginning of list)
     * 
     * Algorithm:
     * 1. Create new node with given value
     * 2. Set new node's next to current head
     * 3. Return new node as new head
     * 
     * @param head Current head of the list (may be null)
     * @param val Value to insert
     * @return New head of the list
     * 
     * Time Complexity: O(1) - Constant time operation
     * Space Complexity: O(1) - Only creates one new node
     * 
     * Example:
     * Before: 2 -> 3 -> null, insert 1 at head
     * After: 1 -> 2 -> 3 -> null
     */
    public static ListNode insertAtHead(ListNode head, int val) {
        // Create new node
        ListNode newNode = new ListNode(val);
        
        // Link new node to current head
        newNode.next = head;
        
        // New node becomes the head
        return newNode;
    }
    
    /**
     * INSERT AT TAIL (End of list)
     * 
     * Algorithm:
     * 1. Create new node with given value
     * 2. If list is empty (head is null), new node becomes head
     * 3. Otherwise, traverse to last node
     * 4. Set last node's next to new node
     * 5. Return head (unchanged unless list was empty)
     * 
     * @param head Current head of the list
     * @param val Value to insert
     * @return Head of the list (same if not empty)
     * 
     * Time Complexity: O(n) - Need to traverse entire list
     * Space Complexity: O(1) - Only creates one new node
     * 
     * Example:
     * Before: 1 -> 2 -> null, insert 3 at tail
     * After: 1 -> 2 -> 3 -> null
     */
    public static ListNode insertAtTail(ListNode head, int val) {
        ListNode newNode = new ListNode(val);
        
        // Special case: empty list
        if (head == null) {
            return newNode;
        }
        
        // Traverse to last node
        ListNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        
        // Attach new node at end
        current.next = newNode;
        
        return head;
    }
    
    /**
     * INSERT AT TAIL using dummy node (more elegant)
     * 
     * @param head Current head
     * @param val Value to insert
     * @return Head of list
     */
    public static ListNode insertAtTailDummy(ListNode head, int val) {
        ListNode dummy = new ListNode(-1, head); // Dummy points to head
        ListNode current = dummy;
        
        // Find last node (dummy helps handle empty list)
        while (current.next != null) {
            current = current.next;
        }
        
        // Insert new node
        current.next = new ListNode(val);
        
        return dummy.next; // Return real head
    }
    
    /**
     * INSERT AT SPECIFIC POSITION (1-based indexing)
     * 
     * Algorithm:
     * 1. Handle edge cases:
     *    - Position ≤ 1: insert at head
     *    - Empty list: insert at head
     * 2. Traverse to node before target position
     * 3. Insert new node between current and current.next
     * 4. Return head (may change if inserted at head)
     * 
     * @param head Current head of list
     * @param position Position to insert (1 = head, 2 = after head, etc.)
     * @param val Value to insert
     * @return Head of modified list
     * 
     * Time Complexity: O(n) worst case (inserting near end)
     * Space Complexity: O(1)
     * 
     * Example:
     * List: 10 -> 20 -> 30 -> null
     * insertAtPosition(head, 2, 15)
     * Result: 10 -> 15 -> 20 -> 30 -> null
     */
    public static ListNode insertAtPosition(ListNode head, int position, int val) {
        // Edge cases: insert at head
        if (position <= 1 || head == null) {
            return insertAtHead(head, val);
        }
        
        ListNode current = head;
        int index = 1;
        
        // Traverse to node before target position
        while (current != null && index < position - 1) {
            current = current.next;
            index++;
        }
        
        // If position is beyond list length, insert at end
        if (current == null) {
            return insertAtTail(head, val);
        }
        
        // Insert new node between current and current.next
        ListNode newNode = new ListNode(val);
        newNode.next = current.next;
        current.next = newNode;
        
        return head;
    }
    
    /**
     * INSERT AFTER A SPECIFIC VALUE (first occurrence)
     * 
     * @param head Current head
     * @param targetValue Value to search for
     * @param newValue Value to insert after target
     * @return Head of list
     */
    public static ListNode insertAfterValue(ListNode head, int targetValue, int newValue) {
        ListNode current = head;
        
        // Search for target value
        while (current != null && current.val != targetValue) {
            current = current.next;
        }
        
        // If target found, insert after it
        if (current != null) {
            ListNode newNode = new ListNode(newValue);
            newNode.next = current.next;
            current.next = newNode;
        }
        // If target not found, optionally insert at end or do nothing
        // Here we choose to do nothing
        
        return head;
    }
    
    /**
     * INSERT IN SORTED LIST (maintains ascending order)
     * 
     * @param head Head of sorted list
     * @param val Value to insert
     * @return Head of list (may change)
     */
    public static ListNode insertInSortedList(ListNode head, int val) {
        ListNode newNode = new ListNode(val);
        
        // Case 1: Empty list or insert at head
        if (head == null || val <= head.val) {
            newNode.next = head;
            return newNode;
        }
        
        // Case 2: Insert in middle or end
        ListNode current = head;
        while (current.next != null && current.next.val < val) {
            current = current.next;
        }
        
        newNode.next = current.next;
        current.next = newNode;
        
        return head;
    }
    
    /**
     * Main method with comprehensive testing
     */
    public static void main(String[] args) {
        System.out.println("=== Linked List Insertion Operations ===\n");
        
        // Test 1: Insert at Head
        System.out.println("1. Insert at Head:");
        ListNode list1 = null;
        System.out.print("Start: ");
        Intro.printList(list1);
        
        list1 = insertAtHead(list1, 3);
        System.out.print("Insert 3 at head: ");
        Intro.printList(list1);
        
        list1 = insertAtHead(list1, 2);
        System.out.print("Insert 2 at head: ");
        Intro.printList(list1);
        
        list1 = insertAtHead(list1, 1);
        System.out.print("Insert 1 at head: ");
        Intro.printList(list1);
        
        // Test 2: Insert at Tail
        System.out.println("\n2. Insert at Tail:");
        ListNode list2 = null;
        System.out.print("Start: ");
        Intro.printList(list2);
        
        list2 = insertAtTail(list2, 1);
        System.out.print("Insert 1 at tail: ");
        Intro.printList(list2);
        
        list2 = insertAtTail(list2, 2);
        System.out.print("Insert 2 at tail: ");
        Intro.printList(list2);
        
        list2 = insertAtTail(list2, 3);
        System.out.print("Insert 3 at tail: ");
        Intro.printList(list2);
        
        // Test 3: Insert at Position
        System.out.println("\n3. Insert at Position:");
        ListNode list3 = Intro.buildFromArray(new int[]{10, 20, 30, 40});
        System.out.print("Original: ");
        Intro.printList(list3);
        
        // Insert at position 2 (between 10 and 20)
        list3 = insertAtPosition(list3, 2, 15);
        System.out.print("Insert 15 at position 2: ");
        Intro.printList(list3);
        
        // Insert at position 1 (head)
        list3 = insertAtPosition(list3, 1, 5);
        System.out.print("Insert 5 at position 1 (head): ");
        Intro.printList(list3);
        
        // Insert at position beyond length (should go to tail)
        list3 = insertAtPosition(list3, 10, 50);
        System.out.print("Insert 50 at position 10 (beyond length): ");
        Intro.printList(list3);
        
        // Test 4: Insert after value
        System.out.println("\n4. Insert After Value:");
        ListNode list4 = Intro.buildFromArray(new int[]{1, 3, 5, 7});
        System.out.print("Original: ");
        Intro.printList(list4);
        
        list4 = insertAfterValue(list4, 3, 4);
        System.out.print("Insert 4 after 3: ");
        Intro.printList(list4);
        
        list4 = insertAfterValue(list4, 7, 8);
        System.out.print("Insert 8 after 7: ");
        Intro.printList(list4);
        
        list4 = insertAfterValue(list4, 10, 9); // 10 doesn't exist
        System.out.print("Try insert 9 after 10 (not found): ");
        Intro.printList(list4);
        
        // Test 5: Insert in sorted list
        System.out.println("\n5. Insert in Sorted List:");
        ListNode sortedList = Intro.buildFromArray(new int[]{10, 20, 40, 50});
        System.out.print("Sorted list: ");
        Intro.printList(sortedList);
        
        sortedList = insertInSortedList(sortedList, 5); // Insert at head
        System.out.print("Insert 5 (at head): ");
        Intro.printList(sortedList);
        
        sortedList = insertInSortedList(sortedList, 30); // Insert in middle
        System.out.print("Insert 30 (in middle): ");
        Intro.printList(sortedList);
        
        sortedList = insertInSortedList(sortedList, 60); // Insert at tail
        System.out.print("Insert 60 (at tail): ");
        Intro.printList(sortedList);
        
        // Test 6: Complex scenario
        System.out.println("\n6. Complex Insertion Scenario:");
        ListNode complex = null;
        System.out.print("Start: ");
        Intro.printList(complex);
        
        // Build list using various insertions
        complex = insertAtHead(complex, 100);     // 100 -> null
        complex = insertAtTail(complex, 200);     // 100 -> 200 -> null
        complex = insertAtPosition(complex, 2, 150); // 100 -> 150 -> 200 -> null
        complex = insertAtHead(complex, 50);      // 50 -> 100 -> 150 -> 200 -> null
        complex = insertAfterValue(complex, 150, 175); // 50 -> 100 -> 150 -> 175 -> 200 -> null
        
        System.out.print("Final complex list: ");
        Intro.printList(complex);
        
        // Test 7: Performance comparison
        System.out.println("\n7. Performance Analysis:");
        ListNode perfList = null;
        int size = 10000;
        
        // Measure head insertion time
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            perfList = insertAtHead(perfList, i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Insert " + size + " elements at head: " + (end - start) + " ms");
        
        // Measure tail insertion time (much slower!)
        ListNode perfList2 = null;
        start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            perfList2 = insertAtTail(perfList2, i);
        }
        end = System.currentTimeMillis();
        System.out.println("Insert " + size + " elements at tail: " + (end - start) + " ms");
    }
    
    /**
     * Insertion Complexity Summary:
     * 
     * Operation         Time Complexity   Notes
     * Insert at Head    O(1)              Always constant time
     * Insert at Tail    O(n)              Need to traverse entire list
     * Insert at Position O(n) worst       Need to traverse to position
     * Insert in Sorted  O(n)              Need to find correct position
     * 
     * Tips for Optimization:
     * 1. Maintain tail pointer for O(1) tail insertion
     * 2. Use dummy nodes to simplify edge cases
     * 3. Consider doubly linked list for bidirectional traversal
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. FORGETTING TO UPDATE HEAD:
     *    insertAtHead must return new head
     *    
     * 2. LOST NODE REFERENCES:
     *    ListNode newNode = new ListNode(val);
     *    newNode.next = current.next;
     *    current.next = newNode;
     *    // Order matters! Do step 2 before step 3
     *    
     * 3. NOT HANDLING EMPTY LIST:
     *    Always check if head == null
     *    
     * 4. OFF-BY-ONE ERRORS:
     *    Position indexing (0-based vs 1-based)
     *    Be consistent throughout your code
     */
}