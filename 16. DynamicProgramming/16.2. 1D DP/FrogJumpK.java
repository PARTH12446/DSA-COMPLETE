

public class FrogJumpK {

    // Min cost to reach last stone with jumps up to distance k
    public static int frogJump(int[] heights, int k) {
        int n = heights.length;
        int[] dp = new int[n];
        dp[0] = 0;
        for (int i = 1; i < n; i++) {
            int minCost = Integer.MAX_VALUE;
            for (int j = 1; j <= Math.min(k, i); j++) {
                int cost = dp[i - j] + Math.abs(heights[i] - heights[i - j]);
                minCost = Math.min(minCost, cost);
            }
            dp[i] = minCost;
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        int[] heights = {10, 30, 40, 50, 20};
        int k = 3;
        System.out.println("Frog min energy with k jumps: " + frogJump(heights, k));
    }
}
