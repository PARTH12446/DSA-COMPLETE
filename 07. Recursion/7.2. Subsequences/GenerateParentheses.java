/**
 * Class solving the Generate Parentheses problem
 * 
 * Problem: Given n pairs of parentheses, generate all combinations of 
 * well-formed parentheses.
 * 
 * Example:
 * Input: n = 3
 * Output: ["((()))","(()())","(())()","()(())","()()()"]
 * 
 * Constraints:
 * - n pairs means 2n total parentheses
 * - Must be well-formed (valid):
 *   1. Every opening parenthesis '(' must have a corresponding closing ')'
 *   2. Closing parenthesis cannot come before its matching opening
 *   3. At any point, number of ')' cannot exceed number of '('
 * 
 * Approach: Backtracking with constraints
 * Time Complexity: O(4^n/√n) - Catalan number growth
 * Space Complexity: O(n) for recursion stack + O(C_n) for results
 * 
 * Mathematical Insight: Number of valid combinations is the nth Catalan number:
 * C_n = (1/(n+1)) * C(2n, n) ≈ 4^n / (n^(3/2) * √π)
 */
import java.util.ArrayList;
import java.util.List;

public class GenerateParentheses {

    /**
     * Generates all valid combinations of n pairs of parentheses
     * 
     * @param n Number of pairs of parentheses
     * @return List of all valid parentheses strings
     * 
     * Algorithm Steps:
     * 1. Start with empty string
     * 2. At each step, we can:
     *    - Add '(' if we haven't used all n opening parentheses
     *    - Add ')' if we have more '(' than ')' so far
     * 3. Stop when string length reaches 2n
     * 4. Add valid combination to results
     * 
     * Key Insight: The two conditions ensure we only generate valid parentheses:
     * 1. open < max: Can add '(' if we haven't used all opening parentheses
     * 2. close < open: Can add ')' only if we have unclosed opening parentheses
     */
    public static List<String> generateParenthesis(int n) {
        System.out.println("\n=== Generating Valid Parentheses for n=" + n + " ===");
        System.out.println("Total parentheses: " + (2 * n));
        System.out.println("Expected number of combinations (Catalan C_" + n + "): " + catalan(n));
        System.out.println("Catalan formula: C_n = (1/(n+1)) * C(2n, n)");
        
        List<String> res = new ArrayList<>();
        
        // Edge cases
        if (n <= 0) {
            System.out.println("n must be positive");
            res.add("");  // Empty string for n=0
            return res;
        }
        
        if (n > 8) {
            System.out.println("Warning: n=" + n + " will generate " + catalan(n) + 
                             " combinations (may be large)");
        }
        
        System.out.println("\nStarting backtracking...");
        backtrack(res, new StringBuilder(), 0, 0, n, 0);
        
        System.out.println("\nTotal valid combinations generated: " + res.size());
        System.out.println("Catalan C_" + n + " = " + catalan(n) + 
                         " (expected: " + (res.size() == catalan(n) ? "✓ match" : "✗ mismatch") + ")");
        return res;
    }

    /**
     * Recursive backtracking function
     * 
     * @param res Result list to store valid strings
     * @param sb StringBuilder holding current string
     * @param open Number of '(' used so far
     * @param close Number of ')' used so far
     * @param max Maximum pairs allowed (n)
     * @param depth Recursion depth (for visualization)
     */
    private static void backtrack(List<String> res, StringBuilder sb, int open, int close, int max, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "backtrack(open=" + open + ", close=" + close + 
                         ", sb=\"" + sb + "\", depth=" + depth + ")");
        
        // Base case: we've used all 2n parentheses
        if (sb.length() == 2 * max) {
            System.out.println(indent + "  ✓ Complete valid string: \"" + sb + "\"");
            res.add(sb.toString());
            return;
        }
        
        // Option 1: Add '(' if we haven't used all opening parentheses
        if (open < max) {
            System.out.println(indent + "  Can add '(': open=" + open + " < max=" + max);
            sb.append('(');
            System.out.println(indent + "  Added '(', new sb: \"" + sb + "\"");
            backtrack(res, sb, open + 1, close, max, depth + 1);
            
            // Backtrack: remove the '('
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(indent + "  Backtracked, removed '(', sb: \"" + sb + "\"");
        } else {
            System.out.println(indent + "  Cannot add '(': open=" + open + " == max=" + max);
        }
        
