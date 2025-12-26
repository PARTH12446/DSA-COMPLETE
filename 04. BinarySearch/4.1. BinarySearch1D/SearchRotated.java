import java.util.*;

/** 
 * Problem: Search in Rotated Sorted Array
 * LeetCode 33 / Coding Ninjas equivalent
 * 
 * Given a sorted rotated array of distinct integers,
 * find the index of target element in O(log n) time.
 */
public class SearchRotated {

    // ============================
    // APPROACH 1: Standard Binary Search (Original)
    // ============================
    
    /**
     * Search for target in rotated sorted array
     * 
     * @param arr Rotated sorted array with distinct elements
     * @param n   Size of array
     * @param k   Target value
     * @return Index of target, or -1 if not found
     * 
     * Time: O(log n), Space: O(1)
     */
    public static int search(int[] arr, int n, int k) {
        int low = 0;
        int high = n - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2; // Avoid overflow

            if (arr[mid] == k) {
                return mid;
            }

            if (arr[low] <= arr[mid]) {
                // Left half [low..mid] is sorted
                if (arr[low] <= k && k < arr[mid]) {
                    // Target is in sorted left half
                    high = mid - 1;
                } else {
                    // Target is in right half
                    low = mid + 1;
                }
            } else {
                // Right half [mid..high] is sorted
                if (arr[mid] < k && k <= arr[high]) {
                    // Target is in sorted right half
                    low = mid + 1;
                } else {
                    // Target is in left half
                    high = mid - 1;
                }
            }
        }

