import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Merge k sorted linked lists using a min-heap.
 * 
 * Problem Statement (LeetCode 23):
 * Given an array of k linked-lists, each linked-list is sorted in ascending order.
 * Merge all the linked-lists into one sorted linked-list and return it.
 *
 * Example:
 * Input: lists = [[1,4,5],[1,3,4],[2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 *
 * Approach: Min-Heap (Priority Queue)
 * 1. Insert the first node of each non-empty list into a min-heap
 * 2. Repeatedly extract the smallest node from the heap
 * 3. Append this node to the result list
 * 4. If the extracted node has a next node, insert it into the heap
 * 5. Continue until the heap is empty
 *
 * Time Complexity: O(N log k) where N is total nodes, k is number of lists
 * Space Complexity: O(k) for the heap
 */
public class MergeKLinked {

    // Basic singly-linked list node
    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val) { this.val = val; }
    }

    /**
     * Merges k sorted linked lists into one sorted list using min-heap.
     * 
     * @param lists Array of list heads (may contain null lists)
     * @return Head of merged sorted list
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // Edge cases: null or empty input
        if (lists == null || lists.length == 0) {
            return null;
        }

        // Create a min-heap (priority queue) sorted by node values
        // Lambda comparator: compare nodes by their val field
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        
        // Add the first node of each non-empty list to the heap
        for (ListNode head : lists) {
            if (head != null) {
                pq.offer(head);
            }
        }

        // Dummy node to simplify result construction
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;  // Pointer to last node in result list

        // Process nodes until heap is empty
        while (!pq.isEmpty()) {
            // Get the smallest node from heap
            ListNode node = pq.poll();
            
            // Append this node to result list
            tail.next = node;
            tail = tail.next;
            
            // If this node has a next node, add it to heap
            if (node.next != null) {
                pq.offer(node.next);
            }
        }

        return dummy.next;
    }

    /**
     * Alternative approach: Divide and Conquer (Merge Sort style)
     * 1. Pair up lists and merge them
     * 2. Repeat until one list remains
     * Time: O(N log k), Space: O(1) excluding recursion stack
     */
    public ListNode mergeKListsDivideConquer(ListNode[] lists) {
        if (lists == null || lists.length == 0) return null;
        return mergeLists(lists, 0, lists.length - 1);
    }
    
    private ListNode mergeLists(ListNode[] lists, int left, int right) {
        if (left == right) return lists[left];
        if (left > right) return null;
        
        int mid = left + (right - left) / 2;
        ListNode leftMerged = mergeLists(lists, left, mid);
        ListNode rightMerged = mergeLists(lists, mid + 1, right);
        
        return mergeTwoLists(leftMerged, rightMerged);
    }
    
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode current = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                current.next = l1;
                l1 = l1.next;
            } else {
                current.next = l2;
                l2 = l2.next;
            }
            current = current.next;
        }
        
        current.next = (l1 != null) ? l1 : l2;
        return dummy.next;
    }

    // Utility to read a linked list from input
    private static ListNode readList(Scanner sc, int n) {
        if (n == 0) return null;
        ListNode head = new ListNode(sc.nextInt());
        ListNode curr = head;
        for (int i = 1; i < n; i++) {
            curr.next = new ListNode(sc.nextInt());
            curr = curr.next;
        }
        return head;
    }

    // Utility to print a linked list
    private static void printList(ListNode head) {
        ListNode curr = head;
        while (curr != null) {
            System.out.print(curr.val + " ");
            curr = curr.next;
        }
        System.out.println();
    }

    /**
     * Visualization helper to show step-by-step merging process
     */
    public void visualizeMerge(ListNode[] lists) {
        System.out.println("\n=== Visualizing Merge Process ===");
        
        // Print input lists
        for (int i = 0; i < lists.length; i++) {
            System.out.print("List " + i + ": ");
            printList(lists[i]);
        }
        
        PriorityQueue<ListNode> pq = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode head : lists) {
            if (head != null) pq.offer(head);
        }
        
        System.out.println("\nMin-Heap contents after initial insertion:");
        for (ListNode node : pq) {
            System.out.print(node.val + " ");
        }
        System.out.println();
        
        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;
        int step = 1;
        
        while (!pq.isEmpty()) {
            System.out.println("\nStep " + step++ + ":");
            ListNode node = pq.poll();
            System.out.println("  Extracted node: " + node.val);
            
            tail.next = node;
            tail = tail.next;
            
            if (node.next != null) {
                pq.offer(node.next);
                System.out.println("  Added next node " + node.next.val + " to heap");
            }
            
            System.out.print("  Current heap: ");
            for (ListNode n : pq) {
                System.out.print(n.val + " ");
            }
            System.out.println();
            
            System.out.print("  Result so far: ");
            printList(dummy.next);
        }
        
        System.out.println("\nFinal merged list: ");
        printList(dummy.next);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int k = sc.nextInt();  // Number of lists
            ListNode[] lists = new ListNode[k];
            
            for (int i = 0; i < k; i++) {
                int n = sc.nextInt();  // Size of current list
                lists[i] = readList(sc, n);
            }
            
            MergeKLinked solver = new MergeKLinked();
            
            // Method 1: Min-Heap approach
            ListNode mergedHead = solver.mergeKLists(lists);
            System.out.print("Min-Heap result: ");
            printList(mergedHead);
            
            // Uncomment for visualization
            // solver.visualizeMerge(lists);
            
            // Method 2: Divide and Conquer approach
            // Need to recreate lists since they're modified by first method
            // ListNode mergedHead2 = solver.mergeKListsDivideConquer(lists);
            // System.out.print("Divide & Conquer result: ");
            // printList(mergedHead2);
        }
        
        sc.close();
    }
}