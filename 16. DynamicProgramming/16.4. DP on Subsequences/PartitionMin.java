

public class PartitionMin {

    // Min subset sum difference
    public static int minSubsetSumDifference(int[] arr) {
        int n = arr.length;
        int sum = 0;
        for (int x : arr) sum += x;
        boolean[] lastRow = subsetSumToKRow(n, sum, arr);
        int ans = Integer.MAX_VALUE;
        for (int s1 = 0; s1 <= sum / 2; s1++) {
            if (lastRow[s1]) {
                ans = Math.min(ans, Math.abs(2 * s1 - sum));
            }
        }
        return ans;
    }

    // Returns last row dp[n-1][*] from subset sum DP
    private static boolean[] subsetSumToKRow(int n, int k, int[] arr) {
        if (n == 0) return new boolean[k + 1];
        boolean[][] dp = new boolean[n][k + 1];
        for (int i = 0; i < n; i++) dp[i][0] = true;
        if (arr[0] <= k) dp[0][arr[0]] = true;

        for (int index = 1; index < n; index++) {
            for (int target = 1; target <= k; target++) {
                boolean notTake = dp[index - 1][target];
                boolean take = false;
                if (arr[index] <= target) {
                    take = dp[index - 1][target - arr[index]];
                }
                dp[index][target] = take || notTake;
            }
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        System.out.println("Min subset sum difference: " + minSubsetSumDifference(arr));
    }
}
