import java.util.Stack;

/**
 * Prefix to Postfix Converter
 * Converts Polish Notation to Reverse Polish Notation
 * Example: +AB → AB+, *+ABC → AB+C*
 */
public class Pre_To_Post {

    /**
     * Convert prefix to postfix using stack
     * 
     * Algorithm:
     * 1. Scan prefix from right to left
     * 2. Operand → push to stack
     * 3. Operator → pop two operands, format "operand1 operand2 operator", push result
     * 4. Final stack top = postfix expression
     * 
     * Time: O(n), Space: O(n)
     */
    public static String prefixToPostfix(String s) {
        Stack<String> st = new Stack<>();
        
        // Scan from right to left
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            
            if (Character.isLetterOrDigit(c)) {
                // Operand: push to stack
                st.push(String.valueOf(c));
            } 
            else { // Operator
                // Pop two operands
                if (st.size() < 2) 
                    throw new IllegalArgumentException("Invalid prefix expression");
                
                String a = st.pop();  // First operand
                String b = st.pop();  // Second operand
                
                // Create postfix: "operand1 operand2 operator"
                String exp = a + b + c;
                st.push(exp);
            }
        }
        
        // Validate result
        if (st.size() != 1) 
            throw new IllegalArgumentException("Invalid prefix expression");
        
        return st.pop();
    }

    /**
     * Enhanced version with:
     * - Multi-character operands
     * - Whitespace handling
     * - Better validation
     */
    public static String prefixToPostfixEnhanced(String s) {
        Stack<String> st = new Stack<>();
        String[] tokens = s.trim().split("\\s+");
        
        // Process from right to left
        for (int i = tokens.length - 1; i >= 0; i--) {
            String token = tokens[i];
            
            if (isOperand(token)) {
                // Operand: push to stack
                st.push(token);
            } 
            else { // Operator
                // Validate operator
                if (token.length() != 1 || !isOperator(token.charAt(0))) {
                    throw new IllegalArgumentException("Invalid operator: " + token);
                }
                
                // Check stack has at least 2 operands
                if (st.size() < 2) {
                    throw new IllegalArgumentException("Insufficient operands for operator: " + token);
                }
                
                char op = token.charAt(0);
                String a = st.pop();  // First operand
                String b = st.pop();  // Second operand
                
                // Create postfix with space separator
                String exp = a + " " + b + " " + op;
                st.push(exp);
            }
        }
        
        // Validate final result
        if (st.size() != 1) {
            throw new IllegalArgumentException("Invalid prefix expression");
        }
        
        return st.pop();
    }
    
    /**
     * Direct evaluation without conversion
     * Evaluates prefix expression using stack
     * Time: O(n), Space: O(n)
     */
    public static int evaluatePrefix(String s) {
        Stack<Integer> st = new Stack<>();
        
        // Process from right to left
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            
            if (Character.isDigit(c)) {
                // Operand: push numeric value
                st.push(c - '0');
            } 
            else if (Character.isLetter(c)) {
                // Variable: treat as 1 for evaluation
                st.push(1);
            }
            else { // Operator
                // Pop two operands
                if (st.size() < 2) 
                    throw new IllegalArgumentException("Invalid expression");
                
                int a = st.pop();
                int b = st.pop();
                
                // Apply operation
                switch (c) {
                    case '+': st.push(a + b); break;
                    case '-': st.push(a - b); break;
                    case '*': st.push(a * b); break;
                    case '/': st.push(a / b); break;
                    case '^': st.push((int) Math.pow(a, b)); break;
                    default: throw new IllegalArgumentException("Unknown operator: " + c);
                }
            }
        }
        
        if (st.size() != 1) 
            throw new IllegalArgumentException("Invalid expression");
        
        return st.pop();
    }
    
    /**
     * Alternative approach: Convert to infix first, then to postfix
     * For demonstration purposes
     */
    public static String prefixToPostfixAlternative(String s) {
        // Convert prefix to infix
        String infix = Pre_To_In.prefixToInfix(s);
        
        // Convert infix to postfix
        return In_To_Post.infixToPostfix(infix);
    }
    
    private static boolean isOperand(String token) {
        return token.matches("[a-zA-Z0-9]+");
    }
    
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '^';
    }

    /**
     * Test examples
     */
    public static void main(String[] args) {
        // Test basic conversion
        String prefix1 = "*+AB-CD";
        String postfix1 = prefixToPostfix(prefix1);
        System.out.println("Prefix: " + prefix1);
        System.out.println("Postfix: " + postfix1);
        // Output: AB+CD-*
        
        // Test with spaces
        String prefix2 = "* + A B - C D";
        String postfix2 = prefixToPostfixEnhanced(prefix2);
        System.out.println("\nPrefix: " + prefix2);
        System.out.println("Postfix: " + postfix2);
        // Output: A B + C D - *
        
        // Test evaluation
        String prefix3 = "+*23-45"; // (2*3)+(4-5) = 6 + (-1) = 5
        int result = evaluatePrefix(prefix3);
        System.out.println("\nPrefix: " + prefix3);
        System.out.println("Evaluate: " + result);
        // Output: 5
        
        // Test complex expression
        String prefix4 = "-+*2 3 4 / 8 2"; // ((2*3)+4)-(8/2) = (6+4)-4 = 6
        String postfix4 = prefixToPostfixEnhanced(prefix4);
        int result2 = evaluatePrefix(prefix4.replace(" ", ""));
        System.out.println("\nPrefix: " + prefix4);
        System.out.println("Postfix: " + postfix4);
        System.out.println("Evaluate: " + result2);
        // Output: 2 3 * 4 + 8 2 / -
        
        // Test alternative approach
        String prefix5 = "+A*BC"; // A+(B*C)
        String postfix5 = prefixToPostfixAlternative(prefix5);
        System.out.println("\nPrefix: " + prefix5);
        System.out.println("Postfix (alternative): " + postfix5);
        // Output: ABC*+
    }
}