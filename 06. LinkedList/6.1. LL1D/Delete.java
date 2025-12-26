/**
 * Delete Operations in Singly Linked List
 * 
 * This class demonstrates various deletion operations in a singly linked list.
 * Deletion is a critical operation that requires careful pointer manipulation
 * to maintain list integrity and avoid memory issues.
 * 
 * Types of Deletions Covered:
 * 1. Delete by value (first occurrence)
 * 2. Delete at position (0-based or 1-based)
 * 3. Delete all occurrences of a value
 * 4. Delete head/tail
 * 5. Delete with conditions
 * 
 * Key Concepts:
 * - Pointer manipulation and reassignment
 * - Memory management and garbage collection
 * - Edge case handling (empty list, single node)
 * - Maintaining list connectivity
 */

public class Delete {
    
    /**
     * DELETE BY VALUE (first occurrence)
     * 
     * Algorithm:
     * 1. Handle edge case: empty list → return null
     * 2. Special case: head contains target → return head.next
     * 3. Traverse list to find node before target
     * 4. If found, bypass target node
     * 5. Return head (may be new head if original head deleted)
     * 
     * @param head Head node of the linked list
     * @param key Value to delete (first occurrence)
     * @return Head of modified list
     * 
     * Time Complexity: O(n) worst case
     * Space Complexity: O(1)
     * 
     * Example:
     * List: 10 -> 20 -> 30 -> null
     * deleteByValue(head, 20)
     * Result: 10 -> 30 -> null
     */
    public static ListNode deleteByValue(ListNode head, int key) {
        // Case 1: Empty list
        if (head == null) {
            return null;
        }
        
        // Case 2: Head contains key
        if (head.val == key) {
            return head.next; // New head is second node (or null)
        }
        
        // Case 3: Search for node before key
        ListNode current = head;
        while (current.next != null && current.next.val != key) {
            current = current.next;
        }
        
        // Case 4: If found, bypass the key node
        if (current.next != null) {
            current.next = current.next.next;
        }
        // If not found, list remains unchanged
        
        return head;
    }
    
    /**
     * DELETE BY VALUE using dummy node (simpler edge case handling)
     * 
     * @param head Head node of list
     * @param key Value to delete
     * @return Head of modified list
     */
    public static ListNode deleteByValueDummy(ListNode head, int key) {
        ListNode dummy = new ListNode(-1, head); // Dummy node before head
        ListNode current = dummy;
        
        while (current.next != null) {
            if (current.next.val == key) {
                current.next = current.next.next;
                break; // Delete first occurrence only
            }
            current = current.next;
        }
        
        return dummy.next; // Real head (may have changed)
    }
    
    /**
     * DELETE AT POSITION (1-based indexing)
     * 
     * Algorithm:
     * 1. Handle invalid input: null head or position <= 0
     * 2. Special case: position == 1 → delete head
     * 3. Traverse to node before target position
     * 4. If position exists, bypass node at that position
     * 5. Return head (may be new head if position was 1)
     * 
     * @param head Head node of list
     * @param position Position to delete (1 = head, 2 = second node, etc.)
     * @return Head of modified list
     * 
     * Time Complexity: O(n) worst case
     * Space Complexity: O(1)
     */
    public static ListNode deleteAtPosition(ListNode head, int position) {
        // Invalid cases
        if (head == null || position <= 0) {
            return head;
        }
        
        // Delete head
        if (position == 1) {
            return head.next;
        }
        
        ListNode current = head;
        int index = 1;
        
        // Traverse to node before target position
        while (current.next != null && index < position - 1) {
            current = current.next;
            index++;
        }
        
        // If position exists, delete it
        if (current.next != null) {
            current.next = current.next.next;
        }
        // If position beyond list length, do nothing
        
        return head;
    }
    
    /**
     * DELETE HEAD
     * 
     * @param head Head node of list
     * @return New head (head.next), or null if list becomes empty
     */
    public static ListNode deleteHead(ListNode head) {
        if (head == null) {
            return null;
        }
        return head.next;
    }
    
