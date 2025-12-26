import java.util.*;

/**
 * Sum of Subarray Ranges (LeetCode 2104)
 * 
 * Problem: Given an integer array nums, return the sum of all 
 * subarray ranges (max - min) for every non-empty subarray.
 * 
 * Approach: 
 * 1. Compute sum of maximum elements of all subarrays
 * 2. Compute sum of minimum elements of all subarrays  
 * 3. Result = sumMax - sumMin
 * 
 * Key Insight: 
 * - Range = max - min for each subarray
 * - Sum of ranges = (sum of all max) - (sum of all min)
 * - Can compute sum of max/min using monotonic stacks
 * 
 * Time Complexity: O(n) - Two passes with monotonic stacks
 * Space Complexity: O(n) - Stack storage
 * 
 * Example: nums = [1, 2, 3]
 * Subarrays: [1],[2],[3],[1,2],[2,3],[1,2,3]
 * Ranges: 0,0,0,1,1,2
 * Sum: 0+0+0+1+1+2 = 4
 */
public class SumSubarrayRanges {
    
    /**
     * Computes sum of (max - min) for all non-empty subarrays.
     * 
     * @param nums Input array
     * @return Sum of all subarray ranges
     */
    public static long subarrayRanges(int[] nums) {
        long sumMax = sumSubarrayMax(nums);
        long sumMin = sumSubarrayMin(nums);
        return sumMax - sumMin;
    }
    
    /**
     * Computes sum of maximum elements of all subarrays.
     * Uses monotonic decreasing stack to find contribution of each element.
     * 
     * For each element nums[i]:
     * - Find left boundary: index of previous greater element (or -1)
     * - Find right boundary: index of next greater or equal element (or n)
     * - nums[i] is maximum for (leftDist * rightDist) subarrays
     * 
     * @param nums Input array
     * @return Sum of maximum elements of all subarrays
     */
    private static long sumSubarrayMax(int[] nums) {
        long sum = 0;
        Stack<Integer> stack = new Stack<>(); // Stores indices in decreasing value order
        
        // Traverse with sentinel (n acts as boundary)
        for (int i = 0; i <= nums.length; i++) {
            // Use sentinel value to pop remaining elements
            int currentVal = (i == nums.length) ? Integer.MAX_VALUE : nums[i];
            
            // Pop while current element is greater (strictly for right boundary)
            // This means: nums[i] is the next greater or equal for popped elements
            while (!stack.isEmpty() && nums[stack.peek()] < currentVal) {
                int midIdx = stack.pop(); // Element being evaluated
                int midVal = nums[midIdx];
                
                // Left boundary: previous greater element index
                int leftIdx = stack.isEmpty() ? -1 : stack.peek();
                int leftDist = midIdx - leftIdx;
                
                // Right boundary: next greater or equal element index
                int rightDist = i - midIdx;
                
                // Number of subarrays where midVal is maximum
                long subarrayCount = (long) leftDist * rightDist;
                
                // Add contribution
                sum += (long) midVal * subarrayCount;
            }
            
            // Push current index
            if (i < nums.length) {
                stack.push(i);
            }
        }
        return sum;
    }
    
    /**
     * Computes sum of minimum elements of all subarrays.
     * Uses monotonic increasing stack to find contribution of each element.
     * 
     * For each element nums[i]:
     * - Find left boundary: index of previous smaller element (or -1)
     * - Find right boundary: index of next smaller or equal element (or n)
     * - nums[i] is minimum for (leftDist * rightDist) subarrays
     * 
     * @param nums Input array
     * @return Sum of minimum elements of all subarrays
     */
    private static long sumSubarrayMin(int[] nums) {
        long sum = 0;
        Stack<Integer> stack = new Stack<>(); // Stores indices in increasing value order
        
        // Traverse with sentinel
        for (int i = 0; i <= nums.length; i++) {
            // Use sentinel value to pop remaining elements
            int currentVal = (i == nums.length) ? Integer.MIN_VALUE : nums[i];
            
            // Pop while current element is smaller (strictly for right boundary)
            // This means: nums[i] is the next smaller or equal for popped elements
            while (!stack.isEmpty() && nums[stack.peek()] > currentVal) {
                int midIdx = stack.pop(); // Element being evaluated
                int midVal = nums[midIdx];
                
                // Left boundary: previous smaller element index
                int leftIdx = stack.isEmpty() ? -1 : stack.peek();
                int leftDist = midIdx - leftIdx;
                
                // Right boundary: next smaller or equal element index
                int rightDist = i - midIdx;
                
                // Number of subarrays where midVal is minimum
                long subarrayCount = (long) leftDist * rightDist;
                
                // Add contribution
                sum += (long) midVal * subarrayCount;
            }
            
            // Push current index
            if (i < nums.length) {
                stack.push(i);
            }
        }
        return sum;
    }
    
