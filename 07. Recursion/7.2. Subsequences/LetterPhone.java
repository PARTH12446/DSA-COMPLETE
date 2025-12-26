/**
 * Class solving the Letter Combinations of a Phone Number problem
 * 
 * Problem: Given a string containing digits from 2-9 inclusive, 
 * return all possible letter combinations that the number could represent.
 * 
 * Telephone keypad mapping:
 * 2: abc    3: def
 * 4: ghi    5: jkl    6: mno
 * 7: pqrs   8: tuv    9: wxyz
 * 
 * Note: Digits 0 and 1 have no letters (empty string in mapping)
 * 
 * Example:
 * Input: "23"
 * Output: ["ad","ae","af","bd","be","bf","cd","ce","cf"]
 * 
 * Explanation:
 * 2 → a, b, c
 * 3 → d, e, f
 * All combinations: ad, ae, af, bd, be, bf, cd, ce, cf
 * 
 * Approach: Backtracking / DFS
 * Time Complexity: O(4^n) where n is number of digits (worst case 7→"pqrs" has 4 letters)
 * Space Complexity: O(n) for recursion stack + O(4^n) for results
 */
import java.util.ArrayList;
import java.util.List;

public class LetterPhone {

    // Telephone keypad mapping (index corresponds to digit)
    // index 0, 1 have empty strings since 0 and 1 have no letters
    private static final String[] MAPPING = {
        "",    "",    "abc", "def", "ghi",  // 0, 1, 2, 3, 4
        "jkl", "mno", "pqrs","tuv","wxyz"   // 5, 6, 7, 8, 9
    };

    /**
     * Generates all letter combinations for a phone number
     * 
     * @param digits String containing digits 2-9
     * @return List of all possible letter combinations
     * 
     * Algorithm Steps:
     * 1. Handle edge case: empty input
     * 2. Use backtracking to explore all combinations
     * 3. At each position, get corresponding letters for digit
     * 4. For each letter, append and recurse to next digit
     * 5. When all digits processed, add combination to results
     * 
     * This is essentially a Cartesian product of all letter sets
     */
    public static List<String> letterCombinations(String digits) {
        System.out.println("\n=== Generating Letter Combinations ===");
        System.out.println("Digits: \"" + digits + "\"");
        System.out.println("Telephone keypad mapping:");
        printKeypadMapping();
        
        List<String> res = new ArrayList<>();
        
        // Edge case: empty or null input
        if (digits == null || digits.isEmpty()) {
            System.out.println("Empty input, returning empty list");
            return res;
        }
        
        // Validate input contains only digits 2-9
        if (!isValidDigits(digits)) {
            System.out.println("Invalid input: contains digits 0 or 1 or non-digits");
            return res;
        }
        
        // Calculate total expected combinations
        int totalCombinations = calculateTotalCombinations(digits);
        System.out.println("\nExpected total combinations: " + totalCombinations);
        System.out.println("Digits breakdown:");
        for (int i = 0; i < digits.length(); i++) {
            char digit = digits.charAt(i);
            String letters = MAPPING[digit - '0'];
            System.out.println("  " + digit + " → \"" + letters + "\" (" + letters.length() + " letters)");
        }
        
        System.out.println("\nStarting backtracking...");
        backtrack(0, digits, new StringBuilder(), res, 0);
        
        System.out.println("\nTotal combinations generated: " + res.size());
        System.out.println("Expected: " + totalCombinations + " (match: " + 
                         (res.size() == totalCombinations ? "✓" : "✗") + ")");
        return res;
    }

