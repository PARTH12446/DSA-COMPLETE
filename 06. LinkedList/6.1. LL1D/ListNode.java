/**
 * ListNode - Basic Building Block of Singly Linked List
 * 
 * A ListNode represents a single node in a singly linked list.
 * Each node contains:
 * 1. Data (value) - stores the actual data
 * 2. Next pointer - reference to the next node in the list
 * 
 * This is a self-contained class that can be used to build linked lists
 * for various problems and applications.
 * 
 * Characteristics:
 * - Singly linked (only points to next, not previous)
 * - Generic structure (can be adapted for any data type)
 * - Simple constructor for initialization
 */

public class ListNode {
    // The data stored in this node
    int val;
    
    // Reference to the next node in the list
    ListNode next;
    
    /**
     * Constructor to create a new ListNode
     * 
     * @param val The value to store in this node
     * 
     * Example:
     * ListNode node = new ListNode(5);
     * Creates: [5] -> null
     */
    ListNode(int val) {
        this.val = val;     // Set the node's value
        this.next = null;   // Initially, node points to nothing
    }
    
    /**
     * Alternative constructor that allows setting next pointer
     * 
     * @param val The value to store
     * @param next Reference to next node
     * 
     * Example:
     * ListNode second = new ListNode(2, null);
     * ListNode first = new ListNode(1, second);
     * Creates: [1] -> [2] -> null
     */
    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
    
    /**
     * Helper method to create a linked list from an array
     * 
     * @param arr Array of integers to convert to linked list
     * @return Head node of the created linked list
     * 
     * Time Complexity: O(n) where n is array length
     * Space Complexity: O(n) for the new nodes
     */
    public static ListNode fromArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return null;
        }
        
        ListNode dummy = new ListNode(-1); // Dummy node to simplify
        ListNode current = dummy;
        
        for (int value : arr) {
            current.next = new ListNode(value);
            current = current.next;
        }
        
        return dummy.next; // Return the real head
    }
    
    /**
     * Helper method to convert linked list to array
     * 
     * @param head Head of the linked list
     * @return Array containing all node values
     * 
     * Time Complexity: O(n) where n is list length
     * Space Complexity: O(n) for the result array
     */
    public static int[] toArray(ListNode head) {
        // First, count the nodes
        int count = 0;
        ListNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        
        // Create array and fill it
        int[] result = new int[count];
        current = head;
        for (int i = 0; i < count; i++) {
            result[i] = current.val;
            current = current.next;
        }
        
        return result;
    }
    
    /**
     * Helper method to print a linked list in readable format
     * 
     * @param head Head of the linked list
     * 
     * Example output: 1 -> 2 -> 3 -> null
     */
    public static void printList(ListNode head) {
        ListNode current = head;
        while (current != null) {
            System.out.print(current.val);
            if (current.next != null) {
                System.out.print(" -> ");
            }
            current = current.next;
        }
        System.out.println(" -> null");
    }
    
    /**
     * Example usage and testing
     */
    public static void main(String[] args) {
        System.out.println("=== Testing ListNode Class ===");
        
        // Test 1: Basic node creation
        System.out.println("\n1. Basic Node Creation:");
        ListNode node1 = new ListNode(10);
        System.out.println("Single node value: " + node1.val);
        System.out.println("Single node next: " + node1.next);
        
        // Test 2: Creating a small linked list manually
        System.out.println("\n2. Manual Linked List Creation:");
        ListNode third = new ListNode(30);
        ListNode second = new ListNode(20, third);
        ListNode first = new ListNode(10, second);
        
        System.out.print("Manual list: ");
        printList(first);
        
        // Test 3: Creating list from array
        System.out.println("\n3. Creating List from Array:");
        int[] arr = {1, 2, 3, 4, 5};
        ListNode listFromArray = fromArray(arr);
        System.out.print("From array: ");
        printList(listFromArray);
        
        // Test 4: Converting list back to array
        System.out.println("\n4. Converting List to Array:");
        int[] convertedArray = toArray(listFromArray);
        System.out.print("Converted array: [");
        for (int i = 0; i < convertedArray.length; i++) {
            System.out.print(convertedArray[i]);
            if (i < convertedArray.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
        
        // Test 5: Edge cases
        System.out.println("\n5. Edge Cases:");
        System.out.print("Null list: ");
        printList(null);
        
        System.out.print("Empty array to list: ");
        printList(fromArray(new int[0]));
        
        // Test 6: Visual representation
        System.out.println("\n6. Visual Representation:");
        ListNode visualList = new ListNode(1);
        visualList.next = new ListNode(2);
        visualList.next.next = new ListNode(3);
        System.out.println("Visual representation:");
        System.out.println("head");
        System.out.println(" ↓");
        System.out.println("[1] -> [2] -> [3] -> null");
    }
    
    /**
     * Memory Layout Understanding:
     * 
     * When we create: ListNode node = new ListNode(5);
     * 
     * Memory representation:
     * Stack:             Heap:
     * node ────────→     [val: 5]
     *                    [next: null]
     * 
     * When we link nodes:
     * ListNode node2 = new ListNode(10);
     * node.next = node2;
     * 
     * Memory representation:
     * Stack:             Heap:
     * node ────────→     [val: 5]
     *                    [next: ──┐]
     *                             ↓
     * node2 ───────→     [val: 10]
     *                    [next: null]
     */
}