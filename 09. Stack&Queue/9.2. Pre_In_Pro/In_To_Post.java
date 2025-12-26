import java.util.Stack;

/**
 * Infix to Postfix Converter
 * Algorithm: Shunting-yard algorithm by Edsger Dijkstra
 * Converts infix (A+B) to postfix (AB+) notation
 */
public class In_To_Post {

    // Operator precedence (higher number = higher precedence)
    private static int prec(char c) {
        return switch (c) {
            case '+', '-' -> 1;  // Lowest
            case '*', '/', '%' -> 2;
            case '^' -> 3;       // Highest (right-associative)
            default -> -1;       // Not an operator
        };
    }

    /**
     * Convert infix expression to postfix (Reverse Polish Notation)
     * 
     * Rules:
     * 1. Operands (letters/digits) → add to output immediately
     * 2. '(' → push to stack
     * 3. ')' → pop operators to output until '(' found
     * 4. Operator → pop higher/equal precedence operators, then push current
     * 
     * Time: O(n) where n = length of expression
     * Space: O(n) for stack and output
     */
    public static String infixToPostfix(String s) {
        StringBuilder out = new StringBuilder();
        Stack<Character> st = new Stack<>();
        
        for (char c : s.toCharArray()) {
            if (Character.isLetterOrDigit(c)) {
                // Operand: add directly to output
                out.append(c);
            } 
            else if (c == '(') {
                // Left parenthesis: push to stack
                st.push(c);
            } 
            else if (c == ')') {
                // Right parenthesis: pop until '('
                while (!st.isEmpty() && st.peek() != '(') 
                    out.append(st.pop());
                if (!st.isEmpty() && st.peek() == '(') 
                    st.pop(); // Discard '('
            } 
            else { // Operator
                // Pop operators with higher or equal precedence
                while (!st.isEmpty() && prec(st.peek()) >= prec(c)) {
                    out.append(st.pop());
                }
                st.push(c); // Push current operator
            }
        }
        
        // Pop remaining operators
        while (!st.isEmpty()) 
            out.append(st.pop());
        
        return out.toString();
    }

    /**
     * Enhanced version with:
     * - Error handling for invalid expressions
     * - Support for multi-digit numbers
     * - Right-associative '^' operator
     */
    public static String infixToPostfixEnhanced(String s) {
        StringBuilder out = new StringBuilder();
        Stack<Character> st = new Stack<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            // Skip whitespace
            if (Character.isWhitespace(c)) continue;
            
            // Multi-digit number support
            if (Character.isDigit(c)) {
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    out.append(s.charAt(i));
                    i++;
                }
                out.append(' '); // Separator for numbers
                i--; // Adjust for loop increment
                continue;
            }
            
            if (Character.isLetter(c)) {
                out.append(c).append(' ');
            } 
            else if (c == '(') {
                st.push(c);
            } 
            else if (c == ')') {
                while (!st.isEmpty() && st.peek() != '(') 
                    out.append(st.pop()).append(' ');
                if (st.isEmpty()) 
                    throw new IllegalArgumentException("Mismatched parentheses");
                st.pop(); // Remove '('
            } 
            else { // Operator
                // Right-associative '^' handled differently
                if (c == '^') {
                    while (!st.isEmpty() && prec(st.peek()) > prec(c)) 
                        out.append(st.pop()).append(' ');
                } else {
                    while (!st.isEmpty() && prec(st.peek()) >= prec(c)) 
                        out.append(st.pop()).append(' ');
                }
                st.push(c);
            }
        }
        
        while (!st.isEmpty()) {
            if (st.peek() == '(') 
                throw new IllegalArgumentException("Mismatched parentheses");
            out.append(st.pop()).append(' ');
        }
        
        return out.toString().trim();
    }

    /**
     * Evaluate postfix expression
     * Time: O(n), Space: O(n) for operand stack
     */
    public static int evaluatePostfix(String postfix) {
        Stack<Integer> st = new Stack<>();
        String[] tokens = postfix.split("\\s+");
        
        for (String token : tokens) {
            if (token.matches("\\d+")) { // Number
                st.push(Integer.parseInt(token));
            } else { // Operator
                int b = st.pop();
                int a = st.pop();
                switch (token) {
                    case "+": st.push(a + b); break;
                    case "-": st.push(a - b); break;
                    case "*": st.push(a * b); break;
                    case "/": st.push(a / b); break;
                    case "^": st.push((int) Math.pow(a, b)); break;
                }
            }
        }
        return st.pop();
    }

    public static void main(String[] args) {
        String infix = "a+b*(c^d-e)^(f+g*h)-i";
        String postfix = infixToPostfix(infix);
        System.out.println("Infix: " + infix);
        System.out.println("Postfix: " + postfix);
        // Output: abcd^e-fgh*+^*+i-
    }
}