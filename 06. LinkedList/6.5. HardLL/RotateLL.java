/**
 * Class for rotating a linked list to the right by k places
 * 
 * Problem: Given a linked list, rotate the list to the right by k places,
 * where k is non-negative.
 * 
 * Example:
 * Input:  1 → 2 → 3 → 4 → 5 → null, k = 2
 * Output: 4 → 5 → 1 → 2 → 3 → null
 * 
 * Explanation:
 * Rotate 1: 5 → 1 → 2 → 3 → 4 → null
 * Rotate 2: 4 → 5 → 1 → 2 → 3 → null
 * 
 * Approach:
 * 1. Find the length of the list and the tail node
 * 2. Calculate effective k = k % length (handle k > length)
 * 3. If k == 0, no rotation needed
 * 4. Connect tail to head to form a cycle
 * 5. Find new tail (length - k steps from original tail)
 * 6. Break the cycle at new tail to create new head
 * 
 * Time Complexity: O(n) where n is number of nodes
 * Space Complexity: O(1) - uses only constant extra space
 */
public class RotateLL {

    /**
     * Rotates linked list to the right by k places
     * 
     * @param head Head of the linked list
     * @param k Number of positions to rotate right
     * @return Head of the rotated list
     * 
     * Algorithm Steps:
     * 1. Handle edge cases: empty list, single node, or k=0
     * 2. Calculate list length and find tail
     * 3. Compute effective rotation: k % length (optimize large k)
     * 4. If effective k == 0, return original head
     * 5. Make list circular by connecting tail to head
     * 6. Find new tail by moving (length - k) steps from current tail
     * 7. New head is newTail.next
     * 8. Break the cycle by setting newTail.next = null
     * 
     * Key Insight:
     * Rotating right by k places is equivalent to:
     * - Moving last k nodes to the front
     * - OR moving first (n-k) nodes to the end
     */
    public static ListNode rotateRight(ListNode head, int k) {
        // Step 1: Handle edge cases
        if (head == null || head.next == null || k == 0) {
            System.out.println("Edge case: " + 
                (head == null ? "empty list" : 
                 head.next == null ? "single node" : "k=0"));
            return head;
        }
        
        System.out.println("\n=== Starting Rotation by k=" + k + " ===");
        
        // Step 2: Calculate list length and find tail
        System.out.println("Step 1: Find length and tail");
        int len = 1;  // Start with head
        ListNode tail = head;
        
        while (tail.next != null) {
            tail = tail.next;
            len++;
        }
        
        System.out.println("  Length = " + len);
        System.out.println("  Tail node value = " + tail.val);
        
        // Step 3: Optimize k (handle k > len)
        k = k % len;
        System.out.println("Step 2: Effective k = " + k + " (after k % " + len + ")");
        
        if (k == 0) {
            System.out.println("  Effective k = 0, no rotation needed");
            return head;
        }
        
        // Step 4: Make the list circular
        System.out.println("Step 3: Make list circular (connect tail to head)");
        tail.next = head;  // Create cycle
        System.out.println("  tail(" + tail.val + ").next = head(" + head.val + ")");
        
        // Step 5: Find new tail (which will be len-k steps from original tail)
        int stepsToNewTail = len - k;
        System.out.println("Step 4: Find new tail");
        System.out.println("  Need to move " + stepsToNewTail + " steps from current tail");
        
        ListNode newTail = tail;  // Start from current tail
        for (int i = 0; i < stepsToNewTail; i++) {
            newTail = newTail.next;
            System.out.println("  Step " + (i+1) + ": newTail now at node " + newTail.val);
        }
        
        // Step 6: Determine new head and break the cycle
        ListNode newHead = newTail.next;
        System.out.println("Step 5: Determine new head");
        System.out.println("  newHead = newTail.next = node " + newHead.val);
        
        newTail.next = null;  // Break the cycle
        System.out.println("  Break cycle: newTail(" + newTail.val + ").next = null");
        
        System.out.println("Step 6: Rotation complete!");
        System.out.println("  New head: node " + newHead.val);
        System.out.println("  New tail: node " + newTail.val);
        
        return newHead;
    }
    
    /**
     * Alternative approach without creating a cycle
     * Uses two-pointer technique
     */
    public static ListNode rotateRightNoCycle(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;
        
        System.out.println("\n=== Alternative Approach (No Cycle) ===");
        
        // Step 1: Find length and tail
        int len = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            len++;
        }
        
        k = k % len;
        if (k == 0) return head;
        
        System.out.println("Length = " + len + ", Effective k = " + k);
        
