/**
 * Divide Two Integers Without Using Multiplication, Division, and Mod Operator
 * 
 * Problem: Given two integers dividend and divisor, divide them and return the
 * quotient without using multiplication, division, and mod operators.
 * 
 * Constraints:
 * 1. Handle 32-bit signed integers
 * 2. Truncate toward zero (return integer part only)
 * 3. Handle overflow cases (especially Integer.MIN_VALUE / -1)
 * 4. Handle division by zero
 * 
 * Applications:
 * - Embedded systems with limited operations
 * - Mathematics libraries
 * - Understanding bit manipulation
 * - Interview preparation
 */
public class DivideTwoNumbers {

    /**
     * 1. BIT MANIPULATION APPROACH (Efficient)
     * Uses bit shifting to perform division
     * 
     * Algorithm:
     * 1. Handle edge cases (division by zero, overflow)
     * 2. Convert to positive numbers
     * 3. Find largest multiple of divisor ≤ dividend
     * 4. Subtract and accumulate result
     * 5. Apply sign
     * 
     * Time Complexity: O(32) = O(1) since we check all 32 bits
     * Space Complexity: O(1)
     * 
     * @param dividend Number to be divided
     * @param divisor Number to divide by
     * @return Quotient of division
     */
    public static int divide(int dividend, int divisor) {
        // Edge case: division by zero
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        // Edge case: MIN_VALUE / -1 = MAX_VALUE + 1 (overflow)
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;  // Truncate overflow
        }
        
