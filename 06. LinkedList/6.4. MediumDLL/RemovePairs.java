/**
 * Class for removing all pairs with given sum from a sorted doubly linked list
 * 
 * Problem: Given a SORTED doubly linked list, remove all pairs of nodes 
 * (one from left end, one from right end) whose values sum to a given target.
 * 
 * Example:
 * Input:  null ← 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5 ↔ 6 → null, target = 7
 * Remove: 1+6=7, 2+5=7, 3+4=7
 * Output: null (empty list) because all pairs sum to 7
 * 
 * Approach: Two-pointer technique from both ends
 * 1. left pointer starts at head (smallest)
 * 2. right pointer starts at tail (largest)
 * 3. While they haven't met:
 *    - If left.val + right.val == target: remove BOTH nodes
 *    - If sum < target: move left forward (need larger sum)
 *    - If sum > target: move right backward (need smaller sum)
 * 
 * Time Complexity: O(n) where n is number of nodes
 * Space Complexity: O(1) - uses only constant extra space
 * 
 * Important: After deleting nodes, we need to carefully update:
 * - Head pointer if deleting first node
 * - Tail pointer if deleting last node
 * - Neighbors' next/prev pointers
 * - Our traversal pointers (left and right)
 */
public class RemovePairs {

    /**
     * Removes all pairs with given sum from ends of sorted DLL
     * 
     * @param head The head node of the SORTED doubly linked list
     * @param target The target sum for pairs to remove
     * @return New head of the list after removals (may be null if all removed)
     * 
     * Algorithm Steps:
     * 1. Initialize left at head, right at tail
     * 2. While pointers haven't met/crossed:
     *    a. Calculate sum = left.val + right.val
     *    b. If sum == target:
     *       - Delete left node (update head if needed)
     *       - Delete right node (update tail if needed)
     *       - Move left forward, right backward
     *    c. If sum < target: move left forward
     *    d. If sum > target: move right backward
     * 3. Return (possibly updated) head
     */
    public static DNode removePairsWithSum(DNode head, int target) {
        // Edge case: empty list
        if (head == null) {
            System.out.println("Empty list, nothing to remove");
            return null;
        }
        
        // Step 1: Initialize pointers
        DNode left = head;          // Start from smallest element
        DNode right = head;         // Will move to largest element
        
        // Move right pointer to the last node (tail)
        while (right.next != null) {
            right = right.next;
        }
        
        System.out.println("Initial: left at " + left.val + ", right at " + right.val);
        System.out.println("Removing pairs with sum: " + target);
        
        // Step 2: Two-pointer traversal
        while (left != null && right != null && left != right && right.next != left) {
            int sum = left.val + right.val;
            System.out.println("\nChecking pair: " + left.val + " + " + right.val + " = " + sum);
            
            // Case 1: Found a pair with target sum
            if (sum == target) {
                System.out.println("  ✓ Found pair to remove: " + left.val + " + " + right.val);
                
                // Save references before deletion
                DNode nextLeft = left.next;   // Node after left (could be null)
                DNode prevRight = right.prev; // Node before right (could be null)
                
                // Special check: Are left and right adjacent?
                boolean areAdjacent = (left.next == right && right.prev == left);
                
                // Step 2a: Delete left node
                System.out.println("  Deleting left node: " + left.val);
                
                if (left.prev != null) {
                    // Left is not head - connect previous to next
                    left.prev.next = left.next;
                    System.out.println("    left.prev.next = left.next");
                } else {
                    // Left is head - update head pointer
                    head = left.next;
                    System.out.println("    Updating head to: " + (head != null ? head.val : "null"));
                }
                
                if (left.next != null) {
                    // Left has next node - update its prev pointer
                    left.next.prev = left.prev;
                    System.out.println("    left.next.prev = left.prev");
                }
                
                // Clean up left node
                left.prev = null;
                left.next = null;
                
                // Step 2b: Delete right node
                System.out.println("  Deleting right node: " + right.val);
                
                if (right.next != null) {
                    // Right is not tail - connect next to previous
                    right.next.prev = right.prev;
                    System.out.println("    right.next.prev = right.prev");
                }
                
                if (right.prev != null) {
                    // Right is not head - connect previous to next
                    right.prev.next = right.next;
                    System.out.println("    right.prev.next = right.next");
                }
                
                // Clean up right node
                right.prev = null;
                right.next = null;
                
                // Step 2c: Update traversal pointers
                // If left and right were adjacent, we've already processed them
                if (areAdjacent) {
                    System.out.println("  Left and right were adjacent - stopping");
                    break;
                }
                
                left = nextLeft;
                right = prevRight;
                
                System.out.println("  New left: " + (left != null ? left.val : "null"));
                System.out.println("  New right: " + (right != null ? right.val : "null"));
                
                // If we've removed all nodes
                if (left == null || right == null) {
                    head = null;
                    System.out.println("  All nodes removed - empty list");
                    break;
                }
            } 
            // Case 2: Sum is less than target
            else if (sum < target) {
                System.out.println("  Sum too small (" + sum + " < " + target + ")");
                System.out.println("  Moving left forward");
                left = left.next;
            } 
            // Case 3: Sum is greater than target
            else {
                System.out.println("  Sum too large (" + sum + " > " + target + ")");
                System.out.println("  Moving right backward");
                right = right.prev;
            }
        }
        
        return head;
    }
    
