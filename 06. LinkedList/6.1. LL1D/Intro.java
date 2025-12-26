/**
 * Introduction to Linked Lists - Basic Operations
 * 
 * This class provides fundamental operations for working with singly linked lists.
 * It serves as a foundation for understanding linked list manipulation.
 * 
 * Key Concepts Covered:
 * 1. Building a linked list from an array
 * 2. Printing a linked list in readable format
 * 3. Understanding linked list traversal
 * 
 * Why Linked Lists?
 * - Dynamic size (unlike arrays)
 * - Efficient insertions/deletions (O(1) at head)
 * - No need for contiguous memory
 * - Foundation for other data structures (stacks, queues, graphs)
 */

public class Intro {
    
    /**
     * Builds a singly linked list from an integer array
     * 
     * Algorithm:
     * 1. Handle edge case: null or empty array returns null
     * 2. Create head node from first array element
     * 3. Use current pointer to traverse while appending new nodes
     * 4. Return head of the created list
     * 
     * @param arr Integer array to convert to linked list
     * @return Head node of the created linked list
     * 
     * Time Complexity: O(n) where n is array length
     * Space Complexity: O(n) for the new linked list nodes
     * 
     * Example:
     * Input: [1, 2, 3]
     * Output: 1 -> 2 -> 3 -> null
     */
    public static ListNode buildFromArray(int[] arr) {
        // Edge case: null or empty array
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        // Create head from first element
        ListNode head = new ListNode(arr[0]);
        ListNode current = head; // Pointer to track current node
        
        // Append remaining elements
        for (int i = 1; i < arr.length; i++) {
            current.next = new ListNode(arr[i]);
            current = current.next; // Move to the new node
        }
        
        return head;
    }
    
    /**
     * Alternative: Build list using dummy node technique
     * 
     * Advantages:
     * - Simpler code (no special case for head)
     * - More elegant solution
     * 
     * @param arr Integer array to convert
     * @return Head node of created list
     */
    public static ListNode buildFromArrayDummy(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(-1); // Dummy starter node
        ListNode current = dummy;
        
        for (int value : arr) {
            current.next = new ListNode(value);
            current = current.next;
        }
        
        return dummy.next; // Skip the dummy node
    }
    
    /**
     * Prints a linked list in readable "->" format
     * 
     * Algorithm:
     * 1. Start from head node
     * 2. While current node is not null:
     *    - Print node value
     *    - If next exists, print " -> "
     *    - Move to next node
     * 
     * @param head Head node of the linked list to print
     * 
     * Time Complexity: O(n) where n is list length
     * Space Complexity: O(1) extra space
     * 
     * Example output: 1 -> 2 -> 3 -> null
     */
    public static void printList(ListNode head) {
        ListNode current = head;
        
        while (current != null) {
            System.out.print(current.val);
            
            // Print arrow if not last node
            if (current.next != null) {
                System.out.print(" -> ");
            }
            
            current = current.next;
        }
        
        System.out.println(); // End with newline
    }
    
    /**
     * Enhanced print with null indicator
     * 
     * @param head Head node of list
     */
    public static void printListWithNull(ListNode head) {
        ListNode current = head;
        
        while (current != null) {
            System.out.print(current.val + " -> ");
            current = current.next;
        }
        
        System.out.println("null");
    }
    
    /**
     * Recursive print (for educational purposes)
     * 
     * @param head Head node of list
     */
    public static void printListRecursive(ListNode head) {
        if (head == null) {
            System.out.println("null");
            return;
        }
        
        System.out.print(head.val);
        if (head.next != null) {
            System.out.print(" -> ");
        }
        
        printListRecursive(head.next);
    }
    
    /**
     * Calculates length of linked list
     * 
     * @param head Head node of list
     * @return Number of nodes in list
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
     * Main method with comprehensive examples
     */
    public static void main(String[] args) {
        System.out.println("=== Introduction to Linked Lists ===\n");
        
        // Example 1: Basic list creation and printing
        System.out.println("1. Basic Example:");
        int[] arr1 = {1, 2, 3, 4, 5};
        ListNode list1 = buildFromArray(arr1);
        System.out.print("List from array [1,2,3,4,5]: ");
        printList(list1);
        System.out.println("Length: " + getLength(list1));
        
        // Example 2: Empty array
        System.out.println("\n2. Edge Cases:");
        System.out.print("Empty array: ");
        printList(buildFromArray(new int[0]));
        
        System.out.print("Null array: ");
        printList(buildFromArray(null));
        
        // Example 3: Single element
        System.out.print("Single element [10]: ");
        printList(buildFromArray(new int[]{10}));
        
        // Example 4: Comparison of building methods
        System.out.println("\n3. Building Methods Comparison:");
        int[] arr2 = {10, 20, 30, 40};
        
        ListNode method1 = buildFromArray(arr2);
        System.out.print("Method 1 (direct): ");
        printList(method1);
        
        ListNode method2 = buildFromArrayDummy(arr2);
        System.out.print("Method 2 (dummy):  ");
        printList(method2);
        
        // Example 5: Different print styles
        System.out.println("\n4. Print Styles:");
        System.out.print("Standard print: ");
        printList(list1);
        
        System.out.print("With null: ");
        printListWithNull(list1);
        
        System.out.print("Recursive print: ");
        printListRecursive(list1);
        
        // Example 6: Memory visualization
        System.out.println("\n5. Memory Visualization:");
        ListNode visualList = new ListNode(100);
        visualList.next = new ListNode(200);
        visualList.next.next = new ListNode(300);
        
        System.out.println("Memory representation:");
        System.out.println("Stack        Heap");
        System.out.println("visualList → [100|•] → [200|•] → [300|null]");
        System.out.println("              val|next");
        
        // Example 7: Performance demonstration
        System.out.println("\n6. Performance Demonstration:");
        int[] largeArray = new int[10000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i;
        }
        
        long startTime = System.currentTimeMillis();
        ListNode largeList = buildFromArray(largeArray);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Built list of 10,000 nodes in " + (endTime - startTime) + " ms");
        System.out.println("Length of large list: " + getLength(largeList));
    }
    
    /**
     * Common Mistakes to Avoid:
     * 
     * 1. LOST HEAD REFERENCE:
     *    ListNode current = head;
     *    while (current != null) {
     *        // ... do something ...
     *        current = current.next;
     *    }
     *    // head still points to beginning!
     *    
     * 2. MODIFYING HEAD ACCIDENTALLY:
     *    head = head.next; // Original head lost!
     *    Solution: Use a temporary pointer
     *    
     * 3. NULL POINTER EXCEPTIONS:
     *    Always check if head is null before accessing head.next
     *    
     * 4. INFINITE LOOPS:
     *    Make sure list is properly terminated with null
     *    Check for cycles in advanced problems
     */
    
    /**
     * Linked List vs Array Comparison:
     * 
     *               Array          Linked List
     * Access        O(1)           O(n)
     * Insert Begin  O(n)           O(1)
     * Delete Begin  O(n)           O(1)
     * Insert End    O(1)*          O(n)
     * Delete End    O(1)           O(n)
     * Memory       Contiguous      Fragmented
     * Size         Fixed           Dynamic
     * 
     * * If array has space, otherwise O(n) for resize
     */
}