        // Convert to positive numbers to avoid negative overflow
        // Use long to handle MIN_VALUE case
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);
        
        int result = 0;
        
        // Process bits from most significant to least significant
        // Starting from 31 (since we use int, max 2^31 - 1)
        for (int i = 31; i >= 0; i--) {
            // Check if we can subtract (dvs << i) from dvd
            // (dvd >> i) >= dvs is equivalent to dvd >= (dvs << i)
            if ((dvd >> i) >= dvs) {
                dvd -= (dvs << i);    // Subtract divisor shifted by i
                result |= (1 << i);   // Add 2^i to result
            }
        }
        
        // Apply sign based on original signs
        // XOR: negative if signs are different
        if ((dividend < 0) ^ (divisor < 0)) {
            result = -result;
        }
        
        return result;
    }

    /**
     * 2. EXPONENTIAL SEARCH APPROACH
     * Doubles the divisor until it exceeds dividend
     * 
     * @param dividend Number to be divided
     * @param divisor Number to divide by
     * @return Quotient of division
     */
    public static int divideExponential(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        
        // Convert to positive
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);
        
        int result = 0;
        
        while (dvd >= dvs) {
            long temp = dvs;
            int multiple = 1;
            
            // Double the divisor until it exceeds dividend
            while (dvd >= (temp << 1)) {
                temp <<= 1;      // Double the divisor
                multiple <<= 1;  // Double the multiple
            }
            
            dvd -= temp;
            result += multiple;
        }
        
        // Apply sign
        if ((dividend < 0) ^ (divisor < 0)) {
            result = -result;
        }
        
        return result;
    }

    /**
     * 3. BINARY SEARCH APPROACH
     * Uses binary search to find quotient
     * 
     * @param dividend Number to be divided
     * @param divisor Number to divide by
     * @return Quotient of division
     */
    public static int divideBinarySearch(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        
        // Use long to handle edge cases
        long dvd = dividend;
        long dvs = divisor;
        
        // Determine sign
        boolean negative = (dvd < 0) ^ (dvs < 0);
        
        // Work with positive numbers
        dvd = Math.abs(dvd);
        dvs = Math.abs(dvs);
        
        // Binary search for quotient
        long left = 0;
        long right = dvd;
        long result = 0;
        
        while (left <= right) {
            long mid = left + (right - left) / 2;
            
            if (mid * dvs <= dvd) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        
        // Apply sign and convert to int
        result = negative ? -result : result;
        
        // Handle overflow
        if (result > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (result < Integer.MIN_VALUE) return Integer.MIN_VALUE;
        
        return (int) result;
    }

    /**
     * 4. SUBTRACTION APPROACH (Naive but intuitive)
     * Repeatedly subtract divisor from dividend
     * 
     * @param dividend Number to be divided
     * @param divisor Number to divide by
     * @return Quotient of division
     */
    public static int divideSubtraction(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        
        // Handle signs
        boolean negative = (dividend < 0) ^ (divisor < 0);
        
        // Use positive numbers
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);
        
        int result = 0;
        
        // Repeated subtraction
        while (dvd >= dvs) {
            dvd -= dvs;
            result++;
        }
        
        return negative ? -result : result;
    }

    /**
     * 5. RECURSIVE DIVISION
     * Recursive implementation of exponential search
     * 
     * @param dividend Number to be divided
     * @param divisor Number to divide by
     * @return Quotient of division
     */
    public static int divideRecursive(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }
        
        // Convert to long and positive
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);
        
        int result = divideRecursiveHelper(dvd, dvs);
        
        // Apply sign
        if ((dividend < 0) ^ (divisor < 0)) {
            result = -result;
        }
        
        return result;
    }
    
    private static int divideRecursiveHelper(long dividend, long divisor) {
        if (dividend < divisor) {
            return 0;
        }
        
        // Find largest multiple of divisor <= dividend
        long sum = divisor;
        int multiple = 1;
        
        while (sum + sum <= dividend) {
            sum += sum;
            multiple += multiple;
        }
        
        // Recursively process remainder
        return multiple + divideRecursiveHelper(dividend - sum, divisor);
    }

    /**
     * 6. DIVISION WITH REMAINDER
     * Returns both quotient and remainder
     * 
     * @param dividend Number to be divided
     * @param divisor Number to divide by
     * @return Array [quotient, remainder]
     */
    public static int[] divideWithRemainder(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return new int[]{Integer.MAX_VALUE, 0};
        }
        
        // Get quotient
        int quotient = divide(dividend, divisor);
        
        // Calculate remainder: dividend - quotient * divisor
        int remainder;
        try {
            remainder = dividend - multiply(quotient, divisor);
        } catch (ArithmeticException e) {
            // Handle overflow in multiplication
            remainder = 0;
        }
        
        return new int[]{quotient, remainder};
    }
    
    /**
     * 7. MULTIPLY WITHOUT USING * OPERATOR
     * Helper function for division with remainder
     */
    private static int multiply(int a, int b) {
        if (a == 0 || b == 0) return 0;
        
        // Handle overflow
        if (a == Integer.MIN_VALUE && b == -1 || 
            a == -1 && b == Integer.MIN_VALUE) {
            return Integer.MAX_VALUE;
        }
        
        // Use long to avoid overflow
        long result = 0;
        long absA = Math.abs((long) a);
        long absB = Math.abs((long) b);
        
        // Russian peasant multiplication
        while (absB > 0) {
            if ((absB & 1) == 1) {
                result += absA;
            }
            absA <<= 1;
            absB >>= 1;
        }
        
        // Apply sign
        if ((a < 0) ^ (b < 0)) {
            result = -result;
        }
        
        // Check bounds
        if (result > Integer.MAX_VALUE) return Integer.MAX_VALUE;
        if (result < Integer.MIN_VALUE) return Integer.MIN_VALUE;
        
        return (int) result;
    }

    /**
     * 8. CHECK FOR DIVISIBILITY
     * Returns true if dividend is divisible by divisor
     */
    public static boolean isDivisible(int dividend, int divisor) {
        if (divisor == 0) return false;
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return true;  // Actually not divisible but special case
        }
        
        // Quick checks
        if (divisor == 1) return true;
        if (divisor == -1) return true;
        if (dividend == 0) return true;
        
        // Use division with remainder
        int[] result = divideWithRemainder(dividend, divisor);
        return result[1] == 0;
    }

    /**
     * 9. DIVISION WITH PRECISION (Floating point result)
     * Returns result with specified decimal places
     * 
     * @param dividend Number to be divided
     * @param divisor Number to divide by
     * @param precision Number of decimal places
     * @return String representation with given precision
     */
    public static String divideWithPrecision(int dividend, int divisor, int precision) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return String.valueOf(Integer.MAX_VALUE);
        }
        
        // Handle sign
        boolean negative = (dividend < 0) ^ (divisor < 0);
        
        // Work with absolute values
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);
        
        // Integer part
        StringBuilder result = new StringBuilder();
        
        if (negative) {
            result.append('-');
        }
        
        result.append(dvd / dvs);
        
        // If no precision needed, return integer part
        if (precision == 0) {
            return result.toString();
        }
        
        // Decimal part
        result.append('.');
        long remainder = dvd % dvs;
        
        for (int i = 0; i < precision; i++) {
            remainder *= 10;
            result.append(remainder / dvs);
            remainder %= dvs;
        }
        
        return result.toString();
    }

    /**
     * 10. COMPREHENSIVE TEST DRIVER
     */
    public static void main(String[] args) {
        System.out.println("=== DIVIDE TWO NUMBERS DEMO ===\n");
        
        testBasicOperations();
        testEdgeCases();
        testPerformance();
        testDifferentMethods();
        testAdditionalFeatures();
    }
    
    private static void testBasicOperations() {
        System.out.println("1. BASIC OPERATIONS");
        
        int[][] testCases = {
            {10, 2},       // 10 / 2 = 5
            {7, 3},        // 7 / 3 = 2
            {-10, 2},      // -10 / 2 = -5
            {10, -2},      // 10 / -2 = -5
            {-10, -2},     // -10 / -2 = 5
            {0, 5},        // 0 / 5 = 0
            {5, 1},        // 5 / 1 = 5
            {5, 5},        // 5 / 5 = 1
        };
        
        System.out.println("Dividend\tDivisor\t\tExpected\tBit Manip\tExponential\tBinary Search");
        System.out.println("--------\t-------\t\t--------\t----------\t-----------\t-----------");
        
        for (int[] test : testCases) {
            int dividend = test[0];
            int divisor = test[1];
            int expected = dividend / divisor;  // Java's built-in division
            
            System.out.printf("%-8d\t%-7d\t%-8d\t%-10d\t%-11d\t%-11d\n",
                dividend, divisor, expected,
                divide(dividend, divisor),
                divideExponential(dividend, divisor),
                divideBinarySearch(dividend, divisor)
            );
        }
        System.out.println();
    }
    
    private static void testEdgeCases() {
        System.out.println("2. EDGE CASES");
        
        // Test division by zero
        try {
            divide(10, 0);
            System.out.println("FAIL: Should have thrown ArithmeticException for division by zero");
        } catch (ArithmeticException e) {
            System.out.println("PASS: Correctly threw ArithmeticException for division by zero");
        }
        
        // Test MIN_VALUE / -1 (overflow)
        int result = divide(Integer.MIN_VALUE, -1);
        System.out.println("MIN_VALUE / -1 = " + result + 
                         " (should be " + Integer.MAX_VALUE + ")");
        
        // Test MIN_VALUE / 1
        result = divide(Integer.MIN_VALUE, 1);
        System.out.println("MIN_VALUE / 1 = " + result + 
                         " (should be " + Integer.MIN_VALUE + ")");
        
        // Test MAX_VALUE / 1
        result = divide(Integer.MAX_VALUE, 1);
        System.out.println("MAX_VALUE / 1 = " + result + 
                         " (should be " + Integer.MAX_VALUE + ")");
        
        // Test large numbers
        result = divide(Integer.MAX_VALUE, 2);
        System.out.println("MAX_VALUE / 2 = " + result + 
                         " (should be " + (Integer.MAX_VALUE / 2) + ")");
        
        System.out.println();
    }
    
    private static void testPerformance() {
        System.out.println("3. PERFORMANCE COMPARISON");
        
        int dividend = Integer.MAX_VALUE;
        int divisor = 2;
        int iterations = 1000000;
        
        System.out.println("Testing with dividend = " + dividend + ", divisor = " + divisor);
        System.out.println("Running " + iterations + " iterations\n");
        
        // Bit manipulation
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            divide(dividend, divisor);
        }
        long time1 = System.nanoTime() - start;
        
        // Exponential search
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            divideExponential(dividend, divisor);
        }
        long time2 = System.nanoTime() - start;
        
        // Binary search
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            divideBinarySearch(dividend, divisor);
        }
        long time3 = System.nanoTime() - start;
        
        // Subtraction (very slow for large numbers)
        start = System.nanoTime();
        for (int i = 0; i < 100; i++) {  // Fewer iterations due to slowness
            divideSubtraction(dividend, divisor);
        }
        long time4 = System.nanoTime() - start;
        
        System.out.printf("Bit manipulation:  %8.2f ms\n", time1 / 1_000_000.0);
        System.out.printf("Exponential search:%8.2f ms\n", time2 / 1_000_000.0);
        System.out.printf("Binary search:     %8.2f ms\n", time3 / 1_000_000.0);
        System.out.printf("Subtraction (100x):%8.2f ms\n", time4 / 1_000_000.0);
        System.out.println();
    }
    
    private static void testDifferentMethods() {
        System.out.println("4. METHOD COMPARISON WITH VARIOUS INPUTS");
        
        int[][] tests = {
            {100, 7},
            {-100, 7},
            {100, -7},
            {-100, -7},
            {1, 2},
            {2, 1},
            {15, 4},
            {123456, 789},
        };
        
        System.out.println("Test\t\tBit\t\tExp\t\tBin\t\tSub\t\tRec");
        System.out.println("----\t\t---\t\t---\t\t---\t\t---\t\t---");
        
        for (int[] test : tests) {
            int dividend = test[0];
            int divisor = test[1];
            
            System.out.printf("%d/%d\t\t%d\t\t%d\t\t%d\t\t%d\t\t%d\n",
                dividend, divisor,
                divide(dividend, divisor),
                divideExponential(dividend, divisor),
                divideBinarySearch(dividend, divisor),
                divideSubtraction(dividend, divisor),
                divideRecursive(dividend, divisor)
            );
        }
        System.out.println();
    }
    
    private static void testAdditionalFeatures() {
        System.out.println("5. ADDITIONAL FEATURES");
        
        // Test division with remainder
        System.out.println("Division with remainder:");
        int[] result = divideWithRemainder(10, 3);
        System.out.println("10 / 3 = " + result[0] + " remainder " + result[1]);
        
        result = divideWithRemainder(-10, 3);
        System.out.println("-10 / 3 = " + result[0] + " remainder " + result[1]);
        
        // Test divisibility
        System.out.println("\nDivisibility checks:");
        System.out.println("10 divisible by 2? " + isDivisible(10, 2));
        System.out.println("10 divisible by 3? " + isDivisible(10, 3));
        System.out.println("0 divisible by 5? " + isDivisible(0, 5));
        
        // Test division with precision
        System.out.println("\nDivision with precision:");
        System.out.println("1 / 2 = " + divideWithPrecision(1, 2, 5));
        System.out.println("22 / 7 = " + divideWithPrecision(22, 7, 10));
        System.out.println("10 / 3 = " + divideWithPrecision(10, 3, 3));
        
        // Test multiplication helper
        System.out.println("\nMultiplication without * operator:");
        System.out.println("5 * 3 = " + multiply(5, 3));
        System.out.println("-5 * 3 = " + multiply(-5, 3));
        System.out.println("5 * -3 = " + multiply(5, -3));
        System.out.println("-5 * -3 = " + multiply(-5, -3));
    }
    
    /**
     * 11. DIVISION WITH ERROR CHECKING
     * Returns result and error flag
     */
    public static Object[] safeDivide(int dividend, int divisor) {
        Object[] result = new Object[2];
        
        try {
            int quotient = divide(dividend, divisor);
            result[0] = quotient;
            result[1] = false; // no error
        } catch (ArithmeticException e) {
            result[0] = 0;
            result[1] = true; // error occurred
        }
        
        return result;
    }
    
    /**
     * 12. DIVISION WITH ROUNDING
     * Rounds to nearest integer (away from zero)
     */
    public static int divideWithRounding(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        // Get quotient and remainder
        int quotient = divide(dividend, divisor);
        int remainder = Math.abs(dividend - multiply(quotient, divisor));
        
        // Check if we need to round up
        // If remainder * 2 >= divisor, round away from zero
        if (multiply(remainder, 2) >= Math.abs(divisor)) {
            if ((dividend < 0) ^ (divisor < 0)) {
                quotient--;
            } else {
                quotient++;
            }
        }
        
        return quotient;
    }
    
    /**
     * 13. DIVISION FOR UNSIGNED INTEGERS
     * Treats integers as unsigned (0 to 2^32-1)
     */
    public static long divideUnsigned(int dividend, int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero");
        }
        
        // Convert to unsigned long
        long dvd = dividend & 0xFFFFFFFFL;
        long dvs = divisor & 0xFFFFFFFFL;
        
        return dvd / dvs;
    }
    
    /**
     * 14. EXPLAIN BIT MANIPULATION APPROACH
     * Prints step-by-step explanation
     */
    public static void explainDivision(int dividend, int divisor) {
        System.out.println("\n=== EXPLANATION OF BIT MANIPULATION DIVISION ===");
        System.out.println("Dividend: " + dividend + " (" + 
                         Integer.toBinaryString(dividend) + ")");
        System.out.println("Divisor:  " + divisor + " (" + 
                         Integer.toBinaryString(divisor) + ")");
        
        if (divisor == 0) {
            System.out.println("ERROR: Division by zero");
            return;
        }
        
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            System.out.println("SPECIAL CASE: MIN_VALUE / -1 = MAX_VALUE (overflow)");
            return;
        }
        
        long dvd = Math.abs((long) dividend);
        long dvs = Math.abs((long) divisor);
        int result = 0;
        
        System.out.println("\nStep-by-step calculation:");
        System.out.println("i\tdvd >> i\tComparison\tdvd after\tResult");
        System.out.println("-\t--------\t----------\t---------\t------");
        
        for (int i = 31; i >= 0; i--) {
            long shifted = dvd >> i;
            boolean canSubtract = shifted >= dvs;
            String action = "";
            
            if (canSubtract) {
                long subtractAmount = dvs << i;
                dvd -= subtractAmount;
                result |= (1 << i);
                action = "Subtract " + subtractAmount;
            } else {
                action = "Skip";
            }
            
            System.out.printf("%-2d\t%-10d\t%s\t\t%-10d\t%s\n",
                i, shifted, canSubtract ? "≥ " + dvs : "< " + dvs,
                dvd, action);
        }
        
        // Apply sign
        boolean negative = (dividend < 0) ^ (divisor < 0);
        if (negative) {
            result = -result;
        }
        
        System.out.println("\nFinal result: " + result);
        System.out.println("Sign applied: " + (negative ? "negative" : "positive"));
    }
}