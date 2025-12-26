// Problem: LeetCode <ID>. <Title>
/*
 * PROBLEM: Maximum Product Subarray (Coding Ninjas / LeetCode 152)
 * 
 * Given an integer array 'arr', find the contiguous subarray that has the
 * largest product, and return the product.
 * 
 * IMPORTANT: The product can be negative, zero, or positive.
 * The array may contain negative numbers and zeros.
 * 
 * CONSTRAINTS:
 * - 1 = arr.length = 10^5
 * - -10 = arr[i] = 10
 * - The product fits in 32-bit integer range
 * 
 * KEY CHALLENGES:
 * 1. Negative numbers: Two negatives make a positive
 * 2. Zeros: Reset the product to zero
 * 3. Odd vs even number of negatives affects maximum product
 * 
 * APPROACH: Prefix and Suffix Product (Optimal O(n) time, O(1) space)
 * 
 * INTUITION:
 * - Maximum product subarray can come from:
 *   1. Prefix product (left to right)
 *   2. Suffix product (right to left)
 * - Why? Because zeros reset the product, and negatives change sign
 * - By computing both directions, we catch all possible subarrays
 * 
 * ALGORITHM:
 * 1. Initialize prefix = 0, suffix = 0, answer = min_value
 * 2. Traverse array from both ends simultaneously:
 *    - If prefix/suffix becomes 0, reset to 1 (skip zero)
 *    - Multiply current element to prefix and suffix
 *    - Update answer with max of current answer, prefix, suffix
 * 3. Return answer
 * 
 * TIME COMPLEXITY: O(n) - Single pass
 * SPACE COMPLEXITY: O(1) - Constant extra space
 */

public class MaximumProductHard {

    /**
     * Optimal solution using prefix and suffix products
     * 
     * @param arr Input array
     * @return Maximum product of any contiguous subarray
     */
    public static int subarrayWithMaxProduct(int[] arr) {
        int n = arr.length;
        int maxProduct = Integer.MIN_VALUE;
        int prefixProduct = 1;  // Product from left to right
        int suffixProduct = 1;  // Product from right to left
        
        for (int i = 0; i < n; i++) {
            // Reset to 1 if product becomes 0 (skip zero)
            // This allows us to start fresh after encountering zero
            if (prefixProduct == 0) prefixProduct = 1;
            if (suffixProduct == 0) suffixProduct = 1;
            
            // Multiply current elements
            prefixProduct *= arr[i];
            suffixProduct *= arr[n - 1 - i];  // Mirror index from end
            
            // Update maximum product
            maxProduct = Math.max(maxProduct, Math.max(prefixProduct, suffixProduct));
        }
        
        return maxProduct;
    }
    
    /**
     * Alternative: Dynamic Programming approach
     * Track both max and min products at each position
     * Time: O(n), Space: O(1)
     */
    public static int maxProductSubarrayDP(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int maxSoFar = nums[0];
        int minSoFar = nums[0];  // Important for negative numbers
        int result = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];
            
            // Store temporary max because minSoFar depends on old maxSoFar
            int tempMax = Math.max(current, Math.max(maxSoFar * current, minSoFar * current));
            minSoFar = Math.min(current, Math.min(maxSoFar * current, minSoFar * current));
            maxSoFar = tempMax;
            
