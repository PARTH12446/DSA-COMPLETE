import java.util.*;

class AssignCookiesDP {

    // Greedy version
    public static int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int children = g.length;
        int gptr = 0;
        for (int cookie : s) {
            if (gptr < children && cookie >= g[gptr]) {
                gptr++;
            }
        }
        return gptr;
    }

    // DP version: dp[i][j] = max content children using first i kids and j cookies
    public static int findContentChildrenDP(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int n = g.length;
        int m = s.length;
        int[][] dp = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = dp[i][j - 1]; // skip j-th cookie
                if (s[j - 1] >= g[i - 1]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + 1);
                }
            }
        }
        return dp[n][m];
    }

    public static void main(String[] args) {
        int[] g = {1,2,3};
        int[] s = {1,1};
        System.out.println("Content children (greedy): " + findContentChildren(g, s));
        System.out.println("Content children (DP): " + findContentChildrenDP(g, s));
    }
}
