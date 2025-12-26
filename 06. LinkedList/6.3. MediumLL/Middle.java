/**
 * Problem: Find Middle of Linked List
 * 
 * LeetCode 876: https://leetcode.com/problems/middle-of-the-linked-list/
 * 
 * Problem Statement:
 * Given the head of a singly linked list, return the middle node.
 * If there are two middle nodes (even length), return the second middle node.
 * 
 * Key Points:
 * - Must be done in single pass
 * - O(1) space complexity
 * - Fast/slow pointer approach (tortoise and hare)
 * - For even length: return second middle (LeetCode standard)
 * - For odd length: return exact middle
 * 
 * Example 1 (Odd Length):
 * Input: 1 → 2 → 3 → 4 → 5
 * Output: 3 (middle node)
 * 
 * Example 2 (Even Length):
 * Input: 1 → 2 → 3 → 4 → 5 → 6
 * Output: 4 (second middle)
 * 
 * Applications:
 * - Binary search on linked lists
 * - Merge sort on linked lists
 * - Balancing data structures
 * - Palindrome checking
 */

import java.util.ArrayList;

public class Middle {
    
    /**
     * Primary Solution: Fast/Slow Pointer (Tortoise and Hare)
     * 
     * Algorithm:
     * 1. Initialize slow and fast pointers at head
     * 2. Move slow by 1 step, fast by 2 steps
     * 3. When fast reaches end, slow is at middle
     * 4. For even length, fast becomes null, slow at second middle
     * 
     * Mathematical Insight:
     * - Fast pointer travels twice as fast as slow
     * - When fast reaches end (n steps), slow has traveled n/2 steps
     * - This gives us the middle in O(n) time, O(1) space
     * 
     * Time Complexity: O(n) - Single pass through list
     * Space Complexity: O(1) - Only two pointers
     * 
     * @param head Head of the linked list
     * @return Middle node (second middle for even length)
     */
    public static ListNode middleNode(ListNode head) {
        // Edge case: empty list
        if (head == null) {
            return null;
        }
        
        ListNode slow = head;  // Tortoise: moves 1 step at a time
        ListNode fast = head;  // Hare: moves 2 steps at a time
        
        // Move until fast reaches end or goes beyond
        while (fast != null && fast.next != null) {
            slow = slow.next;           // Move slow by 1
            fast = fast.next.next;      // Move fast by 2
        }
        
        // Slow is now at middle (or second middle for even length)
        return slow;
    }
    
    /**
     * Alternative: Return first middle for even length
     * 
     * For even length lists (e.g., 4 nodes):
     * - First middle: 2nd node (1-indexed)
     * - Second middle: 3rd node (LeetCode standard)
     * 
     * @param head Head of the linked list
     * @return First middle node for even length
     */
    public static ListNode middleNodeFirst(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Different stopping condition for first middle
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow;
    }
    
    /**
     * Alternative: Two-pass approach
     * 
     * Algorithm:
     * 1. First pass: calculate length of list
     * 2. Second pass: move to middle position
     * 
     * Advantages:
     * - Simpler to understand
     * - Can easily get first or second middle
     * 
     * Disadvantages:
     * - Two passes through list
     * - Still O(n) time but less efficient
     */
    public static ListNode middleNodeTwoPass(ListNode head) {
        if (head == null) {
            return null;
        }
        
        // First pass: calculate length
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        
        // Second pass: move to middle
        int middleIndex = length / 2;  // Integer division gives second middle
        current = head;
        for (int i = 0; i < middleIndex; i++) {
            current = current.next;
        }
        
        return current;
    }
    
