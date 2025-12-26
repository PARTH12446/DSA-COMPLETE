/**
 * Class for generating all binary strings of length n
 * 
 * Problem: Generate all possible binary strings of length n
 * A binary string is a string consisting only of '0's and '1's
 * 
 * Example:
 * n = 2 → ["00", "01", "10", "11"]
 * n = 3 → ["000", "001", "010", "011", "100", "101", "110", "111"]
 * 
 * Number of strings: 2^n (exponential growth)
 * 
 * Approach: Backtracking / DFS (Depth-First Search)
 * Time Complexity: O(n * 2^n) - 2^n strings, each of length n
 * Space Complexity: O(n) for recursion depth + O(n * 2^n) for output
 */
import java.util.ArrayList;
import java.util.List;

public class BinaryStrings {

    /**
     * Generates all binary strings of length n using backtracking
     * 
     * @param n Length of binary strings to generate
     * @return List of all binary strings of length n
     * 
     * Algorithm Steps:
     * 1. Create result list and string builder
     * 2. Use backtracking to explore all possibilities:
     *    - At each position, try '0'
     *    - Recursively fill remaining positions
     *    - Backtrack (remove '0')
     *    - Try '1'
     *    - Recursively fill remaining positions
     *    - Backtrack (remove '1')
     * 3. When string reaches length n, add to result
     * 
     * This is essentially a depth-first traversal of a binary tree
     * where each leaf node represents a complete binary string
     */
    public static List<String> generateBinaryStrings(int n) {
        System.out.println("\n=== Generating All Binary Strings of Length " + n + " ===");
        System.out.println("Total strings expected: 2^" + n + " = " + (1 << n));
        
        List<String> res = new ArrayList<>();
        
        // Edge cases
        if (n < 0) {
            System.out.println("Invalid input: n cannot be negative");
            return res;
        }
        
        if (n == 0) {
            res.add("");  // Empty string is valid for n=0
            System.out.println("Result for n=0: [\"\"]");
            return res;
        }
        
        // Start backtracking with empty string builder
        System.out.println("\nStarting backtracking...");
        backtrack(n, new StringBuilder(), res, 0);
        
        System.out.println("\nTotal strings generated: " + res.size());
        return res;
    }

    /**
     * Recursive backtracking function to generate binary strings
     * 
     * @param n Target length of strings
     * @param sb StringBuilder holding current partial string
     * @param res Result list to store complete strings
     * @param depth Current recursion depth (for visualization)
     */
    private static void backtrack(int n, StringBuilder sb, List<String> res, int depth) {
        String indent = "  ".repeat(depth);
        
        // Base case: string has reached desired length
        if (sb.length() == n) {
            String result = sb.toString();
            res.add(result);
            System.out.println(indent + "✓ Complete: \"" + result + "\" added to results");
            return;
        }
        
        // Current position we're filling
        int pos = sb.length() + 1;
        System.out.println(indent + "Position " + pos + " of " + n + ", current: \"" + sb + "\"");
        
        // Option 1: Append '0'
        System.out.println(indent + "Trying '0' at position " + pos);
        sb.append('0');
        backtrack(n, sb, res, depth + 1);
        
        // Backtrack: remove '0' to try other options
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(indent + "Backtracked, removed '0'");
        
        // Option 2: Append '1'
        System.out.println(indent + "Trying '1' at position " + pos);
        sb.append('1');
        backtrack(n, sb, res, depth + 1);
        
        // Backtrack: remove '1' to restore state
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(indent + "Backtracked, removed '1'");
    }
    
    /**
     * Alternative approach: Generate binary strings iteratively
     * Using the fact that binary strings correspond to numbers 0 to 2^n-1
     * 
     * @param n Length of binary strings
     * @return List of all binary strings
     * 
     * Time: O(n * 2^n), Space: O(1) extra space (excluding output)
     */
    public static List<String> generateBinaryStringsIterative(int n) {
        System.out.println("\n=== Iterative Generation ===");
        
        List<String> res = new ArrayList<>();
        int total = 1 << n;  // 2^n
        
        System.out.println("Generating " + total + " strings...");
        
        for (int i = 0; i < total; i++) {
            // Convert integer to binary string with leading zeros
            String binary = Integer.toBinaryString(i);
            
            // Pad with leading zeros to reach length n
            while (binary.length() < n) {
                binary = "0" + binary;
            }
            
            res.add(binary);
            System.out.println("  Generated: " + binary + " (decimal: " + i + ")");
        }
        
        return res;
    }
    
