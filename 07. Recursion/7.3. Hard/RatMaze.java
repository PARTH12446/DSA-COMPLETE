import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Rat in a Maze Problem
 * 
 * Problem: Given an N×N maze where 1 represents an open cell and 0 represents 
 * a blocked cell, find all paths from the top-left (0,0) to bottom-right (N-1,N-1).
 * The rat can move in 4 directions: Down(D), Left(L), Right(R), Up(U).
 * 
 * Variations:
 * 1. Find all paths
 * 2. Find shortest path (lexicographically first)
 * 3. Count number of paths
 * 4. Find paths with obstacles
 * 5. Multiple exits
 * 
 * Applications:
 * - Robotics path planning
 * - GPS navigation
 * - Game AI
 * - Network routing
 */
public class RatMaze {

    // Direction vectors: Down, Left, Right, Up (sorted for lexicographical order)
    private static final int[][] DIRS = {{1,0},{0,-1},{0,1},{-1,0}};
    private static final char[] DIR_CHARS = {'D','L','R','U'};
    
    /**
     * Main method to find all paths from (0,0) to (n-1,n-1).
     * 
     * @param maze N×N matrix where 1 = open cell, 0 = blocked cell
     * @return List of all possible paths as strings of direction characters
     * 
     * Time Complexity: O(4^(n²)) in worst case (exponential)
     * Space Complexity: O(n²) for visited matrix + O(n²) recursion stack
     */
    public static List<String> findPaths(int[][] maze) {
        List<String> res = new ArrayList<>();
        
        // Validate inputs
        if (maze == null || maze.length == 0) {
            return res;
        }
        
        int n = maze.length;
        
        // Check if start or end is blocked
        if (maze[0][0] == 0 || maze[n-1][n-1] == 0) {
            return res;
        }
        
        boolean[][] vis = new boolean[n][n];
        backtrack(0, 0, maze, vis, new StringBuilder(), res);
        
        // Sort results lexicographically
        Collections.sort(res);
        return res;
    }

    /**
     * Recursive backtracking function using DFS.
     * 
     * The algorithm explores all 4 directions from each cell.
     * The order of directions (D,L,R,U) ensures lexicographically sorted paths.
     * 
     * @param r Current row
     * @param c Current column
     * @param maze Original maze
     * @param vis Visited cells to avoid cycles
     * @param path Current path being built
     * @param res Result list
     */
    private static void backtrack(int r, int c, int[][] maze, boolean[][] vis, 
                                  StringBuilder path, List<String> res) {
        int n = maze.length;
        
        // Base case: reached destination
        if (r == n - 1 && c == n - 1) {
            res.add(path.toString());
            return;
        }
        
        // Mark current cell as visited
        vis[r][c] = true;
        
        // Explore all 4 directions in order: D, L, R, U
        for (int k = 0; k < 4; k++) {
            int nr = r + DIRS[k][0];
            int nc = c + DIRS[k][1];
            
            // Check if new position is valid
            if (isValid(nr, nc, n) && maze[nr][nc] == 1 && !vis[nr][nc]) {
                // Append direction to path
                path.append(DIR_CHARS[k]);
                
                // Recurse to next cell
                backtrack(nr, nc, maze, vis, path, res);
                
                // Backtrack: remove last direction
                path.deleteCharAt(path.length() - 1);
            }
        }
        
        // Unmark current cell for other paths
        vis[r][c] = false;
    }
    
    /**
     * Check if position is within maze boundaries.
     */
    private static boolean isValid(int r, int c, int n) {
        return r >= 0 && r < n && c >= 0 && c < n;
    }

