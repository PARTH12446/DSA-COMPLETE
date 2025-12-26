import java.util.Arrays;

/**
 * Demonstration of building max-heap and min-heap from an array
 * and performing basic heap operations similar to Python's heapq.
 * 
 * Heap: A complete binary tree that satisfies the heap property.
 * - Max-Heap: Parent ≥ Children (root is maximum)
 * - Min-Heap: Parent ≤ Children (root is minimum)
 * 
 * Array Representation (0-indexed):
 * - Parent of node i: floor((i-1)/2)
 * - Left child of node i: 2*i + 1
 * - Right child of node i: 2*i + 2
 * 
 * Heapify (Floyd's Algorithm): O(n) time to build heap from unsorted array
 * - Start from last non-leaf node and work backwards to root
 * - Last non-leaf node index = floor(n/2) - 1
 */
public class MinMaxHeap {

    // ==================== MAX-HEAP OPERATIONS ====================
    
    /**
     * Build a max heap in-place from an unsorted array.
     * Max-Heap Property: Parent ≥ Children
     * 
     * @param a The array to be transformed into max-heap
     */
    public static void buildMaxHeap(int[] a) {
        int n = a.length;
        
        // Start from last non-leaf node and move up to root
        // Last non-leaf node index = floor(n/2) - 1
        for (int i = n / 2 - 1; i >= 0; i--) {
            shiftDownMax(a, n, i);
        }
    }

    /**
     * Heapify-down operation for max-heap (also called max-heapify)
     * Ensures max-heap property is satisfied at node i
     * Assumes left and right subtrees are already max-heaps
     * 
     * @param a The heap array
     * @param n Size of heap (number of elements to consider)
     * @param i Index of node to heapify
     */
    private static void shiftDownMax(int[] a, int n, int i) {
        int largest = i;        // Assume current node is largest
        int left = 2 * i + 1;   // Left child index
        int right = 2 * i + 2;  // Right child index
        
        // Check if left child exists and is larger than current largest
        if (left < n && a[left] > a[largest]) {
            largest = left;
        }
        
        // Check if right child exists and is larger than current largest
        if (right < n && a[right] > a[largest]) {
            largest = right;
        }
        
        // If largest is not the current node, swap and recursively heapify
        if (largest != i) {
            swap(a, i, largest);
            shiftDownMax(a, n, largest);  // Recursively heapify the affected subtree
        }
    }
    
    /**
     * Heapify-up operation for max-heap
     * Used after insertion to maintain heap property
     * 
     * @param a The heap array
     * @param i Index of node to shift up
     */
    private static void shiftUpMax(int[] a, int i) {
        // While not root and parent is smaller
        while (i > 0 && a[i] > a[(i - 1) / 2]) {
            swap(a, i, (i - 1) / 2);
            i = (i - 1) / 2;  // Move to parent
        }
    }

    // ==================== MIN-HEAP OPERATIONS ====================
    
    /**
     * Build a min heap in-place from an unsorted array.
     * Min-Heap Property: Parent ≤ Children
     * 
     * @param a The array to be transformed into min-heap
     */
    public static void buildMinHeap(int[] a) {
        int n = a.length;
        
        // Start from last non-leaf node
        for (int i = n / 2 - 1; i >= 0; i--) {
            shiftDownMin(a, n, i);
        }
    }

    /**
     * Heapify-down operation for min-heap
     * 
     * @param a The heap array
     * @param n Size of heap
     * @param i Index of node to heapify
     */
    private static void shiftDownMin(int[] a, int n, int i) {
        int smallest = i;       // Assume current node is smallest
        int left = 2 * i + 1;   // Left child index
        int right = 2 * i + 2;  // Right child index
        
        // Check if left child exists and is smaller than current smallest
        if (left < n && a[left] < a[smallest]) {
            smallest = left;
        }
        
        // Check if right child exists and is smaller than current smallest
        if (right < n && a[right] < a[smallest]) {
            smallest = right;
        }
        
        // If smallest is not the current node, swap and recursively heapify
        if (smallest != i) {
            swap(a, i, smallest);
            shiftDownMin(a, n, smallest);  // Recursively heapify the affected subtree
        }
    }
    
    /**
     * Heapify-up operation for min-heap
     * 
     * @param a The heap array
     * @param i Index of node to shift up
     */
    private static void shiftUpMin(int[] a, int i) {
        // While not root and parent is larger
        while (i > 0 && a[i] < a[(i - 1) / 2]) {
            swap(a, i, (i - 1) / 2);
            i = (i - 1) / 2;  // Move to parent
        }
    }

