/**
 * Problem: Square Root (Integral) - Find floor(sqrt(n))
 * 
 * Problem Description:
 * Given a non-negative integer n, find the largest integer x such that x² ≤ n.
 * In other words, find the floor of the square root of n.
 * 
 * Example 1:
 * Input: n = 25
 * Output: 5 (5² = 25 ≤ 25)
 * 
 * Example 2:
 * Input: n = 20
 * Output: 4 (4² = 16 ≤ 20, 5² = 25 > 20)
 * 
 * Example 3:
 * Input: n = 0
 * Output: 0
 * 
 * Approach: Binary Search on Answer
 * 
 * Key Insight:
 * - We're searching for x in range [0, n] such that x² ≤ n
 * - The function f(x) = x² is monotonic increasing
 * - We want the largest x where f(x) ≤ n
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(1)
 */

public class SQRT {

    /**
     * Finds floor(sqrt(n)) using binary search
     * 
     * @param n - Non-negative integer
     * @return Largest integer x such that x² ≤ n
     * 
     * Algorithm:
     * 1. Handle edge cases: n ≤ 1
     * 2. Binary search in range [1, n]
     * 3. For each mid, calculate mid²
     * 4. Compare with n and adjust search range
     * 5. Track the largest mid where mid² ≤ n
     */
    public static int integerSqrt(int n) {
        // Edge cases
        if (n < 0) {
            throw new IllegalArgumentException("Input must be non-negative");
        }
        
        if (n <= 1) {
            return n; // sqrt(0)=0, sqrt(1)=1
        }
        
        System.out.println("Finding floor(sqrt(" + n + "))");
        System.out.println("Binary search range: [1, " + n + "]");
        
        int low = 1;
        int high = n;
        int answer = 0;
        int iteration = 1;
        
        while (low <= high) {
            int mid = low + (high - low) / 2;
            long square = (long) mid * mid; // Use long to prevent overflow
            
            System.out.printf("\nIteration %d:\n", iteration);
            System.out.printf("  low=%d, high=%d, mid=%d\n", low, high, mid);
            System.out.printf("  %d² = %d (compare with %d)\n", mid, square, n);
            
            if (square == n) {
                System.out.println("  ✓ Exact square found!");
                return mid;
            } else if (square < n) {
                System.out.println("  ✓ square < n, update answer and search right");
                answer = mid; // This is a valid candidate
                low = mid + 1; // Try larger value
            } else {
                System.out.println("  ✗ square > n, search left");
                high = mid - 1; // Try smaller value
            }
            
            iteration++;
        }
        
        System.out.println("\nFinal answer: " + answer);
        System.out.printf("Verification: %d² = %d ≤ %d < %d² = %d\n",
            answer, (long)answer * answer, n, answer + 1, (long)(answer + 1) * (answer + 1));
        
        return answer;
    }
    
    /**
     * Alternative implementation with optimization
     * Upper bound can be n/2 for n > 1 since sqrt(n) ≤ n/2 for n ≥ 4
     */
    public static int sqrtOptimized(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input must be non-negative");
        }
        
        if (n <= 1) {
            return n;
        }
        
        // For n > 1, sqrt(n) ≤ n/2
        int left = 1;
        int right = n / 2;
        int result = 0;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            long square = (long) mid * mid;
            