    /**
     * Alternative: Find shortest path using BFS (guaranteed shortest length).
     * BFS finds shortest path but only returns one path.
     * 
     * @param maze N×N maze
     * @return Shortest path as string, or empty string if no path exists
     */
    public static String findShortestPathBFS(int[][] maze) {
        if (maze == null || maze.length == 0) return "";
        
        int n = maze.length;
        if (maze[0][0] == 0 || maze[n-1][n-1] == 0) return "";
        
        // BFS queue stores (row, col, path)
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];
        
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        
        // Store parent information to reconstruct path
        int[][][] parent = new int[n][n][3]; // parent[row][col] = {parentRow, parentCol, direction}
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                parent[i][j] = new int[]{-1, -1, -1};
            }
        }
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int r = curr[0];
            int c = curr[1];
            
            // Check if reached destination
            if (r == n-1 && c == n-1) {
                return reconstructPath(parent);
            }
            
            // Explore neighbors
            for (int k = 0; k < 4; k++) {
                int nr = r + DIRS[k][0];
                int nc = c + DIRS[k][1];
                
                if (isValid(nr, nc, n) && maze[nr][nc] == 1 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    parent[nr][nc] = new int[]{r, c, k};
                    queue.offer(new int[]{nr, nc});
                }
            }
        }
        
        return ""; // No path found
    }
    
    private static String reconstructPath(int[][][] parent) {
        StringBuilder path = new StringBuilder();
        int n = parent.length;
        int r = n-1, c = n-1;
        
        // Backtrack from destination to source
        while (r != 0 || c != 0) {
            int[] p = parent[r][c];
            int dir = p[2];
            path.append(DIR_CHARS[dir]);
            r = p[0];
            c = p[1];
        }
        
        return path.reverse().toString(); // Reverse to get source→destination order
    }

    /**
     * Count number of paths using DP (more efficient for counting).
     * 
     * @param maze N×N maze
     * @return Number of distinct paths from (0,0) to (n-1,n-1)
     */
    public static int countPathsDP(int[][] maze) {
        if (maze == null || maze.length == 0) return 0;
        
        int n = maze.length;
        if (maze[0][0] == 0 || maze[n-1][n-1] == 0) return 0;
        
        int[][] dp = new int[n][n];
        dp[0][0] = 1;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (maze[i][j] == 1) {
                    if (i > 0 && maze[i-1][j] == 1) {
                        dp[i][j] += dp[i-1][j]; // From up
                    }
                    if (j > 0 && maze[i][j-1] == 1) {
                        dp[i][j] += dp[i][j-1]; // From left
                    }
                }
            }
        }
        
        return dp[n-1][n-1];
    }

    /**
     * Find all shortest paths (multiple paths with same minimum length).
     * 
     * @param maze N×N maze
     * @return List of all shortest paths
     */
    public static List<String> findAllShortestPaths(int[][] maze) {
        List<String> allPaths = findPaths(maze);
        if (allPaths.isEmpty()) return allPaths;
        
        // Find minimum path length
        int minLength = Integer.MAX_VALUE;
        for (String path : allPaths) {
            minLength = Math.min(minLength, path.length());
        }
        
        // Collect paths with minimum length
        List<String> shortestPaths = new ArrayList<>();
        for (String path : allPaths) {
            if (path.length() == minLength) {
                shortestPaths.add(path);
            }
        }
        
        return shortestPaths;
    }

    /**
     * Find path with minimum turns (prefer straight paths).
     * Counts direction changes as penalty.
     * 
     * @param maze N×N maze
     * @return Path with minimum turns
     */
    public static String findPathWithMinTurns(int[][] maze) {
        List<String> allPaths = findPaths(maze);
        if (allPaths.isEmpty()) return "";
        
        return allPaths.stream()
                .min(Comparator.comparingInt(RatMaze::countTurns))
                .orElse("");
    }
    
    private static int countTurns(String path) {
        if (path.length() <= 1) return 0;
        
        int turns = 0;
        for (int i = 1; i < path.length(); i++) {
            if (path.charAt(i) != path.charAt(i-1)) {
                turns++;
            }
        }
        return turns;
    }

    /**
     * Check if path is valid for given maze.
     * 
     * @param maze N×N maze
     * @param path Path string of direction characters
     * @return true if path is valid from start to end
     */
    public static boolean isValidPath(int[][] maze, String path) {
        if (maze == null || maze.length == 0 || path == null) return false;
        
        int n = maze.length;
        if (maze[0][0] == 0 || maze[n-1][n-1] == 0) return false;
        
        int r = 0, c = 0;
        
        for (char dir : path.toCharArray()) {
            int k = getDirectionIndex(dir);
            if (k == -1) return false; // Invalid direction character
            
            int nr = r + DIRS[k][0];
            int nc = c + DIRS[k][1];
            
            if (!isValid(nr, nc, n) || maze[nr][nc] == 0) {
                return false; // Out of bounds or blocked
            }
            
            r = nr;
            c = nc;
        }
        
        // Check if reached destination
        return r == n-1 && c == n-1;
    }
    
    private static int getDirectionIndex(char dir) {
        for (int i = 0; i < DIR_CHARS.length; i++) {
            if (DIR_CHARS[i] == dir) return i;
        }
        return -1;
    }

    /**
     * Print maze with path visualization.
     */
    public static void printMazeWithPath(int[][] maze, String path) {
        if (maze == null || maze.length == 0) return;
        
        int n = maze.length;
        char[][] display = new char[n][n];
        
        // Initialize display
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                display[i][j] = maze[i][j] == 1 ? '.' : '#';
            }
        }
        
        // Mark path
        int r = 0, c = 0;
        display[r][c] = 'S'; // Start
        
        for (char dir : path.toCharArray()) {
            int k = getDirectionIndex(dir);
            r += DIRS[k][0];
            c += DIRS[k][1];
            display[r][c] = '*';
        }
        
        display[r][c] = 'E'; // End (might overwrite last *)
        
        // Print maze
        System.out.println("Maze with path (S=Start, E=End, *=Path, .=Open, #=Blocked):");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(display[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Comprehensive test driver.
     */
    public static void main(String[] args) {
        System.out.println("=== RAT IN A MAZE DEMO ===\n");
        
        testBasicMaze();
        testImpossibleMaze();
        testMultiplePaths();
        testPerformance();
        testPathValidation();
        testShortestPath();
    }
    
    private static void testBasicMaze() {
        System.out.println("1. BASIC MAZE (2×2)");
        int[][] maze = {
            {1, 1},
            {1, 1}
        };
        
        List<String> paths = findPaths(maze);
        System.out.println("Maze:");
        printMaze(maze);
        System.out.println("All paths: " + paths);
        System.out.println("Number of paths: " + paths.size());
        System.out.println("Shortest path (BFS): " + findShortestPathBFS(maze));
        System.out.println("Number of paths (DP): " + countPathsDP(maze));
        System.out.println();
    }
    
    private static void testImpossibleMaze() {
        System.out.println("2. IMPOSSIBLE MAZE (Blocked destination)");
        int[][] maze = {
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 0}  // Destination blocked
        };
        
        List<String> paths = findPaths(maze);
        System.out.println("All paths: " + paths);
        System.out.println("Shortest path (BFS): '" + findShortestPathBFS(maze) + "'");
        System.out.println("Number of paths (DP): " + countPathsDP(maze));
        System.out.println();
    }
    
    private static void testMultiplePaths() {
        System.out.println("3. MAZE WITH MULTIPLE PATHS (4×4)");
        int[][] maze = {
            {1, 0, 0, 0},
            {1, 1, 0, 1},
            {0, 1, 0, 0},
            {1, 1, 1, 1}
        };
        
        List<String> paths = findPaths(maze);
        System.out.println("Maze:");
        printMaze(maze);
        System.out.println("Number of paths: " + paths.size());
        System.out.println("All paths: " + paths);
        
        // Find and visualize shortest path
        String shortest = findShortestPathBFS(maze);
        System.out.println("Shortest path: " + shortest);
        System.out.println("Length: " + shortest.length());
        printMazeWithPath(maze, shortest);
        
        // Find all shortest paths
        List<String> allShortest = findAllShortestPaths(maze);
        System.out.println("All shortest paths: " + allShortest);
        System.out.println();
    }
    
    private static void testPerformance() {
        System.out.println("4. PERFORMANCE COMPARISON");
        
        // Create a 5×5 open maze (worst case for DFS)
        int n = 5;
        int[][] maze = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(maze[i], 1);
        }
        
        System.out.println("Testing on " + n + "×" + n + " open maze");
        
        // DFS approach
        long start = System.nanoTime();
        List<String> dfsPaths = findPaths(maze);
        long dfsTime = System.nanoTime() - start;
        
        // BFS approach (single path)
        start = System.nanoTime();
        String bfsPath = findShortestPathBFS(maze);
        long bfsTime = System.nanoTime() - start;
        
        // DP approach (count only)
        start = System.nanoTime();
        int dpCount = countPathsDP(maze);
        long dpTime = System.nanoTime() - start;
        
        System.out.println("DFS found " + dfsPaths.size() + " paths in " + 
                         (dfsTime/1000000.0) + " ms");
        System.out.println("BFS found shortest path in " + 
                         (bfsTime/1000000.0) + " ms");
        System.out.println("DP counted " + dpCount + " paths in " + 
                         (dpTime/1000000.0) + " ms");
        System.out.println();
    }
    
    private static void testPathValidation() {
        System.out.println("5. PATH VALIDATION TEST");
        
        int[][] maze = {
            {1, 0, 0},
            {1, 1, 0},
            {0, 1, 1}
        };
        
        String validPath = "DDRR";   // Down, Down, Right, Right
        String invalidPath1 = "RRDD"; // Right, Right, Down, Down (blocked)
        String invalidPath2 = "DDUU"; // Contains illegal moves
        
        System.out.println("Maze:");
        printMaze(maze);
        System.out.println("Path '" + validPath + "' is valid: " + 
                         isValidPath(maze, validPath));
        System.out.println("Path '" + invalidPath1 + "' is valid: " + 
                         isValidPath(maze, invalidPath1));
        System.out.println("Path '" + invalidPath2 + "' is valid: " + 
                         isValidPath(maze, invalidPath2));
        System.out.println();
    }
    
    private static void testShortestPath() {
        System.out.println("6. SHORTEST PATH WITH MINIMUM TURNS");
        
        int[][] maze = {
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
        };
        
        List<String> allPaths = findPaths(maze);
        System.out.println("Total paths: " + allPaths.size());
        
        // Find shortest paths
        List<String> shortestPaths = findAllShortestPaths(maze);
        System.out.println("Shortest paths (length " + 
                         (shortestPaths.isEmpty() ? 0 : shortestPaths.get(0).length()) + 
                         "): " + shortestPaths.size());
        
        // Find path with minimum turns
        String minTurnsPath = findPathWithMinTurns(maze);
        System.out.println("Path with minimum turns: " + minTurnsPath);
        System.out.println("Number of turns: " + countTurns(minTurnsPath));
        System.out.println();
    }
    
    /**
     * Utility method to print maze.
     */
    private static void printMaze(int[][] maze) {
        for (int[] row : maze) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Advanced: Maze with multiple exits (find path to any exit).
     */
    public static List<String> findPathsToAnyExit(int[][] maze, int[][] exits) {
        List<String> result = new ArrayList<>();
        if (maze == null || maze.length == 0) return result;
        
        int n = maze.length;
        boolean[][] visited = new boolean[n][n];
        backtrackToAnyExit(0, 0, maze, exits, visited, new StringBuilder(), result);
        return result;
    }
    
    private static void backtrackToAnyExit(int r, int c, int[][] maze, 
                                          int[][] exits, boolean[][] visited,
                                          StringBuilder path, List<String> result) {
        int n = maze.length;
        
        // Check if current position is an exit
        for (int[] exit : exits) {
            if (r == exit[0] && c == exit[1]) {
                result.add(path.toString());
                // Don't return here to allow finding multiple paths
            }
        }
        
        visited[r][c] = true;
        
        for (int k = 0; k < 4; k++) {
            int nr = r + DIRS[k][0];
            int nc = c + DIRS[k][1];
            
            if (isValid(nr, nc, n) && maze[nr][nc] == 1 && !visited[nr][nc]) {
                path.append(DIR_CHARS[k]);
                backtrackToAnyExit(nr, nc, maze, exits, visited, path, result);
                path.deleteCharAt(path.length() - 1);
            }
        }
        
        visited[r][c] = false;
    }
}