/*
 * PROBLEM: Implement ATOI Function (Coding Ninjas)
 *
 * DESCRIPTION:
 *   - Implement the function atoi (string to integer conversion).
 *   - Given a string s, convert it to a 32-bit signed integer similar to C/C++'s atoi.
 *   - The function should handle:
 *       * Optional leading/trailing spaces
 *       * Optional leading '+' or '-' sign
 *       * Digits until a non-digit character is encountered
 *       * Overflow: clamp to INT_MAX (2147483647) or INT_MIN (-2147483648)
 *
 * EXAMPLE:
 *   "42"           → 42
 *   "   -42"       → -42
 *   "4193 with"    → 4193
 *   "words 987"    → 0
 *   "2147483648"   → 2147483647 (clamped)
 *
 * APPROACH:
 *   1. Trim leading/trailing spaces.
 *   2. Check if the whole trimmed string is numeric:
 *        - If yes, parse as long and clamp to INT range.
 *   3. Otherwise, record sign if first char is '+' or '-'.
 *   4. Traverse characters, appending only digits to a StringBuilder.
 *      Stop at the first non-digit.
 *   5. If we collected only a sign or nothing, return 0.
 *   6. Parse the collected number as long, handle NumberFormatException,
 *      and clamp to INT_MAX/INT_MIN as required.
 *
 * TIME COMPLEXITY:  O(n)  (single pass over string)
 * SPACE COMPLEXITY: O(1) extra (ignoring temporary StringBuilder)
 */

public class ATOI {

    public static int createAtoi(String s) {
        int INT_MAX = 2147483647;
        int INT_MIN = -2147483648;
        s = s.trim();
        if (s.isEmpty()) return 0;
        boolean numeric = s.chars().allMatch(Character::isDigit);
        if (numeric) {
            try {
                long val = Long.parseLong(s);
                if (val > INT_MAX) return INT_MAX;
                if (val < INT_MIN) return INT_MIN;
                return (int) val;
            } catch (NumberFormatException e) {
                return INT_MAX;
            }
        }
        String sign = (s.charAt(0) == '-') ? "-" : "";
        StringBuilder ans = new StringBuilder(sign);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c == '-' || c == '+') && i == 0) {
                continue;
            }
            if (Character.isDigit(c)) {
                ans.append(c);
            } else {
                break;
            }
        }
        if (ans.toString().equals("-") || ans.toString().equals("")) return 0;
        try {
            long val = Long.parseLong(ans.toString());
            if (val > INT_MAX) return INT_MAX;
            if (val < INT_MIN) return INT_MIN;
            return (int) val;
        } catch (NumberFormatException e) {
            return INT_MAX;
        }
    }

    public static void main(String[] args) {
        String[] tests = {
            "42",
            "   -42",
            "4193 with words",
            "words and 987",
            "2147483648",
            "-2147483649",
            "+123",
            "   +0 123"
        };

        for (String s : tests) {
            System.out.println("input: '" + s + "' → " + createAtoi(s));
        }
    }
}
