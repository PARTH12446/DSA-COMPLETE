import java.util.*;

public class MaxChoc {

    // 3D DP: i (row), ai (col of Alice), bi (col of Bob)
    public static int maximumChocolates(int r, int c, int[][] grid) {
        int[][][] dp = new int[r][c][c];
        for (int i = 0; i < r; i++) {
            for (int a = 0; a < c; a++) {
                Arrays.fill(dp[i][a], Integer.MIN_VALUE);
            }
        }
        return functionCall(0, 0, c - 1, r, c, grid, dp);
    }

    private static int functionCall(int i, int ai, int bi, int r, int c,
                                   int[][] grid, int[][][] dp) {
        if (ai < 0 || bi < 0 || ai >= c || bi >= c) return (int) -1e8;
        if (i == r - 1) {
            if (ai == bi) return grid[i][ai];
            return grid[i][ai] + grid[i][bi];
        }
        if (dp[i][ai][bi] != Integer.MIN_VALUE) return dp[i][ai][bi];

        int maxi = (int) -1e8;
        for (int dai = -1; dai <= 1; dai++) {
            for (int dbi = -1; dbi <= 1; dbi++) {
                int curr;
                if (ai == bi) curr = grid[i][ai];
                else curr = grid[i][ai] + grid[i][bi];
                int next = functionCall(i + 1, ai + dai, bi + dbi, r, c, grid, dp);
                maxi = Math.max(maxi, curr + next);
            }
        }
        dp[i][ai][bi] = maxi;
        return maxi;
    }

    public static void main(String[] args) {
        int[][] grid = {
                {3,1,1},
                {2,5,1},
                {1,5,5},
                {2,1,1}
        };
        System.out.println("Maximum chocolates: " + maximumChocolates(grid.length, grid[0].length, grid));
    }
}
