import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * M-Coloring Problem (Graph Coloring)
 * 
 * Problem: Given an undirected graph and an integer m, determine if the graph
 * can be colored with at most m colors such that no two adjacent vertices
 * share the same color.
 * 
 * This is a classic NP-complete problem with applications in:
 * - Map coloring (Four Color Theorem)
 * - Scheduling problems
 * - Register allocation in compilers
 * - Sudoku puzzles
 * - Frequency assignment in wireless networks
 */
public class MColoring {

    /**
     * Main method to check if graph can be colored with m colors.
     * 
     * @param graph Adjacency matrix representation of the graph
     *              graph[i][j] = true if there's an edge between vertex i and j
     * @param m     Maximum number of colors available
     * @return true if graph can be colored with ≤ m colors, false otherwise
     * 
     * Time Complexity: O(m^n) in worst case, where n is number of vertices
     * Space Complexity: O(n) for colors array + recursion stack
     */
    public static boolean graphColoring(boolean[][] graph, int m) {
        // Validate inputs
        if (graph == null || graph.length == 0) return true; // Empty graph
        if (m <= 0) return false; // Need at least 1 color
        
        int n = graph.length;
        int[] colors = new int[n]; // 0 = uncolored, 1..m = colors
        return solve(0, graph, m, colors);
    }

    /**
     * Recursive backtracking function to color vertices.
     * Uses DFS to try all possible color assignments.
     * 
     * @param node   Current vertex being colored
     * @param graph  Adjacency matrix
     * @param m      Number of colors available
     * @param colors Current color assignment for each vertex
     * @return true if coloring is possible from current state
     */
    private static boolean solve(int node, boolean[][] graph, int m, int[] colors) {
        int n = graph.length;
        
        // Base case: All vertices colored successfully
        if (node == n) return true;
        
        // Try all possible colors for current vertex
        for (int c = 1; c <= m; c++) {
            if (isSafe(node, c, graph, colors)) {
                // Assign color
                colors[node] = c;
                
                // Recur to color next vertex
                if (solve(node + 1, graph, m, colors)) {
                    return true; // Found valid coloring
                }
                
                // Backtrack: remove color assignment
                colors[node] = 0;
            }
        }
        
        // No valid color found for this vertex
        return false;
    }

    /**
     * Check if assigning 'color' to 'node' is safe (no conflicts with neighbors).
     * 
     * @param node   Vertex to check
     * @param color  Color to assign
     * @param graph  Adjacency matrix
     * @param colors Current color assignments
     * @return true if assignment is safe
     */
    private static boolean isSafe(int node, int color, boolean[][] graph, int[] colors) {
        int n = graph.length;
        
        // Check all adjacent vertices
        for (int neighbor = 0; neighbor < n; neighbor++) {
            // If there's an edge and neighbor has same color
            if (graph[node][neighbor] && colors[neighbor] == color) {
                return false; // Conflict!
            }
        }
        return true;
    }

    /**
     * Enhanced version: Returns the actual coloring if possible.
     * 
     * @param graph Adjacency matrix
     * @param m     Number of colors
     * @return Color assignment array if possible, null otherwise
     */
    public static int[] graphColoringWithSolution(boolean[][] graph, int m) {
        if (graph == null || graph.length == 0) return new int[0];
        if (m <= 0) return null;
        
        int n = graph.length;
        int[] colors = new int[n];
        
        if (solveWithSolution(0, graph, m, colors)) {
            return colors;
        }
        return null;
    }
    
    private static boolean solveWithSolution(int node, boolean[][] graph, int m, int[] colors) {
        int n = graph.length;
        if (node == n) return true;
        
        for (int c = 1; c <= m; c++) {
            if (isSafe(node, c, graph, colors)) {
                colors[node] = c;
                if (solveWithSolution(node + 1, graph, m, colors)) {
                    return true;
                }
                colors[node] = 0;
            }
        }
        return false;
    }

    /**
     * Optimized version with heuristic: Most Constrained Variable (MCV) / Degree heuristic.
     * Color vertices with highest degree first to fail early.
     * 
     * @param graph Adjacency matrix
     * @param m     Number of colors
     * @return true if coloring possible
     */
    public static boolean graphColoringOptimized(boolean[][] graph, int m) {
        if (graph == null || graph.length == 0) return true;
        if (m <= 0) return false;
        
        int n = graph.length;
        int[] colors = new int[n];
        
        // Get vertices sorted by degree (highest first)
        Integer[] vertices = getVerticesByDegree(graph);
        
        return solveOptimized(0, vertices, graph, m, colors);
    }
    
