import java.util.*;

public class ShortestUG {

    // BFS shortest path in unweighted undirected graph from src
    public static int[] shortestPath(List<List<Integer>> adj, int src) {
        int n = adj.size();
        int INF = 1_000_000_000;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(src);
        while (!q.isEmpty()) {
            int node = q.poll();
            for (int nei : adj.get(node)) {
                if (dist[node] + 1 < dist[nei]) {
                    dist[nei] = dist[node] + 1;
                    q.offer(nei);
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (dist[i] == INF) dist[i] = -1;
        }
        return dist;
    }

    public static void main(String[] args) {
        int V = 6;
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        addEdge(adj, 0, 1);
        addEdge(adj, 0, 2);
        addEdge(adj, 1, 3);
        addEdge(adj, 2, 4);
        addEdge(adj, 3, 5);
        addEdge(adj, 4, 5);

        System.out.println("Distances from 0: " + Arrays.toString(shortestPath(adj, 0)));
    }

    private static void addEdge(List<List<Integer>> adj, int u, int v) {
        adj.get(u).add(v);
        adj.get(v).add(u);
    }
}
