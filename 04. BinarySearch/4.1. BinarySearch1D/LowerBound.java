import java.util.*;
import java.util.function.Predicate;

/** 
 * Problem: Find Lower Bound in Sorted Array
 * 
 * Lower Bound: First index where arr[i] >= target
 * If no such element exists, return array length (insertion point)
 * 
 * Applications: Range queries, insertion points, binary search variations
 */
public class LowerBound {

    // ============================
    // APPROACH 1: Standard Implementation (Original)
    // ============================
    
    /**
     * Returns index of first element >= x
     * If all elements < x, returns n
     * 
     * @param arr Sorted array in non-decreasing order
     * @param n   Size of array
     * @param x   Target value
     * @return Lower bound index
     * 
     * Time: O(log n), Space: O(1)
     */
    public static int lowerBound(int[] arr, int n, int x) {
        int low = 0;
        int high = n - 1;
        int answer = n; // Default: insert at end

        while (low <= high) {
            // Prevent integer overflow
            int mid = low + (high - low) / 2;
            
            if (arr[mid] >= x) {
                answer = mid;      // Found candidate
                high = mid - 1;    // Search left for earlier
            } else {
                low = mid + 1;     // Search right
            }
        }
        
        return answer;
    }
    
    // ============================
    // APPROACH 2: Alternative Implementation
    // ============================
    
