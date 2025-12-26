/**
 * Class demonstrating iterative linked list reversal
 * Time Complexity: O(n) where n is the number of nodes
 * Space Complexity: O(1) - constant space, only uses a few pointers
 */
public class ReverseLL {

    /**
     * Iteratively reverses a singly linked list
     * 
     * @param head The head node of the linked list to reverse
     * @return The new head of the reversed linked list
     * 
     * Algorithm Steps:
     * 1. Initialize three pointers:
     *    - prev: tracks the previous node (starts as null, will become the new tail)
     *    - curr: tracks the current node being processed (starts at head)
     *    - next: temporary storage for the next node in original list
     *    
     * 2. Traverse through the list until curr becomes null:
     *    a. Save the next node (curr.next) to prevent losing reference
     *    b. Reverse the link: point curr.next to prev
     *    c. Move prev forward: prev = curr (prev now becomes the current node)
     *    d. Move curr forward: curr = next (curr advances to the saved next node)
     *    
     * 3. When loop ends, prev will be pointing to the last node of original list,
     *    which is now the first node of the reversed list
     */
    public static ListNode reverseIterative(ListNode head) {
        // Step 1: Initialize pointers
        ListNode prev = null;    // Previous node starts as null (end of reversed list)
        ListNode curr = head;    // Start with the head of the original list
        
        // Step 2: Traverse through the entire list
        while (curr != null) {
            // Critical: Save the next node BEFORE modifying curr.next
            // Otherwise, we lose access to the rest of the original list
            ListNode next = curr.next;
            
            // Reverse the link: current node now points backward instead of forward
            curr.next = prev;
            
            // Move prev forward to current position for next iteration
            prev = curr;
            
            // Move curr forward to the saved next node
            curr = next;
            
            // Visual representation of one iteration:
            // Before: prev -> null, curr -> A -> B -> C -> null
            // After saving next: next points to B
            // After curr.next = prev: A -> null
            // After prev = curr: prev points to A
            // After curr = next: curr points to B
            // Result: null <- A, B -> C -> null
        }
        
        // Step 3: Return new head (prev now points to last node of original list)
        return prev;
    }
    
    /**
     * Helper method to print linked list (for demonstration purposes)
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
     * Main method with test cases demonstrating the reversal
     */
    public static void main(String[] args) {
        // Test Case 1: Normal linked list
        System.out.println("Test Case 1: Normal linked list");
        ListNode head1 = new ListNode(1);
        head1.next = new ListNode(2);
        head1.next.next = new ListNode(3);
        head1.next.next.next = new ListNode(4);
        
        System.out.print("Original: ");
        printList(head1);
        
        ListNode reversed1 = reverseIterative(head1);
        System.out.print("Reversed: ");
        printList(reversed1);
        System.out.println();
        
        // Test Case 2: Single node list
        System.out.println("Test Case 2: Single node list");
        ListNode head2 = new ListNode(1);
        
        System.out.print("Original: ");
        printList(head2);
        
        ListNode reversed2 = reverseIterative(head2);
        System.out.print("Reversed: ");
        printList(reversed2);
        System.out.println();
        
        // Test Case 3: Empty list (null head)
        System.out.println("Test Case 3: Empty list");
        ListNode head3 = null;
        
        System.out.print("Original: ");
        printList(head3);
        
        ListNode reversed3 = reverseIterative(head3);
        System.out.print("Reversed: ");
        printList(reversed3);
        System.out.println();
        
        // Test Case 4: Two node list
        System.out.println("Test Case 4: Two node list");
        ListNode head4 = new ListNode(1);
        head4.next = new ListNode(2);
        
        System.out.print("Original: ");
        printList(head4);
        
        ListNode reversed4 = reverseIterative(head4);
        System.out.print("Reversed: ");
        printList(reversed4);
    }
    
    /**
     * Definition for singly-linked list node
     * (Assuming this inner class exists or is imported)
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