import java.util.*;

public class KahnAlgo {

    // Kahn's algorithm (BFS-based topological sort)
    public static List<Integer> topologicalSort(List<List<Integer>> adj) {
        int n = adj.size();
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

        List<Integer> res = new ArrayList<>();
        while (!q.isEmpty()) {
            int node = q.poll();
            res.add(node);
            for (int nei : adj.get(node)) {
                indeg[nei]--;
                if (indeg[nei] == 0) q.offer(nei);
            }
        }
        return res.size() == n ? res : Collections.emptyList();
    }

    public static void main(String[] args) {
        int V = 6;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        addEdge(adj, 5, 2);
        addEdge(adj, 5, 0);
        addEdge(adj, 4, 0);
        addEdge(adj, 4, 1);
        addEdge(adj, 2, 3);
        addEdge(adj, 3, 1);

        System.out.println("Topological order (Kahn): " + topologicalSort(adj));
    }

    private static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
    }
}
