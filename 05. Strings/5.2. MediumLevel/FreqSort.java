/*
 * PROBLEM: Sorting Characters by Frequency (Coding Ninjas)
 *
 * DESCRIPTION:
 *   - Given a string s of length n, sort its characters in non-decreasing
 *     order of frequency (least frequent characters first).
 *   - For equal frequency, original order between different characters is
 *     determined by the sort implementation here.
 *
 * EXAMPLE:
 *   s = "tree" → frequencies: t:1, r:1, e:2 → result could be "tr ee" → "tree" or "rtee".
 *
 * APPROACH:
 *   1. Count frequency of each character using a HashMap.
 *   2. Move entries to a list and sort by frequency ascending.
 *   3. Build the answer by repeating each character its frequency times.
 *
 * TIME COMPLEXITY:  O(n log m) where m = distinct chars
 * SPACE COMPLEXITY: O(m)
 */

import java.util.*;

public class FreqSort {

    public static String sortByFrequency(int n, String s) {
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : s.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        List<Map.Entry<Character, Integer>> arr = new ArrayList<>(freq.entrySet());
        arr.sort(Comparator.comparingInt(Map.Entry::getValue));
        StringBuilder ans = new StringBuilder();
        for (Map.Entry<Character, Integer> e : arr) {
            for (int i = 0; i < e.getValue(); i++) ans.append(e.getKey());
        }
        return ans.toString();
    }

    public static void main(String[] args) {
        String s1 = "tree";
        String s2 = "cccaaa";

        System.out.println("s1 sorted by frequency → " + sortByFrequency(s1.length(), s1));
        System.out.println("s2 sorted by frequency → " + sortByFrequency(s2.length(), s2));
    }
}
