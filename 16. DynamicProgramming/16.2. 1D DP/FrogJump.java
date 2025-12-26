

public class FrogJump {

    // Min cost to reach last stone with jumps of 1 or 2
    public static int frogJump(int n, int[] heights) {
        if (n == 1) return 0;
        int[] dp = new int[n];
        dp[0] = 0;
        dp[1] = Math.abs(heights[1] - heights[0]);
        for (int i = 2; i < n; i++) {
            int one = dp[i - 1] + Math.abs(heights[i] - heights[i - 1]);
            int two = dp[i - 2] + Math.abs(heights[i] - heights[i - 2]);
            dp[i] = Math.min(one, two);
        }
        return dp[n - 1];
    }

    public static void main(String[] args) {
        int[] heights = {10, 20, 30, 10};
        System.out.println("Frog min energy: " + frogJump(heights.length, heights));
    }
}
