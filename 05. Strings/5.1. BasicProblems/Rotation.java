/*
 * PROBLEM: Check if One String is a Rotation of Another (Coding Ninjas)
 *
 * DESCRIPTION:
 *   - Given two strings A and B, check whether B is a cyclic rotation of A.
 *   - A rotation means we can take some prefix of A and move it to the end
 *     (or vice versa) to obtain B.
 *
 * EXAMPLE:
 *   A = "abcd", B = "cdab" → 1 (true)
 *   A = "abcd", B = "acbd" → 0 (false)
 *
 * APPROACH:
 *   1. If lengths of A and B differ, return 0.
 *   2. Concatenate A with itself: A + A.
 *   3. If B is a substring of (A + A), then B is a rotation of A.
 *
 * TIME COMPLEXITY:  O(n^2) worst (depending on substring search)
 * SPACE COMPLEXITY: O(n) for concatenated string
 */

public class Rotation {

    public static int isCyclicRotation(String A, String B) {
        return (A.length() == B.length() && (A + A).contains(B)) ? 1 : 0;
    }

    public static void main(String[] args) {
        String A1 = "abcd";
        String B1 = "cdab";

        String A2 = "abcd";
        String B2 = "acbd";

        System.out.println("A1 = " + A1 + ", B1 = " + B1 + " → " + isCyclicRotation(A1, B1));
        System.out.println("A2 = " + A2 + ", B2 = " + B2 + " → " + isCyclicRotation(A2, B2));
    }
}
