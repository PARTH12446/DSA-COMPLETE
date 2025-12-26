import java.util.*;

public class Dijkstra {

    // Dijkstra-like relaxation using a simple queue (as in your Python), adjacency: list of (neighbor, weight)
    public static int[] dijkstra(List<List<int[]>> adj, int src) {
        int n = adj.size();
        int INF = 1_000_000_000;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[src] = 0;
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(src);
        while (!q.isEmpty()) {
            int node = q.poll();
            for (int[] edge : adj.get(node)) {
                int nei = edge[0], w = edge[1];
                if (dist[node] + w < dist[nei]) {
                    dist[nei] = dist[node] + w;
                    q.offer(nei);
                }
            }
        }
        for (int i = 0; i < n; i++) if (dist[i] == INF) dist[i] = -1;
        return dist;
    }

    public static void main(String[] args) {
        int V = 5;
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) adj.add(new ArrayList<>());
        addEdge(adj, 0, 1, 2);
        addEdge(adj, 0, 2, 4);
        addEdge(adj, 1, 2, 1);
        addEdge(adj, 1, 3, 7);
        addEdge(adj, 2, 4, 3);

        System.out.println("Dijkstra-like distances from 0: " + Arrays.toString(dijkstra(adj, 0)));
    }

    private static void addEdge(List<List<int[]>> adj, int u, int v, int w) {
        adj.get(u).add(new int[]{v, w});
        adj.get(v).add(new int[]{u, w});
    }
}
