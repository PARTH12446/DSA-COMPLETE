import java.util.*;

public class BellmanFord {

    // Bellman-Ford: detect negative cycle and return distances or [-1]
    public static int[] bellmanFord(int V, int[][] edges, int src) {
        int INF = 100_000_000;
        int[] dist = new int[V];
        Arrays.fill(dist, INF);
        dist[src] = 0;
        for (int i = 0; i < V; i++) {
            for (int[] e : edges) {
                int u = e[0], v = e[1], w = e[2];
                if (dist[u] != INF && dist[u] + w < dist[v]) {
                    dist[v] = dist[u] + w;
                }
            }
        }
        for (int[] e : edges) {
            int u = e[0], v = e[1], w = e[2];
            if (dist[u] != INF && dist[u] + w < dist[v]) {
                return new int[]{-1};
            }
        }
        return dist;
    }

    public static void main(String[] args) {
        int V = 5;
        int[][] edges = {
                {0,1,-1},{0,2,4},{1,2,3},{1,3,2},{1,4,2},{3,2,5},{3,1,1},{4,3,-3}
        };
        System.out.println("Bellman-Ford distances: " + Arrays.toString(bellmanFord(V, edges, 0)));
    }
}
