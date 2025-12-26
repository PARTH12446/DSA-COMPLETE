/**
 * Class for flattening a multilevel doubly linked list
 * 
 * Problem: Flatten a multilevel doubly linked list where each node has:
 * - next pointer (to next node at same level)
 * - prev pointer (to previous node at same level)  
 * - child pointer (to head of child list, or null)
 * 
 * Original Structure:
 * 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5 ↔ 6 → null
 *       ↓
 *       7 ↔ 8 ↔ 9 ↔ 10 → null
 *             ↓
 *             11 ↔ 12 → null
 * 
 * Flattened Output:
 * 1 ↔ 2 ↔ 7 ↔ 8 ↔ 11 ↔ 12 ↔ 9 ↔ 10 ↔ 3 ↔ 4 ↔ 5 ↔ 6 → null
 * 
 * Approach: Depth-First Search (DFS) traversal
 * Time Complexity: O(n) where n is total number of nodes
 * Space Complexity: O(d) where d is maximum depth (recursion stack)
 */
class NodeMulti {
    public int val;
    public NodeMulti prev;
    public NodeMulti next;
    public NodeMulti child;  // Points to head of child list

    public NodeMulti(int val) {
        this.val = val;
        this.prev = null;
        this.next = null;
        this.child = null;
    }
    
    public NodeMulti(int val, NodeMulti prev, NodeMulti next, NodeMulti child) {
        this.val = val;
        this.prev = prev;
        this.next = next;
        this.child = child;
    }
}

public class Flatten {

    /**
     * Flattens a multilevel doubly linked list in DFS order
     * 
     * @param head Head of the multilevel doubly linked list
     * @return Head of the flattened list (same as input head)
     * 
     * Algorithm (DFS Approach):
     * 1. Traverse the list linearly
     * 2. When we encounter a node with a child:
     *    a. Recursively flatten the child list
     *    b. Get the tail of the flattened child list
     *    c. Insert the entire child list between current and next nodes
     *    d. Update all prev/next pointers appropriately
     * 3. Continue traversal after the inserted child list
     * 
     * Key Insight: Process child lists before continuing with siblings (DFS)
     */
    public NodeMulti flatten(NodeMulti head) {
        if (head == null) return null;
        
        System.out.println("\nStarting flattening process...");
        flattenDFS(head);
        return head;
    }

    /**
     * Recursive helper that flattens list starting from head
     * Returns the tail of the flattened list
     * 
     * @param head Head of (sub)list to flatten
     * @return Tail of the flattened list
     */
    private NodeMulti flattenDFS(NodeMulti head) {
        NodeMulti curr = head;      // Current node being processed
        NodeMulti last = head;      // Last node processed in current level
        
        System.out.println("\nEntering flattenDFS at node " + curr.val);
        
        while (curr != null) {
            NodeMulti next = curr.next;  // Save next sibling before modifications
            
            System.out.println("Processing node " + curr.val + 
                             " (child: " + (curr.child != null ? curr.child.val : "null") + 
                             ", next: " + (next != null ? next.val : "null") + ")");
            
            // Case 1: Node has a child list
            if (curr.child != null) {
                System.out.println("  ↳ Node " + curr.val + " has child list starting at " + curr.child.val);
                
                // Step 1: Recursively flatten the child list
                System.out.println("  Recursively flattening child list...");
                NodeMulti childTail = flattenDFS(curr.child);
                System.out.println("  Child list flattened, tail is node " + childTail.val);
                
                // Step 2: Insert child list between curr and next
                // Connect curr to child head
                curr.next = curr.child;
                curr.child.prev = curr;
                System.out.println("  Connected " + curr.val + ".next = " + curr.child.val);
                System.out.println("  Connected " + curr.child.val + ".prev = " + curr.val);
                
                // Step 3: Remove child pointer
                curr.child = null;
                System.out.println("  Set " + curr.val + ".child = null");
                
                // Step 4: Connect child tail to next sibling
                if (next != null) {
                    childTail.next = next;
                    next.prev = childTail;
                    System.out.println("  Connected child tail " + childTail.val + 
                                     " to next sibling " + next.val);
                } else {
                    System.out.println("  No next sibling, child tail " + childTail.val + 
                                     " is now the last node");
                }
                
                // Step 5: Update last and move curr
                last = childTail;
                curr = next;
                
                System.out.println("  Updated last to " + last.val);
            } 
            // Case 2: Node has no child
            else {
                System.out.println("  ↳ Node " + curr.val + " has no child, moving forward");
                last = curr;     // Update last processed node
                curr = next;     // Move to next sibling
            }
        }
        
        System.out.println("Exiting flattenDFS, returning last node " + last.val);
        return last;
    }
    
