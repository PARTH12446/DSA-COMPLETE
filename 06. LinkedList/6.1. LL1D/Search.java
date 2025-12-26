/**
 * Search Operations in Singly Linked List
 * 
 * This class demonstrates search operations in a singly linked list.
 * Searching is a fundamental operation that involves traversing the list
 * to find nodes with specific values or properties.
 * 
 * Types of Searches Covered:
 * 1. Linear Search for a value
 * 2. Find first occurrence
 * 3. Find all occurrences
 * 4. Search with conditions
 * 
 * Key Concepts:
 * - Linear traversal (O(n) complexity)
 * - Early termination on finding target
 * - Handling empty lists and edge cases
 * - Iterative vs recursive approaches
 */

public class Search {
    
    /**
     * LINEAR SEARCH for a target value
     * 
     * Algorithm:
     * 1. Start from head node
     * 2. While current node is not null:
     *    - Compare current node's value with target
     *    - If match found, return true (early exit)
     *    - Move to next node
     * 3. If loop completes without finding, return false
     * 
     * @param head Head node of the linked list
     * @param target Value to search for
     * @return true if value exists, false otherwise
     * 
     * Time Complexity: O(n) in worst case
     * Space Complexity: O(1) extra space
     * 
     * Best Case: O(1) if target is at head
     * Worst Case: O(n) if target at tail or not present
     * Average Case: O(n/2) = O(n)
     * 
     * Example:
     * List: 10 -> 20 -> 30 -> null
     * search(head, 20) returns true
     * search(head, 40) returns false
     */
    public static boolean search(ListNode head, int target) {
        ListNode current = head;
        
        while (current != null) {
            if (current.val == target) {
                return true; // Found target
            }
            current = current.next;
        }
        
        return false; // Target not found
    }
    
    /**
     * SEARCH AND RETURN INDEX (0-based)
     * 
     * @param head Head node of list
     * @param target Value to search for
     * @return Index of first occurrence, -1 if not found
     */
    public static int searchIndex(ListNode head, int target) {
        ListNode current = head;
        int index = 0;
        
        while (current != null) {
            if (current.val == target) {
                return index;
            }
            current = current.next;
            index++;
        }
        
        return -1; // Not found
    }
    
    /**
     * SEARCH AND RETURN NODE REFERENCE
     * 
     * @param head Head node of list
     * @param target Value to search for
     * @return Reference to first node with target value, null if not found
     */
    public static ListNode searchNode(ListNode head, int target) {
        ListNode current = head;
        
        while (current != null) {
            if (current.val == target) {
                return current;
            }
            current = current.next;
        }
        
        return null; // Not found
    }
    
    /**
     * RECURSIVE SEARCH
     * 
     * Base case: Empty list (head == null) return false
     * Recursive case: Check head, then search rest of list
     * 
     * @param head Head node of list
     * @param target Value to search for
     * @return true if value exists, false otherwise
     * 
     * Note: Uses O(n) recursion stack space
     */
    public static boolean searchRecursive(ListNode head, int target) {
        // Base case 1: Empty list
        if (head == null) {
            return false;
        }
        
        // Base case 2: Found at current node
        if (head.val == target) {
            return true;
        }
        
        // Recursive case: Search in remaining list
        return searchRecursive(head.next, target);
    }
    
    /**
     * SEARCH WITH CONDITION (find first node meeting condition)
     * 
     * @param head Head node of list
     * @param condition Predicate to test nodes
     * @return First node meeting condition, null if none
     */
    public static ListNode searchWithCondition(ListNode head, java.util.function.Predicate<ListNode> condition) {
        ListNode current = head;
        
        while (current != null) {
            if (condition.test(current)) {
                return current;
            }
            current = current.next;
        }
        
        return null;
    }
    
    /**
     * FIND ALL OCCURRENCES
     * 
     * @param head Head node of list
     * @param target Value to search for
     * @return List of indices where value occurs
     */
    public static java.util.List<Integer> searchAll(ListNode head, int target) {
        java.util.List<Integer> indices = new java.util.ArrayList<>();
        ListNode current = head;
        int index = 0;
        
        while (current != null) {
            if (current.val == target) {
                indices.add(index);
            }
            current = current.next;
            index++;
        }
        
        return indices;
    }
    
    /**
     * SEARCH FOR MAXIMUM VALUE
     * 
     * @param head Head node of list
     * @return Maximum value in list, Integer.MIN_VALUE if empty
     */
    public static int searchMax(ListNode head) {
        if (head == null) {
            return Integer.MIN_VALUE; // Sentinel for empty list
        }
        
        int max = head.val;
        ListNode current = head.next;
        
        while (current != null) {
            if (current.val > max) {
                max = current.val;
            }
            current = current.next;
        }
        
        return max;
    }
    
