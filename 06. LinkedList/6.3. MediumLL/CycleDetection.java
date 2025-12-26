/**
 * Problem: Linked List Cycle Detection
 * 
 * LeetCode 141: https://leetcode.com/problems/linked-list-cycle/
 * 
 * Problem Statement:
 * Given the head of a linked list, determine if the linked list has a cycle.
 * A cycle exists if a node's next pointer points to a node previously visited.
 * 
 * Key Points:
 * - Must detect cycle in O(n) time and O(1) memory
 * - Cannot modify the linked list
 * - Should handle empty lists and single nodes
 * - Floyd's Cycle Detection Algorithm (Tortoise and Hare) is optimal
 * 
 * Example:
 * Input: 3 -> 2 -> 0 -> -4
 *            ↑         ↓
 *             ← ← ← ← ←
 * Output: true (cycle exists)
 * 
 * Applications:
 * - Memory leak detection
 * - Infinite loop detection
 * - Graph cycle detection
 * - Cryptographic algorithms
 */

import java.util.HashSet;

public class CycleDetection {
    
    /**
     * Primary Solution: Floyd's Cycle Detection Algorithm (Tortoise and Hare)
     * 
     * Algorithm:
     * 1. Initialize two pointers: slow (tortoise) and fast (hare)
     * 2. Move slow by one step, fast by two steps
     * 3. If they meet, cycle exists (fast pointer laps slow pointer)
     * 4. If fast reaches null, no cycle exists
     * 
     * Mathematical Proof:
     * - Let distance from start to cycle entry = p
     * - Let cycle length = c
     * - When slow enters cycle, fast is already in cycle
     * - Relative speed = 1 step per iteration
     * - They will meet within c iterations
     * 
     * Time Complexity: O(n) - Linear time
     * Space Complexity: O(1) - Constant extra space
     * 
     * @param head Head node of the linked list
     * @return true if cycle exists, false otherwise
     */
    public static boolean hasCycle(ListNode head) {
        // Edge case: empty list or single node with no cycle
        if (head == null || head.next == null) {
            return false;
        }
        
        ListNode slow = head;  // Tortoise: moves 1 step at a time
        ListNode fast = head;  // Hare: moves 2 steps at a time
        
        // Traverse until fast reaches end or meets slow
        while (fast != null && fast.next != null) {
            slow = slow.next;           // Move slow by 1
            fast = fast.next.next;      // Move fast by 2
            
            // If they meet, cycle exists
            if (slow == fast) {
                return true;
            }
        }
        
        // Fast reached null, no cycle
        return false;
    }
    
    /**
     * Alternative: Hash Table/Set Approach
     * 
     * Algorithm:
     * 1. Traverse the list, storing visited nodes in a hash set
     * 2. If node already in set, cycle exists
     * 3. If reach null, no cycle
     * 
     * Advantages:
     * - Simple to understand
     * - Can also find cycle start node
     * 
     * Disadvantages:
     * - O(n) extra space
     * - Slower due to hash operations
     */
    public static boolean hasCycleHashSet(ListNode head) {
        if (head == null) {
            return false;
        }
        
        HashSet<ListNode> visited = new HashSet<>();
        ListNode current = head;
        
        while (current != null) {
            // If node already visited, cycle exists
            if (visited.contains(current)) {
                return true;
            }
            
            // Mark current node as visited
            visited.add(current);
            current = current.next;
        }
        
        // Reached null, no cycle
        return false;
    }
    
    /**
     * Alternative: Marking Nodes (Modifies List)
     * 
     * Algorithm:
     * 1. Traverse list, marking visited nodes (e.g., set val to special value)
     * 2. If encounter marked node, cycle exists
     * 3. If reach null, no cycle
     * 
     * Disadvantages:
     * - Modifies original list
     * - Requires special value that might conflict with actual data
     * - Not suitable for production
     */
    public static boolean hasCycleMarking(ListNode head) {
        if (head == null) {
            return false;
        }
        
        ListNode current = head;
        
        while (current != null) {
            // Check if node is marked (value = Integer.MIN_VALUE)
            if (current.val == Integer.MIN_VALUE) {
                return true;
            }
            
            // Mark current node
            current.val = Integer.MIN_VALUE;
            current = current.next;
        }
        
        return false;
    }
    