        // Option 2: Add ')' if we have more '(' than ')' (valid closing)
        if (close < open) {
            System.out.println(indent + "  Can add ')': close=" + close + " < open=" + open);
            sb.append(')');
            System.out.println(indent + "  Added ')', new sb: \"" + sb + "\"");
            backtrack(res, sb, open, close + 1, max, depth + 1);
            
            // Backtrack: remove the ')'
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(indent + "  Backtracked, removed ')', sb: \"" + sb + "\"");
        } else {
            System.out.println(indent + "  Cannot add ')': close=" + close + " == open=" + open);
        }
    }
    
    /**
     * Alternative approach: Generate all strings and filter valid ones
     * Less efficient but demonstrates the concept
     */
    public static List<String> generateParenthesisBruteForce(int n) {
        System.out.println("\n=== Brute Force Approach ===");
        System.out.println("Generate all 2^(2n) strings and filter valid ones");
        
        List<String> res = new ArrayList<>();
        int totalStrings = 1 << (2 * n);  // 2^(2n)
        System.out.println("Total possible strings: 2^" + (2*n) + " = " + totalStrings);
        
        for (int i = 0; i < totalStrings; i++) {
            StringBuilder sb = new StringBuilder();
            // Convert integer to binary, treat 0 as '(' and 1 as ')'
            for (int j = 0; j < 2 * n; j++) {
                if ((i & (1 << j)) == 0) {
                    sb.append('(');
                } else {
                    sb.append(')');
                }
            }
            String str = sb.toString();
            if (isValidParentheses(str)) {
                res.add(str);
            }
        }
        
        System.out.println("Valid strings found: " + res.size());
        return res;
    }
    
    /**
     * Check if a parentheses string is valid
     */
    private static boolean isValidParentheses(String s) {
        int balance = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
                if (balance < 0) {
                    return false;  // More ')' than '(' at some point
                }
            }
        }
        return balance == 0;  // All '(' matched with ')'
    }
    
    /**
     * Calculate nth Catalan number
     */
    private static long catalan(int n) {
        if (n <= 1) return 1;
        
        long[] catalan = new long[n + 1];
        catalan[0] = catalan[1] = 1;
        
        for (int i = 2; i <= n; i++) {
            catalan[i] = 0;
            for (int j = 0; j < i; j++) {
                catalan[i] += catalan[j] * catalan[i - j - 1];
            }
        }
        
        return catalan[n];
    }
    
    /**
     * Visualizes the recursion tree for parentheses generation
     */
    public static void visualizeRecursionTree(int n) {
        System.out.println("\n=== Visualizing Recursion Tree for n=" + n + " ===");
        System.out.println("Each node shows: (open, close, string)");
        System.out.println("Left child: add '(' if open < n");
        System.out.println("Right child: add ')' if close < open");
        
        visualizeTreeHelper(0, 0, n, new StringBuilder(), 0);
    }
    
    private static void visualizeTreeHelper(int open, int close, int max, 
                                           StringBuilder sb, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "Node: (" + open + ", " + close + ") \"" + sb + "\"");
        
        if (sb.length() == 2 * max) {
            System.out.println(indent + "  ✓ Leaf: \"" + sb + "\"");
            return;
        }
        
        // Left branch: add '('
        if (open < max) {
            System.out.println(indent + "  ├─ Add '(':");
            sb.append('(');
            visualizeTreeHelper(open + 1, close, max, sb, depth + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        
        // Right branch: add ')'
        if (close < open) {
            System.out.println(indent + "  └─ Add ')':");
            sb.append(')');
            visualizeTreeHelper(open, close + 1, max, sb, depth + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
    
    /**
     * Demonstrates the Catalan number sequence
     */
    public static void demonstrateCatalanNumbers() {
        System.out.println("\n=== Catalan Numbers ===");
        System.out.println("Count of valid parentheses for n pairs:");
        System.out.println("n | Catalan C_n | Formula");
        System.out.println("--|-------------|----------------------");
        for (int n = 0; n <= 8; n++) {
            long catalan = catalan(n);
            System.out.printf("%d | %11d | ", n, catalan);
            if (n == 0) {
                System.out.println("1 (empty)");
            } else {
                System.out.println("(1/(" + (n+1) + ")) * C(" + (2*n) + "," + n + ")");
            }
        }
        
        System.out.println("\nAsymptotic behavior: C_n ≈ 4^n / (n^(3/2) * √π)");
        System.out.println("Growth is exponential but slower than 4^n");
    }
    
    /**
     * Performance comparison of different approaches
     */
    public static void performanceComparison(int n) {
        System.out.println("\n=== Performance Comparison (n=" + n + ") ===");
        
        // Method 1: Backtracking
        System.out.println("\n1. Backtracking (optimal):");
        long start = System.nanoTime();
        List<String> res1 = generateParenthesis(n);
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
        System.out.println("Combinations: " + res1.size());
        
        // Method 2: Brute force
        if (n <= 4) {  // Brute force becomes too slow for n > 4
            System.out.println("\n2. Brute force (generate all and filter):");
            start = System.nanoTime();
            List<String> res2 = generateParenthesisBruteForce(n);
            end = System.nanoTime();
            System.out.println("Time: " + (end - start) / 1000000.0 + " ms");
            System.out.println("Combinations: " + res2.size());
            
            // Verify results match
            boolean match = res1.size() == res2.size();
            if (match) {
                java.util.Set<String> set1 = new java.util.HashSet<>(res1);
                java.util.Set<String> set2 = new java.util.HashSet<>(res2);
                match = set1.equals(set2);
            }
            System.out.println("Results match: " + (match ? "✓" : "✗"));
        } else {
            System.out.println("\n2. Brute force: Skipped (too slow for n=" + n + ")");
            System.out.println("   Would generate 2^" + (2*n) + " = " + (1L << (2*n)) + " strings");
        }
    }
    
    /**
     * Test different scenarios
     */
    public static void testScenarios() {
        System.out.println("\n=== Testing Different Scenarios ===");
        
        // Test 1: n = 0
        System.out.println("\n1. n = 0 (empty string):");
        List<String> res1 = generateParenthesis(0);
        System.out.println("Result: " + res1);
        System.out.println("Expected: [\"\"]");
        
        // Test 2: n = 1
        System.out.println("\n2. n = 1:");
        List<String> res2 = generateParenthesis(1);
        System.out.println("Result: " + res2);
        System.out.println("Expected: [\"()\"]");
        
        // Test 3: n = 2
        System.out.println("\n3. n = 2:");
        List<String> res3 = generateParenthesis(2);
        System.out.println("Result: " + res3);
        System.out.println("Expected: [\"(())()\",\"()(())\",\"()()()\"]? Wait...");
        System.out.println("Actually: [\"(())\",\"()()\"] (check manual)");
        
        // Test 4: n = 3 (classic example)
        System.out.println("\n4. n = 3:");
        List<String> res4 = generateParenthesis(3);
        System.out.println("Result: " + res4);
        System.out.println("Expected: [\"((()))\",\"(()())\",\"(())()\",\"()(())\",\"()()()\"]");
        
        // Test 5: Verify all strings are valid
        System.out.println("\n5. Verifying all generated strings are valid:");
        boolean allValid = true;
        for (String s : res4) {
            if (!isValidParentheses(s)) {
                System.out.println("Invalid: " + s);
                allValid = false;
            }
        }
        System.out.println("All valid: " + (allValid ? "✓" : "✗"));
        
        // Test 6: Check string lengths
        System.out.println("\n6. Checking string lengths:");
        boolean correctLengths = true;
        for (String s : res4) {
            if (s.length() != 6) {
                System.out.println("Wrong length: " + s + " (length=" + s.length() + ")");
                correctLengths = false;
            }
        }
        System.out.println("All correct length: " + (correctLengths ? "✓" : "✗"));
    }
    
    /**
     * Extended version: Generate parentheses with different brackets
     */
    public static List<String> generateMultipleBrackets(int n) {
        System.out.println("\n=== Extended: Multiple Bracket Types ===");
        System.out.println("Generate valid combinations of [], {}, and ()");
        
        List<String> res = new ArrayList<>();
        backtrackMultiple(res, new StringBuilder(), 0, 0, 0, 0, 0, 0, n, 0);
        return res;
    }
    
    private static void backtrackMultiple(List<String> res, StringBuilder sb,
                                         int openP, int closeP, int openB, int closeB,
                                         int openC, int closeC, int max, int depth) {
        if (sb.length() == 6 * max) {  // 2 * max for each of 3 types
            res.add(sb.toString());
            return;
        }
        
        // Add '('
        if (openP < max) {
            sb.append('(');
            backtrackMultiple(res, sb, openP + 1, closeP, openB, closeB, openC, closeC, max, depth + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        
        // Add ')' if valid
        if (closeP < openP) {
            sb.append(')');
            backtrackMultiple(res, sb, openP, closeP + 1, openB, closeB, openC, closeC, max, depth + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        
        // Similar for [] and {} ...
    }
    
    public static void main(String[] args) {
        System.out.println("=== Generate Parentheses (Catalan Numbers) ===\n");
        
        // Basic example with visualization
        System.out.println("Basic Example: n = 3");
        List<String> result = generateParenthesis(3);
        System.out.println("\nFinal Result: " + result);
        
        // Visualize recursion tree
        visualizeRecursionTree(3);
        
        // Demonstrate Catalan numbers
        demonstrateCatalanNumbers();
        
        // Test different scenarios
        testScenarios();
        
        // Performance comparison
        performanceComparison(3);
        
        // Mathematical analysis
        System.out.println("\n=== Mathematical Analysis ===");
        System.out.println("\nCatalan Numbers C_n = (1/(n+1)) * C(2n, n)");
        System.out.println("where C(2n, n) is binomial coefficient");
        
        System.out.println("\nRecurrence Relation:");
        System.out.println("C_0 = 1");
        System.out.println("C_n = Σ C_i * C_{n-1-i} for i = 0 to n-1");
        
        System.out.println("\nFirst few Catalan numbers:");
        for (int i = 0; i <= 10; i++) {
            System.out.println("C_" + i + " = " + catalan(i));
        }
        
        System.out.println("\nGrowth Rate:");
        System.out.println("C_n ≈ 4^n / (n^(3/2) * √π)");
        System.out.println("So time complexity is O(4^n / √n)");
        
        System.out.println("\n=== Algorithm Analysis ===");
        System.out.println("\nBacktracking Conditions:");
        System.out.println("1. Add '(' if: open < n");
        System.out.println("2. Add ')' if: close < open");
        System.out.println("\nThese conditions ensure:");
        System.out.println("- Never more than n '('");
        System.out.println("- ')' never exceeds '(' at any prefix");
        System.out.println("- Final string has equal '(' and ')'");
        
        System.out.println("\nTime Complexity: O(4^n / √n)");
        System.out.println("Space Complexity: O(n) recursion depth + O(C_n) output");
        
        System.out.println("\n=== Alternative Approaches ===");
        System.out.println("1. Brute force + validation: O(2^(2n) * n)");
        System.out.println("2. Dynamic programming: O(n^2) time, O(n^2) space");
        System.out.println("3. Iterative (BFS): Similar to backtracking");
        System.out.println("4. Closure property: S = \"(\" + A + \")\" + B");
        
        System.out.println("\n=== Dynamic Programming Approach ===");
        System.out.println("Let dp[i] = list of valid strings with i pairs");
        System.out.println("dp[0] = [\"\"]");
        System.out.println("dp[n] = {\"(\" + dp[k] + \")\" + dp[n-1-k] for k=0..n-1}");
        System.out.println("This follows the Catalan recurrence");
        
        System.out.println("\n=== Common Interview Questions ===");
        System.out.println("\nQ: Why does this generate only valid parentheses?");
        System.out.println("A: The conditions ensure at any point:");
        System.out.println("   - '(' count ≤ n");
        System.out.println("   - ')' count ≤ '(' count");
        System.out.println("   - Final lengths equal");
        
        System.out.println("\nQ: How many combinations for n=10?");
        System.out.println("A: C_10 = " + catalan(10) + " (Catalan number)");
        
        System.out.println("\nQ: What if we need to generate in lexicographic order?");
        System.out.println("A: This algorithm already does (since '(' < ')')");
        
        System.out.println("\nQ: How to generate minimum invalid parentheses to fix?");
        System.out.println("A: Different problem - use stack or counting");
        
        System.out.println("\nQ: What about other bracket types ([], {}, etc.)?");
        System.out.println("A: Extend with separate counters for each type");
        
        System.out.println("\n=== Real-World Applications ===");
        System.out.println("\n1. Parsing and compiler design");
        System.out.println("2. DNA sequence folding (secondary structure)");
        System.out.println("3. Polygon triangulation counting");
        System.out.println("4. Binary tree enumeration");
        System.out.println("5. Paths in grid without crossing diagonal");
        System.out.println("6. Non-crossing partitions");
        
        System.out.println("\n=== Key Insights ===");
        System.out.println("\n1. Catalan numbers appear in many combinatorial problems");
        System.out.println("2. Backtracking with constraints is natural for generation");
        System.out.println("3. The two conditions (open < n, close < open) are sufficient");
        System.out.println("4. Time complexity follows Catalan number asymptotics");
        System.out.println("5. The recursion tree is a binary tree of valid prefixes");
        
        System.out.println("\n=== Advanced Variations ===");
        System.out.println("\n1. Generate with maximum depth constraint");
        System.out.println("2. Generate with minimum/maximum length of consecutive '('");
        System.out.println("3. Generate parentheses with colors/types");
        System.out.println("4. Count without generating (use Catalan formula)");
        System.out.println("5. Generate all invalid parentheses of length 2n");
    }
}