    /**
     * Generate binary strings without consecutive 1's
     * Also known as Fibonacci binary strings
     * 
     * @param n Length of binary strings
     * @return List of binary strings without consecutive 1's
     * 
     * Example: n=3 → ["000", "001", "010", "100", "101"]
     * Count follows Fibonacci sequence: F(n+2)
     */
    public static List<String> generateNoConsecutiveOnes(int n) {
        System.out.println("\n=== Generating Binary Strings Without Consecutive 1's ===");
        
        List<String> res = new ArrayList<>();
        backtrackNoConsecutiveOnes(n, new StringBuilder(), res, 0, false);
        
        System.out.println("Total strings without consecutive 1's: " + res.size());
        System.out.println("Expected count (Fibonacci F(" + (n+2) + ")): " + fibonacci(n+2));
        
        return res;
    }
    
    private static void backtrackNoConsecutiveOnes(int n, StringBuilder sb, List<String> res, 
                                                  int depth, boolean lastWasOne) {
        if (sb.length() == n) {
            res.add(sb.toString());
            return;
        }
        
        // Always can append '0'
        sb.append('0');
        backtrackNoConsecutiveOnes(n, sb, res, depth + 1, false);
        sb.deleteCharAt(sb.length() - 1);
        
        // Can only append '1' if last wasn't '1'
        if (!lastWasOne) {
            sb.append('1');
            backtrackNoConsecutiveOnes(n, sb, res, depth + 1, true);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
    
    /**
     * Generate binary strings with exactly k ones
     * 
     * @param n Length of binary strings
     * @param k Number of '1's in each string
     * @return List of binary strings with exactly k ones
     * 
     * Example: n=4, k=2 → ["0011", "0101", "0110", "1001", "1010", "1100"]
     * Count: C(n, k) = n! / (k! * (n-k)!)
     */
    public static List<String> generateExactlyKOnes(int n, int k) {
        System.out.println("\n=== Generating Binary Strings with Exactly " + k + " Ones ===");
        
        List<String> res = new ArrayList<>();
        backtrackExactlyKOnes(n, k, new StringBuilder(), res, 0, 0);
        
        System.out.println("Total strings with exactly " + k + " ones: " + res.size());
        System.out.println("Expected count (C(" + n + ", " + k + ")): " + combination(n, k));
        
        return res;
    }
    
    private static void backtrackExactlyKOnes(int n, int k, StringBuilder sb, 
                                             List<String> res, int depth, int onesSoFar) {
        // Pruning: if we can't reach k ones or have too many ones
        int remainingPositions = n - sb.length();
        if (onesSoFar > k || onesSoFar + remainingPositions < k) {
            return;
        }
        
        if (sb.length() == n) {
            if (onesSoFar == k) {
                res.add(sb.toString());
            }
            return;
        }
        
        // Option 1: Append '0'
        sb.append('0');
        backtrackExactlyKOnes(n, k, sb, res, depth + 1, onesSoFar);
        sb.deleteCharAt(sb.length() - 1);
        
        // Option 2: Append '1'
        sb.append('1');
        backtrackExactlyKOnes(n, k, sb, res, depth + 1, onesSoFar + 1);
        sb.deleteCharAt(sb.length() - 1);
    }
    
    /**
     * Generate gray code sequences (adjacent strings differ by exactly one bit)
     * 
     * @param n Length of binary strings
     * @return List of gray code sequences
     * 
     * Example: n=2 → ["00", "01", "11", "10"] or ["00", "10", "11", "01"]
     */
    public static List<String> generateGrayCode(int n) {
        System.out.println("\n=== Generating Gray Code of Length " + n + " ===");
        
        List<String> res = new ArrayList<>();
        
        // Base case
        if (n == 0) {
            res.add("");
            return res;
        }
        
        // Generate gray code for n-1
        List<String> smaller = generateGrayCode(n - 1);
        
        // Reflect and prefix
        for (String s : smaller) {
            res.add("0" + s);
        }
        for (int i = smaller.size() - 1; i >= 0; i--) {
            res.add("1" + smaller.get(i));
        }
        
        System.out.println("Gray code sequence for n=" + n + ":");
        for (int i = 0; i < res.size(); i++) {
            System.out.println("  " + i + ": " + res.get(i));
        }
        
        return res;
    }
    
    /**
     * Visualizes the recursion tree for binary string generation
     */
    public static void visualizeRecursionTree(int n) {
        System.out.println("\n=== Recursion Tree Visualization (n=" + n + ") ===");
        System.out.println("Each level represents a position in the string");
        System.out.println("Each branch represents choosing '0' or '1'");
        System.out.println("\nTree Structure:");
        visualizeTreeHelper(n, new StringBuilder(), 0);
    }
    
    private static void visualizeTreeHelper(int n, StringBuilder sb, int depth) {
        String indent = "  ".repeat(depth);
        
        if (depth == n) {
            System.out.println(indent + "Leaf: \"" + sb + "\"");
            return;
        }
        
        System.out.println(indent + "Node at depth " + depth + ": \"" + sb + "\"");
        
        // Left branch: choose '0'
        System.out.println(indent + "  Branch '0':");
        sb.append('0');
        visualizeTreeHelper(n, sb, depth + 1);
        sb.deleteCharAt(sb.length() - 1);
        
        // Right branch: choose '1'
        System.out.println(indent + "  Branch '1':");
        sb.append('1');
        visualizeTreeHelper(n, sb, depth + 1);
        sb.deleteCharAt(sb.length() - 1);
    }
    
    /**
     * Performance comparison of different generation methods
     */
    public static void performanceComparison(int n) {
        System.out.println("\n=== Performance Comparison (n=" + n + ") ===");
        
        // Method 1: Recursive backtracking
        System.out.println("\n1. Recursive backtracking:");
        long start = System.nanoTime();
        List<String> res1 = generateBinaryStrings(n);
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        
        // Method 2: Iterative (using integer to binary conversion)
        System.out.println("\n2. Iterative (integer to binary):");
        start = System.nanoTime();
        List<String> res2 = generateBinaryStringsIterative(n);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        
        // Verify results match
        boolean match = res1.size() == res2.size();
        if (match) {
            for (int i = 0; i < res1.size(); i++) {
                if (!res1.get(i).equals(res2.get(i))) {
                    match = false;
                    break;
                }
            }
        }
        System.out.println("Results match: " + (match ? "✓" : "✗"));
    }
    
    /**
     * Helper: Calculate Fibonacci number
     */
    private static int fibonacci(int n) {
        if (n <= 1) return n;
        int a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
    
    /**
     * Helper: Calculate combination C(n, k)
     */
    private static long combination(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (k > n - k) k = n - k;  // Use symmetry
        
        long result = 1;
        for (int i = 1; i <= k; i++) {
            result = result * (n - k + i) / i;
        }
        return result;
    }
    
    /**
     * Test different binary string generation scenarios
     */
    public static void testScenarios() {
        System.out.println("\n=== Testing Different Scenarios ===");
        
        // Test 1: Basic binary strings
        System.out.println("\n1. All binary strings of length 3:");
        List<String> basic = generateBinaryStrings(3);
        System.out.println("Result: " + basic);
        System.out.println("Count: " + basic.size() + " (expected: 8)");
        
        // Test 2: No consecutive ones
        System.out.println("\n2. Binary strings of length 3 without consecutive 1's:");
        List<String> noConsecutive = generateNoConsecutiveOnes(3);
        System.out.println("Result: " + noConsecutive);
        System.out.println("Count: " + noConsecutive.size() + " (expected: 5, Fibonacci F5)");
        
        // Test 3: Exactly k ones
        System.out.println("\n3. Binary strings of length 4 with exactly 2 ones:");
        List<String> exactly2Ones = generateExactlyKOnes(4, 2);
        System.out.println("Result: " + exactly2Ones);
        System.out.println("Count: " + exactly2Ones.size() + " (expected: 6, C(4,2))");
        
        // Test 4: Gray code
        System.out.println("\n4. Gray code of length 3:");
        List<String> grayCode = generateGrayCode(3);
        System.out.println("Result: " + grayCode);
        
        // Test 5: Verify adjacent gray codes differ by one bit
        System.out.println("\n5. Verifying Gray Code property:");
        boolean validGrayCode = true;
        for (int i = 1; i < grayCode.size(); i++) {
            String prev = grayCode.get(i-1);
            String curr = grayCode.get(i);
            int diff = 0;
            for (int j = 0; j < prev.length(); j++) {
                if (prev.charAt(j) != curr.charAt(j)) diff++;
            }
            if (diff != 1) {
                validGrayCode = false;
                System.out.println("  Invalid at position " + i + ": " + prev + " → " + curr);
            }
        }
        System.out.println("Valid Gray Code: " + (validGrayCode ? "✓" : "✗"));
    }
    
    /**
     * Demonstrate the binary tree structure
     */
    public static void demonstrateBinaryTree(int n) {
        System.out.println("\n=== Binary Tree Representation (n=" + n + ") ===");
        System.out.println("Root: empty string");
        System.out.println("Each level adds a bit (0=left child, 1=right child)");
        System.out.println("Leaves are complete binary strings");
        
        int totalNodes = (1 << (n + 1)) - 1;  // Sum of geometric series
        int leaves = 1 << n;  // 2^n
        System.out.println("\nTree Statistics:");
        System.out.println("Total nodes: " + totalNodes);
        System.out.println("Leaf nodes: " + leaves);
        System.out.println("Internal nodes: " + (totalNodes - leaves));
        
        System.out.println("\nTree traversal would generate strings in this order:");
        List<String> strings = generateBinaryStrings(n);
        for (int i = 0; i < Math.min(strings.size(), 10); i++) {
            System.out.println("  " + strings.get(i));
        }
        if (strings.size() > 10) {
            System.out.println("  ... and " + (strings.size() - 10) + " more");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Binary String Generation Using Backtracking ===\n");
        
        // Basic example
        System.out.println("Basic Example: All binary strings of length 3");
        List<String> result = generateBinaryStrings(3);
        System.out.println("Result: " + result);
        
        // Visualization
        System.out.println("\n--- Recursion Tree Visualization ---");
        visualizeRecursionTree(3);
        
        // Iterative method
        System.out.println("\n--- Iterative Method ---");
        List<String> iterativeResult = generateBinaryStringsIterative(3);
        System.out.println("Result: " + iterativeResult);
        
        // Test different scenarios
        testScenarios();
        
        // Performance comparison
        System.out.println("\n--- Performance Comparison ---");
        performanceComparison(4);
        
        // Binary tree demonstration
        demonstrateBinaryTree(3);
        
        // Mathematical analysis
        System.out.println("\n=== Mathematical Analysis ===");
        System.out.println("\n1. Counting Principles:");
        System.out.println("   Total binary strings of length n: 2^n");
        System.out.println("   Strings without consecutive 1's: F(n+2) (Fibonacci)");
        System.out.println("   Strings with exactly k ones: C(n, k)");
        System.out.println("   Gray codes: 2^n (but special ordering)");
        
        System.out.println("\n2. Time Complexity:");
        System.out.println("   Each complete string takes O(n) to build");
        System.out.println("   2^n strings total → O(n * 2^n)");
        System.out.println("   This is optimal since output size is n * 2^n");
        
        System.out.println("\n3. Space Complexity:");
        System.out.println("   Recursion depth: O(n)");
        System.out.println("   Output storage: O(n * 2^n)");
        System.out.println("   Working memory: O(n) for StringBuilder");
        
        System.out.println("\n=== Backtracking Pattern ===");
        System.out.println("The algorithm follows a standard backtracking template:");
        System.out.println("1. Base case: if solution complete, add to results");
        System.out.println("2. For each possible choice at current step:");
        System.out.println("   a. Make the choice (append '0' or '1')");
        System.out.println("   b. Recurse to next step");
        System.out.println("   c. Undo the choice (backtrack)");
        
        System.out.println("\n=== Applications ===");
        System.out.println("1. Generating test cases for binary systems");
        System.out.println("2. Creating truth tables for logic circuits");
        System.out.println("3. Generating subsets (each bit = include/exclude element)");
        System.out.println("4. Creating binary masks for bit manipulation");
        System.out.println("5. Error-correcting codes (Hamming codes, Gray codes)");
        
        System.out.println("\n=== Common Interview Questions ===");
        System.out.println("Q: How would you modify this to generate ternary strings (0,1,2)?");
        System.out.println("A: Add a third branch for '2' in the backtracking");
        
        System.out.println("\nQ: How to generate strings in lexicographic order?");
        System.out.println("A: This algorithm already does (00, 01, 10, 11)");
        
        System.out.println("\nQ: What if n is very large (e.g., n=100)?");
        System.out.println("A: 2^100 is astronomical (~1.27e30). Can't generate all,");
        System.out.println("   but can generate specific patterns or random samples");
        
        System.out.println("\nQ: How to generate only strings with certain properties?");
        System.out.println("A: Add pruning conditions in backtracking");
        System.out.println("   Example: if(sb.toString().contains("11")) return;");
        
        System.out.println("\n=== Optimization Techniques ===");
        System.out.println("1. Pruning: Stop early if current path can't lead to valid solution");
        System.out.println("2. Memoization: Cache results for subproblems if possible");
        System.out.println("3. Iterative deepening: Generate strings of increasing length");
        System.out.println("4. Bit manipulation: Use integers and bit operations");
    }
}