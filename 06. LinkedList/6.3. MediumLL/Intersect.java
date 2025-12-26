/**
 * Problem: Intersection of Two Linked Lists
 * 
 * LeetCode 160: https://leetcode.com/problems/intersection-of-two-linked-lists/
 * 
 * Problem Statement:
 * Given the heads of two singly linked lists, return the node where the two lists intersect.
 * If the two linked lists have no intersection, return null.
 * 
 * Key Points:
 * - Intersection means the two lists share common nodes (same reference, not just value)
 * - Lists may have different lengths before intersection
 * - No cycles in the lists (given constraints)
 * - Must find intersection in O(m+n) time and O(1) memory
 * - Cannot modify the linked lists
 * 
 * Visual Representation:
 * List A: a1 → a2 → a3 → c1 → c2 → c3
 * List B: b1 → b2 → c1 → c2 → c3
 * Intersection at node c1
 */

import java.util.HashSet;
import java.util.Stack;

public class Intersect {
    
    /**
     * Primary Solution: Two-pointer with length alignment
     * 
     * Algorithm:
     * 1. Calculate lengths of both lists
     * 2. Align starting points by moving pointer of longer list forward
     * 3. Move both pointers simultaneously until they meet or reach end
     * 
     * Time Complexity: O(m + n) - Two passes through each list
     * Space Complexity: O(1) - Only pointers used
     * 
     * @param headA Head of first linked list
     * @param headB Head of second linked list
     * @return Intersection node if exists, null otherwise
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // Edge cases: either list is empty
        if (headA == null || headB == null) {
            return null;
        }
        
        // Step 1: Calculate lengths of both lists
        int lenA = 0, lenB = 0;
        ListNode ptrA = headA, ptrB = headB;
        
        while (ptrA != null) {
            lenA++;
            ptrA = ptrA.next;
        }
        
        while (ptrB != null) {
            lenB++;
            ptrB = ptrB.next;
        }
        
        // Step 2: Reset pointers and align starting positions
        ptrA = headA;
        ptrB = headB;
        
        // Move pointer of longer list forward by difference
        if (lenA > lenB) {
            int diff = lenA - lenB;
            while (diff-- > 0) {
                ptrA = ptrA.next;
            }
        } else {
            int diff = lenB - lenA;
            while (diff-- > 0) {
                ptrB = ptrB.next;
            }
        }
        
        // Step 3: Move both pointers until they meet or reach end
        while (ptrA != null && ptrB != null) {
            // Compare references, not values
            if (ptrA == ptrB) {
                return ptrA;  // Intersection found
            }
            ptrA = ptrA.next;
            ptrB = ptrB.next;
        }
        
        // No intersection
        return null;
    }
    
    /**
     * Alternative: Two-pointer "Cycle Detection" approach
     * 
     * Algorithm:
     * 1. Start two pointers at heads of both lists
     * 2. When a pointer reaches end, switch it to head of other list
     * 3. They will meet at intersection or null
     * 
     * Insight:
     * - Total distance traveled by both pointers will be same
     * - If lists intersect: both pointers meet at intersection
     * - If don't intersect: both become null simultaneously
     * 
     * Advantages:
     * - No length calculation needed
     * - Single pass through each list
     */
    public static ListNode getIntersectionNodeTwoPointer(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        
        ListNode ptrA = headA;
        ListNode ptrB = headB;
        
        // Traverse until pointers meet or both become null
        while (ptrA != ptrB) {
            // Move to next or switch to other list's head
            ptrA = (ptrA == null) ? headB : ptrA.next;
            ptrB = (ptrB == null) ? headA : ptrB.next;
        }
        
        // Either intersection node or null
        return ptrA;
    }
    
    /**
     * Alternative: HashSet approach
     * 
     * Algorithm:
     * 1. Traverse first list, store all nodes in hash set
     * 2. Traverse second list, check if node exists in set
     * 3. First match is intersection
     * 
     * Advantages:
     * - Simple to understand and implement
     * - Can find intersection even with cycles (with modification)
     * 
     * Disadvantages:
     * - O(m) or O(n) extra space
     * - Slower due to hash operations
     */
    public static ListNode getIntersectionNodeHashSet(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        
        HashSet<ListNode> visited = new HashSet<>();
        ListNode current = headA;
        
        // Store all nodes from list A
        while (current != null) {
            visited.add(current);
            current = current.next;
        }
        
        // Check list B for any visited node
        current = headB;
        while (current != null) {
            if (visited.contains(current)) {
                return current;  // First intersection found
            }
            current = current.next;
        }
        
        return null;  // No intersection
    }
    