    /**
     * Alternative: Using exclusive high boundary
     */
    public static int lowerBoundAlt(int[] arr, int n, int x) {
        int low = 0;
        int high = n; // Exclusive boundary
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] >= x) {
                high = mid; // Search left, including mid
            } else {
                low = mid + 1; // Search right
            }
        }
        
        return low; // When low == high, we found lower bound
    }
    
    // ============================
    // APPROACH 3: Recursive Implementation
    // ============================
    
    /**
     * Recursive version
     */
    public static int lowerBoundRecursive(int[] arr, int n, int x) {
        return lowerBoundRecursiveHelper(arr, x, 0, n - 1, n);
    }
    
    private static int lowerBoundRecursiveHelper(int[] arr, int x, int low, int high, int answer) {
        if (low > high) {
            return answer;
        }
        
        int mid = low + (high - low) / 2;
        
        if (arr[mid] >= x) {
            // Search left with updated answer
            return lowerBoundRecursiveHelper(arr, x, low, mid - 1, mid);
        } else {
            // Search right
            return lowerBoundRecursiveHelper(arr, x, mid + 1, high, answer);
        }
    }
    
    // ============================
    // APPROACH 4: Using Java's Collections.binarySearch
    // ============================
    
    /**
     * Using Collections.binarySearch for List<Integer>
     */
    public static int lowerBoundList(List<Integer> list, int x) {
        int index = Collections.binarySearch(list, x);
        
        if (index >= 0) {
            // Element found, need to find first occurrence
            while (index > 0 && list.get(index - 1) == x) {
                index--;
            }
            return index;
        } else {
            // Element not found, insertion point is -index - 1
            return -index - 1;
        }
    }
    
    // ============================
    // VARIATIONS AND EXTENSIONS
    // ============================
    
    /**
     * Upper Bound: First index where arr[i] > x
     */
    public static int upperBound(int[] arr, int n, int x) {
        int low = 0;
        int high = n - 1;
        int answer = n;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] > x) {
                answer = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return answer;
    }
    
    /**
     * Strict Lower Bound: First index where arr[i] > x
     * (Different from upper bound - this is for elements strictly greater)
     */
    public static int strictLowerBound(int[] arr, int n, int x) {
        return upperBound(arr, n, x);
    }
    
    /**
     * Find range [lowerBound, upperBound) for element x
     */
    public static int[] findRange(int[] arr, int n, int x) {
        int lower = lowerBound(arr, n, x);
        int upper = upperBound(arr, n, x);
        return new int[]{lower, upper};
    }
    
    /**
     * Check if element exists using lower bound
     */
    public static boolean exists(int[] arr, int n, int x) {
        int idx = lowerBound(arr, n, x);
        return idx < n && arr[idx] == x;
    }
    
    // ============================
    // EXTENSION: Lower Bound with Custom Comparator
    // ============================
    
    /**
     * Generic lower bound with comparator
     */
    public static <T> int lowerBoundGeneric(T[] arr, T x, Comparator<T> comparator) {
        int low = 0;
        int high = arr.length - 1;
        int answer = arr.length;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = comparator.compare(arr[mid], x);
            
            if (cmp >= 0) { // arr[mid] >= x
                answer = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return answer;
    }
    
    // ============================
    // EXTENSION: Lower Bound for Real Numbers
    // ============================
    
    /**
     * Lower bound for floating point numbers with tolerance
     */
    public static int lowerBoundReal(double[] arr, double x, double tolerance) {
        int low = 0;
        int high = arr.length - 1;
        int answer = arr.length;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] >= x - tolerance) {
                answer = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return answer;
    }
    
    // ============================
    // EXTENSION: Lower Bound in 2D Array
    // ============================
    
    /**
     * Lower bound in 2D array (row-wise sorted)
     */
    public static int[] lowerBound2D(int[][] matrix, int x) {
        if (matrix == null || matrix.length == 0) {
            return new int[]{0, 0};
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Treat as 1D array
        int low = 0;
        int high = rows * cols - 1;
        int answer = rows * cols;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midValue = matrix[mid / cols][mid % cols];
            
            if (midValue >= x) {
                answer = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return new int[]{answer / cols, answer % cols};
    }
    
    // ============================
    // EXTENSION: Lower Bound with Predicate
    // ============================
    
    /**
     * Find first index where predicate is true
     * Predicate should be monotonic: false, false, ..., true, true, ...
     */
    public static int lowerBoundPredicate(int[] arr, Predicate<Integer> predicate) {
        int low = 0;
        int high = arr.length - 1;
        int answer = arr.length;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (predicate.test(arr[mid])) {
                answer = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return answer;
    }
    
    // ============================
    // EXTENSION: Binary Search on Answer using Lower Bound
    // ============================
    
    /**
     * Example: Find smallest number whose square is >= target
     */
    public static int sqrtLowerBound(int target) {
        if (target < 0) return -1;
        if (target <= 1) return target;
        
        int low = 0;
        int high = target;
        int answer = target;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            long square = (long) mid * mid;
            
            if (square >= target) {
                answer = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return answer;
    }
    
    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        System.out.println("=== Testing Lower Bound ===");
        
        // Test Case 1: Basic example
        System.out.println("\nTest Case 1: Basic Example");
        int[] arr1 = {1, 2, 4, 4, 5};
        int x1 = 4;
        int result1 = lowerBound(arr1, arr1.length, x1);
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("x = " + x1);
        System.out.println("Lower bound: " + result1);
        System.out.println("Expected: 2 (first occurrence of 4)");
        
        // Test different approaches
        System.out.println("\nDifferent Approaches:");
        System.out.println("Standard: " + result1);
        System.out.println("Alternative: " + lowerBoundAlt(arr1, arr1.length, x1));
        System.out.println("Recursive: " + lowerBoundRecursive(arr1, arr1.length, x1));
        
        // Test Case 2: Element not present
        System.out.println("\nTest Case 2: Element Not Present");
        int x2 = 3;
        int result2 = lowerBound(arr1, arr1.length, x2);
        System.out.println("x = " + x2);
        System.out.println("Lower bound: " + result2);
        System.out.println("Expected: 2 (first element >= 3 is 4 at index 2)");
        
        // Test Case 3: Element smaller than all
        System.out.println("\nTest Case 3: Element Smaller Than All");
        int x3 = 0;
        int result3 = lowerBound(arr1, arr1.length, x3);
        System.out.println("x = " + x3);
        System.out.println("Lower bound: " + result3);
        System.out.println("Expected: 0");
        
        // Test Case 4: Element larger than all
        System.out.println("\nTest Case 4: Element Larger Than All");
        int x4 = 6;
        int result4 = lowerBound(arr1, arr1.length, x4);
        System.out.println("x = " + x4);
        System.out.println("Lower bound: " + result4);
        System.out.println("Expected: 5 (array length)");
        
        // Test Case 5: Empty array
        System.out.println("\nTest Case 5: Empty Array");
        int[] arr5 = {};
        int x5 = 5;
        int result5 = lowerBound(arr5, arr5.length, x5);
        System.out.println("Array: []");
        System.out.println("x = " + x5);
        System.out.println("Lower bound: " + result5);
        System.out.println("Expected: 0");
        
        // Test Case 6: Single element array
        System.out.println("\nTest Case 6: Single Element Array");
        int[] arr6 = {5};
        int[] testValues6 = {3, 5, 7};
        for (int val : testValues6) {
            System.out.println("x = " + val + " -> Lower bound: " + 
                             lowerBound(arr6, arr6.length, val));
        }
        
        // Test Upper Bound
        System.out.println("\n=== Testing Upper Bound ===");
        int[] arr7 = {1, 2, 2, 2, 3, 4};
        int x7 = 2;
        int upper = upperBound(arr7, arr7.length, x7);
        System.out.println("Array: " + Arrays.toString(arr7));
        System.out.println("x = " + x7);
        System.out.println("Upper bound: " + upper);
        System.out.println("Expected: 4 (first element > 2 is 3 at index 4)");
        
        // Test Find Range
        System.out.println("\n=== Testing Find Range ===");
        int[] range = findRange(arr7, arr7.length, x7);
        System.out.println("Range for " + x7 + ": [" + range[0] + ", " + range[1] + ")");
        System.out.println("Count of " + x7 + ": " + (range[1] - range[0]));
        
        // Test Exists function
        System.out.println("\n=== Testing Exists Function ===");
        System.out.println("Does 2 exist? " + exists(arr7, arr7.length, 2));
        System.out.println("Does 5 exist? " + exists(arr7, arr7.length, 5));
        
        // Test Generic Lower Bound
        System.out.println("\n=== Testing Generic Lower Bound ===");
        String[] strArr = {"apple", "banana", "cherry", "date", "fig"};
        String targetStr = "cherry";
        Comparator<String> stringComparator = String::compareTo;
        int strLowerBound = lowerBoundGeneric(strArr, targetStr, stringComparator);
        System.out.println("String array: " + Arrays.toString(strArr));
        System.out.println("Target: " + targetStr);
        System.out.println("Lower bound: " + strLowerBound);
        
        // Test Lower Bound for Real Numbers
        System.out.println("\n=== Testing Lower Bound for Real Numbers ===");
        double[] realArr = {1.1, 2.2, 3.3, 4.4, 5.5};
        double targetReal = 3.0;
        double tolerance = 0.1;
        int realLowerBound = lowerBoundReal(realArr, targetReal, tolerance);
        System.out.println("Real array: " + Arrays.toString(realArr));
        System.out.println("Target: " + targetReal + " (±" + tolerance + ")");
        System.out.println("Lower bound: " + realLowerBound);
        
        // Test 2D Lower Bound
        System.out.println("\n=== Testing 2D Lower Bound ===");
        int[][] matrix = {
            {1, 3, 5},
            {7, 9, 11},
            {13, 15, 17}
        };
        int target2D = 10;
        int[] pos2D = lowerBound2D(matrix, target2D);
        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("Target: " + target2D);
        System.out.println("Lower bound position: [" + pos2D[0] + ", " + pos2D[1] + "]");
        if (pos2D[0] < matrix.length && pos2D[1] < matrix[0].length) {
            System.out.println("Value at position: " + matrix[pos2D[0]][pos2D[1]]);
        }
        
        // Test Lower Bound with Predicate
        System.out.println("\n=== Testing Lower Bound with Predicate ===");
        int[] predArr = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        // Find first number >= 5
        Predicate<Integer> predicate = num -> num >= 5;
        int predResult = lowerBoundPredicate(predArr, predicate);
        System.out.println("Array: " + Arrays.toString(predArr));
        System.out.println("First element >= 5 at index: " + predResult);
        
        // Test Sqrt Lower Bound
        System.out.println("\n=== Testing Sqrt Lower Bound ===");
        int[] sqrtTargets = {0, 1, 4, 8, 16, 25, 30};
        for (int target : sqrtTargets) {
            int sqrt = sqrtLowerBound(target);
            System.out.println("sqrt(" + target + ") >= " + sqrt + " (actual sqrt: " + 
                             Math.sqrt(target) + ")");
        }
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[] perfArray = new int[10000000];
        for (int i = 0; i < perfArray.length; i++) {
            perfArray[i] = i * 2; // Even numbers
        }
        int perfTarget = 7500000;
        
        long startTime = System.currentTimeMillis();
        int perfResult = lowerBound(perfArray, perfArray.length, perfTarget);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Array size: " + perfArray.length);
        System.out.println("Target: " + perfTarget);
        System.out.println("Lower bound: " + perfResult);
        System.out.println("Time: " + (endTime - startTime) + "ms");
        
        // Edge cases comprehensive test
        System.out.println("\n=== Comprehensive Edge Cases ===");
        int[][] testArrays = {
            {},                    // Empty
            {5},                   // Single
            {1, 3, 5, 7},          // Odd numbers
            {2, 4, 6, 8},          // Even numbers
            {1, 1, 2, 2, 3, 3},    // Duplicates
            {1, 2, 3, 4, 5}        // Sequential
        };
        
        int[] testTargets = {0, 1, 3, 5, 6, 10};
        
        for (int[] testArr : testArrays) {
            System.out.println("\nArray: " + Arrays.toString(testArr));
            for (int target : testTargets) {
                if (target <= 6 || testArr.length == 0) {
                    int lb = lowerBound(testArr, testArr.length, target);
                    System.out.println("  Target " + target + " -> Lower bound: " + lb);
                }
            }
        }
        
        // Test with Collections
        System.out.println("\n=== Testing with Collections ===");
        List<Integer> list = Arrays.asList(1, 2, 2, 2, 3, 4, 4, 5);
        int listTarget = 2;
        int listLowerBound = lowerBoundList(list, listTarget);
        System.out.println("List: " + list);
        System.out.println("Target: " + listTarget);
        System.out.println("Lower bound: " + listLowerBound);
        
        // Validate algorithm correctness
        System.out.println("\n=== Validation with Random Tests ===");
        Random rand = new Random();
        int numTests = 1000;
        int passed = 0;
        
        for (int i = 0; i < numTests; i++) {
            // Generate random sorted array
            int size = rand.nextInt(100) + 1;
            int[] randomArr = new int[size];
            for (int j = 0; j < size; j++) {
                randomArr[j] = rand.nextInt(200);
            }
            Arrays.sort(randomArr);
            
            // Random target
            int randomTarget = rand.nextInt(300) - 50; // Wider range
            
            // Get binary search result
            int bsResult = lowerBound(randomArr, randomArr.length, randomTarget);
            
            // Get linear search result (ground truth)
            int linearResult = 0;
            while (linearResult < size && randomArr[linearResult] < randomTarget) {
                linearResult++;
            }
            
            if (bsResult == linearResult) {
                passed++;
            } else {
                System.out.println("Test failed!");
                System.out.println("Array: " + Arrays.toString(randomArr));
                System.out.println("Target: " + randomTarget);
                System.out.println("Binary Search: " + bsResult);
                System.out.println("Linear Search: " + linearResult);
                break;
            }
        }
        
        System.out.println("Passed: " + passed + "/" + numTests);
        
        // Visualization
        System.out.println("\n=== Visualization ===");
        int[] vizArr = {1, 3, 5, 7, 9, 11, 13, 15};
        int vizTarget = 8;
        visualizeLowerBound(vizArr, vizTarget);
    }
    
    // ============================
    // DEBUGGING AND VISUALIZATION
    // ============================
    
    /**
     * Visualize the lower bound search process
     */
    public static void visualizeLowerBound(int[] arr, int x) {
        System.out.println("\n=== Visualizing Lower Bound Search ===");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target: " + x);
        System.out.println("Length: " + arr.length);
        
        int low = 0;
        int high = arr.length - 1;
        int answer = arr.length;
        int step = 1;
        
        while (low <= high) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  Search range: [" + low + ", " + high + "]");
            
            int mid = low + (high - low) / 2;
            System.out.println("  Mid index: " + mid + ", value: " + arr[mid]);
            
            if (arr[mid] >= x) {
                System.out.println("  arr[" + mid + "] = " + arr[mid] + " >= " + x + 
                                 " → updating answer to " + mid);
                answer = mid;
                high = mid - 1;
                System.out.println("  New high: " + high);
            } else {
                System.out.println("  arr[" + mid + "] = " + arr[mid] + " < " + x);
                low = mid + 1;
                System.out.println("  New low: " + low);
            }
            step++;
        }
        
        System.out.println("\nFinal answer: " + answer);
        if (answer < arr.length) {
            System.out.println("First element >= " + x + " is " + arr[answer] + " at index " + answer);
        } else {
            System.out.println("No element >= " + x + ", would insert at end (index " + answer + ")");
        }
    }
    
    // ============================
    // ADDITIONAL UTILITIES
    // ============================
    
    /**
     * Check if lower bound result is correct
     */
    public static boolean validateLowerBound(int[] arr, int x, int result) {
        if (result < 0 || result > arr.length) {
            return false;
        }
        
        // All elements before result should be < x
        for (int i = 0; i < result; i++) {
            if (arr[i] >= x) {
                return false;
            }
        }
        
        // Element at result (if exists) should be >= x
        if (result < arr.length && arr[result] < x) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Find all valid lower bounds (for arrays with duplicates)
     */
    public static List<Integer> findAllLowerBounds(int[] arr, int x) {
        List<Integer> results = new ArrayList<>();
        int first = lowerBound(arr, arr.length, x);
        
        if (first < arr.length && arr[first] == x) {
            // If x exists, we can consider positions from first to last occurrence
            int last = first;
            while (last < arr.length && arr[last] == x) {
                last++;
            }
            
            // All positions from first to last are technically "lower bounds"
            for (int i = first; i <= last; i++) {
                results.add(i);
            }
        } else {
            // Only one valid lower bound
            results.add(first);
        }
        
        return results;
    }
    
    /**
     * Lower bound for circular array
     */
    public static int lowerBoundCircular(int[] arr, int x) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        int low = 0;
        int high = arr.length - 1;
        int answer = arr.length;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] >= x) {
                answer = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        // For circular, if not found in normal search, wrap around
        if (answer == arr.length) {
            // Check if we should wrap to beginning
            if (x <= arr[0]) {
                answer = 0;
            }
        }
        
        return answer;
    }
}