    /**
     * Extended: Find Cycle Start Node (if exists)
     * 
     * LeetCode 142: https://leetcode.com/problems/linked-list-cycle-ii/
     * 
     * Algorithm (Floyd's):
     * 1. Use Floyd's to detect cycle and find meeting point
     * 2. Reset one pointer to head
     * 3. Move both pointers one step at a time
     * 4. They meet at cycle start
     * 
     * Mathematical Proof:
     * - Let distance head->cycleStart = p
     * - Let distance cycleStart->meetingPoint = x
     * - Let cycle length = c
     * - Slow distance = p + x
     * - Fast distance = p + x + n*c (n = number of laps)
     * - Since fast = 2*slow: 2(p + x) = p + x + n*c
     * - Simplifies to: p = n*c - x
     * - So p steps from head = n*c - x steps from meeting point
     */
    public static ListNode detectCycleStart(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        
        // Step 1: Detect cycle using Floyd's
        ListNode slow = head;
        ListNode fast = head;
        boolean hasCycle = false;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }
        
        // No cycle found
        if (!hasCycle) {
            return null;
        }
        
        // Step 2: Find cycle start
        // Reset slow to head, keep fast at meeting point
        slow = head;
        
        // Move both one step at a time
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        
        // Meeting point is cycle start
        return slow;
    }
    
    /**
     * Extended: Calculate Cycle Length
     * 
     * Algorithm:
     * 1. Detect cycle using Floyd's
     * 2. Once meeting point found, keep one pointer fixed
     * 3. Move other pointer until it returns to meeting point
     * 4. Count steps = cycle length
     */
    public static int getCycleLength(ListNode head) {
        if (head == null) {
            return 0;
        }
        
        // Detect cycle and find meeting point
        ListNode meetingPoint = detectCycleStart(head);
        if (meetingPoint == null) {
            return 0;  // No cycle
        }
        
        // Count cycle length
        ListNode current = meetingPoint.next;
        int length = 1;
        
        while (current != meetingPoint) {
            length++;
            current = current.next;
        }
        
        return length;
    }
    
    /**
     * Extended: Break/Cycle Removal
     * 
     * Algorithm:
     * 1. Find cycle start using Floyd's
     * 2. Traverse to node before cycle start
     * 3. Set its next pointer to null
     */
    public static ListNode breakCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        
        // Find cycle start
        ListNode cycleStart = detectCycleStart(head);
        if (cycleStart == null) {
            return head;  // No cycle to break
        }
        
        // Find node before cycle start
        ListNode current = cycleStart;
        while (current.next != cycleStart) {
            current = current.next;
        }
        
        // Break the cycle
        current.next = null;
        
        return head;
    }
    
    /**
     * Helper: Create a linked list with cycle for testing
     * 
     * @param values Array of values for nodes
     * @param pos Position (0-indexed) where tail connects to create cycle
     *            pos = -1 for no cycle
     * @return Head of created linked list
     */
    public static ListNode createListWithCycle(int[] values, int pos) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode[] nodes = new ListNode[values.length];
        ListNode head = new ListNode(values[0]);
        nodes[0] = head;
        
        // Create list
        ListNode current = head;
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
            nodes[i] = current;
        }
        
        // Create cycle if pos is valid
        if (pos >= 0 && pos < values.length) {
            current.next = nodes[pos];
        }
        
        return head;
    }
    
    /**
     * Helper: Visualize list (with cycle detection for safety)
     */
    public static void visualizeList(ListNode head, int maxNodes) {
        if (head == null) {
            System.out.println("List: (empty)");
            return;
        }
        
        System.out.print("List: ");
        ListNode current = head;
        int count = 0;
        HashSet<ListNode> visited = new HashSet<>();
        
        while (current != null && count < maxNodes) {
            if (visited.contains(current)) {
                System.out.print("[" + current.val + "] -> (cycle back)");
                break;
            }
            
            System.out.print(current.val);
            visited.add(current);
            current = current.next;
            count++;
            
            if (current != null && count < maxNodes) {
                System.out.print(" -> ");
            }
        }
        
        if (count >= maxNodes) {
            System.out.print("... (truncated)");
        }
        System.out.println();
    }
    
    /**
     * Helper: Create circular list
     */
    public static ListNode createCircularList(int[] values) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        ListNode head = new ListNode(values[0]);
        ListNode current = head;
        
        for (int i = 1; i < values.length; i++) {
            current.next = new ListNode(values[i]);
            current = current.next;
        }
        
        // Make it circular
        current.next = head;
        return head;
    }
    
    /**
     * Performance comparison of different methods
     */
    public static void compareMethods(ListNode head, String description) {
        System.out.println("\n" + description + ":");
        
        // Method 1: Floyd's Algorithm
        long start = System.nanoTime();
        boolean result1 = hasCycle(head);
        long end = System.nanoTime();
        System.out.println("Floyd's: " + result1 + " (" + (end - start) + " ns)");
        
        // Method 2: HashSet
        start = System.nanoTime();
        boolean result2 = hasCycleHashSet(head);
        end = System.nanoTime();
        System.out.println("HashSet: " + result2 + " (" + (end - start) + " ns)");
        
        // Verify consistency
        if (result1 != result2) {
            System.out.println("WARNING: Inconsistent results!");
        }
    }
    
    /**
     * Main method with comprehensive test cases
     */
    public static void main(String[] args) {
        System.out.println("=== Linked List Cycle Detection ===\n");
        
        // Test Case 1: No cycle (straight list)
        System.out.println("1. No Cycle (Straight List):");
        ListNode list1 = createListWithCycle(new int[]{1, 2, 3, 4, 5}, -1);
        visualizeList(list1, 10);
        System.out.println("Has cycle (Floyd's): " + hasCycle(list1));
        System.out.println("Has cycle (HashSet): " + hasCycleHashSet(list1));
        
        // Test Case 2: Simple cycle
        System.out.println("\n2. Simple Cycle (Tail to Head):");
        ListNode list2 = createListWithCycle(new int[]{1, 2, 3, 4, 5}, 0);
        visualizeList(list2, 10);
        System.out.println("Has cycle: " + hasCycle(list2));
        System.out.println("Cycle start: " + 
                          (detectCycleStart(list2) != null ? 
                           detectCycleStart(list2).val : "null"));
        System.out.println("Cycle length: " + getCycleLength(list2));
        
        // Test Case 3: Cycle in middle
        System.out.println("\n3. Cycle in Middle:");
        ListNode list3 = createListWithCycle(new int[]{3, 2, 0, -4}, 1);
        visualizeList(list3, 10);
        System.out.println("Has cycle: " + hasCycle(list3));
        ListNode cycleStart3 = detectCycleStart(list3);
        System.out.println("Cycle start: " + (cycleStart3 != null ? cycleStart3.val : "null"));
        System.out.println("Cycle length: " + getCycleLength(list3));
        
        // Test Case 4: Self-loop (single node cycle)
        System.out.println("\n4. Self-loop (Single Node Cycle):");
        ListNode list4 = new ListNode(1);
        list4.next = list4;  // Self-loop
        System.out.println("Single node with self-loop");
        System.out.println("Has cycle: " + hasCycle(list4));
        System.out.println("Cycle start: " + 
                          (detectCycleStart(list4) != null ? 
                           detectCycleStart(list4).val : "null"));
        
        // Test Case 5: Two-node cycle
        System.out.println("\n5. Two-Node Cycle:");
        ListNode nodeA = new ListNode(1);
        ListNode nodeB = new ListNode(2);
        nodeA.next = nodeB;
        nodeB.next = nodeA;  // Mutual cycle
        System.out.println("Two nodes pointing to each other");
        System.out.println("Has cycle: " + hasCycle(nodeA));
        System.out.println("Cycle start: " + detectCycleStart(nodeA).val);
        System.out.println("Cycle length: " + getCycleLength(nodeA));
        
        // Test Case 6: Empty list and single node
        System.out.println("\n6. Edge Cases:");
        System.out.println("Empty list: " + hasCycle(null));
        
        ListNode singleNode = new ListNode(1);
        System.out.println("Single node (no cycle): " + hasCycle(singleNode));
        
        // Test Case 7: Large cycle
        System.out.println("\n7. Large Cycle (1000 nodes):");
        int[] largeArray = new int[1000];
        for (int i = 0; i < 1000; i++) {
            largeArray[i] = i;
        }
        ListNode largeList = createListWithCycle(largeArray, 500);
        System.out.println("Created 1000-node list with cycle starting at node 500");
        System.out.println("Has cycle: " + hasCycle(largeList));
        System.out.println("Cycle start value: " + detectCycleStart(largeList).val);
        System.out.println("Cycle length: " + getCycleLength(largeList));
        
        // Test Case 8: Break cycle
        System.out.println("\n8. Break Cycle:");
        ListNode listToBreak = createListWithCycle(new int[]{1, 2, 3, 4, 5}, 2);
        System.out.print("Before break: ");
        visualizeList(listToBreak, 10);
        System.out.println("Has cycle before break: " + hasCycle(listToBreak));
        
        ListNode brokenList = breakCycle(listToBreak);
        System.out.print("After break: ");
        visualizeList(brokenList, 10);
        System.out.println("Has cycle after break: " + hasCycle(brokenList));
        
        // Test Case 9: Performance comparison
        System.out.println("\n9. Performance Comparison:");
        
        // Small list without cycle
        ListNode smallNoCycle = createListWithCycle(new int[]{1, 2, 3, 4, 5}, -1);
        compareMethods(smallNoCycle, "Small list (no cycle)");
        
        // Small list with cycle
        ListNode smallWithCycle = createListWithCycle(new int[]{1, 2, 3, 4, 5}, 2);
        compareMethods(smallWithCycle, "Small list (with cycle)");
        
        // Test Case 10: Circular list
        System.out.println("\n10. Circular List:");
        ListNode circular = createCircularList(new int[]{1, 2, 3, 4, 5});
        System.out.println("Fully circular list (5 nodes in circle)");
        System.out.println("Has cycle: " + hasCycle(circular));
        System.out.println("Cycle length: " + getCycleLength(circular));
        
        // Test Case 11: Complex cycle patterns
        System.out.println("\n11. Complex Cycle Patterns:");
        
        // Cycle with "tail" before cycle
        ListNode complex1 = new ListNode(1);
        ListNode complex2 = new ListNode(2);
        ListNode complex3 = new ListNode(3);
        ListNode complex4 = new ListNode(4);
        ListNode complex5 = new ListNode(5);
        
        complex1.next = complex2;
        complex2.next = complex3;
        complex3.next = complex4;
        complex4.next = complex5;
        complex5.next = complex3;  // Cycle: 3 -> 4 -> 5 -> 3
        
        System.out.println("Pattern: 1 -> 2 -> 3 -> 4 -> 5 -> 3 (cycle)");
        System.out.println("Has cycle: " + hasCycle(complex1));
        System.out.println("Cycle start: " + detectCycleStart(complex1).val);
        System.out.println("Cycle length: " + getCycleLength(complex1));
        
        // Test Case 12: Floyd's algorithm visualization
        System.out.println("\n12. Floyd's Algorithm Step-by-Step:");
        System.out.println("List: 1 -> 2 -> 3 -> 4 -> 5 -> 3 (cycle at node 3)");
        System.out.println("\nStep 0: slow=1, fast=1");
        System.out.println("Step 1: slow=2, fast=3");
        System.out.println("Step 2: slow=3, fast=5");
        System.out.println("Step 3: slow=4, fast=3");
        System.out.println("Step 4: slow=5, fast=5 ← MEET!");
        System.out.println("\nCycle detected at step 4");
        
        // Test Case 13: Real-world applications
        System.out.println("\n13. Real-World Applications:");
        System.out.println("1. Memory Management:");
        System.out.println("   - Detect circular references causing memory leaks");
        System.out.println("   - Garbage collection algorithms");
        
        System.out.println("\n2. Network Routing:");
        System.out.println("   - Detect routing loops in network protocols");
        System.out.println("   - Prevent infinite packet forwarding");
        
        System.out.println("\n3. Cryptography:");
        System.out.println("   - Pollard's rho algorithm for factorization");
        System.out.println("   - Birthday paradox applications");
        
        // Test Case 14: Error handling
        System.out.println("\n14. Error Handling and Validation:");
        
        // Try marking method (warns about modification)
        ListNode testList = createListWithCycle(new int[]{1, 2, 3}, 1);
        System.out.println("Testing marking method (modifies list):");
        boolean hasCycleMarked = hasCycleMarking(testList);
        System.out.println("Result: " + hasCycleMarked);
        System.out.println("WARNING: List values modified! Original values lost.");
        
        // Test with very long non-cyclic list
        System.out.println("\n15. Very Long List (No Cycle):");
        int[] veryLong = new int[100000];
        for (int i = 0; i < veryLong.length; i++) {
            veryLong[i] = i;
        }
        ListNode longList = createListWithCycle(veryLong, -1);
        
        long startTime = System.currentTimeMillis();
        boolean longResult = hasCycle(longList);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Checked 100,000 node list (no cycle) in " + 
                          (endTime - startTime) + " ms");
        System.out.println("Result: " + longResult);
    }
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. NULL POINTER EXCEPTION:
     *    ❌ while (fast.next != null) // Crashes if fast is null
     *    ✅ while (fast != null && fast.next != null)
     *    
     * 2. INFINITE LOOP IN CYCLIC LIST:
     *    Always check for cycle when traversing unknown lists
     *    
     * 3. MODIFYING ORIGINAL LIST:
     *    Some approaches modify node values - document this
     *    
     * 4. ASSUMING UNIQUE VALUES:
     *    Don't rely on values, use node references
     *    
     * 5. MEMORY LEAKS:
     *    Creating cycles intentionally can cause memory issues
     */
    
    /**
     * Floyd's Algorithm Mathematical Proof:
     * 
     * Let:
     * - p = distance from head to cycle start
     * - c = cycle length
     * - x = distance from cycle start to meeting point
     * 
     * When slow enters cycle:
     * - Slow has traveled p steps
     * - Fast has traveled 2p steps (already in cycle)
     * - Fast is (2p - p) = p steps ahead in cycle
     * 
     * Relative speed = 1 step per iteration
     * They meet when: (c - (p % c)) iterations
     * 
     * Meeting point from cycle start: x = (c - (p % c)) % c
     */
    
    /**
     * Complexity Analysis:
     * 
     * Floyd's Algorithm:
     * Time: O(n) - At most 2n steps (slow never goes through cycle twice)
     * Space: O(1) - Two pointers
     * 
     * HashSet Approach:
     * Time: O(n) - One traversal
     * Space: O(n) - Store all nodes
     * 
     * Marking Approach:
     * Time: O(n) - One traversal
     * Space: O(1) - But modifies original data
     */
    
    /**
     * Variations and Extensions:
     * 
     * 1. DETECT CYCLE IN DOUBLY LINKED LIST:
     *    Same algorithm works
     *    
     * 2. DETECT CYCLE IN GRAPH:
     *    Use DFS with visited/visiting states
     *    
     * 3. FIND CYCLE IN ARRAY (SIMILAR PROBLEM):
     *    Treat array indices as pointers
     *    
     * 4. DETECT CYCLE WITH MULTIPLE POINTERS:
     *    k-step and m-step pointers
     *    
     * 5. RANDOMIZED CYCLE DETECTION:
     *    Random walk algorithms
     */
    
    /**
     * Interview Tips:
     * 
     * 1. ALWAYS START WITH FLOYD'S:
     *    Mention it's optimal for time and space
     *    
     * 2. EXPLAIN THE PROOF:
     *    Interviewers love the mathematical reasoning
     *    
     * 3. DISCUSS ALTERNATIVES:
     *    Show you know multiple approaches
     *    
     * 4. HANDLE EDGE CASES:
     *    Empty list, single node, self-loop
     *    
     * 5. EXTEND TO RELATED PROBLEMS:
     *    Mention finding cycle start, length, etc.
     */
}