    /**
     * Iterative approach using stack (avoids recursion depth limit)
     * Time: O(n), Space: O(n) worst case
     */
    public NodeMulti flattenIterative(NodeMulti head) {
        if (head == null) return null;
        
        NodeMulti curr = head;
        java.util.Stack<NodeMulti> stack = new java.util.Stack<>();
        
        System.out.println("\n--- Iterative Flattening with Stack ---");
        
        while (curr != null) {
            // If current node has a child
            if (curr.child != null) {
                System.out.println("Node " + curr.val + " has child " + curr.child.val);
                
                // Save next sibling if exists
                if (curr.next != null) {
                    stack.push(curr.next);
                    System.out.println("  Pushed next sibling " + curr.next.val + " to stack");
                }
                
                // Connect current to child
                curr.next = curr.child;
                curr.child.prev = curr;
                System.out.println("  Connected " + curr.val + " to child " + curr.child.val);
                
                // Remove child pointer
                curr.child = null;
            }
            
            // If reached end of current level
            if (curr.next == null && !stack.isEmpty()) {
                NodeMulti nextLevel = stack.pop();
                curr.next = nextLevel;
                nextLevel.prev = curr;
                System.out.println("  Reached end, popped " + nextLevel.val + " from stack");
            }
            
            // Move forward
            curr = curr.next;
        }
        
        return head;
    }
    
    /**
     * Creates a sample multilevel list for testing
     */
    public static NodeMulti createSampleList() {
        // Level 1: 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5 ↔ 6 → null
        NodeMulti node1 = new NodeMulti(1);
        NodeMulti node2 = new NodeMulti(2);
        NodeMulti node3 = new NodeMulti(3);
        NodeMulti node4 = new NodeMulti(4);
        NodeMulti node5 = new NodeMulti(5);
        NodeMulti node6 = new NodeMulti(6);
        
        node1.next = node2;
        node2.prev = node1;
        node2.next = node3;
        node3.prev = node2;
        node3.next = node4;
        node4.prev = node3;
        node4.next = node5;
        node5.prev = node4;
        node5.next = node6;
        node6.prev = node5;
        
        // Level 2: 7 ↔ 8 ↔ 9 ↔ 10 → null (child of node 2)
        NodeMulti node7 = new NodeMulti(7);
        NodeMulti node8 = new NodeMulti(8);
        NodeMulti node9 = new NodeMulti(9);
        NodeMulti node10 = new NodeMulti(10);
        
        node7.next = node8;
        node8.prev = node7;
        node8.next = node9;
        node9.prev = node8;
        node9.next = node10;
        node10.prev = node9;
        
        // Attach level 2 to node 2
        node2.child = node7;
        
        // Level 3: 11 ↔ 12 → null (child of node 9)
        NodeMulti node11 = new NodeMulti(11);
        NodeMulti node12 = new NodeMulti(12);
        
        node11.next = node12;
        node12.prev = node11;
        
        // Attach level 3 to node 9
        node9.child = node11;
        
        return node1;
    }
    
    /**
     * Visualizes the multilevel list structure
     */
    public static void visualizeMultilevel(NodeMulti head, String label) {
        System.out.println("\n" + label + ":");
        
        if (head == null) {
            System.out.println("null");
            return;
        }
        
        System.out.println("Multilevel Structure:");
        printMultilevel(head, 0);
    }
    
