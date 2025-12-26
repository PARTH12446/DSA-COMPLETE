// Problem: LeetCode <ID>. <Title>
import java.util.Arrays;
import java.math.BigInteger;

/*
 * PROBLEM: Find Missing and Repeating Numbers (Coding Ninjas)
 * 
 * Given an array of size N containing numbers from 1 to N,
 * with one number missing and one number repeating.
 * Find both the missing and repeating numbers.
 * 
 * CONSTRAINTS:
 * - 2 = N = 10^5
 * - 1 = arr[i] = N
 * - Array contains all numbers from 1 to N except:
 *   - One number is missing
 *   - One number appears twice
 * - Exactly one solution exists
 * 
 * APPROACH: Mathematical Method using Sum and Sum of Squares
 * 
 * INTUITION:
 * Let:
 *   x = repeating number
 *   y = missing number
 * 
 * From given array:
 *   sum(arr) = (1+2+...+N) - y + x = S - y + x
 *   sum(arr²) = (1²+2²+...+N²) - y² + x² = S2 - y² + x²
 * 
 * From these equations:
 *   (1) x - y = sum(arr) - S
 *   (2) x² - y² = sum(arr²) - S2
 * 
 * Since x² - y² = (x-y)(x+y), we get:
 *   (3) x + y = (sum(arr²) - S2) / (sum(arr) - S)
 * 
 * Solving (1) and (3) gives x and y.
 * 
 * TIME COMPLEXITY: O(n)
 *   - Two passes through array
 *   - Each pass does O(1) work per element
 * 
 * SPACE COMPLEXITY: O(1)
 *   - Only a few long variables used
 * 
 * ALTERNATIVE APPROACHES:
 * 1. Sorting: O(n log n) time, O(1) or O(n) space
 * 2. Hash Map: O(n) time, O(n) space
 * 3. XOR method: O(n) time, O(1) space
 */

public class RepeatingAndMissing {

    /**
     * Find missing and repeating numbers using sum and sum of squares
     * 
     * @param a Input array of size n containing numbers 1 to n
     * @return int array [repeating, missing]
     */
    public static int[] findMissingRepeatingNumbers(int[] a) {
        int n = a.length;
        
        // Calculate actual sums from array
        long sumActual = 0;      // sum of elements in array
        long sumSquaresActual = 0; // sum of squares of elements
        
        for (int num : a) {
            sumActual += num;
            sumSquaresActual += (long) num * num;
        }
        
        // Calculate expected sums for 1 to n
        long sumExpected = (long) n * (n + 1) / 2;
        long sumSquaresExpected = (long) n * (n + 1) * (2L * n + 1) / 6;
        
        // Let x = repeating, y = missing
        // Equation 1: x - y = sumActual - sumExpected
        long diff = sumActual - sumExpected;  // x - y
        
        // Equation 2: x² - y² = sumSquaresActual - sumSquaresExpected
        // But x² - y² = (x - y)(x + y)
        long diffSquares = sumSquaresActual - sumSquaresExpected;  // x² - y²
        
        // Equation 3: x + y = (x² - y²) / (x - y)
        long sum = diffSquares / diff;  // x + y
        
        // Solve for x and y:
        // x = ((x+y) + (x-y)) / 2
        // y = ((x+y) - (x-y)) / 2
        long repeating = (sum + diff) / 2;
        long missing = (sum - diff) / 2;
        
        return new int[]{(int) repeating, (int) missing};
    }
    
    /**
     * Alternative: Using XOR method (bit manipulation)
     * Time: O(n), Space: O(1)
     */
    public static int[] findMissingRepeatingNumbersXOR(int[] arr) {
        int n = arr.length;
        
        // Step 1: XOR all array elements and numbers 1 to n
        int xor = 0;
        for (int i = 0; i < n; i++) {
            xor ^= arr[i];
            xor ^= (i + 1);
        }
        
        // Step 2: Get rightmost set bit
        int setBit = xor & ~(xor - 1);
        
        // Step 3: Divide numbers into two groups based on set bit
        int x = 0, y = 0;
        for (int i = 0; i < n; i++) {
            // Group 1: array elements with set bit
            if ((arr[i] & setBit) != 0) {
                x ^= arr[i];
            } else {
                y ^= arr[i];
            }
            
            // Group 2: numbers 1..n with set bit
            if (((i + 1) & setBit) != 0) {
                x ^= (i + 1);
            } else {
                y ^= (i + 1);
            }
        }
        
        // Step 4: Determine which is missing and which is repeating
        // Check which one appears in array
        int repeating = 0, missing = 0;
        for (int num : arr) {
            if (num == x) {
                repeating = x;
                missing = y;
                break;
            } else if (num == y) {
                repeating = y;
                missing = x;
                break;
            }
        }
        
        return new int[]{repeating, missing};
    }
    