        // Step 2: Find the (len-k)th node (new tail)
        ListNode newTail = head;
        for (int i = 1; i < len - k; i++) {
            newTail = newTail.next;
        }
        
        System.out.println("New tail found at node " + newTail.val);
        
        // Step 3: Rearrange pointers
        ListNode newHead = newTail.next;
        newTail.next = null;  // Break at new tail
        tail.next = head;     // Connect original tail to original head
        
        System.out.println("New head = node " + newHead.val);
        System.out.println("Connected original tail to original head");
        
        return newHead;
    }
    
    /**
     * Rotates left by k places (opposite direction)
     */
    public static ListNode rotateLeft(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) return head;
        
        System.out.println("\n=== Rotate Left by k=" + k + " ===");
        
        // Find length
        int len = 1;
        ListNode tail = head;
        while (tail.next != null) {
            tail = tail.next;
            len++;
        }
        
        k = k % len;
        if (k == 0) return head;
        
        System.out.println("Length = " + len + ", Effective k = " + k);
        
        // Find new tail (k-th node)
        ListNode newTail = head;
        for (int i = 1; i < k; i++) {
            newTail = newTail.next;
        }
        
        ListNode newHead = newTail.next;
        newTail.next = null;
        tail.next = head;
        
        System.out.println("New head = node " + newHead.val);
        System.out.println("New tail = node " + newTail.val);
        
        return newHead;
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
     * Visualizes the rotation process step by step
     */
    public static void visualizeRotation(ListNode head, int k, boolean right) {
        System.out.println("\n=== Visualizing " + (right ? "Right" : "Left") + 
                         " Rotation by " + k + " ===");
        
        ListNode listCopy = copyList(head);
        printList(listCopy);
        
        if (listCopy == null || listCopy.next == null) {
            System.out.println("List too short to rotate");
            return;
        }
        
        // Find length and tail
        int len = 1;
        ListNode tail = listCopy;
        while (tail.next != null) {
            tail = tail.next;
            len++;
        }
        
        System.out.println("\nStep 1: Length = " + len + ", Tail = node " + tail.val);
        
        int effectiveK = k % len;
        System.out.println("Step 2: Effective k = " + effectiveK + " (after k % " + len + ")");
        
        if (effectiveK == 0) {
            System.out.println("No rotation needed");
            return;
        }
        
        if (right) {
            // Right rotation
            System.out.println("Step 3: Connect tail to head to form cycle");
            tail.next = listCopy;
            System.out.println("  Created: " + tail.val + " → " + listCopy.val);
            
            System.out.println("\nStep 4: Find new tail");
            System.out.println("  Need to move " + (len - effectiveK) + " steps from current tail");
            
            ListNode newTail = tail;
            for (int i = 0; i < len - effectiveK; i++) {
                newTail = newTail.next;
                System.out.println("  Move " + (i+1) + ": now at node " + newTail.val);
            }
            
            ListNode newHead = newTail.next;
            System.out.println("\nStep 5: New head = newTail.next = node " + newHead.val);
            
            newTail.next = null;
            System.out.println("Step 6: Break cycle at new tail");
            
            System.out.print("\nFinal rotated list: ");
            printList(newHead);
        } else {
            // Left rotation
            System.out.println("Step 3: Find new tail (k-th node)");
            ListNode newTail = listCopy;
            for (int i = 1; i < effectiveK; i++) {
                newTail = newTail.next;
                System.out.println("  Move " + i + ": now at node " + newTail.val);
            }
            
            ListNode newHead = newTail.next;
            System.out.println("\nStep 4: New head = newTail.next = node " + newHead.val);
            
            tail.next = listCopy;
            newTail.next = null;
            
            System.out.println("Step 5: Connect original tail to original head");
            System.out.println("Step 6: Break at new tail");
            
            System.out.print("\nFinal rotated list: ");
            printList(newHead);
        }
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
        System.out.println("=== Rotate Linked List ===\n");
        
        // Test Case 1: Right rotation, k < length
        System.out.println("Test Case 1: Rotate right by 2");
        int[] arr1 = {1, 2, 3, 4, 5};
        ListNode list1 = createList(arr1);
        System.out.print("Original: ");
        printList(list1);
        
        visualizeRotation(copyList(list1), 2, true);
        
        ListNode result1 = rotateRight(list1, 2);
        System.out.print("Result: ");
        printList(result1);
        System.out.println("Expected: 4 → 5 → 1 → 2 → 3");
        System.out.println();
        
        // Test Case 2: Right rotation, k = length
        System.out.println("Test Case 2: Rotate right by 5 (k = length)");
        int[] arr2 = {1, 2, 3, 4, 5};
        ListNode list2 = createList(arr2);
        System.out.print("Original: ");
        printList(list2);
        
        ListNode result2 = rotateRight(list2, 5);
        System.out.print("Result: ");
        printList(result2);
        System.out.println("Expected same as original (complete rotation)");
        System.out.println();
        
        // Test Case 3: Right rotation, k > length
        System.out.println("Test Case 3: Rotate right by 8 (k > length, length=5)");
        int[] arr3 = {1, 2, 3, 4, 5};
        ListNode list3 = createList(arr3);
        System.out.print("Original: ");
        printList(list3);
        
        ListNode result3 = rotateRight(list3, 8);
        System.out.print("Result: ");
        printList(result3);
        System.out.println("Expected: 3 → 4 → 5 → 1 → 2 (8 % 5 = 3)");
        System.out.println();
        
        // Test Case 4: Single node
        System.out.println("Test Case 4: Single node list, k=3");
        ListNode list4 = new ListNode(1);
        System.out.print("Original: ");
        printList(list4);
        
        ListNode result4 = rotateRight(list4, 3);
        System.out.print("Result: ");
        printList(result4);
        System.out.println("Expected same as original (single node)");
        System.out.println();
        
        // Test Case 5: Empty list
        System.out.println("Test Case 5: Empty list");
        ListNode list5 = null;
        System.out.print("Original: null");
        ListNode result5 = rotateRight(list5, 3);
        System.out.print("\nResult: ");
        printList(result5);
        System.out.println();
        
        // Test Case 6: Left rotation
        System.out.println("Test Case 6: Rotate left by 2");
        int[] arr6 = {1, 2, 3, 4, 5};
        ListNode list6 = createList(arr6);
        System.out.print("Original: ");
        printList(list6);
        
        visualizeRotation(copyList(list6), 2, false);
        
        ListNode result6 = rotateLeft(list6, 2);
        System.out.print("Result: ");
        printList(result6);
        System.out.println("Expected: 3 → 4 → 5 → 1 → 2");
        System.out.println();
        
        // Test Case 7: Alternative approach (no cycle)
        System.out.println("Test Case 7: Alternative approach (no cycle)");
        int[] arr7 = {10, 20, 30, 40, 50};
        ListNode list7 = createList(arr7);
        System.out.print("Original: ");
        printList(list7);
        
        ListNode result7 = rotateRightNoCycle(list7, 3);
        System.out.print("Result (no cycle): ");
        printList(result7);
        System.out.println("Expected: 30 → 40 → 50 → 10 → 20");
        System.out.println();
        
        // Test Case 8: k = 0
        System.out.println("Test Case 8: k = 0 (no rotation)");
        int[] arr8 = {1, 2, 3, 4, 5};
        ListNode list8 = createList(arr8);
        System.out.print("Original: ");
        printList(list8);
        
        ListNode result8 = rotateRight(list8, 0);
        System.out.print("Result: ");
        printList(result8);
        System.out.println("Expected same as original");
        System.out.println();
        
        // Test Case 9: Two nodes
        System.out.println("Test Case 9: Two nodes, rotate right by 1");
        ListNode list9 = createList(new int[]{1, 2});
        System.out.print("Original: ");
        printList(list9);
        
        ListNode result9 = rotateRight(list9, 1);
        System.out.print("Result: ");
        printList(result9);
        System.out.println("Expected: 2 → 1");
        System.out.println();
        
        // Mathematical insight
        System.out.println("=== Mathematical Insight ===");
        System.out.println("Right rotation by k places:");
        System.out.println("  - Move last k nodes to the front");
        System.out.println("  - OR move first (n-k) nodes to the end");
        System.out.println("\nLeft rotation by k places:");
        System.out.println("  - Move first k nodes to the end");
        System.out.println("  - OR move last (n-k) nodes to the front");
        System.out.println("\nRelationship:");
        System.out.println("  rotateRight(head, k) == rotateLeft(head, n-k)");
        
        // Complexity Analysis
        System.out.println("\n=== Algorithm Analysis ===");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Finding length and tail: O(n)");
        System.out.println("  - Finding new tail: O(n) in worst case");
        System.out.println("  - Total: O(2n) = O(n)");
        System.out.println("\nSpace Complexity: O(1)");
        System.out.println("  - Uses only constant extra pointers");
        System.out.println("\nKey Optimization:");
        System.out.println("  k = k % length  // Handle k > length efficiently");
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