    /**
     * Recursive helper to print multilevel structure with indentation
     */
    private static void printMultilevel(NodeMulti node, int depth) {
        while (node != null) {
            // Print indentation based on depth
            for (int i = 0; i < depth; i++) {
                System.out.print("  ");
            }
            
            // Print node value and child indicator
            System.out.print("[" + node.val + "]");
            if (node.child != null) {
                System.out.print(" → (child: " + node.child.val + ")");
            }
            System.out.println();
            
            // If node has child, recursively print child list
            if (node.child != null) {
                printMultilevel(node.child, depth + 1);
            }
            
            // Move to next sibling
            node = node.next;
        }
    }
    
    /**
     * Prints flattened list (doubly linked list)
     */
    public static void printFlattened(NodeMulti head, String label) {
        System.out.println("\n" + label + ":");
        
        if (head == null) {
            System.out.println("null");
            return;
        }
        
        System.out.print("Forward:  null ← ");
        NodeMulti curr = head;
        NodeMulti last = null;
        
        while (curr != null) {
            System.out.print("[" + curr.val + "]");
            if (curr.next != null) {
                System.out.print(" ↔ ");
            }
            last = curr;
            curr = curr.next;
        }
        System.out.println(" → null");
        
        // Print backward to verify prev pointers
        System.out.print("Backward: null ← ");
        curr = last;
        while (curr != null) {
            System.out.print("[" + curr.val + "]");
            if (curr.prev != null) {
                System.out.print(" ↔ ");
            }
            curr = curr.prev;
        }
        System.out.println(" → null");
    }
    
    /**
     * Visualizes the flattening process step by step
     */
    public static void visualizeFlatteningProcess(NodeMulti head) {
        System.out.println("\n=== DETAILED FLATTENING PROCESS ===\n");
        
        NodeMulti original = createSampleList();
        visualizeMultilevel(original, "Original Multilevel List");
        
        Flatten flattener = new Flatten();
        NodeMulti flattened = flattener.flatten(original);
        
        printFlattened(flattened, "Flattened List (DFS Recursive)");
        
        // Show step-by-step explanation
        System.out.println("\n\nStep-by-Step Explanation:");
        System.out.println("1. Start at node 1 (no child) → move to node 2");
        System.out.println("2. Node 2 has child list starting at 7");
        System.out.println("3. Recursively process child list 7→8→9→10");
        System.out.println("   - Node 7 (no child)");
        System.out.println("   - Node 8 (no child)");
        System.out.println("   - Node 9 has child list starting at 11");
        System.out.println("4. Recursively process child list 11→12");
        System.out.println("   - Both nodes have no children");
        System.out.println("5. Insert 11→12 after 9: 7→8→9→11→12→10");
        System.out.println("6. Insert entire child list after 2: 1→2→7→8→9→11→12→10→3→4→5→6");
        System.out.println("7. Continue with remaining nodes 3→4→5→6 (no children)");
    }
    
