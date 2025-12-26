/**
 * Class for detecting and finding the start of a cycle in a linked list
 * Uses Floyd's Cycle Detection Algorithm (Tortoise and Hare algorithm)
 * 
 * Problem: Given a linked list, determine if it has a cycle, and if so,
 * return the node where the cycle begins. If no cycle, return null.
 * 
 * Floyd's Algorithm has two phases:
 * Phase 1: Detect if a cycle exists using slow and fast pointers
 * Phase 2: Find the start of the cycle by resetting one pointer
 * 
 * Mathematical Proof:
 * Let:
 * - L = distance from head to cycle start
 * - C = length of cycle
 * - x = distance from cycle start to meeting point
 * 
 * When slow and fast meet:
 * Distance traveled by slow = L + x
 * Distance traveled by fast = L + x + nC (n is number of cycles)
 * 
 * Since fast is twice as fast as slow:
 * 2(L + x) = L + x + nC
 * L + x = nC
 * L = nC - x
 * 
 * This means distance from head to cycle start (L) equals 
 * (n cycles - x) which is the same as moving from meeting point
 * (x steps forward) backward (C - x) steps n-1 times.
 * 
 * Time Complexity: O(n) where n is number of nodes
 * Space Complexity: O(1) (uses only two pointers)
 */
public class StartOfCycle {

    /**
     * Detects if a cycle exists and returns the start node of the cycle
     * 
     * @param head The head node of the linked list
     * @return The node where the cycle begins, or null if no cycle exists
     * 
     * Algorithm Steps:
     * 1. Phase 1: Cycle Detection (Floyd's Tortoise and Hare)
     *    - Initialize slow and fast pointers at head
     *    - Move slow by 1 step, fast by 2 steps
     *    - If they meet, cycle exists
     *    - If fast reaches null, no cycle
     *    
     * 2. Phase 2: Find Cycle Start
     *    - Reset slow to head
     *    - Move both slow and fast by 1 step at a time
     *    - They will meet at the cycle start node
     *    
     * 3. Return the meeting point (cycle start) or null
     */
    public static ListNode detectCycleStart(ListNode head) {
        // Edge case: empty list or single node with no cycle
        if (head == null || head.next == null) {
            return null;
        }
        
        // Phase 1: Detect if cycle exists using Floyd's algorithm
        ListNode slow = head;    // Tortoise - moves 1 step at a time
        ListNode fast = head;    // Hare - moves 2 steps at a time
        boolean hasCycle = false;
        
        // Traverse the list with two pointers
        // fast checks both fast and fast.next to avoid NullPointerException
        while (fast != null && fast.next != null) {
            // Move slow by 1 step
            slow = slow.next;
            
            // Move fast by 2 steps
            fast = fast.next.next;
            
            // If slow and fast meet, cycle exists
            if (slow == fast) {
                hasCycle = true;
                break;  // Exit the detection phase
            }
        }
        
        // If no cycle found, return null
        if (!hasCycle) {
            return null;
        }
        
        // Phase 2: Find the start of the cycle
        // Mathematical proof: 
        // Distance from head to cycle start = distance from meeting point to cycle start
        // when moving both pointers at same speed
        
        // Reset slow to head, keep fast at meeting point
        slow = head;
        
        // Move both pointers one step at a time
        // They will meet at the cycle start node
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;  // Fast now moves at same speed as slow
        }
        