        return -1;
    }
    
    // ============================
    // APPROACH 2: Two-Pass (Find Pivot + Binary Search)
    // ============================
    
    /**
     * First find pivot, then binary search in appropriate half
     */
    public static int searchTwoPass(int[] arr, int n, int k) {
        // Find pivot (minimum element index)
        int pivot = findPivot(arr, n);
        
        // Binary search in appropriate half
        if (pivot == 0) {
            // Array not rotated, search entire array
            return binarySearch(arr, 0, n - 1, k);
        }
        
        if (k >= arr[0] && k <= arr[pivot - 1]) {
            // Target in left sorted half
            return binarySearch(arr, 0, pivot - 1, k);
        } else {
            // Target in right sorted half
            return binarySearch(arr, pivot, n - 1, k);
        }
    }
    
    private static int findPivot(int[] arr, int n) {
        int low = 0, high = n - 1;
        
        // Array not rotated
        if (arr[low] <= arr[high]) {
            return 0;
        }
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Check if mid is pivot
            if (mid < n - 1 && arr[mid] > arr[mid + 1]) {
                return mid + 1;
            }
            if (mid > 0 && arr[mid] < arr[mid - 1]) {
                return mid;
            }
            
            // Decide which half contains pivot
            if (arr[low] <= arr[mid]) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return 0;
    }
    
    private static int binarySearch(int[] arr, int low, int high, int k) {
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == k) {
                return mid;
            } else if (arr[mid] < k) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
    
    // ============================
    // APPROACH 3: Recursive Implementation
    // ============================
    
    /**
     * Recursive search in rotated sorted array
     */
    public static int searchRecursive(int[] arr, int n, int k) {
        return searchRecursiveHelper(arr, k, 0, n - 1);
    }
    
    private static int searchRecursiveHelper(int[] arr, int k, int low, int high) {
        if (low > high) {
            return -1;
        }
        
        int mid = low + (high - low) / 2;
        
        if (arr[mid] == k) {
            return mid;
        }
        
        if (arr[low] <= arr[mid]) {
            // Left half is sorted
            if (arr[low] <= k && k < arr[mid]) {
                return searchRecursiveHelper(arr, k, low, mid - 1);
            } else {
                return searchRecursiveHelper(arr, k, mid + 1, high);
            }
        } else {
            // Right half is sorted
            if (arr[mid] < k && k <= arr[high]) {
                return searchRecursiveHelper(arr, k, mid + 1, high);
            } else {
                return searchRecursiveHelper(arr, k, low, mid - 1);
            }
        }
    }
    
    // ============================
    // APPROACH 4: With Duplicates (LeetCode 81)
    // ============================
    
    /**
     * Search in rotated sorted array with duplicates
     * 
     * @param arr Rotated sorted array (may have duplicates)
     * @param k Target value
     * @return true if target exists, false otherwise
     */
    public static boolean searchWithDuplicates(int[] arr, int k) {
        int low = 0, high = arr.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == k) {
                return true;
            }
            
            // Handle duplicates: arr[low] == arr[mid] == arr[high]
            if (arr[low] == arr[mid] && arr[mid] == arr[high]) {
                low++;
                high--;
                continue;
            }
            
            if (arr[low] <= arr[mid]) {
                // Left half is sorted
                if (arr[low] <= k && k < arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                // Right half is sorted
                if (arr[mid] < k && k <= arr[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        
        return false;
    }
    
    // ============================
    // EXTENSION: Find all occurrences
    // ============================
    
    /**
     * Find all indices where target appears
     */
    public static List<Integer> searchAll(int[] arr, int n, int k) {
        List<Integer> result = new ArrayList<>();
        int first = search(arr, n, k);
        
        if (first == -1) {
            return result;
        }
        
        // Add first occurrence
        result.add(first);
        
        // Search left for more occurrences
        int left = first - 1;
        while (left >= 0 && arr[left] == k) {
            result.add(left);
            left--;
        }
        
        // Search right for more occurrences
        int right = first + 1;
        while (right < n && arr[right] == k) {
            result.add(right);
            right++;
        }
        
        // Sort indices
        Collections.sort(result);
        return result;
    }
    
    // ============================
    // EXTENSION: Search with bounds
    // ============================
    
    /**
     * Search for target within specific bounds
     */
    public static int searchInRange(int[] arr, int k, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (arr[mid] == k) {
                return mid;
            }
            
            if (arr[left] <= arr[mid]) {
                // Left half is sorted
                if (arr[left] <= k && k < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // Right half is sorted
                if (arr[mid] < k && k <= arr[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }
        
        return -1;
    }
    
    // ============================
    // EXTENSION: Search in rotated sorted array II
    // ============================
    
    /**
     * Return leftmost occurrence in rotated sorted array
     */
    public static int searchLeftmost(int[] arr, int n, int k) {
        int index = search(arr, n, k);
        
        if (index == -1) {
            return -1;
        }
        
        // Move left to find first occurrence
        while (index > 0 && arr[index - 1] == k) {
            index--;
        }
        
        return index;
    }
    
    /**
     * Return rightmost occurrence in rotated sorted array
     */
    public static int searchRightmost(int[] arr, int n, int k) {
        int index = search(arr, n, k);
        
        if (index == -1) {
            return -1;
        }
        
        // Move right to find last occurrence
        while (index < n - 1 && arr[index + 1] == k) {
            index++;
        }
        
        return index;
    }
    
    // ============================
    // EXTENSION: Search for range
    // ============================
    
    /**
     * Find range [first, last] where target appears
     */
    public static int[] searchRange(int[] arr, int n, int k) {
        int first = searchLeftmost(arr, n, k);
        int last = searchRightmost(arr, n, k);
        return new int[]{first, last};
    }
    
    // ============================
    // EXTENSION: Search for closest element
    // ============================
    
    /**
     * Find element closest to target
     */
    public static int searchClosest(int[] arr, int n, int k) {
        int low = 0, high = n - 1;
        int closest = -1;
        int minDiff = Integer.MAX_VALUE;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Update closest
            int diff = Math.abs(arr[mid] - k);
            if (diff < minDiff) {
                minDiff = diff;
                closest = mid;
            }
            
            if (arr[mid] == k) {
                return mid;
            }
            
            if (arr[low] <= arr[mid]) {
                // Left half is sorted
                if (arr[low] <= k && k < arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                // Right half is sorted
                if (arr[mid] < k && k <= arr[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        
        return closest;
    }
    
    // ============================
    // EXTENSION: Search for element with custom comparator
    // ============================
    
    /**
     * Generic search with comparator
     */
    public static <T extends Comparable<T>> int searchGeneric(T[] arr, T target) {
        int low = 0, high = arr.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid].compareTo(target) == 0) {
                return mid;
            }
            
            // Check which half is sorted
            if (arr[low].compareTo(arr[mid]) <= 0) {
                // Left half is sorted
                if (arr[low].compareTo(target) <= 0 && target.compareTo(arr[mid]) < 0) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                // Right half is sorted
                if (arr[mid].compareTo(target) < 0 && target.compareTo(arr[high]) <= 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        
        return -1;
    }
    
    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        System.out.println("=== Search in Rotated Sorted Array ===");
        
        // Test Case 1: Basic example
        System.out.println("\nTest Case 1: Basic Example");
        int[] arr1 = {4, 5, 6, 7, 0, 1, 2};
        int target1 = 0;
        int result1 = search(arr1, arr1.length, target1);
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Target: " + target1);
        System.out.println("Index: " + result1);
        System.out.println("Expected: 4");
        
        // Test different approaches
        System.out.println("\nDifferent Approaches:");
        System.out.println("Standard: " + result1);
        System.out.println("Two-pass: " + searchTwoPass(arr1, arr1.length, target1));
        System.out.println("Recursive: " + searchRecursive(arr1, arr1.length, target1));
        
        // Test Case 2: Target not found
        System.out.println("\nTest Case 2: Target Not Found");
        int target2 = 3;
        int result2 = search(arr1, arr1.length, target2);
        System.out.println("Target: " + target2);
        System.out.println("Index: " + result2);
        System.out.println("Expected: -1");
        
        // Test Case 3: Target at pivot
        System.out.println("\nTest Case 3: Target at Pivot");
        int[] arr3 = {4, 5, 6, 7, 0, 1, 2};
        int target3 = 4;
        int result3 = search(arr3, arr3.length, target3);
        System.out.println("Array: " + Arrays.toString(arr3));
        System.out.println("Target: " + target3);
        System.out.println("Index: " + result3);
        System.out.println("Expected: 0");
        
        // Test Case 4: Array not rotated
        System.out.println("\nTest Case 4: Array Not Rotated");
        int[] arr4 = {0, 1, 2, 3, 4, 5};
        int target4 = 3;
        int result4 = search(arr4, arr4.length, target4);
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("Target: " + target4);
        System.out.println("Index: " + result4);
        System.out.println("Expected: 3");
        
        // Test Case 5: Single element
        System.out.println("\nTest Case 5: Single Element");
        int[] arr5 = {5};
        int[] testTargets5 = {5, 3};
        for (int target : testTargets5) {
            System.out.println("Target " + target + ": " + search(arr5, arr5.length, target));
        }
        
        // Test Case 6: Two elements
        System.out.println("\nTest Case 6: Two Elements");
        int[][] twoElementArrays = {
            {1, 2},
            {2, 1}
        };
        for (int[] arr : twoElementArrays) {
            System.out.println("Array: " + Arrays.toString(arr));
            for (int target = 0; target <= 3; target++) {
                int idx = search(arr, arr.length, target);
                if (idx != -1) {
                    System.out.println("  Target " + target + " at index " + idx);
                }
            }
        }
        
        // Test with duplicates
        System.out.println("\n=== Testing with Duplicates ===");
        int[] dupArr = {2, 2, 2, 0, 1, 2};
        int dupTarget = 0;
        boolean dupResult = searchWithDuplicates(dupArr, dupTarget);
        System.out.println("Array with duplicates: " + Arrays.toString(dupArr));
        System.out.println("Target: " + dupTarget);
        System.out.println("Found: " + dupResult);
        
        // Test search all occurrences
        System.out.println("\n=== Testing All Occurrences ===");
        int[] allArr = {5, 6, 7, 1, 2, 2, 2, 3, 4};
        int allTarget = 2;
        List<Integer> allIndices = searchAll(allArr, allArr.length, allTarget);
        System.out.println("Array: " + Arrays.toString(allArr));
        System.out.println("Target: " + allTarget);
        System.out.println("All indices: " + allIndices);
        
        // Test search range
        System.out.println("\n=== Testing Search Range ===");
        int[] rangeArr = {4, 5, 6, 7, 0, 1, 2, 2, 2, 3};
        int rangeTarget = 2;
        int[] range = searchRange(rangeArr, rangeArr.length, rangeTarget);
        System.out.println("Array: " + Arrays.toString(rangeArr));
        System.out.println("Target: " + rangeTarget);
        System.out.println("Range: [" + range[0] + ", " + range[1] + "]");
        
        // Test search closest
        System.out.println("\n=== Testing Search Closest ===");
        int[] closeArr = {4, 5, 6, 7, 0, 1, 2};
        int[] closeTargets = {3, 8, -1};
        for (int target : closeTargets) {
            int closest = searchClosest(closeArr, closeArr.length, target);
            System.out.println("Target: " + target + " -> Closest: index " + closest + 
                             " (value " + closeArr[closest] + ")");
        }
        
        // Test generic search
        System.out.println("\n=== Testing Generic Search ===");
        String[] strArr = {"cat", "dog", "egg", "apple", "banana"};
        Arrays.sort(strArr); // Sort then rotate for testing
        String strTarget = "egg";
        int strIndex = searchGeneric(strArr, strTarget);
        System.out.println("String array: " + Arrays.toString(strArr));
        System.out.println("Target: " + strTarget);
        System.out.println("Index: " + strIndex);
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[] perfArray = new int[10000000];
        for (int i = 0; i < perfArray.length; i++) {
            perfArray[i] = (i + 3000000) % perfArray.length;
        }
        int perfTarget = 5000000;
        
        long startTime = System.currentTimeMillis();
        int perfResult = search(perfArray, perfArray.length, perfTarget);
        long endTime = System.currentTimeMillis();
        
        System.out.println("Array size: " + perfArray.length);
        System.out.println("Target: " + perfTarget);
        System.out.println("Index: " + perfResult);
        System.out.println("Time: " + (endTime - startTime) + "ms");
        
        // Compare with linear search
        startTime = System.currentTimeMillis();
        int linearResult = -1;
        for (int i = 0; i < perfArray.length; i++) {
            if (perfArray[i] == perfTarget) {
                linearResult = i;
                break;
            }
        }
        endTime = System.currentTimeMillis();
        System.out.println("Linear search time: " + (endTime - startTime) + "ms");
        
        // Edge cases visualization
        System.out.println("\n=== Edge Cases Visualization ===");
        visualizeSearch(arr1, target1, 4);
        
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
            
            // Choose target (might not be in array)
            int target = rand.nextInt(1100) - 50;
            
            // Get binary search result
            int bsResult = search(rotated, rotated.length, target);
            
            // Get linear search result (ground truth)
            int linearIdx = -1;
            for (int j = 0; j < rotated.length; j++) {
                if (rotated[j] == target) {
                    linearIdx = j;
                    break;
                }
            }
            
            if (bsResult == linearIdx) {
                passed++;
            } else {
                System.out.println("Test failed!");
                System.out.println("Original sorted: " + Arrays.toString(sorted));
                System.out.println("Rotated by " + rotation + ": " + Arrays.toString(rotated));
                System.out.println("Target: " + target);
                System.out.println("Binary Search: " + bsResult);
                System.out.println("Linear Search: " + linearIdx);
                break;
            }
        }
        
        System.out.println("Passed: " + passed + "/" + numTests);
        
        // Test with manual rotations
        System.out.println("\n=== Testing Manual Rotations ===");
        int[] manual = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println("Original: " + Arrays.toString(manual));
        
        for (int rotation = 0; rotation <= manual.length; rotation++) {
            int[] rotated = manual.clone();
            rotateArray(rotated, rotation);
            
            for (int target = 1; target <= 8; target++) {
                int idx = search(rotated, rotated.length, target);
                int expected = (target - 1 + rotation) % manual.length;
                if (idx != expected) {
                    System.out.println("Error: Rotation " + rotation + 
                                     ", target " + target + 
                                     ", got " + idx + ", expected " + expected);
                }
            }
        }
    }
    
    // ============================
    // DEBUGGING AND VISUALIZATION
    // ============================
    
    /**
     * Visualize the search process
     */
    public static void visualizeSearch(int[] arr, int target, int expectedIndex) {
        System.out.println("\n=== Visualizing Search in Rotated Sorted Array ===");
        System.out.println("Array: " + Arrays.toString(arr));
        System.out.println("Target: " + target);
        System.out.println("Expected index: " + expectedIndex);
        System.out.println("Length: " + arr.length);
        
        int low = 0;
        int high = arr.length - 1;
        int step = 1;
        
        System.out.println("\nBinary search steps:");
        
        while (low <= high) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  Search range: [" + low + ", " + high + "]");
            System.out.println("  Range values: [" + arr[low] + ".." + arr[high] + "]");
            
            int mid = low + (high - low) / 2;
            System.out.println("  Mid index: " + mid + ", value: " + arr[mid]);
            
            if (arr[mid] == target) {
                System.out.println("  Found target at index " + mid + "!");
                return;
            }
            
            if (arr[low] <= arr[mid]) {
                System.out.println("  Left half [" + low + ".." + mid + "] is sorted");
                System.out.println("  Checking if target " + target + " is in [" + 
                                 arr[low] + ".." + arr[mid] + "]");
                
                if (arr[low] <= target && target < arr[mid]) {
                    System.out.println("  Yes → search left half");
                    high = mid - 1;
                } else {
                    System.out.println("  No → search right half");
                    low = mid + 1;
                }
            } else {
                System.out.println("  Right half [" + mid + ".." + high + "] is sorted");
                System.out.println("  Checking if target " + target + " is in [" + 
                                 arr[mid] + ".." + arr[high] + "]");
                
                if (arr[mid] < target && target <= arr[high]) {
                    System.out.println("  Yes → search right half");
                    low = mid + 1;
                } else {
                    System.out.println("  No → search left half");
                    high = mid - 1;
                }
            }
            step++;
        }
        
        System.out.println("\nTarget not found in array.");
        System.out.println("Final search range: [" + low + ", " + high + "]");
    }
    
    // ============================
    // ADDITIONAL UTILITIES
    // ============================
    
    /**
     * Rotate array right by k positions
     */
    private static void rotateArray(int[] arr, int k) {
        k = k % arr.length;
        if (k == 0) return;
        
        reverse(arr, 0, arr.length - 1);
        reverse(arr, 0, k - 1);
        reverse(arr, k, arr.length - 1);
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
    
    /**
     * Validate search result
     */
    public static boolean validateSearch(int[] arr, int target, int result) {
        if (result == -1) {
            // Check that target is not in array
            for (int num : arr) {
                if (num == target) {
                    return false;
                }
            }
            return true;
        } else {
            // Check that result is within bounds and has target value
            return result >= 0 && result < arr.length && arr[result] == target;
        }
    }
    
    /**
     * Generate test cases
     */
    public static int[][] generateTestCases(int n, int numTests) {
        Random rand = new Random();
        List<int[]> testCases = new ArrayList<>();
        
        for (int i = 0; i < numTests; i++) {
            int[] arr = new int[n];
            for (int j = 0; j < n; j++) {
                arr[j] = rand.nextInt(1000);
            }
            Arrays.sort(arr);
            
            // Rotate
            int rotation = rand.nextInt(n);
            int[] rotated = new int[n];
            for (int j = 0; j < n; j++) {
                rotated[j] = arr[(j + rotation) % n];
            }
            
            testCases.add(rotated);
        }
        
        return testCases.toArray(new int[0][]);
    }
    
    /**
     * Search with early exit optimizations
     */
    public static int searchOptimized(int[] arr, int n, int k) {
        // Early exit: check boundaries
        if (n == 0) return -1;
        if (arr[0] == k) return 0;
        if (arr[n-1] == k) return n-1;
        
        // If array not rotated, use standard binary search
        if (arr[0] < arr[n-1]) {
            return Arrays.binarySearch(arr, k);
        }
        
        // Otherwise use rotated search
        return search(arr, n, k);
    }
    
    /**
     * Find insertion position in rotated sorted array
     */
    public static int searchInsertRotated(int[] arr, int n, int k) {
        int low = 0, high = n - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == k) {
                return mid;
            }
            
            if (arr[low] <= arr[mid]) {
                // Left half is sorted
                if (arr[low] <= k && k < arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                // Right half is sorted
                if (arr[mid] < k && k <= arr[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        
        return low; // Insertion position
    }
}