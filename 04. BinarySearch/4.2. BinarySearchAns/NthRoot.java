/**
 * Problem: Nth Root of M
 * 
 * Problem Description:
 * Given two integers n and m, find the n-th root of m.
 * If the n-th root is not an integer, return -1.
 * 
 * Example 1:
 * Input: n = 3, m = 27
 * Output: 3 (since 3^3 = 27)
 * 
 * Example 2:
 * Input: n = 4, m = 69
 * Output: -1 (no integer whose 4th power is 69)
 * 
 * Constraints:
 * - 1 ≤ n ≤ 30
 * - 1 ≤ m ≤ 10^9
 * 
 * Approach: Binary Search on Answer
 * 
 * Key Insight:
 * The n-th root of m must lie between 1 and m.
 * We can binary search for x such that x^n = m.
 * Need to handle integer overflow carefully.
 * 
 * Time Complexity: O(log m * n)  (n for power calculation)
 * Space Complexity: O(1)
 */

public class NthRoot {

    /**
     * Finds integer n-th root of m using binary search
     * 
     * @param n - Root degree (e.g., 2 for square root)
     * @param m - Number to find root of
     * @return Integer n-th root if exists, -1 otherwise
     * 
     * Algorithm:
     * 1. Binary search for x in range [1, m]
     * 2. For each candidate mid, calculate mid^n
     * 3. Handle overflow by stopping when value exceeds m
     * 4. Compare with m and adjust search range
     */
    public static int nthRoot(int n, int m) {
        // Edge cases
        if (n == 1) return m;  // 1st root is the number itself
        if (m == 1) return 1;  // 1^n = 1 for any n
        if (m == 0) return 0;  // 0^n = 0 for n > 0
        
        int low = 1;
        int high = m; // n-th root cannot exceed m for n ≥ 1
        
        // Special optimization: for n >= 30, root can't be large
        if (n >= 30) {
            high = Math.min(high, 2); // 2^30 > 10^9
        } else if (n >= 15) {
            high = Math.min(high, 10); // 10^15 > 10^9
        }
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Calculate mid^n with overflow protection
            long result = power(mid, n, m);
            
            if (result == -1) {
                // mid^n > m, search left
                high = mid - 1;
            } else if (result == m) {
                // Found exact root
                return mid;
            } else if (result < m) {
                // mid^n < m, search right
                low = mid + 1;
            } else {
                // Should not reach here
                high = mid - 1;
            }
        }
        
