import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Balanced Parentheses / Brackets Checker
 * 
 * Problem: Given a string containing parentheses, brackets, and braces,
 * determine if the string is balanced (properly nested and closed).
 * 
 * A string is balanced if:
 * 1. Every opening symbol has a corresponding closing symbol
 * 2. Symbols are properly nested
 * 3. No unmatched symbols remain
 * 
 * Examples:
 * Balanced: "()", "([])", "{()[]}", ""
 * Unbalanced: "(", ")(", "([)]", "({)}"
 * 
 * Applications:
 * - Compiler syntax checking
 * - JSON/XML validation
 * - Mathematical expression evaluation
 * - Code editor bracket matching
 * - HTML tag validation
 */
public class BalancedParen {

    /**
     * 1. STACK-BASED SOLUTION (Standard approach)
     * 
     * Algorithm:
     * 1. Initialize empty stack
     * 2. For each character in string:
     *    a. If opening bracket → push to stack
     *    b. If closing bracket:
     *       - If stack empty → return false
     *       - Pop from stack and check if matches
     *       - If no match → return false
     * 3. At end, check if stack empty (all brackets matched)
     * 
     * Time Complexity: O(n) where n is string length
     * Space Complexity: O(n) worst case (all opening brackets)
     */
    public static boolean isBalanced(String s) {
        // Use ArrayDeque instead of Stack for better performance
        Deque<Character> stack = new ArrayDeque<>();
        
        // Predefined mapping for faster lookups
        Map<Character, Character> matchingPairs = new HashMap<>();
        matchingPairs.put(')', '(');
        matchingPairs.put('}', '{');
        matchingPairs.put(']', '[');
        matchingPairs.put('>', '<');
        
        for (char c : s.toCharArray()) {
            // If it's an opening bracket
            if (isOpeningBracket(c)) {
                stack.push(c);
            }
            // If it's a closing bracket
            else if (isClosingBracket(c)) {
                // No matching opening bracket
                if (stack.isEmpty()) {
                    return false;
                }
                
                char top = stack.pop();
                
                // Check if brackets match
                if (matchingPairs.get(c) != top) {
                    return false;
                }
            }
            // Ignore other characters (optional)
        }
        
        // All brackets must be matched
        return stack.isEmpty();
    }

