import java.util.*;

public class CountSubsetSum {

    private static final int MOD = 1_000_000_007;

    // Count subsets with sum k, handling zeros as in Python solution
    public static int findWays(int[] arr, int k) {
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
            // only zeros: empty subset counts if k==0 else 0
            if (k == 0) {
                long ways = 1L << zeroCount; // 2^zeroCount
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
        long mul = 1L << zeroCount; // 2^zeroCount
        ways = (ways * mul) % MOD;
        return (int) ways;
    }

    public static void main(String[] args) {
        int[] arr = {1,2,0,3};
        int k = 3;
        System.out.println("Number of subsets with sum " + k + ": " + findWays(arr, k));
    }
}