        return -1; // No integer root found
    }
    
    /**
     * Calculates x^n with overflow protection
     * Returns -1 if result exceeds limit
     * 
     * @param x - Base
     * @param n - Exponent
     * @param limit - Upper bound to check overflow
     * @return x^n if ≤ limit, -1 otherwise
     */
    private static long power(int x, int n, int limit) {
        long result = 1;
        
        for (int i = 0; i < n; i++) {
            // Check for overflow before multiplying
            if (result > Long.MAX_VALUE / x) {
                return -1; // Would overflow
            }
            result *= x;
            
            // Early exit if already exceeds limit
            if (result > limit) {
                return -1;
            }
        }
        
        return result;
    }
    
    /**
     * Alternative implementation using binary exponentiation
     */
    public static int nthRootBinaryExponentiation(int n, int m) {
        if (n == 1) return m;
        if (m == 1) return 1;
        
        int low = 1;
        int high = Math.min(m, 100000); // Upper bound optimization
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            
            // Calculate mid^n using binary exponentiation
            long pow = binaryPower(mid, n, m);
            
            if (pow == -1) {
                // Overflow or > m
                high = mid - 1;
            } else if (pow == m) {
                return mid;
            } else if (pow < m) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        
        return -1;
    }
    
    /**
     * Binary exponentiation with overflow check
     */
    private static long binaryPower(int x, int n, int limit) {
        long result = 1;
        long base = x;
        
        while (n > 0) {
            if ((n & 1) == 1) {
                // Check overflow before multiplying
                if (result > Long.MAX_VALUE / base) {
                    return -1;
                }
                result *= base;
                
                // Check if exceeds limit
                if (result > limit) {
                    return -1;
                }
            }
            
            // Check overflow before squaring base
            if (base > Long.MAX_VALUE / base) {
                // Next iteration would overflow, but current n might be 0
                if (n > 1) return -1;
            }
            base *= base;
            n >>= 1;
        }
        
        return result;
    }
    
    /**
     * Optimized version with early pruning
     */
    public static int nthRootOptimized(int n, int m) {
        // Special cases
        if (m == 0) return 0;
        if (m == 1) return 1;
        if (n == 1) return m;
        
        // For large n, the root must be small
        int maxPossible = (int) Math.pow(m, 1.0 / n);
        maxPossible = Math.max(maxPossible, 1);
        
        // Try exact calculation first
        for (int x = Math.max(1, maxPossible - 2); x <= maxPossible + 2; x++) {
            long pow = 1;
            boolean overflow = false;
            
            for (int i = 0; i < n; i++) {
                if (pow > Long.MAX_VALUE / x) {
                    overflow = true;
                    break;
                }
                pow *= x;
                if (pow > m) {
                    overflow = true;
                    break;
                }
            }
            
            if (!overflow && pow == m) {
                return x;
            }
        }
        
        return -1;
    }
    
    /**
     * Main method with comprehensive tests
     */
    public static void main(String[] args) {
        System.out.println("=== Nth Root of M ===\n");
        
        // Test Case 1: Perfect cube
        System.out.println("Test Case 1: Perfect cube");
        int n1 = 3, m1 = 27;
        int result1 = nthRoot(n1, m1);
        System.out.println(n1 + "-th root of " + m1 + " = " + result1);
        System.out.println("Expected: 3 (3^3 = 27)");
        System.out.println("Result: " + (result1 == 3 ? "✓" : "✗") + "\n");
        
        // Test Case 2: Perfect square
        System.out.println("Test Case 2: Perfect square");
        int n2 = 2, m2 = 25;
        int result2 = nthRoot(n2, m2);
        System.out.println(n2 + "-th root of " + m2 + " = " + result2);
        System.out.println("Expected: 5 (5^2 = 25)");
        System.out.println("Result: " + (result2 == 5 ? "✓" : "✗") + "\n");
        
        // Test Case 3: No integer root
        System.out.println("Test Case 3: No integer root");
        int n3 = 4, m3 = 69;
        int result3 = nthRoot(n3, m3);
        System.out.println(n3 + "-th root of " + m3 + " = " + result3);
        System.out.println("Expected: -1 (no integer^4 = 69)");
        System.out.println("Result: " + (result3 == -1 ? "✓" : "✗") + "\n");
        
        // Test Case 4: Root is 1
        System.out.println("Test Case 4: Root is 1");
        int n4 = 10, m4 = 1;
        int result4 = nthRoot(n4, m4);
        System.out.println(n4 + "-th root of " + m4 + " = " + result4);
        System.out.println("Expected: 1 (1^10 = 1)");
        System.out.println("Result: " + (result4 == 1 ? "✓" : "✗") + "\n");
        
        // Test Case 5: Large exponent
        System.out.println("Test Case 5: Large exponent");
        int n5 = 30, m5 = 1;
        int result5 = nthRoot(n5, m5);
        System.out.println(n5 + "-th root of " + m5 + " = " + result5);
        System.out.println("Expected: 1 (1^30 = 1)");
        System.out.println("Result: " + (result5 == 1 ? "✓" : "✗") + "\n");
        
        // Test Case 6: Large number
        System.out.println("Test Case 6: Large number");
        int n6 = 2, m6 = 1000000000;
        int result6 = nthRoot(n6, m6);
        System.out.println(n6 + "-th root of " + m6 + " = " + result6);
        System.out.println("Expected: 31622 (31622^2 = 999950884 ≈ 1e9)");
        System.out.println("Check: " + result6 + "^2 = " + ((long)result6 * result6));
        System.out.println("Result: " + ((long)result6 * result6 <= m6 ? "✓" : "✗") + "\n");
        
        // Test Case 7: Edge case - m = 0
        System.out.println("Test Case 7: m = 0");
        int n7 = 5, m7 = 0;
        int result7 = nthRoot(n7, m7);
        System.out.println(n7 + "-th root of " + m7 + " = " + result7);
        System.out.println("Expected: 0 (0^5 = 0)");
        System.out.println("Result: " + (result7 == 0 ? "✓" : "✗") + "\n");
        
        // Test Case 8: n = 1
        System.out.println("Test Case 8: n = 1");
        int n8 = 1, m8 = 42;
        int result8 = nthRoot(n8, m8);
        System.out.println(n8 + "-th root of " + m8 + " = " + result8);
        System.out.println("Expected: 42 (1st root is the number itself)");
        System.out.println("Result: " + (result8 == 42 ? "✓" : "✗") + "\n");
        
        // Test Case 9: Perfect 10th power
        System.out.println("Test Case 9: Perfect 10th power");
        int n9 = 10, m9 = 1024;
        int result9 = nthRoot(n9, m9);
        System.out.println(n9 + "-th root of " + m9 + " = " + result9);
        System.out.println("Expected: 2 (2^10 = 1024)");
        System.out.println("Result: " + (result9 == 2 ? "✓" : "✗") + "\n");
        
        // Test Case 10: Close but not perfect
        System.out.println("Test Case 10: Close but not perfect");
        int n10 = 3, m10 = 28;
        int result10 = nthRoot(n10, m10);
        System.out.println(n10 + "-th root of " + m10 + " = " + result10);
        System.out.println("Expected: -1 (3^3=27, 4^3=64, no integer cube root of 28)");
        System.out.println("Result: " + (result10 == -1 ? "✓" : "✗") + "\n");
        
        // Performance test
        System.out.println("=== Performance Test ===");
        int nLarge = 7;
        int mLarge = 1000000000;
        
        long startTime = System.nanoTime();
        int resultBinary = nthRoot(nLarge, mLarge);
        long binaryTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int resultOptimized = nthRootOptimized(nLarge, mLarge);
        long optimizedTime = System.nanoTime() - startTime;
        
        startTime = System.nanoTime();
        int resultBinaryExp = nthRootBinaryExponentiation(nLarge, mLarge);
        long binaryExpTime = System.nanoTime() - startTime;
        
        System.out.println(nLarge + "-th root of " + mLarge + ":");
        System.out.println("Binary Search: " + resultBinary + " (" + binaryTime/1000 + " μs)");
        System.out.println("Optimized: " + resultOptimized + " (" + optimizedTime/1000 + " μs)");
        System.out.println("Binary Exponentiation: " + resultBinaryExp + " (" + binaryExpTime/1000 + " μs)");
        
        // Verify all methods give same result
        boolean allMatch = (resultBinary == resultOptimized) && (resultOptimized == resultBinaryExp);
        System.out.println("All methods match: " + (allMatch ? "✓" : "✗"));
        
        // Additional verification
        System.out.println("\n=== Additional Verification ===");
        int[][] testCases = {
            {2, 4},     // √4 = 2
            {2, 8},     // √8 = -1
            {3, 8},     // ∛8 = 2
            {3, 9},     // ∛9 = -1
            {4, 16},    // ⁴√16 = 2
            {4, 15},    // ⁴√15 = -1
            {5, 32},    // ⁵√32 = 2
            {5, 33},    // ⁵√33 = -1
            {6, 64},    // ⁶√64 = 2
            {6, 65},    // ⁶√65 = -1
            {10, 1024}, // ¹⁰√1024 = 2
            {10, 1025}, // ¹⁰√1025 = -1
        };
        
        int passed = 0;
        for (int[] test : testCases) {
            int n = test[0];
            int m = test[1];
            int result = nthRoot(n, m);
            long pow = 1;
            boolean hasRoot = false;
            int root = -1;
            
            // Brute force to find expected result
            for (int x = 1; x <= m; x++) {
                pow = 1;
                boolean overflow = false;
                for (int i = 0; i < n; i++) {
                    if (pow > Long.MAX_VALUE / x) {
                        overflow = true;
                        break;
                    }
                    pow *= x;
                }
                if (!overflow && pow == m) {
                    hasRoot = true;
                    root = x;
                    break;
                } else if (pow > m || overflow) {
                    break;
                }
            }
            
            int expected = hasRoot ? root : -1;
            boolean correct = (result == expected);
            if (correct) passed++;
            
            System.out.printf("n=%d, m=%d: got %d, expected %d %s%n",
                n, m, result, expected, correct ? "✓" : "✗");
        }
        System.out.println("Passed: " + passed + "/" + testCases.length);
    }
}

