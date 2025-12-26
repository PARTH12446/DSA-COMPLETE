/**
 * Class implementing the atoi (string to integer) conversion
 * 
 * Problem: Implement the myAtoi function which converts a string to a 32-bit signed integer.
 * The algorithm mimics the behavior of C/C++'s atoi function.
 * 
 * Rules (in order):
 * 1. Read and ignore any leading whitespace
 * 2. Check for '+' or '-' sign (optional)
 * 3. Read digits until a non-digit character or end of string
 * 4. Convert digits to integer
 * 5. Handle overflow: clamp to INT_MAX or INT_MIN
 * 6. If no valid conversion, return 0
 * 
 * Example:
 * Input: "   -42"
 * Output: -42
 * 
 * Input: "4193 with words"
 * Output: 4193
 * 
 * Input: "words and 987"
 * Output: 0
 * 
 * Input: "-91283472332"
 * Output: -2147483648 (INT_MIN due to overflow)
 * 
 * Time Complexity: O(n) where n is string length
 * Space Complexity: O(1) - uses only constant extra space
 */
class ATOIRec {

    /**
     * Converts string to 32-bit signed integer following atoi rules
     * 
     * @param s Input string to convert
     * @return Converted integer value
     * 
     * Algorithm Steps:
     * 1. Skip leading whitespace
     * 2. Determine sign (+ or -)
     * 3. Process digits character by character
     * 4. Check for overflow after each digit
     * 5. Return final result with appropriate sign
     * 
     * Key Points:
     * - Must handle overflow before it happens
     * - Stop at first non-digit character
     * - Empty/whitespace-only strings return 0
     * - Strings starting with non-digit/non-sign return 0
     */
    public static int myAtoi(String s) {
        // Edge case: null string
        if (s == null) {
            System.out.println("Input is null, returning 0");
            return 0;
        }
        
        System.out.println("\n=== Converting string: \"" + s + "\" to integer ===");
        
        int i = 0;          // Current index in string
        int n = s.length(); // Length of string
        
        System.out.println("Step 1: Skip leading whitespace");
        
        // Step 1: Skip leading whitespace
        while (i < n && s.charAt(i) == ' ') {
            System.out.println("  Skipping whitespace at index " + i);
            i++;
        }
        
        // If we've reached end of string after skipping whitespace
        if (i == n) {
            System.out.println("  Reached end of string, only whitespace found");
            System.out.println("  Returning 0");
            return 0;
        }
        
        System.out.println("  First non-whitespace character: '" + s.charAt(i) + "' at index " + i);
        
        // Step 2: Determine sign
        System.out.println("\nStep 2: Determine sign");
        int sign = 1;  // Default positive
        
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            System.out.println("  Found sign: " + (sign == 1 ? "'+'" : "'-'"));
            i++;  // Move past the sign character
        } else {
            System.out.println("  No sign found, defaulting to positive");
        }
        
        // Step 3: Process digits
        System.out.println("\nStep 3: Process digits");
        long result = 0;  // Use long to detect overflow before casting to int
        
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';  // Convert char to integer
            System.out.println("  Processing digit " + digit + " at index " + i);
            
            // Build the number
            result = result * 10 + digit;
            System.out.println("    Current result: " + result);
            
            // Step 4: Check for overflow BEFORE it happens
            if (sign == 1 && result > Integer.MAX_VALUE) {
                System.out.println("\n  OVERFLOW DETECTED: result = " + result + 
                                 " > INT_MAX = " + Integer.MAX_VALUE);
                System.out.println("  Clamping to Integer.MAX_VALUE");
                return Integer.MAX_VALUE;
            }
            
            if (sign == -1 && -result < Integer.MIN_VALUE) {
                System.out.println("\n  OVERFLOW DETECTED: -result = " + (-result) + 
                                 " < INT_MIN = " + Integer.MIN_VALUE);
                System.out.println("  Clamping to Integer.MIN_VALUE");
                return Integer.MIN_VALUE;
            }
            
