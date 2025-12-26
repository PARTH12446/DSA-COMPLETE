/**
 * Delete Last Node in Doubly Linked List
 * 
 * This class demonstrates deletion of the last node in a doubly linked list.
 * Deleting the last node requires careful pointer manipulation to maintain
 * list integrity and handle edge cases properly.
 * 
 * Key Concepts:
 * - Handling empty list (nothing to delete)
 * - Handling single node list (becomes empty)
 * - Updating both next and prev pointers
 * - Memory management considerations
 * - Edge cases and error handling
 */

public class DeleteLastNode {
    
    /**
     * DELETE LAST NODE (Basic version)
     * 
     * Algorithm:
     * 1. Handle edge cases:
     *    - Empty list (head == null) → return null
     *    - Single node (head.next == null) → return null
     * 2. Traverse to last node
     * 3. Get reference to second last node (last.prev)
     * 4. If second last exists, set its next to null
     * 5. Set last node's prev to null (optional, helps garbage collection)
     * 6. Return head (unchanged unless list becomes empty)
     * 
     * @param head Head node of the doubly linked list
     * @return Head of modified list (may be null if list becomes empty)
     * 
     * Time Complexity: O(n) - Need to traverse to last node
     * Space Complexity: O(1) - Only uses constant extra space
     * 
     * Example:
     * Before: null ← [10] ⇄ [20] ⇄ [30] → null
     * After deleteLast: null ← [10] ⇄ [20] → null
     */
    public static DNode deleteLast(DNode head) {
        // Case 1: Empty list
        if (head == null) {
            return null;
        }
        
        // Case 2: Single node list
        if (head.next == null) {
            // Optional: In C/C++, would need to free memory here
            return null; // List becomes empty
        }
        
        // Case 3: Multiple nodes
        DNode current = head;
        
        // Traverse to last node
        while (current.next != null) {
            current = current.next;
        }
        
        // Get second last node
        DNode prev = current.prev;
        
        // Unlink last node
        if (prev != null) {
            prev.next = null;
        }
        
        // Help garbage collection by removing backward reference
        current.prev = null;
        
        return head;
    }
    
    /**
     * DELETE LAST using tail pointer (More efficient)
     * 
     * If we maintain a tail pointer, deletion becomes O(1)
     * 
     * @param head Head node of list
     * @param tail Tail node of list
     * @return New head and tail in an array [head, tail]
     */
    public static DNode[] deleteLastWithTail(DNode head, DNode tail) {
        // Case 1: Empty list
        if (head == null || tail == null) {
            return new DNode[]{null, null};
        }
        
        // Case 2: Single node
        if (head == tail) {
            // Optional: free memory in C/C++
            return new DNode[]{null, null};
        }
        
        // Case 3: Multiple nodes
        DNode newTail = tail.prev;
        
        // Unlink old tail
        if (newTail != null) {
            newTail.next = null;
        }
        tail.prev = null; // Help garbage collection
        
        return new DNode[]{head, newTail};
    }
    
    /**
     * DELETE LAST using getTail helper
     * 
     * @param head Head node of list
     * @return Head of modified list
     */
    public static DNode deleteLastUsingGetTail(DNode head) {
        if (head == null) {
            return null;
        }
        
        DNode tail = getTail(head);
        
        // Single node
        if (tail == head) {
            return null;
        }
        
        // Multiple nodes
        DNode prev = tail.prev;
        prev.next = null;
        tail.prev = null;
        
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
     * DELETE MULTIPLE NODES from end
     * 
     * @param head Head node of list
     * @param count Number of nodes to delete from end
     * @return Head of modified list
     * @throws IllegalArgumentException if count is negative
     */
    public static DNode deleteMultipleFromEnd(DNode head, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative");
        }
        
        if (head == null || count == 0) {
            return head;
        }
        
        // Get length first
        int length = getLength(head);
        
        // If deleting all or more nodes
        if (count >= length) {
            return null; // List becomes empty
        }
        
        // Find node before the ones to delete
        DNode current = head;
        for (int i = 0; i < length - count - 1; i++) {
            current = current.next;
        }
        
        // Unlink the nodes to be deleted
        DNode toDelete = current.next;
        current.next = null;
        
        // Help garbage collection by clearing prev pointers
        if (toDelete != null) {
            toDelete.prev = null;
        }
        
        return head;
    }
    