/**
 * MATHEMATICAL INSIGHTS:
 * 
 * 1. Range Analysis:
 *    - For n ≥ 2, the n-th root of m is ≤ √m for n=2
 *    - For larger n, the root is even smaller
 *    - Upper bound can be approximated as m^(1/n)
 * 
 * 2. Integer Root Properties:
 *    - If m is prime and n > 1, root can only be 1 or -1
 *    - If n is even and m < 0, no real root exists
 *    - For this problem, we only consider positive integers
 * 
 * 3. Binary Search Validity:
 *    - Function f(x) = x^n is monotonic increasing for x > 0
 *    - Binary search works because:
 *        if x^n < m, then y^n < m for all y < x
 *        if x^n > m, then y^n > m for all y > x
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * 1. Binary Search with Linear Power:
 *    - Binary search iterations: O(log m)
 *    - Each power calculation: O(n)
 *    - Total: O(n log m)
 * 
 * 2. Binary Search with Binary Exponentiation:
 *    - Binary search iterations: O(log m)
 *    - Each power calculation: O(log n)
 *    - Total: O(log m * log n)
 * 
 * 3. Optimized Search:
 *    - Try values around m^(1/n): O(1) to O(n)
 *    - Best for large m
 */

/**
 * OVERFLOW HANDLING:
 * 
 * Critical issue: For large n and x, x^n can overflow 64-bit long.
 * Example: 10^19 > 2^63
 * 
 * Solutions:
 * 1. Check before multiplication: if (result > Long.MAX_VALUE / x) → overflow
 * 2. Early exit: stop if result > m (we only care if ≤ m)
 * 3. Use BigInteger for unlimited precision (but slower)
 */