    /**
     * Using counting array (extra space)
     * Time: O(n), Space: O(n)
     */
    public static int[] findMissingRepeatingNumbersCount(int[] arr) {
        int n = arr.length;
        int[] count = new int[n + 1];
        
        // Count occurrences
        for (int num : arr) {
            count[num]++;
        }
        
        int repeating = -1, missing = -1;
        for (int i = 1; i <= n; i++) {
            if (count[i] == 2) {
                repeating = i;
            } else if (count[i] == 0) {
                missing = i;
            }
        }
        
        return new int[]{repeating, missing};
    }
    
    /**
     * Using sorting approach
     * Time: O(n log n), Space: O(1) or O(n) depending on sort
     */
    public static int[] findMissingRepeatingNumbersSort(int[] arr) {
        Arrays.sort(arr);
        int repeating = -1, missing = -1;
        
        // Check first element
        if (arr[0] != 1) {
            missing = 1;
        }
        
        // Check middle elements
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == arr[i - 1]) {
                repeating = arr[i];
            } else if (arr[i] != arr[i - 1] + 1) {
                missing = arr[i - 1] + 1;
            }
        }
        
        // Check last element
        if (missing == -1) {
            missing = arr.length;
        }
        
        return new int[]{repeating, missing};
    }
    
    /**
     * Using mathematical formulas with better overflow handling
     */
    public static int[] findMissingRepeatingNumbersSafe(int[] arr) {
        int n = arr.length;
        
        // Use BigInteger for safety with large n
        BigInteger sumActual = BigInteger.ZERO;
        BigInteger sumSquaresActual = BigInteger.ZERO;
        
        for (int num : arr) {
            BigInteger bigNum = BigInteger.valueOf(num);
            sumActual = sumActual.add(bigNum);
            sumSquaresActual = sumSquaresActual.add(bigNum.multiply(bigNum));
        }
        
        // Expected sums
        BigInteger nBig = BigInteger.valueOf(n);
        BigInteger sumExpected = nBig.multiply(nBig.add(BigInteger.ONE))
                                     .divide(BigInteger.valueOf(2));
        BigInteger sumSquaresExpected = nBig.multiply(nBig.add(BigInteger.ONE))
                                           .multiply(nBig.multiply(BigInteger.valueOf(2))
                                                         .add(BigInteger.ONE))
                                           .divide(BigInteger.valueOf(6));
        
        // Differences
        BigInteger diff = sumActual.subtract(sumExpected);  // x - y
        BigInteger diffSquares = sumSquaresActual.subtract(sumSquaresExpected); // x² - y²
        
        // x + y = (x² - y²) / (x - y)
        BigInteger sum = diffSquares.divide(diff);  // x + y
        
        // Solve: x = ((x+y) + (x-y)) / 2
        //        y = ((x+y) - (x-y)) / 2
        BigInteger repeatingBig = sum.add(diff).divide(BigInteger.valueOf(2));
        BigInteger missingBig = sum.subtract(diff).divide(BigInteger.valueOf(2));
        
        return new int[]{repeatingBig.intValue(), missingBig.intValue()};
    }
    
    /**
     * Test method with examples
     */
    public static void main(String[] args) {
        // Test case 1: Simple case
        int[] arr1 = {1, 2, 3, 2};
        System.out.println("Test 1: [1, 2, 3, 2]");
        int[] result1 = findMissingRepeatingNumbers(arr1);
        System.out.println("Repeating: " + result1[0] + ", Missing: " + result1[1]);
        System.out.println("Expected: Repeating=2, Missing=4");
        
        // Test case 2: Another example
        int[] arr2 = {3, 1, 2, 5, 3};
        System.out.println("\nTest 2: [3, 1, 2, 5, 3]");
        int[] result2 = findMissingRepeatingNumbers(arr2);
        System.out.println("Repeating: " + result2[0] + ", Missing: " + result2[1]);
        System.out.println("Expected: Repeating=3, Missing=4");
        
        // Test case 3: Missing at beginning
        int[] arr3 = {2, 2};
        System.out.println("\nTest 3: [2, 2]");
        int[] result3 = findMissingRepeatingNumbers(arr3);
        System.out.println("Repeating: " + result3[0] + ", Missing: " + result3[1]);
        System.out.println("Expected: Repeating=2, Missing=1");
        
        // Test case 4: Missing at end
        int[] arr4 = {1, 1};
        System.out.println("\nTest 4: [1, 1]");
        int[] result4 = findMissingRepeatingNumbers(arr4);
        System.out.println("Repeating: " + result4[0] + ", Missing: " + result4[1]);
        System.out.println("Expected: Repeating=1, Missing=2");
        
        // Test case 5: Larger array
        int[] arr5 = {4, 3, 6, 2, 1, 1};
        System.out.println("\nTest 5: [4, 3, 6, 2, 1, 1]");
        int[] result5 = findMissingRepeatingNumbers(arr5);
        System.out.println("Repeating: " + result5[0] + ", Missing: " + result5[1]);
        System.out.println("Expected: Repeating=1, Missing=5");
        
        // Test case 6: Verify with different methods
        System.out.println("\nTest 6: Verify all methods give same result");
        int[] testArr = {1, 3, 4, 2, 2};
        
        int[] resultSum = findMissingRepeatingNumbers(testArr);
        int[] resultXOR = findMissingRepeatingNumbersXOR(testArr);
        int[] resultCount = findMissingRepeatingNumbersCount(testArr);
        int[] resultSort = findMissingRepeatingNumbersSort(testArr);
        
        System.out.println("Sum method:     Repeating=" + resultSum[0] + ", Missing=" + resultSum[1]);
        System.out.println("XOR method:     Repeating=" + resultXOR[0] + ", Missing=" + resultXOR[1]);
        System.out.println("Count method:   Repeating=" + resultCount[0] + ", Missing=" + resultCount[1]);
        System.out.println("Sort method:    Repeating=" + resultSort[0] + ", Missing=" + resultSort[1]);
        
        boolean allMatch = resultSum[0] == resultXOR[0] && resultSum[0] == resultCount[0] && 
                          resultSum[1] == resultXOR[1] && resultSum[1] == resultCount[1];
        System.out.println("All methods match: " + allMatch);
        
        // Performance test
        System.out.println("\n=== Performance Test ===");
        int n = 100000;
        int[] largeArr = new int[n];
        // Create array with missing n and repeating n-1
        for (int i = 0; i < n; i++) {
            largeArr[i] = i + 1;
        }
        largeArr[n-1] = n-1; // Make n-1 repeating
        
        long startTime = System.currentTimeMillis();
        int[] perfResult = findMissingRepeatingNumbers(largeArr);
        long endTime = System.currentTimeMillis();
        System.out.println("Sum method time for n=100,000: " + (endTime - startTime) + "ms");
        System.out.println("Result: Repeating=" + perfResult[0] + ", Missing=" + perfResult[1]);
        
        startTime = System.currentTimeMillis();
        int[] perfResultXOR = findMissingRepeatingNumbersXOR(largeArr);
        endTime = System.currentTimeMillis();
        System.out.println("XOR method time for n=100,000: " + (endTime - startTime) + "ms");
        System.out.println("Result: Repeating=" + perfResultXOR[0] + ", Missing=" + perfResultXOR[1]);
        
        // Edge case: Integer overflow test
        System.out.println("\n=== Integer Overflow Test ===");
        // For large n, sum of squares can overflow 64-bit integer
        // n=100,000: n*(n+1)*(2n+1)/6 ˜ 3.33e14 fits in long
        // n=1,000,000: ˜ 3.33e17 still fits in long (2^63 ˜ 9.22e18)
        int[] overflowTest = new int[1000000];
        // Fill with pattern
        for (int i = 0; i < overflowTest.length; i++) {
            overflowTest[i] = i + 1;
        }
        overflowTest[overflowTest.length-1] = overflowTest.length-1; // Make last-1 repeating
        
        System.out.println("Testing with n=1,000,000 (sum of squares ~3.33e17)");
        startTime = System.currentTimeMillis();
        int[] overflowResult = findMissingRepeatingNumbers(overflowTest);
        endTime = System.currentTimeMillis();
        System.out.println("Time: " + (endTime - startTime) + "ms");
        System.out.println("Result: Repeating=" + overflowResult[0] + ", Missing=" + overflowResult[1]);
    }
    
    /**
     * Mathematical Derivation:
     * 
     * Let S = 1 + 2 + ... + n = n(n+1)/2
     * Let S2 = 1² + 2² + ... + n² = n(n+1)(2n+1)/6
     * 
     * Given array has:
     * - All numbers 1 to n except one missing (y)
     * - One number appears twice (x)
     * 
     * So: sum(arr) = S - y + x
     *     sum(arr²) = S2 - y² + x²
     * 
     * Rearranging:
     * (1) x - y = sum(arr) - S
     * (2) x² - y² = sum(arr²) - S2
     * 
     * But x² - y² = (x-y)(x+y), so:
     * (3) x + y = (sum(arr²) - S2) / (sum(arr) - S)
     * 
     * Now we have two equations:
     * x - y = A  (where A = sum(arr) - S)
     * x + y = B  (where B = (sum(arr²) - S2) / A)
     * 
     * Solving:
     * x = (A + B) / 2
     * y = (B - A) / 2
     */
    
    /**
     * Step-by-step example:
     * 
     * Array: [1, 2, 3, 2], n=4
     * 
     * Step 1: Calculate actual sums
     * sumActual = 1+2+3+2 = 8
     * sumSquaresActual = 1²+2²+3²+2² = 1+4+9+4 = 18
     * 
     * Step 2: Calculate expected sums
     * sumExpected = 4*5/2 = 10
     * sumSquaresExpected = 4*5*9/6 = 180/6 = 30
     * 
     * Step 3: Compute differences
     * diff = sumActual - sumExpected = 8-10 = -2 (x-y)
     * diffSquares = sumSquaresActual - sumSquaresExpected = 18-30 = -12 (x²-y²)
     * 
     * Step 4: Compute sum
     * sum = diffSquares / diff = (-12)/(-2) = 6 (x+y)
     * 
     * Step 5: Solve
     * x = (sum + diff)/2 = (6 + (-2))/2 = 4/2 = 2
     * y = (sum - diff)/2 = (6 - (-2))/2 = 8/2 = 4
     * 
     * Result: Repeating=2, Missing=4 ?
     */
    
    /**
     * Potential Issues and Solutions:
     * 
     * 1. Integer overflow:
     *    - Use long for intermediate calculations
     *    - For very large n, use BigInteger
     *    
     * 2. Division by zero:
     *    - diff (x-y) cannot be zero because x ? y
     *    - If diff=0, input is invalid (x=y)
     *    
     * 3. Floating point errors:
     *    - Use integer arithmetic, not floating point
     *    - Division should be exact since (x²-y²) divisible by (x-y)
     */
    
    /**
     * XOR Method Explanation:
     * 
     * 1. XOR all array elements with numbers 1 to n
     *    Result = x XOR y (since all other numbers cancel)
     *    
     * 2. Find a set bit in the XOR result
     *    This bit is set in either x or y but not both
     *    
     * 3. Divide numbers into two groups based on that bit
     *    One group contains x, other contains y
     *    
     * 4. XOR each group separately
     *    Each group gives either x or y
     *    
     * 5. Identify which is repeating by checking array
     */
    
    /**
     * Edge Cases:
     * 
     * 1. Smallest n (2): Only two possible arrays
     * 2. Repeating at beginning/end
     * 3. Missing at beginning/end
     * 4. Large n with overflow potential
     * 5. Already sorted or reverse sorted
     */
    
    /**
     * Common Mistakes:
     * 
     * 1. Using int instead of long (overflow)
     * 2. Incorrect formula for sum of squares
     * 3. Not handling division properly
     * 4. Forgetting to cast to long before multiplication
     * 5. Assuming array is sorted
     */
    
    /**
     * Related Problems:
     * 
     * 1. Find All Duplicates in Array (LeetCode 442)
     * 2. Find All Numbers Disappeared in Array (LeetCode 448)
     * 3. First Missing Positive (LeetCode 41)
     * 4. Set Mismatch (LeetCode 645) - Same problem
     */
    
    /**
     * Applications:
     * 
     * 1. Database integrity checking
     * 2. Data validation in sequences
     * 3. Error detection in transmitted data
     * 4. Audit trails and log analysis
     */
    
    /**
     * Complexity Comparison:
     * 
     * Method           Time      Space      Notes
     * -----------------------------------------------
     * Sum & Squares    O(n)      O(1)      Risk of overflow
     * XOR              O(n)      O(1)      No overflow, elegant
     * Count Array      O(n)      O(n)      Simple but uses extra space
     * Sorting          O(n log n) O(1)     Modifies input
     */
    
    /**
     * When to use which method:
     * 
     * - Sum & Squares: When n = 10^5 (safe from overflow)
     * - XOR: General purpose, no overflow issues
     * - Count Array: When extra space is acceptable
     * - Sorting: When array can be modified and n is small
     */
}
