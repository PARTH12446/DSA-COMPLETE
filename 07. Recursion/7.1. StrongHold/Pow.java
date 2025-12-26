/**
 * Class implementing fast exponentiation (power) algorithm
 * 
 * Problem: Implement pow(x, n) which calculates x raised to the power n
 * 
 * Approach: Fast Exponentiation (Exponentiation by Squaring)
 * 
 * Key Insights:
 * 1. Naive approach: Multiply x n times → O(n) time
 * 2. Fast exponentiation: Use divide and conquer → O(log n) time
 * 
 * Mathematical Principle:
 * x^n = {
 *   1                     if n = 0
 *   (x^(n/2))^2           if n is even
 *   x * (x^((n-1)/2))^2   if n is odd
 * }
 * 
 * Example: 2^10 = (2^5)^2 = (2 * 2^4)^2 = (2 * (2^2)^2)^2
 * 
 * Special Cases:
 * - n = 0 → return 1 (x^0 = 1 for all x ≠ 0)
 * - n < 0 → return 1/x^(-n)
 * - x = 0, n ≤ 0 → undefined (handled as 0 or error)
 * 
 * Time Complexity: O(log n)
 * Space Complexity: O(log n) for recursion, O(1) for iterative version
 */
public class Pow {

    /**
     * Calculates x raised to the power n using fast exponentiation
     * 
     * @param x Base number
     * @param n Exponent (can be negative)
     * @return x^n as double
     * 
     * Algorithm Steps:
     * 1. Handle negative exponent: convert to positive by using reciprocal
     * 2. Use fast exponentiation (divide and conquer)
     * 3. Handle edge cases: n=0, n=Integer.MIN_VALUE
     * 
     * Note: Uses long for n to handle Integer.MIN_VALUE overflow
     */
    public static double myPow(double x, int n) {
        System.out.println("\n=== Calculating " + x + " ^ " + n + " ===");
        
        // Step 1: Convert n to long to handle Integer.MIN_VALUE overflow
        long exp = n;
        
        // Step 2: Handle negative exponent
        if (exp < 0) {
            System.out.println("Negative exponent detected: " + exp);
            System.out.println("Converting: " + x + " ^ " + exp + " = 1/(" + x + " ^ " + (-exp) + ")");
            x = 1.0 / x;
            exp = -exp;  // Make exponent positive
            System.out.println("New base: " + x + ", New exponent: " + exp);
        }
        
        // Step 3: Fast exponentiation
        double result = fastPow(x, exp);
        System.out.println("Result: " + result);
        return result;
    }

    /**
     * Recursive fast exponentiation using divide and conquer
     * 
     * @param x Base number
     * @param n Non-negative exponent
     * @return x^n
     * 
     * Recurrence Relation:
     * fastPow(x, n) = {
     *   1                  if n == 0
     *   fastPow(x, n/2)^2  if n is even
     *   x * fastPow(x, n/2)^2 if n is odd
     * }
     * 
     * Time Complexity: O(log n) - halves the exponent each time
     * Space Complexity: O(log n) - recursion stack depth
     */
    private static double fastPow(double x, long n) {
        System.out.println("  fastPow(" + x + ", " + n + ")");
        
        // Base case: x^0 = 1
        if (n == 0) {
            System.out.println("    Base case: " + x + "^0 = 1");
            return 1.0;
        }
        
        // Recursive case: compute x^(n/2)
        double half = fastPow(x, n / 2);
        System.out.println("    Computed half: " + x + "^" + (n/2) + " = " + half);
        
        double result;
        // If n is even: x^n = (x^(n/2))^2
        if (n % 2 == 0) {
            result = half * half;
            System.out.println("    Even exponent: " + x + "^" + n + " = (" + 
                             x + "^" + (n/2) + ")^2 = " + half + "^2 = " + result);
        } 
        // If n is odd: x^n = x * (x^((n-1)/2))^2 = x * (x^(n/2))^2
        else {
            result = half * half * x;
            System.out.println("    Odd exponent: " + x + "^" + n + " = " + x + 
                             " * (" + x + "^" + (n/2) + ")^2 = " + x + " * " + 
                             half + "^2 = " + result);
        }
        
        return result;
    }
    