/**
 * OPTIMIZATION TECHNIQUES:
 * 
 * 1. Range Reduction:
 *    - For n ≥ 30, x ≤ 2 (since 2^30 ≈ 1e9)
 *    - For n ≥ 15, x ≤ 10
 *    - For n ≥ 10, x ≤ 100
 * 
 * 2. Early Termination:
 *    - If x^n > m, stop calculating further
 *    - If x becomes too large, no need to continue
 * 
 * 3. Binary Exponentiation:
 *    - Compute x^n in O(log n) instead of O(n)
 *    - Still need overflow checks
 */

/**
 * ALTERNATIVE APPROACHES:
 * 
 * 1. Math.pow with rounding:
 *    double root = Math.pow(m, 1.0/n);
 *    int intRoot = (int) Math.round(root);
 *    Check if intRoot^n == m
 *    Issues: floating point precision errors
 * 
 * 2. Newton's Method:
 *    Iterative approximation: x_{k+1} = ((n-1)x_k + m/x_k^{n-1})/n
 *    Converges quickly but needs careful initialization
 * 
 * 3. Precomputation:
 *    For fixed n, precompute all possible x^n ≤ m
 *    Use binary search on precomputed array
 */

/**
 * COMMON PITFALLS:
 * 
 * 1. Integer Overflow:
 *    Wrong: long result = (long) Math.pow(mid, n);
 *    Right: Compute power with overflow checks
 * 
 * 2. Off-by-one Errors:
 *    Wrong: high = m (root can be m only for n=1)
 *    Right: high = m (but optimize for large n)
 * 
 * 3. Precision Issues:
 *    Wrong: Using floating point for exact equality
 *    Right: Use integer arithmetic only
 * 
 * 4. Edge Cases:
 *    - n = 1
 *    - m = 0, m = 1
 *    - Large n, small m
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Square Root of Integer: Special case n=2
 * 2. Pow(x, n): Compute x^n efficiently
 * 3. Perfect Squares/Cubes: Check if number is perfect power
 * 4. Integer Break: Maximize product when breaking integer
 */

/**
 * PRACTICAL APPLICATIONS:
 * 
 * 1. Cryptography: Checking if numbers are perfect powers
 * 2. Number Theory: Integer factorization
 * 3. Geometry: Scaling problems with n-dimensional volumes
 * 4. Finance: Compound interest calculations
 */

/**
 * LEETCODE VERSION:
 * If this were a LeetCode problem, we might need to:
 * 1. Handle negative m for odd n
 * 2. Return double for non-integer roots
 * 3. Handle very large n (e.g., n up to 2^31-1)
 * 4. Optimize for multiple queries
 */