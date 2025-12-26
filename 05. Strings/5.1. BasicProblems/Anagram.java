/*
 * PROBLEM: Anagram Pairs (Coding Ninjas)
 *
 * DESCRIPTION:
 *   - You are given two strings s and t.
 *   - Check whether t is an anagram of s.
 *   - Two strings are anagrams if they contain exactly the same characters
 *     with the same frequencies, possibly in a different order.
 *
 * EXAMPLE:
 *   s = "listen", t = "silent"   → true
 *   s = "rat",    t = "car"      → false
 *
 * APPROACH:
 *   1. If lengths differ, they cannot be anagrams → return false.
 *   2. Use two integer arrays of size 26 to count frequencies of each letter
 *      'a' to 'z' in s and t.
 *   3. Iterate through s and t:
 *        - For each character c in s, do countS[c - 'a']++.
 *        - For each character c in t, do countT[c - 'a']++.
 *   4. Compare the two frequency arrays:
 *        - If all 26 counts match, they are anagrams → return true.
 *        - Otherwise, return false.
 *
 * TIME COMPLEXITY:  O(n)  where n = length of the strings
 * SPACE COMPLEXITY: O(1)  (fixed-size arrays of length 26)
 */

public class Anagram {

    public static boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;
        int[] a = new int[26];
        int[] b = new int[26];
        for (char c : s.toCharArray()) a[c - 'a']++;
        for (char c : t.toCharArray()) b[c - 'a']++;
        for (int i = 0; i < 26; i++) {
            if (a[i] != b[i]) return false;
        }
        return true;
    }
    public static void main(String[] args) {
        // Basic test cases for isAnagram
        String s1 = "listen";
        String t1 = "silent";
        System.out.println("s = " + s1 + ", t = " + t1 + " → " + isAnagram(s1, t1));

        String s2 = "rat";
        String t2 = "car";
        System.out.println("s = " + s2 + ", t = " + t2 + " → " + isAnagram(s2, t2));

        String s3 = "aabbcc";
        String t3 = "abcabc";
        System.out.println("s = " + s3 + ", t = " + t3 + " → " + isAnagram(s3, t3));
    }
}