            if (square == n) {
                return mid;
            } else if (square < n) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        return result;
    }
    
    /**
     * Newton's Method (Heron's Method) for comparison
     * Faster convergence for large numbers
     */
    public static int sqrtNewton(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input must be non-negative");
        }
        
        if (n <= 1) {
            return n;
        }
        
        // Initial guess: n/2 is a reasonable starting point
        double x = n / 2.0;
        double epsilon = 0.1; // Precision
        
        // Newton's iteration: x_{k+1} = 0.5 * (x_k + n/x_k)
        while (Math.abs(x * x - n) > epsilon) {
            x = 0.5 * (x + n / x);
        }
        
        return (int) x; // Return floor
    }
    
    /**
     * Integer-only Newton's method (no floating point)
     */
    public static int sqrtNewtonInt(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input must be non-negative");
        }
        
        if (n <= 1) {
            return n;
        }
        
        // Initial guess
        int x = n / 2;
        
        // Keep iterating until convergence
        while (true) {
            int nextX = (x + n / x) / 2;
            
            // If next guess is not better, we've converged
            if (nextX >= x) {
                return x;
            }
            x = nextX;
        }
    }
    
    /**
     * Linear search for comparison (slow)
     */
    public static int sqrtLinear(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Input must be non-negative");
        }
        
        if (n <= 1) {
            return n;
        }
        
        // Find largest x such that x*x ≤ n
        int x = 1;
        while ((long)x * x <= n) {
            x++;
        }
        
        return x - 1;
    }
    
    public static void main(String[] args) {
        System.out.println("=== Square Root (Integral) ===\n");
        
        // Test Case 1: Perfect square
        System.out.println("Test Case 1: Perfect square");
        int n1 = 25;
        int result1 = integerSqrt(n1);
        System.out.println("sqrt(" + n1 + ") = " + result1);
        System.out.println("Expected: 5");
        System.out.println("Match: " + (result1 == 5 ? "✓" : "✗") + "\n");
        
        // Test Case 2: Non-perfect square
        System.out.println("Test Case 2: Non-perfect square");
        int n2 = 20;
        int result2 = integerSqrt(n2);
        System.out.println("sqrt(" + n2 + ") = " + result2);
        System.out.println("Expected: 4 (since 4²=16 ≤ 20 < 5²=25)");
        System.out.println("Match: " + (result2 == 4 ? "✓" : "✗") + "\n");
        
        // Test Case 3: n = 0
        System.out.println("Test Case 3: n = 0");
        int n3 = 0;
        int result3 = integerSqrt(n3);
        System.out.println("sqrt(" + n3 + ") = " + result3);
        System.out.println("Expected: 0");
        System.out.println("Match: " + (result3 == 0 ? "✓" : "✗") + "\n");
        
        // Test Case 4: n = 1
        System.out.println("Test Case 4: n = 1");
        int n4 = 1;
        int result4 = integerSqrt(n4);
        System.out.println("sqrt(" + n4 + ") = " + result4);
        System.out.println("Expected: 1");
        System.out.println("Match: " + (result4 == 1 ? "✓" : "✗") + "\n");
        
        // Test Case 5: n = 2
        System.out.println("Test Case 5: n = 2");
        int n5 = 2;
        int result5 = integerSqrt(n5);
        System.out.println("sqrt(" + n5 + ") = " + result5);
        System.out.println("Expected: 1 (since 1²=1 ≤ 2 < 2²=4)");
        System.out.println("Match: " + (result5 == 1 ? "✓" : "✗") + "\n");
        
        // Test Case 6: n = 100 (perfect square)
        System.out.println("Test Case 6: n = 100");
        int n6 = 100;
        int result6 = integerSqrt(n6);
        System.out.println("sqrt(" + n6 + ") = " + result6);
        System.out.println("Expected: 10");
        System.out.println("Match: " + (result6 == 10 ? "✓" : "✗") + "\n");
        
        // Test Case 7: n = 99 (just below perfect square)
        System.out.println("Test Case 7: n = 99");
        int n7 = 99;
        int result7 = integerSqrt(n7);
        System.out.println("sqrt(" + n7 + ") = " + result7);
        System.out.println("Expected: 9 (since 9²=81 ≤ 99 < 10²=100)");
        System.out.println("Match: " + (result7 == 9 ? "✓" : "✗") + "\n");
        
        // Test Case 8: Large number
        System.out.println("Test Case 8: Large number");
        int n8 = 1000000;
        int result8 = integerSqrt(n8);
        System.out.println("sqrt(" + n8 + ") = " + result8);
        System.out.println("Expected: 1000");
        System.out.println("Match: " + (result8 == 1000 ? "✓" : "✗") + "\n");
        
        // Test Case 9: Maximum integer value
        System.out.println("Test Case 9: Maximum integer");
        int n9 = Integer.MAX_VALUE;
        int result9 = integerSqrt(n9);
        System.out.println("sqrt(" + n9 + ") = " + result9);
        int expected9 = (int) Math.sqrt(Integer.MAX_VALUE);
        System.out.println("Expected: " + expected9);
        System.out.println("Match: " + (result9 == expected9 ? "✓" : "✗") + "\n");
        
        // Test Case 10: Very large square
        System.out.println("Test Case 10: Very large square");
        int n10 = 46340 * 46340; // Largest square that fits in int
        int result10 = integerSqrt(n10);
        System.out.println("sqrt(" + n10 + ") = " + result10);
        System.out.println("Expected: 46340");
        System.out.println("Match: " + (result10 == 46340 ? "✓" : "✗") + "\n");
        
        // Performance comparison
        System.out.println("=== Performance Comparison ===");
        int[] testNumbers = {100, 10000, 1000000, Integer.MAX_VALUE};
        
        for (int n : testNumbers) {
            System.out.println("\nTesting n = " + n + ":");
            
            long startTime = System.nanoTime();
            int binaryResult = integerSqrt(n);
            long binaryTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            int optimizedResult = sqrtOptimized(n);
            long optimizedTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            int newtonResult = sqrtNewtonInt(n);
            long newtonTime = System.nanoTime() - startTime;
            
            startTime = System.nanoTime();
            int linearResult = sqrtLinear(n);
            long linearTime = System.nanoTime() - startTime;
            
            System.out.println("Binary Search: " + binaryResult + " (" + binaryTime/1000 + " μs)");
            System.out.println("Optimized Binary: " + optimizedResult + " (" + optimizedTime/1000 + " μs)");
            System.out.println("Newton's Method: " + newtonResult + " (" + newtonTime/1000 + " μs)");
            System.out.println("Linear Search: " + linearResult + " (" + linearTime/1000 + " μs)");
            
            // Verify all methods give same result
            boolean allMatch = (binaryResult == optimizedResult) && 
                              (optimizedResult == newtonResult) && 
                              (newtonResult == linearResult);
            System.out.println("All methods match: " + (allMatch ? "✓" : "✗"));
        }
        
        // Additional verification with random tests
        System.out.println("\n=== Random Test Verification ===");
        int numTests = 1000;
        int passed = 0;
        
        for (int test = 0; test < numTests; test++) {
            int n = (int)(Math.random() * 1000000);
            
            int binaryAns = integerSqrt(n);
            int linearAns = sqrtLinear(n);
            
            if (binaryAns == linearAns) {
                passed++;
            } else {
                System.out.println("Mismatch: n=" + n + 
                                 ", binary=" + binaryAns + 
                                 ", linear=" + linearAns);
            }
        }
        
        System.out.println("Passed " + passed + "/" + numTests + " random tests");
        
        // Test edge cases for overflow
        System.out.println("\n=== Overflow Test ===");
        System.out.println("Testing n = " + Integer.MAX_VALUE);
        System.out.println("Using long to prevent overflow:");
        int n = Integer.MAX_VALUE;
        int low = 1, high = n, ans = 0;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            // CRITICAL: Use long for multiplication
            long square = (long) mid * mid;
            if (square == n) {
                ans = mid;
                break;
            } else if (square < n) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        System.out.println("sqrt(" + n + ") = " + ans);
        System.out.println("Expected: " + (int)Math.sqrt(n));
    }
}

