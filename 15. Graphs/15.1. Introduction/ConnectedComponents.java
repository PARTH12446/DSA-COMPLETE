import java.util.*;

public class ConnectedComponents {

    // Union-Find (Disjoint Set Union) to count connected components in an
    // undirected graph with vertices [0..n-1] and given edge list.
    public static int countComponents(int n, int[][] edges) {
        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        // Union for each undirected edge
        for (int[] e : edges) {
            union(e[0], e[1], parent);
        }

        // Count distinct roots
        Set<Integer> roots = new HashSet<>();
        for (int i = 0; i < n; i++) {
            roots.add(find(i, parent));
        }
        return roots.size();
    }

    private static int find(int x, int[] parent) {
        if (parent[x] != x) {
            parent[x] = find(parent[x], parent); // path compression
        }
        return parent[x];
    }

    private static void union(int x, int y, int[] parent) {
        int rootX = find(x, parent);
        int rootY = find(y, parent);
        if (rootX != rootY) {
            parent[rootX] = rootY;
        }
    }

    public static void main(String[] args) {
        // Small example: 5 nodes, edges form 2 components
        int n = 5;
        int[][] edges = {
                {0, 1},
                {1, 2},
                {3, 4}
        };

        // Optional adjacency list representation from edges
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int components = countComponents(n, edges);
        System.out.println("Number of connected components: " + components);

        // Print adjacency list just to visualize the graph
        System.out.println("Adjacency list:");
        for (int i = 0; i < n; i++) {
            System.out.println(i + " -> " + adj.get(i));
        }
    }
}
