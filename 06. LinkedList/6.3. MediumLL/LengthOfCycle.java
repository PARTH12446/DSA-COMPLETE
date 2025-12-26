/**
 * Problem: Length of Cycle in Linked List
 * 
 * Problem Statement:
 * Given the head of a linked list, detect if there's a cycle and return its length.
 * If no cycle exists, return 0.
 * 
 * Key Points:
 * - Must detect cycle in O(n) time and O(1) memory
 * - Cannot modify the linked list
 * - Should handle empty lists and single nodes
 * - Floyd's Cycle Detection Algorithm (Tortoise and Hare) is optimal
 * 
 * Example:
 * Input: 3 → 2 → 0 → -4
 *            ↑         ↓
 *             ← ← ← ← ←
 * Cycle length = 3 (nodes: 2 → 0 → -4 → back to 2)
 * 
 * Applications:
 * - Memory leak detection (circular references)
 * - Infinite loop detection
 * - Cycle detection in state machines
 * - Cryptographic algorithms (Pollard's rho)
 */

import java.util.HashSet;

public class LengthOfCycle {
    
    /**
     * Primary Solution: Floyd's Cycle Detection with Length Calculation
     * 
     * Algorithm:
     * 1. Use Floyd's algorithm to detect cycle and find meeting point
     * 2. Once meeting point found, keep one pointer fixed
     * 3. Move other pointer until it returns to meeting point
     * 4. Count steps = cycle length
     * 
     * Mathematical Insight:
     * - When slow and fast meet, they are inside the cycle
     * - Starting from meeting point, one full traversal returns to same point
     * - Number of steps = cycle length
     * 
     * Time Complexity: O(n) - At most 2n steps
     * Space Complexity: O(1) - Only pointers used
     * 
     * @param head Head node of the linked list
     * @return Length of cycle if exists, 0 otherwise
     */
    public static int cycleLength(ListNode head) {
        // Edge case: empty list or single node with no cycle
        if (head == null || head.next == null) {
            return 0;
        }
        
        ListNode slow = head;  // Tortoise: moves 1 step at a time
        ListNode fast = head;  // Hare: moves 2 steps at a time
        
        // Phase 1: Detect cycle using Floyd's algorithm
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            // Cycle detected
            if (slow == fast) {
                // Phase 2: Calculate cycle length
                return calculateCycleLength(slow);
            }
        }
        
