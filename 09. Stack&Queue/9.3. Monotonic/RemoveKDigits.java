import java.util.*;

/**
 * Remove K Digits
 * Problem: Given string num representing a non-negative integer, 
 * remove k digits to make the resulting number as small as possible.
 * 
 * Approach: Use monotonic increasing stack to remove larger digits 
 * from left while maintaining smallest possible prefix.
 * 
 * Key Insight: To get the smallest number, we want to remove digits 
 * where a larger digit is followed by a smaller digit (peak removal).
 * 
 * Time Complexity: O(n) - Each digit pushed/popped at most once
 * Space Complexity: O(n) - Stack storage
 */
public class RemoveKDigits {
    
    /**
     * Removes k digits from num to form the smallest possible number.
     * 
     * Algorithm:
     * 1. Use monotonic increasing stack (bottom to top)
     * 2. Traverse each digit:
     *    - While k > 0 and current digit < stack top, pop from stack (remove larger digit)
     *    - Push current digit
     * 3. Remove remaining k digits from end (for cases like "12345" where digits are increasing)
     * 4. Build result, remove leading zeros
     * 
     * Example: num = "1432219", k = 3
     * Process: 1 → 14 → 13 (remove 4) → 132 (remove 3) → 122 (remove 2) → 1219
     * Result: "1219"
     * 
     * @param num String representation of non-negative integer
     * @param k Number of digits to remove
     * @return Smallest possible number as string after removing k digits
     */
    public static String removeKdigits(String num, int k) {
        // Edge case: If we remove all digits or more, result is "0"
        if (k >= num.length()) return "0";
        
        Stack<Character> stack = new Stack<>();
        
        // Process each digit
        for (char currentDigit : num.toCharArray()) {
            // Remove larger digits from left while we still have removals left
            // We remove when a larger digit is followed by a smaller digit
            while (k > 0 && !stack.isEmpty() && stack.peek() > currentDigit) {
                stack.pop();
                k--;
            }
            // Push current digit to stack
            stack.push(currentDigit);
        }
        
        // Case: Digits are in increasing order (e.g., "12345")
        // Remove remaining k digits from the end
        while (k-- > 0) {
            stack.pop();
        }
        
        // Build result string from stack
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }
        // Reverse because we popped from stack (LIFO)
        result.reverse();
        
        // Remove leading zeros
        // Important: We need to keep at least one digit (even if it's 0)
        while (result.length() > 1 && result.charAt(0) == '0') {
            result.deleteCharAt(0);
        }
        