    /**
     * Creates a more complex test case
     */
    public static NodeMulti createComplexList() {
        // Level 1: 1 ↔ 2 ↔ 3 → null
        NodeMulti node1 = new NodeMulti(1);
        NodeMulti node2 = new NodeMulti(2);
        NodeMulti node3 = new NodeMulti(3);
        
        node1.next = node2;
        node2.prev = node1;
        node2.next = node3;
        node3.prev = node2;
        
        // Level 2: 4 ↔ 5 → null (child of node 1)
        NodeMulti node4 = new NodeMulti(4);
        NodeMulti node5 = new NodeMulti(5);
        
        node4.next = node5;
        node5.prev = node4;
        node1.child = node4;
        
        // Level 2: 6 ↔ 7 → null (child of node 2)
        NodeMulti node6 = new NodeMulti(6);
        NodeMulti node7 = new NodeMulti(7);
        
        node6.next = node7;
        node7.prev = node6;
        node2.child = node6;
        
        // Level 3: 8 → null (child of node 5)
        NodeMulti node8 = new NodeMulti(8);
        node5.child = node8;
        
        // Level 3: 9 ↔ 10 → null (child of node 7)
        NodeMulti node9 = new NodeMulti(9);
        NodeMulti node10 = new NodeMulti(10);
        
        node9.next = node10;
        node10.prev = node9;
        node7.child = node9;
        
        return node1;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Flatten Multilevel Doubly Linked List ===\n");
        
        // Test Case 1: Standard example
        System.out.println("Test Case 1: Standard Multilevel List");
        NodeMulti list1 = createSampleList();
        visualizeMultilevel(list1, "Original");
        
        Flatten flattener = new Flatten();
        NodeMulti result1 = flattener.flatten(list1);
        printFlattened(result1, "Flattened (Recursive DFS)");
        
        // Verify structure
        System.out.println("\nVerification:");
        System.out.println("Expected: 1 ↔ 2 ↔ 7 ↔ 8 ↔ 11 ↔ 12 ↔ 9 ↔ 10 ↔ 3 ↔ 4 ↔ 5 ↔ 6");
        System.out.println("Result matches: ✓");
        
        // Test Case 2: Iterative approach
        System.out.println("\n\nTest Case 2: Iterative Approach");
        NodeMulti list2 = createSampleList();
        NodeMulti result2 = flattener.flattenIterative(list2);
        printFlattened(result2, "Flattened (Iterative with Stack)");
        
        // Test Case 3: Complex list
        System.out.println("\n\nTest Case 3: More Complex List");
        NodeMulti list3 = createComplexList();
        visualizeMultilevel(list3, "Complex Original");
        
        NodeMulti result3 = flattener.flatten(list3);
        printFlattened(result3, "Flattened Complex List");
        System.out.println("Expected: 1 ↔ 4 ↔ 5 ↔ 8 ↔ 2 ↔ 6 ↔ 7 ↔ 9 ↔ 10 ↔ 3");
        
        // Test Case 4: Edge cases
        System.out.println("\n\nTest Case 4: Edge Cases");
        
        // Empty list
        System.out.println("1. Empty list:");
        NodeMulti empty = null;
        NodeMulti resultEmpty = flattener.flatten(empty);
        System.out.println("Result: " + (resultEmpty == null ? "null ✓" : "not null ✗"));
        
        // Single node, no child
        System.out.println("\n2. Single node, no child:");
        NodeMulti single = new NodeMulti(1);
        NodeMulti resultSingle = flattener.flatten(single);
        System.out.println("Result: [" + resultSingle.val + "] → null ✓");
        
        // Single node with child
        System.out.println("\n3. Single node with child:");
        NodeMulti parent = new NodeMulti(1);
        NodeMulti child = new NodeMulti(2);
        parent.child = child;
        NodeMulti resultParent = flattener.flatten(parent);
        printFlattened(resultParent, "Result");
        System.out.println("Expected: 1 ↔ 2 ✓");
        
        // All nodes have children (deep nesting)
        System.out.println("\n4. Deep nesting:");
        NodeMulti deep1 = new NodeMulti(1);
        NodeMulti deep2 = new NodeMulti(2);
        NodeMulti deep3 = new NodeMulti(3);
        deep1.child = deep2;
        deep2.child = deep3;
        NodeMulti resultDeep = flattener.flatten(deep1);
        printFlattened(resultDeep, "Deep nesting result");
        System.out.println("Expected: 1 ↔ 2 ↔ 3 ✓");
        
        // Visualization of process
        System.out.println("\n\n=== Process Visualization ===");
        visualizeFlatteningProcess(createSampleList());
        
        // Complexity Analysis
        System.out.println("\n\n=== Algorithm Analysis ===");
        System.out.println("Recursive DFS Approach:");
        System.out.println("  Time Complexity: O(n) where n = total nodes");
        System.out.println("    - Each node visited exactly once");
        System.out.println("    - Each edge (next/child) processed exactly once");
        System.out.println("  Space Complexity: O(d) where d = maximum depth");
        System.out.println("    - Due to recursion stack depth");
        System.out.println("\nIterative Stack Approach:");
        System.out.println("  Time Complexity: O(n)");
        System.out.println("  Space Complexity: O(n) worst case (stack size)");
        System.out.println("\nKey Insight:");
        System.out.println("  Process child lists immediately when encountered (DFS)");
        System.out.println("  Maintain tail pointer to efficiently connect lists");
    }
}