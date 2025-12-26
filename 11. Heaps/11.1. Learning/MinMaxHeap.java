import java.util.Arrays;

/**
 * Demonstration of building max-heap and min-heap from an array
 * and performing basic heap operations similar to Python's heapq.
 * 
 * Heapify Algorithm (Floyd's Algorithm):
 * - Starts from the last parent node and works backwards
 * - Time complexity: O(n) to build heap from unsorted array
 * - More efficient than repeated insertion (O(n log n))
 * 
 * Key Insight: Leaves are already valid heaps, so start from last parent
 */
public class MinMaxHeap {

    /**
     * Build a max heap in-place from an unsorted array.
     * Max-Heap Property: Parent >= children
     * 
     * @param a Array to heapify into max-heap
     */
    public static void buildMaxHeap(int[] a) {
        int n = a.length;
        
        // Start from last parent node and move up to root
        // Last parent index = floor(n/2) - 1
        for (int i = n / 2 - 1; i >= 0; i--) {
            shiftDownMax(a, n, i);
        }
    }

    /**
     * Restore max-heap property by shifting element down (heapify-down)
     * 
     * @param a The heap array
     * @param n Current size of heap (elements to consider)
     * @param i Index of element to shift down
     */
    private static void shiftDownMax(int[] a, int n, int i) {
        int largest = i;        // Assume current node is largest
        int left = 2 * i + 1;   // Left child index
        int right = 2 * i + 2;  // Right child index
        
        // If left child exists and is larger than current largest
        if (left < n && a[left] > a[largest]) {
            largest = left;
        }
        
        // If right child exists and is larger than current largest
        if (right < n && a[right] > a[largest]) {
            largest = right;
        }
        
        // If largest is not the current node, swap and continue
        if (largest != i) {
            swap(a, i, largest);
            shiftDownMax(a, n, largest);  // Recursively heapify affected subtree
        }
    }

    /**
     * Build a min heap in-place from an unsorted array.
     * Min-Heap Property: Parent <= children
     * 
     * @param a Array to heapify into min-heap
     */
    public static void buildMinHeap(int[] a) {
        int n = a.length;
        
        // Start from last parent node
        for (int i = n / 2 - 1; i >= 0; i--) {
            shiftDownMin(a, n, i);
        }
    }

    /**
     * Restore min-heap property by shifting element down
     * 
     * @param a The heap array
     * @param n Current size of heap
     * @param i Index of element to shift down
     */
    private static void shiftDownMin(int[] a, int n, int i) {
        int smallest = i;       // Assume current node is smallest
        int left = 2 * i + 1;   // Left child index
        int right = 2 * i + 2;  // Right child index
        
        // If left child exists and is smaller than current smallest
        if (left < n && a[left] < a[smallest]) {
            smallest = left;
        }
        
        // If right child exists and is smaller than current smallest
        if (right < n && a[right] < a[smallest]) {
            smallest = right;
        }
        
        // If smallest is not the current node, swap and continue
        if (smallest != i) {
            swap(a, i, smallest);
            shiftDownMin(a, n, smallest);  // Recursively heapify affected subtree
        }
    }

    /**
     * Extract maximum element from max-heap (heap sort step)
     * 
     * @param a Max-heap array
     * @param n Current size of heap
     * @return Maximum element
     */
    public static int extractMax(int[] a, int n) {
        if (n <= 0) {
            throw new IllegalStateException("Heap is empty");
        }
        
        int max = a[0];         // Root is maximum
        a[0] = a[n - 1];        // Move last element to root
        shiftDownMax(a, n - 1, 0);  // Restore heap property
        return max;
    }

    /**
     * Extract minimum element from min-heap
     * 
     * @param a Min-heap array
     * @param n Current size of heap
     * @return Minimum element
     */
    public static int extractMin(int[] a, int n) {
        if (n <= 0) {
            throw new IllegalStateException("Heap is empty");
        }
        
        int min = a[0];         // Root is minimum
        a[0] = a[n - 1];        // Move last element to root
        shiftDownMin(a, n - 1, 0);  // Restore heap property
        return min;
    }

