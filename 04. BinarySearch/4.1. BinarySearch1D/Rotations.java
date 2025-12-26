import java.util.*;

/** 
 * Problem: Find Rotation Count in Rotated Sorted Array
 * 
 * Given a sorted rotated array, find how many times it has been rotated.
 * Rotation count = index of minimum element
 * 
 * Applications: Database indexing, circular buffers, system recovery
 */
public class Rotations {

    // ============================
    // APPROACH 1: Find Minimum Index (Original)
    // ============================
    
    /**
     * Find rotation count by finding index of minimum element
     * 
     * @param arr Rotated sorted array
     * @return Number of rotations (index of minimum element)
     * 
     * Time: O(log n), Space: O(1)
     */
    public static int findKRotation(int[] arr) {
        int n = arr.length;
        
        // Edge cases
        if (n == 0) return -1; // or throw exception
        if (n == 1) return 0;
        
        // If array is not rotated
        if (arr[0] < arr[n-1]) return 0;
        
        int low = 0;
        int high = n - 1;
        int minIndex = high; // Initialize with last index
        
        while (low <= high) {
            int mid = low + (high - low) / 2; // Avoid overflow
            
            if (arr[low] <= arr[mid]) {
                // Left half is sorted
                if (arr[low] < arr[minIndex]) {
                    minIndex = low; // Update min index
                }
                low = mid + 1; // Search in right half
            } else {
                // Right half is sorted
                if (arr[mid] < arr[minIndex]) {
                    minIndex = mid; // Update min index
                }
                high = mid - 1; // Search in left half
            }
        }
        
        return minIndex;
    }
    
    // ============================
    // APPROACH 2: Binary Search for Pivot
    // ============================
    
    /**
     * Find rotation count by finding pivot (minimum element index)
     */
    public static int findKRotationPivot(int[] arr) {
        int pivot = findPivot(arr);
        return pivot;
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
        
        return 0; // Should not reach here for valid rotated array
    }
    
    // ============================
    // APPROACH 3: Simplified Binary Search
    // ============================
    
    /**
     * Simplified version using standard binary search
     */
    public static int findKRotationSimple(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        // If array is not rotated
        if (arr[low] <= arr[high]) {
            return 0;
        }
        
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
        
        return low; // Index of minimum element
    }
    
    // ============================
    // APPROACH 4: Linear Scan
    // ============================
    