            i++;  // Move to next character
        }
        
        // Apply sign to final result
        int finalResult = (int) (sign * result);
        System.out.println("\nStep 4: Apply sign and finalize");
        System.out.println("  Final result before sign: " + result);
        System.out.println("  Sign: " + (sign == 1 ? "+" : "-"));
        System.out.println("  Final integer: " + finalResult);
        
        return finalResult;
    }
    
    /**
     * Alternative implementation with state machine approach
     * More structured but slightly more complex
     */
    public static int myAtoiStateMachine(String s) {
        if (s == null || s.length() == 0) return 0;
        
        System.out.println("\n=== State Machine Approach ===");
        
        final int START = 0;
        final int SIGN = 1;
        final int DIGIT = 2;
        final int END = 3;
        
        int state = START;
        int sign = 1;
        long result = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            switch (state) {
                case START:
                    if (c == ' ') {
                        // Stay in START state
                        System.out.println("State: START, char: '" + c + "' (space)");
                    } else if (c == '+' || c == '-') {
                        sign = (c == '-') ? -1 : 1;
                        state = SIGN;
                        System.out.println("State: START → SIGN, sign: " + sign);
                    } else if (Character.isDigit(c)) {
                        result = c - '0';
                        state = DIGIT;
                        System.out.println("State: START → DIGIT, first digit: " + result);
                    } else {
                        state = END;
                        System.out.println("State: START → END, invalid char: '" + c + "'");
                    }
                    break;
                    
                case SIGN:
                    if (Character.isDigit(c)) {
                        result = c - '0';
                        state = DIGIT;
                        System.out.println("State: SIGN → DIGIT, first digit: " + result);
                    } else {
                        state = END;
                        System.out.println("State: SIGN → END, expected digit got: '" + c + "'");
                    }
                    break;
                    
                case DIGIT:
                    if (Character.isDigit(c)) {
                        int digit = c - '0';
                        result = result * 10 + digit;
                        
                        // Check overflow
                        if (sign == 1 && result > Integer.MAX_VALUE) {
                            System.out.println("Overflow detected, returning MAX_VALUE");
                            return Integer.MAX_VALUE;
                        }
                        if (sign == -1 && -result < Integer.MIN_VALUE) {
                            System.out.println("Overflow detected, returning MIN_VALUE");
                            return Integer.MIN_VALUE;
                        }
                        
                        System.out.println("State: DIGIT, adding digit: " + digit + ", result: " + result);
                    } else {
                        state = END;
                        System.out.println("State: DIGIT → END, non-digit: '" + c + "'");
                    }
                    break;
                    
                case END:
                    // Stop processing
                    break;
            }
            
            if (state == END) break;
        }
        
        return (int) (sign * result);
    }
    
    /**
     * Helper method to visualize the conversion process
     */
    public static void visualizeAtoi(String s) {
        System.out.println("\n=== Visualizing ATOI for: \"" + s + "\" ===");
        
        if (s == null) {
            System.out.println("null → 0");
            return;
        }
        
        System.out.println("Step-by-step conversion:");
        System.out.println("String: " + s);
        
        // Create visual representation
        System.out.print("Indices: ");
        for (int i = 0; i < s.length(); i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        
        System.out.print("Chars:   ");
        for (int i = 0; i < s.length(); i++) {
            System.out.print(s.charAt(i) + " ");
        }
        System.out.println();
        
        System.out.println("\nProcessing:");
        
        int i = 0;
        int n = s.length();
        
        // Step 1: Skip whitespace
        System.out.print("1. Skip whitespace: ");
        while (i < n && s.charAt(i) == ' ') {
            System.out.print("[" + s.charAt(i) + "] ");
            i++;
        }
        System.out.println("\n   Starting at index: " + i);
        
        if (i == n) {
            System.out.println("   Only whitespace → return 0");
            return;
        }
        
        // Step 2: Check sign
        int sign = 1;
        System.out.print("2. Check sign: ");
        if (s.charAt(i) == '+' || s.charAt(i) == '-') {
            sign = (s.charAt(i) == '-') ? -1 : 1;
            System.out.println("Found " + s.charAt(i) + ", sign = " + sign);
            i++;
        } else {
            System.out.println("No sign, default to +1");
        }
        
        // Step 3: Process digits
        System.out.println("3. Process digits:");
        long result = 0;
        boolean overflow = false;
        
        while (i < n && Character.isDigit(s.charAt(i))) {
            int digit = s.charAt(i) - '0';
            
            System.out.print("   Index " + i + ": char '" + s.charAt(i) + 
                           "' → digit " + digit + ", ");
            
            // Check overflow BEFORE multiplication
            if (result > (Integer.MAX_VALUE - digit) / 10.0) {
                System.out.println("\n   OVERFLOW DETECTED!");
                overflow = true;
                break;
            }
            
            result = result * 10 + digit;
            System.out.println("result = " + result);
            i++;
        }
        
        // Step 4: Final result
        System.out.println("4. Final result:");
        if (overflow) {
            System.out.println("   Overflow occurred!");
            System.out.println("   Returning: " + (sign == 1 ? "Integer.MAX_VALUE" : "Integer.MIN_VALUE"));
        } else {
            int finalResult = (int) (sign * result);
            System.out.println("   result = " + result);
            System.out.println("   sign = " + sign);
            System.out.println("   final = " + finalResult);
        }
    }
    
    /**
     * Test cases covering all edge cases
     */
    public static void runTestCases() {
        System.out.println("=== ATOI Test Cases ===\n");
        
        // Test case matrix: [input, expected output, description]
        Object[][] testCases = {
            // Basic cases
            {"42", 42, "Simple positive number"},
            {"   -42", -42, "Negative number with leading spaces"},
            {"4193 with words", 4193, "Number followed by text"},
            {"words and 987", 0, "Text before number"},
            {"", 0, "Empty string"},
            {"     ", 0, "Only whitespace"},
            
            // Edge cases
            {"+1", 1, "Explicit positive sign"},
            {"+-12", 0, "Invalid sign sequence"},
            {"-+12", 0, "Invalid sign sequence"},
            {"00000-42a1234", 0, "Multiple signs"},
            
            // Overflow cases
            {"2147483647", Integer.MAX_VALUE, "Max int value"},
            {"-2147483648", Integer.MIN_VALUE, "Min int value"},
            {"2147483648", Integer.MAX_VALUE, "Just above max"},
            {"-2147483649", Integer.MIN_VALUE, "Just below min"},
            {"91283472332", Integer.MAX_VALUE, "Large overflow positive"},
            {"-91283472332", Integer.MIN_VALUE, "Large overflow negative"},
            
            // Boundary cases
            {"-", 0, "Only sign"},
            {"+", 0, "Only plus sign"},
            {"  0000000000012345678", 12345678, "Leading zeros"},
            {"   +0 123", 0, "Zero with space after"},
            {"0-1", 0, "Digit then sign"},
            
            // Decimal numbers
            {"3.14159", 3, "Decimal number"},
            {"  -3.14159", -3, "Negative decimal"},
            
            // Mixed characters
            {"   -0012a42", -12, "Number then letter"},
            {"  -0012 42", -12, "Number then space"},
            
            // Very large strings
            {"999999999999999999999999999999", Integer.MAX_VALUE, "Extremely large number"},
            {"-999999999999999999999999999999", Integer.MIN_VALUE, "Extremely large negative"},
            
            // Unicode and special characters
            {"  42€", 42, "Number then currency symbol"},
            {"  42  ", 42, "Number with trailing spaces"},
        };
        
        int passed = 0;
        int failed = 0;
        
        for (Object[] testCase : testCases) {
            String input = (String) testCase[0];
            int expected = (int) testCase[1];
            String description = (String) testCase[2];
            
            int result = myAtoi(input);
            boolean success = (result == expected);
            
            System.out.println("Test: " + description);
            System.out.println("  Input:    \"" + input + "\"");
            System.out.println("  Expected: " + expected);
            System.out.println("  Got:      " + result);
            System.out.println("  Result:   " + (success ? "✓ PASS" : "✗ FAIL"));
            System.out.println();
            
            if (success) passed++;
            else failed++;
        }
        
        System.out.println("Summary: " + passed + " passed, " + failed + " failed");
    }
    
    public static void main(String[] args) {
        System.out.println("=== String to Integer (ATOI) Implementation ===\n");
        
        // Interactive examples
        String[] examples = {
            "42",
            "   -42",
            "4193 with words",
            "words and 987",
            "-91283472332",
            "3.14159",
            "+1",
            "  0000000000012345678",
            "2147483648"
        };
        
        for (String example : examples) {
            System.out.println("Example: \"" + example + "\"");
            int result = myAtoi(example);
            System.out.println("Result: " + result);
            System.out.println();
        }
        
        // Visualization of a complex case
        System.out.println("\n--- Detailed Visualization ---");
        visualizeAtoi("   -4193 with words");
        
        // State machine approach
        System.out.println("\n--- State Machine Approach ---");
        int stateMachineResult = myAtoiStateMachine("   +123abc");
        System.out.println("Result: " + stateMachineResult);
        
        // Run comprehensive test cases
        System.out.println("\n--- Comprehensive Test Suite ---");
        runTestCases();
        
        // Important concepts
        System.out.println("\n=== Key Concepts ===");
        System.out.println("1. Whitespace Handling:");
        System.out.println("   - Skip ALL leading whitespace");
        System.out.println("   - Stop at first non-whitespace character");
        
        System.out.println("\n2. Sign Handling:");
        System.out.println("   - Optional '+' or '-' sign");
        System.out.println("   - Only ONE sign allowed");
        System.out.println("   - Sign must be immediately after whitespace");
        
        System.out.println("\n3. Digit Processing:");
        System.out.println("   - Read consecutive digits");
        System.out.println("   - Stop at first non-digit character");
        System.out.println("   - Leading zeros are ignored");
        
        System.out.println("\n4. Overflow Handling:");
        System.out.println("   - Check BEFORE adding each digit");
        System.out.println("   - Use long for intermediate calculation");
        System.out.println("   - Clamp to INT_MAX or INT_MIN");
        
        System.out.println("\n5. Edge Cases:");
        System.out.println("   - Empty string → 0");
        System.out.println("   - Only whitespace → 0");
        System.out.println("   - Only sign → 0");
        System.out.println("   - Text before number → 0");
        
        // Complexity Analysis
        System.out.println("\n=== Algorithm Analysis ===");
        System.out.println("Time Complexity: O(n)");
        System.out.println("  - Worst case: process entire string");
        System.out.println("  - Best case: stop at first non-digit");
        System.out.println("\nSpace Complexity: O(1)");
        System.out.println("  - Uses only a few variables");
        System.out.println("  - No extra data structures");
        
        System.out.println("\n=== Common Mistakes ===");
        System.out.println("1. Not checking overflow properly");
        System.out.println("2. Not handling sign correctly");
        System.out.println("3. Not skipping all leading whitespace");
        System.out.println("4. Continuing after non-digit characters");
        System.out.println("5. Not using long for overflow detection");
    }
}