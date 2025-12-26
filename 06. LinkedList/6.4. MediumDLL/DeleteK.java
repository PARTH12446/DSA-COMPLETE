/**
 * Class for deleting the k-th node from a doubly linked list (1-based indexing)
 * 
 * Doubly Linked List Structure:
 * Each node (DNode) has:
 * - data value
 * - prev pointer to previous node
 * - next pointer to next node
 * 
 * Example:
 * null ← [1] ↔ [2] ↔ [3] ↔ [4] → null
 * 
 * Deleting k-th node involves:
 * 1. Locating the k-th node
 * 2. Updating neighbors' pointers
 * 3. Handling edge cases (head, tail, invalid k)
 * 
 * Time Complexity: O(n) in worst case (to find k-th node)
 * Space Complexity: O(1) - uses only constant extra space
 */
public class DeleteK {

    /**
     * Deletes the k-th node (1-based) from a doubly linked list
     * 
     * @param head The head node of the doubly linked list
     * @param k The position to delete (1 = first node, 2 = second node, etc.)
     * @return The new head of the list (may change if deleting first node)
     * 
     * Algorithm Steps:
     * 1. Handle invalid inputs (null head, k <= 0)
     * 2. Special case: Delete first node (k == 1)
     *    - Update head to head.next
     *    - If new head exists, set its prev to null
     *    - Disconnect old head
     * 3. General case: Delete k-th node where k > 1
     *    - Traverse to k-th node
     *    - If k exceeds list length, return original head
     *    - Update prev node's next pointer
     *    - Update next node's prev pointer
     *    - Disconnect k-th node completely
     * 4. Return appropriate head
     */
    public static DNode deleteKth(DNode head, int k) {
        // Step 1: Handle invalid inputs
        // If list is empty or k is invalid, return head unchanged
        if (head == null || k <= 0) {
            System.out.println("Invalid input: " + (head == null ? "empty list" : "k=" + k));
            return head;
        }
        
        // Step 2: Special case - delete first node (k = 1)
        if (k == 1) {
            System.out.println("Deleting first node (value: " + head.data + ")");
            
            DNode next = head.next;  // Save reference to second node
            
            // If there's a second node, update its prev pointer
            if (next != null) {
                next.prev = null;    // Second node becomes new head
            }
            
            // Clean up old head's pointers
            head.next = null;
            head.prev = null;
            
            // Return new head (could be null if list had only one node)
            return next;
        }
        
        // Step 3: General case - delete k-th node where k > 1
        DNode curr = head;
        int index = 1;  // 1-based indexing
        
        // Traverse to the k-th node
        while (curr != null && index < k) {
            curr = curr.next;
            index++;
        }
        
        // Step 4: Check if k-th node exists
        if (curr == null) {
            System.out.println("Position " + k + " is out of bounds. List has less than " + k + " nodes.");
            return head;  // k exceeds list length, return original head
        }
        
        System.out.println("Deleting node at position " + k + " (value: " + curr.data + ")");
        
        // Step 5: Get neighbors of node to delete
        DNode prev = curr.prev;  // Node before the one to delete
        DNode next = curr.next;  // Node after the one to delete
        
        // Step 6: Update neighbors' pointers
        // Connect prev node to next node (skip curr)
        if (prev != null) {
            prev.next = next;
        }
        
        // Connect next node to prev node (skip curr)
        if (next != null) {
            next.prev = prev;
        }
        
        // Step 7: Clean up deleted node's pointers
        curr.prev = null;
        curr.next = null;
        
        // Head doesn't change unless we deleted first node (handled in special case)
        return head;
    }
    
    /**
     * Alternative approach using dummy head for cleaner code
     * Dummy head simplifies edge cases (deleting first node)
     */
    public static DNode deleteKthWithDummy(DNode head, int k) {
        if (head == null || k <= 0) return head;
        
        // Create dummy node that points to head
        DNode dummy = new DNode(-1);
        dummy.next = head;
        head.prev = dummy;
        
        DNode curr = dummy.next;  // Start from actual head
        int count = 1;
        
        // Find k-th node
        while (curr != null && count < k) {
            curr = curr.next;
            count++;
        }
        
        // If found, delete it
        if (curr != null) {
            DNode prev = curr.prev;
            DNode next = curr.next;
            
            prev.next = next;
            if (next != null) {
                next.prev = prev;
            }
            
            curr.prev = null;
            curr.next = null;
        }
        
        // Get new head (skip dummy)
        DNode newHead = dummy.next;
        if (newHead != null) {
            newHead.prev = null;  // Ensure new head's prev is null
        }
        
        return newHead;
    }
    
