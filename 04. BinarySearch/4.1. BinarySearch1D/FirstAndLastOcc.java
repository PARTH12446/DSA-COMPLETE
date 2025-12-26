import java.util.*;

/** 
 * Problem: Find First and Last Position of Element in Sorted Array
 * LeetCode 34 / Coding Ninjas equivalent
 * 
 * Given an array of integers nums sorted in non-decreasing order,
 * find the starting and ending position of a given target value.
 * 
 * Return [-1, -1] if target is not found in the array.
 */
public class FirstAndLastOcc {

    // ============================
    // APPROACH 1: Two Binary Searches (Original)
    // ============================
    
    /**
     * Returns an int array of size 2: {firstIndex, lastIndex} or {-1, -1} if k not found.
     * 
     * Time: O(log n) - Two binary searches
     * Space: O(1)
     */
    public static int[] firstAndLastPosition(int[] arr, int n, int k) {
        // Find lower bound (first index where arr[i] >= k)
        int lowerBound = findLowerBound(arr, n, k);
        
        // If element not found
        if (lowerBound == n || arr[lowerBound] != k) {
            return new int[]{-1, -1};
        }
        
        // Find upper bound (first index where arr[i] > k)
        int upperBound = findUpperBound(arr, n, k);
        
        return new int[]{lowerBound, upperBound - 1};
    }
    
    private static int findLowerBound(int[] arr, int n, int k) {
        int low = 0, high = n - 1;
        int result = n; // Initialize with n (not found)
        
        while (low <= high) {
            int mid = low + (high - low) / 2; // Avoid overflow
            
            if (arr[mid] >= k) {
                result = mid;   // Found candidate
                high = mid - 1; // Search left for earlier occurrence
            } else {
                low = mid + 1;
            }
        }
        
        return result;
    }
    
