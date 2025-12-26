import java.util.*;

/**
 * Sum of Subarray Minimums (LeetCode 907)
 * 
 * Problem: Given an array of integers arr, find the sum of min(b) 
 * for every non-empty subarray b of arr.
 * 
 * Approach: Use monotonic stack to find how many subarrays have 
 * each element as their minimum.
 * 
 * Key Insight: For each element arr[i], find:
 * - Left boundary: index of previous smaller element
 * - Right boundary: index of next smaller or equal element
 * Then, arr[i] is the minimum for (leftDistance * rightDistance) subarrays
 * 
 * Mathematical Formulation:
 * sum = Σ(arr[i] * count_of_subarrays_where_arr[i]_is_minimum)
 * 
 * Time Complexity: O(n) - Each element pushed/popped at most once
 * Space Complexity: O(n) - Stack storage
 * 
 * Example: arr = [3, 1, 2, 4]
 * Subarrays and their minimums:
 * [3] → 3, [3,1] → 1, [3,1,2] → 1, [3,1,2,4] → 1,
 * [1] → 1, [1,2] → 1, [1,2,4] → 1,
 * [2] → 2, [2,4] → 2,
 * [4] → 4
 * Sum = 3 + 1 + 1 + 1 + 1 + 1 + 1 + 2 + 2 + 4 = 17
 */
public class SumSubarrayMinimums {
    
    /**
     * Calculates sum of minimum elements of all subarrays.
     * 
     * Algorithm:
     * 1. Use monotonic increasing stack (strictly increasing from bottom to top)
     * 2. For each element, when we find a smaller element to the right:
     *    - Pop elements from stack
     *    - For each popped element, it's the minimum for certain subarrays
     *    - Calculate: leftDistance * rightDistance * arr[mid]
     * 3. Handle boundary cases with sentinel values
     * 
     * @param arr Input array
     * @return Sum of minimums of all subarrays modulo 10^9+7
     */
    public static int sumSubarrayMins(int[] arr) {
        int n = arr.length;
        long sum = 0;
        Stack<Integer> stack = new Stack<>(); // Stores indices in increasing value order
        
        // Traverse from 0 to n (n acts as sentinel to pop remaining elements)
        for (int i = 0; i <= n; i++) {
            // Current value (use Integer.MAX_VALUE as sentinel for i == n)
            int currentVal = (i == n) ? Integer.MIN_VALUE : arr[i];
            
            // Pop while current element is smaller or equal (for right boundary)
            // Use > for strictly previous smaller, >= for next smaller or equal
            while (!stack.isEmpty() && arr[stack.peek()] > currentVal) {
                int midIdx = stack.pop(); // Element that's being evaluated
                int midVal = arr[midIdx];
                
                // Calculate left boundary distance
                // Previous smaller element index (or -1 if none)
                int leftIdx = stack.isEmpty() ? -1 : stack.peek();
                int leftDistance = midIdx - leftIdx;
                
                // Calculate right boundary distance
                int rightDistance = i - midIdx;
                
                // Number of subarrays where midVal is minimum
                long subarrayCount = (long) leftDistance * rightDistance;
                
                // Add contribution of this element
                sum += (long) midVal * subarrayCount;
                
                // Debug output (optional)
                // System.out.printf("arr[%d]=%d: leftDist=%d, rightDist=%d, count=%d, contrib=%d%n",
                //                   midIdx, midVal, leftDistance, rightDistance, 
                //                   subarrayCount, midVal * subarrayCount);
            }
            
            // Push current index to stack
            if (i < n) {
                stack.push(i);
            }
        }
        
        // Return result modulo 10^9+7 as required by problem
        return (int) (sum % 1_000_000_007);
    }
    
