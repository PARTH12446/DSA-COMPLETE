public class CountSubsequences {

    // Count palindromic subsequences using DP
    public static long countPS(String s) {
        int n = s.length();
        long[][] t = new long[n][n];
        for (int i = 0; i < n; i++) t[i][i] = 1;
        for (int len = 2; len <= n; len++) {
            for (int i = 0; i <= n - len; i++) {
                int j = i + len - 1;
                if (s.charAt(i) == s.charAt(j)) {
                    t[i][j] = 1 + t[i + 1][j] + t[i][j - 1];
                } else {
                    t[i][j] = t[i + 1][j] + t[i][j - 1] - t[i + 1][j - 1];
                }
            }
        }
        return t[0][n - 1];
    }

    public static void main(String[] args) {
        String s = "abcb";
        System.out.println("Palindromic subsequences: " + countPS(s));
    }
}