    /**
     * Alternative: Using ArrayList/List
     * 
     * Algorithm:
     * 1. Store all nodes in ArrayList
     * 2. Return middle element from list
     * 
     * Advantages:
     * - Simple implementation
     * - Easy to get any position
     * 
     * Disadvantages:
     * - O(n) extra space
     * - Not suitable for large lists
     */
    public static ListNode middleNodeArrayList(ListNode head) {
        if (head == null) {
            return null;
        }
        
        ArrayList<ListNode> nodes = new ArrayList<>();
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
     * Alternative: Recursive approach
     * 
     * Algorithm:
     * 1. Recurse to end of list
     * 2. Count back to find middle
     * 
     * Advantages:
     * - Elegant recursive solution
     * 
     * Disadvantages:
     * - O(n) recursion stack space
     * - More complex
     */
    public static ListNode middleNodeRecursive(ListNode head) {
        if (head == null) {
            return null;
        }
        
        int[] length = {0};
        return middleNodeRecursiveHelper(head, 0, length);
    }
    
    private static ListNode middleNodeRecursiveHelper(ListNode node, int index, int[] totalLength) {
        if (node == null) {
            totalLength[0] = index;
            return null;
        }
        
        ListNode middle = middleNodeRecursiveHelper(node.next, index + 1, totalLength);
        
        // Calculate middle index
        if (index == totalLength[0] / 2) {
            return node;
        }
        
        return middle;
    }
    
    /**
     * Extended: Split list at middle
     * 
     * @param head Head of the linked list
     * @return Array containing [firstHalf, secondHalf]
     */
    public static ListNode[] splitAtMiddle(ListNode head) {
        if (head == null) {
            return new ListNode[]{null, null};
        }
        
        // Find first middle (for splitting)
        ListNode slow = head;
        ListNode fast = head;
        ListNode prev = null;
        
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        
        // Split the list
        ListNode firstHalf = head;
        ListNode secondHalf = null;
        
        if (prev != null) {
            prev.next = null;
            secondHalf = slow;
        } else {
            // Single node or empty
            secondHalf = slow.next;
            if (secondHalf == null) {
                return new ListNode[]{head, null};
            }
        }
        
        return new ListNode[]{firstHalf, secondHalf};
    }
    
    /**
     * Extended: Check if list is palindrome using middle
     * 
     * Algorithm:
     * 1. Find middle of list
     * 2. Reverse second half
     * 3. Compare with first half
     * 
     * @param head Head of the linked list
     * @return true if list is palindrome
     */
    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        
        // Find middle
        ListNode middle = middleNodeFirst(head);
        
        // Reverse second half
        ListNode secondHalf = reverseList(middle.next);
        
        // Compare halves
        ListNode p1 = head;
        ListNode p2 = secondHalf;
        
        while (p2 != null) {
            if (p1.val != p2.val) {
                return false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        
        return true;
    }
    
    /**
     * Helper: Reverse linked list
     */
    private static ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode current = head;
        
        while (current != null) {
            ListNode next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        
        return prev;
    }
    
    /**
     * Extended: Find kth node from middle
     * 
     * @param head Head of the linked list
     * @param k Position from middle (positive = towards end, negative = towards start)
     * @return Node k positions from middle
     */
    public static ListNode kthFromMiddle(ListNode head, int k) {
        if (head == null) {
            return null;
        }
        
        // First find middle
        ListNode middle = middleNode(head);
        
        if (k == 0) {
            return middle;
        }
        
        // Move k positions from middle
        ListNode current = middle;
        if (k > 0) {
            // Move towards end
            for (int i = 0; i < k; i++) {
                if (current == null) return null;
                current = current.next;
            }
        } else {
            // Move towards start (need to traverse from head)
            int stepsFromHead = getLength(head) / 2 + k;
            if (stepsFromHead < 0) return null;
            
            current = head;
            for (int i = 0; i < stepsFromHead; i++) {
                current = current.next;
            }
        }
        
        return current;
    }
    
    /**
     * Helper: Get length of linked list
     */
    private static int getLength(ListNode head) {
        int length = 0;
        ListNode current = head;
        while (current != null) {
            length++;
            current = current.next;
        }
        return length;
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
     * Helper: Print linked list with middle highlighted
     */
    public static void printListWithMiddle(ListNode head) {
        if (head == null) {
            System.out.println("List: (empty)");
            return;
        }
        
        // Find middle first
        ListNode middle = middleNode(head);
        
        System.out.print("List: ");
        ListNode current = head;
        
        while (current != null) {
            if (current == middle) {
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
     * Helper: Visualize fast/slow pointer algorithm
     */
    public static void visualizeTwoPointer(ListNode head) {
        if (head == null) {
            System.out.println("Empty list");
            return;
        }
        
        System.out.println("Fast/Slow Pointer Visualization:");
        System.out.println("Initial: S,F→" + listToString(head));
        
        ListNode slow = head;
        ListNode fast = head;
        int step = 1;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            System.out.println("Step " + step + ": S at " + slow.val + 
                             ", F at " + (fast != null ? fast.val : "null"));
            step++;
        }
        
        System.out.println("Middle node: " + slow.val);
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
    public static void compareMethods(ListNode head, String description) {
        System.out.println("\n" + description + ":");
        
        // Method 1: Fast/slow pointer
        long start = System.nanoTime();
        ListNode result1 = middleNode(head);
        long end = System.nanoTime();
        System.out.println("Fast/Slow: " + result1.val + " (" + (end - start) + " ns)");
        
        // Method 2: Two-pass
        start = System.nanoTime();
        ListNode result2 = middleNodeTwoPass(head);
        end = System.nanoTime();
        System.out.println("Two-pass: " + result2.val + " (" + (end - start) + " ns)");
        
        // Method 3: ArrayList
        start = System.nanoTime();
        ListNode result3 = middleNodeArrayList(head);
        end = System.nanoTime();
        System.out.println("ArrayList: " + result3.val + " (" + (end - start) + " ns)");
        
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
        System.out.println("=== Find Middle of Linked List ===\n");
        
        // Test Case 1: Odd length list
        System.out.println("1. Odd Length List (5 nodes):");
        ListNode oddList = arrayToList(new int[]{1, 2, 3, 4, 5});
        printListWithMiddle(oddList);
        System.out.println("Middle value: " + middleNode(oddList).val);
        System.out.println("Expected: 3 (exact middle)");
        
        // Test Case 2: Even length list (second middle)
        System.out.println("\n2. Even Length List - Second Middle (6 nodes):");
        ListNode evenList = arrayToList(new int[]{1, 2, 3, 4, 5, 6});
        printListWithMiddle(evenList);
        System.out.println("Middle value: " + middleNode(evenList).val);
        System.out.println("Expected: 4 (second middle)");
        
        // Test Case 3: First middle for even length
        System.out.println("\n3. Even Length List - First Middle:");
        System.out.println("First middle value: " + middleNodeFirst(evenList).val);
        System.out.println("Expected: 3 (first middle)");
        
        // Test Case 4: Single node
        System.out.println("\n4. Single Node:");
        ListNode single = new ListNode(99);
        printListWithMiddle(single);
        System.out.println("Middle value: " + middleNode(single).val);
        
        // Test Case 5: Two nodes
        System.out.println("\n5. Two Nodes:");
        ListNode twoNodes = arrayToList(new int[]{10, 20});
        printListWithMiddle(twoNodes);
        System.out.println("Second middle: " + middleNode(twoNodes).val);
        System.out.println("First middle: " + middleNodeFirst(twoNodes).val);
        
        // Test Case 6: Empty list
        System.out.println("\n6. Empty List:");
        System.out.println("Middle of null: " + middleNode(null));
        
        // Test Case 7: Split at middle
        System.out.println("\n7. Split List at Middle:");
        ListNode splitList = arrayToList(new int[]{1, 2, 3, 4, 5, 6});
        System.out.print("Original: ");
        printListWithMiddle(splitList);
        
        ListNode[] halves = splitAtMiddle(splitList);
        System.out.print("First half: ");
        printListWithMiddle(halves[0]);
        System.out.print("Second half: ");
        printListWithMiddle(halves[1]);
        
        // Test Case 8: Palindrome check using middle
        System.out.println("\n8. Palindrome Check:");
        ListNode palindrome = arrayToList(new int[]{1, 2, 3, 2, 1});
        System.out.print("List: ");
        printListWithMiddle(palindrome);
        System.out.println("Is palindrome? " + isPalindrome(palindrome));
        
        ListNode notPalindrome = arrayToList(new int[]{1, 2, 3, 4, 5});
        System.out.print("List: ");
        printListWithMiddle(notPalindrome);
        System.out.println("Is palindrome? " + isPalindrome(notPalindrome));
        
        // Test Case 9: kth from middle
        System.out.println("\n9. kth from Middle:");
        ListNode kthList = arrayToList(new int[]{1, 2, 3, 4, 5, 6, 7});
        printListWithMiddle(kthList);
        
        System.out.println("Middle: " + kthFromMiddle(kthList, 0).val);
        System.out.println("1 after middle: " + kthFromMiddle(kthList, 1).val);
        System.out.println("1 before middle: " + kthFromMiddle(kthList, -1).val);
        
        // Test Case 10: Method comparison
        System.out.println("\n10. Method Comparison:");
        ListNode compareList = arrayToList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        compareMethods(compareList, "10-node list");
        
        // Test Case 11: Visualization
        System.out.println("\n11. Algorithm Visualization:");
        ListNode visList = arrayToList(new int[]{1, 2, 3, 4, 5, 6});
        visualizeTwoPointer(visList);
        
        // Test Case 12: Large list performance
        System.out.println("\n12. Large List Performance:");
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i;
        }
        ListNode largeList = arrayToList(largeArray);
        
        long startTime = System.currentTimeMillis();
        ListNode largeMiddle = middleNode(largeList);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Found middle of 10,000 node list in " + 
                          (endTime - startTime) + " ms");
        System.out.println("Middle value: " + largeMiddle.val);
        System.out.println("Expected: ~5000");
        
        // Test Case 13: Recursive approach
        System.out.println("\n13. Recursive Approach:");
        ListNode recList = arrayToList(new int[]{1, 2, 3, 4, 5});
        ListNode recMiddle = middleNodeRecursive(recList);
        System.out.println("Recursive middle: " + recMiddle.val);
        
        // Test Case 14: All methods on same list
        System.out.println("\n14. All Methods Verification:");
        ListNode testList = arrayToList(new int[]{10, 20, 30, 40, 50});
        
        System.out.println("Fast/slow: " + middleNode(testList).val);
        System.out.println("Two-pass: " + middleNodeTwoPass(testList).val);
        System.out.println("ArrayList: " + middleNodeArrayList(testList).val);
        System.out.println("Recursive: " + middleNodeRecursive(testList).val);
        System.out.println("First middle: " + middleNodeFirst(testList).val);
        
        // Test Case 15: Real-world applications
        System.out.println("\n15. Real-World Applications:");
        System.out.println("1. Merge Sort on Linked Lists:");
        System.out.println("   - Find middle to split list for divide & conquer");
        System.out.println("   - Essential for O(n log n) merge sort");
        
        System.out.println("\n2. Binary Search on Linked Lists:");
        System.out.println("   - Find middle to compare and eliminate half");
        System.out.println("   - Convert to skip list for efficient search");
        
        System.out.println("\n3. Load Balancing:");
        System.out.println("   - Distribute tasks to middle of queue");
        System.out.println("   - Implement round-robin scheduling");
        
        System.out.println("\n4. Data Structure Balancing:");
        System.out.println("   - Balance binary search trees");
        System.out.println("   - Create balanced BST from sorted linked list");
        
        // Test Case 16: Edge cases and special patterns
        System.out.println("\n16. Edge Cases and Patterns:");
        
        // Very long odd list
        ListNode longOdd = arrayToList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9});
        System.out.println("9-node list middle: " + middleNode(longOdd).val);
        
        // Very long even list
        ListNode longEven = arrayToList(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        System.out.println("10-node list second middle: " + middleNode(longEven).val);
        System.out.println("10-node list first middle: " + middleNodeFirst(longEven).val);
        
        // Test Case 17: Algorithm walkthrough
        System.out.println("\n17. Fast/Slow Pointer Walkthrough:");
        System.out.println("List: 1 → 2 → 3 → 4 → 5 → 6");
        System.out.println("\nStep 0: S=1, F=1");
        System.out.println("Step 1: S=2, F=3");
        System.out.println("Step 2: S=3, F=5");
        System.out.println("Step 3: S=4, F=null (F.next would be null)");
        System.out.println("\nStop! F is null or F.next is null");
        System.out.println("Middle: S at node 4 ✓");
        
        // Test Case 18: Mathematical insight
        System.out.println("\n18. Mathematical Insight:");
        System.out.println("Let n = number of nodes");
        System.out.println("Fast pointer moves 2 steps per iteration");
        System.out.println("Slow pointer moves 1 step per iteration");
        System.out.println("When fast reaches end (n steps), slow has moved n/2 steps");
        System.out.println("Thus slow is at position n/2 (middle)");
        System.out.println("For even n: n/2 gives second middle (e.g., 6/2=3, 0-indexed)");
        
        // Test Case 19: Different middle definitions
        System.out.println("\n19. Different Middle Definitions:");
        ListNode defList = arrayToList(new int[]{1, 2, 3, 4, 5, 6});
        System.out.println("List of 6 nodes: 1 → 2 → 3 → 4 → 5 → 6");
        System.out.println("Second middle (LeetCode): " + middleNode(defList).val);
        System.out.println("First middle: " + middleNodeFirst(defList).val);
        System.out.println("Both middles (for even length): [3, 4]");
    }
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. WRONG STOPPING CONDITION:
     *    ❌ while (fast.next != null && fast.next.next != null) // Gets first middle
     *    ✅ while (fast != null && fast.next != null) // Gets second middle
     *    
     * 2. NOT CHECKING FOR NULL:
     *    Always check if head is null first
     *    
     * 3. MODIFYING ORIGINAL LIST:
     *    Some approaches (like splitting) modify the list
     *    Document if method has side effects
     *    
     * 4. OFF-BY-ONE ERRORS:
     *    Be clear about 0-indexed vs 1-indexed positions
     *    
     * 5. INFINITE LOOP:
     *    Ensure fast pointer moves correctly (fast.next.next)
     */
    
    /**
     * Fast/Slow Pointer Mathematical Analysis:
     * 
     * For list with n nodes:
     * - Fast pointer moves 2 steps per iteration
     * - Slow pointer moves 1 step per iteration
     * 
     * Number of iterations = n/2 (when n is even) or (n+1)/2 (when n is odd)
     * 
     * Position of slow after k iterations = k (0-indexed)
     * Position of fast after k iterations = 2k
     * 
     * When fast reaches or passes end:
     * - If n is odd: fast at position n-1, slow at (n-1)/2 = middle
     * - If n is even: fast at position n, slow at n/2 = second middle
     */
    
    /**
     * Complexity Analysis:
     * 
     * Fast/Slow Pointer:
     * Time: O(n) - Single pass through half the list
     * Space: O(1) - Two pointers
     * 
     * Two-pass Method:
     * Time: O(2n) = O(n) - Two full passes
     * Space: O(1) - Only length counter
     * 
     * ArrayList Method:
     * Time: O(n) - One pass to store, O(1) to access middle
     * Space: O(n) - Store all nodes
     * 
     * Recursive Method:
     * Time: O(n) - Recursive traversal
     * Space: O(n) - Recursion stack
     */
    
    /**
     * Variations and Extensions:
     * 
     * 1. FIND MIDDLE IN DOUBLY LINKED LIST:
     *    Same algorithm works, or traverse from both ends
     *    
     * 2. FIND MIDDLE IN CIRCULAR LIST:
     *    Need cycle detection first
     *    
     * 3. FIND K MIDDLES:
     *    Return array of k middle nodes
     *    
     * 4. WEIGHTED MIDDLE:
     *    Consider node weights when finding middle
     *    
     * 5. FIND MIDDLE IN MULTIPLE LISTS:
     *    Find middle of merged list without merging
     */
    
    /**
     * Interview Tips:
     * 
     * 1. EXPLAIN BOTH EVEN AND ODD CASES:
     *    Clarify which middle you're returning
     *    
     * 2. WALK THROUGH EXAMPLE:
     *    Show step-by-step for both even and odd lengths
     *    
     * 3. DISCUSS ALTERNATIVES:
     *    Mention two-pass, array list approaches
     *    
     * 4. HANDLE EDGE CASES:
     *    Empty list, single node, two nodes
     *    
     * 5. EXTEND TO RELATED PROBLEMS:
     *    Mention palindrome check, list splitting
     */
}