    /**
     * Alternative implementation with separate left/right boundary calculation.
     * More explicit but uses extra space.
     */
    public static int sumSubarrayMinsExplicit(int[] arr) {
        int n = arr.length;
        int MOD = 1_000_000_007;
        
        // Arrays to store distances to previous smaller and next smaller or equal
        int[] prevSmaller = new int[n];
        int[] nextSmaller = new int[n];
        
        // Initialize with boundary values
        Arrays.fill(prevSmaller, -1); // -1 means no smaller element to left
        Arrays.fill(nextSmaller, n);  // n means no smaller element to right
        
        Stack<Integer> stack = new Stack<>();
        
        // Find previous smaller elements (strictly smaller)
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                stack.pop();
            }
            prevSmaller[i] = stack.isEmpty() ? -1 : stack.peek();
            stack.push(i);
        }
        
        stack.clear();
        
        // Find next smaller or equal elements
        for (int i = n - 1; i >= 0; i--) {
            while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
                stack.pop();
            }
            nextSmaller[i] = stack.isEmpty() ? n : stack.peek();
            stack.push(i);
        }
        
        // Calculate sum
        long sum = 0;
        for (int i = 0; i < n; i++) {
            long leftCount = i - prevSmaller[i];
            long rightCount = nextSmaller[i] - i;
            sum += (long) arr[i] * leftCount * rightCount;
            sum %= MOD;
        }
        
        return (int) sum;
    }
    
    /**
     * Brute force solution for verification (O(n³) time).
     * Only for small arrays (n ≤ 100).
     */
    public static int sumSubarrayMinsBruteForce(int[] arr) {
        int n = arr.length;
        long sum = 0;
        
        for (int start = 0; start < n; start++) {
            for (int end = start; end < n; end++) {
                int min = Integer.MAX_VALUE;
                for (int k = start; k <= end; k++) {
                    min = Math.min(min, arr[k]);
                }
                sum += min;
            }
        }
        
        return (int) (sum % 1_000_000_007);
    }
    
    /**
     * Helper method to visualize the calculation for a specific example.
     */
    public static void visualizeCalculation(int[] arr) {
        System.out.println("\n=== Visualization for arr = " + Arrays.toString(arr) + " ===");
        System.out.println("Element | LeftDist | RightDist | Count | Value | Contribution");
        System.out.println("--------|----------|-----------|-------|-------|-------------");
        
        int n = arr.length;
        Stack<Integer> stack = new Stack<>();
        long total = 0;
        
        for (int i = 0; i <= n; i++) {
            int currentVal = (i == n) ? Integer.MIN_VALUE : arr[i];
            
            while (!stack.isEmpty() && arr[stack.peek()] > currentVal) {
                int mid = stack.pop();
                int left = stack.isEmpty() ? -1 : stack.peek();
                int leftDist = mid - left;
                int rightDist = i - mid;
                long count = (long) leftDist * rightDist;
                long contrib = (long) arr[mid] * count;
                total += contrib;
                
                System.out.printf("arr[%d]=%d | %8d | %9d | %5d | %5d | %12d%n",
                                  mid, arr[mid], leftDist, rightDist, count, arr[mid], contrib);
            }
            
            if (i < n) stack.push(i);
        }
        
        System.out.println("\nTotal sum: " + total);
        System.out.println("Total modulo 10^9+7: " + (total % 1_000_000_007));
    }
    
    /**
     * Test cases to verify correctness.
     */
    public static void runTestCases() {
        System.out.println("=== Test Cases for Sum of Subarray Minimums ===\n");
        
        // Test 1: Basic example
        int[] test1 = {3, 1, 2, 4};
        System.out.println("Test 1: arr = [3, 1, 2, 4]");
        System.out.println("Expected: 17");
        System.out.println("Result: " + sumSubarrayMins(test1));
        System.out.println("Brute force: " + sumSubarrayMinsBruteForce(test1));
        System.out.println();
        
        // Test 2: All increasing
        int[] test2 = {1, 2, 3, 4, 5};
        System.out.println("Test 2: arr = [1, 2, 3, 4, 5]");
        System.out.println("Expected: 35 (1*5 + 2*4 + 3*3 + 4*2 + 5*1)");
        System.out.println("Result: " + sumSubarrayMins(test2));
        System.out.println("Brute force: " + sumSubarrayMinsBruteForce(test2));
        System.out.println();
        
        // Test 3: All decreasing
        int[] test3 = {5, 4, 3, 2, 1};
        System.out.println("Test 3: arr = [5, 4, 3, 2, 1]");
        System.out.println("Expected: 35 (same as increasing due to symmetry)");
        System.out.println("Result: " + sumSubarrayMins(test3));
        System.out.println("Brute force: " + sumSubarrayMinsBruteForce(test3));
        System.out.println();
        
        // Test 4: All equal
        int[] test4 = {2, 2, 2, 2};
        System.out.println("Test 4: arr = [2, 2, 2, 2]");
        System.out.println("Expected: 40 (2 * (4+3+2+1 + 3+2+1 + 2+1 + 1))");
        System.out.println("Result: " + sumSubarrayMins(test4));
        System.out.println("Brute force: " + sumSubarrayMinsBruteForce(test4));
        System.out.println();
        
        // Test 5: Single element
        int[] test5 = {7};
        System.out.println("Test 5: arr = [7]");
        System.out.println("Expected: 7");
        System.out.println("Result: " + sumSubarrayMins(test5));
        System.out.println("Brute force: " + sumSubarrayMinsBruteForce(test5));
        System.out.println();
        
        // Test 6: Random case
        int[] test6 = {11, 81, 94, 43, 3};
        System.out.println("Test 6: arr = [11, 81, 94, 43, 3]");
        System.out.println("Result: " + sumSubarrayMins(test6));
        System.out.println("Brute force: " + sumSubarrayMinsBruteForce(test6));
        System.out.println();
    }
    
    /**
     * Explain the mathematical formula with example.
     */
    public static void explainFormula() {
        System.out.println("\n=== Mathematical Explanation ===");
        System.out.println("For element arr[i] = x:");
        System.out.println("Find left boundary L: index of previous smaller element");
        System.out.println("Find right boundary R: index of next smaller or equal element");
        System.out.println();
        System.out.println("Then x is the minimum for all subarrays that:");
        System.out.println("1. Start between (L, i] inclusive");
        System.out.println("2. End between [i, R) inclusive");
        System.out.println();
        System.out.println("Number of such subarrays = (i - L) * (R - i)");
        System.out.println("Contribution of x = x * (i - L) * (R - i)");
        System.out.println();
        
        int[] example = {3, 1, 2, 4};
        System.out.println("Example: arr = " + Arrays.toString(example));
        System.out.println("For arr[1] = 1:");
        System.out.println("Previous smaller: none (L = -1)");
        System.out.println("Next smaller or equal: none (R = 4)");
        System.out.println("Subarrays where 1 is minimum:");
        System.out.println("Starts: 0,1 (2 choices)");
        System.out.println("Ends: 1,2,3 (3 choices)");
        System.out.println("Total: 2 * 3 = 6 subarrays");
        System.out.println("Contribution: 1 * 2 * 3 = 6");
    }
    
    /**
     * Performance comparison between different implementations.
     */
    public static void benchmark() {
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate large test array
        int n = 100000;
        int[] largeArr = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            largeArr[i] = rand.nextInt(10000) + 1;
        }
        
        System.out.println("Array size: " + n);
        
        long start = System.currentTimeMillis();
        int result1 = sumSubarrayMins(largeArr);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Single-pass stack: " + time1 + " ms, result: " + result1);
        
        start = System.currentTimeMillis();
        int result2 = sumSubarrayMinsExplicit(largeArr);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Explicit boundaries: " + time2 + " ms, result: " + result2);
        
        System.out.println("Results match: " + (result1 == result2));
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize calculation for main example
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Visualization of the algorithm:");
        System.out.println("=".repeat(60));
        int[] arr = {3, 1, 2, 4};
        visualizeCalculation(arr);
        
        // Explain the formula
        explainFormula();
        
        // Run benchmark for large input (optional)
        // benchmark();
    }
}