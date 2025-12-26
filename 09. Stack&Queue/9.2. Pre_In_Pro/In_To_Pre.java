import java.util.Stack;

/**
 * Infix to Prefix Converter
 * Prefix (Polish Notation): Operators come before operands
 * Example: (A+B) → +AB, A*(B+C) → *A+BC
 */
public class In_To_Pre {

    /**
     * Convert infix to prefix using modified shunting-yard algorithm
     * 
     * Algorithm:
     * 1. Reverse the infix expression
     * 2. Swap '(' with ')' and vice versa
     * 3. Convert to postfix using standard algorithm
     * 4. Reverse the result to get prefix
     * 
     * Time: O(n), Space: O(n)
     */
    public static String infixToPrefix(String s) {
        // Step 1: Reverse infix expression
        StringBuilder sb = new StringBuilder(s).reverse();
        
        // Step 2: Swap '(' with ')'
        for (int i = 0; i < sb.length(); i++) {
            char c = sb.charAt(i);
            if (c == '(') sb.setCharAt(i, ')');
            else if (c == ')') sb.setCharAt(i, '(');
        }
        
        // Step 3: Convert reversed/swapped to postfix
        String rev = sb.toString();
        String post = infixToPostfixCustom(rev);
        
        // Step 4: Reverse postfix to get prefix
        return new StringBuilder(post).reverse().toString();
    }

    /**
     * Direct conversion without relying on external class
     * Handles parentheses and operator precedence
     */
    private static String infixToPostfixCustom(String s) {
        StringBuilder out = new StringBuilder();
        Stack<Character> st = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                out.append(c);
            } 
            else if (c == '(') {
                st.push(c);
            } 
            else if (c == ')') {
                while (!st.isEmpty() && st.peek() != '(') 
                    out.append(st.pop());
                if (!st.isEmpty()) st.pop(); // Remove '('
            } 
            else { // Operator
                while (!st.isEmpty() && 
                       precedence(st.peek()) >= precedence(c)) {
                    out.append(st.pop());
                }
                st.push(c);
            }
        }
        
        while (!st.isEmpty()) out.append(st.pop());
        return out.toString();
    }

    /**
     * Operator precedence (same as postfix)
     */
    private static int precedence(char c) {
        return switch (c) {
            case '+', '-' -> 1;
            case '*', '/', '%' -> 2;
            case '^' -> 3;  // Right-associative in postfix, but treated as left in prefix
            default -> -1;
        };
    }

    /**
     * Evaluate prefix expression
     * Algorithm: Process from right to left
     * Time: O(n), Space: O(n) for operand stack
     */
    public static int evaluatePrefix(String prefix) {
        Stack<Integer> st = new Stack<>();
        
        // Process from right to left
        for (int i = prefix.length() - 1; i >= 0; i--) {
            char c = prefix.charAt(i);
            
            if (Character.isDigit(c)) {
                st.push(c - '0');  // Convert char to int
            } 
            else { // Operator
                int a = st.pop();
                int b = st.pop();
                switch (c) {
                    case '+': st.push(a + b); break;
                    case '-': st.push(a - b); break;
                    case '*': st.push(a * b); break;
                    case '/': st.push(a / b); break;
                    case '^': st.push((int) Math.pow(a, b)); break;
                }
            }
        }
        return st.pop();
    }

    /**
     * Enhanced version with multi-digit support
     */
    public static String infixToPrefixEnhanced(String s) {
        // Add spaces for tokenization
        StringBuilder spaced = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (isOperator(c) || c == '(' || c == ')') {
                spaced.append(' ').append(c).append(' ');
            } else {
                spaced.append(c);
            }
        }
        
        // Split into tokens
        String[] tokens = spaced.toString().trim().split("\\s+");
        
        // Reverse tokens
        StringBuilder reversed = new StringBuilder();
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            if (token.equals("(")) reversed.append(") ");
            else if (token.equals(")")) reversed.append("( ");
            else reversed.append(token).append(" ");
        }
        
        // Convert reversed to postfix
        String postfix = infixToPostfixTokens(reversed.toString().trim().split("\\s+"));
        
        // Reverse postfix to get prefix
        String[] postfixTokens = postfix.split("\\s+");
        StringBuilder prefix = new StringBuilder();
        for (int i = postfixTokens.length - 1; i >= 0; i--) {
            prefix.append(postfixTokens[i]).append(" ");
        }
        
        return prefix.toString().trim();
    }
    
    private static String infixToPostfixTokens(String[] tokens) {
        StringBuilder out = new StringBuilder();
        Stack<String> st = new Stack<>();
        
        for (String token : tokens) {
            if (token.length() == 1) {
                char c = token.charAt(0);
                if (Character.isLetterOrDigit(c)) {
                    out.append(token).append(" ");
                } 
                else if (c == '(') {
                    st.push(token);
                } 
                else if (c == ')') {
                    while (!st.isEmpty() && !st.peek().equals("(")) 
                        out.append(st.pop()).append(" ");
                    if (!st.isEmpty()) st.pop(); // Remove "("
                } 
                else { // Operator
                    while (!st.isEmpty() && 
                           precedence(st.peek().charAt(0)) >= precedence(c)) {
                        out.append(st.pop()).append(" ");
                    }
                    st.push(token);
                }
            } else { // Multi-character operand
                out.append(token).append(" ");
            }
        }
        
        while (!st.isEmpty()) out.append(st.pop()).append(" ");
        return out.toString().trim();
    }
    
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
    }

    public static void main(String[] args) {
        String infix = "A+B*(C-D)";
        String prefix = infixToPrefix(infix);
        System.out.println("Infix: " + infix);
        System.out.println("Prefix: " + prefix); // Output: +A*B-CD
        
        // Test evaluation
        String prefixExpr = "*+23-45"; // (2+3)*(4-5) = 5*-1 = -5
        int result = evaluatePrefix(prefixExpr);
        System.out.println("Evaluate " + prefixExpr + " = " + result);
    }
}