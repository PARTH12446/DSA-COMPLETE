import java.util.*;

public class CycleInDirectedGraph {

    // Detect cycle in a directed graph using Kahn's algorithm
    public static boolean isCyclic(int n, List<List<Integer>> adj) {
        int[] indeg = new int[n];
        for (int i = 0; i < n; i++) {
            for (int v : adj.get(i)) {
                indeg[v]++;
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (indeg[i] == 0) q.offer(i);
        }

        int count = 0;
        while (!q.isEmpty()) {
            int node = q.poll();
            count++;
            for (int nei : adj.get(node)) {
                indeg[nei]--;
                if (indeg[nei] == 0) q.offer(nei);
            }
        }
        return count != n; // if not all nodes processed, cycle exists
    }

    public static void main(String[] args) {
        int V = 4;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        // 0 -> 1 -> 2 -> 0 has a cycle
        addEdge(adj, 0, 1);
        addEdge(adj, 1, 2);
        addEdge(adj, 2, 0);
        addEdge(adj, 2, 3);

        System.out.println("Has cycle (directed)? " + isCyclic(V, adj));
    }

    private static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
    }
}
