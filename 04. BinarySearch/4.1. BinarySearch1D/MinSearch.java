import java.util.*;

/** 
 * Problem: Find Minimum in Rotated Sorted Array
 * LeetCode 153 / Coding Ninjas equivalent
 * 
 * Given a sorted rotated array of unique elements, 
 * find the minimum element in O(log n) time.
 */
public class MinSearch {

    // ============================
    // APPROACH 1: Standard Binary Search (Original)
    // ============================
    
    /**
     * Find minimum element in rotated sorted array
     * 
     * @param arr Rotated sorted array
     * @return Minimum element
     * 
     * Time: O(log n), Space: O(1)
     */
    public static int findMin(int[] arr) {
        int n = arr.length;
        
        // Edge cases
        if (n == 0) return -1; // or throw exception
        if (n == 1) return arr[0];
        if (arr[0] < arr[n-1]) return arr[0]; // Array not rotated
        
        int low = 0;
        int high = n - 1;
        int ans = Integer.MAX_VALUE;
        
        while (low <= high) {
            int mid = low + (high - low) / 2; // Avoid overflow
            
            // Check if left half is sorted
            if (arr[low] <= arr[mid]) {
                // Left half is sorted, minimum is at low or in right half
                ans = Math.min(ans, arr[low]);
                low = mid + 1; // Search in right half
            } else {
                // Right half is sorted, minimum is at mid or in left half
                ans = Math.min(ans, arr[mid]);
                high = mid - 1; // Search in left half
            }
        }
        
        return ans;
    }
    
    // ============================
    // APPROACH 2: Standard LeetCode Solution
    // ============================
    
    /**
     * Standard solution without using ans variable
     */
    public static int findMinStandard(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        // If array is not rotated
        if (arr[low] <= arr[high]) {
            return arr[low];
        }
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Check if mid is the minimum
            if (mid > 0 && arr[mid] < arr[mid - 1]) {
                return arr[mid];
            }
            if (mid < arr.length - 1 && arr[mid] > arr[mid + 1]) {
                return arr[mid + 1];
            }
            
            // Decide which half to search
            if (arr[low] <= arr[mid]) {
                // Left half is sorted, minimum is in right half
                low = mid + 1;
            } else {
                // Right half is sorted, minimum is in left half
                high = mid - 1;
            }
        }
        