        return result.length() == 0 ? "0" : result.toString();
    }
    
    /**
     * Alternative implementation using char array for better performance.
     * Avoids Stack overhead and StringBuilder reversals.
     */
    public static String removeKdigitsOptimized(String num, int k) {
        if (k >= num.length()) return "0";
        
        char[] stack = new char[num.length()];
        int top = -1; // Stack pointer
        
        for (char currentDigit : num.toCharArray()) {
            // Remove larger digits while we can
            while (k > 0 && top >= 0 && stack[top] > currentDigit) {
                top--; // Pop from stack
                k--;
            }
            stack[++top] = currentDigit; // Push to stack
        }
        
        // Remove remaining digits from end if any k left
        top -= k;
        
        // Find first non-zero digit (skip leading zeros)
        int start = 0;
        while (start <= top && stack[start] == '0') {
            start++;
        }
        
        // If all digits are zero, return "0"
        if (start > top) return "0";
        
        // Build result from start to top
        return new String(stack, start, top - start + 1);
    }
    
    /**
     * Version that shows step-by-step process for educational purposes.
     */
    public static String removeKdigitsWithSteps(String num, int k) {
        System.out.println("Removing " + k + " digits from: " + num);
        System.out.println("Step-by-step process:");
        
        if (k >= num.length()) {
            System.out.println("Removing all digits, result: 0");
            return "0";
        }
        
        Stack<Character> stack = new Stack<>();
        int step = 1;
        
        for (int i = 0; i < num.length(); i++) {
            char current = num.charAt(i);
            System.out.println("\nStep " + step++ + ": Processing digit '" + current + "'");
            System.out.println("Current stack: " + stack);
            System.out.println("Removals left: " + k);
            
            while (k > 0 && !stack.isEmpty() && stack.peek() > current) {
                char removed = stack.pop();
                k--;
                System.out.println("  Removed '" + removed + "' because '" + removed + "' > '" + current + "'");
                System.out.println("  New stack: " + stack + ", k=" + k);
            }
            
            stack.push(current);
            System.out.println("  Pushed '" + current + "'");
        }
        
        // Remove remaining from end
        while (k-- > 0) {
            char removed = stack.pop();
            System.out.println("Removed '" + removed + "' from end (increasing sequence case)");
        }
        
        // Build result
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) sb.append(stack.pop());
        sb.reverse();
        
        // Remove leading zeros
        int originalLength = sb.length();
        while (sb.length() > 1 && sb.charAt(0) == '0') sb.deleteCharAt(0);
        int zerosRemoved = originalLength - sb.length();
        if (zerosRemoved > 0) {
            System.out.println("Removed " + zerosRemoved + " leading zero(s)");
        }
        
        String result = sb.length() == 0 ? "0" : sb.toString();
        System.out.println("\nFinal result: " + result);
        return result;
    }
    
    /**
     * Test cases to demonstrate various scenarios.
     */
    public static void runTestCases() {
        System.out.println("=== Test Cases for Remove K Digits ===\n");
        
        // Test 1: Standard case
        System.out.println("Test 1: num=\"1432219\", k=3");
        System.out.println("Expected: \"1219\"");
        System.out.println("Result: " + removeKdigits("1432219", 3));
        System.out.println();
        
        // Test 2: Remove all but one
        System.out.println("Test 2: num=\"10200\", k=1");
        System.out.println("Expected: \"200\"");
        System.out.println("Result: " + removeKdigits("10200", 1));
        System.out.println();
        
        // Test 3: Increasing sequence
        System.out.println("Test 3: num=\"12345\", k=2");
        System.out.println("Expected: \"123\"");
        System.out.println("Result: " + removeKdigits("12345", 2));
        System.out.println();
        
        // Test 4: Decreasing sequence
        System.out.println("Test 4: num=\"54321\", k=2");
        System.out.println("Expected: \"321\"");
        System.out.println("Result: " + removeKdigits("54321", 2));
        System.out.println();
        
        // Test 5: Remove all digits
        System.out.println("Test 5: num=\"123\", k=3");
        System.out.println("Expected: \"0\"");
        System.out.println("Result: " + removeKdigits("123", 3));
        System.out.println();
        
        // Test 6: Leading zeros after removal
        System.out.println("Test 6: num=\"100200\", k=1");
        System.out.println("Expected: \"200\" (remove the 1)");
        System.out.println("Result: " + removeKdigits("100200", 1));
        System.out.println();
        
        // Test 7: All zeros
        System.out.println("Test 7: num=\"0000\", k=2");
        System.out.println("Expected: \"0\"");
        System.out.println("Result: " + removeKdigits("0000", 2));
        System.out.println();
        
        // Test 8: Large number
        System.out.println("Test 8: num=\"1234567890\", k=9");
        System.out.println("Expected: \"0\"");
        System.out.println("Result: " + removeKdigits("1234567890", 9));
        System.out.println();
        
        // Test with step-by-step visualization
        System.out.println("=== Step-by-step Example ===");
        removeKdigitsWithSteps("1432219", 3);
    }
    
    /**
     * Compare optimized vs original implementation.
     */
    public static void benchmark() {
        String num = "987654321098765432109876543210";
        int k = 15;
        
        System.out.println("\n=== Performance Comparison ===");
        System.out.println("Number length: " + num.length());
        System.out.println("Digits to remove: " + k);
        
        long start = System.nanoTime();
        String result1 = removeKdigits(num, k);
        long time1 = System.nanoTime() - start;
        
        start = System.nanoTime();
        String result2 = removeKdigitsOptimized(num, k);
        long time2 = System.nanoTime() - start;
        
        System.out.println("Original result: " + result1);
        System.out.println("Original time: " + time1 + " ns");
        System.out.println("Optimized result: " + result2);
        System.out.println("Optimized time: " + time2 + " ns");
        System.out.println("Speedup: " + (time1 * 1.0 / time2) + "x");
    }
    
    public static void main(String[] args) {
        // Run test cases
        runTestCases();
        
        // Show step-by-step for main example
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Main example from problem statement:");
        System.out.println("=".repeat(50));
        System.out.println(removeKdigits("1432219", 3)); // "1219"
        
        // Optional: Run benchmark
        // benchmark();
    }
}