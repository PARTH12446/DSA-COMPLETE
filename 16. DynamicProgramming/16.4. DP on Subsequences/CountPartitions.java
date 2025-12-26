import java.util.*;

public class CountPartitions {

    private static final int MOD = 1_000_000_007;

    private static int findWays(int[] arr, int k) {
        int n = arr.length;
        if (n == 0) return 0;

        int zeroCount = 0;
        List<Integer> filtered = new ArrayList<>();
        for (int x : arr) {
            if (x == 0) zeroCount++;
            else filtered.add(x);
        }
        n = filtered.size();
        if (n == 0) {
            if (k == 0) {
                long ways = 1L << zeroCount;
                return (int) (ways % MOD);
            }
            return 0;
        }

        int[][] dp = new int[n][k + 1];
        for (int i = 0; i < n; i++) dp[i][0] = 1;
        int first = filtered.get(0);
        if (first <= k) dp[0][first] = 1;

        for (int index = 1; index < n; index++) {
            int val = filtered.get(index);
            for (int target = 1; target <= k; target++) {
                int notTake = dp[index - 1][target];
                int take = 0;
                if (val <= target) take = dp[index - 1][target - val];
                dp[index][target] = (take + notTake) % MOD;
            }
        }
        long ways = dp[n - 1][k];
        long mul = 1L << zeroCount;
        ways = (ways * mul) % MOD;
        return (int) ways;
    }

    // Count partitions with given difference d
    public static int countPartitions(int n, int d, int[] arr) {
        long total = 0;
        for (int x : arr) total += x;
        if ((total - d) < 0 || ((total - d) & 1L) == 1L) return 0;
        int s2 = (int) ((total - d) / 2);
        return findWays(arr, s2);
    }

    public static void main(String[] args) {
        int[] arr = {1,1,2,3};
        int d = 1;
        System.out.println("Count partitions with diff " + d + ": " + countPartitions(arr.length, d, arr));
    }
}
