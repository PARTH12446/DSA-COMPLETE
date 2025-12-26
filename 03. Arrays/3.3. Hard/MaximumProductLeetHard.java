// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Maximum Product Subarray (LeetCode 152)
 * 
 * Given an integer array nums, find a contiguous non-empty subarray within 
 * the array that has the largest product, and return the product.
 * 
 * The test cases are generated so that the answer will fit in a 32-bit integer.
 * 
 * CONSTRAINTS:
 * - 1 <= nums.length <= 2 * 10^4
 * - -10 <= nums[i] <= 10
 * - The product of any prefix or suffix of nums is guaranteed to fit in a 32-bit integer.
 * 
 * KEY POINTS:
 * - Array contains both positive and negative integers
 * - Product can be negative, zero, or positive
 * - Zeros act as reset points for product calculation
 * - Negative numbers can become positive when multiplied by another negative
 * 
 * APPROACH: Prefix and Suffix Product Traversal
 * 
 * INTUITION:
 * The maximum product subarray can be found by:
 * 1. Computing product from left to right (prefix)
 * 2. Computing product from right to left (suffix)
 * 3. Taking maximum of both at each position
 * 
 * Why this works:
 * - For arrays without zeros, max product is either:
 *   a) Entire array (if even number of negatives)
 *   b) Excluding some prefix or suffix (if odd number of negatives)
 * - For arrays with zeros, we reset at zeros
 * 
 * TIME COMPLEXITY: O(n)
 *   - Single pass through array
 *   - Each iteration does constant work
 * 
 * SPACE COMPLEXITY: O(1)
 *   - Only a few integer variables used
 */

public class MaximumProductLeetHard {

    /**
     * Optimal solution using prefix and suffix products
     * 
     * @param nums Input array
     * @return Maximum product of any contiguous subarray
     */
    public int maxProduct(int[] nums) {
        int n = nums.length;
        int maxProduct = Integer.MIN_VALUE;
        int prefixProduct = 1;
        int suffixProduct = 1;
        
        for (int i = 0; i < n; i++) {
            // Reset products to 1 if they become 0
            // This allows skipping zeros and starting fresh
            if (prefixProduct == 0) prefixProduct = 1;
            if (suffixProduct == 0) suffixProduct = 1;
            
            // Multiply current elements from both ends
            prefixProduct *= nums[i];
            suffixProduct *= nums[n - 1 - i];
            
            // Update maximum product
            maxProduct = Math.max(maxProduct, Math.max(prefixProduct, suffixProduct));
        }
        
        return maxProduct;
    }
    
    /**
     * Dynamic Programming approach (more intuitive)
     * Track both max and min at each position
     * Time: O(n), Space: O(1)
     */
    public int maxProductDP(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int maxSoFar = nums[0];
        int minSoFar = nums[0];
        int result = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];
            
            // Store temp max because min calculation uses old max
            int tempMax = Math.max(current, Math.max(maxSoFar * current, minSoFar * current));
            minSoFar = Math.min(current, Math.min(maxSoFar * current, minSoFar * current));
            maxSoFar = tempMax;
            
