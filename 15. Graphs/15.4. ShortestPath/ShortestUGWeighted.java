import java.util.*;

public class ShortestUGWeighted {

    // Shortest path in a DAG-like directed weighted graph using BFS-style relaxation
    public static int[] shortestPath(int n, int e, int[][] edges) {
        List<List<int[]>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < e; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            int w = edges[i][2];
            adj.get(u).add(new int[]{v, w});
        }

        int INF = 1_000_000_000;
        int[] dist = new int[n];
        Arrays.fill(dist, INF);
        dist[0] = 0;
        Deque<Integer> q = new ArrayDeque<>();
        q.offer(0);
        while (!q.isEmpty()) {
            int node = q.poll();
            for (int[] p : adj.get(node)) {
                int nei = p[0], w = p[1];
                if (dist[node] + w < dist[nei]) {
                    dist[nei] = dist[node] + w;
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
        int n = 4;
        int[][] edges = {
                {0, 1, 1},
                {0, 2, 4},
                {1, 2, 2},
                {1, 3, 6},
                {2, 3, 3}
        };
        System.out.println("Shortest from 0: " + Arrays.toString(shortestPath(n, edges.length, edges)));
    }
}
