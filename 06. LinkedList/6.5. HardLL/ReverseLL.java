/**
 * Class for reversing linked list in groups of size k
 * 
 * Problem: Given a linked list, reverse the nodes of the list k at a time
 * and return the modified list. If the number of nodes is not a multiple of k,
 * then left-out nodes at the end should remain as is.
 * 
 * Example:
 * Input: 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8, k = 3
 * Output: 3 → 2 → 1 → 6 → 5 → 4 → 7 → 8
 * 
 * Approach: Recursive divide-and-conquer
 * 1. Check if there are at least k nodes
 * 2. Reverse first k nodes
 * 3. Recursively process remaining list
 * 4. Connect the reversed group to the result of recursion
 * 
 * Time Complexity: O(n) where n is number of nodes
 * Space Complexity: O(n/k) recursion stack depth
 */
public class ReverseLL {

    /**
     * Reverses linked list in groups of size k
     * 
     * @param head Head of the linked list
     * @param k Group size for reversal
     * @return Head of the modified list
     * 
     * Algorithm Steps:
     * 1. Edge cases: null head or k = 1 (no reversal needed)
     * 2. Check if at least k nodes exist from current position
     * 3. If less than k nodes, return head unchanged
     * 4. Reverse first k nodes using iterative reversal
     * 5. Recursively reverse remaining list in groups of k
     * 6. Connect current reversed group to result of recursion
     * 
     * Visual Example (k=3):
     * Step 1: 1 → 2 → 3 → 4 → 5 → 6 → 7 → 8
     * Step 2: Reverse first 3: 3 → 2 → 1
     * Step 3: Recursively process: 4 → 5 → 6 → 7 → 8
     * Step 4: Reverse next 3: 6 → 5 → 4
     * Step 5: Connect: 3 → 2 → 1 → 6 → 5 → 4 → 7 → 8
     */
    public static ListNode reverseKGroup(ListNode head, int k) {
        // Edge case 1: Empty list
        if (head == null) {
            System.out.println("Empty list, returning null");
            return null;
        }
        
        // Edge case 2: k = 1 means no reversal needed
        if (k <= 1) {
            System.out.println("k=" + k + ", no reversal needed");
            return head;
        }
        
        System.out.println("\n=== Starting reverseKGroup with k=" + k + " ===");
        
        // Step 1: Check if there are at least k nodes remaining
        ListNode curr = head;
        int count = 0;
        
        System.out.print("Checking if at least " + k + " nodes exist: ");
        while (curr != null && count < k) {
            curr = curr.next;
            count++;
        }
        
        System.out.println(count + " nodes found");
        
        // If less than k nodes, don't reverse this group
        if (count < k) {
            System.out.println("Less than " + k + " nodes, returning head unchanged");
            return head;
        }
        
        System.out.println("At least " + k + " nodes exist, proceeding with reversal");
        
        // Step 2: Reverse first k nodes iteratively
        System.out.println("\nReversing first " + k + " nodes:");
        
        ListNode prev = null;   // Will become the new head of reversed group
        ListNode next = null;   // Temporary storage for next node
        curr = head;            // Start from head
        
        count = 0;  // Reset counter for reversal
        
        while (curr != null && count < k) {
            next = curr.next;           // Save next node
            curr.next = prev;           // Reverse the link
            prev = curr;                // Move prev forward
            curr = next;                // Move curr forward
            count++;
            
            System.out.println("  Step " + count + ": reversed link, prev now at " + 
                             (prev != null ? prev.val : "null"));
        }
        
        // At this point:
        // - prev is the new head of reversed group (originally the k-th node)
        // - curr is the (k+1)-th node (head of remaining list)
        // - head is now the tail of reversed group
        
        System.out.println("\nAfter reversing first " + k + " nodes:");
        System.out.println("  New head of group: " + (prev != null ? prev.val : "null"));
        System.out.println("  Old head (now tail): " + head.val);
        System.out.println("  Next node to process: " + (curr != null ? curr.val : "null"));
        
        // Step 3: Recursively process remaining list
        System.out.println("\nRecursively processing remaining list...");
        if (curr != null) {
            // Note: head is now the tail of reversed group
            // Connect it to the result of reversing remaining list
            head.next = reverseKGroup(curr, k);
            System.out.println("Connected tail " + head.val + " to result of recursion");
        } else {
            System.out.println("No more nodes to process");
        }
        
        System.out.println("Returning new head: " + (prev != null ? prev.val : "null"));
        return prev;  // prev is the new head of this group
    }
    
