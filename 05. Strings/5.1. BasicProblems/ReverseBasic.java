/*
 * PROBLEM: Reverse Words in a String (Coding Ninjas basic)
 *
 * DESCRIPTION:
 *   - Given a string s, reverse the order of words.
 *   - Words are separated by one or more spaces.
 *   - Leading/trailing spaces should be removed and words should be joined
 *     by a single space in the result.
 *
 * EXAMPLE:
 *   s = "  hello   world  " → "world hello"
 *
 * APPROACH:
 *   1. Trim the string to remove leading/trailing spaces.
 *   2. Split by one or more whitespace characters using regex "\\s+".
 *   3. Iterate the parts array from the end to the beginning and build the
 *      result with a StringBuilder, inserting a single space between words.
 *
 * TIME COMPLEXITY:  O(n)
 * SPACE COMPLEXITY: O(n) for the array and result
 */

public class ReverseBasic {

    public static String reverseString(String s) {
        String[] parts = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = parts.length - 1; i >= 0; i--) {
            sb.append(parts[i]);
            if (i > 0) sb.append(' ');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s1 = "  hello   world  ";
        String s2 = "a good   example";

        System.out.println("s1 → '" + reverseString(s1) + "'");
        System.out.println("s2 → '" + reverseString(s2) + "'");
    }
}