    /**
     * Alternative approach: Collect pairs first, then delete
     * Easier to understand but uses O(n) space
     */
    public static DNode removePairsWithSumAlternative(DNode head, int target) {
        if (head == null) return null;
        
        // First pass: collect nodes to delete
        java.util.Set<DNode> toDelete = new java.util.HashSet<>();
        
        DNode left = head;
        DNode right = head;
        
        // Find tail
        while (right.next != null) {
            right = right.next;
        }
        
        while (left != null && right != null && left != right && right.next != left) {
            int sum = left.val + right.val;
            
            if (sum == target) {
                toDelete.add(left);
                toDelete.add(right);
                left = left.next;
                right = right.prev;
            } else if (sum < target) {
                left = left.next;
            } else {
                right = right.prev;
            }
        }
        
        // Second pass: delete collected nodes
        DNode dummy = new DNode(-1);
        dummy.next = head;
        head.prev = dummy;
        
        DNode curr = dummy.next;
        while (curr != null) {
            DNode next = curr.next;
            
            if (toDelete.contains(curr)) {
                // Delete curr node
                curr.prev.next = curr.next;
                if (curr.next != null) {
                    curr.next.prev = curr.prev;
                }
                curr.prev = null;
                curr.next = null;
            }
            
            curr = next;
        }
        
        // Get new head (skip dummy)
        DNode newHead = dummy.next;
        if (newHead != null) {
            newHead.prev = null;
        }
        
        return newHead;
    }
    