    /**
     * Iterative version of fast exponentiation (more space efficient)
     * Uses binary representation of exponent
     * 
     * @param x Base number
     * @param n Non-negative exponent
     * @return x^n
     * 
     * Algorithm (Binary Exponentiation):
     * 1. Initialize result = 1
     * 2. While n > 0:
     *    - If n is odd: result *= x
     *    - Square x: x *= x
     *    - Halve n: n >>= 1 (integer division by 2)
     * 3. Return result
     * 
     * Example: x^13 = x^(1101)_2
     * 13 = 8 + 4 + 1 = 2^3 + 2^2 + 2^0
     * x^13 = x^8 * x^4 * x^1
     */
    public static double fastPowIterative(double x, long n) {
        System.out.println("\n=== Iterative Fast Exponentiation ===");
        System.out.println("Computing " + x + " ^ " + n + " iteratively");
        
        double result = 1.0;
        double base = x;
        long exp = n;
        
        System.out.println("Binary representation of " + exp + ": " + 
                         Long.toBinaryString(exp));
        
        int step = 1;
        while (exp > 0) {
            System.out.println("\nStep " + step + ":");
            System.out.println("  Current exponent (binary): " + Long.toBinaryString(exp));
            System.out.println("  Current base: " + base);
            System.out.println("  Current result: " + result);
            
            // If current bit is 1 (exponent is odd)
            if ((exp & 1) == 1) {
                result *= base;
                System.out.println("  LSB is 1: result *= base = " + result);
            } else {
                System.out.println("  LSB is 0: result unchanged");
            }
            
            // Square the base
            base *= base;
            System.out.println("  Square base: " + base);
            
            // Right shift exponent (divide by 2)
            exp >>= 1;
            System.out.println("  Right shift exponent: " + exp);
            
            step++;
        }
        
        System.out.println("\nFinal result: " + result);
        return result;
    }
    
    /**
     * Extended version that handles all edge cases including:
     * - x = 0, n ≤ 0
     * - Integer.MIN_VALUE overflow
     * - Very large/small numbers
     */
    public static double myPowExtended(double x, int n) {
        System.out.println("\n=== Extended Power Calculation ===");
        System.out.println("Computing " + x + " ^ " + n);
        
        // Handle x = 0
        if (x == 0.0) {
            if (n > 0) {
                System.out.println("0 ^ positive = 0");
                return 0.0;
            } else if (n == 0) {
                System.out.println("0 ^ 0 is mathematically undefined, returning 1 (common convention)");
                return 1.0;  // Common convention
            } else {
                System.out.println("0 ^ negative = undefined (division by zero), returning " + 
                                 (x > 0 ? "Infinity" : "-Infinity"));
                return x > 0 ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
            }
        }
        
        // Handle n = 0
        if (n == 0) {
            System.out.println("Any non-zero number ^ 0 = 1");
            return 1.0;
        }
        
        // Handle n = 1
        if (n == 1) {
            System.out.println("Any number ^ 1 = itself");
            return x;
        }
        
        // Handle n = -1
        if (n == -1) {
            System.out.println("x ^ -1 = 1/x");
            return 1.0 / x;
        }
        
        // Convert to long to handle Integer.MIN_VALUE
        long exp = n;
        if (exp < 0) {
            x = 1.0 / x;
            exp = -exp;
        }
        
        // Use iterative method for better space efficiency
        return fastPowIterative(x, exp);
    }
    
    /**
     * Visualizes the recursion tree for fast exponentiation
     */
    public static void visualizePow(double x, int n) {
        System.out.println("\n=== Visualizing " + x + " ^ " + n + " ===");
        System.out.println("Recursion Tree:");
        visualizePowHelper(x, n, 0);
    }
    
    private static void visualizePowHelper(double x, long n, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "fastPow(" + x + ", " + n + ")");
        
        if (n == 0) {
            System.out.println(indent + "  → return 1.0");
            return;
        }
        
