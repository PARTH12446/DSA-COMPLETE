import java.util.*;

public class DistinctIslands {

    // Count distinct island shapes using DFS path encoding
    public static int distinctIsland(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        Set<String> shapes = new HashSet<>();
        List<String> path = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    dfs(grid, i, j, 0, m, n, path);
                    shapes.add(String.join(",", path));
                    path.clear();
                }
            }
        }
        return shapes.size();
    }

    private static void dfs(int[][] grid, int i, int j, int move, int m, int n, List<String> path) {
        grid[i][j] = 0;
        path.add(String.valueOf(move));
        int[] dirs = {-1, 0, 1, 0, -1};
        for (int h = 0; h < 4; h++) {
            int x = i + dirs[h];
            int y = j + dirs[h + 1];
            if (x >= 0 && x < m && y >= 0 && y < n && grid[x][y] == 1) {
                dfs(grid, x, y, h + 1, m, n, path);
            }
        }
        path.add(String.valueOf(-move));
    }

    public static void main(String[] args) {
        int[][] grid = {
                {1,1,0,0},
                {1,0,0,0},
                {0,0,1,1},
                {0,0,1,0}
        };
        System.out.println("Distinct islands: " + distinctIsland(grid));
    }
}
