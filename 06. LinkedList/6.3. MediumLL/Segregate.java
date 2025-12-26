/**
 * Class for segregating even and odd valued nodes in a linked list
 * 
 * Problem: Given a linked list, rearrange it such that all even-valued nodes
 * appear before all odd-valued nodes, while maintaining:
 * 1. Relative order of even nodes (as they appeared originally)
 * 2. Relative order of odd nodes (as they appeared originally)
 * 
 * Example:
 * Input:  1 → 2 → 3 → 4 → 5 → 6 → null
 * Output: 2 → 4 → 6 → 1 → 3 → 5 → null
 * 
 * Time Complexity: O(n) where n is number of nodes (single pass)
 * Space Complexity: O(1) (uses only constant extra space, no new nodes created)
 * 
 * Approach: Four-pointer technique using separate even and odd lists
 */
public class Segregate {

    /**
     * Segregates even and odd valued nodes while preserving relative order
     * 
     * @param head The head node of the linked list to segregate
     * @return Head of the segregated linked list (even nodes first, then odd nodes)
     * 
     * Algorithm Steps:
     * 1. Create four pointers:
     *    - evenHead, evenTail: For building even nodes list
     *    - oddHead, oddTail: For building odd nodes list
     *    
     * 2. Traverse the original list:
     *    - For each node, check if value is even or odd
     *    - Append to appropriate list (even or odd)
     *    - Maintain tail pointers for O(1) appends
     *    
     * 3. Connect the two lists:
     *    - evenTail.next = oddHead (link even list to odd list)
     *    - oddTail.next = null (terminate the final list)
     *    
     * 4. Return appropriate head:
     *    - If even list exists: return evenHead
     *    - If no even nodes: return oddHead
     * 
     * Note: This approach preserves the relative order within each group because
     * we process nodes sequentially and append them to their respective lists.
     */
    public static ListNode segregateEvenOdd(ListNode head) {
        // Edge case: empty list
        if (head == null) return null;
        
        // Initialize pointers for even and odd lists
        ListNode evenHead = null, evenTail = null;  // Head and tail of even list
        ListNode oddHead = null, oddTail = null;    // Head and tail of odd list
        ListNode curr = head;                       // Traversal pointer
        
        // Step 1: Single pass through original list
        while (curr != null) {
            // Check if current node value is even (curr.val % 2 == 0)
            if (curr.val % 2 == 0) {
                // Append to even list
                if (evenHead == null) {
                    // First even node - initialize both head and tail
                    evenHead = evenTail = curr;
                } else {
                    // Append to existing even list
                    evenTail.next = curr;   // Link current tail to new node
                    evenTail = evenTail.next; // Move tail to new node
                }
            } else {
                // Append to odd list
                if (oddHead == null) {
                    // First odd node - initialize both head and tail
                    oddHead = oddTail = curr;
                } else {
                    // Append to existing odd list
                    oddTail.next = curr;    // Link current tail to new node
                    oddTail = oddTail.next;  // Move tail to new node
                }
            }
            
            // IMPORTANT: Move to next node in original list
            // We save next before potentially modifying curr.next in appends above
            // This ensures we don't lose the original chain
            ListNode nextNode = curr.next;
            
            // Disconnect current node from original list to prevent cycles
            // This is safe because we're adding nodes to new lists
            curr.next = null;
            
            curr = nextNode;  // Move to next node
        }
        
        // Step 2: Connect even list to odd list
        if (evenTail != null) {
            // Even list exists - connect it to odd list
            evenTail.next = oddHead;
        }
        
        // Step 3: Ensure proper termination
        if (oddTail != null) {
            // Odd list exists - make sure it ends with null
            oddTail.next = null;
        }
        
        // Step 4: Return appropriate head
        // If even list exists, return its head, otherwise return odd head
        return (evenHead != null) ? evenHead : oddHead;
    }
    
    /**
     * Alternative approach using dummy nodes for cleaner code
     * Uses dummy nodes to avoid null checks for head pointers
     */
    public static ListNode segregateEvenOddWithDummy(ListNode head) {
        if (head == null) return null;
        
        // Create dummy nodes for even and odd lists
        // Dummy nodes simplify the code by avoiding null checks
        ListNode evenDummy = new ListNode(-1);  // Dummy head for even list
        ListNode oddDummy = new ListNode(-1);   // Dummy head for odd list
        
        ListNode evenTail = evenDummy;  // Tail of even list
        ListNode oddTail = oddDummy;    // Tail of odd list
        ListNode curr = head;           // Traversal pointer
        
        while (curr != null) {
            if (curr.val % 2 == 0) {
                // Append to even list
                evenTail.next = curr;
                evenTail = evenTail.next;
            } else {
                // Append to odd list
                oddTail.next = curr;
                oddTail = oddTail.next;
            }
            
            // Move to next node
            ListNode nextNode = curr.next;
            curr.next = null;  // Disconnect from original list
            curr = nextNode;
        }
        
        // Connect even list to odd list
        evenTail.next = oddDummy.next;  // Skip dummy node
        
        // Return head of even list (skip dummy node)
        return evenDummy.next;
    }
    
