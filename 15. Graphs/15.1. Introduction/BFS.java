import java.util.*;

public class BFS {

    // Breadth-First Search traversal from source 0
    // V: number of vertices, adj: adjacency list (0-based)
    public static List<Integer> bfsOfGraph(int V, List<List<Integer>> adj) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new ArrayDeque<>();

        queue.offer(0);
        visited[0] = true;

        while (!queue.isEmpty()) {
            int current = queue.poll();
            order.add(current);

            for (int nei : adj.get(current)) {
                if (!visited[nei]) {
                    visited[nei] = true;
                    queue.offer(nei);
                }
            }
        }
        return order;
    }

    public static void main(String[] args) {
        // Example: simple undirected graph with 5 vertices
        int V = 5;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // edges: 0-1, 0-2, 1-3, 2-4
        addUndirectedEdge(adj, 0, 1);
        addUndirectedEdge(adj, 0, 2);
        addUndirectedEdge(adj, 1, 3);
        addUndirectedEdge(adj, 2, 4);

        List<Integer> bfsOrder = bfsOfGraph(V, adj);
        System.out.println("BFS traversal from 0: " + bfsOrder);
    }

    private static void addUndirectedEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
}
