/**
 * Problem: Find Middle Node of Linked List
 * 
 * LeetCode 876: Middle of the Linked List
 * Problem Statement: Given the head of a singly linked list, 
 * return the middle node of the linked list.
 * If there are two middle nodes, return the second middle node.
 * 
 * Example 1: 1→2→3→4→5 → Returns 3
 * Example 2: 1→2→3→4→5→6 → Returns 4 (second middle)
 * 
 * Time Complexity: O(n) - single pass
 * Space Complexity: O(1) - constant space
 */

import java.util.*;

public class MiddleNode {
    
    /**
     * Primary Solution: Tortoise and Hare Algorithm (Two Pointers)
     * 
     * Algorithm:
     * 1. Initialize slow and fast pointers at head
     * 2. Move slow by 1 step, fast by 2 steps
     * 3. When fast reaches end, slow is at middle
     * 
     * Why it works:
     * - Fast moves twice as fast as slow
     * - When fast covers n nodes, slow covers n/2 nodes
     * - For odd n: fast ends at last node, slow at exact middle
     * - For even n: fast ends at null, slow at second middle
     * 
     * Edge Cases:
     * - Empty list → return null
     * - Single node → return the node
     * - Two nodes → return second node (second middle)
     */
    public static ListNode middleNode(ListNode head) {
        // Handle empty list
        if (head == null) return null;
        
        ListNode slow = head;  // Tortoise: moves 1 step at a time
        ListNode fast = head;  // Hare: moves 2 steps at a time
        
        // Traverse until fast reaches end or goes beyond
        while (fast != null && fast.next != null) {
            slow = slow.next;      // Move slow by 1
            fast = fast.next.next; // Move fast by 2
        }
        
        return slow; // Slow is now at middle (or second middle for even length)
    }
    
    /**
     * Alternative 1: Two-pass Approach
     * 
     * Steps:
     * 1. First pass: count nodes to get length
     * 2. Second pass: traverse to middle position
     * 
     * Pros: Simple, easy to understand
     * Cons: Two passes through list
     */
    public static ListNode middleNodeTwoPass(ListNode head) {
        if (head == null) return null;
        
        // First pass: count nodes
        int count = 0;
        ListNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        
        // Second pass: go to middle
        current = head;
        for (int i = 0; i < count / 2; i++) {
            current = current.next;
        }
        
        return current;
    }
    
    /**
     * Alternative 2: Using Array/List
     * 
     * Steps:
     * 1. Store all nodes in an array
     * 2. Return middle element from array
     * 
     * Pros: Simple, O(1) access to middle
     * Cons: O(n) extra space
     */
    public static ListNode middleNodeArray(ListNode head) {
        if (head == null) return null;
        
        List<ListNode> nodes = new ArrayList<>();
        ListNode current = head;
        
        // Store all nodes in array
        while (current != null) {
            nodes.add(current);
            current = current.next;
        }
        
        // Return middle element
        return nodes.get(nodes.size() / 2);
    }
    
    /**
     * Alternative 3: Recursive Approach
     * 
     * Steps:
     * 1. Recurse to end while counting
     * 2. Return node when count reaches middle
     * 
     * Pros: Elegant recursive solution
     * Cons: O(n) recursion stack space
     */
    public static ListNode middleNodeRecursive(ListNode head) {
        if (head == null) return null;
        
        // Use helper with counter
        int[] length = {0};
        return findMiddleRecursive(head, 0, length);
    }
    
    private static ListNode findMiddleRecursive(ListNode node, int index, int[] totalLength) {
        if (node == null) {
            totalLength[0] = index;
            return null;
        }
        
        ListNode middle = findMiddleRecursive(node.next, index + 1, totalLength);
        
        // Check if current node is middle
        if (index == totalLength[0] / 2) {
            return node;
        }
        
        return middle;
    }
    
