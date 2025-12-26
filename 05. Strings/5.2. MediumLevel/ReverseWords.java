/*
 * PROBLEM: Reverse Words (Coding Ninjas, 5.2)
 *
 * DESCRIPTION:
 *   - Given a string s, reverse the order of words.
 *   - This is similar to the basic version but in the Medium section.
 *
 * EXAMPLE:
 *   s = "  hello   world  " → "world hello"
 *
 * APPROACH:
 *   1. Trim the string to remove leading/trailing spaces.
 *   2. Split by one or more whitespace characters.
 *   3. Join the words in reverse order with a single space.
 *
 * TIME COMPLEXITY:  O(n)
 * SPACE COMPLEXITY: O(n)
 */

public class ReverseWords {

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
