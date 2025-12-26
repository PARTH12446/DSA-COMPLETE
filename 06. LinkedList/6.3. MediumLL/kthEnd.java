/**
 * Problem: Find kth Node From End of Linked List
 * 
 * Problem Statement:
 * Given a singly linked list, find the kth node from the end of the list.
 * Return null if k is invalid or list is too short.
 * 
 * Key Points:
 * - k = 1 returns last node (1st from end)
 * - k = length returns first node (head)
 * - k must be positive (k > 0)
 * - Should be done in single pass if possible
 * - Cannot modify the linked list
 * 
 * Example:
 * List: 1 → 2 → 3 → 4 → 5
 * k = 1 → returns 5 (last node)
 * k = 2 → returns 4 (second last)
 * k = 5 → returns 1 (first node)
 * k = 6 → returns null (invalid)
 * 
 * Applications:
 * - Music playlist: get nth song from end
 * - File system: get nth file from end
 * - Cache systems: LRU cache implementation
 * - Network packets: find nth packet from end
 */

import java.util.Stack;

public class kthEnd {
    
    /**
     * Primary Solution: Two-pointer (fast/slow) approach
     * 
     * Algorithm (Runner Technique):
     * 1. Move first pointer k steps ahead
     * 2. If first becomes null before k steps, return null (list too short)
     * 3. Move both pointers together until first reaches end
     * 4. Second pointer will be at kth from end
     * 
     * Visualization:
     * Initial: S→1→2→3→4→5, k=2
     *         F
     * Step 1: Move F k steps: S→1→2→3→4→5
     *                           F
     * Step 2: Move both:      S→1→2→3→4→5
     *                                 F
     *                                 S (result: 4)
     * 
     * Time Complexity: O(n) - Single pass through list
     * Space Complexity: O(1) - Only two pointers
     * 
     * @param head Head of the linked list
     * @param k Position from end (1-indexed)
     * @return kth node from end, or null if invalid
     */
    public static ListNode kthFromEnd(ListNode head, int k) {
        // Validate input
        if (head == null || k <= 0) {
            return null;
        }
        
        ListNode first = head;  // Fast pointer (runner)
        ListNode second = head; // Slow pointer (kth from end)
        
        // Move first pointer k steps ahead
        for (int i = 0; i < k; i++) {
            if (first == null) {
                // List has fewer than k nodes
                return null;
            }
            first = first.next;
        }
        
        // Move both pointers until first reaches end
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        
        // Second is now kth from end
        return second;
    }
    
    /**
     * Alternative: Two-pass approach (length calculation)
     * 
     * Algorithm:
     * 1. Traverse list to calculate length n
     * 2. Validate k (1 ≤ k ≤ n)
     * 3. Traverse again to (n-k)th node
     * 
     * Advantages:
     * - Simpler logic
     * - More intuitive for beginners
     * 
     * Disadvantages:
     * - Two passes through list
     * - Still O(n) time, but slightly slower
     */
    public static ListNode kthFromEndTwoPass(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        
        // First pass: calculate length
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        
        // Validate k
        if (k > length) {
            return null;
        }
        
        // Second pass: find (length - k)th node
        int target = length - k;
        current = head;
        for (int i = 0; i < target; i++) {
            current = current.next;
        }
        
        return current;
    }
    
    /**
     * Alternative: Recursive approach
     * 
     * Algorithm:
     * 1. Recurse to end of list
     * 2. Count back from end
     * 3. Return node when count reaches k
     * 
     * Advantages:
     * - Elegant recursive solution
     * - No explicit pointer manipulation
     * 
     * Disadvantages:
     * - O(n) recursion stack space
     * - Risk of stack overflow for long lists
     */
    public static ListNode kthFromEndRecursive(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        
        // Use helper with counter
        int[] counter = {0};
        return kthFromEndHelper(head, k, counter);
    }
    
    private static ListNode kthFromEndHelper(ListNode node, int k, int[] counter) {
        if (node == null) {
            return null;
        }
        
        // Recurse to end first
        ListNode result = kthFromEndHelper(node.next, k, counter);
        
        // Count back from end
        counter[0]++;
        
        // Return node when count equals k
        if (counter[0] == k) {
            return node;
        }
        
        return result;
    }
    