/**
 * MATHEMATICAL INSIGHTS:
 * 
 * 1. Search Space Optimization:
 *    For n ≥ 4, sqrt(n) ≤ n/2
 *    Proof: For n ≥ 4, (n/2)² = n²/4 ≥ n (since n ≥ 4)
 *    So we can set high = n/2 for n > 1
 * 
 * 2. Overflow Prevention:
 *    When calculating mid * mid, use long to prevent integer overflow
 *    Example: mid = 46341, mid² = 2,147,488,281 > Integer.MAX_VALUE
 * 
 * 3. Monotonic Property:
 *    f(x) = x² is strictly increasing for x ≥ 0
 *    This guarantees binary search works
 */

/**
 * TIME COMPLEXITY ANALYSIS:
 * 
 * Binary Search:
 * - Search space: 1 to n (or n/2 with optimization)
 * - Iterations: O(log n)
 * - Each iteration: O(1) operations
 * - Total: O(log n)
 * 
 * Newton's Method:
 * - Converges quadratically (doubles correct digits each iteration)
 * - Typically O(log log n) iterations for desired precision
 * - Each iteration: O(1) operations
 * - Total: O(log log n) (faster for large n)
 * 
 * Linear Search:
 * - O(√n) iterations
 * - Each iteration: O(1) operations  
 * - Total: O(√n)
 */

