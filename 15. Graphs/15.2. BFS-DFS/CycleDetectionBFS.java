import java.util.*;

public class CycleDetectionBFS {

    // Detect cycle in an undirected graph using BFS with parent tracking
    public static boolean isCycle(int V, List<List<Integer>> adj) {
        boolean[] vis = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!vis[i] && bfsHasCycle(i, adj, vis)) {
                return true;
            }
        }
        return false;
    }

    private static boolean bfsHasCycle(int src, List<List<Integer>> adj, boolean[] vis) {
        Queue<int[]> q = new ArrayDeque<>();
        vis[src] = true;
        q.offer(new int[]{src, -1});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int node = cur[0];
            int parent = cur[1];
            for (int nei : adj.get(node)) {
                if (!vis[nei]) {
                    vis[nei] = true;
                    q.offer(new int[]{nei, node});
                } else if (nei != parent) {
                    return true;
                }
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
        System.out.println("Has cycle (BFS)? " + isCycle(V, adj));
    }

    private static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
}
