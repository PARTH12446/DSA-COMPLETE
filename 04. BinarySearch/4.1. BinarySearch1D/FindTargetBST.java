import java.util.*;

/**
 * Binary Search Algorithm - Various Implementations and Applications
 * 
 * Binary search is a divide-and-conquer algorithm that finds the position
 * of a target value within a sorted array by repeatedly dividing the search
 * interval in half.
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1) for iterative, O(log n) for recursive
 */
public class FindTargetBST {

    // ============================
    // APPROACH 1: Standard Iterative Binary Search
    // ============================
    
    /**
     * Standard iterative binary search
     * Returns index of target if found, -1 otherwise
     * 
     * @param nums   Sorted array in non-decreasing order
     * @param target Value to search for
     * @return Index of target, or -1 if not found
     */
    public static int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;

        while (low <= high) {
            // Prevent integer overflow: use low + (high - low) / 2
            int mid = low + (high - low) / 2;

            if (nums[mid] == target) {
                return mid; // Found target
            } else if (nums[mid] > target) {
                // Target is in left half
                high = mid - 1;
            } else {
                // Target is in right half
                low = mid + 1;
            }
        }

        return -1; // Target not found
    }

    // ============================
    // APPROACH 2: Recursive Binary Search
    // ============================
    
    /**
     * Recursive binary search implementation
     * 
     * @param nums   Sorted array
     * @param target Value to search for
     * @return Index of target, or -1 if not found
     */
    public static int searchRecursive(int[] nums, int target) {
        return binarySearchRecursive(nums, target, 0, nums.length - 1);
    }

    private static int binarySearchRecursive(int[] nums, int target, int low, int high) {
        // Base case: search space is empty
        if (low > high) {
            return -1;
        }

        int mid = low + (high - low) / 2;

        if (nums[mid] == target) {
            return mid;
        } else if (nums[mid] > target) {
            // Search left half
            return binarySearchRecursive(nums, target, low, mid - 1);
        } else {
            // Search right half
            return binarySearchRecursive(nums, target, mid + 1, high);
        }
    }

    // ============================
    // APPROACH 3: Binary Search with Comparator
    // ============================
    
    /**
     * Generic binary search that works with any comparable type
     * 
     * @param <T> Type that implements Comparable
     * @param arr Sorted array
     * @param target Target value
     * @return Index of target, or -1 if not found
     */
    public static <T extends Comparable<T>> int genericBinarySearch(T[] arr, T target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int comparison = arr[mid].compareTo(target);

            if (comparison == 0) {
                return mid;
            } else if (comparison > 0) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    // ============================
    // APPROACH 4: Binary Search for Real Numbers
    // ============================
    
    /**
     * Binary search for finding root of a function f(x) = 0
     * in range [low, high] with given tolerance
     * 
     * @param low Lower bound
     * @param high Upper bound
     * @param tolerance Precision tolerance
     * @return Approximate root of function
     */
    public static double binarySearchReal(double low, double high, double tolerance) {
        // Example: Find square root of 2
        double target = 2.0;
        
        while (high - low > tolerance) {
            double mid = low + (high - low) / 2.0;
            double midSquared = mid * mid;
            
            if (Math.abs(midSquared - target) < tolerance) {
                return mid;
            } else if (midSquared > target) {
                high = mid;
            } else {
                low = mid;
            }
        }
        
        return low + (high - low) / 2.0;
    }

    // ============================
    // APPROACH 5: Lower Bound (First occurrence)
    // ============================
    
    /**
     * Find first occurrence of target (lower bound)
     * Returns index of first element >= target
     * 
     * @param nums Sorted array
     * @param target Target value
     * @return Index of first occurrence, or insertion point if not found
     */
    public static int lowerBound(int[] nums, int target) {
        int low = 0;
        int high = nums.length; // Note: high = nums.length, not nums.length - 1
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (nums[mid] >= target) {
                high = mid; // Move left, including mid
            } else {
                low = mid + 1; // Move right, excluding mid
            }
        }
        
        return low; // First position where nums[i] >= target
    }

    // ============================
    // APPROACH 6: Upper Bound (Last occurrence + 1)
    // ============================
    
    /**
     * Find first element > target (upper bound)
     * Returns index of first element > target
     * 
     * @param nums Sorted array
     * @param target Target value
     * @return Index of first element > target
     */
    public static int upperBound(int[] nums, int target) {
        int low = 0;
        int high = nums.length;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (nums[mid] > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        
        return low; // First position where nums[i] > target
    }

    // ============================
    // APPROACH 7: Search in Rotated Sorted Array
    // ============================
    
    /**
     * Search in rotated sorted array (no duplicates)
     * Problem: LeetCode 33
     * 
     * @param nums Rotated sorted array
     * @param target Target value
     * @return Index of target, or -1 if not found
     */
    public static int searchInRotatedArray(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (nums[mid] == target) {
                return mid;
            }
            
            // Check which side is sorted
            if (nums[low] <= nums[mid]) {
                // Left side is sorted
                if (nums[low] <= target && target < nums[mid]) {
                    // Target is in sorted left side
                    high = mid - 1;
                } else {
                    // Target is in right side
                    low = mid + 1;
                }
            } else {
                // Right side is sorted
                if (nums[mid] < target && target <= nums[high]) {
                    // Target is in sorted right side
                    low = mid + 1;
                } else {
                    // Target is in left side
                    high = mid - 1;
                }
            }
        }
        
        return -1;
    }

    // ============================
    // APPROACH 8: Search in 2D Matrix
    // ============================
    
    /**
     * Search in row-wise and column-wise sorted matrix
     * Problem: LeetCode 74
     * 
     * @param matrix 2D sorted matrix
     * @param target Target value
     * @return true if target exists, false otherwise
     */
    public static boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        
        // Treat matrix as 1D array
        int low = 0;
        int high = rows * cols - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int midValue = matrix[mid / cols][mid % cols];
            
            if (midValue == target) {
                return true;
            } else if (midValue < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return false;
    }

    // ============================
    // APPROACH 9: Find Peak Element
    // ============================
    
    /**
     * Find a peak element (greater than neighbors)
     * Problem: LeetCode 162
     * 
     * @param nums Array
     * @return Index of a peak element
     */
    public static int findPeakElement(int[] nums) {
        int low = 0, high = nums.length - 1;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (nums[mid] > nums[mid + 1]) {
                // Peak is on left side (including mid)
                high = mid;
            } else {
                // Peak is on right side
                low = mid + 1;
            }
        }
        
        return low; // When low == high, we found a peak
    }

    // ============================
    // APPROACH 10: Find Minimum in Rotated Sorted Array
    // ============================
    
    /**
     * Find minimum element in rotated sorted array
     * Problem: LeetCode 153
     * 
     * @param nums Rotated sorted array
     * @return Minimum element
     */
    public static int findMinInRotatedArray(int[] nums) {
        int low = 0, high = nums.length - 1;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (nums[mid] > nums[high]) {
                // Minimum is in right half
                low = mid + 1;
            } else {
                // Minimum is in left half (including mid)
                high = mid;
            }
        }
        
        return nums[low];
    }

    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("=== Binary Search Algorithm Demonstrations ===");
        
        // Test Case 1: Standard binary search
        System.out.println("\n=== Test Case 1: Standard Binary Search ===");
        int[] arr1 = {1, 3, 5, 7, 9, 11, 13, 15};
        int target1 = 7;
        
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("Target: " + target1);
        System.out.println("Iterative: Index = " + search(arr1, target1));
        System.out.println("Recursive: Index = " + searchRecursive(arr1, target1));
        System.out.println("Expected: 3");
        
        // Test Case 2: Target not found
        System.out.println("\n=== Test Case 2: Target Not Found ===");
        int target2 = 8;
        System.out.println("Target: " + target2);
        System.out.println("Result: " + search(arr1, target2));
        System.out.println("Expected: -1");
        
        // Test Case 3: Empty array
        System.out.println("\n=== Test Case 3: Empty Array ===");
        int[] arr2 = {};
        int target3 = 5;
        System.out.println("Array: []");
        System.out.println("Target: " + target3);
        System.out.println("Result: " + search(arr2, target3));
        System.out.println("Expected: -1");
        
        // Test Case 4: Single element
        System.out.println("\n=== Test Case 4: Single Element ===");
        int[] arr3 = {5};
        int target4a = 5, target4b = 3;
        System.out.println("Array: [5]");
        System.out.println("Target 5: " + search(arr3, target4a));
        System.out.println("Target 3: " + search(arr3, target4b));
        
        // Test Lower and Upper Bound
        System.out.println("\n=== Test Case 5: Lower and Upper Bound ===");
        int[] arr4 = {1, 2, 2, 2, 3, 4, 5};
        int target5 = 2;
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("Target: " + target5);
        System.out.println("Lower Bound (first >= 2): " + lowerBound(arr4, target5));
        System.out.println("Upper Bound (first > 2): " + upperBound(arr4, target5));
        System.out.println("Count of 2s: " + (upperBound(arr4, target5) - lowerBound(arr4, target5)));
        
        // Test Search in Rotated Array
        System.out.println("\n=== Test Case 6: Search in Rotated Array ===");
        int[] rotated = {4, 5, 6, 7, 0, 1, 2};
        int target6 = 0;
        System.out.println("Rotated Array: " + Arrays.toString(rotated));
        System.out.println("Target: " + target6);
        System.out.println("Result: " + searchInRotatedArray(rotated, target6));
        System.out.println("Expected: 4");
        
        // Test Search in 2D Matrix
        System.out.println("\n=== Test Case 7: Search in 2D Matrix ===");
        int[][] matrix = {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 60}
        };
        int target7 = 16;
        System.out.println("Matrix:");
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
        System.out.println("Target: " + target7);
        System.out.println("Exists: " + searchMatrix(matrix, target7));
        
        // Test Find Peak Element
        System.out.println("\n=== Test Case 8: Find Peak Element ===");
        int[] arr5 = {1, 2, 3, 1};
        System.out.println("Array: " + Arrays.toString(arr5));
        System.out.println("Peak Element Index: " + findPeakElement(arr5));
        System.out.println("Peak Value: " + arr5[findPeakElement(arr5)]);
        
        // Test Find Minimum in Rotated Array
        System.out.println("\n=== Test Case 9: Find Minimum in Rotated Array ===");
        int[] rotated2 = {3, 4, 5, 1, 2};
        System.out.println("Rotated Array: " + Arrays.toString(rotated2));
        System.out.println("Minimum: " + findMinInRotatedArray(rotated2));
        
        // Test Generic Binary Search
        System.out.println("\n=== Test Case 10: Generic Binary Search ===");
        String[] words = {"apple", "banana", "cherry", "date", "fig", "grape"};
        String target10 = "cherry";
        System.out.println("Words: " + Arrays.toString(words));
        System.out.println("Target: '" + target10 + "'");
        System.out.println("Index: " + genericBinarySearch(words, target10));
        
        // Test Binary Search for Real Numbers
        System.out.println("\n=== Test Case 11: Binary Search for Real Numbers ===");
        System.out.println("Finding square root of 2:");
        double sqrt2 = binarySearchReal(0, 2, 0.000001);
        System.out.println("Approximate sqrt(2): " + sqrt2);
        System.out.println("Actual sqrt(2): " + Math.sqrt(2));
        
        // Interactive test
        System.out.println("\n=== Interactive Test ===");
        System.out.print("Enter array size: ");
        int n = sc.nextInt();
        
        int[] arr = new int[n];
        System.out.print("Enter " + n + " sorted integers: ");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }
        
        System.out.print("Enter target to search: ");
        int target = sc.nextInt();
        
        int result = search(arr, target);
        if (result != -1) {
            System.out.println("Target found at index: " + result);
        } else {
            System.out.println("Target not found in array");
        }
        
        // Additional: Find insertion point
        System.out.println("\n=== Insertion Point Information ===");
        int insertionPoint = lowerBound(arr, target);
        System.out.println("If target not found, it should be inserted at index: " + insertionPoint);
        
        sc.close();
    }
    
    // ============================
    // ADDITIONAL UTILITIES
    // ============================
    
    /**
     * Check if array is sorted (for validation)
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
     * Binary search with error handling
     */
    public static int safeBinarySearch(int[] nums, int target) {
        if (nums == null) {
            throw new IllegalArgumentException("Array cannot be null");
        }
        
        if (!isSorted(nums)) {
            throw new IllegalArgumentException("Array must be sorted");
        }
        
        return search(nums, target);
    }
    
    /**
     * Find closest element to target
     * Returns index of element closest to target
     */
    public static int findClosest(int[] nums, int target) {
        int low = 0, high = nums.length - 1;
        
        while (low < high - 1) {
            int mid = low + (high - low) / 2;
            
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                low = mid;
            } else {
                high = mid;
            }
        }
        
        // Now low and high are adjacent
        if (Math.abs(nums[low] - target) <= Math.abs(nums[high] - target)) {
            return low;
        } else {
            return high;
        }
    }
    
    /**
     * Find first bad version (like in software testing)
     * Assumes there's a boolean function isBadVersion(version)
     */
    public static int firstBadVersion(int n) {
        int low = 1, high = n;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            // In real scenario: if (isBadVersion(mid)) {
            // For this example, we'll simulate with target
            boolean isBad = false; // Placeholder
            
            if (isBad) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        
        return low;
    }
    
    /**
     * Binary search on answer (for optimization problems)
     * Example: Find minimum capacity to ship packages within D days
     */
    public static int shipWithinDays(int[] weights, int days) {
        // Find maximum weight and total weight
        int maxWeight = 0, totalWeight = 0;
        for (int weight : weights) {
            maxWeight = Math.max(maxWeight, weight);
            totalWeight += weight;
        }
        
        // Binary search on capacity
        int low = maxWeight, high = totalWeight;
        
        while (low < high) {
            int mid = low + (high - low) / 2;
            
            if (canShip(weights, days, mid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        
        return low;
    }
    
    private static boolean canShip(int[] weights, int days, int capacity) {
        int currentLoad = 0;
        int daysNeeded = 1;
        
        for (int weight : weights) {
            if (currentLoad + weight > capacity) {
                daysNeeded++;
                currentLoad = 0;
            }
            currentLoad += weight;
        }
        
        return daysNeeded <= days;
    }
}