        // Both pointers now point to the cycle start node
        return slow;
    }
    
    /**
     * Alternative method that combines both phases without boolean flag
     * More concise but slightly less readable
     */
    public static ListNode detectCycleStartConcise(ListNode head) {
        if (head == null) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        // Phase 1: Detect cycle
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            
            if (slow == fast) {
                // Phase 2: Find cycle start
                slow = head;
                while (slow != fast) {
                    slow = slow.next;
                    fast = fast.next;
                }
                return slow;  // Cycle start
            }
        }
        
        return null;  // No cycle
    }
    
    /**
     * Helper method to create a cycle in a linked list for testing
     * 
     * @param values Array of values for the list
     * @param cycleIndex The index (0-based) where the cycle should start
     *                  If cycleIndex >= values.length, no cycle created
     * @return Head of the linked list with cycle
     */
    public static ListNode createListWithCycle(int[] values, int cycleIndex) {
        if (values == null || values.length == 0) {
            return null;
        }
        
        // Create nodes
        ListNode[] nodes = new ListNode[values.length];
        for (int i = 0; i < values.length; i++) {
            nodes[i] = new ListNode(values[i]);
        }
        
        // Link nodes
        for (int i = 0; i < values.length - 1; i++) {
            nodes[i].next = nodes[i + 1];
        }
        
        // Create cycle if cycleIndex is valid
        if (cycleIndex >= 0 && cycleIndex < values.length) {
            nodes[values.length - 1].next = nodes[cycleIndex];
            System.out.println("Created cycle: tail connects to node index " + cycleIndex + " (value: " + values[cycleIndex] + ")");
        } else {
            System.out.println("No cycle created (cycle index out of bounds)");
        }
        
        return nodes[0];
    }
    
    /**
     * Visualizes Floyd's cycle detection algorithm step by step
     */
    public static void visualizeFloydAlgorithm(ListNode head) {
        System.out.println("\nVisualizing Floyd's Cycle Detection Algorithm:");
        System.out.println("===============================================");
        
        ListNode slow = head;
        ListNode fast = head;
        int step = 1;
        boolean cycleDetected = false;
        
        System.out.println("Initial: slow = head, fast = head");
        printListWithPointers(head, slow, fast, step);
        
        while (fast != null && fast.next != null) {
            step++;
            
            // Move pointers
            slow = slow.next;
            fast = fast.next.next;
            
            System.out.println("\nStep " + step + ":");
            System.out.println("slow moves 1 step → node " + slow.val);
            System.out.println("fast moves 2 steps → node " + (fast != null ? fast.val : "null"));
            
            if (slow == fast) {
                System.out.println("✓ CYCLE DETECTED: slow == fast at node " + slow.val);
                cycleDetected = true;
                break;
            }
            
            printListWithPointers(head, slow, fast, step);
        }
        
        if (!cycleDetected) {
            System.out.println("\n✗ NO CYCLE DETECTED: fast reached null");
            return;
        }
        
        // Phase 2: Find cycle start
        System.out.println("\n--- Phase 2: Finding Cycle Start ---");
        System.out.println("Mathematical fact: distance(head, cycleStart) = distance(meetingPoint, cycleStart)");
        
        // Reset slow to head
        slow = head;
        step = 0;
        
        System.out.println("\nReset slow to head:");
        printListWithPointers(head, slow, fast, step);
        
        while (slow != fast) {
            step++;
            slow = slow.next;
            fast = fast.next;
            
            System.out.println("\nStep " + step + ":");
            System.out.println("Both slow and fast move 1 step");
            System.out.println("slow → node " + slow.val);
            System.out.println("fast → node " + fast.val);
            printListWithPointers(head, slow, fast, step);
        }
        
        System.out.println("\n✓ CYCLE START FOUND: slow == fast at node " + slow.val);
        System.out.println("This is the node where the cycle begins!");
    }
    
    /**
     * Helper method to print list with pointer positions
     */
    private static void printListWithPointers(ListNode head, ListNode slow, ListNode fast, int step) {
        ListNode curr = head;
        System.out.print("List state: ");
        
        // To avoid infinite loop in cyclic list, limit iterations
        int maxIterations = 20;
        int count = 0;
        
        while (curr != null && count < maxIterations) {
            String marker = "";
            if (curr == slow && curr == fast) {
                marker = " [S/F]";
            } else if (curr == slow) {
                marker = " [S]";
            } else if (curr == fast) {
                marker = " [F]";
            }
            
            System.out.print(curr.val + marker);
            
            if (curr.next != null && count < maxIterations - 1) {
                System.out.print(" → ");
            }
            
            curr = curr.next;
            count++;
            
            // If we detect we're in a cycle and have shown enough, break
            if (count == maxIterations) {
                System.out.print(" ... (cycle continues)");
            }
        }
        
        if (curr == null) {
            System.out.print(" → null");
        }
        
        System.out.println();
    }
    
    /**
     * Helper method to print list (safe for cycles - limits iterations)
     */
    public static void printList(ListNode head) {
        ListNode current = head;
        System.out.print("List: ");
        
        // For cyclic lists, we need to limit iterations
        int maxIterations = 20;
        int count = 0;
        
        while (current != null && count < maxIterations) {
            System.out.print(current.val);
            if (current.next != null && count < maxIterations - 1) {
                System.out.print(" → ");
            }
            current = current.next;
            count++;
        }
        
        if (current != null) {
            System.out.print(" → ... (cycle)");
        } else {
            System.out.print(" → null");
        }
        System.out.println();
    }
    
    public static void main(String[] args) {
        System.out.println("=== Floyd's Cycle Detection Algorithm ===\n");
        
        // Test Case 1: List with cycle
        System.out.println("Test Case 1: List with cycle (3 → 2 → 0 → -4 → back to 2)");
        int[] arr1 = {3, 2, 0, -4};
        ListNode list1 = createListWithCycle(arr1, 1);  // Cycle starts at index 1 (value 2)
        System.out.print("List with cycle: ");
        printList(list1);
        
        ListNode cycleStart1 = detectCycleStart(list1);
        System.out.println("Cycle start node value: " + (cycleStart1 != null ? cycleStart1.val : "null"));
        visualizeFloydAlgorithm(createListWithCycle(arr1, 1));
        System.out.println();
        
        // Test Case 2: List without cycle
        System.out.println("Test Case 2: List without cycle");
        ListNode list2 = createListWithCycle(new int[]{1, 2, 3, 4}, 5);  // Invalid index = no cycle
        System.out.print("List without cycle: ");
        printList(list2);
        
        ListNode cycleStart2 = detectCycleStart(list2);
        System.out.println("Cycle start node value: " + (cycleStart2 != null ? cycleStart2.val : "null"));
        System.out.println();
        
        // Test Case 3: Single node with self-cycle
        System.out.println("Test Case 3: Single node with self-cycle");
        ListNode singleNode = new ListNode(1);
        singleNode.next = singleNode;  // Self-cycle
        System.out.print("Self-cycling node: ");
        printList(singleNode);
        
        ListNode cycleStart3 = detectCycleStart(singleNode);
        System.out.println("Cycle start node value: " + (cycleStart3 != null ? cycleStart3.val : "null"));
        System.out.println();
        
        // Test Case 4: Two nodes with cycle
        System.out.println("Test Case 4: Two nodes with cycle");
        ListNode nodeA = new ListNode(1);
        ListNode nodeB = new ListNode(2);
        nodeA.next = nodeB;
        nodeB.next = nodeA;  // Cycle: 1 → 2 → 1
        System.out.print("Two-node cycle: ");
        printList(nodeA);
        
        ListNode cycleStart4 = detectCycleStart(nodeA);
        System.out.println("Cycle start node value: " + (cycleStart4 != null ? cycleStart4.val : "null"));
        System.out.println();
        
        // Test Case 5: Concise method
        System.out.println("Test Case 5: Using concise method");
        int[] arr5 = {1, 2, 3, 4, 5};
        ListNode list5 = createListWithCycle(arr5, 2);  // Cycle at node 3
        System.out.print("List: ");
        printList(list5);
        
        ListNode cycleStart5 = detectCycleStartConcise(list5);
        System.out.println("Cycle start (concise method): " + (cycleStart5 != null ? cycleStart5.val : "null"));
        System.out.println();
        
        // Mathematical Proof Explanation
        System.out.println("=== Mathematical Proof of Floyd's Algorithm ===");
        System.out.println("Let:");
        System.out.println("  L = distance from head to cycle start");
        System.out.println("  C = length of cycle");
        System.out.println("  x = distance from cycle start to meeting point");
        System.out.println();
        System.out.println("When slow and fast meet:");
        System.out.println("  Distance(slow) = L + x");
        System.out.println("  Distance(fast) = L + x + nC  (n is number of cycles)");
        System.out.println();
        System.out.println("Since fast is twice as fast:");
        System.out.println("  2(L + x) = L + x + nC");
        System.out.println("  L + x = nC");
        System.out.println("  L = nC - x");
        System.out.println();
        System.out.println("This means: distance from head to cycle start (L)");
        System.out.println("equals moving from meeting point (x steps forward)");
        System.out.println("backward (C - x) steps (n-1 times).");
        System.out.println("Thus, moving from head and meeting point at same speed");
        System.out.println("will meet at cycle start!");
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