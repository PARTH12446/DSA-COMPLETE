import java.util.*;

/** 
 * Problem: Find Peak Element
 * LeetCode 162 / Coding Ninjas equivalent
 * 
 * A peak element is an element that is strictly greater than its neighbors.
 * For edge elements, only consider the one existing neighbor.
 * 
 * The array may contain multiple peaks, return any peak index.
 */
public class Peak {

    // ============================
    // APPROACH 1: Binary Search on Slope (Original)
    // ============================
    
    /**
     * Find a peak element using binary search on slope
     * 
     * @param arr Array of integers
     * @return Index of a peak element
     * 
     * Time: O(log n), Space: O(1)
     */
    public static int findPeakElement(int[] arr) {
        int n = arr.length;
        
        // Edge cases
        if (n == 0) return -1; // or throw exception
        if (n == 1) return 0;  // Single element is trivially a peak
        
        // Check boundaries
        if (arr[0] > arr[1]) return 0;
        if (arr[n - 1] > arr[n - 2]) return n - 1;
        
        int low = 1;
        int high = n - 2;
        
        while (low <= high) {
            int mid = low + (high - low) / 2; // Avoid overflow
            
            // Check if mid is peak
            if (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) {
                return mid;
            }
            
            // Decide search direction based on slope
            if (arr[mid] > arr[mid - 1]) {
                // Increasing slope, peak is on right
                low = mid + 1;
            } else {
                // Decreasing slope, peak is on left
                high = mid - 1;
            }
        }
        
        return -1; // Should not reach here for valid input
    }
    
    // ============================
    // APPROACH 2: Simplified Binary Search
    // ============================
    