    // ==================== HEAP OPERATIONS ====================
    
    /**
     * Extract maximum element from max-heap
     * 
     * @param a Max-heap array
     * @param n Current heap size
     * @return Maximum element
     */
    public static int extractMax(int[] a, int n) {
        if (n <= 0) {
            throw new IllegalStateException("Heap is empty");
        }
        
        int max = a[0];          // Root is maximum
        a[0] = a[n - 1];         // Move last element to root
        shiftDownMax(a, n - 1, 0);  // Restore heap property with reduced size
        return max;
    }
    
    /**
     * Extract minimum element from min-heap
     * 
     * @param a Min-heap array
     * @param n Current heap size
     * @return Minimum element
     */
    public static int extractMin(int[] a, int n) {
        if (n <= 0) {
            throw new IllegalStateException("Heap is empty");
        }
        
        int min = a[0];          // Root is minimum
        a[0] = a[n - 1];         // Move last element to root
        shiftDownMin(a, n - 1, 0);  // Restore heap property with reduced size
        return min;
    }
    
    /**
     * Insert element into max-heap
     * Note: Array must have capacity for new element
     * 
     * @param a Max-heap array
     * @param n Current heap size before insertion
     * @param value Value to insert
     * @return New heap size
     */
    public static int insertMaxHeap(int[] a, int n, int value) {
        if (n >= a.length) {
            throw new IllegalStateException("Heap is full");
        }
        
        a[n] = value;           // Place at end
        shiftUpMax(a, n);       // Move up to correct position
        return n + 1;           // Return new size
    }
    
    /**
     * Insert element into min-heap
     * 
     * @param a Min-heap array
     * @param n Current heap size before insertion
     * @param value Value to insert
     * @return New heap size
     */
    public static int insertMinHeap(int[] a, int n, int value) {
        if (n >= a.length) {
            throw new IllegalStateException("Heap is full");
        }
        
        a[n] = value;
        shiftUpMin(a, n);
        return n + 1;
    }