    /**
     * Recursive backtracking function
     * 
     * @param idx Current index in digits string
     * @param digits Input digits string
     * @param sb StringBuilder holding current combination
     * @param res Result list to store complete combinations
     * @param depth Recursion depth (for visualization)
     */
    private static void backtrack(int idx, String digits, StringBuilder sb, 
                                 List<String> res, int depth) {
        String indent = "  ".repeat(depth);
        System.out.println(indent + "backtrack(idx=" + idx + ", sb=\"" + sb + "\", depth=" + depth + ")");
        
        // Base case: processed all digits
        if (idx == digits.length()) {
            System.out.println(indent + "  ✓ Complete combination: \"" + sb + "\"");
            res.add(sb.toString());
            return;
        }
        
        // Get current digit and its corresponding letters
        char digitChar = digits.charAt(idx);
        int digit = digitChar - '0';
        String letters = MAPPING[digit];
        
        System.out.println(indent + "  Processing digit " + digit + " → letters: \"" + letters + "\"");
        
        // Try each possible letter for current digit
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            System.out.println(indent + "    Trying letter '" + letter + "' for digit " + digit);
            
            // Make choice: append letter
            sb.append(letter);
            System.out.println(indent + "    Added '" + letter + "', sb: \"" + sb + "\"");
            
            // Recurse to next digit
            backtrack(idx + 1, digits, sb, res, depth + 1);
            
            // Backtrack: remove letter
            sb.deleteCharAt(sb.length() - 1);
            System.out.println(indent + "    Backtracked, removed '" + letter + "', sb: \"" + sb + "\"");
        }
    }
    
    /**
     * Alternative approach: Iterative using BFS/Queue
     * Build combinations level by level
     */
    public static List<String> letterCombinationsIterative(String digits) {
        System.out.println("\n=== Iterative Approach (BFS with Queue) ===");
        
        List<String> res = new ArrayList<>();
        if (digits == null || digits.isEmpty()) return res;
        
        // Start with empty string
        res.add("");
        
        for (int i = 0; i < digits.length(); i++) {
            char digitChar = digits.charAt(i);
            int digit = digitChar - '0';
            String letters = MAPPING[digit];
            
            System.out.println("\nProcessing digit " + digit + " → \"" + letters + "\"");
            System.out.println("Current combinations: " + res);
            
            List<String> newRes = new ArrayList<>();
            for (String combination : res) {
                for (char letter : letters.toCharArray()) {
                    newRes.add(combination + letter);
                }
            }
            res = newRes;
            
            System.out.println("After processing: " + res);
        }
        
        return res;
    }
    
    /**
     * Alternative approach: Iterative using array (more memory efficient)
     */
    public static List<String> letterCombinationsIterativeArray(String digits) {
        System.out.println("\n=== Iterative Approach (Array) ===");
        
        if (digits == null || digits.isEmpty()) {
            return new ArrayList<>();
        }
        
        // Calculate total combinations
        int total = 1;
        for (int i = 0; i < digits.length(); i++) {
            total *= MAPPING[digits.charAt(i) - '0'].length();
        }
        
        System.out.println("Total combinations: " + total);
        
        String[] result = new String[total];
        // Initialize with empty strings
        for (int i = 0; i < total; i++) {
            result[i] = "";
        }
        
        // Build combinations digit by digit
        int cycle = total;
        for (int i = 0; i < digits.length(); i++) {
            char digitChar = digits.charAt(i);
            String letters = MAPPING[digitChar - '0'];
            cycle /= letters.length();
            
            System.out.println("\nProcessing digit " + digitChar + " → \"" + letters + "\"");
            System.out.println("Cycle size: " + cycle);
            
            for (int j = 0; j < total; j++) {
                int letterIndex = (j / cycle) % letters.length();
                result[j] += letters.charAt(letterIndex);
            }
        }
        
        return java.util.Arrays.asList(result);
    }
    
    /**
     * Prints the telephone keypad mapping
     */
    private static void printKeypadMapping() {
        System.out.println("+---+-----------+");
        System.out.println("| 0 | (none)    |");
        System.out.println("| 1 | (none)    |");
        for (int i = 2; i <= 9; i++) {
            String letters = MAPPING[i];
            System.out.printf("| %d | %-9s |\n", i, "\"" + letters + "\"");
        }
        System.out.println("+---+-----------+");
    }
    
    /**
     * Validates that digits string contains only 2-9
     */
    private static boolean isValidDigits(String digits) {
        for (char c : digits.toCharArray()) {
            if (c < '2' || c > '9') {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Calculates total number of combinations
     */
    private static int calculateTotalCombinations(String digits) {
        int total = 1;
        for (int i = 0; i < digits.length(); i++) {
            int digit = digits.charAt(i) - '0';
            total *= MAPPING[digit].length();
        }
        return total;
    }
    
    /**
     * Visualizes the recursion tree
     */
    public static void visualizeRecursionTree(String digits) {
        System.out.println("\n=== Visualizing Recursion Tree ===");
        System.out.println("Digits: \"" + digits + "\"");
        
        if (digits == null || digits.isEmpty()) {
            System.out.println("Empty input");
            return;
        }
        
        System.out.println("\nTree Structure (DFS):");
        visualizeTreeHelper(0, digits, new StringBuilder(), 0);
    }
    
    private static void visualizeTreeHelper(int idx, String digits, 
                                           StringBuilder sb, int depth) {
        String indent = "  ".repeat(depth);
        
        if (idx == digits.length()) {
            System.out.println(indent + "Leaf: \"" + sb + "\"");
            return;
        }
        
        char digitChar = digits.charAt(idx);
        int digit = digitChar - '0';
        String letters = MAPPING[digit];
        
        System.out.println(indent + "Node: digit " + digit + " → \"" + letters + "\", sb: \"" + sb + "\"");
        
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            System.out.println(indent + "  Branch '" + letter + "':");
            sb.append(letter);
            visualizeTreeHelper(idx + 1, digits, sb, depth + 1);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
    
    /**
     * Performance comparison of different approaches
     */
    public static void performanceComparison(String digits) {
        System.out.println("\n=== Performance Comparison ===");
        System.out.println("Digits: \"" + digits + "\"");
        
        // Method 1: Recursive backtracking
        System.out.println("\n1. Recursive backtracking (DFS):");
        long start = System.nanoTime();
        List<String> res1 = letterCombinations(digits);
        long end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000.0 + " microseconds");
        System.out.println("Combinations: " + res1.size());
        
        // Method 2: Iterative BFS
        System.out.println("\n2. Iterative BFS:");
        start = System.nanoTime();
        List<String> res2 = letterCombinationsIterative(digits);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000.0 + " microseconds");
        System.out.println("Combinations: " + res2.size());
        
        // Method 3: Iterative array
        System.out.println("\n3. Iterative array:");
        start = System.nanoTime();
        List<String> res3 = letterCombinationsIterativeArray(digits);
        end = System.nanoTime();
        System.out.println("Time: " + (end - start) / 1000.0 + " microseconds");
        System.out.println("Combinations: " + res3.size());
        
        // Verify all methods produce same results
        boolean allMatch = res1.size() == res2.size() && res2.size() == res3.size();
        if (allMatch) {
            java.util.Set<String> set1 = new java.util.HashSet<>(res1);
            java.util.Set<String> set2 = new java.util.HashSet<>(res2);
            java.util.Set<String> set3 = new java.util.HashSet<>(res3);
            allMatch = set1.equals(set2) && set2.equals(set3);
        }
        System.out.println("All results match: " + (allMatch ? "✓" : "✗"));
    }
    
    /**
     * Test different scenarios
     */
    public static void testScenarios() {
        System.out.println("\n=== Testing Different Scenarios ===");
        
        // Test 1: Empty input
        System.out.println("\n1. Empty input:");
        List<String> res1 = letterCombinations("");
        System.out.println("Result: " + res1);
        System.out.println("Expected: []");
        
        // Test 2: Single digit
        System.out.println("\n2. Single digit \"2\":");
        List<String> res2 = letterCombinations("2");
        System.out.println("Result: " + res2);
        System.out.println("Expected: [\"a\", \"b\", \"c\"]");
        
        // Test 3: Classic example
        System.out.println("\n3. Two digits \"23\":");
        List<String> res3 = letterCombinations("23");
        System.out.println("Result: " + res3);
        System.out.println("Expected: [\"ad\",\"ae\",\"af\",\"bd\",\"be\",\"bf\",\"cd\",\"ce\",\"cf\"]");
        
        // Test 4: Digit with 4 letters
        System.out.println("\n4. Digit with 4 letters \"7\":");
        List<String> res4 = letterCombinations("7");
        System.out.println("Result: " + res4);
        System.out.println("Expected: [\"p\", \"q\", \"r\", \"s\"]");
        
        // Test 5: Multiple digits including 7 and 9
        System.out.println("\n5. Multiple digits \"79\":");
        List<String> res5 = letterCombinations("79");
        System.out.println("Result size: " + res5.size());
        System.out.println("Expected: 4 * 4 = 16 combinations");
        System.out.println("First few: " + res5.subList(0, Math.min(5, res5.size())));
        
        // Test 6: Long input
        System.out.println("\n6. Longer input \"234\":");
        List<String> res6 = letterCombinations("234");
        System.out.println("Result size: " + res6.size());
        System.out.println("Expected: 3 * 3 * 3 = 27 combinations");
        
        // Test 7: Invalid input (contains 0 or 1)
        System.out.println("\n7. Invalid input \"01\":");
        List<String> res7 = letterCombinations("01");
        System.out.println("Result: " + res7);
        System.out.println("Expected: [] (0 and 1 have no letters)");
    }
    
    /**
     * Demonstrates the Cartesian product concept
     */
    public static void demonstrateCartesianProduct() {
        System.out.println("\n=== Demonstrating Cartesian Product ===");
        
        System.out.println("Problem is essentially Cartesian product of letter sets");
        System.out.println("\nExample: digits = \"23\"");
        System.out.println("Set A (digit 2): {\"a\", \"b\", \"c\"}");
        System.out.println("Set B (digit 3): {\"d\", \"e\", \"f\"}");
        System.out.println("Cartesian product A × B = {");
        System.out.println("  (a,d), (a,e), (a,f),");
        System.out.println("  (b,d), (b,e), (b,f),");
        System.out.println("  (c,d), (c,e), (c,f)");
        System.out.println("}");
        
        System.out.println("\nGeneral formula:");
        System.out.println("For digits d1d2...dn with letter counts c1, c2, ..., cn");
        System.out.println("Total combinations = c1 × c2 × ... × cn");
    }
    
    public static void main(String[] args) {
        System.out.println("=== Letter Combinations of a Phone Number ===\n");
        
        // Basic example with visualization
        System.out.println("Basic Example: digits = \"23\"");
        List<String> result = letterCombinations("23");
        System.out.println("\nFinal Result: " + result);
        
        // Visualize recursion tree
        visualizeRecursionTree("23");
        
        // Demonstrate Cartesian product
        demonstrateCartesianProduct();
        
        // Test different scenarios
        testScenarios();
        
        // Performance comparison
        performanceComparison("234");
        
        // Advanced topics
        System.out.println("\n=== Advanced Topics ===");
        System.out.println("\n1. Time Complexity Analysis:");
        System.out.println("   Worst case: digit 7 or 9 (4 letters)");
        System.out.println("   For n digits, worst case time: O(4^n)");
        System.out.println("   For digits \"777...\" (n times): 4^n combinations");
        
        System.out.println("\n2. Space Complexity:");
        System.out.println("   Recursion depth: O(n)");
        System.out.println("   Output storage: O(4^n * n) characters");
        
        System.out.println("\n3. Alternative Data Structures:");
        System.out.println("   Could use char array instead of StringBuilder");
        System.out.println("   Could use iterative approaches for large n");
        
        System.out.println("\n=== Different Approaches ===");
        System.out.println("\n1. Backtracking (DFS):");
        System.out.println("   - Natural recursive solution");
        System.out.println("   - Easy to understand and implement");
        System.out.println("   - Uses O(n) recursion stack space");
        
        System.out.println("\n2. Iterative BFS:");
        System.out.println("   - Build combinations level by level");
        System.out.println("   - No recursion overhead");
        System.out.println("   - Uses O(4^n) intermediate storage");
        
        System.out.println("\n3. Iterative with array:");
        System.out.println("   - Pre-calculate total combinations");
        System.out.println("   - Fill array systematically");
        System.out.println("   - Most memory efficient");
        
        System.out.println("\n=== Common Interview Questions ===");
        System.out.println("\nQ: What about digits 0 and 1?");
        System.out.println("A: They have no letters, so return empty list");
        
        System.out.println("\nQ: How would you handle international keypads?");
        System.out.println("A: Use different mapping array based on locale");
        
        System.out.println("\nQ: What if we need to generate combinations in specific order?");
        System.out.println("A: This algorithm generates in lexicographic order");
        
        System.out.println("\nQ: How to optimize for memory?");
        System.out.println("A: Use iterative array approach or generate on demand");
        
        System.out.println("\nQ: What if digits string is very long?");
        System.out.println("A: Number of combinations grows exponentially, so");
        System.out.println("   practical limit is around 10-12 digits");
        
        System.out.println("\n=== Real-World Applications ===");
        System.out.println("\n1. Telephone word generation (vanity numbers)");
        System.out.println("2. T9 predictive text input");
        System.out.println("3. Password generation from numeric sequences");
        System.out.println("4. Game cheat code generation");
        System.out.println("5. Mnemonic devices for numbers");
        
        System.out.println("\n=== Key Insights ===");
        System.out.println("\n1. Problem reduces to Cartesian product of letter sets");
        System.out.println("2. Number of combinations grows exponentially with digits");
        System.out.println("3. Digits 7 and 9 are worst case (4 letters each)");
        System.out.println("4. Backtracking naturally produces lexicographic order");
        System.out.println("5. Multiple valid approaches with different tradeoffs");
        
        System.out.println("\n=== Edge Cases to Consider ===");
        System.out.println("1. Empty input string");
        System.out.println("2. Input containing 0 or 1");
        System.out.println("3. Very long input (exponential blowup)");
        System.out.println("4. Input with repeated digits");
        System.out.println("5. Non-digit characters in input");
        
        System.out.println("\n=== Optimization Ideas ===");
        System.out.println("1. Pre-calculate total combinations for validation");
        System.out.println("2. Use char array instead of StringBuilder");
        System.out.println("3. Generate combinations iteratively for large n");
        System.out.println("4. Use bit manipulation for small n (n ≤ 15)");
        System.out.println("5. Lazy generation using iterator pattern");
    }
}