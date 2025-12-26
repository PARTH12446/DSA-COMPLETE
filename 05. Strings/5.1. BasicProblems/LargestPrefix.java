/*
 * PROBLEM: Longest Common Prefix (Coding Ninjas)
 *
 * DESCRIPTION:
 *   - Given an array of strings inp with length n, find the longest
 *     string that is a prefix of all the strings.
 *   - If no common prefix exists, return "-1".
 *
 * EXAMPLE:
 *   inp = ["flower", "flow", "flight"] → "fl"
 *   inp = ["dog", "racecar", "car"]   → "-1" (no common prefix)
 *
 * APPROACH:
 *   1. Find the minimum length among all strings (mini).
 *   2. For each position i from 0 to mini - 1:
 *        - Take the character from inp[0].
 *        - Check if all other strings have the same character at i.
 *        - If any mismatch, return the prefix built so far or "-1".
 *   3. If loop completes, the entire mini-length prefix is common.
 *
 * TIME COMPLEXITY:  O(n * L)  where n = number of strings, L = min length
 * SPACE COMPLEXITY: O(1) extra (besides output)
 */

public class LargestPrefix {

    public static String commonPrefix(String[] inp, int n) {
        String ans = "";
        int mini = Integer.MAX_VALUE;
        for (String s : inp) {
            mini = Math.min(mini, s.length());
        }
        for (int i = 0; i < mini; i++) {
            char mainchar = inp[0].charAt(i);
            boolean ok = true;
            for (int j = 1; j < n; j++) {
                if (inp[j].charAt(i) != mainchar) {
                    ok = false;
                    break;
                }
            }
            if (ok) ans += mainchar;
            else return ans.isEmpty() ? "-1" : ans;
        }
        return ans.isEmpty() ? "-1" : ans;
    }

    public static void main(String[] args) {
        String[] arr1 = {"flower", "flow", "flight"};
        String[] arr2 = {"dog", "racecar", "car"};

        System.out.println("Common prefix arr1 → " + commonPrefix(arr1, arr1.length));
        System.out.println("Common prefix arr2 → " + commonPrefix(arr2, arr2.length));
    }
}