    // ==================== HELPER METHODS ====================
    
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
     * Print heap in tree-like format
     */
    public static void printHeapTree(int[] a, int n) {
        System.out.println("\nHeap as tree:");
        int levels = (int) (Math.log(n) / Math.log(2)) + 1;
        
        for (int level = 0; level < levels; level++) {
            int start = (int) Math.pow(2, level) - 1;
            int end = Math.min((int) Math.pow(2, level + 1) - 1, n);
            
            // Print leading spaces
            int spaces = (int) Math.pow(2, levels - level - 1) - 1;
            for (int i = 0; i < spaces; i++) {
                System.out.print("   ");
            }
            
            // Print nodes at this level
            for (int i = start; i < end; i++) {
                System.out.printf("%3d", a[i]);
                if (i < end - 1) {
                    // Print spaces between nodes
                    int between = (int) Math.pow(2, levels - level) - 1;
                    for (int j = 0; j < between; j++) {
                        System.out.print("   ");
                    }
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Print heap in array format with parent-child relationships
     */
    public static void printHeapDetails(int[] a, int n) {
        System.out.println("\nHeap details (array index: value [parent → children]):");
        for (int i = 0; i < n; i++) {
            System.out.printf("%d: %d", i, a[i]);
            
            // Show parent if not root
            if (i > 0) {
                System.out.printf(" (parent: a[%d]=%d)", (i-1)/2, a[(i-1)/2]);
            }
            
            // Show children if they exist
            int left = 2*i + 1;
            int right = 2*i + 2;
            if (left < n || right < n) {
                System.out.print(" → children: ");
                if (left < n) System.out.printf("a[%d]=%d ", left, a[left]);
                if (right < n) System.out.printf("a[%d]=%d", right, a[right]);
            }
            System.out.println();
        }
    }

    // ==================== DEMONSTRATION AND TESTING ====================
    
    /**
     * Simple demo main:
     * - Start with a fixed array.
     * - Convert to max heap, print.
     * - Convert to min heap, print.
     */
    public static void main(String[] args) {
        int[] A = {1, 3, 4, 2, 5, 7, 5, 6, 8};
        
        System.out.println("Original array: " + Arrays.toString(A));
        System.out.println("Is max heap? " + isMaxHeap(A));
        System.out.println("Is min heap? " + isMinHeap(A));
        
        // ========== MAX-HEAP DEMONSTRATION ==========
        System.out.println("\n" + "=".repeat(50));
        System.out.println("BUILDING MAX-HEAP");
        System.out.println("=".repeat(50));
        
        int[] maxHeap = A.clone();
        buildMaxHeap(maxHeap);
        System.out.println("Max heap array: " + Arrays.toString(maxHeap));
        System.out.println("Is max heap? " + isMaxHeap(maxHeap));
        printHeapTree(maxHeap, maxHeap.length);
        printHeapDetails(maxHeap, maxHeap.length);
        
        // Extract max operation
        System.out.println("\nExtracting maximum (heap sort step):");
        int heapSize = maxHeap.length;
        for (int i = 0; i < 3; i++) {  // Extract 3 times for demonstration
            if (heapSize > 0) {
                int max = extractMax(maxHeap, heapSize);
                heapSize--;
                System.out.printf("Extracted: %d, Remaining heap size: %d\n", max, heapSize);
                System.out.println("Heap after extraction: " + 
                                 Arrays.toString(Arrays.copyOf(maxHeap, heapSize)));
            }
        }
        
        // ========== MIN-HEAP DEMONSTRATION ==========
        System.out.println("\n" + "=".repeat(50));
        System.out.println("BUILDING MIN-HEAP");
        System.out.println("=".repeat(50));
        
        int[] minHeap = A.clone();
        buildMinHeap(minHeap);
        System.out.println("Min heap array: " + Arrays.toString(minHeap));
        System.out.println("Is min heap? " + isMinHeap(minHeap));
        printHeapTree(minHeap, minHeap.length);
        printHeapDetails(minHeap, minHeap.length);
        
        // Extract min operation
        System.out.println("\nExtracting minimum:");
        heapSize = minHeap.length;
        for (int i = 0; i < 3; i++) {  // Extract 3 times for demonstration
            if (heapSize > 0) {
                int min = extractMin(minHeap, heapSize);
                heapSize--;
                System.out.printf("Extracted: %d, Remaining heap size: %d\n", min, heapSize);
                System.out.println("Heap after extraction: " + 
                                 Arrays.toString(Arrays.copyOf(minHeap, heapSize)));
            }
        }
        
        // ========== HEAP SORT DEMONSTRATION ==========
        System.out.println("\n" + "=".repeat(50));
        System.out.println("HEAP SORT (Using Max-Heap)");
        System.out.println("=".repeat(50));
        
        int[] sortArray = A.clone();
        System.out.println("Original: " + Arrays.toString(sortArray));
        
        // Build max heap
        buildMaxHeap(sortArray);
        System.out.println("After building max heap: " + Arrays.toString(sortArray));
        
        // One by one extract elements (heap sort)
        for (int i = sortArray.length - 1; i > 0; i--) {
            swap(sortArray, 0, i);  // Move current root to end
            shiftDownMax(sortArray, i, 0);  // Heapify reduced heap
        }
        System.out.println("After heap sort (ascending): " + Arrays.toString(sortArray));
        
        // ========== INSERTION DEMONSTRATION ==========
        System.out.println("\n" + "=".repeat(50));
        System.out.println("HEAP INSERTION OPERATIONS");
        System.out.println("=".repeat(50));
        
        // Create array with extra space for insertion
        int[] insertHeap = new int[A.length + 3];
        System.arraycopy(A, 0, insertHeap, 0, A.length);
        
        // Build max heap initially
        buildMaxHeap(insertHeap);
        System.out.println("Initial max heap: " + 
                         Arrays.toString(Arrays.copyOf(insertHeap, A.length)));
        
        // Insert new elements
        int size = A.length;
        System.out.println("\nInserting 10 into max heap:");
        size = insertMaxHeap(insertHeap, size, 10);
        System.out.println("After insertion: " + Arrays.toString(Arrays.copyOf(insertHeap, size)));
        System.out.println("Is max heap? " + isMaxHeap(Arrays.copyOf(insertHeap, size)));
        
        System.out.println("\nInserting 0 into max heap:");
        size = insertMaxHeap(insertHeap, size, 0);
        System.out.println("After insertion: " + Arrays.toString(Arrays.copyOf(insertHeap, size)));
        System.out.println("Is max heap? " + isMaxHeap(Arrays.copyOf(insertHeap, size)));
    }
}