    /**
     * SEARCH FOR MINIMUM VALUE
     * 
     * @param head Head node of list
     * @return Minimum value in list, Integer.MAX_VALUE if empty
     */
    public static int searchMin(ListNode head) {
        if (head == null) {
            return Integer.MAX_VALUE; // Sentinel for empty list
        }
        
        int min = head.val;
        ListNode current = head.next;
        
        while (current != null) {
            if (current.val < min) {
                min = current.val;
            }
            current = current.next;
        }
        
        return min;
    }
    
    /**
     * SEARCH FOR NTH NODE FROM BEGINNING
     * 
     * @param head Head node of list
     * @param n Position (1-based)
     * @return Value at nth position, -1 if position invalid
     */
    public static int searchNth(ListNode head, int n) {
        if (n <= 0) {
            return -1; // Invalid position
        }
        
        ListNode current = head;
        int count = 1;
        
        while (current != null) {
            if (count == n) {
                return current.val;
            }
            current = current.next;
            count++;
        }
        
        return -1; // Position beyond list length
    }
    
    /**
     * SEARCH FOR MIDDLE NODE (Tortoise and Hare algorithm)
     * 
     * @param head Head node of list
     * @return Value at middle node
     */
    public static int searchMiddle(ListNode head) {
        if (head == null) {
            return -1; // Empty list
        }
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        
        return slow.val;
    }
    
