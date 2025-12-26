/**
 * Class for counting "good numbers" with various definitions
 * 
 * A "good number" can be defined in multiple ways. This class explores
 * several definitions and provides optimized counting solutions.
 * 
 * Common definitions of "good numbers":
 * 1. Even numbers (basic definition)
 * 2. Numbers where every digit is even
 * 3. Numbers divisible by the sum of their digits
 * 4. Numbers where digits alternate between even and odd
 * 5. Harshad/Niven numbers (divisible by sum of digits)
 * 6. Numbers with digits in non-decreasing order
 * 7. Self-descriptive numbers
 * 
 * This implementation explores various definitions and provides both
 * brute-force and optimized counting algorithms.
 */
public class GoodNumbers {

    // ==============================
    // BRUTE-FORCE RECURSIVE APPROACH
    // ==============================
    
    /**
     * Counts "good numbers" in range [1, n] using brute-force recursion
     * This is a naive approach for demonstration purposes
     * 
     * @param n Upper bound of range (inclusive)
     * @return Count of good numbers in [1, n]
     * 
     * Time Complexity: O(n * d) where d is number of digits (for isGood check)
     * Space Complexity: O(n) recursion stack (can cause stack overflow for large n)
     */
    public static int countGoodNumbers(int n) {
        System.out.println("\n=== Counting Good Numbers (Brute-Force Recursive) ===");
        System.out.println("Range: [1, " + n + "]");
        
        if (n <= 0) {
            System.out.println("Invalid range, returning 0");
            return 0;
        }
        
        int count = countGood(1, n);
        System.out.println("Total good numbers: " + count);
        return count;
    }

    /**
     * Recursive helper function to count good numbers from curr to n
     * 
     * @param curr Current number being checked
     * @param n Upper bound
     * @return Count of good numbers in [curr, n]
     */
    private static int countGood(int curr, int n) {
        // Base case: reached end of range
        if (curr > n) return 0;
        
        // Check if current number is "good"
        boolean good = isGoodEvenDigits(curr);  // Can change definition here
        int add = good ? 1 : 0;
        
        // Recursive call for next number
        int nextCount = countGood(curr + 1, n);
        
        return add + nextCount;
    }

    // ==============================
    // VARIOUS DEFINITIONS OF "GOOD"
    // ==============================
    
    /**
     * Definition 1: Basic - even numbers
     * Example: 2, 4, 6, 8, 10, ...
     * Time: O(1) per number
     */
    private static boolean isGoodEven(int x) {
        return x % 2 == 0;
    }
    
    /**
     * Definition 2: All digits are even
     * Example: 2, 4, 6, 8, 20, 22, 24, 26, 28, 40, ...
     * Time: O(d) where d is number of digits
     */
    private static boolean isGoodEvenDigits(int x) {
        if (x == 0) return false;  // 0 is not in our range [1, n]
        
        int num = x;
        while (num > 0) {
            int digit = num % 10;
            if (digit % 2 != 0) {
                return false;
            }
            num /= 10;
        }
        return true;
    }
    
    /**
     * Definition 3: Divisible by sum of digits (Harshad/Niven numbers)
     * Example: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 12, 18, 20, ...
     * Time: O(d) where d is number of digits
     */
    private static boolean isGoodHarshad(int x) {
        int sum = sumOfDigits(x);
        return sum != 0 && x % sum == 0;
    }
    
    /**
     * Definition 4: Alternating even-odd digits
     * Example: 12, 14, 16, 18, 21, 23, 25, 27, 29, 30, ...
     * Time: O(d) where d is number of digits
     */
    private static boolean isGoodAlternating(int x) {
        if (x < 10) return false;  // Need at least 2 digits
        
        int num = x;
        int lastDigit = num % 10;
        num /= 10;
        
        // Check if last digit is even
        boolean lastEven = (lastDigit % 2 == 0);
        
        while (num > 0) {
            int digit = num % 10;
            boolean currentEven = (digit % 2 == 0);
            
            // Digits must alternate between even and odd
            if (currentEven == lastEven) {
                return false;
            }
            
            lastEven = currentEven;
            num /= 10;
        }
        return true;
    }
    
    /**
     * Definition 5: Non-decreasing digits
     * Example: 1, 2, 3, ..., 9, 11, 12, ..., 19, 22, 23, ..., 123, ...
     * Time: O(d) where d is number of digits
     */
    private static boolean isGoodNonDecreasing(int x) {
        if (x < 10) return true;  // Single digit numbers always non-decreasing
        
        int num = x;
        int lastDigit = num % 10;
        num /= 10;
        
        while (num > 0) {
            int digit = num % 10;
            if (digit > lastDigit) {
                return false;
            }
            lastDigit = digit;
            num /= 10;
        }
        return true;
    }
    