    /**
     * Simplified version without boundary checks
     */
    public static int findPeakElementSimple(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] > arr[mid + 1]) {
                // Peak is in left half (including mid)
                high = mid;
            } else {
                // Peak is in right half
                low = mid + 1;
            }
        }
        
        return low; // When low == high, we found a peak
    }
    
    // ============================
    // APPROACH 3: Linear Scan
    // ============================
    
    /**
     * Linear scan approach - O(n) but simpler
     */
    public static int findPeakElementLinear(int[] arr) {
        int n = arr.length;
        
        if (n == 0) return -1;
        if (n == 1) return 0;
        
        // Check first element
        if (arr[0] > arr[1]) return 0;
        
        // Check middle elements
        for (int i = 1; i < n - 1; i++) {
            if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                return i;
            }
        }
        
        // Check last element
        if (arr[n - 1] > arr[n - 2]) return n - 1;
        
        return -1; // No peak found
    }
    
    // ============================
    // APPROACH 4: Recursive Binary Search
    // ============================
    
    /**
     * Recursive implementation
     */
    public static int findPeakElementRecursive(int[] arr) {
        return findPeakElementRecursiveHelper(arr, 0, arr.length - 1);
    }
    
    private static int findPeakElementRecursiveHelper(int[] arr, int low, int high) {
        // Base case: single element
        if (low == high) {
            return low;
        }
        
        int mid = low + (high - low) / 2;
        
        if (arr[mid] > arr[mid + 1]) {
            // Peak is in left half (including mid)
            return findPeakElementRecursiveHelper(arr, low, mid);
        } else {
            // Peak is in right half
            return findPeakElementRecursiveHelper(arr, mid + 1, high);
        }
    }
    
    // ============================
    // EXTENSION: Find all peaks
    // ============================
    
    /**
     * Find all peak elements in array
     */
    public static List<Integer> findAllPeaks(int[] arr) {
        List<Integer> peaks = new ArrayList<>();
        int n = arr.length;
        
        if (n == 0) return peaks;
        if (n == 1) {
            peaks.add(0);
            return peaks;
        }
        
        // Check first element
        if (arr[0] > arr[1]) {
            peaks.add(0);
        }
        
        // Check middle elements
        for (int i = 1; i < n - 1; i++) {
            if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                peaks.add(i);
            }
        }
        
        // Check last element
        if (arr[n - 1] > arr[n - 2]) {
            peaks.add(n - 1);
        }
        
        return peaks;
    }
    
    // ============================
    // EXTENSION: Find global maximum (strict peak)
    // ============================
    
    /**
     * Find global maximum (strictly greater than all neighbors)
     * Equivalent to finding a peak in array without duplicates
     */
    public static int findGlobalMaximum(int[] arr) {
        // For array without duplicates, any peak is global maximum
        return findPeakElement(arr);
    }
    
    // ============================
    // EXTENSION: Find peak in 2D array
    // ============================
    
    /**
     * Find a peak in 2D array (greater than all 4 neighbors)
     * Greedy ascent algorithm
     */
    public static int[] findPeak2D(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new int[]{-1, -1};
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Start from middle column
        int startCol = cols / 2;
        int startRow = findMaxInColumn(matrix, startCol);
        
        return findPeak2DHelper(matrix, startRow, startCol);
    }
    
    private static int findMaxInColumn(int[][] matrix, int col) {
        int maxRow = 0;
        int maxValue = matrix[0][col];
        
        for (int i = 1; i < matrix.length; i++) {
            if (matrix[i][col] > maxValue) {
                maxValue = matrix[i][col];
                maxRow = i;
            }
        }
        
        return maxRow;
    }
    
    private static int[] findPeak2DHelper(int[][] matrix, int row, int col) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Check if current element is peak
        boolean isPeak = true;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            
            if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                if (matrix[newRow][newCol] > matrix[row][col]) {
                    isPeak = false;
                    // Move to the neighbor with higher value
                    return findPeak2DHelper(matrix, newRow, newCol);
                }
            }
        }
        
        return new int[]{row, col};
    }
    
    // ============================
    // EXTENSION: Find peak in mountain array
    // ============================
    
    /**
     * Find peak index in mountain array (strictly increasing then decreasing)
     * LeetCode 852: Peak Index in a Mountain Array
     */
    public static int peakIndexInMountainArray(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] < arr[mid + 1]) {
                // Still in increasing part
                low = mid + 1;
            } else {
                // In decreasing part or at peak
                high = mid;
            }
        }
        
        return low; // Peak index
    }
    
    // ============================
    // EXTENSION: Find peak with custom comparator
    // ============================
    
    /**
     * Generic peak finder with comparator
     */
    public static <T extends Comparable<T>> int findPeakGeneric(T[] arr) {
        int low = 0;
        int high = arr.length - 1;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid].compareTo(arr[mid + 1]) > 0) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        
        return low;
    }
    
    // ============================
    // EXTENSION: Find peak in circular array
    // ============================
    
    /**
     * Find peak in circular array (wraps around)
     */
    public static int findPeakCircular(int[] arr) {
        int n = arr.length;
        if (n == 0) return -1;
        if (n == 1) return 0;
        
        // Check all elements
        for (int i = 0; i < n; i++) {
            int left = (i - 1 + n) % n;
            int right = (i + 1) % n;
            
            if (arr[i] > arr[left] && arr[i] > arr[right]) {
                return i;
            }
        }
        
        return -1; // No peak in circular sense
    }
    
    // ============================
    // EXTENSION: Find valley element
    // ============================
    
    /**
     * Find valley element (smaller than neighbors)
     */
    public static int findValleyElement(int[] arr) {
        int n = arr.length;
        
        if (n == 0) return -1;
        if (n == 1) return 0;
        
        // Check boundaries
        if (arr[0] < arr[1]) return 0;
        if (arr[n - 1] < arr[n - 2]) return n - 1;
        
        int low = 1;
        int high = n - 2;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Check if mid is valley
            if (arr[mid - 1] > arr[mid] && arr[mid] < arr[mid + 1]) {
                return mid;
            }
            
            // Decide search direction
            if (arr[mid] < arr[mid - 1]) {
                // Decreasing slope, valley on right
                low = mid + 1;
            } else {
                // Increasing slope, valley on left
                high = mid - 1;
            }
        }
        
        return -1;
    }
    
    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        System.out.println("=== Find Peak Element ===");
        
        // Test Case 1: Basic example
        System.out.println("\nTest Case 1: Basic Example");
        int[] arr1 = {1, 3, 4, 3, 2};
        System.out.println("Array: " + Arrays.toString(arr1));
        
        int peak1 = findPeakElement(arr1);
        System.out.println("Peak index: " + peak1 + ", value: " + 
                         (peak1 != -1 ? arr1[peak1] : "N/A"));
        System.out.println("Expected: 2 (value 4)");
        
        // Test different approaches
        System.out.println("\nDifferent Approaches:");
        System.out.println("Binary Search (Original): " + findPeakElement(arr1));
        System.out.println("Simplified Binary Search: " + findPeakElementSimple(arr1));
        System.out.println("Linear Scan: " + findPeakElementLinear(arr1));
        System.out.println("Recursive: " + findPeakElementRecursive(arr1));
        
        // Test Case 2: Multiple peaks
        System.out.println("\nTest Case 2: Multiple Peaks");
        int[] arr2 = {1, 2, 1, 3, 5, 6, 4};
        System.out.println("Array: " + Arrays.toString(arr2));
        
        int peak2 = findPeakElement(arr2);
        System.out.println("A peak index: " + peak2 + ", value: " + arr2[peak2]);
        System.out.println("Possible peaks: 1 (value 2) or 5 (value 6)");
        
        // Find all peaks
        List<Integer> allPeaks = findAllPeaks(arr2);
        System.out.println("All peaks: " + allPeaks);
        
        // Test Case 3: Single element
        System.out.println("\nTest Case 3: Single Element");
        int[] arr3 = {5};
        System.out.println("Array: " + Arrays.toString(arr3));
        System.out.println("Peak: " + findPeakElement(arr3));
        
        // Test Case 4: Two elements
        System.out.println("\nTest Case 4: Two Elements");
        int[] arr4 = {2, 1};
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("Peak: " + findPeakElement(arr4));
        
        int[] arr5 = {1, 2};
        System.out.println("Array: " + Arrays.toString(arr5));
        System.out.println("Peak: " + findPeakElement(arr5));
        
        // Test Case 5: Strictly increasing
        System.out.println("\nTest Case 5: Strictly Increasing");
        int[] arr6 = {1, 2, 3, 4, 5};
        System.out.println("Array: " + Arrays.toString(arr6));
        System.out.println("Peak: " + findPeakElement(arr6));
        System.out.println("Expected: 4 (last element)");
        
        // Test Case 6: Strictly decreasing
        System.out.println("\nTest Case 6: Strictly Decreasing");
        int[] arr7 = {5, 4, 3, 2, 1};
        System.out.println("Array: " + Arrays.toString(arr7));
        System.out.println("Peak: " + findPeakElement(arr7));
        System.out.println("Expected: 0 (first element)");
        
        // Test Case 7: Plateau (equal neighbors)
        System.out.println("\nTest Case 7: Plateau");
        int[] arr8 = {1, 2, 2, 1};
        System.out.println("Array: " + Arrays.toString(arr8));
        System.out.println("Peak: " + findPeakElement(arr8));
        System.out.println("Note: Strict peak requires > neighbors, not >=");
        
        // Test Mountain Array Peak
        System.out.println("\n=== Testing Mountain Array Peak ===");
        int[] mountain = {0, 1, 2, 3, 4, 5, 4, 3, 2, 1};
        System.out.println("Mountain array: " + Arrays.toString(mountain));
        int mountainPeak = peakIndexInMountainArray(mountain);
        System.out.println("Peak index: " + mountainPeak + ", value: " + mountain[mountainPeak]);
        
        // Test 2D Peak
        System.out.println("\n=== Testing 2D Peak ===");
        int[][] matrix = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12},
            {13, 14, 15, 16}
        };
        int[] peak2D = findPeak2D(matrix);
        System.out.println("2D matrix peak at: [" + peak2D[0] + ", " + peak2D[1] + "]");
        System.out.println("Value: " + matrix[peak2D[0]][peak2D[1]]);
        
        // Test Valley Element
        System.out.println("\n=== Testing Valley Element ===");
        int[] valleyArr = {5, 3, 1, 2, 4};
        System.out.println("Array: " + Arrays.toString(valleyArr));
        int valley = findValleyElement(valleyArr);
        System.out.println("Valley index: " + valley + ", value: " + valleyArr[valley]);
        
        // Test Circular Peak
        System.out.println("\n=== Testing Circular Peak ===");
        int[] circular = {1, 2, 3, 2, 1};
        System.out.println("Circular array: " + Arrays.toString(circular));
        int circularPeak = findPeakCircular(circular);
        System.out.println("Circular peak index: " + circularPeak);
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[] perfArray = new int[10000000];
        for (int i = 0; i < perfArray.length; i++) {
            perfArray[i] = i < perfArray.length / 2 ? i : perfArray.length - i;
        }
        
        long startTime = System.currentTimeMillis();
        int perfPeak = findPeakElement(perfArray);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Array size: " + perfArray.length);
        System.out.println("Peak index: " + perfPeak + ", value: " + perfArray[perfPeak]);
        System.out.println("Time: " + (endTime - startTime) + "ms");
        
        // Compare with linear scan
        startTime = System.currentTimeMillis();
        int linearPeak = findPeakElementLinear(perfArray);
        endTime = System.currentTimeMillis();
        System.out.println("Linear scan time: " + (endTime - startTime) + "ms");
        
        // Edge cases visualization
        System.out.println("\n=== Edge Cases Visualization ===");
        visualizePeakSearch(arr1, 2);
        
        // Random tests validation
        System.out.println("\n=== Random Tests Validation ===");
        Random rand = new Random();
        int numTests = 1000;
        int passed = 0;
        
        for (int i = 0; i < numTests; i++) {
            // Generate random array
            int size = rand.nextInt(100) + 1;
            int[] randomArr = new int[size];
            for (int j = 0; j < size; j++) {
                randomArr[j] = rand.nextInt(1000);
            }
            
            // Get binary search result
            int bsResult = findPeakElement(randomArr);
            
            // Validate peak condition
            boolean isValid = true;
            if (bsResult == -1) {
                // Check if array really has no peak
                isValid = !hasPeak(randomArr);
            } else {
                // Check if bsResult is actually a peak
                isValid = isPeak(randomArr, bsResult);
            }
            
            if (isValid) {
                passed++;
            } else {
                System.out.println("Test failed!");
                System.out.println("Array: " + Arrays.toString(randomArr));
                System.out.println("Claimed peak index: " + bsResult);
                System.out.println("Is valid peak: " + isPeak(randomArr, bsResult));
                break;
            }
        }
        
        System.out.println("Passed: " + passed + "/" + numTests);
    }
    
    // ============================
    // DEBUGGING AND VISUALIZATION
    // ============================
    
    /**
     * Visualize the peak search process
     */
    public static void visualizePeakSearch(int[] arr, int expectedPeak) {
        System.out.println("\n=== Visualizing Peak Search ===");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Expected peak index: " + expectedPeak);
        System.out.println("Length: " + arr.length);
        
        int n = arr.length;
        
        // Check boundaries first
        System.out.println("\nChecking boundaries:");
        if (n > 1) {
            if (arr[0] > arr[1]) {
                System.out.println("  arr[0] = " + arr[0] + " > arr[1] = " + arr[1] + 
                                 " → index 0 is peak");
                return;
            }
            if (arr[n-1] > arr[n-2]) {
                System.out.println("  arr[" + (n-1) + "] = " + arr[n-1] + 
                                 " > arr[" + (n-2) + "] = " + arr[n-2] + 
                                 " → index " + (n-1) + " is peak");
                return;
            }
        }
        
        int low = 1;
        int high = n - 2;
        int step = 1;
        
        System.out.println("\nBinary search in range [" + low + ", " + high + "]:");
        
        while (low <= high) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  Search range: [" + low + ", " + high + "]");
            
            int mid = low + (high - low) / 2;
            System.out.println("  Mid index: " + mid + ", value: " + arr[mid]);
            System.out.println("  Left neighbor: arr[" + (mid-1) + "] = " + arr[mid-1]);
            System.out.println("  Right neighbor: arr[" + (mid+1) + "] = " + arr[mid+1]);
            
            if (arr[mid - 1] < arr[mid] && arr[mid] > arr[mid + 1]) {
                System.out.println("  arr[mid] > both neighbors → found peak at index " + mid);
                return;
            }
            
            if (arr[mid] > arr[mid - 1]) {
                System.out.println("  Increasing slope (arr[mid] > arr[mid-1])");
                System.out.println("  → Peak must be on right side");
                low = mid + 1;
                System.out.println("  New low: " + low);
            } else {
                System.out.println("  Decreasing slope (arr[mid] ≤ arr[mid-1])");
                System.out.println("  → Peak must be on left side");
                high = mid - 1;
                System.out.println("  New high: " + high);
            }
            step++;
        }
        
        System.out.println("\nNo peak found in binary search range.");
    }
    
    // ============================
    // ADDITIONAL UTILITIES
    // ============================
    
    /**
     * Check if index is a valid peak
     */
    public static boolean isPeak(int[] arr, int index) {
        int n = arr.length;
        
        if (n == 0) return false;
        if (n == 1) return index == 0;
        
        if (index == 0) {
            return arr[0] > arr[1];
        } else if (index == n - 1) {
            return arr[n - 1] > arr[n - 2];
        } else {
            return arr[index] > arr[index - 1] && arr[index] > arr[index + 1];
        }
    }
    
    /**
     * Check if array has any peak
     */
    public static boolean hasPeak(int[] arr) {
        int n = arr.length;
        
        if (n == 0) return false;
        if (n == 1) return true; // Single element is trivially a peak
        
        // Check first element
        if (arr[0] > arr[1]) return true;
        
        // Check middle elements
        for (int i = 1; i < n - 1; i++) {
            if (arr[i] > arr[i - 1] && arr[i] > arr[i + 1]) {
                return true;
            }
        }
        
        // Check last element
        if (arr[n - 1] > arr[n - 2]) return true;
        
        return false;
    }
    
    /**
     * Generate test arrays with guaranteed peak
     */
    public static int[] generateArrayWithPeak(int n, int peakIndex) {
        if (peakIndex < 0 || peakIndex >= n) {
            throw new IllegalArgumentException("Peak index out of bounds");
        }
        
        int[] arr = new int[n];
        Random rand = new Random();
        
        // Generate increasing sequence before peak
        for (int i = 0; i <= peakIndex; i++) {
            arr[i] = i + rand.nextInt(10);
        }
        
        // Generate decreasing sequence after peak
        for (int i = peakIndex + 1; i < n; i++) {
            arr[i] = arr[peakIndex] - (i - peakIndex) - rand.nextInt(10);
        }
        
        return arr;
    }
    
    /**
     * Find highest peak (global maximum)
     */
    public static int findHighestPeak(int[] arr) {
        List<Integer> peaks = findAllPeaks(arr);
        
        if (peaks.isEmpty()) {
            // If no strict peak, find maximum
            int maxIndex = 0;
            for (int i = 1; i < arr.length; i++) {
                if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }
            return maxIndex;
        }
        
        // Find peak with maximum value
        int highestIndex = peaks.get(0);
        for (int i = 1; i < peaks.size(); i++) {
            if (arr[peaks.get(i)] > arr[highestIndex]) {
                highestIndex = peaks.get(i);
            }
        }
        
        return highestIndex;
    }
    
    /**
     * Find peak with binary search template
     */
    public static int findPeakTemplate(int[] arr) {
        // Template for binary search problems
        int low = 0;
        int high = arr.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Check if mid is answer
            boolean isAnswer = false;
            if (mid == 0) {
                isAnswer = (mid == arr.length - 1) || (arr[mid] > arr[mid + 1]);
            } else if (mid == arr.length - 1) {
                isAnswer = arr[mid] > arr[mid - 1];
            } else {
                isAnswer = arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1];
            }
            
            if (isAnswer) {
                return mid;
            }
            
            // Decide search direction
            if (mid > 0 && arr[mid - 1] > arr[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return -1;
    }
}