    /**
     * Deletes the k-th node from the END of the list
     * Example: deleteKthFromEnd([1↔2↔3↔4], 2) → [1↔2↔4]
     */
    public static DNode deleteKthFromEnd(DNode head, int k) {
        if (head == null || k <= 0) return head;
        
        // First pass: count total nodes
        int length = 0;
        DNode curr = head;
        while (curr != null) {
            length++;
            curr = curr.next;
        }
        
        // Calculate position from beginning (1-based)
        int positionFromStart = length - k + 1;
        
        // If k > length, positionFromStart <= 0
        if (positionFromStart <= 0) {
            System.out.println("Position " + k + " from end is out of bounds");
            return head;
        }
        
        // Delete using original method
        return deleteKth(head, positionFromStart);
    }
    
    /**
     * Helper method to create a doubly linked list from array
     */
    public static DNode createList(int[] values) {
        if (values == null || values.length == 0) return null;
        
        DNode head = new DNode(values[0]);
        DNode current = head;
        DNode prev = null;
        
        for (int i = 1; i < values.length; i++) {
            DNode newNode = new DNode(values[i]);
            current.next = newNode;
            newNode.prev = current;
            current = newNode;
        }
        
        return head;
    }
    
    /**
     * Helper method to print doubly linked list forward
     */
    public static void printForward(DNode head) {
        DNode current = head;
        System.out.print("Forward:  null ← ");
        
        while (current != null) {
            System.out.print("[" + current.data + "]");
            if (current.next != null) {
                System.out.print(" ↔ ");
            }
            current = current.next;
        }
        System.out.println(" → null");
    }
    
    /**
     * Helper method to print doubly linked list backward
     */
    public static void printBackward(DNode head) {
        if (head == null) {
            System.out.println("Backward: null");
            return;
        }
        
        // Go to tail first
        DNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
        }
        
        System.out.print("Backward: null ← ");
        DNode current = tail;
        