    private static Integer[] getVerticesByDegree(boolean[][] graph) {
        int n = graph.length;
        Integer[] vertices = new Integer[n];
        int[] degrees = new int[n];
        
        // Calculate degrees
        for (int i = 0; i < n; i++) {
            vertices[i] = i;
            for (int j = 0; j < n; j++) {
                if (graph[i][j]) degrees[i]++;
            }
        }
        
        // Sort by degree descending
        Arrays.sort(vertices, (a, b) -> degrees[b] - degrees[a]);
        return vertices;
    }
    
    private static boolean solveOptimized(int idx, Integer[] vertices, 
                                         boolean[][] graph, int m, int[] colors) {
        if (idx == vertices.length) return true;
        
        int node = vertices[idx];
        for (int c = 1; c <= m; c++) {
            if (isSafe(node, c, graph, colors)) {
                colors[node] = c;
                if (solveOptimized(idx + 1, vertices, graph, m, colors)) {
                    return true;
                }
                colors[node] = 0;
            }
        }
        return false;
    }

    /**
     * Find the chromatic number (minimum number of colors needed).
     * Uses binary search between 1 and max degree + 1.
     * 
     * @param graph Adjacency matrix
     * @return Minimum number of colors needed (chromatic number)
     */
    public static int findChromaticNumber(boolean[][] graph) {
        if (graph == null || graph.length == 0) return 0;
        
        int n = graph.length;
        
        // Lower bound: 1 color
        int low = 1;
        
        // Upper bound: maximum degree + 1 (Brook's Theorem upper bound)
        int maxDegree = 0;
        for (int i = 0; i < n; i++) {
            int degree = 0;
            for (int j = 0; j < n; j++) {
                if (graph[i][j]) degree++;
            }
            maxDegree = Math.max(maxDegree, degree);
        }
        int high = maxDegree + 1;
        
        // Binary search for minimum m
        int result = high;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (graphColoring(graph, mid)) {
                result = mid;
                high = mid - 1; // Try smaller m
            } else {
                low = mid + 1;  // Need more colors
            }
        }
        
