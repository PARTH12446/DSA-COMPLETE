import java.util.*;

/** 
 * Problem: Count occurrences of X in a sorted array
 * 
 * Key Insight: Use binary search to find first and last occurrence
 * Count = (last - first + 1) if element exists, else 0
 * 
 * Applications: Frequency counting, data analysis, search optimization
 */
public class CountOcc {

    // ============================
    // APPROACH 1: Two Binary Searches (Original)
    // ============================
    
    /**
     * Count occurrences using first and last position
     * Time: O(log n), Space: O(1)
     */
    public static int count(int[] arr, int n, int x) {
        int[] bounds = firstAndLastPosition(arr, n, x);
        // Check if element not found
        if (bounds[0] == -1) {
            return 0;
        }
        return bounds[1] - bounds[0] + 1;
    }

    /**
     * Helper: Find first and last occurrence of k
     * Returns [-1, -1] if not found
     */
    private static int[] firstAndLastPosition(int[] arr, int n, int k) {
        // Find first occurrence (lower bound)
        int first = findFirst(arr, n, k);
        if (first == -1) {
            return new int[]{-1, -1}; // Element not found
        }
        
        // Find last occurrence (upper bound - 1)
        int last = findLast(arr, n, k);
        return new int[]{first, last};
    }
    
    /**
     * Find first occurrence (lower bound)
     */
    private static int findFirst(int[] arr, int n, int k) {
        int low = 0, high = n - 1;
        int result = -1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2; // Avoid overflow
            
            if (arr[mid] == k) {
                result = mid;        // Found a candidate
                high = mid - 1;      // Search left for earlier occurrence
            } else if (arr[mid] < k) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Find last occurrence
     */
    private static int findLast(int[] arr, int n, int k) {
        int low = 0, high = n - 1;
        int result = -1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == k) {
                result = mid;        // Found a candidate
                low = mid + 1;       // Search right for later occurrence
            } else if (arr[mid] < k) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return result;
    }
    
    // ============================
    // APPROACH 2: Using Standard Library (Java 8+)
    // ============================
    
    /**
     * Using Arrays.binarySearch() and linear expansion
     * Time: O(log n + count) - good when count is small
     */
    public static int countUsingStandardLib(int[] arr, int n, int x) {
        int index = Arrays.binarySearch(arr, x);
        if (index < 0) {
            return 0; // Element not found
        }
        
        // Expand left and right to count all occurrences
        int count = 1;
        
        // Count to the left
        int left = index - 1;
        while (left >= 0 && arr[left] == x) {
            count++;
            left--;
        }
        
        // Count to the right
        int right = index + 1;
        while (right < n && arr[right] == x) {
            count++;
            right++;
        }
        
        return count;
    }
    
    // ============================
    // APPROACH 3: Single Binary Search Variant
    // ============================
    
    /**
     * Find upper bound and lower bound using binary search
     * Count = upperBound - lowerBound
     */
    public static int countUsingBounds(int[] arr, int n, int x) {
        // Find lower bound (first position where arr[i] >= x)
        int lowerBound = findLowerBound(arr, n, x);
        
        // If element not found
        if (lowerBound == n || arr[lowerBound] != x) {
            return 0;
        }
        
        // Find upper bound (first position where arr[i] > x)
        int upperBound = findUpperBound(arr, n, x);
        
        return upperBound - lowerBound;
    }
    