            result = Math.max(result, maxSoFar);
        }
        
        return result;
    }
    
    /**
     * Brute force solution for verification (O(n²) time)
     */
    public static int maxProductSubarrayBruteForce(int[] arr) {
        int n = arr.length;
        int maxProduct = Integer.MIN_VALUE;
        
        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = i; j < n; j++) {
                product *= arr[j];
                maxProduct = Math.max(maxProduct, product);
            }
        }
        
        return maxProduct;
    }
    
    /**
     * Solution using prefix products only (less intuitive)
     */
    public static int maxProductSubarrayPrefixOnly(int[] arr) {
        int n = arr.length;
        int maxProduct = Integer.MIN_VALUE;
        
        // Forward pass
        int product = 1;
        for (int i = 0; i < n; i++) {
            product *= arr[i];
            maxProduct = Math.max(maxProduct, product);
            if (product == 0) {
                product = 1;  // Reset on zero
            }
        }
        
        // Backward pass
        product = 1;
        for (int i = n - 1; i >= 0; i--) {
            product *= arr[i];
            maxProduct = Math.max(maxProduct, product);
            if (product == 0) {
                product = 1;  // Reset on zero
            }
        }
        
        return maxProduct;
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Mixed positive and negative
        int[] arr1 = {2, 3, -2, 4};
        System.out.println("Test 1: [2, 3, -2, 4]");
        System.out.println("Prefix/Suffix: " + subarrayWithMaxProduct(arr1));
        System.out.println("DP: " + maxProductSubarrayDP(arr1));
        System.out.println("Brute Force: " + maxProductSubarrayBruteForce(arr1));
        System.out.println("Expected: 6 (subarray [2, 3])");
        
        // Test case 2: All negative
        int[] arr2 = {-2, -3, -4, -5};
        System.out.println("\nTest 2: [-2, -3, -4, -5]");
        System.out.println("Prefix/Suffix: " + subarrayWithMaxProduct(arr2));
        System.out.println("DP: " + maxProductSubarrayDP(arr2));
        System.out.println("Brute Force: " + maxProductSubarrayBruteForce(arr2));
        System.out.println("Expected: 120 (entire array, even count of negatives)");
        
        // Test case 3: Contains zero
        int[] arr3 = {-2, 0, -1};
        System.out.println("\nTest 3: [-2, 0, -1]");
        System.out.println("Prefix/Suffix: " + subarrayWithMaxProduct(arr3));
        System.out.println("DP: " + maxProductSubarrayDP(arr3));
        System.out.println("Brute Force: " + maxProductSubarrayBruteForce(arr3));
        System.out.println("Expected: 0 (subarray [0] or [-1])");
        
        // Test case 4: Single element
        int[] arr4 = {0};
        System.out.println("\nTest 4: [0]");
        System.out.println("Prefix/Suffix: " + subarrayWithMaxProduct(arr4));
        System.out.println("DP: " + maxProductSubarrayDP(arr4));
        System.out.println("Brute Force: " + maxProductSubarrayBruteForce(arr4));
        System.out.println("Expected: 0");
        
        // Test case 5: Complex case
        int[] arr5 = {2, -5, 3, 1, -4, 0, -10, 2, 8};
        System.out.println("\nTest 5: Complex array");
        System.out.println("Prefix/Suffix: " + subarrayWithMaxProduct(arr5));
        System.out.println("DP: " + maxProductSubarrayDP(arr5));
        System.out.println("Brute Force: " + maxProductSubarrayBruteForce(arr5));
        
        // Test case 6: All positive
        int[] arr6 = {1, 2, 3, 4};
        System.out.println("\nTest 6: [1, 2, 3, 4]");
        System.out.println("Prefix/Suffix: " + subarrayWithMaxProduct(arr6));
        System.out.println("DP: " + maxProductSubarrayDP(arr6));
        System.out.println("Brute Force: " + maxProductSubarrayBruteForce(arr6));
        System.out.println("Expected: 24 (entire array)");
        
        // Test case 7: Large array for performance
        System.out.println("\n=== Performance Test ===");
        int[] largeArr = new int[100000];
        java.util.Random rand = new java.util.Random();
        for (int i = 0; i < largeArr.length; i++) {
            largeArr[i] = rand.nextInt(21) - 10; // Range -10 to 10
        }
        
        long startTime = System.currentTimeMillis();
        int result1 = subarrayWithMaxProduct(largeArr);
        long endTime = System.currentTimeMillis();
        System.out.println("Prefix/Suffix Time: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        int result2 = maxProductSubarrayDP(largeArr);
        endTime = System.currentTimeMillis();
        System.out.println("DP Time: " + (endTime - startTime) + "ms");
        
        // Verify with brute force on small array
        System.out.println("\n=== Verification with Brute Force ===");
        int[] testArr = {2, -1, 3, 0, -2, 4};
        int algoResult = subarrayWithMaxProduct(testArr);
        int bruteResult = maxProductSubarrayBruteForce(testArr);
        System.out.println("Algorithm Result: " + algoResult);
        System.out.println("Brute Force Result: " + bruteResult);
        System.out.println("Match: " + (algoResult == bruteResult));
    }
    
    /**
     * Why prefix/suffix approach works:
     * 
     * Consider array: [a, b, c, d, e]
     * 
     * All possible subarrays can be categorized as:
     * 1. Starting from left: [a], [a,b], [a,b,c], [a,b,c,d], [a,b,c,d,e]
     * 2. Ending at right: [e], [d,e], [c,d,e], [b,c,d,e], [a,b,c,d,e]
     * 3. Others: [b], [b,c], [b,c,d], [c], [c,d], [d]
     * 
     * But notice: [b,c,d] = total product / (a*e)
     * 
     * Actually, the key insight is:
     * - If array has no zeros, max product is either:
     *   a) Entire array product (if even number of negatives)
     *   b) Product excluding some prefix or suffix (if odd number of negatives)
     * 
     * - If array has zeros, split at zeros and solve for each segment
     * 
     * Our algorithm computes products from both ends, effectively
     * considering all possible segments.
     */
    
    /**
     * Visual Example:
     * 
     * Array: [2, 3, -2, 4, -1]
     * 
     * Prefix products (left to right):
     * i=0: 2
     * i=1: 2*3=6
     * i=2: 6*(-2)=-12
     * i=3: -12*4=-48
     * i=4: -48*(-1)=48
     * 
     * Suffix products (right to left):
     * i=4: -1
     * i=3: -1*4=-4
     * i=2: -4*(-2)=8
     * i=1: 8*3=24
     * i=0: 24*2=48
     * 
     * Max of all these: 48
     * Subarray: entire array [2,3,-2,4,-1] = 48
     * 
     * Another example: [2, -5, 3, 1, -4]
     * Prefix: 2, -10, -30, -30, 120
     * Suffix: -4, -12, -12, -20, 120
     * Max: 120 (entire array)
     */
    
    /**
     * Dynamic Programming Explanation:
     * 
     * At each position i, we track:
     * - maxSoFar: maximum product ending at i
     * - minSoFar: minimum product ending at i (important for negatives)
     * 
     * For next element nums[i]:
     * tempMax = max(nums[i], maxSoFar*nums[i], minSoFar*nums[i])
     * minSoFar = min(nums[i], maxSoFar*nums[i], minSoFar*nums[i])
     * maxSoFar = tempMax
     * 
     * Why track min? Because when we see negative number:
     * - Current negative * previous min (negative) = positive
     * - This positive could become new maximum
     * 
     * Example: [-2, 3, -4]
     * i=0: max=-2, min=-2
     * i=1: tempMax=max(3, -2*3=-6, -2*3=-6)=3
     *      min=min(3, -6, -6)=-6
     *      max=3
     * i=2: tempMax=max(-4, 3*(-4)=-12, -6*(-4)=24)=24
     *      min=min(-4, -12, 24)=-12
     *      max=24
     * Result: 24 (subarray [3, -4])
     */
    
    /**
     * Edge Cases to Consider:
     * 
     * 1. Single element arrays: Return that element
     * 2. All zeros: Maximum product is 0
     * 3. All negatives with odd count: Exclude smallest negative
     * 4. Product overflow: Use long if constraints allow larger numbers
     * 5. Empty array: Usually return 0 or throw exception
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Only tracking maximum, not minimum:
     *    - Fails when negative * negative = positive
     * 
     * 2. Resetting product to 0 instead of 1 when encountering zero:
     *    - Product becomes permanently zero
     * 
     * 3. Not considering single element subarrays:
     *    - Maximum might be a single element
     * 
     * 4. Integer overflow:
     *    - Product of many numbers can exceed 32-bit range
     *    - Use long or handle with early termination
     */
    
    /**
     * Optimization Notes:
     * 
     * 1. Early termination:
     *    - If product becomes 0, can skip to next segment
     * 
     * 2. Zero handling:
     *    - Split array at zeros, solve each segment separately
     * 
     * 3. Sign tracking:
     *    - Count negative numbers to determine if product can be positive
     */
    
    /**
     * Related Problems:
     * 
     * 1. Maximum Sum Subarray (Kadane's Algorithm)
     * 2. Maximum Product Subarray (this problem)
     * 3. Maximum Length of Subarray With Positive Product
     * 4. Subarray Product Less Than K
     * 5. Maximum Absolute Sum of Any Subarray
     */
    
    /**
     * Applications:
     * 
     * 1. Financial analysis: Maximum profit with leverage (multiplication)
     * 2. Signal processing: Maximum amplitude product
     * 3. Game theory: Maximum cumulative advantage
     * 4. Machine learning: Feature interaction strength
     */
    
    /**
     * Complexity Analysis:
     * 
     * Time:
     * - Prefix/Suffix: O(n) single pass
     * - DP: O(n) single pass
     * - Brute force: O(n²)
     * 
     * Space:
     * - Prefix/Suffix: O(1)
     * - DP: O(1)
     * - Brute force: O(1)
     */
}