    /**
     * 2. COUNTER-BASED SOLUTION (Only for single type of parentheses)
     * Works only for strings with '(' and ')' only.
     * More space efficient: O(1) space.
     */
    public static boolean isBalancedSimple(String s) {
        int balance = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                balance++;
            } else if (c == ')') {
                balance--;
                // More closing than opening at any point
                if (balance < 0) {
                    return false;
                }
            }
        }
        
        return balance == 0;
    }

    /**
     * 3. RECURSIVE SOLUTION (Functional approach)
     * Uses recursion to validate nesting.
     */
    public static boolean isBalancedRecursive(String s) {
        return isBalancedRecursive(s, 0, new StringBuilder());
    }
    
    private static boolean isBalancedRecursive(String s, int index, 
                                              StringBuilder stack) {
        // Base case: reached end of string
        if (index == s.length()) {
            return stack.length() == 0;
        }
        
        char c = s.charAt(index);
        
        // Opening bracket
        if (isOpeningBracket(c)) {
            stack.append(c);
            return isBalancedRecursive(s, index + 1, stack);
        }
        // Closing bracket
        else if (isClosingBracket(c)) {
            if (stack.length() == 0) {
                return false;
            }
            
            char last = stack.charAt(stack.length() - 1);
            if (!match(last, c)) {
                return false;
            }
            
            // Remove matched opening bracket
            stack.deleteCharAt(stack.length() - 1);
            return isBalancedRecursive(s, index + 1, stack);
        }
        // Other character
        else {
            return isBalancedRecursive(s, index + 1, stack);
        }
    }

    /**
     * 4. FIND FIRST UNBALANCED POSITION
     * Returns index of first unbalanced character, or -1 if balanced.
     */
    public static int findFirstUnbalanced(String s) {
        Deque<BracketInfo> stack = new ArrayDeque<>();
        
        Map<Character, Character> matchingPairs = new HashMap<>();
        matchingPairs.put(')', '(');
        matchingPairs.put('}', '{');
        matchingPairs.put(']', '[');
        matchingPairs.put('>', '<');
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (isOpeningBracket(c)) {
                // Store both character and its position
                stack.push(new BracketInfo(c, i));
            }
            else if (isClosingBracket(c)) {
                if (stack.isEmpty()) {
                    return i; // Extra closing bracket
                }
                
                BracketInfo top = stack.pop();
                if (matchingPairs.get(c) != top.bracket) {
                    return i; // Mismatched bracket
                }
            }
        }
        
        // Check for unmatched opening brackets
        if (!stack.isEmpty()) {
            return stack.peek().position; // First unmatched opening
        }
        
        return -1; // All balanced
    }
    
    private static class BracketInfo {
        char bracket;
        int position;
        
        BracketInfo(char bracket, int position) {
            this.bracket = bracket;
            this.position = position;
        }
    }

    /**
     * 5. COUNT MINIMUM SWAPS TO BALANCE
     * For strings with only '(' and ')'
     * Returns minimum swaps needed to balance the string.
     */
    public static int minSwapsToBalance(String s) {
        int balance = 0;
        int swaps = 0;
        int mismatch = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                balance++;
                if (balance > 0) {
                    mismatch++;
                }
            } else if (c == ')') {
                balance--;
                if (balance < 0) {
                    mismatch++;
                }
            }
        }
        
        if (balance != 0) {
            return -1; // Cannot be balanced
        }
        
        return (mismatch + 1) / 2;
    }

    /**
     * 6. GENERATE ALL BALANCED PARENTHESES
     * Generate all valid combinations of n pairs of parentheses.
     */
    public static List<String> generateBalanced(int n) {
        List<String> result = new ArrayList<>();
        generateBalancedRecursive(n, n, "", result);
        return result;
    }
    
    private static void generateBalancedRecursive(int open, int close, 
                                                 String current, 
                                                 List<String> result) {
        // Valid combination found
        if (open == 0 && close == 0) {
            result.add(current);
            return;
        }
        
        // Can add opening bracket if we have any left
        if (open > 0) {
            generateBalancedRecursive(open - 1, close, current + "(", result);
        }
        
        // Can add closing bracket if we have more opening than closing
        if (close > open) {
            generateBalancedRecursive(open, close - 1, current + ")", result);
        }
    }

    /**
     * 7. LONGEST VALID PARENTHESES SUBSTRING
     * Find length of longest balanced parentheses substring.
     */
    public static int longestValidParentheses(String s) {
        int maxLength = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(-1); // Base index
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (c == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i); // New base
                } else {
                    maxLength = Math.max(maxLength, i - stack.peek());
                }
            }
        }
        
        return maxLength;
    }

    /**
     * 8. VALIDATE WITH ADDITIONAL SYMBOLS
     * Supports quotes and escapes.
     */
    public static boolean isBalancedWithQuotes(String s) {
        Deque<Character> brackets = new ArrayDeque<>();
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        boolean escapeNext = false;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            // Handle escape character
            if (escapeNext) {
                escapeNext = false;
                continue;
            }
            
            if (c == '\\') {
                escapeNext = true;
                continue;
            }
            
            // Handle quotes
            if (c == '\'' && !inDoubleQuote) {
                inSingleQuote = !inSingleQuote;
                continue;
            }
            
            if (c == '"' && !inSingleQuote) {
                inDoubleQuote = !inDoubleQuote;
                continue;
            }
            
            // If inside quotes, ignore brackets
            if (inSingleQuote || inDoubleQuote) {
                continue;
            }
            
            // Handle brackets
            if (isOpeningBracket(c)) {
                brackets.push(c);
            } else if (isClosingBracket(c)) {
                if (brackets.isEmpty()) {
                    return false;
                }
                
                char top = brackets.pop();
                if (!match(top, c)) {
                    return false;
                }
            }
        }
        
        // Check if all quotes are closed
        if (inSingleQuote || inDoubleQuote) {
            return false;
        }
        
        return brackets.isEmpty();
    }

    /**
     * 9. MINIMUM ADDITIONS TO BALANCE
     * Returns minimum number of brackets to add to make string balanced.
     */
    public static int minAddToMakeValid(String s) {
        int openNeeded = 0;
        int closeNeeded = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                closeNeeded++;  // Need a closing bracket
            } else if (c == ')') {
                if (closeNeeded > 0) {
                    closeNeeded--;  // Match found
                } else {
                    openNeeded++;   // Need an opening bracket
                }
            }
        }
        
        return openNeeded + closeNeeded;
    }

    /**
     * 10. CHECK NESTING LEVEL
     * Returns maximum nesting depth of brackets.
     */
    public static int maxNestingDepth(String s) {
        int currentDepth = 0;
        int maxDepth = 0;
        
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                currentDepth++;
                maxDepth = Math.max(maxDepth, currentDepth);
            } else if (c == ')' || c == '}' || c == ']') {
                currentDepth--;
            }
        }
        
        return maxDepth;
    }

    /**
     * Helper Methods
     */
    private static boolean isOpeningBracket(char c) {
        return c == '(' || c == '{' || c == '[' || c == '<';
    }
    
    private static boolean isClosingBracket(char c) {
        return c == ')' || c == '}' || c == ']' || c == '>';
    }
    
    private static boolean match(char opening, char closing) {
        return (opening == '(' && closing == ')') ||
               (opening == '{' && closing == '}') ||
               (opening == '[' && closing == ']') ||
               (opening == '<' && closing == '>');
    }

    /**
     * 11. COMPREHENSIVE TEST DRIVER
     */
    public static void main(String[] args) {
        System.out.println("=== BALANCED PARENTHESES DEMO ===\n");
        
        testBasicCases();
        testAdvancedCases();
        testPerformance();
        testEdgeCases();
        testGeneration();
        testRealWorldExamples();
    }
    
    private static void testBasicCases() {
        System.out.println("1. BASIC TEST CASES");
        
        String[] testCases = {
            "()",           // true
            "()[]{}",       // true
            "(]",           // false
            "([)]",         // false
            "{[]}",         // true
            "",             // true (empty string)
            "((()))",       // true
            "((())",        // false
            "())",          // false
        };
        
        System.out.println("Test Case\t\tResult\tExpected\tPosition\tDepth");
        System.out.println("---------\t\t------\t--------\t--------\t-----");
        
        for (String test : testCases) {
            boolean result = isBalanced(test);
            boolean expected = test.isEmpty() || 
                             (test.equals("()") || test.equals("()[]{}") || 
                              test.equals("{[]}") || test.equals("((()))"));
            
            int position = findFirstUnbalanced(test);
            int depth = maxNestingDepth(test);
            
            System.out.printf("%-20s\t%-5s\t%-8s\t%-8s\t%-5d\n",
                "\"" + test + "\"",
                result,
                expected,
                position == -1 ? "Balanced" : position,
                depth
            );
        }
        System.out.println();
    }
    
    private static void testAdvancedCases() {
        System.out.println("2. ADVANCED TEST CASES");
        
        String[] testCases = {
            "({[<>]})",     // true - all bracket types
            "a(b[c]d)e",    // true - with other characters
            "(a[b{c}d]e)",  // true - nested with content
            "((())",        // false - missing closing
            "([{)]}",       // false - wrong order
            "({[)",         // false - incomplete
        };
        
        for (String test : testCases) {
            boolean result = isBalanced(test);
            System.out.println("\"" + test + "\" → " + result);
        }
        System.out.println();
    }
    
    private static void testPerformance() {
        System.out.println("3. PERFORMANCE COMPARISON");
        
        // Generate a long balanced string
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100000; i++) {
            sb.append("(");
        }
        for (int i = 0; i < 100000; i++) {
            sb.append(")");
        }
        String longBalanced = sb.toString();
        
        System.out.println("Testing with string length " + longBalanced.length());
        
        // Test different approaches
        long start = System.nanoTime();
        boolean result1 = isBalanced(longBalanced);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        boolean result2 = isBalancedSimple(longBalanced);
        long time2 = System.nanoTime() - start;
        
        System.out.printf("Standard stack: %8.2f ms (result: %s)\n", 
                        time1 / 1_000_000.0, result1);
        System.out.printf("Counter method: %8.2f ms (result: %s)\n", 
                        time2 / 1_000_000.0, result2);
        System.out.println();
    }
    
    private static void testEdgeCases() {
        System.out.println("4. EDGE CASES");
        
        // Test with null
        try {
            isBalanced(null);
            System.out.println("FAIL: Should throw exception for null");
        } catch (NullPointerException e) {
            System.out.println("PASS: Correctly throws NPE for null");
        }
        
        // Test with only opening brackets
        System.out.println("\"(((\" → " + isBalanced("((("));
        
        // Test with only closing brackets
        System.out.println("\")))\" → " + isBalanced(")))"));
        
        // Test with mixed invalid characters
        System.out.println("\"a(b)c\" → " + isBalanced("a(b)c"));
        
        // Test very deep nesting
        String deepNested = "((((((((((()))))))))))";
        System.out.println("\"" + deepNested + "\" → " + isBalanced(deepNested));
        System.out.println("Max depth: " + maxNestingDepth(deepNested));
        
        System.out.println();
    }
    
    private static void testGeneration() {
        System.out.println("5. GENERATE BALANCED PARENTHESES");
        
        for (int n = 1; n <= 4; n++) {
            List<String> combinations = generateBalanced(n);
            System.out.println("n = " + n + ": " + combinations.size() + " combinations");
            if (n <= 3) {
                System.out.println("  " + combinations);
            } else {
                System.out.println("  First 5: " + combinations.subList(0, Math.min(5, combinations.size())));
            }
        }
        System.out.println();
    }
    
    private static void testRealWorldExamples() {
        System.out.println("6. REAL-WORLD EXAMPLES");
        
        // JSON example
        String json = "{\"name\": \"John\", \"age\": 30, \"cars\": [\"Ford\", \"BMW\"]}";
        System.out.println("JSON validation: " + isBalancedWithQuotes(json));
        
        // HTML-like example
        String html = "<div><p>Hello</p></div>";
        System.out.println("HTML validation: " + isBalanced(html.replace('<', '(').replace('>', ')')));
        
        // Code snippet
        String code = "if (x > 0) { System.out.println(\"Positive\"); }";
        System.out.println("Code validation: " + isBalancedWithQuotes(code));
        
        // Mathematical expression
        String math = "((a + b) * (c - d)) / [e + f]";
        System.out.println("Math expression: " + isBalanced(math));
        
        System.out.println();
    }
    
    /**
     * 12. VALIDATE SPECIFIC BRACKET TYPES ONLY
     * Only checks specified bracket types, ignores others.
     */
    public static boolean isBalancedSpecific(String s, String bracketTypes) {
        Map<Character, Character> pairs = new HashMap<>();
        
        // Parse bracket types string like "()[]{}"
        for (int i = 0; i < bracketTypes.length(); i += 2) {
            if (i + 1 < bracketTypes.length()) {
                pairs.put(bracketTypes.charAt(i + 1), bracketTypes.charAt(i));
            }
        }
        
        Deque<Character> stack = new ArrayDeque<>();
        
        for (char c : s.toCharArray()) {
            if (pairs.containsValue(c)) { // Opening bracket
                stack.push(c);
            } else if (pairs.containsKey(c)) { // Closing bracket
                if (stack.isEmpty() || stack.pop() != pairs.get(c)) {
                    return false;
                }
            }
        }
        
        return stack.isEmpty();
    }
    
    /**
     * 13. FIX UNBALANCED STRING
     * Returns a balanced version of the input string.
     */
    public static String fixUnbalanced(String s) {
        StringBuilder result = new StringBuilder();
        Deque<Character> stack = new ArrayDeque<>();
        
        // First pass: handle closing brackets
        for (char c : s.toCharArray()) {
            if (isOpeningBracket(c)) {
                stack.push(c);
                result.append(c);
            } else if (isClosingBracket(c)) {
                if (!stack.isEmpty() && match(stack.peek(), c)) {
                    stack.pop();
                    result.append(c);
                }
                // Else skip invalid closing bracket
            } else {
                result.append(c);
            }
        }
        
        // Second pass: add missing closing brackets
        while (!stack.isEmpty()) {
            char opening = stack.pop();
            result.append(getClosingBracket(opening));
        }
        
        return result.toString();
    }
    
    private static char getClosingBracket(char opening) {
        switch (opening) {
            case '(': return ')';
            case '{': return '}';
            case '[': return ']';
            case '<': return '>';
            default: return ' ';
        }
    }
    
    /**
     * 14. CHECK IF STRING CAN BE MADE BALANCED
     * By removing at most k brackets.
     */
    public static boolean canBeBalanced(String s, int k) {
        int minRemovals = minRemovalsToBalance(s);
        return minRemovals <= k;
    }
    
    private static int minRemovalsToBalance(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        int removals = 0;
        
        for (char c : s.toCharArray()) {
            if (isOpeningBracket(c)) {
                stack.push(c);
            } else if (isClosingBracket(c)) {
                if (stack.isEmpty()) {
                    removals++; // Extra closing bracket
                } else if (!match(stack.peek(), c)) {
                    removals++; // Mismatched bracket
                    stack.pop();
                } else {
                    stack.pop();
                }
            }
        }
        
        return removals + stack.size(); // Extra opening brackets
    }
}