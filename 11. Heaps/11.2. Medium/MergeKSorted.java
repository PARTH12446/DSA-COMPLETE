import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Merge k sorted arrays into a single sorted array using a min-heap.
 * Each array is assumed to be sorted in non-decreasing order.
 * 
 * Problem Statement:
 * Given k sorted arrays of integers, merge them into a single sorted array.
 *
 * Example:
 * Input: arrays = [[1,4,5], [1,3,4], [2,6]]
 * Output: [1,1,2,3,4,4,5,6]
 *
 * Approach: Min-Heap (Priority Queue)
 * 1. Create a min-heap that stores tuples (value, arrayIndex, elementIndex)
 * 2. Initialize heap with first element of each non-empty array
 * 3. Repeatedly extract smallest element from heap
 * 4. Add extracted element to result array
 * 5. If extracted element has next element in its array, add it to heap
 * 6. Continue until heap is empty
 *
 * Time Complexity: O(N log k) where N is total elements, k is number of arrays
 * Space Complexity: O(k) for heap + O(N) for result array
 */
public class MergeKSorted {

    /**
     * Inner class to represent a node in the heap
     * Stores: value, which array it came from, position in that array
     */
    private static class Node {
        int value;          // The actual integer value
        int arrayIndex;     // Which array this value comes from (0-indexed)
        int elementIndex;   // Position within that array

        Node(int value, int arrayIndex, int elementIndex) {
            this.value = value;
            this.arrayIndex = arrayIndex;
            this.elementIndex = elementIndex;
        }
    }

    /**
     * Main method to merge k sorted arrays
     * 
     * @param arrays 2D array containing k sorted arrays
     * @return Single sorted array containing all elements
     */
    public int[] mergeKSortedArrays(int[][] arrays) {
        int k = arrays.length;
        
        // Edge case: empty input
        if (k == 0) {
            return new int[0];
        }

        // Create min-heap with comparator based on value
        // Smallest value has highest priority
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.value - b.value);

        // Calculate total size of result and initialize heap
        int totalSize = 0;
        for (int i = 0; i < k; i++) {
            // Only add to heap if array is non-empty
            if (arrays[i].length > 0) {
                pq.offer(new Node(arrays[i][0], i, 0));
                totalSize += arrays[i].length;
            }
        }

        // Edge case: all arrays are empty
        if (totalSize == 0) {
            return new int[0];
        }

        // Initialize result array
        int[] result = new int[totalSize];
        int resultIndex = 0;

        // Process heap until empty
        while (!pq.isEmpty()) {
            // Extract smallest element from heap
            Node currentNode = pq.poll();
            result[resultIndex++] = currentNode.value;

            // Check if there's next element in the same array
            int nextElementIndex = currentNode.elementIndex + 1;
            if (nextElementIndex < arrays[currentNode.arrayIndex].length) {
                // Add next element from same array to heap
                pq.offer(new Node(
                    arrays[currentNode.arrayIndex][nextElementIndex],
                    currentNode.arrayIndex,
                    nextElementIndex
                ));
            }
        }

        return result;
    }

    /**
     * Alternative approach: Divide and Conquer (Merge Sort style)
     * 1. Repeatedly merge pairs of arrays until one remains
     * 2. Uses O(1) extra space (excluding result array)
     * Time: O(N log k), Space: O(1) for merging, O(N) for result
     */
    public int[] mergeKSortedArraysDivideConquer(int[][] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new int[0];
        }
        
        int k = arrays.length;
        List<int[]> arrayList = new ArrayList<>();
        for (int[] arr : arrays) {
            arrayList.add(arr);
        }
        
        while (arrayList.size() > 1) {
            List<int[]> mergedList = new ArrayList<>();
            for (int i = 0; i < arrayList.size(); i += 2) {
                if (i + 1 < arrayList.size()) {
                    mergedList.add(mergeTwoArrays(arrayList.get(i), arrayList.get(i + 1)));
                } else {
                    mergedList.add(arrayList.get(i));
                }
            }
            arrayList = mergedList;
        }
        
        return arrayList.get(0);
    }
    
    private int[] mergeTwoArrays(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }
        
        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }
        
        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }
        
        return result;
    }

    /**
     * Visualization helper to show step-by-step merging process
     */
    public void visualizeMerge(int[][] arrays) {
        System.out.println("\n=== Visualizing Merge Process ===");
        
        // Print input arrays
        for (int i = 0; i < arrays.length; i++) {
            System.out.print("Array " + i + ": ");
            for (int val : arrays[i]) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
        
        PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.value - b.value);
        
        // Initialize heap
        for (int i = 0; i < arrays.length; i++) {
            if (arrays[i].length > 0) {
                pq.offer(new Node(arrays[i][0], i, 0));
            }
        }
        
        System.out.println("\nInitial heap contents:");
        for (Node node : pq) {
            System.out.println("  Value=" + node.value + ", Array=" + node.arrayIndex + 
                             ", Pos=" + node.elementIndex);
        }
        
        int step = 1;
        int totalSize = 0;
        for (int[] arr : arrays) totalSize += arr.length;
        int[] result = new int[totalSize];
        int resultIdx = 0;
        
        while (!pq.isEmpty()) {
            System.out.println("\nStep " + step++ + ":");
            Node node = pq.poll();
            System.out.println("  Extracted: Value=" + node.value + 
                             " (from Array " + node.arrayIndex + 
                             " at position " + node.elementIndex + ")");
            
            result[resultIdx++] = node.value;
            
            int nextPos = node.elementIndex + 1;
            if (nextPos < arrays[node.arrayIndex].length) {
                Node nextNode = new Node(arrays[node.arrayIndex][nextPos], 
                                       node.arrayIndex, nextPos);
                pq.offer(nextNode);
                System.out.println("  Added to heap: Value=" + nextNode.value + 
                                 " (from Array " + nextNode.arrayIndex + 
                                 " at position " + nextNode.elementIndex + ")");
            }
            
            System.out.print("  Current heap: ");
            for (Node n : pq) {
                System.out.print(n.value + " ");
            }
            System.out.println();
            
            System.out.print("  Result so far: ");
            for (int i = 0; i < resultIdx; i++) {
                System.out.print(result[i] + " ");
            }
            System.out.println();
        }
        
        System.out.println("\nFinal merged array: ");
        for (int val : result) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            int k = sc.nextInt();  // Number of arrays
            int[][] arrays = new int[k][];
            
            for (int i = 0; i < k; i++) {
                int n = sc.nextInt();  // Size of current array
                arrays[i] = new int[n];
                for (int j = 0; j < n; j++) {
                    arrays[i][j] = sc.nextInt();
                }
            }
            
            MergeKSorted solver = new MergeKSorted();
            
            // Method 1: Min-Heap approach
            int[] merged = solver.mergeKSortedArrays(arrays);
            System.out.print("Merged array: ");
            for (int val : merged) {
                System.out.print(val + " ");
            }
            System.out.println();
            
            // Uncomment for visualization
            // solver.visualizeMerge(arrays);
            
            // Method 2: Divide and Conquer approach
            // int[] merged2 = solver.mergeKSortedArraysDivideConquer(arrays);
            // System.out.print("Divide & Conquer result: ");
            // for (int val : merged2) {
            //     System.out.print(val + " ");
            // }
            // System.out.println();
        }
        
        sc.close();
    }
}