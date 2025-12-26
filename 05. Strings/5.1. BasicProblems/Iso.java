/*
 * PROBLEM: Isomorphic Strings (Coding Ninjas)
 *
 * DESCRIPTION:
 *   - Two strings s and t are isomorphic if the characters in s can be
 *     replaced to get t, with:
 *       - A one-to-one mapping from s's characters to t's characters.
 *       - No two different characters in s mapping to the same character in t.
 *       - Mapping is consistent across all positions.
 *
 * EXAMPLE:
 *   s = "egg", t = "add"   → true  (e→a, g→d)
 *   s = "foo", t = "bar"   → false (o would have to map to both 'a' and 'r')
 *   s = "paper", t = "title" → true  (p→t, a→i, e→l, r→e)
 *
 * APPROACH:
 *   1. If lengths differ, return false.
 *   2. Use two hash maps:
 *        - s2t: map from characters of s to characters of t.
 *        - t2s: map from characters of t to characters of s.
 *   3. Iterate positions i from 0 to n-1:
 *        - Let cs = s.charAt(i), ct = t.charAt(i).
 *        - If cs is already in s2t but mapped value != ct → false.
 *        - If ct is already in t2s but mapped value != cs → false.
 *        - Otherwise, add/update both mappings.
 *   4. If we never violate consistency, return true.
 *
 * TIME COMPLEXITY:  O(n)
 * SPACE COMPLEXITY: O(Σ) for maps (bounded by character set size)
 */

import java.util.HashMap;
import java.util.Map;

public class Iso {

    public static boolean areIsomorphic(String s, String t) {
        if (s.length() != t.length()) return false;
        Map<Character, Character> s2t = new HashMap<>();
        Map<Character, Character> t2s = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char cs = s.charAt(i);
            char ct = t.charAt(i);
            if (s2t.containsKey(cs) && s2t.get(cs) != ct) return false;
            if (t2s.containsKey(ct) && t2s.get(ct) != cs) return false;
            s2t.put(cs, ct);
            t2s.put(ct, cs);
        }
        return true;
    }

    public static void main(String[] args) {
        String s1 = "egg";
        String t1 = "add";

        String s2 = "foo";
        String t2 = "bar";

        String s3 = "paper";
        String t3 = "title";

        System.out.println("s = " + s1 + ", t = " + t1 + " → " + areIsomorphic(s1, t1));
        System.out.println("s = " + s2 + ", t = " + t2 + " → " + areIsomorphic(s2, t2));
        System.out.println("s = " + s3 + ", t = " + t3 + " → " + areIsomorphic(s3, t3));
    }
}