    private static int findLowerBound(int[] arr, int n, int x) {
        int low = 0, high = n;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
    
    private static int findUpperBound(int[] arr, int n, int x) {
        int low = 0, high = n;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > x) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
    
    // ============================
    // APPROACH 4: Modified Binary Search (Count during search)
    // ============================
    
    /**
     * Recursive binary search that counts occurrences
     * Time: O(log n) best case, O(n) worst case (all elements same)
     */
    public static int countRecursive(int[] arr, int n, int x) {
        return countRecursiveHelper(arr, 0, n - 1, x);
    }
    
    private static int countRecursiveHelper(int[] arr, int low, int high, int x) {
        if (low > high) {
            return 0;
        }
        
        if (arr[low] == x && arr[high] == x) {
            return high - low + 1; // All elements in range are x
        }
        
        int mid = low + (high - low) / 2;
        
        if (arr[mid] == x) {
            // Count occurrences on both sides
            return 1 + 
                   countRecursiveHelper(arr, low, mid - 1, x) + 
                   countRecursiveHelper(arr, mid + 1, high, x);
        } else if (arr[mid] < x) {
            return countRecursiveHelper(arr, mid + 1, high, x);
        } else {
            return countRecursiveHelper(arr, low, mid - 1, x);
        }
    }
    
    // ============================
    // APPROACH 5: For Unsorted Arrays
    // ============================
    
    /**
     * Linear scan - works for unsorted arrays
     * Time: O(n), Space: O(1)
     */
    public static int countLinear(int[] arr, int n, int x) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (arr[i] == x) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * Using HashMap for multiple queries on unsorted array
     * Time: O(n) preprocessing, O(1) per query
     */
    public static class FrequencyCounter {
        private Map<Integer, Integer> freqMap;
        
        public FrequencyCounter(int[] arr) {
            freqMap = new HashMap<>();
            for (int num : arr) {
                freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
            }
        }
        
        public int getFrequency(int x) {
            return freqMap.getOrDefault(x, 0);
        }
        
        public void update(int index, int newValue, int[] arr) {
            int oldValue = arr[index];
            // Decrease old value frequency
            freqMap.put(oldValue, freqMap.get(oldValue) - 1);
            if (freqMap.get(oldValue) == 0) {
                freqMap.remove(oldValue);
            }
            
            // Increase new value frequency
            freqMap.put(newValue, freqMap.getOrDefault(newValue, 0) + 1);
            arr[index] = newValue;
        }
    }
    
    // ============================
    // EXTENSION: Count occurrences in rotated sorted array
    // ============================
    
    /**
     * Count occurrences in rotated sorted array
     * Example: [5, 6, 7, 1, 2, 3, 4] is rotated
     */
    public static int countInRotatedArray(int[] arr, int n, int x) {
        // Find pivot (smallest element index)
        int pivot = findPivot(arr, 0, n - 1);
        
        if (pivot == -1) {
            // Array not rotated, use normal binary search
            return count(arr, n, x);
        }
        
        if (arr[pivot] == x) {
            // Count from pivot
            return countFromIndex(arr, n, x, pivot);
        }
        
        if (x >= arr[0]) {
            // Search in left sorted part
            return countInRange(arr, 0, pivot - 1, x);
        } else {
            // Search in right sorted part
            return countInRange(arr, pivot, n - 1, x);
        }
    }
    
    private static int findPivot(int[] arr, int low, int high) {
        if (low > high) return -1;
        if (low == high) return low;
        
        int mid = low + (high - low) / 2;
        
        if (mid < high && arr[mid] > arr[mid + 1]) {
            return mid + 1;
        }
        if (mid > low && arr[mid] < arr[mid - 1]) {
            return mid;
        }
        
        if (arr[low] >= arr[mid]) {
            return findPivot(arr, low, mid - 1);
        }
        return findPivot(arr, mid + 1, high);
    }
    
    private static int countFromIndex(int[] arr, int n, int x, int start) {
        int count = 0;
        int i = start;
        while (i < n && arr[i] == x) {
            count++;
            i++;
        }
        i = start - 1;
        while (i >= 0 && arr[i] == x) {
            count++;
            i--;
        }
        return count;
    }
    
    private static int countInRange(int[] arr, int low, int high, int x) {
        int first = -1, last = -1;
        
        // Find first occurrence in range
        int left = low, right = high;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == x) {
                first = mid;
                right = mid - 1;
            } else if (arr[mid] < x) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        if (first == -1) return 0;
        
        // Find last occurrence in range
        left = first;
        right = high;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == x) {
                last = mid;
                left = mid + 1;
            } else if (arr[mid] < x) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return last - first + 1;
    }
    
    // ============================
    // EXTENSION: Count in 2D sorted matrix
    // ============================
    
    /**
     * Count occurrences in row-wise and column-wise sorted matrix
     * Each row and column is sorted
     */
    public static int countInSortedMatrix(int[][] matrix, int x) {
        if (matrix == null || matrix.length == 0) return 0;
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int count = 0;
        
        // Start from top-right corner
        int row = 0, col = cols - 1;
        
        while (row < rows && col >= 0) {
            if (matrix[row][col] == x) {
                count++;
                // Count duplicates in current row
                int temp = col - 1;
                while (temp >= 0 && matrix[row][temp] == x) {
                    count++;
                    temp--;
                }
                row++; // Move to next row
            } else if (matrix[row][col] > x) {
                col--; // Move left
            } else {
                row++; // Move down
            }
        }
        
        return count;
    }
    
    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        System.out.println("=== Testing Count Occurrences ===");
        
        // Test Case 1: Basic example
        int[] arr1 = {1, 1, 2, 2, 2, 2, 3};
        int x1 = 2;
        System.out.println("\nTest Case 1:");
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("x = " + x1);
        
        System.out.println("Two Binary Searches: " + count(arr1, arr1.length, x1));
        System.out.println("Using Standard Lib: " + countUsingStandardLib(arr1, arr1.length, x1));
        System.out.println("Using Bounds: " + countUsingBounds(arr1, arr1.length, x1));
        System.out.println("Recursive: " + countRecursive(arr1, arr1.length, x1));
        System.out.println("Expected: 4");
        
        // Test Case 2: Element not present
        int[] arr2 = {1, 2, 3, 4, 5};
        int x2 = 6;
        System.out.println("\nTest Case 2 (Element not present):");
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("x = " + x2);
        
        System.out.println("Result: " + count(arr2, arr2.length, x2));
        System.out.println("Expected: 0");
        
        // Test Case 3: All elements same
        int[] arr3 = {5, 5, 5, 5, 5};
        int x3 = 5;
        System.out.println("\nTest Case 3 (All elements same):");
        System.out.println("Array: " + Arrays.toString(arr3));
        System.out.println("x = " + x3);
        
        System.out.println("Result: " + count(arr3, arr3.length, x3));
        System.out.println("Expected: 5");
        
        // Test Case 4: Single element
        int[] arr4 = {7};
        int x4a = 7, x4b = 8;
        System.out.println("\nTest Case 4 (Single element):");
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("x = 7: " + count(arr4, arr4.length, x4a));
        System.out.println("x = 8: " + count(arr4, arr4.length, x4b));
        
        // Test Case 5: Empty array
        int[] arr5 = {};
        int x5 = 5;
        System.out.println("\nTest Case 5 (Empty array):");
        System.out.println("Array: []");
        System.out.println("x = " + x5 + ": " + count(arr5, arr5.length, x5));
        
        // Test Case 6: Large array with many duplicates
        int[] arr6 = new int[1000];
        Arrays.fill(arr6, 0, 300, 1);
        Arrays.fill(arr6, 300, 700, 2);
        Arrays.fill(arr6, 700, 1000, 3);
        int x6 = 2;
        System.out.println("\nTest Case 6 (Large array):");
        System.out.println("Array size: 1000 (300 ones, 400 twos, 300 threes)");
        System.out.println("x = " + x6 + ": " + count(arr6, arr6.length, x6));
        System.out.println("Expected: 400");
        
        // Test FrequencyCounter for unsorted arrays
        System.out.println("\n=== Testing FrequencyCounter (for unsorted) ===");
        int[] unsorted = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        FrequencyCounter counter = new FrequencyCounter(unsorted);
        
        System.out.println("Array: " + Arrays.toString(unsorted));
        System.out.println("Frequency of 5: " + counter.getFrequency(5));
        System.out.println("Frequency of 1: " + counter.getFrequency(1));
        System.out.println("Frequency of 7: " + counter.getFrequency(7));
        
        // Test rotated array
        System.out.println("\n=== Testing Rotated Sorted Array ===");
        int[] rotated = {5, 6, 7, 1, 2, 3, 4};
        int x7 = 2;
        System.out.println("Rotated array: " + Arrays.toString(rotated));
        System.out.println("x = " + x7 + ": " + countInRotatedArray(rotated, rotated.length, x7));
        
        int[] rotated2 = {2, 2, 2, 2, 3, 1, 2, 2};
        int x8 = 2;
        System.out.println("Rotated with duplicates: " + Arrays.toString(rotated2));
        System.out.println("x = " + x8 + ": " + countInRotatedArray(rotated2, rotated2.length, x8));
        
        // Test 2D matrix
        System.out.println("\n=== Testing 2D Sorted Matrix ===");
        int[][] matrix = {
            {1, 4, 7, 8},
            {2, 5, 8, 9},
            {3, 6, 9, 10}
        };
        int x9 = 8;
        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("x = " + x9 + ": " + countInSortedMatrix(matrix, x9));
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeArray = new int[1000000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i / 100; // Creates many duplicates
        }
        int xTest = 500; // Should appear 100 times
        
        long startTime = System.currentTimeMillis();
        int res1 = count(largeArray, largeArray.length, xTest);
        long time1 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        int res2 = countLinear(largeArray, largeArray.length, xTest);
        long time2 = System.currentTimeMillis() - startTime;
        
        System.out.println("Array size: " + largeArray.length);
        System.out.println("Binary Search: " + time1 + "ms, Count: " + res1);
        System.out.println("Linear Search: " + time2 + "ms, Count: " + res2);
        
        // Edge case: Element at boundaries
        System.out.println("\n=== Testing Boundary Cases ===");
        int[] arr7 = {1, 2, 2, 2, 2, 3, 4};
        System.out.println("Array: " + Arrays.toString(arr7));
        System.out.println("x = 1 (first element): " + count(arr7, arr7.length, 1));
        System.out.println("x = 4 (last element): " + count(arr7, arr7.length, 4));
        System.out.println("x = 0 (before first): " + count(arr7, arr7.length, 0));
        System.out.println("x = 5 (after last): " + count(arr7, arr7.length, 5));
    }
    
    // ============================
    // EXTENSION: Count with range queries
    // ============================
    
    /**
     * Count occurrences of x in subarray [left, right]
     * Preprocessing: O(n) time, O(n) space
     * Query: O(log n) time
     */
    public static class RangeCounter {
        private Map<Integer, List<Integer>> indexMap;
        
        public RangeCounter(int[] arr) {
            indexMap = new HashMap<>();
            for (int i = 0; i < arr.length; i++) {
                indexMap.putIfAbsent(arr[i], new ArrayList<>());
                indexMap.get(arr[i]).add(i);
            }
        }
        
        public int countInRange(int x, int left, int right) {
            if (!indexMap.containsKey(x)) {
                return 0;
            }
            
            List<Integer> indices = indexMap.get(x);
            
            // Find first index >= left
            int lower = lowerBound(indices, left);
            
            // Find first index > right
            int upper = upperBound(indices, right);
            
            return upper - lower;
        }
        
        private int lowerBound(List<Integer> list, int target) {
            int low = 0, high = list.size();
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (list.get(mid) >= target) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            return low;
        }
        
        private int upperBound(List<Integer> list, int target) {
            int low = 0, high = list.size();
            while (low < high) {
                int mid = low + (high - low) / 2;
                if (list.get(mid) > target) {
                    high = mid;
                } else {
                    low = mid + 1;
                }
            }
            return low;
        }
    }
    
    // ============================
    // EXTENSION: Count with tolerance
    // ============================
    
    /**
     * Count elements within tolerance of x
     * Returns count of elements where |arr[i] - x| <= tolerance
     */
    public static int countWithTolerance(int[] arr, int n, int x, int tolerance) {
        // Find lower bound for x - tolerance
        int lower = findLowerBound(arr, n, x - tolerance);
        
        // Find upper bound for x + tolerance
        int upper = findUpperBound(arr, n, x + tolerance);
        
        return upper - lower;
    }
    
    // ============================
    // EXTENSION: Most frequent element in sorted array
    // ============================
    
    /**
     * Find the most frequent element in sorted array
     * If multiple have same frequency, return any
     */
    public static int findMostFrequent(int[] arr, int n) {
        if (n == 0) return -1;
        
        int maxCount = 0;
        int mostFrequent = arr[0];
        int current = arr[0];
        int currentCount = 1;
        
        for (int i = 1; i < n; i++) {
            if (arr[i] == current) {
                currentCount++;
            } else {
                if (currentCount > maxCount) {
                    maxCount = currentCount;
                    mostFrequent = current;
                }
                current = arr[i];
                currentCount = 1;
            }
        }
        
        // Check last element
        if (currentCount > maxCount) {
            mostFrequent = current;
        }
        
        return mostFrequent;
    }
}