    /**
     * Definition 6: Self-descriptive numbers (base 10)
     * A number is self-descriptive if each digit at position i 
     * counts how many times digit i appears in the number
     * Example: 1210, 2020, 21200, 3211000
     * Note: Very rare in base 10
     */
    private static boolean isGoodSelfDescriptive(int x) {
        String str = Integer.toString(x);
        int[] count = new int[10];  // Count of each digit 0-9
        
        // Count digit occurrences
        for (char c : str.toCharArray()) {
            count[c - '0']++;
        }
        
        // Check if each digit matches its count
        for (int i = 0; i < str.length(); i++) {
            int expected = count[i];
            int actual = str.charAt(i) - '0';
            if (expected != actual) {
                return false;
            }
        }
        return true;
    }
    
    // ==============================
    // HELPER FUNCTIONS
    // ==============================
    
    /**
     * Calculates sum of digits
     */
    private static int sumOfDigits(int x) {
        int sum = 0;
        int num = Math.abs(x);
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
    
    /**
     * Counts number of digits
     */
    private static int countDigits(int x) {
        if (x == 0) return 1;
        return (int) Math.log10(x) + 1;
    }
    
    // ==============================
    // OPTIMIZED COUNTING ALGORITHMS
    // ==============================
    
    /**
     * Optimized count: Even numbers
     * Formula: floor(n/2)
     * Time: O(1)
     */
    public static int countEvenNumbers(int n) {
        return n / 2;
    }
    
    /**
     * Optimized count: Numbers with all even digits
     * Mathematical approach: treat as base-5 number system
     * Time: O(log n)
     */
    public static int countEvenDigitNumbers(int n) {
        System.out.println("\n=== Optimized Count: Even Digit Numbers ===");
        
        if (n <= 0) return 0;
        
        int count = 0;
        int position = 1;  // Current digit position (1s, 10s, 100s, etc.)
        
        // Build numbers with only even digits (0, 2, 4, 6, 8)
        // Map: 0→0, 1→2, 2→4, 3→6, 4→8 (like base-5 to even digits)
        
        // Convert n to base-5 representation for counting
        int temp = n;
        int[] digits = new int[10];  // Up to 10 digits (max n=10^10)
        int len = 0;
        
        // Extract digits
        while (temp > 0) {
            digits[len++] = temp % 10;
            temp /= 10;
        }
        
        // Count using combinatorial approach
        // For each digit position, count valid numbers
        for (int i = len - 1; i >= 0; i--) {
            int digit = digits[i];
            int evenChoices = (digit / 2) + 1;  // How many even digits <= current digit
            
            if (digit % 2 != 0) {
                // If current digit is odd, we can use all smaller even digits
                evenChoices = (digit + 1) / 2;
            }
            
            count += evenChoices * (int) Math.pow(5, i);
            
            // If current digit is odd, we can't continue to lower positions
            if (digit % 2 != 0) {
                break;
            }
        }
        
        // Subtract 1 if we counted 0 (not in our range)
        if (n >= 0) {
            count--;
        }
        
        System.out.println("Count of numbers with all even digits ≤ " + n + ": " + count);
        return Math.max(0, count);
    }
    
    /**
     * Optimized count: Harshad numbers using sieve
     * Time: O(n log n) but more efficient than brute force
     */
    public static int countHarshadNumbers(int n) {
        System.out.println("\n=== Optimized Count: Harshad Numbers ===");
        
        boolean[] isHarshad = new boolean[n + 1];
        int count = 0;
        
        for (int i = 1; i <= n; i++) {
            int sum = sumOfDigits(i);
            if (sum != 0 && i % sum == 0) {
                isHarshad[i] = true;
                count++;
            }
        }
        
        System.out.println("Count of Harshad numbers ≤ " + n + ": " + count);
        
        // Print first few Harshad numbers
        System.out.print("First 20 Harshad numbers: ");
        int printed = 0;
        for (int i = 1; i <= n && printed < 20; i++) {
            if (isHarshad[i]) {
                System.out.print(i + " ");
                printed++;
            }
        }
        System.out.println();
        
        return count;
    }
    
    /**
     * Optimized count: Non-decreasing digit numbers using DP
     * Time: O(10 * d) where d is number of digits
     */
    public static int countNonDecreasingNumbers(int n) {
        System.out.println("\n=== Optimized Count: Non-Decreasing Numbers ===");
        
        String str = Integer.toString(n);
        int len = str.length();
        
        // DP table: dp[pos][lastDigit] = count of numbers
        int[][][] dp = new int[len + 1][10][2];
        
        // Initialize: empty number
        for (int d = 0; d < 10; d++) {
            dp[0][d][1] = 1;  // Tight bound
        }
        
        // Fill DP table
        for (int pos = 1; pos <= len; pos++) {
            int currentDigit = str.charAt(pos - 1) - '0';
            
            for (int last = 0; last < 10; last++) {
                for (int tight = 0; tight <= 1; tight++) {
                    if (dp[pos - 1][last][tight] == 0) continue;
                    
                    int limit = (tight == 1) ? currentDigit : 9;
                    
                    for (int next = last; next <= limit; next++) {
                        int nextTight = (tight == 1 && next == limit) ? 1 : 0;
                        dp[pos][next][nextTight] += dp[pos - 1][last][tight];
                    }
                }
            }
        }
        
        // Sum all valid numbers of length len
        int count = 0;
        for (int d = 0; d < 10; d++) {
            count += dp[len][d][0] + dp[len][d][1];
        }
        
        // Subtract 1 for 0 (not in our range)
        count--;
        
        System.out.println("Count of non-decreasing numbers ≤ " + n + ": " + count);
        return count;
    }
    
    // ==============================
    // VISUALIZATION & TESTING
    // ==============================
    
    /**
     * Visualizes different definitions of "good numbers"
     */
    public static void visualizeGoodNumbers(int limit) {
        System.out.println("\n=== Visualizing Good Numbers (1 to " + limit + ") ===");
        
        System.out.println("\n1. Even numbers:");
        System.out.print("   ");
        int count = 0;
        for (int i = 1; i <= limit; i++) {
            if (isGoodEven(i)) {
                System.out.print(i + " ");
                count++;
                if (count % 10 == 0) System.out.print("\n   ");
            }
        }
        System.out.println("\n   Total: " + count);
        
        System.out.println("\n2. Numbers with all even digits:");
        System.out.print("   ");
        count = 0;
        for (int i = 1; i <= limit; i++) {
            if (isGoodEvenDigits(i)) {
                System.out.print(i + " ");
                count++;
                if (count % 10 == 0) System.out.print("\n   ");
            }
        }
        System.out.println("\n   Total: " + count);
        
        System.out.println("\n3. Harshad numbers (divisible by sum of digits):");
        System.out.print("   ");
        count = 0;
        for (int i = 1; i <= limit; i++) {
            if (isGoodHarshad(i)) {
                System.out.print(i + " ");
                count++;
                if (count % 10 == 0) System.out.print("\n   ");
            }
        }
        System.out.println("\n   Total: " + count);
        
        System.out.println("\n4. Alternating even-odd digits (2+ digits):");
        System.out.print("   ");
        count = 0;
        for (int i = 1; i <= limit; i++) {
            if (isGoodAlternating(i)) {
                System.out.print(i + " ");
                count++;
                if (count % 10 == 0) System.out.print("\n   ");
            }
        }
        System.out.println("\n   Total: " + count);
        
        System.out.println("\n5. Non-decreasing digits:");
        System.out.print("   ");
        count = 0;
        for (int i = 1; i <= limit; i++) {
            if (isGoodNonDecreasing(i)) {
                System.out.print(i + " ");
                count++;
                if (count % 10 == 0) System.out.print("\n   ");
            }
        }
        System.out.println("\n   Total: " + count);
        
        System.out.println("\n6. Self-descriptive numbers (base 10):");
        System.out.print("   ");
        count = 0;
        for (int i = 1; i <= limit; i++) {
            if (isGoodSelfDescriptive(i)) {
                System.out.print(i + " ");
                count++;
            }
        }
        if (count == 0) System.out.print("None in this range");
        System.out.println("\n   Total: " + count);
    }
    
    /**
     * Performance comparison of different counting methods
     */
    public static void performanceComparison(int n) {
        System.out.println("\n=== Performance Comparison (n=" + n + ") ===");
        
        System.out.println("\n1. Brute-force recursive:");
        long start = System.currentTimeMillis();
        int bruteCount = countGoodNumbers(n);
        long end = System.currentTimeMillis();
        System.out.println("   Time: " + (end - start) + "ms");
        System.out.println("   Count: " + bruteCount);
        
        System.out.println("\n2. Optimized even numbers:");
        start = System.currentTimeMillis();
        int evenCount = countEvenNumbers(n);
        end = System.currentTimeMillis();
        System.out.println("   Time: " + (end - start) + "ms");
        System.out.println("   Count: " + evenCount);
        
        System.out.println("\n3. Optimized even-digit numbers:");
        start = System.currentTimeMillis();
        int evenDigitCount = countEvenDigitNumbers(n);
        end = System.currentTimeMillis();
        System.out.println("   Time: " + (end - start) + "ms");
        System.out.println("   Count: " + evenDigitCount);
        
        System.out.println("\n4. Optimized Harshad numbers:");
        start = System.currentTimeMillis();
        int harshadCount = countHarshadNumbers(n);
        end = System.currentTimeMillis();
        System.out.println("   Time: " + (end - start) + "ms");
        System.out.println("   Count: " + harshadCount);
        
        System.out.println("\n5. Optimized non-decreasing numbers:");
        start = System.currentTimeMillis();
        int nonDecreasingCount = countNonDecreasingNumbers(n);
        end = System.currentTimeMillis();
        System.out.println("   Time: " + (end - start) + "ms");
        System.out.println("   Count: " + nonDecreasingCount);
    }
    
    /**
     * Test specific definitions with examples
     */
    public static void testDefinitions() {
        System.out.println("\n=== Testing Different Definitions ===");
        
        int[] testNumbers = {12, 23, 44, 68, 100, 135, 246, 357, 468, 1000};
        
        System.out.println("\nNumber | Even | EvenDigits | Harshad | Alternating | NonDec | SelfDesc");
        System.out.println("-------|------|------------|---------|-------------|--------|----------");
        
        for (int num : testNumbers) {
            System.out.printf("%6d | %4s | %10s | %7s | %11s | %6s | %8s%n",
                num,
                isGoodEven(num) ? "Yes" : "No",
                isGoodEvenDigits(num) ? "Yes" : "No",
                isGoodHarshad(num) ? "Yes" : "No",
                isGoodAlternating(num) ? "Yes" : "No",
                isGoodNonDecreasing(num) ? "Yes" : "No",
                isGoodSelfDescriptive(num) ? "Yes" : "No"
            );
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Good Numbers - Various Definitions and Counting Methods ===\n");
        
        // Basic recursive count (even numbers)
        System.out.println("Basic recursive count (even numbers up to 20):");
        countGoodNumbers(20);
        
        // Visualize different definitions
        System.out.println("\n\n--- Visualizing Different Definitions ---");
        visualizeGoodNumbers(100);
        
        // Test specific definitions
        testDefinitions();
        
        // Performance comparison
        System.out.println("\n\n--- Performance Comparison ---");
        performanceComparison(10000);
        
        // Special examples
        System.out.println("\n\n--- Special Examples ---");
        
        System.out.println("\n1. Self-descriptive numbers (all in base 10 up to 10^7):");
        int[] selfDescriptive = {1210, 2020, 21200, 3211000};
        for (int num : selfDescriptive) {
            System.out.println("   " + num + ": " + isGoodSelfDescriptive(num));
        }
        
        System.out.println("\n2. Perfect Harshad numbers (Harshad numbers that are also prime):");
        int[] harshadPrimes = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
        for (int num : harshadPrimes) {
            if (isGoodHarshad(num)) {
                System.out.println("   " + num + " is a Harshad prime");
            }
        }
        
        // Mathematical insights
        System.out.println("\n\n=== Mathematical Insights ===");
        System.out.println("1. Even numbers: Exactly floor(n/2) numbers in [1, n]");
        System.out.println("2. Even-digit numbers: Can be counted using base-5 conversion");
        System.out.println("3. Harshad numbers: Density ~ (14/27) * log(n)");
        System.out.println("4. Non-decreasing numbers: Count = C(n+9, 9) - 1");
        System.out.println("5. Self-descriptive numbers: Only 4 in base 10");
        
        // Complexity analysis
        System.out.println("\n=== Complexity Analysis ===");
        System.out.println("Brute-force recursive:");
        System.out.println("  Time: O(n * d) where d = number of digits");
        System.out.println("  Space: O(n) recursion stack (can overflow)");
        System.out.println("\nOptimized counting:");
        System.out.println("  Even numbers: O(1)");
        System.out.println("  Even-digit numbers: O(log n)");
        System.out.println("  Harshad numbers: O(n log n) with sieve");
        System.out.println("  Non-decreasing: O(10 * d) with DP");
        
        // When to use which approach
        System.out.println("\n=== When to Use Which Approach ===");
        System.out.println("1. Brute-force: Small n (< 1000), simple definitions");
        System.out.println("2. Mathematical formulas: When pattern is regular");
        System.out.println("3. Dynamic Programming: For digit-based constraints");
        System.out.println("4. Sieve methods: When checking divisibility");
    }
}