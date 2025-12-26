/**
 * Class for sorting a linked list containing only 0s, 1s, and 2s (Dutch National Flag Problem)
 * 
 * Problem: Given a linked list containing only nodes with values 0, 1, and 2,
 * sort it in non-decreasing order (all 0s first, then 1s, then 2s)
 * 
 * Example:
 * Input:  1 → 0 → 2 → 1 → 2 → 0 → 1 → null
 * Output: 0 → 0 → 1 → 1 → 1 → 2 → 2 → null
 * 
 * Approach 1: Counting Sort (implemented here)
 * Approach 2: Three-pointer (Dutch National Flag) in-place rearrangement
 * 
 * Time Complexity: O(n) where n is number of nodes
 * Space Complexity: O(1) for counting array (fixed size 3)
 */
public class Sort012 {

    /**
     * Sorts a linked list containing only 0s, 1s, and 2s using counting approach
     * 
     * @param head The head node of the linked list to sort
     * @return Head of the sorted linked list (same head, values modified)
     * 
     * Algorithm Steps (Counting Sort):
     * 1. Count Phase: Traverse list once to count occurrences of 0s, 1s, and 2s
     * 2. Overwrite Phase: Traverse list again, overwriting values in sorted order
     * 
     * Advantages:
     * - Simple and intuitive
     * - Guaranteed O(2n) = O(n) time
     * - Preserves nodes (only modifies values)
     * 
     * Disadvantages:
     * - Requires two passes
     * - Modifies node values (not always allowed)
     */
    public static ListNode sort012(ListNode head) {
        // Edge case: empty list or single node
        if (head == null || head.next == null) return head;
        
        // Step 1: Initialize count array for values 0, 1, 2
        // count[0] = number of 0s, count[1] = number of 1s, count[2] = number of 2s
        int[] count = new int[3];
        
        // Step 2: First pass - count occurrences of each value
        ListNode curr = head;
        while (curr != null) {
            // Validate value is 0, 1, or 2 (optional safety check)
            if (curr.val >= 0 && curr.val <= 2) {
                count[curr.val]++;  // Increment count for this value
            } else {
                // Handle invalid value (optional - could throw exception)
                System.err.println("Warning: Node with value " + curr.val + 
                                 " found. Expected only 0, 1, or 2.");
            }
            curr = curr.next;
        }
        
        // Step 3: Second pass - overwrite values in sorted order
        curr = head;
        int value = 0;  // Start with 0s
        
        while (curr != null) {
            // Skip values that have count 0
            while (value < 3 && count[value] == 0) {
                value++;
            }
            
            // If we've processed all values (0, 1, 2), break
            if (value >= 3) break;
            
            // Set current node's value and decrement count
            curr.val = value;
            count[value]--;
            
            // Move to next node
            curr = curr.next;
        }
        
        return head;
    }
    
    /**
     * Alternative approach: Three-pointer (Dutch National Flag) in-place rearrangement
     * Does not modify node values, only rearranges pointers
     * More efficient single-pass solution
     * 
     * @param head Head of the list to sort
     * @return Sorted list head
     * 
     * Algorithm:
     * 1. Create three dummy nodes for 0s, 1s, and 2s lists
     * 2. Traverse original list, appending nodes to appropriate list
     * 3. Connect 0s list → 1s list → 2s list
     * 4. Return new head
     */
    public static ListNode sort012ThreePointer(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // Create dummy nodes for three lists
        ListNode zeroDummy = new ListNode(-1);
        ListNode oneDummy = new ListNode(-1);
        ListNode twoDummy = new ListNode(-1);
        
        // Tail pointers for each list
        ListNode zeroTail = zeroDummy;
        ListNode oneTail = oneDummy;
        ListNode twoTail = twoDummy;
        
        // Traverse original list
        ListNode curr = head;
        while (curr != null) {
            // Save next node before disconnecting
            ListNode next = curr.next;
            curr.next = null;  // Disconnect from original list
            
            // Append to appropriate list based on value
            if (curr.val == 0) {
                zeroTail.next = curr;
                zeroTail = zeroTail.next;
            } else if (curr.val == 1) {
                oneTail.next = curr;
                oneTail = oneTail.next;
            } else if (curr.val == 2) {
                twoTail.next = curr;
                twoTail = twoTail.next;
            } else {
                // Invalid value (optional error handling)
                System.err.println("Invalid value " + curr.val + " found");
            }
            
            curr = next;  // Move to next node
        }
        
        // Connect the three lists
        // 0s list → 1s list → 2s list → null
        
        // First, connect 1s list to 2s list
        oneTail.next = twoDummy.next;
        
        // Then connect 0s list to 1s list
        zeroTail.next = oneDummy.next;
        
        // Return head of 0s list (skip dummy node)
        return zeroDummy.next;
    }
    
