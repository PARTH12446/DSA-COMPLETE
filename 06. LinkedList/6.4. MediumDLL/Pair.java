/**
 * Class for finding a pair with given sum in a sorted doubly linked list
 * 
 * Problem: Given a SORTED doubly linked list, determine if there exists 
 * a pair of nodes whose values sum to a given target.
 * 
 * Example:
 * Input:  null ← 1 ↔ 2 ↔ 3 ↔ 4 ↔ 5 → null, target = 7
 * Output: true (because 2 + 5 = 7 or 3 + 4 = 7)
 * 
 * Approach: Two-pointer technique
 * 1. Initialize left pointer at head (smallest value)
 * 2. Initialize right pointer at tail (largest value)
 * 3. While left < right:
 *    - Calculate sum = left.val + right.val
 *    - If sum == target: return true
 *    - If sum < target: move left forward (increase sum)
 *    - If sum > target: move right backward (decrease sum)
 * 
 * Time Complexity: O(n) where n is number of nodes
 * Space Complexity: O(1) - uses only two pointers
 * 
 * Note: This algorithm assumes the list is sorted in non-decreasing order
 */
public class Pair {

    /**
     * Checks if there exists a pair of nodes with given sum in sorted DLL
     * 
     * @param head The head node of the SORTED doubly linked list
     * @param target The target sum to find
     * @return true if a pair exists, false otherwise
     * 
     * Algorithm Steps:
     * 1. Handle edge case: empty list
     * 2. Initialize two pointers:
     *    - left: starts at head (smallest element)
     *    - right: starts at tail (largest element)
     * 3. Move pointers toward each other:
     *    - If sum == target: pair found
     *    - If sum < target: need larger sum → move left forward
     *    - If sum > target: need smaller sum → move right backward
     * 4. Continue until pointers meet or cross
     */
    public static boolean pairSum(DNode head, int target) {
        // Edge case: empty list has no pairs
        if (head == null) {
            return false;
        }
        
        // Step 1: Initialize pointers
        DNode left = head;          // Start from smallest element (head)
        DNode right = head;         // Will move to largest element (tail)
        
        // Move right pointer to the last node (tail)
        while (right.next != null) {
            right = right.next;
        }
        
        System.out.println("Initial: left at " + left.val + ", right at " + right.val);
        System.out.println("Target sum: " + target);
        
        // Step 2: Two-pointer traversal
        // Continue while:
        // - Both pointers are not null
        // - Pointers haven't met or crossed
        // - right.next != left ensures we don't cross in DLL (more precise than left < right)
        while (left != null && right != null && left != right && right.next != left) {
            int sum = left.val + right.val;
            System.out.println("  Checking pair: " + left.val + " + " + right.val + " = " + sum);
            
            // Case 1: Found exact sum
            if (sum == target) {
                System.out.println("  ✓ Found pair: " + left.val + " + " + right.val + " = " + target);
                return true;
            }
            
            // Case 2: Sum is less than target
            // Need larger sum → move left pointer forward (larger values)
            if (sum < target) {
                left = left.next;
                System.out.println("  Sum too small, moving left forward → " + 
                                 (left != null ? left.val : "null"));
            } 
            // Case 3: Sum is greater than target
            // Need smaller sum → move right pointer backward (smaller values)
            else {
                right = right.prev;
                System.out.println("  Sum too large, moving right backward → " + 
                                 (right != null ? right.val : "null"));
            }
        }
        
        System.out.println("✗ No pair found with sum " + target);
        return false;
    }
    
    /**
     * Alternative implementation with more explicit termination conditions
     * Uses while (left.val < right.val) for sorted lists
     */
    public static boolean pairSumAlternative(DNode head, int target) {
        if (head == null) return false;
        
        DNode left = head;
        DNode right = head;
        
        // Find tail
        while (right.next != null) {
            right = right.next;
        }
        
        // Use value comparison for termination (only works for strictly sorted list)
        while (left != null && right != null && left.val < right.val) {
            int sum = left.val + right.val;
            
            if (sum == target) {
                return true;
            } else if (sum < target) {
                left = left.next;  // Need larger sum
            } else {
                right = right.prev; // Need smaller sum
            }
        }
        
        return false;
    }
    
