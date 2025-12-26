

public class SubsetSum {

    // Return true if there exists a subset of arr[0..n-1] with sum k
    public static boolean subsetSumToK(int n, int k, int[] arr) {
        if (n == 0) return false;
        boolean[][] dp = new boolean[n][k + 1];

        // target 0 is always achievable
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
        return dp[n - 1][k];
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4};
        int k = 5;
        System.out.println("Subset sum to " + k + " exists? " + subsetSumToK(arr.length, k, arr));
    }
}
