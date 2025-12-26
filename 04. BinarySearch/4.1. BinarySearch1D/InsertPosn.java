import java.util.*;

/** 
 * Problem: Find the best insert position in a sorted array
 * LeetCode 35 - Search Insert Position / Coding Ninjas equivalent
 * 
 * Given a sorted array of distinct integers and a target value,
 * return the index if the target is found. If not, return the index
 * where it would be if it were inserted in order.
 * 
 * The algorithm should run in O(log n) time.
 */
public class InsertPosn {

    // ============================
    // APPROACH 1: Standard Binary Search (Original)
    // ============================
    
    /**
     * Returns the index where x should be inserted to keep array sorted
     * 
     * @param arr Sorted array in non-decreasing order
     * @param x Value to insert
     * @return Insertion index (first index where arr[i] >= x)
     * 
     * Time: O(log n), Space: O(1)
     */
    public static int searchInsert(int[] arr, int x) {
        int n = arr.length;
        int low = 0;
        int high = n - 1;
        int ans = n;  // Default: insert at end if x > all elements
        
        while (low <= high) {
            // Prevent integer overflow
            int mid = low + (high - low) / 2;
            
            if (arr[mid] >= x) {
                ans = mid;      // Found candidate position
                high = mid - 1; // Search left for earlier position
            } else {
                low = mid + 1;  // Search right
            }
        }
        
        return ans;
    }
    
    // ============================
    // APPROACH 2: Using Standard Library
    // ============================
    
    /**
     * Using Arrays.binarySearch() - returns (-(insertion point) - 1) if not found
     */
    public static int searchInsertBuiltIn(int[] arr, int x) {
        int index = Arrays.binarySearch(arr, x);
        
        if (index >= 0) {
            // Element found
            return index;
        } else {
            // Element not found, calculate insertion point
            return -index - 1;
        }
    }
    
    // ============================
    // APPROACH 3: Simplified Binary Search
    // ============================
    