    /**
     * Main method with comprehensive testing
     */
    public static void main(String[] args) {
        System.out.println("=== Linked List Search Operations ===\n");
        
        // Build test list
        ListNode testList = Intro.buildFromArray(new int[]{10, 20, 30, 20, 40, 50});
        System.out.print("Test List: ");
        Intro.printList(testList);
        
        // Test 1: Basic search
        System.out.println("\n1. Basic Search:");
        System.out.println("Search 30: " + search(testList, 30) + " (expected: true)");
        System.out.println("Search 60: " + search(testList, 60) + " (expected: false)");
        System.out.println("Search 10: " + search(testList, 10) + " (expected: true - at head)");
        System.out.println("Search 50: " + search(testList, 50) + " (expected: true - at tail)");
        
        // Test 2: Search with index
        System.out.println("\n2. Search with Index:");
        System.out.println("Index of 20: " + searchIndex(testList, 20) + " (expected: 1 - first occurrence)");
        System.out.println("Index of 40: " + searchIndex(testList, 40) + " (expected: 4)");
        System.out.println("Index of 100: " + searchIndex(testList, 100) + " (expected: -1)");
        
        // Test 3: Search all occurrences
        System.out.println("\n3. Search All Occurrences:");
        java.util.List<Integer> indices = searchAll(testList, 20);
        System.out.print("Indices of 20: ");
        for (int idx : indices) {
            System.out.print(idx + " ");
        }
        System.out.println("(expected: 1, 3)");
        
        // Test 4: Search min and max
        System.out.println("\n4. Search Min and Max:");
        System.out.println("Maximum value: " + searchMax(testList) + " (expected: 50)");
        System.out.println("Minimum value: " + searchMin(testList) + " (expected: 10)");
        
        // Empty list test
        System.out.println("Max in empty list: " + searchMax(null) + " (expected: " + Integer.MIN_VALUE + ")");
        System.out.println("Min in empty list: " + searchMin(null) + " (expected: " + Integer.MAX_VALUE + ")");
        
        // Test 5: Search nth node
        System.out.println("\n5. Search Nth Node:");
        System.out.println("1st node: " + searchNth(testList, 1) + " (expected: 10)");
        System.out.println("3rd node: " + searchNth(testList, 3) + " (expected: 30)");
        System.out.println("6th node: " + searchNth(testList, 6) + " (expected: 50)");
        System.out.println("7th node: " + searchNth(testList, 7) + " (expected: -1)");
        System.out.println("0th node: " + searchNth(testList, 0) + " (expected: -1)");
        
        // Test 6: Search middle
        System.out.println("\n6. Search Middle Node:");
        System.out.println("Middle of test list: " + searchMiddle(testList) + " (expected: 20)");
        
        ListNode oddList = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5});
        System.out.print("Odd length list: ");
        Intro.printList(oddList);
        System.out.println("Middle: " + searchMiddle(oddList) + " (expected: 3)");
        
        ListNode evenList = Intro.buildFromArray(new int[]{1, 2, 3, 4, 5, 6});
        System.out.print("Even length list: ");
        Intro.printList(evenList);
        System.out.println("Middle: " + searchMiddle(evenList) + " (expected: 4)");
        
        // Test 7: Recursive search
        System.out.println("\n7. Recursive Search:");
        System.out.println("Recursive search 30: " + searchRecursive(testList, 30) + " (expected: true)");
        System.out.println("Recursive search 100: " + searchRecursive(testList, 100) + " (expected: false)");
        System.out.println("Recursive search empty list: " + searchRecursive(null, 10) + " (expected: false)");
        
        // Test 8: Search with condition
        System.out.println("\n8. Search with Condition:");
        // Find first even number greater than 25
        ListNode result = searchWithCondition(testList, node -> node.val > 25 && node.val % 2 == 0);
        if (result != null) {
            System.out.println("First even > 25: " + result.val + " (expected: 30)");
        }
        
        // Find first node with value divisible by 7
        result = searchWithCondition(testList, node -> node.val % 7 == 0);
        if (result != null) {
            System.out.println("First divisible by 7: " + result.val);
        } else {
            System.out.println("No node divisible by 7 (expected: null)");
        }
        
        // Test 9: Search node reference
        System.out.println("\n9. Search Node Reference:");
        ListNode nodeRef = searchNode(testList, 40);
        if (nodeRef != null) {
            System.out.println("Found node with value 40");
            System.out.println("Can modify or use this reference: " + nodeRef.val);
            // Example: Change the value
            nodeRef.val = 45;
            System.out.print("After modifying found node: ");
            Intro.printList(testList);
        }
        
        // Test 10: Performance testing
        System.out.println("\n10. Performance Analysis:");
        int size = 100000;
        ListNode largeList = null;
        
        // Build large list
        for (int i = 0; i < size; i++) {
            largeList = Insert.insertAtHead(largeList, i);
        }
        
        // Measure search time for different positions
        long start, end;
        
        // Search first element
        start = System.currentTimeMillis();
        boolean found = search(largeList, 99999);
        end = System.currentTimeMillis();
        System.out.println("Search first element: " + (end - start) + " ms");
        
        // Search middle element
        start = System.currentTimeMillis();
        found = search(largeList, size / 2);
        end = System.currentTimeMillis();
        System.out.println("Search middle element: " + (end - start) + " ms");
        
        // Search last element (worst case for this list)
        start = System.currentTimeMillis();
        found = search(largeList, 0);
        end = System.currentTimeMillis();
        System.out.println("Search last element: " + (end - start) + " ms");
        
        // Search non-existent element (worst case)
        start = System.currentTimeMillis();
        found = search(largeList, -1);
        end = System.currentTimeMillis();
        System.out.println("Search non-existent: " + (end - start) + " ms");
    }
    
    /**
     * Search Complexity Analysis:
     * 
     * Operation               Time Complexity   Space Complexity   Notes
     * Basic Search            O(n)              O(1)               Linear traversal
     * Recursive Search        O(n)              O(n)               Recursion stack
     * Search with Index       O(n)              O(1)               Same as basic
     * Search All              O(n)              O(k)               k = number of matches
     * Search Min/Max          O(n)              O(1)               Must check all nodes
     * Search Nth              O(n)              O(1)               May traverse entire list
     * Search Middle           O(n)              O(1)               Fast and slow pointers
     * 
     * Optimization Tips:
     * 1. For frequently searched values, consider maintaining a hash table
     * 2. For sorted lists, consider skip lists for O(log n) search
     * 3. For multiple searches, consider caching or indexing
     */
    
    /**
     * Common Search Patterns:
     * 
     * 1. LINEAR TRAVERSAL:
     *    ListNode current = head;
     *    while (current != null) {
     *        // Check condition
     *        current = current.next;
     *    }
     *    
     * 2. TWO POINTER (FAST/SLOW):
     *    ListNode slow = head, fast = head;
     *    while (fast != null && fast.next != null) {
     *        slow = slow.next;
     *        fast = fast.next.next;
     *    }
     *    
     * 3. RECURSIVE TRAVERSAL:
     *    Base case: head == null
     *    Recursive: check head, then head.next
     *    
     * 4. WITH INDEX TRACKING:
     *    int index = 0;
     *    while (current != null) {
     *        // Process
     *        index++;
     *        current = current.next;
     *    }
     */
    
    /**
     * Search Applications:
     * 
     * 1. EXISTENCE CHECK: Does value exist in list?
     * 2. POSITION FINDING: Where is value located?
     * 3. CONDITIONAL SEARCH: Find node meeting specific criteria
     * 4. EXTREMUM FINDING: Find min/max values
     * 5. PATTERN MATCHING: Find sequence of values
     * 6. DUPLICATE DETECTION: Find repeated values
     */
}