    /**
     * Iterative approach using dummy node (O(1) space, no recursion)
     * More efficient for large lists
     */
    public static ListNode reverseKGroupIterative(ListNode head, int k) {
        if (head == null || k <= 1) return head;
        
        System.out.println("\n=== Iterative Approach ===");
        
        // Create dummy node to simplify edge cases
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        
        ListNode prevGroupTail = dummy;  // Tail of previous reversed group
        ListNode curr = head;
        int groupNum = 1;
        
        while (curr != null) {
            System.out.println("\nProcessing group " + groupNum++);
            
            // Step 1: Check if we have k nodes
            ListNode check = curr;
            int count = 0;
            while (check != null && count < k) {
                check = check.next;
                count++;
            }
            
            if (count < k) {
                System.out.println("  Only " + count + " nodes left, leaving as is");
                break;
            }
            
            // Step 2: Reverse k nodes
            ListNode groupPrev = null;
            ListNode groupHead = curr;  // Will become tail after reversal
            
            for (int i = 0; i < k && curr != null; i++) {
                ListNode next = curr.next;
                curr.next = groupPrev;
                groupPrev = curr;
                curr = next;
            }
            
            // Step 3: Connect previous group to current reversed group
            prevGroupTail.next = groupPrev;
            groupHead.next = curr;
            
            System.out.println("  Reversed group, connected to previous group");
            
            // Step 4: Update previous group tail for next iteration
            prevGroupTail = groupHead;
        }
        
        return dummy.next;
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
        System.out.print("List: ");
        
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
     * Visualizes the k-group reversal process
     */
    public static void visualizeKGroupReversal(ListNode head, int k) {
        System.out.println("\n=== Visualizing k-Group Reversal (k=" + k + ") ===");
        
        ListNode listCopy = copyList(head);
        printList(listCopy);
        
        System.out.println("\nStep-by-step process:");
        
        ListNode curr = listCopy;
        ListNode dummy = new ListNode(-1);
        dummy.next = listCopy;
        ListNode prevGroupTail = dummy;
        int groupNum = 1;
        
        while (curr != null) {
            System.out.println("\n--- Group " + groupNum + " ---");
            
            // Check if k nodes available
            ListNode check = curr;
            int count = 0;
            while (check != null && count < k) {
                check = check.next;
                count++;
            }
            
            if (count < k) {
                System.out.println("  Only " + count + " nodes left, leaving unchanged");
                break;
            }
            
            System.out.print("  Reversing nodes: ");
            ListNode groupHead = curr;
            for (int i = 0; i < k; i++) {
                System.out.print(curr.val + " ");
                curr = curr.next;
            }
            System.out.println();
            
            // Reverse the group
            ListNode reversedHead = reverseList(groupHead, k);
            
            // Connect
            prevGroupTail.next = reversedHead;
            groupHead.next = curr;
            
            System.out.print("  After reversal: ");
            printList(dummy.next);
            
            // Update for next group
            prevGroupTail = groupHead;
            groupNum++;
        }
        
        System.out.println("\nFinal result: ");
        printList(dummy.next);
    }
    
    /**
     * Helper to reverse a linked list of exact length k
     */
    private static ListNode reverseList(ListNode head, int k) {
        ListNode prev = null;
        ListNode curr = head;
        
        for (int i = 0; i < k; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        
        return prev;
    }
    
    /**
     * Helper to copy a linked list
     */
    private static ListNode copyList(ListNode head) {
        if (head == null) return null;
        
        ListNode newHead = new ListNode(head.val);
        ListNode original = head.next;
        ListNode copy = newHead;
        
        while (original != null) {
            copy.next = new ListNode(original.val);
            copy = copy.next;
            original = original.next;
        }
        
        return newHead;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Reverse Linked List in Groups of Size k ===\n");
        
        // Test Case 1: Perfect multiple of k
        System.out.println("Test Case 1: k=3, list length 9 (perfect multiple)");
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        ListNode list1 = createList(arr1);
        System.out.print("Original: ");
        printList(list1);
        
        visualizeKGroupReversal(copyList(list1), 3);
        
        ListNode result1 = reverseKGroup(list1, 3);
        System.out.print("Result (recursive): ");
        printList(result1);
        System.out.println("Expected: 3 → 2 → 1 → 6 → 5 → 4 → 9 → 8 → 7");
        System.out.println();
        
        // Test Case 2: Not a multiple of k
        System.out.println("Test Case 2: k=4, list length 7 (not a multiple)");
        int[] arr2 = {1, 2, 3, 4, 5, 6, 7};
        ListNode list2 = createList(arr2);
        System.out.print("Original: ");
        printList(list2);
        
        ListNode result2 = reverseKGroup(list2, 4);
        System.out.print("Result (recursive): ");
        printList(result2);
        System.out.println("Expected: 4 → 3 → 2 → 1 → 5 → 6 → 7");
        System.out.println();
        
        // Test Case 3: k=1 (no reversal)
        System.out.println("Test Case 3: k=1 (should return same list)");
        int[] arr3 = {1, 2, 3, 4, 5};
        ListNode list3 = createList(arr3);
        System.out.print("Original: ");
        printList(list3);
        
        ListNode result3 = reverseKGroup(list3, 1);
        System.out.print("Result: ");
        printList(result3);
        System.out.println("Expected same as original");
        System.out.println();
        
        // Test Case 4: k equals list length
        System.out.println("Test Case 4: k=5 (equal to list length)");
        int[] arr4 = {1, 2, 3, 4, 5};
        ListNode list4 = createList(arr4);
        System.out.print("Original: ");
        printList(list4);
        
        ListNode result4 = reverseKGroup(list4, 5);
        System.out.print("Result: ");
        printList(result4);
        System.out.println("Expected: 5 → 4 → 3 → 2 → 1");
        System.out.println();
        
        // Test Case 5: k larger than list length
        System.out.println("Test Case 5: k=10, list length 3 (k > length)");
        int[] arr5 = {1, 2, 3};
        ListNode list5 = createList(arr5);
        System.out.print("Original: ");
        printList(list5);
        
        ListNode result5 = reverseKGroup(list5, 10);
        System.out.print("Result: ");
        printList(result5);
        System.out.println("Expected same as original (no reversal)");
        System.out.println();
        
        // Test Case 6: Iterative approach
        System.out.println("Test Case 6: Iterative approach (k=2)");
        int[] arr6 = {1, 2, 3, 4, 5, 6, 7, 8};
        ListNode list6 = createList(arr6);
        System.out.print("Original: ");
        printList(list6);
        
        ListNode result6 = reverseKGroupIterative(list6, 2);
        System.out.print("Result (iterative): ");
        printList(result6);
        System.out.println("Expected: 2 → 1 → 4 → 3 → 6 → 5 → 8 → 7");
        System.out.println();
        
        // Test Case 7: Single node
        System.out.println("Test Case 7: Single node list, k=2");
        ListNode list7 = new ListNode(1);
        System.out.print("Original: ");
        printList(list7);
        
        ListNode result7 = reverseKGroup(list7, 2);
        System.out.print("Result: ");
        printList(result7);
        System.out.println("Expected same as original");
        System.out.println();
        
        // Test Case 8: Empty list
        System.out.println("Test Case 8: Empty list");
        ListNode list8 = null;
        System.out.print("Original: null");
        ListNode result8 = reverseKGroup(list8, 3);
        System.out.print("\nResult: ");
        printList(result8);
        System.out.println();
        
        // Complexity Analysis
        System.out.println("=== Algorithm Analysis ===");
        System.out.println("Recursive Approach:");
        System.out.println("  Time Complexity: O(n)");
        System.out.println("    - Each node visited exactly once");
        System.out.println("    - Each reversal of k nodes takes O(k) time");
        System.out.println("  Space Complexity: O(n/k)");
        System.out.println("    - Recursion depth = number of groups = n/k");
        System.out.println("\nIterative Approach:");
        System.out.println("  Time Complexity: O(n)");
        System.out.println("  Space Complexity: O(1)");
        System.out.println("    - Uses only constant extra space");
        System.out.println("\nKey Insight:");
        System.out.println("  - Reverse groups independently");
        System.out.println("  - Connect reversed groups properly");
        System.out.println("  - Handle leftover nodes at the end");
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