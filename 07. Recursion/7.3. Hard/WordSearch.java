public class WordSearch {

    private static final int[][] DIRS = {{1,0},{-1,0},{0,1},{0,-1}};

    public static boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        boolean[][] vis = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, word, 0, i, j, vis)) return true;
            }
        }
        return false;
    }

    private static boolean dfs(char[][] board, String word, int idx, int r, int c, boolean[][] vis) {
        if (idx == word.length()) return true;
        int m = board.length, n = board[0].length;
        if (r < 0 || c < 0 || r >= m || c >= n || vis[r][c] || board[r][c] != word.charAt(idx)) return false;
        vis[r][c] = true;
        for (int[] d : DIRS) {
            if (dfs(board, word, idx + 1, r + d[0], c + d[1], vis)) {
                vis[r][c] = false;
                return true;
            }
        }
        vis[r][c] = false;
        return false;
    }
    public static void main(String[] args) {
        
    }
}
