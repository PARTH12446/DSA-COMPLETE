import java.util.Stack;

/**
 * Postfix to Infix Converter
 * Converts Reverse Polish Notation (RPN) to infix notation
 * Example: AB+ → (A+B), ABC*+ → (A+(B*C))
 */
public class Post_To_In {

    /**
     * Convert postfix to infix using stack
     * 
     * Algorithm:
     * 1. Scan postfix left to right
     * 2. Operand → push to stack
     * 3. Operator → pop two operands, format "(operand1 operator operand2)", push result
     * 4. Final stack top = infix expression
     * 
     * Time: O(n), Space: O(n)
     */
    public static String postfixToInfix(String s) {
        Stack<String> st = new Stack<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if (Character.isLetterOrDigit(c)) {
                // Operand: push to stack
                st.push(String.valueOf(c));
            } 
            else { // Operator
                // Pop two operands
                if (st.size() < 2) 
                    throw new IllegalArgumentException("Invalid postfix expression");
                
                String b = st.pop();
                String a = st.pop();
                
                // Create infix subexpression with parentheses
                String exp = "(" + a + c + b + ")";
                st.push(exp);
            }
        }
        
        // Final result is on stack
        if (st.size() != 1) 
            throw new IllegalArgumentException("Invalid postfix expression");
        
        return st.pop();
    }

    /**
     * Enhanced version with:
     * - Multi-character operands
     * - Whitespace handling
     * - Operator validation
     */
    public static String postfixToInfixEnhanced(String s) {
        Stack<String> st = new Stack<>();
        String[] tokens = s.trim().split("\\s+");
        
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            
            if (isOperand(token)) {
                // Operand: push to stack
                st.push(token);
            } 
            else { // Operator
                // Validate token length
                if (token.length() != 1 || !isOperator(token.charAt(0))) {
                    throw new IllegalArgumentException("Invalid operator: " + token);
                }
                
                // Check stack has at least 2 operands
                if (st.size() < 2) {
                    throw new IllegalArgumentException("Insufficient operands for operator: " + token);
                }
                
                char op = token.charAt(0);
                String b = st.pop();
                String a = st.pop();
                
                // Add parentheses for clarity (optional optimization: minimal parentheses)
                String exp = "(" + a + " " + op + " " + b + ")";
                st.push(exp);
            }
        }
        
        // Validate final stack
        if (st.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression: extra operands");
        }
        
        return st.pop();
    }
    
    /**
     * Convert postfix to infix with minimal parentheses
     * Only adds parentheses when necessary based on operator precedence
     */
    public static String postfixToInfixMinimalParentheses(String s) {
        Stack<Expr> st = new Stack<>();
        String[] tokens = s.trim().split("\\s+");
        
        for (String token : tokens) {
            if (token.isEmpty()) continue;
            
            if (isOperand(token)) {
                st.push(new Expr(token, 0)); // Operand has highest precedence
            } 
            else {
                char op = token.charAt(0);
                int opPrec = precedence(op);
                
                // Pop two operands
                Expr right = st.pop();
                Expr left = st.pop();
                
                // Add parentheses based on precedence
                String leftStr = left.expr;
                String rightStr = right.expr;
                
                // Left operand needs parentheses if its precedence < current op
                if (left.prec < opPrec || 
                    (left.prec == opPrec && isRightAssociative(op))) {
                    leftStr = "(" + leftStr + ")";
                }
                
                // Right operand needs parentheses if:
                // 1. Its precedence < current op, OR
                // 2. Same precedence and operator is not commutative
                if (right.prec < opPrec || 
                    (right.prec == opPrec && !isCommutative(op))) {
                    rightStr = "(" + rightStr + ")";
                }
                
                // Build expression
                String expr = leftStr + " " + op + " " + rightStr;
                st.push(new Expr(expr, opPrec));
            }
        }
        
        return st.pop().expr;
    }
    
    // Helper class to track expression and its operator precedence
    private static class Expr {
        String expr;
        int prec; // Precedence of top-level operator
        
        Expr(String expr, int prec) {
            this.expr = expr;
            this.prec = prec;
        }
    }
    
    private static int precedence(char op) {
        return switch (op) {
            case '+', '-' -> 1;
            case '*', '/', '%' -> 2;
            case '^' -> 3;
            default -> 0; // Operand
        };
    }
    
    private static boolean isRightAssociative(char op) {
        return op == '^'; // Only ^ is right-associative
    }
    
    private static boolean isCommutative(char op) {
        return op == '+' || op == '*'; // + and * are commutative
    }
    
    private static boolean isOperand(String token) {
        return token.matches("[a-zA-Z0-9]+");
    }
    
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '^';
    }

    /**
     * Test examples
     */
    public static void main(String[] args) {
        // Test basic conversion
        String postfix1 = "AB+CD-*";
        String infix1 = postfixToInfix(postfix1);
        System.out.println("Postfix: " + postfix1);
        System.out.println("Infix: " + infix1);
        // Output: ((A+B)*(C-D))
        
        // Test with spaces
        String postfix2 = "A B + C D - *";
        String infix2 = postfixToInfixEnhanced(postfix2);
        System.out.println("\nPostfix: " + postfix2);
        System.out.println("Infix: " + infix2);
        // Output: ((A + B) * (C - D))
        
        // Test minimal parentheses
        String postfix3 = "A B + C * D E - /";
        String infix3 = postfixToInfixMinimalParentheses(postfix3);
        System.out.println("\nPostfix: " + postfix3);
        System.out.println("Minimal Infix: " + infix3);
        // Output: (A + B) * C / (D - E)
        
        // Test with multi-digit numbers
        String postfix4 = "12 3 + 4 5 - *";
        String infix4 = postfixToInfixEnhanced(postfix4);
        System.out.println("\nPostfix: " + postfix4);
        System.out.println("Infix: " + infix4);
        // Output: ((12 + 3) * (4 - 5))
    }
}