    /**
     * Optimized Dutch National Flag algorithm (single pass, O(1) space)
     * Rearranges nodes in-place without dummy nodes
     */
    public static ListNode sort012DutchNationalFlag(ListNode head) {
        if (head == null || head.next == null) return head;
        
        // Create separate heads and tails for 0s, 1s, and 2s
        ListNode zeroHead = null, zeroTail = null;
        ListNode oneHead = null, oneTail = null;
        ListNode twoHead = null, twoTail = null;
        
        ListNode curr = head;
        
        while (curr != null) {
            // Save next node
            ListNode next = curr.next;
            curr.next = null;
            
            // Add to appropriate list
            if (curr.val == 0) {
                if (zeroHead == null) {
                    zeroHead = zeroTail = curr;
                } else {
                    zeroTail.next = curr;
                    zeroTail = zeroTail.next;
                }
            } else if (curr.val == 1) {
                if (oneHead == null) {
                    oneHead = oneTail = curr;
                } else {
                    oneTail.next = curr;
                    oneTail = oneTail.next;
                }
            } else if (curr.val == 2) {
                if (twoHead == null) {
                    twoHead = twoTail = curr;
                } else {
                    twoTail.next = curr;
                    twoTail = twoTail.next;
                }
            }
            
            curr = next;
        }
        
        // Connect the three lists
        // Note: We need to handle cases where some lists might be empty
        
        ListNode result = null;
        
        if (zeroHead != null) {
            result = zeroHead;
            if (oneHead != null) {
                zeroTail.next = oneHead;
                if (twoHead != null) {
                    oneTail.next = twoHead;
                }
            } else if (twoHead != null) {
                zeroTail.next = twoHead;
            }
        } else if (oneHead != null) {
            result = oneHead;
            if (twoHead != null) {
                oneTail.next = twoHead;
            }
        } else {
            result = twoHead;
        }
        
        return result;
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
     * Helper method to visualize the counting sort process
     */
    public static void visualizeCountingSort(ListNode head) {
        System.out.println("\nVisualizing Counting Sort for 0s, 1s, and 2s:");
        System.out.println("===============================================");
        
        // Create a copy for visualization
        ListNode original = copyList(head);
        
        // Step 1: Show initial list
        System.out.println("Step 1: Original list");
        System.out.print("  ");
        printList(original);
        
        // Step 2: Count occurrences
        System.out.println("\nStep 2: Counting occurrences");
        int[] count = new int[3];
        ListNode curr = original;
        while (curr != null) {
            if (curr.val >= 0 && curr.val <= 2) {
                count[curr.val]++;
            }
            curr = curr.next;
        }
        System.out.println("  Count of 0s: " + count[0]);
        System.out.println("  Count of 1s: " + count[1]);
        System.out.println("  Count of 2s: " + count[2]);
        
        // Step 3: Overwrite values
        System.out.println("\nStep 3: Overwriting values in sorted order");
        curr = original;
        int step = 1;
        
        for (int value = 0; value < 3; value++) {
            for (int i = 0; i < count[value]; i++) {
                if (curr != null) {
                    System.out.println("  Step " + step + ": Set node to " + value);
                    curr.val = value;
                    curr = curr.next;
                    step++;
                }
            }
        }
        
        System.out.print("\nFinal sorted list: ");
        printList(original);
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
        System.out.println("=== Sorting Linked List with 0s, 1s, and 2s ===\n");
        
        // Test Case 1: Random order
        System.out.println("Test Case 1: Random order of 0s, 1s, and 2s");
        int[] arr1 = {1, 0, 2, 1, 2, 0, 1, 2, 0};
        ListNode list1 = createList(arr1);
        System.out.print("Original:    ");
        printList(list1);
        System.out.print("Sorted (counting): ");
        ListNode result1 = sort012(createList(arr1));
        printList(result1);
        visualizeCountingSort(createList(arr1));
        System.out.println();
        
        // Test Case 2: Already sorted
        System.out.println("Test Case 2: Already sorted list");
        int[] arr2 = {0, 0, 0, 1, 1, 2, 2};
        ListNode list2 = createList(arr2);
        System.out.print("Original:    ");
        printList(list2);
        System.out.print("Sorted:      ");
        printList(sort012(list2));
        System.out.println();
        
        // Test Case 3: Reverse sorted
        System.out.println("Test Case 3: Reverse sorted list");
        int[] arr3 = {2, 2, 1, 1, 0, 0};
        ListNode list3 = createList(arr3);
        System.out.print("Original:    ");
        printList(list3);
        System.out.print("Sorted:      ");
        printList(sort012(list3));
        System.out.println();
        
        // Test Case 4: All same value
        System.out.println("Test Case 4: All 1s");
        int[] arr4 = {1, 1, 1, 1};
        ListNode list4 = createList(arr4);
        System.out.print("Original:    ");
        printList(list4);
        System.out.print("Sorted:      ");
        printList(sort012(list4));
        System.out.println();
        
        // Test Case 5: Single element
        System.out.println("Test Case 5: Single element (2)");
        ListNode list5 = new ListNode(2);
        System.out.print("Original:    ");
        printList(list5);
        System.out.print("Sorted:      ");
        printList(sort012(list5));
        System.out.println();
        
        // Test Case 6: Empty list
        System.out.println("Test Case 6: Empty list");
        ListNode list6 = null;
        System.out.print("Original:    ");
        printList(list6);
        System.out.print("Sorted:      ");
        printList(sort012(list6));
        System.out.println();
        
        // Test Case 7: Three-pointer approach
        System.out.println("Test Case 7: Three-pointer approach");
        int[] arr7 = {2, 1, 0, 1, 2, 0, 0, 1};
        ListNode list7 = createList(arr7);
        System.out.print("Original:    ");
        printList(list7);
        System.out.print("3-Pointer:   ");
        printList(sort012ThreePointer(createList(arr7)));
        System.out.println();
        
        // Test Case 8: Dutch National Flag algorithm
        System.out.println("Test Case 8: Dutch National Flag algorithm");
        int[] arr8 = {0, 2, 1, 0, 2, 1, 0};
        ListNode list8 = createList(arr8);
        System.out.print("Original:    ");
        printList(list8);
        System.out.print("Dutch Flag:  ");
        printList(sort012DutchNationalFlag(createList(arr8)));
        System.out.println();
        
        // Performance comparison
        System.out.println("=== Performance Characteristics ===");
        System.out.println("1. Counting Sort (two-pass):");
        System.out.println("   - Time: O(2n) = O(n)");
        System.out.println("   - Space: O(1) for count array");
        System.out.println("   - Modifies node values");
        System.out.println();
        System.out.println("2. Three-pointer / Dutch Flag (single-pass):");
        System.out.println("   - Time: O(n)");
        System.out.println("   - Space: O(1)");
        System.out.println("   - Rearranges nodes, doesn't modify values");
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