public class KMP_LPS {

    private static int[] lpsMaker(String pattern) {
        int[] lps = new int[pattern.length()];
        int prevLPS = 0, i = 1;
        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(prevLPS)) {
                lps[i] = prevLPS + 1;
                prevLPS++;
                i++;
            } else if (prevLPS == 0) {
                lps[i] = 0;
                i++;
            } else {
                prevLPS = lps[prevLPS - 1];
            }
        }
        return lps;
    }

    private static int kmp(String pattern, String text, int[] lps) {
        int l = 0, r = 0;
        while (l < text.length()) {
            if (text.charAt(l) == pattern.charAt(r)) {
                l++;
                r++;
            } else {
                if (r == 0) {
                    l++;
                } else {
                    r = lps[r - 1];
                }
            }
            if (r == pattern.length()) {
                return l - pattern.length();
            }
        }
        return -1;
    }

    public static int strStr(String haystack, String needle) {
        if (needle.isEmpty()) return 0;
        int[] lps = lpsMaker(needle);
        return kmp(needle, haystack, lps);
    }

    public static void main(String[] args) {
        System.out.println("Index: " + strStr("sadbutsad", "sad"));
    }
}