    /**
     * Helper method to get length
     */
    private static int getLength(DNode head) {
        int length = 0;
        DNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
    }
    
    /**
     * DELETE LAST in circular doubly linked list
     * 
     * @param head Head node of circular list
     * @return Head of modified circular list
     */
    public static DNode deleteLastCircular(DNode head) {
        // Case 1: Empty list
        if (head == null) {
            return null;
        }
        
        // Case 2: Single node
        if (head.next == head) {
            // Optional: free memory in C/C++
            return null;
        }
        
        // Case 3: Multiple nodes
        DNode tail = head.prev; // In circular list, head.prev is tail
        DNode newTail = tail.prev;
        
        // Update links
        newTail.next = head;
        head.prev = newTail;
        
        // Help garbage collection
        tail.next = null;
        tail.prev = null;
        
        return head;
    }
    
    /**
     * DELETE LAST with validation
     * 
     * @param head Head node
     * @return Head of modified list
     * @throws IllegalArgumentException if validation fails
     */
    public static DNode deleteLastValidated(DNode head) {
        // Validate before deletion
        if (!validateList(head)) {
            throw new IllegalArgumentException("Input list is malformed");
        }
        
        DNode result = deleteLast(head);
        
        // Validate after deletion
        if (!validateList(result)) {
            throw new IllegalStateException("List became malformed after deletion");
        }
        
        return result;
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
     * DELETE LAST and return deleted value
     * 
     * @param head Head node
     * @return Array containing [newHead, deletedValue]
     *         deletedValue is Integer.MIN_VALUE if list was empty
     */
    public static Object[] deleteLastAndGetValue(DNode head) {
        if (head == null) {
            return new Object[]{null, Integer.MIN_VALUE};
        }
        
        DNode tail = getTail(head);
        int deletedValue = tail.val;
        
        DNode newHead = deleteLast(head);
        
        return new Object[]{newHead, deletedValue};
    }
    
    /**
     * DELETE LAST N nodes (more efficient version)
     * 
     * @param head Head node
     * @param n Number of nodes to delete from end
     * @return Head of modified list
     */
    public static DNode deleteLastN(DNode head, int n) {
        if (head == null || n <= 0) {
            return head;
        }
        
        // First pass: get length
        int length = 0;
        DNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        
        if (n >= length) {
            return null; // Delete all nodes
        }
        
        // Find the node before deletion point
        current = head;
        for (int i = 0; i < length - n - 1; i++) {
            current = current.next;
        }
        
        // Unlink the last n nodes
        DNode toDelete = current.next;
        current.next = null;
        
        // Help garbage collection
        if (toDelete != null) {
            toDelete.prev = null;
        }
        
        return head;
    }
    
    /**
     * Main method with comprehensive testing
     */
    public static void main(String[] args) {
        System.out.println("=== Delete Last Node in Doubly Linked List ===\n");
        
        // Test 1: Basic deletion from end
        System.out.println("1. Basic Deletion from End:");
        DNode list1 = Intro.buildFromArray(new int[]{10, 20, 30, 40});
        System.out.print("Original: ");
        Intro.printForward(list1);
        
        list1 = deleteLast(list1);
        System.out.print("After deleting last: ");
        Intro.printForward(list1);
        
        list1 = deleteLast(list1);
        System.out.print("After deleting last again: ");
        Intro.printForward(list1);
        
        list1 = deleteLast(list1);
        System.out.print("After deleting last again: ");
        Intro.printForward(list1);
        
        list1 = deleteLast(list1);
        System.out.print("After deleting last (list becomes empty): ");
        Intro.printForward(list1);
        
        list1 = deleteLast(list1);
        System.out.print("Delete from empty list: ");
        Intro.printForward(list1);
        
        // Test 2: Deletion with tail pointer
        System.out.println("\n2. Deletion with Tail Pointer:");
        DNode head2 = Intro.buildFromArray(new int[]{100, 200, 300});
        DNode tail2 = Intro.getTail(head2);
        
        System.out.print("Original: ");
        Intro.printForward(head2);
        System.out.println("Head: " + head2.val + ", Tail: " + tail2.val);
        
        DNode[] result = deleteLastWithTail(head2, tail2);
        head2 = result[0];
        tail2 = result[1];
        System.out.print("After deleting last: ");
        Intro.printForward(head2);
        System.out.println("Head: " + head2.val + ", Tail: " + tail2.val);
        
        result = deleteLastWithTail(head2, tail2);
        head2 = result[0];
        tail2 = result[1];
        System.out.print("After deleting last: ");
        Intro.printForward(head2);
        System.out.println("Head: " + head2.val + ", Tail: " + tail2.val);
        
        // Test 3: Delete multiple nodes from end
        System.out.println("\n3. Delete Multiple Nodes from End:");
        DNode list3 = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5, 6, 7});
        System.out.print("Original: ");
        Intro.printForward(list3);
        
