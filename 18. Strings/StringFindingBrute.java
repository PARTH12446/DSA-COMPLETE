public class StringFindingBrute {

    // Brute-force substring search, like Python findMatching
    public static int findMatching(String text, String pattern) {
        if (pattern.isEmpty()) return 0;
        if (pattern.length() > text.length()) return -1;

        int n = text.length();
        int m = pattern.length();
        int l = 0;
        while (l <= n - m) {
            int r = 0;
            while (r < m && text.charAt(l + r) == pattern.charAt(r)) {
                r++;
            }
            if (r == m) return l;
            l++;
        }
        return -1;
    }

    public static void main(String[] args) {
        String text = "hello world";
        String pattern = "world";
        System.out.println("First occurrence index: " + findMatching(text, pattern));
    }
}