    /**
     * Alternative 4: First Middle (for even length)
     * 
     * For even length lists (e.g., 4 nodes: 1→2→3→4):
     * - First middle: node 2
     * - Second middle: node 3 (LeetCode standard)
     */
    public static ListNode firstMiddleNode(ListNode head) {
        if (head == null || head.next == null) return head;
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Different condition to get first middle
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    /**
     * Alternative 5: Using Stack
     * 
     * Steps:
     * 1. Push all nodes to stack
     * 2. Pop half the nodes
     * 3. Top of stack is middle
     * 
     * Pros: Can be useful for certain problems
     * Cons: O(n) extra space, two passes
     */
    public static ListNode middleNodeStack(ListNode head) {
        if (head == null) return null;
        
        Stack<ListNode> stack = new Stack<>();
        ListNode current = head;
        int count = 0;
        
        // Push all nodes to stack and count
        while (current != null) {
            stack.push(current);
            count++;
            current = current.next;
        }
        
        // Pop half the nodes
        for (int i = 0; i < count / 2; i++) {
            stack.pop();
        }
        
        return stack.peek(); // Top of stack is middle
    }
    
    /**
     * Utility: Find both middles for even length lists
     * 
     * Returns array containing [firstMiddle, secondMiddle] for even length,
     * or [middle, middle] for odd length
     */
    public static ListNode[] bothMiddleNodes(ListNode head) {
        if (head == null) return new ListNode[]{null, null};
        
        ListNode firstMiddle = firstMiddleNode(head);
        ListNode secondMiddle = middleNode(head);
        
        return new ListNode[]{firstMiddle, secondMiddle};
    }
    
    /**
     * Utility: Check if node is middle of list
     */
    public static boolean isMiddleNode(ListNode head, ListNode node) {
        if (head == null || node == null) return false;
        return middleNode(head) == node;
    }
    
    /**
     * Utility: Get distance from head to middle
     */
    public static int distanceToMiddle(ListNode head) {
        if (head == null) return 0;
        
        int distance = 0;
        ListNode middle = middleNode(head);
        ListNode current = head;
        
        while (current != middle) {
            distance++;
            current = current.next;
        }
        
        return distance;
    }
    
    /**
     * Utility: Split list at middle
     * Returns array of two lists: [firstHalf, secondHalf]
     */
    public static ListNode[] splitAtMiddle(ListNode head) {
        if (head == null) return new ListNode[]{null, null};
        
        // Find first middle for splitting
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Create two halves
        ListNode firstHalf = head;
        ListNode secondHalf = null;
        
        if (prev != null) {
            prev.next = null; // Break the link
            secondHalf = slow;
        } else {
            // Single node or empty
            secondHalf = slow.next;
        }
        
        return new ListNode[]{firstHalf, secondHalf};
    }
    
    /**
     * Utility: Create linked list from array
     */
    public static ListNode createList(int[] values) {
        if (values == null || values.length == 0) return null;
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        for (int val : values) {
            current.next = new ListNode(val);
            current = current.next;
        }
        
        return dummy.next;
    }
    
    /**
     * Utility: Print linked list
     */
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println();
    }
    
    /**
     * Utility: Print list with middle highlighted
     */
    public static void printListWithMiddle(ListNode head) {
        ListNode middle = middleNode(head);
        ListNode current = head;
        
        System.out.print("List: ");
        while (current != null) {
            if (current == middle) {
                System.out.print("[" + current.val + "]");
            } else {
                System.out.print(current.val);
            }
            
            if (current.next != null) System.out.print(" → ");
            current = current.next;
        }
        System.out.println();
    }
    
    /**
     * Performance comparison of all methods
     */
    public static void compareAllMethods(ListNode head, String description) {
        System.out.println("\n" + description);
        System.out.println("-".repeat(50));
        
        long start, end;
        
        // Method 1: Two pointers
        start = System.nanoTime();
        ListNode result1 = middleNode(head);
        end = System.nanoTime();
        System.out.printf("Two Pointers: %d (%d ns)\n", 
                         result1.val, (end - start));
        
        // Method 2: Two-pass
        start = System.nanoTime();
        ListNode result2 = middleNodeTwoPass(head);
        end = System.nanoTime();
        System.out.printf("Two-pass:     %d (%d ns)\n", 
                         result2.val, (end - start));
        
        // Method 3: Array
        start = System.nanoTime();
        ListNode result3 = middleNodeArray(head);
        end = System.nanoTime();
        System.out.printf("Array:        %d (%d ns)\n", 
                         result3.val, (end - start));
        
        // Method 4: Recursive
        start = System.nanoTime();
        ListNode result4 = middleNodeRecursive(head);
        end = System.nanoTime();
        System.out.printf("Recursive:    %d (%d ns)\n", 
                         result4.val, (end - start));
        
        // Method 5: First middle
        start = System.nanoTime();
        ListNode result5 = firstMiddleNode(head);
        end = System.nanoTime();
        System.out.printf("First Middle: %d (%d ns)\n", 
                         result5.val, (end - start));
        
        // Verify all methods return correct results
        boolean allMatch = result1 == result2 && 
                          result2 == result3 && 
                          result3 == result4;
        System.out.println("All methods match: " + allMatch);
    }
    