    /**
     * Alternative implementation with separate boundary calculation.
     * More explicit but uses more space.
     */
    public static long subarrayRangesExplicit(int[] nums) {
        int n = nums.length;
        long sum = 0;
        
        // For each element as starting point
        for (int i = 0; i < n; i++) {
            int min = nums[i];
            int max = nums[i];
            
            // For each subarray starting at i
            for (int j = i; j < n; j++) {
                min = Math.min(min, nums[j]);
                max = Math.max(max, nums[j]);
                sum += (max - min);
            }
        }
        
        return sum;
    }
    
    /**
     * Optimized version combining max and min calculation in one pass.
     * More efficient but slightly more complex.
     */
    public static long subarrayRangesOptimized(int[] nums) {
        int n = nums.length;
        long sum = 0;
        
        // For each starting point, track min and max
        for (int i = 0; i < n; i++) {
            int min = nums[i];
            int max = nums[i];
            
            // Expand to the right
            for (int j = i; j < n; j++) {
                min = Math.min(min, nums[j]);
                max = Math.max(max, nums[j]);
                sum += (max - min);
            }
        }
        
        return sum;
    }
    
    /**
     * Helper to visualize calculation for a specific example.
     */
    public static void visualizeCalculation(int[] nums) {
        System.out.println("\n=== Visualization for nums = " + Arrays.toString(nums) + " ===");
        System.out.println("\n1. Calculating sum of maximums:");
        System.out.println("Element | LeftDist | RightDist | Count | Value | Contribution");
        System.out.println("--------|----------|-----------|-------|-------|-------------");
        
        long sumMax = 0;
        Stack<Integer> maxStack = new Stack<>();
        
        for (int i = 0; i <= nums.length; i++) {
            int currentVal = (i == nums.length) ? Integer.MAX_VALUE : nums[i];
            
            while (!maxStack.isEmpty() && nums[maxStack.peek()] < currentVal) {
                int mid = maxStack.pop();
                int left = maxStack.isEmpty() ? -1 : maxStack.peek();
                int leftDist = mid - left;
                int rightDist = i - mid;
                long count = (long) leftDist * rightDist;
                long contrib = (long) nums[mid] * count;
                sumMax += contrib;
                
                System.out.printf("arr[%d]=%d | %8d | %9d | %5d | %5d | %12d%n",
                                  mid, nums[mid], leftDist, rightDist, count, nums[mid], contrib);
            }
            
            if (i < nums.length) maxStack.push(i);
        }
        
        System.out.println("\n2. Calculating sum of minimums:");
        System.out.println("Element | LeftDist | RightDist | Count | Value | Contribution");
        System.out.println("--------|----------|-----------|-------|-------|-------------");
        
        long sumMin = 0;
        Stack<Integer> minStack = new Stack<>();
        
        for (int i = 0; i <= nums.length; i++) {
            int currentVal = (i == nums.length) ? Integer.MIN_VALUE : nums[i];
            
            while (!minStack.isEmpty() && nums[minStack.peek()] > currentVal) {
                int mid = minStack.pop();
                int left = minStack.isEmpty() ? -1 : minStack.peek();
                int leftDist = mid - left;
                int rightDist = i - mid;
                long count = (long) leftDist * rightDist;
                long contrib = (long) nums[mid] * count;
                sumMin += contrib;
                
                System.out.printf("arr[%d]=%d | %8d | %9d | %5d | %5d | %12d%n",
                                  mid, nums[mid], leftDist, rightDist, count, nums[mid], contrib);
            }
            
            if (i < nums.length) minStack.push(i);
        }
        
        System.out.println("\n3. Final calculation:");
        System.out.println("Sum of maximums: " + sumMax);
        System.out.println("Sum of minimums: " + sumMin);
        System.out.println("Sum of ranges: " + sumMax + " - " + sumMin + " = " + (sumMax - sumMin));
    }
    