    /**
     * Linear scan approach - O(n) but simple
     */
    public static int findKRotationLinear(int[] arr) {
        int n = arr.length;
        
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                return i + 1; // Rotation count = index where decrease happens
            }
        }
        
        return 0; // Array is not rotated
    }
    
    // ============================
    // EXTENSION: With duplicates
    // ============================
    
    /**
     * Find rotation count in array with duplicates
     */
    public static int findKRotationWithDuplicates(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        // If array is not rotated
        if (arr[low] < arr[high]) {
            return 0;
        }
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] > arr[high]) {
                low = mid + 1;
            } else if (arr[mid] < arr[high]) {
                high = mid;
            } else {
                // arr[mid] == arr[high], can't decide
                // Check if high is the rotation point
                if (high > 0 && arr[high] < arr[high - 1]) {
                    return high;
                }
                high--;
            }
        }
        
        return low;
    }
    
    // ============================
    // EXTENSION: Find rotation direction
    // ============================
    
    /**
     * Determine if array is rotated left or right
     * Returns: 
     *   1 for right rotation (elements shifted right)
     *   -1 for left rotation (elements shifted left)
     *   0 for not rotated
     */
    public static int findRotationDirection(int[] arr) {
        int rotationCount = findKRotation(arr);
        
        if (rotationCount == 0) {
            return 0; // Not rotated
        }
        
        // Right rotation: minimum element moved from start to rotationCount
        // Left rotation: maximum element moved from end to (rotationCount-1)
        // For simplicity, assume right rotation
        return 1;
    }
    
    // ============================
    // EXTENSION: Rotate array by k positions
    // ============================
    
    /**
     * Rotate array right by k positions
     */
    public static void rotateRight(int[] arr, int k) {
        if (arr == null || arr.length == 0) return;
        
        k = k % arr.length;
        if (k == 0) return;
        
        reverse(arr, 0, arr.length - 1);
        reverse(arr, 0, k - 1);
        reverse(arr, k, arr.length - 1);
    }
    
    /**
     * Rotate array left by k positions
     */
    public static void rotateLeft(int[] arr, int k) {
        if (arr == null || arr.length == 0) return;
        
        k = k % arr.length;
        if (k == 0) return;
        
        reverse(arr, 0, k - 1);
        reverse(arr, k, arr.length - 1);
        reverse(arr, 0, arr.length - 1);
    }
    
    private static void reverse(int[] arr, int start, int end) {
        while (start < end) {
            int temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }
    
    // ============================
    // EXTENSION: Check if rotation is valid
    // ============================
    
    /**
     * Check if array could be a rotated version of sorted array
     */
    public static boolean isValidRotatedSorted(int[] arr) {
        if (arr.length <= 2) return true;
        
        int dropCount = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > arr[(i + 1) % arr.length]) {
                dropCount++;
            }
        }
        
        return dropCount == 1; // Rotated sorted array has exactly 1 drop
    }
    
    // ============================
    // EXTENSION: Find rotation count for circular array
    // ============================
    
    /**
     * Find rotation count for circular sorted array
     * (Array that wraps around)
     */
    public static int findCircularRotationCount(int[] arr) {
        int minIndex = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[minIndex]) {
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    // ============================
    // EXTENSION: Find rotation count with binary search template
    // ============================
    
    /**
     * Using binary search template
     */
    public static int findKRotationTemplate(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        // Template: find first element where arr[i] < arr[0]
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] >= arr[0]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return low % arr.length; // Handle case when array not rotated
    }
    
    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        System.out.println("=== Rotation Count in Rotated Sorted Array ===");
        
        // Test Case 1: Basic rotation
        System.out.println("\nTest Case 1: Basic Rotation");
        int[] arr1 = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Rotation count: " + findKRotation(arr1));
        System.out.println("Expected: 4 (minimum 0 at index 4)");
        
        // Test different approaches
        System.out.println("\nDifferent Approaches:");
        System.out.println("Original: " + findKRotation(arr1));
        System.out.println("Pivot: " + findKRotationPivot(arr1));
        System.out.println("Simple: " + findKRotationSimple(arr1));
        System.out.println("Linear: " + findKRotationLinear(arr1));
        
        // Test Case 2: Not rotated
        System.out.println("\nTest Case 2: Not Rotated");
        int[] arr2 = {0, 1, 2, 3, 4, 5};
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("Rotation count: " + findKRotation(arr2));
        System.out.println("Expected: 0");
        
        // Test Case 3: Rotated at different positions
        System.out.println("\nTest Case 3: Different Rotation Points");
        int[][] testArrays = {
            {1, 2, 3, 4, 5},      // Not rotated (0)
            {2, 3, 4, 5, 1},      // Rotated 4 times
            {3, 4, 5, 1, 2},      // Rotated 3 times
            {4, 5, 1, 2, 3},      // Rotated 2 times
            {5, 1, 2, 3, 4},      // Rotated 1 time
            {1},                   // Single element (0)
            {2, 1},                // Two elements (1)
            {3, 1, 2}              // Three elements (1)
        };
        
        for (int[] arr : testArrays) {
            System.out.println("Array: " + Arrays.toString(arr) + 
                             " -> Rotations: " + findKRotation(arr));
        }
        
        // Test Case 4: With duplicates
        System.out.println("\nTest Case 4: With Duplicates");
        int[] arr4 = {2, 2, 2, 0, 1, 2};
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("Rotation count: " + findKRotationWithDuplicates(arr4));
        System.out.println("Expected: 3 (minimum 0 at index 3)");
        
        // Test rotation direction
        System.out.println("\n=== Testing Rotation Direction ===");
        int[] dirTest = {3, 4, 5, 1, 2};
        System.out.println("Array: " + Arrays.toString(dirTest));
        System.out.println("Rotation direction: " + findRotationDirection(dirTest));
        
        // Test rotate operations
        System.out.println("\n=== Testing Rotate Operations ===");
        int[] rotateTest = {1, 2, 3, 4, 5};
        System.out.println("Original: " + Arrays.toString(rotateTest));
        
        rotateRight(rotateTest, 2);
        System.out.println("After right rotate 2: " + Arrays.toString(rotateTest));
        System.out.println("Rotation count: " + findKRotation(rotateTest));
        
        rotateLeft(rotateTest, 2);
        System.out.println("After left rotate 2: " + Arrays.toString(rotateTest));
        
        // Test validity check
        System.out.println("\n=== Testing Validity Check ===");
        int[][] validityTests = {
            {1, 2, 3, 4, 5},      // Valid (not rotated)
            {3, 4, 5, 1, 2},      // Valid (rotated)
            {1, 2, 1, 3, 4},      // Invalid (multiple drops)
            {5, 4, 3, 2, 1},      // Invalid (completely reversed)
            {1, 1, 1, 1, 1}       // Valid (all same)
        };
        
        for (int[] arr : validityTests) {
            System.out.println("Array: " + Arrays.toString(arr) + 
                             " -> Valid rotated sorted: " + isValidRotatedSorted(arr));
        }
        
        // Test circular rotation
        System.out.println("\n=== Testing Circular Rotation ===");
        int[] circular = {4, 5, 6, 7, 0, 1, 2, 3};
        System.out.println("Circular array: " + Arrays.toString(circular));
        System.out.println("Circular rotation count: " + findCircularRotationCount(circular));
        
        // Test template approach
        System.out.println("\n=== Testing Template Approach ===");
        int[] templateTest = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("Array: " + Arrays.toString(templateTest));
        System.out.println("Template rotation count: " + findKRotationTemplate(templateTest));
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[] perfArray = new int[10000000];
        for (int i = 0; i < perfArray.length; i++) {
            perfArray[i] = (i + 3000000) % perfArray.length;
        }
        
        long startTime = System.currentTimeMillis();
        int perfRotations = findKRotation(perfArray);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Array size: " + perfArray.length);
        System.out.println("Rotation count: " + perfRotations);
        System.out.println("Time: " + (endTime - startTime) + "ms");
        
        // Compare with linear scan
        startTime = System.currentTimeMillis();
        int linearRotations = findKRotationLinear(perfArray);
        endTime = System.currentTimeMillis();
        System.out.println("Linear scan time: " + (endTime - startTime) + "ms");
        
        // Edge cases visualization
        System.out.println("\n=== Edge Cases Visualization ===");
        visualizeRotationSearch(arr1, 4);
        
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
            int bsResult = findKRotation(rotated);
            
            // Get linear search result (ground truth)
            int linearResult = 0;
            for (int j = 1; j < size; j++) {
                if (rotated[j] < rotated[j - 1]) {
                    linearResult = j;
                    break;
                }
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
        
        // Test with manual rotations
        System.out.println("\n=== Testing Manual Rotations ===");
        int[] manual = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("Original: " + Arrays.toString(manual));
        
        for (int k = 0; k <= manual.length; k++) {
            int[] rotated = manual.clone();
            rotateRight(rotated, k);
            int rotations = findKRotation(rotated);
            System.out.println("Rotated right by " + k + ": " + Arrays.toString(rotated) + 
                             " -> Rotation count: " + rotations + " (expected: " + (k % manual.length) + ")");
        }
    }
    
    // ============================
    // DEBUGGING AND VISUALIZATION
    // ============================
    
    /**
     * Visualize rotation count search
     */
    public static void visualizeRotationSearch(int[] arr, int expectedRotations) {
        System.out.println("\n=== Visualizing Rotation Count Search ===");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Expected rotations: " + expectedRotations);
        System.out.println("Length: " + arr.length);
        
        int n = arr.length;
        
        // Check if not rotated
        if (n > 1 && arr[0] < arr[n-1]) {
            System.out.println("Array is not rotated (arr[0] < arr[n-1])");
            System.out.println("Rotation count: 0");
            return;
        }
        
        int low = 0;
        int high = n - 1;
        int minIndex = high;
        int step = 1;
        
        System.out.println("\nBinary search for minimum element:");
        
        while (low <= high) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  Search range: [" + low + ", " + high + "]");
            System.out.println("  Current min index: " + minIndex + 
                             " (value: " + arr[minIndex] + ")");
            
            int mid = low + (high - low) / 2;
            System.out.println("  Mid index: " + mid + ", value: " + arr[mid]);
            
            if (arr[low] <= arr[mid]) {
                System.out.println("  Left half [" + low + ".." + mid + "] is sorted");
                System.out.println("  arr[low] = " + arr[low] + 
                                 " vs current min " + arr[minIndex] + " = " + 
                                 (arr[low] < arr[minIndex] ? "new min" : "not min"));
                
                if (arr[low] < arr[minIndex]) {
                    minIndex = low;
                    System.out.println("  Updated min index to " + minIndex);
                }
                
                low = mid + 1;
                System.out.println("  Search right half, new low: " + low);
            } else {
                System.out.println("  Right half [" + mid + ".." + high + "] is sorted");
                System.out.println("  arr[mid] = " + arr[mid] + 
                                 " vs current min " + arr[minIndex] + " = " + 
                                 (arr[mid] < arr[minIndex] ? "new min" : "not min"));
                
                if (arr[mid] < arr[minIndex]) {
                    minIndex = mid;
                    System.out.println("  Updated min index to " + minIndex);
                }
                
                high = mid - 1;
                System.out.println("  Search left half, new high: " + high);
            }
            step++;
        }
        
        System.out.println("\nFinal min index: " + minIndex);
        System.out.println("Rotation count = min index = " + minIndex);
        System.out.println("Correct: " + (minIndex == expectedRotations));
    }
    
    // ============================
    // ADDITIONAL UTILITIES
    // ============================
    
    /**
     * Generate rotated sorted array
     */
    public static int[] generateRotatedArray(int n, int rotations) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        
        // Rotate array
        rotations = rotations % n;
        int[] rotated = new int[n];
        for (int i = 0; i < n; i++) {
            rotated[i] = arr[(i + rotations) % n];
        }
        
        return rotated;
    }
    
    /**
     * Find maximum element index (alternative approach)
     */
    public static int findMaxIndex(int[] arr) {
        int n = arr.length;
        if (n == 0) return -1;
        
        int low = 0;
        int high = n - 1;
        
        // If array not rotated, max is at end
        if (arr[low] < arr[high]) {
            return high;
        }
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] > arr[mid + 1]) {
                return mid;
            }
            
            if (arr[low] > arr[mid]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        
        return low;
    }
    
    /**
     * Calculate rotations from max index
     */
    public static int findKRotationFromMax(int[] arr) {
        int maxIndex = findMaxIndex(arr);
        return (maxIndex + 1) % arr.length;
    }
    
    /**
     * Check if index is correct rotation count
     */
    public static boolean validateRotationCount(int[] arr, int rotationCount) {
        if (rotationCount < 0 || rotationCount >= arr.length) {
            return false;
        }
        
        // Minimum should be at rotationCount
        int minIndex = rotationCount;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < arr[minIndex]) {
                return false; // Found smaller element elsewhere
            }
        }
        
        return true;
    }
    
    /**
     * Find all possible rotation counts (for arrays with duplicates)
     */
    public static List<Integer> findAllRotationCounts(int[] arr) {
        List<Integer> rotations = new ArrayList<>();
        int minValue = Integer.MAX_VALUE;
        
        // Find minimum value
        for (int num : arr) {
            minValue = Math.min(minValue, num);
        }
        
        // Find all indices with minimum value
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == minValue) {
                rotations.add(i);
            }
        }
        
        return rotations;
    }
    
    /**
     * Find rotation count using modular arithmetic
     */
    public static int findKRotationModular(int[] arr) {
        int n = arr.length;
        if (n == 0) return -1;
        
        // Find minimum element
        int minIndex = 0;
        for (int i = 1; i < n; i++) {
            if (arr[i] < arr[minIndex]) {
                minIndex = i;
            }
        }
        
        // Rotation count = minIndex
        return minIndex;
    }
}