    /**
     * DELETE TAIL
     * 
     * @param head Head node of list
     * @return Head of modified list
     */
    public static ListNode deleteTail(ListNode head) {
        // Empty list or single node
        if (head == null || head.next == null) {
            return null;
        }
        
        ListNode current = head;
        
        // Traverse to second last node
        while (current.next.next != null) {
            current = current.next;
        }
        
        // Remove last node
        current.next = null;
        
        return head;
    }
    
    /**
     * DELETE ALL OCCURRENCES of a value
     * 
     * @param head Head node of list
     * @param key Value to delete
     * @return Head of modified list
     */
    public static ListNode deleteAllOccurrences(ListNode head, int key) {
        // Use dummy node to simplify edge cases
        ListNode dummy = new ListNode(-1, head);
        ListNode current = dummy;
        
        while (current.next != null) {
            if (current.next.val == key) {
                current.next = current.next.next;
                // Don't move current yet - check new current.next
            } else {
                current = current.next;
            }
        }
        
        return dummy.next;
    }
    
    /**
     * DELETE DUPLICATES from sorted list
     * 
     * @param head Head of sorted linked list
     * @return Head of list with duplicates removed
     */
    public static ListNode deleteDuplicatesSorted(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode current = head;
        
        while (current != null && current.next != null) {
            if (current.val == current.next.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        
        return head;
    }
    
    /**
     * DELETE NTH NODE FROM END (one pass)
     * 
     * @param head Head node of list
     * @param n Position from end (1 = last node)
     * @return Head of modified list
     */
    public static ListNode deleteNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1, head);
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        // Move fast n+1 steps ahead
        for (int i = 0; i <= n; i++) {
            if (fast == null) {
                return head; // n is larger than list length
            }
            fast = fast.next;
        }
        
        // Move both until fast reaches end
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        // Delete node after slow
        slow.next = slow.next.next;
        
        return dummy.next;
    }
    
    /**
     * DELETE NODES WITH CONDITION
     * 
     * @param head Head node of list
     * @param condition Predicate to test nodes for deletion
     * @return Head of modified list
     */
    public static ListNode deleteWithCondition(ListNode head, java.util.function.Predicate<ListNode> condition) {
        ListNode dummy = new ListNode(-1, head);
        ListNode current = dummy;
        
        while (current.next != null) {
            if (condition.test(current.next)) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        
        return dummy.next;
    }
    
    /**
     * DELETE ALTERNATE NODES
     * 
     * @param head Head node of list
     * @return Head of modified list
     */
    public static ListNode deleteAlternate(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode current = head;
        
        while (current != null && current.next != null) {
            current.next = current.next.next;
            current = current.next;
        }
        
        return head;
    }
    
    /**
     * DELETE MIDDLE NODE
     * 
     * @param head Head node of list
     * @return Head of modified list
     */
    public static ListNode deleteMiddle(ListNode head) {
        if (head == null || head.next == null) {
            return null; // List becomes empty or had only one node
        }
        
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Delete middle node (slow)
        prev.next = slow.next;
        
        return head;
    }
    
    /**
     * CLEAR ENTIRE LIST (delete all nodes)
     * 
     * In Java, we simply return null and let garbage collector
     * clean up unreachable nodes
     * 
     * @param head Head node of list
     * @return null (empty list)
     */
    public static ListNode clearList(ListNode head) {
        // In languages without garbage collection,
        // we would need to traverse and delete each node
        return null;
    }
    
    /**
     * Main method with comprehensive testing
     */
    public static void main(String[] args) {
        System.out.println("=== Linked List Delete Operations ===\n");
        
        // Test 1: Delete by value
        System.out.println("1. Delete by Value:");
        ListNode list1 = Intro.buildFromArray(new int[]{10, 20, 30, 40, 50});
        System.out.print("Original: ");
        Intro.printList(list1);
        
        // Delete middle
        list1 = deleteByValue(list1, 30);
        System.out.print("Delete 30 (middle): ");
        Intro.printList(list1);
        
        // Delete head
        list1 = deleteByValue(list1, 10);
        System.out.print("Delete 10 (head): ");
        Intro.printList(list1);
        
        // Delete tail
        list1 = deleteByValue(list1, 50);
        System.out.print("Delete 50 (tail): ");
        Intro.printList(list1);
        
        // Delete non-existent
        list1 = deleteByValue(list1, 100);
        System.out.print("Delete 100 (non-existent): ");
        Intro.printList(list1);
        
        // Test 2: Delete at position
        System.out.println("\n2. Delete at Position:");
        ListNode list2 = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5});
        System.out.print("Original: ");
        Intro.printList(list2);
        
