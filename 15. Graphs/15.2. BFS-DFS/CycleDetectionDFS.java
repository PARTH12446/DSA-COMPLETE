import java.util.*;

public class CycleDetectionDFS {

    // Detect cycle in an undirected graph using DFS with parent tracking
    public static boolean isCycle(int V, List<List<Integer>> adj) {
        boolean[] vis = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!vis[i] && dfsHasCycle(i, -1, adj, vis)) {
                return true;
            }
        }
        return false;
    }

    private static boolean dfsHasCycle(int node, int parent, List<List<Integer>> adj, boolean[] vis) {
        vis[node] = true;
        for (int nei : adj.get(node)) {
            if (!vis[nei]) {
                if (dfsHasCycle(nei, node, adj, vis)) return true;
            } else if (nei != parent) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int V = 4;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        addEdge(adj, 0, 1);
        addEdge(adj, 1, 2);
        addEdge(adj, 2, 0); // cycle
        addEdge(adj, 2, 3);
        System.out.println("Has cycle (DFS)? " + isCycle(V, adj));
    }

    private static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
}