    /**
     * Insert element into max-heap
     * 
     * @param a Max-heap array
     * @param n Current size of heap before insertion
     * @param value Value to insert
     */
    public static void insertMaxHeap(int[] a, int n, int value) {
        if (n >= a.length) {
            throw new IllegalStateException("Heap is full");
        }
        
        a[n] = value;                    // Place at end
        shiftUpMax(a, n + 1, n);         // Move up to correct position
    }

    /**
     * Move element up in max-heap (used after insertion)
     */
    private static void shiftUpMax(int[] a, int n, int i) {
        while (i > 0 && a[i] > a[parent(i)]) {
            swap(a, i, parent(i));
            i = parent(i);
        }
    }

    /**
     * Insert element into min-heap
     */
    public static void insertMinHeap(int[] a, int n, int value) {
        if (n >= a.length) {
            throw new IllegalStateException("Heap is full");
        }
        
        a[n] = value;
        shiftUpMin(a, n + 1, n);
    }

    /**
     * Move element up in min-heap
     */
    private static void shiftUpMin(int[] a, int n, int i) {
        while (i > 0 && a[i] < a[parent(i)]) {
            swap(a, i, parent(i));
            i = parent(i);
        }
    }

    /**
     * Get parent index of node i
     */
    private static int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     * Swap two elements in array
     */
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * Check if array satisfies max-heap property
     */
    public static boolean isMaxHeap(int[] a) {
        int n = a.length;
        for (int i = 0; i <= n / 2 - 1; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            
            if (left < n && a[i] < a[left]) {
                return false;
            }
            if (right < n && a[i] < a[right]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if array satisfies min-heap property
     */
    public static boolean isMinHeap(int[] a) {
        int n = a.length;
        for (int i = 0; i <= n / 2 - 1; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            
            if (left < n && a[i] > a[left]) {
                return false;
            }
            if (right < n && a[i] > a[right]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Perform heap sort using max-heap
     * 
     * Steps:
     * 1. Build max-heap from array
     * 2. Repeatedly extract maximum and place at end
     * 3. Result is sorted array in ascending order
     */
    public static void heapSort(int[] a) {
        int n = a.length;
        
        // Build max-heap
        buildMaxHeap(a);
        
        // One by one extract elements from heap
        for (int i = n - 1; i > 0; i--) {
            // Move current root (max) to end
            swap(a, 0, i);
            
            // Heapify reduced heap
            shiftDownMax(a, i, 0);
        }
    }

    /**
     * Demonstration of heap operations
     */
    public static void main(String[] args) {
        int[] A = {1, 3, 4, 2, 5, 7, 5, 6, 8};
        System.out.println("Original array: " + Arrays.toString(A));
        
        // 1. Build and display max-heap
        buildMaxHeap(A);
        System.out.println("Max heap: " + Arrays.toString(A));
        System.out.println("Is max heap? " + isMaxHeap(A));
        
        // 2. Extract max and show heap
        int max = extractMax(A, A.length);
        System.out.println("Extracted max: " + max);
        System.out.println("After extraction: " + Arrays.toString(A));
        
        // 3. Build and display min-heap
        buildMinHeap(A);
        System.out.println("Min heap: " + Arrays.toString(A));
        System.out.println("Is min heap? " + isMinHeap(A));
        
        // 4. Extract min and show heap
        int min = extractMin(A, A.length - 1);
        System.out.println("Extracted min: " + min);
        System.out.println("After extraction: " + Arrays.toString(A));
        
        // 5. Heap sort demonstration
        int[] B = {9, 3, 7, 1, 8, 2, 6, 4, 5};
        System.out.println("\nHeap Sort Demo:");
        System.out.println("Before sorting: " + Arrays.toString(B));
        heapSort(B);
        System.out.println("After heap sort: " + Arrays.toString(B));
    }
}