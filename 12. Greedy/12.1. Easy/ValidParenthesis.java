import java.util.Scanner;

/**
 * Valid Parenthesis String (LeetCode 678) - Greedy two-pass approach.
 *
 * Problem Statement:
 * Given a string s containing only three types of characters: '(', ')' and '*',
 * return true if s can be made into a valid parentheses string.
 * The '*' character can be treated as:
 * - An open parenthesis '('
 * - A closing parenthesis ')'
 * - An empty string (ignored)
 *
 * Example 1:
 * Input: s = "()"
 * Output: true
 *
 * Example 2:
 * Input: s = "(*)"
 * Output: true
 * Explanation: '*' can be treated as ')' → "())" or as '(' → "(())" or ignored → "()"
 *
 * Example 3:
 * Input: s = "(*))"
 * Output: true
 * Explanation: Treat first '*' as '(' → "(())"
 *
 * Greedy Two-Pass Approach:
 * 1. Left-to-right pass: Treat '*' as '(' when needed (can't have too many ')')
 *    - Keep track of balance (open - close)
 *    - Increment for '(' or '*', decrement for ')'
 *    - If balance goes negative, we have too many ')' that can't be matched
 *
 * 2. Right-to-left pass: Treat '*' as ')' when needed (can't have too many '(')
 *    - Keep track of balance (close - open)
 *    - Increment for ')' or '*', decrement for '('
 *    - If balance goes negative, we have too many '(' that can't be matched
 *
 * If both passes succeed, the string is valid.
 *
 * Time Complexity: O(n) - two passes through the string
 * Space Complexity: O(1) - only integer counters
 */
public class ValidParenthesis {

    /**
     * Greedy two-pass solution
     * 
     * @param s String containing '(', ')', and '*'
     * @return true if string can be valid parentheses
     */
    public boolean checkValidString(String s) {
        // Edge cases
        if (s == null || s.length() == 0) {
            return true; // Empty string is valid
        }
        
        // ===== PASS 1: Left to Right =====
        // Treat '*' as '(' when needed to match ')'
        int balance = 0;
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '*') {
                balance++;  // Treat as open parenthesis
            } else { // c == ')'
                balance--;  // Need a matching open
            }
            