/**
 * VISUAL EXAMPLE:
 * 
 * n = 20
 * 
 * Iteration 1: low=1, high=20, mid=10
 *   10² = 100 > 20 → high=9
 * 
 * Iteration 2: low=1, high=9, mid=5  
 *   5² = 25 > 20 → high=4
 * 
 * Iteration 3: low=1, high=4, mid=2
 *   2² = 4 < 20 → answer=2, low=3
 * 
 * Iteration 4: low=3, high=4, mid=3
 *   3² = 9 < 20 → answer=3, low=4
 * 
 * Iteration 5: low=4, high=4, mid=4
 *   4² = 16 < 20 → answer=4, low=5
 * 
 * Loop ends (low=5 > high=4)
 * Answer = 4
 */

/**
 * EDGE CASES:
 * 
 * 1. n = 0: sqrt(0) = 0
 * 2. n = 1: sqrt(1) = 1
 * 3. n = 2, 3: sqrt = 1
 * 4. n negative: Should throw exception
 * 5. n = Integer.MAX_VALUE: Handle overflow with long
 * 6. Perfect squares: Should return exact root
 */

/**
 * RELATED PROBLEMS:
 * 
 * 1. Pow(x, n): Compute x^n efficiently
 * 2. Perfect Square Check: Determine if number is perfect square
 * 3. Cube Root: Extension to cube root
 * 4. Sqrt(x) with precision: Return floating point sqrt
 * 5. Binary Search general pattern
 */

/**
 * PRACTICAL APPLICATIONS:
 * 
 * 1. Computer Graphics: Distance calculations
 * 2. Physics: Magnitude calculations
 * 3. Cryptography: Number theory algorithms
 * 4. Machine Learning: Distance metrics
 * 5. Game Development: Collision detection
 */

/**
 * LEETCODE VERSION (69. Sqrt(x)):
 * This is LeetCode 69: Sqrt(x)
 * Constraints:
 * - 0 <= x <= 2^31 - 1
 * - Return floor(sqrt(x))
 * 
 * Must handle Integer.MAX_VALUE = 2,147,483,647
 * sqrt(Integer.MAX_VALUE) ≈ 46,340.95
 */

/**
 * OPTIMIZATION TIPS:
 * 
 * 1. Use long for multiplication to prevent overflow
 * 2. Optimize upper bound: high = n/2 for n > 1
 * 3. Early return for n <= 1
 * 4. Use while (left <= right) for standard binary search
 * 5. For maximum speed, use Newton's method
 */

/**
 * COMMON MISTAKES:
 * 
 * 1. Integer Overflow:
 *    Wrong: int square = mid * mid;
 *    Right: long square = (long) mid * mid;
 * 
 * 2. Infinite Loop:
 *    Wrong termination condition
 * 
 * 3. Off-by-One:
 *    Returning wrong value for non-perfect squares
 * 
 * 4. Not Handling Edge Cases:
 *    n = 0, n = 1, n negative
 */

/**
 * ALTERNATIVE APPROACHES:
 * 
 * 1. Newton's Method (Heron's Method):
 *    x_{k+1} = 0.5 * (x_k + n/x_k)
 *    Faster convergence for large n
 * 
 * 2. Bit Manipulation:
 *    For integers, can use bitwise operations
 * 
 * 3. Built-in Methods:
 *    Math.sqrt() but that returns double
 * 
 * 4. Precomputed Tables:
 *    For limited range of n
 */