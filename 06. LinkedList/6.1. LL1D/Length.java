/**
 * Length Operations in Singly Linked List
 * 
 * This class demonstrates various ways to calculate the length/size
 * of a singly linked list. Determining list length is a fundamental
 * operation used in many list manipulations.
 * 
 * Methods Covered:
 * 1. Iterative length calculation
 * 2. Recursive length calculation
 * 3. Length with conditions
 * 4. Length of sublists
 * 
 * Key Concepts:
 * - Counting nodes during traversal
 * - Handling empty lists
 * - Time/space tradeoffs (iterative vs recursive)
 * - Edge cases and optimization
 */

public class Length {
    
    /**
     * ITERATIVE LENGTH CALCULATION
     * 
     * Algorithm:
     * 1. Initialize count to 0
     * 2. Start from head node
     * 3. While current node is not null:
     *    - Increment count
     *    - Move to next node
     * 4. Return count
     * 
     * @param head Head node of the linked list
     * @return Number of nodes in the list (0 if empty)
     * 
     * Time Complexity: O(n) - Must visit every node
     * Space Complexity: O(1) - Only uses constant extra space
     * 
     * Example:
     * List: 10 -> 20 -> 30 -> null
     * Length = 3
     */
    public static int getLength(ListNode head) {
        int length = 0;
        ListNode current = head;
        
        while (current != null) {
            length++;
            current = current.next;
        }
        
        return length;
    }
    
    /**
     * RECURSIVE LENGTH CALCULATION
     * 
     * Base case: Empty list (head == null) returns 0
     * Recursive case: 1 + length of rest of list
     * 
     * @param head Head node of the linked list
     * @return Number of nodes in the list
     * 
     * Time Complexity: O(n) - Each node visited once
     * Space Complexity: O(n) - Recursion stack depth
     * 
     * Note: For very long lists, may cause stack overflow
     */
    public static int getLengthRecursive(ListNode head) {
        // Base case: empty list
        if (head == null) {
            return 0;
        }
        
        // Recursive case: count current node + rest of list
        return 1 + getLengthRecursive(head.next);
    }
    
    /**
     * LENGTH WITH TAIL RECURSION (optimized)
     * 
     * Tail recursion can be optimized by some compilers
     * to avoid stack buildup (though Java doesn't guarantee TCO)
     * 
     * @param head Head node of list
     * @return Length of list
     */
    public static int getLengthTailRecursive(ListNode head) {
        return getLengthTailRecursiveHelper(head, 0);
    }
    
    private static int getLengthTailRecursiveHelper(ListNode node, int count) {
        if (node == null) {
            return count;
        }
        return getLengthTailRecursiveHelper(node.next, count + 1);
    }
    
    /**
     * LENGTH WITH CONDITION (count nodes meeting criteria)
     * 
     * @param head Head node of list
     * @param condition Predicate to test nodes
     * @return Count of nodes meeting condition
     */
    public static int getLengthWithCondition(ListNode head, java.util.function.Predicate<ListNode> condition) {
        int count = 0;
        ListNode current = head;
        
        while (current != null) {
            if (condition.test(current)) {
                count++;
            }
            current = current.next;
        }
        
        return count;
    }
    
    /**
     * LENGTH OF EVEN-VALUED NODES
     * 
     * @param head Head node of list
     * @return Count of nodes with even values
     */
    public static int getLengthEven(ListNode head) {
        return getLengthWithCondition(head, node -> node.val % 2 == 0);
    }
    
    /**
     * LENGTH OF ODD-VALUED NODES
     * 
     * @param head Head node of list
     * @return Count of nodes with odd values
     */
    public static int getLengthOdd(ListNode head) {
        return getLengthWithCondition(head, node -> node.val % 2 != 0);
    }
    
    /**
     * LENGTH UNTIL TARGET (nodes before target value)
     * 
     * @param head Head node of list
     * @param target Value to stop counting at
     * @return Number of nodes before first occurrence of target
     *         (or full length if target not found)
     */
    public static int getLengthUntil(ListNode head, int target) {
        int count = 0;
        ListNode current = head;
        
        while (current != null && current.val != target) {
            count++;
            current = current.next;
        }
        
        return count;
    }
    
    /**
     * LENGTH FROM NODE (distance from given node to end)
     * 
     * @param node Starting node
     * @return Number of nodes from given node to end
     */
    public static int getLengthFromNode(ListNode node) {
        int length = 0;
        ListNode current = node;
        
        while (current != null) {
            length++;
            current = current.next;
        }
        
        return length;
    }
    
    /**
     * IS EMPTY CHECK
     * 
     * @param head Head node of list
     * @return true if list is empty (head == null)
     */
    public static boolean isEmpty(ListNode head) {
        return head == null;
    }
    
