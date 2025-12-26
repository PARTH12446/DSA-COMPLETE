import java.util.Stack;

/**
 * Prefix to Infix Converter
 * Converts Polish Notation to infix notation
 * Example: +AB → (A+B), *+ABC → ((A+B)*C)
 */
public class Pre_To_In {

    /**
     * Convert prefix to infix using stack
     * 
     * Algorithm:
     * 1. Scan prefix from right to left (reverse direction)
     * 2. Operand → push to stack
     * 3. Operator → pop two operands, format "(operand1 operator operand2)", push result
     * 4. Final stack top = infix expression
     * 
     * Time: O(n), Space: O(n)
     */
    public static String prefixToInfix(String s) {
        Stack<String> st = new Stack<>();
        
        // Scan from right to left
        for (int i = s.length() - 1; i >= 0; i--) {
            char c = s.charAt(i);
            
            if (Character.isLetterOrDigit(c)) {
                // Operand: push to stack
                st.push(String.valueOf(c));
            } 
            else { // Operator
                // Pop two operands (order matters)
                if (st.size() < 2) 
                    throw new IllegalArgumentException("Invalid prefix expression");
                
                String a = st.pop();  // First operand
                String b = st.pop();  // Second operand
                
                // Create infix subexpression with parentheses
                String exp = "(" + a + c + b + ")";
                st.push(exp);
            }
        }
        
        // Validate final result
        if (st.size() != 1) 
            throw new IllegalArgumentException("Invalid prefix expression");
        
        return st.pop();
    }

    /**
     * Enhanced version with:
     * - Multi-character operands
     * - Whitespace handling
     * - Error validation
     */
    public static String prefixToInfixEnhanced(String s) {
        Stack<String> st = new Stack<>();
        String[] tokens = s.trim().split("\\s+");
        
        // Process tokens from right to left
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
                
                // Add parentheses for clarity
                String exp = "(" + a + " " + op + " " + b + ")";
                st.push(exp);
            }
        }
        
        // Validate final stack
        if (st.size() != 1) {
            throw new IllegalArgumentException("Invalid prefix expression");
        }
        
        return st.pop();
    }
    
    /**
     * Evaluate prefix expression directly
     * Uses stack to compute result without conversion
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
    
    private static boolean isOperand(String token) {
        // Check if token is operand (letters or numbers)
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
        String infix1 = prefixToInfix(prefix1);
        System.out.println("Prefix: " + prefix1);
        System.out.println("Infix: " + infix1);
        // Output: ((A+B)*(C-D))
        
        // Test with spaces
        String prefix2 = "* + A B - C D";
        String infix2 = prefixToInfixEnhanced(prefix2);
        System.out.println("\nPrefix: " + prefix2);
        System.out.println("Infix: " + infix2);
        // Output: ((A + B) * (C - D))
        
        // Test evaluation
        String prefix3 = "+*23-45"; // (2*3)+(4-5) = 6 + (-1) = 5
        int result = evaluatePrefix(prefix3);
        System.out.println("\nPrefix: " + prefix3);
        System.out.println("Evaluate: " + result);
        // Output: 5
        
        // Test complex expression
        String prefix4 = "+-*2 3 4 / 6 2"; // ((2*3)-4)+(6/2) = (6-4)+3 = 5
        int result2 = evaluatePrefix(prefix4.replace(" ", ""));
        System.out.println("\nPrefix: " + prefix4);
        System.out.println("Evaluate: " + result2);
        // Output: 5
    }
}