        list3 = deleteMultipleFromEnd(list3, 3);
        System.out.print("Delete last 3 nodes: ");
        Intro.printForward(list3);
        
        list3 = deleteMultipleFromEnd(list3, 2);
        System.out.print("Delete last 2 nodes: ");
        Intro.printForward(list3);
        
        list3 = deleteMultipleFromEnd(list3, 10); // Delete all
        System.out.print("Delete 10 nodes (more than length): ");
        Intro.printForward(list3);
        
        // Test 4: Edge cases
        System.out.println("\n4. Edge Cases:");
        
        // Single element
        DNode single = new DNode(99);
        System.out.print("Single element: ");
        Intro.printForward(single);
        single = deleteLast(single);
        System.out.print("After deleting: ");
        Intro.printForward(single);
        
        // Empty list
        DNode empty = null;
        empty = deleteLast(empty);
        System.out.print("Delete from empty: ");
        Intro.printForward(empty);
        
        // Test 5: Circular doubly linked list deletion
        System.out.println("\n5. Circular Doubly Linked List Deletion:");
        DNode circular = null;
        circular = InsertAtEnd.insertAtEndCircular(circular, 1);
        circular = InsertAtEnd.insertAtEndCircular(circular, 2);
        circular = InsertAtEnd.insertAtEndCircular(circular, 3);
        
        System.out.println("Original circular list (3 nodes)");
        System.out.print("Circular traversal: ");
        DNode current = circular;
        for (int i = 0; i < 7; i++) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println("...");
        
        circular = deleteLastCircular(circular);
        System.out.print("After deleting last (2 nodes left), traversal: ");
        current = circular;
        for (int i = 0; i < 5; i++) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println("...");
        
        circular = deleteLastCircular(circular);
        System.out.print("After deleting last (1 node left), traversal: ");
        current = circular;
        for (int i = 0; i < 3; i++) {
            System.out.print(current.val + " ");
            current = current.next;
        }
        System.out.println("...");
        
        circular = deleteLastCircular(circular);
        System.out.print("After deleting last (empty): ");
        Intro.printForward(circular);
        
        // Test 6: Validation
        System.out.println("\n6. Validation:");
        DNode validList = Intro.buildFromArray(new int[]{1, 2, 3});
        try {
            validList = deleteLastValidated(validList);
            System.out.println("Valid deletion successful");
            System.out.print("List after deletion: ");
            Intro.printForward(validList);
        } catch (Exception e) {
            System.out.println("Validation failed: " + e.getMessage());
        }
        
        // Test 7: Delete and get value
        System.out.println("\n7. Delete Last and Get Value:");
        DNode valueList = Intro.buildFromArray(new int[]{10, 20, 30});
        System.out.print("Original: ");
        Intro.printForward(valueList);
        
        Object[] deleteResult = deleteLastAndGetValue(valueList);
        valueList = (DNode) deleteResult[0];
        int deletedValue = (int) deleteResult[1];
        System.out.println("Deleted value: " + deletedValue);
        System.out.print("List after deletion: ");
        Intro.printForward(valueList);
        
        // Test 8: Performance comparison
        System.out.println("\n8. Performance Comparison:");
        int size = 10000;
        
        // Build a large list
        DNode perfList = null;
        for (int i = 0; i < size; i++) {
            perfList = InsertAtEnd.insertAtEnd(perfList, i);
        }
        
