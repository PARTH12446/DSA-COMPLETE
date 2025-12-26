/**
 * Class for cloning a linked list with random pointers
 * Problem: Clone a linked list where each node has:
 * - next pointer (points to next node)
 * - random pointer (points to any random node or null)
 * 
 * Original: Node1 → Node2 → Node3 → ... → NodeN
 *           ↓        ↓                  ↓
 *           Random   Random            Random
 * 
 * Challenge: Random pointers create arbitrary connections that must be preserved
 * 
 * Approach (Three-Pass O(n) time, O(1) space):
 * 1. Insert cloned nodes between original nodes
 * 2. Set random pointers for cloned nodes
 * 3. Separate the two lists
 * 
 * Time Complexity: O(3n) = O(n)
 * Space Complexity: O(1) excluding the cloned list
 */
class RandomNode {
    int val;
    RandomNode next;
    RandomNode random;
    
    RandomNode(int val) { 
        this.val = val; 
        this.next = null;
        this.random = null;
    }
    
    RandomNode(int val, RandomNode next, RandomNode random) {
        this.val = val;
        this.next = next;
        this.random = random;
    }
}

public class Clone {

    /**
     * Clones a linked list with random pointers in O(1) extra space
     * 
     * @param head Head of the original linked list
     * @return Head of the cloned linked list
     * 
     * Algorithm (Three Passes):
     * 
     * Pass 1: Insert cloned nodes
     *   Original: A → B → C → D → null
     *   After:    A → A' → B → B' → C → C' → D → D' → null
     *   
     * Pass 2: Set random pointers
     *   For each original node X:
     *     if X.random != null:
     *       X'.random = (X.random).next  // X' is X.next
     *     
     * Pass 3: Separate the lists
     *   Restore original list: A → B → C → D → null
     *   Extract cloned list:   A' → B' → C' → D' → null
     */
    public static RandomNode cloneList(RandomNode head) {
        // Edge case: empty list
        if (head == null) {
            System.out.println("Empty list, returning null");
            return null;
        }
        
        System.out.println("\n=== Step 1: Insert Cloned Nodes ===");
        
        // Pass 1: Insert cloned nodes between original nodes
        RandomNode curr = head;
        while (curr != null) {
            // Create a clone of current node
            RandomNode copy = new RandomNode(curr.val);
            
            // Insert copy between curr and curr.next
            copy.next = curr.next;
            curr.next = copy;
            
            System.out.println("Created copy of node " + curr.val + 
                             " and inserted after it");
            
            // Move to next original node (skip the copy we just inserted)
            curr = copy.next;
        }
        
        System.out.println("\n=== Step 2: Set Random Pointers ===");
        
        // Pass 2: Set random pointers for cloned nodes
        curr = head;
        while (curr != null) {
            RandomNode copy = curr.next;  // Clone is right after original
            
            // If original has a random pointer, set clone's random
            if (curr.random != null) {
                // Important: curr.random.next is the clone of curr.random
                copy.random = curr.random.next;
                System.out.println("Set copy of node " + curr.val + 
                                 ".random to copy of node " + curr.random.val);
            } else {
                System.out.println("Node " + curr.val + ".random is null");
            }
            
            // Move to next original node (skip the clone)
            curr = curr.next.next;
        }
        
        System.out.println("\n=== Step 3: Separate the Lists ===");
        
        // Pass 3: Separate original and cloned lists
        curr = head;
        RandomNode newHead = head.next;  // Head of cloned list
        
        while (curr != null) {
            RandomNode copy = curr.next;  // Current clone
            
            // Restore original's next pointer
            curr.next = copy.next;
            
            // Set clone's next pointer
            if (copy.next != null) {
                copy.next = copy.next.next;
            }
            
            System.out.println("Separated original node " + curr.val + 
                             " from its copy");
            
            // Move to next original node
            curr = curr.next;
        }
        
        System.out.println("\nCloning complete! Returning head of cloned list");
        return newHead;
    }
    