        list2 = deleteAtPosition(list2, 1); // Delete head
        System.out.print("Delete position 1 (head): ");
        Intro.printList(list2);
        
        list2 = deleteAtPosition(list2, 2); // Delete second node (now value 3)
        System.out.print("Delete position 2: ");
        Intro.printList(list2);
        
        list2 = deleteAtPosition(list2, 3); // Delete last node
        System.out.print("Delete position 3 (last): ");
        Intro.printList(list2);
        
        list2 = deleteAtPosition(list2, 5); // Position beyond length
        System.out.print("Delete position 5 (beyond length): ");
        Intro.printList(list2);
        
        // Test 3: Delete head and tail directly
        System.out.println("\n3. Delete Head and Tail:");
        ListNode list3 = Intro.buildFromArray(new int[]{100, 200, 300});
        System.out.print("Original: ");
        Intro.printList(list3);
        
        list3 = deleteHead(list3);
        System.out.print("Delete head: ");
        Intro.printList(list3);
        
        list3 = deleteTail(list3);
        System.out.print("Delete tail: ");
        Intro.printList(list3);
        
        list3 = deleteHead(list3);
        System.out.print("Delete head (list becomes empty): ");
        Intro.printList(list3);
        
        // Test 4: Delete all occurrences
        System.out.println("\n4. Delete All Occurrences:");
        ListNode list4 = Intro.buildFromArray(new int[]{2, 2, 3, 2, 4, 2, 5, 2});
        System.out.print("Original: ");
        Intro.printList(list4);
        
        list4 = deleteAllOccurrences(list4, 2);
        System.out.print("Delete all 2's: ");
        Intro.printList(list4);
        
        // Test 5: Delete duplicates from sorted list
        System.out.println("\n5. Delete Duplicates from Sorted List:");
        ListNode sortedList = Intro.buildFromArray(new int[]{1, 1, 2, 3, 3, 3, 4, 5, 5});
        System.out.print("Original sorted with duplicates: ");
        Intro.printList(sortedList);
        
        sortedList = deleteDuplicatesSorted(sortedList);
        System.out.print("After removing duplicates: ");
        Intro.printList(sortedList);
        