    /**
     * Finds ALL pairs with given sum and returns count
     * 
     * @param head Sorted doubly linked list
     * @param target Target sum
     * @return Number of distinct pairs with given sum
     */
    public static int countPairsWithSum(DNode head, int target) {
        if (head == null) return 0;
        
        DNode left = head;
        DNode right = head;
        int count = 0;
        
        // Find tail
        while (right.next != null) {
            right = right.next;
        }
        
        System.out.println("\nFinding ALL pairs with sum " + target + ":");
        
        while (left != null && right != null && left != right && right.next != left) {
            int sum = left.val + right.val;
            
            if (sum == target) {
                System.out.println("  Found pair #" + (++count) + ": " + left.val + " + " + right.val);
                left = left.next;
                right = right.prev;
            } else if (sum < target) {
                left = left.next;
            } else {
                right = right.prev;
            }
        }
        
        return count;
    }
    
    /**
     * Helper method to create a sorted doubly linked list from array
     */
    public static DNode createSortedList(int[] values) {
        if (values == null || values.length == 0) return null;
        
        // Sort array first (assuming input might not be sorted)
        java.util.Arrays.sort(values);
        
        DNode head = new DNode(values[0]);
        DNode current = head;
        DNode prev = null;
        
        for (int i = 1; i < values.length; i++) {
            DNode newNode = new DNode(values[i]);
            current.next = newNode;
            newNode.prev = current;
            current = newNode;
        }
        
        return head;
    }
    
    /**
     * Helper method to print doubly linked list
     */
    public static void printList(DNode head) {
        DNode current = head;
        System.out.print("List: null ← ");
        
        while (current != null) {
            System.out.print("[" + current.val + "]");
            if (current.next != null) {
                System.out.print(" ↔ ");
            }
            current = current.next;
        }
        System.out.println(" → null");
    }
    
    /**
     * Visualizes the two-pointer algorithm step by step
     */
    public static void visualizePairSum(DNode head, int target) {
        System.out.println("\n=== Visualizing Two-Pointer Pair Sum Search ===");
        printList(head);
        System.out.println("Target sum: " + target);
        System.out.println();
        
        if (head == null) {
            System.out.println("Empty list, no pairs exist");
            return;
        }
        
        DNode left = head;
        DNode right = head;
        
        // Move right to tail
        while (right.next != null) {
            right = right.next;
        }
        
        System.out.println("Step 1: Initialize pointers");
        System.out.println("  left → smallest element: [" + left.val + "]");
        System.out.println("  right → largest element: [" + right.val + "]");
        System.out.println();
        
        int step = 1;
        
        while (left != null && right != null && left != right && right.next != left) {
            System.out.println("Step " + step + ":");
            System.out.println("  left = [" + left.val + "], right = [" + right.val + "]");
            
            int sum = left.val + right.val;
            System.out.println("  Sum = " + left.val + " + " + right.val + " = " + sum);
            
            if (sum == target) {
                System.out.println("  ✓ MATCH FOUND! " + left.val + " + " + right.val + " = " + target);
                System.out.println("  Pair exists in the list");
                return;
            } else if (sum < target) {
                System.out.println("  Sum < target (" + target + ")");
                System.out.println("  Need larger sum → move left forward");
                left = left.next;
                System.out.println("  New left = [" + (left != null ? left.val : "null") + "]");
            } else {
                System.out.println("  Sum > target (" + target + ")");
                System.out.println("  Need smaller sum → move right backward");
                right = right.prev;
                System.out.println("  New right = [" + (right != null ? right.val : "null") + "]");
            }
            
            step++;
            System.out.println();
        }
        
        System.out.println("✗ NO PAIR FOUND with sum " + target);
        System.out.println("Termination condition reached:");
        if (left == null) System.out.println("  left became null");
        if (right == null) System.out.println("  right became null");
        if (left == right) System.out.println("  left and right met at same node");
        if (right != null && left != null && right.next == left) 
            System.out.println("  pointers crossed (right.next == left)");
    }
    
    /**
     * Test with unsorted list (will fail to find correct pairs)
     */
    public static void testUnsortedList() {
        System.out.println("\n=== Testing with Unsorted List ===");
        System.out.println("WARNING: Algorithm requires sorted list!");
        
        // Create unsorted list
        DNode head = new DNode(5);
        head.next = new DNode(1);
        head.next.prev = head;
        head.next.next = new DNode(3);
        head.next.next.prev = head.next;
        head.next.next.next = new DNode(2);
        head.next.next.next.prev = head.next.next;
        
        printList(head);
        System.out.println("Searching for sum = 4");
        
        // This might give incorrect result because list is not sorted
        boolean result = pairSum(head, 4);
        System.out.println("Result: " + result);
        System.out.println("Note: May give false negative even though 1+3=4 exists");
    }
    