            // If balance negative, we have more ')' than '(' + '*'
            // This cannot be fixed even by treating '*' as '('
            if (balance < 0) {
                return false;
            }
        }
        
        // ===== PASS 2: Right to Left =====
        // Treat '*' as ')' when needed to match '('
        balance = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == ')' || c == '*') {
                balance++;  // Treat as close parenthesis
            } else { // c == '('
                balance--;  // Need a matching close
            }
            
            // If balance negative, we have more '(' than ')' + '*'
            // This cannot be fixed even by treating '*' as ')'
            if (balance < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Alternative: Range tracking approach
     * Keep track of possible range of open parentheses counts
     * 
     * Intuition:
     * - At any point, we have a range [low, high] of possible open counts
     * - '(' increases both low and high
     * - ')' decreases both low and high (but low can't go below 0)
     * - '*' expands the range (can be '(' or ')' or empty)
     * 
     * At the end, valid if low == 0
     */
    public boolean checkValidStringRange(String s) {
        if (s == null || s.length() == 0) return true;
        
        int low = 0;   // Minimum possible open parentheses
        int high = 0;  // Maximum possible open parentheses
        
        for (char c : s.toCharArray()) {
            if (c == '(') {
                low++;
                high++;
            } else if (c == ')') {
                low = Math.max(low - 1, 0);  // Can't go below 0
                high--;
            } else { // c == '*'
                low = Math.max(low - 1, 0);  // Could be ')'
                high++;                      // Could be '('
            }
            
            // If at any point high < 0, impossible to be valid
            if (high < 0) {
                return false;
            }
        }
        
        // Valid if we can have 0 open parentheses
        return low == 0;
    }
    
    /**
     * Visualization helper: Show step-by-step process
     */
    public void visualizeValidString(String s) {
        System.out.println("\n=== Visualizing Valid Parenthesis String ===");
        System.out.println("String: " + s);
        System.out.println("Length: " + s.length());
        
        // Method 1: Two-pass greedy
        System.out.println("\n--- Method 1: Two-Pass Greedy ---");
        
        // Pass 1: Left to Right
        System.out.println("\nPass 1 (Left → Right, treat '*' as '('):");
        int balance = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            System.out.printf("  Position %d: '%c'", i, c);
            
            if (c == '(' || c == '*') {
                balance++;
                System.out.printf(" → balance++ = %d", balance);
            } else { // c == ')'
                balance--;
                System.out.printf(" → balance-- = %d", balance);
            }
            
            if (balance < 0) {
                System.out.println(" ✗ BALANCE NEGATIVE!");
            } else {
                System.out.println();
            }
        }
        boolean pass1 = balance >= 0;
        System.out.println("Pass 1 result: " + (pass1 ? "PASS" : "FAIL"));
        
        // Pass 2: Right to Left
        System.out.println("\nPass 2 (Right → Left, treat '*' as ')'):");
        balance = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            System.out.printf("  Position %d: '%c'", i, c);
            
            if (c == ')' || c == '*') {
                balance++;
                System.out.printf(" → balance++ = %d", balance);
            } else { // c == '('
                balance--;
                System.out.printf(" → balance-- = %d", balance);
            }
            
            if (balance < 0) {
                System.out.println(" ✗ BALANCE NEGATIVE!");
            } else {
                System.out.println();
            }
        }
        boolean pass2 = balance >= 0;
        System.out.println("Pass 2 result: " + (pass2 ? "PASS" : "FAIL"));
        System.out.println("\nTwo-pass result: " + (pass1 && pass2 ? "VALID" : "INVALID"));
        
        // Method 2: Range tracking
        System.out.println("\n--- Method 2: Range Tracking ---");
        System.out.println("Tracking [low, high] = possible open counts");
        
        int low = 0, high = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            System.out.printf("\nPosition %d: '%c'", i, c);
            
            if (c == '(') {
                low++;
                high++;
                System.out.printf(" → [%d, %d]", low, high);
            } else if (c == ')') {
                low = Math.max(low - 1, 0);
                high--;
                System.out.printf(" → [%d, %d]", low, high);
            } else { // c == '*'
                low = Math.max(low - 1, 0);
                high++;
                System.out.printf(" → [%d, %d] (could be ')', '(', or empty)", low, high);
            }
            
            if (high < 0) {
                System.out.println(" ✗ HIGH < 0 → IMPOSSIBLE!");
            }
        }
        
        System.out.println("\nFinal range: [" + low + ", " + high + "]");
        System.out.println("Range result: " + (low == 0 ? "VALID (can reach 0)" : "INVALID"));
    }
    
    /**
     * Test cases and examples
     */
    public static void runTestCases() {
        ValidParenthesis solver = new ValidParenthesis();
        
        String[] testCases = {
            "()",      // true
            "(*)",     // true
            "(*))",    // true
            "(((**)",  // true
            ")**(",    // false
            "(()",     // false
            "())",     // false
            "(*()",    // true
            ")*()",    // false
            "",        // true
            "*",       // true
            "**",      // true
            "(*",      // true
            "*)",      // true
            "(()*",    // true
            "(*())",   // true
            "(((*)",   // true
            "((()))",  // true
            "((**)",   // true
            ")**",     // false
        };
        
        System.out.println("=== Test Cases ===");
        System.out.printf("%-15s %-10s %-10s %-10s\n", 
            "String", "Expected", "TwoPass", "Range");
        System.out.println("-".repeat(50));
        
        for (String test : testCases) {
            boolean twoPass = solver.checkValidString(test);
            boolean range = solver.checkValidStringRange(test);
            
            // Determine expected (simple heuristic)
            boolean expected = true; // Most are true in our test set
            if (test.equals(")**(") || test.equals("(()") || test.equals("())") || 
                test.equals(")*()") || test.equals(")**")) {
                expected = false;
            }
            
            String status = (twoPass == expected && range == expected) ? "✓" : "✗";
            System.out.printf("%-15s %-10s %-10s %-10s %s\n", 
                "\"" + test + "\"", expected, twoPass, range, status);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();  // Number of test cases
        
        while (t-- > 0) {
            String s = sc.next();
            
            ValidParenthesis solver = new ValidParenthesis();
            
            // Method 1: Two-pass greedy
            boolean result = solver.checkValidString(s);
            System.out.println(result ? "true" : "false");
            
            // Uncomment for visualization
            // solver.visualizeValidString(s);
            
            // Method 2: Range tracking (should give same result)
            // boolean result2 = solver.checkValidStringRange(s);
            // System.out.println("Range method: " + result2);
        }
        
        sc.close();
        
        // Uncomment to run test cases
        // runTestCases();
    }
}