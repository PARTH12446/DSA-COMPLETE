/*
 * PROBLEM: Count Substrings with Exactly K Different Characters (Coding Ninjas)
 *
 * DESCRIPTION:
 *   - Given a string s and an integer k, count the number of substrings that
 *     contain exactly k distinct characters.
 *
 * EXAMPLE:
 *   s = "pqpqs", k = 2 → 7
 *
 * APPROACH (atMost trick):
 *   1. Define a helper atMost(s, k) which counts substrings with at most k
 *      distinct characters using a sliding window + hashmap.
 *   2. Then answer = atMost(s, k) - atMost(s, k - 1).
 *
 * TIME COMPLEXITY:  O(n) average with sliding window
 * SPACE COMPLEXITY: O(k) for hashmap
 */

import java.util.*;

public class SubarraysWithKCount {

    private static int atMost(String s, int k) {
        if (s == null || s.isEmpty() || k <= 0) return 0;
        Map<Character, Integer> charCount = new HashMap<>();
        int num = 0;
        int left = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            charCount.put(c, charCount.getOrDefault(c, 0) + 1);
            while (charCount.size() > k) {
                char lc = s.charAt(left);
                charCount.put(lc, charCount.get(lc) - 1);
                if (charCount.get(lc) == 0) charCount.remove(lc);
                left++;
            }
            num += i - left + 1;
        }
        return num;
    }

    public static int countSubStrings(String s, int k) {
        return atMost(s, k) - atMost(s, k - 1);
    }

    public static void main(String[] args) {
        String s1 = "pqpqs";
        int k1 = 2;

        String s2 = "aabc";
        int k2 = 2;

        System.out.println("s1 = " + s1 + ", k1 = " + k1 + " → " + countSubStrings(s1, k1));
        System.out.println("s2 = " + s2 + ", k2 = " + k2 + " → " + countSubStrings(s2, k2));
    }
}