        // No cycle found
        return 0;
    }
    
    /**
     * Helper: Calculate cycle length from a node inside the cycle
     * 
     * @param nodeInCycle Any node inside the cycle
     * @return Length of the cycle
     */
    private static int calculateCycleLength(ListNode nodeInCycle) {
        int length = 1;  // Count current node
        ListNode current = nodeInCycle.next;
        
        // Traverse until returning to starting node
        while (current != nodeInCycle) {
            length++;
            current = current.next;
        }
        
        return length;
    }
    
    /**
     * Alternative: Hash Table/Set approach
     * 
     * Algorithm:
     * 1. Traverse list, storing visited nodes in a hash set
     * 2. If node already in set, cycle detected
     * 3. From detection point, count until returning to same node
     * 
     * Advantages:
     * - Simple to understand
     * - Can also find cycle start node
     * 
     * Disadvantages:
     * - O(n) extra space
     * - Slower due to hash operations
     */
    public static int cycleLengthHashSet(ListNode head) {
        if (head == null) {
            return 0;
        }
        
        HashSet<ListNode> visited = new HashSet<>();
        ListNode current = head;
        
        while (current != null) {
            // Check if node already visited (cycle detected)
            if (visited.contains(current)) {
                // Calculate cycle length from this node
                return calculateCycleLengthFromNode(current);
            }
            
            // Mark current node as visited
            visited.add(current);
            current = current.next;
        }
        
        // No cycle found
        return 0;
    }
    
    /**
     * Helper: Calculate cycle length from first repeated node
     */
    private static int calculateCycleLengthFromNode(ListNode startNode) {
        int length = 1;
        ListNode current = startNode.next;
        
        while (current != startNode) {
            length++;
            current = current.next;
        }
        
        return length;
    }
    
    /**
     * Extended: Find cycle start node and length
     * 
     * @param head Head of linked list
     * @return Array containing [cycleStartNode, cycleLength]
     *         If no cycle, returns [null, 0]
     */
    public static Object[] findCycleStartAndLength(ListNode head) {
        if (head == null || head.next == null) {
            return new Object[]{null, 0};
        }
        
        // Detect cycle using Floyd's
        ListNode meetingPoint = detectCycle(head);
        if (meetingPoint == null) {
            return new Object[]{null, 0};
        }
        
        // Find cycle start
        ListNode cycleStart = findCycleStart(head, meetingPoint);
        
        // Calculate cycle length
        int length = calculateCycleLength(meetingPoint);
        
        return new Object[]{cycleStart, length};
    }
    
    /**
     * Helper: Detect cycle using Floyd's algorithm
     * 
     * @param head Head of list
     * @return Meeting point if cycle exists, null otherwise
     */
    private static ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                return slow;  // Meeting point
            }
        }
        
        return null;  // No cycle
    }
    
    /**
     * Helper: Find cycle start node
     * 
     * @param head Head of list
     * @param meetingPoint Node where slow and fast met
     * @return Start node of cycle
     */
    private static ListNode findCycleStart(ListNode head, ListNode meetingPoint) {
        ListNode ptr1 = head;
        ListNode ptr2 = meetingPoint;
        
        // Move both one step at a time until they meet
        while (ptr1 != ptr2) {
            ptr1 = ptr1.next;
            ptr2 = ptr2.next;
        }
        
        return ptr1;  // Cycle start
    }
    
    /**
     * Extended: Check if list has cycle of specific length
     * 
     * @param head Head of list
     * @param targetLength Target cycle length to check
     * @return true if cycle exists with exactly targetLength, false otherwise
     */
    public static boolean hasCycleOfLength(ListNode head, int targetLength) {
        if (targetLength <= 0) {
            return false;
        }
        
        int actualLength = cycleLength(head);
        return actualLength == targetLength;
    }
    
    /**
     * Extended: Find longest possible cycle in list with multiple cycles
     * Note: In a singly linked list, there can be at most one cycle
     * This method would be useful for graphs, not simple linked lists
     */
    public static int findLongestCycle(ListNode head) {
        // For singly linked list, there can be only one cycle
        // So longest cycle = cycle length if exists, 0 otherwise
        return cycleLength(head);
    }
    
    /**
     * Extended: Break the cycle (if exists)
     * 
     * @param head Head of list
     * @return Head of list with cycle broken, or original head if no cycle
     */
    public static ListNode breakCycle(ListNode head) {
        if (head == null) {
            return null;
        }
        
        Object[] cycleInfo = findCycleStartAndLength(head);
        ListNode cycleStart = (ListNode) cycleInfo[0];
        
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
     * Extended: Create list with cycle for testing
     * 
     * @param totalNodes Total number of nodes in list
     * @param cycleStartPos Position where cycle starts (0-indexed)
     * @param cycleLength Length of the cycle
     * @return Head of created linked list with cycle
     */
    public static ListNode createListWithCycle(int totalNodes, int cycleStartPos, int cycleLength) {
        if (totalNodes <= 0) {
            return null;
        }
        
        if (cycleStartPos < 0 || cycleStartPos >= totalNodes || cycleLength <= 0) {
            // Create simple list without cycle
            return createSimpleList(totalNodes);
        }
        
        // Create nodes
        ListNode[] nodes = new ListNode[totalNodes];
        for (int i = 0; i < totalNodes; i++) {
            nodes[i] = new ListNode(i + 1);  // Values 1, 2, 3, ...
        }
        
        // Link nodes linearly
        for (int i = 0; i < totalNodes - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }
        
        // Create cycle
        int cycleEndPos = Math.min(cycleStartPos + cycleLength - 1, totalNodes - 1);
        nodes[cycleEndPos].next = nodes[cycleStartPos];
        
        return nodes[0];
    }
    
    /**
     * Helper: Create simple list without cycle
     */
    private static ListNode createSimpleList(int n) {
        if (n <= 0) {
            return null;
        }
        
        ListNode head = new ListNode(1);
        ListNode current = head;
        
        for (int i = 2; i <= n; i++) {
            current.next = new ListNode(i);
            current = current.next;
        }
        
        return head;
    }
    
    /**
     * Helper: Visualize list with cycle (safely)
     */
    public static void visualizeListWithCycle(ListNode head, int maxNodesToPrint) {
        if (head == null) {
            System.out.println("List: (empty)");
            return;
        }
        
        System.out.print("List: ");
        ListNode current = head;
        int count = 0;
        HashSet<ListNode> visited = new HashSet<>();
        
        while (current != null && count < maxNodesToPrint) {
            if (visited.contains(current)) {
                System.out.print("[" + current.val + "] → (cycle back)");
                break;
            }
            
            System.out.print(current.val);
            visited.add(current);
            current = current.next;
            count++;
            
            if (current != null && count < maxNodesToPrint) {
                System.out.print(" → ");
            }
        }
        
        if (count >= maxNodesToPrint) {
            System.out.print("... (truncated)");
        }
        System.out.println();
    }
    
    /**
     * Helper: Print cycle information
     */
    public static void printCycleInfo(ListNode head) {
        int length = cycleLength(head);
        
        if (length == 0) {
            System.out.println("No cycle detected");
        } else {
            System.out.println("Cycle detected with length: " + length);
            
            // Get more details
            Object[] details = findCycleStartAndLength(head);
            ListNode cycleStart = (ListNode) details[0];
            int detailedLength = (int) details[1];
            
            System.out.println("Cycle starts at node with value: " + 
                              (cycleStart != null ? cycleStart.val : "N/A"));
            System.out.println("Verified cycle length: " + detailedLength);
        }
    }
    
    /**
     * Performance comparison of different methods
     */
    public static void compareMethods(ListNode head, String description) {
        System.out.println("\n" + description + ":");
        
        // Method 1: Floyd's algorithm
        long start = System.nanoTime();
        int result1 = cycleLength(head);
        long end = System.nanoTime();
        System.out.println("Floyd's: " + result1 + " (" + (end - start) + " ns)");
        
        // Method 2: HashSet
        start = System.nanoTime();
        int result2 = cycleLengthHashSet(head);
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
        System.out.println("=== Length of Cycle in Linked List ===\n");
        
        // Test Case 1: Simple cycle
        System.out.println("1. Simple Cycle (Length 3):");
        ListNode cycle1 = createListWithCycle(5, 1, 3);
        visualizeListWithCycle(cycle1, 10);
        printCycleInfo(cycle1);
        
        // Test Case 2: No cycle
        System.out.println("\n2. No Cycle:");
        ListNode noCycle = createSimpleList(5);
        visualizeListWithCycle(noCycle, 10);
        printCycleInfo(noCycle);
        
        // Test Case 3: Self-loop (cycle length 1)
        System.out.println("\n3. Self-loop (Cycle Length 1):");
        ListNode selfLoop = new ListNode(1);
        selfLoop.next = selfLoop;
        System.out.println("Single node pointing to itself");
        printCycleInfo(selfLoop);
        
        // Test Case 4: Two-node cycle
        System.out.println("\n4. Two-node Cycle:");
        ListNode nodeA = new ListNode(1);
        ListNode nodeB = new ListNode(2);
        nodeA.next = nodeB;
        nodeB.next = nodeA;
        System.out.println("Two nodes pointing to each other");
        printCycleInfo(nodeA);
        
        // Test Case 5: Cycle starting at head (full circle)
        System.out.println("\n5. Full Circular List (Cycle at Head):");
        ListNode circular = createListWithCycle(4, 0, 4);
        visualizeListWithCycle(circular, 10);
        printCycleInfo(circular);
        
        // Test Case 6: Large cycle
        System.out.println("\n6. Large Cycle (100 nodes):");
        ListNode largeCycle = createListWithCycle(200, 50, 100);
        System.out.println("Created 200-node list with 100-node cycle starting at node 50");
        
        long startTime = System.currentTimeMillis();
        int largeLength = cycleLength(largeCycle);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Cycle length detected: " + largeLength);
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
        
        // Test Case 7: Edge cases
        System.out.println("\n7. Edge Cases:");
        System.out.println("Empty list: " + cycleLength(null));
        
        ListNode singleNoCycle = new ListNode(1);
        System.out.println("Single node (no cycle): " + cycleLength(singleNoCycle));
        
        // Test Case 8: Find cycle start and length together
        System.out.println("\n8. Find Cycle Start and Length:");
        ListNode testList = createListWithCycle(8, 3, 4);
        visualizeListWithCycle(testList, 12);
        
        Object[] cycleDetails = findCycleStartAndLength(testList);
        ListNode startNode = (ListNode) cycleDetails[0];
        int length = (int) cycleDetails[1];
        
        System.out.println("Cycle starts at node with value: " + 
                          (startNode != null ? startNode.val : "N/A"));
        System.out.println("Cycle length: " + length);
        
        // Test Case 9: Break the cycle
        System.out.println("\n9. Break the Cycle:");
        ListNode listToBreak = createListWithCycle(6, 2, 3);
        System.out.print("Before breaking: ");
        visualizeListWithCycle(listToBreak, 10);
        
        ListNode brokenList = breakCycle(listToBreak);
        System.out.print("After breaking: ");
        visualizeListWithCycle(brokenList, 10);
        System.out.println("Cycle after break: " + cycleLength(brokenList));
        
        // Test Case 10: Method comparison
        System.out.println("\n10. Method Comparison:");
        ListNode compareList = createListWithCycle(100, 20, 30);
        compareMethods(compareList, "100-node list with 30-node cycle");
        
        // Test Case 11: Check for specific cycle length
        System.out.println("\n11. Check for Specific Cycle Length:");
        ListNode specificList = createListWithCycle(10, 3, 4);
        System.out.println("Has cycle of length 4: " + hasCycleOfLength(specificList, 4));
        System.out.println("Has cycle of length 5: " + hasCycleOfLength(specificList, 5));
        
        // Test Case 12: Algorithm walkthrough
        System.out.println("\n12. Floyd's Algorithm Step-by-Step:");
        System.out.println("List: 1 → 2 → 3 → 4 → 5 → 2 (cycle at node 2, length 4)");
        System.out.println("\nPhase 1: Detect cycle");
        System.out.println("S=1, F=1");
        System.out.println("S=2, F=3");
        System.out.println("S=3, F=5");
        System.out.println("S=4, F=3");
        System.out.println("S=5, F=5 ← MEET!");
        System.out.println("\nPhase 2: Calculate length");
        System.out.println("Start at meeting point (node 5)");
        System.out.println("Count: 5 → 2 → 3 → 4 → 5");
        System.out.println("Length = 4 steps");
        
        // Test Case 13: Complex cycle scenarios
        System.out.println("\n13. Complex Cycle Scenarios:");
        
        // Cycle with tail before it
        ListNode complex1 = createListWithCycle(10, 5, 3);
        System.out.println("List with tail (5 nodes) then cycle (3 nodes)");
        printCycleInfo(complex1);
        
        // Very small cycle in long list
        ListNode complex2 = createListWithCycle(1000, 800, 5);
        System.out.println("\n1000-node list with 5-node cycle at position 800");
        System.out.println("Cycle length: " + cycleLength(complex2));
        
        // Test Case 14: Real-world applications
        System.out.println("\n14. Real-World Applications:");
        System.out.println("1. Memory Management:");
        System.out.println("   - Detect circular references causing memory leaks");
        System.out.println("   - Garbage collection algorithms");
        
        System.out.println("\n2. Network Protocols:");
        System.out.println("   - Detect routing loops (TTL expiration)");
        System.out.println("   - Prevent infinite packet forwarding");
        
        System.out.println("\n3. Cryptography:");
        System.out.println("   - Pollard's rho algorithm for integer factorization");
        System.out.println("   - Cycle detection in random number generators");
        
        System.out.println("\n4. State Machines:");
        System.out.println("   - Detect infinite loops in finite automata");
        System.out.println("   - Model checking and verification");
        
        // Test Case 15: Performance with different cycle sizes
        System.out.println("\n15. Performance vs Cycle Size:");
        
        for (int cycleSize : new int[]{10, 100, 1000}) {
            ListNode perfList = createListWithCycle(cycleSize * 2, cycleSize / 2, cycleSize);
            
            startTime = System.currentTimeMillis();
            int detectedLength = cycleLength(perfList);
            endTime = System.currentTimeMillis();
            
            System.out.println("Cycle size " + cycleSize + ": " + 
                              detectedLength + " (detected in " + 
                              (endTime - startTime) + " ms)");
        }
        
        // Test Case 16: Floyd's algorithm mathematical insight
        System.out.println("\n16. Mathematical Insight:");
        System.out.println("Let:");
        System.out.println("  p = distance from head to cycle start");
        System.out.println("  c = cycle length");
        System.out.println("  x = distance from cycle start to meeting point");
        System.out.println("\nWhen slow and fast meet:");
        System.out.println("  Slow distance = p + x");
        System.out.println("  Fast distance = p + x + n*c (n laps around cycle)");
        System.out.println("  Since fast = 2*slow: 2(p + x) = p + x + n*c");
        System.out.println("  Therefore: p = n*c - x");
        System.out.println("\nThis explains why moving from head and meeting point");
        System.out.println("at same speed finds cycle start.");
        
        // Test Case 17: Error cases and validation
        System.out.println("\n17. Error Handling:");
        
        // Try with negative cycle length
        try {
            ListNode invalid = createListWithCycle(5, 2, -1);
            System.out.println("Invalid cycle length handled");
        } catch (Exception e) {
            System.out.println("Caught exception for negative cycle length");
        }
        
        // Test with very large list (memory test)
        System.out.println("\nTesting memory efficiency with large list...");
        ListNode hugeList = createListWithCycle(100000, 50000, 20000);
        System.out.println("Large list created, checking for cycle...");
        System.out.println("Cycle length: " + cycleLength(hugeList));
        System.out.println("Memory efficient (O(1) space) ✓");
    }
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. INFINITE LOOP WHEN COUNTING CYCLE LENGTH:
     *    ❌ Forgetting to move current pointer in while loop
     *    ✅ Always update current = current.next
     *    
     * 2. OFF-BY-ONE IN LENGTH COUNTING:
     *    ❌ Starting count at 0 instead of 1
     *    ✅ Count starts at 1 for current node
     *    
     * 3. NOT CHECKING FOR NULL IN FAST POINTER:
     *    ❌ while (fast.next != null) // Crashes if fast is null
     *    ✅ while (fast != null && fast.next != null)
     *    
     * 4. ASSUMING MEETING POINT IS CYCLE START:
     *    Meeting point is inside cycle, not necessarily start
     *    Need separate logic to find cycle start
     *    
     * 5. MEMORY LEAKS WITH TEST DATA:
     *    Creating cycles intentionally can cause issues
     */
    
    /**
     * Floyd's Algorithm Mathematical Proof:
     * 
     * Let:
     * - m = distance from head to cycle start
     * - k = distance from cycle start to meeting point
     * - n = cycle length
     * - p = number of laps fast pointer made before meeting
     * 
     * When slow and fast meet:
     * Distance traveled by slow = m + k
     * Distance traveled by fast = m + k + p*n
     * 
     * Since fast is twice as fast:
     * 2(m + k) = m + k + p*n
     * m + k = p*n
     * m = p*n - k
     * 
     * This means m steps from head = (p*n - k) steps from meeting point
     * So starting from head and meeting point, moving at same speed,
     * they meet at cycle start after m steps.
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
     * Cycle Length Calculation:
     * Time: O(c) where c is cycle length
     * Space: O(1) - One pointer
     */
    
    /**
     * Variations and Extensions:
     * 
     * 1. DETECT CYCLE IN DOUBLY LINKED LIST:
     *    Same algorithm works, or can use prev pointers
     *    
     * 2. FIND SHORTEST CYCLE IN GRAPH:
     *    More complex, requires BFS/DFS
     *    
     * 3. DETECT MULTIPLE CYCLES:
     *    Not possible in singly linked list (only one next pointer)
     *    
     * 4. FIND CYCLE WITH GIVEN CONSTRAINT:
     *    e.g., cycle containing specific value
     *    
     * 5. RANDOMIZED CYCLE DETECTION:
     *    Random walk algorithms
     */
    
    /**
     * Interview Tips:
     * 
     * 1. START WITH FLOYD'S ALGORITHM:
     *    Explain both phases: detection and length calculation
     *    
     * 2. WALK THROUGH EXAMPLE:
     *    Draw list, show pointer movements
     *    
     * 3. EXPLAIN MATHEMATICAL INSIGHT:
     *    Interviewers appreciate understanding of why it works
     *    
     * 4. HANDLE EDGE CASES:
     *    Empty list, single node, self-loop, no cycle
     *    
     * 5. DISCUSS ALTERNATIVES:
     *    HashSet approach, trade-offs
     */
}