    private static int findUpperBound(int[] arr, int n, int k) {
        int low = 0, high = n - 1;
        int result = n; // Initialize with n (not found)
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] > k) {
                result = mid;   // Found candidate
                high = mid - 1; // Search left for earlier > k
            } else {
                low = mid + 1;
            }
        }
        
        return result;
    }
    
    // ============================
    // APPROACH 2: Standard LeetCode Solution
    // ============================
    
    /**
     * Standard solution for LeetCode 34
     * 
     * @param nums Sorted array
     * @param target Target value
     * @return [first, last] indices
     */
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = findFirst(nums, target);
        result[1] = findLast(nums, target);
        return result;
    }
    
    private int findFirst(int[] nums, int target) {
        int idx = -1;
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
            
            if (nums[mid] == target) {
                idx = mid; // Record potential first occurrence
            }
        }
        
        return idx;
    }
    
    private int findLast(int[] nums, int target) {
        int idx = -1;
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
            
            if (nums[mid] == target) {
                idx = mid; // Record potential last occurrence
            }
        }
        
        return idx;
    }
    
    // ============================
    // APPROACH 3: Single Binary Search with Modified Logic
    // ============================
    
    /**
     * Alternative: Find first and last in one pass
     */
    public static int[] firstAndLastPositionSinglePass(int[] arr, int n, int k) {
        if (n == 0) return new int[]{-1, -1};
        
        int first = -1, last = -1;
        int left = 0, right = n - 1;
        
        // First find any occurrence
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == k) {
                // Found an occurrence, now expand to find first and last
                first = last = mid;
                
                // Expand left to find first occurrence
                int temp = mid - 1;
                while (temp >= 0 && arr[temp] == k) {
                    first = temp;
                    temp--;
                }
                
                // Expand right to find last occurrence
                temp = mid + 1;
                while (temp < n && arr[temp] == k) {
                    last = temp;
                    temp++;
                }
                
                break;
            } else if (arr[mid] < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return new int[]{first, last};
    }
    
    // ============================
    // APPROACH 4: Using Java's Arrays.binarySearch()
    // ============================
    
    /**
     * Using built-in binarySearch and expanding
     */
    public static int[] firstAndLastPositionBuiltIn(int[] arr, int n, int k) {
        int index = Arrays.binarySearch(arr, k);
        
        if (index < 0) {
            return new int[]{-1, -1};
        }
        
        // Expand left
        int left = index;
        while (left > 0 && arr[left - 1] == k) {
            left--;
        }
        
        // Expand right
        int right = index;
        while (right < n - 1 && arr[right + 1] == k) {
            right++;
        }
        
        return new int[]{left, right};
    }
    
    // ============================
    // APPROACH 5: Template-based Binary Search
    // ============================
    
    /**
     * Template that can be reused for similar problems
     */
    public static int[] firstAndLastPositionTemplate(int[] arr, int n, int k) {
        int first = findBound(arr, n, k, true);
        if (first == -1) {
            return new int[]{-1, -1};
        }
        int last = findBound(arr, n, k, false);
        return new int[]{first, last};
    }
    
    private static int findBound(int[] arr, int n, int k, boolean isFirst) {
        int left = 0, right = n - 1;
        int bound = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == k) {
                bound = mid;
                if (isFirst) {
                    right = mid - 1; // Search left for first occurrence
                } else {
                    left = mid + 1;  // Search right for last occurrence
                }
            } else if (arr[mid] < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return bound;
    }
    
    // ============================
    // EXTENSION: Count occurrences
    // ============================
    
    /**
     * Count total occurrences of target in sorted array
     * 
     * @param arr Sorted array
     * @param n Array size
     * @param k Target value
     * @return Count of occurrences
     */
    public static int countOccurrences(int[] arr, int n, int k) {
        int[] positions = firstAndLastPosition(arr, n, k);
        
        if (positions[0] == -1) {
            return 0;
        }
        
        return positions[1] - positions[0] + 1;
    }
    
    /**
     * Optimized count using binary search bounds
     */
    public static int countOccurrencesOptimized(int[] arr, int n, int k) {
        // Find lower bound
        int left = 0, right = n - 1;
        int first = -1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] >= k) {
                if (arr[mid] == k) first = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        
        if (first == -1) return 0;
        
        // Find upper bound
        left = first;
        right = n - 1;
        int last = first;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] <= k) {
                if (arr[mid] == k) last = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return last - first + 1;
    }
    
    // ============================
    // EXTENSION: Find range for multiple targets
    // ============================
    
    /**
     * Find ranges for multiple targets efficiently
     */
    public static Map<Integer, int[]> findRangesForMultiple(int[] arr, int[] targets) {
        Map<Integer, int[]> result = new HashMap<>();
        
        for (int target : targets) {
            result.put(target, firstAndLastPosition(arr, arr.length, target));
        }
        
        return result;
    }
    
    /**
     * Optimized version for multiple targets (preprocess)
     */
    public static Map<Integer, int[]> findRangesOptimized(int[] arr, int[] targets) {
        Map<Integer, int[]> result = new HashMap<>();
        
        // Create map of value to first occurrence
        Map<Integer, Integer> firstOccurrence = new HashMap<>();
        Map<Integer, Integer> lastOccurrence = new HashMap<>();
        
        // Linear scan to build maps (O(n))
        for (int i = 0; i < arr.length; i++) {
            int num = arr[i];
            
            if (!firstOccurrence.containsKey(num)) {
                firstOccurrence.put(num, i);
            }
            lastOccurrence.put(num, i);
        }
        
        // Build results (O(m) where m = targets.length)
        for (int target : targets) {
            if (firstOccurrence.containsKey(target)) {
                result.put(target, new int[]{
                    firstOccurrence.get(target),
                    lastOccurrence.get(target)
                });
            } else {
                result.put(target, new int[]{-1, -1});
            }
        }
        
        return result;
    }
    
    // ============================
    // EXTENSION: Find k closest elements
    // ============================
    
    /**
     * Find k closest elements to target
     * Returns indices of k closest elements
     */
    public static int[] findKClosestIndices(int[] arr, int n, int k, int target) {
        // Find insertion point
        int pos = Arrays.binarySearch(arr, target);
        
        if (pos < 0) {
            pos = -pos - 1; // Insertion point
        }
        
        int left = pos - 1;
        int right = pos;
        List<Integer> result = new ArrayList<>();
        
        // Expand to find k closest
        while (result.size() < k && (left >= 0 || right < n)) {
            if (left < 0) {
                result.add(right++);
            } else if (right >= n) {
                result.add(left--);
            } else {
                int leftDiff = Math.abs(arr[left] - target);
                int rightDiff = Math.abs(arr[right] - target);
                
                if (leftDiff <= rightDiff) {
                    result.add(left--);
                } else {
                    result.add(right++);
                }
            }
        }
        
        // Convert to array and sort indices
        int[] indices = result.stream().mapToInt(i -> i).toArray();
        Arrays.sort(indices);
        return indices;
    }
    
    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        System.out.println("=== Testing First and Last Position ===");
        
        // Test Case 1: Basic example
        System.out.println("\nTest Case 1: Basic Example");
        int[] arr1 = {1, 2, 2, 2, 3, 4, 4, 5};
        int target1 = 2;
        int[] result1 = firstAndLastPosition(arr1, arr1.length, target1);
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Target: " + target1);
        System.out.println("Result: " + Arrays.toString(result1));
        System.out.println("Expected: [1, 3]");
        
        // Test different approaches
        System.out.println("\nDifferent Approaches:");
        System.out.println("Two Binary Searches: " + Arrays.toString(result1));
        System.out.println("Single Pass: " + Arrays.toString(firstAndLastPositionSinglePass(arr1, arr1.length, target1)));
        System.out.println("Built-in: " + Arrays.toString(firstAndLastPositionBuiltIn(arr1, arr1.length, target1)));
        System.out.println("Template: " + Arrays.toString(firstAndLastPositionTemplate(arr1, arr1.length, target1)));
        
        // Test Case 2: Target not found
        System.out.println("\nTest Case 2: Target Not Found");
        int target2 = 6;
        int[] result2 = firstAndLastPosition(arr1, arr1.length, target2);
        System.out.println("Target: " + target2);
        System.out.println("Result: " + Arrays.toString(result2));
        System.out.println("Expected: [-1, -1]");
        
        // Test Case 3: Single occurrence
        System.out.println("\nTest Case 3: Single Occurrence");
        int target3 = 3;
        int[] result3 = firstAndLastPosition(arr1, arr1.length, target3);
        System.out.println("Target: " + target3);
        System.out.println("Result: " + Arrays.toString(result3));
        System.out.println("Expected: [4, 4]");
        
        // Test Case 4: All elements same
        System.out.println("\nTest Case 4: All Elements Same");
        int[] arr4 = {5, 5, 5, 5, 5};
        int target4 = 5;
        int[] result4 = firstAndLastPosition(arr4, arr4.length, target4);
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("Target: " + target4);
        System.out.println("Result: " + Arrays.toString(result4));
        System.out.println("Expected: [0, 4]");
        
        // Test Case 5: Empty array
        System.out.println("\nTest Case 5: Empty Array");
        int[] arr5 = {};
        int target5 = 1;
        int[] result5 = firstAndLastPosition(arr5, arr5.length, target5);
        System.out.println("Array: []");
        System.out.println("Target: " + target5);
        System.out.println("Result: " + Arrays.toString(result5));
        System.out.println("Expected: [-1, -1]");
        
        // Test Case 6: Target at boundaries
        System.out.println("\nTest Case 6: Target at Boundaries");
        int[] arr6 = {1, 2, 3, 3, 3, 4, 5};
        int target6a = 1, target6b = 5;
        System.out.println("Array: " + Arrays.toString(arr6));
        System.out.println("Target " + target6a + ": " + Arrays.toString(firstAndLastPosition(arr6, arr6.length, target6a)));
        System.out.println("Target " + target6b + ": " + Arrays.toString(firstAndLastPosition(arr6, arr6.length, target6b)));
        
        // Test Case 7: Large array
        System.out.println("\nTest Case 7: Large Array");
        int[] arr7 = new int[1000000];
        Arrays.fill(arr7, 500000, 500100, 42); // Put 100 occurrences of 42 in middle
        for (int i = 0; i < 500000; i++) arr7[i] = i;
        for (int i = 500100; i < 1000000; i++) arr7[i] = i - 99;
        
        int target7 = 42;
        long startTime = System.currentTimeMillis();
        int[] result7 = firstAndLastPosition(arr7, arr7.length, target7);
        long endTime = System.currentTimeMillis();
        System.out.println("Array size: " + arr7.length);
        System.out.println("Target: " + target7);
        System.out.println("Result: " + Arrays.toString(result7));
        System.out.println("Time: " + (endTime - startTime) + "ms");
        System.out.println("Expected: [500000, 500099]");
        
        // Test Count Occurrences
        System.out.println("\n=== Testing Count Occurrences ===");
        int[] arr8 = {1, 2, 2, 2, 3, 4, 4, 5, 5, 5, 5};
        System.out.println("Array: " + Arrays.toString(arr8));
        System.out.println("Count of 2: " + countOccurrences(arr8, arr8.length, 2));
        System.out.println("Count of 5: " + countOccurrences(arr8, arr8.length, 5));
        System.out.println("Count of 6: " + countOccurrences(arr8, arr8.length, 6));
        
        // Test optimized count
        System.out.println("Optimized count of 2: " + countOccurrencesOptimized(arr8, arr8.length, 2));
        
        // Test Multiple Targets
        System.out.println("\n=== Testing Multiple Targets ===");
        int[] arr9 = {1, 2, 2, 3, 3, 3, 4, 5, 5};
        int[] targets = {2, 3, 5, 6};
        Map<Integer, int[]> ranges = findRangesForMultiple(arr9, targets);
        
        System.out.println("Array: " + Arrays.toString(arr9));
        for (int target : targets) {
            System.out.println("Target " + target + ": " + Arrays.toString(ranges.get(target)));
        }
        
        // Test optimized multiple targets
        Map<Integer, int[]> rangesOpt = findRangesOptimized(arr9, targets);
        System.out.println("\nOptimized version:");
        for (int target : targets) {
            System.out.println("Target " + target + ": " + Arrays.toString(rangesOpt.get(target)));
        }
        
        // Test K Closest Elements
        System.out.println("\n=== Testing K Closest Elements ===");
        int[] arr10 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int target10 = 5;
        int k = 4;
        int[] closest = findKClosestIndices(arr10, arr10.length, k, target10);
        System.out.println("Array: " + Arrays.toString(arr10));
        System.out.println("Target: " + target10 + ", k: " + k);
        System.out.print("Closest indices: " + Arrays.toString(closest));
        System.out.print(" -> Values: ");
        for (int idx : closest) {
            System.out.print(arr10[idx] + " ");
        }
        System.out.println();
        
        // Edge Cases Visualization
        System.out.println("\n=== Edge Cases Visualization ===");
        int[][] testArrays = {
            {1},               // Single element
            {1, 1, 1},         // All same
            {1, 3, 5, 7},      // No duplicates
            {},                // Empty
            {1, 2, 3, 4, 5}    // Sorted without target
        };
        
        int[] testTargets = {1, 2, 0, 6};
        
        for (int[] testArr : testArrays) {
            System.out.println("\nArray: " + Arrays.toString(testArr));
            for (int testTarget : testTargets) {
                if (testTarget <= 5 || testTarget == 0 || testTarget == 6) {
                    int[] res = firstAndLastPosition(testArr, testArr.length, testTarget);
                    System.out.println("  Target " + testTarget + ": " + Arrays.toString(res));
                }
            }
        }
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] perfArray = new int[10000000];
        for (int i = 0; i < perfArray.length; i++) {
            perfArray[i] = i / 100; // Creates many duplicates
        }
        int perfTarget = 5000; // Will have many occurrences
        
        // Two binary searches
        startTime = System.currentTimeMillis();
        int[] perfResult1 = firstAndLastPosition(perfArray, perfArray.length, perfTarget);
        long time1 = System.currentTimeMillis() - startTime;
        
        // Single pass with expansion
        startTime = System.currentTimeMillis();
        int[] perfResult2 = firstAndLastPositionSinglePass(perfArray, perfArray.length, perfTarget);
        long time2 = System.currentTimeMillis() - startTime;
        
        // Built-in with expansion
        startTime = System.currentTimeMillis();
        int[] perfResult3 = firstAndLastPositionBuiltIn(perfArray, perfArray.length, perfTarget);
        long time3 = System.currentTimeMillis() - startTime;
        
        System.out.println("Array size: " + perfArray.length);
        System.out.println("Two Binary Searches: " + time1 + "ms, Result: " + Arrays.toString(perfResult1));
        System.out.println("Single Pass with Expansion: " + time2 + "ms, Result: " + Arrays.toString(perfResult2));
        System.out.println("Built-in with Expansion: " + time3 + "ms, Result: " + Arrays.toString(perfResult3));
        
        // Validate all give same result
        boolean allEqual = Arrays.equals(perfResult1, perfResult2) && 
                          Arrays.equals(perfResult2, perfResult3);
        System.out.println("All results equal: " + allEqual);
    }
    
    // ============================
    // DEBUGGING AND VALIDATION
    // ============================
    
    /**
     * Visualize binary search process for finding first occurrence
     */
    public static void visualizeFirstOccurrence(int[] arr, int n, int k) {
        System.out.println("\n=== Visualizing First Occurrence Search ===");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target: " + k);
        System.out.println("Length: " + n);
        
        int low = 0, high = n - 1;
        int result = n;
        int step = 1;
        
        while (low <= high) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  Search range: [" + low + ", " + high + "]");
            
            int mid = low + (high - low) / 2;
            System.out.println("  Mid index: " + mid + ", value: " + arr[mid]);
            
            if (arr[mid] >= k) {
                System.out.println("  arr[" + mid + "] = " + arr[mid] + " >= " + k + " → updating result to " + mid);
                result = mid;
                high = mid - 1;
                System.out.println("  New high: " + high);
            } else {
                System.out.println("  arr[" + mid + "] = " + arr[mid] + " < " + k);
                low = mid + 1;
                System.out.println("  New low: " + low);
            }
            step++;
        }
        
        System.out.println("\nFinal result: " + result);
        if (result < n && arr[result] == k) {
            System.out.println("First occurrence at index: " + result);
        } else {
            System.out.println("Target not found");
        }
    }
    
    /**
     * Validate algorithm with random tests
     */
    public static void stressTest(int numTests, int maxSize, int maxValue) {
        Random rand = new Random();
        int passed = 0;
        
        for (int test = 0; test < numTests; test++) {
            // Generate random sorted array
            int size = rand.nextInt(maxSize) + 1;
            int[] arr = new int[size];
            for (int i = 0; i < size; i++) {
                arr[i] = rand.nextInt(maxValue);
            }
            Arrays.sort(arr);
            
            // Choose random target
            int target = rand.nextInt(maxValue * 2);
            
            // Get binary search result
            int[] bsResult = firstAndLastPosition(arr, size, target);
            
            // Get linear search result (ground truth)
            int first = -1, last = -1;
            for (int i = 0; i < size; i++) {
                if (arr[i] == target) {
                    if (first == -1) first = i;
                    last = i;
                }
            }
            int[] linearResult = new int[]{first, last};
            
            // Validate
            if (Arrays.equals(bsResult, linearResult)) {
                passed++;
            } else {
                System.out.println("Test " + test + " failed!");
                System.out.println("Array: " + Arrays.toString(arr));
                System.out.println("Target: " + target);
                System.out.println("Binary Search: " + Arrays.toString(bsResult));
                System.out.println("Linear Search: " + Arrays.toString(linearResult));
                break;
            }
        }
        
        System.out.println("\nStress Test Results:");
        System.out.println("Tests: " + numTests);
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + (numTests - passed));
        System.out.println("Success Rate: " + (passed * 100.0 / numTests) + "%");
    }
    
    // ============================
    // ADDITIONAL UTILITIES
    // ============================
    
    /**
     * Check if array is sorted in non-decreasing order
     */
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Find all ranges of all elements in array
     * Returns map of value -> [first, last] index
     */
    public static Map<Integer, int[]> findAllRanges(int[] arr) {
        Map<Integer, int[]> ranges = new HashMap<>();
        
        int i = 0;
        while (i < arr.length) {
            int current = arr[i];
            int start = i;
            
            // Find end of current sequence
            while (i < arr.length && arr[i] == current) {
                i++;
            }
            
            ranges.put(current, new int[]{start, i - 1});
        }
        
        return ranges;
    }
}