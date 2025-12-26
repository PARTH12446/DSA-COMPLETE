

public class ZFunction {

    public static int[] calculateZ(String s) {
        int n = s.length();
        int[] z = new int[n];
        int l = 0, r = 0;
        for (int i = 1; i < n; i++) {
            if (i > r) {
                l = r = i;
                while (r < n && s.charAt(r) == s.charAt(r - l)) r++;
                z[i] = r - l;
                r--;
            } else {
                int k = i - l;
                if (z[k] < r - i + 1) {
                    z[i] = z[k];
                } else {
                    l = i;
                    while (r < n && s.charAt(r) == s.charAt(r - l)) r++;
                    z[i] = r - l;
                    r--;
                }
            }
        }
        return z;
    }

    public static int strStr(String haystack, String needle) {
        if (needle.isEmpty()) return 0;
        String combined = needle + "$" + haystack;
        int[] z = calculateZ(combined);
        int patternLen = needle.length();
        for (int i = 0; i < z.length; i++) {
            if (z[i] == patternLen) return i - patternLen - 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println("Index: " + strStr("hello", "ll"));
    }
}