    /**
     * Alternative: Stack approach (Reverse traversal)
     * 
     * Algorithm:
     * 1. Push all nodes of both lists to separate stacks
     * 2. Pop from both stacks simultaneously
     * 3. Last common node is intersection
     * 
     * Advantages:
     * - Intuitive (find common suffix)
     * - Can handle lists of any length
     * 
     * Disadvantages:
     * - O(m+n) extra space
     * - Requires two passes
     */
    public static ListNode getIntersectionNodeStack(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }
        
        Stack<ListNode> stackA = new Stack<>();
        Stack<ListNode> stackB = new Stack<>();
        
        // Push all nodes to stacks
        ListNode current = headA;
        while (current != null) {
            stackA.push(current);
            current = current.next;
        }
        
        current = headB;
        while (current != null) {
            stackB.push(current);
            current = current.next;
        }
        
        // Find last common node by popping from stacks
        ListNode intersection = null;
        while (!stackA.isEmpty() && !stackB.isEmpty()) {
            ListNode nodeA = stackA.pop();
            ListNode nodeB = stackB.pop();
            
            if (nodeA == nodeB) {
                intersection = nodeA;
            } else {
                break;  // No more common nodes
            }
        }
        
        return intersection;
    }
    
    /**
     * Extended: Find all intersection nodes
     * 
     * @param headA First list head
     * @param headB Second list head
     * @return List of all intersection nodes (in order from first intersection)
     */
    public static java.util.List<ListNode> getAllIntersectionNodes(ListNode headA, ListNode headB) {
        java.util.List<ListNode> intersections = new java.util.ArrayList<>();
        
        if (headA == null || headB == null) {
            return intersections;
        }
        
        // Find first intersection
        ListNode firstIntersection = getIntersectionNode(headA, headB);
        
        if (firstIntersection != null) {
            // Add all nodes from intersection point
            ListNode current = firstIntersection;
            while (current != null) {
                intersections.add(current);
                current = current.next;
            }
        }
        
        return intersections;
    }
    
    /**
     * Extended: Check if lists intersect (boolean version)
     */
    public static boolean hasIntersection(ListNode headA, ListNode headB) {
        return getIntersectionNode(headA, headB) != null;
    }
    
    /**
     * Extended: Find intersection value (if nodes store comparable data)
     * 
     * Note: This compares values, not references
     * Use with caution - different nodes can have same value
     */
    public static Integer getIntersectionValue(ListNode headA, ListNode headB) {
        ListNode intersection = getIntersectionNode(headA, headB);
        return (intersection != null) ? intersection.val : null;
    }
    
    /**
     * Extended: Calculate intersection length (how many nodes are shared)
     */
    public static int getIntersectionLength(ListNode headA, ListNode headB) {
        ListNode intersection = getIntersectionNode(headA, headB);
        
        if (intersection == null) {
            return 0;
        }
        
        int length = 0;
        ListNode current = intersection;
        while (current != null) {
            length++;
            current = current.next;
        }
        
        return length;
    }
    
    /**
     * Helper: Create intersecting lists for testing
     * 
     * @param listAValues Values for list A (before intersection)
     * @param listBValues Values for list B (before intersection)
     * @param commonValues Values for common part (intersection)
     * @return Array containing [headA, headB, intersectionNode]
     */
    public static ListNode[] createIntersectingLists(int[] listAValues, int[] listBValues, int[] commonValues) {
        if (commonValues == null || commonValues.length == 0) {
            // Create non-intersecting lists
            ListNode headA = arrayToList(listAValues);
            ListNode headB = arrayToList(listBValues);
            return new ListNode[]{headA, headB, null};
        }
        
        // Create common part
        ListNode commonHead = arrayToList(commonValues);
        
        // Create list A with common part
        ListNode headA = arrayToList(listAValues);
        if (headA == null) {
            headA = commonHead;
        } else {
            ListNode tailA = getTail(headA);
            tailA.next = commonHead;
        }
        
        // Create list B with common part
        ListNode headB = arrayToList(listBValues);
        if (headB == null) {
            headB = commonHead;
        } else {
            ListNode tailB = getTail(headB);
            tailB.next = commonHead;
        }
        
        return new ListNode[]{headA, headB, commonHead};
    }
    
    /**
     * Helper: Convert array to linked list
     */
    private static ListNode arrayToList(int[] arr) {
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
     * Helper: Get tail node of list
     */
    private static ListNode getTail(ListNode head) {
        if (head == null) {
            return null;
        }
        
        ListNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        return current;
    }
    
    /**
     * Helper: Print list (with intersection marking)
     */
    public static void printList(ListNode head, ListNode intersection) {
        ListNode current = head;
        System.out.print("List: ");
        
        while (current != null) {
            if (current == intersection) {
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
     * Helper: Visualize both lists with intersection
     */
    public static void visualizeIntersection(ListNode headA, ListNode headB, ListNode intersection) {
        System.out.println("List A:");
        printList(headA, intersection);
        
        System.out.println("\nList B:");
        printList(headB, intersection);
        
        if (intersection != null) {
            System.out.println("\nIntersection at node with value: " + intersection.val);
        } else {
            System.out.println("\nNo intersection");
        }
    }
    
    /**
     * Performance comparison of different methods
     */
    public static void compareMethods(ListNode headA, ListNode headB, String description) {
        System.out.println("\n" + description + ":");
        
        // Method 1: Length alignment
        long start = System.nanoTime();
        ListNode result1 = getIntersectionNode(headA, headB);
        long end = System.nanoTime();
        System.out.println("Length Alignment: " + 
                          (result1 != null ? result1.val : "null") + 
                          " (" + (end - start) + " ns)");
        
        // Method 2: Two-pointer
        start = System.nanoTime();
        ListNode result2 = getIntersectionNodeTwoPointer(headA, headB);
        end = System.nanoTime();
        System.out.println("Two-pointer: " + 
                          (result2 != null ? result2.val : "null") + 
                          " (" + (end - start) + " ns)");
        
        // Method 3: HashSet
        start = System.nanoTime();
        ListNode result3 = getIntersectionNodeHashSet(headA, headB);
        end = System.nanoTime();
        System.out.println("HashSet: " + 
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
        System.out.println("=== Intersection of Two Linked Lists ===\n");
        
        // Test Case 1: Simple intersection
        System.out.println("1. Simple Intersection:");
        ListNode[] lists1 = createIntersectingLists(
            new int[]{1, 2, 3},       // List A prefix
            new int[]{4, 5},          // List B prefix  
            new int[]{6, 7, 8}        // Common part
        );
        
        visualizeIntersection(lists1[0], lists1[1], lists1[2]);
        ListNode result1 = getIntersectionNode(lists1[0], lists1[1]);
        System.out.println("Intersection found at: " + 
                          (result1 != null ? result1.val : "null"));
        System.out.println("Intersection length: " + getIntersectionLength(lists1[0], lists1[1]));
        
        // Test Case 2: No intersection
        System.out.println("\n2. No Intersection:");
        ListNode listA2 = arrayToList(new int[]{1, 2, 3});
        ListNode listB2 = arrayToList(new int[]{4, 5, 6});
        visualizeIntersection(listA2, listB2, null);
        System.out.println("Has intersection: " + hasIntersection(listA2, listB2));
        
        // Test Case 3: Same list (complete overlap)
        System.out.println("\n3. Complete Overlap (Same List):");
        ListNode commonList = arrayToList(new int[]{10, 20, 30, 40});
        ListNode result3 = getIntersectionNode(commonList, commonList);
        System.out.println("Intersection with itself: " + 
                          (result3 != null ? result3.val : "null"));
        
        // Test Case 4: Intersection at head
        System.out.println("\n4. Intersection at Head:");
        ListNode commonHead = arrayToList(new int[]{100, 200, 300});
        ListNode listA4 = commonHead;  // Direct reference
        ListNode listB4 = commonHead;  // Same reference
        visualizeIntersection(listA4, listB4, commonHead);
        System.out.println("Intersection at head: " + 
                          (getIntersectionNode(listA4, listB4) == commonHead));
        
        // Test Case 5: One list is subset of another
        System.out.println("\n5. One List is Subset of Another:");
        ListNode longList = arrayToList(new int[]{1, 2, 3, 4, 5});
        ListNode shortList = longList.next.next;  // Start at node 3
        visualizeIntersection(longList, shortList, shortList);
        System.out.println("Intersection value: " + getIntersectionValue(longList, shortList));
        
        // Test Case 6: Different prefix lengths
        System.out.println("\n6. Different Prefix Lengths:");
        ListNode[] lists6 = createIntersectingLists(
            new int[]{1, 2, 3, 4, 5},  // Longer prefix
            new int[]{6, 7},           // Shorter prefix
            new int[]{8, 9, 10}        // Common
        );
        visualizeIntersection(lists6[0], lists6[1], lists6[2]);
        
        // Test Case 7: Empty lists
        System.out.println("\n7. Edge Cases - Empty Lists:");
        System.out.println("Both null: " + getIntersectionNode(null, null));
        System.out.println("First null: " + getIntersectionNode(null, arrayToList(new int[]{1})));
        System.out.println("Second null: " + getIntersectionNode(arrayToList(new int[]{1}), null));
        
        // Test Case 8: Single node intersection
        System.out.println("\n8. Single Node Intersection:");
        ListNode singleNode = new ListNode(99);
        ListNode listA8 = new ListNode(1);
        listA8.next = new ListNode(2);
        listA8.next.next = singleNode;
        
        ListNode listB8 = new ListNode(3);
        listB8.next = singleNode;
        
        visualizeIntersection(listA8, listB8, singleNode);
        System.out.println("All intersection nodes: " + 
                          getAllIntersectionNodes(listA8, listB8).size() + " nodes");
        
        // Test Case 9: Large lists performance
        System.out.println("\n9. Large Lists Performance:");
        int[] largePrefixA = new int[1000];
        int[] largePrefixB = new int[500];
        int[] largeCommon = new int[200];
        
        for (int i = 0; i < largePrefixA.length; i++) largePrefixA[i] = i;
        for (int i = 0; i < largePrefixB.length; i++) largePrefixB[i] = i + 1000;
        for (int i = 0; i < largeCommon.length; i++) largeCommon[i] = i + 2000;
        
        ListNode[] largeLists = createIntersectingLists(largePrefixA, largePrefixB, largeCommon);
        
        long startTime = System.currentTimeMillis();
        ListNode largeResult = getIntersectionNode(largeLists[0], largeLists[1]);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Found intersection in " + (endTime - startTime) + " ms");
        System.out.println("Intersection value: " + 
                          (largeResult != null ? largeResult.val : "null"));
        
        // Test Case 10: Method comparison
        System.out.println("\n10. Method Comparison:");
        ListNode[] testLists = createIntersectingLists(
            new int[]{1, 2, 3, 4, 5},
            new int[]{6, 7, 8},
            new int[]{9, 10, 11}
        );
        compareMethods(testLists[0], testLists[1], "Small intersecting lists");
        
        // Test Case 11: Stack approach demonstration
        System.out.println("\n11. Stack Approach Demonstration:");
        ListNode[] stackLists = createIntersectingLists(
            new int[]{1, 2},
            new int[]{3, 4, 5},
            new int[]{6, 7}
        );
        ListNode stackResult = getIntersectionNodeStack(stackLists[0], stackLists[1]);
        System.out.println("Stack method result: " + 
                          (stackResult != null ? stackResult.val : "null"));
        
        // Test Case 12: Multiple methods on same lists
        System.out.println("\n12. All Methods Verification:");
        ListNode testA = arrayToList(new int[]{1, 2, 3});
        ListNode testB = arrayToList(new int[]{4, 5});
        ListNode common = arrayToList(new int[]{6, 7, 8});
        
        // Connect lists
        getTail(testA).next = common;
        getTail(testB).next = common;
        
        System.out.println("Length alignment: " + 
                          getIntersectionNode(testA, testB).val);
        System.out.println("Two-pointer: " + 
                          getIntersectionNodeTwoPointer(testA, testB).val);
        System.out.println("HashSet: " + 
                          getIntersectionNodeHashSet(testA, testB).val);
        System.out.println("Stack: " + 
                          getIntersectionNodeStack(testA, testB).val);
        
        // Test Case 13: Intersection with cycles (should handle or detect)
        System.out.println("\n13. Warning: Intersection with Cycles:");
        System.out.println("Note: These methods assume no cycles in lists");
        System.out.println("For lists with cycles, need cycle detection first");
        
        // Test Case 14: Real-world analogy
        System.out.println("\n14. Real-World Analogy:");
        System.out.println("Think of two rivers merging:");
        System.out.println("- Each river is a linked list");
        System.out.println("- The confluence point is intersection");
        System.out.println("- Different lengths before merging");
        System.out.println("- Same flow after merging");
        
        // Test Case 15: Algorithm walkthrough
        System.out.println("\n15. Algorithm Walkthrough (Length Alignment):");
        System.out.println("List A: 1 → 2 → 3 → 4 → 5");
        System.out.println("List B:       6 → 4 → 5");
        System.out.println("\nStep 1: Calculate lengths");
        System.out.println("  lenA = 5, lenB = 3");
        System.out.println("Step 2: Align starting points");
        System.out.println("  diff = 5 - 3 = 2");
        System.out.println("  Move A pointer forward 2 steps: 1 → 2 → [3]");
        System.out.println("Step 3: Compare simultaneously");
        System.out.println("  A: 3 → 4 → 5");
        System.out.println("  B: 6 → 4 → 5");
        System.out.println("  Match at node 4 ✓");
    }
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. COMPARING VALUES INSTEAD OF REFERENCES:
     *    ❌ if (ptrA.val == ptrB.val) // Wrong! Different nodes can have same value
     *    ✅ if (ptrA == ptrB) // Correct! Compare node references
     *    
     * 2. NOT HANDLING DIFFERENT LENGTHS:
     *    Starting comparison without alignment will miss intersections
     *    
     * 3. MODIFYING ORIGINAL LISTS:
     *    Some approaches modify lists - document if this happens
     *    
     * 4. INFINITE LOOP WITH CYCLES:
     *    These algorithms assume no cycles
     *    Add cycle detection if unsure
     *    
     * 5. MEMORY LEAKS WITH TEST DATA:
     *    Creating intersecting lists can cause issues if not careful
     */
    
    /**
     * Two-pointer Approach Insight:
     * 
     * Why it works:
     * Let distance(headA → intersection) = a
     * Let distance(headB → intersection) = b  
     * Let distance(intersection → end) = c
     * 
     * Pointer A travels: a + c + b
     * Pointer B travels: b + c + a
     * Both travel same total distance!
     * 
     * If no intersection (c = 0, lists don't join):
     * Both travel a + b and become null together
     */
    
    /**
     * Complexity Analysis:
     * 
     * Length Alignment Method:
     * Time: O(m + n) - Two passes to get lengths, one to find intersection
     * Space: O(1) - Only pointers
     * 
     * Two-pointer Method:
     * Time: O(m + n) - Each pointer visits each list once
     * Space: O(1) - Two pointers
     * 
     * HashSet Method:
     * Time: O(m + n) - One traversal each
     * Space: O(m) or O(n) - Store one list in hash set
     * 
     * Stack Method:
     * Time: O(m + n) - Two traversals each
     * Space: O(m + n) - Two stacks
     */
    
    /**
     * Variations and Extensions:
     * 
     * 1. INTERSECTION IN DOUBLY LINKED LISTS:
     *    Same algorithms work
     *    
     * 2. INTERSECTION WITH CYCLES:
     *    Combine with cycle detection
     *    
     * 3. MULTIPLE LIST INTERSECTION:
     *    Find common node in k linked lists
     *    
     * 4. NEAREST INTERSECTION:
     *    Find node where lists are closest
     *    
     * 5. INTERSECTION IN TREES:
     *    Find common ancestor in binary trees
     */
    
    /**
     * Interview Tips:
     * 
     * 1. START WITH BRUTE FORCE:
     *    Mention O(mn) approach first, then optimize
     *    
     * 2. EXPLAIN BOTH MAIN SOLUTIONS:
     *    Length alignment and two-pointer approaches
     *    
     * 3. DISCUSS TRADEOFFS:
     *    Time vs space, readability vs efficiency
     *    
     * 4. HANDLE EDGE CASES:
     *    Empty lists, same list, no intersection
     *    
     * 5. WRITE CLEAN CODE:
     *    Use helper methods, meaningful variable names
     */
}