/*
 * PROBLEM: Roman Numeral to Integer (Coding Ninjas)
 *
 * DESCRIPTION:
 *   - Given a string representing a Roman numeral, convert it to an integer.
 *
 * EXAMPLE:
 *   "III" → 3
 *   "IV"  → 4
 *   "IX"  → 9
 *   "LVIII" → 58
 *   "MCMXCIV" → 1994
 *
 * APPROACH:
 *   1. Map each Roman symbol to its value.
 *   2. Traverse from right to left, starting with last symbol as current sum.
 *   3. If current symbol is less than symbol to its right, subtract it;
 *      otherwise, add it.
 *
 * TIME COMPLEXITY:  O(n)
 * SPACE COMPLEXITY: O(1)
 */

import java.util.*;

public class RomanNumerals {

    public static int romanToInt(String s) {
        Map<Character, Integer> d = new HashMap<>();
        d.put('I', 1);
        d.put('V', 5);
        d.put('X', 10);
        d.put('L', 50);
        d.put('C', 100);
        d.put('D', 500);
        d.put('M', 1000);
        int currval = d.get(s.charAt(s.length() - 1));
        for (int i = s.length() - 2; i >= 0; i--) {
            if (d.get(s.charAt(i)) < d.get(s.charAt(i + 1))) {
                currval -= d.get(s.charAt(i));
            } else {
                currval += d.get(s.charAt(i));
            }
        }
        return currval;
    }

    public static void main(String[] args) {
        String s1 = "III";
        String s2 = "IV";
        String s3 = "MCMXCIV";

        System.out.println(s1 + " → " + romanToInt(s1));
        System.out.println(s2 + " → " + romanToInt(s2));
        System.out.println(s3 + " → " + romanToInt(s3));
    }
}