        // Test 6: Delete nth from end
        System.out.println("\n6. Delete Nth Node From End:");
        ListNode list6 = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5});
        System.out.print("Original: ");
        Intro.printList(list6);
        
        list6 = deleteNthFromEnd(list6, 2); // Delete 4
        System.out.print("Delete 2nd from end (4): ");
        Intro.printList(list6);
        
        list6 = deleteNthFromEnd(list6, 1); // Delete last (5)
        System.out.print("Delete 1st from end (5): ");
        Intro.printList(list6);
        
        list6 = deleteNthFromEnd(list6, 3); // Delete head (1)
        System.out.print("Delete 3rd from end (head): ");
        Intro.printList(list6);
        
        // Test 7: Delete with condition
        System.out.println("\n7. Delete with Condition:");
        ListNode list7 = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        System.out.print("Original: ");
        Intro.printList(list7);
        
        // Delete even numbers
        list7 = deleteWithCondition(list7, node -> node.val % 2 == 0);
        System.out.print("Delete even numbers: ");
        Intro.printList(list7);
        
        // Delete numbers greater than 5
        list7 = deleteWithCondition(list7, node -> node.val > 5);
        System.out.print("Delete numbers > 5: ");
        Intro.printList(list7);
        
        // Test 8: Delete alternate nodes
        System.out.println("\n8. Delete Alternate Nodes:");
        ListNode list8 = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8});
        System.out.print("Original: ");
        Intro.printList(list8);
        
        list8 = deleteAlternate(list8);
        System.out.print("Delete alternate nodes: ");
        Intro.printList(list8);
        
        // Test 9: Delete middle node
        System.out.println("\n9. Delete Middle Node:");
        
        ListNode oddList = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5});
        System.out.print("Odd length: ");
        Intro.printList(oddList);
        oddList = deleteMiddle(oddList);
        System.out.print("Delete middle: ");
        Intro.printList(oddList);
        
        ListNode evenList = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5, 6});
        System.out.print("Even length: ");
        Intro.printList(evenList);
        evenList = deleteMiddle(evenList);
        System.out.print("Delete middle (second of two middles): ");
        Intro.printList(evenList);
        
        // Test 10: Complex scenario
        System.out.println("\n10. Complex Deletion Scenario:");
        ListNode complex = Intro.buildFromArray(new int[]{10, 20, 10, 30, 10, 40, 10});
        System.out.print("Original: ");
        Intro.printList(complex);
        
        // Delete all 10's
        complex = deleteAllOccurrences(complex, 10);
        System.out.print("After deleting all 10's: ");
        Intro.printList(complex);
        
        // Delete middle
        complex = deleteMiddle(complex);
        System.out.print("After deleting middle: ");
        Intro.printList(complex);
        
        // Delete head and tail
        complex = deleteHead(complex);
        complex = deleteTail(complex);
        System.out.print("After deleting head and tail: ");
        Intro.printList(complex);
        
        // Test 11: Performance testing
        System.out.println("\n11. Performance Analysis:");
        int size = 100000;
        ListNode largeList = null;
        
        // Build large list
        for (int i = 0; i < size; i++) {
            largeList = Insert.insertAtHead(largeList, i % 100); // Some duplicates
        }
        System.out.println("Built list of " + size + " nodes");
        
        // Measure deletion time
        long start, end;
        
        // Delete from head (fast)
        start = System.currentTimeMillis();
        largeList = deleteHead(largeList);
        end = System.currentTimeMillis();
        System.out.println("Delete head: " + (end - start) + " ms");
        
        // Delete from middle (slower)
        start = System.currentTimeMillis();
        largeList = deleteAtPosition(largeList, size / 2);
        end = System.currentTimeMillis();
        System.out.println("Delete middle: " + (end - start) + " ms");
        
        // Delete all occurrences of a value
        start = System.currentTimeMillis();
        largeList = deleteAllOccurrences(largeList, 50);
        end = System.currentTimeMillis();
        System.out.println("Delete all occurrences of 50: " + (end - start) + " ms");
    }
    
    /**
     * Deletion Complexity Summary:
     * 
     * Operation                 Time Complexity   Notes
     * Delete by value           O(n)              May need full traversal
     * Delete at position        O(n)              Worst case: last position
     * Delete head               O(1)              Constant time
     * Delete tail               O(n)              Need to traverse to end
     * Delete all occurrences    O(n)              Single pass with dummy node
     * Delete duplicates sorted  O(n)              Single pass
     * Delete nth from end       O(n)              Two pointers, one pass
     * Delete middle             O(n)              Fast/slow pointers
     * 
     * Memory Considerations:
     * - In Java, deleted nodes become garbage collected
     * - In C/C++, need to explicitly free memory
     * - Dummy nodes use extra O(1) space
     */
    
    /**
     * Common Deletion Mistakes:
     * 
     * 1. LOST HEAD REFERENCE:
     *    ❌ head = head.next; // If head was only node, now head is null
     *    ✅ Check if head is null first
     *    
     * 2. MEMORY LEAKS (in C/C++):
     *    ❌ current->next = current->next->next; // Lost pointer to deleted node
     *    ✅ Save pointer, then delete: 
     *       Node* toDelete = current->next;
     *       current->next = current->next->next;
     *       delete toDelete;
     *    
     * 3. NOT UPDATING POINTERS CORRECTLY:
     *    Must update previous node's next pointer
     *    
     * 4. INFINITE LOOP WHEN DELETING CURRENT:
     *    If deleting current node, need special handling
     *    
     * 5. OFF-BY-ONE IN POSITION:
     *    Be clear about 0-based vs 1-based indexing
     */
    
    /**
     * Deletion Best Practices:
     * 
     * 1. USE DUMMY NODES: Simplify edge case handling
     * 2. CHECK EMPTY LIST: Always handle head == null case
     * 3. SAVE REFERENCES: If you might need deleted node's data
     * 4. UPDATE LENGTH: If maintaining list size counter
     * 5. TEST EDGE CASES: Single node, head, tail, empty list
     * 6. CONSIDER MEMORY: Ensure proper cleanup in manual memory languages
     */
}