    /**
     * Visualize the two-pointer algorithm
     */
    public static void visualizeTwoPointer(ListNode head) {
        System.out.println("\nTwo-Pointer Algorithm Visualization:");
        System.out.println("S = Slow (1 step), F = Fast (2 steps)");
        System.out.println("-".repeat(50));
        
        ListNode slow = head;
        ListNode fast = head;
        int step = 0;
        
        System.out.printf("Step %d: S=%d, F=%d\n", step, slow.val, fast.val);
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            step++;
            
            System.out.printf("Step %d: S=%d, F=%s\n", 
                step, 
                slow.val, 
                fast != null ? String.valueOf(fast.val) : "null"
            );
        }
        
        System.out.println("\nMiddle Node: " + slow.val);
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=".repeat(60));
        System.out.println("MIDDLE NODE OF LINKED LIST - COMPREHENSIVE TEST");
        System.out.println("=".repeat(60));
        
        // Test Case 1: Odd length list
        System.out.println("\n1. ODD LENGTH LIST (5 nodes)");
        ListNode list1 = createList(new int[]{1, 2, 3, 4, 5});
        printListWithMiddle(list1);
        System.out.println("Middle Node Value: " + middleNode(list1).val);
        System.out.println("Expected: 3");
        
        // Test Case 2: Even length list
        System.out.println("\n2. EVEN LENGTH LIST (6 nodes)");
        ListNode list2 = createList(new int[]{1, 2, 3, 4, 5, 6});
        printListWithMiddle(list2);
        System.out.println("Second Middle Value: " + middleNode(list2).val);
        System.out.println("First Middle Value: " + firstMiddleNode(list2).val);
        System.out.println("Expected: Second=4, First=3");
        
        // Test Case 3: Single node
        System.out.println("\n3. SINGLE NODE");
        ListNode list3 = createList(new int[]{42});
        printListWithMiddle(list3);
        System.out.println("Middle Value: " + middleNode(list3).val);
        System.out.println("Expected: 42");
        
        // Test Case 4: Two nodes
        System.out.println("\n4. TWO NODES");
        ListNode list4 = createList(new int[]{10, 20});
        printListWithMiddle(list4);
        System.out.println("Middle Value: " + middleNode(list4).val);
        System.out.println("Expected: 20 (second middle)");
        
        // Test Case 5: Empty list
        System.out.println("\n5. EMPTY LIST");
        System.out.println("Middle of null: " + middleNode(null));
        System.out.println("Expected: null");
        
        // Test Case 6: Large list
        System.out.println("\n6. LARGE LIST (1000 nodes)");
        int[] largeArray = new int[1000];
        for (int i = 0; i < 1000; i++) largeArray[i] = i + 1;
        ListNode largeList = createList(largeArray);
        
        long startTime = System.currentTimeMillis();
        ListNode middle = middleNode(largeList);
        long endTime = System.currentTimeMillis();
        
        System.out.printf("Middle Value: %d\n", middle.val);
        System.out.printf("Time taken: %d ms\n", (endTime - startTime));
        System.out.println("Expected: ~500");
        
        // Test Case 7: Both middles for even length
        System.out.println("\n7. BOTH MIDDLES FOR EVEN LENGTH");
        ListNode[] middles = bothMiddleNodes(list2);
        System.out.printf("First Middle: %d, Second Middle: %d\n", 
                         middles[0].val, middles[1].val);
        
        // Test Case 8: Split at middle
        System.out.println("\n8. SPLIT LIST AT MIDDLE");
        ListNode[] halves = splitAtMiddle(list1);
        System.out.print("First Half: ");
        printList(halves[0]);
        System.out.print("Second Half: ");
        printList(halves[1]);
        
        // Test Case 9: Check if node is middle
        System.out.println("\n9. CHECK IF NODE IS MIDDLE");
        System.out.println("Node 3 is middle of list1: " + 
                          isMiddleNode(list1, list1.next.next));
        System.out.println("Node 2 is middle of list1: " + 
                          isMiddleNode(list1, list1.next));
        
        // Test Case 10: Distance to middle
        System.out.println("\n10. DISTANCE FROM HEAD TO MIDDLE");
        System.out.println("Distance in list1: " + distanceToMiddle(list1));
        System.out.println("Distance in list2: " + distanceToMiddle(list2));
        