            result = Math.max(result, maxSoFar);
        }
        
        return result;
    }
    
    /**
     * Alternative: Two-pass solution (forward and backward)
     * Similar to prefix/suffix but in separate passes
     */
    public int maxProductTwoPass(int[] nums) {
        int maxProduct = Integer.MIN_VALUE;
        int product = 1;
        
        // Forward pass
        for (int i = 0; i < nums.length; i++) {
            product *= nums[i];
            maxProduct = Math.max(maxProduct, product);
            if (product == 0) {
                product = 1;  // Reset on zero
            }
        }
        
        // Backward pass
        product = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            product *= nums[i];
            maxProduct = Math.max(maxProduct, product);
            if (product == 0) {
                product = 1;  // Reset on zero
            }
        }
        
        return maxProduct;
    }
    
    /**
     * Brute force for verification (O(n²) time)
     */
    public int maxProductBruteForce(int[] nums) {
        int n = nums.length;
        int maxProduct = Integer.MIN_VALUE;
        
        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = i; j < n; j++) {
                product *= nums[j];
                maxProduct = Math.max(maxProduct, product);
            }
        }
        
        return maxProduct;
    }
    
    /**
     * Divide and conquer approach (for completeness)
     * Time: O(n log n), Space: O(log n) for recursion stack
     */
    public int maxProductDivideConquer(int[] nums) {
        return maxProductHelper(nums, 0, nums.length - 1);
    }
    
    private int maxProductHelper(int[] nums, int left, int right) {
        if (left == right) return nums[left];
        
        int mid = left + (right - left) / 2;
        
        int leftMax = maxProductHelper(nums, left, mid);
        int rightMax = maxProductHelper(nums, mid + 1, right);
        int crossMax = maxCrossingProduct(nums, left, mid, right);
        
        return Math.max(leftMax, Math.max(rightMax, crossMax));
    }
    
    private int maxCrossingProduct(int[] nums, int left, int mid, int right) {
        // Compute max product that crosses mid
        int leftProduct = 1;
        int leftMax = Integer.MIN_VALUE;
        for (int i = mid; i >= left; i--) {
            leftProduct *= nums[i];
            leftMax = Math.max(leftMax, leftProduct);
        }
        
        int rightProduct = 1;
        int rightMax = Integer.MIN_VALUE;
        for (int i = mid + 1; i <= right; i++) {
            rightProduct *= nums[i];
            rightMax = Math.max(rightMax, rightProduct);
        }
        
        return leftMax * rightMax;
    }
    
    /**
     * Test method with LeetCode examples
     */
    public static void main(String[] args) {
        MaximumProductLeetHard solver = new MaximumProductLeetHard();
        
        // Test case 1: LeetCode Example 1
        int[] nums1 = {2, 3, -2, 4};
        System.out.println("Test 1: [2, 3, -2, 4]");
        System.out.println("Prefix/Suffix: " + solver.maxProduct(nums1));
        System.out.println("DP: " + solver.maxProductDP(nums1));
        System.out.println("Expected: 6 (subarray [2, 3])");
        
        // Test case 2: LeetCode Example 2
        int[] nums2 = {-2, 0, -1};
        System.out.println("\nTest 2: [-2, 0, -1]");
        System.out.println("Prefix/Suffix: " + solver.maxProduct(nums2));
        System.out.println("DP: " + solver.maxProductDP(nums2));
        System.out.println("Expected: 0 (subarray [0] or [-2, 0, -1] = 0)");
        
        // Test case 3: All negatives with even count
        int[] nums3 = {-2, -3, -4};
        System.out.println("\nTest 3: [-2, -3, -4]");
        System.out.println("Prefix/Suffix: " + solver.maxProduct(nums3));
        System.out.println("DP: " + solver.maxProductDP(nums3));
        System.out.println("Expected: 12 (entire array)");
        
        // Test case 4: Single element
        int[] nums4 = {0};
        System.out.println("\nTest 4: [0]");
        System.out.println("Prefix/Suffix: " + solver.maxProduct(nums4));
        System.out.println("DP: " + solver.maxProductDP(nums4));
        System.out.println("Expected: 0");
        
        // Test case 5: Complex case
        int[] nums5 = {2, -5, 3, 1, -4, 0, -10, 2, 8};
        System.out.println("\nTest 5: Complex array");
        System.out.println("Prefix/Suffix: " + solver.maxProduct(nums5));
        System.out.println("DP: " + solver.maxProductDP(nums5));
        System.out.println("Brute Force: " + solver.maxProductBruteForce(nums5));
        
        // Test case 6: Large negative number at end
        int[] nums6 = {2, 3, -2, 4, -1};
        System.out.println("\nTest 6: [2, 3, -2, 4, -1]");
        System.out.println("Prefix/Suffix: " + solver.maxProduct(nums6));
        System.out.println("DP: " + solver.maxProductDP(nums6));
        System.out.println("Expected: 48 (entire array)");
        
        // Test case 7: All zeros
        int[] nums7 = {0, 0, 0, 0};
        System.out.println("\nTest 7: [0, 0, 0, 0]");
        System.out.println("Prefix/Suffix: " + solver.maxProduct(nums7));
        System.out.println("DP: " + solver.maxProductDP(nums7));
        System.out.println("Expected: 0");
        
        // Test case 8: Mixed with single positive
        int[] nums8 = {-2, 3, -4};
        System.out.println("\nTest 8: [-2, 3, -4]");
        System.out.println("Prefix/Suffix: " + solver.maxProduct(nums8));
        System.out.println("DP: " + solver.maxProductDP(nums8));
        System.out.println("Expected: 24 (subarray [3, -4])");
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int[] largeArray = new int[20000];
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < largeArray.length; i++) {
            largeArray[i] = rand.nextInt(21) - 10; // Range -10 to 10
        }
        
        long startTime = System.currentTimeMillis();
        int result1 = solver.maxProduct(largeArray);
        long endTime = System.currentTimeMillis();
        System.out.println("Prefix/Suffix Time: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        int result2 = solver.maxProductDP(largeArray);
        endTime = System.currentTimeMillis();
        System.out.println("DP Time: " + (endTime - startTime) + "ms");
        
        // Verify with brute force on small array
        System.out.println("\n=== Verification ===");
        int[] testArray = {1, -2, 3, 0, -4, 5};
        int algoResult = solver.maxProduct(testArray);
        int bruteResult = solver.maxProductBruteForce(testArray);
        System.out.println("Algorithm: " + algoResult);
        System.out.println("Brute Force: " + bruteResult);
        System.out.println("Match: " + (algoResult == bruteResult));
    }
    
    /**
     * Why the prefix/suffix approach works:
     * 
     * Consider all possible subarrays:
     * 1. Those that don't include the first element
     * 2. Those that don't include the last element
     * 3. Those that include both first and last elements
     * 
     * The maximum product must be one of:
     * - A prefix product (starting from left)
     * - A suffix product (ending at right)
     * - Or a combination that excludes some elements from both ends
     * 
     * By computing products from both ends, we effectively
     * consider all possible contiguous subarrays.
     * 
     * Example: [a, b, c, d]
     * All subarrays: [a], [a,b], [a,b,c], [a,b,c,d]
     *                [b], [b,c], [b,c,d]
     *                [c], [c,d]
     *                [d]
     * 
     * Prefix covers: [a], [a,b], [a,b,c], [a,b,c,d]
     * Suffix covers: [d], [c,d], [b,c,d], [a,b,c,d]
     * Combined they cover all subarrays.
     */
    
    /**
     * Step-by-step example:
     * 
     * Array: [2, 3, -2, 4]
     * 
     * Iteration 0:
     *   prefix = 1 * 2 = 2
     *   suffix = 1 * 4 = 4
     *   max = max(MIN_VALUE, max(2,4)) = 4
     * 
     * Iteration 1:
     *   prefix = 2 * 3 = 6
     *   suffix = 4 * (-2) = -8
     *   max = max(4, max(6,-8)) = 6
     * 
     * Iteration 2:
     *   prefix = 6 * (-2) = -12
     *   suffix = -8 * 3 = -24
     *   max = max(6, max(-12,-24)) = 6
     * 
     * Iteration 3:
     *   prefix = -12 * 4 = -48
     *   suffix = -24 * 2 = -48
     *   max = max(6, max(-48,-48)) = 6
     * 
     * Result: 6
     */
    
    /**
     * Dynamic Programming intuition:
     * 
     * At each position i, we need to consider:
     * 1. Start new subarray at i: nums[i]
     * 2. Extend previous max subarray: maxSoFar * nums[i]
     * 3. Extend previous min subarray: minSoFar * nums[i]
     * 
     * Why track min? Because:
     * - Negative * negative = positive
     * - Large negative * small negative = even larger positive
     * 
     * Example: [-1, -2, -3]
     * i=0: max=-1, min=-1
     * i=1: max=max(-2, -1*-2=2, -1*-2=2)=2
     *      min=min(-2, -1*-2=2, -1*-2=2)=-2
     * i=2: max=max(-3, 2*-3=-6, -2*-3=6)=6
     *      min=min(-3, -6, 6)=-6
     * Result: 6
     */
    
    /**
     * Edge Cases and Solutions:
     * 
     * 1. All negative numbers:
     *    - If even count: entire array product is max
     *    - If odd count: exclude smallest (least negative)
     * 
     * 2. Contains zeros:
     *    - Zeros reset the product
     *    - Maximum might be zero or product of segment between zeros
     * 
     * 3. Single element:
     *    - Return that element
     * 
     * 4. Product overflow:
     *    - Constraints guarantee 32-bit fit
     *    - But algorithm works for larger numbers with long type
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Using Kadane's algorithm (for sum) directly:
     *    - Doesn't work for product due to negatives
     * 
     * 2. Not resetting on zeros:
     *    - Product stays zero forever
     * 
     * 3. Only tracking maximum, not minimum:
     *    - Misses cases where negative * negative = positive
     * 
     * 4. Initializing maxProduct with 0:
     *    - Fails for all negative arrays
     *    - Should use Integer.MIN_VALUE
     */
    
    /**
     * Optimization Insights:
     * 
     * 1. Early zero detection:
     *    - If product becomes 0, can skip to next non-zero
     * 
     * 2. Sign tracking:
     *    - Count negative numbers to predict sign changes
     * 
     * 3. Memory optimization:
     *    - Prefix/suffix uses O(1) space vs DP's O(1) but simpler
     */
    
    /**
     * Related LeetCode Problems:
     * 
     * 1. Maximum Subarray (53) - Kadane's algorithm for sum
     * 2. Maximum Product of Three Numbers (628)
     * 3. Subarray Product Less Than K (713)
     * 4. Maximum Length of Subarray With Positive Product (1567)
     */
    
    /**
     * Real-world Applications:
     * 
     * 1. Financial portfolios:
     *    - Maximizing compounded returns
     *    - Handling leverage (multiplication effect)
     * 
     * 2. Signal processing:
     *    - Finding maximum amplitude regions
     * 
     * 3. Machine learning:
     *    - Feature interaction strength
     * 
     * 4. Game theory:
     *    - Cumulative advantage in sequential games
     */
    
    /**
     * Complexity Analysis:
     * 
     * Time Complexity:
     * - Prefix/Suffix: O(n) - single pass
     * - DP: O(n) - single pass
     * - Two-pass: O(2n) = O(n)
     * - Brute force: O(n²)
     * 
     * Space Complexity:
     * - All approaches: O(1) extra space
     * - Divide and conquer: O(log n) recursion stack
     */
    
    /**
     * When to use which approach:
     * 
     * 1. Prefix/Suffix:
     *    - Most elegant for interviews
     *    - Easy to explain and implement
     * 
     * 2. Dynamic Programming:
     *    - More intuitive for those familiar with DP
     *    - Generalizes better to similar problems
     * 
     * 3. Two-pass:
     *    - Simplest to understand
     *    - Good for learning purposes
     * 
     * 4. Brute force:
     *    - Only for verification on small inputs
     */
}