    /**
     * Test cases to verify correctness.
     */
    public static void runTestCases() {
        System.out.println("=== Test Cases for Sum of Subarray Ranges ===\n");
        
        // Test 1: Basic example
        int[] test1 = {1, 2, 3};
        System.out.println("Test 1: nums = [1, 2, 3]");
        System.out.println("Expected: 4");
        System.out.println("Result: " + subarrayRanges(test1));
        System.out.println("Brute force: " + subarrayRangesExplicit(test1));
        System.out.println();
        
        // Test 2: Single element
        int[] test2 = {5};
        System.out.println("Test 2: nums = [5]");
        System.out.println("Expected: 0 (only one subarray: [5], range = 5-5 = 0)");
        System.out.println("Result: " + subarrayRanges(test2));
        System.out.println("Brute force: " + subarrayRangesExplicit(test2));
        System.out.println();
        
        // Test 3: All equal
        int[] test3 = {4, 4, 4};
        System.out.println("Test 3: nums = [4, 4, 4]");
        System.out.println("Expected: 0 (all ranges are 0)");
        System.out.println("Result: " + subarrayRanges(test3));
        System.out.println("Brute force: " + subarrayRangesExplicit(test3));
        System.out.println();
        
        // Test 4: Increasing then decreasing
        int[] test4 = {1, 3, 3};
        System.out.println("Test 4: nums = [1, 3, 3]");
        System.out.println("Subarrays: [1],[3],[3],[1,3],[3,3],[1,3,3]");
        System.out.println("Ranges: 0,0,0,2,0,2 → Sum: 4");
        System.out.println("Result: " + subarrayRanges(test4));
        System.out.println("Brute force: " + subarrayRangesExplicit(test4));
        System.out.println();
        
        // Test 5: Random case
        int[] test5 = {4, -2, -3, 4, 1};
        System.out.println("Test 5: nums = [4, -2, -3, 4, 1]");
        System.out.println("Result: " + subarrayRanges(test5));
        System.out.println("Brute force: " + subarrayRangesExplicit(test5));
        System.out.println();
        
        // Test 6: From LeetCode
        int[] test6 = {1, 2, 3, 4};
        System.out.println("Test 6: nums = [1, 2, 3, 4]");
        System.out.println("Result: " + subarrayRanges(test6));
        System.out.println("Brute force: " + subarrayRangesExplicit(test6));
        System.out.println();
        
        // Test 7: Edge case with negative numbers
        int[] test7 = {-5, -2, -1, -4};
        System.out.println("Test 7: nums = [-5, -2, -1, -4]");
        System.out.println("Result: " + subarrayRanges(test7));
        System.out.println("Brute force: " + subarrayRangesExplicit(test7));
        System.out.println();
    }
    
    /**
     * Performance comparison between different implementations.
     */
    public static void benchmark() {
        System.out.println("\n=== Performance Comparison ===");
        
        // Generate test array
        int n = 1000;
        int[] nums = new int[n];
        Random rand = new Random(42);
        for (int i = 0; i < n; i++) {
            nums[i] = rand.nextInt(20001) - 10000; // Range: -10000 to 10000
        }
        
        System.out.println("Array size: " + n);
        
        // O(n) stack-based solution
        long start = System.currentTimeMillis();
        long result1 = subarrayRanges(nums);
        long time1 = System.currentTimeMillis() - start;
        System.out.println("Stack-based (O(n)): " + time1 + " ms, result: " + result1);
        
        // O(n²) explicit solution
        start = System.currentTimeMillis();
        long result2 = subarrayRangesExplicit(nums);
        long time2 = System.currentTimeMillis() - start;
        System.out.println("Explicit (O(n²)): " + time2 + " ms, result: " + result2);
        
        System.out.println("Results match: " + (result1 == result2));
        System.out.println("Speedup: " + (time2 * 1.0 / time1) + "x");
    }
    
    /**
     * Analyze mathematical properties of the problem.
     */
    public static void analyzeProperties() {
        System.out.println("\n=== Mathematical Properties ===");
        
        // Property 1: Range is always non-negative
        System.out.println("1. Range = max - min ≥ 0 for any subarray");
        
        // Property 2: For single element subarrays, range = 0
        System.out.println("2. For single element subarrays, range = 0");
        
        // Property 3: Maximum possible range
        System.out.println("3. Maximum range occurs for subarray containing both min and max of array");
        
        // Property 4: For monotonic arrays
        int[] increasing = {1, 2, 3, 4, 5};
        int[] decreasing = {5, 4, 3, 2, 1};
        System.out.println("4. For strictly increasing array of length n:");
        System.out.println("   Number of subarrays: n(n+1)/2");
        System.out.println("   Sum of ranges follows a specific pattern");
        
        // Property 5: Relationship to other problems
        System.out.println("5. Related problems:");
        System.out.println("   - Sum of Subarray Minimums (LeetCode 907)");
        System.out.println("   - Sum of Subarray Maximums");
        System.out.println("   - Number of Subarrays with Bounded Maximum");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Visualize calculation for main example
        System.out.println("\n" + "=".repeat(60));
        System.out.println("Visualization of the algorithm:");
        System.out.println("=".repeat(60));
        int[] nums = {1, 2, 3};
        visualizeCalculation(nums);
        
        // Analyze mathematical properties
        analyzeProperties();
        
        // Run benchmark (for larger n)
        // benchmark();
    }
}