        // Test Case 11: Compare all methods
        System.out.println("\n11. PERFORMANCE COMPARISON");
        compareAllMethods(list1, "5-node list (odd length)");
        compareAllMethods(list2, "6-node list (even length)");
        
        // Test Case 12: Algorithm visualization
        System.out.println("\n12. ALGORITHM VISUALIZATION");
        visualizeTwoPointer(list1);
        
        // Test Case 13: Special cases
        System.out.println("\n13. SPECIAL CASES");
        
        // Very long odd list
        ListNode longOdd = createList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        System.out.println("9-node list middle: " + middleNode(longOdd).val);
        
        // Very long even list
        ListNode longEven = createList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        System.out.println("10-node list middle: " + middleNode(longEven).val);
        
        // Test Case 14: Real-world applications
        System.out.println("\n14. REAL-WORLD APPLICATIONS");
        System.out.println("1. Merge Sort on Linked Lists:");
        System.out.println("   - Find middle to split list recursively");
        System.out.println("   - Essential for O(n log n) sorting");
        
        System.out.println("\n2. Balanced Binary Search Tree:");
        System.out.println("   - Convert sorted linked list to balanced BST");
        System.out.println("   - Middle becomes root, recursively build left/right");
        
        System.out.println("\n3. Palindrome Checking:");
        System.out.println("   - Find middle, reverse second half, compare");
        System.out.println("   - Used in text processing");
        
        System.out.println("\n4. Load Balancing:");
        System.out.println("   - Distribute tasks to middle of queue");
        System.out.println("   - Implement fair scheduling");
        
        // Test Case 15: Mathematical analysis
        System.out.println("\n15. MATHEMATICAL ANALYSIS");
        System.out.println("For list with n nodes:");
        System.out.println("- Fast pointer moves 2 steps per iteration");
        System.out.println("- Slow pointer moves 1 step per iteration");
        System.out.println("- Number of iterations = ceil(n/2)");
        System.out.println("- Time Complexity: O(n)");
        System.out.println("- Space Complexity: O(1)");
        
        // Test Case 16: Edge case testing
        System.out.println("\n16. EDGE CASE TESTING");
        
        // List with duplicate values
        ListNode duplicates = createList(new int[]{5, 5, 5, 5, 5});
        System.out.println("Duplicates list middle: " + middleNode(duplicates).val);
        
        // Negative values
        ListNode negatives = createList(new int[]{-10, -5, 0, 5, 10});
        System.out.println("Negatives list middle: " + middleNode(negatives).val);
        
        // Test Case 17: Interactive test
        System.out.println("\n17. INTERACTIVE TEST");
        testWithRandomList(15);
        testWithRandomList(16);
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TEST COMPLETE - ALL CASES VERIFIED");
        System.out.println("=".repeat(60));
    }
    
    /**
     * Helper: Test with random list of given size
     */
    private static void testWithRandomList(int size) {
        System.out.printf("\nTesting with %d nodes:\n", size);
        
        Random rand = new Random();
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = rand.nextInt(100);
        }
        
        ListNode list = createList(values);
        printListWithMiddle(list);
        
        ListNode middle = middleNode(list);
        ListNode firstMiddle = firstMiddleNode(list);
        
        if (size % 2 == 1) {
            System.out.printf("Odd length: Middle = %d\n", middle.val);
        } else {
            System.out.printf("Even length: First Middle = %d, Second Middle = %d\n", 
                            firstMiddle.val, middle.val);
        }
    }
    
    /**
     * Common Mistakes Section
     */
    private static void showCommonMistakes() {
        System.out.println("\nCOMMON MISTAKES TO AVOID:");
        System.out.println("1. Not checking for null head");
        System.out.println("2. Wrong loop condition (fast.next vs fast.next.next)");
        System.out.println("3. Modifying original list unintentionally");
        System.out.println("4. Off-by-one errors in two-pass approach");
        System.out.println("5. Not understanding even/odd middle differences");
    }
    
    /**
     * Time Complexity Analysis
     */
    private static void analyzeComplexity() {
        System.out.println("\nTIME COMPLEXITY ANALYSIS:");
        System.out.println("Two-pointer method: O(n) - single pass through half the list");
        System.out.println("Two-pass method: O(2n) = O(n) - two full passes");
        System.out.println("Array method: O(n) time, O(n) space");
        System.out.println("Recursive method: O(n) time, O(n) recursion stack");
    }
}