/*
 * PROBLEM: Maximum Nesting Depth of the Parentheses (Coding Ninjas)
 *
 * DESCRIPTION:
 *   - Given a valid parentheses string s consisting of '(' and ')',
 *     return the maximum nesting depth.
 *   - Nesting depth is the maximum number of '(' that are open at any
 *     position in the string.
 *
 * EXAMPLE:
 *   s = "(1+(2*3)+((8)/4))+1"   → max depth = 3
 *   s = "(())"                  → max depth = 2
 *   s = "()"                    → max depth = 1
 *
 * APPROACH:
 *   1. Traverse the string character by character.
 *   2. Maintain a current depth counter 'depth' and an answer 'ans'.
 *   3. When we see '(', increment depth and update ans = max(ans, depth).
 *   4. When we see ')', decrement depth.
 *   5. Ignore all other characters.
 *
 * TIME COMPLEXITY:  O(n)  where n = length of s
 * SPACE COMPLEXITY: O(1)
 */

public class Depth {

    public static int maxDepth(String s) {
        int depth = 0;
        int ans = 0;
        for (char c : s.toCharArray()) {
            if (c == '(') {
                depth++;
                ans = Math.max(ans, depth);
            } else if (c == ')') {
                depth--;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String s1 = "(1+(2*3)+((8)/4))+1";
        String s2 = "(())";
        String s3 = "()";

        System.out.println("s = " + s1 + " → maxDepth = " + maxDepth(s1));
        System.out.println("s = " + s2 + " → maxDepth = " + maxDepth(s2));
        System.out.println("s = " + s3 + " → maxDepth = " + maxDepth(s3));
    }
}