        if (n % 2 == 0) {
            System.out.println(indent + "  n is even, computing " + x + "^" + (n/2));
            visualizePowHelper(x, n/2, depth + 1);
            System.out.println(indent + "  square the result: (" + x + "^" + (n/2) + ")^2");
        } else {
            System.out.println(indent + "  n is odd, computing " + x + "^" + (n/2));
            visualizePowHelper(x, n/2, depth + 1);
            System.out.println(indent + "  multiply by " + x + ": " + x + " * (" + 
                             x + "^" + (n/2) + ")^2");
        }
    }
    
    /**
     * Compares naive vs fast exponentiation
     */
    public static void performanceComparison(double x, int n) {
        System.out.println("\n=== Performance Comparison ===");
        System.out.println("Computing " + x + " ^ " + n);
        
        // Naive approach
        System.out.println("\n1. Naive approach (O(n)):");
        long start = System.nanoTime();
        double naiveResult = naivePow(x, n);
        long end = System.nanoTime();
        System.out.println("   Result: " + naiveResult);
        System.out.println("   Time: " + (end - start) / 1000.0 + " microseconds");
        
        // Fast exponentiation (recursive)
        System.out.println("\n2. Fast exponentiation - recursive (O(log n)):");
        start = System.nanoTime();
        double fastRecResult = myPow(x, n);
        end = System.nanoTime();
        System.out.println("   Result: " + fastRecResult);
        System.out.println("   Time: " + (end - start) / 1000.0 + " microseconds");
        
        // Fast exponentiation (iterative)
        System.out.println("\n3. Fast exponentiation - iterative (O(log n)):");
        start = System.nanoTime();
        double fastIterResult;
        if (n < 0) {
            fastIterResult = fastPowIterative(1.0/x, -((long)n));
        } else {
            fastIterResult = fastPowIterative(x, n);
        }
        end = System.nanoTime();
        System.out.println("   Result: " + fastIterResult);
        System.out.println("   Time: " + (end - start) / 1000.0 + " microseconds");
        
        // Math.pow for comparison
        System.out.println("\n4. Built-in Math.pow:");
        start = System.nanoTime();
        double mathResult = Math.pow(x, n);
        end = System.nanoTime();
        System.out.println("   Result: " + mathResult);
        System.out.println("   Time: " + (end - start) / 1000.0 + " microseconds");
        
        // Verify results match (within floating point tolerance)
        double tolerance = 1e-10;
        boolean allMatch = Math.abs(naiveResult - fastRecResult) < tolerance &&
                          Math.abs(fastRecResult - fastIterResult) < tolerance &&
                          Math.abs(fastIterResult - mathResult) < tolerance;
        System.out.println("\nAll results match within tolerance: " + 
                          (allMatch ? "✓" : "✗"));
    }
    
    /**
     * Naive implementation (for comparison)
     */
    private static double naivePow(double x, int n) {
        if (n == 0) return 1.0;
        
        double result = 1.0;
        long exp = n;
        
        if (exp < 0) {
            x = 1.0 / x;
            exp = -exp;
        }
        
        for (long i = 0; i < exp; i++) {
            result *= x;
        }
        
        return result;
    }
    
    /**
     * Test various cases
     */
    public static void runTestCases() {
        System.out.println("=== Power Function Test Cases ===\n");
        
        Object[][] testCases = {
            // {x, n, expected (approx), description}
            {2.0, 10, 1024.0, "2^10"},
            {2.0, -2, 0.25, "2^-2 = 1/4"},
            {3.0, 0, 1.0, "3^0 = 1"},
            {1.0, 100, 1.0, "1^100 = 1"},
            {0.0, 5, 0.0, "0^5 = 0"},
            {5.0, 1, 5.0, "5^1 = 5"},
            {4.0, -1, 0.25, "4^-1 = 1/4"},
            {2.0, 31, 2147483648.0, "2^31"},
            {1.5, 3, 3.375, "1.5^3"},
            {-2.0, 3, -8.0, "(-2)^3"},
            {-2.0, 4, 16.0, "(-2)^4"},
            {0.5, 2, 0.25, "0.5^2"},
            {2.0, -3, 0.125, "2^-3"},
            {1.0000001, 1000000, 1.10517, "Small base, large exponent"},
        };
        
        int passed = 0;
        for (Object[] testCase : testCases) {
            double x = (double) testCase[0];
            int n = (int) testCase[1];
            double expected = (double) testCase[2];
            String desc = (String) testCase[3];
            
            double result = myPow(x, n);
            double diff = Math.abs(result - expected);
            boolean success = diff < 1e-9 || 
                            (Double.isInfinite(result) && Double.isInfinite(expected)) ||
                            (Double.isNaN(result) && Double.isNaN(expected));
            
            System.out.printf("%-20s: ", desc);
            System.out.printf("got %.10f, expected %.10f", result, expected);
            System.out.println(" " + (success ? "✓" : "✗"));
            
            if (success) passed++;
        }
        
        System.out.println("\nPassed: " + passed + "/" + testCases.length);
    }
    
    public static void main(String[] args) {
        System.out.println("=== Fast Exponentiation (Power) Algorithm ===\n");
        
        // Example calculations
        System.out.println("Examples:");
        myPow(2.0, 10);
        myPow(2.0, -3);
        myPow(3.0, 5);
        myPow(1.5, 4);
        
        // Visualization
        System.out.println("\n--- Visualization ---");
        visualizePow(2.0, 13);
        
        // Iterative method
        System.out.println("\n--- Iterative Method ---");
        fastPowIterative(2.0, 13);
        
        // Extended version
        System.out.println("\n--- Extended Version ---");
        myPowExtended(0.0, -2);
        myPowExtended(2.0, Integer.MIN_VALUE + 1);  // Handle near MIN_VALUE
        
        // Performance comparison
        System.out.println("\n--- Performance Comparison ---");
        performanceComparison(1.000001, 1000000);
        
        // Test cases
        System.out.println("\n--- Test Cases ---");
        runTestCases();
        
        // Mathematical explanation
        System.out.println("\n=== Mathematical Explanation ===");
        System.out.println("\nBinary Exponentiation Principle:");
        System.out.println("Let n = b_k * 2^k + b_{k-1} * 2^{k-1} + ... + b_0 * 2^0");
        System.out.println("where b_i are bits (0 or 1)");
        System.out.println("Then x^n = x^(Σ b_i * 2^i) = Π (x^(2^i))^b_i");
        System.out.println("\nExample: n = 13 = 1101_2 = 8 + 4 + 1");
        System.out.println("x^13 = x^8 * x^4 * x^1");
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("x^n = {");
        System.out.println("  1                     if n = 0");
        System.out.println("  (x^(n/2))^2           if n is even");
        System.out.println("  x * (x^((n-1)/2))^2   if n is odd");
        System.out.println("}");
        
        // Complexity analysis
        System.out.println("\n=== Complexity Analysis ===");
        System.out.println("Naive approach (multiply n times):");
        System.out.println("  Time: O(n)");
        System.out.println("  Space: O(1)");
        System.out.println("\nFast exponentiation (recursive):");
        System.out.println("  Time: O(log n) - halves exponent each time");
        System.out.println("  Space: O(log n) - recursion stack depth");
        System.out.println("\nFast exponentiation (iterative):");
        System.out.println("  Time: O(log n)");
        System.out.println("  Space: O(1) - no recursion stack");
        
        System.out.println("\n=== Edge Cases ===");
        System.out.println("1. Integer.MIN_VALUE: -(-2147483648) overflows 32-bit int");
        System.out.println("   Solution: Use long for exponent");
        System.out.println("2. x = 0, n ≤ 0: Division by zero");
        System.out.println("   Solution: Handle specially");
        System.out.println("3. Very large exponents: Recursive may cause stack overflow");
        System.out.println("   Solution: Use iterative version");
        System.out.println("4. Floating point precision: Results may have small errors");
        System.out.println("   Solution: Compare with tolerance, not exact equality");
    }
}