import java.util.Arrays;

/**
 * Count Set Bits (Hamming Weight / Population Count)
 * 
 * Problem: Count the number of 1 bits in the binary representation of an integer.
 * 
 * Applications:
 * - Error detection and correction (Hamming codes)
 * - Cryptography (parity checks)
 * - Computer graphics (bit manipulation)
 * - Database indexing (bitmap indexes)
 * - Bioinformatics (DNA sequence analysis)
 */
public class CountSetBits {

    /**
     * 1. KERNIGHAN'S ALGORITHM (Brian Kernighan's Algorithm)
     * Clears the lowest set bit in each iteration.
     * 
     * Key insight: n & (n-1) clears the lowest set bit.
     * Example: n = 14 (1110), n-1 = 13 (1101)
     *          n & (n-1) = 1100 (cleared the rightmost 1)
     * 
     * Time Complexity: O(k) where k = number of set bits (worst case O(log n))
     * Space Complexity: O(1)
     */
    public static int countBits(int n) {
        int count = 0;
        while (n != 0) {
            n &= (n - 1);  // Clear the lowest set bit
            count++;
        }
        return count;
    }

    /**
     * 2. NAIVE APPROACH: Check each bit
     * Check each of the 32 bits (for integers)
     */
    public static int countBitsNaive(int n) {
        int count = 0;
        int mask = 1;
        
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0) {
                count++;
            }
            mask <<= 1;  // Shift mask left
        }
        return count;
    }

    /**
     * 3. LOOKUP TABLE METHOD
     * Precompute bit counts for all 8-bit values (0-255)
     * Then check each byte of the integer
     * 
     * Time Complexity: O(1) after table creation
     * Space Complexity: O(256) = 1KB
     */
    public static int countBitsLookupTable(int n) {
        // Initialize lookup table (can be static for reuse)
        byte[] lookupTable = createLookupTable();
        
        // Count bits in each byte
        int count = 0;
        for (int i = 0; i < 4; i++) {
            int byteValue = (n >> (i * 8)) & 0xFF;
            count += lookupTable[byteValue];
        }
        return count;
    }
    
    private static byte[] createLookupTable() {
        byte[] table = new byte[256];
        for (int i = 0; i < 256; i++) {
            table[i] = (byte) countBits(i);
        }
        return table;
    }

    /**
     * 4. PARALLEL COUNTING (Divide and Conquer)
     * Uses bitwise operations to count bits in parallel
     * Known as "Hamming weight" or "population count"
     * 
     * Steps:
     * 1. Sum bits in pairs
     * 2. Sum groups of 2 bits
     * 3. Sum groups of 4 bits
     * 4. Sum groups of 8 bits
     * 5. Sum all bytes
     */
    public static int countBitsParallel(int n) {
        // Mask and sum bits in parallel
        // 0x55555555 = 01010101010101010101010101010101
        // 0x33333333 = 00110011001100110011001100110011
        // 0x0f0f0f0f = 00001111000011110000111100001111
        // 0x00ff00ff = 00000000111111110000000011111111
        // 0x0000ffff = 00000000000000001111111111111111
        
        n = (n & 0x55555555) + ((n >> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >> 2) & 0x33333333);
        n = (n & 0x0f0f0f0f) + ((n >> 4) & 0x0f0f0f0f);
        n = (n & 0x00ff00ff) + ((n >> 8) & 0x00ff00ff);
        n = (n & 0x0000ffff) + ((n >> 16) & 0x0000ffff);
        
        return n;
    }

    /**
     * 5. JAVA BUILT-IN: Integer.bitCount()
     * Uses native implementation (often the fastest)
     */
    public static int countBitsBuiltIn(int n) {
        return Integer.bitCount(n);
    }

    /**
     * 6. RECURSIVE APPROACH
     */
    public static int countBitsRecursive(int n) {
        if (n == 0) return 0;
        return (n & 1) + countBitsRecursive(n >>> 1);
    }

    /**
     * 7. COUNT BITS FOR ARRAY OF NUMBERS
     */
    public static int[] countBitsArray(int[] nums) {
        int[] result = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            result[i] = countBits(nums[i]);
        }
        return result;
    }

    /**
     * 8. COUNT BITS FROM 0 TO N (Dynamic Programming)
     * Uses the relationship: bits[i] = bits[i >> 1] + (i & 1)
     */
    public static int[] countBitsRange(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i >> 1] + (i & 1);
        }
        return bits;
    }

    /**
     * 9. COUNT SET BITS WITH DIFFERENT BIT SIZES
     * For long, byte, etc.
     */
    public static int countBitsLong(long n) {
        int count = 0;
        while (n != 0) {
            n &= (n - 1);
            count++;
        }
        return count;
    }
    
    public static int countBitsByte(byte b) {
        int count = 0;
        int n = b & 0xFF;  // Convert to unsigned
        while (n != 0) {
            n &= (n - 1);
            count++;
        }
        return count;
    }

    /**
     * 10. FIND PARITY (Even/Odd number of 1s)
     * Returns 1 if odd number of 1s, 0 if even
     */
    public static int parity(int n) {
        int parity = 0;
        while (n != 0) {
            parity ^= 1;  // Toggle parity
            n &= (n - 1); // Clear lowest set bit
        }
        return parity;
    }
    
    /**
     * 11. FAST PARITY (Using XOR properties)
     */
    public static int parityFast(int n) {
        n ^= n >>> 16;
        n ^= n >>> 8;
        n ^= n >>> 4;
        n ^= n >>> 2;
        n ^= n >>> 1;
        return n & 1;
    }

    /**
     * 12. COUNT LEADING ZEROS
     * Number of zeros before the first 1 from left
     */
    public static int countLeadingZeros(int n) {
        if (n == 0) return 32;
        
        int count = 0;
        int mask = 1 << 31;  // 1000...0000
        
        while ((n & mask) == 0) {
            count++;
            mask >>>= 1;  // Unsigned right shift
        }
        return count;
    }

    /**
     * 13. COUNT TRAILING ZEROS
     * Number of zeros after the last 1 from right
     */
    public static int countTrailingZeros(int n) {
        if (n == 0) return 32;
        
        int count = 0;
        while ((n & 1) == 0) {
            count++;
            n >>>= 1;
        }
        return count;
    }
    
    /**
     * 14. FAST TRAILING ZEROS (Using n & -n trick)
     * n & -n isolates the lowest set bit
     */
    public static int countTrailingZerosFast(int n) {
        if (n == 0) return 32;
        
        // Isolate lowest set bit
        int lowestBit = n & -n;
        
        // Count zeros by finding position of the bit
        return 31 - Integer.numberOfLeadingZeros(lowestBit);
    }

    /**
     * 15. FIND NEXT POWER OF TWO
     * Rounds up to the next power of two
     */
    public static int nextPowerOfTwo(int n) {
        if (n == 0) return 1;
        
        n--;  // Handle case when n is already power of two
        
        // Propagate 1-bits to the right
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        
        return n + 1;
    }

    /**
     * 16. CHECK IF POWER OF TWO
     */
    public static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    /**
     * 17. REVERSE BITS
     */
    public static int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;
            result |= (n & 1);
            n >>>= 1;
        }
        return result;
    }

    /**
     * 18. SWAP BITS AT POSITIONS i AND j
     */
    public static int swapBits(int n, int i, int j) {
        // Extract bits at positions i and j
        int bitI = (n >> i) & 1;
        int bitJ = (n >> j) & 1;
        
        // If bits are different, swap them
        if (bitI != bitJ) {
            int mask = (1 << i) | (1 << j);
            n ^= mask;  // Flip both bits
        }
        
        return n;
    }

    /**
     * 19. FIND CLOSEST NUMBER WITH SAME NUMBER OF SET BITS
     */
    public static int closestWithSameBits(int n) {
        if (n == 0 || n == ~0) return n;
        
        // Find rightmost non-trailing zero
        int c0 = 0;  // Count trailing zeros
        int c1 = 0;  // Count ones after trailing zeros
        
        int temp = n;
        while ((temp & 1) == 0 && temp != 0) {
            c0++;
            temp >>= 1;
        }
        
        while ((temp & 1) == 1) {
            c1++;
            temp >>= 1;
        }
        
        // Position of rightmost non-trailing zero
        int p = c0 + c1;
        
        // Flip pth bit
        n |= (1 << p);
        
        // Clear bits to the right of p
        n &= ~((1 << p) - 1);
        
        // Insert (c1 - 1) ones on the right
        n |= (1 << (c1 - 1)) - 1;
        
        return n;
    }

    /**
     * Comprehensive test driver
     */
    public static void main(String[] args) {
        System.out.println("=== COUNT SET BITS DEMO ===\n");
        
        testBasicFunctions();
        testPerformance();
        testBitManipulation();
        testEdgeCases();
        testVisualization();
    }
    
    private static void testBasicFunctions() {
        System.out.println("1. BASIC FUNCTIONALITY TESTS");
        
        int[] testNumbers = {0, 1, 2, 3, 7, 8, 15, 16, 255, 256, 1023, 1024};
        
        System.out.println("Number\tBinary\t\tKernighan\tNaive\t\tLookup\t\tParallel\tBuilt-in");
        System.out.println("------\t------\t\t--------\t-----\t\t------\t\t--------\t--------");
        
        for (int n : testNumbers) {
            String binary = String.format("%12s", Integer.toBinaryString(n)).replace(' ', '0');
            System.out.printf("%-6d\t%s\t%-8d\t%-8d\t%-8d\t%-8d\t%-8d\n",
                n, binary,
                countBits(n),
                countBitsNaive(n),
                countBitsLookupTable(n),
                countBitsParallel(n),
                countBitsBuiltIn(n)
            );
        }
        System.out.println();
    }
    
    private static void testPerformance() {
        System.out.println("2. PERFORMANCE COMPARISON");
        
        int testNumber = 0xFFFFFFFF;  // All bits set (32 ones)
        int iterations = 1000000;
        
        System.out.println("Testing with n = " + testNumber + " (binary: " + 
                         Integer.toBinaryString(testNumber) + ")");
        System.out.println("Running " + iterations + " iterations of each method\n");
        
        // Kernighan's algorithm
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            countBits(testNumber);
        }
        long time1 = System.nanoTime() - start;
        
        // Naive approach
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            countBitsNaive(testNumber);
        }
        long time2 = System.nanoTime() - start;
        
        // Lookup table
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            countBitsLookupTable(testNumber);
        }
        long time3 = System.nanoTime() - start;
        
        // Parallel counting
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            countBitsParallel(testNumber);
        }
        long time4 = System.nanoTime() - start;
        
        // Built-in
        start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            countBitsBuiltIn(testNumber);
        }
        long time5 = System.nanoTime() - start;
        
        System.out.printf("Kernighan's:   %8.2f ms\n", time1 / 1_000_000.0);
        System.out.printf("Naive:         %8.2f ms\n", time2 / 1_000_000.0);
        System.out.printf("Lookup Table:  %8.2f ms\n", time3 / 1_000_000.0);
        System.out.printf("Parallel:      %8.2f ms\n", time4 / 1_000_000.0);
        System.out.printf("Built-in:      %8.2f ms\n", time5 / 1_000_000.0);
        System.out.println();
    }
    
    private static void testBitManipulation() {
        System.out.println("3. BIT MANIPULATION TESTS");
        
        int n = 42;  // 00101010
        
        System.out.println("Original number: " + n + " (binary: " + 
                         String.format("%8s", Integer.toBinaryString(n)).replace(' ', '0') + ")");
        
        // Parity
        System.out.println("Parity (odd=1, even=0): " + parity(n));
        System.out.println("Fast parity: " + parityFast(n));
        
        // Leading and trailing zeros
        System.out.println("Leading zeros: " + countLeadingZeros(n));
        System.out.println("Trailing zeros: " + countTrailingZeros(n));
        System.out.println("Fast trailing zeros: " + countTrailingZerosFast(n));
        
        // Power of two checks
        System.out.println("\nPower of Two tests:");
        System.out.println("Is " + n + " power of two? " + isPowerOfTwo(n));
        System.out.println("Is 16 power of two? " + isPowerOfTwo(16));
        System.out.println("Is 0 power of two? " + isPowerOfTwo(0));
        System.out.println("Is -8 power of two? " + isPowerOfTwo(-8));
        
        // Next power of two
        System.out.println("\nNext power of two:");
        System.out.println("Next power of two after " + n + " is: " + nextPowerOfTwo(n));
        
        // Reverse bits
        int reversed = reverseBits(n);
        System.out.println("Reversed bits of " + n + ": " + reversed + 
                         " (binary: " + Integer.toBinaryString(reversed) + ")");
        
        // Swap bits
        int swapped = swapBits(n, 1, 5); // Swap bits at positions 1 and 5
        System.out.println("After swapping bits 1 and 5: " + swapped + 
                         " (binary: " + Integer.toBinaryString(swapped) + ")");
        System.out.println();
    }
    
    private static void testEdgeCases() {
        System.out.println("4. EDGE CASES");
        
        System.out.println("Testing with 0:");
        System.out.println("  Bits: " + countBits(0));
        System.out.println("  Parity: " + parity(0));
        System.out.println("  Leading zeros: " + countLeadingZeros(0));
        System.out.println("  Is power of two? " + isPowerOfTwo(0));
        
        System.out.println("\nTesting with -1 (all ones):");
        int minusOne = -1;
        System.out.println("  Binary: " + Integer.toBinaryString(minusOne));
        System.out.println("  Bits: " + countBits(minusOne));
        System.out.println("  Parity: " + parity(minusOne));
        
        System.out.println("\nTesting with Integer.MIN_VALUE:");
        int minVal = Integer.MIN_VALUE;
        System.out.println("  Binary: " + Integer.toBinaryString(minVal));
        System.out.println("  Bits: " + countBits(minVal));
        System.out.println("  Leading zeros: " + countLeadingZeros(minVal));
        System.out.println("  Trailing zeros: " + countTrailingZeros(minVal));
        
        System.out.println("\nTesting with Integer.MAX_VALUE:");
        int maxVal = Integer.MAX_VALUE;
        System.out.println("  Binary: " + Integer.toBinaryString(maxVal));
        System.out.println("  Bits: " + countBits(maxVal));
        System.out.println();
    }
    
    private static void testVisualization() {
        System.out.println("5. BIT COUNT VISUALIZATION (0 to 31)");
        
        int[] counts = countBitsRange(31);
        System.out.println("Number\tBinary\t\t\tSet Bits");
        System.out.println("------\t------\t\t\t--------");
        
        for (int i = 0; i <= 31; i++) {
            String binary = String.format("%6s", Integer.toBinaryString(i)).replace(' ', '0');
            System.out.printf("%-6d\t%s\t\t%-8d\n", i, binary, counts[i]);
        }
        
        // Show pattern for powers of two
        System.out.println("\nPattern for powers of two:");
        for (int i = 0; i <= 10; i++) {
            int pow = 1 << i;
            System.out.println("2^" + i + " = " + pow + " has " + countBits(pow) + " set bits");
        }
        
        // Show distribution
        System.out.println("\nDistribution of bit counts for numbers 0-255:");
        int[] distribution = new int[9]; // 0 to 8 bits
        for (int i = 0; i < 256; i++) {
            distribution[countBits(i)]++;
        }
        
        for (int i = 0; i < distribution.length; i++) {
            System.out.println(i + " bits: " + distribution[i] + " numbers");
        }
    }
    
    /**
     * 20. HAMMING DISTANCE: Count differing bits between two numbers
     */
    public static int hammingDistance(int x, int y) {
        return countBits(x ^ y);  // XOR gives 1s where bits differ
    }
    
    /**
     * 21. TOTAL HAMMING DISTANCE FOR ARRAY
     * Sum of Hamming distances between all pairs
     */
    public static int totalHammingDistance(int[] nums) {
        int total = 0;
        int n = nums.length;
        
        // Check each bit position
        for (int i = 0; i < 32; i++) {
            int bitCount = 0;
            for (int num : nums) {
                bitCount += (num >> i) & 1;
            }
            total += bitCount * (n - bitCount);
        }
        
        return total;
    }
    
    /**
     * 22. MINIMUM BIT FLIPS TO CONVERT A TO B
     */
    public static int minBitFlips(int a, int b) {
        return hammingDistance(a, b);
    }
    
    /**
     * 23. BIT ROTATION (Circular shift)
     */
    public static int rotateLeft(int n, int d) {
        return (n << d) | (n >>> (32 - d));
    }
    
    public static int rotateRight(int n, int d) {
        return (n >>> d) | (n << (32 - d));
    }
    
    /**
     * 24. CHECK IF BITS ARE IN ALTERNATING PATTERN
     */
    public static boolean hasAlternatingBits(int n) {
        int prev = n & 1;
        n >>= 1;
        
        while (n > 0) {
            int current = n & 1;
            if (current == prev) {
                return false;
            }
            prev = current;
            n >>= 1;
        }
        return true;
    }
    
    /**
     * 25. COUNT BITS WITH LOOP UNROLLING
     * Optimized for known word sizes
     */
    public static int countBitsUnrolled(int n) {
        // 32-bit unrolled count
        n = n - ((n >> 1) & 0x55555555);
        n = (n & 0x33333333) + ((n >> 2) & 0x33333333);
        n = (n + (n >> 4)) & 0x0F0F0F0F;
        n = n + (n >> 8);
        n = n + (n >> 16);
        return n & 0x3F;
    }
}