    /**
     * Helper method to create a sorted doubly linked list from array
     */
    public static DNode createSortedList(int[] values) {
        if (values == null || values.length == 0) return null;
        
        // Sort array (important for algorithm to work)
        java.util.Arrays.sort(values);
        
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
     * Helper method to print doubly linked list
     */
    public static void printList(DNode head) {
        DNode current = head;
        System.out.print("List: null ← ");
        
        while (current != null) {
            System.out.print("[" + current.val + "]");
            if (current.next != null) {
                System.out.print(" ↔ ");
            }
            current = current.next;
        }
        System.out.println(" → null");
    }
    
    /**
     * Visualizes the removal process step by step
     */
    public static void visualizeRemoval(DNode head, int target) {
        System.out.println("\n=== Visualizing Removal of Pairs with Sum " + target + " ===");
        DNode listCopy = copyList(head);
        printList(listCopy);
        System.out.println();
        
        if (listCopy == null) {
            System.out.println("Empty list, nothing to remove");
            return;
        }
        
        DNode left = listCopy;
        DNode right = listCopy;
        
        // Move right to tail
        while (right.next != null) {
            right = right.next;
        }
        
        int step = 1;
        System.out.println("Step 1: Initialize pointers at ends");
        System.out.println("  left → [" + left.val + "] (head)");
        System.out.println("  right → [" + right.val + "] (tail)");
        
        while (left != null && right != null && left != right && right.next != left) {
            System.out.println("\n--- Step " + (++step) + " ---");
            System.out.println("  left = [" + left.val + "], right = [" + right.val + "]");
            
            int sum = left.val + right.val;
            System.out.println("  Sum = " + left.val + " + " + right.val + " = " + sum);
            
            if (sum == target) {
                System.out.println("  ✓ MATCH! Removing both nodes");
                
                // Show list before removal
                System.out.print("  Before: ");
                printList(listCopy);
                
                // Delete left node
                if (left.prev != null) {
                    left.prev.next = left.next;
                } else {
                    listCopy = left.next; // Update head
                }
                
                if (left.next != null) {
                    left.next.prev = left.prev;
                }
                
                // Delete right node
                if (right.next != null) {
                    right.next.prev = right.prev;
                }
                
                if (right.prev != null) {
                    right.prev.next = right.next;
                }
                
                // Update pointers
                DNode nextLeft = left.next;
                DNode prevRight = right.prev;
                
                // Clean up
                left.prev = null;
                left.next = null;
                right.prev = null;
                right.next = null;
                
                left = nextLeft;
                right = prevRight;
                
                // Show list after removal
                System.out.print("  After:  ");
                printList(listCopy);
                
                if (left == null || right == null) {
                    System.out.println("  No more nodes to check");
                    break;
                }
            } else if (sum < target) {
                System.out.println("  Sum too small, move left forward");
                left = left.next;
            } else {
                System.out.println("  Sum too large, move right backward");
                right = right.prev;
            }
        }
        
        System.out.println("\nFinal list: ");
        printList(listCopy);
    }
    
    /**
     * Helper to copy a doubly linked list
     */
    private static DNode copyList(DNode head) {
        if (head == null) return null;
        
        DNode newHead = new DNode(head.val);
        DNode original = head.next;
        DNode copy = newHead;
        DNode copyPrev = null;
        
        while (original != null) {
            copy.next = new DNode(original.val);
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
    
    /**
     * Counts how many pairs were removed
     */
    public static int countRemovedPairs(DNode originalHead, DNode newHead, int target) {
        if (originalHead == null) return 0;
        
        // Create a copy to count nodes
        DNode originalCopy = copyList(originalHead);
        DNode newCopy = copyList(newHead);
        
        // Count nodes in both lists
        int originalCount = 0;
        DNode curr = originalCopy;
        while (curr != null) {
            originalCount++;
            curr = curr.next;
        }
        
        int newCount = 0;
        curr = newCopy;
        while (curr != null) {
            newCount++;
            curr = curr.next;
        }
        
        int removedNodes = originalCount - newCount;
        return removedNodes / 2; // Each pair has 2 nodes
    }
    
    public static void main(String[] args) {
        System.out.println("=== Remove Pairs with Given Sum from Doubly Linked List ===\n");
        
        // Test Case 1: Remove all pairs (perfect matching)
        System.out.println("Test Case 1: Remove all pairs summing to 7");
        int[] arr1 = {1, 2, 3, 4, 5, 6};
        DNode list1 = createSortedList(arr1);
        System.out.print("Original: ");
        printList(list1);
        
        visualizeRemoval(copyList(list1), 7);
        
        DNode result1 = removePairsWithSum(list1, 7);
        System.out.print("After removal: ");
        printList(result1);
        System.out.println();
        
        // Test Case 2: Remove some pairs
        System.out.println("Test Case 2: Remove pairs summing to 10");
        int[] arr2 = {1, 3, 4, 5, 6, 7, 9};
        DNode list2 = createSortedList(arr2);
        System.out.print("Original: ");
        printList(list2);
        
        visualizeRemoval(copyList(list2), 10);
        
        DNode result2 = removePairsWithSum(list2, 10);
        System.out.print("After removal: ");
        printList(result2);
        System.out.println();
        
        // Test Case 3: No pairs to remove
        System.out.println("Test Case 3: No pairs sum to 20");
        int[] arr3 = {1, 2, 3, 4, 5};
        DNode list3 = createSortedList(arr3);
        System.out.print("Original: ");
        printList(list3);
        
        DNode result3 = removePairsWithSum(list3, 20);
        System.out.print("After removal (unchanged): ");
        printList(result3);
        System.out.println();
        
        // Test Case 4: Remove from list with duplicates
        System.out.println("Test Case 4: Remove pairs summing to 8 (with duplicates)");
        int[] arr4 = {1, 2, 3, 4, 4, 5, 6, 7};
        DNode list4 = createSortedList(arr4);
        System.out.print("Original: ");
        printList(list4);
        
        visualizeRemoval(copyList(list4), 8);
        
        DNode result4 = removePairsWithSum(list4, 8);
        System.out.print("After removal: ");
        printList(result4);
        System.out.println();
        
        // Test Case 5: Single pair at ends
        System.out.println("Test Case 5: Single pair 2+8=10");
        int[] arr5 = {2, 4, 6, 8};
        DNode list5 = createSortedList(arr5);
        System.out.print("Original: ");
        printList(list5);
        
        visualizeRemoval(copyList(list5), 10);
        
        DNode result5 = removePairsWithSum(list5, 10);
        System.out.print("After removal: ");
        printList(result5);
        System.out.println();
        
        // Test Case 6: Empty result
        System.out.println("Test Case 6: All pairs removed (empty result)");
        int[] arr6 = {1, 9, 2, 8, 3, 7};
        java.util.Arrays.sort(arr6);
        DNode list6 = createSortedList(arr6);
        System.out.print("Original: ");
        printList(list6);
        
        DNode result6 = removePairsWithSum(list6, 10);
        System.out.print("After removal: ");
        printList(result6);
        System.out.println();
        
        // Test Case 7: Adjacent nodes form pair
        System.out.println("Test Case 7: Adjacent nodes 5+5=10");
        int[] arr7 = {1, 3, 5, 5, 7, 9};
        DNode list7 = createSortedList(arr7);
        System.out.print("Original: ");
        printList(list7);
        
        visualizeRemoval(copyList(list7), 10);
        
        DNode result7 = removePairsWithSum(list7, 10);
        System.out.print("After removal: ");
        printList(result7);
        System.out.println();
        
        // Test Case 8: Alternative approach
        System.out.println("Test Case 8: Alternative approach (collect then delete)");
        int[] arr8 = {1, 2, 3, 4, 5, 6};
        DNode list8 = createSortedList(arr8);
        System.out.print("Original: ");
        printList(list8);
        
        DNode result8 = removePairsWithSumAlternative(list8, 7);
        System.out.print("After removal (alternative): ");
        printList(result8);
        
        // Count removed pairs
        int pairsRemoved = countRemovedPairs(createSortedList(arr8), result8, 7);
        System.out.println("Pairs removed: " + pairsRemoved);
        System.out.println();
        
        // Complexity Analysis
        System.out.println("=== Algorithm Analysis ===");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Finding tail: O(n)");
        System.out.println("  - Two-pointer traversal: O(n)");
        System.out.println("  - Deletions: O(1) each, O(n) total");
        System.out.println("  - Total: O(3n) = O(n)");
        System.out.println("\nSpace Complexity: O(1)");
        System.out.println("  - Only uses pointers, no additional data structures");
        System.out.println("\nKey Points:");
        System.out.println("  1. List MUST be sorted for algorithm to work");
        System.out.println("  2. Deletes nodes from both ends simultaneously");
        System.out.println("  3. Handles edge cases: head/tail deletion, adjacent nodes");
        System.out.println("  4. Stops when pointers meet or cross");
    }
    
    /**
     * Definition for doubly linked list node
     */
    static class DNode {
        int val;
        DNode prev;
        DNode next;
        
        DNode(int val) {
            this.val = val;
            this.prev = null;
            this.next = null;
        }
        
        DNode(int val, DNode prev, DNode next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }
}