        while (current != null) {
            System.out.print("[" + current.data + "]");
            if (current.prev != null) {
                System.out.print(" ↔ ");
            }
            current = current.prev;
        }
        System.out.println(" → null");
    }
    
    /**
     * Visualizes the deletion process step by step
     */
    public static void visualizeDeletion(DNode head, int k) {
        System.out.println("\n=== Visualizing Deletion of " + k + "-th Node ===");
        System.out.println("Original list:");
        printForward(head);
        printBackward(head);
        
        // Create a copy for visualization
        DNode listCopy = copyList(head);
        
        // Step 1: Check if list is empty or k invalid
        if (listCopy == null) {
            System.out.println("\nStep 1: List is empty, nothing to delete");
            return;
        }
        
        if (k <= 0) {
            System.out.println("\nStep 1: Invalid k=" + k + ", must be positive");
            return;
        }
        
        System.out.println("\nStep 1: Check inputs ✓");
        
        // Step 2: Special case - deleting first node
        if (k == 1) {
            System.out.println("\nStep 2: Deleting first node (k=1)");
            System.out.println("  Before deletion: head → [" + listCopy.data + "]");
            
            DNode next = listCopy.next;
            System.out.println("  Next node: " + (next != null ? "[" + next.data + "]" : "null"));
            
            if (next != null) {
                System.out.println("  Setting next.prev = null");
                next.prev = null;
            }
            
            System.out.println("  Setting head.next = null");
            listCopy.next = null;
            
            DNode newHead = next;
            System.out.println("\n  New head: " + (newHead != null ? "[" + newHead.data + "]" : "null"));
            return;
        }
        
        // Step 3: Find k-th node
        System.out.println("\nStep 2: Find " + k + "-th node");
        DNode curr = listCopy;
        int index = 1;
        
        while (curr != null && index < k) {
            System.out.println("  Index " + index + ": node [" + curr.data + "]");
            curr = curr.next;
            index++;
        }
        
        // Step 4: Check if node exists
        if (curr == null) {
            System.out.println("\nStep 3: Node not found - list has less than " + k + " nodes");
            return;
        }
        
        System.out.println("  Found: node at position " + k + " = [" + curr.data + "]");
        
        // Step 5: Get neighbors
        DNode prev = curr.prev;
        DNode next = curr.next;
        
        System.out.println("\nStep 3: Get neighbors");
        System.out.println("  Previous node: " + (prev != null ? "[" + prev.data + "]" : "null"));
        System.out.println("  Next node: " + (next != null ? "[" + next.data + "]" : "null"));
        
        // Step 6: Update pointers
        System.out.println("\nStep 4: Update pointers");
        
        if (prev != null) {
            System.out.println("  Setting prev.next = next");
            prev.next = next;
        } else {
            System.out.println("  No previous node (this shouldn't happen for k>1)");
        }
        
        if (next != null) {
            System.out.println("  Setting next.prev = prev");
            next.prev = prev;
        } else {
            System.out.println("  No next node (deleting last node)");
        }
        
        // Step 7: Clean up
        System.out.println("\nStep 5: Clean up deleted node");
        System.out.println("  Setting curr.prev = null, curr.next = null");
        curr.prev = null;
        curr.next = null;
        
        System.out.println("\nFinal list:");
        printForward(listCopy);
        printBackward(listCopy);
    }
    
    /**
     * Helper to copy a doubly linked list
     */
    private static DNode copyList(DNode head) {
        if (head == null) return null;
        
        DNode newHead = new DNode(head.data);
        DNode original = head.next;
        DNode copy = newHead;
        DNode copyPrev = null;
        
        while (original != null) {
            copy.next = new DNode(original.data);
            copy.prev = copyPrev;
            copyPrev = copy;
            copy = copy.next;
            original = original.next;
        }
        
        if (copy != null) {
            copy.prev = copyPrev;
        }
        
        return newHead;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Doubly Linked List - Delete K-th Node ===\n");
        
        // Test Case 1: Delete middle node
        System.out.println("Test Case 1: Delete 3rd node from [1↔2↔3↔4↔5]");
        int[] arr1 = {1, 2, 3, 4, 5};
        DNode list1 = createList(arr1);
        printForward(list1);
        
        visualizeDeletion(copyList(list1), 3);
        
        DNode result1 = deleteKth(list1, 3);
        System.out.print("\nAfter deleting 3rd node: ");
        printForward(result1);
        System.out.println();
        
        // Test Case 2: Delete first node
        System.out.println("Test Case 2: Delete 1st node from [10↔20↔30]");
        DNode list2 = createList(new int[]{10, 20, 30});
        printForward(list2);
        visualizeDeletion(copyList(list2), 1);
        DNode result2 = deleteKth(list2, 1);
        System.out.print("After deleting 1st node: ");
        printForward(result2);
        System.out.println();
        
        // Test Case 3: Delete last node
        System.out.println("Test Case 3: Delete last (4th) node from [5↔6↔7↔8]");
        DNode list3 = createList(new int[]{5, 6, 7, 8});
        printForward(list3);
        visualizeDeletion(copyList(list3), 4);
        DNode result3 = deleteKth(list3, 4);
        System.out.print("After deleting last node: ");
        printForward(result3);
        System.out.println();
        
        // Test Case 4: Invalid k (k = 0)
        System.out.println("Test Case 4: Invalid k (k = 0)");
        DNode list4 = createList(new int[]{1, 2, 3});
        DNode result4 = deleteKth(list4, 0);
        System.out.print("List unchanged: ");
        printForward(result4);
        System.out.println();
        
        // Test Case 5: k greater than list length
        System.out.println("Test Case 5: k > list length (k = 5 from list of 3)");
        DNode list5 = createList(new int[]{1, 2, 3});
        DNode result5 = deleteKth(list5, 5);
        System.out.print("List unchanged: ");
        printForward(result5);
        System.out.println();
        
        // Test Case 6: Single node list
        System.out.println("Test Case 6: Delete from single node list [100]");
        DNode list6 = new DNode(100);
        printForward(list6);
        DNode result6 = deleteKth(list6, 1);
        System.out.print("After deleting: ");
        printForward(result6);
        System.out.println();
        
        // Test Case 7: Delete from end (k-th from end)
        System.out.println("Test Case 7: Delete 2nd node from end of [1↔2↔3↔4↔5]");
        DNode list7 = createList(new int[]{1, 2, 3, 4, 5});
        printForward(list7);
        DNode result7 = deleteKthFromEnd(list7, 2);
        System.out.print("After deleting 2nd from end: ");
        printForward(result7);
        System.out.println();
        
        // Test Case 8: Dummy head approach
        System.out.println("Test Case 8: Using dummy head approach (delete 2nd node)");
        DNode list8 = createList(new int[]{10, 20, 30, 40});
        printForward(list8);
        DNode result8 = deleteKthWithDummy(list8, 2);
        System.out.print("After deleting 2nd node (dummy method): ");
        printForward(result8);
    }
    
    /**
     * Definition for doubly linked list node
     */
    static class DNode {
        int data;
        DNode prev;
        DNode next;
        
        DNode(int data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
        
        DNode(int data, DNode prev, DNode next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }
}