    public static void main(String[] args) {
        System.out.println("=== Pair Sum in Sorted Doubly Linked List ===\n");
        
        // Test Case 1: Basic case with pair
        System.out.println("Test Case 1: Find sum 9 in sorted list");
        int[] arr1 = {1, 2, 3, 4, 5, 6, 7, 8};
        DNode list1 = createSortedList(arr1);
        printList(list1);
        boolean result1 = pairSum(list1, 9);
        System.out.println("Pair with sum 9 exists: " + result1);
        visualizePairSum(createSortedList(arr1), 9);
        System.out.println();
        
        // Test Case 2: Pair doesn't exist
        System.out.println("Test Case 2: Find sum 20 (no pair)");
        DNode list2 = createSortedList(new int[]{1, 2, 3, 4, 5});
        printList(list2);
        boolean result2 = pairSum(list2, 20);
        System.out.println("Pair with sum 20 exists: " + result2);
        visualizePairSum(createSortedList(new int[]{1, 2, 3, 4, 5}), 20);
        System.out.println();
        
        // Test Case 3: Multiple possible pairs
        System.out.println("Test Case 3: Find all pairs with sum 7");
        int[] arr3 = {1, 2, 3, 4, 5, 6};
        DNode list3 = createSortedList(arr3);
        printList(list3);
        int pairCount = countPairsWithSum(list3, 7);
        System.out.println("Total pairs with sum 7: " + pairCount);
        System.out.println();
        
        // Test Case 4: Single element list
        System.out.println("Test Case 4: Single element list [5], target=5");
        DNode list4 = createSortedList(new int[]{5});
        printList(list4);
        boolean result4 = pairSum(list4, 5);
        System.out.println("Pair exists (needs two elements): " + result4);
        System.out.println();
        
        // Test Case 5: Negative numbers
        System.out.println("Test Case 5: List with negative numbers, target=0");
        int[] arr5 = {-5, -3, -1, 0, 1, 3, 5};
        DNode list5 = createSortedList(arr5);
        printList(list5);
        boolean result5 = pairSum(list5, 0);
        System.out.println("Pair with sum 0 exists: " + result5);
        visualizePairSum(createSortedList(arr5), 0);
        System.out.println();
        
        // Test Case 6: Duplicate values
        System.out.println("Test Case 6: List with duplicates, target=6");
        int[] arr6 = {1, 2, 2, 3, 4, 4, 5};
        DNode list6 = createSortedList(arr6);
        printList(list6);
        boolean result6 = pairSum(list6, 6);
        System.out.println("Pair with sum 6 exists: " + result6);
        System.out.println();
        
        // Test Case 7: Large list
        System.out.println("Test Case 7: Large list, find sum 199");
        int[] arr7 = new int[100];
        for (int i = 0; i < 100; i++) {
            arr7[i] = i + 1;  // 1 to 100
        }
        DNode list7 = createSortedList(arr7);
        System.out.print("List: 1 ↔ 2 ↔ ... ↔ 100 (100 elements)");
        System.out.println("\nSearching for sum 199 (100 + 99)");
        boolean result7 = pairSum(list7, 199);
        System.out.println("Pair with sum 199 exists: " + result7);
        System.out.println();
        
        // Test Case 8: Edge case - empty list
        System.out.println("Test Case 8: Empty list");
        DNode list8 = null;
        boolean result8 = pairSum(list8, 10);
        System.out.println("Pair exists in empty list: " + result8);
        System.out.println();
        
        // Test Case 9: Alternative implementation
        System.out.println("Test Case 9: Alternative implementation");
        DNode list9 = createSortedList(new int[]{10, 20, 30, 40, 50});
        printList(list9);
        boolean result9 = pairSumAlternative(list9, 60);
        System.out.println("Pair with sum 60 exists (alternative): " + result9);
        System.out.println();
        
        // Demonstrate unsorted list issue
        testUnsortedList();
        
        // Complexity Analysis
        System.out.println("\n=== Algorithm Analysis ===");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Finding tail: O(n) in worst case");
        System.out.println("  - Two-pointer traversal: O(n) in worst case");
        System.out.println("  - Total: O(2n) = O(n)");
        System.out.println("\nSpace Complexity: O(1)");
        System.out.println("  - Only uses two pointers (left, right)");
        System.out.println("\nKey Requirements:");
        System.out.println("  1. List must be SORTED");
        System.out.println("  2. List must be DOUBLY linked (for prev pointer)");
        System.out.println("\nAlternative for singly linked list:");
        System.out.println("  Use hash set: O(n) time, O(n) space");
    }
    
    /**
     * Definition for doubly linked list node
     */
    static class DNode {
        int val;
        DNode prev;
        DNode next;
        
        DNode(int val) {
            this.val = val;
            this.prev = null;
            this.next = null;
        }
        
        DNode(int val, DNode prev, DNode next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }
}