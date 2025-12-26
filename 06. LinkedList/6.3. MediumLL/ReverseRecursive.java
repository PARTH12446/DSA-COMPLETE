/**
 * Class demonstrating recursive linked list reversal
 * Time Complexity: O(n) where n is the number of nodes (visits each node once)
 * Space Complexity: O(n) due to recursion stack (n recursive calls for n nodes)
 * 
 * Recursive Approach Benefits:
 * - Elegant, intuitive solution
 * - Naturally follows the recursive structure of linked lists
 * - Useful for recursive thinking practice
 * 
 * Recursive Approach Drawbacks:
 * - Uses O(n) stack space (can cause stack overflow for very long lists)
 * - Generally slightly slower than iterative due to function call overhead
 */
public class ReverseRecursive {

    /**
     * Recursively reverses a singly linked list
     * 
     * @param head The head node of the linked list to reverse
     * @return The new head of the reversed linked list
     * 
     * Algorithm Steps:
     * 1. Base Case: If head is null (empty list) or head.next is null (single node),
     *    return head as is (no reversal needed)
     *    
     * 2. Recursive Case:
     *    a. Recursively reverse the sublist starting from head.next
     *    b. After recursion returns, we have:
     *       - newHead: the head of the reversed sublist
     *       - head: the original current node
     *    c. Make head.next (which is now the last node of reversed sublist) point back to head
     *    d. Set head.next to null to make head the new tail
     *    e. Return newHead (which remains the head of the completely reversed list)
     */
    public static ListNode reverseRecursive(ListNode head) {
        // Base Case 1: Empty list
        // Base Case 2: Single node list (head.next == null)
        // In both cases, the list is already "reversed" (or doesn't need reversal)
        if (head == null || head.next == null) {
            return head;
        }
        
        // Step 1: Recursively reverse the REST of the list
        // This call will reverse everything from head.next onwards
        // When this returns, newHead points to the last node of original list
        // which becomes the first node of the reversed list
        ListNode newHead = reverseRecursive(head.next);
        
        // Step 2: Reversal at current level
        // At this point in recursion unwinding:
        // - head points to current node (e.g., node 1 in original list)
        // - head.next points to next node (e.g., node 2)
        // - newHead points to the last node of original list (e.g., node 4)
        
        // Critical Step: Make the next node point back to current node
        // head.next is actually the last node of the reversed sublist
        // We make it point back to head (reverse the arrow)
        head.next.next = head;
        
        // Step 3: Break the forward link from current node
        // Current node becomes the new tail of the reversed sublist
        // So its next should be null (will be updated in previous recursion level)
        head.next = null;
        
        // Step 4: Propagate the newHead up the recursion chain
        // newHead remains unchanged throughout unwinding
        // It's always the last node of original list (first node of reversed list)
        return newHead;
    }
    
    /**
     * Alternative recursive approach with helper function
     * This version might be easier to understand for some
     */
    public static ListNode reverseRecursiveAlternative(ListNode head) {
        return reverseHelper(head, null);
    }
    
    /**
     * Helper function for tail-recursive approach
     * 
     * @param curr Current node being processed
     * @param prev Previous node in the reversed list
     * @return New head of reversed list
     * 
     * This is tail-recursive: the recursive call is the last operation
     * Some compilers can optimize this to use constant stack space
     */
    private static ListNode reverseHelper(ListNode curr, ListNode prev) {
        // Base case: reached end of original list
        if (curr == null) {
            return prev;  // prev is the new head
        }
        
        // Save next node before modifying curr.next
        ListNode next = curr.next;
        
        // Reverse the link
        curr.next = prev;
        
        // Recursive call with updated pointers
        // Move forward in original list (curr becomes next)
        // Move backward in building reversed list (prev becomes curr)
        return reverseHelper(next, curr);
    }
    
    /**
     * Helper method to print linked list
     */
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }
    
    /**
     * Main method with test cases and visualization
     */
    public static void main(String[] args) {
        System.out.println("=== Testing Recursive Linked List Reversal ===\n");
        
        // Test Case 1: 1 -> 2 -> 3 -> 4 -> null
        System.out.println("Test Case 1: 1 -> 2 -> 3 -> 4 -> null");
        ListNode head1 = createList(1, 2, 3, 4);
        
        System.out.print("Original: ");
        printList(head1);
        
        ListNode reversed1 = reverseRecursive(head1);
        System.out.print("Reversed: ");
        printList(reversed1);
        
        System.out.println("\nRecursion Stack Visualization for 1->2->3->4:");
        visualizeRecursion(createList(1, 2, 3, 4));
        System.out.println();
        
        // Test Case 2: Single node
        System.out.println("Test Case 2: Single node list");
        ListNode head2 = new ListNode(5);
        System.out.print("Original: ");
        printList(head2);
        System.out.print("Reversed: ");
        printList(reverseRecursive(head2));
        System.out.println();
        
        // Test Case 3: Empty list
        System.out.println("Test Case 3: Empty list");
        ListNode head3 = null;
        System.out.print("Original: ");
        printList(head3);
        System.out.print("Reversed: ");
        printList(reverseRecursive(head3));
        System.out.println();
        
        // Test Case 4: Using alternative tail-recursive approach
        System.out.println("Test Case 4: Alternative approach (1 -> 2 -> 3)");
        ListNode head4 = createList(1, 2, 3);
        System.out.print("Original: ");
        printList(head4);
        System.out.print("Reversed (alt): ");
        printList(reverseRecursiveAlternative(head4));
    }
    
    /**
     * Helper to create a linked list from variable arguments
     */
    private static ListNode createList(int... values) {
        if (values.length == 0) return null;
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }
    
    /**
     * Visualizes the recursion stack for educational purposes
     */
    private static void visualizeRecursion(ListNode head) {
        System.out.println("Recursive Call Stack (for list 1->2->3->4):");
        System.out.println("---------------------------------------------");
        
        // Simulating the recursion
        System.out.println("reverse(1):");
        System.out.println("  │-- Calls reverse(2)");
        System.out.println("  │     │-- Calls reverse(3)");
        System.out.println("  │     │     │-- Calls reverse(4)");
        System.out.println("  │     │     │     Base case: returns 4 (head.next is null)");
        System.out.println("  │     │     │");
        System.out.println("  │     │     At reverse(3):");
        System.out.println("  │     │     head = 3, head.next = 4");
        System.out.println("  │     │     head.next.next = head  // 4.next = 3");
        System.out.println("  │     │     head.next = null       // 3.next = null");
        System.out.println("  │     │     Returns newHead = 4");
        System.out.println("  │     │");
        System.out.println("  │     At reverse(2):");
        System.out.println("  │     head = 2, head.next = 3");
        System.out.println("  │     head.next.next = head  // 3.next = 2");
        System.out.println("  │     head.next = null       // 2.next = null");
        System.out.println("  │     Returns newHead = 4");
        System.out.println("  │");
        System.out.println("  At reverse(1):");
        System.out.println("  head = 1, head.next = 2");
        System.out.println("  head.next.next = head  // 2.next = 1");
        System.out.println("  head.next = null       // 1.next = null");
        System.out.println("  Returns newHead = 4");
        System.out.println();
        System.out.println("Final: 4 -> 3 -> 2 -> 1 -> null");
    }
    
    /**
     * Definition for singly-linked list node
     */
    static class ListNode {
        int val;
        ListNode next;
        
        ListNode(int val) {
            this.val = val;
            this.next = null;
        }
        
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}