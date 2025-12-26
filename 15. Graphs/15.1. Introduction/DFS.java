import java.util.*;

public class DFS {

    // Iterative Depth-First Search traversal from source 0
    // V: number of vertices, adj: adjacency list (0-based)
    public static List<Integer> dfsOfGraph(int V, List<List<Integer>> adj) {
        List<Integer> order = new ArrayList<>();
        boolean[] visited = new boolean[V];
        Deque<Integer> stack = new ArrayDeque<>();

        stack.push(0);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (visited[current]) {
                continue;
            }
            visited[current] = true;
            order.add(current);

            // To mimic typical recursive DFS order, push neighbors in reverse
            List<Integer> neighbors = adj.get(current);
            for (int i = neighbors.size() - 1; i >= 0; i--) {
                int nei = neighbors.get(i);
                if (!visited[nei]) {
                    stack.push(nei);
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

        List<Integer> dfsOrder = dfsOfGraph(V, adj);
        System.out.println("DFS traversal from 0: " + dfsOrder);
    }

    private static void addUndirectedEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
}
