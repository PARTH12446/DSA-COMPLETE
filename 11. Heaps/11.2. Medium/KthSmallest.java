import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;

/**
 * Find the k-th smallest element in an unsorted array using a max-heap of size k.
 *
 * Problem Statement:
 * Given an unsorted array of integers and an integer k, find the k-th smallest
 * element in the array (1-indexed).
 *
 * Example:
 * Input: nums = [3,2,1,5,6,4], k = 2
 * Output: 2 (2nd smallest element is 2)
 *
 * Approach 1: Max-Heap (this implementation)
 * - Maintain a max-heap of size k containing the k smallest elements seen so far
 * - For each element:
 *   1. Add to heap
 *   2. If heap size > k, remove the largest (root)
 * - After processing all elements, root contains k-th smallest
 *
 * Time Complexity: O(n log k) - each heap operation is O(log k)
 * Space Complexity: O(k) - heap stores at most k elements
 *
 * Approach 2: Min-Heap (alternative)
 * - Build min-heap of all elements: O(n)
 * - Extract k times: O(k log n)
 * - Total: O(n + k log n) - better when k is small
 *
 * Approach 3: QuickSelect (optimal average case)
 * - Randomized partition like quicksort: O(n) average, O(n²) worst
 */
public class KthSmallest {

    /**
     * Method 1: Using max-heap of size k (most efficient for k << n)
     * 
     * Intuition: We want the k-th smallest, so we keep k smallest elements.
     * Since we need to remove largest when exceeding k, we use max-heap.
     * 
     * @param nums Input array
     * @param k k-th smallest (1-indexed)
     * @return k-th smallest element
     */
    public int findKthSmallest(int[] nums, int k) {
        // Validation
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        // Create a max-heap using custom comparator (reverse natural order)
        // Max-heap keeps largest element at root for easy removal
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        
        for (int x : nums) {
            // Add current element to heap
            maxHeap.offer(x);
            
            // If heap exceeds size k, remove the largest element
            // This ensures heap always contains k smallest elements seen so far
            if (maxHeap.size() > k) {
                maxHeap.poll(); // removes largest (root of max-heap)
            }
        }
        
        // After processing all elements, root contains k-th smallest
        return maxHeap.peek();
    }
    
    /**
     * Method 2: Using min-heap (alternative approach)
     * - Build min-heap of all elements
     * - Extract k times to get k-th smallest
     * - Better when k is large (close to n)
     * 
     * Time: O(n + k log n)
     */
    public int findKthSmallestMinHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        // Min-heap (default PriorityQueue)
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        
        // Add all elements to heap: O(n) with heapify
        for (int num : nums) {
            minHeap.offer(num);
        }
        
        // Extract k-1 times
        for (int i = 0; i < k - 1; i++) {
            minHeap.poll();
        }
        
        // Next element is k-th smallest
        return minHeap.peek();
    }
    
    /**
     * Method 3: QuickSelect algorithm (optimal average case O(n))
     * - Similar to quicksort but only recurses into relevant partition
     * - Randomized pivot selection for better average performance
     */
    public int findKthSmallestQuickSelect(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        // Convert k from 1-indexed to 0-indexed
        return quickSelect(nums, 0, nums.length - 1, k - 1);
    }
    
    private int quickSelect(int[] nums, int left, int right, int k) {
        if (left == right) {
            return nums[left];
        }
        
        // Partition the array
        int pivotIndex = partition(nums, left, right);
        
        // If pivot is at k-th position, we found our answer
        if (k == pivotIndex) {
            return nums[k];
        } 
        // If k is less than pivot index, search left part
        else if (k < pivotIndex) {
            return quickSelect(nums, left, pivotIndex - 1, k);
        } 
        // Otherwise search right part
        else {
            return quickSelect(nums, pivotIndex + 1, right, k);
        }
    }
    
    private int partition(int[] nums, int left, int right) {
        // Choose random pivot for better average performance
        int pivotIndex = left + (int)(Math.random() * (right - left + 1));
        int pivotValue = nums[pivotIndex];
        
        // Move pivot to end
        swap(nums, pivotIndex, right);
        
        int storeIndex = left;
        
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }
        
        // Move pivot to its final place
        swap(nums, storeIndex, right);
        return storeIndex;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    /**
     * Method 4: Sorting (simplest but O(n log n))
     */
    public int findKthSmallestSorting(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input");
        }
        
        Arrays.sort(nums);
        return nums[k - 1];
    }

    /**
     * Visualization helper: Show step-by-step heap operations
     */
    public void visualizeKthSmallest(int[] nums, int k) {
        System.out.println("\n=== Visualization: Finding " + k + "-th smallest ===");
        System.out.println("Array: " + Arrays.toString(nums));
        
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            System.out.println("\nStep " + (i+1) + ": Processing element " + x);
            
            maxHeap.offer(x);
            System.out.println("  After adding " + x + ": Heap = " + maxHeap);
            
            if (maxHeap.size() > k) {
                int removed = maxHeap.poll();
                System.out.println("  Heap size > " + k + ", removed largest: " + removed);
                System.out.println("  Updated heap: " + maxHeap);
            }
        }
        
        System.out.println("\nFinal heap contains " + k + " smallest elements: " + maxHeap);
        System.out.println(k + "-th smallest element = " + maxHeap.peek());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int n = sc.nextInt();
            int k = sc.nextInt();
            int[] nums = new int[n];
            
            for (int i = 0; i < n; i++) {
                nums[i] = sc.nextInt();
            }
            
            KthSmallest solver = new KthSmallest();
            
            // Using max-heap approach (most efficient for k << n)
            int ans = solver.findKthSmallest(nums, k);
            System.out.println("K-th smallest (max-heap): " + ans);
            
            // Uncomment for visualization
            // solver.visualizeKthSmallest(nums, k);
            
            // Alternative methods for comparison
            // int ans2 = solver.findKthSmallestMinHeap(nums, k);
            // System.out.println("K-th smallest (min-heap): " + ans2);
            
            // int ans3 = solver.findKthSmallestQuickSelect(nums, k);
            // System.out.println("K-th smallest (quickselect): " + ans3);
            
            // int ans4 = solver.findKthSmallestSorting(nums, k);
            // System.out.println("K-th smallest (sorting): " + ans4);
        }
        
        sc.close();
    }
}