        return result;
    }

    /**
     * Test driver with comprehensive examples.
     */
    public static void main(String[] args) {
        System.out.println("=== GRAPH COLORING DEMO ===\n");
        
        testCompleteGraph();
        testBipartiteGraph();
        testPlanarGraph();
        testComplexGraph();
        testPerformance();
    }
    
    private static void testCompleteGraph() {
        System.out.println("1. COMPLETE GRAPH K4 (4 vertices, all connected)");
        boolean[][] k4 = {
            {false, true, true, true},
            {true, false, true, true},
            {true, true, false, true},
            {true, true, true, false}
        };
        
        System.out.println("Can color with 3 colors? " + graphColoring(k4, 3));
        System.out.println("Can color with 4 colors? " + graphColoring(k4, 4));
        
        int[] coloring = graphColoringWithSolution(k4, 4);
        System.out.println("Coloring with 4 colors: " + Arrays.toString(coloring));
        
        int chromatic = findChromaticNumber(k4);
        System.out.println("Chromatic number: " + chromatic + " (should be 4 for K4)");
        System.out.println();
    }
    
    private static void testBipartiteGraph() {
        System.out.println("2. BIPARTITE GRAPH (2-colorable)");
        boolean[][] bipartite = {
            {false, true, false, true},
            {true, false, true, false},
            {false, true, false, true},
            {true, false, true, false}
        };
        
        System.out.println("Can color with 1 color? " + graphColoring(bipartite, 1));
        System.out.println("Can color with 2 colors? " + graphColoring(bipartite, 2));
        
        int chromatic = findChromaticNumber(bipartite);
        System.out.println("Chromatic number: " + chromatic + " (should be 2)");
        System.out.println();
    }
    
    private static void testPlanarGraph() {
        System.out.println("3. PLANAR GRAPH (4-colorable by Four Color Theorem)");
        // Example: Graph of US states (simplified)
        boolean[][] planar = {
            // CA, OR, NV, AZ, UT
            {false, true, true, true, false},  // CA
            {true, false, true, false, false}, // OR
            {true, true, false, true, true},   // NV
            {true, false, true, false, true},  // AZ
            {false, false, true, true, false}  // UT
        };
        
        for (int m = 1; m <= 4; m++) {
            System.out.println("Can color with " + m + " colors? " + 
                             graphColoring(planar, m));
        }
        
        int[] coloring = graphColoringWithSolution(planar, 3);
        System.out.println("Coloring with 3 colors: " + Arrays.toString(coloring));
        System.out.println();
    }
    
    private static void testComplexGraph() {
        System.out.println("4. COMPLEX GRAPH (Petersen Graph - needs 3 colors)");
        // Petersen graph is 3-regular, 3-chromatic
        boolean[][] petersen = new boolean[10][10];
        // Outer pentagon
        for (int i = 0; i < 5; i++) {
            petersen[i][(i + 1) % 5] = true;
            petersen[(i + 1) % 5][i] = true;
        }
        // Inner star
        for (int i = 0; i < 5; i++) {
            petersen[i][i + 5] = true;
            petersen[i + 5][i] = true;
            petersen[i + 5][(i + 2) % 5 + 5] = true;
            petersen[(i + 2) % 5 + 5][i + 5] = true;
        }
        
        System.out.println("Chromatic number: " + findChromaticNumber(petersen));
        System.out.println("Standard vs Optimized comparison:");
        
        long start = System.nanoTime();
        boolean standard = graphColoring(petersen, 3);
        long standardTime = System.nanoTime() - start;
        
        start = System.nanoTime();
        boolean optimized = graphColoringOptimized(petersen, 3);
        long optimizedTime = System.nanoTime() - start;
        
        System.out.println("Standard: " + standard + " (" + standardTime/1000 + " μs)");
        System.out.println("Optimized: " + optimized + " (" + optimizedTime/1000 + " μs)");
        System.out.println();
    }
    
    private static void testPerformance() {
        System.out.println("5. PERFORMANCE COMPARISON ON RANDOM GRAPHS");
        
        // Generate random graph with 10 vertices, 30% density
        int n = 10;
        boolean[][] randomGraph = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (Math.random() < 0.3) {
                    randomGraph[i][j] = randomGraph[j][i] = true;
                }
            }
        }
        
        System.out.println("Testing with random graph (n=10)");
        
        for (int m = 1; m <= 5; m++) {
            long start = System.nanoTime();
            boolean result = graphColoring(randomGraph, m);
            long time = System.nanoTime() - start;
            System.out.println("m=" + m + ": " + result + " (" + time/1000 + " μs)");
        }
    }
    
    /**
     * Utility: Convert adjacency matrix to adjacency list for more efficient
     * neighbor checking in isSafe() method.
     */
    private static List<List<Integer>> toAdjacencyList(boolean[][] graph) {
        int n = graph.length;
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                if (graph[i][j]) {
                    adjList.get(i).add(j);
                }
            }
        }
        return adjList;
    }
    
    /**
     * Alternative implementation using adjacency list for efficiency.
     */
    public static boolean graphColoringAdjList(boolean[][] graph, int m) {
        if (graph == null || graph.length == 0) return true;
        
        int n = graph.length;
        List<List<Integer>> adjList = toAdjacencyList(graph);
        int[] colors = new int[n];
        
        return solveAdjList(0, adjList, m, colors);
    }
    
    private static boolean solveAdjList(int node, List<List<Integer>> adjList, 
                                       int m, int[] colors) {
        if (node == adjList.size()) return true;
        
        for (int c = 1; c <= m; c++) {
            if (isSafeAdjList(node, c, adjList, colors)) {
                colors[node] = c;
                if (solveAdjList(node + 1, adjList, m, colors)) {
                    return true;
                }
                colors[node] = 0;
            }
        }
        return false;
    }
    
    private static boolean isSafeAdjList(int node, int color, 
                                        List<List<Integer>> adjList, int[] colors) {
        for (int neighbor : adjList.get(node)) {
            if (colors[neighbor] == color) {
                return false;
            }
        }
        return true;
    }
}