    /**
     * HAS AT LEAST N NODES
     * 
     * Optimized version that stops early if possible
     * 
     * @param head Head node of list
     * @param n Minimum number of nodes to check for
     * @return true if list has at least n nodes
     */
    public static boolean hasAtLeastN(ListNode head, int n) {
        int count = 0;
        ListNode current = head;
        
        while (current != null && count < n) {
            count++;
            current = current.next;
        }
        
        return count == n;
    }
    
    /**
     * GET LENGTH WITH FAST/SLOW POINTERS (alternative approach)
     * 
     * This method uses the fast/slow pointer technique
     * which is useful for cycle detection as well
     * 
     * @param head Head node of list
     * @return Length of list
     */
    public static int getLengthTwoPointers(ListNode head) {
        if (head == null) {
            return 0;
        }
        
        ListNode slow = head;
        ListNode fast = head;
        int length = 1;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            length += 2;
        }
        
        // Adjust if fast pointer ended early
        if (fast == null) {
            length--; // Even number of nodes
        }
        
        return length;
    }
    
    /**
     * Main method with comprehensive testing
     */
    public static void main(String[] args) {
        System.out.println("=== Linked List Length Operations ===\n");
        
        // Test 1: Basic length calculation
        System.out.println("1. Basic Length Calculation:");
        
        // Test with various lists
        ListNode emptyList = null;
        System.out.println("Empty list length: " + getLength(emptyList) + " (expected: 0)");
        
        ListNode singleList = new ListNode(5);
        System.out.println("Single node list length: " + getLength(singleList) + " (expected: 1)");
        
        ListNode testList = Intro.buildFromArray(new int[]{10, 20, 30, 40, 50});
        System.out.print("Test list: ");
        Intro.printList(testList);
        System.out.println("Length: " + getLength(testList) + " (expected: 5)");
        
        // Test 2: Recursive length
        System.out.println("\n2. Recursive Length Calculation:");
        System.out.println("Iterative length: " + getLength(testList) + " (expected: 5)");
        System.out.println("Recursive length: " + getLengthRecursive(testList) + " (expected: 5)");
        System.out.println("Tail recursive length: " + getLengthTailRecursive(testList) + " (expected: 5)");
        
        // Test 3: Length with conditions
        System.out.println("\n3. Conditional Length:");
        ListNode mixedList = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10});
        System.out.print("Mixed list: ");
        Intro.printList(mixedList);
        
        System.out.println("Even-valued nodes: " + getLengthEven(mixedList) + " (expected: 5)");
        System.out.println("Odd-valued nodes: " + getLengthOdd(mixedList) + " (expected: 5)");
        
        // Custom condition: values greater than 5
        int countGreaterThan5 = getLengthWithCondition(mixedList, node -> node.val > 5);
        System.out.println("Nodes > 5: " + countGreaterThan5 + " (expected: 5)");
        
        // Custom condition: prime numbers
        int countPrimes = getLengthWithCondition(mixedList, node -> isPrime(node.val));
        System.out.println("Prime numbers: " + countPrimes + " (expected: 4 - 2,3,5,7)");
        
        // Test 4: Length until target
        System.out.println("\n4. Length Until Target:");
        ListNode searchList = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5, 6, 7});
        System.out.print("Search list: ");
        Intro.printList(searchList);
        
        System.out.println("Length until 4: " + getLengthUntil(searchList, 4) + " (expected: 3)");
        System.out.println("Length until 1: " + getLengthUntil(searchList, 1) + " (expected: 0)");
        System.out.println("Length until 7: " + getLengthUntil(searchList, 7) + " (expected: 6)");
        System.out.println("Length until 10 (not found): " + getLengthUntil(searchList, 10) + " (expected: 7)");
        
        // Test 5: Length from node
        System.out.println("\n5. Length From Node:");
        ListNode middleNode = searchList.next.next; // Node with value 3
        System.out.println("Length from node with value " + middleNode.val + ": " + 
                          getLengthFromNode(middleNode) + " (expected: 5)");
        
        ListNode lastNode = searchList.next.next.next.next.next.next; // Node with value 7
        System.out.println("Length from last node: " + getLengthFromNode(lastNode) + " (expected: 1)");
        
        // Test 6: Utility methods
        System.out.println("\n6. Utility Methods:");
        System.out.println("Is empty list empty? " + isEmpty(emptyList) + " (expected: true)");
        System.out.println("Is test list empty? " + isEmpty(testList) + " (expected: false)");
        
        System.out.println("Has at least 3 nodes? " + hasAtLeastN(testList, 3) + " (expected: true)");
        System.out.println("Has at least 10 nodes? " + hasAtLeastN(testList, 10) + " (expected: false)");
        
        // Test 7: Two pointers method
        System.out.println("\n7. Two Pointers Method:");
        System.out.println("Two pointers length (odd): " + getLengthTwoPointers(testList) + " (expected: 5)");
        
        ListNode evenList = Intro.buildFromArray(new int[]{1, 2, 3, 4});
        System.out.print("Even length list: ");
        Intro.printList(evenList);
        System.out.println("Two pointers length (even): " + getLengthTwoPointers(evenList) + " (expected: 4)");
        
        // Test 8: Performance comparison
        System.out.println("\n8. Performance Comparison:");
        
        // Build large list
        int largeSize = 100000;
        ListNode largeList = null;
        for (int i = 0; i < largeSize; i++) {
            largeList = Insert.insertAtHead(largeList, i);
        }
        
        // Measure iterative approach
        long start = System.currentTimeMillis();
        int iterLength = getLength(largeList);
        long end = System.currentTimeMillis();
        System.out.println("Iterative (" + largeSize + " nodes): " + (end - start) + " ms, length: " + iterLength);
        
        // Measure recursive approach (may cause stack overflow for very large lists)
        if (largeSize <= 10000) { // Safe recursion depth
            start = System.currentTimeMillis();
            int recLength = getLengthRecursive(largeList);
            end = System.currentTimeMillis();
            System.out.println("Recursive (" + largeSize + " nodes): " + (end - start) + " ms, length: " + recLength);
        } else {
            System.out.println("Skipping recursive test for " + largeSize + " nodes (risk of stack overflow)");
        }
        
        // Measure two pointers approach
        start = System.currentTimeMillis();
        int twoPtrLength = getLengthTwoPointers(largeList);
        end = System.currentTimeMillis();
        System.out.println("Two pointers (" + largeSize + " nodes): " + (end - start) + " ms, length: " + twoPtrLength);
        
        // Test 9: Edge cases
        System.out.println("\n9. Edge Cases:");
        
        // Circular list detection would be infinite loop
        // We create a small cycle for demonstration (carefully)
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        node1.next = node2;
        node2.next = node3;
        node3.next = node2; // Cycle: 3 -> 2
        
        System.out.println("WARNING: Calling getLength on cyclic list would cause infinite loop!");
        System.out.println("(We won't actually execute it)");
        
        // Test 10: Real-world scenario
        System.out.println("\n10. Real-World Scenario:");
        // Simulating a student record list
        ListNode students = Intro.buildFromArray(new int[]{18, 21, 19, 22, 20, 25, 19, 18, 23});
        System.out.print("Student ages: ");
        Intro.printList(students);
        
        // Count students eligible to vote (age >= 18)
        int eligibleVoters = getLengthWithCondition(students, node -> node.val >= 18);
        System.out.println("Eligible voters (age >= 18): " + eligibleVoters + " (expected: 9)");
        
        // Count students in typical college age (18-22)
        int collegeAge = getLengthWithCondition(students, node -> node.val >= 18 && node.val <= 22);
        System.out.println("College age students (18-22): " + collegeAge + " (expected: 7)");
    }
    
    /**
     * Helper method to check if a number is prime
     */
    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Length Operations Complexity Summary:
     * 
     * Operation                   Time    Space    Notes
     * Iterative Length            O(n)    O(1)     Most common, safe for all lists
     * Recursive Length            O(n)    O(n)     Risk of stack overflow
     * Tail Recursive Length       O(n)    O(n)     May be optimized by compiler
     * Conditional Length          O(n)    O(1)     Early stop not possible
     * Length Until Target         O(n)    O(1)     May stop early if target found
     * Has At Least N              O(min(n,N)) O(1) Can stop early
     * Two Pointers Length         O(n)    O(1)     Useful for cycle detection too
     */
    
    /**
     * When to Use Which Method:
     * 
     * USE ITERATIVE WHEN:
     * - List may be long (risk of stack overflow)
     * - You need maximum performance
     * - Code clarity is important
     * 
     * USE RECURSIVE WHEN:
     * - List is guaranteed to be short
     * - Teaching/learning recursion
     * - Code elegance is prioritized
     * 
     * USE CONDITIONAL WHEN:
     * - You need specific subset count
     * - Filtering based on node properties
     * - Statistical analysis of list
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. INFINITE LOOP IN CYCLIC LISTS:
     *    Standard length methods don't handle cycles
     *    Solution: Use cycle detection (Floyd's algorithm)
     *    
     * 2. OFF-BY-ONE ERRORS:
     *    Starting count at 1 instead of 0
     *    Solution: Initialize count = 0
     *    
     * 3. MODIFYING HEAD:
     *    ListNode current = head;
     *    while (current != null) {
     *        count++;
     *        current = current.next;
     *    }
     *    // head is unchanged - this is correct
     *    
     * 4. NOT HANDLING NULL:
     *    Always check if head == null first
     */
}