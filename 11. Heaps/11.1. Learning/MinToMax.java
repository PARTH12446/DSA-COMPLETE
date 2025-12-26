import java.util.Scanner;

/**
 * Convert a min-heap (stored in array form) into a max-heap in-place.
 *
 * Problem Analysis:
 * - Input: A min-heap array where parent <= children for all nodes
 * - Output: A max-heap array where parent >= children for all nodes
 * - Must be done in O(n) time without using extra space
 *
 * Key Insight:
 * A min-heap doesn't provide any useful ordering for building a max-heap.
 * We need to completely rebuild the heap from scratch.
 * The naive approach would be to build max-heap from unsorted array using Floyd's algorithm.
 *
 * Approach:
 * - Treat the input as an ordinary unsorted array (ignore its min-heap structure)
 * - Apply standard max-heapify algorithm starting from last parent node
 * - Time Complexity: O(n) using Floyd's heap construction algorithm
 */
public class MinToMax {

    /**
     * Standard max-heapify procedure (heapify-down)
     * Assumes left and right subtrees are already max-heaps
     * Fixes the node at index idx by moving it down to correct position
     *
     * @param arr The heap array
     * @param n   Size of heap (number of elements to consider)
     * @param idx Index of node to heapify
     */
    private void maxHeapify(int[] arr, int n, int idx) {
        int largest = idx;        // Assume current node is largest
        int left = 2 * idx + 1;   // Left child index
        int right = 2 * idx + 2;  // Right child index
        
        // Compare with left child if it exists
        if (left < n && arr[left] > arr[largest]) {
            largest = left;
        }
        
        // Compare with right child if it exists
        if (right < n && arr[right] > arr[largest]) {
            largest = right;
        }
        
        // If largest is not the current node, swap and recursively heapify
        if (largest != idx) {
            // Swap arr[idx] and arr[largest]
            int temp = arr[idx];
            arr[idx] = arr[largest];
            arr[largest] = temp;
            
            // Recursively heapify the affected subtree
            maxHeapify(arr, n, largest);
        }
    }

    /**
     * Convert min-heap to max-heap in-place
     * 
     * Steps:
     * 1. Start from last non-leaf node (parent of last element)
     * 2. Apply maxHeapify on each node moving up to root
     * 3. This builds max-heap from bottom-up
     *
     * @param arr The array representing min-heap
     * @param n   Number of elements in array
     */
    public void convertMinToMaxHeap(int[] arr, int n) {
        // Start from last non-leaf node and move up to root
        // Last non-leaf node index = floor(n/2) - 1
        // Using (n-2)/2 works because integer division truncates
        for (int idx = (n - 2) / 2; idx >= 0; idx--) {
            maxHeapify(arr, n, idx);
        }
    }

    /**
     * Alternative implementation with explicit heap building
     * More educational version showing the complete process
     */
    public void convertMinToMaxHeapDetailed(int[] arr, int n) {
        System.out.println("Starting conversion of min-heap to max-heap");
        System.out.println("Original array (min-heap): " + arrayToString(arr));
        
        // Step 1: Build max-heap from scratch
        // Start from last parent node and work upwards
        int lastParentIdx = (n - 1) / 2;  // Same as floor(n/2) - 1
        
        for (int i = lastParentIdx; i >= 0; i--) {
            System.out.println("Heapifying node at index " + i + " (value: " + arr[i] + ")");
            maxHeapify(arr, n, i);
            System.out.println("After heapify: " + arrayToString(arr));
        }
        
        System.out.println("Final max-heap: " + arrayToString(arr));
    }

    /**
     * Check if array satisfies max-heap property
     */
    public boolean isMaxHeap(int[] arr, int n) {
        for (int i = 0; i <= (n - 2) / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            
            if (left < n && arr[i] < arr[left]) {
                return false;
            }
            if (right < n && arr[i] < arr[right]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if array satisfies min-heap property
     */
    public boolean isMinHeap(int[] arr, int n) {
        for (int i = 0; i <= (n - 2) / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            
            if (left < n && arr[i] > arr[left]) {
                return false;
            }
            if (right < n && arr[i] > arr[right]) {
                return false;
            }
        }
        return true;
    }

    private String arrayToString(int[] arr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < arr.length; i++) {
            sb.append(arr[i]);
            if (i < arr.length - 1) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int N = sc.nextInt();
            int[] arr = new int[N];
            
            for (int i = 0; i < N; i++) {
                arr[i] = sc.nextInt();
            }
            
            MinToMax solver = new MinToMax();
            
            // Verify input is indeed a min-heap
            if (!solver.isMinHeap(arr, N)) {
                System.out.println("Warning: Input is not a valid min-heap!");
            }
            
            // Convert to max-heap
            solver.convertMinToMaxHeap(arr, N);
            
            // Verify output is a max-heap
            if (!solver.isMaxHeap(arr, N)) {
                System.out.println("Error: Output is not a valid max-heap!");
            }
            
            // Print result
            for (int val : arr) {
                System.out.print(val + " ");
            }
            System.out.println();
            
            // Uncomment for detailed output
            // System.out.println("Is max-heap? " + solver.isMaxHeap(arr, N));
        }
        sc.close();
    }
}