    /**
     * Alternative approach using HashMap (O(n) space, easier to understand)
     * Time: O(n), Space: O(n)
     */
    public static RandomNode cloneListWithMap(RandomNode head) {
        if (head == null) return null;
        
        // Map from original nodes to their clones
        java.util.Map<RandomNode, RandomNode> map = new java.util.HashMap<>();
        
        System.out.println("\n=== HashMap Approach ===");
        
        // First pass: create clones and store in map
        RandomNode curr = head;
        while (curr != null) {
            map.put(curr, new RandomNode(curr.val));
            System.out.println("Mapped original node " + curr.val + " to new clone");
            curr = curr.next;
        }
        
        // Second pass: set next and random pointers
        curr = head;
        while (curr != null) {
            RandomNode copy = map.get(curr);
            
            // Set next pointer
            if (curr.next != null) {
                copy.next = map.get(curr.next);
            }
            
            // Set random pointer
            if (curr.random != null) {
                copy.random = map.get(curr.random);
            }
            
            System.out.println("Set pointers for clone of node " + curr.val);
            curr = curr.next;
        }
        
        return map.get(head);
    }
    
    /**
     * Helper method to create a test linked list with random pointers
     */
    public static RandomNode createTestList() {
        // Create nodes
        RandomNode node1 = new RandomNode(1);
        RandomNode node2 = new RandomNode(2);
        RandomNode node3 = new RandomNode(3);
        RandomNode node4 = new RandomNode(4);
        RandomNode node5 = new RandomNode(5);
        
        // Set next pointers
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        
        // Set random pointers (creating interesting pattern)
        node1.random = node3;  // 1 → 3
        node2.random = node1;  // 2 → 1
        node3.random = node5;  // 3 → 5
        node4.random = node4;  // 4 → 4 (self-loop)
        node5.random = node2;  // 5 → 2
        
        return node1;
    }
    