    /**
     * Helper method to create a linked list from array
     */
    public static ListNode createList(int[] values) {
        if (values == null || values.length == 0) return null;
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        return head;
    }
    
    /**
     * Helper method to print linked list
     */
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" → ");
            }
            current = current.next;
        }
        System.out.println(" → null");
    }
    
    /**
     * Helper method to visualize the segregation process
     */
    public static void visualizeSegregation(ListNode head) {
        System.out.println("\nVisualizing Segregation Process:");
        System.out.println("=================================");
        
        ListNode evenHead = null, evenTail = null;
        ListNode oddHead = null, oddTail = null;
        ListNode curr = head;
        int step = 1;
        
        while (curr != null) {
            System.out.println("\nStep " + step + ": Processing node " + curr.val);
            
            if (curr.val % 2 == 0) {
                System.out.println("  Node " + curr.val + " is EVEN");
                if (evenHead == null) {
                    evenHead = evenTail = curr;
                    System.out.println("  Even list created: [" + curr.val + "]");
                } else {
                    evenTail.next = curr;
                    evenTail = evenTail.next;
                    System.out.println("  Appended to even list");
                }
            } else {
                System.out.println("  Node " + curr.val + " is ODD");
                if (oddHead == null) {
                    oddHead = oddTail = curr;
                    System.out.println("  Odd list created: [" + curr.val + "]");
                } else {
                    oddTail.next = curr;
                    oddTail = oddTail.next;
                    System.out.println("  Appended to odd list");
                }
            }
            
            // Print current state
            System.out.print("  Even list: ");
            printList(evenHead);
            System.out.print("  Odd list: ");
            printList(oddHead);
            
            curr = curr.next;
            step++;
        }
        
        // Connect lists
        if (evenTail != null) {
            evenTail.next = oddHead;
            System.out.println("\nConnecting even list to odd list");
        }
        
        if (oddTail != null) {
            oddTail.next = null;
            System.out.println("Terminating list with null");
        }
        
        ListNode result = (evenHead != null) ? evenHead : oddHead;
        System.out.print("\nFinal segregated list: ");
        printList(result);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Testing Even-Odd Segregation ===\n");
        
        // Test Case 1: Mixed even and odd numbers
        System.out.println("Test Case 1: Mixed list");
        int[] arr1 = {1, 2, 3, 4, 5, 6};
        ListNode list1 = createList(arr1);
        System.out.print("Original: ");
        printList(list1);
        System.out.print("Segregated: ");
        ListNode result1 = segregateEvenOdd(list1);
        printList(result1);
        visualizeSegregation(createList(arr1));
        System.out.println();
        
        // Test Case 2: All even numbers
        System.out.println("Test Case 2: All even numbers");
        int[] arr2 = {2, 4, 6, 8};
        ListNode list2 = createList(arr2);
        System.out.print("Original: ");
        printList(list2);
        System.out.print("Segregated: ");
        ListNode result2 = segregateEvenOdd(list2);
        printList(result2);
        System.out.println();
        
        // Test Case 3: All odd numbers
        System.out.println("Test Case 3: All odd numbers");
        int[] arr3 = {1, 3, 5, 7};
        ListNode list3 = createList(arr3);
        System.out.print("Original: ");
        printList(list3);
        System.out.print("Segregated: ");
        ListNode result3 = segregateEvenOdd(list3);
        printList(result3);
        System.out.println();
        
        // Test Case 4: Single element
        System.out.println("Test Case 4: Single element (even)");
        ListNode list4 = new ListNode(10);
        System.out.print("Original: ");
        printList(list4);
        System.out.print("Segregated: ");
        ListNode result4 = segregateEvenOdd(list4);
        printList(result4);
        System.out.println();
        
        // Test Case 5: Empty list
        System.out.println("Test Case 5: Empty list");
        ListNode list5 = null;
        System.out.print("Original: ");
        printList(list5);
        System.out.print("Segregated: ");
        ListNode result5 = segregateEvenOdd(list5);
        printList(result5);
        System.out.println();
        
        // Test Case 6: Already segregated
        System.out.println("Test Case 6: Already segregated (even first, then odd)");
        int[] arr6 = {2, 4, 6, 1, 3, 5};
        ListNode list6 = createList(arr6);
        System.out.print("Original: ");
        printList(list6);
        System.out.print("Segregated: ");
        ListNode result6 = segregateEvenOdd(list6);
        printList(result6);
        System.out.println();
        
        // Test Case 7: Using dummy node approach
        System.out.println("Test Case 7: Using dummy node approach");
        int[] arr7 = {1, 2, 3, 4, 5};
        ListNode list7 = createList(arr7);
        System.out.print("Original: ");
        printList(list7);
        System.out.print("Segregated (dummy): ");
        ListNode result7 = segregateEvenOddWithDummy(list7);
        printList(result7);
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