    /**
     * Alternative: Stack approach
     * 
     * Algorithm:
     * 1. Push all nodes to stack
     * 2. Pop k nodes
     * 3. kth popped node is answer
     * 
     * Advantages:
     * - Very simple to understand
     * - Can handle multiple queries efficiently
     * 
     * Disadvantages:
     * - O(n) extra space for stack
     * - Two passes (push then pop)
     */
    public static ListNode kthFromEndStack(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        
        Stack<ListNode> stack = new Stack<>();
        ListNode current = head;
        
        // Push all nodes to stack
        while (current != null) {
            stack.push(current);
            current = current.next;
        }
        
        // Validate k (not larger than stack)
        if (k > stack.size()) {
            return null;
        }
        
        // Pop k-1 nodes
        for (int i = 0; i < k - 1; i++) {
            stack.pop();
        }
        
        // kth popped node is answer
        return stack.pop();
    }
    
    /**
     * Extended: Find kth from end value (without returning node)
     */
    public static Integer kthFromEndValue(ListNode head, int k) {
        ListNode node = kthFromEnd(head, k);
        return (node != null) ? node.val : null;
    }
    
    /**
     * Extended: Remove kth node from end
     * 
     * LeetCode 19: https://leetcode.com/problems/remove-nth-node-from-end-of-list/
     * 
     * Algorithm:
     * 1. Use dummy node to handle edge cases (removing head)
     * 2. Find (k+1)th node from end using two-pointer
     * 3. Update its next pointer to skip kth node
     */
    public static ListNode removeKthFromEnd(ListNode head, int k) {
        if (head == null || k <= 0) {
            return head;
        }
        
        // Dummy node handles edge case of removing head
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        ListNode first = dummy;
        ListNode second = dummy;
        
        // Move first k+1 steps ahead
        for (int i = 0; i <= k; i++) {
            if (first == null) {
                return head; // k too large
            }
            first = first.next;
        }
        
        // Move both until first reaches end
        while (first != null) {
            first = first.next;
            second = second.next;
        }
        
        // Remove kth node
        second.next = second.next.next;
        
        return dummy.next;
    }
    