    /**
     * Simplified version without ans variable
     */
    public static int searchInsertSimple(int[] arr, int x) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == x) {
                return mid;
            } else if (arr[mid] < x) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        // When loop ends, low is the insertion point
        return low;
    }
    
    // ============================
    // APPROACH 4: Recursive Implementation
    // ============================
    
    /**
     * Recursive version
     */
    public static int searchInsertRecursive(int[] arr, int x) {
        return searchInsertRecursiveHelper(arr, x, 0, arr.length - 1);
    }
    
    private static int searchInsertRecursiveHelper(int[] arr, int x, int low, int high) {
        if (low > high) {
            return low; // Base case: insertion point is low
        }
        
        int mid = low + (high - low) / 2;
        
        if (arr[mid] == x) {
            return mid;
        } else if (arr[mid] < x) {
            return searchInsertRecursiveHelper(arr, x, mid + 1, high);
        } else {
            return searchInsertRecursiveHelper(arr, x, low, mid - 1);
        }
    }
    
    // ============================
    // EXTENSION: Insert position with duplicates
    // ============================
    
    /**
     * Find insert position in array with duplicates
     * If duplicates exist, returns first position where x can be inserted
     */
    public static int searchInsertWithDuplicates(int[] arr, int x) {
        int low = 0;
        int high = arr.length - 1;
        int result = arr.length;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] >= x) {
                result = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return result;
    }
    
    // ============================
    // EXTENSION: Insert position for multiple values
    // ============================
    
    /**
     * Find insert positions for multiple values
     */
    public static int[] searchInsertMultiple(int[] arr, int[] values) {
        int[] result = new int[values.length];
        
        for (int i = 0; i < values.length; i++) {
            result[i] = searchInsert(arr, values[i]);
        }
        
        return result;
    }
    
    /**
     * Optimized version for multiple values (sort values first)
     */
    public static int[] searchInsertMultipleOptimized(int[] arr, int[] values) {
        // Create copy of values with original indices
        int[][] indexedValues = new int[values.length][2];
        for (int i = 0; i < values.length; i++) {
            indexedValues[i][0] = values[i]; // value
            indexedValues[i][1] = i;         // original index
        }
        
        // Sort values for better cache locality
        Arrays.sort(indexedValues, (a, b) -> Integer.compare(a[0], b[0]));
        
        int[] result = new int[values.length];
        int arrIndex = 0;
        
        for (int i = 0; i < indexedValues.length; i++) {
            int value = indexedValues[i][0];
            int originalIndex = indexedValues[i][1];
            
            // Find insert position starting from where we left off
            while (arrIndex < arr.length && arr[arrIndex] < value) {
                arrIndex++;
            }
            
            result[originalIndex] = arrIndex;
        }
        
        return result;
    }
    
    // ============================
    // EXTENSION: Insert with custom comparator
    // ============================
    
    /**
     * Generic insert position with comparator
     */
    public static <T> int searchInsertGeneric(T[] arr, T x, Comparator<T> comparator) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = comparator.compare(arr[mid], x);
            
            if (cmp == 0) {
                return mid;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return low;
    }
    
    // ============================
    // EXTENSION: Insert in 2D sorted matrix
    // ============================
    
    /**
     * Insert position in row-wise and column-wise sorted matrix
     * Returns [row, col] where element should be inserted
     */
    public static int[] searchInsert2D(int[][] matrix, int x) {
        if (matrix == null || matrix.length == 0) {
            return new int[]{0, 0};
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Treat matrix as 1D array for binary search
        int low = 0;
        int high = rows * cols - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midValue = matrix[mid / cols][mid % cols];
            
            if (midValue == x) {
                return new int[]{mid / cols, mid % cols};
            } else if (midValue < x) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        // Convert 1D insertion point to 2D coordinates
        int insertPos = low;
        return new int[]{insertPos / cols, insertPos % cols};
    }
    
    // ============================
    // EXTENSION: Insert in circular sorted array
    // ============================
    
    /**
     * Insert position in circular sorted array
     */
    public static int searchInsertCircular(int[] arr, int x) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == x) {
                return mid;
            }
            
            // Check which half is sorted
            if (arr[low] <= arr[mid]) {
                // Left half is sorted
                if (arr[low] <= x && x < arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                // Right half is sorted
                if (arr[mid] < x && x <= arr[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        
        return low;
    }
    
    // ============================
    // EXTENSION: Insert with memory of previous searches
    // ============================
    
    /**
     * Data structure for efficient multiple insert position queries
     */
    public static class InsertPositionFinder {
        private int[] arr;
        private Map<Integer, Integer> cache; // Cache for repeated queries
        
        public InsertPositionFinder(int[] arr) {
            this.arr = arr;
            this.cache = new HashMap<>();
        }
        
        public int findInsertPosition(int x) {
            // Check cache first
            if (cache.containsKey(x)) {
                return cache.get(x);
            }
            
            int result = searchInsert(arr, x);
            cache.put(x, result);
            return result;
        }
        
        public void updateArray(int[] newArr) {
            this.arr = newArr;
            cache.clear(); // Clear cache when array changes
        }
    }
    
    // ============================
    // EXTENSION: Insert with constraints
    // ============================
    
    /**
     * Find insert position with constraint that array must remain sorted
     * and no duplicates allowed (if x exists, return its index)
     */
    public static int searchInsertNoDuplicates(int[] arr, int x) {
        int pos = searchInsert(arr, x);
        
        // Check if element already exists at insertion position
        if (pos < arr.length && arr[pos] == x) {
            return pos;
        }
        
        return pos;
    }
    
    /**
     * Find insert position where array must remain strictly increasing
     * (no duplicates allowed, insert only if not present)
     */
    public static int searchInsertStrictlyIncreasing(int[] arr, int x) {
        int pos = searchInsert(arr, x);
        
        // If element exists, don't insert
        if (pos < arr.length && arr[pos] == x) {
            return -1; // Or throw exception
        }
        
        return pos;
    }
    
    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        System.out.println("=== Testing Insert Position ===");
        
        // Test Case 1: Basic example
        System.out.println("\nTest Case 1: Basic Example");
        int[] arr1 = {1, 3, 5, 6};
        int x1 = 5;
        int result1 = searchInsert(arr1, x1);
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("x = " + x1);
        System.out.println("Insert position: " + result1);
        System.out.println("Expected: 2 (element exists)");
        
        // Test different approaches
        System.out.println("\nDifferent Approaches:");
        System.out.println("Standard: " + result1);
        System.out.println("Built-in: " + searchInsertBuiltIn(arr1, x1));
        System.out.println("Simple: " + searchInsertSimple(arr1, x1));
        System.out.println("Recursive: " + searchInsertRecursive(arr1, x1));
        
        // Test Case 2: Insert in middle
        System.out.println("\nTest Case 2: Insert in Middle");
        int x2 = 2;
        int result2 = searchInsert(arr1, x2);
        System.out.println("x = " + x2);
        System.out.println("Insert position: " + result2);
        System.out.println("Expected: 1 (between 1 and 3)");
        
        // Test Case 3: Insert at beginning
        System.out.println("\nTest Case 3: Insert at Beginning");
        int x3 = 0;
        int result3 = searchInsert(arr1, x3);
        System.out.println("x = " + x3);
        System.out.println("Insert position: " + result3);
        System.out.println("Expected: 0");
        
        // Test Case 4: Insert at end
        System.out.println("\nTest Case 4: Insert at End");
        int x4 = 7;
        int result4 = searchInsert(arr1, x4);
        System.out.println("x = " + x4);
        System.out.println("Insert position: " + result4);
        System.out.println("Expected: 4");
        
        // Test Case 5: Empty array
        System.out.println("\nTest Case 5: Empty Array");
        int[] arr5 = {};
        int x5 = 5;
        int result5 = searchInsert(arr5, x5);
        System.out.println("Array: []");
        System.out.println("x = " + x5);
        System.out.println("Insert position: " + result5);
        System.out.println("Expected: 0");
        
        // Test Case 6: Single element array
        System.out.println("\nTest Case 6: Single Element");
        int[] arr6 = {5};
        int[] testValues6 = {3, 5, 7};
        for (int val : testValues6) {
            System.out.println("x = " + val + " -> Position: " + searchInsert(arr6, val));
        }
        
        // Test Case 7: Array with duplicates
        System.out.println("\nTest Case 7: Array with Duplicates");
        int[] arr7 = {1, 2, 2, 2, 3, 4, 4, 5};
        int x7 = 2;
        int result7 = searchInsertWithDuplicates(arr7, x7);
        System.out.println("Array: " + Arrays.toString(arr7));
        System.out.println("x = " + x7);
        System.out.println("Insert position: " + result7);
        System.out.println("Expected: 1 (first occurrence of 2 is at index 1)");
        
        // Test Case 8: Multiple values insertion
        System.out.println("\nTest Case 8: Multiple Values");
        int[] arr8 = {10, 20, 30, 40, 50};
        int[] values8 = {5, 15, 25, 35, 45, 55};
        int[] positions8 = searchInsertMultiple(arr8, values8);
        
        System.out.println("Array: " + Arrays.toString(arr8));
        System.out.println("Values: " + Arrays.toString(values8));
        System.out.println("Positions: " + Arrays.toString(positions8));
        
        // Test optimized multiple values
        int[] positions8Opt = searchInsertMultipleOptimized(arr8, values8);
        System.out.println("Optimized positions: " + Arrays.toString(positions8Opt));
        
        // Test Case 9: Generic insert with comparator
        System.out.println("\nTest Case 9: Generic Insert with Comparator");
        String[] strArr = {"apple", "banana", "cherry", "date"};
        String target = "blueberry";
        Comparator<String> stringComparator = String::compareTo;
        int strPos = searchInsertGeneric(strArr, target, stringComparator);
        System.out.println("Array: " + Arrays.toString(strArr));
        System.out.println("Target: " + target);
        System.out.println("Insert position: " + strPos);
        
        // Test Case 10: 2D matrix insert
        System.out.println("\nTest Case 10: 2D Matrix Insert");
        int[][] matrix = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target2D = 13;
        int[] pos2D = searchInsert2D(matrix, target2D);
        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("Target: " + target2D);
        System.out.println("Insert position: [" + pos2D[0] + ", " + pos2D[1] + "]");
        
        // Test Case 11: Circular array insert
        System.out.println("\nTest Case 11: Circular Array Insert");
        int[] circular = {4, 5, 6, 7, 0, 1, 2};
        int targetCircular = 0;
        int posCircular = searchInsertCircular(circular, targetCircular);
        System.out.println("Circular array: " + Arrays.toString(circular));
        System.out.println("Target: " + targetCircular);
        System.out.println("Insert position: " + posCircular);
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[] perfArray = new int[10000000];
        for (int i = 0; i < perfArray.length; i++) {
            perfArray[i] = i * 2; // Even numbers
        }
        int perfTarget = 7500000;
        
        long startTime = System.currentTimeMillis();
        int perfResult = searchInsert(perfArray, perfTarget);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Array size: " + perfArray.length);
        System.out.println("Target: " + perfTarget);
        System.out.println("Insert position: " + perfResult);
        System.out.println("Time: " + (endTime - startTime) + "ms");
        
        // Test InsertPositionFinder
        System.out.println("\n=== Test InsertPositionFinder (Caching) ===");
        InsertPositionFinder finder = new InsertPositionFinder(arr1);
        System.out.println("Array: " + Arrays.toString(arr1));
        
        int[] queries = {0, 2, 5, 7, 2, 5}; // Repeated queries
        for (int query : queries) {
            System.out.println("Query " + query + " -> Position: " + finder.findInsertPosition(query));
        }
        
        // Edge cases visualization
        System.out.println("\n=== Edge Cases Visualization ===");
        int[][] testArrays = {
            {},                    // Empty
            {5},                   // Single
            {1, 3, 5, 7},          // No target
            {1, 2, 3, 4, 5},       // Target exists
            {1, 1, 2, 2, 3, 3}     // Duplicates
        };
        
        int[] testTargets = {0, 3, 6, 1, 2};
        
        for (int[] testArr : testArrays) {
            System.out.println("\nArray: " + Arrays.toString(testArr));
            for (int targetVal : testTargets) {
                int pos = searchInsert(testArr, targetVal);
                System.out.println("  Target " + targetVal + " -> Position: " + pos);
            }
        }
        
        // Validate with random tests
        System.out.println("\n=== Random Test Validation ===");
        Random rand = new Random();
        int numTests = 1000;
        int passed = 0;
        
        for (int i = 0; i < numTests; i++) {
            // Generate random sorted array
            int size = rand.nextInt(100) + 1;
            int[] randomArr = new int[size];
            for (int j = 0; j < size; j++) {
                randomArr[j] = rand.nextInt(200) - 100; // Range -100 to 100
            }
            Arrays.sort(randomArr);
            
            // Random target
            int randomTarget = rand.nextInt(300) - 150; // Wider range
            
            // Get binary search result
            int bsResult = searchInsert(randomArr, randomTarget);
            
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
    }
    
    // ============================
    // DEBUGGING AND VISUALIZATION
    // ============================
    
    /**
     * Visualize binary search process for insert position
     */
    public static void visualizeInsertSearch(int[] arr, int x) {
        System.out.println("\n=== Visualizing Insert Position Search ===");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target: " + x);
        System.out.println("Length: " + arr.length);
        
        int low = 0;
        int high = arr.length - 1;
        int ans = arr.length;
        int step = 1;
        
        while (low <= high) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  Search range: [" + low + ", " + high + "]");
            
            int mid = low + (high - low) / 2;
            System.out.println("  Mid index: " + mid + ", value: " + 
                             (mid >= 0 && mid < arr.length ? arr[mid] : "N/A"));
            
            if (arr[mid] >= x) {
                System.out.println("  arr[" + mid + "] = " + arr[mid] + " >= " + x + 
                                 " → updating ans to " + mid);
                ans = mid;
                high = mid - 1;
                System.out.println("  New high: " + high);
            } else {
                System.out.println("  arr[" + mid + "] = " + arr[mid] + " < " + x);
                low = mid + 1;
                System.out.println("  New low: " + low);
            }
            step++;
        }
        
        System.out.println("\nFinal result: " + ans);
        if (ans < arr.length) {
            System.out.println("Will insert at index " + ans + 
                             " (before element " + arr[ans] + ")");
        } else {
            System.out.println("Will insert at end of array");
        }
    }
    
    // ============================
    // ADDITIONAL UTILITIES
    // ============================
    
    /**
     * Insert element into array at correct position
     * Returns new array with element inserted
     */
    public static int[] insertElement(int[] arr, int x) {
        int insertPos = searchInsert(arr, x);
        int[] result = new int[arr.length + 1];
        
        // Copy elements before insertion point
        System.arraycopy(arr, 0, result, 0, insertPos);
        
        // Insert new element
        result[insertPos] = x;
        
        // Copy elements after insertion point
        System.arraycopy(arr, insertPos, result, insertPos + 1, arr.length - insertPos);
        
        return result;
    }
    
    /**
     * Check if array remains sorted after insertion
     */
    public static boolean validateInsertion(int[] arr, int x, int insertPos) {
        // Create new array with insertion
        int[] newArr = new int[arr.length + 1];
        System.arraycopy(arr, 0, newArr, 0, insertPos);
        newArr[insertPos] = x;
        System.arraycopy(arr, insertPos, newArr, insertPos + 1, arr.length - insertPos);
        
        // Check if sorted
        for (int i = 1; i < newArr.length; i++) {
            if (newArr[i] < newArr[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Find all possible insertion positions that keep array sorted
     * (for arrays with duplicates)
     */
    public static List<Integer> findAllInsertPositions(int[] arr, int x) {
        List<Integer> positions = new ArrayList<>();
        
        // Find first position where arr[i] >= x
        int firstPos = searchInsert(arr, x);
        
        // If x exists in array, we can insert before or after duplicates
        if (firstPos < arr.length && arr[firstPos] == x) {
            // Can insert before first occurrence
            positions.add(firstPos);
            
            // Find last occurrence of x
            int lastPos = firstPos;
            while (lastPos < arr.length && arr[lastPos] == x) {
                lastPos++;
            }
            
            // Can insert after last occurrence
            positions.add(lastPos);
        } else {
            // x doesn't exist, only one valid position
            positions.add(firstPos);
        }
        
        return positions;
    }
}