        // Method 1: Basic O(n) each deletion
        DNode testList1 = perfList;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            testList1 = deleteLast(testList1);
        }
        long end = System.currentTimeMillis();
        System.out.println("Basic method (1000 deletions): " + (end - start) + " ms");
        
        // Method 2: With tail pointer
        DNode testHead2 = perfList;
        DNode testTail2 = Intro.getTail(perfList);
        start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            DNode[] res = deleteLastWithTail(testHead2, testTail2);
            testHead2 = res[0];
            testTail2 = res[1];
        }
        end = System.currentTimeMillis();
        System.out.println("With tail pointer (1000 deletions): " + (end - start) + " ms");
        
        // Test 9: Real-world scenario - Browser history
        System.out.println("\n9. Real-World Scenario - Browser History:");
        System.out.println("Simulating browser history with back/forward:");
        
        DNode history = Intro.buildFromArray(new int[]{ 
            "Homepage".hashCode(), 
            "News".hashCode(), 
            "Sports".hashCode(),
            "Weather".hashCode(),
            "Entertainment".hashCode()
        });
        
        System.out.println("Initial browsing history (5 pages)");
        System.out.print("History: ");
        Intro.printForward(history);
        
        // User clears recent history (deletes last 2 pages)
        history = deleteMultipleFromEnd(history, 2);
        System.out.print("\nAfter clearing recent 2 pages: ");
        Intro.printForward(history);
        
        // User continues browsing and adds new pages
        history = InsertAtEnd.insertAtEnd(history, "Tech".hashCode());
        history = InsertAtEnd.insertAtEnd(history, "Science".hashCode());
        System.out.print("After browsing 2 new pages: ");
        Intro.printForward(history);
        
        // User clears entire history
        history = deleteMultipleFromEnd(history, getLength(history));
        System.out.print("After clearing all history: ");
        Intro.printForward(history);
        
        // Test 10: Memory and pointer verification
        System.out.println("\n10. Memory and Pointer Verification:");
        DNode testList = Intro.buildFromArray(new int[]{1, 2, 3});
        System.out.print("Original: ");
        Intro.printForward(testList);
        
        // Verify pointers before deletion
        DNode node2 = testList.next;
        DNode node3 = testList.next.next;
        System.out.println("Before deletion - Node3.prev points to: " + 
                          (node3.prev == null ? "null" : node3.prev.val));
        
        testList = deleteLast(testList);
        System.out.print("After deleting last: ");
        Intro.printForward(testList);
        
        // Verify node2 is now last node
        System.out.println("After deletion - Node2.next is: " + 
                          (node2.next == null ? "null" : node2.next.val));
        System.out.println("After deletion - Node3.prev is: " + 
                          (node3.prev == null ? "null" : node3.prev.val));
    }
    
    /**
     * Complexity Analysis:
     * 
     * Method                    Time Complexity   Space Complexity   Notes
     * Basic deleteLast          O(n) per delete   O(1)               Need to traverse each time
     * With tail pointer         O(1) per delete   O(1)               Maintain tail reference
     * Delete multiple          O(n + m)          O(1)               n = length, m = nodes to delete
     * Circular delete          O(1)              O(1)               Direct access via head.prev
     * 
     * Optimization Strategies:
     * 1. Maintain tail pointer for O(1) deletion
     * 2. Consider circular lists for frequent end operations
     * 3. Batch deletions when possible
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. NOT UPDATING BOTH POINTERS:
     *    ❌ prev.next = null; // Missing current.prev = null for GC
     *    
     * 2. MEMORY LEAKS:
     *    In C/C++, forgetting to free deleted node memory
     *    
     * 3. NULL POINTERS:
     *    Not checking for null when accessing prev/next
     *    
     * 4. NOT HANDLING SINGLE NODE:
     *    Special case needed when head.next == null
     *    
     * 5. FORGETTING TO UPDATE TAIL:
     *    If maintaining tail pointer
     */
    
    /**
     * Best Practices:
     * 
     * 1. ALWAYS HANDLE EDGE CASES: Empty list, single node
     * 2. CLEAN UP POINTERS: Set null to help garbage collection
     * 3. USE HELPER METHODS: getTail, validateList
     * 4. DEFENSIVE PROGRAMMING: Validate input and state
     * 5. DOCUMENT MEMORY MANAGEMENT: Especially important in C/C++
     */
}