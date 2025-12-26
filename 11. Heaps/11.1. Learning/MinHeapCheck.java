import java.util.Scanner;

/**
 * Check if a given array represents a max-heap.
 * 
 * Max-Heap Property: For every node i (0-indexed):
 * 1. Parent(i) >= left child (if exists)
 * 2. Parent(i) >= right child (if exists)
 * 
 * The array representation of a heap uses:
 * - Parent index: i
 * - Left child: 2*i + 1
 * - Right child: 2*i + 2
 * 
 * This implementation checks the max-heap property iteratively.
 */
public class MinHeapCheck {

    /**
     * Checks if the given array satisfies the max-heap property.
     * 
     * Time Complexity: O(n) - visits each node once
     * Space Complexity: O(1) - uses only constant extra space
     * 
     * @param arr The array to check
     * @param n The size of the array (could also use arr.length)
     * @return true if arr represents a max-heap, false otherwise
     */
    public boolean isMaxHeap(int[] arr, int n) {
        // Iterate through all potential parent nodes
        // Last parent node is at index floor((n-2)/2) or floor(n/2)-1
        // We can also iterate until i <= n/2 - 1, but checking all is fine too
        for (int i = 0; i < n; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            
            // Check left child if it exists
            if (leftChild < n) {
                if (arr[i] < arr[leftChild]) {
                    return false;  // Parent < left child violates max-heap
                }
            }
            
            // Check right child if it exists
            if (rightChild < n) {
                if (arr[i] < arr[rightChild]) {
                    return false;  // Parent < right child violates max-heap
                }
            }
        }
        return true;
    }

    /**
     * Optimized version that stops at the last parent node.
     * Since leaf nodes have no children, we only need to check parent nodes.
     * Last parent index = floor(n/2) - 1
     */
    public boolean isMaxHeapOptimized(int[] arr, int n) {
        // Only check nodes that have children (parent nodes)
        int lastParentIdx = n / 2 - 1;
        
        for (int i = 0; i <= lastParentIdx; i++) {
            int leftChild = 2 * i + 1;
            int rightChild = 2 * i + 2;
            
            // Check left child
            if (arr[i] < arr[leftChild]) {
                return false;
            }
            
            // Check right child (if exists)
            if (rightChild < n && arr[i] < arr[rightChild]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Recursive approach to check max-heap property.
     * More elegant but uses O(log n) recursion stack space.
     */
    public boolean isMaxHeapRecursive(int[] arr, int n) {
        return isMaxHeapRecursive(arr, 0, n);
    }
    
    private boolean isMaxHeapRecursive(int[] arr, int i, int n) {
        // Base case: if node is leaf (has no children)
        if (i >= n / 2) {
            return true;
        }
        
        int leftChild = 2 * i + 1;
        int rightChild = 2 * i + 2;
        
        // Check if node is greater than or equal to left child
        // and recursively check left subtree
        boolean leftValid = arr[i] >= arr[leftChild] 
                          && isMaxHeapRecursive(arr, leftChild, n);
        
        // Check right child if exists
        boolean rightValid = true;
        if (rightChild < n) {
            rightValid = arr[i] >= arr[rightChild] 
                       && isMaxHeapRecursive(arr, rightChild, n);
        }
        
        return leftValid && rightValid;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();  // Size of array
            int[] arr = new int[n];
            
            for (int i = 0; i < n; i++) {
                arr[i] = sc.nextInt();
            }
            
            MinHeapCheck solver = new MinHeapCheck();
            boolean result = solver.isMaxHeap(arr, n);
            // boolean result = solver.isMaxHeapOptimized(arr, n);  // Alternative
            // boolean result = solver.isMaxHeapRecursive(arr, n);  // Alternative
            
            System.out.println(result ? 1 : 0);
        }
        sc.close();
    }
}