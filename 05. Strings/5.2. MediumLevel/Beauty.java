/*
 * PROBLEM: Sum of Beauty of All Substrings (Coding Ninjas)
 *
 * DESCRIPTION:
 *   - For a string s, the beauty of a substring is defined as:
 *       max_frequency_of_any_char - min_frequency_of_any_char (among chars
 *       that appear in this substring).
 *   - Compute the sum of the beauty of all substrings of s.
 *
 * EXAMPLE:
 *   s = "aabcb" → answer = 5 (per LeetCode/CN examples)
 *
 * APPROACH:
 *   1. Fix a starting index i.
 *   2. For each ending index j >= i, maintain a frequency array for s[i..j].
 *   3. After each update, compute max and min positive frequencies and add
 *      (max - min) to the answer.
 *   4. Overall O(n^2 * 26) time, acceptable for small/medium strings.
 *
 * TIME COMPLEXITY:  O(n^2 * 26) ≈ O(n^2)
 * SPACE COMPLEXITY: O(26) = O(1) extra per start index
 */

public class Beauty {

    public static int sumOfBeauty(String s) {
        int ans = 0;
        int n = s.length();
        for (int i = 0; i < n; i++) {
            int[] freq = new int[26];
            for (int j = i; j < n; j++) {
                freq[s.charAt(j) - 'a']++;
                int max = 0;
                int min = Integer.MAX_VALUE;
                for (int f : freq) {
                    if (f > 0) {
                        if (f > max) max = f;
                        if (f < min) min = f;
                    }
                }
                if (min != Integer.MAX_VALUE) ans += max - min;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        String s1 = "aabcb";
        String s2 = "abc";

        System.out.println("s1 = " + s1 + " → sumOfBeauty = " + sumOfBeauty(s1));
        System.out.println("s2 = " + s2 + " → sumOfBeauty = " + sumOfBeauty(s2));
    }
}