    /**
     * Visualizes the linked list with both next and random pointers
     */
    public static void visualizeList(RandomNode head, String label) {
        System.out.println("\n" + label + ":");
        
        if (head == null) {
            System.out.println("null");
            return;
        }
        
        // First, print nodes with their values
        RandomNode curr = head;
        int index = 1;
        java.util.Map<RandomNode, Integer> nodeToIndex = new java.util.HashMap<>();
        
        System.out.print("Nodes:     ");
        while (curr != null) {
            nodeToIndex.put(curr, index);
            System.out.print("[" + curr.val + "]");
            if (curr.next != null) System.out.print(" → ");
            curr = curr.next;
            index++;
        }
        System.out.println(" → null");
        
        // Print next pointers
        curr = head;
        System.out.print("Next:      ");
        while (curr != null) {
            System.out.print(" " + nodeToIndex.get(curr) + "  ");
            if (curr.next != null) System.out.print("   →    ");
            curr = curr.next;
        }
        System.out.println();
        
        // Print random pointers
        curr = head;
        System.out.print("Random:    ");
        while (curr != null) {
            if (curr.random != null) {
                System.out.print(" " + nodeToIndex.get(curr) + " → " + 
                               nodeToIndex.get(curr.random));
            } else {
                System.out.print(" " + nodeToIndex.get(curr) + " → null");
            }
            if (curr.next != null) System.out.print(" | ");
            curr = curr.next;
        }
        System.out.println();
        
        // Visual diagram
        System.out.println("\nVisual Diagram:");
        curr = head;
        while (curr != null) {
            System.out.print("[" + curr.val + "]");
            if (curr.next != null) {
                System.out.print(" → ");
            }
            curr = curr.next;
        }
        System.out.println(" → null");
        
        // Draw random pointers below
        System.out.print(" ");
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                System.out.print("↓    ");
            } else {
                System.out.print("     ");
            }
            curr = curr.next;
        }
        System.out.println();
        
        curr = head;
        while (curr != null) {
            if (curr.random != null) {
                System.out.print("[" + curr.random.val + "]  ");
            } else {
                System.out.print("      ");
            }
            curr = curr.next;
        }
        System.out.println();
    }
    
    /**
     * Visualizes the three-step cloning process in detail
     */
    public static void visualizeCloningProcess(RandomNode head) {
        System.out.println("\n=== DETAILED CLONING PROCESS VISUALIZATION ===\n");
        
        RandomNode original = copyListSimple(head);
        System.out.println("Original List:");
        visualizeList(original, "Original");
        
        // Step 1: Insert cloned nodes
        System.out.println("\n\n--- STEP 1: Insert Cloned Nodes ---");
        RandomNode curr = original;
        while (curr != null) {
            RandomNode copy = new RandomNode(curr.val);
            copy.next = curr.next;
            curr.next = copy;
            curr = copy.next;
        }
        visualizeList(original, "After Inserting Copies");
        
        // Step 2: Set random pointers
        System.out.println("\n\n--- STEP 2: Set Random Pointers ---");
        curr = original;
        while (curr != null) {
            RandomNode copy = curr.next;
            if (curr.random != null) {
                copy.random = curr.random.next;
                System.out.println("Set copy of node " + curr.val + 
                                 ".random to copy of node " + curr.random.val);
            }
            curr = curr.next.next;
        }
        visualizeList(original, "After Setting Random Pointers");
        
        // Step 3: Separate the lists
        System.out.println("\n\n--- STEP 3: Separate the Lists ---");
        curr = original;
        RandomNode newHead = original.next;
        RandomNode cloneCurr = newHead;
        
        while (curr != null) {
            curr.next = cloneCurr.next;
            if (cloneCurr.next != null) {
                cloneCurr.next = cloneCurr.next.next;
            }
            System.out.println("Separated original node " + curr.val + 
                             " from its copy");
            curr = curr.next;
            if (curr != null) {
                cloneCurr.next = curr.next;
                cloneCurr = cloneCurr.next;
            }
        }
        
        System.out.println("\nFinal Result:");
        visualizeList(original, "Restored Original List");
        visualizeList(newHead, "Cloned List");
    }
    
    /**
     * Simple list copy without random pointers (for visualization)
     */
    private static RandomNode copyListSimple(RandomNode head) {
        if (head == null) return null;
        
        RandomNode newHead = new RandomNode(head.val);
        RandomNode original = head.next;
        RandomNode copy = newHead;
        
        while (original != null) {
            copy.next = new RandomNode(original.val);
            copy = copy.next;
            original = original.next;
        }
        
        // Copy random pointers
        original = head;
        copy = newHead;
        while (original != null) {
            copy.random = original.random;
            original = original.next;
            copy = copy.next;
        }
        
        return newHead;
    }
    
    /**
     * Validates that two lists are deep copies of each other
     */
    public static boolean validateClone(RandomNode original, RandomNode clone) {
        if (original == null && clone == null) return true;
        if (original == null || clone == null) return false;
        
        java.util.Map<RandomNode, RandomNode> originalToClone = new java.util.HashMap<>();
        RandomNode currOrig = original;
        RandomNode currClone = clone;
        
        // First, verify structure and build mapping
        while (currOrig != null && currClone != null) {
            // Values should match
            if (currOrig.val != currClone.val) {
                System.out.println("Value mismatch: " + currOrig.val + " vs " + currClone.val);
                return false;
            }
            
            // Store mapping
            originalToClone.put(currOrig, currClone);
            
            // Move forward
            currOrig = currOrig.next;
            currClone = currClone.next;
        }
        
        // Both should be null at same time
        if (currOrig != null || currClone != null) {
            System.out.println("Length mismatch");
            return false;
        }
        
        // Verify random pointers using mapping
        currOrig = original;
        currClone = clone;
        while (currOrig != null) {
            if (currOrig.random == null) {
                if (currClone.random != null) {
                    System.out.println("Random pointer mismatch for node " + currOrig.val);
                    return false;
                }
            } else {
                RandomNode expectedCloneRandom = originalToClone.get(currOrig.random);
                if (currClone.random != expectedCloneRandom) {
                    System.out.println("Random pointer mismatch for node " + currOrig.val);
                    return false;
                }
            }
            
            currOrig = currOrig.next;
            currClone = currClone.next;
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Cloning Linked List with Random Pointers ===\n");
        
        // Test Case 1: Normal case
        System.out.println("Test Case 1: Clone a linked list with random pointers");
        RandomNode list1 = createTestList();
        
        System.out.println("\n--- Using O(1) Space Algorithm ---");
        visualizeList(list1, "Original List");
        
        RandomNode clonedList1 = cloneList(list1);
        visualizeList(clonedList1, "Cloned List (O1 Space)");
        
        boolean isValid1 = validateClone(list1, clonedList1);
        System.out.println("\nClone validation: " + (isValid1 ? "PASS ✓" : "FAIL ✗"));
        
        // Test Case 2: Using HashMap approach
        System.out.println("\n\n--- Using HashMap Approach ---");
        RandomNode list2 = createTestList();
        RandomNode clonedList2 = cloneListWithMap(list2);
        visualizeList(clonedList2, "Cloned List (HashMap)");
        
        boolean isValid2 = validateClone(list2, clonedList2);
        System.out.println("\nClone validation: " + (isValid2 ? "PASS ✓" : "FAIL ✗"));
        
        // Test Case 3: Detailed visualization
        System.out.println("\n\nTest Case 3: Detailed Step-by-Step Visualization");
        RandomNode list3 = createTestList();
        visualizeCloningProcess(list3);
        
        // Test Case 4: Edge cases
        System.out.println("\n\nTest Case 4: Edge Cases");
        
        // Empty list
        System.out.println("\n1. Empty list:");
        RandomNode empty = null;
        RandomNode clonedEmpty = cloneList(empty);
        System.out.println("Original: null");
        System.out.println("Clone: " + (clonedEmpty == null ? "null ✓" : "not null ✗"));
        
        // Single node
        System.out.println("\n2. Single node with random = null:");
        RandomNode single = new RandomNode(1);
        RandomNode clonedSingle = cloneList(single);
        System.out.println("Single node cloned: " + 
                          (clonedSingle != null && clonedSingle.val == 1 ? "✓" : "✗"));
        
        // Single node with self-loop
        System.out.println("\n3. Single node with self-loop:");
        RandomNode selfLoop = new RandomNode(1);
        selfLoop.random = selfLoop;
        RandomNode clonedSelfLoop = cloneList(selfLoop);
        boolean selfLoopValid = (clonedSelfLoop != null && 
                                clonedSelfLoop.val == 1 && 
                                clonedSelfLoop.random == clonedSelfLoop);
        System.out.println("Self-loop cloned correctly: " + (selfLoopValid ? "✓" : "✗"));
        
        // Complexity Analysis
        System.out.println("\n\n=== Algorithm Analysis ===");
        System.out.println("Three-Pass O(1) Space Algorithm:");
        System.out.println("  Time Complexity: O(3n) = O(n)");
        System.out.println("    - Pass 1: Insert clones: O(n)");
        System.out.println("    - Pass 2: Set random pointers: O(n)");
        System.out.println("    - Pass 3: Separate lists: O(n)");
        System.out.println("  Space Complexity: O(1) extra space");
        System.out.println("    (excluding the cloned list itself)");
        System.out.println("\nHashMap Approach:");
        System.out.println("  Time Complexity: O(2n) = O(n)");
        System.out.println("  Space Complexity: O(n) for the HashMap");
        System.out.println("\nKey Insight:");
        System.out.println("  The trick is: copy.random = original.random.next");
        System.out.println("  This works because we inserted copies right after originals");
    }
}