    /**
     * Extended: Find middle node of list
     * 
     * Uses similar two-pointer approach
     * Fast moves 2 steps, slow moves 1 step
     */
    public static ListNode findMiddle(ListNode head) {
        if (head == null) {
            return null;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    /**
     * Extended: Find kth from beginning
     * 
     * @param head Head of list
     * @param k Position from start (1-indexed)
     * @return kth node from start
     */
    public static ListNode kthFromStart(ListNode head, int k) {
        if (head == null || k <= 0) {
            return null;
        }
        
        ListNode current = head;
        for (int i = 1; i < k; i++) {
            if (current == null) {
                return null;
            }
            current = current.next;
        }
        
        return current;
    }
    
    /**
     * Extended: Find node that is k positions from another node
     * 
     * @param node Starting node
     * @param k Distance to move (positive for forward, negative for backward)
     * @return Node k positions away, or null if out of bounds
     */
    public static ListNode kthFromNode(ListNode node, int k) {
        if (node == null) {
            return null;
        }
        
        ListNode current = node;
        
        if (k >= 0) {
            // Move forward k times
            for (int i = 0; i < k; i++) {
                if (current == null) {
                    return null;
                }
                current = current.next;
            }
        } else {
            // Negative k - this requires doubly linked list or knowing length
            // For singly linked list, we can't go backward
            return null;
        }
        
        return current;
    }
    
    /**
     * Helper: Create linked list from array
     */
    public static ListNode arrayToList(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        for (int value : arr) {
            current.next = new ListNode(value);
            current = current.next;
        }
        return dummy.next;
    }
    
    /**
     * Helper: Print list with kth node highlighted
     */
    public static void printListWithKth(ListNode head, ListNode kthNode) {
        ListNode current = head;
        System.out.print("List: ");
        
        while (current != null) {
            if (current == kthNode) {
                System.out.print("[" + current.val + "]");
            } else {
                System.out.print(current.val);
            }
            
            if (current.next != null) {
                System.out.print(" → ");
            }
            current = current.next;
        }
        System.out.println();
    }
    
    /**
     * Helper: Visualize two-pointer algorithm
     */
    public static void visualizeTwoPointer(ListNode head, int k) {
        System.out.println("\nTwo-pointer visualization for k=" + k + ":");
        System.out.println("Initial: F,S→" + listToString(head));
        
        ListNode first = head;
        ListNode second = head;
        
        // Move first k steps
        for (int i = 0; i < k; i++) {
            if (first == null) break;
            first = first.next;
        }
        
        System.out.println("After moving F " + k + " steps:");
        System.out.println("S at " + second.val + ", F at " + 
                          (first != null ? first.val : "null"));
        
        // Move both until end
        int step = 1;
        while (first != null) {
            first = first.next;
            second = second.next;
            System.out.println("Step " + step + ": S at " + second.val + 
                             ", F at " + (first != null ? first.val : "null"));
            step++;
        }
        
        System.out.println("Result: " + second.val);
    }
    
    private static String listToString(ListNode head) {
        StringBuilder sb = new StringBuilder();
        ListNode current = head;
        while (current != null) {
            sb.append(current.val);
            if (current.next != null) sb.append("→");
            current = current.next;
        }
        return sb.toString();
    }
    
    /**
     * Performance comparison of different methods
     */
    public static void compareMethods(ListNode head, int k, String description) {
        System.out.println("\n" + description + " (k=" + k + "):");
        
        // Method 1: Two-pointer
        long start = System.nanoTime();
        ListNode result1 = kthFromEnd(head, k);
        long end = System.nanoTime();
        System.out.println("Two-pointer: " + 
                          (result1 != null ? result1.val : "null") + 
                          " (" + (end - start) + " ns)");
        
        // Method 2: Two-pass
        start = System.nanoTime();
        ListNode result2 = kthFromEndTwoPass(head, k);
        end = System.nanoTime();
        System.out.println("Two-pass: " + 
                          (result2 != null ? result2.val : "null") + 
                          " (" + (end - start) + " ns)");
        
        // Method 3: Stack
        start = System.nanoTime();
        ListNode result3 = kthFromEndStack(head, k);
        end = System.nanoTime();
        System.out.println("Stack: " + 
                          (result3 != null ? result3.val : "null") + 
                          " (" + (end - start) + " ns)");
        
        // Verify consistency
        boolean consistent = (result1 == result2 && result2 == result3);
        if (!consistent) {
            System.out.println("WARNING: Inconsistent results!");
        }
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Find kth Node From End of Linked List ===\n");
        
        // Test Case 1: Basic example
        System.out.println("1. Basic Example (k=2):");
        ListNode list1 = arrayToList(new int[]{1, 2, 3, 4, 5});
        printListWithKth(list1, null);
        
        ListNode result1 = kthFromEnd(list1, 2);
        System.out.println("2nd from end: " + 
                          (result1 != null ? result1.val : "null"));
        printListWithKth(list1, result1);
        
        // Test Case 2: Last node (k=1)
        System.out.println("\n2. Last Node (k=1):");
        ListNode result2 = kthFromEnd(list1, 1);
        System.out.println("1st from end (last): " + result2.val);
        
        // Test Case 3: First node (k=length)
        System.out.println("\n3. First Node (k=length):");
        ListNode result3 = kthFromEnd(list1, 5);
        System.out.println("5th from end (first): " + result3.val);
        
        // Test Case 4: Invalid k (k > length)
        System.out.println("\n4. Invalid k (k > length):");
        ListNode result4 = kthFromEnd(list1, 6);
        System.out.println("6th from end: " + result4);
        System.out.println("Expected: null");
        
        // Test Case 5: Invalid k (k=0 or negative)
        System.out.println("\n5. Invalid k (k <= 0):");
        System.out.println("k=0: " + kthFromEnd(list1, 0));
        System.out.println("k=-1: " + kthFromEnd(list1, -1));
        
        // Test Case 6: Single node list
        System.out.println("\n6. Single Node List:");
        ListNode single = new ListNode(99);
        System.out.println("k=1: " + kthFromEnd(single, 1).val);
        System.out.println("k=2: " + kthFromEnd(single, 2));
        
        // Test Case 7: Empty list
        System.out.println("\n7. Empty List:");
        System.out.println("k=1 on null: " + kthFromEnd(null, 1));
        
        // Test Case 8: Remove kth from end
        System.out.println("\n8. Remove kth from End (k=2):");
        ListNode list8 = arrayToList(new int[]{1, 2, 3, 4, 5});
        System.out.print("Original: ");
        printListWithKth(list8, null);
        
        ListNode removed = removeKthFromEnd(list8, 2);
        System.out.print("After removing 2nd from end: ");
        printListWithKth(removed, null);
        
        // Test Case 9: Compare all methods
        System.out.println("\n9. Method Comparison:");
        ListNode testList = arrayToList(new int[]{10, 20, 30, 40, 50});
        compareMethods(testList, 3, "Finding 3rd from end");
        
        // Test Case 10: Visualization of two-pointer
        System.out.println("\n10. Two-pointer Algorithm Visualization:");
        visualizeTwoPointer(arrayToList(new int[]{1, 2, 3, 4, 5}), 2);
        
        // Test Case 11: Find middle node
        System.out.println("\n11. Find Middle Node:");
        ListNode oddList = arrayToList(new int[]{1, 2, 3, 4, 5});
        ListNode evenList = arrayToList(new int[]{1, 2, 3, 4, 5, 6});
        
        System.out.print("Odd list middle: ");
        ListNode oddMiddle = findMiddle(oddList);
        printListWithKth(oddList, oddMiddle);
        
        System.out.print("Even list middle: ");
        ListNode evenMiddle = findMiddle(evenList);
        printListWithKth(evenList, evenMiddle);
        
        // Test Case 12: kth from start
        System.out.println("\n12. kth from Start:");
        ListNode list12 = arrayToList(new int[]{100, 200, 300, 400, 500});
        System.out.println("3rd from start: " + kthFromStart(list12, 3).val);
        System.out.println("1st from start: " + kthFromStart(list12, 1).val);
        System.out.println("6th from start: " + kthFromStart(list12, 6));
        
        // Test Case 13: Large list performance
        System.out.println("\n13. Large List Performance:");
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i;
        }
        ListNode largeList = arrayToList(largeArray);
        
        long startTime = System.currentTimeMillis();
        ListNode largeResult = kthFromEnd(largeList, 5000);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Found 5000th from end in " + 
                          (endTime - startTime) + " ms");
        System.out.println("Result value: " + largeResult.val);
        
        // Test Case 14: Recursive approach
        System.out.println("\n14. Recursive Approach:");
        ListNode recList = arrayToList(new int[]{1, 2, 3, 4, 5});
        ListNode recResult = kthFromEndRecursive(recList, 3);
        System.out.println("Recursive 3rd from end: " + recResult.val);
        
        // Warning about stack overflow
        System.out.println("Note: Recursive method may cause stack overflow for large lists");
        
        // Test Case 15: Real-world applications
        System.out.println("\n15. Real-World Applications:");
        System.out.println("1. Music Player:");
        System.out.println("   - 'Play 5th song from end of playlist'");
        System.out.println("   - Fast-forward/rewind by k songs");
        
        System.out.println("\n2. File System:");
        System.out.println("   - 'Get nth recent file'");
        System.out.println("   - Undo/redo operations");
        
        System.out.println("\n3. Network Monitoring:");
        System.out.println("   - 'Check packet loss in last k packets'");
        System.out.println("   - 'Find kth recent connection'");
        
        // Test Case 16: Edge cases with all methods
        System.out.println("\n16. Edge Cases Verification:");
        
        // Test with k = length
        ListNode edgeList = arrayToList(new int[]{1, 2, 3});
        System.out.println("k = length (3):");
        System.out.println("Two-pointer: " + kthFromEnd(edgeList, 3).val);
        System.out.println("Two-pass: " + kthFromEndTwoPass(edgeList, 3).val);
        System.out.println("Stack: " + kthFromEndStack(edgeList, 3).val);
        System.out.println("Recursive: " + kthFromEndRecursive(edgeList, 3).val);
        
        // Test Case 17: Algorithm walkthrough
        System.out.println("\n17. Algorithm Walkthrough:");
        System.out.println("List: 1 → 2 → 3 → 4 → 5, k = 3");
        System.out.println("\nTwo-pointer method:");
        System.out.println("Step 1: Move F 3 steps: S at 1, F at 4");
        System.out.println("Step 2: Move both: S at 2, F at 5");
        System.out.println("Step 3: Move both: S at 3, F at null");
        System.out.println("Result: Node with value 3");
        
        System.out.println("\nTwo-pass method:");
        System.out.println("Step 1: Calculate length = 5");
        System.out.println("Step 2: Target position = 5 - 3 = 2");
        System.out.println("Step 3: Move to 2nd node (0-indexed): Node 3");
        
        // Test Case 18: Multiple k values on same list
        System.out.println("\n18. Multiple k Values:");
        ListNode multiList = arrayToList(new int[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100});
        
        for (int k = 1; k <= 10; k++) {
            ListNode node = kthFromEnd(multiList, k);
            System.out.println("k=" + k + ": " + (node != null ? node.val : "null"));
        }
    }
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. OFF-BY-ONE ERRORS:
     *    ❌ for (int i = 0; i <= k; i++) // Moves k+1 steps
     *    ✅ for (int i = 0; i < k; i++)  // Moves k steps
     *    
     * 2. NOT CHECKING NULL DURING INITIAL MOVEMENT:
     *    ❌ Assume list has at least k nodes
     *    ✅ Check if first becomes null during initial loop
     *    
     * 3. USING k=0 AS LAST NODE:
     *    Clarify: k=1 is last node, k=0 is invalid
     *    
     * 4. MODIFYING HEAD WHEN REMOVING:
     *    Use dummy node when removing to handle edge cases
     *    
     * 5. MEMORY LEAKS:
     *    When removing nodes, ensure proper cleanup in languages without GC
     */
    