        return arr[0]; // Should never reach here for valid input
    }
    
    // ============================
    // APPROACH 3: Binary Search with Pivot
    // ============================
    
    /**
     * Find pivot (minimum element index)
     */
    public static int findMinWithPivot(int[] arr) {
        int pivot = findPivot(arr);
        return arr[pivot];
    }
    
    private static int findPivot(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        // Array not rotated
        if (arr[low] <= arr[high]) {
            return 0;
        }
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Check if mid is pivot
            if (mid < arr.length - 1 && arr[mid] > arr[mid + 1]) {
                return mid + 1;
            }
            if (mid > 0 && arr[mid] < arr[mid - 1]) {
                return mid;
            }
            
            // Decide which half contains pivot
            if (arr[low] <= arr[mid]) {
                // Left half is sorted, pivot in right half
                low = mid + 1;
            } else {
                // Right half is sorted, pivot in left half
                high = mid - 1;
            }
        }
        
        return 0;
    }
    
    // ============================
    // APPROACH 4: Iterative without ans
    // ============================
    
    /**
     * Clean iterative solution
     */
    public static int findMinIterative(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        // Binary search for minimum
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] > arr[high]) {
                // Minimum is in right half
                low = mid + 1;
            } else {
                // Minimum is in left half (including mid)
                high = mid;
            }
        }
        
        return arr[low]; // When low == high, we found minimum
    }
    
    // ============================
    // APPROACH 5: Recursive Solution
    // ============================
    
    /**
     * Recursive implementation
     */
    public static int findMinRecursive(int[] arr) {
        return findMinRecursiveHelper(arr, 0, arr.length - 1);
    }
    
    private static int findMinRecursiveHelper(int[] arr, int low, int high) {
        // Base cases
        if (low == high) {
            return arr[low];
        }
        
        // If subarray is sorted
        if (arr[low] < arr[high]) {
            return arr[low];
        }
        
        int mid = low + (high - low) / 2;
        
        // Check if mid is minimum
        if (mid > low && arr[mid] < arr[mid - 1]) {
            return arr[mid];
        }
        if (mid < high && arr[mid] > arr[mid + 1]) {
            return arr[mid + 1];
        }
        
        // Recursive search
        if (arr[low] <= arr[mid]) {
            return findMinRecursiveHelper(arr, mid + 1, high);
        } else {
            return findMinRecursiveHelper(arr, low, mid - 1);
        }
    }
    
    // ============================
    // EXTENSION: With Duplicates (LeetCode 154)
    // ============================
    
    /**
     * Find minimum in rotated sorted array with duplicates
     * 
     * @param arr Rotated sorted array (may have duplicates)
     * @return Minimum element
     */
    public static int findMinWithDuplicates(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] > arr[high]) {
                // Minimum is in right half
                low = mid + 1;
            } else if (arr[mid] < arr[high]) {
                // Minimum is in left half (including mid)
                high = mid;
            } else {
                // arr[mid] == arr[high], can't decide which half
                // Reduce search space by one
                high--;
            }
        }
        
        return arr[low];
    }
    
    // ============================
    // EXTENSION: Find rotation count
    // ============================
    
    /**
     * Find number of times array is rotated
     * Rotation count = index of minimum element
     */
    public static int findRotationCount(int[] arr) {
        return findPivot(arr);
    }
    
    // ============================
    // EXTENSION: Search in rotated sorted array
    // ============================
    
    /**
     * Search for target in rotated sorted array (LeetCode 33)
     * 
     * @param arr Rotated sorted array
     * @param target Value to search for
     * @return Index of target, or -1 if not found
     */
    public static int searchInRotatedArray(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == target) {
                return mid;
            }
            
            // Check which half is sorted
            if (arr[low] <= arr[mid]) {
                // Left half is sorted
                if (arr[low] <= target && target < arr[mid]) {
                    high = mid - 1; // Target in left half
                } else {
                    low = mid + 1; // Target in right half
                }
            } else {
                // Right half is sorted
                if (arr[mid] < target && target <= arr[high]) {
                    low = mid + 1; // Target in right half
                } else {
                    high = mid - 1; // Target in left half
                }
            }
        }
        
        return -1;
    }
    
    // ============================
    // EXTENSION: Find maximum element
    // ============================
    
    /**
     * Find maximum element in rotated sorted array
     */
    public static int findMax(int[] arr) {
        int pivot = findPivot(arr);
        return arr[(pivot - 1 + arr.length) % arr.length];
    }
    
    // ============================
    // EXTENSION: Check if array is rotated
    // ============================
    
    /**
     * Check if array is rotated sorted
     */
    public static boolean isRotated(int[] arr) {
        if (arr.length <= 1) return false;
        return arr[0] > arr[arr.length - 1];
    }
    
    /**
     * Check if array is sorted (rotated or not)
     */
    public static boolean isSortedRotated(int[] arr) {
        if (arr.length <= 2) return true;
        
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > arr[(i + 1) % arr.length]) {
                count++;
            }
        }
        
        return count <= 1; // Sorted rotated array has at most 1 drop
    }
    
    // ============================
    // EXTENSION: Restore original array
    // ============================
    
    /**
     * Restore original sorted array from rotated version
     */
    public static int[] restoreArray(int[] arr) {
        int pivot = findPivot(arr);
        int n = arr.length;
        int[] result = new int[n];
        
        for (int i = 0; i < n; i++) {
            result[i] = arr[(i + pivot) % n];
        }
        
        return result;
    }
    
    // ============================
    // EXTENSION: Multiple rotations
    // ============================
    
    /**
     * Find minimum after k rotations (simulated)
     */
    public static int findMinAfterKRotations(int[] arr, int k) {
        k = k % arr.length;
        if (k == 0) return arr[0];
        
        // Create rotated array
        int[] rotated = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            rotated[i] = arr[(i + k) % arr.length];
        }
        
        return findMin(rotated);
    }
    
    /**
     * Find k that gives minimum first element after rotation
     */
    public static int findKForMinFirstElement(int[] arr) {
        int minIndex = findPivot(arr);
        return (arr.length - minIndex) % arr.length;
    }
    
    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        System.out.println("=== Find Minimum in Rotated Sorted Array ===");
        
        // Test Case 1: Basic rotated array
        System.out.println("\nTest Case 1: Basic Rotated Array");
        int[] arr1 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Minimum: " + findMin(arr1));
        System.out.println("Expected: 0");
        
        // Test different approaches
        System.out.println("\nDifferent Approaches:");
        System.out.println("Standard: " + findMin(arr1));
        System.out.println("Standard (LeetCode): " + findMinStandard(arr1));
        System.out.println("With Pivot: " + findMinWithPivot(arr1));
        System.out.println("Iterative: " + findMinIterative(arr1));
        System.out.println("Recursive: " + findMinRecursive(arr1));
        
        // Test Case 2: Not rotated
        System.out.println("\nTest Case 2: Not Rotated");
        int[] arr2 = {1, 2, 3, 4, 5};
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("Minimum: " + findMin(arr2));
        System.out.println("Expected: 1");
        
        // Test Case 3: Rotated at different positions
        System.out.println("\nTest Case 3: Different Rotation Points");
        int[][] testArrays = {
            {3, 4, 5, 1, 2},        // Rotated at index 3
            {2, 3, 4, 5, 1},        // Rotated at index 4
            {5, 1, 2, 3, 4},        // Rotated at index 1
            {1},                    // Single element
            {2, 1},                 // Two elements
            {3, 1, 2}               // Three elements
        };
        
        for (int[] arr : testArrays) {
            System.out.println("Array: " + Arrays.toString(arr) + 
                             " -> Minimum: " + findMin(arr));
        }
        
        // Test Case 4: With duplicates
        System.out.println("\nTest Case 4: With Duplicates");
        int[] arr4 = {2, 2, 2, 0, 1, 2};
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("Minimum: " + findMinWithDuplicates(arr4));
        System.out.println("Expected: 0");
        
        // Test Case 5: All same elements
        System.out.println("\nTest Case 5: All Same Elements");
        int[] arr5 = {3, 3, 3, 3, 3};
        System.out.println("Array: " + Arrays.toString(arr5));
        System.out.println("Minimum: " + findMinWithDuplicates(arr5));
        System.out.println("Expected: 3");
        
        // Test rotation count
        System.out.println("\n=== Testing Rotation Count ===");
        int[] arr6 = {4, 5, 6, 7, 0, 1, 2};
        int rotationCount = findRotationCount(arr6);
        System.out.println("Array: " + Arrays.toString(arr6));
        System.out.println("Rotation count: " + rotationCount);
        System.out.println("Expected: 4 (minimum at index 4)");
        
        // Test search in rotated array
        System.out.println("\n=== Testing Search in Rotated Array ===");
        int[] arr7 = {4, 5, 6, 7, 0, 1, 2};
        int target = 0;
        int searchResult = searchInRotatedArray(arr7, target);
        System.out.println("Array: " + Arrays.toString(arr7));
        System.out.println("Target: " + target);
        System.out.println("Found at index: " + searchResult);
        System.out.println("Expected: 4");
        
        // Test find maximum
        System.out.println("\n=== Testing Find Maximum ===");
        int[] arr8 = {4, 5, 6, 7, 0, 1, 2};
        int max = findMax(arr8);
        System.out.println("Array: " + Arrays.toString(arr8));
        System.out.println("Maximum: " + max);
        System.out.println("Expected: 7");
        
        // Test isRotated and isSortedRotated
        System.out.println("\n=== Testing Array Properties ===");
        int[][] propertyTests = {
            {1, 2, 3, 4, 5},      // Not rotated
            {3, 4, 5, 1, 2},      // Rotated
            {1},                   // Single
            {2, 1},                // Two elements
            {1, 2, 1},             // Invalid (not sorted rotated)
            {3, 2, 1}              // Invalid
        };
        
        for (int[] arr : propertyTests) {
            System.out.println("Array: " + Arrays.toString(arr));
            System.out.println("  isRotated: " + isRotated(arr));
            System.out.println("  isSortedRotated: " + isSortedRotated(arr));
        }
        
        // Test restore array
        System.out.println("\n=== Testing Restore Array ===");
        int[] arr9 = {4, 5, 6, 7, 0, 1, 2};
        int[] restored = restoreArray(arr9);
        System.out.println("Rotated: " + Arrays.toString(arr9));
        System.out.println("Restored: " + Arrays.toString(restored));
        
        // Test multiple rotations
        System.out.println("\n=== Testing Multiple Rotations ===");
        int[] arr10 = {0, 1, 2, 3, 4};
        for (int k = 0; k < 5; k++) {
            int minAfterK = findMinAfterKRotations(arr10, k);
            System.out.println("After " + k + " rotations, minimum: " + minAfterK);
        }
        
        // Test find K for minimum first element
        System.out.println("\n=== Testing Find K for Minimum First Element ===");
        int[] arr11 = {4, 5, 6, 7, 0, 1, 2};
        int k = findKForMinFirstElement(arr11);
        System.out.println("Array: " + Arrays.toString(arr11));
        System.out.println("Rotate by " + k + " to get minimum as first element");
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[] perfArray = new int[10000000];
        for (int i = 0; i < perfArray.length; i++) {
            perfArray[i] = (i + 5000000) % perfArray.length;
        }
        
        long startTime = System.currentTimeMillis();
        int perfResult = findMin(perfArray);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Array size: " + perfArray.length);
        System.out.println("Minimum: " + perfResult);
        System.out.println("Time: " + (endTime - startTime) + "ms");
        
        // Edge cases visualization
        System.out.println("\n=== Edge Cases Visualization ===");
        visualizeFindMin(arr1, 0);
        
        // Random tests validation
        System.out.println("\n=== Random Tests Validation ===");
        Random rand = new Random();
        int numTests = 1000;
        int passed = 0;
        
        for (int i = 0; i < numTests; i++) {
            // Generate sorted array
            int size = rand.nextInt(100) + 1;
            int[] sorted = new int[size];
            for (int j = 0; j < size; j++) {
                sorted[j] = rand.nextInt(1000);
            }
            Arrays.sort(sorted);
            
            // Rotate array
            int rotation = rand.nextInt(size);
            int[] rotated = new int[size];
            for (int j = 0; j < size; j++) {
                rotated[j] = sorted[(j + rotation) % size];
            }
            
            // Get binary search result
            int bsResult = findMin(rotated);
            
            // Get linear search result (ground truth)
            int linearResult = Integer.MAX_VALUE;
            for (int num : rotated) {
                linearResult = Math.min(linearResult, num);
            }
            
            if (bsResult == linearResult) {
                passed++;
            } else {
                System.out.println("Test failed!");
                System.out.println("Original sorted: " + Arrays.toString(sorted));
                System.out.println("Rotated by " + rotation + ": " + Arrays.toString(rotated));
                System.out.println("Binary Search: " + bsResult);
                System.out.println("Linear Search: " + linearResult);
                break;
            }
        }
        
        System.out.println("Passed: " + passed + "/" + numTests);
    }
    
    // ============================
    // DEBUGGING AND VISUALIZATION
    // ============================
    
    /**
     * Visualize the find minimum process
     */
    public static void visualizeFindMin(int[] arr, int expectedMin) {
        System.out.println("\n=== Visualizing Find Minimum ===");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Expected minimum: " + expectedMin);
        System.out.println("Length: " + arr.length);
        
        int low = 0;
        int high = arr.length - 1;
        int ans = Integer.MAX_VALUE;
        int step = 1;
        
        while (low <= high) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  Search range: [" + low + ", " + high + "]");
            System.out.println("  Current ans: " + (ans == Integer.MAX_VALUE ? "∞" : ans));
            
            int mid = low + (high - low) / 2;
            System.out.println("  Mid index: " + mid + ", value: " + arr[mid]);
            
            if (arr[low] <= arr[mid]) {
                // Left half is sorted
                System.out.println("  Left half [" + low + ".." + mid + "] is sorted");
                System.out.println("  Minimum in left half is arr[" + low + "] = " + arr[low]);
                ans = Math.min(ans, arr[low]);
                System.out.println("  Updated ans: " + ans);
                low = mid + 1;
                System.out.println("  Moving to right half, new low: " + low);
            } else {
                // Right half is sorted
                System.out.println("  Right half [" + mid + ".." + high + "] is sorted");
                System.out.println("  Minimum in right half is arr[" + mid + "] = " + arr[mid]);
                ans = Math.min(ans, arr[mid]);
                System.out.println("  Updated ans: " + ans);
                high = mid - 1;
                System.out.println("  Moving to left half, new high: " + high);
            }
            step++;
        }
        
        System.out.println("\nFinal minimum: " + ans);
        System.out.println("Correct: " + (ans == expectedMin));
    }
    
    // ============================
    // ADDITIONAL UTILITIES
    // ============================
    
    /**
     * Validate that array is rotated sorted
     */
    public static boolean validateRotatedSorted(int[] arr) {
        if (arr.length <= 1) return true;
        
        int dropCount = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > arr[(i + 1) % arr.length]) {
                dropCount++;
            }
        }
        
        return dropCount == 1; // Exactly one drop for rotated sorted
    }
    
    /**
     * Generate rotated sorted array
     */
    public static int[] generateRotatedSorted(int n, int rotation) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        
        // Rotate array
        rotation = rotation % n;
        int[] rotated = new int[n];
        for (int i = 0; i < n; i++) {
            rotated[i] = arr[(i + rotation) % n];
        }
        
        return rotated;
    }
    
    /**
     * Find all minimum elements (for arrays with duplicates)
     */
    public static List<Integer> findAllMinima(int[] arr) {
        int minValue = findMinWithDuplicates(arr);
        List<Integer> indices = new ArrayList<>();
        
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == minValue) {
                indices.add(i);
            }
        }
        
        return indices;
    }
    
    /**
     * Find minimum in circular array (wraps around)
     */
    public static int findMinCircular(int[] arr) {
        // For circular array, minimum is just the minimum element
        int min = Integer.MAX_VALUE;
        for (int num : arr) {
            min = Math.min(min, num);
        }
        return min;
    }
}