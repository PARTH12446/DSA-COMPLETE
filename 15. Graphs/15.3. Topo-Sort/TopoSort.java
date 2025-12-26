import java.util.*;

public class TopoSort {

    // DFS-based topological sort
    public static List<Integer> topologicalSort(List<List<Integer>> adj) {
        int n = adj.size();
        boolean[] visited = new boolean[n];
        List<Integer> stack = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                dfs(i, adj, visited, stack);
            }
        }

        Collections.reverse(stack);
        return stack;
    }

    private static void dfs(int node, List<List<Integer>> adj, boolean[] visited, List<Integer> stack) {
        if (visited[node]) return;
        visited[node] = true;
        for (int nei : adj.get(node)) {
            dfs(nei, adj, visited, stack);
        }
        stack.add(node);
    }

    public static void main(String[] args) {
        int V = 6;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());

        // Example DAG
        addEdge(adj, 5, 2);
        addEdge(adj, 5, 0);
        addEdge(adj, 4, 0);
        addEdge(adj, 4, 1);
        addEdge(adj, 2, 3);
        addEdge(adj, 3, 1);

        System.out.println("Topological order (DFS): " + topologicalSort(adj));
    }

    private static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
    }
}