    /**
     * Two-pointer Algorithm Mathematical Insight:
     * 
     * Let n = length of list
     * Let k = position from end (1-indexed)
     * 
     * Distance from head to kth-from-end = n - k
     * 
     * Algorithm:
     * 1. Move fast pointer k steps: position = k
     * 2. Move both until fast reaches end: fast moves n-k steps
     * 3. Slow also moves n-k steps: position = 0 + (n-k) = n-k ✓
     */
    
    /**
     * Complexity Analysis:
     * 
     * Two-pointer Method:
     * Time: O(n) - Single pass through list
     * Space: O(1) - Two pointers
     * 
     * Two-pass Method:
     * Time: O(2n) = O(n) - Two passes
     * Space: O(1) - Only length counter
     * 
     * Stack Method:
     * Time: O(2n) = O(n) - Push then pop
     * Space: O(n) - Stack storage
     * 
     * Recursive Method:
     * Time: O(n) - Single recursive traversal
     * Space: O(n) - Recursion stack
     */
    
    /**
     * Variations and Extensions:
     * 
     * 1. FIND KTH FROM END IN DOUBLY LINKED LIST:
     *    Can traverse backward from tail
     *    
     * 2. FIND KTH FROM END IN CIRCULAR LIST:
     *    Need to detect cycle first
     *    
     * 3. FIND KTH FROM END WITH MULTIPLE POINTERS:
     *    Use 3+ pointers for different step sizes
     *    
     * 4. FIND KTH FROM END IN MULTIPLE LISTS:
     *    Compare kth nodes from multiple lists
     *    
     * 5. FIND KTH FROM END WITH WINDOW:
     *    Return kth, (k-1)th, and (k+1)th together
     */
    
    /**
     * Interview Tips:
     * 
     * 1. START WITH BRUTE FORCE:
     *    Mention two-pass approach first
     *    
     * 2. THEN OPTIMIZE:
     *    Explain two-pointer optimization
     *    
     * 3. HANDLE EDGE CASES:
     *    Null list, k <= 0, k > length
     *    
     * 4. TEST WITH EXAMPLES:
     *    Walk through algorithm with sample list
     *    
     * 5. DISCUSS TRADEOFFS:
     *    Time vs space, readability vs efficiency
     */
}