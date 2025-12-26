import java.util.*;

/** 
 * Problem: Find Floor and Ceil in a Sorted Array
 * 
 * Floor: Largest element ≤ x
 * Ceil: Smallest element ≥ x
 * 
 * Applications: Database range queries, nearest values, interpolation
 */
public class CeilTheFloor {

    // ============================
    // APPROACH 1: Two Binary Searches (Original)
    // ============================
    
    /**
     * Returns an int array of size 2: {floor, ceil}
     * If floor or ceil does not exist, the corresponding value is -1.
     * 
     * Time: O(log n) - Two binary searches
     * Space: O(1)
     */
    public static int[] getFloorAndCeil(int[] arr, int n, int x) {
        int low = 0;
        int high = n - 1;
        int upper = -1; // ceil

        // Find ceil (smallest element >= x)
        while (low <= high) {
            int mid = low + (high - low) / 2; // Avoid overflow
            if (arr[mid] >= x) {
                upper = arr[mid];
                high = mid - 1; // Look for smaller ceil on left
            } else {
                low = mid + 1;
            }
        }

        // Reset for floor search
        low = 0;
        high = n - 1;
        int lower = -1; // floor

        // Find floor (largest element <= x)
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= x) {
                lower = arr[mid];
                low = mid + 1; // Look for larger floor on right
            } else {
                high = mid - 1;
            }
        }

        return new int[]{lower, upper};
    }
    
    // ============================
    // APPROACH 2: Single Binary Search
    // ============================
    
    /**
     * Find floor and ceil in single binary search
     * More efficient but slightly more complex
     * 
     * Time: O(log n), Space: O(1)
     */
    public static int[] getFloorAndCeilSingleBS(int[] arr, int n, int x) {
        int floor = -1, ceil = -1;
        int low = 0, high = n - 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            if (arr[mid] == x) {
                // Exact match - both floor and ceil are x
                return new int[]{arr[mid], arr[mid]};
            }
            else if (arr[mid] < x) {
                // arr[mid] is a candidate for floor
                floor = arr[mid];
                low = mid + 1;
            }
            else { // arr[mid] > x
                // arr[mid] is a candidate for ceil
                ceil = arr[mid];
                high = mid - 1;
            }
        }
        
        return new int[]{floor, ceil};
    }
    
    // ============================
    // APPROACH 3: Binary Search with Standard Library
    // ============================
    
    /**
     * Using Arrays.binarySearch() for cleaner code
     * 
     * Arrays.binarySearch() returns:
     * - index if found
     * - (-(insertion point) - 1) if not found
     */
    public static int[] getFloorAndCeilStandardLib(int[] arr, int n, int x) {
        int floor = -1, ceil = -1;
        
        int index = Arrays.binarySearch(arr, x);
        
        if (index >= 0) {
            // Exact match found
            floor = ceil = arr[index];
        } else {
            // insertionPoint = -(index + 1)
            int insertionPoint = -index - 1;
            
            if (insertionPoint > 0) {
                floor = arr[insertionPoint - 1];
            }
            if (insertionPoint < n) {
                ceil = arr[insertionPoint];
            }
        }
        
        return new int[]{floor, ceil};
    }
    
    // ============================
    // APPROACH 4: Linear Scan (for small arrays or unsorted)
    // ============================
    
    /**
     * Linear approach - works for unsorted arrays too
     * 
     * Time: O(n), Space: O(1)
     */
    public static int[] getFloorAndCeilLinear(int[] arr, int n, int x) {
        int floor = Integer.MIN_VALUE;
        int ceil = Integer.MAX_VALUE;
        
        for (int num : arr) {
            if (num <= x && num > floor) {
                floor = num;
            }
            if (num >= x && num < ceil) {
                ceil = num;
            }
        }
        
        // Adjust sentinel values
        if (floor == Integer.MIN_VALUE) floor = -1;
        if (ceil == Integer.MAX_VALUE) ceil = -1;
        
        return new int[]{floor, ceil};
    }
    
    // ============================
    // APPROACH 5: TreeSet (for dynamic array with insertions)
    // ============================
    
    /**
     * Using TreeSet for O(log n) queries with dynamic updates
     * Useful when array changes frequently
     */
    public static class DynamicFloorCeil {
        private TreeSet<Integer> treeSet;
        
        public DynamicFloorCeil() {
            treeSet = new TreeSet<>();
        }
        
        public void insert(int num) {
            treeSet.add(num);
        }
        
        public int[] getFloorAndCeil(int x) {
            Integer floor = treeSet.floor(x);  // largest ≤ x
            Integer ceil = treeSet.ceiling(x); // smallest ≥ x
            
            return new int[]{
                floor != null ? floor : -1,
                ceil != null ? ceil : -1
            };
        }
        
        public void remove(int num) {
            treeSet.remove(num);
        }
    }
    
    // ============================
    // EXTENSION: Find Floor and Ceil in BST
    // ============================
    
    static class TreeNode {
        int val;
        TreeNode left, right;
        TreeNode(int x) { val = x; }
    }
    
    /**
     * Find floor and ceil in Binary Search Tree
     * 
     * Time: O(h) where h is height of BST
     */
    public static int[] getFloorAndCeilBST(TreeNode root, int x) {
        int floor = -1, ceil = -1;
        TreeNode curr = root;
        
        while (curr != null) {
            if (curr.val == x) {
                return new int[]{curr.val, curr.val};
            }
            else if (curr.val < x) {
                floor = curr.val;
                curr = curr.right; // Look for larger floor
            }
            else {
                ceil = curr.val;
                curr = curr.left; // Look for smaller ceil
            }
        }
        
        return new int[]{floor, ceil};
    }
    
    // ============================
    // EXTENSION: Find k nearest elements
    // ============================
    
    /**
     * Find k elements closest to x
     * Returns floor and ceil if they exist within k nearest
     */
    public static List<Integer> findKNearest(int[] arr, int n, int x, int k) {
        List<Integer> result = new ArrayList<>();
        
        // Find insertion point
        int index = Arrays.binarySearch(arr, x);
        int left, right;
        
        if (index >= 0) {
            // x found in array
            left = index - 1;
            right = index + 1;
            result.add(arr[index]);
            k--;
        } else {
            // x not found
            int insertionPoint = -index - 1;
            left = insertionPoint - 1;
            right = insertionPoint;
        }
        
        // Expand to find k nearest
        while (k > 0 && (left >= 0 || right < n)) {
            if (left < 0) {
                result.add(arr[right++]);
            } else if (right >= n) {
                result.add(arr[left--]);
            } else {
                // Compare which is closer
                int leftDiff = Math.abs(arr[left] - x);
                int rightDiff = Math.abs(arr[right] - x);
                
                if (leftDiff <= rightDiff) {
                    result.add(arr[left--]);
                } else {
                    result.add(arr[right++]);
                }
            }
            k--;
        }
        
        return result;
    }
    
    // ============================
    // TESTING AND EXAMPLES
    // ============================
    
    public static void main(String[] args) {
        System.out.println("=== Testing Floor and Ceil ===");
        
        // Test Case 1: Basic example
        int[] arr1 = {1, 2, 3, 4, 8, 10, 10, 12, 19};
        int x1 = 5;
        System.out.println("\nTest Case 1:");
        System.out.println("Array: " + Arrays.toString(arr1));
        System.out.println("x = " + x1);
        
        int[] result1 = getFloorAndCeil(arr1, arr1.length, x1);
        System.out.println("Two BS: Floor = " + result1[0] + ", Ceil = " + result1[1]);
        System.out.println("Expected: Floor = 4, Ceil = 8");
        
        int[] result1b = getFloorAndCeilSingleBS(arr1, arr1.length, x1);
        System.out.println("Single BS: Floor = " + result1b[0] + ", Ceil = " + result1b[1]);
        
        int[] result1c = getFloorAndCeilStandardLib(arr1, arr1.length, x1);
        System.out.println("Standard Lib: Floor = " + result1c[0] + ", Ceil = " + result1c[1]);
        
        // Test Case 2: Exact match
        int[] arr2 = {1, 2, 8, 10, 10, 12, 19};
        int x2 = 10;
        System.out.println("\nTest Case 2 (Exact match):");
        System.out.println("Array: " + Arrays.toString(arr2));
        System.out.println("x = " + x2);
        
        int[] result2 = getFloorAndCeil(arr2, arr2.length, x2);
        System.out.println("Result: Floor = " + result2[0] + ", Ceil = " + result2[1]);
        System.out.println("Expected: Floor = 10, Ceil = 10");
        
        // Test Case 3: x smaller than all elements
        int[] arr3 = {10, 20, 30, 40};
        int x3 = 5;
        System.out.println("\nTest Case 3 (x smaller than all):");
        System.out.println("Array: " + Arrays.toString(arr3));
        System.out.println("x = " + x3);
        
        int[] result3 = getFloorAndCeil(arr3, arr3.length, x3);
        System.out.println("Result: Floor = " + result3[0] + ", Ceil = " + result3[1]);
        System.out.println("Expected: Floor = -1, Ceil = 10");
        
        // Test Case 4: x larger than all elements
        int[] arr4 = {10, 20, 30, 40};
        int x4 = 50;
        System.out.println("\nTest Case 4 (x larger than all):");
        System.out.println("Array: " + Arrays.toString(arr4));
        System.out.println("x = " + x4);
        
        int[] result4 = getFloorAndCeil(arr4, arr4.length, x4);
        System.out.println("Result: Floor = " + result4[0] + ", Ceil = " + result4[1]);
        System.out.println("Expected: Floor = 40, Ceil = -1");
        
        // Test Case 5: Single element array
        int[] arr5 = {7};
        int x5a = 7, x5b = 3, x5c = 10;
        System.out.println("\nTest Case 5 (Single element):");
        System.out.println("Array: " + Arrays.toString(arr5));
        
        System.out.println("x = " + x5a + ": " + Arrays.toString(getFloorAndCeil(arr5, arr5.length, x5a)));
        System.out.println("x = " + x5b + ": " + Arrays.toString(getFloorAndCeil(arr5, arr5.length, x5b)));
        System.out.println("x = " + x5c + ": " + Arrays.toString(getFloorAndCeil(arr5, arr5.length, x5c)));
        
        // Test Case 6: Empty array
        int[] arr6 = {};
        int x6 = 5;
        System.out.println("\nTest Case 6 (Empty array):");
        System.out.println("Array: []");
        System.out.println("x = " + x6);
        
        int[] result6 = getFloorAndCeil(arr6, arr6.length, x6);
        System.out.println("Result: Floor = " + result6[0] + ", Ceil = " + result6[1]);
        System.out.println("Expected: Floor = -1, Ceil = -1");
        
        // Test Case 7: Array with duplicates
        int[] arr7 = {1, 2, 3, 3, 3, 4, 5};
        int x7 = 3;
        System.out.println("\nTest Case 7 (With duplicates):");
        System.out.println("Array: " + Arrays.toString(arr7));
        System.out.println("x = " + x7);
        
        int[] result7 = getFloorAndCeil(arr7, arr7.length, x7);
        System.out.println("Result: Floor = " + result7[0] + ", Ceil = " + result7[1]);
        System.out.println("Expected: Floor = 3, Ceil = 3");
        
        // Test DynamicFloorCeil
        System.out.println("\n=== Testing Dynamic Floor/Ceil with TreeSet ===");
        DynamicFloorCeil dfc = new DynamicFloorCeil();
        dfc.insert(10);
        dfc.insert(20);
        dfc.insert(30);
        dfc.insert(40);
        
        System.out.println("Elements: 10, 20, 30, 40");
        System.out.println("x = 25: " + Arrays.toString(dfc.getFloorAndCeil(25)));
        System.out.println("x = 30: " + Arrays.toString(dfc.getFloorAndCeil(30)));
        System.out.println("x = 5: " + Arrays.toString(dfc.getFloorAndCeil(5)));
        System.out.println("x = 45: " + Arrays.toString(dfc.getFloorAndCeil(45)));
        
        // Test k nearest elements
        System.out.println("\n=== Testing k Nearest Elements ===");
        int[] arr8 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int x8 = 5;
        int k = 4;
        
        System.out.println("Array: " + Arrays.toString(arr8));
        System.out.println("x = " + x8 + ", k = " + k);
        List<Integer> nearest = findKNearest(arr8, arr8.length, x8, k);
        System.out.println(k + " nearest elements: " + nearest);
        
        // Test BST floor and ceil
        System.out.println("\n=== Testing BST Floor and Ceil ===");
        TreeNode root = createBST();
        int x9 = 7;
        int[] bstResult = getFloorAndCeilBST(root, x9);
        System.out.println("BST, x = " + x9 + ": Floor = " + bstResult[0] + ", Ceil = " + bstResult[1]);
        
        // Performance comparison
        System.out.println("\n=== Performance Comparison ===");
        int[] largeArray = new int[1000000];
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = i * 2; // Even numbers only
        }
        int xTest = 567891;
        
        long startTime = System.currentTimeMillis();
        int[] res1 = getFloorAndCeil(largeArray, largeArray.length, xTest);
        long time1 = System.currentTimeMillis() - startTime;
        
        startTime = System.currentTimeMillis();
        int[] res2 = getFloorAndCeilLinear(largeArray, largeArray.length, xTest);
        long time2 = System.currentTimeMillis() - startTime;
        
        System.out.println("Array size: " + largeArray.length);
        System.out.println("Binary Search: " + time1 + "ms, Result: " + Arrays.toString(res1));
        System.out.println("Linear Search: " + time2 + "ms, Result: " + Arrays.toString(res2));
    }
    
    // Helper to create a BST for testing
    private static TreeNode createBST() {
        //        8
        //       / \
        //      3   10
        //     / \    \
        //    1   6    14
        //       / \   /
        //      4   7 13
        TreeNode root = new TreeNode(8);
        root.left = new TreeNode(3);
        root.right = new TreeNode(10);
        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(6);
        root.left.right.left = new TreeNode(4);
        root.left.right.right = new TreeNode(7);
        root.right.right = new TreeNode(14);
        root.right.right.left = new TreeNode(13);
        return root;
    }
    
    // ============================
    // EXTENSION: Find floor/ceil with custom comparator
    // ============================
    
    static class Pair {
        int value;
        int weight;
        Pair(int v, int w) { value = v; weight = w; }
        
        @Override
        public String toString() {
            return "(" + value + "," + weight + ")";
        }
    }
    
    /**
     * Find floor and ceil based on value, but return Pair objects
     */
    public static Pair[] getFloorAndCeilPairs(Pair[] pairs, int n, int x) {
        // Sort by value
        Arrays.sort(pairs, (a, b) -> Integer.compare(a.value, b.value));
        
        int floorIdx = -1, ceilIdx = -1;
        int low = 0, high = n - 1;
        
        // Find ceil
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (pairs[mid].value >= x) {
                ceilIdx = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        // Find floor
        low = 0; high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (pairs[mid].value <= x) {
                floorIdx = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return new Pair[]{
            floorIdx != -1 ? pairs[floorIdx] : null,
            ceilIdx != -1 ? pairs[ceilIdx] : null
        };
    }
    
    // ============================
    // EXTENSION: Find floor/ceil for multiple queries
    // ============================
    
    /**
     * Process multiple queries efficiently
     * Sort queries and use two pointers
     */
    public static Map<Integer, int[]> batchFloorCeil(int[] arr, int[] queries) {
        Arrays.sort(arr);
        Arrays.sort(queries);
        
        Map<Integer, int[]> result = new HashMap<>();
        int arrIdx = 0;
        int floor = -1;
        
        for (int query : queries) {
            // Move arrIdx to find floor for current query
            while (arrIdx < arr.length && arr[arrIdx] <= query) {
                floor = arr[arrIdx];
                arrIdx++;
            }
            
            // Find ceil (smallest element >= query)
            int ceil = -1;
            if (arrIdx < arr.length) {
                ceil = arr[arrIdx];
            }
            
            result.put(query, new int[]{floor, ceil});
        }
        
        return result;
    }
    
    // ============================
    // EXTENSION: Find floor/ceil with tolerance
    // ============================
    
    /**
     * Find floor and ceil within tolerance
     * If no exact floor/ceil within tolerance, return -1
     */
    public static int[] getFloorAndCeilWithTolerance(int[] arr, int n, int x, int tolerance) {
        int floor = -1, ceil = -1;
        int low = 0, high = n - 1;
        
        // Find ceil within tolerance
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x) {
                if (arr[mid] - x <= tolerance) {
                    ceil = arr[mid];
                }
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        // Find floor within tolerance
        low = 0; high = n - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] <= x) {
                if (x - arr[mid] <= tolerance) {
                    floor = arr[mid];
                }
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return new int[]{floor, ceil};
    }
}